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
public class Book27Response extends ReportResponse {
    private String tenDiaChi;
    private String banAnCoHieuLucPhapLuat;
    private String hinhPhat;
    private String quyetDinhThiHanhAn;
    private String quyetDinhCuongCheThiHanhAn;
    private String giayChungNhanDaChapHanhXongHinhPhat;
    private String quyetDinhThiHanh;
    private String quyetDinhCuongCheThiHanh;
    private String giayChungNhanDaChapHanhXongBienPhapTuPhap;
    private String ghiChu;
    private String s_accucode;
}
