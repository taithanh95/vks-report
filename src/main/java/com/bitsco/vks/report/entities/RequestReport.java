package com.bitsco.vks.report.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.report.constant.ReportConstant;
import com.bitsco.vks.report.request.RequestReport02;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = ReportConstant.TABLE.REQUEST_REPORT)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestReport {
    @Column(name = "n_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ReportConstant.SEQUENCE.SQ_REQUEST_REPORT)
    @SequenceGenerator(name = ReportConstant.SEQUENCE.SQ_REQUEST_REPORT, sequenceName = ReportConstant.SEQUENCE.SQ_REQUEST_REPORT, allocationSize = 1)
    private Long id;

    @Column(name = "s_report_code")
    private String reportCode;
    @Column(name = "n_status")
    //Trạng thái yêu cầu
    // 0 - Yêu cầu bị xóa khi chưa thực thi hoặc thực thi lỗi,
    // 1 - Yêu cầu được tiếp nhận chờ thực thi,
    // 2 - YC đang được thực thi,
    // 3 - YC được thực thi thành công. Có kết quả,
    // 4 - YC được thực thi không thành công, có lỗi.
    private Integer status;
    @Column(name = "s_description")
    //Mô tả kết quả thực thi. Thành công hoặc báo lỗi chi tiết
    private String description;
    @Column(name = "d_begin_at")
    //Thời gian tiến trình ngầm bắt đầu thực thi báo cáo
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date beginAt;
    @Column(name = "d_end_at")
    //Thời gian tiến trình ngầm kết thúc thực thi báo cáo
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date endAt;
    @Column(name = "d_created_at")
    //Thời gian tiếp nhận yêu cầu
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @Column(name = "d_updated_at")
    //Thời gian cập nhật cuối cùng của yêu cầu
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    @Column(name = "s_created_by")
    //Người tạo yêu cầu báo cáo
    private String createdBy;
    @Column(name = "s_updated_by")
    //Người cập nhật cuối cùng
    private String updatedBy;

    @Column(name = "s_genid")
    private String genId;

    @Column(name = "s_report_type")
    private String reportType;

    @Column(name = "s_report_level")
    private String reportLevel;

    @Column(name = "s_spp_id")
    private String sppId;

    @Column(name = "s_from_date")
    private String fromDate;

    @Column(name = "s_to_date")
    private String toDate;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    @Transient
    private ReportInput reportInput;

    @Transient
    private RequestReport02 requestReport02;

    @Transient
    private String date;
}
