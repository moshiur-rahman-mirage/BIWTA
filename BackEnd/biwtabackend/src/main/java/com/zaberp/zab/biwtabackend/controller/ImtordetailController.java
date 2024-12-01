package com.zaberp.zab.biwtabackend.controller;



import com.zaberp.zab.biwtabackend.dto.ImtordetailDto;
import com.zaberp.zab.biwtabackend.id.ImtordetailId;
import com.zaberp.zab.biwtabackend.model.Imtordetail;
import com.zaberp.zab.biwtabackend.service.ImtordetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/imtordetails")
public class ImtordetailController {

    @Autowired
    private ImtordetailService service;

    @PostMapping
    public ResponseEntity<Imtordetail> save(@RequestBody Imtordetail detail) {

        Imtordetail savedDetail = service.save(detail);
        System.out.println(savedDetail);
        return ResponseEntity.ok(savedDetail);
    }

    @PutMapping("")
    public ResponseEntity<Imtordetail> updateByZidAndXtornum(
            @RequestParam int zid,
            @RequestParam String xtornum,
            @RequestParam int xrow,
            @RequestBody Imtordetail detail) {
        if (detail.getZid() != zid || !detail.getXtornum().equals(xtornum) || detail.getXrow()!=xrow) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateByZidAndXtornumAndXrow(detail));
    }


    @DeleteMapping("")
    public ResponseEntity<Void> deleteDetail(@RequestParam Integer zid,
                                             @RequestParam String xtornum,
                                             @RequestParam Integer xrow) {
        ImtordetailId id = new ImtordetailId(zid, xtornum, xrow);
        service.deleteDetail(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{zid}/{xtornum}")
    public Page<ImtordetailDto> getImtordetail(
            @PathVariable("zid") int zid,
            @PathVariable("xtornum") String xtornum,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return service.getImtordetail(zid,xtornum, page, size);
    }


}

