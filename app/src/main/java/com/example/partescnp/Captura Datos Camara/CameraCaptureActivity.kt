package com.example.partescnp.Captura

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.partescnp.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File

class CameraCaptureActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnCapture: Button
    private lateinit var btnProcessText: Button
    private lateinit var textResult: TextView

    private var capturedImagePath: String? = null // Ruta de la imagen capturada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_capture)

        imageView = findViewById(R.id.imageView)
        btnCapture = findViewById(R.id.btnCapture)
        btnProcessText = findViewById(R.id.btnProcessText)
        textResult = findViewById(R.id.textResult)

        btnCapture.setOnClickListener {
            openCamera()
        }

        btnProcessText.setOnClickListener {
            if (capturedImagePath != null) {
                processTextFromImage(capturedImagePath!!)
            } else {
                Toast.makeText(this, "Primero captura una imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imageFile = createImageFile()
        capturedImagePath = imageFile.absolutePath
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFile.toURI())
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(capturedImagePath)
            imageView.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Error al capturar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(null)
        return File.createTempFile("IMG_", ".jpg", storageDir)
    }

    private fun processTextFromImage(imagePath: String) {
        val bitmap = BitmapFactory.decodeFile(imagePath)
        val image = InputImage.fromBitmap(bitmap, 0)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                textResult.text = visionText.text
                Toast.makeText(this, "Texto procesado correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al procesar el texto: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 1001
    }
}

