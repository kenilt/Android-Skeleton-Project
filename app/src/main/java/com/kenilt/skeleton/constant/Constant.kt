package com.kenilt.skeleton.constant

/**
 * Created by thanh.nguyen on 11/15/16.
 */
object Constant {
    // Activity request
    const val INSTALL_APP = 0x112

    // Permission request
    const val KEY_SHOULD_MIGRATE_INSTANT_DATA = "KEY_SHOULD_MIGRATE_INSTANT_DATA"

    // For firebase analytics
    const val STATUS_INSTALLED = "installed"
    const val STATUS_INSTANT = "instant"
    const val ANALYTICS_APP_PROP = "app_type"

    // For reporter
    const val OPEN_INSTANT_APP_COUNT_BEFORE_ASK = 5

    // For reporter
    const val SLOW_TIME_LEVEL = 2000        //2s
    const val SLOWER_TIME_LEVEL = 3000      //5s
    const val VERY_SLOW_TIME_LEVEL = 15000  //5s

}
