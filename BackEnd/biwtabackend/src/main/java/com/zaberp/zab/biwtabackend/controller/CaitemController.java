package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.service.CaitemService;
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
public class CaitemController {

    private final CaitemService caitemService;
    private final PrimaryKeyService primaryKeyService;
    @Autowired
    public CaitemController(CaitemService caitemService, PrimaryKeyService primaryKeyService) {
        this.caitemService = caitemService;
        this.primaryKeyService = primaryKeyService;
    }

    @GetMapping
    public ResponseEntity<List<Caitem>> getAllItems() {
        List<Caitem> items = caitemService.getAllItems();
        return ResponseEntity.ok(items);
    }


    @GetMapping("/{zid}")
    public ResponseEntity<List<Caitem>> getCaitemByZid(
            @PathVariable int zid) {
        List<Caitem> caitemList = caitemService.findByZid(zid);
        if (caitemList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(caitemList);
    }



    @GetMapping("/{zid}/{xitem}")
    public ResponseEntity<Caitem> getItemById(
            @PathVariable int zid,
            @PathVariable String xitem) {
        CaitemId id = new CaitemId(zid, xitem);
        return caitemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Caitem> createItem(@RequestBody Caitem caitem) {
        Caitem createdItem = caitemService.createItem(caitem);
        return ResponseEntity.ok(createdItem);
    }


    @PutMapping("/{zid}/{xitem}")
    public ResponseEntity<Caitem> updateItem(
            @PathVariable int zid,
            @PathVariable String xitem,
            @RequestBody Caitem caitem) {
        if (zid != caitem.getZid() || !xitem.equals(caitem.getXitem())) {
            return ResponseEntity.badRequest().build();
        }
        caitem.setZutime(LocalDateTime.now());
        caitem.setZuuserid(SecurityContextHolder.getContext().getAuthentication().getName());
        Caitem updatedItem = caitemService.updateItem(caitem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{zid}/{xitem}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable int zid,
            @PathVariable String xitem) {
        CaitemId id = new CaitemId(zid, xitem);
        caitemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/items/{zid}")
    public Page<Caitem> getItems(
            @PathVariable int zid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ztime") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return caitemService.getItemsWithPaginationAndSorting(page, size, sortBy, ascending);
    }


}
