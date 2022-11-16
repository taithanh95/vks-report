package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book06Request;
import com.bitsco.vks.report.response.Book06Response;
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
public class Book06RepositoryImpl implements Book06Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book06Response> querying(Book06Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book06RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book06Response> rm = new SingleColumnRowMapper<Book06Response>() {
            @Override
            public Book06Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book06Response response = new Book06Response();
                //response.setStt(rs.getInt(rs.getRow()));
                response.setVuAnBiCan(rs.getString("s_case_accused"));
                response.setQuyetDinhLenhBatGiuTHKhanCap(rs.getString("s_column_002"));
                response.setLenhBatGiuTHKhanCap(rs.getString("s_column_003"));
                response.setQuyetDinhKhongPheChuanGiaHanTamGiu(rs.getString("s_column_004"));
                response.setQuyetDinhPheChuanGiaHanTamGiuL1L2(rs.getString("s_column_005"));
                response.setQuyetDinhHuyBoLenhTamGiu(rs.getString("s_column_006"));
                response.setQuyetDinhTraTuDo(rs.getString("s_column_007"));
                response.setQuyetDinhPheChuanLenhBatBiCan(rs.getString("s_column_008"));
                response.setQuyetDinhKhongPheChuanLenhBatBiCan(rs.getString("s_column_009"));
                response.setQuyetDinhPheChuanLenhTamGiam(rs.getString("s_column_010"));
                response.setQuyetDinhKhongPheChuanLenhTamGiam(rs.getString("s_column_011"));
                response.setYeuCauApDungBienPhapTamGiamBiCan(rs.getString("s_column_012"));
                response.setLenhBatBiCanDeTamGiam(rs.getString("s_column_013"));
                response.setQuyetDinhGiaHanThoiHanTamGiamL1L2L3(rs.getString("s_column_014"));
                response.setQuyetDinhGiaHanThoiHanTamGiamDacBiet(rs.getString("s_column_015"));
                response.setQuyetDinhHuyBoBienPhapTamGiam(rs.getString("s_column_016"));
                response.setQuyetDinhThayTheBienPhapNganChan(rs.getString("s_column_017"));
                response.setQuyetDinhBienPhapBaoLinh(rs.getString("s_column_018"));
                response.setQuyetDinhBaoLinh(rs.getString("s_column_019"));
                response.setQuyetDinhPCKPCDatTienDeBaoDam(rs.getString("s_column_020"));
                response.setQuyetDinhDatTienDeBaoDam(rs.getString("s_column_021"));
                response.setLenhCamDiKhoiNoiCuTru(rs.getString("s_column_022"));
                response.setThongBaoApDungBienPhapCamDiKhoiNoiCuTru(rs.getString("s_column_023"));
                response.setQuyetDinhHuyBoBienPhapCamDiKhoiNoiCuTru(rs.getString("s_column_024"));
                response.setQuyetDinhTamHoanXuatCanh(rs.getString("s_column_025"));
                response.setQuyetDinhHuyBoTamHoanXuatCanh(rs.getString("s_column_026"));
                response.setLenhTamGiamDeTruyTo(rs.getString("s_column_027"));
                response.setLenhBatTamGiamBiCanDeTruyTo(rs.getString("s_column_028"));
                response.setQuyetDinhGiaHanThoiHanTamGiamDeTruyTo(rs.getString("s_column_029"));
                response.setQuyetDinhApGiaiBiCan(rs.getString("s_column_030"));
                response.setQuyetDinhDanGiai(rs.getString("s_column_031"));
                response.setLenhKeBienTaiSan(rs.getString("s_column_032"));
                response.setLenhKhamXet(rs.getString("s_column_033"));
                response.setQuyetDinhHuyBoKeBienTaiSan(rs.getString("s_column_034"));
                response.setLenhPhongToaTaiKhoan(rs.getString("s_column_035"));
                response.setQuyetDinhHuyBoPhongToaTaiKhoan(rs.getString("s_column_036"));
                response.setGhiChu(rs.getString("s_column_037"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_06)
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
            return jdbcCall.executeFunction((Class<List<Book06Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book06RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book06RepositoryImpl.querying");
        }
    }
}
