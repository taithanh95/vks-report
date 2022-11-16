package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book08Request;
import com.bitsco.vks.report.response.Book08Response;
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
public class Book08RepositoryImpl implements Book08Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book08Response> querying(Book08Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book08RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book08Response> rm = new SingleColumnRowMapper<Book08Response>() {
            @Override
            public Book08Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book08Response response = new Book08Response();
                response.setStt(rs.getRow());
                response.setSttDetail((rs.getString("s_stt") == null ? "" : rs.getString("s_stt")));
                response.setVuAnBiCan(rs.getString("s_column_2"));
                response.setToiDanh(rs.getString("s_column_3"));
                response.setQuyetDinhKhoiToVuAn(rs.getString("s_column_4"));
                response.setQuyetDinhKhoiToBiCan(rs.getString("s_column_5"));
                response.setKiemSatVienDTVThamPhanThuLy(rs.getString("s_column_6"));
                response.setQuyetDinhTamDinhChi(rs.getString("s_column_7"));
                response.setLyDoTamDinhChi(rs.getString("s_column_8"));
                response.setQuyetDinhTruyNa(rs.getString("s_column_9"));
                response.setTacDongCuaVKS(rs.getString("s_column_10"));
                response.setQuyetDinhHuyBoQDTamDinhChi(rs.getString("s_column_11"));
                response.setQuyetDinhPhucHoi(rs.getString("s_column_12"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_08)
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
            return jdbcCall.executeFunction((Class<List<Book08Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book08RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book08RepositoryImpl.querying");
        }
    }

    public List<Book08Response> queryingOneYear(Book08Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        SimpleJdbcCall jdbcCall;
        RowMapper<Book08Response> rm = new SingleColumnRowMapper<Book08Response>() {
            @Override
            public Book08Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book08Response response = new Book08Response();
                response.setStt(rs.getRow());
                response.setVuAnBiCan(rs.getString("s_column_002"));
                response.setToiDanh(rs.getString("s_column_003"));
                response.setQuyetDinhKhoiToVuAn(rs.getString("s_column_004"));
                response.setQuyetDinhKhoiToBiCan(rs.getString("s_column_005"));
                response.setKiemSatVienDTVThamPhanThuLy(rs.getString("s_column_006"));
                response.setQuyetDinhTamDinhChi(rs.getString("s_column_007"));
                response.setLyDoTamDinhChi(rs.getString("s_column_008"));
                response.setQuyetDinhTruyNa(rs.getString("s_column_009"));
                response.setTacDongCuaVKS(rs.getString("s_column_010"));
                response.setQuyetDinhHuyBoQDTamDinhChi(rs.getString("s_column_011"));
                response.setQuyetDinhPhucHoi(rs.getString("s_column_012"));
                response.setGhiChu(rs.getString("s_column_013"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_08)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_ONE_YEAR)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", request.getYear() == null ? null : DateCommon.convertDateToString(DateCommon.getBeginYear(DateCommon.convertStringToDate(request.getYear()))))
                    .addValue("pi_to_date", request.getYear() == null ? null : DateCommon.convertDateToString(DateCommon.getEndYear(DateCommon.convertStringToDate(request.getYear()))))
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book08Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book08RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book08RepositoryImpl.querying");
        }
    }

    @Override
    public Book08Request save(Book08Request note) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_08)
                    .withFunctionName(ReportConstant.FUNCTION.FN_INSERT_UPDATE_NOTE);

            SqlParameterSource param = new MapSqlParameterSource()

                    .addValue("p_casecode", note.getCaseCode())
                    .addValue("p_note", note.getNote());
            Map _result = jdbcCall.execute(param);
            return (Book08Request) _result.get("return");
        } catch (Exception e) {
            throw (e);
        }
    }

}
