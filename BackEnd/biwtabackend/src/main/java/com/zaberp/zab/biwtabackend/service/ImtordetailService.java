package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.dto.ImtordetailDto;
import com.zaberp.zab.biwtabackend.id.ImtordetailId;
import com.zaberp.zab.biwtabackend.model.Imtordetail;
import com.zaberp.zab.biwtabackend.repository.ImtordetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ImtordetailService {

    @Autowired
    private ImtordetailRepository repository;

    public Imtordetail saveDetail(Imtordetail detail) {
        return repository.save(detail);
    }


    public Imtordetail save(Imtordetail detail) {

        Integer maxXrow = repository.findMaxXrowByZidAndXtornum(detail.getZid(), detail.getXtornum());
        if (maxXrow == null) {
            maxXrow = 0;
        }
        detail.setXrow(maxXrow + 1);
        detail.setXdphqty(detail.getXprepqty());
        detail.setXqtyreq(detail.getXprepqty());
        detail.setXqtyord(detail.getXprepqty());
        return repository.save(detail);
    }

    public void deleteDetail(ImtordetailId id) {
        repository.deleteById(id);
    }


    public Page<ImtordetailDto> getImtordetail(int zid, String xtornum, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByZid(zid,xtornum, pageable);
    }

    public Imtordetail updateByZidAndXtornumAndXrow(Imtordetail detail) {
        detail.setXdphqty(detail.getXprepqty());
        detail.setXqtyreq(detail.getXprepqty());
        detail.setXqtyord(detail.getXprepqty());
        return repository.save(detail);
    }


}


