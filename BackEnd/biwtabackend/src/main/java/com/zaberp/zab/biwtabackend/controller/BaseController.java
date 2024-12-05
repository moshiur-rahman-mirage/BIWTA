package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public abstract class BaseController<T, ID> {

    private final CommonService<T, ID> commonService;

    @Autowired
    public BaseController(CommonService<T, ID> commonService) {
        this.commonService = commonService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getEntityById(@PathVariable ID id) {
        Optional<T> entity = commonService.getRepository().findById(id);
        return entity.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<T> createEntity(@RequestBody T entity) {
        T savedEntity = commonService.save(entity);
        return ResponseEntity.status(201).body(savedEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> updateEntity(@PathVariable ID id, @RequestBody T updatedEntity) {
        Optional<T> existingEntity = commonService.getRepository().findById(id);
        if (existingEntity.isPresent()) {
            // You can copy properties or perform any specific updates here
            T entity = existingEntity.get();
            // Assuming the service handles saving
            return ResponseEntity.ok(commonService.save(updatedEntity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable ID id) {
        Optional<T> entity = commonService.getRepository().findById(id);
        if (entity.isPresent()) {
            commonService.getRepository().deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
