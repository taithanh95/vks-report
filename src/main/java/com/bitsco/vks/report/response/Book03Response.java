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
public class Book03Response extends ReportResponse {
    private String ngayVksTiepNhan;
    private String noiDungToGiac;
    private String canBoTiepNhan;
    private String donViTiepNhan;
    private String maDonVi;
    private String phieuChuyenTin;
    private String maLoaiTin;
    private String ghiChu;
    private String nguoiToCao;
    private String nguoiBiToCao;
    private String ketQuaVks;
    private String maQuyetDinh;
    private Long denouncementId;
}
