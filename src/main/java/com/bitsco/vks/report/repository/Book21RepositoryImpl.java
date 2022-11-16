package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book21Request;
import com.bitsco.vks.report.response.Book21Response;
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
public class Book21RepositoryImpl implements Book21Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book21Response> querying(Book21Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book21RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book21Response> rm = new SingleColumnRowMapper<Book21Response>() {
            @Override
            public Book21Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book21Response response = new Book21Response();
                response.setStt(rs.getRow());
                response.setHoTen(rs.getString("s_column_002"));
                response.setNgayBatTamGiu(rs.getString("s_column_003"));
                response.setCacTruongHopBat(rs.getString("s_column_004"));
                response.setBatKhongCoCanCu(rs.getString("s_column_005"));
                response.setQuyetDinhTamGiu(rs.getString("s_column_006"));
                response.setLyDoTamGiu(rs.getString("s_column_007"));
                response.setQuyetDinhGiaHanTamGiuLan1(rs.getString("s_column_008"));
                response.setQuyetDinhGiaHanTamGiuLan2(rs.getString("s_column_009"));
                response.setQuyetDinhPheChuanQDGiaHanTamGiu(rs.getString("s_column_010"));
                response.setQuyetDinhHuyBoBienPhapTamGiu(rs.getString("s_column_011"));
                response.setChuyenDiNoiKhac(rs.getString("s_column_012"));
                response.setNoiKhacChuyenDen(rs.getString("s_column_013"));
                response.setQuyetDinhADBPNCKhac(rs.getString("s_column_014"));
                response.setLenhTamGiam(rs.getString("s_column_015"));
                response.setQuyetDinhTraTuDo(rs.getString("s_column_016"));
                response.setQuyetDinhTraTuDoCuaVKS(rs.getString("s_column_017"));
                response.setTron(rs.getString("s_column_018"));
                response.setQuyetDinhTraTruyNa(rs.getString("s_column_019"));
                response.setNgayBatLai(rs.getString("s_column_020"));
                response.setQuyetDinhXuLyKhiBatLai(rs.getString("s_column_021"));
                response.setNgayViPham(rs.getString("s_column_022"));
                response.setChet(rs.getString("s_column_024"));
                response.setGhiChu(rs.getString("s_column_025"));
                response.setN_arrestee_id(rs.getLong("n_arrestee_id"));
                if (rs.getString("s_column_026") != null && rs.getString("s_column_023") != null) {
                    response.setQuyetDinhXuLyViPham(rs.getString("s_column_023") + " - " + rs.getString("s_column_026"));
                } else if (rs.getString("s_column_023") != null) {
                    response.setQuyetDinhXuLyViPham(rs.getString("s_column_023"));
                } else if (rs.getString("s_column_026") != null) {
                    response.setQuyetDinhXuLyViPham(rs.getString("s_column_026"));
                }
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_21)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_full_name", StringCommon.isNullOrBlank(request.getArrestName()) ? null : StringCommon.addLikeRightAndLeft(request.getArrestName().trim()))
                    .addValue("pi_arrest_type", StringCommon.isNullOrBlank(request.getArrestType()) ? null : request.getArrestType().trim())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId())
                    .addValue("pi_from_date_decision", DateCommon.convertDateToString(request.getFromDateDecision()))
                    .addValue("pi_to_date_decision", DateCommon.convertDateToString(request.getToDateDecision()));
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book21Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book21RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book21RepositoryImpl.querying");
        }
    }
}
