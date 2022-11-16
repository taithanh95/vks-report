package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.model.Report;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 15/06/2022
 * Time: 4:35 PM
 */
public interface LstReportRepository {
    List<Report> search(String genId) throws SQLException;

    List<Report> getListReport03(String genId) throws SQLException;

    List<Report> getListReport04(String genId) throws SQLException;

    List<Report> getListReport05(String genId) throws SQLException;

    List<Report> getListReport06(String genId) throws SQLException;

    List<Report> getListReport10(String genId) throws SQLException;

    List<Report> getListReport11(String genId) throws SQLException;

    List<Report> getListReport12(String genId) throws SQLException;
}
