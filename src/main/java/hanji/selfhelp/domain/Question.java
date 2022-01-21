package hanji.selfhelp.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    String behavior;
    String type;
    String question;
    String description;

    @OneToMany(mappedBy = "question")
    private List<DiaryItem> diaryItems = new ArrayList<>();

    @Builder
    public Question(String behavior, String type, String question, String description) {
        this.behavior = behavior;
        this.type = type;
        this.question = question;
        this.description = description;
    }
}
