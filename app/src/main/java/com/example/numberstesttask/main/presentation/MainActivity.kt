package com.example.numberstesttask.main.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.numberstesttask.R
import com.example.numberstesttask.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity() , ShowFragment{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (savedInstanceState  == null)
            show(NumbersFragment(),false)

    }

    override fun show(fragment: Fragment,add : Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (add)
        transaction.add(R.id.container, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        else
            transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
interface ShowFragment{
    fun show(fragment: Fragment,add : Boolean)
    object Empty : ShowFragment{
        override fun show(fragment: Fragment,add : Boolean) = Unit
    }
}