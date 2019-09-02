package kirin3.jp.mljanken.award

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AwardViewModel : ViewModel() {

    // LiveViewを作成
    val awardData: MutableLiveData<List<AwardData>> by lazy {
        MutableLiveData<List<AwardData>>()
    }


    // 初期値を設定
    init{
//        awardData.value = AwardCloudFirestoreHelper.getAwardData(db, "users", appContext!!)
    }

    fun setUserList(listAward:List<AwardData>){
        awardData.value = listAward
    }

/*
    // ボタンクリック時の値を変更する関数
    fun change(){
        user.value = User("OHTANI",25)
    }

    // LiveDataの更新を他のLiveDataの更新に依存させる
    val logo: LiveData<LogoMark> = Transformations.map(user) {
        when {
            user.value!!.age > 40 -> LogoMark.LOGO1
            else -> LogoMark.LOGO2
        }
    }
*/
}