package com.zaberp.zab.biwtabackend.service;

<<<<<<< HEAD
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.repository.MmappointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.repository.MmappointmentRepository;
>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MmappointmentService {
<<<<<<< HEAD
    @Autowired
    private MmappointmentRepository mmappointmentRepository;

    public Mmappointment getAllItems(int zid,String xcase) {
        return mmappointmentRepository.findByZidAndXcase(zid,xcase);
    }

    public List<Mmappointment> getAll(int zid){
        return mmappointmentRepository.findByZid(zid);
    }


}
=======

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

>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
