package kirin3.jp.mljanken.award

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.AdmobHelper

class AwardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_award)

        // AdMob設定F
        AdmobHelper.loadBanner(findViewById(R.id.adView) as AdView)
    }
}