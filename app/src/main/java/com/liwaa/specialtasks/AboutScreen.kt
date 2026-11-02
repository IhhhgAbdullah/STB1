package com.liwaa.specialtasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

private const val ABT_URL = "https://pktifzeaqnwdonlqudpq.supabase.co"
private const val ABT_KEY = "sb_publishable_ETwyx2lkYnhIL8frJwZoww_OKm6pjpl"

private fun fetchAbout(): String {
    val url = URL("$ABT_URL/rest/v1/about_content?select=content&id=eq.1")
    val conn = url.openConnection() as HttpURLConnection
    conn.setRequestProperty("apikey", ABT_KEY)
    conn.connectTimeout = 10000
    conn.readTimeout = 10000
    try {
        val text = conn.inputStream.bufferedReader().use { it.readText() }
        val arr = JSONArray(text)
        return if (arr.length() > 0) arr.getJSONObject(0).getString("content") else ""
    } finally {
        conn.disconnect()
    }
}

@Composable
fun AboutScreen() {
    val state by produceState<Result<String>?>(initialValue = null) {
        value = withContext(Dispatchers.IO) { runCatching { fetchAbout() } }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val result = state
        when {
            result == null -> Text("جاري التحميل...", color = Color.White, fontSize = 16.sp)
            result.isFailure -> Text("تعذر تحميل المعلومات، تحقق من الإنترنت", color = Color.White, fontSize = 16.sp)
            result.getOrThrow().isEmpty() -> Text("لا توجد معلومات حالياً", color = Color.White, fontSize = 16.sp)
            else -> Text(text = result.getOrThrow(), color = Color.White, fontSize = 16.sp)
        }
    }
}
