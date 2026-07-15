package com.example.numberstesttask.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numberstesttask.R
import androidx.fragment.app.Fragment
import com.example.numberstesttask.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding : FragmentDetailsBinding ?= null
    private val binding : FragmentDetailsBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}