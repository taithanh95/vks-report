package com.bitsco.vks.report.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.report.request.Book33Request;
import com.bitsco.vks.report.response.Book33Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 31/10/2022
 * Time: 9:47 AM
 */
@Repository
public class Book33RepositoryImpl implements Book33Repository {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Override
    public List<Book33Response> queryingVcc(Book33Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] Book33RepositoryImpl.querying request = " + JsonCommon.objectToJsonNotNull(request));
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Book33Response> book33ResponseList = new ArrayList<>();
        try {
            String dbURL = "jdbc:sqlserver://10.47.104.90;databaseName=Vu13_db";
            String user = "bitsco";
            String pass = "Bitsco@123";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(dbURL, user, pass);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(createQuery(request));
            while (rs.next()) {
                Book33Response book33Response = new Book33Response();
                book33Response.setStt(rs.getRow());
                book33Response.setS_column_2(DateCommon.convertDateToStringByPattern(rs.getDate("NgayTiepNhan"), Constant.DATE.FORMAT.DATE));
                book33Response.setS_column_3(rs.getString("CoQuanYeuCauTrongNuoc"));
                book33Response.setS_column_4(rs.getString("SoVB"));
                book33Response.setS_column_5(rs.getString("HoSo"));
                book33Response.setS_column_6(rs.getString("DoiTuongLienQuan"));
                book33Response.setS_column_7(rs.getString("ToiDanh"));
                book33Response.setS_column_8(rs.getString("NoiDung"));
                book33Response.setS_column_9(rs.getString("SoVBChuyen"));
                book33Response.setS_column_10(rs.getString("NoiThucHien"));
                book33Response.setS_column_11(rs.getString("CanBoThuLy"));
                book33Response.setS_column_12(rs.getString("GhiChu"));
                book33ResponseList.add(book33Response);
            }
            return book33ResponseList;
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu Book33RepositoryImpl.querying", e);
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
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] Book33RepositoryImpl.querying");
        }
    }

    private String createQuery(Book33Request request) {
        String fromDate = DateCommon.convertDateToStringByPattern(request.getFromDate(), "yyyy-MM-dd");
        String toDate = DateCommon.convertDateToStringByPattern(DateCommon.addDay(request.getToDate(), 1), "yyyy-MM-dd");
        StringBuilder query = new StringBuilder("SELECT NgayTiepNhan, CoQuanYeuCauTrongNuoc, SoVB, HoSo, DoiTuongLienQuan, ToiDanh,NoiDung, SoVBChuyen, NoiThucHien, CanBoThuLy, GhiChu"
                + " FROM vw_VTC_MauSo33 where NgayTiepNhan >= ");
        query.append("'" + fromDate + "'");
        query.append(" AND NgayTiepNhan < '" + toDate + "'");
        query.append(" ORDER BY NgayTiepNhan ASC");
        return query.toString();
    }
}
