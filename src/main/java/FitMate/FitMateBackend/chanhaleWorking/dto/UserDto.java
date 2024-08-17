package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.user.entity.User;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 요청에 대한 dto
 * 개인정보 수정에서 활용
 */
@NoArgsConstructor
@Getter
public class UserDto {
    private String userName;
    private String loginEmail;
    private String sex;
    private LocalDate birthDate;

    //    private Float height;
//    private Float weight;
    public static UserDto createUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.userName = user.getUserName();
        userDto.loginEmail = user.getLoginEmail();
        userDto.sex = user.getSex();
        userDto.birthDate = user.getBirthDate();
        return userDto;
    }
}
