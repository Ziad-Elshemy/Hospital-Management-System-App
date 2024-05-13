package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetDoctorByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorUserDashboardFragment : Fragment() {

    var doctor : GetDoctorByIdResponse = GetDoctorByIdResponse()
    lateinit var firstName : TextView
    lateinit var lastName : TextView
    lateinit var specialization : TextView

    lateinit var editIcon :ImageView
    lateinit var editText : TextView
    lateinit var appointmentCard : CardView
    lateinit var inpatientsCard : CardView

    val doctorId = Constants.userID.toInt()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_user_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editIcon = requireView().findViewById(R.id.edit_icon)
        editText = requireView().findViewById(R.id.editText)
        firstName = requireView().findViewById(R.id.fname)
        lastName = requireView().findViewById(R.id.lname)
        specialization = requireView().findViewById(R.id.specialization)


        appointmentCard = requireView().findViewById(R.id.appointmentsCard)
        inpatientsCard = requireView().findViewById(R.id.inpatientsCard)
        getDoctorById(doctorId)

        editIcon.setOnClickListener {
            pushFragment(DoctorProfileFragment())

        }

        editText.setOnClickListener {
            pushFragment(DoctorProfileFragment())

        }

        appointmentCard.setOnClickListener {
            pushFragment(DoctorUserAppointmentsFragment())

        }

        inpatientsCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("doctorId", doctorId)

            val inpatientsFragment: Fragment = DoctorUserInpatientFragment()
            inpatientsFragment.arguments = bundle

            pushFragment(inpatientsFragment)

        }
    }


    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }


    private fun getDoctorById(doctorID: Int) {

        ApiManager.getApis().getDoctorById(doctorID).enqueue(object : Callback<GetDoctorByIdResponse> {
                override fun onFailure(
                    call: Call<GetDoctorByIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetDoctorByIdResponse>,
                    response: Response<GetDoctorByIdResponse>
                ) {
                    doctor = response.body()!!
                    firstName.setText(doctor.firstName)
                    lastName.setText(doctor.lastName)
                    specialization.setText(doctor.docSpecialization)
                }

            })
    }



}