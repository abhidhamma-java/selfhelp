package hanji.selfhelp.repository.QuestionRepository;

import hanji.selfhelp.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
