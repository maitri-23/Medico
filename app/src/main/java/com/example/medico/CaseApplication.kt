package com.example.medico

import android.app.Application
import com.example.medico.data.CaseDatabase

class CaseApplication: Application() {
    val database: CaseDatabase by lazy { CaseDatabase.getDatabase(this) }

}