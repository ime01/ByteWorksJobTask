package com.flowz.byteworksjobtask.Model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "admin_table")
data class Admin(
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val dateOfBirth: String? = null,
    val passPortPhoto: Uri? = null,
    val address: String? = null,
    val country: String? = null,
    val state: String? = null
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var adminId: Int = 0
}

@Parcelize
@Entity(tableName = "employee_table")
data class Employee(
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val desgination: String? = null,
    val dateOfBirth: String? = null,
    val passPortPhoto: Uri? = null,
    val address: String? = null,
    val country: String? = null,
    val state: String? = null
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var employeeId: Int = 0
}

class UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }
}
