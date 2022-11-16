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
public class Book13Response extends ReportResponse {
    public String ngayThangNamGiaoNhan;
    public String vuAnBiCan;
    public String vatChung;
    public String lyDoChuyen;
    public String benGiao;
    public String benNhan;
    public String ghiChu;
    public String s_casecode;
    public String s_regicode;
}
