package com.example.nikestorefinal.feature.main.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestorefinal.R
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.services.http.ImageLoadingService
import com.example.nikestorefinal.view.NikeImageView

class FavoriteProductsAdapter(
    val products: MutableList<Product>,
    val favoriteProductEventListener: FavoriteProductEventListener,
    val imageLoadingService: ImageLoadingService
) :
    RecyclerView.Adapter<FavoriteProductsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv = itemView.findViewById<TextView>(R.id.productTitleTvFavorite)
        val productIv = itemView.findViewById<NikeImageView>(R.id.nikeImageView)

        fun bindProduct(product: Product) {
            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image)
            itemView.setOnClickListener {
                favoriteProductEventListener.onClick(product)
            }

            itemView.setOnLongClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener.onLongClick(product)
                return@setOnLongClickListener false
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite_product, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    interface FavoriteProductEventListener {
        fun onClick(product: Product)
        fun onLongClick(product: Product)

    }
}

