package kirin3.jp.mljanken.util

import android.content.Context
import android.preference.PreferenceManager
import kirin3.jp.mljanken.util.LogUtils.LOGD

object SettingsUtils {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    enum class Sex {
        NOTHING,
        MEN,
        WOMEN
    }

    enum class Age {
        NOTHING,
        ZERO,
        TEN,
        TWENTY,
        THERTY,
        FORTY,
        FIFTY,
        SIXTY,
        SEVENTY,
        EIGHTY
    }

    enum class Prefecture {
        HOKKAIDO,AOMORI,IWATE,MIYAGI,AKITA,YAMAGATA,FUKUSHIMA,IBARAKI,TOCHIGI,
        GUNMA,SAITAMA,CHIBA,TOKYO,KANAGAWA,NIIGATA,YAMANASHI,NAGANO,TOYAMA,
        ISHIKAWA,FUKUI,GIFU,SHIZUOKA,AICHI,MIE,SHIGA,KYOTO,OSAKA,HYOGO,NARA,
        WAKAYAMA,TOTTORI,SHIMANE,OAKAYAMA,HIROSHIMA,YAMAGUCHI,TOKUSHIMA,KAGAWA,
        EHIME,KOCHI,FUKUOKA,SAGA,NAGASAKI,KUMAMOTO,OITA,MIYAZAKI,KAGOSHIMA,OKINAWA
    }


    val PREF_SETTING_SEX = "pref_setting_sex"
    val PREF_SETTING_AGE = "pref_setting_age"
    val PREF_SETTING_PREFECTURE = "pref_setting_prefecture"
    val PREF_SETTING_BATTEL_NUM = "pref_setting_battle_num"
    val PREF_SETTING_WIN_NUM = "pref_setting_win_num"
    val PREF_SETTING_LOSE_NUM = "pref_setting_lose_num"
    val PREF_SETTING_MOST_CHOICE = "pref_setting_most_choice"
    val PREF_SETTING_RECORD = "pref_setting_record"



    // 性別のIDを保存
    fun setSettingRadioIdSex(context: Context, sex_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingRadioIdSex:" + sex_id)
        sp.edit().putInt(PREF_SETTING_SEX, sex_id).apply()
    }

    // 年代のIDを保存
    fun setSettingRadioIdAge(context: Context, age_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingRadioIdAge:" + age_id)
        sp.edit().putInt(PREF_SETTING_AGE, age_id).apply()
    }
    // 出身県のIDを保存
    fun setSettingRadioIdPrefecture(context: Context, prefecture_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingRadioIdPrefecture:" + prefecture_id)
        sp.edit().putInt(PREF_SETTING_PREFECTURE, prefecture_id).apply()
    }

    // 性別のIDを取得
    fun getSettingRadioIdSex(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingRadioIdSex:" + sp.getInt(PREF_SETTING_SEX, 0))
        return sp.getInt(PREF_SETTING_SEX, 0)
    }

    // 年代のIDを取得
    fun getSettingRadioIdAge(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingRadioIdAge:" + sp.getInt(PREF_SETTING_AGE, 0))
        return sp.getInt(PREF_SETTING_AGE, 0)
    }

    // 出身県のIDを取得
    fun getSettingRadioIdPrefecture(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingRadioIdPrefecture:" + sp.getInt(PREF_SETTING_PREFECTURE, 0))
        return sp.getInt(PREF_SETTING_PREFECTURE, 0)
    }
}

