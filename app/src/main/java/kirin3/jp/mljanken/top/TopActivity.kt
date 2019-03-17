package kirin3.jp.mljanken.top

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.game.GameActivity
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.webview.WebViewActivity

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)


        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView) as AdView)

//        val btn_buttle = findViewById(R.id.buttle) as Button
        val view_buttle = findViewById(R.id.buttle) as LottieAnimationView

        val text_policy = findViewById(R.id.policy) as TextView

        /*
        btn_buttle.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@TopActivity, GameActivity::class.java)
                startActivity(intent)
            }
        })
        */

        view_buttle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@TopActivity, GameActivity::class.java)
                startActivity(intent)
            }
        })

        text_policy.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                // インテントのインスタンス生成
                val intent = Intent(this@TopActivity, WebViewActivity::class.java)

                intent.putExtra(
                    WebViewActivity.INTENT_INPUT_URL,
                    applicationContext.getString(R.string.privacy_poricy_url)
                )

                // プライバシーポリシーの起動
                startActivity(intent)
            }
        })

    }
}
