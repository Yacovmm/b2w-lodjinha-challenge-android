package com.example.olodjinha.ui.fragments

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.olodjinha.api.RetrofitInstance
import com.example.olodjinha.databinding.ProdutosListFragmentBinding
import com.example.olodjinha.databinding.SecondFragmentBinding
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.ui.adapters.ProdutosAdapter

class ProdutosListFragment : Fragment() {

    lateinit var binding: ProdutosListFragmentBinding

    val args: ProdutosListFragmentArgs by lazy {
        ProdutosListFragmentArgs.fromBundle(requireArguments())
    }

    private val viewModel: MainViewModel by lazy {
        val repository = MainRepository(RetrofitInstance.apiService)
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    lateinit var produtosAdapter: ProdutosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProdutosListFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProdutos(
            args.categoriaId,
            null,
            null
        )

        setupRv()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            produtosAdapter.differ.submitList(it)
        }
    }

    private fun setupRv() {
        produtosAdapter = ProdutosAdapter()
        binding.produtosRv.adapter = produtosAdapter
    }
}
