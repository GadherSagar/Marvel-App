package com.example.marvelapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityDashboardBinding
import com.example.marvelapp.ui.fragment.CharacterListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private var binding: ActivityDashboardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        addFragment(CharacterListFragment(), false)
    }

    private fun addFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, fragment, fragment.javaClass.name)
        if (isAddToBackStack) {
            ft.addToBackStack(fragment.javaClass.name)
        }
        ft.commit()
    }

    fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment, fragment.javaClass.name)
        if (isAddToBackStack) {
            ft.addToBackStack(fragment.javaClass.name)
        }
        ft.commit()
    }
}
