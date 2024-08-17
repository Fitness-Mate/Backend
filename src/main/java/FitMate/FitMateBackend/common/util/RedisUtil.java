package FitMate.FitMateBackend.common.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisUtil {

    private final RedisTemplate<String, String> userRedisTemplate;
    @Value("${jwt-access-expiration}") private Long accessExp;
    @Value("${jwt-refresh-expiration}") private Long refreshExp;

    @Transactional
    public void saveToken(String refreshToken, boolean rememberMe) {
        long exp = rememberMe ? refreshExp : accessExp;
        ValueOperations<String, String> valueOperations = userRedisTemplate.opsForValue();
        valueOperations.set(refreshToken, refreshToken); //value 값 필요 X
        userRedisTemplate.expire(refreshToken, exp, TimeUnit.MINUTES);

        //redis test
    }

    public Boolean isExist(String refreshToken) {
        return userRedisTemplate.hasKey(refreshToken);
    }

    public void removeToken(String refreshToken) {
        userRedisTemplate.delete(refreshToken);
    }
}
