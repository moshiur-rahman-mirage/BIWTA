package com.zaberp.zab.biwtabackend.controllerimpl;

import com.zaberp.zab.biwtabackend.controller.GenericController;
import com.zaberp.zab.biwtabackend.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenericControllerImpl implements GenericController {

    @Autowired
    private GenericService genericService;

    @PutMapping("/update")
    @Override
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


            int updatedRows = genericService.updateTableWithDynamicColumnsAndWhere(
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
