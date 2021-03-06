package com.bombadu.pixablast.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class LocalData(
    var url: String,
    var caption: String,
    var name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)