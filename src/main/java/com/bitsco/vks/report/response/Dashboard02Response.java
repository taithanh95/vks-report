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
public class Dashboard02Response extends ReportResponse {
    //Đầu ra phần tổng hợp
    private Integer soCapLenhVuAn;
    private Integer soCapLenhBiCan;
    private Integer soCapLenhTinBao;
    //Đầu ra phần chi tiết kiết xuất
    private String giaiDoan;
    private String soQuyetDinh;
    private String quyetDinh;
    private String maBiCan;
    private String tenBiCan;
    private String maVuAn;
    private String tenVuAn;
    private String biCanDauVu;
    private String maTinBao;
    private String nguoiBaoTin;
    private String ngayCapSoLenh;
    private String hieuLucTuNgay;
    private String hieuLucDenNgay;
    private String donViCapSL;
}
