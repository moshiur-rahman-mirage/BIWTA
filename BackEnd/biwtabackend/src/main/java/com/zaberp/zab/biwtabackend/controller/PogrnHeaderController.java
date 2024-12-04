package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.dto.ConfirmGrnDto;
import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.service.PogrnHeaderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
            @RequestParam String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "xgrnnum") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return service.findPogrnWithSupplier(zid,user,page, size, sortBy, ascending);
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

//    @DeleteMapping("/{zid}/{xgrnnum}")
//    public ResponseEntity<Void> deleteByZidAndXgrnnum(@PathVariable int zid, @PathVariable String xgrnnum) {
//        service.deleteByZidAndXgrnnum(zid, xgrnnum);
//        return ResponseEntity.noContent().build();
//    }

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
    public String confirmGRN(@RequestBody ConfirmGrnDto confirmGrn){
        int zid = confirmGrn.getZid();
        String zemail=confirmGrn.getZemail();
        String xgrnnum = confirmGrn.getXgrnnum();
        Date xdate = confirmGrn.getXdate();
        String xwh = confirmGrn.getXwh();
        int len=confirmGrn.getLen();
        return service.confirmGRN(zid, zemail, xgrnnum,xdate,xwh,len);
    }

    @PostMapping("/confirmRequest")
    public String confirmRequest(@RequestBody ConfirmGrnDto confirmGrn){
        int zid = confirmGrn.getZid();
        String user=confirmGrn.getUser();
        String position=confirmGrn.getUser();
        String wh = confirmGrn.getWh();
        String tornum = confirmGrn.getTornum();
        String request = confirmGrn.getRequest();

        return service.confirmRequest(zid, user, position,wh,tornum,request);
    }


    @Transactional
    @PatchMapping("/{zid}/{xgrnnum}")
    public ResponseEntity<String> updatePdsignatory(
            @PathVariable int zid,
            @PathVariable String xgrnnum,
            @RequestBody Map<String, Object> updates) {
        List<String> excludeColumns = List.of("ztime", "zauserid");

        boolean success = service.updatePogrnheader(zid, xgrnnum, updates, excludeColumns);

        if (success) {
            return ResponseEntity.ok("Update successful.");
        } else {
            return ResponseEntity.badRequest().body("No records were updated. Please check the input.");
        }
    }
}
