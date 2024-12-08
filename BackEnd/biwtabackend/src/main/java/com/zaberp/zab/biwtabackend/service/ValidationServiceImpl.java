package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.repository.CacusRepository;
import com.zaberp.zab.biwtabackend.repository.CaitemRepository;
import com.zaberp.zab.biwtabackend.repository.PdmstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final CacusRepository cacusRepository;  // Assumed to be injected
    private final CaitemRepository caitem;
    private final PdmstRepository pdmst;
    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public ValidationServiceImpl(CacusRepository customerRepository, CaitemRepository productRepository, PdmstRepository pdmst, JdbcTemplate jdbcTemplate) {
        this.cacusRepository = customerRepository;
        this.caitem = productRepository;
        this.pdmst = pdmst;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isValidParty(int zid, String xcus) {
        return cacusRepository.findByZidAndXcus(zid,xcus);
    }

    @Override
    public boolean isValidProductId(int zid,String xitem) {
        return caitem.findByZidAndXitem(zid,xitem);
    }

    @Override
    public boolean isValidEmp(int zid,String xposition){
        return pdmst.existsByZidAndXposition(zid,xposition);
    }

    @Override
    public boolean isValidNumber(String numberText) {
        try {
            Integer.parseInt(numberText);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isDetailPresent(String tableName, String transactionColumn, String transactionNumber) {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", tableName, transactionColumn);
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, transactionNumber);
        return count != null && count > 0;
    }

}

