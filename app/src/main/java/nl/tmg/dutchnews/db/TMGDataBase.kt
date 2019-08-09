package nl.tmg.dutchnews.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.tmg.dutchnews.dto.Article


@Database(entities = arrayOf(Article::class), version = 1, exportSchema = false)
abstract class TMGDataBase : RoomDatabase() {


    abstract fun getArticleDao(): ArticleDao

    companion object {

        @VisibleForTesting
        private const val DATABASE_NAME = "tmgDb"

        @Volatile
        private var INSTANCE: TMGDataBase? = null

        fun getInstance(context: Context, testMode: Boolean): TMGDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, testMode).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context, testMode: Boolean): TMGDataBase {
            return if (testMode) {
                Room
                    .inMemoryDatabaseBuilder(context, TMGDataBase::class.java)
                    .allowMainThreadQueries()
                    .build()
            } else {
                Room.databaseBuilder(context, TMGDataBase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
        }

    }
}