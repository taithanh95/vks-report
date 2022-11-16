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
public class Book26Response extends ReportResponse {
    private String hoTen;
    private String banAnToiDanh;
    private String hinhPhatMucAn;
    private String noiKhacChuyenDen;
    private String chuyenDiNoiKhac;
    private String quyetDinhThiHanhAn;
    private String daThiHanhAn;
    private String coQuanDuocGiaoGiamSatGiaoDuc;
    private String phamToiMoi;
    private String viPhamNghiaVu;
    private String quyetDinhXuLyViPham;
    private String banAnGDTTuyenHuyHinhPhat;
    private String quyetDinhMienChapHanhAn;
    private String quyetDinhGiamThoiHanChapHanhAn;
    private String daChapHanhXong;
    private String chet;
    private String ghiChu;
    private String s_accucode;
}
