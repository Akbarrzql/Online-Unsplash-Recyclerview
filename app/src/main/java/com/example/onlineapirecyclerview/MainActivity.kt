package com.example.onlineapirecyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineapirecyclerview.Adapter.UnsplashAdapter
import com.example.onlineapirecyclerview.Retrofit.ApiConfig
import com.example.onlineapirecyclerview.Retrofit.ResponseUnsplash
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.setTitle("Unsplash Indonesia")

        val unsplash = findViewById<RecyclerView>(R.id.rv_Unsplash)
        unsplash.layoutManager = LinearLayoutManager(this)
        unsplash.setLayoutManager(LinearLayoutManager(this))

        ApiConfig.getService().getData().enqueue(object : Callback<ResponseUnsplash>{
            override fun onResponse(call: Call<ResponseUnsplash>, response: Response<ResponseUnsplash>) {
                if (response.isSuccessful){
                    val responseUnsplash = response.body()
                    val dataUnsplash = responseUnsplash?.results
                    val unsplashAdapter = UnsplashAdapter(dataUnsplash)
                    unsplash.apply {
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        unsplashAdapter.notifyDataSetChanged()
                        adapter = unsplashAdapter

                    }
                }
            }

            override fun onFailure(call: Call<ResponseUnsplash>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        endlessRv()

    }

    fun endlessRv(){
        val unsplash = findViewById<RecyclerView>(R.id.rv_Unsplash)
        unsplash.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = unsplash.layoutManager!!.childCount
                    totalItemCount = unsplash.layoutManager!!.itemCount
                    pastVisiblesItems = (unsplash.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            ApiConfig.getService().getData().enqueue(object : Callback<ResponseUnsplash>{
                                override fun onResponse(call: Call<ResponseUnsplash>, response: Response<ResponseUnsplash>) {
                                    if (response.isSuccessful){
                                        val responseUnsplash = response.body()
                                        val dataUnsplash = responseUnsplash?.results
                                        val unsplashAdapter = UnsplashAdapter(dataUnsplash)
                                        unsplash.apply {
                                            layoutManager = LinearLayoutManager(context)
                                            setHasFixedSize(true)
                                            unsplashAdapter.notifyDataSetChanged()
                                            adapter = unsplashAdapter
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<ResponseUnsplash>, t: Throwable) {
                                    Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                                }

                            })
                        }
                    }
                }
            }
        })

    }

}