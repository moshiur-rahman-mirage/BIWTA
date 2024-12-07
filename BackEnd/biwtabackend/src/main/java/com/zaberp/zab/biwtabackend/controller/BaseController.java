package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.dto.ConfirmTrnDto;
import com.zaberp.zab.biwtabackend.dto.DynamicRequest;
import com.zaberp.zab.biwtabackend.dto.RequestBodyDto;
import com.zaberp.zab.biwtabackend.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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



    @GetMapping("/{zid}/rows")
    public ResponseEntity<List<T>> findRows(
            @PathVariable int zid,
            @RequestParam String column,
            @RequestParam String transactionNumber) {
        List<T> result = getService().findRowsOfTransaction(zid, column, transactionNumber);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
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


    @DeleteMapping("/delete/details")
    public ResponseEntity<Void> deleteByConditions(
            @RequestParam int zid,
            @RequestParam String column,
            @RequestParam String transactionNumber,
            @RequestParam int row
    ) {
        getService().deleteByConditions(zid, column, transactionNumber,row);
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

    @PostMapping("/findByZidAndOther/{columnName}/{columnValue}")
    public ResponseEntity<Page<T>> getRecords(
            @PathVariable("columnName") String columnName, // Path variable for 'columnName'
            @PathVariable("columnValue") String columnValue, // Path variable for 'columnValue'
            @RequestBody RequestBodyDto requestBody // Request body for pagination and sorting
    ) {

        Page<T> result = getService().findByZidAndOtherWithPaginationAndSorting(
                requestBody.getZid(), columnName, columnValue, requestBody.getPage(), requestBody.getSize(),
                requestBody.getSortBy(), requestBody.isAscending()
        );
//        System.out.println(result.stream().toArray());
        // Return the result wrapped in a ResponseEntity
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{zid}/search")
    public ResponseEntity<List<T>> getBySearchTextAndZid(
            @PathVariable int zid,
            @RequestParam String searchText,
            @RequestParam List<String> searchFields) {


        List<T> result = getService().getBySearchTextAndZid(zid, searchText, searchFields);

        if (result == null || result.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }else {
            System.out.println(result);
            return ResponseEntity.ok(result);
        }
    }




    @PostMapping("/fetch")
    public ResponseEntity<?> getDynamicData(
            @RequestBody DynamicRequest dynamicRequest) {
        try {
            List<Map<String, Object>> result = getService().getDynamicDataWithColumnNames(
                    dynamicRequest.getSelectedFields(),
                    dynamicRequest.getWhereConditions()
            );

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid input",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal server error",
                    "message", e.getMessage()
            ));
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

        @PostMapping("/confirmRequest")
    public String confirmRequest(@RequestBody ConfirmTrnDto confirmTrnDto){
        int zid = confirmTrnDto.getZid();
        String user=confirmTrnDto.getUser();
        String position=confirmTrnDto.getUser();
        String wh = confirmTrnDto.getWh();
        String tornum = confirmTrnDto.getTornum();
        String request = confirmTrnDto.getRequest();
        String updated=confirmTrnDto.getUpdated();

            System.out.println("Details:");
            System.out.println("ZID: " + zid);
            System.out.println("User: " + user);
            System.out.println("Position: " + position);
            System.out.println("Warehouse (WH): " + wh);
            System.out.println("TORNUM: " + tornum);
            System.out.println("Request: " + request);

        return getService().confirmRequest(zid, user, position,wh,tornum,request);
    }


        @PostMapping("/approveRequest")
    public String approveRequest(@RequestBody ConfirmTrnDto confirmTrnDto) {
            System.out.println("Received DTO: " + confirmTrnDto);
        if (confirmTrnDto == null) {
            return "Error: Request body is null";
        }

        int zid = confirmTrnDto.getZid();
        String user = confirmTrnDto.getUser();
        String position = confirmTrnDto.getPosition();
        String tornum = confirmTrnDto.getTornum();
        String status = confirmTrnDto.getStatus();
        String aprcs = confirmTrnDto.getRequest();

            System.out.println("Details:");
            System.out.println("ZID: " + zid);
            System.out.println("User: " + user);
            System.out.println("Position: " + position);
            System.out.println("TORNUM: " + tornum);
            System.out.println("status: " + status);
            System.out.println("Request: " + aprcs);

        if (user == null || position == null || tornum == null || status == null || aprcs == null) {
            return "Error: Missing required fields";
        }

        try {
            System.out.println("in try");
            String result = getService().approveRequest(zid, user, position, tornum, 0, status, aprcs);
            return result;
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }
}
