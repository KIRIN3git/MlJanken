package kirin3.jp.mljanken.mng

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import kirin3.jp.mljanken.R

object SoundMng {

    private lateinit var soundPool1:SoundPool
    private lateinit var soundPool2:SoundPool
    private lateinit var soundPool3:SoundPool
    private lateinit var soundPool4:SoundPool
    private lateinit var soundPool5:SoundPool
    private var soundSteram1:Int = 0
    private var soundSteram2:Int = 0
    private var soundSteram3:Int = 0
    private var soundSteram4:Int = 0
    private var soundSteram5:Int = 0
    private var soundJankenpon:Int = 0
    private var soundAikodesho:Int = 0
    private var soundGuu:Int = 0
    private var soundChoki:Int = 0
    private var soundPaa:Int = 0
    private var soundFirework1:Int = 0
    private var soundFirework2:Int = 0
    private var soundFirework3:Int = 0
    private var soundStar:Int = 0

    private var sCorrect:Int = 0
    private var sMistake:Int = 0


    /**
     * 初期化
     */
    fun soundInit(context: Context) {
        soundPool1 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        soundPool2 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        soundPool3 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        soundPool4 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        soundPool5 = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        soundJankenpon = soundPool1.load(context, R.raw.sound_jankenpon, 0)
        soundAikodesho = soundPool1.load(context, R.raw.sound_aikodesho, 0)
        soundGuu = soundPool1.load(context, R.raw.sound_guu, 0)
        soundChoki = soundPool1.load(context, R.raw.sound_choki, 0)
        soundPaa = soundPool1.load(context, R.raw.sound_paa, 0)
        soundFirework1 = soundPool2.load(context, R.raw.sound_firework1, 0)
        soundFirework2 = soundPool3.load(context, R.raw.sound_firework2, 0)
        soundFirework3 = soundPool4.load(context, R.raw.sound_firework3, 0)
        soundStar = soundPool5.load(context, R.raw.sound_star, 0)
        sCorrect = soundPool1.load(context, R.raw.correct, 0)
        sMistake = soundPool1.load(context, R.raw.mistake, 0)
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
        soundPool1.release()
        soundPool2.release()
        soundPool3.release()
        soundPool4.release()
        soundPool5.release()
    }

    fun playSoundJankenpon() {
        soundPool1.play(soundJankenpon, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundAikodesho() {
        soundPool1.play(soundAikodesho, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundGoo() {
        soundPool1.play(soundGuu, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundChoki() {
        soundPool1.play(soundChoki, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundPaa() {
        soundPool1.play(soundPaa, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundFirework1() {
        soundSteram2 = soundPool2.play(soundFirework1, 1.0f, 1.0f, 0, 1, 1.0f)
    }

    fun playSoundFirework2() {
        soundSteram3 = soundPool3.play(soundFirework2, 1.0f, 0.2f, 0, 1, 1.0f)
    }

    fun playSoundFirework3() {
        soundSteram4 = soundPool4.play(soundFirework3, 0.2f, 1.0f, 0, 1, 1.0f)
    }

    fun playSoundFirework4() {
        soundSteram5 = soundPool5.play(soundStar, 1.0f, 1.0f, 0, 1, 1.0f)
    }

    fun playSoundCorrect() {
        soundPool1.play(sCorrect, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playSoundMistake() {
        soundPool1.play(sMistake, 1.0f, 1.0f, 0, 0, 1.0f)
    }


    fun stopSoundFirework1() {
        soundPool2.stop(soundSteram2)
    }

    fun stopSoundFirework2() {
        soundPool3.stop(soundSteram3)
    }

    fun stopSoundFirework3() {
        soundPool4.stop(soundSteram4)
    }

    fun stopSoundFirework4() {
        soundPool5.stop(soundSteram5)
    }
}