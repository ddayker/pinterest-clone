package com.dayker.pexels.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.core.util.Resource
import com.dayker.pexels.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksState())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<BookmarksScreenAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.getBookmarks().onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update {
                            state.value.copy(isLoading = false, isNoBookmarks = true)
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            state.value.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        if (resource.data != null)
                            if (resource.data.isNotEmpty()) {
                                _state.update {
                                    state.value.copy(
                                        isLoading = false,
                                        isNoBookmarks = false,
                                        images = resource.data
                                    )
                                }
                            } else {
                                _state.update {
                                    state.value.copy(
                                        isLoading = false,
                                        isNoBookmarks = true,
                                        images = emptyList()
                                    )
                                }
                            }
                    }
                }
            }.launchIn(this)
        }
    }

    fun onEvent(event: BookmarksScreenEvent) {
        when (event) {
            BookmarksScreenEvent.OnExploreClicked -> {
                viewModelScope.launch {
                    _actionFlow.emit(
                        BookmarksScreenAction.Explore
                    )
                }
            }

            is BookmarksScreenEvent.OnImageClicked -> {
                viewModelScope.launch {
                    _actionFlow.emit(
                        BookmarksScreenAction.OpenImageDetails(
                            id = event.id
                        )
                    )
                }
            }
        }
    }
}