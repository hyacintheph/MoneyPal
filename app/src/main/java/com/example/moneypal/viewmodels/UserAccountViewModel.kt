package com.example.moneypal.viewmodels

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.moneypal.UserAccountActivity
import com.example.moneypal.models.Status
import com.example.moneypal.repositories.UserAccountRepository
import com.example.moneypal.utils.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAccountViewModel @Inject constructor(
    private val userAccountRepository: UserAccountRepository,
) : ViewModel() {

    // current send code sms
    val code: LiveData<String>
        get() = userAccountRepository.code
    // status of app on response of events
    val isProfileUpdated: LiveData<Boolean>
        get() = userAccountRepository.isProfileUpdated

    val isInvalidCredential: LiveData<Boolean> = userAccountRepository.isInvalidCredential
    val isTooManyRequest: LiveData<Boolean>  = userAccountRepository.isTooManyRequest
    val isSuccessfullyUpload:  LiveData<Boolean> = userAccountRepository.isSuccessfullyUpload
    val isErrorUploadImage:  LiveData<Boolean> = userAccountRepository.isErrorUploadImage
    val isSuccessfullyAuthenticated:  LiveData<Boolean> = userAccountRepository.isSuccessfullyAuthenticated
    val isErrorAuthenticated:  LiveData<Boolean> = userAccountRepository.isErrorAuthenticated
    val isInputCodeInvalid:  LiveData<Boolean> = userAccountRepository.isInputCodeInvalid
    val isUpdateProfileLoading: LiveData<Boolean> = userAccountRepository.isUpdateProfileLoading
    val isCodeVerificationSend:  LiveData<Boolean> = userAccountRepository.isCodeVerificationSend
    val isErrorUpdatedProfile:  LiveData<Boolean> = userAccountRepository.isErrorUpdatedProfile
    val isPhoneNumberSuccessfullyUpdated:  LiveData<Boolean> = userAccountRepository.isPhoneNumberSuccessfullyUpdated
    val isPhoneNumberAlreadyExist:  LiveData<Boolean> = userAccountRepository.isPhoneNumberAlreadyExist
    val isInvalidUser:  LiveData<Boolean> = userAccountRepository.isInvalidUser
    val isExpireConnectionTime:  LiveData<Boolean> = userAccountRepository.isExpireConnectionTime
    val isInvalidAccount:  LiveData<Boolean> = userAccountRepository.isInvalidAccount

     fun resendVerificationCode(
         activity: Activity,
         phoneNumber: String,
         isAccountCreated: Boolean
    ) {
       userAccountRepository.resendVerificationCode(activity, phoneNumber, isAccountCreated)
    }


    // map viewModel state of code sms and sending state
    fun processCodeVerification(){
       userAccountRepository.processCodeVerification()
    }

    fun processAuthentication(activity: Activity,
                              navController: NavHostController,
                              phoneNumber: String, isAccountCreated: Boolean){
        userAccountRepository.processAuthentication(activity = activity,
            phoneNumber = phoneNumber, isAccountCreated = isAccountCreated )
    }


    fun processCodeVerificationWithCode(activity: Activity,
                                        userInputCode: String, isAccountCreated: Boolean){
        userAccountRepository.processCodeVerificationWithCode(activity, userInputCode)
    }

    fun uploadImage(bitmap: Bitmap, userName: String = "", isUpdated: Boolean = false){
        userAccountRepository.uploadImage(bitmap, userName, isUpdated = isUpdated)
    }

    fun updateUserProfile(userName: String = "",
                          phoneNumber: String = ""){
        userAccountRepository.processToUpdatesProfile(userName, phoneNumber)
    }


}