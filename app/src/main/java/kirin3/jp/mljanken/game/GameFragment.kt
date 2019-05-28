package kirin3.jp.mljanken.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
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
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.mng.databaseMng.HandHelper
import kirin3.jp.mljanken.game.GameData.CHOKI
import kirin3.jp.mljanken.game.GameData.DROW
import kirin3.jp.mljanken.game.GameData.LOSE
import kirin3.jp.mljanken.game.GameData.NOTHING
import kirin3.jp.mljanken.game.GameData.PAA
import kirin3.jp.mljanken.game.GameData.WIN
import kirin3.jp.mljanken.mng.SoundMng
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.*
import kirin3.jp.mljanken.game.GameData.GUU as GUU1

class GameFragment : Fragment(), Animator.AnimatorListener {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var sImgManGu: ImageView? = null
    var sImgManChoki: ImageView? = null
    var sImgManPa: ImageView? = null
    var sImgJankenpon: ImageView? = null
    var sImgRobArm: ImageView? = null
    var sImgResult: ImageView? = null
    var sLottieRetry: LottieAnimationView? = null
    var sLottieWinStar: LottieAnimationView? = null
    var sLottieFireWork: LottieAnimationView? = null

    var mHandler: Handler? = null
    var mRunnable: Runnable? = null

    var sManChoice = NOTHING
    var sRoboChoice = NOTHING

    var sMode = 0

    private var mContext: Context? = null
    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity?.applicationContext
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        mHandler = Handler()
        SoundMng.soundInit(mContext!!)

        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sImgManGu = view?.findViewById(R.id.imgManGu) as ImageView
        sImgManChoki = view?.findViewById(R.id.imgManChoki) as ImageView
        sImgManPa = view?.findViewById(R.id.imgManPa) as ImageView
        sImgJankenpon = view?.findViewById(R.id.jankenpon) as ImageView
        sImgRobArm = view?.findViewById(R.id.robo_arm) as ImageView
        sImgResult = view?.findViewById(R.id.totalization) as ImageView
        sLottieRetry = view?.findViewById(R.id.retry) as LottieAnimationView
        
        sLottieRetry?.setOnClickListener {
            LOGD(TAG, "sLottieRetry CLICK")
            playGame(view)
        }

