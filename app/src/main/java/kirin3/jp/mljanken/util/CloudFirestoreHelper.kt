package kirin3.jp.mljanken.util

import android.content.Context
import android.provider.ContactsContract
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.firebase.firestore.Query
import kirin3.jp.mljanken.util.LogUtils.LOGD
import java.util.*


object CloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    fun getInitDb(context:Context):FirebaseFirestore {
        var db = FirebaseFirestore.getInstance()
        return db
    }

    /*
     * 利用サンプル
     */
    /*
            // CloudFirestore初期化
        var db = CloudFirestoreHelper.getInitDb(mContext)

        val user1 = CloudFirestoreHelper.UserItem("Ichiro","Suzuki",55,true,"TOKYO")
        val user2 = CloudFirestoreHelper.UserItem("Junji","Takada",18,false,"OSAKA")
        val user3 = CloudFirestoreHelper.UserItem("Hanako","Yoshida",17,true,"TOKYO")
        val user4 = CloudFirestoreHelper.UserItem("Toshio","Sato",25,false,"TOKYO")
        val user5 = CloudFirestoreHelper.UserItem("Yoshiko","Imai",30,false,"OSAKA")
        val user6 = CloudFirestoreHelper.UserItem("Taro","Nagase",43,false,"TOKYO")

        val hobby1 = CloudFirestoreHelper.HobbyItem("Ski",3)

        CloudFirestoreHelper.addData(db,user1,"users","u001")
        CloudFirestoreHelper.addData(db,user2,"users","u002")
        CloudFirestoreHelper.addData(db,user3,"users","u003")
        CloudFirestoreHelper.addData(db,user4,"users","u004")
        CloudFirestoreHelper.addData(db,user5,"users","u005")
        CloudFirestoreHelper.addData(db,user6,"users","u006")
        CloudFirestoreHelper.addDataSecondCollection(db,hobby1,"users","u001","hobby","h001")

        CloudFirestoreHelper.getData(db,"users","u001")
        CloudFirestoreHelper.getDataSecondCollection(db,"users","u001","hobby","h001")
        CloudFirestoreHelper.getDataAll(db,"users")
        CloudFirestoreHelper.getDataWhere(db,"users","TOKYO")
        CloudFirestoreHelper.getDataOrderByLimit(db,"users")
     */
    data class UserItem(
        val firstName: String = "",
        val lastName: String = "",
        val age: Int = 0,
        val adult: Boolean = false,
        val state: String = "",
        val regTime: Date = Date())

    data class HobbyItem(
        val firstHobby: String = "Ski",
        val year: Int = 3)


    /*
     * コレクション、ドキュメント、フィールドを登録
     * ・コレクション、ドキュメントが同名だと上書き保存
     * ・ .documnentを外せば、ランダムでユニークなIDが自動で付与される
     */
    fun addData(db:FirebaseFirestore,user:UserItem,collection:String,document:String){
        db.collection(collection)
            .document(document)
            .set(user)
            .addOnSuccessListener { documentReference ->
                LOGD(TAG, "addData")
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
     * コレクション、ドキュメント、フィールドを登録
     * （注意）コレクション、ドキュメントが同名だと上書き保存
     */
    fun addDataSecondCollection(db:FirebaseFirestore,hobby:HobbyItem,collectionFirst:String,documentFirst:String,collectionSecond:String,documentSecond:String){
        db.collection(collectionFirst)
            .document(documentFirst)
            .collection(collectionSecond)
            .document(documentSecond)
            .set(hobby)
            .addOnSuccessListener { documentReference ->
                LOGD(TAG, "addDataSecondCollection")
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
     * コレクション、ドキュメントを指定してデータ取得
     */
    fun getData(db:FirebaseFirestore,collection:String,document:String){
        db.collection(collection)
            .document(document)
            .get()
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.data != null) {
                    LOGD(TAG, "getData")

                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("firstName"))
                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("lastName"))
                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("age"))
                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("regTime"))
                } else {
                    LOGD(TAG, "No such document")
                }
            } else {
                LOGD(TAG, "get failed with " + task.exception)
            }
        }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
 * コレクション、ドキュメントを指定してデータ取得
 */
    fun getDataSecondCollection(db:FirebaseFirestore,collectionFirst:String,documentFirst:String,collectionSecond:String,documentSecond:String){
        db.collection(collectionFirst)
            .document(documentFirst)
            .collection(collectionSecond)
            .document(documentSecond)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.data != null) {
                        LOGD(TAG, "getDataSecondCollection")

                        LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("firstHobby"))
                        LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("year"))
                    } else {
                        LOGD(TAG, "No such document")
                    }
                } else {
                    LOGD(TAG, "get failed with " + task.exception)
                }
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
     * コレクションとフィールドの一致を指定して全データを取得
     */
    fun getDataAll(db:FirebaseFirestore,collection:String){
        db.collection(collection)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
                        LOGD(TAG, "getDataAll")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){
                            LOGD(TAG, "userList.get(" + i + ").firstName " + userList.get(i).firstName)
                            LOGD(TAG, "userList.get(" + i + ").lastName " + userList.get(i).lastName)
                            LOGD(TAG, "userList.get(" + i + ").age " + userList.get(i).age)
                            LOGD(TAG, "userList.get(" + i + ").regTime " + userList.get(i).regTime)
                        }
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }
    }


    /*
     * コレクションを指定してデータ取得
     * Where,Limit,OrderBy,Betweenを設定
     * 等価演算子（==）と範囲比較（<、<=、>、>=）を組み合わせる場合はインデックスの作成が必要
     * OrderByは重ねることも可能
     * OrderByにQuery.Direction.DESCENDINGを付ければ逆順
     * 境界値を含みたい場合はstartAfter()とendBefore()
     */
    fun getDataConditions(db:FirebaseFirestore,collection:String){
        db.collection(collection)
            .whereEqualTo(UserItem::state.name,"TOKYO")
            .orderBy(UserItem::age.name,Query.Direction.DESCENDING)
            .startAt(40)
            .endAt(60)
            .limit(2)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
                        LOGD(TAG, "getDataOrderByLimit")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){
                            LOGD(TAG, "userList.get(" + i + ").firstName " + userList.get(i).firstName)
                            LOGD(TAG, "userList.get(" + i + ").lastName " + userList.get(i).lastName)
                            LOGD(TAG, "userList.get(" + i + ").age " + userList.get(i).age)
                            LOGD(TAG, "userList.get(" + i + ").regTime " + userList.get(i).regTime)
                        }
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }
    }
}

