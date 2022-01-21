package hanji.selfhelp;


import hanji.selfhelp.domain.Diary;
import hanji.selfhelp.domain.DiaryItem;
import hanji.selfhelp.domain.Member;
import hanji.selfhelp.domain.Question;
import hanji.selfhelp.domain.security.ERole;
import hanji.selfhelp.domain.security.Role;
import hanji.selfhelp.dto.Diary.DiarySearchCondition;
import hanji.selfhelp.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final DiaryService diaryService;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit() {
            //role
            Role userRole = createRole(ERole.ROLE_USER);
            em.persist(userRole);
            Role moderatorRole = createRole(ERole.ROLE_MODERATOR);
            em.persist(moderatorRole);
            Role adminRole = createRole(ERole.ROLE_ADMIN);
            em.persist(adminRole);

            //member
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            Member member = createMember(
                    "test","test",roles);
            em.persist(member);

            //question
            Question question1 = createQuestion(
                    "행동","","오전" ,"");
            em.persist(question1);
            Question question2 = createQuestion(
                    "생각", "","잠들기전","");
            em.persist(question2);

            //diary

            //diary정보 생성
            DiaryItem diaryItem1 = DiaryItem.builder().question(question1).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem1);
            DiaryItem diaryItem2 = DiaryItem.builder().question(question2).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem2);
            DiaryItem diaryItem3 = DiaryItem.builder().question(question1).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem3);
            DiaryItem diaryItem4 = DiaryItem.builder().question(question2).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem4);
            DiaryItem diaryItem5 = DiaryItem.builder().question(question1).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem5);
            DiaryItem diaryItem6 = DiaryItem.builder().question(question2).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem6);
            DiaryItem diaryItem7 = DiaryItem.builder().question(question1).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem7);
            DiaryItem diaryItem8 = DiaryItem.builder().question(question2).regDate(LocalDateTime.now()).build();
            em.persist(diaryItem8);

            List<DiaryItem> diaryItemList1 = new ArrayList<>();
            diaryItemList1.add(diaryItem1);
            diaryItemList1.add(diaryItem2);
            List<DiaryItem> diaryItemList2 = new ArrayList<>();
            diaryItemList2.add(diaryItem3);
            diaryItemList2.add(diaryItem4);
            List<DiaryItem> diaryItemList3 = new ArrayList<>();
            diaryItemList3.add(diaryItem5);
            diaryItemList3.add(diaryItem6);
            List<DiaryItem> diaryItemList4 = new ArrayList<>();
            diaryItemList4.add(diaryItem7);
            diaryItemList4.add(diaryItem8);


            Diary diary1 = Diary.createDiary("첫번째다이어리", diaryItemList1,1,member);
            em.persist(diary1);
            Diary diary2 = Diary.createDiary("두번째다이어리", diaryItemList2,2,member);
            em.persist(diary2);
            Diary diary3 = Diary.createDiary("세번째다이어리", diaryItemList3,3,member);
            em.persist(diary3);
            Diary diary4 = Diary.createDiary("네번째다이어리", diaryItemList4,4,member);
            em.persist(diary4);
        }

        private Role createRole(ERole eRole) {
            return Role.builder()
                    .name(eRole)
                    .build();
        }

        private Member createMember(
                String username, String password, Set<Role> roles) {
            return Member.builder()
                    .username(username)
                    .password(password)
                    .roles(roles)
                    .build();
        }

        private Question createQuestion(
                String behavior, String type,
                String question, String description) {
            return Question.builder()
                    .behavior(behavior)
                    .type(type)
                    .question(question)
                    .description(description)
                    .build();
        }

/*        private Diary createDiary(
                String content, List<DiaryItem> diaryItemList,
                int score, Member member) {
            return Diary.builder()
                    .content(content)
                    .diaryItem(diaryItemList)
                    .score(score)
                    .member(member)
                    .build();
        }*/
    }
}
