package com.example.medico

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.medico.data.Case
import com.example.medico.databinding.FragmentCaseDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CaseDetailFragment : Fragment() {
    private val navigationArgs: CaseDetailFragmentArgs by navArgs()
    lateinit var case: Case

    private val viewModel: CaseViewModel by activityViewModels {
        CaseViewModelFactory(
            (activity?.application as CaseApplication).database.getCaseDao()
        )
    }

    private var _binding: FragmentCaseDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCaseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(case: Case) {
        binding.apply {
            name.text = case.patientName
            contact.text = case.contactNo
            symptoms.text = case.symptoms
            spec.text = case.specialist
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editCase() }
        }
    }
    private fun editCase() {
        val action = CaseDetailFragmentDirections.actionCaseDetailFragmentToAddCaseFragment(
            getString(R.string.edit_fragment_title),
            case.id
        )
        this.findNavController().navigate(action)
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteCase()
            }
            .show()
    }

    private fun deleteCase() {
        viewModel.deleteCase(case)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.animalId
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            case = selectedItem
            bind(case)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}