package com.barreto.unsplashapi.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.barreto.unsplashapi.databinding.UnsplashPhotoLoadStateFooterBinding

class AdapterGalleryLoadState(private val retry: () -> Unit) :
    LoadStateAdapter<AdapterGalleryLoadState.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyViewHolder {
        val binding = UnsplashPhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, loadState: LoadState) {

    }

    inner class MyViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }

    }


}