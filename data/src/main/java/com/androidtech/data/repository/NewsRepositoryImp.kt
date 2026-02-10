package com.androidtech.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.androidtech.data.local.database.AppDatabase
import com.androidtech.data.model.news.BookmarkDto
import com.androidtech.data.model.news.BookmarkEntity
import com.androidtech.data.model.news.toDomain
import com.androidtech.data.model.news.toEntity
import com.androidtech.data.network.ApiService
import com.androidtech.domain.model.news.Article
import com.androidtech.domain.repository.NewsRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val firebaseDb: FirebaseDatabase
): NewsRepository{

    /*override fun getNews(): Flow<Resource<List<Article>>> = flow {

        val cached = appDatabase.getArticleDao().getAllArticles().map { articleModel ->
            articleModel.transform() }
        Log.d("NewsRepositoryImp", "getNews from cache: ${cached.size}")
        if(cached.isNotEmpty()) {
            return@flow emit(Resource.Success(cached))
        }

        val response = apiService.getTopHeadlines("us", 1,20,"e2c8643cf3b84da391633b213c9efac7",)
        if (response.isSuccessful) {
            appDatabase.getArticleDao().deleteAll()
            appDatabase.getArticleDao().insertAll(response.body()?.articles?:listOf())
            response.body()?.let {
                emit(Resource.Success(
                    it.articles.map { articleModel ->
                        articleModel.transform()
                    }
                ))
            }
        } else {
            emit(Resource.Error(response.message()))
        }

    }*/

    override fun getNewsPaging(category: String, q: String): Flow<PagingData<Article>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = NewsRemoteMediator(
                apiService = apiService,
                database = database,
                apiKey = "e2c8643cf3b84da391633b213c9efac7",
                category = category,
                q = q
            ),
            pagingSourceFactory = {database.getNewsDao().pagingSource(category, q)}
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    private fun String.toKey(): String = this.hashCode().toString().replace("-", "m")

    override suspend fun addBookmark(
        uid: String,
        article: Article
    ): Result<Unit> {
        return try {
            val key = article.url.toKey()
            database.bookmarkDao().upsert(article.toEntity())
            firebaseDb.getReference("users")
                .child(uid)
                .child("bookmarks")
                .child(key)
                .setValue(article)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeBookmark(
        uid: String,
        newsId: String
    ): Result<Unit> {
        return try {
            database.bookmarkDao().deleteByUrl(newsId)
            firebaseDb.getReference("users")
                .child(uid)
                .child("bookmarks")
                .child(newsId)
                .removeValue()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getAllBookmarks(uid: String): Flow<Result<List<Article>>> = callbackFlow {
        val ref = firebaseDb.getReference("users").child(uid).child("bookmarks")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { it.getValue(Article::class.java) }
                trySend(Result.success(items))
                CoroutineScope(Dispatchers.IO).launch {
                    database.bookmarkDao().upsertBookmark(items.map { it.toEntity() })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(error.toException()))
            }
        }
        ref.addValueEventListener(listener)
        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override fun getSavedBookmarks(): Flow<List<Article>> {
        return database.bookmarkDao().getSavedBookmark().map { entities ->
            entities.map { it.toDomain() }
        }
    }

}

