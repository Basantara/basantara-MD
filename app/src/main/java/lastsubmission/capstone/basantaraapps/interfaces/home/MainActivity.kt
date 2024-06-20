package lastsubmission.capstone.basantaraapps.interfaces.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lastsubmission.capstone.basantaraapps.R

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lastsubmission.capstone.basantaraapps.data.responses.PredictResponse
import lastsubmission.capstone.basantaraapps.data.retrofit.ML.ApiConfigML
import lastsubmission.capstone.basantaraapps.databinding.ActivityMainBinding
import lastsubmission.capstone.basantaraapps.helper.ImageClassifierHelper
import lastsubmission.capstone.basantaraapps.helper.getImageUri
import lastsubmission.capstone.basantaraapps.interfaces.camera.CameraActivity
import lastsubmission.capstone.basantaraapps.interfaces.camera.CameraActivity.Companion.CAMERAX_RESULT
import lastsubmission.capstone.basantaraapps.interfaces.result.ResultActivity
import lastsubmission.capstone.basantaraapps.interfaces.result.ResultViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null

    private lateinit var imageClassifier: ImageClassifier
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val viewModel: ResultViewModel by viewModels()
    private val PICK_IMAGE = 100
    private lateinit var imageUri: Uri


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.uploadButton.setOnClickListener {
            currentImageUri?.let { uri ->
                uploadImage(uri)
            } ?: showToast(getString(R.string.empty_image))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP &&  resultCode == Activity.RESULT_OK) {
            handleUCropResult(data)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleUCropError(data)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                currentImageUri = it
                binding.previewImageView.setImageURI(null)
                cutUsingUCrop()
            }?: Log.d("Photo Picker", "no media selected")
        }

    @Suppress("DEPRECATION")
    private fun startCamera() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage(currentImageUri)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage(currentImageUri)
        }
    }

    private fun showImage(uri: Uri?) {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
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


    private fun uploadImage(uri: Uri) {
        binding.progressIndicator.visibility = android.view.View.VISIBLE

        val file = File(uri.path ?: return)
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val multipartBody = MultipartBody.Part.createFormData("imageFile", file.name, requestFile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfigML.getApiService().predict(multipartBody).execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) {
                            binding.progressIndicator.visibility = android.view.View.GONE
                            moveResultIntent(uri, it)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.progressIndicator.visibility = android.view.View.GONE
                        showToast("Error: ${response.message()}")
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    binding.progressIndicator.visibility = android.view.View.GONE
                    showToast("Error: ${e.message()}")
                }
            }
        }
    }

//    private fun analyzeImage(uri: Uri) {
//        imageClassifierHelper = ImageClassifierHelper(
//            context = this,
//            classifierListener = object : ImageClassifierHelper.ClassifierListener {
//                override fun onError(error: String) {
//                    runOnUiThread {
//                        Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
//                    runOnUiThread {
//                        results?.let { it ->
//                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
//                                println(it)
//                                val sortedCategories =
//                                    it[0].categories.sortedByDescending { it?.score }
//                                val displayResult =
//                                    sortedCategories.joinToString("\n") {
//                                        "${it.label} " + DecimalFormat("#.##").format(it.score).trim()
//                                            .format(it.score).trim()
//                                    }
//                                moveResultIntent(uri, displayResult)
//                            }
//                        }
//                    }
//                }
//            }
//        )
//        imageClassifierHelper.classifyStaticImage(uri)
//    }

    private fun moveResultIntent(uri: Uri, response: PredictResponse) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
            putExtra(ResultActivity.EXTRA_RESULT, "${response.data.className} (${response.data.confidenceScore})")
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}