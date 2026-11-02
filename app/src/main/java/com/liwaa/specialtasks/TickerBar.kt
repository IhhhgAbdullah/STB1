package com.liwaa.specialtasks

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

private const val TCK_URL = "https://pktifzeaqnwdonlqudpq.supabase.co"
private const val TCK_KEY = "sb_publishable_ETwyx2lkYnhIL8frJwZoww_OKm6pjpl"

private fun fetchTicker(): String {
    val url = URL("$TCK_URL/rest/v1/ticker_items?select=text&active=eq.true&order=sort_order.asc")
    val conn = url.openConnection() as HttpURLConnection
    conn.setRequestProperty("apikey", TCK_KEY)
    conn.connectTimeout = 10000
    conn.readTimeout = 10000
    try {
        val text = conn.inputStream.bufferedReader().use { it.readText() }
        val arr = JSONArray(text)
        return (0 until arr.length())
            .joinToString("     ●     ") { arr.getJSONObject(it).getString("text") }
    } finally {
        conn.disconnect()
    }
}

@Composable
fun TickerBar(modifier: Modifier = Modifier) {
    val state by produceState<String?>(initialValue = null) {
        value = withContext(Dispatchers.IO) {
            runCatching { fetchTicker() }.getOrDefault("")
        }
    }

    val text = state
    if (!text.isNullOrEmpty()) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip,
            modifier = modifier
                .background(Color(0xFFC62828))
                .basicMarquee()
                .padding(vertical = 8.dp)
        )
    }
}
