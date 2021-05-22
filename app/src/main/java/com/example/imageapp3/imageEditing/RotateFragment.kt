package com.example.imageapp3.imageEditing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.FragmentRotateBinding

class RotateFragment : Fragment(R.layout.fragment_rotate) {
    private val binding by viewBinding(FragmentRotateBinding::bind, R.id.frameLayout)

    companion object {
        val TAG = RotateFragment::class.java.simpleName

        fun newInstance() = RotateFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rotateButton.setOnClickListener {
            ImageEditingActivity.imageView?.let {
                it.rotation = it.rotation + 90F
            }
        }
        binding.checkbox.setOnClickListener {
            ImageEditingActivity.imageView?.let {
                if (binding.degreeInput.text.toString().isNotEmpty()) {
                    var degree = binding.degreeInput.text.toString().toFloat()
                    it.rotation = degree
                }

            }
        }
    }
}