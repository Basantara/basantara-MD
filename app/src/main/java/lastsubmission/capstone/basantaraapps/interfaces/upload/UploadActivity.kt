package lastsubmission.capstone.basantaraapps.interfaces.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.databinding.ActivityUploadBinding
import com.yalantis.ucrop.UCrop
import lastsubmission.capstone.basantaraapps.helper.ImageClassifierHelper

import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File


    private var currentImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cameraExecutor = Executors.newSingleThreadExecutor()
        outputDirectory = getOutputDirectory()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.cameraButton.setOnClickListener {
            takePhoto()
        }

        binding.galleryButton.setOnClickListener {
            startGallery()

        }

        binding.scanningButton.setOnClickListener {

        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP &&  resultCode == Activity.RESULT_OK) {
            handleUCropResult(data)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleUCropError(data)
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array< String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Izin tidak diberikan oleh pengguna.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }



    private fun takePhoto() {
        val photoFile = File(
            outputDirectory,
            "${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraX", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Log.d("CameraX", "Photo capture succeeded: $savedUri")
                    // Show the image
                    binding.imageView.setImageURI(savedUri)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch(exc: Exception) {
                Log.e("CameraX", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }


    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                currentImageUri = it
                binding.imageView.setImageURI(null)
                cutUsingUCrop()
            }?: Log.d("Photo Picker", "no media selected")
        }

    private fun startGallery() {

        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun cutUsingUCrop() {
        currentImageUri?.let { uri ->
            val outputUri = Uri.fromFile(File(cacheDir, "crop_image.jpg"))
            UCrop.of(uri, outputUri)
                .withOptions(UCrop.Options().apply {
                    setCompressionFormat(Bitmap.CompressFormat.JPEG)
                    setCompressionQuality(90)
                })
                .start(this)
        }
    }

    private fun handleUCropResult(data: Intent?) {
        val resultUri = data?.let { UCrop.getOutput(it) }
        currentImageUri = resultUri
        showImage(resultUri)
    }

    private fun handleUCropError(data: Intent?) {
        val cropError = data?.let { UCrop.getError(it) }
        Log.e("uCrop", "Error cropping image: $cropError")
    }

    private fun showImage(uri: Uri?) {

        uri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageView.setImageURI(it)
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    }

}