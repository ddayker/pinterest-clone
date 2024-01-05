package com.dayker.pexels.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.core.navigation.graphs.Graph.IMAGE_ID_PARAM
import com.dayker.pexels.core.navigation.graphs.Graph.IS_IMAGE_BOOKMARK
import com.dayker.pexels.core.navigation.graphs.Graph.IS_IMAGE_CURATED_PARAM
import com.dayker.pexels.core.util.Resource
import com.dayker.pexels.domain.model.Image
import com.dayker.pexels.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ImageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.IsLoading)
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<DetailsScreenAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        val isCurated: Boolean = savedStateHandle.get<Boolean>(IS_IMAGE_CURATED_PARAM) ?: false
        val id: Int = savedStateHandle.get<Int>(IMAGE_ID_PARAM) ?: -1
        var isBookmark: Boolean = savedStateHandle.get<Boolean>(IS_IMAGE_BOOKMARK) ?: false

        if (id != -1) {
            viewModelScope.launch {
                val result = repository.getImageDetails(
                    id = id,
                    isImageCurated = isCurated,
                    isImageBookmark = isBookmark
                )
                when (result) {
                    is Resource.Success -> {
                        if (result.data != null) {
                            if (!isBookmark) {
                                isBookmark = repository.checkForBookmark(result.data.src)
                            }
                            _state.update {
                                DetailsState.ImageDetails(
                                    photographer = result.data.photographer,
                                    src = result.data.src,
                                    isBookmark = isBookmark
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            DetailsState.IsNotFound
                        }
                    }

                    else -> {}
                }
            }

        } else {
            _state.update {
                DetailsState.IsNotFound
            }
        }
    }


    fun onEvent(event: DetailsScreenEvent) {
        when (event) {
            DetailsScreenEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _actionFlow.emit(
                        DetailsScreenAction.GoBack
                    )
                }
            }

            DetailsScreenEvent.OnBookmarkClicked -> {
                viewModelScope.launch {
                    with(state.value) {
                        if (this.isBookmark) {
                            repository.deleteBookmark(this.src)
                        } else {
                            repository.addBookmark(Image(0, this.src, photographer))
                        }
                        _state.update {
                            DetailsState.ImageDetails(
                                photographer = this.photographer,
                                src = this.src,
                                isBookmark = !this.isBookmark
                            )
                        }
                    }
                }
            }

            DetailsScreenEvent.OnDownloadClicked -> {
                viewModelScope.launch {
                    repository.saveImage(state.value.src)
                }
            }

            DetailsScreenEvent.OnExploreClicked -> {
                viewModelScope.launch {
                    _actionFlow.emit(
                        DetailsScreenAction.Explore
                    )
                }
            }
        }
    }
}


