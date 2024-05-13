package com.route.myapplication.hms.ui.DoctorUserFragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientTestsByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfLabTestFragment : Fragment() {
    var testsList: List<GetPatientTestsByPatientIdResponseItem> = arrayListOf()
    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var pdfView: PDFView
    lateinit var bmp: Bitmap
    private lateinit var scaledbmp: Bitmap
    lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf_lab_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val item: GetPatientTestsByPatientIdResponseItem = bundle!!.getSerializable("item") as GetPatientTestsByPatientIdResponseItem

        Log.e("item at pdf",item.toString())
        pdfView = requireView().findViewById(R.id.pdfView)

        bmp = BitmapFactory.decodeResource(resources,R.drawable.fayoum_uni_logo)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 150, 150, false)

        getTests(item.patientId!!)
        getpatientById(item.patientId!!,item)
    }

    private fun getpatientById(patientId: Int,item: GetPatientTestsByPatientIdResponseItem) {

        ApiManager.getApis().getPatientByIdResponse(patientId)
            .enqueue(object : Callback<GetPatientByIdResponse> {
                override fun onFailure(
                    call: Call<GetPatientByIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetPatientByIdResponse>,
                    response: Response<GetPatientByIdResponse>
                ) {
                    patient = response.body()!!
                    createPDF(item)
                    Log.e("patient",patient.toString())

                }

            })
    }


    private fun getTests(patientId: Int) {
        ApiManager.getApis().getPatientTestsByPatientId(patientId)
            .enqueue(object : Callback<ArrayList<GetPatientTestsByPatientIdResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetPatientTestsByPatientIdResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetPatientTestsByPatientIdResponseItem>>,
                    response: Response<ArrayList<GetPatientTestsByPatientIdResponseItem>>
                ) {
                    testsList = response.body()!!.toList()

                }
            })

    }


    private fun createPDF(item: GetPatientTestsByPatientIdResponseItem) {

        val testPDF = PdfDocument()
        val myPaint = Paint()
        val titlePaint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(800, 1200, 1).create()
        val testPage = testPDF.startPage(pageInfo)

        val canvas = testPage.canvas


        canvas.drawBitmap(scaledbmp, 40f, 50f, myPaint)
        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 35F
        canvas.drawText("Fayoum University Hospitals", 450f, 105f, titlePaint)

        myPaint.textAlign = Paint.Align.CENTER
        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        canvas.drawText("Doctor:"+" " + item.doctorName, 400f, 160f, myPaint)

        canvas.drawText("Patient:"+" " + patient?.firstName+" "+patient?.lastName, 400f, 190f, myPaint)

        canvas.drawText("Age:" +" "+ "43", 330f, 230f, myPaint)

        canvas.drawText("Test Name:" +" "+ item.testName, 370f, 260f, myPaint)
        // canvas.drawLine(40f,300f,750f,300f,myPaint)

        canvas.drawText("Test Date:" +" "+ item.testDate!!.substring(0, item.testDate!!.indexOf("T")), 400f, 290f, myPaint)
        canvas.drawLine(40f,330f,750f,330f,myPaint)

//        myPaint.textAlign = Paint.Align.LEFT
//        myPaint.textSize = 70F
//        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
//        canvas.drawText("Report", 80f, 400f, myPaint)

        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        myPaint.style=Paint.Style.STROKE
        myPaint.strokeWidth= 2F
        canvas.drawRect(20F,360F,780F,400F,myPaint)

        myPaint.textAlign=Paint.Align.LEFT
        myPaint.style=Paint.Style.FILL
        canvas.drawText("Parameter",40F,390F,myPaint)
        canvas.drawText("Result",350F,390F,myPaint)
        canvas.drawText("Unit",470F,390F,myPaint)
        canvas.drawText("Reference Value",550F,390F,myPaint)

        canvas.drawLine(340F,358F,340F,400F,myPaint)
        canvas.drawLine(460F,358F,460F,400F,myPaint)
        canvas.drawLine(540F,358F,540F,400F,myPaint)

        var i=0
        var y =450f
        myPaint.textSize = 20F
        item.numericalDetails?.forEach {
            i++
            if (i==1){
            canvas.drawText(  it?.testParameterName.toString(), 30f, y, myPaint)
            canvas.drawText(  it?.numericalValue.toString(), 380f, y, myPaint)
            canvas.drawText(  it?.unit.toString(), 480f, y, myPaint)
            canvas.drawText(  it?.minRange.toString()+" - "+it?.maxRange.toString(), 630f, y, myPaint)
            canvas.drawLine(20f,y+10,780f,y+10,myPaint)
            }
            if(i>1){
                    y+=60f
                canvas.drawText(  it?.testParameterName.toString(), 30f, y, myPaint)
                canvas.drawText(  it?.numericalValue.toString(), 380f, y, myPaint)
                canvas.drawText(  it?.unit.toString(), 480f, y, myPaint)
                canvas.drawText(  it?.minRange.toString()+" - "+it?.maxRange.toString(), 630f, y, myPaint)
                canvas.drawLine(20f,y+10,780f,y+10,myPaint)
            }

        }






        testPDF.finishPage(testPage)


        //var file: File = File(Environment.getExternalStorageDirectory(), "/prescription.pdf")
        file  = File(requireContext().getExternalFilesDir("/"),"test.pdf")

        pdfView.fromFile(file).load()

        try {
            testPDF.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        testPDF.close()


    }


}