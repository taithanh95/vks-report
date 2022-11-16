package com.bitsco.vks.report.service;

import com.bitsco.vks.report.dto.RequestReportDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 07/07/2022
 * Time: 8:30 AM
 */
public interface AsyncRequestReportService {
    void requestReport(RequestReportDTO req) throws Exception;
}
