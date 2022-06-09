package com.example.nasagridapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasagridapp.app.utils.ConnectivityLiveData
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectivityLiveData = ConnectivityLiveData(application)
        snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "No Internet Found!",
            Snackbar.LENGTH_INDEFINITE
        )
        setupObservers()
    }

    private fun setupObservers() {
        connectivityLiveData.observe(this) { isAvailable ->
            isAvailable.let {
                when (isAvailable) {
                    true -> snackbar.dismiss()
                    false -> snackbar.show()
                }
            }
        }
    }

}