package com.route.myapplication.hms.ui.DoctorUserFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.ui.AppointmentsDetails

class DoctorAppointmentAdapter (var items :List<AppointmentsDetails>):RecyclerView.Adapter<DoctorAppointmentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var yrs:TextView = itemView.findViewById(R.id.patient_yrs_term)
        var time_am_pm:TextView = itemView.findViewById(R.id.time_am_pm)
        var name:TextView = itemView.findViewById(R.id.patient_name)
        var age:TextView = itemView.findViewById(R.id.patient_age)
        var time:TextView = itemView.findViewById(R.id.time)

        var Scan: ImageView = itemView.findViewById(R.id.test)
        var Lab: ImageView = itemView.findViewById(R.id.scan)

        var Record: ImageView = itemView.findViewById(R.id.record)
        var prescription: ImageView = itemView.findViewById(R.id.prescription)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor_appointment,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.name.setText(item.name)
        holder.age.setText(item.age.toString())
        holder.time.setText(item.time.toString())
        holder.yrs.setText("Yrs")
        holder.time_am_pm.setText(item.time_convension.toString())
        holder.Lab.setImageResource((R.drawable.ic_test))
        holder.Scan.setImageResource((R.drawable.ic_scan))
        holder.Record.setImageResource((R.drawable.ic_medical_record))
        holder.prescription.setImageResource((R.drawable.ic_medicien))




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

        onRecordImgClickListener.let {
            holder.Record.setOnClickListener {
                onRecordImgClickListener?.onImageClick(position, item)
            }
        }

        onPrescriptionImgClickListener.let {
            holder.prescription.setOnClickListener {
                onPrescriptionImgClickListener?.onImageClick(position, item)
            }

        }

    }
    fun changeData(newItems:List<AppointmentsDetails>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    var onLabImgClickListener : OnImageClickListener?=null
    var onScanImgClickListener : OnImageClickListener?=null
    var onRecordImgClickListener : OnImageClickListener?=null
    var onPrescriptionImgClickListener : OnImageClickListener?=null

    interface OnImageClickListener{
        fun onImageClick(pos:Int,item: AppointmentsDetails)
    }


}