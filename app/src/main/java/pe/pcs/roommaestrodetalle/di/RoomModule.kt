package pe.pcs.roommaestrodetalle.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.database.AppDatabase
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepositoryImpl
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepositoryImpl
import pe.pcs.roommaestrodetalle.domain.repository.PedidoRepository
import pe.pcs.roommaestrodetalle.domain.repository.ProductoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "pedido_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    // *** Proveer los Dao *** //

    @Singleton
    @Provides
    fun provideProductoDao(db: AppDatabase): ProductoDao {
        return db.productoDao()
    }

    @Singleton
    @Provides
    fun providePedidoDao(db: AppDatabase): PedidoDao {
        return db.pedidoDao()
    }

    // *** Proveer los repositorios del dominio *** //

    @Singleton
    @Provides
    fun provideProductoRepository(productoDao: ProductoDao): ProductoRepository {
        return ProductoRepositoryImpl(productoDao)
    }

    @Singleton
    @Provides
    fun providePedidoRepository(pedidoDao: PedidoDao): PedidoRepository {
        return PedidoRepositoryImpl(pedidoDao)
    }

}