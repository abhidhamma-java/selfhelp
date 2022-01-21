package hanji.selfhelp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Diary {
    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    String content;
    int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryItem> diaryItems = new ArrayList<>();

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Diary(String content, int score,
                 Member member, List<DiaryItem> diaryItems, Post post) {
        this.content = content;
        this.score = score;
        this.member = member;
        this.diaryItems = diaryItems;
        this.post = post;
    }

    //==연관관계 메서드==//
    public void addDiaryItem(DiaryItem diaryItem) {
        diaryItems.add(diaryItem);
        diaryItem.setDiary(this);
    }

    //==생성 메서드==//
    public static Diary createDiary(
            String content, List<DiaryItem> diaryItems,int score,
            Member member) {
        Diary diary = new Diary();
        diary.setContent(content);
        diary.setMember(member);
        diary.setScore(score);
        for(DiaryItem diaryItem : diaryItems) {
            diary.addDiaryItem(diaryItem);
        }
        return diary;
    }

    public static Diary createDiary(
            String content, int score,
            Member member, DiaryItem... diaryItems) {
        Diary diary = new Diary();
        diary.setContent(content);
        diary.setMember(member);
        diary.setScore(score);
        for(DiaryItem diaryItem : diaryItems) {
            diary.addDiaryItem(diaryItem);
        }
        return diary;
    }
}

























