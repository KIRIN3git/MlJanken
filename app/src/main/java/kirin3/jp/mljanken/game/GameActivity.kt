package kirin3.jp.mljanken.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.LogUtils

class GameActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var sCname: String = LogUtils.makeLogTag(GameActivity::class.java);

    }

    fun xxx(){

    }
}