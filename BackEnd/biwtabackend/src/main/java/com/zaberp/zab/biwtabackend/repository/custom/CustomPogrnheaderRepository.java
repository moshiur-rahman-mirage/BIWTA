package com.zaberp.zab.biwtabackend.repository.custom;

import java.util.List;
import java.util.Map;



public interface CustomPogrnheaderRepository {
    int updateExcludingColumns(int zid, String xgrnnum, Map<String, Object> updates, List<String> excludeColumns);
}
