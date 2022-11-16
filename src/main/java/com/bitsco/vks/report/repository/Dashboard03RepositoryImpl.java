package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Dashboard03Request;
import com.bitsco.vks.report.response.Dashboard03Response;
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
public class Dashboard03RepositoryImpl implements Dashboard03Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Dashboard03Response> querying(Dashboard03Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard03Repository.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard03Response> rm = new SingleColumnRowMapper<Dashboard03Response>() {
            @Override
            public Dashboard03Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard03Response response = new Dashboard03Response();
                response.setStt(rs.getRow());
                response.setNgay(rs.getString("s_ngay"));
                response.setSoTamGiam(rs.getInt("n_tam_giam"));
                response.setSoTamGiu(rs.getInt("n_tam_giu"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_03)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DASHBOARD)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard03Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard03Repository.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard03Repository.querying");
        }
    }

    @Override
    public List<Dashboard03Response> detail(Dashboard03Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard03Repository.detail request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard03Response> rm = new SingleColumnRowMapper<Dashboard03Response>() {
            @Override
            public Dashboard03Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard03Response response = new Dashboard03Response();
                response.setStt(rs.getRow());
                response.setMaTamGiamTamGiu(rs.getString("s_ma"));
                response.setDonViBatGiu(rs.getString("s_don_vi_bat_giu"));
                response.setLoaiTamGiamTamGiu(rs.getString("s_loai"));
                response.setNguoiBiTamGiamTamGiu(rs.getString("s_nguoi_bi_bat"));
                response.setNgayTamGiamTamGiu(rs.getString("s_ngay_bat_giu"));
                response.setLyDoTamGiamTamGiu(rs.getString("s_ly_do"));
                response.setKiemSatVienThuLy(rs.getString("s_ksv_thu_ly"));
                response.setThongTinXuLy(rs.getString("s_thong_tin_xu_ly"));
                response.setQuyetDinhXuLy(rs.getString("s_quyet_dinh_xy_ly"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_03)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DETAIL)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard03Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard03Repository.detail", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard03Repository.detail");
        }
    }
}
