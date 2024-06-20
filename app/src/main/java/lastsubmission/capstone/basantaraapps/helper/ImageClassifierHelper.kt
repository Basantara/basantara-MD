package lastsubmission.capstone.basantaraapps.helper

import lastsubmission.capstone.basantaraapps.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import java.io.IOException




class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResult: Int = 1,
    var modelName: String = "model.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?) {

    private var imageClassifier: ImageClassifier? = null
    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            inferenceTime: Long
        )
    }

    private fun setupImageClassifier() {

        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResult)

        val baseOptionsBuilder = BaseOptions.builder().setNumThreads(4)

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(context, modelName, optionsBuilder.build())

        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(imageUri: Uri) {

//        if (imageClassifier == null) {
//            setupImageClassifier()
//        }
//
//        val imageProcessor = ImageProcessor.Builder()
//            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
//            .add(CastOp(DataType.UINT8))
//            .build()
//
//        val bitmap = uriToBitmap(context, imageUri)
//        val imageTensor = imageProcessor.process(TensorImage.fromBitmap(bitmap))
//
//        var inferenceTime = SystemClock.uptimeMillis()
//        val results = imageClassifier?.classify(imageTensor)
//        inferenceTime = SystemClock.uptimeMillis() -inferenceTime
//        classifierListener?.onResults(results, inferenceTime)
        // TODO: mengklasifikasikan imageUri dari gambar statis.
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        // Processor gambar
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .build()

        val bitmap = uriToBitmap(context, imageUri)
        val inputImage = TensorImage(DataType.FLOAT32)
        inputImage.load(bitmap)

        // Normalisasi manual
        val buffer = inputImage.buffer
        val pixels = FloatArray(224 * 224 * 3) // Jumlah piksel (224x224) x 3 (RGB)

        for (i in 0 until 224 * 224) {
            pixels[i * 3] = ((buffer.getFloat(i * 3) - MEAN) / STD_DEV) // R
            pixels[i * 3 + 1] = ((buffer.getFloat(i * 3 + 1) - MEAN) / STD_DEV) // G
            pixels[i * 3 + 2] = ((buffer.getFloat(i * 3 + 2) - MEAN) / STD_DEV) // B
        }

        // Isi kembali buffer dengan nilai piksel yang telah dinormalisasi
        for (i in 0 until 224 * 224 * 3) {
            buffer.putFloat(i * 4, pixels[i])
        }

        // Terapkan image processor ke gambar masukan
        val processedImage = imageProcessor.process(inputImage)

        // Lakukan inferensi
        var inferenceTime = SystemClock.uptimeMillis()
        val results = imageClassifier?.classify(processedImage)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        // Handle hasil
        classifierListener?.onResults(results, inferenceTime)

    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }



    companion object {
        private const val TAG = "ImageClassifierHelper"
        private const val MEAN = 127.5f // Sesuaikan dengan nilai mean dari model Anda
        private const val STD_DEV = 127.5f // Sesuaikan dengan nilai std dev dari model Anda
    }
}