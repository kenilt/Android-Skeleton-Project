package com.kenilt.skeleton.ui.feature.main

import android.content.Context
import android.content.Intent
import com.kenilt.skeleton.R
import com.kenilt.skeleton.databinding.ActivityMainBinding
import com.kenilt.skeleton.ui.base.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun init() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
