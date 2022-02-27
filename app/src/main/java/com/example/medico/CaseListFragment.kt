package com.example.medico

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medico.databinding.FragmentCaseListBinding

class CaseListFragment : Fragment() {
    private val viewModel: CaseViewModel by activityViewModels {
        CaseViewModelFactory(
            (activity?.application as CaseApplication).database.getCaseDao()
        )
    }

    private var _binding: FragmentCaseListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CaseListAdapter {
            val action =
                CaseListFragmentDirections.actionAnimalListFragmentToAnimalDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.ListCase.layoutManager = LinearLayoutManager(this.context)
        binding.ListCase.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allCases.observe(this.viewLifecycleOwner) { animals ->
            animals.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = CaseListFragmentDirections.actionAnimalListFragmentToAddAnimalFragment(
                getString(R.string.add_fragment_title))
            this.findNavController().navigate(action)
        }
    }
}