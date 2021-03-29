package com.barreto.unsplashapi.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.barreto.unsplashapi.R
import com.barreto.unsplashapi.databinding.FragmentListGalleryBinding
import com.barreto.unsplashapi.model.UnSplashPhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(), AdapterGallery.OnItemClickListener {


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

        setHasOptionsMenu(true)

        val adapter = AdapterGallery(this)

        binding.apply {
            rvList.setHasFixedSize(true)
            rvList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = AdapterGalleryLoadState { adapter.retry() },
                footer = AdapterGalleryLoadState { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        adapter.addLoadStateListener { loadState ->
            binding.apply {
                Log.d("COSTA", "loadState = " + loadState.source.refresh)
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvList.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvList.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rvList.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }
        
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun onItemClick(photo: UnSplashPhoto) {

        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo = photo)
        findNavController().navigate(action)

    }


}