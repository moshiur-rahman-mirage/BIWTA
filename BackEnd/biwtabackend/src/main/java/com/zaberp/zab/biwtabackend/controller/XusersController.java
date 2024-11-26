package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.id.XusersId;
import com.zaberp.zab.biwtabackend.service.XusersService;
import com.zaberp.zab.biwtabackend.util.ApplicationContextData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/xusers")
public class XusersController {

    private final XusersService service;


    public XusersController(XusersService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createXusers(@RequestBody Xusers xusers) {

        if (service.existsByZidAndZemailAndXposition(xusers.getZid(), xusers.getZemail(), xusers.getXposition())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A user with the same Position already exists.");
        }

        if (service.existsByZidAndZemail(xusers.getZid(), xusers.getZemail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: A user with the same Login Id already exists.");
        }
        // Validate xusername
        if (xusers.getXposition() == null || xusers.getXposition().isBlank()) {
            return ResponseEntity.badRequest().body("Validation failed: User ID cannot be blank.");
        }

        // Validate xpassword
        if (xusers.getXpassword() == null || xusers.getXpassword().isBlank()) {
            return ResponseEntity.badRequest().body("Validation failed: Password cannot be blank.");
        }
        if (xusers.getXpassword().length() < 8) {
            return ResponseEntity.badRequest().body("Validation failed: Password must be at least 8 characters long.");
        }

        // Save the entity if validation passes
        Xusers savedUser = service.save(xusers);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{zid}/{zemail}")
    public ResponseEntity<?> updateXcodes(
            @PathVariable int zid,
            @PathVariable String zemail,
            @RequestBody Xusers updatedXusers) {

        if (updatedXusers.getXposition() == null || updatedXusers.getXposition().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Validation failed: 'User Position' field is required.");
        }

        XusersId id = new XusersId(zid, zemail);
        return service.findById(id)
                .map(existingXusers -> {
                    updatedXusers.setZid(existingXusers.getZid());
                    updatedXusers.setZemail(existingXusers.getZemail());
                    // Update the entity
                    return ResponseEntity.ok(service.save(updatedXusers));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{zid}/{zemail}")
    public ResponseEntity<Void> deleteXcodes(
            @PathVariable int zid,
            @PathVariable String zemail) {
        XusersId id = new XusersId(zid, zemail);

        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Xusers> searchXusers(
            @RequestParam int zid,
            @RequestParam(required = false) String searchtext) {
        return service.searchByText(zid,searchtext);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Xusers xusers) {
        String zemail = xusers.getZemail();
        String xpassword = xusers.getXpassword();
        boolean isValid = service.validateUser(zemail, xpassword);

        if (isValid) {
            Xusers user = service.findByZemail(zemail);
            int zid = user.getZid();
            System.out.println(zid);
            String xwh = user.getXwh();
            String xrole = user.getXrole();

            // Create a response map to include zid, xwh, and a success message
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("zid", zid);
            response.put("xwh", xwh);
            response.put("xroles",xrole);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid credentials.");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}


