package hanji.selfhelp.service;

import hanji.selfhelp.domain.Member;
import hanji.selfhelp.domain.Question;
import hanji.selfhelp.repository.MemberRepository;
import hanji.selfhelp.repository.QuestionRepository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }
}
