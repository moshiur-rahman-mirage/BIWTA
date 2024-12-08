package com.zaberp.zab.biwtabackend.service;

public interface ValidationService {
    boolean isValidParty(int zid, String xcus);
    boolean isValidProductId(int zid,String xitem);
    boolean isValidEmp(int zid,String xposition);
    boolean isValidNumber(String numberText);
    public boolean isDetailPresent(String tableName, String transactionColumn, String transactionNumber);
}
