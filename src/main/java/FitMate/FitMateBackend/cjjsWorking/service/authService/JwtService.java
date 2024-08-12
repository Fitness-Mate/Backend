package FitMate.FitMateBackend.cjjsWorking.service.authService;

import FitMate.FitMateBackend.cjjsWorking.service.RedisCacheService;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.errorcodes.JwtFilterErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.common.exception.exceptions.JwtFilterException;
import FitMate.FitMateBackend.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtService {
    private static String secretKey;
    private static Long accessExp;
    private static Long refreshExp;
    private static String issuer;

    private final RedisCacheService redisCacheService;
    private final UserRepository userRepository;

    @Value("${jwt-access-expiration}")
    public void setAccessExp(Long ep) {
        this.accessExp = ep;
    }
    @Value("${jwt-refresh-expiration}")
    public void setRefreshExp(Long ep) {
        this.refreshExp = ep;
    }
    @Value("${jwt-secret-key}")
    public void setSecretKey(String sk){
        this.secretKey = sk;
    }
    @Value("${jwt-issuer}")
    public void setIssuer(String iss) {
        this.issuer = iss;
    }

    public String generateAccessToken(UserDetails userDetails, ExtraClaims claims) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", claims.getUserId());

        return generateAccessToken(extraClaims, userDetails);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * accessExp))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessTokenWithRefreshToken(String refreshToken) {
        if(!redisCacheService.isExist(refreshToken)) {
            throw new JwtFilterException(JwtFilterErrorCode.EXPIRED_REFRESH_TOKEN_EXCEPTION);
        }

        User user = userRepository.findByLoginEmail(getLoginEmail(refreshToken)).orElse(null);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND_EXCEPTION);
        }

        return generateAccessToken(user, new ExtraClaims(user));
    }

    public String generateRefreshToken(UserDetails userDetails, boolean rememberMe) {
        long exp = rememberMe ? refreshExp : accessExp;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * exp))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getToken(HttpHeaders header) {
        return Objects.requireNonNull(header.getFirst("authorization")).substring("Bearer ".length());
    }

    public static String getLoginEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public static Long getUserId(String token) {
        return getClaim(token, claims -> claims.get("userId", Long.class));
    }

    public static <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            throw new JwtFilterException(JwtFilterErrorCode.EXPIRED_ACCESS_TOKEN_EXCEPTION);
        } catch(UnsupportedJwtException e) {
            throw new JwtFilterException(JwtFilterErrorCode.UNSUPPORTED_JWT_EXCEPTION);
        } catch(MalformedJwtException e) {
            throw new JwtFilterException(JwtFilterErrorCode.MALFORMED_JWT_EXCEPTION);
        } catch(SignatureException e) {
            throw new JwtFilterException(JwtFilterErrorCode.SIGNATURE_EXCEPTION);
        } catch(IllegalArgumentException e) {
            throw new JwtFilterException(JwtFilterErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String loginId = getLoginEmail(token);
        return (loginId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
