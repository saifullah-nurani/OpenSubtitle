package io.github.saifullah.nurani.opensubtitle.demo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import io.github.saifullah.nurani.opensubtitle.OpenSubtitle
import io.github.saifullah.nurani.opensubtitle.SearchOnly
import io.github.saifullah.nurani.opensubtitle.buildOpenSubtitleUrl
import io.github.saifullah.nurani.opensubtitle.data.Subtitle
import io.github.saifullah.nurani.opensubtitle.data.SubtitleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val openSubtitle = OpenSubtitle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return query?.let {
                    loadSubtitles(it)
                    true
                } ?: false
            }
        })
    }


    private fun loadSubtitles(title: String) {
        lifecycleScope.launch {
            val url = buildOpenSubtitleUrl {
                setTitle(title)
                setSearchOnly(SearchOnly.Movies)
            }
            val subtitle = searchSubtitle(url, 1)
            if (subtitle.isSuccess) {
                subtitle.getOrNull()!!.subtitles.joinToString("\n\n\n") {
                    "Id : ${it.id} \n " + "Title : ${it.title} \n " + "Release Year : ${it.releaseYear}\n" + "SubFormat : ${it.subFormat}\n" + "Language : ${it.subLanguage}"
                }.let {
                    findViewById<TextView>(R.id.subtitles_view).text = it
                }
            }
        }
    }

    private suspend fun searchSubtitle(url: String, page: Int): Result<SubtitleData> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(openSubtitle.search(url, page))
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
    }

    private suspend fun getDownloadUrl(id: Long): String? {
        return withContext(Dispatchers.IO) {
            openSubtitle.getDirectLink(id)
        }
    }
}