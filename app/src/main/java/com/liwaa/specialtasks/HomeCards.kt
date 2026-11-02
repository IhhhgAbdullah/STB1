package com.liwaa.specialtasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val openable = setOf(
    "الأخبار اليومية",
    "شريط الأخبار",
    "إنجازات اللواء",
    "أرشيف الصور",
    "أرشيف الفيديوهات",
    "صور وسيرة الشهداء",
    "البيانات الرسمية",
    "عن اللواء"
)

@Composable
fun HomeCards() {
    var openedSection by remember { mutableStateOf<String?>(null) }

    val opened = openedSection
    if (opened != null && opened in openable) {
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
                    .clickable { openedSection = null }
                    .padding(12.dp)
            )
            when (opened) {
                "الأخبار اليومية" -> NewsScreen()
                "شريط الأخبار" -> NewsTickerScreen()
                "إنجازات اللواء" -> AchievementsScreen()
                "أرشيف الصور" -> PhotosScreen()
                "أرشيف الفيديوهات" -> VideosScreen()
                "صور وسيرة الشهداء" -> MartyrsScreen()
                "البيانات الرسمية" -> OfficialDataScreen()
                "عن اللواء" -> AboutScreen()
                else -> {}
            }
        }
    }
}
