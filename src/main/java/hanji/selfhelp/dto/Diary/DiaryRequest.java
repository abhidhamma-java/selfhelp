package hanji.selfhelp.dto.Diary;

import hanji.selfhelp.domain.DiaryItem;
import hanji.selfhelp.domain.Member;
import hanji.selfhelp.domain.Post;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class DiaryRequest {
    @Length(max = 100)
    private String content;
    @NumberFormat
    private int score;
    @NotBlank
    private Member member;
    @NotBlank
    private List<DiaryItem> diaryItem;
    private Post post;
}
















































