package kirin3.jp.mljanken.totalization

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import kirin3.jp.mljanken.R
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils.TAG

class TotalizationActivity : AppCompatActivity() {

    companion object {

        var sViewPager: ViewPager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LOGD(TAG, "DEBUG_DATA :" + "TotalizationActivityTotalizationActivityTotalizationActivity" )

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_totalization)

        var mContext = applicationContext

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView) as AdView)

        sViewPager = findViewById(R.id.pager) as ViewPager
        sViewPager?.setAdapter(TotalizationFragmentStatePagerAdapter(supportFragmentManager))
        sViewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                LOGD(TAG, "DEBUG_DATA position:" + position )
            }
        })


    }
}