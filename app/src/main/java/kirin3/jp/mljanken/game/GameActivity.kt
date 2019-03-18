package kirin3.jp.mljanken.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.LogUtils

class GameActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var sCname: String = LogUtils.makeLogTag(GameActivity::class.java);

        val img_man_gu = findViewById(R.id.imgManGu) as ImageView
        val img_man_choki = findViewById(R.id.imgManChoki) as ImageView
        val img_man_pa = findViewById(R.id.imgManPa) as ImageView

        choiceJanken(img_man_gu)
        choiceJanken(img_man_choki)
        choiceJanken(img_man_pa)

    }

    fun choiceJanken(img:ImageView){
        img.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                img.setBackgroundColor(ContextCompat.getColor(this@GameActivity, R.color.lightGreen))
            }
        })
    }
}