package com.example.nikestorefinal.data.repo

import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.repo.source.ProductDataSource
import com.example.nikestorefinal.data.repo.source.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(sort: Int): Single<List<Product>> =
        localDataSource.getFavoriteProducts()
            .flatMap { favoriteProducts ->
                remoteDataSource.getProducts(sort).doOnSuccess {
                    val favoritesProductIds = favoriteProducts.map {
                        it.id
                    }
                    it.forEach { product ->
                        if (favoritesProductIds.contains(product.id))
                            product.isFavorite=true
                    }
                }
            }


    override fun getFavoriteProducts(): Single<List<Product>> =
        localDataSource.getFavoriteProducts()

    override fun addToFavorites(product: Product): Completable =
        localDataSource.addToFavorites(product)

    override fun deleteFromFavorites(product: Product): Completable =
        localDataSource.deleteFromFavorites(product)
}