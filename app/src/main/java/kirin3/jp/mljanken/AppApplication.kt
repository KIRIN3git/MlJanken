package kirin3.jp.mljanken

import android.app.Application
import androidx.multidex.MultiDexApplication
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.AnalyticsHelper
import kirin3.jp.mljanken.util.CrashlyticsHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD


class AppApplication : MultiDexApplication()  {

    private val TAG = LogUtils.makeLogTag(AppApplication::class.java)

    override fun onCreate() {
        super.onCreate()
        CrashlyticsHelper.initializeCrashlytics(this)
        AdmobHelper.initializeAdmob(this)
        AnalyticsHelper.initializeAnalytic(this)
    }

    companion object {
        private val TAG = LogUtils.makeLogTag(AppApplication::class.java)
    }
}
