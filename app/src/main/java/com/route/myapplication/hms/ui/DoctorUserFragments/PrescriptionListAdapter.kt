package com.route.myapplication.hms.ui.DoctorUserFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem

class PrescriptionListAdapter(var items :List<GetAllPrescriptionsOfPatientResponseItem>?) : RecyclerView.Adapter<PrescriptionListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pdfImage : ImageView = itemView.findViewById(R.id.pdf_img)
        var doctoracronym : TextView = itemView.findViewById(R.id.Dr_acronym)
        var doctorName : TextView = itemView.findViewById(R.id.doctorName)
        var doctordept : TextView = itemView.findViewById(R.id.doctorDepartment)
        var date : TextView = itemView.findViewById(R.id.date)
        var footer : TextView = itemView.findViewById(R.id.footer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient_prescription,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        holder.doctoracronym.setText("Dr.")
        holder.doctorName.setText(item.doctorFullName)
        holder.doctordept.setText(item.department)
        holder.footer.setText("view prescription")
        holder.pdfImage.setImageResource(R.drawable.ic_pdf)

        val date = item.prescription?.prescriptionDate?.substring(0,item.prescription?.prescriptionDate.indexOf("T"))+"  "+item.prescription?.prescriptionDate?.substring(item.prescription?.prescriptionDate.indexOf("T")+1,16)

        holder.date.setText(date)

        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }

        }

    }
    fun changeData(newItems:List<GetAllPrescriptionsOfPatientResponseItem>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    var onItemClickListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,item: GetAllPrescriptionsOfPatientResponseItem)
    }
}
