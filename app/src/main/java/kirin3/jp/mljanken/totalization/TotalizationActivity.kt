package kirin3.jp.mljanken.totalization

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper

class TotalizationActivity : AppCompatActivity() {

    companion object {

        var viewPager: ViewPager? = null

        // Firebaseのデータ取得後にViewPager作成
        fun setViewPager(supportFragmentManager:FragmentManager){
            viewPager?.setAdapter(TotalizationFragmentStatePagerAdapter(supportFragmentManager))
            viewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                }
            })
        }
    }

    var appContext: Context ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_totalization)

        appContext = applicationContext
        viewPager = findViewById(R.id.pager)

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView))

        var db = CloudFirestoreHelper.getInitDb(appContext!!)

        TotalizationCloudFirestoreHelper.getTotalizationData(db,"users",appContext!!,supportFragmentManager)
    }
}