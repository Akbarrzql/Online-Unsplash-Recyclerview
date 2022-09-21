package com.example.onlineapirecyclerview.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineapirecyclerview.R
import com.example.onlineapirecyclerview.Retrofit.ResultsItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class UnsplashAdapter(val dataUnsplash: List<ResultsItem?>?): RecyclerView.Adapter<UnsplashAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent, false))
    }

    override fun getItemCount(): Int {
        if (dataUnsplash != null) {
            return dataUnsplash!!.size
        }
            return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.altDesc.text = dataUnsplash?.get(position)?.altDescription
        holder.username.text = dataUnsplash?.get(position)?.user?.username

        Glide.with(holder.itemView.context)
            .load(dataUnsplash?.get(position)?.urls?.regular)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgUnsplash)

        Glide.with(holder.itemView.context)
            .load(dataUnsplash?.get(position)?.user?.profileImage?.medium)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgUsername)

        holder.itemView.setOnClickListener {
            // Download and open Image form url Api Unsplash
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Download Gambar")
                .setMessage("Kamu ingin mendownload gambar ini?")
                .setPositiveButton("Download") { dialog, which ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(dataUnsplash?.get(position)?.urls?.regular)
                    holder.itemView.context.startActivity(intent)
                }
                .setNegativeButton("Tidak") { dialog, which ->
                    Toast.makeText(holder.itemView.context, "Membatalkan", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgUnsplash = itemView.findViewById<View>(R.id.img_Unsplash) as ImageView
        val altDesc = itemView.findViewById<View>(R.id.alt_desc) as TextView
        val username = itemView.findViewById<View>(R.id.tv_Username) as TextView
        val imgUsername = itemView.findViewById<View>(R.id.iv_Username) as ImageView
    }
}