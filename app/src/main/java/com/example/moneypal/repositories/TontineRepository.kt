package com.example.moneypal.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneypal.models.Session
import com.example.moneypal.models.Tontine
import com.example.moneypal.models.User
import com.example.moneypal.models.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class TontineRepository @Inject constructor() {


    private var _isTontineSuccessfullyCreated = MutableLiveData(false)
    val isTontineSuccessfullyCreated: LiveData<Boolean>
    get() = _isTontineSuccessfullyCreated

    private var _isTontineUnSuccessfullyCreated = MutableLiveData(false)
    val isTontineUnSuccessfullyCreated: LiveData<Boolean>
        get() = _isTontineUnSuccessfullyCreated
    private var _users: MutableLiveData<List<User>> = MutableLiveData(listOf())
    val users: LiveData<List<User>>
    get() = _users

    private var _tontines: MutableLiveData<List<Tontine>> = MutableLiveData(listOf())
    val tontines: LiveData<List<Tontine>>
    get() = _tontines


    fun createNewTontine(
        name: String,
        description: String,
        totalAmount: Int,
        notifyAllMembers: Boolean,
        users: List<UserData>,
        sessions: List<Session>,
        ownerUser: UserData
    ){
        val tontine = Tontine(
            name = name,
            description = description,
            totalAmount = totalAmount,
            notifyAllMembers = notifyAllMembers,
            users = users,
            sessions = sessions,
            ownerUser = ownerUser
        )
        val db = Firebase.firestore
        db.collection("tontines")
            .add(tontine).addOnSuccessListener {
                _isTontineSuccessfullyCreated.value = true
                Log.d("MainActivity", "Successfully create new tontine")
            }.addOnFailureListener{
                _isTontineUnSuccessfullyCreated.value = true
            }

        Log.d("MainActivity", "Tontine: name: $name, desc: $description, " +
                "amount: $totalAmount, notify: $notifyAllMembers")

    }

    suspend fun getAllTontine(){
        Log.d("MainActivity", "Starting gettint tonttin")
        val db = Firebase.firestore
        val auth = Firebase.auth.currentUser
        if(auth != null){
            db.collection("tontines")
                //.whereArrayContainsAny("ownerUser", listOf(User(auth.displayName, auth.photoUrl, auth.phoneNumber)) )
                .get().addOnCompleteListener { task ->

                    if(task.result != null){
                        task.result.forEach { query ->
                            val tontine = query.toObject(Tontine::class.java)
                            _tontines.value = _tontines.value?.plus(listOf(tontine))
                            Log.d("MainActivity", "${tontine.name} = ${tontine.totalAmount}")
                        }
                    }else{
                        Log.d("MainActivity", "Not found")
                    }
                    if(task.exception is FirebaseFirestoreException){
                        Log.d("MainActivity", "Successfully create new tontine")
                    }
                }
        }else{
            Log.d("MainActivity", "unauthenticated user")
        }

    }

}