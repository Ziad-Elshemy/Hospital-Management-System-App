package com.route.myapplication.hms.ui.DoctorUserFragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.*

class ReportAdapter(var items :List<GetIndoorPatientRecordByPatientIdResponseItem>?) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pdfImage : ImageView = itemView.findViewById(R.id.pdf_img)
        var lensImage : ImageView = itemView.findViewById(R.id.lens)
        var title : TextView = itemView.findViewById(R.id.title)
        var startdate : TextView = itemView.findViewById(R.id.Enter_date)
        var enddate : TextView = itemView.findViewById(R.id.End_date)
        var footer : TextView = itemView.findViewById(R.id.footer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        holder.title.setText("Patient Report")
        holder.footer.setText("view report")
        holder.pdfImage.setImageResource(R.drawable.ic_pdf)
        holder.lensImage.setImageResource(R.drawable.ic_lens)
        val enterDate =item.enterDate!!.substring(0, item.enterDate.indexOf("T"))+"  "+item.enterDate.substring(item.enterDate.indexOf("T")+1,16)
        holder.startdate.setText(enterDate)

        val endDate =item.discahrgeDate?.substring(0, item.discahrgeDate.indexOf("T"))+"  "+item.discahrgeDate?.substring(item.discahrgeDate.indexOf("T")+1,16)
        holder.enddate.setText(endDate)


        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }

        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeData(newItems:List<GetIndoorPatientRecordByPatientIdResponseItem>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    var onItemClickListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,item: GetIndoorPatientRecordByPatientIdResponseItem)
    }
}
