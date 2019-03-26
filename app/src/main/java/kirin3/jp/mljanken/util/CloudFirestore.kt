package kirin3.jp.mljanken.util

import android.content.Context
import android.renderscript.Sampler
import com.google.firebase.firestore.FirebaseFirestore
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp


object CloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    fun getInitDb(context:Context):FirebaseFirestore {
        var db = FirebaseFirestore.getInstance()
        return db
    }

    fun addData(db:FirebaseFirestore){
        // Create a new user with a first and last name
        val user = HashMap<String, Any>()
        user.put("name", "Taro")
        user.put("state", "TOKYO")
        user.put("age", 55)


        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                LogUtils.LOGD(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
            }
            .addOnFailureListener { e -> LogUtils.LOGD(TAG, "Error adding document" + e)}


    }
}

