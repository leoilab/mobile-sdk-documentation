package com.omhu.imageoutline.demo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.omhu.imageoutline.OutlineGenerator
import com.omhu.imageoutline.OutlineType
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        populateImageView()
    }

    private fun populateImageView() {
        val bitmap = getTestImageFromAssets()
        val bitmapPath = saveBitmapToDisk(bitmap) ?: return

        val outlineGenerator = OutlineGenerator(this, BuildConfig.IMAGE_OUTLINE_API_KEY)
        val callback = object : OutlineGenerator.OnOutlineGeneratedCallback {
            override fun onOutlineGenerated(path: String) {
                findViewById<ImageView>(R.id.image).setImageURI(Uri.parse(path))
            }

            override fun onError(reason: Throwable) {
                Log.e(this@MainActivity.javaClass.simpleName, "Outline generation failed", reason)
            }
        }

        outlineGenerator.generateOutline(
            sourcePath = bitmapPath,
            outlineType = OutlineType.OUTLINE_COLOR,
            callback = callback
        )
    }

    private fun getTestImageFromAssets(): Bitmap {
        var inputStream: InputStream? = null
        try {
            inputStream = assets.open(TEST_IMAGE_PATH)
        } catch (error: IOException) {
            Log.e(
                this@MainActivity.javaClass.simpleName,
                "Failed to read the test image from assets",
                error
            )
        }

        return BitmapFactory.decodeStream(inputStream)
    }

    private fun saveBitmapToDisk(bitmap: Bitmap): String? {
        val file = File(cacheDir, "temp.jpg")
        return try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            file.absolutePath
        } catch (error: IOException) {
            Log.e(
                this@MainActivity.javaClass.simpleName,
                "Failed to save bitmap to disk",
                error
            )
            null
        }
    }

    companion object {
        private const val TEST_IMAGE_PATH = "test_image.jpg"
    }
}
