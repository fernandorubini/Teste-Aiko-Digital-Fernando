package br.com.fernandorubini.testeaikodigitalfernandorubini.ui.home.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fernandorubini.testeaikodigitalfernandorubini.model.Lines
import br.com.fernandorubini.testeaikofernandorubini.R

class LinhaAdapter(private val linhas: List<Lines>) :
    RecyclerView.Adapter<LinhaAdapter.LinhaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lines_adpater, parent, false)
        return LinhaViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinhaViewHolder, position: Int) {
        val linha = linhas[position]
        holder.linhaLetreironumericoTextView.text = "Letreiro: ${linha.lt}"
        holder.linhaCodigoTextView.text = linha.tl.toString()
        holder.terminalPrincipalTextView.text = "Terminal Principal ${linha.tp}"
        holder.terminalSecundarioTextView.text = "Terminal Secundario ${linha.ts}"
    }

    override fun getItemCount(): Int {
        return linhas.size
    }

    class LinhaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linhaCodigoTextView: TextView = itemView.findViewById(R.id.textCodigoLinha)
        val linhaLetreironumericoTextView: TextView = itemView.findViewById(R.id.textLetreiroNumerico)
        val terminalPrincipalTextView: TextView = itemView.findViewById(R.id.textLetreiroPrincipal)
        val terminalSecundarioTextView: TextView = itemView.findViewById(R.id.textLetreiroSecundario)
    }
}
