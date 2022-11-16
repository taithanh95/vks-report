package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Dashboard02Request;
import com.bitsco.vks.report.response.Dashboard02Response;
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
public class Dashboard02RepositoryImpl implements Dashboard02Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Dashboard02Response> querying(Dashboard02Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard02Repository.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard02Response> rm = new SingleColumnRowMapper<Dashboard02Response>() {
            @Override
            public Dashboard02Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard02Response response = new Dashboard02Response();
                response.setStt(rs.getRow());
                response.setSoCapLenhVuAn(rs.getInt("n_cap_lenh_vu_an"));
                response.setSoCapLenhBiCan(rs.getInt("n_cap_lenh_bi_can"));
                response.setSoCapLenhTinBao(rs.getInt("n_cap_lenh_tbtg"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_02)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DASHBOARD)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard02Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard02Repository.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard02Repository.querying");
        }
    }

    @Override
    public List<Dashboard02Response> detail(Dashboard02Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Dashboard02Repository.detail request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Dashboard02Response> rm = new SingleColumnRowMapper<Dashboard02Response>() {
            @Override
            public Dashboard02Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dashboard02Response response = new Dashboard02Response();
                response.setStt(rs.getRow());
                response.setGiaiDoan(rs.getString("s_giai_doan"));
                response.setSoQuyetDinh(rs.getString("s_so_quyet_dinh"));
                response.setQuyetDinh(rs.getString("s_quyet_dinh"));
                response.setMaBiCan(rs.getString("s_ma_bi_can"));
                response.setTenBiCan(rs.getString("s_ten_bi_can"));
                response.setMaVuAn(rs.getString("s_ma_vu_an"));
                response.setTenVuAn(rs.getString("s_ten_vu_an"));
                response.setBiCanDauVu(rs.getString("s_bi_can_dau_vu"));
                response.setMaTinBao(rs.getString("s_ma_tin_bao"));
                response.setNguoiBaoTin(rs.getString("s_nguoi_bao_tin"));
                response.setNgayCapSoLenh(rs.getString("s_ngay_cap_so"));
                response.setHieuLucTuNgay(rs.getString("s_hieu_luc_tu_ngay"));
                response.setHieuLucDenNgay(rs.getString("s_hieu_luc_den_ngay"));
                response.setDonViCapSL(rs.getString("s_don_vi_cap_sl"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_DASHBOARD_02)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING_DETAIL)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_spp_id", request.getSppId())
                    .addValue("pi_type", request.getType());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Dashboard02Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Dashboard02Repository.detail", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Dashboard02Repository.detail");
        }
    }
}
