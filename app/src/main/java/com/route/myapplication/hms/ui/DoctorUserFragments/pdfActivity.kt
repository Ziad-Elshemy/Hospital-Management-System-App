package com.route.myapplication.hms.ui.DoctorUserFragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.github.barteksc.pdfviewer.PDFView
import com.route.myapplication.hms.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.Manifest

class pdfActivity : AppCompatActivity() {
    lateinit var pdfView: PDFView
    lateinit var bmp: Bitmap
    private lateinit var scaledbmp: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        pdfView = findViewById(R.id.pdfView)

        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),PackageManager.PERMISSION_GRANTED)

        bmp = BitmapFactory.decodeResource(resources, R.drawable.fayoum_uni_logo)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 250, 250, false)

        createPDF()

    }


    private fun createPDF() {
        val prescriptionPDF = PdfDocument()
        //val titlePaint = Paint()
        val myPaint = Paint()


        val pageInfo = PdfDocument.PageInfo.Builder(400, 500, 1).create()
        val prescriptionPage = prescriptionPDF.startPage(pageInfo)

        val canvas = prescriptionPage.canvas
        canvas.drawBitmap(scaledbmp, 40f, 50f, myPaint)

//        canvas.drawText("Fayoum University Hospitals", 40f, 50f, titlePaint)
        prescriptionPDF.finishPage(prescriptionPage)



        var file: File = File(Environment.getExternalStorageDirectory(), "/test.pdf")


        try {
            prescriptionPDF.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        prescriptionPDF.close()


    }
}