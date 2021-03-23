package com.flowz.byteworksjobtask.Model

data class Admin (
    val firstName: String? = null,
    val lastName: String? = null,
    val  male: Boolean ? = null,
    val dateOfBirth: String? = null,
    val passPortPhoto: Int? = null,
    val address: String? = null,
    val country: String? = null,
    val state: String? = null
)

data class Employee (
    val firstName: String? = null,
    val lastName: String? = null,
    val  male: Boolean ? = null,
    val desgination: String? = null,
    val dateOfBirth: String? = null,
    val passPortPhoto: Int? = null,
    val address: String? = null,
    val country: String? = null,
    val state: String? = null
)