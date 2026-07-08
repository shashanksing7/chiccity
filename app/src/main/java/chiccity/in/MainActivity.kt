package chiccity.`in`

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import chiccity.`in`.appWebView.MainScreen
import chiccity.`in`.appWebView.WebViewManager
import chiccity.`in`.ui.theme.Chiccity2Theme
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import java.security.MessageDigest

class MainActivity : ComponentActivity() {
    private lateinit var logger: AppEventsLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplash = true
        super.onCreate(savedInstanceState)

        // Initialize Facebook Logger
        logger = AppEventsLogger.newLogger(this)

        // Generate Key Hash for Facebook
        generateKeyHash()

        // Initialize WebViewManager to preload and cache the homepage
        WebViewManager.initialize(this)

        splashScreen.setKeepOnScreenCondition {
            keepSplash
        }

        enableEdgeToEdge()

        setContent {
            Chiccity2Theme {
                MainScreen()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplash = false
        }, 100)
    }

    fun logSentFriendRequestEvent() {
        logger.logEvent("sentFriendRequest")
    }

    private fun generateKeyHash() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val info = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
                val signingInfo = info.signingInfo
                signingInfo?.apkContentsSigners?.forEach { signature ->
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                    Log.d("KeyHash", keyHash)
                }
            } else {
                @Suppress("DEPRECATION")
                val info = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )
                @Suppress("DEPRECATION")
                info.signatures?.forEach { signature ->
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                    Log.d("KeyHash", keyHash)
                }
            }
        } catch (e: Exception) {
            Log.e("KeyHash", "Error generating key hash", e)
        }
    }
}
