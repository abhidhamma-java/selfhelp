package hanji.selfhelp.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Archive {
    @Id
    @GeneratedValue
    @Column(name = "archive_id")
    private Long id;

    private Long originalId;
    private String tableName;

    @Column(length = 10000)
    private String payload;
    private LocalDateTime createdDate;

    @Builder
    public Archive(
            Long id, Long originalId, String tableName,
            String payload, LocalDateTime createdDate) {
        this.id = id;
        this.originalId = originalId;
        this.tableName = tableName;
        this.payload = payload;
        this.createdDate = createdDate;
    }
}
