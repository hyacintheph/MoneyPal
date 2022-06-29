package com.example.moneypal.models

 class Status(
    var isInvalidCredential: Boolean = false,
    var isTooManyRequest: Boolean = false,
    var isSuccessfullyUpload: Boolean = false,
    var isErrorUploadImage: Boolean = false,
    var isProfileUpdated: Boolean = false,
    var isSuccessfullyAuthenticated: Boolean = false,
    var isErrorAuthenticated: Boolean = false,
    var isInputCodeInvalid: Boolean = false,
    var isUpdateProfileLoading: Boolean = false,
    var isCodeVerificationSend: Boolean = false,
    var isErrorUpdatedProfile: Boolean = false,
    var isPhoneNumberSuccessfullyUpdated: Boolean = false,
    var isPhoneNumberAlreadyExist: Boolean = false,
    var isInvalidUser: Boolean = false,
    var isExpireConnectionTime: Boolean = false,
    var isInvalidAccount: Boolean = false,
){}