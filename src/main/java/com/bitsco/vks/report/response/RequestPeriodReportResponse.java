package com.bitsco.vks.report.response;

import com.bitsco.vks.common.response.ReportResponse;
import com.bitsco.vks.report.entities.ReportPeriodDetail;
import com.bitsco.vks.report.entities.ReportPeriodInput;
import com.bitsco.vks.report.entities.RequestPeriodReport;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPeriodReportResponse extends ReportResponse {
    private List<ReportPeriodDetail> reportDetails;
    private RequestPeriodReport requestReport;
    private ReportPeriodInput reportInput;
}
