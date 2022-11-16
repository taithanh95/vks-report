package com.bitsco.vks.report.service;

import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.dto.RequestReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 07/07/2022
 * Time: 8:31 AM
 */
@Service
@Transactional
public class AsyncRequestReportServiceImpl implements AsyncRequestReportService {
    @Autowired
    RequestReportService requestReportService;

    @Override
    @Async
    public void requestReport(RequestReportDTO req) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://10.47.102.53:6011/VKSTool/%s?fromDate=%s&toDate=%s&sppID=%s&reportLevel=%s&isHeader=ALL&reportType=%s&reportID=%s",
                req.getReportTypeUrl(), req.getFromDate(), req.getToDate(), req.getSppId(), req.getReportLevel(), req.getReportType(), req.getGenId());
        ResponseEntity<ResponseBody> response =
                new ResponseEntity<>(new ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), restTemplate.getForEntity(url, String.class)), HttpStatus.OK);
        if (response.getStatusCodeValue() == ReportConstant.REQUEST_REPORT.STATUS_RESPONSE.OKE &&
                ReportConstant.REQUEST_REPORT.STATUS_RESPONSE.SUCCESS.equals(response.getBody().getResponseCode())) {
            requestReportService.changeStatus(req.getGenId(), ReportConstant.REQUEST_REPORT.STATUS.SUCCESS);
        } else {
            requestReportService.changeStatus(req.getGenId(), ReportConstant.REQUEST_REPORT.STATUS.ERROR);
        }
    }
}
