package com.bombadu.pixablast.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bombadu.pixablast.databinding.ActivityImageSearchBinding
import com.bombadu.pixablast.other.Constants.GRID_SPAN_COUNT
import com.bombadu.pixablast.other.Constants.SEARCH_TIME_DELAY
import com.bombadu.pixablast.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageSearchBinding
    lateinit var viewModel: ImagePostViewModel
    private var imageSearchAdapter: ImageSearchAdapter = ImageSearchAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ImagePostViewModel::class.java)


        setupRecyclerView()
        subscribeToObservers()


        var job : Job? = null
        binding.searchView.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchForImage(editable.toString())
                    }
                }
            }
        }


        imageSearchAdapter.setOnItemClickListener {
            val intent = Intent()
            intent.putExtra("key", it)
            setResult(RESULT_OK, intent)
            //Log.d("IMAGEURL", it)
            viewModel.setCurImageUrl(it)
            finish()

        }
    }

    private fun subscribeToObservers() {
        viewModel.images.observe(this, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        val urls = result.data?.hits?.map { imageResult -> imageResult.previewURL }
                        imageSearchAdapter.images = urls ?: listOf()
                    }

                    Status.ERROR -> {
                        Log.e("ERROR", "An Unknown error occurred")
                    }

                    Status.LOADING -> {
                        Log.e("ERROR", "An Unknown error occurred")
                    }
                }
            }
        })

    }

    private fun setupRecyclerView() {

        binding.imageGridRecyclerView.apply {
            adapter = imageSearchAdapter
            layoutManager = GridLayoutManager(applicationContext, GRID_SPAN_COUNT)
            setHasFixedSize(true)
        }
    }


}