package com.norm.vkgroupviewer.presentation.friendlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import com.norm.vkgroupviewer.util.FieldsFriendsGet
import com.norm.vkgroupviewer.util.onError
import com.norm.vkgroupviewer.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val vkUseCases: VkUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(FriendsState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        _state.update {
            it.copy(
                fields = "${FieldsFriendsGet.PHOTO_100},${FieldsFriendsGet.NICKNAME},${FieldsFriendsGet.DOMAIN}"
            )
        }
        _state
            .map { it.userId }
            .distinctUntilChanged()
            .onEach { userId ->
                userId?.let {
                    if (_state.value.friendsInfo == null) {
                        getFriends(it, _state.value.fields)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun setUserId(userId: Int) {
        _state.update {
            it.copy(
                userId = userId,
            )
        }
    }

    fun showOrHideMoreInfo(id: Int) {
        _state.update {
            it.copy(
                showMoreInfo = _state.value.showMoreInfo.mapValues { map ->
                    if (map.key == id) {
                        !map.value
                    } else
                        map.value
                }
            )
        }
    }

    fun refreshVkFriends() {
        _state.value.userId?.let {
            viewModelScope.launch {
                getFriends(it, _state.value.fields)
            }
        }
    }

    private fun setStatusLoadingGroups(isLoadingGroups: Boolean) {
        _state.update {
            it.copy(
                isLoadingGroups = isLoadingGroups,
            )
        }
    }

    private suspend fun getFriends(id: Int, fields: String?) {
        setStatusLoadingGroups(true)
        vkUseCases.getFriends(id, fields)
            .onSuccess { friendsInfo ->
                _state.update { it ->
                    it.copy(
                        friendsInfo = friendsInfo,
                        showMoreInfo = friendsInfo.response.items.associate { user ->
                            user.id to false
                        }
                    )
                }
            }
            .onError {

            }
        setStatusLoadingGroups(false)
    }
}