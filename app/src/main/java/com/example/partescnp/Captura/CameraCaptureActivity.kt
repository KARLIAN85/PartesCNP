package com.example.partescnp.Captura

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Size
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.example.partescnp.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraCaptureActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var imageView: ImageView
    private lateinit var btnCapture: Button
    private lateinit var btnProcessText: Button
    private lateinit var textResult: TextView
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var capturedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_capture)

        // Vincular vistas del layout
        previewView = findViewById(R.id.cameraPreview)
        imageView = findViewById(R.id.imageView)
        btnCapture = findViewById(R.id.btnCapture)
        btnProcessText = findViewById(R.id.btnProcessText)
        textResult = findViewById(R.id.textResult)

        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()

        // Capturar imagen
        btnCapture.setOnClickListener {
            captureImage()
        }

        // Procesar texto
        btnProcessText.setOnClickListener {
            if (capturedBitmap != null) {
                processTextFromImage(capturedBitmap!!)
            } else {
                Toast.makeText(this, "Primero captura una imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // Configurar ImageCapture con relación de aspecto fija
            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(1280, 720)) // Relación de aspecto fija
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun captureImage() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    val bitmap = imageProxy.toBitmap()
                    imageProxy.close()

                    // Rotar y mostrar la imagen en el ImageView
                    val rotatedBitmap = rotateBitmap(bitmap, 90)
                    capturedBitmap = rotatedBitmap
                    imageView.setImageBitmap(rotatedBitmap) // Mostrar imagen rotada
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(this@CameraCaptureActivity, "Error al capturar la imagen", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun processTextFromImage(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                textResult.text = visionText.text // Mostrar texto procesado en el TextView
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al procesar el texto: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun ImageProxy.toBitmap(): Bitmap {
        val buffer: ByteBuffer = planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        // Decodificar los bytes en un Bitmap
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
