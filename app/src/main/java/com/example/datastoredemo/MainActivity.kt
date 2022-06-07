package com.example.datastoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.example.datastoredemo.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var userManager: UserManager
    var age = 0
    var fname = ""
    var lname = ""
    var gender = ""
    val binding by lazy {  ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Get reference to our userManager class
        userManager = UserManager(dataStore)
        buttonSave()

        observeData()
    }

    private fun observeData() {

        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, {
            if (it != null) {
                age = it
                binding.tvAge.text = it.toString()
            }
        })

        //Updates firstname
        userManager.userFirstNameFlow.asLiveData().observe(this, {
            if (it != null) {
                fname = it
                binding.tvFname.text = it
            }
        })

        //Updates lastname
        userManager.userLastNameFlow.asLiveData().observe(this, {
            if (it != null) {
                lname = it
                binding.tvLname.text = it
            }
        })

        //Updates gender
        userManager.userGenderFlow.asLiveData().observe(this, {
            if (it != null) {
                gender = if (it) "Male" else "Female"
                binding.tvGender.text = gender
            }
        })
    }

    private fun buttonSave() {
        //Gets the user input and saves it
        binding.btnSave.setOnClickListener {
            fname = binding.etFname.text.toString()
            lname = binding.etLname.text.toString()
            age = binding.etAge.text.toString().toInt()
            val isMale = binding.switchGender.isChecked
            //Stores the values
            GlobalScope.launch {
                userManager.storeUser(age, fname, lname, isMale)
            }
        }
    }
}