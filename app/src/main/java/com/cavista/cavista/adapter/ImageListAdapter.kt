package com.cavista.cavista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cavista.cavista.MainViewModel
import com.cavista.cavista.R
import com.cavista.cavista.databinding.RvImagItemBinding

class ImageListAdapter(var viewModel : MainViewModel) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {


    data class ViewHolder(val binding : RvImagItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =

        ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.rv_imag_item, parent,
                false)
        )

    override fun getItemCount(): Int = viewModel.imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.viewModel = viewModel
        holder.binding.model = viewModel.imageList[position]
        holder.binding.position= position

    }
}