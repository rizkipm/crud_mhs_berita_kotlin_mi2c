package com.rizki.crud_mhs_berita_mi2a.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizki.crud_mhs_berita_mi2a.R
import com.rizki.crud_mhs_berita_mi2a.model.ModelBerita



class BeritaAdapter (
    private val onClick: (ModelBerita) -> Unit

): ListAdapter<ModelBerita, BeritaAdapter.BeritaViewHolder>(BeritaCallBack){
    class BeritaViewHolder(itemview: View, val onClick: (ModelBerita) -> Unit) :
        RecyclerView.ViewHolder(itemview) {

        private val imgBerita: ImageView = itemview.findViewById(R.id.imgBerita)
        private val judulBerita: TextView = itemview.findViewById(R.id.judulBerita)
        private val tglBerita: TextView = itemview.findViewById(R.id.tglBerita)


        //cek produk yg saat ini
        private var currentBerita: ModelBerita? = null

        init {
            itemview.setOnClickListener() {
                currentBerita?.let {
                    onClick(it)
                }
            }
        }

        fun bind(berita: ModelBerita){
            currentBerita = berita
            //set ke widget
            judulBerita.text = berita.judul
            tglBerita.text = berita.tgl_berita

            //untuk menampilkan gambar pada widget (fetching gambar dg glide)
            Glide.with(itemView).load("http://10.208.104.237:8080/beritaDb/gambar_berita/" +
                    berita.gambar_berita).centerCrop()
                .into(imgBerita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_berita,
            parent, false
        )
        return BeritaViewHolder(view, onClick)

    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val produk = getItem(position)
        holder.bind(produk)

    }
}

object BeritaCallBack : DiffUtil.ItemCallback<ModelBerita>() {
    override fun areItemsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem.id == newItem.id
    }
}
