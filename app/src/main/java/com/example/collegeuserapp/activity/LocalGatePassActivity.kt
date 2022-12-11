package com.example.collegeuserapp.activity

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.collegeuserapp.databinding.ActivityLocalGatePassBinding
import com.example.collegeuserapp.model.AdminModel
import com.example.collegeuserapp.model.LocalGatePass
import com.example.collegeuserapp.notification.NotificationData
import com.example.collegeuserapp.notification.PushNotification
import com.example.collegeuserapp.notification.api.ApiUtilities
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import kotlin.collections.ArrayList

class LocalGatePassActivity : AppCompatActivity() ,DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener{
    private lateinit var binding:ActivityLocalGatePassBinding
    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0


    private lateinit var items:ArrayList<String>
    private lateinit var depart:ArrayList<String>
    private lateinit var branch:String
    private lateinit var programme:String
private  var date:String?=null
private  var TimeOut:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocalGatePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
supportActionBar?.title="Local Gate Pass"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.selectDate.setOnClickListener {
            pickDate()
        }
binding.selectTimeOut.setOnClickListener {
    pickTime()
}
       binding.proceed.setOnClickListener {
           ValidateData()
       }

        items= arrayListOf("Select Programme","B Tech",
            "IDD","MBA","M Tech",
            "PhD")
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,items)
        binding.Programme.adapter=arrayAdapter

        binding.Programme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                programme= binding.Programme.selectedItem.toString()
            }

        }

        depart= arrayListOf("Select Department","Chemical Engineering",
            "Computer Science & Engineering","Electronics Engineering",
            "Petroleum Engineering & Geoengineering","Mathematical Science","Management Studies",
            "Science and Humanities")

        val arrayAdapter2 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,depart)
        binding.Branch.adapter=arrayAdapter2

        binding.Branch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                branch= binding.Branch.selectedItem.toString()
            }
        }

      }

    private fun ValidateData() {
        if(binding.name.text!!.isEmpty()){
            binding.name.requestFocus()
            binding.name.error="Empty"
        }
        else if(binding.rollNo.text!!.isEmpty()){
            binding.rollNo.requestFocus()
            binding.rollNo.error="Empty"
        }
        else if(binding.mobileNo.text!!.isEmpty()){
            binding.mobileNo.requestFocus()
            binding.mobileNo.error="Empty"
        }


        else if(binding.RoomNo.text!!.isEmpty()){
            binding.RoomNo.requestFocus()
            binding.RoomNo.error="Empty"
        }
        else if(binding.place.text!!.isEmpty()){
            binding.place.requestFocus()
            binding.place.error="Empty"
        }

        else if(branch=="Select Department" || branch==null){
            Toast.makeText(this,"Select Department", Toast.LENGTH_SHORT).show()
        }
        else if(programme=="Select Programme" || programme==null){
            Toast.makeText(this,"Select Programme", Toast.LENGTH_SHORT).show()
        }
        else if(date==null){
            Toast.makeText(this,"Select Date",Toast.LENGTH_SHORT).show()
        }
        else if(TimeOut==null){
            Toast.makeText(this,"Select Time Out",Toast.LENGTH_SHORT).show()
        }
        else{
            storeData()
        }

    }

    private fun storeData() {


        val db = Firebase.firestore.collection("Local Gate Pass")
        val key = db.document().id

     val   data=LocalGatePass(
         id=key,
name = binding.name.text.toString(),
         rollNo = binding.rollNo.text.toString(),
         place = binding.place.text.toString(),
         programme = programme,
         branch = branch,
         mobileNo = binding.mobileNo.text.toString(),
        timestamp= FieldValue.serverTimestamp(),
         roomNo = binding.RoomNo.text.toString(),
         date = date,
         timeOut = TimeOut,
         emailId = binding.emailId.text.toString()
        )


        Firebase.firestore.collection("Local Gate Pass")
            .document(key).set(data).addOnSuccessListener {

                Toast.makeText(this, "Applied for gate pass", Toast.LENGTH_SHORT).show()
                binding.name.text = null
                binding.rollNo.text = null
                binding.place.text = null
                binding.mobileNo.text = null
                binding.emailId.text=null
                binding.RoomNo.text = null
                binding.TimeOutTV.text = "No time selected"
                binding.dateTV.text = "No date selected"
                date = null
                TimeOut = null
                programme = "Select Programme"
                branch = "Select Department"
                binding.Branch.setSelection(0)
                binding.Programme.setSelection(0)


            }

sendNotification(key)



    }

    private fun sendNotification(key: String) {

        Firebase.firestore.collection("Admin").document("token")
            .get().addOnSuccessListener {
             val   data=it.toObject(AdminModel::class.java)


                val notificationData= PushNotification(NotificationData("New Local Gate Pass",key),
                    data!!.token
                )

                ApiUtilities.getInstance().sendNotification(
                    notificationData
                ).enqueue(object : Callback<PushNotification> {
                    override fun onResponse(
                        call: Call<PushNotification>,
                        response: retrofit2.Response<PushNotification>
                    ) {
                        Toast.makeText(this@LocalGatePassActivity,data.token.toString()
                            ,Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<PushNotification>, t: Throwable) {
                        Toast.makeText(this@LocalGatePassActivity,"Something Went Wrong"
                        ,Toast.LENGTH_SHORT).show()
                    }

                })
            }


    }


    private fun pickTime() {
        getDateTimeCalender()
        TimePickerDialog(this,this,hour,minute,false).show()
    }

    private fun pickDate() {

            getDateTimeCalender()
            DatePickerDialog(this,this,year,month,day).show()

    }

    private fun getDateTimeCalender(){
        val cal:Calendar = Calendar.getInstance()
         day = cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

        var   SavedDay=p3
        var    SavedMonth=p2
        var    SavedYear=p1
        binding.dateTV.text="$SavedDay/$SavedMonth/$SavedYear"
        date= binding.dateTV.text.toString()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        var   SavedHour=p1
        var  SavedMinute=p2
        binding.TimeOutTV.text="$SavedHour:$SavedMinute"
        TimeOut=binding.TimeOutTV.text.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(com.example.collegeuserapp.R.menu.local_gate_pass_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            com.example.collegeuserapp.R.id.LocalGatePassList-> {
                var intent = Intent(this, LocalGatePassListActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }


}