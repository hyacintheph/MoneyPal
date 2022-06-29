package com.example.moneypal.repositories

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneypal.models.DataOrException
import com.example.moneypal.models.User
import com.example.moneypal.models.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import okhttp3.internal.toImmutableList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    private val _authUser: MutableLiveData<User> = MutableLiveData()
    val authUser: LiveData<User>
        get() = _authUser
    private val _isImageUriNotFound = MutableLiveData(false)
    val isImageUriNotFound: LiveData<Boolean>
    get() = _isImageUriNotFound
    private var _users: MutableLiveData<List<UserData>> = MutableLiveData(listOf())
    val users: LiveData<List<UserData>>
    get() = _users

    // get authenticated user
    fun getAuthUser(){
        val auth = Firebase.auth.currentUser
        auth?.let {
            _authUser.value = User(
                name = it.displayName,
                avatar = it.photoUrl,
                phoneNumber = it.phoneNumber
            )
        }
    }

    // set notification
    fun setNotification(){
        _authUser.value?.notification = true
    }

    suspend fun getUsers() {
        val dataOrException = DataOrException<MutableList<UserData>, Exception>()
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get().addOnCompleteListener {  task ->
                if(task.result != null){
                    _users.value = task.result.toObjects(UserData::class.java)
                    // update uri avatar image
                    var reference = FirebaseStorage.getInstance().reference
                    (_users.value as MutableList<UserData>).forEach {
                        Log.d("MainActivity", it.avatar)
                        reference.child("users/${it.avatar}")
                            .downloadUrl.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                it.avatar = task.result.toString()
                            }

                        }.addOnFailureListener{
                            //userUri = null
                        }
                    }
                }

                if(task.exception is FirebaseFirestoreException){

                }
            }

    }

    fun deleteSelectedUser(userData: UserData) {
        _users.value!!.minus(userData)
    }

    companion object{
        const val notification = "notification"
        const val phoneNumber = "phoneNumber"
        const val name = "name"
        const val avatar = "avatar"
        const val darkMode = "darkMode"
    }


}