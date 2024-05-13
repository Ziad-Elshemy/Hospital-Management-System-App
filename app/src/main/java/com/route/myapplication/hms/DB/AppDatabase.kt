//package com.route.myapplication.hms.DB
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.route.myapplication.hms.DB.dao.AdminDoctorsTableDao
//import com.route.myapplication.hms.DB.model.AdminDoctorsTableDB
//
//@Database(entities = [AdminDoctorsTableDB::class], version = 1,)
//abstract class AppDatabase: RoomDatabase() {
//
//    abstract fun AdminDoctorsTableDao():AdminDoctorsTableDao
//
//    companion object {
//        private val DATABASE_NAME:String = "AdminDoctorsTable-Database";
//        private var myDatabase:AppDatabase?=null;
//        fun getInstance(context: Context):AppDatabase{
//            // single object from the database(singleton pattern)
//            if (myDatabase==null) {
//                myDatabase = Room.databaseBuilder(
//                    context,
//                    AppDatabase::class.java,
//                    DATABASE_NAME
//                )
//                    .fallbackToDestructiveMigration()
//                    .allowMainThreadQueries() // ANR -> wait or kill ?
//                    .build() //return object from the data base
//            }
//            return myDatabase!!;
//        }
//    }
//}