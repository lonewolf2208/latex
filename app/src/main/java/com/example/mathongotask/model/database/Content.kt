package com.example.mathongotask.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_table")
data class Content(
    @PrimaryKey var id:String,
)
