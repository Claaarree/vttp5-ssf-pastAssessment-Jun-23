package sg.edu.nus.iss.vttp5a_ssf_past_assessment_jun_23.respositories;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	// TODO Task 5
	// Use this class to implement CRUD operations on Redis

	@Autowired
    @Qualifier("String-String")
    RedisTemplate<String, String> redisTemplate;

    public void lockValue(String redisKey, String redisValue, Duration timeout) {
        redisTemplate.opsForValue().set(redisKey, redisValue, timeout);
    }

	public Boolean isExists(String redisKey) {
        return redisTemplate.hasKey(redisKey);
    }
}
