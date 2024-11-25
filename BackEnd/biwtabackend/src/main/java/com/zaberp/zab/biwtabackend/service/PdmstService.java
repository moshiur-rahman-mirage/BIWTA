package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.repository.PdmstRepository;
import com.zaberp.zab.biwtabackend.repository.XcodesRepository;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PdmstService {

    private final PdmstRepository repository;

    public PdmstService(PdmstRepository repository) {
        this.repository = repository;
    }

    public Pdmst save(Pdmst pdmst) {
        return repository.save(pdmst);
    }

    public boolean existsByZidAndXstaffAndXposition(Integer zid, String xstaff ,String xposition) {
        return repository.existsByZidAndXstaffAndXposition(zid, xstaff,xposition);
    }

    public boolean existsByZidAndXposition(Integer zid, String xposition) {
        return repository.existsByZidAndXposition(zid,xposition);
    }

    public boolean existsByZidAndXmobile(Integer zid, String xmobile) {
        return repository.existsByZidAndXmobile(zid,xmobile);
    }

    public Optional<Pdmst> findById(PdmstId id) {
        return repository.findById(id);
    }

    public List<Pdmst> findPdmst(Integer zid, String xstaff) {
        Specification<Pdmst> spec = Specification.where(zidEquals(zid))
                .and(xstaffEquals(xstaff));
        return repository.findAll(spec);
    }


    public void deleteById(PdmstId id) {
        repository.deleteById(id);
    }
    public List<Pdmst> findPdmst(Integer zid, String xstaff, String xmobile) {
        Specification<Pdmst> spec = Specification.where(zidEquals(zid));
//                .and(xstaffEquals(xstaff))
//                .and(xmobileEquals(xmobile));
        if (xstaff != null && !xstaff.isEmpty()) {
            spec = spec.and(xstaffEquals(xstaff));
        }
        if (xmobile != null && !xmobile.isEmpty()) {
            spec = spec.and(xmobileEquals(xmobile));
        }
        return repository.findAll(spec);
    }

    public List<Pdmst> findPdmst(Integer zid, String xstaff, String xposition,String xmobile) {
        Specification<Pdmst> spec = Specification.where(zidEquals(zid))
                .and(xstaffEquals(xstaff))
                .and(xpositionEquals(xposition))
                .and(xmobileEquals(xmobile));
        return repository.findAll(spec);
    }

    private Specification<Pdmst> zidEquals(Integer zid) {
        return (root, query, builder) -> builder.equal(root.get("zid"), zid);
    }

    private Specification<Pdmst> xpositionEquals(String xposition) {
        return (root, query, builder) -> builder.equal(root.get("xposition"), xposition);
    }

    private Specification<Pdmst> xstaffEquals(String xstaff) {
        return (root, query, builder) ->
                xstaff == null || xstaff.isEmpty() ? builder.conjunction() : builder.equal(root.get("xstaff"), xstaff);
    }


    public List<Pdmst> searchByText(String zid,String searchText) {
        return repository.findBySearchTextAndZid(zid, searchText);
    }


    private Specification<Pdmst> xmobileEquals(String xmobile) {
        return (root, query, builder) ->
                xmobile == null || xmobile.isEmpty() ? builder.conjunction() : builder.equal(root.get("xmobile"), xmobile);
    }
}

