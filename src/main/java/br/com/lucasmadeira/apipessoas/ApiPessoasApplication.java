package br.com.lucasmadeira.apipessoas;

import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class ApiPessoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPessoasApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, Pessoa> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Pessoa> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		// Configurar serializadores
		template.setKeySerializer(new StringRedisSerializer());
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
				new Jackson2JsonRedisSerializer<Pessoa>(Pessoa.class);
		ObjectMapper mapper = JsonMapper.builder() // or different mapper for other format
				.addModule(new ParameterNamesModule())
				.addModule(new Jdk8Module())
				.addModule(new JavaTimeModule())
				// and possibly other configuration, modules, then:
				.build();
		jackson2JsonRedisSerializer.setObjectMapper(mapper);
		template.setValueSerializer(jackson2JsonRedisSerializer);

		return template;
	}



}
