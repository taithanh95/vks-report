package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book16Request;
import com.bitsco.vks.report.response.Book16Response;
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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Book16RepositoryImpl implements Book16Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book16Response> querying(Book16Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book16RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book16Response> rm = new SingleColumnRowMapper<Book16Response>() {
            @Override
            public Book16Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book16Response response = new Book16Response();
                response.setSttDetail(rs.getRow() + " - " + rs.getString("stt"));
                response.setS_column_001(rs.getString("s_column_001"));
                response.setS_column_002(rs.getString("s_column_002"));
                response.setS_column_003(rs.getString("s_column_003"));
                response.setS_column_004(rs.getString("s_column_004"));
                response.setS_column_005(rs.getString("s_column_005"));
                response.setS_column_006(rs.getString("s_column_006"));
                response.setS_column_007(rs.getString("s_column_007"));
                response.setS_column_008(rs.getString("s_column_008"));
                response.setS_column_009(rs.getString("s_column_009"));
                response.setS_column_010(rs.getString("s_column_010"));
                response.setS_column_011(rs.getString("s_column_011"));
                response.setS_casecode(rs.getString("s_casecode"));
                response.setS_accucode(rs.getString("s_accucode"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_16)
                    .withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_case_code", StringCommon.isNullOrBlank(request.getCaseCode()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseCode().trim()))
                    .addValue("pi_case_name", StringCommon.isNullOrBlank(request.getCaseName()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseName().trim()))
                    .addValue("pi_accu_code", StringCommon.isNullOrBlank(request.getAccuCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuCode().trim()))
                    .addValue("pi_accu_name", StringCommon.isNullOrBlank(request.getAccuName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccuName().trim()))
                    .addValue("pi_group_law_code", StringCommon.isNullOrBlank(request.getGroupLawCode()) ? null : request.getGroupLawCode())
                    .addValue("pi_law_id", StringCommon.isNullOrBlank(request.getLawId()) ? null : request.getLawId())
                    .addValue("pi_item", StringCommon.isNullOrBlank(request.getItem()) ? null : request.getItem())
                    .addValue("pi_point", StringCommon.isNullOrBlank(request.getPoint()) ? null : request.getPoint())
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_unit_id", request.getUnitId());
            //First parameter is function output parameter type.
            return jdbcCall.executeFunction((Class<List<Book16Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book16RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book16RepositoryImpl.querying");
        }
    }

    @Override
    public List<Book16Response> queryingVcc(Book16Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book16RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Book16Response> book16ResponseList = new ArrayList<>();
        try {
            String dbURL = "jdbc:sqlserver://10.47.104.90;databaseName=VCC";
            String user = "bitsco";
            String pass = "Bitsco@123";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(createQuery(request));
            while (rs.next()) {
                Book16Response book16Response = new Book16Response();
                String gioiTinh = rs.getString("GioiTinh");
                if (StringCommon.isNullOrBlank(gioiTinh)) {
                    gioiTinh = "";
                } else if (gioiTinh.equals("1")) {
                    gioiTinh = " - Giới tính: Nam";
                } else if (gioiTinh.equals("2")) {
                    gioiTinh = " - Giới tính: Nữ";
                } else
                    gioiTinh = "";
                String dangVien = rs.getString("DangVien");
                if (StringCommon.isNullOrBlank(dangVien)) {
                    dangVien = "";
                } else if (dangVien.equals("1")) {
                    dangVien = " - Đảng Viên";
                } else dangVien = "";
                String isDauVu = rs.getString("IsDauVu");
                if (StringCommon.isNullOrBlank(isDauVu)) {
                    isDauVu = "";
                } else if (isDauVu.equals("1")) {
                    isDauVu = " - Bị can đầu vụ";
                } else isDauVu = "";
                book16Response.setSttDetail(
                        (StringCommon.isNullOrBlank(rs.getString("soVCC")) ? "" : (rs.getString("soVCC")))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVCC"), Constant.DATE.FORMAT.DATE)) ? "" : " - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayVCC"), Constant.DATE.FORMAT.DATE))
                );
                book16Response.setS_column_001(
                        (StringCommon.isNullOrBlank(rs.getString("HoTen")) ? "" : rs.getString("HoTen"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgaySinh"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgaySinh"), Constant.DATE.FORMAT.DATE)))
                                + gioiTinh + dangVien + isDauVu
                );
                book16Response.setS_column_002(
                        (StringCommon.isNullOrBlank(rs.getString("SoPT")) ? "" : rs.getString("SoPT"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("MSDieuLuat")) ? "" : (" - Điều: " + rs.getString("MSDieuLuat")))
                                + (StringCommon.isNullOrBlank(rs.getString("TenDieuLuat")) ? "" : (" - " + rs.getString("TenDieuLuat")))
                );
                book16Response.setS_column_003(
                        (StringCommon.isNullOrBlank(rs.getString("SoKN")) ? "" : rs.getString("SoKN"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKN"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayKN"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("NoiDungKN")) ? "" : (" - " + rs.getString("NoiDungKN")))
                                + (StringCommon.isNullOrBlank(rs.getString("TenCoQuanKN")) ? "" : (" - " + rs.getString("TenCoQuanKN")))
                );
                book16Response.setS_column_004(StringCommon.isNullOrBlank(rs.getString("TenCoQuanKN")) ? "" : rs.getString("TenCoQuanKN"));
                book16Response.setS_column_005(
                        (StringCommon.isNullOrBlank(rs.getString("SoQDXX")) ? "" : rs.getString("SoQDXX") + " - ")
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayQDXX"), Constant.DATE.FORMAT.DATE)) ? "" : (DateCommon.convertDateToStringByPattern(rs.getDate("NgayQDXX"), Constant.DATE.FORMAT.DATE)))
                );
                book16Response.setS_column_006(
                        (StringCommon.isNullOrBlank(rs.getString("SoGDT")) ? "" : rs.getString("SoGDT") + " - ")
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayGDT"), Constant.DATE.FORMAT.DATE)) ? "" : (DateCommon.convertDateToStringByPattern(rs.getDate("NgayGDT"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("NoiDungVA")) ? "" : (" - " + rs.getString("NoiDungVA")))
                                + (StringCommon.isNullOrBlank(rs.getString("KetQuaXetXu")) ? "" : (" - " + rs.getString("KetQuaXetXu")))
                );
                book16Response.setS_column_007(
                        (StringCommon.isNullOrBlank(rs.getString("SoST")) ? "" : rs.getString("SoST"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayST"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayST"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("KetQuaST")) ? "" : (" - " + rs.getString("KetQuaST")))
                );
                book16Response.setS_column_008(
                        (StringCommon.isNullOrBlank(rs.getString("SoPT")) ? "" : rs.getString("SoPT"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("KetQuaPT")) ? "" : (" -  " + rs.getString("KetQuaPT")))
                );
                book16Response.setS_column_009(
                        (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayTCCChuyenVCCDieuTraLai"), Constant.DATE.FORMAT.DATE)) ? "" : DateCommon.convertDateToStringByPattern(rs.getDate("NgayTCCChuyenVCCDieuTraLai"), Constant.DATE.FORMAT.DATE))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVCCChuyenVCDDieuTraLai"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayVCCChuyenVCDDieuTraLai"), Constant.DATE.FORMAT.DATE)))
                );
                book16Response.setS_column_010(
                        (StringCommon.isNullOrBlank(rs.getString("SoBCVTCKN")) ? "" : rs.getString("SoBCVTCKN"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayBCVTCKN"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayBCVTCKN"), Constant.DATE.FORMAT.DATE)))
                );
                book16ResponseList.add(book16Response);
            }
            return book16ResponseList;
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book16RepositoryImpl.querying", e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book16RepositoryImpl.querying");
        }
    }

    private String createQuery(Book16Request request) {
        String donVi = request.getUnitId();
        if (donVi.equals("0180")) {
            donVi = "1";
        } else if (donVi.equals("0182")) {
            donVi = "2";
        } else donVi = "3";
        String soVCC = request.getSoVCC();
        String fromDate = DateCommon.convertDateToStringByPattern(request.getFromDate(), "yyyy-MM-dd");
        String toDate = DateCommon.convertDateToStringByPattern(DateCommon.addDay(request.getToDate(), 1), "yyyy-MM-dd");
        StringBuilder query = new StringBuilder("select SoVCC, NgayVCC, IsDauVu, HoTen, SoPT, SoKN, SoQDXX, SoGDT, SoST, SoPT, NgayTCCChuyenVCCDieuTraLai," +
                " SoBCVTCKN, NgaySinh, NgayPT, NgayKN, NgayQDXX, NgayGDT, NgayST, KetQuaST, KetQuaPT," +
                " NgayVCCChuyenVCDDieuTraLai, NgayBCVTCKN, GioiTinh, MSDieuLuat, NoiDungKN, NoiDungVA," +
                " DangVien, TenDieuLuat, TenCoQuanKN, IsDauVu, NamDieuLuat, SoTLHS, KetQuaXetXu" +
                " from vw_VTC_Mau16 where NgayVCC >= ");
        query.append("'" + fromDate + "'");
        query.append(" AND NgayVCC < '" + toDate + "'");
        query.append(" AND DonVi = '" + donVi + "'");
        if (!StringCommon.isNullOrBlank(soVCC)) {
            query.append(" AND SoVCC = '" + soVCC + "'");
        }
        query.append(" ORDER BY NgayVCC DESC");
        return query.toString();
    }
}
