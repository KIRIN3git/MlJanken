package kirin3.jp.mljanken.totalization

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kirin3.jp.mljanken.R
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils.TAG
import kotlinx.android.synthetic.main.activity_totalization.*
import kotlinx.android.synthetic.main.activity_totalization.view.*

class TotalizationActivity : AppCompatActivity() {

    companion object {

        var sViewPager: ViewPager? = null

        // Firebaseのデータ取得後にViewPager作成
        fun setViewPager(supportFragmentManager:FragmentManager){
            sViewPager?.setAdapter(TotalizationFragmentStatePagerAdapter(supportFragmentManager))
            sViewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                }
            })
        }
    }

    var mContext: Context ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_totalization)

        mContext = applicationContext
        sViewPager = findViewById(R.id.pager)

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView))

        var db = CloudFirestoreHelper.getInitDb(mContext!!)

        TotalizationCloudFirestoreHelper.getTotalizationData(db,"users",mContext!!,supportFragmentManager)
    }
}