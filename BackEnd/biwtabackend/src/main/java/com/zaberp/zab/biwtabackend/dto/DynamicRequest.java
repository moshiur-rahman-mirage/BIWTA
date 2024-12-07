package com.zaberp.zab.biwtabackend.dto;

import java.util.List;
import java.util.Map;

public class DynamicRequest {
    private List<String> selectedFields;
    private Map<String, Object> whereConditions;

    public List<String> getSelectedFields() {
        return selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    public Map<String, Object> getWhereConditions() {
        return whereConditions;
    }

    public void setWhereConditions(Map<String, Object> whereConditions) {
        this.whereConditions = whereConditions;
    }
}
