package com.zaberp.zab.biwtabackend.xcodes;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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


    private Specification<Xcodes> xcodeEquals(String xcode) {
        return (root, query, builder) ->
                xcode == null || xcode.isEmpty() ? builder.conjunction() : builder.equal(root.get("xcode"), xcode);
    }
}

