package hanji.selfhelp.dto.Question;

import hanji.selfhelp.domain.Question;
import lombok.Data;

@Data
public class QuestionDto {

    Long id;
    String behavior;
    String type;
    String question;
    String description;

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.behavior = question.getBehavior();
        this.type = question.getType();
        this.question = question.getQuestion();
        this.description = question.getDescription();
    }
}
