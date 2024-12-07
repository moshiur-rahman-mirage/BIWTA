package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.dto.ConfirmTrnDto;
import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.service.PogrnHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pogrnheader")
public class PogrnHeaderController {

    @Autowired
    private PogrnHeaderService service;

    @GetMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Pogrnheader> getByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
        Optional<Pogrnheader> entity = service.getByZidAndXgrnnum(zid, xgrnnum);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping()
    public Page<PogrnheaderXcusdto> getItems(
            @RequestParam int zid,
            @RequestParam Optional<String> user,
            @RequestParam Optional<String> superior,
            @RequestParam Optional<String> status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xgrnnum") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        String finalSuperior = superior.orElse(""); // Default to empty string if absent
        String finalStatus = status.orElse("");
        String allUser= user.orElse("");

        return service.getPogrnList(zid,allUser,finalSuperior,finalStatus,page, size, sortBy, ascending);
    }

    @PostMapping
    public ResponseEntity<Pogrnheader> createItem(@RequestBody Pogrnheader pogrnheader) {
        Pogrnheader createdGrn = service.createGrn(pogrnheader);
        return ResponseEntity.ok(createdGrn);
    }

    @PutMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Pogrnheader> updateByZidAndXgrnnum(
            @PathVariable int zid,
            @PathVariable String xgrnnum,
            @RequestBody Pogrnheader entity) {
        if (entity.getZid() != zid || !entity.getXgrnnum().equals(xgrnnum)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateByZidAndXgrnnum(entity));
    }



    @DeleteMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<Void> deleteXgrnnum(
            @PathVariable int zid,
            @PathVariable String xgrnnum) {
        PogrnHeaderId id = new PogrnHeaderId(zid, xgrnnum);
        service.deleteGrn(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<PogrnheaderXcusdto> search(
            @RequestParam("zid") int zid,
            @RequestParam("text") String searchText
    ) {
        return service.searchByText(zid, searchText);
    }

    @PostMapping("/confirmGRN")
    public String confirmGRN(@RequestBody ConfirmTrnDto confirmGrn){
        int zid = confirmGrn.getZid();
        String zemail=confirmGrn.getZemail();
        String xgrnnum = confirmGrn.getXgrnnum();
        Date xdate = confirmGrn.getXdate();
        String xwh = confirmGrn.getXwh();
        int len=confirmGrn.getLen();
        return service.confirmGRN(zid, zemail, xgrnnum,xdate,xwh,len);
    }





}
