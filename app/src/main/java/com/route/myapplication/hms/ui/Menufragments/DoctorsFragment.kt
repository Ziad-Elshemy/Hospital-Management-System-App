package com.route.myapplication.hms.ui.Menufragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.AdminDoctorsTableResponse
import com.route.myapplication.hms.ui.ui.DoctorsAdapter
import com.route.myapplication.hms.ui.ui.DoctorsDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorsFragment : Fragment() {
    lateinit var addDoctorBtn : Button
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : DoctorsAdapter
    lateinit var items_row: MutableList<DoctorsDetails>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView =requireView().findViewById(R.id.recyclerView_doctors)
        items_row = AddingItems()
        adapter = DoctorsAdapter(items_row)
        recyclerView.adapter = adapter


       addDoctorBtn = requireView().findViewById(R.id.addDoctor_btn)
        addDoctorBtn.setOnClickListener {
            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, AddDoctorFragment())
                .addToBackStack("")
                .commit()
        }

        getAdminDoctorsTable();

    }

    private fun getAdminDoctorsTable() {
        ApiManager.getApis()
            .getAdminDoctorsTable()
            .enqueue(object :Callback<AdminDoctorsTableResponse>{
                override fun onFailure(call: Call<AdminDoctorsTableResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),"Throwable"+t.localizedMessage, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<AdminDoctorsTableResponse>,
                    response: Response<AdminDoctorsTableResponse>
                ) {
                    Log.e("response",response.body().toString())
                }
            }
            )
    }

    private fun so(body: AdminDoctorsTableResponse?) {

    }

    private fun AddingItems() : MutableList<DoctorsDetails>{
        val items: MutableList<DoctorsDetails> = arrayListOf()
//        items.add(DoctorsDetails(R.drawable.ic_doctor, "ahmed", "01111111111","a.bb@yahoo.com","male",23,"address","010999999","degree","dept",R.drawable.ic_delete,R.drawable.ic_edit))
//        items.add(DoctorsDetails(R.drawable.ic_doctor, "ahmed", "01111111111","a.bb@yahoo.com","male",23,"address","010999999","degree","dept",R.drawable.ic_delete,R.drawable.ic_edit))
//         for (i in 0..50) {
//            items.add(DoctorsDetails(R.drawable.ic_doctor, "Ahmed", "01111111111","a.bb@yahoo.com","male",23,"address","010999999","degree","dept"))
//
//        }
        var sources :List<AdminDoctorsTableResponse?>? =null
        sources?.forEach{ item->
            items.add(DoctorsDetails(item?.imageID,item?.name,item?.nationalID,item?.email,item?.gender,item?.age,item?.address,item?.phone,item?.degree,item?.department))
        }
        return items
    }



}