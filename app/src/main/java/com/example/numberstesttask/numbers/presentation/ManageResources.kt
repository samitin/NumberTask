package com.example.numberstesttask.numbers.presentation

import android.content.Context
import androidx.annotation.StringRes

interface ManageResources {

    fun string( id: Int): String

    class Base(private val context: Context): ManageResources{
        override fun string(id: Int): String =
            context.getString(id)

    }
}