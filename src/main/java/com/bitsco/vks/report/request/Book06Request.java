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
public class Book06Request extends ReportRequest {
    private String caseCode;
    private String caseName;
    private String accuCode;
    private String accuName;
    private String groupLawCode;
    private String lawId;
    private String item;
    private String point;
    private List<String> decisionIdList;
    private String unitId;

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
