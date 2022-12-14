package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book02Request;
import com.bitsco.vks.report.response.Book02Response;
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
public class Book02RepositoryImpl implements Book02Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book02Response> querying(Book02Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        int rowNumber = 1;
        LOGGER.info("[B][" + startTime + "] Book01RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book02Response> rm = new SingleColumnRowMapper<Book02Response>() {
            @Override
            public Book02Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book02Response response = new Book02Response();
                response.setVuAnBiCan(rs.getString("s_column_002"));
                response.setToiDanh(rs.getString("s_column_003"));
                response.setQuyetDinhChuyenVuAn(rs.getString("s_column_004"));
                response.setPheChuanQDKhoiToBiCan(rs.getString("s_column_005"));
                response.setPheChuanQDBoSungKhoiToBiCan(rs.getString("s_column_006"));
                response.setQuyetDinhHuyBoKhoiToBiCan(rs.getString("s_column_007"));
                response.setYeuCauRaQuyetDinh(rs.getString("s_column_008"));
                response.setQuyetDinhHuyBoQDKhongKhoiToVuAn(rs.getString("s_column_009"));
                response.setQuyetDinhKhoiToVuAn(rs.getString("s_column_010"));
                response.setQuyetDinhKhoiToBiCan(rs.getString("s_column_011"));
                response.setQuyetDinhNhapTachVuAn(rs.getString("s_column_012"));
                response.setQuyetDinhKhongGiaHanTGDieuTraVuAn(rs.getString("s_column_013"));
                response.setDeNghiGiaHanTGDieuTraVuAn(rs.getString("s_column_014"));
                response.setYeuCauTruyNaBiCan(rs.getString("s_column_015"));
                response.setQuyetDinhHuyBoQDTamDinhChiDieuTra(rs.getString("s_column_016"));
                response.setYeuCauPhucHoiDieuTraVuAn(rs.getString("s_column_017"));
                response.setQuyetDinhPhucHoiDieuTraVuAn(rs.getString("s_column_018"));
                response.setQuyetDinhPheChuanLenhKhamXet(rs.getString("s_column_019"));
                response.setQuyetDinhPheChuanLenhThuGiuThuTin(rs.getString("s_column_020"));
                response.setQuyetDinhKhamXet(rs.getString("s_column_021"));
                response.setQuyetDinhThucNghiemDieuTra(rs.getString("s_column_022"));
                response.setQuyetDinhDoiChat(rs.getString("s_column_023"));
                response.setThongBaoKhongChapNhanDeNghiTrungCauGiamDinh(rs.getString("s_column_024"));
                response.setQuyetDinhTrungCauGiamDinh(rs.getString("s_column_025"));
                response.setYeuCauThongBaoKetluanGiamDinh(rs.getString("s_column_026"));
                response.setYeuCauCungCapTaiLieuLienQuan(rs.getString("s_column_027"));
                response.setVanBanKhac(rs.getString("s_column_028"));
                response.setGhiChu(rs.getString("s_column_029"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));

                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_02)
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
            return jdbcCall.executeFunction((Class<List<Book02Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy v???n d??? li???u Book02RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book02RepositoryImpl.querying");
        }
    }
}
