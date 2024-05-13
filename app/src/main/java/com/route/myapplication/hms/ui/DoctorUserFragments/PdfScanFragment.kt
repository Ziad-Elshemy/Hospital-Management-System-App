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
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfScanFragment : Fragment() {
    var scansList: List<GetPatientsScansByPatientIdResponseItem> = arrayListOf()
    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var pdfView: PDFView
    lateinit var bmp: Bitmap
    private lateinit var scaledbmp: Bitmap

    lateinit var bmp2: Bitmap
    private lateinit var scaledbmp2: Bitmap

    lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val item: GetPatientsScansByPatientIdResponseItem = bundle!!.getSerializable("item") as GetPatientsScansByPatientIdResponseItem

        pdfView = requireView().findViewById(R.id.pdfView)

        bmp = BitmapFactory.decodeResource(resources,R.drawable.fayoum_uni_logo)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 150, 150, false)

        getScans(item.patientId!!)
        getpatientById(item.patientId!!,item)
    }

    private fun getScans(patientId: Int) {

        ApiManager.getApis().getPatientScansByPatientId(patientId)
            .enqueue(object : Callback<ArrayList<GetPatientsScansByPatientIdResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetPatientsScansByPatientIdResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetPatientsScansByPatientIdResponseItem>>,
                    response: Response<ArrayList<GetPatientsScansByPatientIdResponseItem>>
                ) {
                    scansList = response.body()!!.toList()

                }
            })
    }

    private fun getpatientById(patientId: Int,item: GetPatientsScansByPatientIdResponseItem) {

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

    private fun createPDF(item: GetPatientsScansByPatientIdResponseItem) {

        val scanPDF = PdfDocument()
        val myPaint = Paint()
        val titlePaint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(800, 1200, 1).create()
        val scanPage = scanPDF.startPage(pageInfo)

        val canvas = scanPage.canvas



        canvas.drawBitmap(scaledbmp, 40f, 50f, myPaint)
        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 35F
        canvas.drawText("Fayoum University Hospital", 450f, 105f, titlePaint)

        myPaint.textAlign = Paint.Align.CENTER
        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        canvas.drawText("Doctor:"+" " + item.doctorName, 400f, 160f, myPaint)

        canvas.drawText("Patient:"+" " + patient?.firstName+" "+patient?.lastName, 360f, 190f, myPaint)

        canvas.drawText("Age:" +" "+ "43", 330f, 230f, myPaint)

        canvas.drawText("Scan Name:" +" "+ item.scanName, 370f, 260f, myPaint)
       // canvas.drawLine(40f,300f,750f,300f,myPaint)

        canvas.drawText("Date:" +" "+ item.scanDate!!.substring(0, item.scanDate!!.indexOf("T")), 360f, 290f, myPaint)
        canvas.drawLine(40f,330f,750f,330f,myPaint)

        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 50F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        canvas.drawText("Report", 80f, 400f, myPaint)

        bmp2 = BitmapFactory.decodeResource(resources,R.drawable.chest_xray)
        scaledbmp2 = Bitmap.createScaledBitmap(bmp2, 650, 500, false)
        canvas.drawBitmap(scaledbmp2, 90f, 450f, myPaint)



        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 25F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)


        //////////////////separate lines between written report //////////////////

        canvas.drawText("Radiographic examination of the chest by means of PA", 90f, 1000f, myPaint)
//         (Boyden segment #5) obliterating\n" +
//                "the right cardiac margin (positive silhouette sign). The tracheal silhouette is midline and the\n" +
//                "lungfields appear well-pneumatized without atelectasis. ", 90f, 770f, myPaint)

        canvas.drawText("and lateral projections reveals ill-defined consolidation"
                , 90f, 1030f, myPaint)

        canvas.drawText("within the medial aspect of the right middle lobe(Boyden", 90f, 1060f, myPaint)

        canvas.drawText("segment #5) the right cardiac margin (positive silhouette sign)."
            , 90f, 1090f, myPaint)


        canvas.drawText(" The tracheal silhouette is midline and thelungfields appear well-pneumatized without atelectasis."
            , 90f, 1120f, myPaint)

        ////////////////// scan images are not handled yet/////////////////////

        scanPDF.finishPage(scanPage)


        //var file: File = File(Environment.getExternalStorageDirectory(), "/prescription.pdf")
        file  = File(requireContext().getExternalFilesDir("/"),"scan.pdf")

        pdfView.fromFile(file).load()

        try {
            scanPDF.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        scanPDF.close()


    }





}