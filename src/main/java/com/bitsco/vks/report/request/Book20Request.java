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
public class Book20Request extends ReportRequest {
    // Đơn vị (3)
    private String sppId;

    // Người YC bồi thường (4)
    private String claimantName;

    // Người thiệt hại (5)
    private String damagesName;

    // Số QĐ/ bản án XĐ bị oan (6)
    private BigInteger decisionCompensationNumber;

    // Ngày ra QĐ/ bản án XĐ bị oan từ (7)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDecisionCompensationDate;

    // Ngày ra QĐ/ bản án XĐ bị oan đến (8)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toDecisionCompensationDate;

    // Số QĐ giải quyết bồi thường (9)
    private BigInteger decisionEnforcementNumber;

    // Ngày QĐ giải quyết bồi thường từ (10)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDecisionEnforcementDate;

    // Ngày QĐ giải quyết bồi thường đến (11)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toDecisionEnforcementDate;

    // Số bản án giải quyết bồi thường (12)
    private BigInteger judgmentCompensationNumber;
}
