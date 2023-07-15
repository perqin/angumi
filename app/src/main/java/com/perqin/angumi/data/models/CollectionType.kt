package com.perqin.angumi.data.models

import androidx.annotation.StringRes
import com.perqin.angumi.R

/**
 * example: 3
 * 1: 想看
 * 2: 看过
 * 3: 在看
 * 4: 搁置
 * 5: 抛弃
 */
enum class CollectionType(private val value: Int, @StringRes val titleRes: Int) {
    WISH(1, R.string.title_wish),
    COLLECT(2, R.string.title_collect),
    DO(3, R.string.title_do),
    ON_HOLD(4, R.string.title_on_hold),
    DROPPED(5, R.string.title_dropped);

    override fun toString(): String {
        return value.toString()
    }
}