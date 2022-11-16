package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book20Request;
import com.bitsco.vks.report.response.Book20Response;
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
 * Time: 11:52 AM
 */

@Repository
public class Book20RepositoryImpl implements Book20Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book20Response> querying(Book20Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book20RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book20Response> rm = new SingleColumnRowMapper<Book20Response>() {
            @Override
            public Book20Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book20Response response = new Book20Response();
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
                response.setS_column_11(rs.getString("s_column_11"));
                response.setS_column_12(rs.getString("s_column_12"));
                response.setS_column_13(rs.getString("s_column_13"));
                response.setS_column_14(rs.getString("s_column_14"));
                response.setS_column_15(rs.getString("s_column_15"));
                response.setS_column_16(rs.getString("s_column_16"));
                response.setS_ghi_chu(rs.getString("s_ghi_chu"));
                response.setN_compensation_id(rs.getLong("n_compensation_id"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_20)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_sppid", StringCommon.isNullOrBlank(request.getSppId()) ? null : request.getSppId().trim())
                    .addValue("pi_claimant_name", StringCommon.isNullOrBlank(request.getClaimantName()) ? null : StringCommon.addLikeRightAndLeft(request.getClaimantName()))
                    .addValue("pi_damages_name", StringCommon.isNullOrBlank(request.getDamagesName()) ? null : StringCommon.addLikeRightAndLeft(request.getDamagesName()))
                    .addValue("pi_decision_compensation_number", request.getDecisionCompensationNumber() == null ? -1 : request.getDecisionCompensationNumber())
                    .addValue("pi_from_decision_compensation_date", DateCommon.convertDateToString(request.getFromDecisionCompensationDate()))
                    .addValue("pi_to_decision_compensation_date", DateCommon.convertDateToString(request.getToDecisionCompensationDate()))
                    .addValue("pi_decision_enforcement_number", request.getDecisionEnforcementNumber() == null ? -1 : request.getDecisionEnforcementNumber())
                    .addValue("pi_from_decision_enforcement_date", DateCommon.convertDateToString(request.getFromDecisionEnforcementDate()))
                    .addValue("pi_to_decision_enforcement_date", DateCommon.convertDateToString(request.getToDecisionEnforcementDate()))
                    .addValue("pi_judgment_compensation_number", request.getJudgmentCompensationNumber() == null ? -1 : request.getJudgmentCompensationNumber());
            return jdbcCall.executeFunction((Class<List<Book20Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book20RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book20RepositoryImpl.querying");
        }
    }
}
