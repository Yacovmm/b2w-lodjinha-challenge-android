package com.example.olodjinha.ui.fragments

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.example.olodjinha.api.RetrofitInstance
import com.example.olodjinha.databinding.ProductDetailFragmentBinding
import com.example.olodjinha.databinding.ProdutosListFragmentBinding
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.ui.adapters.ProdutosAdapter
import com.example.olodjinha.ui.helpers.NavigationDelegate
import com.example.olodjinha.ui.viewmodels.MainViewModel
import com.example.olodjinha.ui.viewmodels.MainViewModelProviderFactory

class ProductDetailFragment : Fragment() {

    lateinit var binding: ProductDetailFragmentBinding

    val args: ProductDetailFragmentArgs by lazy {
        ProductDetailFragmentArgs.fromBundle(requireArguments())
    }

    private val viewModel: MainViewModel by lazy {
        val repository = MainRepository(RetrofitInstance.apiService)
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

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
        binding = ProductDetailFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setupWithNavController(findNavController(), null)

        binding.productIv.load(args.imageUrl) {
            crossfade(true)
            crossfade(700)
            listener(onSuccess = { _, _ ->
                startPostponedEnterTransition()
                // do something
            }, onError = { request: ImageRequest, throwable: Throwable ->
                // handle error here
                println(throwable.message)
            })
        }

        listener?.setToolBarTitle("")


    }



}
