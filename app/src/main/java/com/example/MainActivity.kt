package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.JourneyScreenId
import com.example.model.PrimaryTab
import com.example.model.ScreenStateMode
import com.example.ui.components.JourneyBottomNavigationBar
import com.example.ui.components.JourneyScreenStateOverlay
import com.example.ui.components.tr
import com.example.ui.screens.AiCameraScreen
import com.example.ui.screens.AiChatScreen
import com.example.ui.screens.AiProcessingScreen
import com.example.ui.screens.AiStoryPreviewScreen
import com.example.ui.screens.ArchitectureHandoffScreen
import com.example.ui.screens.CollectionDetailsScreen
import com.example.ui.screens.ConversationHistoryScreen
import com.example.ui.screens.CreateDailyMomentScreen
import com.example.ui.screens.CreateTripScreen
import com.example.ui.screens.DesignSystemTokensScreen
import com.example.ui.screens.DiscoveryCollectionScreen
import com.example.ui.screens.DiscoveryDetailsScreen
import com.example.ui.screens.DiscoveryFeedScreen
import com.example.ui.screens.EmailVerificationScreen
import com.example.ui.screens.ExploreMapScreen
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.GlobalSearchScreen
import com.example.ui.screens.HomeDashboardScreen
import com.example.ui.screens.ImagePreviewScreen
import com.example.ui.screens.JournalHomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MemoryDetailScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OnboardingSlidesScreen
import com.example.ui.screens.PermissionIntroductionScreen
import com.example.ui.screens.PlaceDetailsScreen
import com.example.ui.screens.PrivacyAndDataScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RecognitionResultScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SavedPlacesScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SubscriptionScreen
import com.example.ui.screens.TripDetailScreen
import com.example.ui.screens.TripTimelineScreen
import com.example.ui.screens.VideoCreatorScreen
import com.example.ui.screens.VideoPreviewExportScreen
import com.example.ui.screens.VoiceAssistantScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.JourneyAITheme
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState
import com.example.viewmodel.JourneyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: JourneyViewModel = viewModel()
            JourneyAppRoot(viewModel = viewModel)
        }
    }
}

