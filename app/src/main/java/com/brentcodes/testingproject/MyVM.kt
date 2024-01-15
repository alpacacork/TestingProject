package com.brentcodes.testingproject

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MyVM : ViewModel() {
    private val _filteredList: MutableStateFlow<List<Person>> = MutableStateFlow(emptyList())
    val filteredList = _filteredList.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    var query : MutableState<String> = mutableStateOf("")

    init {
        _filteredList.value = listOf(
            Person("Brent", 10, false ),
            Person("Josh", 120, false),
            Person("Peter", 1221, false)
        )
    }

    fun onFilterClicked(index: Int) {
        toggleFilter(index)
    }

    private fun toggleFilter(index: Int) {
        val updatedList = _filteredList.value.toMutableList()
        updatedList[index].clicked = !updatedList[index].clicked
        _filteredList.value = updatedList
    }
}