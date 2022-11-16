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
public class Book35Response extends ReportResponse {
    private String s_column_2;
    private String s_column_3;
    private String s_column_4;
    private String s_column_5;
    private String s_column_6;
    private String s_column_7;
    private String s_column_8;
    private String s_column_9;
    private String s_column_10;
    private String s_column_11;
    private String s_column_12;
}
