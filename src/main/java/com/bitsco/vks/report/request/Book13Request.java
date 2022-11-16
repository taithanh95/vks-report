package com.bitsco.vks.report.request;

import com.bitsco.vks.common.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book13Request extends ReportRequest {
    private String caseCode;
    private String caseName;
    private String accuCode;
    private String accuName;
    private String unitId;
    private String organIdDelivery;
    private String unitIdDelivery;
}

