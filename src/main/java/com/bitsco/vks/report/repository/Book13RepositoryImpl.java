package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book13Request;
import com.bitsco.vks.report.response.Book13Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Book13RepositoryImpl implements Book13Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book13Response> querying(Book13Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book13RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book13Response> rm = new SingleColumnRowMapper<Book13Response>() {
            @Override
            public Book13Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book13Response response = new Book13Response();
                response.setStt(rs.getRow());
                response.setNgayThangNamGiaoNhan(rs.getRow() + " - " + rs.getString("s_column_001"));
                response.setVuAnBiCan(rs.getString("s_column_002"));
                response.setVatChung(rs.getString("s_column_003"));
                response.setLyDoChuyen(rs.getString("s_column_004"));
                response.setBenGiao(rs.getString("s_column_005"));
                response.setBenNhan(rs.getString("s_column_006"));
                response.setGhiChu(rs.getString("s_column_007"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_regicode(rs.getString("s_regicode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_13)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_case_code", StringCommon.isNullOrBlank(request.getCaseCode()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseCode()))
                    .addValue("pi_case_name", StringCommon.isNullOrBlank(request.getCaseName()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseName()))
                    .addValue("pi_accu_code", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode()))
                    .addValue("pi_accu_name", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName()))
                    .addValue("pi_organ_id_delivery", StringCommon.isNullOrBlank(request.getOrganIdDelivery()) ? null : request.getOrganIdDelivery())
                    .addValue("pi_unit_id_delivery", StringCommon.isNullOrBlank(request.getUnitIdDelivery()) ? null : request.getUnitIdDelivery())
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book13Response>>) (Class) List.class, paramMap);
        } catch (
                Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy v???n d??? li???u Book12RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book12RepositoryImpl.querying");
        }
    }
}
