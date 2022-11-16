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
public class Book08Response extends ReportResponse {
    private String vuAnBiCan;
    private String toiDanh;
    private String quyetDinhKhoiToVuAn;
    private String quyetDinhKhoiToBiCan;
    private String kiemSatVienDTVThamPhanThuLy;
    private String quyetDinhTamDinhChi;
    private String lyDoTamDinhChi;
    private String quyetDinhTruyNa;
    private String tacDongCuaVKS;
    private String quyetDinhHuyBoQDTamDinhChi;
    private String quyetDinhPhucHoi;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
