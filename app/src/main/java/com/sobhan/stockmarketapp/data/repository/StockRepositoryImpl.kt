package com.sobhan.stockmarketapp.data.repository

import com.sobhan.stockmarketapp.data.csv.CSVParser
import com.sobhan.stockmarketapp.data.local.StockDatabase
import com.sobhan.stockmarketapp.data.mapper.toCompanyListing
import com.sobhan.stockmarketapp.data.mapper.toCompanyListingEntity
import com.sobhan.stockmarketapp.data.remote.StockApi
import com.sobhan.stockmarketapp.domain.Repository.StockRepository
import com.sobhan.stockmarketapp.domain.model.CompanyListing
import com.sobhan.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StockRepositoryImpl @Inject constructor(
    private val db: StockDatabase,
    private val api: StockApi,
    private val csvParser: CSVParser<CompanyListing>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListing(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListing = dao.searchCompanyListing(query)

            emit(Resource.Success(data = localListing.map { item ->
                item.toCompanyListing()
            }))

            val isDbEmpty = query.isEmpty() && localListing.isEmpty()
            val shouldJustLoadFromCache = !fetchFromRemote && !isDbEmpty

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val apiValues = api.getListings()
                csvParser.parse(apiValues.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading data"))
                null
            }

            remoteListing.let { list ->
                dao.clearCompanyListings()
                list?.let {
                    dao.insertCompanyListings(
                        it.map {
                            it.toCompanyListingEntity()
                        }
                    )
                }

            }

            emit(Resource.Success(dao.searchCompanyListing("").map {
                it.toCompanyListing()
            }))
            emit(Resource.Loading(false))


        }


    }


}