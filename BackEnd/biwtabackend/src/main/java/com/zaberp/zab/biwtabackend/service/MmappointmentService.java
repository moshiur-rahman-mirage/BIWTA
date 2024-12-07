package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.repository.MmappointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MmappointmentService {
    @Autowired
    private MmappointmentRepository mmappointmentRepository;

    public Mmappointment getAllItems(int zid,String xcase) {
        return mmappointmentRepository.findByZidAndXcase(zid,xcase);
    }

    public List<Mmappointment> getAll(int zid){
        return mmappointmentRepository.findByZid(zid);
    }


}
