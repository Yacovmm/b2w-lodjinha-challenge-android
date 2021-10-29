package com.example.olodjinha.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.olodjinha.R
import com.example.olodjinha.api.RetrofitInstance
import com.example.olodjinha.databinding.ProdutosListFragmentBinding
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.ui.adapters.ProdutosAdapter
import com.example.olodjinha.ui.helpers.NavigationDelegate
import com.example.olodjinha.ui.viewmodels.MainViewModel
import com.example.olodjinha.ui.viewmodels.MainViewModelProviderFactory

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

    var listener: NavigationDelegate? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationDelegate) {
            listener = context
        }
    }

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


        listener?.setToolBarTitle(args.title)

        viewModel.getProdutos(
            args.categoriaId
        )

        setupRv()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            produtosAdapter.differ.submitList(it.toList())
            viewModel.isPaginating = false
            binding.progress.visibility = View.GONE
        }
    }

    private fun setupRv() {
        produtosAdapter = ProdutosAdapter()
        binding.produtosRv.apply {
            adapter = produtosAdapter
            addOnScrollListener(scrollLister)
        }
    }

    private val scrollLister = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            (recyclerView.layoutManager as? LinearLayoutManager)?.let { linearLayout ->
                if (dy > 0 && viewModel.isPaginating.not() && viewModel.hasMoreItems) { // Check to scrool down
                    val visibleItemCount = linearLayout.childCount // Items visible in the recycler
                    val firstVisibleItem = linearLayout.findFirstCompletelyVisibleItemPosition() // Position of the first visible Item
                    val totalItemCount = produtosAdapter.itemCount // Items total of the adapter

                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        viewModel.getProdutos(args.categoriaId)
                        binding.progress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}
