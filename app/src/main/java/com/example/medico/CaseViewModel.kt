package com.example.medico

import androidx.lifecycle.*
import com.example.medico.data.Case
import com.example.medico.data.CaseDao
import kotlinx.coroutines.launch

class CaseViewModel(private val caseDao: CaseDao): ViewModel() {

    val allCases: LiveData<List<Case>> = caseDao.getAllCases().asLiveData()

    fun updateCase (
        caseId: Int,
        name: String,
        contact: String,
        symptoms: String,
        spec: String
    ) {
        val updatedCase = getUpdatedCaseEntry(caseId,name,contact,symptoms,spec)
        updateCase(updatedCase)
    }

    fun addNewCase(name: String, contact: String, symptoms: String,spec: String) {
        val newCase = getNewCaseEntry(name, contact, symptoms, spec)
        addCase(newCase)
    }

    fun deleteCase(case: Case){
        viewModelScope.launch {
            caseDao.delete(case)
        }
    }

    fun addCase(case: Case){
        viewModelScope.launch {
            caseDao.insert(case)
        }
    }
    fun retrieveItem(id: Int): LiveData<Case> {
        return caseDao.getCase(id).asLiveData()
    }
    fun isEntryValid(name: String, contact: String, symptoms: String,spec: String): Boolean {
        if (name.isBlank() || contact.isBlank() || symptoms.isBlank()|| spec.isBlank()) {
            return false
        }
        return true
    }

    private fun updateCase(case: Case){
        viewModelScope.launch {
            caseDao.update(case)
        }
    }
    private fun getNewCaseEntry(name: String, contact: String, symptoms: String,spec: String): Case {
        return Case(
            patientName = name,
            contactNo = contact ,
            symptoms = symptoms,
            specialist = spec
        )
    }

    private fun getUpdatedCaseEntry(
        caseId: Int,
        name: String,
        contact: String,
        symptoms: String,
        spec: String): Case {
        return Case(
            id = caseId,
            patientName = name,
            contactNo = contact ,
            symptoms = symptoms,
            specialist = spec
        )
    }

}
class CaseViewModelFactory(private val caseDao: CaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaseViewModel(caseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}