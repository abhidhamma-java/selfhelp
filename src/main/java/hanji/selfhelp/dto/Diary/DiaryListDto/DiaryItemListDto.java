package hanji.selfhelp.dto.Diary.DiaryListDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class DiaryItemListDto {
    private Long diaryId;
    private Long diaryItemId;
    private Long questionId;
    private String behavior;
    private String type;
    private String question;
    private String description;

    @QueryProjection
    public DiaryItemListDto(
            Long diaryId, Long diaryItemId, Long questionId,
            String behavior, String type, String question,
            String description
    ) {
        this.diaryId = diaryId;
        this.diaryItemId = diaryItemId;
        this.questionId = questionId;
        this.behavior = behavior;
        this.type = type;
        this.question = question;
        this.description = description;
    }
}
