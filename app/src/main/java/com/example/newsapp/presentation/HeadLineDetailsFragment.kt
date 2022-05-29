package com.example.newsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentHeadLineDetailsBinding
import com.example.newsapp.presentation.ext.picassoLoading
import com.squareup.picasso.Picasso

class HeadLineDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHeadLineDetailsBinding
    private val args: HeadLineDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadLineDetailsBinding.inflate(layoutInflater)
        args.headLine.let {
            binding.hdDescriptionTv.text = it.description
            binding.hdTitleHeadlineTv.text = it.title
            Picasso.get()
                .picassoLoading(it.urlImage, binding.hdHeadlineIv, binding.hdHeadLinePb)
        }
        return binding.root
    }
}