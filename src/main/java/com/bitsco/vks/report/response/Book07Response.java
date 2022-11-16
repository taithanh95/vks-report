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
public class Book07Response extends ReportResponse {
    private String vuAnBiCan;
    private String toiDanh;
    private String quyetDinhKhoiToVuAnBiCan;
    private String tomTatSuKienPhamToi;
    private String hoTenDTVThuLy;
    private String hoTenKSVThuLy;
    private String hoTenNguoiThamGiaBaoChua;
    private String yeuCauDieuTra;
    private String bienPhapNganChanApDung;
    private String quyetDinhTachNhapVuAn;
    private String quyetDinhChuyenVuAn;
    private String quyetDinhGiaHanThoiHanDieuTra;
    private String quyetDinhTamDinhChiDieuTra;
    private String ketLuanDieuTra;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
