package com.app.neostoreassessment13531.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object FileHelper {

    fun saveBitmapToCacheAndGetUri(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "picked_image_${System.currentTimeMillis()}.jpg")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
}