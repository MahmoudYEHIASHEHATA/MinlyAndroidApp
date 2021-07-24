package com.task.minlyapp.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.task.minlyapp.databinding.ActivityMainBinding

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}