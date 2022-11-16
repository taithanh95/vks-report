package com.bitsco.vks.report.entities;

import com.bitsco.vks.report.constant.ReportConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ReportConstant.TABLE.REPORT_PERIOD_DETAIL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportPeriodDetail {
    @Column(name = "n_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ReportConstant.SEQUENCE.SQ_REPORT_PERIOD_DETAIL)
    @SequenceGenerator(name = ReportConstant.SEQUENCE.SQ_REPORT_PERIOD_DETAIL, sequenceName = ReportConstant.SEQUENCE.SQ_REPORT_PERIOD_DETAIL, allocationSize = 1)
    private Long id;

    @Column(name = "n_request_report_id")
    private Long requestReportId;
    @Column(name = "n_column_num")
    private Integer columnNum;
    @Column(name = "n_value")
    private Integer value;
    @Column(name = "n_value_manual")
    private Integer valueManual;
    @Column(name = "s_lawname")
    private String lawname;
    @Column(name = "s_lawid")
    private String lawid;
    @Column(name = "n_col3")
    private Integer col3;
    @Column(name = "n_col4")
    private Integer col4;
    @Column(name = "n_col5")
    private Integer col5;
    @Column(name = "n_col6")
    private Integer col6;
    @Column(name = "n_col7")
    private Integer col7;
    @Column(name = "n_col8")
    private Integer col8;
    @Column(name = "n_col9")
    private Integer col9;
    @Column(name = "n_col10")
    private Integer col10;
    @Column(name = "n_col11")
    private Integer col11;
    @Column(name = "n_col12")
    private Integer col12;
    @Column(name = "n_col13")
    private Integer col13;
    @Column(name = "n_col14")
    private Integer col14;
    @Column(name = "n_col15")
    private Integer col15;
    @Column(name = "n_col16")
    private Integer col16;
    @Column(name = "n_col17")
    private Integer col17;
    @Column(name = "n_col18")
    private Integer col18;
    @Column(name = "n_col19")
    private Integer col19;
    @Column(name = "n_col20")
    private Integer col20;
    @Column(name = "n_col21")
    private Integer col21;
    @Column(name = "n_col22")
    private Integer col22;
    @Column(name = "n_col23")
    private Integer col23;
    @Column(name = "n_col24")
    private Integer col24;
    @Column(name = "n_col25")
    private Integer col25;
    @Column(name = "n_col26")
    private Integer col26;
    @Column(name = "n_col27")
    private Integer col27;
    @Column(name = "n_col28")
    private Integer col28;
    @Column(name = "n_col29")
    private Integer col29;
    @Column(name = "n_col30")
    private Integer col30;
    @Column(name = "n_col31")
    private Integer col31;
    @Column(name = "n_col32")
    private Integer col32;
    @Column(name = "n_col33")
    private Integer col33;
    @Column(name = "n_col34")
    private Integer col34;
    @Column(name = "n_col35")
    private Integer col35;
    @Column(name = "n_col36")
    private Integer col36;
    @Column(name = "n_col37")
    private Integer col37;
    @Column(name = "n_col38")
    private Integer col38;
    @Column(name = "n_col39")
    private Integer col39;
    @Column(name = "n_col40")
    private Integer col40;
    @Column(name = "n_col41")
    private Integer col41;
    @Column(name = "n_col42")
    private Integer col42;
    @Column(name = "n_col43")
    private Integer col43;
    @Column(name = "n_col44")
    private Integer col44;
    @Column(name = "n_col45")
    private Integer col45;
    @Column(name = "n_col46")
    private Integer col46;
    @Column(name = "n_col47")
    private Integer col47;
    @Column(name = "n_col48")
    private Integer col48;
    @Column(name = "n_col49")
    private Integer col49;
    @Column(name = "n_col50")
    private Integer col50;
    @Column(name = "n_col51")
    private Integer col51;
    @Column(name = "n_col52")
    private Integer col52;
    @Column(name = "n_col53")
    private Integer col53;
    @Column(name = "n_col54")
    private Integer col54;
    @Column(name = "n_col55")
    private Integer col55;
    @Column(name = "n_col56")
    private Integer col56;
    @Column(name = "n_col57")
    private Integer col57;

    @Transient
    private String descriptionColumn;

    public ReportPeriodDetail(Integer columnNum, Integer value, String descriptionColumn) {
        this.columnNum = columnNum;
        this.value = value;
        this.descriptionColumn = descriptionColumn;
    }
}

