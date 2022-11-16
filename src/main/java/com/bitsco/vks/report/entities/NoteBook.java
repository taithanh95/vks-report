package com.bitsco.vks.report.entities;

import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.report.constant.ReportConstant;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = ReportConstant.TABLE.NOTEBOOKS)
public class NoteBook {
    @Column(name = "n_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ReportConstant.SEQUENCE.SQ_NOTE_BOOK)
    @SequenceGenerator(name = ReportConstant.SEQUENCE.SQ_NOTE_BOOK, sequenceName = ReportConstant.SEQUENCE.SQ_NOTE_BOOK, allocationSize = 1)
    private Long id;
    @Column(name = "n_denouncement_id")
    private Long denouncementId;
    @Column(name = "s_note", length = 200)
    private String note;
    @Column(name = "s_book_code", columnDefinition = "CHAR(2)")
    private String bookCode;
    @Column(name = "s_accucode")
    private String accucode;
    @Column(name = "s_casecode")
    private String casecode;
    @Column(name = "s_regicode")
    private String regicode;
    @Column(name = "n_arrestee_id")
    private Long arresteeId;
    @Column(name = "n_violation_id")
    private Long violationId;
    @Column(name = "n_compensation_id")
    private Long compensationId;

    public NoteBook coppyFrom(NoteBook note) {
        this.setNote(note.getNote());
        return this;
    }
}
