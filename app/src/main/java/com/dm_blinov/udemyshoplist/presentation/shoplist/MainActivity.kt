package com.dm_blinov.udemyshoplist.presentation.shoplist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dm_blinov.udemyshoplist.R
import com.dm_blinov.udemyshoplist.databinding.ActivityMainBinding
import com.dm_blinov.udemyshoplist.presentation.ShopListApplication
import com.dm_blinov.udemyshoplist.presentation.ViewModelFactory
import com.dm_blinov.udemyshoplist.presentation.shopitem.ShopItemActivity
import com.dm_blinov.udemyshoplist.presentation.shopitem.ShopItemFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ShopListApplication).component
    }

    lateinit var shopListAdapter: ShopListAdapter
    //private var shopItemContainer: FragmentContainerView? = null
    private lateinit var binding: ActivityMainBinding

    //var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main)
        //shopItemContainer = findViewById(R.id.shop_item_container)
        initRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            //shopListAdapter.shopList = it
            shopListAdapter.submitList(it)
        }

        //val addShopItemButton = findViewById<FloatingActionButton>(R.id.btn_add_shop_item)

        binding.btnAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                //В книжной ориентации запускаем ShopItemActivity
                startActivity(ShopItemActivity.newIntentAdd(this))
            } else {
                //В альбомной ориентации запускаем ShopItemFragment в main activity
                launchFragment(ShopItemFragment.newInstanceAddFragment())
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        // Удаляет фрагмент из стека если он существует
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null) // Чтоб не закррывался проект если фрагмент вставлен в mainActivity
            .commit()
    }

    fun initRecyclerView() {
        //val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()

        binding.rvShopList.adapter = shopListAdapter
        binding.rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        binding.rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        initLongClickListener()
        initClickListener()
        InitSwipeListener(binding.rvShopList)
    }

    private fun InitSwipeListener(rvShopList: RecyclerView) {
        //Создаем simpleCallBack для удаления с помощью свайпа
        val simpleCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        //Создаем объект класса ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        //Прикрепляем его к RecyclerView
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun initClickListener() {
        shopListAdapter.onShopItemClick = {
            if (isOnePaneMode()) {
                //В книжной ориентации запускаем ShopItemActivity
                val intent = ShopItemActivity.newIntentEdit(this, it.id)
                startActivity(intent)
            } else {
                //В альбомной ориентации запускаем ShopItemFragment в main activity
                launchFragment(ShopItemFragment.newInstanceEditFragment(it.id))
            }

        }
    }

    private fun initLongClickListener() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.editShopItem(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Succsess", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }
}