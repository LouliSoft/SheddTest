package com.m2f.sheddtest.data.persistency

import android.os.SystemClock
import android.util.LruCache
import java.io.Serializable
import java.util.*

class ExpiringLruCache<K, V>
(maxSize: Int, private val mExpireTime: Long) : Serializable {
    private val mCache: LruCache<K, V>
    private val mExpirationTimes: MutableMap<K, Long> = HashMap(maxSize)

    init {
        mCache = MyLruCache(maxSize)
    }

    @Synchronized
    operator fun get(key: K): V? {
        val value = mCache.get(key)
        if (value != null && elapsedRealtime() >= getExpiryTime(key)) {
            remove(key)
            return null
        }
        return value
    }

    @Synchronized
    fun put(key: K, value: V) {
        mCache.put(key, value)
        mExpirationTimes.put(key, elapsedRealtime() + mExpireTime)
    }

    internal fun elapsedRealtime(): Long {
        return SystemClock.elapsedRealtime()
    }

    internal fun getExpiryTime(key: K): Long {
        val time = mExpirationTimes[key] ?: return 0
        return time
    }

    internal fun removeExpiryTime(key: K) {
        mExpirationTimes.remove(key)
    }

    fun remove(key: K): V {
        return mCache.remove(key)
    }

    fun snapshot(): Map<K, V> {
        return mCache.snapshot()
    }

    fun trimToSize(maxSize: Int) {
        mCache.trimToSize(maxSize)
    }

    fun createCount(): Int {
        return mCache.createCount()
    }

    fun evictAll() {
        mCache.evictAll()
    }

    fun evictionCount(): Int {
        return mCache.evictionCount()
    }

    fun hitCount(): Int {
        return mCache.hitCount()
    }

    fun maxSize(): Int {
        return mCache.maxSize()
    }

    fun missCount(): Int {
        return mCache.missCount()
    }

    fun putCount(): Int {
        return mCache.putCount()
    }

    fun size(): Int {
        return mCache.size()
    }

    /**
     * Extended the LruCache to override the [.entryRemoved] method
     * so we can remove expiration time entries when things are evicted from the cache.

     * Gotta love some of those Google engineers making things difficult with paranoid
     * usage of the `final` keyword.
     */
    private inner class MyLruCache(maxSize: Int) : LruCache<K, V>(maxSize) {

        override fun entryRemoved(evicted: Boolean, key: K, oldValue: V, newValue: V) {
            this@ExpiringLruCache.removeExpiryTime(key) // dirty hack
        }

        override fun sizeOf(key: K, value: V): Int {
            return this@ExpiringLruCache.sizeOf()
        }
    }

    /**
     * Returns the size of the entry for `key` and `value` in
     * user-defined units.  The default implementation returns 1 so that size
     * is the number of entries and max size is the maximum number of entries.

     *
     * An entry's size must not change while it is in the cache.
     */
    protected fun sizeOf(): Int {
        return 1
    }

    operator fun set(key: K, value: V) {
        put(key, value)
    }
}