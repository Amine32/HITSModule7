package com.example.imageapp3.imageEditing

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.FragmentRetouchBinding

class RetouchFragment : Fragment() {
    private val binding by viewBinding(FragmentRetouchBinding::bind, R.id.retouchFragmentLayout)

    companion object {
        val TAG = RetouchFragment::class.java.simpleName

        fun newInstance() = RetouchFragment()
    }
}