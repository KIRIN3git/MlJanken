package kirin3.jp.mljanken.game

object GameData {

    val NOTHING = 0
    val GUU = 1
    val CHOKI = 2
    val PAA = 3

    val WIN = 1
    val DROW = 2
    val LOSE = 3

    val MODE_NUM = 6
    val RANDOM_MODE = 1
    val MOST_WIN_MODE = 2
    val MOST_CHAIN_WIN_MODE = 3
    val MOST_GENDER_CHOICE_MODE = 4
    // ここからCroudFirestoreのデータを参照
    val MOST_AGE_CHOICE_MODE = 5
    val MOST_PREFECTURE_CHOICE_MODE = 6
    val MOST_GENDER_FIRST_CHOICE_MODE = 7
    val MOST_AGE_FIRST_CHOICE_MODE = 8
    val MOST_PREFECTURE_FIRST_CHOICE_MODE = 9
}