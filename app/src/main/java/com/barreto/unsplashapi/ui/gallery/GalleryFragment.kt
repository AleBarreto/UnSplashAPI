package com.barreto.unsplashapi.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.barreto.unsplashapi.databinding.FragmentListGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {


    private lateinit var binding: FragmentListGalleryBinding
    private val viewModel by viewModels<GalleryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListGalleryBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = AdapterGallery()

        binding.apply {
            rvList.setHasFixedSize(true)
            rvList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = AdapterGalleryLoadState { adapter.retry() },
                footer = AdapterGalleryLoadState { adapter.retry() }
            )
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }


}