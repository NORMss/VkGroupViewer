package com.norm.vkgroupviewer.presentation.friendlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val vkUseCases: VkUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(FriendsState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)
}