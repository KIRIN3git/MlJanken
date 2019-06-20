package kirin3.jp.mljanken.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
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
import kirin3.jp.mljanken.Config
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.mng.databaseMng.HandHelper
import kirin3.jp.mljanken.game.GameData.CHOKI
import kirin3.jp.mljanken.game.GameData.DROW
import kirin3.jp.mljanken.game.GameData.LOSE
import kirin3.jp.mljanken.game.GameData.NOTHING
import kirin3.jp.mljanken.game.GameData.PAA
import kirin3.jp.mljanken.game.GameData.WIN
import kirin3.jp.mljanken.mng.SoundMng
import kirin3.jp.mljanken.util.*
import kirin3.jp.mljanken.util.LogUtils.LOGD
import java.util.*
import kirin3.jp.mljanken.game.GameData.GUU as GUU1

class GameFragment : Fragment(), Animator.AnimatorListener {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private lateinit var imgManGu: ImageView
    private lateinit var imgManChoki: ImageView
    private lateinit var imgManPa: ImageView
    private lateinit var imgJankenpon: ImageView
    private lateinit var imgRobArm: ImageView
    private lateinit var imgResult: ImageView
    private lateinit var lottieRetry: LottieAnimationView
    private lateinit var lottieWinStar: LottieAnimationView
    private lateinit var lottieFireWork: LottieAnimationView

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    var manChoice = NOTHING
    var roboChoice = NOTHING

    // 画像のタッチ判定可能フラグ
    var imgTouchOkFlg = false

    var mode = 0

    private var appContext: Context? = null
    private var dbHelper: HandHelper? = null
    private var db: SQLiteDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appContext = activity?.applicationContext
        dbHelper = HandHelper(activity!!.applicationContext)
        db = dbHelper?.getWritableDatabase()

        handler = Handler()
        SoundMng.soundInit(appContext!!)

        if( GameCloudFirestoreHelper.dataExisting == false ){
            // CloudFirestoreのデータを事前取得
            var db = CloudFirestoreHelper.getInitDb(appContext!!)
            GameCloudFirestoreHelper.getGameData(db,"users",appContext!!)
        }

        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgManGu = view.findViewById(R.id.imgManGu) as ImageView
        imgManChoki = view.findViewById(R.id.imgManChoki) as ImageView
        imgManPa = view.findViewById(R.id.imgManPa) as ImageView
        imgJankenpon = view.findViewById(R.id.jankenpon) as ImageView
        imgRobArm = view.findViewById(R.id.robo_arm) as ImageView
        imgResult = view.findViewById(R.id.totalization) as ImageView
        lottieRetry = view.findViewById(R.id.retry) as LottieAnimationView

        lottieRetry.setOnClickListener {
            LOGD(TAG, "lottieRetry CLICK")
            playGame(view)
        }

