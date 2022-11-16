package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book09Request;
import com.bitsco.vks.report.response.Book09Response;
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
import java.util.Map;

@Repository
public class Book09RepositoryImpl implements Book09Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book09Response> querying(Book09Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book09RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book09Response> rm = new SingleColumnRowMapper<Book09Response>() {
            @Override
            public Book09Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book09Response response = new Book09Response();
                response.setStt(rs.getRow());
                response.setSttDetail((rs.getString("s_stt") == null ? "" : rs.getString("s_stt")));
                response.setVuAnBiCan(rs.getString("s_column_2"));
                response.setQuyetDinhKhoiToVuAn(rs.getString("s_column_3"));
                response.setQuyetDinhKhoiToBiCan(rs.getString("s_column_4"));
                response.setToiDanh(rs.getString("s_column_5"));
                response.setKiemSatVienDTVThamPhanThuLy(rs.getString("s_column_6"));
                response.setQuyetDinhDinhChi(rs.getString("s_column_7"));
                response.setLyDoDinhChi(rs.getString("s_column_8"));
                response.setQuyetDinhXuLyVatChung(rs.getString("s_column_9"));
                response.setTacDongCuaVKS(rs.getString("s_column_10"));
                response.setKetLuanCuaVKS(rs.getString("s_column_11"));
                response.setQuyetDinhHuyBoQDDinhChi(rs.getString("s_column_12"));
                response.setQuyetDinhPhucHoi(rs.getString("s_column_13"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_09)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_case_code", StringCommon.isNullOrBlank(request.getCaseCode()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseCode()))
                    .addValue("pi_case_name", StringCommon.isNullOrBlank(request.getCaseName()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseName()))
                    .addValue("pi_accu_code", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode()))
                    .addValue("pi_accu_name", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book09Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book09RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book09RepositoryImpl.querying");
        }
    }

    @Override
    public Book09Request save(Book09Request note) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_09)
                    .withFunctionName(ReportConstant.FUNCTION.FN_INSERT_UPDATE_NOTE);

            SqlParameterSource param = new MapSqlParameterSource()

                    .addValue("p_casecode", note.getCaseCode())
                    .addValue("p_note", note.getNote());
            Map _result = jdbcCall.execute(param);
            return (Book09Request) _result.get("return");
        } catch (Exception e) {
            throw (e);
        }
    }
}
