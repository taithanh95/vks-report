package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book25Request;
import com.bitsco.vks.report.response.Book25Response;
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
public class Book25RepositoryImpl implements Book25Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book25Response> querying(Book25Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book25RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book25Response> rm = new SingleColumnRowMapper<Book25Response>() {
            @Override
            public Book25Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book25Response response = new Book25Response();
                response.setStt(rs.getRow());
                response.setHoTen(rs.getString("s_column_2"));
                response.setBanAnToiDanh(rs.getString("s_column_3"));
                response.setMucAn(rs.getString("s_column_4"));
                response.setTamGiam(rs.getString("s_column_5"));
                response.setQuyetDinhThiHanhAn(rs.getString("s_column_6"));
                response.setQuyetDinhDuaNguoiChapHanhAnDenTraiGiam(rs.getString("s_column_7"));
                response.setChuyenDiNoiKhac(rs.getString("s_column_8"));
                response.setNoiKhacChuyenDen(rs.getString("s_column_9"));
                response.setQuyetDinhGiamThoiHanChapHanhAn(rs.getString("s_column_10"));
                response.setQuyetDinhTamDinhChiChapHanhAn(rs.getString("s_column_11"));
                response.setQuyetDinhApDungBienPhapBatBuocChuaBenh(rs.getString("s_column_12"));
                response.setQuyetDinhDinhChiChapHanhAn(rs.getString("s_column_13"));
                response.setBanAnGDTTuyenHuyHinhPhatTu(rs.getString("s_column_14"));
                response.setQuyetDinhTraTuDo(rs.getString("s_column_15"));
                response.setQuyetDinhMienChapHanhAn(rs.getString("s_column_16"));
                response.setQuyetDinhDacXa(rs.getString("s_column_17"));
                response.setQuyetDinhThaTuTruocThoiHan(rs.getString("s_column_18"));
                response.setDaChapHanhXong(rs.getString("s_column_19"));
                response.setChet(rs.getString("s_column_20"));
                response.setNgayTron(rs.getString("s_column_21"));
                response.setQuyetDinhTruyNa(rs.getString("s_column_22"));
                response.setBatLai(rs.getString("s_column_23"));
                response.setQuyetDinhXuLyKhiBatLai(rs.getString("s_column_24"));
                response.setViPhamBiKyLuat(rs.getString("s_column_25"));
                response.setQuyetDinhXuLyViPham(rs.getString("s_column_26"));
                response.setGhiChu(rs.getString("s_ghi_chu"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_25)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_accucode", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode().trim()))
                    .addValue("pi_accuname", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName().trim()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode().trim())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId().trim())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem().trim())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint().trim())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_sppid", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book25Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book25RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book25RepositoryImpl.querying");
        }
    }
}
