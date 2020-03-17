package com.kenilt.skeleton.managers.common

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.kenilt.skeleton.R


/**
 * Created by thangnguyen on 11/30/18.
 */
class CommonTypeface private constructor() {

    private var regularTypeface: Typeface? = null
    private var mediumTypeface: Typeface? = null
    private var demiBoldTypeface: Typeface? = null
    private var boldTypeface: Typeface? = null

    fun getRegularTypeface(context: Context): Typeface {
        if (regularTypeface == null) {
            regularTypeface = try {
                val appContext = context.applicationContext
                ResourcesCompat.getFont(appContext, R.font.nunito_sans)
            } catch (exception: Exception) {
                Typeface.DEFAULT
            }
        }
        return regularTypeface as Typeface
    }

    fun getMediumTypeface(context: Context): Typeface {
        if (mediumTypeface == null) {
            mediumTypeface = try {
                val appContext = context.applicationContext
                ResourcesCompat.getFont(appContext, R.font.nunito_sans_medium)
            } catch (exception: Exception) {
                Typeface.DEFAULT
            }
        }
        return mediumTypeface as Typeface
    }

    fun getDemiBoldTypeface(context: Context): Typeface {
        if (demiBoldTypeface == null) {
            demiBoldTypeface = try {
                val appContext = context.applicationContext
                ResourcesCompat.getFont(appContext, R.font.nunito_sans_semibold)
            } catch (exception: Exception) {
                Typeface.DEFAULT_BOLD
            }
        }
        return demiBoldTypeface as Typeface
    }

    fun getBoldTypeface(context: Context): Typeface {
        if (boldTypeface == null) {
            boldTypeface = try {
                val appContext = context.applicationContext
                ResourcesCompat.getFont(appContext, R.font.nunito_sans_bold)
            } catch (exception: Exception) {
                Typeface.DEFAULT_BOLD
            }
        }
        return boldTypeface as Typeface
    }

    companion object {
        private var instance: CommonTypeface? = null

        fun getInstance(): CommonTypeface {
            if (instance == null) {
                synchronized(CommonTypeface::class.java) {
                    instance = CommonTypeface()
                }
            }

            return instance as CommonTypeface
        }
    }
}
