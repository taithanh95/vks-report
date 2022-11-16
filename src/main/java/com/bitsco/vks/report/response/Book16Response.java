package com.bitsco.vks.report.response;

import com.bitsco.vks.common.response.ReportResponse;
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
public class Book16Response extends ReportResponse {
    private String s_column_001;
    private String s_column_002;
    private String s_column_003;
    private String s_column_004;
    private String s_column_005;
    private String s_column_006;
    private String s_column_007;
    private String s_column_008;
    private String s_column_009;
    private String s_column_010;
    private String s_column_011;
    private String s_casecode;
    private String s_accucode;
}
