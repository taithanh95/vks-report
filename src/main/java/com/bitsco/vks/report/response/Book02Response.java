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
public class Book02Response extends ReportResponse {
    private String vuAnBiCan;
    private String toiDanh;
    private String quyetDinhChuyenVuAn;
    private String pheChuanQDKhoiToBiCan;
    private String pheChuanQDBoSungKhoiToBiCan;
    private String quyetDinhHuyBoKhoiToBiCan;
    private String yeuCauRaQuyetDinh;
    private String quyetDinhHuyBoQDKhongKhoiToVuAn;
    private String quyetDinhKhoiToVuAn;
    private String quyetDinhKhoiToBiCan;
    private String quyetDinhNhapTachVuAn;
    private String quyetDinhKhongGiaHanTGDieuTraVuAn;
    private String deNghiGiaHanTGDieuTraVuAn;
    private String yeuCauTruyNaBiCan;
    private String quyetDinhHuyBoQDTamDinhChiDieuTra;
    private String yeuCauPhucHoiDieuTraVuAn;
    private String quyetDinhPhucHoiDieuTraVuAn;
    private String quyetDinhPheChuanLenhKhamXet;
    private String quyetDinhPheChuanLenhThuGiuThuTin;
    private String quyetDinhKhamXet;
    private String quyetDinhThucNghiemDieuTra;
    private String quyetDinhDoiChat;
    private String thongBaoKhongChapNhanDeNghiTrungCauGiamDinh;
    private String quyetDinhTrungCauGiamDinh;
    private String yeuCauThongBaoKetluanGiamDinh;
    private String yeuCauCungCapTaiLieuLienQuan;
    private String vanBanKhac;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
