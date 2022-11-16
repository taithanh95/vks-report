package com.bitsco.vks.report.request;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book17Request extends ReportRequest {
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date judgmentFromDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date judgmentToDate;
    private String caseCode;
    private String caseName;
    private String accuCode;
    private String accuName;
    private String unitId;
    private String judgmentNum;
}
