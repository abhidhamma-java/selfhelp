package hanji.selfhelp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanji.selfhelp.domain.Diary;
import hanji.selfhelp.domain.DiaryItem;
import hanji.selfhelp.domain.Member;
import hanji.selfhelp.domain.Question;
import hanji.selfhelp.dto.Diary.*;
import hanji.selfhelp.dto.Diary.DiaryFlatDto.DiaryFlatDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.DiaryItemListDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.DiaryListDto;
import hanji.selfhelp.etc.security.services.UserDetailsImpl;
import hanji.selfhelp.service.DiaryService;
import hanji.selfhelp.service.MemberService;
import hanji.selfhelp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final QuestionService questionService;
    private final MemberService memberService;

    //Create
    @PostMapping("/")
    public ResponseEntity<?> createDiary(
//            DiaryRequest diaryRequest
    ) {
        System.out.println("createDiary called...");

        //        Authentication authentication = SecurityContextHolder
//                .getContext().getAuthentication();
//        UserDetailsImpl userDetails =
//                (UserDetailsImpl)authentication.getPrincipal();

        //json을 바로 만들필요는 없다
        //그냥 여기서 만들어보자

        //일기 저장
        Diary savedDiary = diaryService.createDiary();

        //DTO로 반환
        DiaryDto diaryDto = new DiaryDto(savedDiary);

//        Diary diary = Diary.builder()
//                .content(diaryRequest.getContent())
//                .score(diaryRequest.getScore())
//                .member(diaryRequest.getMember())
//                .diaryItems(diaryRequest.getDiaryItem())
//                .post(diaryRequest.getPost())
//                .build();
//
//        diaryService.save(diary);
        return ResponseEntity.ok(diaryDto);
    }

    //Read
/*    @GetMapping("/")
    public ResponseEntity<?> searchDiary(HttpServletRequest request) {
        System.out.println("searchDiary called...");

        DiarySearchCondition diarySearchCondition = new DiarySearchCondition();

        System.out.println("before");
        List<DiaryMemberDto> diaryMemberDtoList = diaryService.search(diarySearchCondition);
        System.out.println("diaryMemberDtoList = " + diaryMemberDtoList);
        return ResponseEntity.ok(diaryMemberDtoList);
    }*/
    @GetMapping("/")
    public ResponseEntity<?> readDiaries(HttpServletRequest request) {

        //dto에서 get 하는과정에서 쿼리가 10번넘게 나간다
        //fetch join으로 최적화 할 수 있다
//        List<Diary> diaries = diaryService.findAll();

        //여기서도 set하는 과정에서 member를 조회하기위해 쿼리가 나간다
//        List<DiaryDto> diaryDtos = diaries.stream().map(diary-> new DiaryDto(diary)).collect(Collectors.toList());

        //쿼리가 두번으로 줄었다!!
//        List<DiaryListDto> diaries = diaryService.findAllToDto();

        List<DiaryFlatDto> diaries = diaryService.findDiariesOneQuery();


        return ResponseEntity.ok(diaries);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readDiary(@PathVariable String id) {
        return ResponseEntity.ok(new DiaryResponse());
    }


    //Update
    @PatchMapping("/")
    public ResponseEntity<?> updateDiary() {
        return ResponseEntity.ok(new DiaryResponse());
    }

    //Delete
    @DeleteMapping("/")
    public ResponseEntity<?> deleteDiary() throws JsonProcessingException {
        Long deletedDiaryId = diaryService.deleteWithSaveToArchive(12L);
        return ResponseEntity.ok(deletedDiaryId);
    }
}































