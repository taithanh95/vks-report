package com.bitsco.vks.report.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.report.request.Book36Request;
import com.bitsco.vks.report.service.Book36Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "book36")
public class Book36Controller {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);
    @Autowired
    Book36Service book36Service;

    @Operation(description = "Truy vấn dữ liệu báo cáo của sổ 36")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReport/")
    public ResponseEntity<?> requestReport(@Valid @NotNull @RequestBody Book36Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, book36Service.querying(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book36/requestReport/ ", e);
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Download file in pdf của sổ 15")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReportPdf/")
    public ResponseEntity<?> requestReportPdf(@Valid @NotNull @RequestBody Book36Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), book36Service.requestReportPdf(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book36/requestReportPdf/ ", e);
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Download file in excel của sổ 15")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReportExcel/")
    public ResponseEntity<?> requestReportExcel(@Valid @NotNull @RequestBody Book36Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), book36Service.requestReportXlsx(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book36/requestReportExcel/ ", e);
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Download file in docx của sổ 15")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReportDocx/")
    public ResponseEntity<?> requestReportDocx(@Valid @NotNull @RequestBody Book36Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), book36Service.requestReportDocx(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book36/requestReportDocx/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
