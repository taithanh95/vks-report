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
public class Book29Response extends ReportResponse {
    private String hoTen;
    private String toiDanh;
    private String mucAn;
    private String quyetDinhThiHanhAn;
    private String quyetDinhHoanThiHanhAn;
    private String quyetDinhTamDinhChiThiHanhAn;
    private String quyetDinhHuyQDTamDinhChi;
    private String quyetDinhDinhChiThiHanhAn;
    private String quyetDinhCuaToaAn;
    private String chet;
    private String tron;
    private String ngayDiThiHanhAn;
    private String ghiChu;
    private String s_accucode;
}
