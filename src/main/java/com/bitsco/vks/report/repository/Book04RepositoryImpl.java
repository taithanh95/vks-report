package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book04Request;
import com.bitsco.vks.report.response.Book04Response;
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
public class Book04RepositoryImpl implements Book04Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book04Response> querying(Book04Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book04RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book04Response> rm = new SingleColumnRowMapper<Book04Response>() {
            @Override
            public Book04Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book04Response response = new Book04Response();
                response.setStt(rs.getRow());
                response.setNgayVksThuLy(rs.getString("s_ngay_vks_xu_ly"));
                response.setNguoiToCao(rs.getString("s_nguoi_to_cao"));
                response.setNoiDung(rs.getString("s_noi_dung_to_cao"));
                response.setNguoiBiToCao(rs.getString("s_nguoi_bi_to_cao"));
                response.setKiemSatVienThuLy(rs.getString("s_kiem_sat_vien_thu_ly"));
                response.setYeuCauCuaVienKiemSat(rs.getString("s_yc_vks"));
                response.setQuyetDinhGiaHanThoiGianGiaiQuyet(rs.getString("s_qd_gia_han"));
                response.setQuyetDinhTamDinhChi(rs.getString("s_qd_tam_dinh_chi"));
                response.setQuyetDinhHuyBoTamDinhChi(rs.getString("s_qd_huy_bo_dinh_chi"));
                response.setQuyetDinhPhucHoiGiaiQuyet(rs.getString("s_qd_phuc_hoi_gq"));
                response.setQuyetDinhGiaiQuyetTranhChapVeThamQuyen(rs.getString("s_qd_gq_tranh_chap"));
                response.setThongBaoGiaiQuyetToGiacTinBao(rs.getString("s_tb_gq"));
                response.setYeuCauVuAnHinhSu(rs.getString("s_yc_vahs"));
                response.setKetQuaThucHienYcVks(rs.getString("s_kq_vks"));
                response.setKetQuaGiaiQuyet(rs.getString("s_kq_gq"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setDenouncementId(rs.getLong("denouncement_id"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_04)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_reporter_info", StringCommon.isNullOrBlank(request.getNguoiToCao()) ? null : StringCommon.addLikeRightAndLeft(request.getNguoiToCao()))
                    .addValue("pi_dp_name", StringCommon.isNullOrBlank(request.getNguoiBiToCao()) ? null : request.getNguoiBiToCao().trim())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.chuyenDoiDanhSachMaQuyetDinh()) ? null : request.chuyenDoiDanhSachMaQuyetDinh())
                    .addValue("pi_unit_id", request.getMaDonVi());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book04Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book04RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book04RepositoryImpl.querying");
        }
    }
}
