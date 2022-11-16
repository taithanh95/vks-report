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
public class Book01Response extends ReportResponse {
    private String vuAnBiCan;
    private String toiDanh;
    private String quyetDinhPhanCongPVT;
    private String quyetDinhPhanCongKSV;
    private String yeuCauThayDoiThuTruongPhoThuTruong;
    private String yeuCauThayDoiDieuTraVien;
    private String yeuCauQuyetDinhThayDoiNguoiPhienDich;
    private String thongBaoNguoiBaoChua;
    private String yeuCauThayDoiNguoiBaoChua;
    private String thongBaoTuChoiDangKyNguoiBaoChua;
    private String quyetDinhThayDoiNguoiGiamDinh;
    private String quyetDinhThamGiaToTung;
    private String vanBanKhac;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
