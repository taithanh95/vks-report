package com.bitsco.vks.report.response;

import com.bitsco.vks.common.response.ReportResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book04Response extends ReportResponse {
    private String ngayVksThuLy;
    private String nguoiToCao;
    private String noiDung;
    private String nguoiBiToCao;
    private String kiemSatVienThuLy;
    private String yeuCauCuaVienKiemSat;
    private String quyetDinhGiaHanThoiGianGiaiQuyet;
    private String quyetDinhTamDinhChi;
    private String quyetDinhHuyBoTamDinhChi;
    private String quyetDinhPhucHoiGiaiQuyet;
    private String quyetDinhGiaiQuyetTranhChapVeThamQuyen;
    private String thongBaoGiaiQuyetToGiacTinBao;
    private String yeuCauVuAnHinhSu;
    private String ketQuaThucHienYcVks;
    private String ketQuaGiaiQuyet;
    private String ghiChu;
    private Long denouncementId;
}
