package hanji.selfhelp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hanji.selfhelp.domain.*;
import hanji.selfhelp.dto.Diary.DiaryDto;
import hanji.selfhelp.dto.Diary.DiaryFlatDto.DiaryFlatDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.DiaryListDto;
import hanji.selfhelp.dto.Diary.DiaryMemberDto;
import hanji.selfhelp.dto.Diary.DiarySearchCondition;
import hanji.selfhelp.repository.ArchiveRepository;
import hanji.selfhelp.repository.Diary.DiaryRepository;
import hanji.selfhelp.repository.MemberRepository;
import hanji.selfhelp.repository.QuestionRepository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final ArchiveRepository archiveRepository;

    //Create
    @Transactional
    public Diary createDiary() {
        //질문 조회, 글쓴이 조회
        Question question = questionRepository.getById(3L);

//        Authentication authentication = SecurityContextHolder
//                .getContext().getAuthentication();
//        UserDetailsImpl userDetails =
//                (UserDetailsImpl)authentication.getPrincipal();

        Member member = memberRepository.getById(1L);



        System.out.println(question.getQuestion());
        System.out.println(member.getUsername());

        //일기질문 생성
        DiaryItem diaryItem = new DiaryItem(question, LocalDateTime.now());

        //일기 생성
        Diary diary = Diary.createDiary("일기장",10,member,diaryItem);

        //일기 저장
        Diary savedDiary = diaryRepository.save(diary);

        return savedDiary;
    }

    //Read , Update
    public Diary findById(Long id) {
        return diaryRepository.findById(id).get();
    }

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }
    public List<DiaryListDto> findAllToDto() {
        return diaryRepository.findAllToDto();
    }
    public List<DiaryFlatDto> findDiariesOneQuery() {
        return diaryRepository.findDiariesOneQuery();
    }

    public List<DiaryMemberDto> search(DiarySearchCondition condition) {
        System.out.println("DiaryService search called...");
        return diaryRepository.search(condition);
    }

    //Delete
    public void deleteById(Long id) {
        diaryRepository.deleteById(id);
    }

    //soft delete - 리턴값은 삭제된 컬럼의 id로 하자
    @Transactional
    public Long deleteWithSaveToArchive(Long id) throws JsonProcessingException {
        //findDiary
        Diary diary = diaryRepository.findById(id).get();

        //archive table 에 저장
        //entity의 값은 payload에 jackson으로 sirialize해서 String으로 넣는다
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        DiaryDto diaryDto = new DiaryDto(diary);
        String payload = objectMapper.writeValueAsString(diaryDto);
        Archive archive = Archive.builder()
                .originalId(id)
                .tableName("Diary")
                .payload(payload)
                .createdDate(LocalDateTime.now())
                .build();
        System.out.println("archive save before");
        Archive save = archiveRepository.save(archive);
        System.out.println("save = " + save);
        System.out.println("archive save after");

        //delete
        System.out.println("diary delete before");
        diaryRepository.deleteById(id);
        System.out.println("diary delete after");
        return id;
    }
}



































