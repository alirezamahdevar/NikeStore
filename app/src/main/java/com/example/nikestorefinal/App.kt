package com.example.nikestorefinal

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.nikestorefinal.data.db.AppDatabase
import com.example.nikestorefinal.data.repo.*
import com.example.nikestorefinal.data.repo.order.OrderRemoteDataSource
import com.example.nikestorefinal.data.repo.order.OrderRepository
import com.example.nikestorefinal.data.repo.order.OrderRepositoryImpl
import com.example.nikestorefinal.data.repo.source.*
import com.example.nikestorefinal.feature.main.ProductDetailViewModel
import com.example.nikestorefinal.feature.main.auth.AuthViewModel
import com.example.nikestorefinal.feature.main.cart.CartViewModel
import com.example.nikestorefinal.feature.main.checkout.CheckoutViewModel
import com.example.nikestorefinal.feature.main.common.ProductListAdapter
import com.example.nikestorefinal.feature.main.favorite.FavoriteProductActivity
import com.example.nikestorefinal.feature.main.favorite.FavoriteProductsViewModel
import com.example.nikestorefinal.feature.main.home.HomeViewModel
import com.example.nikestorefinal.feature.main.list.ProductListViewModel
import com.example.nikestorefinal.feature.main.main.MainViewModel
import com.example.nikestorefinal.feature.main.order.OrderHistoryViewModel
import com.example.nikestorefinal.feature.main.product.comment.CommentListViewModel
import com.example.nikestorefinal.feature.main.profile.ProfileViewModel
import com.example.nikestorefinal.feature.main.shipping.ShippingViewModel
import com.example.nikestorefinal.services.http.FrescoImageLoadingService
import com.example.nikestorefinal.services.http.ImageLoadingService
import com.example.nikestorefinal.services.http.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.scope.get
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)
        val myModules = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            single { Room.databaseBuilder(this@App,AppDatabase::class.java,"db_app").build() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get<AppDatabase>().productDao()
                )
            }
            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_settings",
                    MODE_PRIVATE
                )
            }
            single { UserLocalDataSource(get()) }

            single<UserRepository> {
                UserRepositoryImpl(

                    UserLocalDataSource(get()),
                    UserRemoteDataSource(get())
                )
            }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId: Int) -> CheckoutViewModel(orderId, get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }
            viewModel { OrderHistoryViewModel(get()) }

        }

        startKoin() {
            androidContext(this@App)
            modules(myModules)
        }
        val userRepository: UserRepository = get()
        userRepository.loadToken()

    }
}