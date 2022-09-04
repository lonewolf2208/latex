package com.example.mathongotask

import androidx.annotation.WorkerThread
import com.example.mathongotask.model.database.Content
import com.example.mathongotask.database.ContentDAO


class ContentRepo(private val wordDao: ContentDAO) {

    // Room executes all queries on a` separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords :kotlinx.coroutines.flow.Flow<List<Content>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: MutableList<Content>) {
        wordDao.insert(word)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delteAll()
    {
        wordDao.deleteAll()
    }
}
