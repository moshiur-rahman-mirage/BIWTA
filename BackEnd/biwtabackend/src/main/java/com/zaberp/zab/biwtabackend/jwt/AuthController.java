package com.zaberp.zab.biwtabackend.jwt;

import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.model.Zbusiness;
import com.zaberp.zab.biwtabackend.service.PdmstService;
import com.zaberp.zab.biwtabackend.service.XusersService;
import com.zaberp.zab.biwtabackend.service.ZbusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final XusersService service;

    @Autowired final PdmstService pdmstService;

    @Autowired final ZbusinessService zbusinessService;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, XusersService service, PdmstService pdmstService, ZbusinessService zbusinessService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.service = service;
        this.pdmstService = pdmstService;
        this.zbusinessService = zbusinessService;
    }




    // Working perfectly
//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody Map<String, String> request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password"))
//        );
//
//
//        String token = jwtTokenUtil.generateToken(((User) authentication.getPrincipal()).getUsername());
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//        return response;
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.get("zemail"),
                        request.get("xpassword")
                )
        );

        // Get the authenticated user's username (email in this case)
        String username = ((User) authentication.getPrincipal()).getUsername();

        // Fetch the user details from the database
        Xusers user = service.findByZemail(username);
        Pdmst pdmst = pdmstService.findByZidAndXposition(user.getZid(),user.getXposition());
        Zbusiness zbusiness = zbusinessService.findByZid(user.getZid());

        // Generate the JWT token
        String token = jwtTokenUtil.generateToken(username);

        // Prepare the response with additional fields
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("zid", user.getZid());
        response.put("xwh", user.getXwh());
        response.put("xname", pdmst != null ? pdmst.getXname() : user.getZemail());
        response.put("xroles", user.getXrole());
        response.put("zorg",zbusiness.getZorg());
        // Return the response as JSON
        return ResponseEntity.ok(response);
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Extract token from "Bearer <token>" format
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Validate the token
            if (!jwtTokenUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            // Get username from token
            String username = jwtTokenUtil.extractUsername(token);

            // Fetch user details (optional)
            Xusers user = service.findByZemail(username);

            // Respond with user details
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed");
        }
    }

}

