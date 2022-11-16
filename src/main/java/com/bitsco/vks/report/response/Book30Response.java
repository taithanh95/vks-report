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
public class Book30Response extends ReportResponse {
    private String hoTen;
    private String toiDanhHinhPhat;
    private String thoiGianDaChapHanhAnPhat;
    private String quyetDinhThaTuTruocThoiHan;
    private String chuyenDiNoiKhac;
    private String noiKhacChuyenDen;
    private String coQuanDuocGiaoNhiemVuQuanLy;
    private String quyetDinhRutNganTGThuThach;
    private String quyetDinhHuyQDThaTuTruocThoiHan;
    private String chet;
    private String quyetDinhDinhChiThiHanhAn;
    private String giayChungNhanChapHanhXongHinhPhat;
    private String ghiChu;
    private String s_accucode;
}
