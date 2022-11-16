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
public class Dashboard04Response extends ReportResponse {
    //Đầu ra phần tổng hợp
    private String giaiDoan;
    private Integer soVuAn;
    private Integer soBiCan;
    //Đầu ra phần chi tiết kiết xuất
    private String maGiaiDoan;
    private String maVuAn;
    private String tenVuAn;
    private String tenBiCanDauVu;
    private String quyetDinhKhoiToVuAn;
    private String ngayQuyetDinh;
    private String coQuanRaQuyetDinh;
    private String loaiToiPham;
    private String dieuLuatChinh;
    private String ngayXayRa;

    public Dashboard04Response(String giaiDoan, Integer soVuAn, Integer soBiCan) {
        this.giaiDoan = giaiDoan;
        this.soVuAn = soVuAn;
        this.soBiCan = soBiCan;
    }
}
