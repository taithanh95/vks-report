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
public class Book21Response extends ReportResponse {
    private String hoTen;
    private String ngayBatTamGiu;
    private String cacTruongHopBat;
    private String batKhongCoCanCu;
    private String quyetDinhTamGiu;
    private String lyDoTamGiu;
    private String quyetDinhGiaHanTamGiuLan1;
    private String quyetDinhGiaHanTamGiuLan2;
    private String quyetDinhPheChuanQDGiaHanTamGiu;
    private String quyetDinhHuyBoBienPhapTamGiu;
    private String chuyenDiNoiKhac;
    private String noiKhacChuyenDen;
    private String quyetDinhADBPNCKhac;
    private String lenhTamGiam;
    private String quyetDinhTraTuDo;
    private String quyetDinhTraTuDoCuaVKS;
    private String tron;
    private String quyetDinhTraTruyNa;
    private String ngayBatLai;
    private String quyetDinhXuLyKhiBatLai;
    private String ngayViPham;
    private String quyetDinhXuLyViPham;
    private String chet;
    private String ghiChu;
    private String hinhThucXuLy;
    private Long n_arrestee_id;
}
