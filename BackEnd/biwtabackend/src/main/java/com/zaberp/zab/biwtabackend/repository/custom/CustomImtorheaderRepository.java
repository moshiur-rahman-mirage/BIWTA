package com.zaberp.zab.biwtabackend.repository.custom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CustomImtorheaderRepository {
    int updateExcludingColumns(int zid, String xtornum, Map<String, Object> updates, List<String> excludeColumns);
}