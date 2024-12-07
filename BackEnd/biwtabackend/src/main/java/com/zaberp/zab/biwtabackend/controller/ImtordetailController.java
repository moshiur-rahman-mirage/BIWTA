package com.zaberp.zab.biwtabackend.controller;



import com.zaberp.zab.biwtabackend.dto.ImtordetailDto;
import com.zaberp.zab.biwtabackend.id.ImtordetailId;
import com.zaberp.zab.biwtabackend.model.Imtordetail;
import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.ImtordetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/imtordetail")
public class ImtordetailController extends BaseController<Imtordetail,ImtordetailId> {

    @Autowired
    private ImtordetailService service;

    @PostMapping
    public ResponseEntity<Imtordetail> save(@RequestBody Imtordetail detail) {
        System.out.println(detail.getZid());
        System.out.println(detail.getXtornum());
        Imtordetail savedDetail = service.save(detail);
        System.out.println(savedDetail);
        return ResponseEntity.ok(savedDetail);
    }

    @GetMapping("/{zid}/{xtornum}")
    public Page<ImtordetailDto> getImtordetail(
            @PathVariable("zid") int zid,
            @PathVariable("xtornum") String xtornum,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return service.getImtordetail(zid,xtornum, page, size);
    }

    @Override
    protected CommonService<Imtordetail, ImtordetailId> getService() {
        return service;
    }
}

