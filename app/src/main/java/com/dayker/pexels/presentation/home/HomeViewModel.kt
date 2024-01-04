package com.dayker.pexels.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dayker.pexels.data.datasource.remote.PexelsApiService.Companion.COLLECTIONS_QUANTITY
import com.dayker.pexels.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var featuredCollections = emptyList<String>()

    init {
        viewModelScope.launch {
            val curatedImages = repository.getImages().cachedIn(viewModelScope)
            featuredCollections = repository.getFeaturedCollections(quantity = COLLECTIONS_QUANTITY)
                .map { collection -> collection.title }
            _state.update {
                it.copy(
                    images = curatedImages,
                    featuredCollections = featuredCollections
                )
            }
        }
    }

    private val _actionFlow = MutableSharedFlow<HomeScreenAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.OnExploreClicked -> {
                viewModelScope.launch {
                    _state.update {
                        state.value.copy(
                            query = "",
                            images = repository.getImages().cachedIn(viewModelScope)
                        )
                    }
                }
            }

            is HomeScreenEvent.OnImageClicked -> {
                viewModelScope.launch {
                    val isCurated = state.value.query.isEmpty()
                    _actionFlow.emit(
                        HomeScreenAction.OpenImageDetails(
                            id = event.id,
                            isCurated = isCurated
                        )
                    )
                }
            }

            is HomeScreenEvent.OnSearch -> {
                _state.update {
                    state.value.copy(
                        query = event.query,
                        featuredCollections = featuredCollections.toMutableList().also {
                            if (it.remove(event.query)) {
                                it.add(0, event.query)
                            }
                        }
                    )
                }
                viewModelScope.launch {
                    if (event.query.isNotEmpty()) {
                        delay(1000)
                        _state.update {
                            state.value.copy(
                                images = repository.getImages(query = event.query)
                                    .cachedIn(viewModelScope)
                            )
                        }
                    } else {
                        _state.update {
                            state.value.copy(
                                images = repository.getImages().cachedIn(viewModelScope)
                            )
                        }
                    }
                }
            }

            is HomeScreenEvent.OnTitleClicked -> {
                viewModelScope.launch {
                    _state.update {
                        state.value.copy(
                            query = event.title,
                            images = repository.getImages(query = event.title)
                                .cachedIn(viewModelScope),
                            featuredCollections = featuredCollections.toMutableList().also {
                                if (it.remove(event.title)) {
                                    it.add(0, event.title)
                                }
                            })
                    }
                }
            }

            is HomeScreenEvent.OnReloadClicked -> {
                viewModelScope.launch {
                    if (featuredCollections.isEmpty()) {
                        featuredCollections =
                            repository.getFeaturedCollections(quantity = COLLECTIONS_QUANTITY)
                                .map { collection -> collection.title }
                    }
                    if (state.value.query.isNotEmpty()) {
                        delay(1000)
                        _state.update {
                            state.value.copy(
                                images = repository.getImages(query = state.value.query)
                                    .cachedIn(viewModelScope),
                                featuredCollections = featuredCollections.toMutableList().also {
                                    if (it.remove(state.value.query)) {
                                        it.add(0, state.value.query)
                                    }
                                })
                        }
                    } else {
                        _state.update {
                            state.value.copy(
                                images = repository.getImages().cachedIn(viewModelScope),
                                featuredCollections = featuredCollections
                            )
                        }
                    }
                }
            }
        }
    }
}