        // タッチ処理
        sImgManGu?.setOnTouchListener(ImageViewEvent())
        sImgManChoki?.setOnTouchListener(ImageViewEvent())
        sImgManPa?.setOnTouchListener(ImageViewEvent())
    }

    fun playGame(view: View) {
        LOGD(TAG, "playGame")

        SoundMng.soundStopFireWork()
        ThinkRobo()
        ChangeJankenImg(view)
    }

    override fun onStart() {
        LOGD(TAG, "onStart")

        super.onStart()
        playGame(view!!)
    }


    override fun onPause() {
        LOGD(TAG, "onPause")

        super.onPause()
        mHandler?.removeCallbacks(mRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()

        LOGD(TAG, "onDestroy")

        SoundMng.soundEnd()

        var db = CloudFirestoreHelper.getInitDb(mContext!!)

        // アクティビティ破棄時にプリファランスとデータベースに設定
        val user = CloudFirestoreHelper.UserItem(
            SettingsUtils.getSettingRadioIdSex(mContext!!),
            SettingsUtils.getSettingRadioIdAge(mContext!!),
            SettingsUtils.getSettingRadioIdPrefecture(mContext!!),
            SettingsUtils.getSettingBattleNum(mContext!!),
            SettingsUtils.getSettingWinNum(mContext!!),
            SettingsUtils.getSettingDrowNum(mContext!!),
            SettingsUtils.getSettingLoseNum(mContext!!),
            SettingsUtils.getSettingProbability(mContext!!),
            SettingsUtils.getSettingMaxChainWinNum(mContext!!),
            SettingsUtils.getSettingMaxChainLoseNum(mContext!!),
            mDbHelper?.getMostChoice(mDb)!!,
            mDbHelper?.getFirstChoice(mDb)!!,
            mDbHelper?.getMostChainChoice(mDb)!!,
            mDbHelper?.getExcellenceMode(mDb)!!,
            mDbHelper?.getModeProbability(mDb, 1)!!,
            mDbHelper?.getModeProbability(mDb, 2)!!,
            mDbHelper?.getModeProbability(mDb, 3)!!,
            mDbHelper?.getModeProbability(mDb, 4)!!,
            mDbHelper?.getModeProbability(mDb, 5)!!,
            mDbHelper?.getModeProbability(mDb, 6)!!,
            mDbHelper?.getModeProbability(mDb, 7)!!
        )

        CloudFirestoreHelper.addUserData(db, user, "users", SettingsUtils.getSettingUuid(mContext!!))
    }

    fun ThinkRobo() {
        val r = Random()
        // 1/2で分岐
        val judge = r.nextInt(2) + 1
        if (judge == 1) { // 最強のモードを採用
            sMode = mDbHelper?.getExcellenceMode(mDb)!!
        } else { // ランダムでモードを採用
            sMode = r.nextInt(GameData.MODE_NUM) + 1
        }

        LOGD(TAG, "ThinkRobo mode:" + sMode);

        if (sMode == GameData.MOST_WIN_MODE) sRoboChoice = mDbHelper?.getMostChoice(mDb)!!
        else if (sMode == GameData.MOST_CHAIN_WIN_MODE) sRoboChoice = mDbHelper?.getMostChoice(mDb)!!
        // ランダムで手を選択
        else sRoboChoice = r.nextInt(3) + 1
    }

    fun JudgeJanken(): Int {
        var judge: Int
        if (sManChoice == GUU1) {
            if (sRoboChoice == GUU1) judge = DROW
            else if (sRoboChoice == CHOKI) judge = WIN
            else judge = LOSE
        } else if (sManChoice == CHOKI) {
            if (sRoboChoice == GUU1) judge = LOSE
            else if (sRoboChoice == CHOKI) judge = DROW
            else judge = WIN
        } else { // PAA
            if (sRoboChoice == GUU1) judge = WIN
            else if (sRoboChoice == CHOKI) judge = LOSE
            else judge = DROW
        }

        return judge
    }

    fun DisplayWinStar() {
        val num = SettingsUtils.getSettingNowChainWinNum(mContext!!)

        LOGD(TAG, "DisplayWinStar:" + num);

        for (i in 0 until num) {
            // 最大7個
            if (i > 6) break

            var id = 0
            when (i) {
                1 -> id = R.id.wstar2
                2 -> id = R.id.wstar3
                3 -> id = R.id.wstar4
                4 -> id = R.id.wstar5
                5 -> id = R.id.wstar6
                6 -> id = R.id.wstar7
                else -> id = R.id.wstar1
            }

            LOGD(TAG, "DisplayWinStar:");
            sLottieWinStar = view?.findViewById(id) as LottieAnimationView
            sLottieWinStar?.visibility = View.VISIBLE
            sLottieWinStar?.playAnimation()
        }
    }

    fun HiddenWinStar() {
        val num = SettingsUtils.getSettingMaxChainWinNum(mContext!!)
        for (i in 0 until num) {
            // 最大7個
            if (i > 6) break

            var id = 0
            when (i) {
                1 -> id = R.id.wstar2
                2 -> id = R.id.wstar3
                3 -> id = R.id.wstar4
                4 -> id = R.id.wstar5
                5 -> id = R.id.wstar6
                6 -> id = R.id.wstar7
                else -> id = R.id.wstar1
            }

            sLottieWinStar = view?.findViewById(id) as LottieAnimationView
            sLottieWinStar?.visibility = View.GONE
        }
    }

    fun AddWinStar() {
        val num = SettingsUtils.getSettingNowChainWinNum(mContext!!)

        var id = 0
        when (num) {
            2 -> id = R.id.wstar2
            3 -> id = R.id.wstar3
            4 -> id = R.id.wstar4
            5 -> id = R.id.wstar5
            6 -> id = R.id.wstar6
            7 -> id = R.id.wstar7
            else -> id = R.id.wstar1
        }

        sLottieWinStar = view?.findViewById(id) as LottieAnimationView
        sLottieWinStar?.visibility = View.VISIBLE
        sLottieWinStar?.playAnimation()
    }


    fun DisplayFireWork() {
        val num = SettingsUtils.getSettingNowChainWinNum(mContext!!)

        LOGD(TAG, "DisplayFireWork getSettingNowChainWinNum:" + num);

        // 音を再生
        val now_chain_win_num = SettingsUtils.getSettingNowChainWinNum(mContext!!)
        if (now_chain_win_num > 0) SoundMng.playSoundFirework1()
        if (now_chain_win_num > 2) SoundMng.playSoundFirework2()
        if (now_chain_win_num > 4) SoundMng.playSoundFirework3()
        if (now_chain_win_num > 6) SoundMng.playSoundFirework4()

        for (i in 0 until num) {
            // 最大7個
            if (i > 6) break

            var id = 0
            when (i) {
                1 -> id = R.id.fireworks2
                2 -> id = R.id.fireworks3
                3 -> id = R.id.fireworks4
                4 -> id = R.id.fireworks5
                5 -> id = R.id.fireworks6
                6 -> id = R.id.fireworks7
                else -> id = R.id.fireworks1
            }

            sLottieFireWork = view?.findViewById(id) as LottieAnimationView
            sLottieFireWork?.visibility = View.VISIBLE
            sLottieFireWork?.playAnimation()
        }
    }

    fun HiddenFireWork() {

        LOGD(TAG, "HiddenFireWork");

        for (i in 0 until 7) {

            var id = 0
            when (i) {
                1 -> id = R.id.fireworks2
                2 -> id = R.id.fireworks3
                3 -> id = R.id.fireworks4
                4 -> id = R.id.fireworks5
                5 -> id = R.id.fireworks6
                6 -> id = R.id.fireworks7
                else -> id = R.id.fireworks1
            }

            sLottieFireWork = view?.findViewById(id) as LottieAnimationView
            sLottieFireWork?.visibility = View.GONE
        }
    }

    fun ChangeJankenImg(view: View) {

        // 0:jan,1:janken,2:jankenpon,3:robo_arm,4:Judge
        var situation = 0
        // あいこ状態
        var drow_flg = false

        sImgJankenpon?.visibility = View.GONE
        sImgRobArm?.visibility = View.GONE
        sImgResult?.visibility = View.GONE
        sLottieRetry?.visibility = View.GONE
        HiddenFireWork()
        DisplayWinStar()


        mRunnable = Runnable {

            LOGD(TAG, "ChangeJankenImg situation:" + situation);

            // じゃん or あい
            if (situation == 0) {
                if (drow_flg == false) {
                    sImgJankenpon?.setImageResource(R.drawable.jan)
                    SoundMng.playSoundJankenpon()
                } else {
                    sImgJankenpon?.setImageResource(R.drawable.ai)
                    SoundMng.playSoundAikodesho()
                }
                sImgJankenpon?.visibility = View.VISIBLE

                situation++
                // 0.8秒静止
                mHandler?.postDelayed(mRunnable, 800)
            }
            // じゃんけん or あいこで
            else if (situation == 1) {
                LOGD(TAG, "situationb:" + situation);

                if (drow_flg == false) sImgJankenpon?.setImageResource(R.drawable.janken)
                else sImgJankenpon?.setImageResource(R.drawable.aikode)
                situation++
                // 1秒静止
                mHandler?.postDelayed(mRunnable, 500)
            }
            // じゃんけんぽん or あいこでしょ
            else if (situation == 2) {
                LOGD(TAG, "situationc:" + situation);
                if (drow_flg == false) sImgJankenpon?.setImageResource(R.drawable.jankenpon)
                else sImgJankenpon?.setImageResource(R.drawable.aikodesho)
                situation++
                // 1秒静止
                mHandler?.postDelayed(mRunnable, 1000)
            }
            // 人とロボが選択済みなら、ロボの手を出す。
            else if (situation == 3 && sManChoice != NOTHING && sRoboChoice != NOTHING) {

                sImgJankenpon?.visibility = View.GONE
                sImgRobArm?.visibility = View.VISIBLE
                if (sRoboChoice == GUU1) {
                    sImgRobArm?.setImageResource(R.drawable.robo_gu)
                    SoundMng.playSoundGoo()
                } else if (sRoboChoice == CHOKI) {
                    sImgRobArm?.setImageResource(R.drawable.robo_choki)
                    SoundMng.playSoundChoki()
                } else if (sRoboChoice == PAA) {
                    sImgRobArm?.setImageResource(R.drawable.robo_pa)
                    SoundMng.playSoundPaa()
                }

                // PropertyValuesHolderを使ってＸ軸方向移動範囲のpropertyを保持
                val vhX = PropertyValuesHolder.ofFloat("translationX", 0.0f, 0.0f)
                // PropertyValuesHolderを使ってＹ軸方向移動範囲のpropertyを保持
                val vhY = PropertyValuesHolder.ofFloat("translationY", 0.0f, 700.0f)
                // PropertyValuesHolderを使って回転範囲のpropertyを保持
                val vhRotaion = PropertyValuesHolder.ofFloat("rotation", 0.0f, 0.0f)

                // ObjectAnimatorにセットする
                var objectAnimator = ObjectAnimator.ofPropertyValuesHolder(sImgRobArm, vhX, vhY, vhRotaion)

                // アニメーション再生時間を設定 1000msec=1sec
                objectAnimator.setDuration(300)

                // リスナーの追加
                objectAnimator.addListener(this)

                // アニメーションを開始する
                objectAnimator.start()

                situation++

                // 1秒静止
                mHandler?.postDelayed(mRunnable, 1000)
            }
            // 判定
            else if (situation == 4) {
                LOGD(TAG, "situatione:" + situation);

                var judge = JudgeJanken()
                if (judge == WIN) {
                    sImgResult?.setImageResource(R.drawable.mark_maru)
                    sImgResult?.visibility = View.VISIBLE
                    sLottieRetry?.visibility = View.VISIBLE
                    sLottieRetry?.playAnimation()
                    // 総合勝ち数を+1
                    SettingsUtils.setSettingWinNum(mContext!!, SettingsUtils.getSettingWinNum(mContext!!) + 1)
                    // 現在連勝数を+1
                    SettingsUtils.setSettingNowChainWinNum(
                        mContext!!,
                        SettingsUtils.getSettingNowChainWinNum(mContext!!) + 1
                    )
                    // 現在連勝数が最大連勝数を超えたら、最大連勝数を更新
                    if (SettingsUtils.getSettingNowChainWinNum(mContext!!) > SettingsUtils.getSettingMaxChainWinNum(mContext!!)){
                        SettingsUtils.setSettingMaxChainWinNum(mContext!!,SettingsUtils.getSettingNowChainWinNum(mContext!!))
                    }
                    // 現在連勝数を0
                    SettingsUtils.setSettingNowChainWinNum(mContext!!, 0)

                    AddWinStar()
                    DisplayFireWork()
                } else if (judge == LOSE) {
                    sImgResult?.setImageResource(R.drawable.mark_batsu)
                    sImgResult?.visibility = View.VISIBLE
                    sLottieRetry?.visibility = View.VISIBLE
                    sLottieRetry?.playAnimation()
                    // 総合負け数を+1
                    SettingsUtils.setSettingLoseNum(mContext!!, SettingsUtils.getSettingLoseNum(mContext!!) + 1)
                    // 現在連負数を+1
                    SettingsUtils.setSettingNowChainLoseNum(mContext!!,SettingsUtils.getSettingNowChainLoseNum(mContext!!) + 1)
                    // 現在連負数が最大連負数を超えたら、最大連負数を更新
                    if (SettingsUtils.getSettingNowChainLoseNum(mContext!!) > SettingsUtils.getSettingMaxChainLoseNum(mContext!!)){
                        SettingsUtils.setSettingMaxChainLoseNum(mContext!!,SettingsUtils.getSettingNowChainLoseNum(mContext!!))
                    }
                    // 現在連負数を0
                    SettingsUtils.setSettingNowChainLoseNum(mContext!!, 0)

                    HiddenWinStar()

                } else { // DROW
                    drow_flg = true
                    sImgRobArm?.visibility = View.GONE
                    // ロボの手を再考
                    ThinkRobo()
                    // 総合あいこ数を+1
                    SettingsUtils.setSettingDrowNum(mContext!!, SettingsUtils.getSettingDrowNum(mContext!!) + 1)

                    situation = 0
                    mHandler?.postDelayed(mRunnable, 500)
                }

                // 勝負数を+1
                SettingsUtils.setSettingBattleNum(mContext!!, SettingsUtils.getSettingBattleNum(mContext!!) + 1)

                mDbHelper?.saveData(
                    mDb!!,
                    activity!!.applicationContext,
                    sManChoice,
                    judge,
                    sMode,
                    SettingsUtils.getSettingWinNum(mContext!!),
                    SettingsUtils.getSettingDrowNum(mContext!!),
                    SettingsUtils.getSettingLoseNum(mContext!!),
                    SettingsUtils.getSettingNowChainWinNum(mContext!!),
                    SettingsUtils.getSettingNowChainLoseNum(mContext!!)
                )
            }
            // 1秒ごとに再判定
            else mHandler?.postDelayed(mRunnable, 1000)
        }
        // 画面が出て最初の1秒表示
        mHandler?.postDelayed(mRunnable, 1)
    }

    /**
     * 人間の手をタッチしたときの挙動
     */
    inner class ImageViewEvent : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (v == sImgManGu) {
                        (v as ImageView)?.setImageResource(R.drawable.man_gu2)
                        sImgManChoki?.setImageResource(R.drawable.man_choki1)
                        sImgManPa?.setImageResource(R.drawable.man_pa1)
                        sManChoice = GUU1
                    } else if (v == sImgManChoki) {
                        (v as ImageView)?.setImageResource(R.drawable.man_choki2)
                        sImgManGu?.setImageResource(R.drawable.man_gu1)
                        sImgManPa?.setImageResource(R.drawable.man_pa1)
                        sManChoice = CHOKI
                    } else {
                        (v as ImageView)?.setImageResource(R.drawable.man_pa2)
                        sImgManGu?.setImageResource(R.drawable.man_gu1)
                        sImgManChoki?.setImageResource(R.drawable.man_choki1)
                        sManChoice = PAA
                    }
                    return true;
                }
                MotionEvent.ACTION_UP -> {
                    if (v == sImgManGu) {
                        (v as ImageView)?.setImageResource(R.drawable.man_gu3)
                        sImgManChoki?.setImageResource(R.drawable.man_choki1)
                        sImgManPa?.setImageResource(R.drawable.man_pa1)
                    } else if (v == sImgManChoki) {
                        (v as ImageView)?.setImageResource(R.drawable.man_choki3)
                        sImgManGu?.setImageResource(R.drawable.man_gu1)
                        sImgManPa?.setImageResource(R.drawable.man_pa1)
                    } else {
                        (v as ImageView)?.setImageResource(R.drawable.man_pa3)
                        sImgManGu?.setImageResource(R.drawable.man_gu1)
                        sImgManChoki?.setImageResource(R.drawable.man_choki1)
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