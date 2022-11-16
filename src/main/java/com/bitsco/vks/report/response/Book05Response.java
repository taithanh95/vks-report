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
public class Book05Response extends ReportResponse {
    private String ngayThangThucHien;
    private String vuAnBiCan;
    private String khamNghiemHienTruong;
    private String khamNghiemTuThi;
    private String khamXet;
    private String thucNghiemDieuTra;
    private String nhanDang;
    private String doiChat;
    private String nhanBietGiongNoi;
    private String ghiChu;
    private Long denouncementId;
    private String s_casecode;
}
