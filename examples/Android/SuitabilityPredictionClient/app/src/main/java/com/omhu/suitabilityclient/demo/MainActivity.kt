package com.omhu.suitabilityclient.demo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.omhu.suitabilityclient.SuitabilityPredictionClient
import com.omhu.suitabilityclient.SuitabilityResult
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testBitmap = getTestImageFromAssets()
        findViewById<ImageView>(R.id.test_image).setImageBitmap(testBitmap)

        val results =
            SuitabilityPredictionClient(this, BuildConfig.SUITABILITY_API_KEY).predict(testBitmap)
        findViewById<TextView>(R.id.results_value).text = formatResults(results)
    }

    private fun formatResults(results: SuitabilityResult): String {
        return "CNS=${results.cns}\n" +
                "SNV=${results.snv}\n" +
                "NEP=${results.nep}\n" +
                "LESION=${results.lesion}\n"
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

    companion object {
        private const val TEST_IMAGE_PATH = "test_image.jpg"
    }
}
