package ibf2021.workshop14.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final Logger logger = Logger.getLogger(RedisConfig.class.getName());

    @Autowired
    private Environment env;

    @Bean("AppConfig")
    @Scope("singleton")
    public RedisTemplate<String, Object> createRedisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(0);
        config.setHostName(env.getProperty("spring.redis.host"));
        logger.info(env.getProperty("spring.redis.host"));
        config.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        logger.info(env.getProperty("spring.redis.port"));
        config.setPassword(System.getenv("spring.redis.password"));
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config, jedisClient);
        jedisFactory.afterPropertiesSet();

        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> valueSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(valueSerializer);
        return template;

    }

}
