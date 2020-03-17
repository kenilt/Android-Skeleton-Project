package com.kenilt.skeleton.constant

import com.kenilt.skeleton.BuildConfig

/**
 * Created by neal on 2/15/17.
 */

object Skeleton {

    val IS_PRODUCTION_RELEASE = !BuildConfig.DEBUG && (BuildConfig.FLAVOR == "production")

}
