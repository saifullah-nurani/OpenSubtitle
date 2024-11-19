package org.opensubtitle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.opensubtitle.exception.SubtitleException

class MainActivity : AppCompatActivity() {

    private val openSubtitle = OpenSubtitle(OpenSubtitle.DEFAULT_USER_AGENT)
    private var url: String? = null
    private var currentPage: Int = 1
    private var totalPages: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener {
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    currentPage = 1
//                    url = OpenSubtitle.UrlBuilder(it).build()
//                    searchSubtitle(url!!, currentPage)
//                    return true
//                }
//                return false
//            }
//        })
    }

    private fun searchSubtitle(url: String, page: Int) {
        lifecycleScope.launch {
            openSubtitle.search(url, page, response = object : OpenSubtitle.Response {
                override fun onFailure(error: SubtitleException) {
                    Log.e("MainActivity", "onFailure: ${error.message}")
                }

                override fun onSuccess(data: Result) {
                    currentPage = data.page
                    totalPages = data.totalPage
                    data.subtitles.forEach {
                        it.id
                    }
                }
            })
        }
    }

    private fun getDownloadUrl(id: Long) {
        lifecycleScope.launch {
            openSubtitle.getDownloadUrl(id)
        }
    }
}