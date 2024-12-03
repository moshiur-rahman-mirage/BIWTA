package com.zaberp.zab.biwtabackend.controller;



import com.zaberp.zab.biwtabackend.id.MmprescriptionId;
import com.zaberp.zab.biwtabackend.model.Mmprescription;
import com.zaberp.zab.biwtabackend.service.MmprescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mmprescription")
@RequiredArgsConstructor
public class MmprescriptionController {

    private final MmprescriptionService mmprescriptionService;

    @GetMapping
    public List<Mmprescription> getAllPrescriptions() {

        return mmprescriptionService.findAll();
    }

    @GetMapping("/{zid}/{xcase}/{xrow}")
    public ResponseEntity<Mmprescription> getPrescriptionById(
            @PathVariable Integer zid, @PathVariable String xcase, @PathVariable Integer xrow) {
        MmprescriptionId id = new MmprescriptionId(zid, xcase, xrow);
        return mmprescriptionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mmprescription createPrescription(@RequestBody Mmprescription mmprescription) {
        return mmprescriptionService.save(mmprescription);
    }

    @PutMapping("/{zid}/{xcase}/{xrow}")
    public ResponseEntity<Mmprescription> updatePrescription(
            @PathVariable Integer zid, @PathVariable String xcase, @PathVariable Integer xrow,
            @RequestBody Mmprescription mmprescription) {
        MmprescriptionId id = new MmprescriptionId(zid, xcase, xrow);
        return mmprescriptionService.findById(id)
                .map(existingPrescription -> {
                    mmprescription.setZid(zid);
                    mmprescription.setXcase(xcase);
                    mmprescription.setXrow(xrow);
                    return ResponseEntity.ok(mmprescriptionService.save(mmprescription));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{zid}/{xcase}/{xrow}")
    public ResponseEntity<Void> deletePrescription(
            @PathVariable Integer zid, @PathVariable String xcase, @PathVariable Integer xrow) {
        MmprescriptionId id = new MmprescriptionId(zid, xcase, xrow);
        if (mmprescriptionService.findById(id).isPresent()) {
            mmprescriptionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

