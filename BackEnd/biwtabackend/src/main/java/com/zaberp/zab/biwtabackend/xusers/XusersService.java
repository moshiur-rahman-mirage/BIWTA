package com.zaberp.zab.biwtabackend.xusers;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class XusersService {

    private final XusersRepository repository;

    public XusersService(XusersRepository repository) {
        this.repository = repository;
    }

    public boolean existsByZidAndZemailAndXposition(Integer zid, String zemail, String xposition) {
        return repository.existsByZidAndZemailAndXposition(zid, zemail, xposition);
    }

    public boolean existsByZidAndZemail(Integer zid, String zemail) {
        return repository.existsByZidAndZemail(zid, zemail);
    }

    public Xusers save(Xusers xusers) {
        return repository.save(xusers);
    }

    public Optional<Xusers> findById(XusersId id) {
        return repository.findById(id);
    }

    public void deleteById(XusersId id) {
        repository.deleteById(id);
    }
    public List<Xusers> findXusers(Integer zid, String zemail) {
        Specification<Xusers> spec = Specification.where(zidEquals(zid))
                .and(zemailEquals(zemail));
        return repository.findAll(spec);
    }


    private Specification<Xusers> zidEquals(Integer zid) {
        return (root, query, builder) -> builder.equal(root.get("zid"), zid);
    }

    private Specification<Xusers> zemailEquals(String zemail) {
        return (root, query, builder) ->
                zemail == null || zemail.isEmpty() ? builder.conjunction() : builder.equal(root.get("zemail"), zemail);
    }

}

