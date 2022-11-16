package com.bitsco.vks.report.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.report.entities.NoteBook;
import com.bitsco.vks.report.repository.NoteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

@Service
public class NoteBookServiceImpl implements NoteBookService {

    @Autowired
    private NoteBookRepository repository;

    @Transactional
    @Override
    public NoteBook createOrUpdate(NoteBook noteBook) {
        ValidateCommon.validateNullObject(noteBook.getBookCode(), "Mã sổ");
        NoteBook note = new NoteBook();
        if (!StringCommon.isNullOrBlank(noteBook.getNote()) && noteBook.getNote().length() > 200) {
            throw new CommonException(Response.REQUIRED_MAX_LENGTH, "Ghi chú vượt quá 200 ký tự");
        }
        if (noteBook.getDenouncementId() != null) {
             note = repository.findByDenouncementIdAndBookCode(noteBook.getDenouncementId(), noteBook.getBookCode());
        } else if (noteBook.getAccucode() != null) {
             note = repository.findByAccucodeAndBookCode(noteBook.getAccucode(), noteBook.getBookCode());
        } else if (noteBook.getCasecode() != null) {
            note = repository.findByCasecodeAndRegicodeAndBookCode(noteBook.getCasecode(), noteBook.getRegicode(), noteBook.getBookCode());
        } else if (noteBook.getArresteeId() != null) {
            note = repository.findByArresteeIdAndBookCode(noteBook.getArresteeId(), noteBook.getBookCode());
        } else if (noteBook.getViolationId() != null) {
            note = repository.findByViolationIdAndBookCode(noteBook.getViolationId(), noteBook.getBookCode());
        } else if (noteBook.getCompensationId() != null) {
            note = repository.findByCompensationIdAndBookCode(noteBook.getCompensationId(), noteBook.getBookCode());
        }

        if (note == null) {
            return saveNote(noteBook);
        } else return saveNote(note.coppyFrom(noteBook));
    }

    private NoteBook saveNote(NoteBook note) {
        return repository.save(note);
    }
}
