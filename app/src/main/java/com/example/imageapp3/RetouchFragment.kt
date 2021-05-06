package com.example.imageapp3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.databinding.FragmentRetouchBinding

class RetouchFragment : Fragment() {
    private val binding by viewBinding(FragmentRetouchBinding::bind, R.id.retouchFragmentLayout)

    companion object {
        val TAG = RetouchFragment::class.java.simpleName

        fun newInstance() = RetouchFragment()
    }
}