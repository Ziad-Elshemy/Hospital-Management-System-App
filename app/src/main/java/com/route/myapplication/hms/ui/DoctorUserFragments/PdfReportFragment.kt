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
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientReportResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.roundToInt

class PdfReportFragment : Fragment() {

    var report: Result? = null
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
        return inflater.inflate(R.layout.fragment_pdf_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val requestReport : PatientReportRequest = bundle!!.getSerializable("requestReport") as PatientReportRequest

        pdfView = requireView().findViewById(R.id.pdfView)

        bmp = BitmapFactory.decodeResource(resources,R.drawable.fayoum_uni_logo)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 150, 150, false)

        getReport(requestReport.patientId,requestReport.dischargeDate)

    }


    private fun getReport(patientId: Int,dischargeDate: String) {

        ApiManager.getApis().getPatientReport(patientId,dischargeDate)
            .enqueue(object : Callback<GetPatientReportResponse> {
                override fun onFailure(
                    call: Call<GetPatientReportResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetPatientReportResponse>,
                    response: Response<GetPatientReportResponse>
                ) {
                    report = response.body()?.result
                    getpatientById(report?.patientId!!)

                }
            })
    }

    private fun getpatientById(patientId: Int) {

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
                    createPDF(report)
                }

            })
    }

    private fun createPDF(report: Result?) {

        val scanPDF = PdfDocument()
        val myPaint = Paint()
        val titlePaint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(800, 1200, 2).create()
        val scanPage = scanPDF.startPage(pageInfo)

        val canvas = scanPage.canvas


        canvas.drawBitmap(scaledbmp, 40f, 50f, myPaint)
        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        titlePaint.textSize = 40F
        canvas.drawText("Fayoum University Hospitals", 450f, 105f, titlePaint)
        myPaint.textAlign = Paint.Align.CENTER
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Report Summary", 400f, 180f, myPaint)


        canvas.drawLine(40f, 230f, 750f, 230f, myPaint)


        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Patient Details", 80f, 285f, myPaint)

        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        canvas.drawText(
            "Name:" + " " + patient.firstName + " " + patient.lastName,
            80f,
            350f,
            myPaint
        )

        canvas.drawText("Gender:" + " " + patient?.gender, 80f, 390f, myPaint)

        canvas.drawText("Age:" + " " + "22", 80f, 430f, myPaint)

        canvas.drawText("Phone No.:" + " " + patient.phoneNumber, 385f, 350f, myPaint)

        canvas.drawText(
            "Admission Date:" + " " + report!!.enterDate?.substring(
                0,
                report.enterDate!!.indexOf("T")
            ), 385f, 390f, myPaint
        )

        canvas.drawText(
            "Discharge Date:" + " " + report!!.dateOfDischarge?.substring(
                0,
                report.dateOfDischarge!!.indexOf("T")
            ), 385f, 430f, myPaint
        )

        canvas.drawLine(40f, 460f, 750f, 460f, myPaint)


        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Cause Of Admission", 80f, 510f, myPaint)

        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        canvas.drawText(report.causeOfAdmission!!, 80f, 560f, myPaint)
        canvas.drawLine(40f, 620f, 750f, 620f, myPaint)


        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Recommendations", 80f, 660f, myPaint)

        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        canvas.drawText(report.recommendation!!, 80f, 700f, myPaint)
        canvas.drawLine(40f, 750f, 750f, 750f, myPaint)



        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Lab Tests", 80f, 790f, myPaint)

        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        var i = 0
        var y = 830f
        report.testNames?.forEach {
            i++
            if (i == 1) {
                canvas.drawText(it.toString(), 80f, y, myPaint)
            }

            if (i > 1) {
                y += 30f
                canvas.drawText(it.toString(), 80f, y, myPaint)
            }
        }
        canvas.drawLine(40f, y+30, 750f, y+30, myPaint)






        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Scans", 430f, 790f, myPaint)

        myPaint.textSize = 30F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        var j = 0
        var k = 830f

        report.scanNames?.forEach {
            j++
            if (j == 1) {
                canvas.drawText(it.toString(), 430f, k, myPaint)
            }

            if (j > 1) {
                k += 30f
                canvas.drawText(it.toString(), 430f, k, myPaint)
            }
        }


        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 35F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.MONOSPACE.style)
        canvas.drawText("Medicines", 80f, k+70, myPaint)



        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        myPaint.style=Paint.Style.STROKE
        myPaint.strokeWidth= 2F
        canvas.drawRect(20F,k+80,780F,k+120,myPaint)

        myPaint.textAlign=Paint.Align.LEFT
        myPaint.style=Paint.Style.FILL

        myPaint.textSize = 25F
        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        canvas.drawText("Name",40F,k+110,myPaint)
        canvas.drawText("Type",210F,k+110,myPaint)
        canvas.drawText("Strength",310F,k+110,myPaint)
        canvas.drawText("Duration",420F,k+110,myPaint)
        canvas.drawText("Dose",530F,k+110,myPaint)
        canvas.drawText("Instructions",610F,k+110,myPaint)

        canvas.drawLine(200F,k+80,200F,k+120,myPaint)
        canvas.drawLine(300F,k+80,300F,k+120,myPaint)
        canvas.drawLine(410F,k+80,410f,k+120,myPaint)
        canvas.drawLine(520F,k+80,520F,k+120,myPaint)
        canvas.drawLine(600F,k+80,600F,k+120,myPaint)


        var m=0
        var z =k+110+35
        myPaint.textSize = 20F
        report.listOfMedicineNames?.forEach {
            m++
            if (m == 1) {
                canvas.drawText(it?.medicineName!!, 40f, z, myPaint)
                canvas.drawText(it?.medicineType!!, 210f, z, myPaint)
                canvas.drawText(it?.medicineConcentration!!, 310f, z, myPaint)
                canvas.drawText(it?.durarion!!, 420f, z, myPaint)
                canvas.drawText(it?.dose!!, 530f, z, myPaint)
                canvas.drawText(it?.instructions!!, 610f, z, myPaint)

                canvas.drawLine(20f, z + 10, 780f, z + 10, myPaint)
            }
            if (m > 1) {
                z += 30f
                canvas.drawText(it?.medicineName!!, 40f, z, myPaint)
                canvas.drawText(it?.medicineType!!, 210f, z, myPaint)
                canvas.drawText(it?.medicineConcentration!!, 310f, z, myPaint)
                canvas.drawText(it?.durarion!!, 420f, z, myPaint)
                canvas.drawText(it?.dose!!, 530f, z, myPaint)
                canvas.drawText(it?.instructions!!, 610f, z, myPaint)
                canvas.drawLine(20f, z + 10, 780f, z + 10, myPaint)
            }
        }


