package com.kenilt.skeleton.ui.feature.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.kenilt.skeleton.LXApplication
import com.kenilt.skeleton.R
import com.kenilt.skeleton.constant.Constant
import com.kenilt.skeleton.databinding.ActivityWelcomeBinding
import com.kenilt.skeleton.extension.value
import com.kenilt.skeleton.managers.helpers.ThreadFinishedCounting
import com.kenilt.skeleton.ui.base.BaseActivity
import com.kenilt.skeleton.ui.feature.main.MainActivity
import com.kenilt.skeleton.utils.InstantAppUtil


open class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    private var threadFinishedCounting: ThreadFinishedCounting? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun init() {
        val shouldMigrate = intent.getBooleanExtra(Constant.KEY_SHOULD_MIGRATE_INSTANT_DATA, false)
        if (shouldMigrate) {
            LXApplication.instance.config.putTutorial(false)
            InstantAppUtil.migrateCookieData(this)
        }

        checkStartingFlow()
    }

    open fun checkStartingFlow() {
        val isShowTutorial = LXApplication.instance.config.isTutorial
        if (isShowTutorial && !LXApplication.instance.isInstantApp) {
            Handler().postDelayed({
                if (!isFinishing) {
                    // go to another screen if need
                    startMainActivity()
                }
            }, 500)
        } else {
            loadAsyncData()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun loadAsyncData() {
        threadFinishedCounting = ThreadFinishedCounting(1, 2000)    // 2s
        threadFinishedCounting?.start(object: ThreadFinishedCounting.ThreadCountingCallback {
            override fun onFinishCounting(successCount: Int, failedCount: Int) {
                if (successCount + failedCount < threadFinishedCounting?.totalThread.value) {
//                    sendGAEvent("Start main but fetching not finished")
                } else if (failedCount > 0) {
//                    sendGAEvent("Start main with failed fetching")
                }

                startMainActivity()
            }
        })
    }

//    private fun getFeedData() {
//        prefetchFeedManager.onPrefetchFeedListener = object: PrefetchFeedManager.OnPrefetchFeedListener {
//            override fun onSuccess() {
//                threadFinishedCounting?.countSuccess()
//            }
//
//            override fun onFailed() {
//                threadFinishedCounting?.countFailed()
//            }
//        }
//        prefetchFeedManager.prefetchFeedFirstPage()
//    }

    open fun startMainActivity() {
        if (!isFinishing) {
            MainActivity.start(this)
            finish()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

            context.startActivity(intent)
        }
    }
}
