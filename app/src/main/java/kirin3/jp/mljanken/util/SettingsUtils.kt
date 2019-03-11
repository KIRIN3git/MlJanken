package kirin3.jp.mljanken.util

import android.content.Context
import android.preference.PreferenceManager

object SettingsUtils {

    val PREF_SETTING_TIME = "pref_setting_time"
    val PREF_SETTING_NUMBER = "pref_setting_number"
    val PREF_SETTING_SPEED = "pref_setting_speed"
    val PREF_SETTING_ITEM = "pref_setting_item"
    val PREF_SETTING_FIELD = "pref_setting_field"

    val PREF_WINNING_NUM1 = "pref_winning_num1"
    val PREF_WINNING_NUM2 = "pref_winning_num2"
    val PREF_WINNING_NUM3 = "pref_winning_num3"
    val PREF_WINNING_NUM4 = "pref_winning_num4"

    val PREF_PLAY_NUM = "pref_play_num"

    // TIMEのラジオボタンのIDを保存
    fun setSettingRadioIdTime(context: Context, time_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_TIME, time_id).apply()
    }

    // TIMEのラジオボタンのIDを取得
    fun getSettingRadioIdTime(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_TIME, 0)
    }

    // 人数のラジオボタンのIDを保存
    fun setSettingRadioIdNumber(context: Context, number_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_NUMBER, number_id).apply()
    }

    // 人数のラジオボタンのIDを取得
    fun getSettingRadioIdNumber(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_NUMBER, 0)
    }

    // 速さのラジオボタンのIDを保存
    fun setSettingRadioIdSpeed(context: Context, speed_id: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_SPEED, speed_id!!).apply()
    }

    // 速さのラジオボタンのIDを取得
    fun getSettingRadioIdSpeed(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_SPEED, 0)
    }

    // アイテムのラジオボタンのIDを保存
    fun setSettingRadioIdItem(context: Context, item_id: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_ITEM, item_id!!).apply()
    }

    // アイテムのラジオボタンのIDを取得
    fun getSettingRadioIdItem(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_ITEM, 0)
    }

    // フィールドのラジオボタンのIDを保存
    fun setSettingRadioIdField(context: Context, field_id: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_FIELD, field_id!!).apply()
    }

    // フィールドのラジオボタンのIDを取得
    fun getSettingRadioIdField(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_FIELD, 0)
    }


    // プレイヤー1の勝利数を保存
    fun setWinningNum1(context: Context, num: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_WINNING_NUM1, num!!).apply()

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num1", num)
    }

    // プレイヤー1の勝利数を取得
    fun getWiiningNum1(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_WINNING_NUM1, 0)
    }

    // プレイヤー2の勝利数を保存
    fun setWinningNum2(context: Context, num: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_WINNING_NUM2, num!!).apply()

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num2", num)
    }

    // プレイヤー2の勝利数を取得
    fun getWiiningNum2(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_WINNING_NUM2, 0)
    }

    // プレイヤー3の勝利数を保存
    fun setWinningNum3(context: Context, num: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_WINNING_NUM3, num!!).apply()

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num3", num)
    }

    // プレイヤー3の勝利数を取得
    fun getWiiningNum3(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_WINNING_NUM3, 0)
    }

    // プレイヤー4の勝利数を保存
    fun setWinningNum4(context: Context, num: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_WINNING_NUM4, num!!).apply()

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num4", num)
    }

    // プレイヤー4の勝利数を取得
    fun getWiiningNum4(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_WINNING_NUM4, 0)
    }

    // プレイヤーの勝利数をリセット
    fun resetWinningNum(context: Context) {
        setWinningNum1(context, 0)
        setWinningNum2(context, 0)
        setWinningNum3(context, 0)
        setWinningNum4(context, 0)
    }


    // ゲームのクリア数を保存
    fun setPlayNum(context: Context, num: Int?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_PLAY_NUM, num!!).apply()

        AnalyticsHelper.setAnalyticsPlayNum("play_num", num)
    }

    // プレイヤー4の勝利数を取得
    fun getPlayNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_PLAY_NUM, 0)
    }
}

