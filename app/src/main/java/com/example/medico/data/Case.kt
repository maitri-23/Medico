package com.example.medico.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Case(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val patientName: String,

    @ColumnInfo(name = "contact")
    val contactNo: String,

    @ColumnInfo(name = "symptoms")
    val symptoms: String,

    @ColumnInfo(name = "spec")
    val specialist: String
)