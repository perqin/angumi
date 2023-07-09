package com.perqin.angumi.data.models

/**
 * 条目类型
 *
 * 1 为 书籍
 * 2 为 动画
 * 3 为 音乐
 * 4 为 游戏
 * 6 为 三次元
 * 没有 5
 */
enum class SubjectType(private val value: Int) {
    BOOK(1), ANIME(2), MUSIC(3), GAME(4), SANJIGEN(6);

    override fun toString(): String {
        return value.toString()
    }
}
