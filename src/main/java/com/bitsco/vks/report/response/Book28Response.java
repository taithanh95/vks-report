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
public class Book28Response extends ReportResponse {
    private String hoTen;
    private String banAn;
    private String hinhPhat;
    private String ngayThiHanhAn;
    private String coQuanGiamSatGiaoDuc;
    private String viPhamNghiaVu;
    private String quyetDinhXuLyViPham;
    private String chet;
    private String giayChungNhanChapHanhXongHinhPhat;
    private String ghiChu;
    private String s_accucode;
}
