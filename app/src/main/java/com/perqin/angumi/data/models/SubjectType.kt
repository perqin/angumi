package com.perqin.angumi.data.models

import androidx.annotation.StringRes
import com.perqin.angumi.R

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
enum class SubjectType(private val value: Int, @StringRes val titleRes: Int) {
    BOOK(1, R.string.title_book),
    ANIME(2, R.string.title_anime),
    MUSIC(3, R.string.title_music),
    GAME(4, R.string.title_game),
    SANJIGEN(6, R.string.title_sanjigen);

    override fun toString(): String {
        return value.toString()
    }
}
