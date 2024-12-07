package com.zaberp.zab.biwtabackend.controller;


import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.service.MmappointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mmappointment")
public class MmappointmentController {
    @Autowired
    private MmappointmentService service;

    @GetMapping()
    public List<Mmappointment> findAll(
            @RequestParam("zid") int zid
    ) {
        return service.getAll(zid);
    }


//    api/appintment?zid=100000

//    api/mmappontment/100000/Case-00000000001

    @GetMapping("{zid}/{xcase}")
    public Mmappointment findByCase(
            @PathVariable("zid") int zid,
            @PathVariable("case") String xcase
    ){
        return service.getAllItems(zid,xcase);
    }
}
