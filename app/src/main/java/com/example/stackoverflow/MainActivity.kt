package com.example.stackoverflow

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var topSongs = mutableListOf<question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FetchTopSongs().execute()



    }

    private inner class FetchTopSongs : AsyncTask<Void, Void, MutableList<question>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            topSongs =
                urlConnection.getInputStream()?.let {
                    parser.parse(it)
                }
                        as MutableList<question>
            return topSongs
        }
        override fun onPostExecute(result: MutableList<question>?) {
            super.onPostExecute(result)
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topSongs)
            val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.adapter = RecyclerViewAdapter(topSongs)
            rvMain.adapter?.notifyDataSetChanged()

        }
         }


    }

//
//super.onPostExecute(result)
//val adapter = RecyclerViewAdapter( topSongs)
//rvMain.adapter = adapter
//rvMain.layoutManager = LinearLayoutManager( LinearLayoutManager.VERTICAL, false)