package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book27Request;
import com.bitsco.vks.report.response.Book27Response;
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
public class Book27RepositoryImpl implements Book27Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book27Response> querying(Book27Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book27RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book27Response> rm = new SingleColumnRowMapper<Book27Response>() {
            @Override
            public Book27Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book27Response response = new Book27Response();
                response.setStt(rs.getRow());
                response.setTenDiaChi(rs.getString("s_column_2"));
                response.setBanAnCoHieuLucPhapLuat(rs.getString("s_column_3"));
                response.setHinhPhat(rs.getString("s_column_4"));
                response.setQuyetDinhThiHanhAn(rs.getString("s_column_5"));
                response.setQuyetDinhCuongCheThiHanhAn(rs.getString("s_column_6"));
                response.setGiayChungNhanDaChapHanhXongHinhPhat(rs.getString("s_column_7"));
                response.setQuyetDinhThiHanh(rs.getString("s_column_8"));
                response.setQuyetDinhCuongCheThiHanh(rs.getString("s_column_9"));
                response.setGiayChungNhanDaChapHanhXongBienPhapTuPhap(rs.getString("s_column_10"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_27)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_legal_code", StringCommon.isNullOrBlank(request.getLegalCode()) ? null : StringCommon.addLikeRightAndLeft(request.getLegalCode().trim()))
                    .addValue("pi_legal_name", StringCommon.isNullOrBlank(request.getLegalName()) ? null : StringCommon.addLikeRightAndLeft(request.getLegalName().trim()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode().trim())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId().trim())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem().trim())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint().trim())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book27Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book27RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book27RepositoryImpl.querying");
        }
    }
}
