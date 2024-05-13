package com.route.myapplication.hms.ui.DoctorUserFragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfFragment : Fragment() {

    var prescriptionsList: List<GetAllPrescriptionsOfPatientResponseItem> = arrayListOf()
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
        return inflater.inflate(R.layout.fragment_pdf, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = requireArguments()
        val item: GetAllPrescriptionsOfPatientResponseItem = bundle!!.getSerializable("item") as GetAllPrescriptionsOfPatientResponseItem

        pdfView = requireView().findViewById(R.id.pdfView)

        bmp = BitmapFactory.decodeResource(resources,R.drawable.fayoum_uni_logo)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 150, 150, false)
//        file=File(requireContext().getExternalFilesDir("/"),"trial1.pdf")

        getPrescription(item.prescription?.patientId!!)
        getpatientById(item.prescription?.patientId!!,item)

    }

    private fun createPDF(item: GetAllPrescriptionsOfPatientResponseItem) {

        val prescriptionPDF = PdfDocument()
        val myPaint = Paint()
        val titlePaint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(800, 1200, 1).create()
        val prescriptionPage = prescriptionPDF.startPage(pageInfo)

        val canvas = prescriptionPage.canvas


        canvas.drawBitmap(scaledbmp, 40f, 50f, myPaint)
        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 35F
        canvas.drawText("Fayoum University Hospitals", 450f, 105f, titlePaint)

        myPaint.textAlign = Paint.Align.CENTER
        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        canvas.drawText("Doctor:"+" " + item.doctorFullName, 400f, 160f, myPaint)

        canvas.drawText("Department:"+" " + item.department, 400f, 190f, myPaint)

        canvas.drawText("Patient:"+" " + patient?.firstName+" "+patient?.lastName, 400f, 240f, myPaint)

        canvas.drawText("Age:" +" "+"43", 340f, 270f, myPaint)
        canvas.drawLine(40f,300f,750f,300f,myPaint)

        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 95F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        canvas.drawText("Rx", 80f, 400f, myPaint)


        var i=0
        var y3 =530f
        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 25F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)


        item.prescription?.prescriptionItems?.forEach {
            i++
            Log.e("i",i.toString())
            if (i==1){
                canvas.drawText(  "$i. Medicine Name : "+it?.medicineName+"                  "+"Type : "+it?.medicineType, 95f, 470f, myPaint)
                canvas.drawText(  "   Dose : "+it?.dose+"    "+"Duration : "+it?.durarion+"    "+"Concentration : "+it?.medicineConcentration, 95f, 500f, myPaint)
                canvas.drawText("   Instructions : " +it?.instructions, 95f, y3, myPaint)
            }
            if(i > 1){
                if(i==3)
                    y3 += 50 * (i - 1)
                if(i >= 4)
                    y3 += 50*(0.6f) * (i - 1)

                canvas.drawText(  "$i. Medicine Name : "+it?.medicineName+"                  "+"Type : "+it?.medicineType, 95f, y3+50, myPaint)
                canvas.drawText(  "   Dose : "+it?.dose+"    "+"Duration : "+it?.durarion+"    "+"Concentration : "+it?.medicineConcentration, 95f, y3+80, myPaint)
                canvas.drawText("   Instructions : " +it?.instructions, 95f, y3+100, myPaint)

            }
        }

        titlePaint.textAlign = Paint.Align.LEFT
        titlePaint.textSize = 22F
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Prescription Date : ", 95f, 1170f, titlePaint)

        titlePaint.textAlign = Paint.Align.LEFT
        titlePaint.textSize = 20F
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        val prescription_date = item.prescription?.prescriptionDate?.substring(0,item.prescription?.prescriptionDate.indexOf("T"))+"  "+item.prescription?.prescriptionDate?.substring(item.prescription?.prescriptionDate.indexOf("T")+1,16)
        canvas.drawText(prescription_date!!, 95f, 1190f, titlePaint)

        titlePaint.textAlign = Paint.Align.RIGHT
        titlePaint.textSize = 22F
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText(" Re-Examination Date : ", 750f, 1170f, titlePaint)

        titlePaint.textAlign = Paint.Align.LEFT
        titlePaint.textSize = 20F
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        val reappointement_date = item.prescription?.reAppointementDate?.substring(0,item.prescription?.reAppointementDate.indexOf("T"))+"  "+item.prescription?.reAppointementDate?.substring(item.prescription?.reAppointementDate.indexOf("T")+1,16)

        canvas.drawText(reappointement_date!!, 550f, 1190f, titlePaint)


        prescriptionPDF.finishPage(prescriptionPage)


        //var file: File = File(Environment.getExternalStorageDirectory(), "/prescription.pdf")
         file  = File(requireContext().getExternalFilesDir("/"),"trial1.pdf")

        pdfView.fromFile(file).load()

        try {
            prescriptionPDF.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        prescriptionPDF.close()


    }

    private fun getPrescription(patientId: Int) {

        ApiManager.getApis().getPrescriptions(patientId)
            .enqueue(object : Callback<ArrayList<GetAllPrescriptionsOfPatientResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>,
                    response: Response<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>
                ) {
                    prescriptionsList = response.body()!!.toList()
                }
            })
    }

    private fun getpatientById(patientId: Int,item: GetAllPrescriptionsOfPatientResponseItem) {

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


}