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
public class Book24Response extends ReportResponse {
    private String hoTen;
    private String toiDanh;
    private String banAnTuyenPhatTuHinh;
    private String banAnTuyenBangHinhPhatKhac;
    private String quyetDinhAnGiam;
    private String quyetDinhThiHanhAn;
    private String daThiHanh;
    private String tron;
    private String chet;
    private String ghiChu;
    private String s_accucode;
}
