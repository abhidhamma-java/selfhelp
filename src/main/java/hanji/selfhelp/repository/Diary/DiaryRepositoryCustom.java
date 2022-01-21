package hanji.selfhelp.repository.Diary;

import hanji.selfhelp.domain.Diary;
import hanji.selfhelp.dto.Diary.DiaryDto;
import hanji.selfhelp.dto.Diary.DiaryFlatDto.DiaryFlatDto;
import hanji.selfhelp.dto.Diary.DiaryListDto.DiaryListDto;
import hanji.selfhelp.dto.Diary.DiaryMemberDto;
import hanji.selfhelp.dto.Diary.DiarySearchCondition;

import java.util.List;

public interface DiaryRepositoryCustom {
    List<DiaryMemberDto> search(DiarySearchCondition diarySearchCondition);
    List<DiaryListDto> findAllToDto();
    List<DiaryFlatDto> findDiariesOneQuery();
}
