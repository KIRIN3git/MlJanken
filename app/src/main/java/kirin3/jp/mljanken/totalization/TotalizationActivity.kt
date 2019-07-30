package kirin3.jp.mljanken.totalization

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper

class TotalizationActivity : AppCompatActivity() {

    companion object {

        var viewPager: androidx.viewpager.widget.ViewPager? = null

        // Firebaseのデータ取得後にViewPager作成
        fun setViewPager(supportFragmentManager: androidx.fragment.app.FragmentManager){
            viewPager?.setAdapter(TotalizationFragmentStatePagerAdapter(supportFragmentManager))
            viewPager?.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener() {
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