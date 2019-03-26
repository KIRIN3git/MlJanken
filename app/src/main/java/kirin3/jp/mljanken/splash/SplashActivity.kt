package kirin3.jp.mljanken.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.top.TopActivity
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.AnalyticsHelper
import kirin3.jp.mljanken.util.CrashlyticsHelper
import kirin3.jp.mljanken.util.SettingsUtils

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mContext = applicationContext


//        CrashlyticsHelper.initializeCrashlytics(mContext)
        AdmobHelper.initializeAdmob(mContext)
//        AnalyticsHelper.initializeAnalytic(mContext)

        setContentView(R.layout.activity_splash)


        val handler = Handler()
        handler.postDelayed({
            if (!isFinishing) {
                val intent = Intent(this@SplashActivity, TopActivity::class.java)
                startActivity(intent)
            }
        }, SPLASH_DISPLAY_LENGHT.toLong())
    }

    companion object {

//        private val SPLASH_DISPLAY_LENGHT = 2 * 1000
        private val SPLASH_DISPLAY_LENGHT = 0.5 * 1000
    }
}