package com.example.moneypal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneypal.models.Session
import com.example.moneypal.models.Tontine
import com.example.moneypal.models.User
import com.example.moneypal.models.UserData
import com.example.moneypal.repositories.TontineRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TontineViewModel @Inject constructor(
    private val tontineRepository: TontineRepository
): ViewModel() {
    val isTontineSuccessfullyCreated: LiveData<Boolean>
    get() = tontineRepository.isTontineSuccessfullyCreated

    val isTontineUnSuccessfullyCreated: LiveData<Boolean>
        get() = tontineRepository.isTontineUnSuccessfullyCreated
    private var _tontineName = MutableLiveData("")
    val tontineName: LiveData<String>
        get() = _tontineName

    private var _tontineDesc = MutableLiveData("")
    val tontineDesc: LiveData<String>
        get() = _tontineDesc

    private var _tontineAmount = MutableLiveData(0)
    val tontineAmount: LiveData<Int>
        get() = _tontineAmount

    private var _notifyAllMembers = MutableLiveData(false)
    val notifyAllMembers: LiveData<Boolean>
        get() = _notifyAllMembers

    private var _sessions = MutableLiveData(listOf(Session()))
    val sessions: LiveData<List<Session>>
        get() = _sessions

    private var _users = MutableLiveData(listOf(UserData()))
    val users: LiveData<List<UserData>>
        get() = _users

    private var _ownerUser = MutableLiveData(UserData())
    val ownerUser: LiveData<UserData>
        get() = _ownerUser

    val tontines: LiveData<List<Tontine>>
    get() = tontineRepository.tontines

    private var _tontineLoading = MutableLiveData(false)
    val tontineLoading: LiveData<Boolean>
    get() = _tontineLoading

    init{
        _tontineLoading.value = true
        getAllTontines()
        _tontineLoading.value = false
    }

    fun getAllTontines(){
       viewModelScope.launch {
           tontineRepository.getAllTontine()
       }
    }
    fun setTontineData(
        name: String,
        description: String,
        totalAmount: String,
        notifyAllMembers: Boolean
    ){

        _tontineName.value = name
        _tontineDesc.value = description
        _tontineAmount.value = totalAmount.toInt()
        _notifyAllMembers.value = notifyAllMembers

    }

    fun createNewTontine(
        users: List<UserData> = listOf(),
        sessions: List<Session> = listOf(),
        ownerUser: UserData = UserData()
    ){
        val user: UserData = UserData()
        val auth = Firebase.auth.currentUser
        auth?.let {
            user.name = auth.displayName
            user.phoneNumber = auth.phoneNumber
            user.avatar = auth.photoUrl.toString()
        }
        _users.value = users
        _sessions.value = sessions
        _ownerUser.value = user
        tontineRepository.createNewTontine(
            name = _tontineName.value!!,
            description = _tontineDesc.value!!, totalAmount = _tontineAmount.value!!.toInt(),
            notifyAllMembers = _notifyAllMembers.value!!, users = _users.value!!,
            sessions = _sessions.value!!, ownerUser = _ownerUser.value!!)
        viewModelScope.launch {
            getAllTontines()
        }
    }
}