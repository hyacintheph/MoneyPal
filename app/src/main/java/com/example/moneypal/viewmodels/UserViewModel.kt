package com.example.moneypal.viewmodels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneypal.models.DataOrException
import com.example.moneypal.models.User
import com.example.moneypal.models.UserData
import com.example.moneypal.repositories.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){
    val authUser: LiveData<User>
    get() = userRepository.authUser
    val isImageUriNotFound: LiveData<Boolean>
    get() = userRepository.isImageUriNotFound
    val isLoadingUsers = MutableLiveData(false)
    val allUsers: LiveData<List<UserData>> = userRepository.users
    private var _selectedRecipient: MutableLiveData<List<UserData>> = MutableLiveData(listOf())
    val selectedRecipient: LiveData<List<UserData>>
    get() = _selectedRecipient


    init {
        getUsers()
    }
    // get authenticated user
    fun getAuthUser(){
       userRepository.getAuthUser()
    }
    // set notification
    fun setNotification(){
        userRepository.setNotification()
    }

    fun setSelectedRecipient(selectedRecipient: List<UserData>){
        _selectedRecipient.value = selectedRecipient
    }

    private fun getUsers(){
        viewModelScope.launch {
            isLoadingUsers.value = true
            userRepository.getUsers()
            isLoadingUsers.value = false
        }
    }

    fun addSelectedRecipient(userData: UserData){

    }

    fun deleteSelectedUser(userData: UserData) {
        userRepository.deleteSelectedUser(userData)

    }

}

