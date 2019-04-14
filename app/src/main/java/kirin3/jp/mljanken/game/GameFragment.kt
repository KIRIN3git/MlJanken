package kirin3.jp.mljanken.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.data.HandHelper
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import java.util.*

class GameFragment : Fragment(), Animator.AnimatorListener {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var imgManGu: ImageView? = null
    var imgManChoki: ImageView? = null
    var imgManPa: ImageView? = null
    var imgResult: ImageView? = null

    val NOTHING = 0
    val GU = 1
    val CHOKI = 2
    val PA = 3

    val WIN = 1
    val DROW = 2
    val LOSE = 3

    var manChoice = NOTHING
    var roboChoice = NOTHING

    private var mActivity: Activity? = null
    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = getActivity();
        mDbHelper = HandHelper(mActivity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mDbHelper?.saveData(mDb!!,mActivity!!.applicationContext,3,2,3,3,1,1)
        mDbHelper?.saveData(mDb!!,mActivity!!.applicationContext,6,6,3,3,1,1)
        var arrayHand = mDbHelper?.readData(mDb)
        for( i in 0 until arrayHand!!.size ){
            LOGD(TAG, "gettttttt : " + arrayHand[i]?.id )
            LOGD(TAG, "gettttttt : " + arrayHand[i]?.hand_id )
        }



        // CloudFirestore初期化
        var db = CloudFirestoreHelper.getInitDb(mActivity!!.applicationContext)

        val user1 = CloudFirestoreHelper.UserItem("Ichiro","Suzuki",55,true,"TOKYO")
        val user2 = CloudFirestoreHelper.UserItem("Junji","Takada",18,false,"OSAKA")
        val user3 = CloudFirestoreHelper.UserItem("Hanako","Yoshida",17,true,"TOKYO")
        val user4 = CloudFirestoreHelper.UserItem("Toshio","Sato",25,false,"TOKYO")
        val user5 = CloudFirestoreHelper.UserItem("Yoshiko","Imai",30,false,"OSAKA")
        val user6 = CloudFirestoreHelper.UserItem("Taro","Nagase",43,false,"TOKYO")

        val hobby1 = CloudFirestoreHelper.HobbyItem("Ski",3)

        CloudFirestoreHelper.addData(db,user1,"users","u001")
        CloudFirestoreHelper.addData(db,user2,"users","u002")
        CloudFirestoreHelper.addData(db,user3,"users","u003")
        CloudFirestoreHelper.addData(db,user4,"users","u004")
        CloudFirestoreHelper.addData(db,user5,"users","u005")
        CloudFirestoreHelper.addData(db,user6,"users","u006")
        CloudFirestoreHelper.addDataSecondCollection(db,hobby1,"users","u001","hobby","h001")

        CloudFirestoreHelper.getData(db,"users","u001")
        CloudFirestoreHelper.getDataSecondCollection(db,"users","u001","hobby","h001")
        CloudFirestoreHelper.getDataAll(db,"users")
        CloudFirestoreHelper.getDataConditions(db,"users")


        var sCname: String = LogUtils.makeLogTag(GameActivity::class.java);

        imgManGu = view.findViewById(R.id.imgManGu) as ImageView?
        imgManChoki = view.findViewById(R.id.imgManChoki) as ImageView?
        imgManPa = view.findViewById(R.id.imgManPa) as ImageView?

        // タッチ処理
        imgManGu?.setOnTouchListener(ImageViewEvent())
        imgManChoki?.setOnTouchListener(ImageViewEvent())
        imgManPa?.setOnTouchListener(ImageViewEvent())


        ChangeJankenImg(view)
        ThinkRobo()

    }

    fun ThinkRobo() {
        val r = Random()
        roboChoice = r.nextInt(3) + 1
    }

    fun JudgeJanken():Int{
        var judge:Int
        if( manChoice == GU ){
            if( roboChoice == GU ) judge = DROW
            else if( roboChoice == CHOKI ) judge = WIN
            else judge = LOSE
        } else if( manChoice == CHOKI ){
            if( roboChoice == GU ) judge = LOSE
            else if( roboChoice == CHOKI ) judge = DROW
            else judge = WIN
        } else{ // PA
            if( roboChoice == GU ) judge = WIN
            else if( roboChoice == CHOKI ) judge = LOSE
            else judge = DROW
        }

        return judge
    }


