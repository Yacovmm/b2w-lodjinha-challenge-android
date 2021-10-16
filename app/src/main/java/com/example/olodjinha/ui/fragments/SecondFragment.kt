package com.example.olodjinha.ui.fragments

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.olodjinha.databinding.SecondFragmentBinding

class SecondFragment : Fragment() {

    lateinit var binding: SecondFragmentBinding

    val args: SecondFragmentArgs by lazy {
        SecondFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SecondFragmentBinding.inflate(inflater)

        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }

        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 750
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val arg = arguments as SecondFragmentArgs

        binding.iv.load(args.url)
    }
}
