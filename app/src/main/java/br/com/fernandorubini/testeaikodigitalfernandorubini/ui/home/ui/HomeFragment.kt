package br.com.fernandorubini.testeaikodigitalfernandorubini.ui.home.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fernandorubini.testeaikodigitalfernandorubini.model.Lines
import br.com.fernandorubini.testeaikodigitalfernandorubini.service.ApiService
import br.com.fernandorubini.testeaikodigitalfernandorubini.ui.home.ui.adapter.LinhaAdapter
import br.com.fernandorubini.testeaikofernandorubini.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.OlhoVivoApi

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var linhasList: MutableList<Lines> = mutableListOf()
    private lateinit var linhaAdapter: LinhaAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner) {

        }

        linhaAdapter = LinhaAdapter(linhasList)
        binding.rvLines.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLines.setHasFixedSize(true)
        binding.rvLines.adapter = linhaAdapter

        val apiService = ApiService.create()

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchTerm = s.toString().trim()
                if (searchTerm.isNotEmpty()) {
                    searchLinhas(apiService, searchTerm)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        return root
    }


    private fun searchLinhas(apiService: OlhoVivoApi, searchTerm: String) {
        apiService.getLinhas(searchTerm).enqueue(object : Callback<List<Lines>> {
            override fun onResponse(call: Call<List<Lines>>, response: Response<List<Lines>>) {
                if (response.isSuccessful) {
                    val linhas = response.body()
                    if (linhas != null) {
                        linhasList.clear()
                        linhasList.addAll(linhas)
                        linhaAdapter.notifyDataSetChanged()
                    } else {
                        Log.e(LINES, RESPONSE_NULL)
                    }
                } else {
                    Log.e(LINES, "Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Lines>>, t: Throwable) {
                Log.e(LINES, "Falha na requisição: ${t.message}")
            }
        })
    }

    companion object {
        private const val LINES: String = "Linhas"
        private const val RESPONSE_NULL = "Resposta vazia ou nula"
    }
}

