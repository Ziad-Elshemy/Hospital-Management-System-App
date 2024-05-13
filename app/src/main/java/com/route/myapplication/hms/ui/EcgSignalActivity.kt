package com.route.myapplication.hms.ui

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.route.myapplication.hms.R
import com.scichart.charting.model.dataSeries.XyDataSeries
import com.scichart.charting.modifiers.PinchZoomModifier
import com.scichart.charting.modifiers.RolloverModifier
import com.scichart.charting.modifiers.ZoomExtentsModifier
import com.scichart.charting.modifiers.ZoomPanModifier
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.AutoRange
import com.scichart.charting.visuals.axes.AxisAlignment
import com.scichart.charting.visuals.axes.IAxis
import com.scichart.charting.visuals.axes.NumericAxis
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries
import com.scichart.core.framework.UpdateSuspender
import com.scichart.data.model.DoubleRange
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class EcgSignalActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("/test/int")


    private val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private lateinit var schedule: ScheduledFuture<*>

    private lateinit var surface: SciChartSurface

    private lateinit var value: DoubleArray


    private var _valueIndex = 0
    private var _totalIndex = 0
    private  val SAMPLE_RATE = 40.0 //400 previous

    private val lineDataSeries0 = XyDataSeries(Double::class.javaObjectType, Double::class.javaObjectType)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecg_signal)



    value = DoubleArray(1250)

    try {
//        SciChartSurface.setRuntimeLicenseKey(// Set this code once in MainActivity or application startup
            SciChartSurface.setRuntimeLicenseKey("FDvciFgT8Iv0oqkhyN0O4hA6HNiuLIVyh8i0v77RO1Boca4/H7WUONw32k/76pqVOR2zDWWp/cJUVIMI1rMIvVgCbejQtOWA3T+rABO+jmCXAsG1LRxhLaKyELK40dHEX37wrrgr4A4raDbsq7iTNv+/U2HABNNjNtsOYfXXzHU6rLuJsg7rxrVc3X06+fsWkplJP074Wbyff3H7iwmK7rTvOj6enzJyuYmEiURl5Zuk9+BQfEMgMtuNsnCKWPeWr899PsJTTq1NeuDOhlttiwRgsiav0Gz5qk0eOSf0UbhVycihn5gCZ4rWHjzsgemqtUkGWNCODjuDWZm2m28uDFMHlhGdeHp5/qbMW9zrUxgf7CfB0cvZEbaEJdcXncYehxk/Uo7RwzeOp9HczR/xj4r4XTeWo+sDTl4yvOgodkEZMfETUIvbuHZqfdv97tXeYp6As0eHqRFj0knU5dj8O6gaNBZ384UcJrmOwTP3uPPuroerkc87t6+TQknBaCTzyI2xL4J63h/UxQHQMkPm9i8DgBfN7e0YnPGbv0Wx6UCW1BYFRq3Tw6x4jIiy4ocQtZxUuYc=")
    } catch (
    e:

    Exception
    ) {
        Log.e("SciChart", "Error when setting the license", e)
    }


    val chartLayout = findViewById<ViewGroup>(R.id.chart_layout)

    surface = SciChartSurface(this)
    chartLayout.addView(surface)

    val xAxis: IAxis = NumericAxis(this)
    val yAxis: IAxis = NumericAxis(this)
        //yAxis.minimalZoomConstrain = 0.0
       // VisibleRange="50,300"
       // yAxis.visibleRange = IRange <*>!

//        val dataIndex1 = 10.0
//        val dataIndex2 = 100.0
//        val visibleRange = DoubleRange(dataIndex1, dataIndex2)
//        xAxis.visibleRange = visibleRange


      //  val yAxis = NumericAxis(getActivity(this,))
        yAxis.axisAlignment = AxisAlignment.Right
        yAxis.autoRange = AutoRange.Always
        yAxis.growBy = DoubleRange(0.25, 0.25)
        yAxis.setVisibleRange(DoubleRange(1.0, 10.0))



    UpdateSuspender.using(surface) {
        Collections.addAll(surface.xAxes, xAxis)
        Collections.addAll(surface.yAxes, yAxis)
    }

    lineDataSeries0.seriesName = "EcgSeries"

    lineDataSeries0.fifoCapacity = 1250


    val lineSeries0: IRenderableSeries = FastLineRenderableSeries()
    lineSeries0.dataSeries = lineDataSeries0


    UpdateSuspender.using(surface) {
        Collections.addAll(surface.renderableSeries, lineSeries0)

        Collections.addAll(surface.chartModifiers, PinchZoomModifier(), ZoomPanModifier(), ZoomExtentsModifier())
        Collections.addAll(surface.chartModifiers, RolloverModifier())
    }

    schedule = scheduledExecutorService.scheduleWithFixedDelay(updateData, 0, 15, TimeUnit.MILLISECONDS)
}


private val updateData = Runnable {

    UpdateSuspender.using(surface) {

        if (_valueIndex >= value.size) {
            this.readECG()
            _valueIndex = 0
        }
//            value.forEach {
//                it ->
//                val u = 0
//                val readValue = value[u]
//                val time = _totalIndex / SAMPLE_RATE
//                lineDataSeries0.append(time, readValue)
//                _totalIndex++
//            }
        val readValue = value[_valueIndex]
        Log.e("value",readValue.toString())
        val time = _totalIndex / SAMPLE_RATE


        lineDataSeries0.append(time, readValue)

        _totalIndex++
        _valueIndex++

        surface.zoomExtentsX()



    }
}

private fun readECG() {
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for ((i, myDataSnapshot) in dataSnapshot.children.withIndex())
            {
                value[i] = myDataSnapshot.getValue(Double :: class.java)!!
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}
}