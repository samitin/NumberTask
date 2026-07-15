package com.example.numberstesttask.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.numberstesttask.R
import com.example.numberstesttask.main.presentation.ShowFragment
import com.example.numberstesttask.databinding.FragmentNumbersBinding
import com.example.numberstesttask.details.presentation.DetailsFragment

class NumbersFragment : Fragment() {

    private var _binding : FragmentNumbersBinding ?= null
    private val binding : FragmentNumbersBinding get() = _binding!!

    private var showFragment : ShowFragment = ShowFragment.Empty
    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = requireActivity() as ShowFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNumbersBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.GONE

        binding.getFactButton.setOnClickListener {
            val fragment = DetailsFragment()
            showFragment.show(fragment,true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        showFragment = ShowFragment.Empty
    }
}