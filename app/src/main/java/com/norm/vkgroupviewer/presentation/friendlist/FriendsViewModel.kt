package com.norm.vkgroupviewer.presentation.friendlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import com.norm.vkgroupviewer.util.FieldsFriendsGet
import com.norm.vkgroupviewer.util.onError
import com.norm.vkgroupviewer.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val vkUseCases: VkUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(FriendsState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        _state.update {
            it.copy(
                fields = "${FieldsFriendsGet.PHOTO_100},${FieldsFriendsGet.NICKNAME}"
            )
        }
        _state.onEach { state ->
            state.userId?.let { userId ->
                viewModelScope.launch {
                    getFriends(userId, _state.value.fields)
                }.join()
            }
        }
    }

    fun setUserId(userId: Int) {
        _state.update {
            it.copy(
                userId = userId,
            )
        }
    }

    private fun getFriends(id: Int, fields: String?) {
        viewModelScope.launch {
            vkUseCases.getFriends(id, fields)
                .onSuccess { friendsInfo ->
                    _state.update {
                        it.copy(
                            friendsInfo = friendsInfo,
                        )
                    }
                }
                .onError {

                }
        }
    }
}