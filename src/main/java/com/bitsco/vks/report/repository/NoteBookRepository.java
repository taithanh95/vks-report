package com.bitsco.vks.report.repository;

import com.bitsco.vks.report.entities.NoteBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteBookRepository extends JpaRepository<NoteBook, Long> {
    NoteBook findByDenouncementIdAndBookCode(Long denouncementId, String bookCode);
    NoteBook findByAccucodeAndBookCode(String accucode, String bookCode);
    NoteBook findByCasecodeAndRegicodeAndBookCode(String casecode, String regicode, String bookCode);
    NoteBook findByArresteeIdAndBookCode(Long arresteeId, String bookCode);
    NoteBook findByViolationIdAndBookCode(Long violationId, String bookCode);
    NoteBook findByCompensationIdAndBookCode(Long compensationId, String bookCode);

}
