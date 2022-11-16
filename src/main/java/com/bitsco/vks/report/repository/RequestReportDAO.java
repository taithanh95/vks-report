package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.request.RequestUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 11/10/2022
 * Time: 11:48 AM
 */
@Service
public class RequestReportDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String updateValori_02(RequestUpdate requestUpdate, String genid) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                .withCatalogName("PKG_SUMMARY_REPORTS")
                .withFunctionName("fn_update_bieu2_2022");
        try {
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_genid", genid)
                    .addValue("p_rowcode", requestUpdate.getRowcode())
                    .addValue("p_valori", requestUpdate.getValori());

            Map results = jdbcCall.execute(paramMap);
            return (String) results.get("return");
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateValori_03(RequestUpdate requestUpdate, String genid) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                .withCatalogName("PKG_SUMMARY_REPORTS")
                .withFunctionName("fn_update_bieu3_2022");
        try {
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_genid", genid)
                    .addValue("p_rowcode", requestUpdate.getRowcode())
                    .addValue("p_valori", requestUpdate.getValori());

            Map results = jdbcCall.execute(paramMap);
            return (String) results.get("return");
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateValori_05(RequestUpdate requestUpdate, String genid) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                .withCatalogName("PKG_SUMMARY_REPORTS")
                .withFunctionName("fn_update_bieu5_2022");
        try {
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_genid", genid)
                    .addValue("p_rowcode", requestUpdate.getRowcode())
                    .addValue("p_valori", requestUpdate.getValori());

            Map results = jdbcCall.execute(paramMap);
            return (String) results.get("return");
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateValori_06(RequestUpdate requestUpdate, String genid) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource())
                .withCatalogName("PKG_SUMMARY_REPORTS")
                .withFunctionName("fn_update_bieu6_2022");
        try {
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("p_genid", genid)
                    .addValue("p_rowcode", requestUpdate.getRowcode())
                    .addValue("p_valori", requestUpdate.getValori());

            Map results = jdbcCall.execute(paramMap);
            return (String) results.get("return");
        } catch (Exception e){
            return e.getMessage();
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
}
