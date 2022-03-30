package com.example.libraryapp.device.di

object DiAppModules {
    private val modules = listOf(
        DispatchersDiModule.dispatchers,
        RepositoryDiModuleProvider.repositories,
        RepositoryDiModuleProvider.sources,
        RepositoryDiModuleProvider.stores,
        NetworkDiModule.network,
        UseCaseDiModule.useCases,
        ViewModelDiModule.viewModels
    )

    fun provideModules() = modules

}