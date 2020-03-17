package com.kenilt.skeleton.model.`object`

import com.kenilt.skeleton.model.BaseModel

/**
 * Created by neal on 1/9/17.
 */

class Paging : BaseModel() {
    var current: Int = 0
    var per: Int = 0
    var total: Int = 0

    val hasNextPage: Boolean
        get() = current < total
}
