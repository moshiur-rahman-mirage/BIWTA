package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.repository.MmappointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MmappointmentService {

    @Autowired
    private MmappointmentRepository mmappointmentRepository;


    private final MmappointmentRepository repository;

    private final PrimaryKeyService primaryKeyService;

    public MmappointmentService(MmappointmentRepository repository, PrimaryKeyService primaryKeyService) {
        this.repository = repository;
        this.primaryKeyService = primaryKeyService;
    }

    public List<Mmappointment> findAll() {
        return repository.findAll();
    }

    public Mmappointment save(Mmappointment mmAppointment) {
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(mmAppointment.getZid(),"Inventory Transaction","RX--",6);
        mmAppointment.setXcase(generatedKey);
        return repository.save(mmAppointment);
    }

    public void deleteById(MmappointmentId id) {
        repository.deleteById(id);
    }

    public Mmappointment findById(MmappointmentId id) {
        return repository.findById(id).orElse(null);
    }

    public List<Mmappointment> findByZid(int zid) {
        return repository.findByZid(zid);
    }

    public Mmappointment findByZidAndXcase(int zid, String xcase) {
        return repository.findByZidAndXcase(zid, xcase);
    }
}


