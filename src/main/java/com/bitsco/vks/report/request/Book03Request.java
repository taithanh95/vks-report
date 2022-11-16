package com.bitsco.vks.report.request;

import com.bitsco.vks.common.request.ReportRequest;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book03Request extends ReportRequest {
    private String nguoiToCao;
    private String nguoiBiToCao;
    private List<String> danhSachMaQuyetDinh;
    private String maDonVi;

    public String chuyenDoiDanhSachMaQuyetDinh() {
        if (ArrayListCommon.isNullOrEmpty(danhSachMaQuyetDinh))
            return null;
        else {
            String rs = danhSachMaQuyetDinh.get(0);
            if (danhSachMaQuyetDinh.size() > 1)
                for (int i = 1; i < danhSachMaQuyetDinh.size(); i++) {
                    rs += ";" + danhSachMaQuyetDinh.get(i);
                }
            return rs;
        }
    }
}
