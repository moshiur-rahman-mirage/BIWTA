package com.zaberp.zab.biwtabackend.controller;

import com.zaberp.zab.biwtabackend.dto.GrnItem;
import com.zaberp.zab.biwtabackend.dto.GrnRequest;
import com.zaberp.zab.biwtabackend.model.Pogrndetail;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.repository.PogrnHeaderRepository;
import com.zaberp.zab.biwtabackend.repository.PogrndetailRepository;
import com.zaberp.zab.biwtabackend.service.PrimaryKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/grn")
public class GrnController {

    @Autowired
    private PogrnHeaderRepository pogrnheaderRepository;

    @Autowired
    private final PrimaryKeyService primaryKeyService;



    @Autowired
    private PogrndetailRepository pogrndetailRepository;

    public GrnController(PrimaryKeyService primaryKeyService) {
        this.primaryKeyService = primaryKeyService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGrn(@RequestBody GrnRequest grnRequest) {
        // Generate GRN number (you can replace this logic with your own)
        System.out.println("inside create grn");
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(grnRequest.getZid(),"GRN Number","GRN-",6);

        // Create header object
        Pogrnheader header = new Pogrnheader();
        header.setXstatusdoc("Open");
        header.setXref(grnRequest.getXref());
        header.setXorg(grnRequest.getXorg());
        header.setXnote(grnRequest.getXnote());
        header.setXwh(grnRequest.getXwh());
        header.setZid(grnRequest.getZid());
        header.setXgrnnum(generatedKey);
        header.setXcus(grnRequest.getXcus());
        header.setXdate(grnRequest.getXdate());
        header.setZtime(LocalDateTime.now());
        header.setZauserid(grnRequest.getZauserid());

        pogrnheaderRepository.save(header);

        // Process items and create detail records
        int rowNumber = 1;
        for (GrnItem item : grnRequest.getItems()) {
            Pogrndetail detail = new Pogrndetail();
            detail.setXgrnnum(generatedKey);
            detail.setZid(header.getZid());
            detail.setXrow(rowNumber++);
            detail.setXitem(item.getXitem());
            detail.setXqtygrn(item.getXqty());
            detail.setXrate(item.getXrate());
            detail.setXlineamt(item.getXqty().multiply(item.getXrate()));
            detail.setZtime(LocalDateTime.now());
            detail.setZutime(LocalDateTime.now());
            pogrndetailRepository.save(detail);
        }

        return ResponseEntity.ok("GRN created successfully with GRN Number: " + generatedKey);
    }
}

