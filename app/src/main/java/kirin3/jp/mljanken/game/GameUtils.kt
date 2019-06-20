package kirin3.jp.mljanken.game

import android.content.Context
import java.io.*
import java.net.URLEncoder
import java.nio.charset.Charset


object GameUtils {

    /*
     * 手を入力して、それに勝つものを返却する
     */
    fun choiceWinHand(choice:Int): Int {

        when(choice){
            GameData.GUU -> return GameData.PAA
            GameData.CHOKI -> return  GameData.GUU
            GameData.PAA -> return  GameData.CHOKI
        }

        return GameData.NOTHING
    }

    /*
     * 文字列のエンコード
     */
    fun encode(text: String): String {
        var encodedString = text
        try {
            encodedString = URLEncoder.encode(encodedString, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            encodedString = text
        }

        return encodedString
    }
}
