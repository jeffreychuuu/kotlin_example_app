package com.example.mongo_service.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Component
class RedisUtil {
    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    // =============================common============================
    /**
     * 指定緩存失效時間
     * @param key 鍵
     * @param time 時間(秒)
     * @return
     */
    fun expire(key: String, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 根據key 獲取過期時間
     * @param key 鍵 不能為null
     * @return 時間(秒) 返回0代表為永久有效
     */
    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS)
    }

    /**
     * 判斷key是否存在
     * @param key 鍵
     * @return true 存在 false不存在
     */
    fun hasKey(key: String): Boolean {
        try {
            return redisTemplate.hasKey(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 刪除緩存
     * @param key 可以傳一個值 或多個
     */
    fun del(vararg key: String) {
        if (key.isNotEmpty()) {
            if (key.size == 1) {
                redisTemplate.delete(key[0])
            } else {
                redisTemplate.delete(key.toList())
            }
        }
    }

    // ============================String=============================
    /**
     * 普通緩存獲取
     * @param key 鍵
     * @return 值
     */
    operator fun get(key: String?): Any? {
        return if (key == null) null else redisTemplate.opsForValue().get(key)
    }

    /**
     * 普通緩存放入
     * @param key 鍵
     * @param value 值
     * @return true成功 false失敗
     */
    operator fun set(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForValue().set(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 普通緩存放入並設置時間
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒) time要大於0 如果time小於等於0 將設置無限期
     * @return true成功 false 失敗
     */
    operator fun set(key: String, value: Any, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS)
            } else {
                set(key, value)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 遞增
     * @param key 鍵
     * @param delta 要增加幾(大於0)
     * @return
     */
    fun incr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("遞增因子必須大於0")
        }
        return redisTemplate.opsForValue().increment(key, delta)!!
    }

    /**
     * 遞減
     * @param key 鍵
     * @param delta 要減少幾(小於0)
     * @return
     */
    fun decr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("遞減因子必須大於0")
        }
        return redisTemplate.opsForValue().increment(key, -delta)!!
    }

    // ================================Map=================================
    /**
     * HashGet
     * @param key 鍵 不能為null
     * @param item 項 不能為null
     * @return 值
     */
    fun hget(key: String, item: String): Any? {
        return redisTemplate.opsForHash<Any, Any>().get(key, item)
    }

    /**
     * 獲取hashKey對應的所有鍵值
     * @param key 鍵
     * @return 對應的多個鍵值
     */
    fun hmget(key: String): Map<Any, Any> {
        return redisTemplate.opsForHash<Any, Any>().entries(key)
    }

    /**
     * HashSet
     * @param key 鍵
     * @param map 對應多個鍵值
     * @return true 成功 false 失敗
     */
    fun hmset(key: String, map: Map<String, Any>): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * HashSet 並設置時間
     * @param key 鍵
     * @param map 對應多個鍵值
     * @param time 時間(秒)
     * @return true成功 false失敗
     */
    fun hmset(key: String, map: Map<String, Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 向一張hash表中放入數據,如果不存在將創建
     * @param key 鍵
     * @param item 項
     * @param value 值
     * @return true 成功 false失敗
     */
    fun hset(key: String, item: String, value: Any): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 向一張hash表中放入數據,如果不存在將創建
     * @param key 鍵
     * @param item 項
     * @param value 值
     * @param time 時間(秒) 注意:如果已存在的hash表有時間,這裡將會替換原有的時間
     * @return true 成功 false失敗
     */
    fun hset(key: String, item: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 刪除hash表中的值
     * @param key 鍵 不能為null
     * @param item 項 可以使多個 不能為null
     */
    fun hdel(key: String, vararg item: Any) {
        redisTemplate.opsForHash<Any, Any>().delete(key, *item)
    }

    /**
     * 判斷hash表中是否有該項的值
     * @param key 鍵 不能為null
     * @param item 項 不能為null
     * @return true 存在 false不存在
     */
    fun hHasKey(key: String, item: String): Boolean {
        return redisTemplate.opsForHash<Any, Any>().hasKey(key, item)
    }

    /**
     * hash遞增 如果不存在,就會創建一個 並把新增後的值返回
     * @param key 鍵
     * @param item 項
     * @param by 要增加幾(大於0)
     * @return
     */
    fun hincr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, by)
    }

    /**
     * hash遞減
     * @param key 鍵
     * @param item 項
     * @param by 要減少記(小於0)
     * @return
     */
    fun hdecr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, -by)
    }

    // ============================set=============================
    /**
     * 根據key獲取Set中的所有值
     * @param key 鍵
     * @return
     */
    fun sGet(key: String): Set<Any>? {
        try {
            return redisTemplate.opsForSet().members(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 根據value從一個set中查詢,是否存在
     * @param key 鍵
     * @param value 值
     * @return true 存在 false不存在
     */
    fun sHasKey(key: String, value: Any): Boolean {
        try {
            return redisTemplate.opsForSet().isMember(key, value)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將數據放入set緩存
     * @param key 鍵
     * @param values 值 可以是多個
     * @return 成功個數
     */
    fun sSet(key: String, vararg values: Any): Long {
        try {
            return redisTemplate.opsForSet().add(key, *values)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 將set數據放入緩存
     * @param key 鍵
     * @param time 時間(秒)
     * @param values 值 可以是多個
     * @return 成功個數
     */
    fun sSetAndTime(key: String, time: Long, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().add(key, *values)
            if (time > 0)
                expire(key, time)
            return count!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 獲取set緩存的長度
     * @param key 鍵
     * @return
     */
    fun sGetSetSize(key: String): Long {
        try {
            return redisTemplate.opsForSet().size(key)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 移除值為value的
     * @param key 鍵
     * @param values 值 可以是多個
     * @return 移除的個數
     */
    fun setRemove(key: String, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().remove(key, *values)
            return count!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }
    // ===============================list=================================

    /**
     * 獲取list緩存的內容
     * @param key 鍵
     * @param start 開始
     * @param end 結束 0 到 -1代表所有值
     * @return
     */
    fun lGet(key: String, start: Long, end: Long): List<Any>? {
        try {
            return redisTemplate.opsForList().range(key, start, end)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 獲取list緩存的長度
     * @param key 鍵
     * @return
     */
    fun lGetListSize(key: String): Long {
        try {
            return redisTemplate.opsForList().size(key)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 通過索引 獲取list中的值
     * @param key 鍵
     * @param index 索引 index>=0時， 0 表頭，1 第二個元素，依次類推；index<0時，-1，表尾，-2倒數第二個元素，依次類推
     * @return
     */
    fun lGetIndex(key: String, index: Long): Any? {
        try {
            return redisTemplate.opsForList().index(key, index)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 將list放入緩存
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將list放入緩存
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將list放入緩存
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: List<Any>): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將list放入緩存
     *
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: List<Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 根據索引修改list中的某條數據
     * @param key 鍵
     * @param index 索引
     * @param value 值
     * @return
     */
    fun lUpdateIndex(key: String, index: Long, value: Any): Boolean {
        try {
            redisTemplate.opsForList().set(key, index, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 移除N個值為value
     * @param key 鍵
     * @param count 移除多少個
     * @param value 值
     * @return 移除的個數
     */
    fun lRemove(key: String, count: Long, value: Any): Long {
        try {
            val remove = redisTemplate.opsForList().remove(key, count, value)
            return remove!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }
}