package ibf2021.mock_assessment.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    @Scope("singleton") // default scope
    public RedisTemplate<String, Object> createRedisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(Integer.parseInt(System.getenv("spring.redis.redisDatabase")));
        config.setHostName(System.getenv("spring.redis.redisHost"));
        config.setPort(Integer.parseInt(System.getenv("spring.redis.redisPort")));
        config.setUsername(System.getenv("spring.redis.username"));
        config.setPassword(System.getenv("spring.redis.password"));

        final GenericObjectPoolConfig<Integer> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(Integer.parseInt(System.getenv("spring.redis.jedis.pool.max-active")));
        poolConfig.setMinIdle(Integer.parseInt(System.getenv("spring.redis.jedis.pool.min-idle")));
        poolConfig.setMaxIdle(Integer.parseInt(System.getenv("spring.redis.jedis.pool.max-idle")));

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder()
                .usePooling().poolConfig(poolConfig).build();

        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;

    }

}
