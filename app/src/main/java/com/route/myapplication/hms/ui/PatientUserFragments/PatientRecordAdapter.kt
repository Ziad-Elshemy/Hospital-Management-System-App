package com.route.myapplication.hms.ui.PatientUserFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.NurseUserFragments.NurseInpatientAdapter
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientRecordByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientsResponseItem

class PatientRecordAdapter (var items :List<GetIndoorPatientRecordByPatientIdResponseItem>?) : RecyclerView.Adapter<PatientRecordAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var enterDate: TextView = itemView.findViewById(R.id.enterDate)
        var exitDate: TextView = itemView.findViewById(R.id.exitDate)

        var roomType: TextView = itemView.findViewById(R.id.roomType)
        var roomNo: TextView = itemView.findViewById(R.id.roomNo)
        var floorNo: TextView = itemView.findViewById(R.id.floorNo)
        var bedNo: TextView = itemView.findViewById(R.id.bedNo)
        var Lab: ImageView = itemView.findViewById(R.id.test)
        var Scan: ImageView = itemView.findViewById(R.id.scan)
        var Report: ImageView = itemView.findViewById(R.id.report)
        var prescription: ImageView = itemView.findViewById(R.id.medication)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient_record_layout, parent, false)
        return PatientRecordAdapter.ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items?.get(position)

        val enterDate = item?.enterDate!!.substring(0, item?.enterDate.toString().indexOf("T"))
        val endDate = item?.discahrgeDate?.substring(0, item?.discahrgeDate.toString().indexOf("T"))
        holder.enterDate.setText(enterDate)
        holder.exitDate.setText(endDate)
        holder.roomType.setText(item?.roomType.toString())
        holder.roomNo.setText(item?.roomNumber.toString())
        holder.bedNo.setText(item?.bedNumber.toString())
        holder.floorNo.setText(item?.floorNumber.toString())
        holder.Lab.setImageResource((R.drawable.ic_test))
        holder.Report.setImageResource((R.drawable.ic_file))
        holder.Scan.setImageResource((R.drawable.ic_scan))
        holder.prescription.setImageResource((R.drawable.ic_medicien))



        onLabImgClickListener.let {
            holder.Lab.setOnClickListener {
                onLabImgClickListener?.onImageClick(position, item!!)
            }
        }

        onScanImgClickListener.let {
            holder.Scan.setOnClickListener {
                onScanImgClickListener?.onImageClick(position, item!!)
            }
        }

        onReportImgClickListener.let {
            holder.Report.setOnClickListener {
                onReportImgClickListener?.onImageClick(position, item!!)
            }
        }

        onPrescriptionImgClickListener.let {
            holder.prescription.setOnClickListener {
                onPrescriptionImgClickListener?.onImageClick(position, item!!)
            }

        }
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    fun changeData(newItems:List<GetIndoorPatientRecordByPatientIdResponseItem>){
        items = newItems
        notifyDataSetChanged()
    }

    var onLabImgClickListener : OnImageClickListener?=null
    var onScanImgClickListener : OnImageClickListener?=null
    var onReportImgClickListener : OnImageClickListener?=null
    var onPrescriptionImgClickListener : OnImageClickListener?=null

    interface OnImageClickListener{
        fun onImageClick(pos:Int,item:GetIndoorPatientRecordByPatientIdResponseItem)
    }

}


