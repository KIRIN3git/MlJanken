package kirin3.jp.mljanken.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.game.GameActivity
import kirin3.jp.mljanken.webview.WebViewActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_buttle = findViewById(R.id.buttle) as Button
        val text_policy = findViewById(R.id.policy) as TextView

        btn_buttle.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, GameActivity::class.java)
                startActivity(intent)
            }
        })

        text_policy.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                // インテントのインスタンス生成
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)

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
