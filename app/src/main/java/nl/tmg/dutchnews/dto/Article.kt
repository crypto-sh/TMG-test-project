package nl.tmg.dutchnews.dto


import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import nl.tmg.dutchnews.db.DataConverter
import java.util.*


@Parcelize
@Entity
@TypeConverters(DataConverter::class)
data class Article(
        @PrimaryKey(autoGenerate = true) val id      : Int,
        val title   : String,
        val source  : Source,
        @SerializedName("urlToImage")
        val image   : String?,
        @SerializedName("publishedAt")
        val date    : Date?,
        @SerializedName("content")
        val content : String?,
        val author  : String?
) : Parcelable {

    class ArticleDiff : DiffUtil.ItemCallback<Article>() {
        /**
         * Called to check whether two objects represent the same item.
         *
         *
         * For example, if your items have unique ids, this method should check their id equality.
         *
         *
         * Note: `null` items in the list are assumed to be the same as another `null`
         * item and are assumed to not be the same as a non-`null` item. This callback will
         * not be invoked for either of those cases.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return True if the two items represent the same object or false if they are different.
         *
         * @see Callback.areItemsTheSame
         */
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem

        /**
         * Called to check whether two items have the same data.
         *
         *
         * This information is used to detect if the contents of an item have changed.
         *
         *
         * This method to check equality instead of [Object.equals] so that you can
         * change its behavior depending on your UI.
         *
         *
         * For example, if you are using DiffUtil with a
         * [RecyclerView.Adapter], you should
         * return whether the items' visual representations are the same.
         *
         *
         * This method is called only if [.areItemsTheSame] returns `true` for
         * these items.
         *
         *
         * Note: Two `null` items are assumed to represent the same contents. This callback
         * will not be invoked for this case.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return True if the contents of the items are the same or false if they are different.
         *
         * @see Callback.areContentsTheSame
         */
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = newItem.title == oldItem.title

    }
}

fun articleGenerator() = Article(12,"title",Source("id","name" ), "", Date(), "", "Bita")