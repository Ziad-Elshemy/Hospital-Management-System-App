package com.route.myapplication.hms.ui.PatientUserFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientAppointmentsResponseItem

class AppointmentListAdapter(var items :List<PatientUpcomingAppointmentDetails>?) : RecyclerView.Adapter<AppointmentListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateImg : ImageView = itemView.findViewById(R.id.calenderImg)
        var timeImg : ImageView = itemView.findViewById(R.id.timeImg)
        var doctoracronym : TextView = itemView.findViewById(R.id.Dr_acronym)
        var timeAcronym : TextView = itemView.findViewById(R.id.time_acronym)
        var doctorName : TextView = itemView.findViewById(R.id.doctorName)
//        var doctordept : TextView = itemView.findViewById(R.id.doctorDepartment)
        var date : TextView = itemView.findViewById(R.id.date)
        var time : TextView = itemView.findViewById(R.id.time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient_upcoming_appointment,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        holder.doctoracronym.setText("Dr.")
        holder.timeAcronym.setText(item.timeAcronym)
        holder.doctorName.setText(item.doctorName)
//        holder.doctordept.setText(item.doctorDept)
        holder.dateImg.setImageResource(R.drawable.ic_baseline_calendar_month_24)
        holder.timeImg.setImageResource(R.drawable.ic_time)
        val time = item.appointmentDate!!.substring(item.appointmentDate.indexOf("T")+1,16)
        val date = item.appointmentDate.substring(0, item.appointmentDate.indexOf("T"))
        holder.date.setText(date)
        holder.time.setText(item.slotTime!!)

        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }

        }

    }
    fun changeData(newItems:List<PatientUpcomingAppointmentDetails>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    var onItemClickListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,item: PatientUpcomingAppointmentDetails)
    }
}
