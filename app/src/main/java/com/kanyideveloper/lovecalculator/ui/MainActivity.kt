package com.kanyideveloper.lovecalculator.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kanyideveloper.lovecalculator.R
import com.kanyideveloper.lovecalculator.databinding.ActivityMainBinding
import com.kanyideveloper.lovecalculator.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {


    private lateinit var timer: CountDownTimer
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    var fname: String? = null
    var sname: String? = null

    // private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.textView.isSelected = true

        setupViewModel()


        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        viewModel.progressb.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = VISIBLE
            } else {
                binding.progressBar.visibility = INVISIBLE
            }
        })

        viewModel.results.observe(this, Observer {
           binding.loveMessage.text = it.result

            binding.circleProgress.apply {
                progressMax = 100f
                setProgressWithAnimation(it.percentage.toFloat(), 1000)
                progressBarWidth = 15f
            }

            binding.percentage.text = "${it.percentage}%"
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setupUI(){

    }

    fun setupObserver(view : View){

        viewModel.getResults(fname,sname).observe(this, Observer {

            Toast.makeText(this, "Observed", Toast.LENGTH_LONG).show()
        })
    }
}