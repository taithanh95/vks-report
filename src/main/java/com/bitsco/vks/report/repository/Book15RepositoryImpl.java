package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.Book15Request;
import com.bitsco.vks.report.response.Book15Response;
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
public class Book15RepositoryImpl implements Book15Repository {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book15Response> querying(Book15Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book15RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<Book15Response> rm = new SingleColumnRowMapper<Book15Response>() {
            @Override
            public Book15Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book15Response response = new Book15Response();
                response.setStt(rs.getRow());
                response.setNgayThangThuLy(rs.getString("s_column_001"));
                response.setVuAnBiCan(rs.getString("s_column_002"));
                response.setBanAnSoTham(rs.getString("s_column_003"));
                response.setKiemSatVien(rs.getString("s_column_004"));
                response.setThamPhan(rs.getString("s_column_005"));
                response.setKhangCaoPhucTham(rs.getString("s_column_006"));
                response.setKhangNghiPhucTham(rs.getString("s_column_007"));
                response.setDinhChiXetXuPhucTham(rs.getString("s_column_008"));
                response.setDuaVuAnRaXetXu(rs.getString("s_column_009"));
                response.setNgayXetXuPhucTham(rs.getString("s_column_010"));
                response.setTamHoanPhienToa(rs.getString("s_column_011"));
                response.setQuanDiemCuaVKS(rs.getString("s_column_012"));
                response.setBanAn(rs.getString("s_column_013"));
                response.setDeNghiVKSCapTrenKhangNghiGDT(rs.getString("s_column_014"));
                response.setGhiChu(rs.getString("s_column_015"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(ReportConstant.PACKAGE.PKG_REPORT_BOOK_15).withFunctionName(ReportConstant.FUNCTION.FUNC_QUERYING).returningResultSet("return", rm);
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
            return jdbcCall.executeFunction((Class<List<Book15Response>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book15RepositoryImpl.querying", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book15RepositoryImpl.querying");
        }
    }

    @Override
    public List<Book15Response> queryingVcc(Book15Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book15RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Book15Response> book15ResponseList = new ArrayList<>();
        try {
            String dbURL = "jdbc:sqlserver://10.47.104.90;databaseName=VCC";
            String user = "bitsco";
            String pass = "Bitsco@123";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(createQuery(request));
            while (rs.next()) {
                Book15Response book15Response = new Book15Response();
                String gioiTinh = rs.getString("GioiTinh");
                if (StringCommon.isNullOrBlank(gioiTinh)) {
                    gioiTinh = "";
                } else if (gioiTinh.equals("1")) {
                    gioiTinh = " - Giới tính: Nam";
                } else if (gioiTinh.equals("2")) {
                    gioiTinh = " - Giới tính: Nữ";
                } else gioiTinh = "";
                String dangVien = rs.getString("DangVien");
                if (StringCommon.isNullOrBlank(dangVien)) {
                    dangVien = "";
                } else if (dangVien.equals("1")) {
                    dangVien = " - Đảng Viên";
                } else dangVien = "";
                String dinhChiRutKC = rs.getString("DinhChiRutKC");
                if (StringCommon.isNullOrBlank(dinhChiRutKC)) {
                    dinhChiRutKC = "";
                } else if (dinhChiRutKC.equals("1")) {
                    dinhChiRutKC = " - Có đình chỉ rút kháng cáo";
                } else dinhChiRutKC = "";
                String dinhChiRutKN = rs.getString("DinhChiRutKN");
                if (StringCommon.isNullOrBlank(dinhChiRutKN)) {
                    dinhChiRutKN = "";
                } else if (dinhChiRutKN.equals("1")) {
                    dinhChiRutKN = " - Có đình chỉ rút kháng nghị";
                } else dinhChiRutKN = "";
                String isPhienToaTARutKN = rs.getString("IsPhienToaTARutKN");
                if (StringCommon.isNullOrBlank(isPhienToaTARutKN)) {
                    isPhienToaTARutKN = "";
                } else if (isPhienToaTARutKN.equals("1")) {
                    isPhienToaTARutKN = "Tòa án rút kháng nghị";
                } else isPhienToaTARutKN = "";
                String isPhienToaVKSRutKN = rs.getString("IsPhienToaVKSRutKN");
                if (StringCommon.isNullOrBlank(isPhienToaVKSRutKN)) {
                    isPhienToaVKSRutKN = "";
                } else if (isPhienToaVKSRutKN.equals("1")) {
                    isPhienToaVKSRutKN = " - VKS rút kháng nghị";
                } else isPhienToaVKSRutKN = "";
                String coBaoChuaVien = rs.getString("CoBaoChuaVien");
                if (StringCommon.isNullOrBlank(coBaoChuaVien)) {
                    coBaoChuaVien = "";
                } else if (coBaoChuaVien.equals("1")) {
                    coBaoChuaVien = " - Có bào chữa viên";
                } else coBaoChuaVien = "";
                String isDauVu = rs.getString("IsDauVu");
                if (StringCommon.isNullOrBlank(isDauVu)) {
                    isDauVu = "";
                } else if (isDauVu.equals("1")) {
                    isDauVu = " - Bị can đầu vụ";
                } else isDauVu = "";
                String cot7 = "";
                if ((!StringCommon.isNullOrBlank(rs.getString("SoKNVCD")) || !StringCommon.isNullOrBlank(rs.getString("NgayKNVCD")) || !StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCD"))) && (StringCommon.isNullOrBlank(rs.getString("SoKNVCC")) && StringCommon.isNullOrBlank(rs.getString("NgayKNVCC")) && StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCC")))) {
                    cot7 = "VKS cấp dưới kháng nghị: ";
                } else if (((!StringCommon.isNullOrBlank(rs.getString("SoKNVCD")) || !StringCommon.isNullOrBlank(rs.getString("NgayKNVCD")) || !StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCD"))) && (!StringCommon.isNullOrBlank(rs.getString("SoKNVCC")) || !StringCommon.isNullOrBlank(rs.getString("NgayKNVCC")) || !StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCC"))))) {
                    cot7 = "; VKS cấp dưới kháng nghị: ";
                } else cot7 = "";
                String ndkcbc = "";
                if ((!StringCommon.isNullOrBlank(rs.getString("NoiDungKCBiCao")) && !StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiCao"), Constant.DATE.FORMAT.DATE)))) {
                    ndkcbc = (rs.getString("NoiDungKCBiCao") + " - ");
                } else if ((StringCommon.isNullOrBlank(rs.getString("NoiDungKCBiCao")) && !StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiCao"), Constant.DATE.FORMAT.DATE)))) {
                    ndkcbc = DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiCao"), Constant.DATE.FORMAT.DATE);
                } else ndkcbc = "";
                book15Response.setNgayThangThuLy(
                        (StringCommon.isNullOrBlank(rs.getString("soVCC")) ? "" : (rs.getString("soVCC")))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVCC"), Constant.DATE.FORMAT.DATE)) ? "" : " - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayVCC"), Constant.DATE.FORMAT.DATE))

                );
                book15Response.setVuAnBiCan(
                        (StringCommon.isNullOrBlank(rs.getString("HoTen")) ? "" : rs.getString("HoTen"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgaySinh"), Constant.DATE.FORMAT.DATE)) ? "" : (" - Ngày sinh: " + DateCommon.convertDateToStringByPattern(rs.getDate("NgaySinh"), Constant.DATE.FORMAT.DATE)))
                                + ((StringCommon.isNullOrBlank(rs.getString("NamSinh")) || (!StringCommon.isNullOrBlank(rs.getString("NgaySinh")))) ? "" : (" - Năm sinh: " + rs.getString("NamSinh")))
                                + gioiTinh
                                + dangVien + isDauVu
                );
                book15Response.setBanAnSoTham(
                        (StringCommon.isNullOrBlank(rs.getString("SoST")) ? "" : rs.getString("SoST"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayST"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayST"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("NamDieuLuat")) ? "" : (" - " + rs.getString("NamDieuLuat")))
                                + (StringCommon.isNullOrBlank(rs.getString("MSDieuLuat")) ? "" : (" - Điều: " + rs.getString("MSDieuLuat")))
                                + (StringCommon.isNullOrBlank(rs.getString("TenDieuLuat")) ? "" : (" - " + rs.getString("TenDieuLuat")))
                                + (StringCommon.isNullOrBlank(rs.getString("KetQuaST")) ? "" : (" - " + rs.getString("KetQuaST")))
                );
                book15Response.setKiemSatVien(rs.getString("TenKSV"));

                book15Response.setThamPhan(
                        (StringCommon.isNullOrBlank(rs.getString("ThamPhan1")) ? "" : rs.getString("ThamPhan1"))
                                + (StringCommon.isNullOrBlank(rs.getString("ThamPhan2")) ? "" : (" - " + rs.getString("ThamPhan2")))
                                + (StringCommon.isNullOrBlank(rs.getString("ThamPhan3")) ? "" : (" - " + rs.getString("ThamPhan3")))
                );
                book15Response.setKhangCaoPhucTham(
                        ndkcbc
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiCao"), Constant.DATE.FORMAT.DATE)) ? "" : (DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiCao"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("NoiDungKCBiHai")) ? "" : (" - " + rs.getString("NoiDungKCBiHai")))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiHai"), Constant.DATE.FORMAT.DATE)) ? "" : (DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCBiHai"), Constant.DATE.FORMAT.DATE) + " - "))
                                + (StringCommon.isNullOrBlank(rs.getString("NoiDungKCLienQuan")) ? "" : (rs.getString("NoiDungKCLienQuan") + " - "))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCLienQuan"), Constant.DATE.FORMAT.DATE)) ? "" : (DateCommon.convertDateToStringByPattern(rs.getDate("NgayKCLienQuan"), Constant.DATE.FORMAT.DATE)))
                );
                book15Response.setKhangNghiPhucTham(((!StringCommon.isNullOrBlank(rs.getString("SoKNVCC")) || !StringCommon.isNullOrBlank(rs.getString("NgayKNVCC")) || !StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCC"))) ? "VCC kháng nghị: " : "")
                        + (StringCommon.isNullOrBlank(rs.getString("SoKNVCC")) ? "" : rs.getString("SoKNVCC"))
                        + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKNVCC"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayKNVCC"), Constant.DATE.FORMAT.DATE)))
                        + (StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCC")) ? "" : (" - " + rs.getString("NoiDungKNVCC")))
                        + cot7
                        + (StringCommon.isNullOrBlank(rs.getString("SoKNVCD")) ? "" : (" - " + rs.getString("SoKNVCD")))
                        + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayKNVCD"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayKNVCD"), Constant.DATE.FORMAT.DATE)))
                        + (StringCommon.isNullOrBlank(rs.getString("NoiDungKNVCD")) ? "" : (" - " + rs.getString("NoiDungKNVCD")))
                );
                book15Response.setDinhChiXetXuPhucTham(
                        (StringCommon.isNullOrBlank(rs.getString("SoDCXX")) ? "" : rs.getString("SoDCXX"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayDCXX"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayDCXX"), Constant.DATE.FORMAT.DATE)))
                                + dinhChiRutKC
                                + dinhChiRutKN
                );
                book15Response.setDuaVuAnRaXetXu(
                        (StringCommon.isNullOrBlank(rs.getString("SoQDXX")) ? "" : rs.getString("SoQDXX"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayQDXX"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayQDXX"), Constant.DATE.FORMAT.DATE)))
                );
                book15Response.setNgayXetXuPhucTham(DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE));

                book15Response.setTamHoanPhienToa(
                        (StringCommon.isNullOrBlank(rs.getString("SoHoan")) ? "" : rs.getString("SoHoan"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayHoan"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayHoan"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("LyDoHoan")) ? "" : (" - " + rs.getString("LyDoHoan")))
                );
                book15Response.setQuanDiemCuaVKS(rs.getString("KSVDeNghi"));
                book15Response.setBanAn(
                        (StringCommon.isNullOrBlank(rs.getString("SoPT")) ? "" : (rs.getString("SoPT") + " - "))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE)) ? "" : (DateCommon.convertDateToStringByPattern(rs.getDate("NgayPT"), Constant.DATE.FORMAT.DATE)))
                                + (StringCommon.isNullOrBlank(rs.getString("NoiDungBAPT")) ? "" : (" - " + rs.getString("NoiDungBAPT")))
                                + (StringCommon.isNullOrBlank(rs.getString("KetQuaXetXu")) ? "" : (" - " + rs.getString("KetQuaXetXu")))
                );
                book15Response.setDeNghiVKSCapTrenKhangNghiGDT(
                        (StringCommon.isNullOrBlank(rs.getString("SoVTCKhangNghi")) ? "" : rs.getString("SoVTCKhangNghi"))
                                + (StringCommon.isNullOrBlank(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVTCKhangNghi"), Constant.DATE.FORMAT.DATE)) ? "" : (" - " + DateCommon.convertDateToStringByPattern(rs.getDate("NgayVTCKhangNghi"), Constant.DATE.FORMAT.DATE)))
                );
                book15Response.setGhiChu(
                        isPhienToaTARutKN
                                + isPhienToaVKSRutKN
                                + coBaoChuaVien
                );
                book15ResponseList.add(book15Response);
            }
            return book15ResponseList;
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book15RepositoryImpl.querying", e);
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
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book15RepositoryImpl.querying");
        }
    }

    private String createQuery(Book15Request request) {
        String donVi = request.getUnitId();
        if (donVi.equals("0180")) {
            donVi = "1";
        } else if (donVi.equals("0182")) {
            donVi = "2";
        } else donVi = "3";
        String soVCC = request.getSoVCC();
        String fromDate = DateCommon.convertDateToStringByPattern(request.getFromDate(), "yyyy-MM-dd");
        String toDate = DateCommon.convertDateToStringByPattern(DateCommon.addDay(request.getToDate(), 1), "yyyy-MM-dd");
        StringBuilder query = new StringBuilder("select SoVCC, KetQuaST, NgayVCC, isDauVu, HoTen, SoST, TenKSV, ThamPhan1, NoiDungKCBiCao, SoKNVCC, SoDCXX, SoQDXX,"
                + " NgayPT, SoHoan, KSVDeNghi, SoPT, SoVTCKhangNghi, IsPhienToaTARutKN, NgaySinh, NamSinh, NgayST, ThamPhan2, NgayKCBiCao,"
                + " NgayKNVCC, NgayDCXX, NgayQDXX, NgayHoan, NgayPT, NgayVTCKhangNghi, IsPhienToaVKSRutKN, GioiTinh, MSDieuLuat, ThamPhan3,"
                + " NoiDungKCBiHai, NoiDungKNVCC, DinhChiRutKC, LyDoHoan, NoiDungBAPT, CoBaoChuaVien, DangVien, TenDieuLuat,"
                + " NgayKCBiHai, SoKNVCD, DinhChiRutKN , NamDieuLuat, NoiDungKCLienQuan, NgayKNVCD,"
                + " NgayKCLienQuan, NoiDungKNVCD, SoTLHS, KetQuaXetXu from vw_VTC_Mau15 where NgayVCC >= ");
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
