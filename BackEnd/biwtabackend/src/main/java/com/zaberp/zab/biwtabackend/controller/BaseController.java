package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.service.CommonService;
import com.zaberp.zab.biwtabackend.service.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class BaseController<T, Id> {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected abstract CommonService<T, Id> getService();

    @GetMapping("/{zid}")
    public ResponseEntity<Iterable<T>> findByZid(@PathVariable int zid) {
        Iterable<T> result = getService().findByZid(zid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{zid}/transaction")
    public ResponseEntity<Optional<T>> findByZidAndTransactionNumber(
            @PathVariable int zid,
            @RequestParam String column,
            @RequestParam String transactionNumber) {
        Optional<T> result = getService().findByZidAndTransactionNumber(zid, column, transactionNumber);
        if (result.isPresent()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{zid}/transaction")
    public ResponseEntity<Void> deleteByZidAndTransactionNumber(
            @PathVariable int zid,
            @RequestParam String column,
            @RequestParam String transactionNumber) {
        getService().deleteByZidAndTransactionNumber(zid, column, transactionNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{zid}/paginated")
    public ResponseEntity<Page<T>> findByZidWithPaginationAndSorting(
            @PathVariable int zid,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean ascending) {
        Page<T> result = getService().findByZidWithPaginationAndSorting(zid, page, size, sortBy, ascending);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{zid}/search")
    public ResponseEntity<List<T>> getBySearchTextAndZid(
            @PathVariable int zid,
            @RequestParam String searchText,
            @RequestParam List<String> searchFields) {


        logger.info("Search request received for zid: {}, searchText: {}, searchFields: {}", zid, searchText, searchFields);

        List<T> result = getService().getBySearchTextAndZid(zid, searchText, searchFields);

        logger.info("Search result size: {}", result != null ? result.size() : 0);

        if (result == null || result.isEmpty()) {
            System.out.println("Inside");
            return ResponseEntity.ok(Collections.emptyList());
        }else {
            System.out.println("outside");
            return ResponseEntity.ok(result);
        }
    }



    @PutMapping("/update")
    public String updateTable(@RequestBody Map<String, Object> payload) {
        try {

            System.out.println("Payload received in Controller: " + payload);

            if (payload == null || !payload.containsKey("tableName") || !payload.containsKey("updates") || !payload.containsKey("whereConditions")) {
                System.out.println("00");
                return "Error: Missing required fields (tableName, updates, or whereConditions).";
            }

            String tableName = (String) payload.get("tableName");

            @SuppressWarnings("unchecked")
            Map<String, Object> updates = (Map<String, Object>) payload.get("updates");

            @SuppressWarnings("unchecked")
            Map<String, Object> whereConditions = (Map<String, Object>) payload.get("whereConditions");

            if (tableName == null || updates == null || whereConditions == null) {

                return "Error: Invalid data in the request.";
            }


            int updatedRows = getService().updateTableWithDynamicColumnsAndWhere(
                    tableName,
                    updates,
                    whereConditions
            );


            return updatedRows + " row(s) updated successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
