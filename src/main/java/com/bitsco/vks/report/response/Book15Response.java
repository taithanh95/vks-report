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
public class Book15Response extends ReportResponse {
    private String ngayThangThuLy;
    private String vuAnBiCan;
    private String banAnSoTham;
    private String kiemSatVien;
    private String thamPhan;
    private String khangCaoPhucTham;
    private String khangNghiPhucTham;
    private String dinhChiXetXuPhucTham;
    private String duaVuAnRaXetXu;
    private String ngayXetXuPhucTham;
    private String tamHoanPhienToa;
    private String quanDiemCuaVKS;
    private String banAn;
    private String deNghiVKSCapTrenKhangNghiGDT;
    private String ghiChu;
}
