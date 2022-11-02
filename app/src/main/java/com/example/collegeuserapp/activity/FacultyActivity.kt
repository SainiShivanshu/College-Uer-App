package com.example.collegeuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.collegeuserapp.adapter.FacultyAdapter
import com.example.collegeuserapp.model.AddFacultyModel
import com.example.collegeuserapp.databinding.ActivityFacultyBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FacultyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFacultyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFacultyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Faculty List"
        getCheInfo()
        getCsInfo()
        getElecInfo()
        getMathInfo()
        getMangInfo()
        getPetInfo()
        getScienInfo()
    }
    private fun getScienInfo() {
        val  Scien = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Science and Humanities" )
            .get()
            .addOnSuccessListener {

                Scien.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Scien.add(data!!)
                }
                if(!Scien.isEmpty()){
                    binding.ScienceNoData.visibility= View.GONE
                    binding.ScienceDept.visibility= View.VISIBLE
                    binding.ScienceDept.adapter= FacultyAdapter(this,Scien)
                }

            }



    }

    private fun getPetInfo() {
        val  pet = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Petroleum Engineering & Geoengineering" )
            .get()
            .addOnSuccessListener {

                pet.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    pet.add(data!!)
                }
                if(!pet.isEmpty()){
                    binding.PetNoData.visibility= View.GONE
                    binding.PetDept.visibility= View.VISIBLE
                    binding.PetDept.adapter=FacultyAdapter(this,pet)
                }

            }

    }

    private fun getMangInfo() {
        val  Mang = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Management Studies" )
            .get()
            .addOnSuccessListener {

                Mang.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Mang.add(data!!)
                }
                if(!Mang.isEmpty()){
                    binding.MangNoData.visibility= View.GONE
                    binding.MangDept.adapter=FacultyAdapter(this,Mang)
                    binding.MangDept.visibility= View.VISIBLE
                }

            }
    }

    private fun getMathInfo() {
        val  Math = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Mathematical Science" )
            .get()
            .addOnSuccessListener {

                Math.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Math.add(data!!)
                }
                if(!Math.isEmpty()){
                    binding.MathNoData.visibility= View.GONE
                    binding.MathDept.adapter=FacultyAdapter(this,Math)
                    binding.MathDept.visibility= View.VISIBLE
                }

            }
    }

    private fun getElecInfo() {
        val Elec = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Electronics Engineering" )
            .get()
            .addOnSuccessListener {

                Elec.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Elec.add(data!!)
                }
                if(!Elec.isEmpty()){
                    binding.ElecNoData.visibility= View.GONE
                    binding.ElecDept.adapter=FacultyAdapter(this,Elec)
                    binding.ElecDept.visibility= View.VISIBLE
                }

            }
    }

    private fun getCheInfo() {
        val Che = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Chemical Engineering" )
            .get()
            .addOnSuccessListener {
                Che.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Che.add(data!!)
                }
                if(!Che.isEmpty()){
                    binding.CheNoData.visibility= View.GONE
                    binding.CheDept.visibility= View.VISIBLE
                    binding.CheDept.adapter=FacultyAdapter(this,Che)

                }

            }
    }

    private fun getCsInfo() {

        val   Cs = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Computer Science & Engineering" )
            .get()
            .addOnSuccessListener {

                Cs.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Cs.add(data!!)
                }
                if(!Cs.isEmpty()){
                    binding.CsNoData.visibility= View.GONE
                    binding.CsDept.visibility= View.VISIBLE
                    binding.CsDept.adapter=FacultyAdapter(this,Cs)
                }

            }

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}