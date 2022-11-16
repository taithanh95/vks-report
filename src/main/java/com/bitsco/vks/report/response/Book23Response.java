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
public class Book23Response extends ReportResponse {
    private String hoTen;
    private String banAnCoHieuLucPhapLuat;
    private String toiDanh;
    private String hinhPhat;
    private String quyetDinhUyThacDiNoiKhacRaQDThiHanhAn;
    private String nhanUyThacTuNoiKhacDeRaQDThiHanhAn;
    private String quyetDinhThiHanhAn;
    private String banAnGDTTTTuyenHuyAn;
    private String quyetDinhHoanChapHanhAn;
    private String quyetDinhTamDinhChiChapHanhAn;
    private String quyetDinhDinhChiChapHanhAn;
    private String quyetDinhMienChapHanhHinhPhat;
    private String quyetDinhHuongThoiHieu;
    private String quyetDinhApDungBienPhapBatBuocChuaBenh;
    private String chet;
    private String phamToiMoi;
    private String quyetDinhTruyNa;
    private String daThiHanhAn;
    private String ghiChu;
    private String s_accucode;
}
