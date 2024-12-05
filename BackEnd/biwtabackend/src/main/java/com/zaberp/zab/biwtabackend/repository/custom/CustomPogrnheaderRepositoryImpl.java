package com.zaberp.zab.biwtabackend.repository.custom;

import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomPogrnheaderRepositoryImpl implements CustomPoGrnHeaderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<PogrnheaderXcusdto> getPogrnList(int zid, String user, String superior, String status, Pageable pageable) {
        System.out.println("------------------");
        System.out.println(zid);
        System.out.println(user);
        System.out.println(superior);
        System.out.println(status);
        System.out.println("------------------");
        StringBuilder sql = new StringBuilder("SELECT new com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto(" +
                "p.zid, p.xgrnnum, p.xdate, s.xcus, s.xorg, p.xwh, x.xlong, p.xstatus, p.xstatusdoc, p.zauserid, pd.xname) " +
                "FROM Pogrnheader p " +
                "JOIN Cacus s ON p.zid = s.zid AND p.xcus = s.xcus " +
                "JOIN Pdmst pd ON p.zid = pd.zid AND p.xpreparer = pd.xstaff " +
                "JOIN Xcodes x ON p.zid = x.zid AND p.xwh = x.xcode AND x.xtype = 'Branch' " +
                "WHERE p.zid = :zid");

        if (user != null && user!="") {
            sql.append(" AND p.zauserid = :user");
        }
        if (superior != null && superior!="") {
            sql.append(" AND p.xsuperiorsp = :superior");
        }
        if (status != null && status!="") {
            sql.append(" AND p.xstatusdoc = :status");
        }
        System.out.println(sql.toString());
        TypedQuery<PogrnheaderXcusdto> query = entityManager.createQuery(sql.toString(), PogrnheaderXcusdto.class);
        query.setParameter("zid", zid);
        if (user != null && user!="") {
            query.setParameter("user", user);
        }
        if (superior != null && superior!="") {
            query.setParameter("superior", superior);
        }
        if (status != null && status!="") {
            query.setParameter("status", status);
        }

        System.out.println(query.toString());
        // Implement pagination manually
        int totalRecords = query.getResultList().size();
        int firstResult = (int) pageable.getOffset();
        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());

        List<PogrnheaderXcusdto> results = query.getResultList();

        return new PageImpl<>(results, pageable, totalRecords);
    }
}
