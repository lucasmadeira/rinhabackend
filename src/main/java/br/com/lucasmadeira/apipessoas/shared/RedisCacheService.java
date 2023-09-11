package br.com.lucasmadeira.apipessoas.shared;

import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class RedisCacheService {
    private final RedisTemplate<String, Pessoa> redisTemplate;

    public RedisCacheService(RedisTemplate<String, Pessoa> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheItem(Pessoa item) {
        redisTemplate.opsForValue().set(item.getApelido(), item);
        redisTemplate.opsForValue().set(item.getId().toString(), item);
    }

    public Pessoa getCachedItem(String key) {
        Pessoa pessoa = redisTemplate.opsForValue().get(key);
        return  pessoa;
    }

    public void delete(List<String> keys){
        redisTemplate.delete(keys);
    }

}