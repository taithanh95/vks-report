package com.bitsco.vks.report.entities;

import com.bitsco.vks.report.constant.ReportConstant;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 15/03/2022
 * Time: 1:30 PM
 */

@Data
@Entity
@Table(name = ReportConstant.TABLE.RP_BOOK_07)
public class RP_BOOK_07 {
    @Column(name = "n_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ReportConstant.SEQUENCE.SQ_BOOK_07)
    @SequenceGenerator(name = ReportConstant.SEQUENCE.SQ_BOOK_07, sequenceName = ReportConstant.SEQUENCE.SQ_BOOK_07, allocationSize = 1)
    private Long id;
    @Column(name = "S_COLUMN_002", length = 2000)
    private String s_column_002;
    @Column(name = "S_COLUMN_003", length = 2000)
    private String s_column_003;
    @Column(name = "S_COLUMN_004", length = 2000)
    private String s_column_004;
    @Column(name = "S_COLUMN_005", length = 2000)
    private String s_column_005;
    @Column(name = "S_COLUMN_006", length = 2000)
    private String s_column_006;
    @Column(name = "S_COLUMN_007", length = 2000)
    private String s_column_007;
    @Column(name = "S_COLUMN_008", length = 2000)
    private String s_column_008;
    @Column(name = "S_COLUMN_009", length = 2000)
    private String s_column_009;
    @Column(name = "S_COLUMN_010", length = 2000)
    private String s_column_010;
    @Column(name = "S_COLUMN_011", length = 2000)
    private String s_column_011;
    @Column(name = "S_COLUMN_012", length = 2000)
    private String s_column_012;
    @Column(name = "S_COLUMN_013", length = 2000)
    private String s_column_013;
    @Column(name = "S_COLUMN_014", length = 2000)
    private String s_column_014;
    @Column(name = "S_COLUMN_015", length = 2000)
    private String s_column_015;
    @Column(name = "S_COLUMN_016", length = 2000)
    private String s_column_016;
}
