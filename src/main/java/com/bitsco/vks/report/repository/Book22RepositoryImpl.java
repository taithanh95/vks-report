package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book22Request;
import com.bitsco.vks.report.response.Book22Response;
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
public class Book22RepositoryImpl implements Book22Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book22Response> querying(Book22Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book22RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book22Response> rm = new SingleColumnRowMapper<Book22Response>() {
            @Override
            public Book22Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book22Response response = new Book22Response();
                response.setStt(rs.getRow());
                response.setHoTen(rs.getString("s_ho_ten"));
                response.setToiDanh(rs.getString("s_toi_danh"));
                response.setLenhTamGiam(rs.getString("s_lenh_tam_giam"));
                response.setLenhBatTamGiam(rs.getString("s_lenh_bat_tam_giam"));
                response.setQuyetDinhGiaHanTamGiam(rs.getString("s_qd_gia_han_tam_giam"));
                response.setTamGiamDieuTraBoSung(rs.getString("s_tam_giam_dieu_tra_bs"));
                response.setTruyToLenhTamGiam(rs.getString("s_truy_to_lenh_tam_giam"));
                response.setTruyToLenhBatTamGiam(rs.getString("s_truy_to_lenh_bat_tam_giam"));
                response.setTruyToGiaHanTamGiam(rs.getString("s_truy_to_gia_han_tam_giam"));
                response.setToaSoThamLenhTamGiam(rs.getString("s_toa_so_tham_lenh_tam_giam"));
                response.setToaSoThamLenhBatTamGiam(rs.getString("s_toa_so_tham_lenh_bat_tam_giam"));
                response.setToaSoThamGiaHanTamGiam(rs.getString("s_toa_so_tham_gia_han_tam_giam"));
                response.setToaPhucThamLenhTamGiam(rs.getString("s_toa_phuc_tham_lenh_tam_giam"));
                response.setToaPhucThamLenhBatTamGiam(rs.getString("s_toa_phuc_tham_lenh_bat_tam_giam"));
                response.setQuyetDinhThiHanhAn(rs.getString("s_quyet_dinh_thi_hanh_an"));
                response.setChuyenNoiKhac(rs.getString("s_chuyen_noi_khac"));
                response.setChuyenNoiKhacDen(rs.getString("s_chuyen_noi_khac_den"));
                response.setNguoiBiKetAnTaiNgoai(rs.getString("s_ng_bi_ket_an_tai_ngoai"));
                response.setQuyetDinhThayThe(rs.getString("s_qd_thay_the"));
                response.setQuyetDinhHuyBo(rs.getString("s_qd_huy_bo"));
                response.setTraTuDoQuyetDinhTamDinhChi(rs.getString("s_tra_tu_do_qd_tam_dinh_chi"));
                response.setTraTuDoKhangCao(rs.getString("s_tra_tu_do_khang_cao"));
                response.setHetThoiHanTu(rs.getString("s_het_thoi_han_tu"));
                response.setQuyetDinhTamDinhChiChapHanhAn(rs.getString("s_qd_tam_dinh_chi_chap_hanh_an"));
                response.setSoThamTraTuDo(rs.getString("s_so_tham_tra_tu_do"));
                response.setPhucThamTraTuDo(rs.getString("s_phuc_tham_tra_tu_do"));
                response.setVienKiemSatTraTuDo(rs.getString("s_vks_tra_tu_do"));
                response.setDaThiHanhAnTuHinh(rs.getString("s_da_thi_hanh_an_tu_hinh"));
                response.setQuyetDinhBatBuocChuaBenh(rs.getString("s_qd_bat_buoc_chua_benh"));
                response.setChuyenThiHanhAn(rs.getString("s_chuyen_thi_hanh_an"));
                response.setNgayTron(rs.getString("s_ngay_tron"));
                response.setQuyetDinhTruyNa(rs.getString("s_qd_truy_na"));
                response.setNgayBatLai(rs.getString("s_ngay_bat_lai"));
                response.setQuyetDinhXuLyKhiBatLai(rs.getString("s_qd_xu_ly_khi_bat_lai"));
                response.setViPhamKyLuat(rs.getString("s_vi_pham_ky_luat"));
                response.setQuyetDinhXuLyViPham(rs.getString("s_qd_xu_ly_vi_pham"));
                response.setChet(rs.getString("s_chet"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setS_accucode(rs.getString("s_accucode"));
                ;
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_22)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_accu_code", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode()))
                    .addValue("pi_accu_name", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId());

            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book22Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book22RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book22RepositoryImpl.querying");
        }
    }
}
