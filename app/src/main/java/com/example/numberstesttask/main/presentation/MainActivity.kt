package com.example.numberstesttask.main.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.numberstesttask.R
import com.example.numberstesttask.main.sl.ProvideViewModel
import com.example.numberstesttask.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity() , ShowFragment, ProvideViewModel{
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

    override fun <T : ViewModel> viewModel(clasz: Class<T>, owner: ViewModelStoreOwner): T =
        (application as ProvideViewModel).viewModel(clasz,this)

}
interface ShowFragment{
    fun show(fragment: Fragment,add : Boolean)
    object Empty : ShowFragment{
        override fun show(fragment: Fragment,add : Boolean) = Unit
    }
}