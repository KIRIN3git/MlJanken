package kirin3.jp.mljanken.util

import android.content.Context
import android.preference.PreferenceManager

object SettingsUtils {

    val PREF_SETTING_SEIBETU = "pref_setting_seibetu"
    val PREF_SETTING_NENDAI = "pref_setting_nendai"
    val PREF_SETTING_SHUSHIN = "pref_setting_shushin"

    val PREF_PLAY_NUM = "pref_play_num"

    // 性別のIDを保存
    fun setSettingRadioIdSeibetu(context: Context, seibetu_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_SEIBETU, seibetu_id).apply()
    }

    // 年代のIDを保存
    fun setSettingRadioIdNendai(context: Context, nendai_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_NENDAI, nendai_id).apply()
    }
    // 出身県のIDを保存
    fun setSettingRadioIdShushin(context: Context, shushin_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_SHUSHIN, shushin_id).apply()
    }

    // 性別のIDを取得
    fun getSettingRadioIdSeibetu(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_SEIBETU, 0)
    }

    // 年代のIDを取得
    fun getSettingRadioIdNendai(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_NENDAI, 0)
    }

    // 出身県のIDを取得
    fun getSettingRadioIdShushin(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_SHUSHIN, 0)
    }
}

