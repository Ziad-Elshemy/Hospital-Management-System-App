package com.route.myapplication.hms.ui.NurseUserFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientsResponseItem

class NurseInpatientAdapter (var items :List<GetIndoorPatientsResponseItem>) : RecyclerView.Adapter<NurseInpatientAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Fname: TextView = itemView.findViewById(R.id.patient_fname)
        var Lname: TextView = itemView.findViewById(R.id.patient_lname)

        var patientAge: TextView = itemView.findViewById(R.id.patient_age)
        var patientPhone: TextView = itemView.findViewById(R.id.patient_phoneNumber)
        var vitalSigns: ImageView = itemView.findViewById(R.id.vitalSignsImg)
        var addVitals: ImageView = itemView.findViewById(R.id.addVitals)
        var medication: ImageView = itemView.findViewById(R.id.medication)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nurse_inpatient_layout, parent, false)
        return NurseInpatientAdapter.ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

        holder.Fname.setText(item.firstName.toString())
        holder.Lname.setText(item.lastName.toString())
        holder.patientAge.setText(item.age.toString())
        holder.patientPhone.setText(item.phoneNumber.toString())

        holder.vitalSigns.setImageResource((R.drawable.ic_vitalsigns))
        holder.addVitals.setImageResource((R.drawable.ic_add_vitals))
        holder.medication.setImageResource((R.drawable.ic_medicien))

        onVitalSignsImgClickListener.let {
            holder.vitalSigns.setOnClickListener {
                onVitalSignsImgClickListener?.onImageClick(position, item)
            }
        }
        onaddVitalsImgClickListener.let {
            holder.addVitals.setOnClickListener {
                onaddVitalsImgClickListener?.onImageClick(position, item)
            }
        }

        onPrescriptionImgClickListener.let {
            holder.medication.setOnClickListener {
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

    var onVitalSignsImgClickListener : OnImageClickListener?=null
    var onaddVitalsImgClickListener : OnImageClickListener?=null
    var onPrescriptionImgClickListener : OnImageClickListener?=null

    interface OnImageClickListener{
        fun onImageClick(pos:Int,item:GetIndoorPatientsResponseItem)
    }

}


