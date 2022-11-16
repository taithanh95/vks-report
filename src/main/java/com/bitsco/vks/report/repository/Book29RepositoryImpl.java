package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book29Request;
import com.bitsco.vks.report.response.Book29Response;
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
public class Book29RepositoryImpl implements Book29Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book29Response> querying(Book29Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book29RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book29Response> rm = new SingleColumnRowMapper<Book29Response>() {
            @Override
            public Book29Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book29Response response = new Book29Response();
                response.setStt(rs.getRow());
                response.setHoTen(rs.getString("s_column_2"));
                response.setToiDanh(rs.getString("s_column_3"));
                response.setMucAn(rs.getString("s_column_4"));
                response.setQuyetDinhThiHanhAn(rs.getString("s_column_5"));
                response.setQuyetDinhHoanThiHanhAn(rs.getString("s_column_6"));
                response.setQuyetDinhTamDinhChiThiHanhAn(rs.getString("s_column_7"));
                response.setQuyetDinhHuyQDTamDinhChi(rs.getString("s_column_8"));
                response.setQuyetDinhDinhChiThiHanhAn(rs.getString("s_column_9"));
                response.setQuyetDinhCuaToaAn(rs.getString("s_column_10"));
                response.setChet(rs.getString("s_column_11"));
                response.setTron(rs.getString("s_column_12"));
                response.setNgayDiThiHanhAn(rs.getString("s_column_13"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_29)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_accucode", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode().trim()))
                    .addValue("pi_accuname", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName().trim()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode().trim())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId().trim())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem().trim())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint().trim())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_sppid", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book29Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book29RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book29RepositoryImpl.querying");
        }
    }
}
