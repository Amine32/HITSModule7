package com.example.imageapp3.imageEditing

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.FragmentSharpeningBinding

class SharpeningFragment : Fragment() {
    private val binding by viewBinding(FragmentSharpeningBinding::bind, R.id.sharpeningFragmentLayout)

    companion object {
        val TAG: String = SharpeningFragment::class.java.simpleName

        fun newInstance() = SharpeningFragment()
    }
}