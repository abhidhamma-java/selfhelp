package hanji.selfhelp.dto.Diary.DiaryFlatDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiaryFlatDto {
    //diary
    private Long diaryId;
    private String content;
    private int score;

    //member
    private Long memberId;
    private String username;

    //diaryItem
    private Long diaryItemId;
    private LocalDateTime regDate;

    //question
    private Long questionId;
    private String behavior;
    private String type;
    private String question;
    private String description;

    @QueryProjection
    public DiaryFlatDto(
            Long diaryId, String content, int score,
            Long memberId, String username, Long diaryItemId, LocalDateTime regDate,
            Long questionId, String behavior, String type, String question, String description) {
        this.diaryId = diaryId;
        this.content = content;
        this.score = score;
        this.memberId = memberId;
        this.username = username;
        this.diaryItemId = diaryItemId;
        this.regDate = regDate;
        this.questionId = questionId;
        this.behavior = behavior;
        this.type = type;
        this.question = question;
        this.description = description;
    }
}
