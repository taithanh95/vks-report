package com.bitsco.vks.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 15/06/2022
 * Time: 9:21 AM
 */
@Data
@NoArgsConstructor
public class RequestReportDTO implements Serializable {
    private String fromDate;
    private String toDate;
    private String genId;
    private String sppId;
    private String reportType;
    private String reportTypeUrl;
    private String reportLevel;
}
