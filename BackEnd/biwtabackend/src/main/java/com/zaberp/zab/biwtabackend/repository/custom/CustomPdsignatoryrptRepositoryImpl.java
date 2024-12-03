package com.zaberp.zab.biwtabackend.repository.custom;

        import jakarta.persistence.EntityManager;
        import jakarta.persistence.Query;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Repository;


        import java.time.LocalDate;
        import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;
        import java.util.Map;
        import java.util.List;

@Repository
public class CustomPdsignatoryrptRepositoryImpl implements CustomPdsignatoryrptRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public int updateExcludingColumns(int zid, int xrow, Map<String, Object> updates, List<String> excludeColumns) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE Pdsignatoryrpt p SET ");
        boolean isFirst = true;


        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            if (!excludeColumns.contains(entry.getKey())) {
                if (!isFirst) {
                    queryBuilder.append(", ");
                }
                queryBuilder.append("p.").append(entry.getKey()).append(" = :").append(entry.getKey());
                isFirst = false;
            }
        }

        // Validate if at least one column is being updated
        if (isFirst) {
            throw new IllegalArgumentException("No valid columns to update.");
        }

        queryBuilder.append(" WHERE p.zid = :zid AND p.xrow = :xrow");

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setParameter("zid", zid);
        query.setParameter("xrow", xrow);

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            if (!excludeColumns.contains(entry.getKey())) {
                Object value = entry.getValue();


                    query.setParameter(entry.getKey(), value);

            }
        }

        return query.executeUpdate();
    }
}

