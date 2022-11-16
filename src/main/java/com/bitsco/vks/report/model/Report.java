package com.bitsco.vks.report.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 15/06/2022
 * Time: 5:17 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {
    private String genid;
    private String sppid;
    private String rptlevel;
    private Date fromdate;
    private Date todate;
    private String rptcode;
    private String rowcode;
    private String rowname;
    private String ishead;
    private String valori;
    private String val;
    private String applyfor;
    private Date lasttime;

    // Them field cho cac bieu bao cao (4,10,11,12)
    private String rptsppid;
    private String codeid;
    private String groupid;
    private String lawcode;
    private String lawid;
    private String lawname;
    private Integer col3;
    private Integer col4;
    private Integer col5;
    private Integer col6;
    private Integer col7;
    private Integer col8;
    private Integer col9;
    private Integer col10;
    private Integer col11;
    private Integer col12;
    private Integer col13;
    private Integer col14;
    private Integer col15;
    private Integer col16;
    private Integer col17;
    private Integer col18;
    private Integer col19;
    private Integer col20;
    private Integer col21;
    private Integer col22;
    private Integer col23;
    private Integer col24;
    private Integer col25;
    private Integer col26;
    private Integer col27;
    private Integer col28;
    private Integer col29;
    private Integer col30;
    private Integer col31;
    private Integer col32;
    private Integer col33;
    private Integer col34;
    private Integer col35;
    private Integer col36;
    private Integer col37;
    private Integer col38;
    private Integer col39;
    private Integer col40;
    private Integer col41;
    private Integer col42;
    private Integer col43;
    private Integer col44;
    private Integer col45;
    private Integer col46;
    private Integer col47;
    private Integer col48;
    private Integer col49;
    private Integer col50;
    private Integer col51;
    private Integer col52;
    private Integer col53;
    private Integer col54;
    private Integer col55;
    private Integer col56;
    private Integer col57;
}
