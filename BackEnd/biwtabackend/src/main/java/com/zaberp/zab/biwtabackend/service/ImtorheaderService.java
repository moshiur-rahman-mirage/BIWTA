package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.repository.ImtorheaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImtorheaderService extends CommonServiceImpl<Imtorheader, ImtorheaderId> {

    private final ImtorheaderRepository repository;
    private final PrimaryKeyService primaryKeyService;


    @Autowired
    public ImtorheaderService(ImtorheaderRepository repository, NamedParameterJdbcTemplate jdbcTemplate, PrimaryKeyService primaryKeyService) {
        super(jdbcTemplate);
        this.repository = repository;
        this.primaryKeyService = primaryKeyService;
    }



    public Imtorheader createTransaction(Imtorheader imtorheader, String action) {
        String transactionPrefix = determineTransactionPrefix(action);
        String tempStatus = determineTempStatus(action);

        String generatedKey = primaryKeyService.getGeneratedPrimaryKey(
                imtorheader.getZid(), "Transfer Transaction", transactionPrefix, 6
        );
        imtorheader.setXtornum(generatedKey);
        imtorheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        imtorheader.setZtime(LocalDateTime.now());
        imtorheader.setXstatustor(tempStatus);
        imtorheader.setXtrn(transactionPrefix);
        return repository.save(imtorheader);
    }



    public Page<ImtorDto> findImtorWithFilters(int zid, String xstatus, String xtrn, String user, int page, int size, String sortBy, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findImtorWithZidAndStatusAndUser(zid, xstatus, xtrn, user, pageable);
    }



    public String confirmImtor(int zid, String user, String position, String xtornum, Date xdate, String xwh, String xtwh, String xstatustor, String screen, int len) {
        try {
            String sql = """
                        EXEC zabsp_confirmTO 
                        @zid = :zid, 
                        @user = :user, 
                        @position = :position, 
                        @tornum = :xtornum, 
                        @date = :xdate, 
                        @fwh = :xwh, 
                        @twh = :xtwh, 
                        @statustor = :xstatustor, 
                        @screen = :screen, 
                        @trnlength = :len
                    """;

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("zid", zid);
            parameters.addValue("user", user);
            parameters.addValue("position", position);
            parameters.addValue("xtornum", xtornum);
            parameters.addValue("xdate", new java.sql.Date(xdate.getTime()));
            parameters.addValue("xwh", xwh);
            parameters.addValue("xtwh", xtwh);
            parameters.addValue("xstatustor", xstatustor);
            parameters.addValue("screen", screen);
            parameters.addValue("len", len);

            // Execute the query
            jdbcTemplate.update(sql, parameters);

            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }

    }
    public String checkSR(int zid, String user, String xtornum, Date xdate, String xfwh, String xstatustor) {
        try {
            String sql = """
            EXEC zabsp_IM_CheckMORequisition 
                @zid = :zid, 
                @user = :user, 
                @tornum = :xtornum, 
                @date = :xdate, 
                @wh = :xfwh, 
                @statustor = :xstatustor 
                """;
            MapSqlParameterSource parmeters = new MapSqlParameterSource();
            parmeters.addValue("zid",zid);
            parmeters.addValue("user",user);
            parmeters.addValue("xtornum",xtornum);
            parmeters.addValue("xdate",new java.sql.Date(xdate.getTime()));
            parmeters.addValue("xfwh",xfwh);
            parmeters.addValue("xstatustor",xstatustor);


            jdbcTemplate.update(sql, parmeters);
            return "Procedure executed successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error executing procedure: " + e.getMessage(), e);
        }
    }


    private String determineTransactionPrefix(String action) {
        return switch (action.toLowerCase()) {
            case "damage" -> "DAM-";
            case "requisition" -> "SR--";
            default -> throw new IllegalArgumentException("Unsupported action: " + action);
        };
    }

    private String determineTempStatus(String action) {
        return "damage".equalsIgnoreCase(action) ? "Open" : "Open";
    }


    @Override
    public JpaRepository<Imtorheader, ImtorheaderId> getRepository() {

        return repository;
    }

    @Override
    public Page<Imtorheader> findByZidAndTrnnumWithPaginationAndSorting(int zid, String trnnum, int page, int size, String sortBy, boolean ascending) {
        return null;
    }

    @Override
    protected NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    protected String getTableName() {
        return "Imtorheader";
    }

    @Override
    protected RowMapper<Imtorheader> getRowMapper() {
        return new BeanPropertyRowMapper<>(Imtorheader.class);
    }

    public Page<ImtorDto> findImtorWithZidAndStatusAndUser(int zid, String xstatus,String xtrn, String user, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findImtorWithZidAndStatusAndUser(zid,xstatus,xtrn,user,pageable);
    }


    public Page<ImtorDto> findImtorWithZidAndStatus(int zid, String xtrn, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findImtorWithZidAndStatus(zid,xtrn,pageable);
    }

    public List<ImtorDto> searchByText(int zid,String action, String searchText) {
        return repository.findImtorWithZidAndSearchText(zid,action,searchText);
    }

}
