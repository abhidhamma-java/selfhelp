package hanji.selfhelp.dto.Diary;

import com.querydsl.core.annotations.QueryProjection;
import hanji.selfhelp.domain.DiaryItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DiaryMemberDto {
    //member
    private Long memberId;
    private String username;

    //diary
    private Long diaryId;
    private String content;
    private int score;

    @QueryProjection
    public DiaryMemberDto(
            Long memberId, String username,
            Long diaryId, String content, int score) {
        this.memberId = memberId;
        this.username = username;
        this.diaryId = diaryId;
        this.content = content;
        this.score = score;
    }
}
