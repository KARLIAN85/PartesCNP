package com.example.partescnp.utils

import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

fun cropToOverlay(bitmap: Bitmap, overlayFrame: ConstraintLayout, previewView: View): Bitmap {
    // Obtener el rectángulo global del overlay
    val rect = Rect()
    overlayFrame.getGlobalVisibleRect(rect)

    // Ajustar las coordenadas del rectángulo al PreviewView
    val previewRect = Rect()
    previewView.getGlobalVisibleRect(previewRect)

    rect.offset(-previewRect.left, -previewRect.top)

    // Validar las dimensiones del rectángulo
    val x = rect.left.coerceIn(0, bitmap.width)
    val y = rect.top.coerceIn(0, bitmap.height)
    val width = (rect.width()).coerceIn(1, bitmap.width - x)
    val height = (rect.height()).coerceIn(1, bitmap.height - y)

    Log.d("CropToOverlay", "Cropping dimensions: x=$x, y=$y, width=$width, height=$height")
    Log.d("CropToOverlay", "Bitmap dimensions: width=${bitmap.width}, height=${bitmap.height}")

    // Crear el bitmap recortado
    return Bitmap.createBitmap(bitmap, x, y, width, height)
}
