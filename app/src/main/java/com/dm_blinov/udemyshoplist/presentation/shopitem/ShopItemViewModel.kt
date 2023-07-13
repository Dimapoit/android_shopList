package com.dm_blinov.udemyshoplist.presentation.shopitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dm_blinov.udemyshoplist.domain.*
import javax.inject.Inject

class ShopItemViewModel @Inject constructor (
    private val addShopItemUseCase: AddShopItemUseCase,
    private  val getShopItemUseCase: GetShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    //Сообщит когда можно закрыть экран
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() {
            return _shouldCloseScreen
        }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            println(e.message)
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var res = true
        if (name.isBlank()) {
            //Устанавливаем ошибку ввода name
            _errorInputName.value = true
            res = false
        }
        if (count <= 0) {
            //Устанавливаем ошибку ввода count
            _errorInputCount.value = true
            res = false
        }
        return res
    }

    //Функция сброса ошибки ввода name
    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    //Функция сброса ошибки ввода count
    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun addShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val item = ShopItem(name = name, count = count, enabled = true)
            addShopItemUseCase.addShopItem(item)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {

            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.value = item
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}