    fun ChangeJankenImg(view:View) {
        val handler = Handler()
        var runnable:Runnable? = null

        // 0:jan,1:janken,2:jankenpon,3:robo
        var situation = 1
        // あいこ判定
        var drow_flg = false

        runnable = Runnable {
            val imgJankenpon = view.findViewById(R.id.jankenpon) as ImageView?
            val imgRobArm = view.findViewById(R.id.robo_arm) as ImageView?
            val imgResult = view.findViewById(R.id.result) as ImageView?
            val lottieFireWork1 = view.findViewById(R.id.fireworks1) as LottieAnimationView?

            if( situation == 1 ){
                imgJankenpon?.visibility = View.VISIBLE
                imgRobArm?.visibility = View.GONE
                if(drow_flg == false ) imgJankenpon?.setImageResource(R.drawable.janken)
                else imgJankenpon?.setImageResource(R.drawable.aikode)
                situation++
                handler.postDelayed(runnable, 1000)
            } else if( situation == 2){
                if(drow_flg == false ) imgJankenpon?.setImageResource(R.drawable.jankenpon)
                else imgJankenpon?.setImageResource(R.drawable.aikodesho)
                situation++
                handler.postDelayed(runnable, 1000)
            } else if( situation == 3 && manChoice != NOTHING && roboChoice != NOTHING ){

                imgJankenpon?.visibility = View.GONE
                imgRobArm?.visibility = View.VISIBLE
                if( roboChoice == GU ) imgRobArm?.setImageResource(R.drawable.robo_gu)
                else if( roboChoice == CHOKI ) imgRobArm?.setImageResource(R.drawable.robo_choki)
                else if( roboChoice == PA ) imgRobArm?.setImageResource(R.drawable.robo_pa)

                // PropertyValuesHolderを使ってＸ軸方向移動範囲のpropertyを保持
                val vhX = PropertyValuesHolder.ofFloat("translationX", 0.0f, 0.0f)
                // PropertyValuesHolderを使ってＹ軸方向移動範囲のpropertyを保持
                val vhY = PropertyValuesHolder.ofFloat("translationY", 0.0f, 700.0f)
                // PropertyValuesHolderを使って回転範囲のpropertyを保持
                val vhRotaion = PropertyValuesHolder.ofFloat("rotation", 0.0f, 0.0f)

                var objectAnimator: ObjectAnimator? = null

                // ObjectAnimatorにセットする
                objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imgRobArm, vhX, vhY, vhRotaion)

                // 再生時間を設定 1000msec=1sec
                objectAnimator.setDuration(300)

                // リスナーの追加
                objectAnimator.addListener(this)

                // アニメーションを開始する
                objectAnimator.start()

                situation++
                handler.postDelayed(runnable, 1000)
            } else if( situation == 4){
                var judge = JudgeJanken()
                if(judge == WIN){
                    imgResult?.setImageResource(R.drawable.mark_maru)
                    lottieFireWork1?.visibility = View.VISIBLE
                }
                else if(judge == LOSE) imgResult?.setImageResource(R.drawable.mark_batsu)
                else{ // DROW
                    drow_flg = true
                    situation = 1
                    imgJankenpon?.visibility = View.VISIBLE
                    imgRobArm?.visibility = View.GONE
                    imgJankenpon?.setImageResource(R.drawable.ai)
                    handler.postDelayed(runnable, 1000)
                }

                imgResult?.visibility = View.VISIBLE

            }
            else handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    inner class ImageViewEvent : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN ->{
                    if( v == imgManGu){
                        (v as ImageView).setImageResource(R.drawable.man_gu2)
                        imgManChoki?.setImageResource(R.drawable.man_choki1)
                        imgManPa?.setImageResource(R.drawable.man_pa1)
                        manChoice = GU
                    }
                    else if( v == imgManChoki){
                        (v as ImageView).setImageResource(R.drawable.man_choki2)
                        imgManGu?.setImageResource(R.drawable.man_gu1)
                        imgManPa?.setImageResource(R.drawable.man_pa1)
                        manChoice = CHOKI
                    }
                    else{
                        (v as ImageView).setImageResource(R.drawable.man_pa2)
                        imgManGu?.setImageResource(R.drawable.man_gu1)
                        imgManChoki?.setImageResource(R.drawable.man_choki1)
                        manChoice = PA
                    }
                    return true;
                }
                MotionEvent.ACTION_UP ->{
                    if( v == imgManGu){
                        (v as ImageView).setImageResource(R.drawable.man_gu3)
                        imgManChoki?.setImageResource(R.drawable.man_choki1)
                        imgManPa?.setImageResource(R.drawable.man_pa1)
                    }
                    else if( v == imgManChoki){
                        (v as ImageView).setImageResource(R.drawable.man_choki3)
                        imgManGu?.setImageResource(R.drawable.man_gu1)
                        imgManPa?.setImageResource(R.drawable.man_pa1)
                    }
                    else{
                        (v as ImageView).setImageResource(R.drawable.man_pa3)
                        imgManGu?.setImageResource(R.drawable.man_gu1)
                        imgManChoki?.setImageResource(R.drawable.man_choki1)
                    }
                }
                MotionEvent.ACTION_CANCEL -> {
                }
                MotionEvent.ACTION_MASK -> {
                }
                MotionEvent.ACTION_MOVE -> {
                }
            }
            return false
        }
    }

    // アニメーション開始で呼ばれる
    override fun onAnimationStart(animation: Animator) {
    }

    // アニメーションがキャンセルされると呼ばれる
    override fun onAnimationCancel(animation: Animator) {
    }

    // アニメーション終了時
    override fun onAnimationEnd(animation: Animator) {
    }

    // 繰り返しでコールバックされる
    override fun onAnimationRepeat(animation: Animator) {
    }

}