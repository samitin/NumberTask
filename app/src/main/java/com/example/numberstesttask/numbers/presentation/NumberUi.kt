package com.example.numberstesttask.numbers.presentation

import android.widget.TextView

data class NumberUi (val id : String, val fact : String): Mapper<Boolean, NumberUi>{
    fun map(head : TextView,subTitle: TextView){
        head.text = id
        subTitle.text = fact
    }

    override fun map(source: NumberUi): Boolean = source.id == id
}