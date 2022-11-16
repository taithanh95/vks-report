package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book19Request;
import com.bitsco.vks.report.response.Book19Response;
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

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Ba Tuan Anh <anhnbt.it@gmail.com>
 * Date: 8/9/2021
 * Time: 11:48 AM
 */

@Repository
public class Book19RepositoryImpl implements Book19Repository {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book19Response> querying(Book19Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book19RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book19Response> rm = new SingleColumnRowMapper<Book19Response>() {
            @Override
            public Book19Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book19Response response = new Book19Response();
                response.setStt(rs.getRow());
                response.setSttDetail(rs.getString("s_stt") == null ? String.valueOf(rs.getRow()) : rs.getRow() + " - " + rs.getString("s_stt"));
                response.setS_column_2(rs.getString("s_column_2"));
                response.setS_column_3(rs.getString("s_column_3"));
                response.setS_column_4(rs.getString("s_column_4"));
                response.setS_column_5(rs.getString("s_column_5"));
                response.setS_column_6(rs.getString("s_column_6"));
                response.setS_column_7(rs.getString("s_column_7"));
                response.setS_column_8(rs.getString("s_column_8"));
                response.setS_column_9(rs.getString("s_column_9"));
                response.setS_column_10(rs.getString("s_column_10"));
                response.setS_ghi_chu(rs.getString("s_ghi_chu"));
                response.setN_compensation_id(rs.getLong("n_compensation_id"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_19)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_sppid", StringCommon.isNullOrBlank(request.getSppId()) ? null : request.getSppId().trim())
                    .addValue("pi_result_code", request.getResultCode() == null ? -1 : request.getResultCode())
                    .addValue("pi_claimant_name", StringCommon.isNullOrBlank(request.getClaimantName()) ? null : StringCommon.addLikeRightAndLeft(request.getClaimantName()))
                    .addValue("pi_result_handler", StringCommon.isNullOrBlank(request.getResultHandler()) ? null : StringCommon.addLikeRightAndLeft(request.getResultHandler()))
                    .addValue("pi_damages_name", StringCommon.isNullOrBlank(request.getDamagesName()) ? null : StringCommon.addLikeRightAndLeft(request.getDamagesName()))
                    .addValue("pi_claimant_address", StringCommon.isNullOrBlank(request.getClaimantAddress()) ? null : StringCommon.addLikeRightAndLeft(request.getClaimantAddress()))
                    .addValue("pi_damages_address", StringCommon.isNullOrBlank(request.getDamagesAddress()) ? null : StringCommon.addLikeRightAndLeft(request.getDamagesAddress()))
                    .addValue("pi_result_number", request.getResultNumber() == null ? -1 : request.getResultNumber())
                    .addValue("pi_from_result_date", DateCommon.convertDateToString(request.getFromResultDate()))
                    .addValue("pi_to_result_date", DateCommon.convertDateToString(request.getToResultDate()))
                    .addValue("pi_from_document_date", DateCommon.convertDateToString(request.getFromDocumentDate()))
                    .addValue("pi_to_document_date", DateCommon.convertDateToString(request.getToDocumentDate()));
            return jdbcCall.executeFunction((Class<List<Book19Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book19RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book19RepositoryImpl.querying");
        }
    }
}
