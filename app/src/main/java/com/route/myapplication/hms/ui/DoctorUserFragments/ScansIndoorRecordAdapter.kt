package com.route.myapplication.hms.ui.DoctorUserFragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientScanByScanIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem

class ScansIndoorRecordAdapter(var items :List<GetPatientScanByScanIdResponse>?) : RecyclerView.Adapter<ScansIndoorRecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pdfImage : ImageView = itemView.findViewById(R.id.pdf_img)
//        var lensImage : ImageView = itemView.findViewById(R.id.lens)
        var title : TextView = itemView.findViewById(R.id.title)
        var date : TextView = itemView.findViewById(R.id.date)
        var footer : TextView = itemView.findViewById(R.id.footer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient_lab_tests,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        holder.title.setText(item.scanName)
        holder.footer.setText("view scan")
        holder.pdfImage.setImageResource(R.drawable.ic_pdf)
//        holder.lensImage.setImageResource(R.drawable.ic_lens)

        val date = item.scanDate!!.substring(0, item.scanDate!!.indexOf("T"))+"  "+item.scanDate.substring(item.scanDate.indexOf("T")+1,16)
            //item.prescription?.prescriptionDate?.substring(0,item.prescription?.prescriptionDate.indexOf("T"))+"  "+item.prescription?.prescriptionDate?.substring(item.prescription?.prescriptionDate.indexOf("T")+1,16)

        holder.date.setText(date)

        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }

        }

    }
    fun changeData(newItems:List<GetPatientScanByScanIdResponse>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    var onItemClickListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,item: GetPatientScanByScanIdResponse)
    }
}
