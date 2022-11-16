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
public class Dashboard03Response extends ReportResponse {
    //Đầu ra phần tổng hợp
    private String ngay;
    private Integer soTamGiam;
    private Integer soTamGiu;
    //Đầu ra phần chi tiết kiết xuất
    private String maTamGiamTamGiu;
    private String donViBatGiu;
    private String loaiTamGiamTamGiu;
    private String nguoiBiTamGiamTamGiu;
    private String ngayTamGiamTamGiu;
    private String lyDoTamGiamTamGiu;
    private String kiemSatVienThuLy;
    private String thongTinXuLy;
    private String quyetDinhXuLy;
}
