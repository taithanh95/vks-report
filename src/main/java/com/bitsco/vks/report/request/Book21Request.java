package com.bitsco.vks.report.request;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.request.ReportRequest;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book21Request extends ReportRequest {
    private String arrestName;
    private String arrestType;
    private List<String> decisionIdList;
    private String unitId;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDateDecision;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toDateDecision;

    public String getDecisionId() {
        if (ArrayListCommon.isNullOrEmpty(decisionIdList))
            return null;
        else {
            String rs = decisionIdList.get(0);
            if (decisionIdList.size() > 1)
                for (int i = 1; i < decisionIdList.size(); i++) {
                    rs += ";" + decisionIdList.get(i);
                }
            return rs;
        }
    }
}
