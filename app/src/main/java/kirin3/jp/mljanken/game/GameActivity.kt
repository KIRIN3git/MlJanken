package kirin3.jp.mljanken.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.LogUtils
import android.view.MotionEvent
import android.os.Handler
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.ads.AdView
import com.google.firebase.FirebaseApp
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.*




class GameActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var mContext = applicationContext

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView) as AdView)
    }


}