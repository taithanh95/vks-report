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
public class Book09Response extends ReportResponse {
    private String vuAnBiCan;
    private String quyetDinhKhoiToVuAn;
    private String quyetDinhKhoiToBiCan;
    private String toiDanh;
    private String kiemSatVienDTVThamPhanThuLy;
    private String quyetDinhDinhChi;
    private String lyDoDinhChi;
    private String quyetDinhXuLyVatChung;
    private String tacDongCuaVKS;
    private String ketLuanCuaVKS;
    private String quyetDinhHuyBoQDDinhChi;
    private String quyetDinhPhucHoi;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
