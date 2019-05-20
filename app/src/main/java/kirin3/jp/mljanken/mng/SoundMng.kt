package kirin3.jp.mljanken.mng

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import kirin3.jp.mljanken.R

object SoundMng {

    private var sSoundPool1:SoundPool ?= null
    private var sSoundPool2:SoundPool ?= null
    private var sSoundPool3:SoundPool ?= null
    private var sSoundPool4:SoundPool ?= null
    private var sSoundSteram1:Int = 0
    private var sSoundSteram2:Int = 0
    private var sSoundSteram3:Int = 0
    private var sSoundSteram4:Int = 0
    private var sSoundJankenpon:Int = 0
    private var sSoundAikodesho:Int = 0
    private var sSoundGuu:Int = 0
    private var sSoundChoki:Int = 0
    private var sSoundPaa:Int = 0
    private var sSoundFirework1:Int = 0
    private var sSoundFirework2:Int = 0
    private var sSoundFirework3:Int = 0
    private var sSoundStar:Int = 0


    /**
     * 初期化
     */
    fun soundInit(context: Context) {
        sSoundPool1 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        sSoundPool2 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        sSoundPool3 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        sSoundPool4 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        sSoundJankenpon = sSoundPool1!!.load(context, R.raw.sound_jankenpon, 0)
        sSoundAikodesho = sSoundPool1!!.load(context, R.raw.sound_aikodesho, 0)
        sSoundGuu = sSoundPool1!!.load(context, R.raw.sound_guu, 0)
        sSoundChoki = sSoundPool1!!.load(context, R.raw.sound_choki, 0)
        sSoundPaa = sSoundPool1!!.load(context, R.raw.sound_paa, 0)
        sSoundFirework1 = sSoundPool1!!.load(context, R.raw.sound_firework1, 0)
        sSoundFirework2 = sSoundPool2!!.load(context, R.raw.sound_firework2, 0)
        sSoundFirework3 = sSoundPool3!!.load(context, R.raw.sound_firework3, 0)
        sSoundStar = sSoundPool4!!.load(context, R.raw.sound_star, 0)
    }

    /**
     * 停止
     */
    fun soundStopFireWork() {
        stopSoundFirework1()
        stopSoundFirework2()
        stopSoundFirework3()
        stopSoundFirework4()
    }

    /**
     * 解放
     */
    fun soundEnd() {
        sSoundPool1!!.release()
        sSoundPool2!!.release()
        sSoundPool3!!.release()
        sSoundPool4!!.release()
    }

    fun playSoundJankenpon() {
        sSoundPool1!!.play(sSoundJankenpon, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundAikodesho() {
        sSoundPool1!!.play(sSoundAikodesho, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundGoo() {
        sSoundPool1!!.play(sSoundGuu, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundChoki() {
        sSoundPool1!!.play(sSoundChoki, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundPaa() {
        sSoundPool1!!.play(sSoundPaa, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundFirework1() {
        sSoundSteram1 = sSoundPool1!!.play(sSoundFirework1, 1.0f, 1.0f, 0, 1, 1.0f)
    }

    fun playSoundFirework2() {
        sSoundSteram2 = sSoundPool2!!.play(sSoundFirework2, 1.0f, 0.2f, 0, 1, 1.0f)
    }

    fun playSoundFirework3() {
        sSoundSteram3 = sSoundPool3!!.play(sSoundFirework3, 0.2f, 1.0f, 0, 1, 1.0f)
    }

    fun playSoundFirework4() {
        sSoundSteram4 = sSoundPool4!!.play(sSoundStar, 1.0f, 1.0f, 0, 1, 1.0f)
    }


    fun stopSoundFirework1() {
        sSoundPool1!!.stop(sSoundSteram1)
    }

    fun stopSoundFirework2() {
        sSoundPool2!!.stop(sSoundSteram2)
    }

    fun stopSoundFirework3() {
        sSoundPool3!!.stop(sSoundSteram3)
    }

    fun stopSoundFirework4() {
        sSoundPool4!!.stop(sSoundSteram4)
    }
}