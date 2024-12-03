package com.zaberp.zab.biwtabackend.repository.custom;

import java.util.List;
import java.util.Map;

public interface CustomPdsignatoryrptRepository {
    int updateExcludingColumns(int zid, int xrow, Map<String, Object> updates, List<String> excludeColumns);
}
