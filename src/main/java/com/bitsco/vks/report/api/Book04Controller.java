package com.bitsco.vks.report.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.report.request.Book04Request;
import com.bitsco.vks.report.service.Book04Service;
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
@RequestMapping(value = "book04")
public class Book04Controller {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Autowired
    Book04Service book04Service;

    @Operation(description = "Truy vấn dữ liệu báo cáo của sổ 04")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReport/")
    public ResponseEntity<?> requestReport(@Valid @NotNull @RequestBody Book04Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, book04Service.querying(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book04/requestReport/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Download file in pdf của sổ 04")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReportPdf/")
    public ResponseEntity<?> requestReportPdf(@Valid @NotNull @RequestBody Book04Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), book04Service.requestReportPdf(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book03/requestReportPdf/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Download file in excel của sổ 04")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReportExcel/")
    public ResponseEntity<?> requestReportExcel(@Valid @NotNull @RequestBody Book04Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), book04Service.requestReportXlsx(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book03/requestReportExcel/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Download file in docx của sổ 04")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "0008", description = "Dữ liệu đầu vào không đúng định dạng", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/requestReportDocx/")
    public ResponseEntity<?> requestReportDocx(@Valid @NotNull @RequestBody Book04Request request) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, Response.SUCCESS.getResponseMessage(), book04Service.requestReportDocx(request)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /book03/requestReportExcel/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
