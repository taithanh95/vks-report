package com.bitsco.vks.report.request;

import com.bitsco.vks.common.request.ReportRequest;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 06/01/2022
 * Time: 3:48 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book18Request extends ReportRequest {
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

