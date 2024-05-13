package com.route.myapplication.hms.ui.HomeFragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.GetAllDoctorsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientTestsByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem

class DoctorsAdapter(var items :List<GetAllDoctorsResponseItem>?) : RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var doctorImage : ImageView = itemView.findViewById(R.id.doctorImg)
        var drAcrynom : TextView = itemView.findViewById(R.id.Dr_acronym)
        var doctorname : TextView = itemView.findViewById(R.id.dr_name)
        var department : TextView = itemView.findViewById(R.id.dept)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        holder.doctorname.setText(item.firstName+" "+item.lastName)
        holder.department.setText(item.departmentName)
        holder.drAcrynom.setText("Dr")
        holder.doctorImage.setImageResource(R.drawable.ic_doctor)

        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }

        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeData(newItems:List<GetAllDoctorsResponseItem>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    var onItemClickListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,item: GetAllDoctorsResponseItem)
    }
}
