package kirin3.jp.mljanken.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.top.TopActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGHT = 0.5 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            if (!isFinishing) {
                val intent = Intent(this@SplashActivity, TopActivity::class.java)
                startActivity(intent)
            }
        }, SPLASH_DISPLAY_LENGHT.toLong())
    }
}