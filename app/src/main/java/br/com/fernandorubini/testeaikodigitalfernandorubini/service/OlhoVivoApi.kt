package util

import br.com.fernandorubini.testeaikodigitalfernandorubini.model.Lines
import br.com.fernandorubini.testeaikofernandorubini.model.Previsao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OlhoVivoApi {


    @GET("Linha/Buscar")
    fun getLinhas(@Query("termosBusca") termosBusca: String): Call<List<Lines>>


    @GET("Previsao/Linha")
    fun getPrevisaoPorLinha(
        @Query("codigoLinha") codigoLinha: String
    ): Call<Previsao>

}