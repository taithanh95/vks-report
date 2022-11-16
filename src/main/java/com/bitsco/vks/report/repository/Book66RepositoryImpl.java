package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book66Request;
import com.bitsco.vks.report.response.Book66Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
public class Book66RepositoryImpl implements Book66Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book66Response> querying(Book66Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book66RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book66Response> rm = new SingleColumnRowMapper<Book66Response>() {
            @Override
            public Book66Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book66Response response = new Book66Response();
                response.setStt(rs.getRow());
                response.setS_column_2(rs.getString("s_column_2"));
                response.setS_column_3(rs.getString("s_column_3"));
                response.setS_column_4(rs.getString("s_column_4"));
                response.setS_column_5(rs.getString("s_column_5"));
                response.setS_column_6(rs.getString("s_column_6"));
                response.setS_column_7(rs.getString("s_column_7"));
                response.setS_column_8(rs.getString("s_column_8"));
                response.setS_column_9(rs.getString("s_column_9"));
                response.setS_column_10(rs.getString("s_column_10"));
                response.setS_column_11(rs.getString("s_column_11"));
                response.setS_column_12(rs.getString("s_column_12"));
                response.setN_violation_id(rs.getLong("n_violation_id"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_66)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_document_from_date", DateCommon.convertDateToString(request.getDocumentFromDate()))
                    .addValue("pi_document_to_date", DateCommon.convertDateToString(request.getDocumentToDate()))
                    .addValue("pi_result_from_date", DateCommon.convertDateToString(request.getResultFromDate()))
                    .addValue("pi_result_to_date", DateCommon.convertDateToString(request.getResultToDate()))
                    .addValue("pi_document_code", request.getDocumentCode() == null ? -1 : request.getDocumentCode())
                    .addValue("pi_result_code", request.getResultCode() == null ? -1 : request.getResultCode())
                    .addValue("pi_violated_agency", StringCommon.isNullOrBlank(request.getViolatedAgency()) ? null : request.getViolatedAgency())
                    .addValue("pi_violated_units_id", StringCommon.isNullOrBlank(request.getViolatedUnitsId()) ? null : request.getViolatedUnitsId())
                    .addValue("pi_violated_units_name", StringCommon.isNullOrBlank(request.getViolatedUnitsName()) ? null : request.getViolatedUnitsName())
                    .addValue("pi_document_number", StringCommon.isNullOrBlank(request.getDocumentNumber()) ? null : StringCommon.addLikeRightAndLeft(request.getDocumentNumber().trim().toUpperCase()))
                    .addValue("pi_result_number", StringCommon.isNullOrBlank(request.getResultNumber()) ? null : StringCommon.addLikeRightAndLeft(request.getResultNumber().trim().toUpperCase()))
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book66Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book66RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book66RepositoryImpl.querying");
        }
    }
}
