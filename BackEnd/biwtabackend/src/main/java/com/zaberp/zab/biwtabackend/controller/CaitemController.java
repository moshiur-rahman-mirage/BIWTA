package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.service.CaitemService;
import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class CaitemController extends BaseController<Caitem,CaitemId>{

    private final CaitemService caitemService;
    private final PrimaryKeyService primaryKeyService;
    @Autowired
    public CaitemController(CaitemService caitemService, PrimaryKeyService primaryKeyService) {
        this.caitemService = caitemService;
        this.primaryKeyService = primaryKeyService;
    }

    @PostMapping
    public ResponseEntity<Caitem> createItem(@RequestBody Caitem caitem) {
        Caitem createdItem = caitemService.createItem(caitem);
        return ResponseEntity.ok(createdItem);
    }

    @Override
    protected CommonService<Caitem, CaitemId> getService() {
        return caitemService;
    }


    @GetMapping("/search")
    public List<Caitem> search(
            @RequestParam("zid") int zid,
            @RequestParam("text") String searchText
    ) {
        return caitemService.searchByText(zid, searchText);

    }
}
