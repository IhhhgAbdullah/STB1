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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeCards() {
    var openedSection by remember { mutableStateOf<String?>(null) }

    if (openedSection == "الأخبار اليومية") {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "رجوع",
                color = Color(0xFFFFD200),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161616))
                    .padding(12.dp)
                    .let { it }
            )
            NewsScreen()
        }
        return
    }

    val sections = listOf(
        "الأخبار اليومية",
        "شريط الأخبار",
        "إنجازات اللواء",
        "أرشيف الصور",
        "أرشيف الفيديوهات",
        "صور وسيرة الشهداء",
        "البيانات الرسمية",
        "عن اللواء"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sections.forEach { title ->
            Text(
                text = title,
                color = Color(0xFFFFD200),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161616))
                    .padding(20.dp)
                    .let { base ->
                        base
                    }
            )
        }
    }
}
