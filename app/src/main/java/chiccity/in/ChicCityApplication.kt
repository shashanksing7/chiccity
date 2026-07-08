package chiccity.`in`

import android.app.Application
import android.webkit.WebView
import chiccity.`in`.appWebView.WebViewManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class ChicCityApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize WebViewManager and preload a WebView instance
        WebViewManager.initialize(this)
        
        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}
