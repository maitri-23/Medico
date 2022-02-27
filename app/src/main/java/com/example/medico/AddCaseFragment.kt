package com.example.medico

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.medico.data.Case
import com.example.medico.databinding.FragmentAddCaseBinding

class AddCaseFragment : Fragment() {
    private val viewModel: CaseViewModel by activityViewModels {
        CaseViewModelFactory(
            (activity?.application as CaseApplication).database.getCaseDao()
        )
    }
    private val navigationArgs: CaseDetailFragmentArgs by navArgs()
    lateinit var case: Case

    private var _binding: FragmentAddCaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.name.text.toString(),
            binding.contact.text.toString(),
            binding.symptoms.text.toString(),
            binding.spec.text.toString()
        )
    }
    private fun bind(case: Case) {
        binding.apply {
            name.setText(case.patientName, TextView.BufferType.SPANNABLE)
            contact.setText(case.contactNo, TextView.BufferType.SPANNABLE)
            symptoms.setText(case.symptoms, TextView.BufferType.SPANNABLE)
            spec.setText(case.specialist, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateAnimal() }
        }
    }
    private fun addNewCase() {
        if (isEntryValid()) {
            viewModel.addNewCase(
                binding.name.text.toString(),
                binding.contact.text.toString(),
                binding.symptoms.text.toString(),
                binding.spec.text.toString()
            )
            val action = AddCaseFragmentDirections.actionAddAnimalFragmentToAnimalListFragment()
            findNavController().navigate(action)
        }
    }
    private fun updateAnimal() {
        if (isEntryValid()) {
            viewModel.updateCase(
                this.navigationArgs.animalId,
                this.binding.name.text.toString(),
                this.binding.contact.text.toString(),
                this.binding.symptoms.text.toString(),
                this.binding.spec.text.toString()
            )
            val action = AddCaseFragmentDirections.actionAddAnimalFragmentToAnimalListFragment()
            findNavController().navigate(action)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.animalId
        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                case = selectedItem
                bind(case)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewCase()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}