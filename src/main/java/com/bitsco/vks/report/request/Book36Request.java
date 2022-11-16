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
public class Book36Request extends ReportRequest {
    private String requestAgency;
    private String requestUnit;
    private String relatedObjects;
    private String groupLawCode;
    private String lawId;
    private String item;
    private String point;
    private String unitId;
}
