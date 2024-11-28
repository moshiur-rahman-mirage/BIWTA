package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.dto.PogrndetailXitemdto;
import com.zaberp.zab.biwtabackend.id.PogrndetailId;
import com.zaberp.zab.biwtabackend.model.Pogrndetail;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.repository.PogrndetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PogrndetailService {

    @Autowired
    private PogrndetailRepository repository;






    public Pogrndetail saveDetail(Pogrndetail detail) {
        return repository.save(detail);
    }


    public Pogrndetail save(Pogrndetail detail) {

        Integer maxXrow = repository.findMaxXrowByZidAndXgrnnumber(detail.getZid(), detail.getXgrnnum());
        if (maxXrow == null) {
            maxXrow = 0;
        }
        detail.setXrow(maxXrow + 1);
        return repository.save(detail);
    }

    public void deleteDetail(PogrndetailId id) {
        repository.deleteById(id);
    }


    public Page<PogrndetailXitemdto> getPogrndetailXitems(int zid,String xgrnnum, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByZid(zid,xgrnnum, pageable);
    }

    public Pogrndetail updateByZidAndXgrnnumAndXrow(Pogrndetail detail) {
        return repository.save(detail);
    }


}

