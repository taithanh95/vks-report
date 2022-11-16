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
public class Book22Response extends ReportResponse {
    private String hoTen;
    private String toiDanh;
    private String lenhTamGiam;
    private String lenhBatTamGiam;
    private String quyetDinhGiaHanTamGiam;
    private String tamGiamDieuTraBoSung;
    private String truyToLenhTamGiam;
    private String truyToLenhBatTamGiam;
    private String truyToGiaHanTamGiam;
    private String toaSoThamLenhTamGiam;
    private String toaSoThamLenhBatTamGiam;
    private String toaSoThamGiaHanTamGiam;
    private String toaPhucThamLenhTamGiam;
    private String toaPhucThamLenhBatTamGiam;
    private String quyetDinhThiHanhAn;
    private String chuyenNoiKhac;
    private String chuyenNoiKhacDen;
    private String nguoiBiKetAnTaiNgoai;
    private String quyetDinhThayThe;
    private String quyetDinhHuyBo;
    private String traTuDoQuyetDinhTamDinhChi;
    private String traTuDoKhangCao;
    private String hetThoiHanTu;
    private String quyetDinhTamDinhChiChapHanhAn;
    private String soThamTraTuDo;
    private String phucThamTraTuDo;
    private String vienKiemSatTraTuDo;
    private String daThiHanhAnTuHinh;
    private String quyetDinhBatBuocChuaBenh;
    private String chuyenThiHanhAn;
    private String ngayTron;
    private String quyetDinhTruyNa;
    private String ngayBatLai;
    private String quyetDinhXuLyKhiBatLai;
    private String viPhamKyLuat;
    private String quyetDinhXuLyViPham;
    private String chet;
    private String ghiChu;
    private String s_accucode;
}
