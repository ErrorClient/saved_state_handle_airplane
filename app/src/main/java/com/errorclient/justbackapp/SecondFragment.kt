package com.errorclient.justbackapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.errorclient.justbackapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        /**
         * Записываем значение из editText в savedStateHandle с key = KEY_STRING
         */
        binding.editArgForFirstFragment.doAfterTextChanged{ text ->
            findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(KEY_STRING, text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}