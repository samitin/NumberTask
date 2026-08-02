package com.example.numberstesttask.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.numberstesttask.R
import com.example.numberstesttask.main.presentation.ShowFragment
import com.example.numberstesttask.databinding.FragmentNumbersBinding
import com.example.numberstesttask.details.presentation.DetailsFragment
import com.example.numberstesttask.main.sl.ProvideViewModel

class NumbersFragment : Fragment() {

    private var _binding : FragmentNumbersBinding ?= null
    private val binding : FragmentNumbersBinding get() = _binding!!
    private var showFragment : ShowFragment = ShowFragment.Empty
    private lateinit var viewModel: NumbersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = requireActivity() as ShowFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).viewModel(NumbersViewModel::class.java,this)
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
        val mapper = DetailsUi()
        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) {
                showFragment.show(DetailsFragment.newInstance(item.map(mapper)),true)
            }
        })
        binding.historyRecyclerView.adapter = adapter

        binding.editText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                viewModel.clearError()
            }
        })
        binding.getFactButton.setOnClickListener {
            viewModel.fetchNumberFact(binding.editText.text.toString())
        }

        binding.randomFactButton.setOnClickListener {
            viewModel.fetchRandomNumberFact()
        }

        viewModel.observeState(this) {
            it.apply(binding.textInputLayout, binding.editText)
        }

        viewModel.observeList(this) {
            adapter.map(it)
        }

        viewModel.observeProgress(this) {
            binding.progressBar.visibility = it
        }

        viewModel.init(savedInstanceState == null)
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
abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}