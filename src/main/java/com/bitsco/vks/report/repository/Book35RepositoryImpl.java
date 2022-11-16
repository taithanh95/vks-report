package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.request.Book35Request;
import com.bitsco.vks.report.response.Book35Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Book35RepositoryImpl implements Book35Repository {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Override
    public List<Book35Response> queryingVcc(Book35Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book35RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Book35Response> book35ResponseList = new ArrayList<>();
        try {
            String dbURL = "jdbc:sqlserver://10.47.104.90;databaseName=Vu13_db";
            String user = "bitsco";
            String pass = "Bitsco@123";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(createQuery(request));
            while (rs.next()) {
                Book35Response book35Response = new Book35Response();
                book35Response.setStt(rs.getRow());
                book35Response.setS_column_2(DateCommon.convertDateToStringByPattern(rs.getDate("NgayTiepNhan"), Constant.DATE.FORMAT.DATE));
                book35Response.setS_column_3(rs.getString("NuocYeucau"));
                book35Response.setS_column_4(rs.getString("SoVB"));
                book35Response.setS_column_5(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVB"), Constant.DATE.FORMAT.DATE));
                book35Response.setS_column_6(rs.getString("TenYeuCau"));
                book35Response.setS_column_7(rs.getString("KetQuaTuongTro"));
                book35Response.setS_column_8(rs.getString("SoVBXL"));
                book35Response.setS_column_9(DateCommon.convertDateToStringByPattern(rs.getDate("NgayVBXL"), Constant.DATE.FORMAT.DATE));
                book35Response.setS_column_10(rs.getString("CoQuanNhan"));
                book35Response.setS_column_11(rs.getString("CanBoThuLy"));
                book35Response.setS_column_12(rs.getString("GhiChu"));
                book35ResponseList.add(book35Response);
            }
            return book35ResponseList;
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book35RepositoryImpl.querying", e);
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
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book35RepositoryImpl.querying");
        }
    }

    private String createQuery(Book35Request request) {
        String fromDate = DateCommon.convertDateToStringByPattern(request.getFromDate(), "yyyy-MM-dd");
        String toDate = DateCommon.convertDateToStringByPattern(DateCommon.addDay(request.getToDate(), 1), "yyyy-MM-dd");
        StringBuilder query = new StringBuilder("SELECT NgayTiepNhan, NuocYeuCau, SoVB, NgayVB, TenYeuCau, KetQuaTuongTro, SoVBXL, NgayVBXL, CoQuanNhan, CanBoThuLy, GhiChu"
                + " FROM vw_VTC_MauSo35 where NgayTiepNhan >= ");
        query.append("'" + fromDate + "'");
        query.append(" AND NgayTiepNhan < '" + toDate + "'");
        query.append(" ORDER BY NgayTiepNhan ASC");
        return query.toString();
    }
}
