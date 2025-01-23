package com.iav.contestdataprovider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iav.contestdataprovider.databinding.ItemGeneratedStringBinding
import com.iav.contestdataprovider.model.GeneratedString
import com.iav.contestdataprovider.viewmodel.StringViewModel

class GeneratedStringAdapter(
    private val context: Context,
    private val strings: List<GeneratedString>,
    private val viewModel: StringViewModel
) : RecyclerView.Adapter<GeneratedStringAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGeneratedStringBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = strings[position]
        holder.binding.generatedString = item

        holder.binding.btnDelete.setOnClickListener {
            viewModel.deleteString(item)
        }
    }

    override fun getItemCount(): Int {
        return strings.size
    }

    class ViewHolder(val binding: ItemGeneratedStringBinding) :
        RecyclerView.ViewHolder(binding.root)
}
