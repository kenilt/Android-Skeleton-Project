package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals

/**
 * Created by thangnguyen on 2019-08-12.
 */
fun assertListContent(vararg items: Any, list: List<*>?) {
    assertEquals(items.size, list?.size.value)
    items.forEachIndexed { index, item ->
        assertEquals(item, list?.get(index))
    }
}
