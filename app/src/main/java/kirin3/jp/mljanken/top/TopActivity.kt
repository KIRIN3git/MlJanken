package kirin3.jp.mljanken.top

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.ads.AdView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.AdmobHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.*


class TopActivity : AppCompatActivity() {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        // AdMob設定
        AdmobHelper.loadBanner(findViewById(R.id.adView) as AdView)

        // UUIDをプリファランスに登録
        // ☆☆
        /*
        if( SettingsUtils.getSettingUuid(this).isEmpty() ){
            SettingsUtils.setSettingUuid(this, UUID.randomUUID().toString())
        }
        */

        SettingsUtils.setSettingUuid(this, UUID.randomUUID().toString())

        if (SettingsUtils.getSettingRadioIdGender(this) == 0
            || SettingsUtils.getSettingRadioIdAge(this) == 0
            || SettingsUtils.getSettingRadioIdPrefecture(this) == 0
        ) showInfoDialog()

    }

    fun showInfoDialog() {
        val alertDlg = AlertDialog.Builder(this)
        alertDlg.setTitle("はじめに")
        alertDlg.setMessage("性別（せいべつ）、年代（ねんだい）、出身地（しゅっしんち）ごとにデータをあつめてロボがつよくなります。\nランキングもあるので協力（きょうりょく）おねがいします。")
        alertDlg.setPositiveButton(
            "OK"
        ) { dialog, which ->
            // OK ボタンクリック処理
            settingGenderDialog()
        }
        // 表示
        alertDlg.create().show()

    }


    fun settingGenderDialog() {

        LOGD(TAG, "setSettingRadioIdGender:")

        val items = arrayOf("男性（だんせい）", "女性（じょせい）")
        // タイトル部分のTextView
        val paddingLeftRight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
        val paddingTopBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
        val textView = TextView(this)
        // タイトルの背景色
        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
        // タイトルの文字色
        textView.setTextColor(Color.WHITE)
        textView.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
        // テキスト
        textView.text = "性別（せいべつ）"
        // テキストサイズ
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        AlertDialog.Builder(this)
            .setCustomTitle(textView)
            .setCancelable(false)
            .setItems(items) { dialog, which ->
                SettingsUtils.setSettingRadioIdGender(this, which + 1)
                settingAgeDialog()
            }
            .show()
    }

    fun settingAgeDialog() {
        val items = arrayOf(
            "０～９歳（さい）", "１０～１９歳（さい）", "２０～２９歳（さい）", "３０～３９歳（さい）",
            "４０～４９歳（さい）", "５０～５９歳（さい）", "６０～６９歳（さい）", "７０～７９歳（さい）", "８０歳（さい）～"
        )
        // タイトル部分のTextView
        val paddingLeftRight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
        val paddingTopBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
        val textView = TextView(this)
        // タイトルの背景色
        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
        // タイトルの文字色
        textView.setTextColor(Color.WHITE)
        textView.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
        // テキスト
        textView.text = "年代（ねんだい）"
        // テキストサイズ
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        AlertDialog.Builder(this)
            .setCustomTitle(textView)
            .setCancelable(false)
            .setItems(items) { dialog, which ->
                SettingsUtils.setSettingRadioIdAge(this, which + 1)
                settingPrefectureDialog()
            }
            .show()
    }

    fun settingPrefectureDialog() {
        val items = arrayOf(
            "北海道（ほっかいどう）", "青森（あおもり）", "岩手（いわて）", "宮城（みやぎ）", "秋田（あきた）", "山形（やまがた）",
            "福島（ふくしま）", "茨城（いばらき）", "栃木（とちぎ）", "群馬（ぐんま）", "埼玉（さいたま）", "千葉（ちば）", "東京（とうきょう）",
            "神奈川（かながわ）", "新潟（にいがた）", "富山（とやま）", "石川（いしかわ）", "福井（ふくい）", "山梨（やまなし）", "長野（ながの）",
            "岐阜（ぎふ）", "静岡（しずおか）", "愛知（あいち）", "三重（みえ）", "滋賀（しが）", "京都（きょうと）", "大阪（おおさか）", "兵庫（ひょうご）",
            "奈良（なら）", "和歌山（わかやま）", "鳥取（とっとり）", "島根（しまね）", "岡山（おかやま）", "広島（ひろしま）", "山口（やまぐち）",
            "徳島（とくしま）", "香川（かがわ）", "愛媛（えひめ）", "高知（こうち）", "福岡（ふくおか）", "佐賀（さが）", "長崎（ながさき）",
            "熊本（くまもと）", "大分（おおいた）", "宮崎（みやざき）", "鹿児島（かごしま）", "沖縄（おきなわ）"
        )
        // タイトル部分のTextView
        val paddingLeftRight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
        val paddingTopBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
        val textView = TextView(this)
        // タイトルの背景色
        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
        // タイトルの文字色
        textView.setTextColor(Color.WHITE)
        textView.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
        // テキスト
        textView.text = "出身地（しゅっしんち）"
        // テキストサイズ
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        AlertDialog.Builder(this)
            .setCustomTitle(textView)
            .setCancelable(false)
            .setItems(items) { dialog, which ->
                SettingsUtils.setSettingRadioIdPrefecture(this, which + 1)
            }
            .show()
    }
}
