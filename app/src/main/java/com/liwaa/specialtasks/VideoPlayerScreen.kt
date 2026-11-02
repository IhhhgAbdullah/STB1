package com.liwaa.specialtasks

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoPlayerScreen(videoId: String) {
    val html = """
        <html>
        <body style="margin:0;background:#000;">
        <iframe width="100%" height="100%"
            src="https://www.youtube.com/embed/$videoId?playsinline=1"
            frameborder="0"
            allow="autoplay; encrypted-media; picture-in-picture"
            allowfullscreen>
        </iframe>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                loadDataWithBaseURL(
                    "https://www.youtube.com",
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    )
}
