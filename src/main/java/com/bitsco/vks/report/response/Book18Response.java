package com.bitsco.vks.report.response;

import com.bitsco.vks.common.response.ReportResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 06/01/2022
 * Time: 3:50 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book18Response extends ReportResponse {
    private String s_column_001;
    private String s_column_002;
    private String s_column_003;
    private String s_column_004;
    private String s_column_005;
    private String s_column_006;
    private String s_column_007;
    private String s_accucode;
}
