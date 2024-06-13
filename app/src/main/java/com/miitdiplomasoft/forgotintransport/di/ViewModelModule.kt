package com.miitdiplomasoft.forgotintransport.di

import com.miitdiplomasoft.forgotintransport.data.authorization.AuthorizationNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.add.ItemAddNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.delete.ItemDeleteNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.favotites.FavoriteStatusChangeNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.find.ItemsFindNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.many.ItemsReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.single.ItemReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.registration.RegistrationNetworkDataSource
import com.miitdiplomasoft.forgotintransport.viewmodels.AddItemFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.AdminCardFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.AuthorizationFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.CardFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.FavoritesFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.SearchFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.MenuFragmentViewModel
import com.miitdiplomasoft.forgotintransport.viewmodels.RegistrationFragmentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideAuthorizationFragmentViewModel(authorizationNetworkDataSource: AuthorizationNetworkDataSource): AuthorizationFragmentViewModel {
        return AuthorizationFragmentViewModel(authorizationNetworkDataSource)
    }
    @Provides
    fun provideRegistrationFragmentViewModel(registrationNetworkDataSource: RegistrationNetworkDataSource): RegistrationFragmentViewModel {
        return RegistrationFragmentViewModel(registrationNetworkDataSource)
    }
    @Provides
    fun provideMenuFragmentViewModel(itemsReceivingNetworkDataSource: ItemsReceivingNetworkDataSource): MenuFragmentViewModel {
        return MenuFragmentViewModel(itemsReceivingNetworkDataSource)
    }
    @Provides
    fun provideFavoritesFragmentViewModel(itemsReceivingNetworkDataSource: ItemsReceivingNetworkDataSource): FavoritesFragmentViewModel {
        return FavoritesFragmentViewModel(itemsReceivingNetworkDataSource)
    }
    @Provides
    fun provideCardFragmentViewModel(favoriteStatusChangeNetworkDataSource: FavoriteStatusChangeNetworkDataSource, itemReceivingNetworkDataSource: ItemReceivingNetworkDataSource): CardFragmentViewModel {
        return CardFragmentViewModel(favoriteStatusChangeNetworkDataSource, itemReceivingNetworkDataSource)
    }
    @Provides
    fun provideSearchFragmentViewModel(itemsFindNetworkDataSource: ItemsFindNetworkDataSource): SearchFragmentViewModel {
        return SearchFragmentViewModel(itemsFindNetworkDataSource)
    }
    @Provides
    fun provideAdminCardFragmentViewModel(itemDeleteNetworkDataSource: ItemDeleteNetworkDataSource, itemReceivingNetworkDataSource: ItemReceivingNetworkDataSource): AdminCardFragmentViewModel {
        return AdminCardFragmentViewModel(itemDeleteNetworkDataSource, itemReceivingNetworkDataSource)
    }
    @Provides
    fun provideAddItemFragmentViewModel(itemAddNetworkDataSource: ItemAddNetworkDataSource): AddItemFragmentViewModel {
        return AddItemFragmentViewModel(itemAddNetworkDataSource)
    }
}