//        val text="cmkjsncdhncdjhncdjhfbvhjdfvjhdfvhjdfbvjhfbjhvbdjhfbvjdfhbvjhfdbvjhdfbvjjhjjjhjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdnvdjfvjhdfvjhdfnvjdfnvdjfnvdjvjdfnvdfjnvdhjvjhdvdfjhdjfdfvjhdfvdjfndfjvndfjvdjhbvdfjvjhdvkjdfnvdfjhvbjvndj"
//        val textWidth= myPaint.measureText (text,0,text.lastIndex)


//        if(textWidth>500)
//        {
//            canvas.drawText(text.substring(0, textWidth.roundToInt()/40), 80f, 560f, myPaint)
//
//        }


//
//        canvas.drawText("Scan Name:" +" "+ item.scanName, 370f, 260f, myPaint)
//        // canvas.drawLine(40f,300f,750f,300f,myPaint)
//
//        canvas.drawText("Date:" +" "+ item.scanDate!!.substring(0, item.scanDate!!.indexOf("T")), 360f, 290f, myPaint)
//        canvas.drawLine(40f,330f,750f,330f,myPaint)
//
//        myPaint.textAlign = Paint.Align.LEFT
//        myPaint.textSize = 50F
//        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
//        canvas.drawText("Report", 80f, 400f, myPaint)
//
//
//        myPaint.textAlign = Paint.Align.LEFT
//        myPaint.textSize = 25F
//        myPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)


            scanPDF.finishPage(scanPage)


            file = File(requireContext().getExternalFilesDir("/"), "report.pdf")

            pdfView.fromFile(file).load()

            try {
                scanPDF.writeTo(FileOutputStream(file))
            } catch (e: IOException) {
                e.printStackTrace()
            }

            scanPDF.close()


        }
    }
