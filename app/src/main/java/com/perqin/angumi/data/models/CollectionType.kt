package com.perqin.angumi.data.models

/**
 * example: 3
 * 1: 想看
 * 2: 看过
 * 3: 在看
 * 4: 搁置
 * 5: 抛弃
 */
enum class CollectionType(private val value: Int) {
    WISH(1), COLLECT(2), DO(3), ON_HOLD(4), DROPPED(5);

    override fun toString(): String {
        return value.toString()
    }
}