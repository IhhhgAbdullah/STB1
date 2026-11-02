package com.liwaa.specialtasks

import androidx.compose.foundation.background
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

private const val MAR_URL = "https://pktifzeaqnwdonlqudpq.supabase.co"
private const val MAR_KEY = "sb_publishable_ETwyx2lkYnhIL8frJwZoww_OKm6pjpl"

data class MartyrItem(val name: String, val biography: String)

private fun fetchMartyrs(): List<MartyrItem> {
    val url = URL(
        "$MAR_URL/rest/v1/martyrs?select=full_name,biography&published=eq.true&order=created_at.desc"
    )
    val conn = url.openConnection() as HttpURLConnection
    conn.setRequestProperty("apikey", MAR_KEY)
    conn.connectTimeout = 10000
    conn.readTimeout = 10000
    try {
        val text = conn.inputStream.bufferedReader().use { it.readText() }
        val arr = JSONArray(text)
        return (0 until arr.length()).map { i ->
            val o = arr.getJSONObject(i)
            MartyrItem(o.getString("full_name"), o.getString("biography"))
        }
    } finally {
        conn.disconnect()
    }
}

@Composable
fun MartyrsScreen() {
    val state by produceState<Result<List<MartyrItem>>?>(initialValue = null) {
        value = withContext(Dispatchers.IO) { runCatching { fetchMartyrs() } }
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
            result.isFailure -> Text("تعذر تحميل البيانات، تحقق من الإنترنت", color = Color.White, fontSize = 16.sp)
            result.getOrThrow().isEmpty() -> Text("لا توجد سير منشورة حالياً", color = Color.White, fontSize = 16.sp)
            else -> result.getOrThrow().forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF161616))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = item.name, color = Color(0xFFFFD200), fontSize = 20.sp)
                    Text(text = item.biography, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
