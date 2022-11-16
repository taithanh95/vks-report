package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Dashboard01Request;
import com.bitsco.vks.report.response.Dashboard01Response;
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
public class Dashboard01RepositoryImpl implements Dashboard01Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Dashboard01Response> querying(Dashboard01Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard01Repository.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard01Response> rm = new SingleColumnRowMapper<Dashboard01Response>() {
            @Override
            public Dashboard01Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard01Response response = new Dashboard01Response();
                response.setStt(rs.getRow());
                response.setSoTbtgChuaThucHien(rs.getInt("n_chua_thuc_hien"));
                response.setSoTbtgDangGiaiQuyet(rs.getInt("n_dang_giai_quyet"));
                response.setSoTbtgTamDinhChi(rs.getInt("n_tam_dinh_chi"));
                response.setSoTbtgDaGiaiQuyet(rs.getInt("n_da_giai_quyet"));
                response.setSoTbtgDaQuaHan(rs.getInt("n_da_qua_han"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_01)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DASHBOARD)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard01Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard01Repository.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard01Repository.querying");
        }
    }

    @Override
    public List<Dashboard01Response> detail(Dashboard01Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard01Repository.detail request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard01Response> rm = new SingleColumnRowMapper<Dashboard01Response>() {
            @Override
            public Dashboard01Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard01Response response = new Dashboard01Response();
                response.setStt(rs.getRow());
                response.setMaTinBao(rs.getString("s_ma_tin_bao"));
                response.setNgayTiepNhan(rs.getString("s_ngay_tiep_nhan"));
                response.setThoiHanGiaiQuyet(rs.getString("s_thoi_han_giai_quyet"));
                response.setNguoiToCao(rs.getString("s_nguoi_to_cao"));
                response.setNguoiBiToCao(rs.getString("s_nguoi_bi_to_cao"));
                response.setDieuTraVienThuLy(rs.getString("s_dieu_tra_vien_thu_ly"));
                response.setKiemSatVienThuLy(rs.getString("s_kiem_sat_vien_thu_ly"));
                response.setTrangThai(rs.getString("s_trang_thai"));
                response.setKetQuaGiaiQuyet(rs.getString("s_kq_gq"));
                response.setDonViTiepNhan(rs.getString("donViTiepNhan"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_01)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DETAIL)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard01Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard01Repository.detail", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard01Repository.detail");
        }
    }
}
