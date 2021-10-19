package com.example.olodjinha.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.olodjinha.api.RetrofitInstance
import com.example.olodjinha.databinding.FragmentMainBinding
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.ui.adapters.BannerAdapter
import com.example.olodjinha.ui.adapters.CategoriesAdapter
import com.example.olodjinha.ui.adapters.ProdutosAdapter
import com.example.olodjinha.ui.viewmodels.MainViewModel
import com.example.olodjinha.ui.viewmodels.MainViewModelProviderFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    lateinit var bannerAdapter: BannerAdapter
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var produtosAdapter: ProdutosAdapter

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

        setupCategoriesRv()

        setupMaisVendidosRv()

        setupObserver()


        viewModel.getBanners()

        viewModel.getCategories()

        viewModel.getMaisVendidos()

    }

    private fun setupMaisVendidosRv() {
        produtosAdapter = ProdutosAdapter()
        binding.maisVendidosRv.adapter = produtosAdapter
    }

    private fun setupCategoriesRv() {
        categoriesAdapter = CategoriesAdapter().apply {
            setOnItemClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToProdutosListFragment(
                        categoriaId = it.id,
                        title = it.descricao
                    )
                )
            }
        }
        binding.rvCategories.apply {
            adapter = categoriesAdapter
        }

    }

    private fun setupObserver() {
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerAdapter.differ.submitList(it)
        }

        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            categoriesAdapter.differ.submitList(it)
        }

        viewModel.maisVendidosLiveData.observe(viewLifecycleOwner) {
            produtosAdapter.differ.submitList(it)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
