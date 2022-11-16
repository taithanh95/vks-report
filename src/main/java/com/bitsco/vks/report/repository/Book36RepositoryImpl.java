package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.request.Book36Request;
import com.bitsco.vks.report.response.Book36Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class Book36RepositoryImpl implements Book36Repository{
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Override
    public List<Book36Response> queryingVcc(Book36Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book36RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Book36Response> book36ResponseList = new ArrayList<>();
        try {
            String dbURL = "jdbc:sqlserver://10.47.104.90;databaseName=Vu13_db";
            String user = "bitsco";
            String pass = "Bitsco@123";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(createQuery(request));
            while (rs.next()) {
                Book36Response book36Response = new Book36Response();
                book36Response.setStt(rs.getRow());
                book36Response.setS_column_2(DateCommon.convertDateToStringByPattern(rs.getDate("NgayTiepNhan"), Constant.DATE.FORMAT.DATE));
                book36Response.setS_column_3(rs.getString("CoQuanYeuCauTrongNuoc"));
                book36Response.setS_column_4(rs.getString("SoVB"));
                book36Response.setS_column_5(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVB"), Constant.DATE.FORMAT.DATE));
                book36Response.setS_column_6(rs.getString("TenYeuCau"));
                book36Response.setS_column_7(rs.getString("KetQuaTuongTro"));
                book36Response.setS_column_8(rs.getString("SoVBXL"));
                book36Response.setS_column_9(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVBXL"), Constant.DATE.FORMAT.DATE));
                book36Response.setS_column_10(rs.getString("CoQuanNhan"));
                book36Response.setS_column_11(rs.getString("CanBoThuLy"));
                book36Response.setS_column_12(rs.getString("GhiChu"));
                book36ResponseList.add(book36Response);
            }
            return book36ResponseList;
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book36RepositoryImpl.querying", e);
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
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book36RepositoryImpl.querying");
        }    }

    private String createQuery(Book36Request request) {
        String fromDate = DateCommon.convertDateToStringByPattern(request.getFromDate(), "yyyy-MM-dd");
        String toDate = DateCommon.convertDateToStringByPattern(DateCommon.addDay(request.getToDate(), 1), "yyyy-MM-dd");
        StringBuilder query = new StringBuilder("SELECT NgayTiepNhan ,CoQuanYeuCauTrongNuoc ,SoVB ,NgayVB ,TenYeuCau ,KetQuaTuongTro ,"
                + "SoVBXL ,NgayVBXL ,CoQuanNhan ,CanBoThuLy ,GhiChu FROM vw_VTC_MauSo36 WHERE NgayTiepNhan >= ");
        query.append("'" + fromDate + "'");
        query.append(" AND NgayTiepNhan < '" + toDate + "'");
        query.append(" ORDER BY NgayTiepNhan ASC");
        return query.toString();
    }
}