        // タッチ処理
        imgManGu.setOnTouchListener(imageViewEvent())
        imgManChoki.setOnTouchListener(imageViewEvent())
        imgManPa.setOnTouchListener(imageViewEvent())
    }

    fun playGame(view: View) {
        LOGD(TAG, "playGame")

        SoundMng.soundStopFireWork()
        changeJankenImg(view)
    }

    override fun onStart() {
        LOGD(TAG, "onStart")

        super.onStart()
        playGame(view!!)
    }


    override fun onPause() {
        LOGD(TAG, "onPause")

        super.onPause()
        handler?.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()

        LOGD(TAG, "onDestroy")

        SoundMng.soundEnd()

        var db = CloudFirestoreHelper.getInitDb(appContext!!)

        // アクティビティ破棄時にプリファランスとデータベースに設定
        // (注)Float型は誤差が出てしまうので注意
        val user = CloudFirestoreHelper.UserItem(
            SettingsUtils.getSettingRadioIdGender(appContext!!),
            SettingsUtils.getSettingRadioIdAge(appContext!!),
            SettingsUtils.getSettingRadioIdPrefecture(appContext!!),
            SettingsUtils.getSettingBattleNum(appContext!!),
            SettingsUtils.getSettingWinNum(appContext!!),
            SettingsUtils.getSettingDrowNum(appContext!!),
            SettingsUtils.getSettingLoseNum(appContext!!),
            SettingsUtils.getSettingProbability(appContext!!),
            SettingsUtils.getSettingMaxChainWinNum(appContext!!),
            SettingsUtils.getSettingMaxChainLoseNum(appContext!!),
            dbHelper?.getMostChoice(this.db)!!,
            dbHelper?.getFirstChoice(this.db)!!,
            dbHelper?.getMostChainChoice(this.db)!!,
            dbHelper?.getExcellenceMode(this.db)!!,
            dbHelper?.getModeProbability(this.db, 1)!!,
            dbHelper?.getModeProbability(this.db, 2)!!,
            dbHelper?.getModeProbability(this.db, 3)!!,
            dbHelper?.getModeProbability(this.db, 4)!!,
            dbHelper?.getModeProbability(this.db, 5)!!,
            dbHelper?.getModeProbability(this.db, 6)!!,
            dbHelper?.getModeProbability(this.db, 7)!!,
            dbHelper?.getModeProbability(this.db, 8)!!,
            dbHelper?.getModeProbability(this.db, 9)!!,
            Config.IS_DOGFOOD_BUILD
        )

        CloudFirestoreHelper.addUserData(db, user, "users", SettingsUtils.getSettingUuid(appContext!!))
    }

    fun thinkRobo() {
        roboChoice = NOTHING

        val r = Random()
        // 初戦なら特別にCloudFirestoreのデータからモード選択
        if (SettingsUtils.getSettingBattleNum(appContext!!) == 0){
            LOGD(TAG, "thinkRobo FIRST BATTLE")
            mode = r.nextInt(5) + 4
        }
        // 1/3で 最強モード or ランダムでモード選択
        else {
            var judge = r.nextInt(3) + 1
            if (judge == 1) { // 最強のモードを採用
                LOGD(TAG, "thinkRobo CHOICE VEST")
                mode = dbHelper?.getExcellenceMode(db)!!
            } else { // その他のモードを採用
                LOGD(TAG, "thinkRobo CHOICE ATHER")
                mode = r.nextInt(GameData.MODE_NUM) + 1
            }
        }

        LOGD(TAG, "thinkRobo mode:" + mode)
        // ・内部DBの情報から判断
        // 最もユーザーに勝利している手に勝つ手
        if (mode == GameData.MOST_WIN_MODE) roboChoice = dbHelper?.getMostChoice(db)!!
        // 最も連勝中のユーザーに勝利している手に勝つ手
        else if (mode == GameData.MOST_CHAIN_WIN_MODE) roboChoice = dbHelper?.getMostChainChoice(db)!!
        // ・CloudFirestoreの情報から判断
        // CloudFirestoreからデータ取得済みなら
        if (GameCloudFirestoreHelper.dataExisting == true) {
            // 最もユーザーの性別の人が出している手に勝つ手
            if (mode == GameData.MOST_GENDER_CHOICE_MODE){
                roboChoice = GameUtils.choiceWinHand(getMapTopKey(GameCloudFirestoreHelper.mostChoiceGender.toList().sortedByDescending { (_, value) -> value }.toMap()))
            }
            // 最もユーザーの年代の人が出している手に勝つ手
            if (mode == GameData.MOST_AGE_CHOICE_MODE){
                roboChoice = GameUtils.choiceWinHand(getMapTopKey(GameCloudFirestoreHelper.mostChoiceAge.toList().sortedByDescending { (_, value) -> value }.toMap()))
            }
            // 最もユーザーの地域の人が出している手に勝つ手
            if (mode == GameData.MOST_PREFECTURE_CHOICE_MODE){
                roboChoice = GameUtils.choiceWinHand(getMapTopKey(GameCloudFirestoreHelper.mostChoicePrefecture.toList().sortedByDescending { (_, value) -> value }.toMap()))
            }

            // 最もユーザーの性別の人が最初に出している手に勝つ手
            if (mode == GameData.MOST_GENDER_FIRST_CHOICE_MODE){
                roboChoice = GameUtils.choiceWinHand(getMapTopKey(GameCloudFirestoreHelper.firstChoiceGender.toList().sortedByDescending { (_, value) -> value }.toMap()))
            }
            // 最もユーザーの年代の人が最初に出している手に勝つ手
            if (mode == GameData.MOST_AGE_FIRST_CHOICE_MODE){
                roboChoice = GameUtils.choiceWinHand(getMapTopKey(GameCloudFirestoreHelper.firstChoiceAge.toList().sortedByDescending { (_, value) -> value }.toMap()))
            }
            // 最もユーザーの地域の人が最初に出している手に勝つ手
            if (mode == GameData.MOST_PREFECTURE_FIRST_CHOICE_MODE){
                roboChoice = GameUtils.choiceWinHand(getMapTopKey(GameCloudFirestoreHelper.firstChoicePrefecture.toList().sortedByDescending { (_, value) -> value }.toMap()))
            }
        }

        if(roboChoice == 0){
            LOGD(TAG, "thinkRobo RANDOM_MODE")
            mode = GameData.RANDOM_MODE
            // ランダムで手を選択
            roboChoice = r.nextInt(3) + 1
        }
        LOGD(TAG, "thinkRobo roboChoice:" + roboChoice)
    }

    fun getMapTopKey(map:Map<Int,Int> ):Int{
        var choice = 0

        for(entry in map){
            if( entry.value != 0 ) choice = entry.key
            break
        }
        return choice
    }


    fun judgeJanken(): Int {
        var judge: Int
        if (manChoice == GUU1) {
            if (roboChoice == GUU1) judge = DROW
            else if (roboChoice == CHOKI) judge = WIN
            else judge = LOSE
        } else if (manChoice == CHOKI) {
            if (roboChoice == GUU1) judge = LOSE
            else if (roboChoice == CHOKI) judge = DROW
            else judge = WIN
        } else { // PAA
            if (roboChoice == GUU1) judge = WIN
            else if (roboChoice == CHOKI) judge = LOSE
            else judge = DROW
        }

        return judge
    }

    fun displayWinStar() {
        val num = SettingsUtils.getSettingNowChainWinNum(appContext!!)

        LOGD(TAG, "displayWinStar:" + num);

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

            lottieWinStar = view?.findViewById(id) as LottieAnimationView
            lottieWinStar?.visibility = View.VISIBLE
            lottieWinStar?.playAnimation()
        }
    }

    fun hiddenWinStar() {
        val num = SettingsUtils.getSettingMaxChainWinNum(appContext!!)
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

            lottieWinStar = view?.findViewById(id) as LottieAnimationView
            lottieWinStar?.visibility = View.GONE
        }
    }

    fun addWinStar() {
        val num = SettingsUtils.getSettingNowChainWinNum(appContext!!)

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

        lottieWinStar = view?.findViewById(id) as LottieAnimationView
        lottieWinStar?.visibility = View.VISIBLE
        lottieWinStar?.playAnimation()
    }


    fun displayFireWork() {
        val num = SettingsUtils.getSettingNowChainWinNum(appContext!!)

        LOGD(TAG, "displayFireWork getSettingNowChainWinNum:" + num);

        // 音を再生
        val now_chain_win_num = SettingsUtils.getSettingNowChainWinNum(appContext!!)
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

            lottieFireWork = view?.findViewById(id) as LottieAnimationView
            lottieFireWork?.visibility = View.VISIBLE
            lottieFireWork?.playAnimation()
        }
    }

    fun hiddenFireWork() {

        LOGD(TAG, "hiddenFireWork");

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

            lottieFireWork = view?.findViewById(id) as LottieAnimationView
            lottieFireWork?.visibility = View.GONE
        }
    }

    fun changeJankenImg(view: View) {

        // 0:jan,1:janken,2:jankenpon,3:robo_arm,4:Judge
        var situation = 0
        // あいこ状態
        var drow_flg = false

        imgJankenpon?.visibility = View.GONE
        imgRobArm?.visibility = View.GONE
        imgResult?.visibility = View.GONE
        lottieRetry?.visibility = View.GONE
        hiddenFireWork()
        displayWinStar()


        runnable = Runnable {

            // じゃん or あい
            if (situation == 0) {
                clearJankenImg()
                imgTouchOkFlg = true
                if (drow_flg == false) {
                    imgJankenpon.setImageResource(R.drawable.jan)
                    SoundMng.playSoundJankenpon()
                } else {
                    imgJankenpon.setImageResource(R.drawable.ai)
                    SoundMng.playSoundAikodesho()
                }
                imgJankenpon.visibility = View.VISIBLE

                situation++
                // 0.8秒静止
                handler.postDelayed(runnable, 800)
            }
            // じゃんけん or あいこで
            else if (situation == 1) {

                if (drow_flg == false) imgJankenpon.setImageResource(R.drawable.janken)
                else imgJankenpon.setImageResource(R.drawable.aikode)
                situation++
                // 1秒静止
                handler.postDelayed(runnable, 500)
            }
            // じゃんけんぽん or あいこでしょ
            else if (situation == 2) {
                // ロボの手を考える
                thinkRobo()

                if (drow_flg == false) imgJankenpon.setImageResource(R.drawable.jankenpon)
                else imgJankenpon.setImageResource(R.drawable.aikodesho)
                situation++
                // 1秒静止
                handler.postDelayed(runnable, 1000)
            }
            // 人とロボが選択済みなら、ロボの手を出す。
            else if (situation == 3 && manChoice != NOTHING && roboChoice != NOTHING) {
                imgTouchOkFlg = false
                imgJankenpon.visibility = View.GONE
                imgRobArm.visibility = View.VISIBLE
                if (roboChoice == GUU1) {
                    imgRobArm.setImageResource(R.drawable.robo_gu)
                    SoundMng.playSoundGoo()
                } else if (roboChoice == CHOKI) {
                    imgRobArm.setImageResource(R.drawable.robo_choki)
                    SoundMng.playSoundChoki()
                } else if (roboChoice == PAA) {
                    imgRobArm.setImageResource(R.drawable.robo_pa)
                    SoundMng.playSoundPaa()
                }

                // PropertyValuesHolderを使ってＸ軸方向移動範囲のpropertyを保持
                val vhX = PropertyValuesHolder.ofFloat("translationX", 0.0f, 0.0f)
                // PropertyValuesHolderを使ってＹ軸方向移動範囲のpropertyを保持

                val vhY = PropertyValuesHolder.ofFloat("translationY", 0.0f, ViewUtils.dpToPx(250f,appContext!!.getResources()))
                // PropertyValuesHolderを使って回転範囲のpropertyを保持
                val vhRotaion = PropertyValuesHolder.ofFloat("rotation", 0.0f, 0.0f)

                // ObjectAnimatorにセットする
                var objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imgRobArm, vhX, vhY, vhRotaion)

                // アニメーション再生時間を設定 1000msec=1sec
                objectAnimator.setDuration(300)

                // リスナーの追加
                objectAnimator.addListener(this)

                // アニメーションを開始する
                objectAnimator.start()

                situation++

                // 1秒静止
                handler.postDelayed(runnable, 1000)
            }
            // 判定
            else if (situation == 4) {
                // アナリティクス用結果文字列
                var result = ""

                var judge = judgeJanken()
                if (judge == WIN) {
                    result = "WIN"

                    // 正解音
                    SoundMng.playSoundCorrect()
                    imgResult.setImageResource(R.drawable.mark_maru)
                    imgResult.visibility = View.VISIBLE
                    lottieRetry.visibility = View.VISIBLE
                    lottieRetry.playAnimation()
                    // 総合勝ち数を+1
                    SettingsUtils.setSettingWinNum(appContext!!, SettingsUtils.getSettingWinNum(appContext!!) + 1)
                    // 現在連勝数を+1
                    SettingsUtils.setSettingNowChainWinNum(
                        appContext!!,
                        SettingsUtils.getSettingNowChainWinNum(appContext!!) + 1
                    )
                    // 現在連勝数が最大連勝数を超えたら、最大連勝数を更新
                    if (SettingsUtils.getSettingNowChainWinNum(appContext!!) > SettingsUtils.getSettingMaxChainWinNum(appContext!!)){
                        SettingsUtils.setSettingMaxChainWinNum(appContext!!,SettingsUtils.getSettingNowChainWinNum(appContext!!))
                    }
                    // 現在連敗数を0
                    SettingsUtils.setSettingNowChainLoseNum(appContext!!, 0)

                    addWinStar()
                    displayFireWork()
                } else if (judge == LOSE) {
                    result = "LOSE"

                    // 不正解音音
                    SoundMng.playSoundMistake()
                    imgResult.setImageResource(R.drawable.mark_batsu)
                    imgResult.visibility = View.VISIBLE
                    lottieRetry.visibility = View.VISIBLE
                    lottieRetry.playAnimation()
                    // 総合負け数を+1
                    SettingsUtils.setSettingLoseNum(appContext!!, SettingsUtils.getSettingLoseNum(appContext!!) + 1)
                    // 現在連負数を+1
                    SettingsUtils.setSettingNowChainLoseNum(appContext!!,SettingsUtils.getSettingNowChainLoseNum(appContext!!) + 1)
                    // 現在連負数が最大連負数を超えたら、最大連負数を更新
                    if (SettingsUtils.getSettingNowChainLoseNum(appContext!!) > SettingsUtils.getSettingMaxChainLoseNum(appContext!!)){
                        SettingsUtils.setSettingMaxChainLoseNum(appContext!!,SettingsUtils.getSettingNowChainLoseNum(appContext!!))
                    }
                    // 現在連勝数を0
                    SettingsUtils.setSettingNowChainWinNum(appContext!!, 0)

                    hiddenWinStar()

                } else { // DROW
                    result = "DROW"

                    drow_flg = true
                    imgRobArm.visibility = View.GONE
                    // 総合あいこ数を+1
                    SettingsUtils.setSettingDrowNum(appContext!!, SettingsUtils.getSettingDrowNum(appContext!!) + 1)

                    situation = 0
                    handler.postDelayed(runnable, 500)
                }

                // 勝負数を+1
                SettingsUtils.setSettingBattleNum(appContext!!, SettingsUtils.getSettingBattleNum(appContext!!) + 1)

                // DBに結果を保存
                dbHelper?.saveData(
                    db!!,
                    activity!!.applicationContext,
                    manChoice,
                    judge,
                    mode,
                    SettingsUtils.getSettingWinNum(appContext!!),
                    SettingsUtils.getSettingDrowNum(appContext!!),
                    SettingsUtils.getSettingLoseNum(appContext!!),
                    SettingsUtils.getSettingNowChainWinNum(appContext!!),
                    SettingsUtils.getSettingNowChainLoseNum(appContext!!)
                )

                // アナリティクスにデータを送信
                var gender = SettingsUtils.genderItems[SettingsUtils.getSettingRadioIdGender(appContext!!)]
                var age = SettingsUtils.ageItems[SettingsUtils.getSettingRadioIdAge(appContext!!)]
                var prefecture = SettingsUtils.prefectureItems[SettingsUtils.getSettingRadioIdPrefecture(appContext!!)]



                AnalyticsHelper.setAnalyticsJanken(result,gender,age,prefecture)
            }
            // 1秒ごとに再判定
            else handler.postDelayed(runnable, 1000)
        }
        // 画面が出て最初の1秒表示
        handler?.postDelayed(runnable, 1)
    }

    /**
     * ジャンケンイメージをタッチしたときの挙動
     */
    inner class imageViewEvent : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            // デバックモードでないとチート禁止
            if( imgTouchOkFlg == false && Config.IS_DOGFOOD_BUILD == false ) return true

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (v == imgManGu) {
                        (v as ImageView).setImageResource(R.drawable.man_gu2)
                        imgManChoki.setImageResource(R.drawable.man_choki1)
                        imgManPa.setImageResource(R.drawable.man_pa1)
                        manChoice = GUU1
                    } else if (v == imgManChoki) {
                        (v as ImageView).setImageResource(R.drawable.man_choki2)
                        imgManGu.setImageResource(R.drawable.man_gu1)
                        imgManPa.setImageResource(R.drawable.man_pa1)
                        manChoice = CHOKI
                    } else {
                        (v as ImageView).setImageResource(R.drawable.man_pa2)
                        imgManGu.setImageResource(R.drawable.man_gu1)
                        imgManChoki.setImageResource(R.drawable.man_choki1)
                        manChoice = PAA
                    }
                    return true;
                }
                MotionEvent.ACTION_UP -> {
                    v.performClick()
                    if (v == imgManGu) {
                        (v as ImageView).setImageResource(R.drawable.man_gu3)
                        imgManChoki.setImageResource(R.drawable.man_choki1)
                        imgManPa.setImageResource(R.drawable.man_pa1)
                    } else if (v == imgManChoki) {
                        (v as ImageView).setImageResource(R.drawable.man_choki3)
                        imgManGu.setImageResource(R.drawable.man_gu1)
                        imgManPa.setImageResource(R.drawable.man_pa1)
                    } else {
                        (v as ImageView).setImageResource(R.drawable.man_pa3)
                        imgManGu.setImageResource(R.drawable.man_gu1)
                        imgManChoki.setImageResource(R.drawable.man_choki1)
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

    fun clearJankenImg(){
        manChoice = NOTHING
        imgManGu.setImageResource(R.drawable.man_gu1)
        imgManChoki.setImageResource(R.drawable.man_choki1)
        imgManPa.setImageResource(R.drawable.man_pa1)
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