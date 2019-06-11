package kirin3.jp.mljanken.information

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kirin3.jp.mljanken.R
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.util.AdmobHelper

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        var mContext = applicationContext

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView))
    }
}