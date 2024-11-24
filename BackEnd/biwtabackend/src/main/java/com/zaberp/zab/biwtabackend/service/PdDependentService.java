package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.PdDependentId;
import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.repository.PdDependentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PdDependentService {

    @Autowired
    private PdDependentRepository repository;

    public List<PdDependent> findAll() {
        return repository.findAll();
    }

    public Optional<PdDependent> findById(PdDependentId id) {
        return repository.findById(id);
    }

    public PdDependent save(PdDependent pdDependent) {
        return repository.save(pdDependent);
    }

    public boolean existsByZidAndXstaffAndXrow(Integer zid, String xstaff, int xrow) {
        PdDependentId id = new PdDependentId(zid, xstaff, xrow);
        return repository.existsById(id);
    }

    public void deleteById(PdDependentId id) {
        repository.deleteById(id);
    }

    public List<PdDependent> findPdDependent(Integer zid, String xstaff,String xrelation) {
        Specification<PdDependent> spec = Specification.where(zidEquals(zid));
        if (xstaff != null && !xstaff.isEmpty()) {
            spec = spec.and(xstaffEquals(xstaff));
        }

        if (xrelation != null && !xrelation.isEmpty()) {
            spec = spec.and(xrelationEquals(xrelation));
        }
        return repository.findAll(spec);
    }

    private Specification<PdDependent> zidEquals(Integer zid) {
        return (root, query, builder) ->
                zid == null  ? builder.conjunction() : builder.equal(root.get("zid"), zid);
    }

    private Specification<PdDependent> xrelationEquals(String xrelation) {
        return (root, query, builder) ->
                xrelation == null || xrelation.isEmpty() ? builder.conjunction() : builder.equal(root.get("xrelation"), xrelation);
    }

    private Specification<PdDependent> xstaffEquals(String xstaff) {
        return (root, query, builder) ->
                xstaff == null || xstaff.isEmpty() ? builder.conjunction() : builder.equal(root.get("xstaff"), xstaff);
    }

    public List<PdDependent> searchByText(Integer zid, String searchText) {
        return repository.findBySearchTextAndZid(zid, searchText);
    }

    public Integer getMaxXrowByZidAndXstaff(int zid, String xstaff) {
        // Query the database to find the maximum xrow for the given zid and xstaff
        Integer maxXrow = repository.findMaxXrowByZidAndXstaff(zid, xstaff);
        return (maxXrow == null) ? 0 : maxXrow;
    }


        @Transactional
    public PdDependent createPdDependent(PdDependent pdDependent) {
        // Fetch the current max xrow for the given zid and xstaff
        int maxXrow = repository.findMaxXrowByZidAndXstaff(pdDependent.getZid(), pdDependent.getXstaff());

        // Set the next xrow
        pdDependent.setXrow(maxXrow + 1);

        // Save the new dependent
        return repository.save(pdDependent);
    }


    public List<PdDependent> findByZidAndXstaff(int zid, String xstaff) {
        return repository.findByZidAndXstaff(zid, xstaff);
    }


}

