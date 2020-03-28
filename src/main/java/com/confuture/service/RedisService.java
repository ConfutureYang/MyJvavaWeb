package com.confuture.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSON;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.util.CollectionUtils;

public class RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisService.class);
    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public RedisService() {
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setString(String key, String value) {
        try {
            this.stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception var4) {
            this.logger.error("redis setString error ! key:{}, value:{}", new Object[]{key, value, var4});
            throw var4;
        }
    }

    public void setString(String key, String value, long timeout, TimeUnit timeUnit) {
        try {
            this.stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception var7) {
            this.logger.error("redis setString error ! key:{}, value:{}, timeout:{}, TimeUnit:{}", new Object[]{key, value, timeout, timeUnit, var7});
            throw var7;
        }
    }

    public String getString(String key) {
        try {
            return (String)this.stringRedisTemplate.opsForValue().get(key);
        } catch (Exception var3) {
            this.logger.error("redis getString error ! key : {}", key, var3);
            throw var3;
        }
    }

    public <T> void setObject(String key, T value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
        } catch (Exception var4) {
            this.logger.error("redis setObject error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit) {
        try {
            this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception var7) {
            this.logger.error("redis setObject error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public <T> T getObject(String key) {
        try {
            ValueOperations<String, T> ops = this.redisTemplate.opsForValue();
            return ops.get(key);
        } catch (Exception var3) {
            this.logger.error("redis getObject error ! key : {}", key, var3);
            throw var3;
        }
    }

    public void setStringList(String key, List<String> value) {
        this.setStringList(key, value, 0L, (TimeUnit)null);
    }

    public void setStringList(String key, List<String> value, long timeout, TimeUnit timeUnit) {
        try {
            this.stringRedisTemplate.delete(key);
            ListOperations<String, String> ops = this.stringRedisTemplate.opsForList();
            if (value != null && value.size() > 0) {
                ops.rightPushAll(key, value);
                if (timeout != 0L) {
                    this.stringRedisTemplate.expire(key, timeout, timeUnit);
                }
            }

        } catch (Exception var7) {
            this.logger.error("redis setStringList error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public List<String> getStringList(String key) {
        return this.getStringList(key, 0, -1);
    }

    public List<String> getStringList(String key, int start, int end) {
        try {
            return this.stringRedisTemplate.opsForList().range(key, (long)start, (long)end);
        } catch (Exception var5) {
            this.logger.error("redis getStringList error ! key : {} , start : {} , end : {}", new Object[]{key, start, end, var5});
            throw var5;
        }
    }

    public String lpopString(String key) {
        try {
            return (String)this.stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception var3) {
            this.logger.error("redis lpopString error ! key : {}", key, var3);
            throw var3;
        }
    }

    public void lpushString(String key, String value) {
        try {
            this.stringRedisTemplate.opsForList().leftPush(key, value);
        } catch (Exception var4) {
            this.logger.error("redis lpushString error ! key : {} , value : {}", new Object[]{key, value, var4});
            throw var4;
        }
    }

    public String lindexString(String key, long index) {
        try {
            return (String)this.stringRedisTemplate.opsForList().index(key, index);
        } catch (Exception var5) {
            this.logger.error("redis lpopString error ! key : {}", key, var5);
            throw var5;
        }
    }

    public void lpushStringList(String key, List<String> list) {
        try {
            if (list != null && list.size() > 0) {
                this.stringRedisTemplate.opsForList().leftPushAll(key, list);
            }

        } catch (Exception var4) {
            this.logger.error("redis lpushStringList error ! key : {}", key, var4);
            throw var4;
        }
    }

    public void lsetString(String key, long index, String value) {
        try {
            this.stringRedisTemplate.opsForList().set(key, index, value);
        } catch (Exception var6) {
            this.logger.error("redis lpushStringList error ! key : {}", key, var6);
            throw var6;
        }
    }

    public String rpopString(String key) {
        try {
            return (String)this.stringRedisTemplate.opsForList().rightPop(key);
        } catch (Exception var3) {
            this.logger.error("redis rpopString error ! key : {}", key, var3);
            throw var3;
        }
    }

    public void rpushString(String key, String value) {
        try {
            if (value != null) {
                this.stringRedisTemplate.opsForList().rightPush(key, value);
            }

        } catch (Exception var4) {
            this.logger.error("redis rpushString error ! key : {} , value : {}", new Object[]{key, value, var4});
            throw var4;
        }
    }

    public void rpushStringList(String key, List<String> list) {
        try {
            if (list != null && list.size() > 0) {
                this.stringRedisTemplate.opsForList().rightPushAll(key, list);
            }

        } catch (Exception var4) {
            this.logger.error("redis rpushStringList error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> void setObjectList(String key, List<T> value) {
        this.setObjectList(key, value, 0L, (TimeUnit)null);
    }

    public <T> void setObjectList(String key, List<T> value, long timeout, TimeUnit timeUnit) {
        try {
            this.redisTemplate.delete(key);
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            if (value != null) {
                ops.rightPushAll(key, value);
                if (timeout != 0L) {
                    this.redisTemplate.expire(key, timeout, timeUnit);
                }
            }

        } catch (Exception var7) {
            this.logger.error("redis setObjectList error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public <T> List<T> getObjectList(String key) {
        return this.getObjectList(key, 0, -1);
    }

    public <T> List<T> getObjectList(String key, int start, int end) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            return ops.range(key, (long)start, (long)end);
        } catch (Exception var5) {
            this.logger.error("redis getObjectList error ! key : {} , start : {} , end : {}", new Object[]{key, start, end, var5});
            throw var5;
        }
    }

    public <T> T lpopObject(String key) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            return ops.leftPop(key);
        } catch (Exception var3) {
            this.logger.error("redis lpopObject error ! key : {}", key, var3);
            throw var3;
        }
    }

    public <T> T lindexObject(String key, long index) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            return ops.index(key, index);
        } catch (Exception var5) {
            this.logger.error("redis lindexObject error ! key : {}", key, var5);
            throw var5;
        }
    }

    public <T> void lpushObject(String key, T value) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            if (value != null) {
                ops.leftPush(key, value);
            }

        } catch (Exception var4) {
            this.logger.error("redis lpushObject error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> void lsetObject(String key, long index, T value) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            if (value != null) {
                ops.set(key, index, value);
            }

        } catch (Exception var6) {
            this.logger.error("redis lsetObject error ! key : {}", key, var6);
            throw var6;
        }
    }

    public <T> void lpushObjectList(String key, List<T> list) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            if (list != null) {
                ops.leftPushAll(key, list);
            }

        } catch (Exception var4) {
            this.logger.error("redis lpushObjectList error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> T rpopObject(String key) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            return ops.leftPop(key);
        } catch (Exception var3) {
            this.logger.error("redis rpopObject error ! key : {}", key, var3);
            throw var3;
        }
    }

    public <T> void rpushObject(String key, T value) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            if (value != null) {
                ops.rightPush(key, value);
            }

        } catch (Exception var4) {
            this.logger.error("redis lpushObject error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> void rpushObjectList(String key, List<T> list) {
        try {
            ListOperations<String, T> ops = this.redisTemplate.opsForList();
            if (list != null) {
                ops.rightPushAll(key, list);
            }

        } catch (Exception var4) {
            this.logger.error("redis rpushObjectList error ! key : {}", key, var4);
            throw var4;
        }
    }

    public Set<String> getStringSet(String key) {
        try {
            return this.stringRedisTemplate.opsForSet().members(key);
        } catch (Exception var3) {
            this.logger.error("redis getStringSet error ! key : {}", key, var3);
            throw var3;
        }
    }

    public String popStringSet(String key) {
        try {
            return (String)this.stringRedisTemplate.opsForSet().pop(key);
        } catch (Exception var3) {
            this.logger.error("redis popStringSet error ! key : {}", key, var3);
            throw var3;
        }
    }

    public void setStringSet(String key, Set<String> set) {
        this.setStringSet(key, set, 0L, (TimeUnit)null);
    }

    public void setStringSet(String key, Set<String> set, long timeout, TimeUnit timeUnit) {
        try {
            SetOperations<String, String> ops = this.stringRedisTemplate.opsForSet();
            this.stringRedisTemplate.delete(key);
            if (set != null && set.size() > 0) {
                ops.add(key, set.toArray(new String[set.size()]));
                if (timeout != 0L) {
                    this.stringRedisTemplate.expire(key, timeout, timeUnit);
                }
            }

        } catch (Exception var7) {
            this.logger.error("redis setStringSet error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public void sadd(String key, String value) {
        this.sadd(key, value, 0L, (TimeUnit)null);
    }

    public void sadd(String key, String value, long timeout, TimeUnit timeUnit) {
        try {
            SetOperations<String, String> ops = this.stringRedisTemplate.opsForSet();
            ops.add(key, new String[]{value});
            if (timeout != 0L) {
                this.stringRedisTemplate.expire(key, timeout, timeUnit);
            }

        } catch (Exception var7) {
            this.logger.error("redis sadd error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public <T> Set<T> getObjectSet(String key) {
        try {
            SetOperations<String, T> ops = this.redisTemplate.opsForSet();
            return ops.members(key);
        } catch (Exception var3) {
            this.logger.error("redis getObjectSet error ! key : {}", key, var3);
            throw var3;
        }
    }

//    public <T> void setObjectSet(String key, Set<T> set) {
//        this.setObjectSet(key, set, 0L, (TimeUnit)null);
//    }


//    因为编译报错临时注释掉这个方法
//    public <T> void setObjectSet(String key, Set<T> set, long timeout, TimeUnit timeUnit) {
//        try {
//            SetOperations<String, T> ops = this.redisTemplate.opsForSet();
//            this.redisTemplate.delete(key);
//            if (set != null && set.size() > 0) {
//                Iterator var7 = set.iterator();
//
//                while(var7.hasNext()) {
//                    T t = var7.next();
//                    ops.add(key, new Object[]{t});
//                }
//
//                if (timeout != 0L) {
//                    this.redisTemplate.expire(key, timeout, timeUnit);
//                }
//            }
//
//        } catch (Exception var9) {
//            this.logger.error("redis setObjectSet error ! key : {} , timeout : {}", new Object[]{key, timeout, var9});
//            throw var9;
//        }
//    }

    public Map<String, String> getStringMap(String key) {
        try {
            HashOperations<String, String, String> ops = this.stringRedisTemplate.opsForHash();
            return ops.entries(key);
        } catch (Exception var3) {
            this.logger.error("redis getStringMap error ! key : {}", key, var3);
            throw var3;
        }
    }

    public String getStringMapBySubKey(String key, String subKey) {
        try {
            HashOperations<String, String, String> ops = this.stringRedisTemplate.opsForHash();
            return (String)ops.get(key, subKey);
        } catch (Exception var4) {
            this.logger.error("redis getStringMapBySubKey error ! key : {}", key, var4);
            throw var4;
        }
    }

    public void setStringMap(String key, Map<String, String> map) {
        this.setStringMap(key, map, 0L, (TimeUnit)null);
    }

    public void setStringMap(String key, Map<String, String> map, long timeout, TimeUnit timeUnit) {
        try {
            HashOperations<String, String, String> ops = this.stringRedisTemplate.opsForHash();
            this.stringRedisTemplate.delete(key);
            if (map != null) {
                ops.putAll(key, map);
                if (timeout != 0L) {
                    this.stringRedisTemplate.expire(key, timeout, timeUnit);
                }
            }

        } catch (Exception var7) {
            this.logger.error("redis setStringMap error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public void setStringMapBySubKey(String key, String subKey, String value) {
        try {
            this.stringRedisTemplate.opsForHash().put(key, subKey, value);
        } catch (Exception var5) {
            this.logger.error("redis setStringMap error ! key : {}", key, var5);
            throw var5;
        }
    }

    public <K, V> Map<K, V> getObjectMap(String key) {
        try {
            HashOperations<String, K, V> ops = this.redisTemplate.opsForHash();
            return ops.entries(key);
        } catch (Exception var3) {
            this.logger.error("redis getObjectMap error ! key : {}", key, var3);
            throw var3;
        }
    }

    public <K, V> void setObjectMap(String key, Map<K, V> map) {
        this.setObjectMap(key, map, 0L, (TimeUnit)null);
    }

    public <K, V> void setObjectMap(String key, Map<K, V> map, long timeout, TimeUnit timeUnit) {
        try {
            HashOperations<String, K, V> ops = this.redisTemplate.opsForHash();
            this.redisTemplate.delete(key);
            if (map != null) {
                ops.putAll(key, map);
                if (timeout != 0L) {
                    this.redisTemplate.expire(key, timeout, timeUnit);
                }
            }

        } catch (Exception var7) {
            this.logger.error("redis setObjectMap error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public void setObjectMapBySubKey(String key, Object subKey, Object value) {
        try {
            this.redisTemplate.opsForHash().put(key, subKey, value);
        } catch (Exception var5) {
            this.logger.error("redis setObjectMap error ! key : {}", key, var5);
            throw var5;
        }
    }

    public void removeStringMap(String key, String mapKey) {
        try {
            HashOperations<String, String, String> ops = this.stringRedisTemplate.opsForHash();
            ops.delete(key, new Object[]{mapKey});
        } catch (Exception var4) {
            this.logger.error("redis removeStringMap error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> void removeObjectMap(String key, T mapKey) {
        try {
            HashOperations<String, T, Object> ops = this.redisTemplate.opsForHash();
            ops.delete(key, new Object[]{mapKey});
        } catch (Exception var4) {
            this.logger.error("redis removeObjectMap error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> T getStringFromMap(String key, String hashKey, Class<T> tClass) {
        try {
            T t = null;
            String resultString = (String)this.redisTemplate.opsForHash().get(key, hashKey);
            if (resultString != null) {
                t = JSON.parseObject(resultString, tClass);
            }

            return t;
        } catch (Exception var6) {
            this.logger.error("redis getStringFromMap error ! key : {}", key, var6);
            throw var6;
        }
    }

//    因为编译报错临时注释掉这个方法
//    public <K, V> V getObjectFromMap(String key, K hashKey) {
//        try {
//            V v = null;
//            Object o = this.redisTemplate.opsForHash().get(key, hashKey);
//            if (o != null) {
//                v = o;
//            }
//
//            return v;
//        } catch (Exception var5) {
//            this.logger.error("redis getObjectFromMap error ! key : {}", key, var5);
//            throw var5;
//        }
//    }

    public void delete(String key) {
        try {
            this.redisTemplate.delete(key);
        } catch (Exception var3) {
            this.logger.error("redis delete error ! key : {}", key, var3);
            throw var3;
        }
    }

    public <K> void delete(Collection<K> keys) {
        try {
            if (!CollectionUtils.isEmpty(keys)) {
                this.redisTemplate.delete(keys);
            }

        } catch (Exception var3) {
            this.logger.error("redis delete error ! keySize : {}", keys.size(), var3);
            if (keys.size() > 0) {
                this.logger.error("{keys : " + keys.toArray()[0] + "... }");
            }

            throw var3;
        }
    }

    public void expire(String key, long timeout, TimeUnit timeUnit) {
        try {
            this.redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception var6) {
            this.logger.error("redis expire error ! key : {}", key, var6);
            throw var6;
        }
    }

    public void expireObject(String key, long timeout, TimeUnit timeUnit) {
        try {
            this.redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception var6) {
            this.logger.error("redis expireObject error ! key : {}", key, var6);
            throw var6;
        }
    }

    public Set<String> getStringZSet(String key) {
        return this.getStringZSet(key, 0, -1);
    }

    public Set<String> getStringZSet(String key, int begin, int end) {
        try {
            return this.stringRedisTemplate.opsForZSet().range(key, (long)begin, (long)end);
        } catch (Exception var5) {
            this.logger.error("redis getStringZSet error ! key : {} , begin : {} , end : {}", new Object[]{key, begin, end, var5});
            throw var5;
        }
    }

    public Set<TypedTuple<String>> getStringZSetWithScores(String key) {
        return this.getStringZSetWithScores(key, 0, -1);
    }

    public Set<TypedTuple<String>> getStringZSetWithScores(String key, int start, int end) {
        try {
            return this.stringRedisTemplate.opsForZSet().rangeWithScores(key, (long)start, (long)end);
        } catch (Exception var5) {
            this.logger.error("redis getStringZSetWithScores error ! key : {} , start : {} , end : {}", new Object[]{key, start, end, var5});
            throw var5;
        }
    }

    public Double scoreString(String key, String subKey) {
        try {
            return this.stringRedisTemplate.opsForZSet().score(key, subKey);
        } catch (Exception var4) {
            this.logger.error("redis scoreString error ! key : {} , subKey : {}", new Object[]{key, subKey, var4});
            throw var4;
        }
    }

    public void setZSetString(String key, String value, double score) {
        try {
            this.stringRedisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception var6) {
            this.logger.error("redis setZSetString error ! key : {} , value : {} , score : {}", new Object[]{key, value, score, var6});
        }

    }

    public <T> Set<TypedTuple<T>> getZsetWithScores(String key) {
        return this.getZsetWithScores(key, 0, -1);
    }

    public <T> TypedTuple<T> getZsetWithScores(String key, int index) {
        try {
            Set<TypedTuple<T>> zset = this.getZsetWithScores(key, index, index);
            if (zset != null && zset.size() > 0) {
                Iterator var4 = zset.iterator();
                if (var4.hasNext()) {
                    TypedTuple<T> t = (TypedTuple)var4.next();
                    return t;
                }
            }

            return null;
        } catch (Exception var6) {
            this.logger.error("redis getZsetWithScores error ! key : {}", key, var6);
            throw var6;
        }
    }

    public <T> Set<TypedTuple<T>> getZsetWithScores(String key, int start, int end) {
        try {
            return this.redisTemplate.opsForZSet().rangeWithScores(key, (long)start, (long)end);
        } catch (Exception var5) {
            this.logger.error("redis getZsetWithScores error ! key : {}", key, var5);
            throw var5;
        }
    }

    public <T> TypedTuple<T> getZsetByScoreWithScores(String key, double score) {
        try {
            Set<TypedTuple<T>> zset = this.getZsetByScoreWithScores(key, score, score);
            if (zset != null && zset.size() > 0) {
                Iterator var5 = zset.iterator();
                if (var5.hasNext()) {
                    TypedTuple<T> t = (TypedTuple)var5.next();
                    return t;
                }
            }

            return null;
        } catch (Exception var7) {
            this.logger.error("redis getZsetByScoreWithScores error ! key : {}", key, var7);
            throw var7;
        }
    }

    public <T> Set<TypedTuple<T>> getZsetByScoreWithScores(String key, double min, double max) {
        try {
            return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        } catch (Exception var7) {
            this.logger.error("redis getZsetByScoreWithScores error ! key : {}", key, var7);
            throw var7;
        }
    }

    public <T> void setZset(String key, T t, double score) {
        try {
            this.redisTemplate.opsForZSet().add(key, t, score);
        } catch (Exception var6) {
            this.logger.error("redis addZset error : ! key : {}", key, var6);
            throw var6;
        }
    }

    public <T> void removeZset(String key, T... ts) {
        try {
            if (ts != null && ts.length > 0) {
                this.redisTemplate.opsForZSet().remove(key, ts);
            }

        } catch (Exception var4) {
            this.logger.error("redis removeZset error ! key : {}", key, var4);
            throw var4;
        }
    }

    public void removeStringZset(String key, String... values) {
        try {
            if (values != null && values.length > 0) {
                this.stringRedisTemplate.opsForZSet().remove(key, values);
            }

        } catch (Exception var4) {
            this.logger.error("redis removeStringZset error ! key : {}", key, var4);
            throw var4;
        }
    }

    public <T> void removeZsetByScore(String key, double min, double max) {
        try {
            this.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception var7) {
            this.logger.error("redis removeZsetByScore error ！ key : {}", key, var7);
            throw var7;
        }
    }

    public Set<String> getStringZSetByScore(String key, double min, double max) {
        try {
            return this.stringRedisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception var7) {
            this.logger.error("redis getStringZSetByScore error ！ key : {}", key, var7);
            throw var7;
        }
    }

    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var3) {
            this.logger.error("redis hasKey error ！ key : {}", key, var3);
            throw var3;
        }
    }

    public Long getAndIncrement(String key, long timeout, TimeUnit timeUnit) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            Long atomicValue = redisAtomicLong.getAndIncrement();
            if (timeout != 0L) {
                redisAtomicLong.expire(timeout, timeUnit);
            }

            return atomicValue;
        } catch (Exception var7) {
            this.logger.error("redis getAndIncrement error ! key : {}  , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public Long getAndIncrement(String key) {
        return this.getAndIncrement(key, 0L, (TimeUnit)null);
    }

    public Long getAndDecrement(String key, long timeout, TimeUnit timeUnit) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            Long atomicValue = redisAtomicLong.getAndDecrement();
            if (timeout != 0L) {
                redisAtomicLong.expire(timeout, timeUnit);
            }

            return atomicValue;
        } catch (Exception var7) {
            this.logger.error("redis getAndDecrement error ! key : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public Long getAndDecrement(String key) {
        return this.getAndDecrement(key, 0L, (TimeUnit)null);
    }

    public Long getAndAdd(String key, Long value, long timeout, TimeUnit timeUnit) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            Long atomicValue = redisAtomicLong.getAndAdd(value);
            if (timeout != 0L) {
                redisAtomicLong.expire(timeout, timeUnit);
            }

            return atomicValue;
        } catch (Exception var8) {
            this.logger.error("redis getAndAdd error ! key : {} , value : {} , timeout : {}", new Object[]{key, value, timeout, var8});
            throw var8;
        }
    }

    public Long getAndAdd(String key, Long value) {
        return this.getAndAdd(key, value, 0L, (TimeUnit)null);
    }

    public Long getAndSet(String key, Long value, long timeout, TimeUnit timeUnit) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            Long atomicValue = redisAtomicLong.getAndSet(value);
            if (timeout != 0L) {
                redisAtomicLong.expire(timeout, timeUnit);
            }

            return atomicValue;
        } catch (Exception var8) {
            this.logger.error("redis getAndSet error ! key : {} , value : {} , timeout : {}", new Object[]{key, value, timeout, var8});
            throw var8;
        }
    }

    public Long getAndSet(String key, Long value) {
        return this.getAndSet(key, value, 0L, (TimeUnit)null);
    }

    public Long incrementAndGet(String key, long timeout, TimeUnit timeUnit) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            Long atomicValue = redisAtomicLong.incrementAndGet();
            if (timeout != 0L) {
                redisAtomicLong.expire(timeout, timeUnit);
            }

            return atomicValue;
        } catch (Exception var7) {
            this.logger.error("redis incrementAndGet error ! key : {} , value : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public Long incrementAndGet(String key) {
        return this.incrementAndGet(key, 0L, (TimeUnit)null);
    }

    public Long decrementAndGet(String key, long timeout, TimeUnit timeUnit) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            Long atomicValue = redisAtomicLong.decrementAndGet();
            if (timeout != 0L) {
                redisAtomicLong.expire(timeout, timeUnit);
            }

            return atomicValue;
        } catch (Exception var7) {
            this.logger.error("redis decrementAndGet error ! key : {} , value : {} , timeout : {}", new Object[]{key, timeout, var7});
            throw var7;
        }
    }

    public Long decrementAndGet(String key) {
        return this.decrementAndGet(key, 0L, (TimeUnit)null);
    }

    public Long getIncrementValue(String key) {
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
            return redisAtomicLong.get();
        } catch (Exception var3) {
            this.logger.error("redis getIncrementValue error ! key : {} , value : {} ", key, var3);
            throw var3;
        }
    }

    public Long llen(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var3) {
            this.logger.error("redis llen error ! key : {} ", key, var3);
            throw var3;
        }
    }
}
