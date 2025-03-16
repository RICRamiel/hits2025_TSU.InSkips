package com.team8.tsuinskips.domain

import com.google.gson.annotations.SerializedName

data class Student(
    val id: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val groupName: String,
    val subgroupNames: List<String>
)
