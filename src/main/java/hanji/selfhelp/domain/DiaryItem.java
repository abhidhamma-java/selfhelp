package hanji.selfhelp.domain;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryItem {
    @Id @GeneratedValue
    @Column(name = "diary_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private LocalDateTime regDate;

    @Builder
    public DiaryItem(Question question,LocalDateTime regDate) {
        this.question = question;
        this.regDate = regDate;
    }
}
