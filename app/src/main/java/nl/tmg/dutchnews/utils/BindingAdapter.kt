package nl.tmg.dutchnews.utils


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nl.tmg.dutchnews.adapters.RvAdapterArticle
import nl.tmg.core.widget.ImageViewCustom

class BindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun RecyclerView.setBindingAdapter(adapter: RvAdapterArticle) {
            this.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL,false)
            this.adapter = adapter
        }

        @JvmStatic
        @BindingAdapter("loadImage")
        fun ImageViewCustom.loadImg(url: String?) {
            url?.let {
                this.load(url)
            }
        }
    }
}