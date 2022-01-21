package hanji.selfhelp.repository;

import hanji.selfhelp.domain.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository
        extends JpaRepository<Archive, Long> {}
