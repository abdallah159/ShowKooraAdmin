package showkooraadmin.ka.showkooraadmin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*MobileAds.initialize(this, "ca-app-pub-7647915106985195/9847131670")

        lateinit var mAdView: AdView

        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-7647915106985195/9319791713"

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)*/


        cityBTN.setOnClickListener {
            startActivity(Intent(this, AddCityNewsActivity::class.java))

        }

        worldBTN.setOnClickListener {
            startActivity(Intent(this, AddWorldNewsActivity::class.java))

        }

/*
        shareBTN.setOnClickListener {
            val bitmap = getBitmapFromView(shKoraIV)
            try {
                val file = File(this.externalCacheDir, "logicchip.png")
                val fOut = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                file.setReadable(true, false)
                val intent = Intent(android.content.Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                intent.putExtra(Intent.EXTRA_TEXT, "Test bla bla bla")
                intent.type = "image/png"
                startActivity(Intent.createChooser(intent, "Share image via"))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }*/
    }


 /*   private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.getBackground()
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable!!.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }*/


}
