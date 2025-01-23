package com.iav.contestdataprovider.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iav.contestdataprovider.adapter.GeneratedStringAdapter
import com.iav.contestdataprovider.R
import com.iav.contestdataprovider.databinding.ActivityMainBinding
import com.iav.contestdataprovider.viewmodel.StringViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StringViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        viewModel = ViewModelProvider(this).get(StringViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.generatedStrings.observe(this, Observer { strings ->

            binding.btnClearAll.visibility = if (strings.isNotEmpty()) View.VISIBLE else View.GONE

            binding.etLength.setText("")
            binding.rvGeneratedStrings.adapter = GeneratedStringAdapter(this, strings, viewModel)

        })
    }

    fun onGenerateClick(view: View) {
        val length = binding.etLength.text.toString().toIntOrNull()

        when {
            length == null -> {
                Toast.makeText(this, "Please enter Length number", Toast.LENGTH_SHORT).show()
            }

            length <= 0 -> {
                Toast.makeText(this, "Please enter a positive Length", Toast.LENGTH_SHORT).show()
            }

            else -> {
                length?.let {
                    viewModel.fetchRandomString(it)
                }
            }
        }

    }

    fun onClearAllClick(view: View) {
        // Clear the list in the ViewModel or Adapter
        viewModel.clearAllStrings()
    }
}
