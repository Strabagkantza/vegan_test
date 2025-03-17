package com.lina.test.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lina.domain.base.Output
import com.lina.domain.models.News
import com.lina.test.utils.UiEffect
import com.lina.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    private val _effect = MutableSharedFlow<Effect>()
    val effect: Flow<Effect> = _effect.asSharedFlow()

    fun sendEvent(event: Event) {
        when (event) {
            Event.LoadData -> loadData()
            is Event.NavigateToDetail -> navigateToDetail(event.news)
            is Event.DeleteData -> deleteData(event.news)
            is Event.ShowNetworkError -> showNetworkError(event.snackbarHostState)
        }
    }

    private fun deleteData(news: News) {
        val listTmp = _uiState.value.lists.filter { it.mediaId != news.mediaId }
        _uiState.value = _uiState.value.copy(lists = listTmp)
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, lists = emptyList(), error = false)
                val res = useCase.executeNews()
                if (res.status == Output.Status.SUCCESS) {
                     _uiState.value = _uiState.value.copy(lists = res.data ?: emptyList(), error = false, isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, lists = emptyList(), error = true)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, lists = emptyList(), error = true)
            }
        }
    }

    private fun navigateToDetail(news: News) {
        viewModelScope.launch {
            _effect.emit(Effect.NavigateToDetail(news))
        }
    }

    private fun showNetworkError(snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            _effect.emit(Effect.ShowNetworkError(snackbarHostState))
        }
    }

    sealed class Event {
        object LoadData : Event()
        data class NavigateToDetail(val news: News) : Event()
        data class DeleteData(val news: News) : Event()
        data class ShowNetworkError(val snackbarHostState: SnackbarHostState) : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowNetworkError(val snackbarHostState: SnackbarHostState) : Effect()
        data class NavigateToDetail(val news: News) : Effect()
    }

    data class MainUiState(
        val lists: List<News> = emptyList(),
        val isLoading: Boolean = false,
        val error: Boolean = false
    )
}