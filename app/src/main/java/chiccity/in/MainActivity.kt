package chiccity.`in`

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import chiccity.`in`.appWebView.MainScreen
import chiccity.`in`.ui.theme.Chiccity2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplash = true
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            keepSplash
        }

        enableEdgeToEdge()

        setContent {
            Chiccity2Theme {
//                Surface(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
//                    MyWebView("https://chiccity.in/my-account/")
//                }
                MainScreen()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplash = false
        }, 100)
    }
}
