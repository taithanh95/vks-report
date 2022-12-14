package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book32Request;
import com.bitsco.vks.report.response.Book32Response;
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
public class Book32RepositoryImpl implements Book32Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book32Response> querying(Book32Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book32RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book32Response> rm = new SingleColumnRowMapper<Book32Response>() {
            @Override
            public Book32Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book32Response response = new Book32Response();
                response.setStt(rs.getRow());
                response.setS_column_1(rs.getString("s_column_1"));
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
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_32)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_accu_code", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode()))
                    .addValue("pi_accu_name", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId())
                    .addValue("pi_damage_id", StringCommon.isNullOrBlank(request.getDamageId()) ? null : StringCommon.addLikeRightAndLeft(request.getDamageId()))
                    .addValue("pi_damage_name", StringCommon.isNullOrBlank(request.getDamageName()) ? null : StringCommon.addLikeRightAndLeft(request.getDamageName()));
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book32Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy v???n d??? li???u Book32RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book32RepositoryImpl.querying");
        }
    }
}
