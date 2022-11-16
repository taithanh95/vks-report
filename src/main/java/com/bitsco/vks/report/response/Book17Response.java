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
public class Book17Response extends ReportResponse {
    private String s_column_2;
    private String s_column_3;
    private String s_column_4;
    private String s_column_5;
    private String s_column_6;
    public String s_casecode;
    public String s_regicode;
}
