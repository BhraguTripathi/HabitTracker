package com.example.habittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


//This is one row in the habits table. Every habit the user creates becomes one row here.


@Entity(tableName = "habits")
data class HabitEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    //Basic Info
    val name: String,
    val emoji: String,
    val colorHex: String,

    //Goal
    val goalValue: Int,
    val goalUnit: String,

    //How is progress tracked?
    val trackingType: String = "MANUAL",

    //Frequency
    val frequencyType: String = "DAILY",
    val frequencyDays: String = "",

    //Reminder
    val reminderEnabled: Boolean = false,
    val reminderTime: String = "9:00",

    //Habit Type
    val habitType: String = "BUILD",

    //Status
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)