@Composable
fun JourneyAppRoot(viewModel: JourneyViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    BackHandler(
        enabled = uiState.isQuickProfileOpen ||
            uiState.backStack.isNotEmpty() ||
            uiState.currentScreen != JourneyScreenId.B1_HOME_DASHBOARD
    ) {
        when {
            uiState.isQuickProfileOpen -> viewModel.closeQuickProfile()
            !viewModel.navigateBack() -> viewModel.navigateTo(JourneyScreenId.B1_HOME_DASHBOARD, clearBackStack = true)
        }
    }

    JourneyAITheme(
        darkTheme = uiState.isDarkTheme,
        fontScaleStep = uiState.fontScaleStep
    ) {
        val colors = JourneyTheme.colors

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primaryBackground)
        ) {
            val isWideScreen = maxWidth >= 600.dp
            val showPrimaryNavigation = uiState.currentScreen.showBottomBar

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = colors.primaryBackground,
                contentWindowInsets = WindowInsets.safeDrawing,
                topBar = {
                    if (showPrimaryNavigation) {
                        com.example.ui.components.JourneyAppHeaderBar(
                            uiState = uiState,
                            onToggleQuickProfile = { viewModel.toggleQuickProfile() },
                            onToggleTheme = { viewModel.toggleTheme() },
                            onOpenAiCamera = { viewModel.navigateTo(JourneyScreenId.D1_AI_CAMERA) }
                        )
                    }
                },
                bottomBar = {
                    if (showPrimaryNavigation && !isWideScreen) {
                        JourneyBottomNavigationBar(
                            currentScreen = uiState.currentScreen,
                            language = uiState.language,
                            onTabSelected = { viewModel.selectPrimaryTab(it) }
                        )
                    }
                }
            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (showPrimaryNavigation && isWideScreen) {
                        NavigationRail(
                            containerColor = colors.secondaryBackground,
                            contentColor = colors.primaryText
                        ) {
                            val activeTab = uiState.currentScreen.primaryTab ?: PrimaryTab.HOME
                            PrimaryTab.entries.forEach { tab ->
                                val selected = activeTab == tab
                                val icon = when (tab) {
                                    PrimaryTab.HOME -> Icons.Default.Home
                                    PrimaryTab.EXPLORE -> Icons.Default.Explore
                                    PrimaryTab.DISCOVER -> Icons.Default.CameraAlt
                                    PrimaryTab.JOURNAL -> Icons.Default.MenuBook
                                    PrimaryTab.PROFILE -> Icons.Default.Person
                                }
                                NavigationRailItem(
                                    selected = selected,
                                    onClick = { viewModel.selectPrimaryTab(tab) },
                                    icon = {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = tab.labelEn,
                                            tint = if (selected) colors.accent else colors.secondaryText
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = tr(uiState.language, tab.labelEn, tab.labelVi),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(colors.primaryBackground)
                    ) {
                        JourneyScreenStateOverlay(
                            stateMode = uiState.screenStateMode,
                            language = uiState.language,
                            onResetToDefault = { viewModel.setScreenStateMode(ScreenStateMode.DEFAULT) },
                            onNavigateToSubscription = { viewModel.navigateTo(JourneyScreenId.I3_SUBSCRIPTION) },
                            onNavigateToPermissions = { viewModel.navigateTo(JourneyScreenId.A4_PERMISSIONS) }
                        ) {
                            JourneyScreenRouter(
                                uiState = uiState,
                                viewModel = viewModel
                            )
                        }

                        // Dimmed Scrim + Floating Quick Profile & Settings Card Popover (VitaPulse Style)
                        if (uiState.isQuickProfileOpen) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(colors.scrim.copy(alpha = 0.32f))
                                    .clickable { viewModel.closeQuickProfile() }
                            )
                        }

                        com.example.ui.components.JourneyQuickProfilePopover(
                            uiState = uiState,
                            onDismiss = { viewModel.closeQuickProfile() },
                            onToggleMinimalView = { viewModel.toggleMinimalView() },
                            onToggleVoicePlayback = { viewModel.toggleVoicePlayback() },
                            onToggleTheme = { viewModel.toggleTheme() },
                            onToggleLanguage = { viewModel.toggleLanguage() },
                            onSelectFontScale = { viewModel.setFontScaleStep(it) },
                            onNavigate = { viewModel.navigateTo(it) },
                            modifier = Modifier.align(Alignment.TopCenter)
                        )

                        // Native In-App Toast Banner
                        androidx.compose.animation.AnimatedVisibility(
                            visible = uiState.toastBannerMessage != null,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(JourneySpacing.md)
                        ) {
                            uiState.toastBannerMessage?.let { msg ->
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = colors.elevatedSurface,
                                    border = BorderStroke(1.dp, colors.accent),
                                    modifier = Modifier.clickable { viewModel.showToast(null) }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = colors.accent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = msg,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Dismiss",
                                            tint = colors.secondaryText,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun JourneyScreenRouter(
    uiState: JourneyUiState,
    viewModel: JourneyViewModel
) {
    val onBackAction: () -> Unit = {
        if (!viewModel.navigateBack()) {
            viewModel.navigateTo(JourneyScreenId.B1_HOME_DASHBOARD, clearBackStack = true)
        }
    }

    when (uiState.currentScreen) {
        JourneyScreenId.DS_TOKENS_COMPONENTS -> DesignSystemTokensScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.DS_HANDOFF_ACCEPTANCE -> ArchitectureHandoffScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A1_SPLASH -> SplashScreen(
            uiState = uiState,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A2_WELCOME -> WelcomeScreen(
            uiState = uiState,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A3_ONBOARDING -> OnboardingSlidesScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A4_PERMISSIONS -> PermissionIntroductionScreen(
            uiState = uiState,
            onBack = onBackAction,
            onTogglePermission = { viewModel.togglePermission(it) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A5_LOGIN -> LoginScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A6_REGISTER -> RegisterScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A7_FORGOT_PASSWORD -> ForgotPasswordScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.A8_EMAIL_VERIFY -> EmailVerificationScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.B1_HOME_DASHBOARD -> HomeDashboardScreen(
            uiState = uiState,
            onNavigate = { viewModel.navigateTo(it) },
            onSelectPlace = { id, openDetail -> viewModel.selectPlace(id, openDetail) },
            onToggleSavePlace = { viewModel.toggleSavePlace(it) },
            onSelectCollection = { viewModel.selectCollection(it) },
            onSetFeedFilter = { viewModel.setSocialFeedFilter(it) },
            onToggleLikePost = { viewModel.toggleLikePost(it) },
            onAddComment = { postId, text -> viewModel.addPostComment(postId, text) },
            onDeletePost = { viewModel.deleteMoment(it) },
            onRefreshFeed = { viewModel.refreshFriendsLocketFeed() }
        )

        JourneyScreenId.B2_GLOBAL_SEARCH -> GlobalSearchScreen(
            uiState = uiState,
            onBack = onBackAction,
            onQueryChange = { viewModel.updateSearchQuery(it) },
            onSelectPlace = { id, openDetail -> viewModel.selectPlace(id, openDetail) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.B3_NOTIFICATIONS -> NotificationsScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.C1_EXPLORE_MAP -> ExploreMapScreen(
            uiState = uiState,
            onSelectPlace = { id, openDetail -> viewModel.selectPlace(id, openDetail) },
            onToggleSavePlace = { viewModel.toggleSavePlace(it) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.C2_DISCOVERY_FEED -> DiscoveryFeedScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectPlace = { id, openDetail -> viewModel.selectPlace(id, openDetail) },
            onToggleSavePlace = { viewModel.toggleSavePlace(it) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.C3_PLACE_DETAILS -> PlaceDetailsScreen(
            uiState = uiState,
            onBack = onBackAction,
            onToggleSavePlace = { viewModel.toggleSavePlace(it) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.C4_SAVED_PLACES -> SavedPlacesScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectPlace = { id, openDetail -> viewModel.selectPlace(id, openDetail) },
            onToggleSavePlace = { viewModel.toggleSavePlace(it) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.D1_AI_CAMERA -> AiCameraScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectPlace = { id, openDetail -> viewModel.selectPlace(id, openDetail) },
            onImagePicked = { uriStr -> viewModel.setCapturedImageUri(uriStr) },
            onNavigate = { viewModel.navigateTo(it) },
            onUpdateLiveLocation = { addr -> viewModel.updateLiveLocation(addr) },
            onPublishLocketPost = { caption, placeId, uri, privacy, isVideo, duration, customLocationName, timeWatermark, temperatureTag, stickerTag, spotifyTrack ->
                viewModel.publishLocketPost(
                    caption = caption,
                    placeId = placeId,
                    imageUri = uri,
                    privacyLabel = privacy,
                    isVideo = isVideo,
                    videoDurationSec = duration,
                    customLocationName = customLocationName,
                    timeWatermark = timeWatermark,
                    temperatureTag = temperatureTag,
                    stickerTag = stickerTag,
                    spotifyTrack = spotifyTrack
                )
            }
        )

        JourneyScreenId.D2_IMAGE_PREVIEW -> ImagePreviewScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.D3_AI_PROCESSING -> AiProcessingScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.D4_RECOGNITION_RESULT -> RecognitionResultScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) },
            onSaveToCollection = { viewModel.saveCurrentPlaceToCollection(it) }
        )

        JourneyScreenId.D5_DISCOVERY_DETAILS -> DiscoveryDetailsScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.E1_AI_CHAT -> AiChatScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSendMessage = { viewModel.sendChatMessage(it) },
            onSaveConversation = { viewModel.saveCurrentConversation() },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.E2_VOICE_ASSISTANT -> VoiceAssistantScreen(
            uiState = uiState,
            onBack = onBackAction,
            onTogglePlay = { viewModel.toggleVoicePlayback() },
            onCycleSpeed = { viewModel.cycleVoiceSpeed() },
            onSaveConversation = { viewModel.saveCurrentConversation() },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.E3_CONVERSATION_HISTORY -> ConversationHistoryScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.F1_JOURNAL_HOME -> JournalHomeScreen(
            uiState = uiState,
            onSelectTrip = { viewModel.selectTrip(it, openDetails = true) },
            onNavigate = { viewModel.navigateTo(it) },
            onDeleteMoment = { viewModel.deleteMoment(it) }
        )

        JourneyScreenId.F2_CREATE_TRIP -> CreateTripScreen(
            uiState = uiState,
            onBack = onBackAction,
            onCreateTrip = { title, loc, dates -> viewModel.createNewTrip(title, loc, dates) }
        )

        JourneyScreenId.F3_TRIP_DETAIL -> TripDetailScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectMoment = { viewModel.selectMoment(it, openDetail = true) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.F4_TRIP_TIMELINE -> TripTimelineScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectMoment = { viewModel.selectMoment(it, openDetail = true) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.F5_CREATE_DAILY_MOMENT -> CreateDailyMomentScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSaveMoment = { title, note, loc -> viewModel.addDailyMoment(title, note, loc) }
        )

        JourneyScreenId.F6_MEMORY_DETAIL -> MemoryDetailScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.G1_DISCOVERY_COLLECTION -> DiscoveryCollectionScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectCollection = { viewModel.selectCollection(it) }
        )

        JourneyScreenId.G2_COLLECTION_DETAILS -> CollectionDetailsScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectPlaceForDiscoveryDetail = { placeId ->
                viewModel.selectPlace(placeId, openDetails = false)
                viewModel.navigateTo(JourneyScreenId.D5_DISCOVERY_DETAILS)
            }
        )

        JourneyScreenId.H1_AI_STORY_PREVIEW -> AiStoryPreviewScreen(
            uiState = uiState,
            onBack = onBackAction,
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.H2_VIDEO_CREATOR -> VideoCreatorScreen(
            uiState = uiState,
            onBack = onBackAction,
            onUpdateSettings = { aspect, music -> viewModel.updateVideoSettings(aspect, music) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.H3_VIDEO_PREVIEW_EXPORT -> VideoPreviewExportScreen(
            uiState = uiState,
            onBack = onBackAction,
            onShowExportToast = { viewModel.showToast(it) },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.I1_PROFILE -> ProfileScreen(
            uiState = uiState,
            onNavigate = { viewModel.navigateTo(it) },
            onUpdateProfile = { name, handle, bio, city, email, avatarUri ->
                viewModel.updatePersonalProfile(name, handle, bio, city, email, avatarUri)
            }
        )

        JourneyScreenId.I2_SETTINGS -> SettingsScreen(
            uiState = uiState,
            onBack = onBackAction,
            onToggleTheme = { viewModel.toggleTheme() },
            onToggleLanguage = { viewModel.toggleLanguage() },
            onCycleVoiceSpeed = { viewModel.cycleVoiceSpeed() },
            onNavigate = { viewModel.navigateTo(it) }
        )

        JourneyScreenId.I3_SUBSCRIPTION -> SubscriptionScreen(
            uiState = uiState,
            onBack = onBackAction,
            onSelectPlan = { viewModel.selectSubscriptionPlan(it) }
        )

        JourneyScreenId.I4_PRIVACY_AND_DATA -> PrivacyAndDataScreen(
            uiState = uiState,
            onBack = onBackAction,
            onTogglePermission = { viewModel.togglePermission(it) },
            onShowToast = { viewModel.showToast(it) }
        )
    }
}
