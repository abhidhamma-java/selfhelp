package hanji.selfhelp.dto.DiaryItem;

import com.querydsl.core.annotations.QueryProjection;
import hanji.selfhelp.domain.Diary;
import hanji.selfhelp.domain.DiaryItem;
import hanji.selfhelp.domain.Question;
import hanji.selfhelp.dto.Question.QuestionDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiaryItemDto {
    private Long id;
    private QuestionDto question;
    private LocalDateTime regDate;

    @QueryProjection
    public DiaryItemDto(DiaryItem diaryItem) {
        this.id = diaryItem.getId();
        this.question = new QuestionDto(diaryItem.getQuestion());
        this.regDate = diaryItem.getRegDate();
    }
}
