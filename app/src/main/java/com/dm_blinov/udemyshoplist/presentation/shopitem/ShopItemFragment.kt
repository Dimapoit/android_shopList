package com.dm_blinov.udemyshoplist.presentation.shopitem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.databinding.FragmentShopItemBinding
import com.dm_blinov.udemyshoplist.domain.ShopItem


class ShopItemFragment : Fragment() {

    private lateinit var shopItemViewModel: ShopItemViewModel


    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentShopItemBinding == null")

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        Log.d("lifecycleFragment", "onAttach")
        super.onAttach(context)
        if(context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity must implement OnEditingFinishedListener")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("debug", "ShopItemFragment - onCreate")
        Log.d("lifecycleFragment", "onCreate")
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("lifecycleFragment", "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)

        binding.viewModel = shopItemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        Log.d("lifecycleFragment", "onViewCreated")
        Log.d("debug", "screenMode: $screenMode")
        Log.d("debug", "shopItemId: $shopItemId")
        when (screenMode) {
            MODE_ADD -> launchAddScreenMode()
            MODE_EDIT -> launchEditScreenMode()
        }
        addTextChangedListeners()
        addObserve()
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycleFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycleFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycleFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycleFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("lifecycleFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycleFragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("lifecycleFragment", "onDetach")
    }

    private fun addObserve() {
//        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
//            val message = if (it) getString(R.string.error_input_name) else null
//            binding.etName.error = message
//        }
//        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
//            val message = if (it) getString(R.string.error_input_count) else null
//            binding.etCount.error = message
//        }
        shopItemViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            //activity?.onBackPressed()
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun addTextChangedListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun launchAddScreenMode() {
        binding.saveButton.setOnClickListener {

            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            shopItemViewModel.addShopItem(name, count)
        }
        Log.d("debug", "launchAddScreenMode")
    }

    private fun launchEditScreenMode() {
        shopItemViewModel.getShopItem(shopItemId)
//        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
//            binding.etName.setText(it.name)
//            binding.etCount.setText(it.count.toString())
//        }
        binding.saveButton.setOnClickListener {
            val name = binding.etName.text?.toString()
            val count = binding.etCount.text?.toString()
            shopItemViewModel.editShopItem(name, count)
        }
        Log.d("debug", "launchEditScreenMode")
    }

    //Проверка наличия и корректности параметров
    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (mode == MODE_EDIT ) {
            if(!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
        }
        shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    //NewIntent
    companion object {
        private const val SCREEN_MODE = "SCREEN_MODE"
        private const val SHOP_ITEM_ID = "EXTRA_SHOP_ITEM_ID"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddFragment(): ShopItemFragment {
//            val args = Bundle()
//            args.putString(SCREEN_MODE, MODE_ADD)
//            val fragment = ShopItemFragment()
//            fragment.arguments = args
//            return fragment
            //В стиле kotlin:
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditFragment(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}