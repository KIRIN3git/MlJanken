package kirin3.jp.mljanken.webview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import kirin3.jp.mljanken.R

class WebViewActivity : AppCompatActivity() {


    companion object {

        val INTENT_INPUT_URL = "INPUT_URL"
        internal var url: String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        var webView: WebView = findViewById(R.id.webview)
        val extras = intent.extras
        url = extras!!.getString(INTENT_INPUT_URL)

        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        var webView: WebView = findViewById(R.id.webview)

        // 端末の戻るボタンでブラウザバック
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}