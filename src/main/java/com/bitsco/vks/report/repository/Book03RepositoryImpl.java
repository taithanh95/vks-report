package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book03Request;
import com.bitsco.vks.report.response.Book03Response;
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
public class Book03RepositoryImpl implements Book03Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book03Response> querying(Book03Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book03RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book03Response> rm = new SingleColumnRowMapper<Book03Response>() {
            @Override
            public Book03Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book03Response response = new Book03Response();
                response.setStt(rs.getRow());
                response.setNgayVksTiepNhan(rs.getString("s_ngay_vks_tiep_nhan"));
                response.setNoiDungToGiac(rs.getString("s_noi_dung_to_giac"));
                response.setCanBoTiepNhan(rs.getString("s_can_bo_tiep_nhan"));
                response.setDonViTiepNhan(rs.getString("s_don_vi_tiep_nhan"));
                response.setMaDonVi(rs.getString("s_ma_don_vi"));
                response.setPhieuChuyenTin(rs.getString("s_phieu_chuyen_tin"));
                response.setMaLoaiTin(rs.getString("s_ma_loai_tin"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setNguoiToCao(rs.getString("s_nguoi_to_cao"));
                response.setNguoiBiToCao(rs.getString("s_nguoi_bi_to_cao"));
                response.setNguoiToCao(rs.getString("s_nguoi_to_cao"));
                response.setKetQuaVks(rs.getString("s_ket_qua_vks"));
                response.setMaQuyetDinh(rs.getString("s_ma_quyet_dinh"));
                response.setDenouncementId(rs.getLong("denouncement_id"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_03)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_reporter_info", StringCommon.isNullOrBlank(request.getNguoiToCao()) ? null : request.getNguoiToCao().trim())
                    .addValue("pi_dp_name", StringCommon.isNullOrBlank(request.getNguoiBiToCao()) ? null : request.getNguoiBiToCao().trim())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.chuyenDoiDanhSachMaQuyetDinh()) ? null : request.chuyenDoiDanhSachMaQuyetDinh())
                    .addValue("pi_unit_id", request.getMaDonVi());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book03Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book03RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book03RepositoryImpl.querying");
        }
    }
}
