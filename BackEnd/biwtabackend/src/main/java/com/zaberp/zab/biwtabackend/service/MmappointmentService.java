package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import com.zaberp.zab.biwtabackend.repository.MmappointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MmappointmentService extends CommonServiceImpl<Mmappointment,MmappointmentId>{

    @Autowired
    private MmappointmentRepository mmappointmentRepository;


    private final MmappointmentRepository repository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PrimaryKeyService primaryKeyService;

    public MmappointmentService(MmappointmentRepository repository, NamedParameterJdbcTemplate jdbcTemplate, PrimaryKeyService primaryKeyService) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
        this.primaryKeyService = primaryKeyService;
    }



    public Mmappointment save(Mmappointment mmAppointment) {
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(mmAppointment.getZid(),"Inventory Transaction","RX--",6);
        mmAppointment.setXcase(generatedKey);
        return repository.save(mmAppointment);
    }

    public void deleteById(MmappointmentId id) {
        repository.deleteById(id);
    }


    @Override
    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public JpaRepository<Mmappointment, MmappointmentId> getRepository() {
        return repository;
    }


    @Override
    protected String getTableName() {
        return "Mmappointment";
    }

    @Override
    protected RowMapper<Mmappointment> getRowMapper() {
        return null;
    }

    public Mmappointment findByZidAndXcase(int zid, String xcase) {
        return repository.findByZidAndXcase(zid, xcase);
    }
}


