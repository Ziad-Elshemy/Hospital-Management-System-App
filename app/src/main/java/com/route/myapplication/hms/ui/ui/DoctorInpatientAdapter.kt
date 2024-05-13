package com.route.myapplication.hms.ui.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.NurseUserFragments.NurseInpatientAdapter
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientsResponseItem

class DoctorInpatientAdapter (var items :List<GetIndoorPatientsResponseItem>) : RecyclerView.Adapter<DoctorInpatientAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.patient_name)

//        var patientID: TextView = itemView.findViewById(R.id.patient_id)
        var patientAge: TextView = itemView.findViewById(R.id.patient_age)
        var patientPhone: TextView = itemView.findViewById(R.id.patient_phoneNumber)
        var vitalSigns: ImageView = itemView.findViewById(R.id.vitalSignsImg)
        var Lab: ImageView = itemView.findViewById(R.id.test)
        var Scan: ImageView = itemView.findViewById(R.id.scan)
        var Report: ImageView = itemView.findViewById(R.id.report)
        var prescription: ImageView = itemView.findViewById(R.id.medication)
        var discharge: ImageView = itemView.findViewById(R.id.dischargeImg)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inpatient_layout, parent, false)
        return DoctorInpatientAdapter.ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

        holder.name.setText(item.firstName.toString()+" "+item.lastName)
//        holder.patientID.setText(item.))
        holder.patientAge.setText(item.age.toString())
        holder.patientPhone.setText(item.phoneNumber.toString())

        holder.discharge.setImageResource(R.drawable.ic_discharge)
        holder.vitalSigns.setImageResource((R.drawable.ic_vitalsigns))
        holder.Lab.setImageResource((R.drawable.ic_test))
        holder.Report.setImageResource((R.drawable.ic_medical_record))
        holder.Scan.setImageResource((R.drawable.ic_scan))
        holder.prescription.setImageResource((R.drawable.ic_medicien))


        onDischargeImgClickListener.let {
            holder.discharge.setOnClickListener {
                onDischargeImgClickListener?.onImageClick(position, item)
            }
        }
        onVitalSignsImgClickListener.let {
            holder.vitalSigns.setOnClickListener {
                onVitalSignsImgClickListener?.onImageClick(position, item)
            }
        }

        onLabImgClickListener.let {
            holder.Lab.setOnClickListener {
                onLabImgClickListener?.onImageClick(position, item)
            }
        }

        onScanImgClickListener.let {
            holder.Scan.setOnClickListener {
                onScanImgClickListener?.onImageClick(position, item)
            }
        }

        onReportImgClickListener.let {
            holder.Report.setOnClickListener {
                onReportImgClickListener?.onImageClick(position, item)
            }
        }

        onPrescriptionImgClickListener.let {
            holder.prescription.setOnClickListener {
                onPrescriptionImgClickListener?.onImageClick(position, item)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun changeData(newItems:List<GetIndoorPatientsResponseItem>){
        items = newItems
        notifyDataSetChanged()
    }

    var onDischargeImgClickListener : OnImageClickListener?=null
    var onVitalSignsImgClickListener : OnImageClickListener?=null
    var onLabImgClickListener : OnImageClickListener?=null
    var onScanImgClickListener : OnImageClickListener?=null
    var onReportImgClickListener : OnImageClickListener?=null
    var onPrescriptionImgClickListener : OnImageClickListener?=null

    interface OnImageClickListener{
        fun onImageClick(pos:Int,item:GetIndoorPatientsResponseItem)
    }

}


