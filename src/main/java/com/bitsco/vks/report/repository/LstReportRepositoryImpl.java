package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 15/06/2022
 * Time: 4:39 PM
 */
@Repository
public class LstReportRepositoryImpl implements LstReportRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Report> search(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setRptcode(rs.getString("RPTCODE"));
                response.setRowcode(rs.getString("ROWCODE"));
                response.setRowname(rs.getString("ROWNAME"));
                response.setIshead(rs.getString("ISHEAD"));
                response.setValori(rs.getString("VALORI"));
                response.setVal(rs.getString("VAL"));
                response.setApplyfor(rs.getString("APPLYFOR"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    private DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@10.47.104.230:1521:QLAHSDC1");
        ds.setUsername("SPP_V2");
        ds.setPassword("Ab123456");
        return ds;
    }

    @Override
    public List<Report> getListReport03(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setRptcode(rs.getString("RPTCODE"));
                response.setRowcode(rs.getString("ROWCODE"));
                response.setRowname(rs.getString("ROWNAME"));
                response.setIshead(rs.getString("ISHEAD"));
                response.setValori(rs.getString("VALORI"));
                response.setVal(rs.getString("VAL"));
                response.setApplyfor(rs.getString("APPLYFOR"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_03")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    @Override
    public List<Report> getListReport05(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setRptcode(rs.getString("RPTCODE"));
                response.setRowcode(rs.getString("ROWCODE"));
                response.setRowname(rs.getString("ROWNAME"));
                response.setIshead(rs.getString("ISHEAD"));
                response.setValori(rs.getString("VALORI"));
                response.setVal(rs.getString("VAL"));
                response.setApplyfor(rs.getString("APPLYFOR"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_05")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    @Override
    public List<Report> getListReport06(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setRptcode(rs.getString("RPTCODE"));
                response.setRowcode(rs.getString("ROWCODE"));
                response.setRowname(rs.getString("ROWNAME"));
                response.setIshead(rs.getString("ISHEAD"));
                response.setValori(rs.getString("VALORI"));
                response.setVal(rs.getString("VAL"));
                response.setApplyfor(rs.getString("APPLYFOR"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_06")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    @Override
    public List<Report> getListReport11(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptsppid(rs.getString("RPTSPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setCodeid(rs.getString("CODEID"));
                response.setGroupid(rs.getString("GROUPID"));
                response.setLawcode(rs.getString("LAWCODE"));
                response.setLawid(rs.getString("LAWID"));
                response.setLawname(rs.getString("LAWNAME"));
                response.setCol3(rs.getInt("COL3"));
                response.setCol4(rs.getInt("COL4"));
                response.setCol5(rs.getInt("COL5"));
                response.setCol6(rs.getInt("COL6"));
                response.setCol7(rs.getInt("COL7"));
                response.setCol8(rs.getInt("COL8"));
                response.setCol9(rs.getInt("COL9"));
                response.setCol10(rs.getInt("COL10"));
                response.setCol11(rs.getInt("COL11"));
                response.setCol12(rs.getInt("COL12"));
                response.setCol13(rs.getInt("COL13"));
                response.setCol14(rs.getInt("COL14"));
                response.setCol15(rs.getInt("COL15"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_11")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    @Override
    public List<Report> getListReport04(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptsppid(rs.getString("RPTSPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setCodeid(rs.getString("CODEID"));
                response.setGroupid(rs.getString("GROUPID"));
                response.setLawcode(rs.getString("LAWCODE"));
                response.setLawid(rs.getString("LAWID"));
                response.setLawname(rs.getString("LAWNAME"));
                response.setCol3(rs.getInt("COL3"));
                response.setCol4(rs.getInt("COL4"));
                response.setCol5(rs.getInt("COL5"));
                response.setCol6(rs.getInt("COL6"));
                response.setCol7(rs.getInt("COL7"));
                response.setCol8(rs.getInt("COL8"));
                response.setCol9(rs.getInt("COL9"));
                response.setCol10(rs.getInt("COL10"));
                response.setCol11(rs.getInt("COL11"));
                response.setCol12(rs.getInt("COL12"));
                response.setCol13(rs.getInt("COL13"));
                response.setCol14(rs.getInt("COL14"));
                response.setCol15(rs.getInt("COL15"));
                response.setCol16(rs.getInt("COL16"));
                response.setCol17(rs.getInt("COL17"));
                response.setCol18(rs.getInt("COL18"));
                response.setCol19(rs.getInt("COL19"));
                response.setCol20(rs.getInt("COL20"));
                response.setCol21(rs.getInt("COL21"));
                response.setCol22(rs.getInt("COL22"));
                response.setCol23(rs.getInt("COL23"));
                response.setCol24(rs.getInt("COL24"));
                response.setCol25(rs.getInt("COL25"));
                response.setCol26(rs.getInt("COL26"));
                response.setCol27(rs.getInt("COL27"));
                response.setCol28(rs.getInt("COL28"));
                response.setCol29(rs.getInt("COL29"));
                response.setCol30(rs.getInt("COL30"));
                response.setCol31(rs.getInt("COL31"));
                response.setCol32(rs.getInt("COL32"));
                response.setCol33(rs.getInt("COL33"));
                response.setCol34(rs.getInt("COL34"));
                response.setCol35(rs.getInt("COL35"));
                response.setCol36(rs.getInt("COL36"));
                response.setCol37(rs.getInt("COL37"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_04")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    @Override
    public List<Report> getListReport10(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptsppid(rs.getString("RPTSPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setCodeid(rs.getString("CODEID"));
                response.setGroupid(rs.getString("GROUPID"));
                response.setLawcode(rs.getString("LAWCODE"));
                response.setLawid(rs.getString("LAWID"));
                response.setLawname(rs.getString("LAWNAME"));
                response.setCol3(rs.getInt("COL3"));
                response.setCol4(rs.getInt("COL4"));
                response.setCol5(rs.getInt("COL5"));
                response.setCol6(rs.getInt("COL6"));
                response.setCol7(rs.getInt("COL7"));
                response.setCol8(rs.getInt("COL8"));
                response.setCol9(rs.getInt("COL9"));
                response.setCol10(rs.getInt("COL10"));
                response.setCol11(rs.getInt("COL11"));
                response.setCol12(rs.getInt("COL12"));
                response.setCol13(rs.getInt("COL13"));
                response.setCol14(rs.getInt("COL14"));
                response.setCol15(rs.getInt("COL15"));
                response.setCol16(rs.getInt("COL16"));
                response.setCol17(rs.getInt("COL17"));
                response.setCol18(rs.getInt("COL18"));
                response.setCol19(rs.getInt("COL19"));
                response.setCol20(rs.getInt("COL20"));
                response.setCol21(rs.getInt("COL21"));
                response.setCol22(rs.getInt("COL22"));
                response.setCol23(rs.getInt("COL23"));
                response.setCol24(rs.getInt("COL24"));
                response.setCol25(rs.getInt("COL25"));
                response.setCol26(rs.getInt("COL26"));
                response.setCol27(rs.getInt("COL27"));
                response.setCol28(rs.getInt("COL28"));
                response.setCol29(rs.getInt("COL29"));
                response.setCol30(rs.getInt("COL30"));
                response.setCol31(rs.getInt("COL31"));
                response.setCol32(rs.getInt("COL32"));
                response.setCol33(rs.getInt("COL33"));
                response.setCol34(rs.getInt("COL34"));
                response.setCol35(rs.getInt("COL35"));
                response.setCol36(rs.getInt("COL36"));
                response.setCol37(rs.getInt("COL37"));
                response.setCol38(rs.getInt("COL38"));
                response.setCol39(rs.getInt("COL39"));
                response.setCol40(rs.getInt("COL40"));
                response.setCol41(rs.getInt("COL41"));
                response.setCol42(rs.getInt("COL42"));
                response.setCol43(rs.getInt("COL43"));
                response.setCol44(rs.getInt("COL44"));
                response.setCol45(rs.getInt("COL45"));
                response.setCol46(rs.getInt("COL46"));
                response.setCol47(rs.getInt("COL47"));
                response.setCol48(rs.getInt("COL48"));
                response.setCol49(rs.getInt("COL49"));
                response.setCol50(rs.getInt("COL50"));
                response.setCol51(rs.getInt("COL51"));
                response.setCol52(rs.getInt("COL52"));
                response.setCol53(rs.getInt("COL53"));
                response.setCol54(rs.getInt("COL54"));
                response.setCol55(rs.getInt("COL55"));
                response.setCol56(rs.getInt("COL56"));
                response.setCol57(rs.getInt("COL57"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_10")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }

    @Override
    public List<Report> getListReport12(String genId) throws SQLException {
        RowMapper<Report> rm = new SingleColumnRowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report response = new Report();
                response.setGenid(rs.getString("GENID"));
                response.setSppid(rs.getString("SPPID"));
                response.setRptsppid(rs.getString("RPTSPPID"));
                response.setRptlevel(rs.getString("RPTLEVEL"));
                response.setFromdate(rs.getDate("FROMDATE"));
                response.setTodate(rs.getDate("TODATE"));
                response.setCodeid(rs.getString("CODEID"));
                response.setGroupid(rs.getString("GROUPID"));
                response.setLawcode(rs.getString("LAWCODE"));
                response.setLawid(rs.getString("LAWID"));
                response.setLawname(rs.getString("LAWNAME"));
                response.setCol3(rs.getInt("COL3"));
                response.setCol4(rs.getInt("COL4"));
                response.setCol5(rs.getInt("COL5"));
                response.setCol6(rs.getInt("COL6"));
                response.setCol7(rs.getInt("COL7"));
                response.setCol8(rs.getInt("COL8"));
                response.setCol9(rs.getInt("COL9"));
                response.setCol10(rs.getInt("COL10"));
                response.setCol11(rs.getInt("COL11"));
                response.setCol12(rs.getInt("COL12"));
                response.setCol13(rs.getInt("COL13"));
                response.setCol14(rs.getInt("COL14"));
                response.setCol15(rs.getInt("COL15"));
                response.setCol16(rs.getInt("COL16"));
                response.setCol17(rs.getInt("COL17"));
                response.setCol18(rs.getInt("COL18"));
                response.setCol19(rs.getInt("COL19"));
                response.setCol20(rs.getInt("COL20"));
                response.setCol21(rs.getInt("COL21"));
                response.setLasttime(rs.getDate("LAST_TIME"));
                return response;
            }
        };
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                    .withCatalogName("PKG_SUMMARY_REPORTS") //package name
                    .withFunctionName("FN_GET_LIST_REPORT_12")
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_reportdisp", genId);
            return jdbcCall.executeFunction((Class<List<Report>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            throw (e);
        }
    }
}
