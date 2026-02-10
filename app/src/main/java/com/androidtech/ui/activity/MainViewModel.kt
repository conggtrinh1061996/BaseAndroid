package com.androidtech.ui.activity

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.extension.None
import com.androidtech.domain.model.Demo
import com.androidtech.domain.model.news.Article
import com.androidtech.domain.model.user.User
import com.androidtech.domain.use_case.AddBookmarkUseCase
import com.androidtech.domain.use_case.GetBookmarkUseCase
import com.androidtech.domain.use_case.GetCurrentUserUseCase
import com.androidtech.domain.use_case.GetDemoListUseCase
import com.androidtech.domain.use_case.GetSavedBookmarkUseCase
import com.androidtech.domain.use_case.IsLoggedInUseCase
import com.androidtech.domain.use_case.RemoveBookmarkUseCase
import com.androidtech.domain.use_case.SignOutUseCase
import com.androidtech.util.Logger
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDemoListUseCase: GetDemoListUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getBookmarkUseCase: GetBookmarkUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getSavedBookmarkUseCase: GetSavedBookmarkUseCase
) : BaseViewModel<MainViewModel.MainState>() {

    data class MainState(
        val demo: Demo? = null,
        val isLoggedIn: Boolean = false,
        val user: User? = null,
        val bookmarks: List<Article> = emptyList(),
        val isInitialDataLoaded: Boolean = false,
        val currentUrl: String? = null,
        val selectedCategory: String = "general",
        val exception: Exception? = null
    ) : UIState

    override fun createInitialState(): MainState {
        return MainState()
    }

    fun getDemo() {
        viewModelScope.launch {
            getDemoListUseCase(
                None(),
                success = { demo: Demo ->
                    setState { copy(demo = demo) }
                },
                error = { throwable ->
                    setState { copy(exception = Exception(throwable.message)) }
                }
            )
        }
    }

    init {
        checkAuthentication()
        observeAuthenticationChanges()
        //loadUserProfile()
    }

    fun setCurrentUrl(url: String) {
        setState { copy(currentUrl = url) }
    }

    fun setSelectedCategory(category: String) {
        setState { copy(selectedCategory = category) }
    }
    fun observeAuthenticationChanges() {
        viewModelScope.launch {
            uiState.map { it.isLoggedIn }
                .distinctUntilChanged()
                .collect { isLoggedIn ->
                    if(isLoggedIn) {
                        loadUserProfile()
                        loadInitialData()
                    }else {
                        setState { copy(user = null, bookmarks = emptyList(), isInitialDataLoaded = false)}
                    }
                }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            setState { copy(isInitialDataLoaded = false) }

            val userJob = async { getCurrentUserUseCase() }
            val bookmarkJob = async {
                val uid = uiState.value.user?.uid ?: return@async null
                //val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@async null
                getBookmarkUseCase(uid).first()
            }
            val userResult = userJob.await()
            val bookmarkResult = bookmarkJob.await()

            userResult.onSuccess { user ->
                setState { copy(user = user) }
            }
            bookmarkResult?.onSuccess { bookmarks ->
                setState { copy(bookmarks = bookmarks) }
            }
            setState { copy(isInitialDataLoaded = true) }
            observeBookmarks()
        }
    }

    fun checkAuthentication() {
        val isLoggedIn = isLoggedInUseCase()
        setState { copy(isLoggedIn = isLoggedIn) }
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            val result = getCurrentUserUseCase()
            result.onSuccess {
                setState { copy(user = it) }
            }.onFailure {
                setState { copy(exception = Exception(it.message)) }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val result = signOutUseCase()
            result.onSuccess {
                setState { copy(isLoggedIn = false, user = null) }
                Logger.d("Signed out successfully")
            }.onFailure {
                setState { copy(exception = Exception(it.message)) }
            }
        }
    }
    private fun String.toKey(): String = this.hashCode().toString().replace("-", "m")

    fun toggleBookmark(article: Article) {
        val uid = uiState.value.user?.uid ?: return

        val currentBookmarks = uiState.value.bookmarks
        val isAlreadyBookmarked = currentBookmarks.any { it.url == article.url }
        if(isAlreadyBookmarked) {
            setState { copy(bookmarks = currentBookmarks.filterNot { it.url == article.url }) }
        } else {
            setState { copy(bookmarks = currentBookmarks + article) }
        }
        viewModelScope.launch {
            val articleKey = article.url.toKey()

            val result = if(isAlreadyBookmarked) {
                removeBookmarkUseCase(uid, articleKey)
            }else {
                addBookmarkUseCase(uid, article)
            }
            result.onFailure {
                setState { copy(exception = Exception(it.message)) }
            }
        }
    }

    fun observeBookmarks() {
        viewModelScope.launch {
            val uid = uiState.value.user?.uid ?: return@launch

            getBookmarkUseCase(uid).collect {
                it.onSuccess { bookmarks ->
                    setState { copy(bookmarks = bookmarks) }
                }
                it.onFailure { throwable ->
                    setState { copy(exception = Exception(throwable.message)) }
                }
            }

        }
    }


}