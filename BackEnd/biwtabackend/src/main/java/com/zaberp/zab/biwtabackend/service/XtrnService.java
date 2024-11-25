package com.zaberp.zab.biwtabackend.service;



import com.zaberp.zab.biwtabackend.id.XtrnId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.model.Xtrn;
import com.zaberp.zab.biwtabackend.repository.XtrnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class XtrnService {

    @Autowired
    private XtrnRepository repository;

    public List<Xtrn> getAllRecords() {
        return repository.findAll();
    }

    public Optional<Xtrn> getRecordById(XtrnId id) {
        return repository.findById(id);
    }

    public Xtrn saveRecord(Xtrn xtrn) {
        return repository.save(xtrn);
    }

    public void deleteRecord(XtrnId id) {
        repository.deleteById(id);
    }

    public List<Xtrn> findXtrn(Integer zid, String xtypetrn, String xtrn) {
        Specification<Xtrn> spec = Specification.where(zidEquals(zid))
                .and(XtypetrnEquals(xtypetrn))
                .and(xtrnEquals(xtrn));
        return repository.findAll(spec);
    }
    private Specification<Xtrn> zidEquals(Integer zid) {
        return (root, query, builder) -> builder.equal(root.get("zid"), zid);
    }

    private Specification<Xtrn> XtypetrnEquals(String xtypetrn) {
        return (root, query, builder) -> builder.equal(root.get("xtypetrn"), xtypetrn);
    }

    private Specification<Xtrn> xtrnEquals(String xtrn) {
        return (root, query, builder) ->
                xtrn == null || xtrn.isEmpty() ? builder.conjunction() : builder.equal(root.get("xtrn"), xtrn);
    }

    public List<Xtrn> findActiveXcodesByZidAndXtype(int zid, String xtypetrn) {
        return repository.findActiveXtrnByZidAndTypetrn(zid, xtypetrn);
    }
}

