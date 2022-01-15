package ibf2021_ssf.todo.repositories;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepo {

    @Autowired
    @Qualifier("todo_redis")
    private RedisTemplate<String, String> template;

    public void save(String key, String value) {
        // If one does not access/modify the value for greater than 5 minutes, it will
        // be removed
        template.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
    }

    // if a key is not specified in redis, redis will return null therefore the
    // usage of Optional ensues a check if value is null -> isPresent or isEmpty
    public Optional<String> get(String key) {
        return Optional.ofNullable(template.opsForValue().get(key));
    }

}
