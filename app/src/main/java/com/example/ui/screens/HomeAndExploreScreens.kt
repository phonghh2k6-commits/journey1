package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.R
import com.example.model.JourneyScreenId
import com.example.ui.components.JourneyBadgePill
import com.example.ui.components.JourneyButton
import com.example.ui.components.JourneyButtonVariant
import com.example.ui.components.JourneyCompactLandmarkRowCard
import com.example.ui.components.JourneyEditorialCard
import com.example.ui.components.JourneyFilterChip
import com.example.ui.components.JourneyGhostButton
import com.example.ui.components.JourneyPrimaryButton
import com.example.ui.components.JourneySearchInputBar
import com.example.ui.components.JourneySecondaryButton
import com.example.ui.components.JourneySectionHeader
import com.example.ui.components.JourneyStatPillCard
import com.example.ui.components.JourneyTextField
import com.example.ui.components.JourneyTopBar
import com.example.ui.components.tr
import com.example.ui.theme.JourneyElevation
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState

// ============================================================================
// PHASE 2 — SCREEN B1: HOME DASHBOARD (TRANG CHỦ: TIN BẠN BÈ & BÀI ĐĂNG LOCKET)
// ============================================================================
@Composable
fun HomeDashboardScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onSelectCollection: (String) -> Unit,
    onSetFeedFilter: (String) -> Unit = {},
    onToggleLikePost: (String) -> Unit = {},
    onAddComment: (String, String) -> Unit = { _, _ -> },
    onDeletePost: (String) -> Unit = {},
    onRefreshFeed: () -> Unit = {}
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val coroutineScope = rememberCoroutineScope()
    val feedScrollState = rememberScrollState()
    var isRefreshingFeed by remember { mutableStateOf(false) }
    var pullDistancePx by remember { mutableFloatStateOf(0f) }

    val triggerFeedRefresh: () -> Unit = {
        if (!isRefreshingFeed) {
            isRefreshingFeed = true
            coroutineScope.launch {
                delay(550L)
                onRefreshFeed()
                isRefreshingFeed = false
                pullDistancePx = 0f
            }
        }
    }

    val pullToRefreshConnection = remember(feedScrollState, isRefreshingFeed) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0f && pullDistancePx > 0f) {
                    val consumed = available.y.coerceAtLeast(-pullDistancePx)
                    pullDistancePx = (pullDistancePx + consumed).coerceAtLeast(0f)
                    return Offset(0f, consumed)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (source == NestedScrollSource.UserInput && available.y > 0f && feedScrollState.value == 0 && !isRefreshingFeed) {
                    pullDistancePx = (pullDistancePx + available.y * 0.55f).coerceAtMost(220f)
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (pullDistancePx >= 110f && !isRefreshingFeed) {
                    triggerFeedRefresh()
                } else if (!isRefreshingFeed) {
                    pullDistancePx = 0f
                }
                return Velocity.Zero
            }
        }
    }

    var activeStoryId by remember { mutableStateOf<String?>(null) }
    var commentingPostId by remember { mutableStateOf<String?>(null) }
    var commentDraft by remember { mutableStateOf("") }

    // Friends, Invite Link & Direct Messaging State
    val cleanUserHandle = uiState.userHandle.replace("@", "").trim().ifBlank { "minh.journey" }
    val myInviteLink = "https://journey-ai.vn/?invite=$cleanUserHandle"
    var inviteCopiedFeedback by remember { mutableStateOf(false) }
    var showFriendsAndChatHub by remember { mutableStateOf(false) }
    var friendsHubTab by remember { mutableStateOf("FRIENDS") } // FRIENDS | REQUESTS | CHAT
    var addFriendLinkOrHandle by remember { mutableStateOf("") }
    var socialStatusBanner by remember { mutableStateOf<String?>(null) }

    var journeyFriends by remember {
        mutableStateOf(emptyList<Triple<String, String, String>>())
    }
    var pendingFriendRequests by remember {
        mutableStateOf(emptyList<Triple<String, String, String>>())
    }
    var activeChatFriend by remember { mutableStateOf("") }
    var directMessageInput by remember { mutableStateOf("") }
    var chatMessagesByFriend by remember {
        mutableStateOf(emptyMap<String, List<String>>())
    }
    var quickReplyDraftByPost by remember { mutableStateOf(mapOf<String, String>()) }

    val filteredPosts = uiState.socialPosts.filter { post ->
        when (uiState.socialFeedFilter) {
            "FRIENDS" -> !post.isMine
            "MINE" -> post.isMine
            else -> true
        }
    }

    val activeStory = uiState.friendStories.find { it.id == activeStoryId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshConnection)
            .verticalScroll(feedScrollState)
            .padding(bottom = 28.dp)
            .testTag("screen_b1_home_dashboard"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pull-to-Refresh Banner at the top of the Home Feed
            if (isRefreshingFeed || pullDistancePx > 14f) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 4.dp)
                        .testTag("pull_to_refresh_banner")
                ) {
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = colors.surface,
                        border = BorderStroke(1.5.dp, colors.accent),
                        shadowElevation = JourneyElevation.card
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "🔄",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                text = when {
                                    isRefreshingFeed -> tr(
                                        lang,
                                        "Reloading latest moments...",
                                        "Đang tải khoảnh khắc mới nhất..."
                                    )
                                    pullDistancePx >= 110f -> tr(
                                        lang,
                                        "Release to reload feed ✨",
                                        "Thả tay để làm mới bảng tin ✨"
                                    )
                                    else -> tr(
                                        lang,
                                        "Pull down to refresh feed...",
                                        "Kéo xuống để làm mới bảng tin..."
                                    )
                                },
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.primaryText
                            )
                        }
                    }
                }
            }

            // 0. PREMIUM CINEMATIC HERO, GREETING, SEARCH & 4 QUICK ACTIONS
            val heroPlace = uiState.places.firstOrNull() ?: uiState.selectedPlace
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = tr(lang, "Hello, ${uiState.userName}!", "Xin chào, ${uiState.userName}!"),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText
                )

                // Cinematic Search Bar with Scan Icon
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = colors.surface.copy(alpha = 0.9f),
                    border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(JourneyScreenId.B2_GLOBAL_SEARCH) }
                        .testTag("home_cinematic_search_bar")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = colors.accent,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = tr(
                                    lang,
                                    "Search places, artifacts, stories...",
                                    "Tìm kiếm địa điểm, vật thể, câu chuyện..."
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.secondaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Quét AI",
                            tint = colors.accent,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onNavigate(JourneyScreenId.D1_AI_CAMERA) }
                        )
                    }
                }

                // Hero Card — Vịnh Hạ Long
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.xl),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.45f)),
                    shadowElevation = JourneyElevation.card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                        .clip(RoundedCornerShape(JourneyRadius.xl))
                        .clickable {
                            onSelectPlace(heroPlace.id, true)
                        }
                        .testTag("home_hero_card")
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = heroPlace.imageRes),
                            contentDescription = heroPlace.nameVi,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0x220B1017),
                                            Color(0x770B1017),
                                            Color(0xEB0B1017)
                                        )
                                    )
                                )
                        )

                        // Top-right Weather Pill
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = Color(0xAA0F172A),
                            border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.55f)),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(14.dp)
                        ) {
                            Text(
                                text = "☀️ 28°C",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = JourneyPalette.Ivory,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        // Bottom Content + CTA Button + Dots
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Text(
                                text = tr(lang, heroPlace.nameEn, heroPlace.nameVi),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = JourneyPalette.Ivory
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tr(
                                    lang,
                                    "World Natural Wonder of Vietnam",
                                    "Kỳ quan thiên nhiên thế giới của Việt Nam"
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = JourneyPalette.Ivory.copy(alpha = 0.9f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = JourneyPalette.SandGold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(JourneyRadius.pill))
                                        .clickable { onNavigate(JourneyScreenId.C1_EXPLORE_MAP) }
                                ) {
                                    Text(
                                        text = tr(lang, "➤ Explore Now →", "➤ Khám phá ngay →"),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = JourneyPalette.Midnight,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    repeat(5) { idx ->
                                        Box(
                                            modifier = Modifier
                                                .size(if (idx == 1) 8.dp else 6.dp)
                                                .background(
                                                    if (idx == 1) JourneyPalette.SandGold else Color.White.copy(alpha = 0.45f),
                                                    CircleShape
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 4 Circular Quick Actions Row (Quét khám phá | Hỏi AI | Khám phá quanh đây | Nhật ký)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Top
                ) {
                    listOf(
                        Triple(Icons.Default.CameraAlt, tr(lang, "AI Scan", "Quét khám phá"), JourneyScreenId.D1_AI_CAMERA),
                        Triple(Icons.Default.AutoAwesome, tr(lang, "Ask AI", "Hỏi AI"), JourneyScreenId.E1_AI_CHAT),
                        Triple(Icons.Default.LocationOn, tr(lang, "Nearby", "Khám phá quanh đây"), JourneyScreenId.C1_EXPLORE_MAP),
                        Triple(Icons.Default.Bookmark, tr(lang, "Journal", "Nhật ký"), JourneyScreenId.F1_JOURNAL_HOME)
                    ).forEach { (qIcon, qLabel, targetScreen) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(76.dp)
                                .clickable { onNavigate(targetScreen) }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(CircleShape)
                                    .background(colors.surface)
                                    .border(1.5.dp, colors.accent.copy(alpha = 0.6f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = qIcon,
                                    contentDescription = qLabel,
                                    tint = colors.accent,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = qLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.primaryText,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                // Section: Khám phá gần bạn (Horizontal Place Cards)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tr(lang, "Explore Near You", "Khám phá gần bạn"),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colors.primaryText
                    )
                    Text(
                        text = tr(lang, "See all >", "Xem tất cả >"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent,
                        modifier = Modifier.clickable { onNavigate(JourneyScreenId.C1_EXPLORE_MAP) }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.places.forEach { place ->
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = colors.surface,
                            border = BorderStroke(1.dp, colors.border),
                            modifier = Modifier
                                .width(165.dp)
                                .height(145.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable { onSelectPlace(place.id, true) }
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = place.imageRes),
                                    contentDescription = place.nameVi,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(Color.Transparent, Color(0xDD0B1017))
                                            )
                                        )
                                )
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "📍 ${tr(lang, place.nameEn, place.nameVi)}",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${place.distanceKm} · ⭐ ${place.rating}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = JourneyPalette.Ivory.copy(alpha = 0.85f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 1. Clean Stories Strip
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tr(lang, "Moments", "Khoảnh khắc"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.primaryText
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = if (showFriendsAndChatHub) colors.accent else colors.surface,
                            border = BorderStroke(1.dp, if (showFriendsAndChatHub) colors.accent else colors.border),
                            modifier = Modifier
                                .clip(RoundedCornerShape(JourneyRadius.pill))
                                .clickable { showFriendsAndChatHub = !showFriendsAndChatHub }
                                .testTag("btn_open_friends_hub")
                        ) {
                            Text(
                                text = tr(lang, "👥 Friends (${journeyFriends.size})", "👥 Bạn bè (${journeyFriends.size})"),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (showFriendsAndChatHub) colors.onAccent else colors.primaryText,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                        Text(
                            text = tr(lang, "+ Snap Photo", "+ Chụp mới"),
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.accent,
                            modifier = Modifier.clickable { onNavigate(JourneyScreenId.D1_AI_CAMERA) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Add New Moment item
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(64.dp)
                            .clickable { onNavigate(JourneyScreenId.D1_AI_CAMERA) }
                            .testTag("story_add_locket_btn")
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(colors.surface)
                                .border(2.dp, colors.accent, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Chụp mới",
                                tint = colors.accent,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tr(lang, "Camera", "Máy ảnh"),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Friends' Stories
                    uiState.friendStories.forEach { story ->
                        val isSelectedStory = story.id == activeStoryId
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(64.dp)
                                .clickable {
                                    activeStoryId = if (isSelectedStory) null else story.id
                                }
                                .testTag("story_item_${story.id}")
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (isSelectedStory) 3.dp else 2.dp,
                                        color = if (isSelectedStory) colors.sage else colors.accent,
                                        shape = CircleShape
                                    )
                                    .padding(3.dp)
                                    .clip(CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = story.photoRes),
                                    contentDescription = story.authorName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = story.authorName,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.primaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Story Viewer Card (when a friend's story is tapped)
            if (activeStory != null) {
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.xl),
                    color = colors.surface,
                    border = BorderStroke(1.5.dp, colors.accent),
                    shadowElevation = JourneyElevation.card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .background(colors.accentContainer, CircleShape)
                                ) {
                                    Text(
                                        text = activeStory.avatarInitials,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = colors.accent
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = activeStory.authorName,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = colors.primaryText
                                    )
                                    Text(
                                        text = if (activeStory.placeName.isNotBlank()) "📍 ${activeStory.placeName} · ${activeStory.timeAgo}" else activeStory.timeAgo,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = colors.secondaryText
                                    )
                                }
                            }
                            IconButton(
                                onClick = { activeStoryId = null },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Đóng tin",
                                    tint = colors.secondaryText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(18.dp))
                        ) {
                            Image(
                                painter = painterResource(id = activeStory.photoRes),
                                contentDescription = activeStory.placeName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Surface(
                                shape = RoundedCornerShape(JourneyRadius.pill),
                                color = Color(0xCC0F172A),
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = activeStory.caption,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = JourneyPalette.Ivory,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Expandable Friends, Invite Link & Direct Messaging Card (Hidden by default to keep UI clean!)
            if (showFriendsAndChatHub) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.5f)),
                    shadowElevation = JourneyElevation.card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .testTag("locket_friends_invite_card")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tr(lang, "👥 Friends & Messages", "👥 Kết Bạn & Nhắn Tin"),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = colors.primaryText
                            )
                            Text(
                                text = tr(lang, "✕ Close", "✕ Thu gọn"),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.secondaryText,
                                modifier = Modifier.clickable { showFriendsAndChatHub = false }
                            )
                        }

                        // Invite Link Row (Copy & Share)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = colors.elevatedSurface,
                            border = BorderStroke(1.dp, colors.border),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🔗 $myInviteLink",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.accent,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("my_locket_invite_link_text")
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = colors.accentContainer,
                                    border = BorderStroke(1.dp, colors.accent),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(JourneyRadius.pill))
                                        .clickable {
                                            inviteCopiedFeedback = true
                                            socialStatusBanner = "🔗 Đã sao chép link kết bạn: $myInviteLink"
                                        }
                                        .testTag("btn_copy_invite_link")
                                ) {
                                    Text(
                                        text = if (inviteCopiedFeedback) tr(lang, "✓ Copied", "✓ Đã chép")
                                        else tr(lang, "📋 Copy", "📋 Sao chép"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.accent,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        if (socialStatusBanner != null) {
                            Text(
                                text = socialStatusBanner!!,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.sage,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "FRIENDS" to "👥 Bạn bè (${journeyFriends.size})",
                                "REQUESTS" to "➕ Lời mời (${pendingFriendRequests.size})",
                                "CHAT" to "💬 Tin nhắn"
                            ).forEach { (tabKey, tabLabel) ->
                                JourneyFilterChip(
                                    label = tabLabel,
                                    selected = friendsHubTab == tabKey,
                                    onClick = { friendsHubTab = tabKey }
                                )
                            }
                        }

                        // Add friend via Invite Link or Username input
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                JourneyTextField(
                                    value = addFriendLinkOrHandle,
                                    onValueChange = { addFriendLinkOrHandle = it },
                                    label = tr(lang, "Friend link or @id", "Link kết bạn hoặc @id"),
                                    placeholder = "https://journey-ai.vn/?invite=...",
                                    testTag = "input_add_friend_link"
                                )
                            }
                            JourneyButton(
                                text = tr(lang, "+ Add", "+ Kết bạn"),
                                variant = JourneyButtonVariant.PRIMARY,
                                onClick = {
                                    val raw = addFriendLinkOrHandle.trim()
                                    if (raw.isNotEmpty()) {
                                        val cleanCode = raw
                                            .substringAfter("invite=", raw)
                                            .replace("@", "")
                                            .trim()
                                        val displayName = cleanCode
                                            .split(".", "_", "-")
                                            .joinToString(" ") { part ->
                                                part.replaceFirstChar { it.uppercase() }
                                            }
                                            .ifBlank { "Bạn Mới" }
                                        journeyFriends = listOf(
                                            Triple(displayName, "@$cleanCode", "Đang hoạt động")
                                        ) + journeyFriends
                                        chatMessagesByFriend = chatMessagesByFriend + (
                                            displayName to listOf(
                                                "$displayName: Chào cậu! Mình vừa kết bạn với cậu 👋✨"
                                            )
                                        )
                                        activeChatFriend = displayName
                                        addFriendLinkOrHandle = ""
                                        socialStatusBanner = "🎉 Đã kết bạn với $displayName (@$cleanCode)!"
                                    }
                                },
                                testTag = "btn_add_friend_by_link"
                            )
                        }

                        when (friendsHubTab) {
                            "FRIENDS" -> {
                                if (journeyFriends.isEmpty()) {
                                    Text(
                                        text = tr(lang, "No friends added yet. Share your link above!", "Chưa có bạn bè nào. Hãy chia sẻ link kết bạn ở trên!"),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.secondaryText
                                    )
                                } else {
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        journeyFriends.forEach { (friendName, friendHandle, friendStatus) ->
                                            Surface(
                                                shape = RoundedCornerShape(12.dp),
                                                color = colors.elevatedSurface,
                                                border = BorderStroke(1.dp, colors.border),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                            text = "$friendName ($friendHandle)",
                                                            style = MaterialTheme.typography.labelLarge,
                                                            fontWeight = FontWeight.Bold,
                                                            color = colors.primaryText
                                                        )
                                                        Text(
                                                            text = "🟢 $friendStatus",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = colors.secondaryText
                                                        )
                                                    }
                                                    Surface(
                                                        shape = RoundedCornerShape(JourneyRadius.pill),
                                                        color = colors.accentContainer,
                                                        border = BorderStroke(1.dp, colors.accent),
                                                        modifier = Modifier
                                                            .clip(RoundedCornerShape(JourneyRadius.pill))
                                                            .clickable {
                                                                activeChatFriend = friendName
                                                                friendsHubTab = "CHAT"
                                                            }
                                                    ) {
                                                        Text(
                                                            text = "💬 Nhắn tin",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            fontWeight = FontWeight.Bold,
                                                            color = colors.accent,
                                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            "REQUESTS" -> {
                                if (pendingFriendRequests.isEmpty()) {
                                    Text(
                                        text = tr(lang, "No pending friend requests.", "Chưa có lời mời kết bạn mới."),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.secondaryText
                                    )
                                }
                            }

                            "CHAT" -> {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.testTag("locket_direct_chat_box")
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(modifier = Modifier.weight(1f)) {
                                            JourneyTextField(
                                                value = directMessageInput,
                                                onValueChange = { directMessageInput = it },
                                                label = tr(lang, "Message", "Tin nhắn"),
                                                placeholder = "Nhập tin nhắn...",
                                                testTag = "input_locket_direct_message"
                                            )
                                        }
                                        JourneyButton(
                                            text = tr(lang, "Send", "Gửi"),
                                            variant = JourneyButtonVariant.PRIMARY,
                                            onClick = {
                                                val cleanMsg = directMessageInput.trim()
                                                if (cleanMsg.isNotEmpty()) {
                                                    val target = activeChatFriend.ifBlank { "Bạn bè" }
                                                    val existing = chatMessagesByFriend[target].orEmpty()
                                                    chatMessagesByFriend = chatMessagesByFriend + (
                                                        target to (existing + "Bạn: $cleanMsg")
                                                    )
                                                    directMessageInput = ""
                                                }
                                            },
                                            testTag = "btn_send_locket_direct_message"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 2. Clean Filter Bar (All / Friends / Mine / Refresh)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to tr(lang, "All (${uiState.socialPosts.size})", "🌐 Tất cả (${uiState.socialPosts.size})"),
                    "FRIENDS" to tr(
                        lang,
                        "Friends (${uiState.socialPosts.count { !it.isMine }})",
                        "👥 Bạn bè (${uiState.socialPosts.count { !it.isMine }})"
                    ),
                    "MINE" to tr(
                        lang,
                        "Mine (${uiState.socialPosts.count { it.isMine }})",
                        "👤 Của tôi (${uiState.socialPosts.count { it.isMine }})"
                    )
                ).forEach { (key, label) ->
                    JourneyFilterChip(
                        label = label,
                        selected = uiState.socialFeedFilter == key,
                        onClick = { onSetFeedFilter(key) }
                    )
                }

                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = colors.accentContainer,
                    border = BorderStroke(1.dp, colors.accent),
                    modifier = Modifier
                        .clip(RoundedCornerShape(JourneyRadius.pill))
                        .clickable { triggerFeedRefresh() }
                        .testTag("btn_refresh_locket_feed")
                ) {
                    Text(
                        text = tr(lang, "🔄 Refresh", "🔄 Làm mới"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.accent,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }

            // 3. Responsive Photo Feed Cards
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                filteredPosts.forEach { post ->
                    val displayAuthor = if (post.isMine) uiState.userName else post.authorName
                    val displayInitials = if (post.isMine) uiState.userInitials else post.avatarInitials

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        shadowElevation = JourneyElevation.card,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("feed_post_card_${post.id}")
                    ) {
                        Column {
                            // Post Header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(colors.accentContainer, CircleShape)
                                            .border(1.dp, colors.accent.copy(alpha = 0.5f), CircleShape)
                                    ) {
                                        if (post.isMine && uiState.userAvatarUri != null) {
                                            coil.compose.AsyncImage(
                                                model = uiState.userAvatarUri,
                                                contentDescription = displayAuthor,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        } else {
                                            Text(
                                                text = displayInitials,
                                                style = MaterialTheme.typography.labelMedium,
                                                color = colors.accent
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = displayAuthor,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = colors.primaryText
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                color = colors.elevatedSurface,
                                                border = BorderStroke(1.dp, colors.border)
                                            ) {
                                                Text(
                                                    text = post.privacyLabel,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = colors.secondaryText,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                        val subHeaderParts = buildList {
                                            if (post.placeName.isNotBlank()) add("📍 ${post.placeName}")
                                            add(post.timeAgo)
                                        }
                                        Text(
                                            text = subHeaderParts.joinToString(" · "),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = colors.secondaryText,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                            // Square Photo/Video with Optional Overlays
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .background(JourneyPalette.DeepObsidian)
                                    .clickable { onToggleLikePost(post.id) }
                            ) {
                                if (post.photoUri != null) {
                                    coil.compose.AsyncImage(
                                        model = post.photoUri,
                                        contentDescription = post.caption,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = post.photoRes),
                                        contentDescription = post.caption,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                // Top-End Optional Metadata Badge (Location / Time / Temperature / Sticker)
                                val topBadges = buildList {
                                    if (post.stickerTag.isNotBlank()) add(post.stickerTag)
                                    if (post.timestampWatermark.isNotBlank()) add("🕒 ${post.timestampWatermark}")
                                    if (post.temperatureTag.isNotBlank()) add("🌡️ ${post.temperatureTag}")
                                }
                                if (topBadges.isNotEmpty() || post.placeName.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = Color(0xBF0F172A),
                                        border = BorderStroke(1.dp, Color(0x44FFFFFF)),
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(10.dp)
                                            .testTag("feed_post_timestamp_overlay_${post.id}")
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            if (post.placeName.isNotBlank()) {
                                                Text(
                                                    text = "📍 ${post.placeName}",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color(0xFFF3E5AB),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                            if (topBadges.isNotEmpty()) {
                                                Text(
                                                    text = topBadges.joinToString(" · "),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = JourneyPalette.Ivory,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }

                                if (post.isVideo) {
                                    Surface(
                                        shape = RoundedCornerShape(JourneyRadius.pill),
                                        color = Color(0xCC0F172A),
                                        border = BorderStroke(1.dp, Color(0x44FFFFFF)),
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = "🎥 Video · ${post.videoDurationSec}s",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = JourneyPalette.Ivory,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                // Bottom Spotify + Caption Overlay inside Photo
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    if (post.spotifyTrack.isNotBlank()) {
                                        Surface(
                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                            color = Color(0xD914532D),
                                            border = BorderStroke(1.dp, Color(0xFF22C55E))
                                        ) {
                                            Text(
                                                text = "🎵 ${post.spotifyTrack}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFFBBF7D0),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                            )
                                        }
                                    }

                                    if (post.caption.isNotBlank()) {
                                        Surface(
                                            shape = RoundedCornerShape(18.dp),
                                            color = Color(0xBF0F172A),
                                            border = BorderStroke(1.dp, Color(0x33FFFFFF))
                                        ) {
                                            Text(
                                                text = post.caption,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = JourneyPalette.Ivory,
                                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            // Post Actions Bar (Like, Comment, Delete)
                            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Surface(
                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                            color = if (post.isLiked) Color(0x22DC2626) else colors.elevatedSurface,
                                            border = BorderStroke(
                                                1.dp,
                                                if (post.isLiked) Color(0x66DC2626) else colors.border
                                            ),
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(JourneyRadius.pill))
                                                .clickable { onToggleLikePost(post.id) }
                                                .testTag("like_btn_${post.id}")
                                        ) {
                                            Text(
                                                text = "${if (post.isLiked) "❤️" else "🤍"} ${post.likesCount}",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = colors.primaryText,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                            color = colors.elevatedSurface,
                                            border = BorderStroke(1.dp, colors.border),
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(JourneyRadius.pill))
                                                .clickable {
                                                    commentingPostId = if (commentingPostId == post.id) null else post.id
                                                }
                                                .testTag("comment_btn_${post.id}")
                                        ) {
                                            Text(
                                                text = "💬 ${post.comments.size}",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = colors.primaryText,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }
                                    }

                                    if (post.isMine) {
                                        Surface(
                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                            color = Color(0x22DC2626),
                                            border = BorderStroke(1.dp, Color(0x66DC2626)),
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(JourneyRadius.pill))
                                                .clickable { onDeletePost(post.id) }
                                                .testTag("delete_post_btn_${post.id}")
                                        ) {
                                            Text(
                                                text = tr(lang, "🗑️ Delete", "🗑️ Xóa"),
                                                style = MaterialTheme.typography.labelLarge,
                                                color = Color(0xFFEF4444),
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }

                                // Comments Preview
                                if (post.comments.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    post.comments.takeLast(3).forEach { c ->
                                        Text(
                                            text = "${c.authorName}: ${c.text}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = colors.secondaryText,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }

                                // Inline Comment Input Box
                                if (commentingPostId == post.id) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(modifier = Modifier.weight(1f)) {
                                            JourneyTextField(
                                                value = commentDraft,
                                                onValueChange = { commentDraft = it },
                                                label = tr(lang, "Add comment", "Viết bình luận"),
                                                placeholder = tr(lang, "Write something...", "Nhập bình luận..."),
                                                testTag = "comment_input_${post.id}"
                                            )
                                        }
                                        JourneyButton(
                                            text = tr(lang, "Send", "Gửi"),
                                            onClick = {
                                                if (commentDraft.isNotBlank()) {
                                                    onAddComment(post.id, commentDraft)
                                                    commentDraft = ""
                                                    commentingPostId = null
                                                }
                                            },
                                            variant = JourneyButtonVariant.PRIMARY
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

// ============================================================================
// PHASE 2 — SCREEN B2: GLOBAL SEARCH
// ============================================================================
@Composable
fun GlobalSearchScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val query = uiState.searchQuery

    val filteredPlaces = uiState.places.filter {
        query.isBlank() ||
            it.nameEn.contains(query, ignoreCase = true) ||
            it.nameVi.contains(query, ignoreCase = true) ||
            it.regionEn.contains(query, ignoreCase = true) ||
            it.eraEn.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.B2_GLOBAL_SEARCH,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySearchInputBar(
                value = query,
                onValueChange = onQueryChange,
                placeholder = tr(lang, "Search 'Hội An', 'Văn Miếu', '1593', 'UNESCO'...", "Tìm 'Hội An', 'Văn Miếu', 'Hạ Long', '1593'..."),
                trailingAction = if (query.isNotEmpty()) {
                    {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search",
                                tint = colors.secondaryText
                            )
                        }
                    }
                } else null,
                testTag = "global_search_input"
            )

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Hội An", "Hạ Long", "Văn Miếu", "Mù Cang Chải", "UNESCO", "1593").forEach { tag ->
                    JourneyFilterChip(
                        label = tag,
                        selected = query.equals(tag, ignoreCase = true),
                        onClick = {
                            onQueryChange(if (query.equals(tag, ignoreCase = true)) "" else tag)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))

            Text(
                text = tr(lang, "MATCHING HERITAGE DESTINATIONS (${filteredPlaces.size})", "ĐỊA DANH PHÙ HỢP (${filteredPlaces.size})"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(JourneySpacing.sm))

            filteredPlaces.forEach { place ->
                JourneyCompactLandmarkRowCard(
                    imageRes = place.imageRes,
                    name = tr(lang, place.nameEn, place.nameVi),
                    region = tr(lang, place.regionEn, place.regionVi),
                    era = tr(lang, place.eraEn, place.eraVi),
                    coordinates = place.coordinates,
                    distance = place.distanceKm,
                    audioDuration = place.audioDuration,
                    isSaved = place.isSaved,
                    onClick = { onSelectPlace(place.id, true) },
                    onSaveClick = {},
                    onAudioClick = {
                        onSelectPlace(place.id, false)
                        onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT)
                    },
                    modifier = Modifier.padding(bottom = JourneySpacing.sm)
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN B3: NOTIFICATIONS
// ============================================================================
@Composable
fun NotificationsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    val notifications = listOf(
        Triple(
            tr(lang, "AI Story & 4K Reel Ready", "Câu chuyện AI & Phim 4K Đã Sẵn Sàng"),
            tr(lang, "Your 10-day 'Mists of Tonkin & Lanterns of Faifo' anthology has been composed with ambient đàn tranh score.", "Tuyển tập 'Sương mờ Bắc Bộ & Đêm đèn lồng Hội An' đã hoàn tất dựng phim cùng nhạc nền đàn tranh."),
            JourneyScreenId.H1_AI_STORY_PREVIEW
        ),
        Triple(
            tr(lang, "Proximity Heritage Alert • 180m", "Phát Hiện Di Sản Gần Bạn • 180m"),
            tr(lang, "You are approaching Chùa Cầu (1593). Launch the AI Voice Guide for acoustic history.", "Bạn đang ở gần Chùa Cầu Hội An (1593). Mở Thuyết minh Giọng nói để nghe lịch sử kiến trúc."),
            JourneyScreenId.E2_VOICE_ASSISTANT
        ),
        Triple(
            tr(lang, "Offline Heritage Pack Updated", "Đã Cập Nhật Gói Di Sản Ngoại Tuyến"),
            tr(lang, "High-resolution architectural diagrams for Văn Miếu & Hoàng Liên Terraces cached for offline use.", "Bản vẽ kiến trúc Văn Miếu & Ruộng bậc thang Hoàng Liên Sơn đã được lưu ngoại tuyến."),
            JourneyScreenId.C4_SAVED_PLACES
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.B3_NOTIFICATIONS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            notifications.forEach { (title, desc, target) ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onNavigate(target) }
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        JourneyBadgePill(text = "DISCOVERY DISPATCH")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = title, style = MaterialTheme.typography.titleLarge, color = colors.primaryText)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = desc, style = MaterialTheme.typography.bodyMedium, color = colors.secondaryText)
                    }
                }
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C1: VISITED PLACES MAP (KHÁM PHÁ: BẢN ĐỒ NHỮNG NƠI ĐÃ ĐI / ĐÃ ĐẾN)
// ============================================================================
@Composable
fun ExploreMapScreen(
    uiState: JourneyUiState,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val activePlace = uiState.selectedPlace
    val myCheckinsCount = uiState.socialPosts.count { it.isMine }
    var mapLayerMode by remember { mutableStateOf("STANDARD") } // STANDARD | SATELLITE | TERRAIN
    var mapZoomScale by remember { mutableStateOf(1.0f) } // 0.6f .. 2.2f
    var mapSearchQuery by remember { mutableStateOf("") }
    var visibleMapCategories by remember {
        mutableStateOf(setOf("NATURE", "MUSEUMS", "RESTAURANTS"))
    }

    val filteredMapPlaces = remember(mapSearchQuery, uiState.places) {
        val q = mapSearchQuery.trim().lowercase()
        if (q.isEmpty()) emptyList()
        else uiState.places.filter { place ->
            place.nameVi.lowercase().contains(q) ||
                place.nameEn.lowercase().contains(q) ||
                place.regionVi.lowercase().contains(q) ||
                place.regionEn.lowercase().contains(q)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
            .testTag("screen_c1_explore_map")
    ) {
        // 1. Thống kê Dấu chân Hành trình (Visited Stats Row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JourneyStatPillCard(
                value = "${uiState.places.size}",
                label = tr(lang, "Visited", "Nơi đã đến"),
                sublabel = tr(lang, "Landmarks", "Cột mốc"),
                modifier = Modifier.weight(1f)
            )
            JourneyStatPillCard(
                value = "4",
                label = tr(lang, "Provinces", "Tỉnh / Thành"),
                sublabel = tr(lang, "Vietnam", "Việt Nam"),
                modifier = Modifier.weight(1f)
            )
            JourneyStatPillCard(
                value = "$myCheckinsCount",
                label = tr(lang, "Check-ins", "Ảnh Check-in"),
                sublabel = tr(lang, "Memories", "Kỷ niệm"),
                modifier = Modifier.weight(1f)
            )
        }

        // 1A. Top Map Search Bar: Jump to specific cities or landmarks in Vietnam
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md, vertical = 4.dp)
                .testTag("map_search_bar_container"),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                JourneyTextField(
                    value = mapSearchQuery,
                    onValueChange = { mapSearchQuery = it },
                    label = tr(lang, "Search Vietnam City / Landmark", "Tìm thành phố, địa danh Việt Nam"),
                    placeholder = tr(
                        lang,
                        "Ha Long, Ha Noi, Hoi An, Sa Pa...",
                        "Vịnh Hạ Long, Hà Nội, Hội An, Sa Pa..."
                    ),
                    leadingIcon = Icons.Default.Search,
                    testTag = "map_search_input",
                    modifier = Modifier.weight(1f)
                )
                if (mapSearchQuery.isNotBlank()) {
                    IconButton(
                        onClick = { mapSearchQuery = "" },
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("map_search_clear_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = tr(lang, "Clear search", "Xóa tìm kiếm"),
                            tint = colors.secondaryText
                        )
                    }
                }
            }

            // Live Search Results Dropdown when typing
            if (mapSearchQuery.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.md),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.accent),
                    shadowElevation = JourneyElevation.card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("map_search_suggestions")
                ) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        if (filteredMapPlaces.isEmpty()) {
                            Text(
                                text = tr(
                                    lang,
                                    "No matching landmark found. Try Ha Long, Hoi An, Ha Noi, or Sa Pa.",
                                    "Không tìm thấy địa danh phù hợp. Hãy thử: Hạ Long, Hội An, Hà Nội, Sa Pa."
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText,
                                modifier = Modifier.padding(8.dp)
                            )
                        } else {
                            filteredMapPlaces.forEach { place ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(JourneyRadius.sm))
                                        .clickable {
                                            onSelectPlace(place.id, false)
                                            mapZoomScale = 1.5f
                                            mapSearchQuery = ""
                                        }
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                        .testTag("map_search_result_${place.id}"),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = colors.accent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Column {
                                            Text(
                                                text = tr(lang, place.nameEn, place.nameVi),
                                                style = MaterialTheme.typography.labelLarge,
                                                color = colors.primaryText
                                            )
                                            Text(
                                                text = "${place.regionVi} • ${place.coordinates}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = colors.secondaryText
                                            )
                                        }
                                    }
                                    JourneyBadgePill(
                                        text = tr(lang, "Jump →", "Đi tới →"),
                                        containerColor = colors.accentContainer,
                                        contentColor = colors.accent
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick-Jump Landmark Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                uiState.places.forEach { place ->
                    val isSelected = place.id == activePlace.id
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (isSelected) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (isSelected) colors.accent else colors.border),
                        modifier = Modifier
                            .clip(RoundedCornerShape(JourneyRadius.pill))
                            .clickable {
                                onSelectPlace(place.id, false)
                                mapZoomScale = 1.25f
                            }
                            .testTag("map_quick_jump_${place.id}")
                    ) {
                        Text(
                            text = "📍 ${tr(lang, place.nameEn, place.nameVi)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) colors.onAccent else colors.primaryText,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 1B. Map Layer Toggle Bar: Standard / Satellite / Terrain
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md, vertical = 4.dp)
                .testTag("map_layer_toggle_bar"),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tr(lang, "Map Layer:", "Chế độ bản đồ:"),
                style = MaterialTheme.typography.labelLarge,
                color = colors.primaryText
            )
            Surface(
                shape = RoundedCornerShape(JourneyRadius.pill),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(
                        Triple("STANDARD", "🗺️ Standard", "map_layer_standard"),
                        Triple("SATELLITE", "🛰️ Satellite", "map_layer_satellite"),
                        Triple("TERRAIN", "⛰️ Terrain", "map_layer_terrain")
                    ).forEach { (layerKey, layerLabel, tag) ->
                        val isActiveLayer = mapLayerMode == layerKey
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = if (isActiveLayer) colors.accent else Color.Transparent,
                            modifier = Modifier
                                .clip(RoundedCornerShape(JourneyRadius.pill))
                                .clickable { mapLayerMode = layerKey }
                                .testTag(tag)
                        ) {
                            Text(
                                text = layerLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isActiveLayer) colors.onAccent else colors.secondaryText,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 2. Bản đồ những nơi đã đi / đã đến (Interactive Footprint Map)
        val mapBgColor = when (mapLayerMode) {
            "SATELLITE" -> Color(0xFF06151F)
            "TERRAIN" -> Color(0xFF1A261E)
            else -> JourneyPalette.DeepObsidian
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp)
                .padding(horizontal = JourneySpacing.md)
                .clip(RoundedCornerShape(JourneyRadius.xl))
                .background(mapBgColor)
                .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.xl))
        ) {
            if (mapLayerMode == "SATELLITE") {
                Image(
                    painter = painterResource(id = activePlace.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alpha = 0.28f,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Topographic Contour Lines / Satellite Grid / Standard Route Path
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val gridColor = when (mapLayerMode) {
                    "SATELLITE" -> Color(0x3838BDF8)
                    "TERRAIN" -> Color(0x40A3B18A)
                    else -> Color(0x26879A91)
                }
                val goldLine = when (mapLayerMode) {
                    "SATELLITE" -> Color(0x8838BDF8)
                    "TERRAIN" -> Color(0x88D4A373)
                    else -> Color(0x55B99B72)
                }

                for (i in 1..6) {
                    val x = w * (i / 7f)
                    drawLine(gridColor, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
                }
                for (j in 1..6) {
                    val y = h * (j / 7f)
                    drawLine(gridColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
                }

                val cx = w * activePlace.mapXPercent
                val cy = h * activePlace.mapYPercent
                val ringCount = if (mapLayerMode == "TERRAIN") 5 else 3
                val baseRadius = (if (mapLayerMode == "TERRAIN") 42f else 55f) * mapZoomScale
                for (r in 1..ringCount) {
                    drawCircle(
                        color = goldLine,
                        radius = (r * baseRadius),
                        center = Offset(cx, cy),
                        style = Stroke(
                            width = (if (mapLayerMode == "TERRAIN") 2f else 1.5f) * mapZoomScale.coerceIn(0.8f, 1.5f),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                        )
                    )
                }

                val routePoints = uiState.places.map { Offset(w * it.mapXPercent, h * it.mapYPercent) }
                for (k in 0 until routePoints.size - 1) {
                    drawLine(
                        color = Color(0x99B99B72),
                        start = routePoints[k],
                        end = routePoints[k + 1],
                        strokeWidth = 3.5f * mapZoomScale.coerceIn(0.8f, 1.5f),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
                    )
                }
            }

            // Map Header Badge + Active Layer Indicator + Vietnam Sovereignty Badges (Hoàng Sa & Trường Sa)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                JourneyBadgePill(
                    text = when (mapLayerMode) {
                        "SATELLITE" -> "🛰️ SATELLITE VIEW · VIỆT NAM"
                        "TERRAIN" -> "⛰️ TERRAIN TOPO · VIỆT NAM"
                        else -> tr(lang, "🇻🇳 STANDARD MAP · VIETNAM", "🇻🇳 BẢN ĐỒ VIỆT NAM (STANDARD)")
                    },
                    containerColor = Color(0xCC111923),
                    contentColor = JourneyPalette.SandGold
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 48.dp, end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = Color(0xD9DC2626),
                    border = BorderStroke(1.dp, Color(0xFFFFD700))
                ) {
                    Text(
                        text = "🇻🇳 QĐ. Hoàng Sa (Việt Nam)",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = Color(0xD9DC2626),
                    border = BorderStroke(1.dp, Color(0xFFFFD700))
                ) {
                    Text(
                        text = "🇻🇳 QĐ. Trường Sa (Việt Nam)",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Dedicated Mobile-Accessible '+' and '-' Zoom Controls (48dp touch targets)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xD90F172A),
                border = BorderStroke(1.dp, JourneyPalette.SandGold.copy(alpha = 0.55f)),
                shadowElevation = JourneyElevation.floating,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .testTag("map_zoom_controls_container")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (mapZoomScale < 2.2f) Color(0x26FFFFFF) else Color.Transparent,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(enabled = mapZoomScale < 2.2f) {
                                mapZoomScale = (mapZoomScale + 0.25f).coerceAtMost(2.2f)
                            }
                            .testTag("map_zoom_in_btn")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "+",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (mapZoomScale < 2.2f) Color.White else Color.White.copy(alpha = 0.38f)
                            )
                        }
                    }

                    Text(
                        text = "${(mapZoomScale * 5).toInt()}x",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = JourneyPalette.SandGold
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (mapZoomScale > 0.6f) Color(0x26FFFFFF) else Color.Transparent,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(enabled = mapZoomScale > 0.6f) {
                                mapZoomScale = (mapZoomScale - 0.25f).coerceAtLeast(0.6f)
                            }
                            .testTag("map_zoom_out_btn")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "−",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (mapZoomScale > 0.6f) Color.White else Color.White.copy(alpha = 0.38f)
                            )
                        }
                    }
                }
            }

            // Interactive Category Filter Overlay (Nature Spots / Museums / Restaurants)
            Surface(
                shape = RoundedCornerShape(JourneyRadius.pill),
                color = Color(0xD90F172A),
                border = BorderStroke(1.dp, JourneyPalette.SandGold.copy(alpha = 0.45f)),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 46.dp, start = 12.dp, end = 12.dp)
                    .testTag("map_category_filter_overlay")
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf(
                        Triple("NATURE", "🌿 Nature Spots", "map_filter_nature"),
                        Triple("MUSEUMS", "🏛️ Museums", "map_filter_museums"),
                        Triple("RESTAURANTS", "🍽️ Restaurants", "map_filter_restaurants")
                    ).forEach { (catKey, catLabel, tag) ->
                        val isCatVisible = visibleMapCategories.contains(catKey)
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = if (isCatVisible) JourneyPalette.SandGold else Color(0x551C2934),
                            border = BorderStroke(
                                1.dp,
                                if (isCatVisible) JourneyPalette.Ivory else Color(0x44FFFFFF)
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(JourneyRadius.pill))
                                .clickable {
                                    visibleMapCategories = if (isCatVisible) {
                                        visibleMapCategories - catKey
                                    } else {
                                        visibleMapCategories + catKey
                                    }
                                }
                                .testTag(tag)
                        ) {
                            Text(
                                text = catLabel,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isCatVisible) JourneyPalette.Midnight else JourneyPalette.Ivory.copy(alpha = 0.65f),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }

            // Interactive Visited & Categorized Pins on Map
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 88.dp, start = 12.dp, end = 68.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                uiState.places.filter { place ->
                    val placeCat = when (place.id) {
                        "hanoi_temple", "hoian_ancient_town" -> "MUSEUMS"
                        "halong_bay", "sapa_terraces" -> "NATURE"
                        else -> "RESTAURANTS"
                    }
                    visibleMapCategories.contains(placeCat)
                }.forEach { place ->
                    val isSelected = place.id == activePlace.id
                    val catBadge = when (place.id) {
                        "hanoi_temple", "hoian_ancient_town" -> "🏛️"
                        "halong_bay", "sapa_terraces" -> "🌿"
                        else -> "🍽️"
                    }
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (isSelected) JourneyPalette.SandGold else Color(0xCC1C2934),
                        border = BorderStroke(
                            1.2.dp,
                            if (isSelected) JourneyPalette.Ivory else JourneyPalette.SandGold.copy(alpha = 0.6f)
                        ),
                        shadowElevation = JourneyElevation.card,
                        modifier = Modifier
                            .clickable { onSelectPlace(place.id, false) }
                            .testTag("map_pin_${place.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.MyLocation else Icons.Default.LocationOn,
                                contentDescription = place.nameVi,
                                tint = if (isSelected) JourneyPalette.Midnight else JourneyPalette.SandGold,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "$catBadge ${tr(lang, place.nameEn, place.nameVi)}",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isSelected) JourneyPalette.Midnight else JourneyPalette.Ivory
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "✓ Đã đến",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) JourneyPalette.Midnight.copy(alpha = 0.8f) else JourneyPalette.SandGold
                            )
                        }
                    }
                }

                if (visibleMapCategories.contains("RESTAURANTS")) {
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = Color(0xCC1C2934),
                        border = BorderStroke(1.dp, JourneyPalette.SandGold.copy(alpha = 0.6f)),
                        shadowElevation = JourneyElevation.card,
                        modifier = Modifier
                            .clickable { onSelectPlace("hoian_ancient_town", false) }
                            .testTag("map_pin_restaurant_special")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = tr(
                                    lang,
                                    "🍽️ Cha Ca La Vong & Hoi An Cao Lau",
                                    "🍽️ Chả Cá Lã Vọng & Cao Lầu Hội An"
                                ),
                                style = MaterialTheme.typography.labelMedium,
                                color = JourneyPalette.Ivory
                            )
                        }
                    }
                }
            }

            // Bottom Selected Visited Place Card inside Map
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface.copy(alpha = 0.96f),
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.55f)),
                shadowElevation = JourneyElevation.floating,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = activePlace.imageRes),
                        contentDescription = activePlace.nameVi,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "✓ ĐÃ ĐẶT CHÂN TỚI • ${activePlace.regionVi}",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.sage
                        )
                        Text(
                            text = tr(lang, activePlace.nameEn, activePlace.nameVi),
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = activePlace.coordinates,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    JourneyPrimaryButton(
                        text = tr(lang, "Details", "Chi tiết"),
                        onClick = { onSelectPlace(activePlace.id, true) },
                        testTag = "map_open_place_detail_btn"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Danh sách Cột mốc Đã đặt chân tới
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tr(lang, "Visited Milestones (${uiState.places.size})", "Cột Mốc Đã Đặt Chân Tới (${uiState.places.size})"),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.primaryText
                )
                Text(
                    text = tr(lang, "View Check-ins →", "Xem ảnh Check-in →"),
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.accent,
                    modifier = Modifier.clickable { onNavigate(JourneyScreenId.F1_JOURNAL_HOME) }
                )
            }

            uiState.places.forEach { place ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colors.surface,
                    border = BorderStroke(
                        1.dp,
                        if (place.id == activePlace.id) colors.accent else colors.border
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectPlace(place.id, false) }
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = place.imageRes),
                            contentDescription = place.nameVi,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, place.nameEn, place.nameVi),
                                style = MaterialTheme.typography.titleSmall,
                                color = colors.primaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "📍 ${place.regionVi} · ${place.coordinates}",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = colors.accentContainer,
                            modifier = Modifier.clickable { onSelectPlace(place.id, true) }
                        ) {
                            Text(
                                text = tr(lang, "View", "Xem"),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C2: DISCOVERY FEED
// ============================================================================
@Composable
fun DiscoveryFeedScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val lang = uiState.language
    var selectedEraFilter by remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C2_DISCOVERY_FEED,
            language = lang,
            onBackClick = onBack
        )
        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "EDITORIAL DISCOVERY STREAM", "BẢNG TIN KHÁM PHÁ CHUYÊN SÂU"),
                title = tr(lang, "Architectural & Natural Wonders", "Kỳ Quan Kiến Trúc & Thiên Nhiên")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.sm))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to tr(lang, "All Curations", "Tất cả"),
                    "UNESCO" to tr(lang, "UNESCO Heritage", "Di sản UNESCO"),
                    "IMPERIAL" to tr(lang, "Dynastic Architecture", "Kiến trúc Cổ")
                ).forEach { (id, label) ->
                    JourneyFilterChip(
                        label = label,
                        selected = selectedEraFilter == id,
                        onClick = { selectedEraFilter = id }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))
            uiState.places.forEach { place ->
                JourneyEditorialCard(
                    imageRes = place.imageRes,
                    tagText = tr(lang, place.eraEn, place.eraVi),
                    title = tr(lang, place.nameEn, place.nameVi),
                    subtitle = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                    metaText = "${place.coordinates} • AUDIO GUIDE ${place.audioDuration}",
                    isSaved = place.isSaved,
                    onCardClick = { onSelectPlace(place.id, true) },
                    onSaveClick = { onToggleSavePlace(place.id) },
                    modifier = Modifier.padding(bottom = JourneySpacing.md),
                    heightDp = 265
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C3: PLACE DETAILS (With Tabbed Dossier + Mini Audio Player + FLOW 4 Actions)
// ============================================================================
@Composable
fun PlaceDetailsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace
    var activeTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_c3_place_details")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C3_PLACE_DETAILS,
            language = lang,
            onBackClick = onBack,
            trailingContent = {
                IconButton(
                    onClick = { onToggleSavePlace(place.id) },
                    modifier = Modifier
                        .size(42.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("place_detail_save_btn")
                ) {
                    Icon(
                        imageVector = if (place.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Place",
                        tint = colors.accent
                    )
                }
            }
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyEditorialCard(
                imageRes = place.imageRes,
                tagText = tr(lang, place.categoryEn, place.categoryVi),
                title = tr(lang, place.nameEn, place.nameVi),
                subtitle = tr(lang, place.regionEn, place.regionVi),
                metaText = "${place.coordinates} • ${tr(lang, place.eraEn, place.eraVi)}",
                isSaved = place.isSaved,
                onCardClick = {},
                onSaveClick = { onToggleSavePlace(place.id) },
                heightDp = 300
            )

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Embedded Binaural Voice Guide Mini-Player Bar
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.accentContainer,
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(38.dp)
                                .background(colors.accent, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Audio Guide",
                                tint = colors.onAccent
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = tr(lang, "BINAURAL VOICE GUIDE • ${place.audioDuration}", "THUYẾT MINH GIỌNG NÓI AI • ${place.audioDuration}"),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent
                            )
                            Text(
                                text = tr(lang, "Listen to scholar narration while walking", "Nghe thuyết minh lịch sử & kiến trúc khi dạo bước"),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.primaryText
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = colors.accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Primary Action Row: Ask AI Assistant, Deep Monograph
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyPrimaryButton(
                    text = tr(lang, "Ask AI Scholar", "Hỏi Chuyên gia AI"),
                    leadingIcon = Icons.Default.AutoAwesome,
                    onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                    modifier = Modifier.weight(1f),
                    testTag = "place_detail_ask_ai_btn"
                )
                JourneySecondaryButton(
                    text = tr(lang, "Deep History", "Hồ sơ Chuyên khảo"),
                    onClick = { onNavigate(JourneyScreenId.D5_DISCOVERY_DETAILS) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Dossier Section Tabs (Historical Overview / Architecture / Folklore)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    0 to tr(lang, "Overview", "Tổng quan"),
                    1 to tr(lang, "Architecture", "Kiến trúc"),
                    2 to tr(lang, "Oral Lore", "Truyền thuyết")
                ).forEach { (idx, tabTitle) ->
                    JourneyFilterChip(
                        label = tabTitle,
                        selected = activeTabIndex == idx,
                        onClick = { activeTabIndex = idx }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Historical Overview Card
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Text(
                        text = tr(lang, "HISTORICAL SIGNIFICANCE", "GIÁ TRỊ LỊCH SỬ & VĂN HÓA"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tr(lang, place.summaryEn, place.summaryVi),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.primaryText
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)
                    Text(
                        text = tr(lang, "ARCHITECTURAL & GEOLOGICAL NOTES", "ĐẶC ĐIỂM KIẾN TRÚC & ĐỊA CHẤT"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.architecturalNotesEn, place.architecturalNotesVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)
                    Text(
                        text = tr(lang, "ORAL TRADITION & STORY", "CÂU CHUYỆN DI SẢN TRUYỀN ĐỜI"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Saved Places", "Địa điểm Đã lưu"),
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = { onNavigate(JourneyScreenId.C4_SAVED_PLACES) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Save to Collection", "Lưu vào Bộ sưu tập"),
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = { onNavigate(JourneyScreenId.G1_DISCOVERY_COLLECTION) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C4: SAVED PLACES & OFFLINE PACKS
// ============================================================================
@Composable
fun SavedPlacesScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val savedPlaces = uiState.places.filter { it.isSaved }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C4_SAVED_PLACES,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "PERSONAL ATLAS • ${savedPlaces.size} BOOKMARKS", "BẢN ĐỒ CÁ NHÂN • ${savedPlaces.size} ĐỊA ĐIỂM ĐÃ LƯU"),
                title = tr(lang, "Saved Places & Offline Guides", "Địa Điểm Đã Lưu & Dữ Liệu Ngoại Tuyến")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Offline Storage Telemetry Card
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = JourneySpacing.md)
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = tr(lang, "OFFLINE HERITAGE STORAGE", "DUNG LƯỢNG GÓI DI SẢN NGOẠI TUYẾN"),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.accent
                        )
                        Text(
                            text = "148 MB / 500 MB",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.secondaryText
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.3f },
                        color = colors.accent,
                        trackColor = colors.subtleBorder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }

            savedPlaces.forEach { place ->
                JourneyEditorialCard(
                    imageRes = place.imageRes,
                    tagText = tr(lang, "OFFLINE PACK READY • ${place.audioDuration} AUDIO", "ĐÃ TẢI GÓI NGOẠI TUYẾN • ${place.audioDuration}"),
                    title = tr(lang, place.nameEn, place.nameVi),
                    subtitle = tr(lang, place.summaryEn, place.summaryVi),
                    metaText = place.coordinates,
                    isSaved = true,
                    onCardClick = { onSelectPlace(place.id, true) },
                    onSaveClick = { onToggleSavePlace(place.id) },
                    modifier = Modifier.padding(bottom = JourneySpacing.md),
                    heightDp = 225
                )
            }
        }
    }
}
