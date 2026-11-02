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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val openable = setOf(
    "الأخبار اليومية", "إنجازات اللواء", "البيانات الرسمية",
    "أرشيف الصور", "عن اللواء", "صور وسيرة الشهداء", "أرشيف الفيديوهات"
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
                    .padding(12.
