package hanji.selfhelp.dto.Diary;

import com.querydsl.core.annotations.QueryProjection;
import hanji.selfhelp.domain.Diary;
import hanji.selfhelp.domain.DiaryItem;
import hanji.selfhelp.domain.Member;
import hanji.selfhelp.domain.Post;
import hanji.selfhelp.dto.DiaryItem.DiaryItemDto;
import hanji.selfhelp.dto.Member.MemberDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DiaryDto {
    private Long id;
    private String content;
    private int score;
    private MemberDto member;
    private List<DiaryItemDto> diaryItems;
    private Post post;

    @QueryProjection
    public DiaryDto(Diary diary) {
        this.content = diary.getContent();
        this.score = diary.getScore();
        this.member = new MemberDto(diary.getMember());
        this.diaryItems = diary.getDiaryItems().stream()
                .map(diaryItem -> new DiaryItemDto(diaryItem))
                .collect(Collectors.toList());
    }

    @QueryProjection
    public DiaryDto(String content, int score, Member member,
                    List<DiaryItem> diaryItems) {
        this.content = content;
        this.score = score;
        this.member = new MemberDto(member);
        this.diaryItems = diaryItems.stream()
                .map(diaryItem -> new DiaryItemDto(diaryItem))
                .collect(Collectors.toList());
    }

    @QueryProjection
    public DiaryDto(Long id, String content, int score) {
        this.id = id;
        this.content = content;
        this.score = score;
    }
}
