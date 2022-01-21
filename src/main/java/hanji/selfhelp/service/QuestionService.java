package hanji.selfhelp.service;

import hanji.selfhelp.domain.Question;
import hanji.selfhelp.repository.QuestionRepository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question findById(Long id) {
        return questionRepository.findById(id).get();
    }
}
