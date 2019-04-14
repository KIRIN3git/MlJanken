package kirin3.jp.mljanken

import android.app.Application
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD


class AppApplication : Application() {

    private val TAG = LogUtils.makeLogTag(AppApplication::class.java)

    override fun onCreate() {
        super.onCreate()

        LOGD(TAG, "Analytics being prepared.")

        //        CrashlyticsHelper.initializeCrashlytics(this)
        AdmobHelper.initializeAdmob(this)
//        AnalyticsHelper.initializeAnalytic(this)
    }

    companion object {

        private val TAG = LogUtils.makeLogTag(AppApplication::class.java)
    }


}
