package hanji.selfhelp.dto.Diary.DiaryListDto;

import com.querydsl.core.annotations.QueryProjection;
import hanji.selfhelp.dto.DiaryItem.DiaryItemDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiaryListDto {
    private Long diaryId;
    private String content;
    private int score;

    private Long memberId;
    private String username;

    private LocalDateTime regDate;

    private List<DiaryItemListDto> diaryItems;

    @QueryProjection
    public DiaryListDto(
            Long diaryId, String content, int score,
            Long memberId, String username, LocalDateTime regDate
    ) {
        //diary
        this.diaryId = diaryId;
        this.content = content;
        this.score = score;

        //member
        this.memberId = memberId;
        this.username = username;

        //diaryItem
        this.regDate = regDate;
    }
}
