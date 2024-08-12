package FitMate.FitMateBackend.chanhaleWorking.controller.admin;

import FitMate.FitMateBackend.chanhaleWorking.form.login.LoginForm;
import FitMate.FitMateBackend.chanhaleWorking.service.LoginService;
import FitMate.FitMateBackend.common.constraint.SessionConst;
import FitMate.FitMateBackend.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Admin 페이지의 Login 기능을 처리하는 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@ResponseBody
public class AdminLoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletRequest request){

        log.info("ADMIN login attempt [{}]",loginForm.getLoginEmail());
        User loginAdmin = loginService.adminLogin(loginForm.getLoginEmail(), loginForm.getPassword());
        if(loginAdmin == null){
            return "fail";//"로그인 실패. 아이디와 패스워드를 확인해주세요.";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_ADMIN, loginAdmin);
        // admin은 user의 권한도 가지고 있음
        session.setAttribute(SessionConst.LOGIN_USER, loginAdmin);
        log.info("ADMIN login success [{}]",loginForm.getLoginEmail());

        return "ok";
    }

    @PutMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

    }
}
