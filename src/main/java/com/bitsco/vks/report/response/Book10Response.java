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
public class Book10Response extends ReportResponse {
    private String vuAnBiCan;
    private String toiDanh;
    private String kiemSatVienThuLy;
    private String dieuTraVienThuLy;
    private String thamPhanThuLy;
    private String quyetDinhTraHoSo;
    private String lyDoTraHoSo;
    private String ngayGiaoNhanHoSo;
    private String dinhChiDieuTraVuAnBiCan;
    private String tamDinhChiDieuTraVuAnBiCan;
    private String giuNguyenQDDeNghiTruyTo;
    private String thayDoiQuanDiemDeNghiTruyTo;
    private String vksGiuNguyenCaoTrangTruyTo;
    private String vksTraHoSoChoCQDT;
    private String tamDinhChiVuAnBiCan;
    private String dinhChiVuAnBiCan;
    private String thayDoiCaoTrang;
    private String giuNguyenCaoTrang;
    private String xuLyKhac;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
