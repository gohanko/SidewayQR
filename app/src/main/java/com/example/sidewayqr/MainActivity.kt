package com.example.sidewayqr

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.sidewayqr.data.datastore.CookieRepository
import com.example.sidewayqr.network.SidewayQRAPIService
import com.example.sidewayqr.ui.screens.ScanHistoryScreen
import com.example.sidewayqr.ui.theme.SidewayQRTheme
import com.example.sidewayqr.viewmodel.AuthenticationViewModel
import com.example.sidewayqr.viewmodel.EventOperationViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cookies")

class MainActivity : ComponentActivity() {
    private lateinit var cookieRepository: CookieRepository
    private lateinit var sidewayQRAPIService: SidewayQRAPIService
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var eventOperationViewModel: EventOperationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cookieRepository = CookieRepository(dataStore)
        sidewayQRAPIService = SidewayQRAPIService.getInstance(cookieRepository)
        authenticationViewModel = AuthenticationViewModel(sidewayQRAPIService)
        eventOperationViewModel = EventOperationViewModel(sidewayQRAPIService)

        authenticationViewModel.login(
            email = "student1@email.com",
            password = "student1"
        )

        enableEdgeToEdge()
        setContent {
            SidewayQRTheme {
                ScanHistoryScreen(sidewayQRAPIService, eventOperationViewModel)
            }
        }
    }
}
