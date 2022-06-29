package com.example.moneypal.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

fun SingleRouteNavigation(navController: NavHostController, route: String){
    navController.navigate(route)
}
fun splitCode(code: String, index: Int): String{
    return code.split("")[index]
}
fun SingleRouteNavigationWithPopUp(navController: NavHostController, route: String, toPopUp: String){
    navController.navigate(route){
        popUpTo(toPopUp)
    }
}

fun convertUriToBitmap(uri: Uri?, context: Context): Bitmap?{
    var bitmapImage: Bitmap? = null
    try {
        uri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmapImage  = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val imageSource = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmapImage = ImageDecoder.decodeBitmap(imageSource)
            }
        }
    }catch (e: Exception){
        e.printStackTrace()
    }
    return bitmapImage
}

fun randomStr(length: Int): String{
    val pool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { kotlin.random.Random.nextInt(0, pool.size) }
        .map(pool::get)
        .joinToString("");
}

// return correct activity
fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

