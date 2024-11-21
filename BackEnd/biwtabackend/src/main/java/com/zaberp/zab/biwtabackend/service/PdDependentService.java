package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.PdDependentId;
import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.repository.PdDependentRepository;
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

    public boolean existsByZidAndXstaffAndXrow(int zid, String xstaff, int xrow) {
        return repository.existsByZidAndXstaffAndXrow(zid, xstaff, xrow);
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

    public List<PdDependent> searchByText(String zid,String searchText) {
        return repository.findBySearchTextAndZid(zid, searchText);
    }
}

