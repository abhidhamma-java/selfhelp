package hanji.selfhelp.dto.Member;

import hanji.selfhelp.domain.Member;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String email;
    private String username;
    private String avatar;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.avatar = member.getAvatar();
    }
}
