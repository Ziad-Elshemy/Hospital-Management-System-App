//package com.route.myapplication.hms.DB.dao
//
//import androidx.room.*
//import com.route.myapplication.hms.DB.model.AdminDoctorsTableDB
//import java.util.*
//
//@Dao
//interface AdminDoctorsTableDao {
//    @Insert
//    fun addDoctor(AdminDoctorsTable:AdminDoctorsTableDB)
//    @Update
//    fun updateDoctor(AdminDoctorsTable: AdminDoctorsTableDB)
//    @Delete
//    fun deleteDoctor(AdminDoctorsTable: AdminDoctorsTableDB)
//
//    @Query("select * from AdminDoctorsTableDB")
//    fun getAllDoctors():List<AdminDoctorsTableDB>
//
//}