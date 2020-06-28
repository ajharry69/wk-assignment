package com.xently.persona.utils

import android.graphics.BitmapFactory
import android.widget.ImageView

/**
 * scales down the image contained in [imagePath] to the size of [ImageView] and sets
 * it to [this@setImageFromPath]
 * @param imagePath absolute file path of image to be shown on [this@setImageFromPath]
 * ([ImageView])
 */
fun ImageView.setImageFromPath(imagePath: String) {
    val bmOptions = BitmapFactory.Options().apply {
        // Get the dimensions of the bitmap
        inJustDecodeBounds = true

        val photoW: Int = outWidth
        val photoH: Int = outHeight

        // Determine how much to scale down the image
        val scaleFactor: Int = (photoW / width).coerceAtMost(photoH / height)

        // Decode the image file into a Bitmap sized to fill the View
        inJustDecodeBounds = false
        inSampleSize = scaleFactor
        // inPurgeable = true
    }
    BitmapFactory.decodeFile(imagePath, bmOptions)?.also { bitmap ->
        setImageBitmap(bitmap)
    }
}