package com.example.olodjinha.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.olodjinha.api.RetrofitInstance
import com.example.olodjinha.databinding.FragmentMainBinding
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.ui.BannerAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    lateinit var bannerAdapter: BannerAdapter

    private val viewModel: MainViewModel by lazy {
        val repository = MainRepository(RetrofitInstance.apiService)
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBannerRecyclerView()

        setupObserver()

        viewModel.getBanners()

//        binding.tv.setOnClickListener {
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSecondFragment())
//        }
    }

    private fun setupObserver() {
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerAdapter.differ.submitList(it)
        }
    }

    private fun setupBannerRecyclerView() {
        bannerAdapter = BannerAdapter()
        binding.rvBanner.adapter = bannerAdapter
        binding.indicator.attachToRecyclerView(binding.rvBanner)
        PagerSnapHelper().attachToRecyclerView(binding.rvBanner)
        bannerAdapter.setOnItemClickListener {
        }
    }
}
