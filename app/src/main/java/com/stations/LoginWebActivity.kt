package com.stations

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.stations.util.Constants
import kotlinx.android.synthetic.main.activity_webview.*
import okhttp3.HttpUrl
import java.net.URLDecoder

class LoginWebActivity : AppCompatActivity() {

    private val SCOPELIST: List<String> = listOf(
        Constants.SCOPE_IDENTITY_WRITE,
        Constants.SCOPE_IDENTITY_READ_ONLY,
        Constants.SCOPE_LISTENING_READ_ONLY,
        Constants.SCOPE_LISTENING_WRITE,
        Constants.SCOPE_LOCALACTIVATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webview.clearHistory()
        webview.clearFormData()
        webview.clearCache(true)
        val webSettings = webview.getSettings()
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        var url = execute()
        startWebView(url)
    }

    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            super.onPageFinished(view, url)
            Log.e("Url", "Data:$url")
            if (url.startsWith(Constants.REDIRECT_URI)) {
                val uri = Uri.parse(url)
                val receivedState = uri.getQueryParameter("state")
                val error = uri.getQueryParameter("error")
                val message = uri.getQueryParameter("message")

                if (receivedState == (BuildConfig.NPR_APPLICATION_SECRET)) {
                    val code = uri.getQueryParameter("code")
                    var intent = Intent(this@LoginWebActivity, MainActivity::class.java)
                    intent.putExtra("code", code)
                    startActivity(intent)
                    finish()
                } else if (error == "denied") {
                    Toast.makeText(this@LoginWebActivity, message, Toast.LENGTH_LONG).show()
                }
            }
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress.isVisible = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progress.isVisible = false
        }


    }


    private fun execute(): String {
        return HttpUrl.Builder()
            .scheme("https")
            .host(Constants.AUTHORIZATION_API_BASE_URL)
            .addPathSegment(Constants.AUTHORIZE_REQUEST)
            .addQueryParameter(
                Constants.PARAM_CLIENT_ID,
                BuildConfig.NPR_APPLICATION_ID
            )
            .addQueryParameter(Constants.PARAM_REDIRECT_URI, Constants.REDIRECT_URI)
            .addQueryParameter(Constants.PARAM_RESPONSE_TYPE, Constants.RESPONSE_TYPE_CODE)
            .addQueryParameter(Constants.PARAM_SCOPE, SCOPELIST.joinToString(separator = " "))
            .addQueryParameter(
                Constants.PARAM_STATE,
                BuildConfig.NPR_APPLICATION_SECRET
            )
            .build()
            .toString()
            .utf8Decode()
    }

    private fun String.utf8Decode(): String {
        return URLDecoder.decode(this, "UTF-8")
    }

    private fun startWebView(url: String) {
        webview.settings.loadsImagesAutomatically = true
        webview.settings.javaScriptEnabled = true
        webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webview.webViewClient = webViewClient
        webview.loadUrl(url)
    }


}