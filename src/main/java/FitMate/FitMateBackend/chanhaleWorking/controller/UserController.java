package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.dto.UserArgResolverDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.UserDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.form.user.DeleteUserForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdatePasswordForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.common.auth.AuthResponse;
import FitMate.FitMateBackend.common.configuration.argumentresolver.Login;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * User 생성, 수정 삭제에 관한 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@ResponseBody
public class UserController {
    private final UserService userService;
    private static final RestTemplate restTemplate = new RestTemplate();

//    @PostMapping
//    public String register(@RequestBody RegisterForm registerForm) {
//        log.info("REGISTER [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
//        String errMsg = registerForm.validateFields();
//        if (!errMsg.equals("ok"))
//            return errMsg;
//        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
//            return "아이디 중복";
//        userService.register(registerForm);
//        return "ok";
//    }

    @GetMapping("/private")
    public UserDto getMUser(@Login UserArgResolverDto loginUser) {
        if (loginUser == null) {
            return new UserDto();
        } else
            return UserDto.createUserDto(userService.getUserWithId(loginUser.getUserId()));
    }

    @PostMapping("/admin/register")
    public String adminRegister(@RequestBody RegisterForm registerForm) {
        log.info("REGISTER [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
        String errMsg = registerForm.validateFields();
        if (errMsg != "ok")
            return errMsg;
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return "아이디 중복";
        userService.registerAdmin(registerForm);
        return "ok";
    }

    @PostMapping("/private")
    public String updateUser(@Login UserArgResolverDto loginUser, @RequestBody UpdateUserForm form) {
        log.info(loginUser.getLoginEmail());
        if (form.getUserName().length()>8 || form.getUserName().length()<3) {
            return "너무 짧은 유저명: 3자리 이상, 8자리 이하";
        }
        userService.updateUser(loginUser.getUserId(), form);
        return "ok";
    }

    @PostMapping("/private/password")
    public String updateUserPassword(@Login UserArgResolverDto loginUser, @RequestBody UpdatePasswordForm form) {
        log.info("old={}, new={}", form.getOldPassword(), form.getNewPassword());
        User user = userService.getUserWithId(loginUser.getUserId());
        if (userService.checkPassword(loginUser.getUserId(), form.getOldPassword())) {
            userService.updateUserPassword(loginUser.getUserId(), form.getNewPassword());
            return "ok";
        }
        return "fail";
    }

    @PostMapping("/private/delete")
    public String deleteUser(@Login UserArgResolverDto loginUser, @RequestBody DeleteUserForm form) {
        log.info(form.getPassword());
        if (loginUser != null) {
            User user = userService.getUserWithId(loginUser.getUserId());
            if (userService.checkPassword(loginUser.getUserId(), form.getPassword())) {
                userService.deleteUser(loginUser.getUserId());
                return "ok";
            }
        }
        return "fail";
    }

    @PostMapping("/auth/verify/email/{userEmail}")
    public String verifyUserId(@PathVariable("userEmail")String userEmail) {
        String regexPattern = "^(.+)@(\\S+)$";
        if (userService.checkDuplicatedLoginEmail(userEmail)) {
            return "아이디 중복";
        } else if (!userEmail.matches(regexPattern)){
            return "형식에 맞지 않는 이메일 주소";
        }
        return "ok";
    }

    //🔽🔽🔽 Jwt 🔽🔽🔽
    /**
     * add Jwt user and admin register (OK)
     * - User Controller
     * - UserService
     *
     * add jwt login, logout (OK)
     * - LoginController
     * - LoginService
     * - UserRepository
     *
     * update user domain (OK)
     * - User
     * */

    @PostMapping("/auth")
    public ResponseEntity<?> userRegisterWithJwt(@RequestBody RegisterForm registerForm) {
        log.info(registerForm.getLoginEmail());
        log.info("REGISTER Customer [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
        String errMsg = registerForm.validateFields();
        if (!errMsg.equals("ok"))
            return ResponseEntity.status(400).body(errMsg); // errMsg 참고
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return ResponseEntity.status(400).body("아이디 중복"); // 아이디 중복
        return ResponseEntity.ok(userService.registerWithJwt(registerForm, "Customer"));
    }
    @PostMapping("/auth/withuuid")
    public ResponseEntity<?> userRegisterWithJwtAndUuid(@RequestBody RegisterForm registerForm) {
        log.info(registerForm.getLoginEmail());
        log.info("REGISTER Customer [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
        String errMsg = registerForm.validateFields();
        if (!errMsg.equals("ok"))
            return ResponseEntity.status(400).body(errMsg); // errMsg 참고
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return ResponseEntity.status(400).body("아이디 중복"); // 아이디 중복

        // 메일 인증 uuid 체크 관련 기능
        if (registerForm.getUuid() == null) {
            log.info("uuid 비어있음");
            return ResponseEntity.status(400).body("blank uuid");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));
        HttpEntity<UuidVerifyingRequestDto> httpEntity = new HttpEntity<>(new UuidVerifyingRequestDto(registerForm.getLoginEmail(), registerForm.getUuid()), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/register/verify/uuid"), httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200) || !responseEntity.getBody().equals("ok")) {
            log.info("error status code responded for /verify/uuid request [{}]",responseEntity.getStatusCode());
            return ResponseEntity.status(400).body(responseEntity.getBody());
        }
        //

        return ResponseEntity.ok(userService.registerWithJwt(registerForm, "Customer"));
    }

    @PostMapping("/auth/jwt/admin/register")
    public ResponseEntity<AuthResponse> adminRegisterWithJwt(@RequestBody RegisterForm registerForm) {
        log.info(registerForm.getLoginEmail());
        log.info("REGISTER Admin [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
        String errMsg = registerForm.validateFields();
        log.info(errMsg);
        if (!errMsg.equals("ok"))
            return ResponseEntity.status(400).body(null); // errMsg 참고
        log.info(errMsg);
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return ResponseEntity.status(400).body(null); // 아이디 중복
        return ResponseEntity.ok(userService.registerWithJwt(registerForm, "Admin"));
    }
}
