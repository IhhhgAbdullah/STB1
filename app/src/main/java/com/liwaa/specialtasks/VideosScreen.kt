package com.liwaa.specialtasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

private const val VID_URL = "https://pktifzeaqnwdonlqudpq.supabase.co"
private const val VID_KEY = "sb_publishable_ETwyx2lkYnhIL8frJwZoww_OKm6pjpl"

data class VideoItem(val title: String, val description: String, val embedId: String)

private fun fetchVideos(): List<VideoItem> {
    val url = URL(
        "$VID_URL/rest/v1/videos?select=title,description,embed_id&published=eq.true&order=created_at.desc"
    )
    val conn = url.openConnection() as HttpURLConnection
    conn.setRequestProperty("apikey", VID_KEY)
    conn.connectTimeout = 10000
    conn.readTimeout = 10000
    try {
        val text = conn.inputStream.bufferedReader().use { it.readText() }
        val arr = JSONArray(text)
        return (0 until arr.length()).map { i ->
            val o = arr.getJSONObject(i)
            VideoItem(
                o.getString("title"),
                o.getString("description"),
                if (o.isNull("embed_id")) "" else o.getString("embed_id")
            )
        }
    } finally {
        conn.disconnect()
    }
}

@Composable
fun VideosScreen() {
    val state by produceState<Result<List<VideoItem>>?>(initialValue = null) {
        value = withContext(Dispatchers.IO) { runCatching { fetchVideos() } }
    }
    var playingId by remember { mutableStateOf<String?>(null) }

    val current = playingId
    if (current != null && current.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "رجوع",
                color = Color(0xFFFFD200),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161616))
                    .clickable { playingId = null }
                    .padding(12.dp)
            )
            VideoPlayerScreen(videoId = current)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val result = state
        when {
            result == null -> Text("جاري التحميل...", color = Color.White, fontSize = 16.sp)
            result.isFailure -> Text("تعذر تحميل الفيديوهات، تحقق من الإنترنت", color = Color.White, fontSize = 16.sp)
            result.getOrThrow().isEmpty() -> Text("لا توجد فيديوهات منشورة حالياً", color = Color.White, fontSize = 16.sp)
            else -> result.getOrThrow().forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF161616))
                        .clickable {
                            if (item.embedId.isNotEmpty()) playingId = item.embedId
                        }
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = item.title, color = Color(0xFFFFD200), fontSize = 20.sp)
                    Text(text = item.description, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
