package com.bitsco.vks.report.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = ReportConstant.TABLE.REPORT_PERIOD_INPUT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportPeriodInput {
    @Column(name = "n_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ReportConstant.SEQUENCE.SQ_REPORT_PERIOD_INPUT)
    @SequenceGenerator(name = ReportConstant.SEQUENCE.SQ_REPORT_PERIOD_INPUT, sequenceName = ReportConstant.SEQUENCE.SQ_REPORT_PERIOD_INPUT, allocationSize = 1)
    private Long id;

    @Column(name = "n_request_report_id")
    private Long requestReportId;
    @Column(name = "d_from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDate;
    @Column(name = "d_to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date toDate;
    @Column(name = "d_created_at")
    //Ngày lấy báo cáo
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @Column(name = "s_paper_type")
    //Khổ giấy in
    private String paperType;
    @Column(name = "s_created_by")
    //Người lập
    private String createdBy;
    @Column(name = "s_position")
    //Chức vụ
    private String position;
    @Column(name = "s_signature")
    //Ký tên
    private String signature;
    @Column(name = "s_spp_id", length = 4000)
    //Danh sách đơn vị thống kê báo cáo
    private String sppId;

    //option lựa chọn đơn vị xem báo cáo
    // "1" Đơn vị vks hiện tại
    // "2" Đơn vị vks trực thuộc
    // "3" Đơn vị vks hiện tại và trực thuộc
    // "4" tùy chọn đơn vị vks
    @Column(name = "s_option")
    private String option;

    //Tên VKS đăng nhập
    @Column(name = "s_sppname")
    private String sppname;

    //Thêm các field cho các biểu báo cáo cũ (4,10,11,12)
    @Column(name = "s_codeid")
    private String codeId;

    @Column(name = "s_groupid")
    private String groupId;

    @Column(name = "s_lawid")
    private String lawId;
    //End

    @Transient
    private String name;

    @Column(name = "s_leader")
    //Lãnh đạo
    private String leader;

    public ReportPeriodInput coppyFrom(ReportPeriodInput r) {
        if (r.getFromDate() != null)
            this.setFromDate(r.getFromDate());
        if (r.getToDate() != null)
            this.setToDate(r.getToDate());
        if (r.getCreatedAt() != null)
            this.setCreatedAt(r.getCreatedAt());
        if (!StringCommon.isNullOrBlank(r.getCreatedBy()))
            this.setCreatedBy(r.getCreatedBy());
        if (!StringCommon.isNullOrBlank(r.getPosition()))
            this.setPosition(r.getPosition());
        if (!StringCommon.isNullOrBlank(r.getPaperType()))
            this.setPaperType(r.getPaperType());
        if (!StringCommon.isNullOrBlank(r.getSignature()))
            this.setSignature(r.getSignature());
        if (!StringCommon.isNullOrBlank(r.getSppId()))
            this.setSppId(r.getSppId());
        if (!StringCommon.isNullOrBlank(r.getLeader()))
            this.setLeader(r.getLeader());
        return this;
    }

    @Transient
    private List<String> sppIdList;

    public void setSppIdList(List<String> sppIdList) {
        this.sppIdList = sppIdList;
        if (!ArrayListCommon.isNullOrEmpty(sppIdList)) {
            String rs = sppIdList.get(0);
            if (sppIdList.size() > 1)
                for (int i = 1; i < sppIdList.size(); i++) {
                    rs += ";" + sppIdList.get(i);
                }
            this.sppId = rs;
        }
    }
}
