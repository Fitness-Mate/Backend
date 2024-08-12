package FitMate.FitMateBackend.chanhaleWorking.config.argumentresolver;

import FitMate.FitMateBackend.chanhaleWorking.dto.UserArgResolverDto;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // Login 애노테이션이 파라미터에 있는가
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // User 클래스가 맴버의 타입인가?
        boolean hasMemberType = UserArgResolverDto.class.isAssignableFrom(parameter.getParameterType());
        // 두개 다 만족하면 resolveArgument 실행
        return hasMemberType && hasLoginAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("authorization").substring("Bearer ".length());
        if (token == "") {
            log.info("noheader!!!");
            return null;
        }
        Long id = JwtService.getUserId(token);
        String email = JwtService.getLoginEmail(token);
        log.info("login Arg resolver id: [{}], email: [{}]", id, email);
        return new UserArgResolverDto(id, email);
    }
}
