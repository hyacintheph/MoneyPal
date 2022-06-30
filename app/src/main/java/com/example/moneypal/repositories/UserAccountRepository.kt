package com.example.moneypal.repositories

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneypal.models.User
import com.example.moneypal.utils.randomStr
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserAccountRepository @Inject constructor() {
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var auth: FirebaseAuth
    private lateinit var activity: Activity
    private lateinit var phoneNumber: String
    private var isAccountShouldCreated: Boolean = false
    private lateinit var credentials: PhoneAuthCredential
    // current send code sms
    private  var _code = MutableLiveData("")
    val code: LiveData<String>
        get() = _code
    // code message state for mapping changing
    private var _codeVerification = MutableLiveData("")
    private var codeSend = MutableLiveData(false)

    // state of transactions
    // we use many boolean fields because LiveData is not observable with dataclass
    private var _isProfileUpdated = MutableLiveData(false)
    val isProfileUpdated: LiveData<Boolean>
        get() = _isProfileUpdated
    private var _isInvalidCredential = MutableLiveData(false)
    val isInvalidCredential: LiveData<Boolean> = _isInvalidCredential
    private var _isTooManyRequest = MutableLiveData(false)
    val isTooManyRequest: LiveData<Boolean>  = _isTooManyRequest
    private var _isSuccessfullyUpload = MutableLiveData(false)
    val isSuccessfullyUpload:  LiveData<Boolean> = _isSuccessfullyUpload
    private var _isErrorUploadImage = MutableLiveData(false)
    val isErrorUploadImage:  LiveData<Boolean> = _isErrorUploadImage
    private var _isSuccessfullyAuthenticated = MutableLiveData(false)
    val isSuccessfullyAuthenticated:  LiveData<Boolean> = _isSuccessfullyAuthenticated
    private var _isErrorAuthenticated = MutableLiveData(false)
    val isErrorAuthenticated:  LiveData<Boolean> = _isErrorAuthenticated
    private var _isInputCodeInvalid = MutableLiveData(false)
    val isInputCodeInvalid:  LiveData<Boolean> = _isInputCodeInvalid
    private var _isUpdateProfileLoading = MutableLiveData(false)
    val isUpdateProfileLoading: LiveData<Boolean> = _isUpdateProfileLoading
    private var _isCodeVerificationSend = MutableLiveData(false)
    val isCodeVerificationSend:  LiveData<Boolean> = _isCodeVerificationSend
    private var _isErrorUpdatedProfile = MutableLiveData(false)
    val isErrorUpdatedProfile:  LiveData<Boolean> = _isErrorUpdatedProfile
    private var _isPhoneNumberSuccessfullyUpdated = MutableLiveData(false)
    val isPhoneNumberSuccessfullyUpdated:  LiveData<Boolean> = _isPhoneNumberSuccessfullyUpdated
    private var _isPhoneNumberAlreadyExist = MutableLiveData(false)
    val isPhoneNumberAlreadyExist:  LiveData<Boolean> = _isPhoneNumberAlreadyExist
    private var _isInvalidUser = MutableLiveData(false)
    val isInvalidUser:  LiveData<Boolean> = _isInvalidUser
    private var _isExpireConnectionTime = MutableLiveData(false)
    val isExpireConnectionTime:  LiveData<Boolean> = _isExpireConnectionTime
    private var _isInvalidAccount = MutableLiveData(false)
    val isInvalidAccount:  LiveData<Boolean> = _isInvalidAccount

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInUser(activity, credential, isAccountShouldCreated)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    _isInvalidAccount.value = true
                    e.printStackTrace()

                } else if (e is FirebaseTooManyRequestsException) {
                    _isTooManyRequest.value = true
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                // get verification ID and resending token
                storedVerificationId = verificationId
                resendToken = token
                codeSend.value = true
               _isCodeVerificationSend.value = true
            }


        }

    fun processAuthentication(activity: Activity,
                              phoneNumber: String, isAccountCreated: Boolean){
        auth = Firebase.auth
        this.activity = activity
        this.phoneNumber = phoneNumber
        isAccountShouldCreated = isAccountCreated
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(this.phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS) // one minute
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendVerificationCode(
        activity: Activity,
        phoneNumber: String,
        isAccountCreated: Boolean
    ) {
        auth = Firebase.auth
        this.activity = activity
        this.phoneNumber = phoneNumber
        isAccountShouldCreated = isAccountCreated
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        optionsBuilder.setForceResendingToken(resendToken)
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInUser(activity: Activity,
                           credential: PhoneAuthCredential,
                           isAccountCreated: Boolean
    ){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // get sending sms code and store it for launchEffect side effect
                    _code.value = credential.smsCode.toString()
                    _codeVerification.value = _code.value!!
                    // navigate to WelcomeUser composable
                    if(!isAccountCreated){
                        // user verify phone number without create an account
                        _isInvalidAccount.value = true
                    }else{
                        _isSuccessfullyAuthenticated.value = true
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        _isInputCodeInvalid.value = true
                    }
                }
            }

    }

    // map repository state of code sms and sending state
    fun processCodeVerification(){
        _code.value = _codeVerification.value
        _isCodeVerificationSend.value = codeSend.value!!
    }


    fun processCodeVerificationWithCode(activity: Activity,
                                        userInputCode: String){
        // TODO: store credentials locally
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, userInputCode)
        credentials = credential
        signInUser(activity, credential, isAccountShouldCreated)
    }

    private fun updateUserProfile(userName: String, avatarImage: String = "",
                                  phoneNumber: String = "", isUpdated: Boolean){
        // get firebase auth user
        val authUser: FirebaseUser? = Firebase.auth.currentUser

        // setting updates request
        var profileUpdates: UserProfileChangeRequest? = null
        if(phoneNumber.isNotBlank()){

            // update the phone number
            authUser?.updatePhoneNumber(credentials)?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    // update the username
                    profileUpdates = UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(userName)
                        .build()
                    _isPhoneNumberSuccessfullyUpdated.value = true
                }
                //thrown if there already exists an account with the given phone number
                if(task.exception is FirebaseAuthUserCollisionException){
                    _isPhoneNumberAlreadyExist.value = true
                }
                //thrown if the current user's account has been disabled,
                // deleted, or its credentials are no longer valid
                if (task.exception is FirebaseAuthInvalidUserException){
                    _isInvalidUser.value = true
                }
                //thrown if the user's last sign-in time does not meet the security threshold.
                if(task.exception is FirebaseAuthRecentLoginRequiredException){
                    _isExpireConnectionTime.value= true
                }
            }
        }
        // update the photo uri
        if(userName.isBlank()){
            profileUpdates = UserProfileChangeRequest
                .Builder()
                .setPhotoUri(Uri.parse(avatarImage))
                .build()
        }
        // update username and avatar image of account creation interface
        if(userName.isNotBlank()){
            profileUpdates = UserProfileChangeRequest
                .Builder()
                .setDisplayName(userName)
                .setPhotoUri(Uri.parse(avatarImage))
                .build()


        }
        // process updates
        if (profileUpdates != null){
            // update and listen to updates events
            authUser?.apply {
                updateProfile(profileUpdates!!).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        // save new user to fireStore
                        val user = User(
                            name = authUser.displayName,
                            phoneNumber = authUser.phoneNumber,
                            avatar = authUser.photoUrl,
                            notification = false,
                            darkMode = false
                        )
                        val db = Firebase.firestore
                        if(!isUpdated){
                            db.collection("users").add(user)
                                .addOnCompleteListener {
                                    _isProfileUpdated.value = true

                                }
                                .addOnFailureListener{e ->
                                    _isErrorUpdatedProfile.value = true
                                }
                        }else{
                            db.collection("users")
                                .document().set(user, SetOptions.merge())
                                .addOnCompleteListener {
                                    _isProfileUpdated.value = true

                                }
                                .addOnFailureListener{e ->
                                    _isErrorUpdatedProfile.value = true
                                }
                        }
                        }

                    if(task.exception is Exception){
                        _isErrorUpdatedProfile.value = true
                    }
                }
            }
        }
    }

   fun processToUpdatesProfile(userName: String, phoneNumber: String, isUpdated: Boolean = false){
       updateUserProfile(userName = userName, phoneNumber = phoneNumber, isUpdated = isUpdated)
   }

    fun uploadImage(bitmap: Bitmap, userName: String, phoneNumber: String = "",
                    isUpdated: Boolean = false){
        // set loading
        _isUpdateProfileLoading.value = true
        // get authenticated user
        val authUser = Firebase.auth.currentUser
        if(authUser != null){
            // Create a storage reference from our app
            val storage = Firebase.storage
            val storageRef = storage.reference
            // create reference for users images profiles
            val imageName = "${randomStr(7)}.png"
            val imageUri = Uri.parse(imageName)
            val imagesRef: StorageReference? = storageRef
                .child("users/${imageName}")
            // create outputstream from image
            val baos = ByteArrayOutputStream()
            // compress our image to bitmap format
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            // process to upload image on cloud firestore
            val data = baos.toByteArray()//
            val uploadTask = imagesRef?.putBytes(data)
            uploadTask?.addOnFailureListener {
                _isErrorUploadImage.value = true
            }?.addOnSuccessListener { task ->
                _isSuccessfullyUpload.value = true
                Log.d(TAG, "Successfully upload image")
                if(userName.isNotBlank()){
                    updateUserProfile(userName = userName,
                        avatarImage = imageName, isUpdated = isUpdated)
                }else{
                    // update firebase photo url for update only avatar image
                    updateUserProfile(userName = "", avatarImage = imageName, isUpdated = isUpdated)

                }
            }
        }else{
            _isErrorAuthenticated.value = true
        }

    }

    companion object{
        val TAG = "Activity"
    }

}