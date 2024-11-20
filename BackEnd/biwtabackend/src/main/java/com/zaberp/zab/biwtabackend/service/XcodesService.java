package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.repository.XcodesRepository;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class XcodesService {

    private final XcodesRepository repository;

    public XcodesService(XcodesRepository repository) {
        this.repository = repository;
    }

    public Xcodes save(Xcodes xcodes) {
        return repository.save(xcodes);
    }

    public boolean existsByZidAndXtypeAndXcode(Integer zid, String xtype,String xcode) {
        return repository.existsByZidAndXtypeAndXcode(zid, xtype,xcode);
    }


    public Optional<Xcodes> findById(XcodesId id) {
        return repository.findById(id);
    }

    public void deleteById(XcodesId id) {
        repository.deleteById(id);
    }
    public List<Xcodes> findXcodes(Integer zid, String xtype, String xcode) {
        Specification<Xcodes> spec = Specification.where(zidEquals(zid))
                .and(xtypeEquals(xtype))
                .and(xcodeEquals(xcode));
        return repository.findAll(spec);
    }

    private Specification<Xcodes> zidEquals(Integer zid) {
        return (root, query, builder) -> builder.equal(root.get("zid"), zid);
    }

    private Specification<Xcodes> xtypeEquals(String xtype) {
        return (root, query, builder) ->
                xtype == null || xtype.isEmpty() ? builder.conjunction() : builder.equal(root.get("xtype"), xtype);
    }


    public List<Xcodes> searchByText(String zid,String xtype,String searchText) {
//        return repository.findBySearchText(searchText);

        return repository.findBySearchTextAndZid(zid, xtype, searchText);
    }


    private Specification<Xcodes> xcodeEquals(String xcode) {
        return (root, query, builder) ->
                xcode == null || xcode.isEmpty() ? builder.conjunction() : builder.equal(root.get("xcode"), xcode);
    }
}

