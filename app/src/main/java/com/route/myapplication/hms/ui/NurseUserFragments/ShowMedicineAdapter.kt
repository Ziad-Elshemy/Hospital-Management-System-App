package com.route.myapplication.hms.ui.NurseUserFragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.PrescriptionListItem

class ShowMedicineAdapter(var items :List<PrescriptionListItem?>?) : RecyclerView.Adapter<ShowMedicineAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var medicinName : TextView = itemView.findViewById(R.id.medicine_name)
        var medicineType : TextView = itemView.findViewById(R.id.patient_medicineType)
        var medicineDose : TextView = itemView.findViewById(R.id.patient_medicineDose)
        var medicineTime : TextView = itemView.findViewById(R.id.patient_medicineTime)

    }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_nurse_medicine_layout,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items!!.get(position)
            holder.medicinName.setText(item?.medicineName)
            holder.medicineType.setText(item?.medicineType)
            holder.medicineDose.setText(item?.dose)
            holder.medicineTime.setText(item?.durarion)


        }
        @SuppressLint("NotifyDataSetChanged")
        fun changeData(newItems:List<PrescriptionListItem?>?){
            items = newItems
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return items?.size ?:0
        }



}