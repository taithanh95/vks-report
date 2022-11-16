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
public class Book06Response extends ReportResponse {
    private String vuAnBiCan;
    private String quyetDinhLenhBatGiuTHKhanCap;
    private String lenhBatGiuTHKhanCap;
    private String quyetDinhKhongPheChuanGiaHanTamGiu;
    private String quyetDinhPheChuanGiaHanTamGiuL1L2;
    private String quyetDinhHuyBoLenhTamGiu;
    private String quyetDinhTraTuDo;
    private String quyetDinhPheChuanLenhBatBiCan;
    private String quyetDinhKhongPheChuanLenhBatBiCan;
    private String quyetDinhPheChuanLenhTamGiam;
    private String quyetDinhKhongPheChuanLenhTamGiam;
    private String yeuCauApDungBienPhapTamGiamBiCan;
    private String lenhBatBiCanDeTamGiam;
    private String quyetDinhGiaHanThoiHanTamGiamL1L2L3;
    private String quyetDinhGiaHanThoiHanTamGiamDacBiet;
    private String quyetDinhHuyBoBienPhapTamGiam;
    private String quyetDinhThayTheBienPhapNganChan;
    private String quyetDinhBienPhapBaoLinh;
    private String quyetDinhBaoLinh;
    private String quyetDinhPCKPCDatTienDeBaoDam;
    private String quyetDinhDatTienDeBaoDam;
    private String lenhCamDiKhoiNoiCuTru;
    private String thongBaoApDungBienPhapCamDiKhoiNoiCuTru;
    private String quyetDinhHuyBoBienPhapCamDiKhoiNoiCuTru;
    private String quyetDinhTamHoanXuatCanh;
    private String quyetDinhHuyBoTamHoanXuatCanh;
    private String lenhTamGiamDeTruyTo;
    private String lenhBatTamGiamBiCanDeTruyTo;
    private String quyetDinhGiaHanThoiHanTamGiamDeTruyTo;
    private String quyetDinhApGiaiBiCan;
    private String quyetDinhDanGiai;
    private String lenhKeBienTaiSan;
    private String lenhKhamXet;
    private String quyetDinhHuyBoKeBienTaiSan;
    private String lenhPhongToaTaiKhoan;
    private String quyetDinhHuyBoPhongToaTaiKhoan;
    private String ghiChu;
    private String s_casecode;
    private String s_accucode;
}
