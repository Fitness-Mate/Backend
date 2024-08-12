package FitMate.FitMateBackend.cjjsWorking.config.securityFilter;

import FitMate.FitMateBackend.common.exception.exceptions.JwtFilterException;
import FitMate.FitMateBackend.common.exception.response.JwtFilterErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtFilterException e) {
            log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getJwtFilterErrorCode(),
                    request.getRequestURI(), e.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), new JwtFilterErrorResponse(e.getJwtFilterErrorCode(), e.getMessage()));
        }
    }
}
