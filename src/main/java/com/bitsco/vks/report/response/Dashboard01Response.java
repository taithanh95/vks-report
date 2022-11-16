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
public class Dashboard01Response extends ReportResponse {
    //Đầu ra phần tổng hợp
    private Integer soTbtgChuaThucHien;
    private Integer soTbtgDangGiaiQuyet;
    private Integer soTbtgDaGiaiQuyet;
    private Integer soTbtgTamDinhChi;
    private Integer soTbtgDaQuaHan;
    //Đầu ra phần chi tiết kiết xuất
    private String maTinBao;
    private String ngayTiepNhan;
    private String thoiHanGiaiQuyet;
    private String nguoiToCao;
    private String nguoiBiToCao;
    private String dieuTraVienThuLy;
    private String kiemSatVienThuLy;
    private String trangThai;
    private String ketQuaGiaiQuyet;
    private String donViTiepNhan;
}
