package kirin3.jp.mljanken.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.AdmobHelper

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var appContext = applicationContext

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView))
    }
}