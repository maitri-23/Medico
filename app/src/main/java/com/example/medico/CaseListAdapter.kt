package com.example.medico

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.medico.data.Case
import com.example.medico.databinding.CaseListBinding

class CaseListAdapter (private val onCaseClicked: (Case) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Case, CaseListAdapter.CaseViewHolder>(DiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        return CaseViewHolder(
            CaseListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onCaseClicked(current)
        }
        holder.bind(current)
    }

    class CaseViewHolder(private var binding: CaseListBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(case: Case){
            binding.name.text = case.patientName
            binding.contact.text = case.contactNo
            binding.symptoms.text = case.symptoms
            binding.spec.text = case.specialist
        }
    }
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Case>() {
            override fun areItemsTheSame(oldCase: Case,newCase: Case):Boolean{
                return oldCase==newCase
            }

            override fun areContentsTheSame(oldCase: Case, newCase: Case): Boolean {
                return oldCase.patientName == newCase.patientName
            }
        }
    }
}