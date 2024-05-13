package com.route.myapplication.hms.ui.NurseUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.DoctorUserInpatientFragment
import com.route.myapplication.hms.ui.PatientUserFragments.PatientProfileFragment
import com.route.myapplication.hms.ui.PatientUserFragments.PatientUserShowAppointmentsFragment
import com.route.myapplication.hms.ui.PatientUserFragments.PatientUserShowPrescriptionsFragment
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetNurseByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NurseUserProfileFragment : Fragment() {

    var nurse : GetNurseByIdResponse = GetNurseByIdResponse()

    lateinit var firstName: TextView
    lateinit var lastName: TextView
    lateinit var dept: TextView

    val nurseId = Constants.userID.toInt()


    lateinit var editIcon : ImageView
    lateinit var editText : TextView
    lateinit var inpatientsCard : CardView
    lateinit var reserveCard : CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editIcon = requireView().findViewById(R.id.edit_icon)
        editText = requireView().findViewById(R.id.editText)

        firstName = requireView().findViewById(R.id.fname)
        lastName = requireView().findViewById(R.id.lname)

        dept = requireView().findViewById(R.id.dept)





        inpatientsCard = requireView().findViewById(R.id.InpatientsCard)
        reserveCard = requireView().findViewById(R.id.ReserveCard)


        getnurseById(nurseId)

        editIcon.setOnClickListener {
            pushFragment(EditProfileFragment())

        }

        inpatientsCard.setOnClickListener{

            val bundle = Bundle()
            bundle.putInt("nurseId", nurseId)

            val nurseinpatientsFragment: Fragment = NurseInpatientFragment()
            nurseinpatientsFragment.arguments = bundle

            pushFragment(nurseinpatientsFragment)

            //pushFragment(NurseInpatientFragment())
        }

        editText.setOnClickListener {
            pushFragment(EditProfileFragment())

        }

        reserveCard.setOnClickListener {
            pushFragment(NurseUserReservePatientFragment())

        }
    }

    private fun getnurseById(nurseId: Int) {

        ApiManager.getApis().getNurseByIdResponse(nurseId)
            .enqueue(object : Callback<GetNurseByIdResponse> {
                override fun onFailure(
                    call: Call<GetNurseByIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetNurseByIdResponse>,
                    response: Response<GetNurseByIdResponse>
                ) {
                    nurse = response.body()!!
                    firstName.setText(nurse.firstName)
                    lastName.setText(nurse.lastName)
                    dept.setText(nurse.departmentName)
                }
            })
    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }


}