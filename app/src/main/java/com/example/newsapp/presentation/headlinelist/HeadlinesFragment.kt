package com.example.newsapp.presentation.headlinelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.presentation.adapter.HeadlinesAdapter
import com.example.newsapp.presentation.model.HeadlinePresentation
import com.example.newsapp.presentation.ext.safeNavigate
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadlinesFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentHeadlinesBinding
    private val adapter = HeadlinesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getHeadLines()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadlinesBinding.inflate(layoutInflater)
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        headlinesObserver()
        errorObserver()
        loadingObserver()
        onNextPageObserver()
    }


    private fun headlinesObserver() {
        viewModel.listHeadlinesLiveData.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    private fun errorObserver() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun onNextPageObserver() {
        viewModel.onNextPage.observe(viewLifecycleOwner) {
            goToHeadlinesDetails(it)
        }
    }

    private fun goToHeadlinesDetails(headLine: HeadlinePresentation) {
        findNavController().safeNavigate(
            HeadlinesFragmentDirections.actionHeadlinesFragmentToHeadLineDetailsFragment(
                headLine
            )
        )
    }

    private fun loadingObserver() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isVisible ->
            binding.headlinesPb.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setupRecyclerView(list: List<HeadlinePresentation>) {
        binding.headlinesRv.adapter = adapter
        binding.headlinesRv.layoutManager = LinearLayoutManager(context)
        adapter.setDataAdapter(list)
        adapter.onItemClick = {
            viewModel.goToNextPage(it)
        }
    }
}
