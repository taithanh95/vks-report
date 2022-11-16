package com.bitsco.vks.report.request;

import com.bitsco.vks.common.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 31/10/2022
 * Time: 9:39 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book33Request extends ReportRequest {
    private String requestAgency;
    private String requestUnit;
    private String relatedObjects;
    private String groupLawCode;
    private String lawId;
    private String item;
    private String point;
    private String unitId;
}
