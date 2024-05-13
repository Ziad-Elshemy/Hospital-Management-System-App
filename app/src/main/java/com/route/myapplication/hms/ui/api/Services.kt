package com.route.myapplication.hms.ui.api

import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.http.*

interface Services {
    @POST("api/Authentication/login")
    fun userLogin(@Body loginRequest:LoginRequest): Call<LoginResponse>

    @GET("api/.....")
    fun getAdminDoctorsTable(
        //pass token here
    ):Call<AdminDoctorsTableResponse>

    @GET("api/VitalSigns/GetVitalSignesByRangeOfDateTime/{PatientId}/{StartDate}/{EndDate}")
    fun getVitals(@Path("PatientId")PatientId:Int,
                  @Path("StartDate")StartDate:String,
                  @Path("EndDate")EndDate:String): Call<ArrayList<getVitalsResponseItem>>



    @POST("api/MedicalTest/addLabRequest")
    fun addTestRequest(@Body testRequest: TestRequest): Call<AddTestResponse>


    @POST(" api/MedicalScan/addScanRequest")
    fun addScanRequest(@Body scanRequest: ScanRequest): Call<AddScanResponse>




    @POST("api/Doctor/AddPrescription")
    fun addPrescriptionRequest(@Body prescriptionRequest: PrescriptionRequest): Call<AddPrescriptionResponse>


    @GET("/api/Doctor/GetDoctorPrescriptionsForPatient/{ParientId}")
    fun getPrescriptions(@Path("ParientId")PatientId:Int,
    ): Call<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>

    @GET("/api/Patient/GetInDoorPatients/{DepartmentId}")
    fun getInPatients(@Path("DepartmentId")DepartmentId:Int,
    ): Call<ArrayList<GetIndoorPatientsResponseItem>>


    @GET("/api/Appointment/GetAppointmentsForTodayByDoctorId/{DoctorId}/{Today}")
    fun getDoctorAppointments(@Path("DoctorId")DoctorId:Int,@Path("Today")Today:String
    ): Call<ArrayList<GetDoctorAppointmentResponseItem>>


    @GET("/api/Patient/{id}")
    fun getPatientByIdResponse(@Path("id") patientId:Int
    ): Call<GetPatientByIdResponse>


    @POST("api/VitalSigns/AddVitalSignes")
    fun addVitalsRequest(@Body vitalsRequest: VitalsRequest): Call<AddVitalsResponse>

    @GET("/api/Patient/getAllPatients")
    fun getAllPatients(): Call<ArrayList<GetAllPatiensResponseItem>>

    @GET("/api/Departments/getAll")
    fun getAllDepts(): Call<ArrayList<GetAllDeptsResponseItem>>

    @GET("/api/Departments/getAllEmps")
    fun getAllEmps(): Call<ArrayList<GetAllEmpsResponseItem>>

    @GET("/api/Admin/GetFreeRooms")
    fun getFreeRooms(): Call<ArrayList<GetFreeRoomsResponseItem>>

    @GET("/api/Admin/GetFreeBedsByRoomId")
    fun getFreeBeds(@Query ("RoomId") RoomId:Int): Call<ArrayList<GetFreeBedsResponseItem>>

    @POST("api/Patient/ReservePatient")
    fun reservePatient(@Body reservePatientRequest: ReservePatientRequest): Call<String>



    @GET("/api/Patient/GetLastPrescriptionByInDoorId/{InDoorRecird_Id}")
    fun getLastPrescription(@Path("InDoorRecird_Id") InDoorRecird_Id:Int
    ): Call<GetLastPrescriptionResponse>


    @GET("/api/MedicalScan/getPatientScansByPatientId/{Id}")
    fun getPatientScansByPatientId(@Path("Id")PatientId:Int,
    ): Call<ArrayList<GetPatientsScansByPatientIdResponseItem>>

    @GET("/api/MedicalTest/getAll")
    fun getAllTest(): Call<ArrayList<GetAllTestsResponseItem>>

    @GET("/api/MedicalScan/getAll")
    fun getAllScan(): Call<ArrayList<GetAllScansResponseItem>>

    @GET("/api/MedicalTest/getPatientTestsByPatientId/{Id}")
    fun getPatientTestsByPatientId(@Path("Id")PatientId:Int,
    ): Call<ArrayList<GetPatientTestsByPatientIdResponseItem>>

    @GET("/api/Patient/GetIndoorPatientRecordsByPatientId/{PatientId}")
    fun getIndoorPatientRecordsByPatientId(@Path("PatientId")PatientId:Int,
    ): Call<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>

    @GET("/api/Patient/GetPatientReport/{PatientId}/{DateOfDischarge}")
    fun getPatientReport(@Path("PatientId")PatientId:Int,
                         @Path("DateOfDischarge")DateOfDischarge:String): Call<GetPatientReportResponse>

    @GET("/api/MedicalTest/getPatientTestById/{Id}")
    fun getPatientTestByTestId(@Path("Id")PatientId:Int): Call<GetPatientTestByTestIdResponse>

    @GET("/api/MedicalScan/getPatientScanById/{Id}")
    fun getPatientScanByScanId(@Path("Id")PatientId:Int): Call<GetPatientScanByScanIdResponse>

    @GET("/api/Doctor/GetPrescription/{Prescription_ID}")
    fun getPrescriptionByPrescriptionId(@Path("Prescription_ID")PrescriptionId:Int): Call<GetPrescriptionByPrescriptionIdResponse>

//    ,@Header("token") token:String=Constants.token

    @GET("/api/Doctor/{id}")
    fun getDoctorById(@Path("id") doctorId:Int
    ): Call<GetDoctorByIdResponse>


    @GET("/api/Patient/GetInDoorPatients/{DepartmentId}")
    fun getIndoorPatientsByDeptId(@Path("DepartmentId") DepartmentId:Int
    ): Call<ArrayList<GetIndoorPatientsByDepartmentIdResponseItem>>



    @POST("api/Patient/AddPatientReport")
    fun addPatientReport(@Body addReportRequest: AddReportRequest): Call<AddPatientReportResponse>

    @GET("/api/Appointment/GetAppointmentsByPatientId/{PatientId}")
    fun getAppointmentsByPatientId(@Path("PatientId") PatientId:Int
    ): Call<ArrayList<GetPatientAppointmentsResponseItem>>


    @PUT("api/Patient/update")
    fun updatePatientRequest(@Body updateRequest: UpdatePatientRequest): Call<UpdateUserResponse>


    @GET("/api/Doctor/getAllDoctors")
    fun getAllDoctors(): Call<ArrayList<GetAllDoctorsResponseItem>>


    @PUT("api/Nurse/update")
    fun updateNurseRequest(@Body updateRequest: UpdateNurseRequest): Call<UpdateNurseResponse>

    @GET("/api/Nurse/{id}")
    fun getNurseByIdResponse(@Path("id") nurseId:Int
    ): Call<GetNurseByIdResponse>

    @PUT("api/Doctor/update")
    fun updateDoctorRequest(@Body updateRequest: UpdateDoctorRequest): Call<UpdateDoctorResponse>


    @GET("/api/TimeSlot/GetFreeTimeSlotsByDoctorId/{DoctorId}")
    fun getFreeSlotsByDocId(@Path("DoctorId") doctorId:Int
    ): Call<GetFreeTimeSlotsByDocIdResponse>


    @POST("api/Appointment")
    fun addAppointment(@Body addAppointmentRequest: AddAppointmentRequest): Call<AddAppointmentResponse>


}