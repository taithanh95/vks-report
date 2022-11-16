package com.bitsco.vks.report.response;

import com.bitsco.vks.common.response.ReportResponse;
import com.bitsco.vks.report.entities.ReportDetail;
import com.bitsco.vks.report.entities.ReportInput;
import com.bitsco.vks.report.entities.RequestReport;
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
public class RequestReportResponse extends ReportResponse {
    private List<ReportDetail> reportDetails;
    private RequestReport requestReport;
    private ReportInput reportInput;
}
