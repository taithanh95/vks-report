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
public class Book12Response extends ReportResponse {
    private String vuAnBiCan;
    private String toiDanh;
    private String quyetDinhPhanCongKSV;
    private String quyetDinhTachNhapVuAn;
    private String quyetDinhChuyenThuLyVuAn;
    private String quyetDinhGiaHanThoiGianTruyTo;
    private String quyetDinhTraHoSoVuAn;
    private String quyetDinhTamDinhChiDieuTraVuAn;
    private String quyetDinhDinhChiDieuTraVuAn;
    private String quyetDinhHuyBoDinhChiDieuTraVuAn;
    private String quyetDinhPhucHoiDieuTraVuAn;
    private String quyetDinhXuLyVatChungTaiSanTKBiPhongToa;
    private String quyetDinhChuyenVatChung;
    private String caoTrangQDTruyTo;
    private String quyetDinhRutQDTruyTo;
    private String quyetDinhHuyQDRutQDTruyTo;
    private String vanBanDeNghiTraHoSo;
    private String quyetDinhPhanCongKSVTaiPhienToaSoTham;
    private String quyetDinhPhanCongVKSCapDuoiTHQCT;
    private String quyetDinhBietPhaiKSVTHQCT;
    private String vanBanTBVuAnCoBiCanBiTamGiam;
    private String vanBanTBTruyToBiCanTruocToa;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
