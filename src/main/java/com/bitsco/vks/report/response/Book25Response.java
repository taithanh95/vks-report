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
public class Book25Response extends ReportResponse {
    private String hoTen;
    private String banAnToiDanh;
    private String mucAn;
    private String tamGiam;
    private String quyetDinhThiHanhAn;
    private String quyetDinhDuaNguoiChapHanhAnDenTraiGiam;
    private String chuyenDiNoiKhac;
    private String noiKhacChuyenDen;
    private String quyetDinhGiamThoiHanChapHanhAn;
    private String quyetDinhTamDinhChiChapHanhAn;
    private String quyetDinhApDungBienPhapBatBuocChuaBenh;
    private String quyetDinhDinhChiChapHanhAn;
    private String banAnGDTTuyenHuyHinhPhatTu;
    private String quyetDinhTraTuDo;
    private String quyetDinhMienChapHanhAn;
    private String quyetDinhDacXa;
    private String quyetDinhThaTuTruocThoiHan;
    private String daChapHanhXong;
    private String chet;
    private String ngayTron;
    private String quyetDinhTruyNa;
    private String batLai;
    private String quyetDinhXuLyKhiBatLai;
    private String viPhamBiKyLuat;
    private String quyetDinhXuLyViPham;
    private String ghiChu;
    private String s_accucode;
}
