package com.norm.vkgroupviewer.presentation.groups

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import com.norm.vkgroupviewer.util.onError
import com.norm.vkgroupviewer.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val vkUseCases: VkUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(GroupsState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        _state.onEach { state ->
            state.userId?.let { userId ->
                Log.d("MyLog", "getUserId: $userId")
                viewModelScope.launch {
                    vkUseCases.getGroups(userId)
                        .onSuccess { groupsInfo ->
                            setGroupsInfo(groupsInfo)
                        }
                        .onError { errorMessage ->
                            setErrorMessage(errorMessage.name)
                        }
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

    private fun setGroupsInfo(groupsInfo: GroupsInfo) {
        _state.update {
            it.copy(
                groupsInfo = groupsInfo,
            )
        }
    }

    private fun setErrorMessage(errorMessage: String) {
        _state.update {
            it.copy(
                errorMessage = errorMessage,
            )
        }
    }
}

