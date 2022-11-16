package com.bitsco.vks.report.request;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Ba Tuan Anh <anhnbt.it@gmail.com>
 * Date: 8/9/2021
 * Time: 11:22 AM
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book19Request extends ReportRequest {
    // Đơn vị (3)
    private String sppId;

    // Kết quả xử lý (4)
    private BigInteger resultCode;

    // Người YC bồi thường (5)
    private String claimantName;

    // Người xử lý đơn YC (6)
    private String resultHandler;

    // Người thiệt hại (7)
    private String damagesName;

    // Địa chỉ người YC (8)
    private String claimantAddress;

    // Địa chỉ người thiệt hại (9)
    private String damagesAddress;

    // Số xử lý (10)
    private BigInteger resultNumber;

    // Ngày xử lý từ (11)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromResultDate;

    // Ngày xử lý đến (12)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toResultDate;

    // Ngày bổ sung tài liệu từ (13)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDocumentDate;

    // Ngày bổ sung tài liệu đến (14)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toDocumentDate;
}
