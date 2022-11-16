package com.bitsco.vks.report.request;

import com.bitsco.vks.common.request.ReportRequest;
import com.bitsco.vks.common.util.ArrayListCommon;
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
public class Book05Request extends ReportRequest {
    private String caseCode;
    private String caseName;
    private String accuCode;
    private String accuName;
    private String groupLawCode;
    private String lawId;
    private String item;
    private String point;
    private String unitId;
}
