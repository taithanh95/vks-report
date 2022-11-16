package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Dashboard04Request;
import com.bitsco.vks.report.response.Dashboard04Response;
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
public class Dashboard04RepositoryImpl implements Dashboard04Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Dashboard04Response> querying(Dashboard04Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard04Repository.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard04Response> rm = new SingleColumnRowMapper<Dashboard04Response>() {
            @Override
            public Dashboard04Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard04Response response = new Dashboard04Response();
                response.setStt(rs.getRow());
                response.setMaGiaiDoan(rs.getString("s_ma_giai_doan"));
                response.setGiaiDoan(rs.getString("s_giai_doan"));
                response.setSoVuAn(rs.getInt("n_so_vu_an"));
                response.setSoBiCan(rs.getInt("n_so_bi_can"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_04)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DASHBOARD)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard04Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard04Repository.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard04Repository.querying");
        }
    }

    @Override
    public List<Dashboard04Response> detail(Dashboard04Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard04Repository.detail request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard04Response> rm = new SingleColumnRowMapper<Dashboard04Response>() {
            @Override
            public Dashboard04Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard04Response response = new Dashboard04Response();
                response.setStt(rs.getRow());
                response.setMaGiaiDoan(rs.getString("s_ma_giai_doan"));
                response.setMaVuAn(rs.getString("s_ma_vu_an"));
                response.setTenVuAn(rs.getString("s_ten_vu_an"));
                response.setTenBiCanDauVu(rs.getString("s_bi_can_dau_vu"));
                response.setQuyetDinhKhoiToVuAn(rs.getString("s_qd_khoi_to_vu_an"));
                response.setNgayQuyetDinh(rs.getString("s_ngay_quyet_dinh"));
                response.setCoQuanRaQuyetDinh(rs.getString("s_co_quan_ra_qd"));
                response.setLoaiToiPham(rs.getString("s_loai_toi_pham"));
                response.setDieuLuatChinh(rs.getString("s_dieu_luat_chinh"));
                response.setNgayXayRa(rs.getString("s_ngay_xay_ra"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_04)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DETAIL)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard04Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard04Repository.detail", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard04Repository.detail");
        }
    }
}
