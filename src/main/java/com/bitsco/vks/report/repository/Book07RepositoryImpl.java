package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book07Request;
import com.bitsco.vks.report.response.Book07Response;
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
public class Book07RepositoryImpl implements Book07Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book07Response> querying(Book07Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book07RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book07Response> rm = new SingleColumnRowMapper<Book07Response>() {
            @Override
            public Book07Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book07Response response = new Book07Response();
                response.setStt(rs.getRow());
                response.setVuAnBiCan(rs.getString("s_column_002"));
                response.setToiDanh(rs.getString("s_column_003"));
                response.setQuyetDinhKhoiToVuAnBiCan(rs.getString("s_column_004"));
                response.setTomTatSuKienPhamToi(rs.getString("s_column_005"));
                response.setHoTenDTVThuLy(rs.getString("s_column_006"));
                response.setHoTenKSVThuLy(rs.getString("s_column_007"));
                response.setHoTenNguoiThamGiaBaoChua(rs.getString("s_column_008"));
                response.setYeuCauDieuTra(rs.getString("s_column_009"));
                response.setBienPhapNganChanApDung(rs.getString("s_column_010"));
                response.setQuyetDinhTachNhapVuAn(rs.getString("s_column_011"));
                response.setQuyetDinhChuyenVuAn(rs.getString("s_column_012"));
                response.setQuyetDinhGiaHanThoiHanDieuTra(rs.getString("s_column_013"));
                response.setQuyetDinhTamDinhChiDieuTra(rs.getString("s_column_014"));
                response.setKetLuanDieuTra(rs.getString("s_column_015"));
                response.setGhiChu(rs.getString("s_column_016"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_07)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_case_code", StringCommon.isNullOrBlank(request.getCaseCode()) ? null : request.getCaseCode().trim())
                    .addValue("pi_case_name", StringCommon.isNullOrBlank(request.getCaseName()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseName().trim()))
                    .addValue("pi_accu_code", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : request.getAccuCode().trim())
                    .addValue("pi_accu_name", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName().trim()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId())
                    .addValue("pi_underlevel", (request.getUnderlevel() != null && request.getUnderlevel() == true) ? "Y" : "N");
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book07Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book07RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book07RepositoryImpl.querying");
        }
    }

    @Override
    public List<Book07Response> queryingOneYear(Book07Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        SimpleJdbcCall jdbcCall;
        RowMapper<Book07Response> rm = new SingleColumnRowMapper<Book07Response>() {
            @Override
            public Book07Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book07Response response = new Book07Response();
                response.setStt(rs.getRow());
                response.setVuAnBiCan(rs.getString("s_column_002"));
                response.setToiDanh(rs.getString("s_column_003"));
                response.setQuyetDinhKhoiToVuAnBiCan(rs.getString("s_column_004"));
                response.setTomTatSuKienPhamToi(rs.getString("s_column_005"));
                response.setHoTenDTVThuLy(rs.getString("s_column_006"));
                response.setHoTenKSVThuLy(rs.getString("s_column_007"));
                response.setHoTenNguoiThamGiaBaoChua(rs.getString("s_column_008"));
                response.setYeuCauDieuTra(rs.getString("s_column_009"));
                response.setBienPhapNganChanApDung(rs.getString("s_column_010"));
                response.setQuyetDinhTachNhapVuAn(rs.getString("s_column_011"));
                response.setQuyetDinhChuyenVuAn(rs.getString("s_column_012"));
                response.setQuyetDinhGiaHanThoiHanDieuTra(rs.getString("s_column_013"));
                response.setQuyetDinhTamDinhChiDieuTra(rs.getString("s_column_014"));
                response.setKetLuanDieuTra(rs.getString("s_column_015"));
                response.setGhiChu(rs.getString("s_column_016"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_07)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_ONE_YEAR)
                    .returningResultSet("return", rm);
//            LocalDate date = LocalDate.of(2014,1,1);
//            LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfYear());
//            LocalDate EndDay = date.with(TemporalAdjusters.lastDayOfYear());
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", request.getYear() == null ? null : DateCommon.convertDateToString(DateCommon.getBeginYear(DateCommon.convertStringToDate(request.getYear()))))
                    .addValue("pi_to_date", request.getYear() == null ? null : DateCommon.convertDateToString(DateCommon.getEndYear(DateCommon.convertStringToDate(request.getYear()))))
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book07Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book07RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book07RepositoryImpl.querying");
        }
    }

    @Override
    public Book07Request save(Book07Request note) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_07)
                    .withFunctionName(ReportConstant.FUNCTION.FN_INSERT_UPDATE_NOTE);

            SqlParameterSource param = new MapSqlParameterSource()

                    .addValue("p_casecode", note.getCaseCode())
                    .addValue("p_note", note.getNote());
            Map _result = jdbcCall.execute(param);
            return (Book07Request) _result.get("return");
        } catch (Exception e) {
            throw (e);
        }
    }
}
