package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.PdmstId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.repository.PdmstRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PdmstService {

    private final PdmstRepository repository;

    private final PrimaryKeyService primaryKeyService;

    public PdmstService(PdmstRepository repository, PrimaryKeyService primaryKeyService) {
        this.repository = repository;
        this.primaryKeyService = primaryKeyService;
    }

    public Pdmst createPdmst(Pdmst pdmst) {
        if (existsByZidAndXmobile(pdmst.getZid(), pdmst.getXmobile())) {
            throw new IllegalArgumentException("Validation failed: A Employee with the same mobile number already exists.");
        }
        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(pdmst.getZid(), "Staff ID", "EID-", 5);
        pdmst.setXstaff(generatedKey);
        pdmst.setXposition(generatedKey);
        pdmst.setZtime(LocalDateTime.now());
        pdmst.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());

        return repository.save(pdmst);
    }

    public Pdmst updatePdmst(Integer zid, String xstaff, Pdmst updatedPdmst) {
        if (updatedPdmst.getXname() == null || updatedPdmst.getXname().isBlank()) {
            throw new IllegalArgumentException("Validation failed: Name field is required.");
        }

        PdmstId id = new PdmstId(zid, xstaff);
        Pdmst existingPdmst = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for the given ID."));
        BeanUtils.copyProperties(updatedPdmst, existingPdmst,
                "zid", "xstaff", "zauserid", "ztime");
        existingPdmst.setZuuserid(SecurityContextHolder.getContext().getAuthentication().getName());
        existingPdmst.setZutime(LocalDateTime.now());
        return repository.save(existingPdmst);
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

    public List<Pdmst> findByZidAndZactive(int zid, String zactive) {
        System.out.println("Repository Input - zid: " + zid + ", zactive: " + zactive);
        List<Pdmst> results = repository.findByZidAndZactive(zid, zactive);
        System.out.println("Repository Output: " + results);
        return results;
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


    public Pdmst findByZidAndXposition(int zid,String xposition){

        return repository.findByZidAndXposition(zid,xposition);
    }
}

