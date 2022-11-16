package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book12Request;
import com.bitsco.vks.report.response.Book12Response;
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
public class Book12RepositoryImpl implements Book12Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book12Response> querying(Book12Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book12RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book12Response> rm = new SingleColumnRowMapper<Book12Response>() {
            @Override
            public Book12Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book12Response response = new Book12Response();
                response.setStt(rs.getRow());
                response.setSttDetail(rs.getRow() + (rs.getString("s_stt") == null ? "" : " - " + rs.getString("s_stt")));
                response.setVuAnBiCan(rs.getString("s_case_accused"));
                response.setToiDanh(rs.getString("s_law"));
                response.setQuyetDinhPhanCongKSV(rs.getString("s_column_004"));
                response.setQuyetDinhTachNhapVuAn(rs.getString("s_column_005"));
                response.setQuyetDinhChuyenThuLyVuAn(rs.getString("s_column_006"));
                response.setQuyetDinhGiaHanThoiGianTruyTo(rs.getString("s_column_007"));
                response.setQuyetDinhTraHoSoVuAn(rs.getString("s_column_008"));
                response.setQuyetDinhTamDinhChiDieuTraVuAn(rs.getString("s_column_009"));
                response.setQuyetDinhDinhChiDieuTraVuAn(rs.getString("s_column_010"));
                response.setQuyetDinhHuyBoDinhChiDieuTraVuAn(rs.getString("s_column_011"));
                response.setQuyetDinhPhucHoiDieuTraVuAn(rs.getString("s_column_012"));
                response.setQuyetDinhXuLyVatChungTaiSanTKBiPhongToa(rs.getString("s_column_013"));
                response.setQuyetDinhChuyenVatChung(rs.getString("s_column_014"));
                response.setCaoTrangQDTruyTo(rs.getString("s_column_015"));
                response.setQuyetDinhRutQDTruyTo(rs.getString("s_column_016"));
                response.setQuyetDinhHuyQDRutQDTruyTo(rs.getString("s_column_017"));
                response.setVanBanDeNghiTraHoSo(rs.getString("s_column_018"));
                response.setQuyetDinhPhanCongKSVTaiPhienToaSoTham(rs.getString("s_column_019"));
                response.setQuyetDinhPhanCongVKSCapDuoiTHQCT(rs.getString("s_column_020"));
                response.setQuyetDinhBietPhaiKSVTHQCT(rs.getString("s_column_021"));
                response.setVanBanTBVuAnCoBiCanBiTamGiam(rs.getString("s_column_022"));
                response.setVanBanTBTruyToBiCanTruocToa(rs.getString("s_column_023"));
                response.setGhiChu(rs.getString("s_column_024"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_12)
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
            return jdbcCall.executeFunction((Class<List<Book12Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book12RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book12RepositoryImpl.querying");
        }
    }
}
