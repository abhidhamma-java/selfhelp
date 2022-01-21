package hanji.selfhelp.repository.Diary;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanji.selfhelp.domain.*;
import hanji.selfhelp.dto.Diary.*;
import hanji.selfhelp.dto.Diary.DiaryFlatDto.DiaryFlatDto;
import hanji.selfhelp.dto.Diary.DiaryFlatDto.QDiaryFlatDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.DiaryItemListDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.DiaryListDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.QDiaryItemListDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.QDiaryListDto;
import hanji.selfhelp.dto.DiaryItem.DiaryItemDto;
import hanji.selfhelp.dto.DiaryItem.QDiaryItemDto;
import hanji.selfhelp.dto.Member.MemberDto;

import static hanji.selfhelp.domain.QDiaryItem.diaryItem;
import static hanji.selfhelp.domain.QPost.post;
import static hanji.selfhelp.domain.QQuestion.question1;
import static org.springframework.util.StringUtils.isEmpty;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static hanji.selfhelp.domain.QDiary.diary;
import static hanji.selfhelp.domain.QMember.member;

public class DiaryRepositoryImpl implements DiaryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DiaryMemberDto> search(DiarySearchCondition condition) {
        System.out.println("DiaryRepositoryImpl search called...");
//        return queryFactory
//                .selectFrom(diary)
//                .fetch();
        return queryFactory
                .select(new QDiaryMemberDto(
                        member.id,
                        member.username,
                        diary.id,
                        diary.content,
                        diary.score
                ))
                .from(diary)
                .leftJoin(diary.member, member)
                .where(
                        usernameLike(condition.getUsername()),
                        contentLike(condition.getContent()))
                .fetch();
    }

    private BooleanExpression usernameLike(String username) {
        return isEmpty(username) ? null : member.username.like(username);
    }

    private BooleanExpression contentLike(String content) {
        return isEmpty(content) ? null : diary.content.like(content);
    }


/*    @Override
    public List<DiaryDto> findAllToDto() {
        System.out.println("DiaryRepositoryImpl findAllToDto called...");

        List<DiaryDto> fetch = queryFactory
                .select(new QDiaryDto(
                        diary.content,
                        diary.score,
                        diary.member,
                        diary.diaryItems))
                .from(diary)
                .leftJoin(diary.member, member)
                .leftJoin(diary.diaryItems, diaryItem)
//                .join(diaryItem.question, question1)
                .fetch();
        return fetch;
    }*/

    /**
     * 최적화
     * Query: 루트 1번, 컬렉션 2번(member, question)
     */
    @Override
    public List<DiaryListDto> findAllToDto() {
        System.out.println("DiaryRepositoryImpl findAllToDto called...");

        List<DiaryListDto> result = findDiaries();

        result.forEach(d -> {
            List<DiaryItemListDto> diaryItems = findDiaryItem(d.getDiaryId());
            d.setDiaryItems(diaryItems);
        });

        return result;
    }

    /**
     * 1:N관계(컬렉션)을 제외한 나머지를 한번에 조회
     * 가져올 수 있는 컬럼
     * diary.id, diary.content, diary.score,
     * member.id, member.name, diaryItem.regDate
     */
    private List<DiaryListDto> findDiaries() {
        return queryFactory
                .select(new QDiaryListDto(diary.id, diary.content, diary.score, member.id,member.username,diaryItem.regDate))
                .from(diary)
                .join(diary.member, member)
                .join(diary.diaryItems, diaryItem)
                .fetch();
    }

    /**
     * 1:N 관계인 DiaryItems, Question 조회
     */
    private List<DiaryItemListDto> findDiaryItem(Long diaryId) {
        return queryFactory
                .select(new QDiaryItemListDto(
                        diaryItem.diary.id, diaryItem.id, question1.id,
                        question1.behavior,question1.type,
                        question1.question,question1.description))
                .from(diaryItem)
                .join(diaryItem.question, question1)
                .where(diaryIdEq(diaryId))
                .fetch();
    }

    //flat 쿼리
    public List<DiaryFlatDto> findDiariesOneQuery() {
        return queryFactory
            .select(new QDiaryFlatDto(
                        diary.id, diary.content, diary.score,
                        member.id, member.username,
                        diaryItem.id, diaryItem.regDate,
                        question1.id, question1.behavior, question1.type, question1.question, question1.description))
                .from(diary)
                .leftJoin(diary.member, member)
                .leftJoin(diary.diaryItems, diaryItem)
                .leftJoin(diaryItem.question, question1)
                .fetch();
    }

    private BooleanExpression diaryIdEq(Long diaryId) {
        return diaryId == null ? null : diary.id.eq(diaryId); }


}
