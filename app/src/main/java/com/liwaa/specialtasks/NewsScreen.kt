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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NewsItem(val title: String, val body: String)

@Composable
fun NewsScreen() {
    val news = listOf(
        NewsItem("خبر تجريبي الأول", "هذا نص تجريبي لخبر من الأخبار اليومية."),
        NewsItem("خبر تجريبي الثاني", "هذا نص تجريبي آخر، وسيتم استبداله ببيانات حقيقية لاحقاً.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        news.forEach { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161616))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = item.title, color = Color(0xFFFFD200), fontSize = 20.sp)
                Text(text = item.body, color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
