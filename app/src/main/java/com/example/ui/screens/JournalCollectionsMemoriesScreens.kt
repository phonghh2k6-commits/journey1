package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.model.JourneyScreenId
import com.example.ui.components.JourneyBadgePill
import com.example.ui.components.JourneyButton
import com.example.ui.components.JourneyButtonVariant
import com.example.ui.components.JourneyEditorialCard
import com.example.ui.components.JourneySectionHeader
import com.example.ui.components.JourneyTextField
import com.example.ui.components.JourneyTopBar
import com.example.ui.components.tr
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState

// ============================================================================
// PHASE 2 — SCREEN F1: CHECK-IN PHOTO JOURNAL (NHẬT KÝ: NHỮNG BỨC ẢNH & NƠI ĐÃ CHECK-IN)
// ============================================================================
@Composable
fun JournalHomeScreen(
    uiState: JourneyUiState,
    onSelectTrip: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit,
    onSelectPlace: (String, Boolean) -> Unit = { _, _ -> },
    onDeleteMoment: (String) -> Unit = {}
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    // Source of truth: user's Locket posts (synchronized with Home feed)
    val myPosts = uiState.socialPosts.filter { it.isMine }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
            .testTag("screen_f1_journal_home")
    ) {
        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md, vertical = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tr(
                            lang,
                            "My Check-in Photos (${myPosts.size})",
                            "Nhật Ký Ảnh Đã Check-in (${myPosts.size})"
                        ),
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.primaryText
                    )
                    Text(
                        text = tr(
                            lang,
                            "Deleting a photo here also removes it from Home feed",
                            "Khi xóa ảnh tại đây, bài đăng trên Trang chủ cũng được xóa đồng bộ"
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.secondaryText
                    )
                }
                JourneyButton(
                    text = tr(lang, "+ Check-in", "+ Chụp mới"),
                    leadingIcon = Icons.Default.PhotoCamera,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.D1_AI_CAMERA) },
                    testTag = "journal_create_trip_btn"
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            myPosts.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { post ->
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = colors.surface,
                            border = BorderStroke(1.dp, colors.border),
                            shadowElevation = com.example.ui.theme.JourneyElevation.card,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onNavigate(JourneyScreenId.C1_EXPLORE_MAP) }
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .background(JourneyPalette.DeepObsidian)
                                ) {
                                    if (post.photoUri != null) {
                                        coil.compose.AsyncImage(
                                            model = post.photoUri,
                                            contentDescription = post.placeName,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = post.photoRes),
                                            contentDescription = post.placeName,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(JourneyRadius.pill),
                                        color = Color(0xBF0F172A),
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "📅 ${post.timestampWatermark.ifBlank { post.dateLabel }}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = JourneyPalette.Ivory,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }

                                    // Bottom Location & Coordinates Overlay Stamp on Photo
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xCC0F172A),
                                        border = BorderStroke(1.dp, Color(0x55D4AF37)),
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "📍 ${post.placeName.take(22)}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFFF3E5AB),
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                        )
                                    }

                                    // Synchronized Delete Button (removes from both Journal & Home)
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xCC0F172A),
                                        border = BorderStroke(1.dp, Color(0x55EF4444)),
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(8.dp)
                                            .size(28.dp)
                                            .clickable { onDeleteMoment(post.id) }
                                            .testTag("journal_delete_btn_${post.id}")
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "🗑️",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "📍 ${post.placeName}",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = colors.accent,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = post.caption,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.secondaryText,
                                        maxLines = 2,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// ============================================================================
// F2: CREATE TRIP
// ============================================================================
@Composable
fun CreateTripScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onCreateTrip: (String, String, String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var title by remember { mutableStateOf("Imperial Hue & Perfume River Autumn") }
    var locations by remember { mutableStateOf("Huế • Đầm Lập An • Hội An") }
    var dates by remember { mutableStateOf("Nov 20 – Nov 26, 2026") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.F2_CREATE_TRIP,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "NEW EXPEDITION FOLIO", "KHỞI TẠO HÀNH TRÌNH MỚI"),
                title = tr(lang, "Compose Your Travel Chronicle", "Đặt Tên Cho Chuyến Đi Của Bạn")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(JourneySpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    JourneyTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = tr(lang, "Trip Title", "Tên Chuyến đi"),
                        placeholder = "e.g., Imperial Hue & Central Coast",
                        testTag = "create_trip_title_input"
                    )
                    JourneyTextField(
                        value = locations,
                        onValueChange = { locations = it },
                        label = tr(lang, "Route & Destinations", "Lộ trình & Điểm đến"),
                        placeholder = "Huế • Đà Nẵng • Hội An",
                        leadingIcon = Icons.Default.LocationOn
                    )
                    JourneyTextField(
                        value = dates,
                        onValueChange = { dates = it },
                        label = tr(lang, "Dates", "Thời gian"),
                        placeholder = "Nov 20 – Nov 26, 2026",
                        leadingIcon = Icons.Default.CalendarMonth
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    JourneyButton(
                        text = tr(lang, "Create Trip Journal", "Tạo Nhật ký Chuyến đi"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onCreateTrip(title, locations, dates) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "submit_create_trip_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// F3: TRIP DETAIL
// ============================================================================
@Composable
fun TripDetailScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectMoment: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val trip = uiState.selectedTrip

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_f3_trip_detail")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.F3_TRIP_DETAIL,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyEditorialCard(
                imageRes = trip.coverRes,
                tagText = trip.dateRange,
                title = tr(lang, trip.titleEn, trip.titleVi),
                subtitle = tr(lang, trip.storyExcerptEn, trip.storyExcerptVi),
                metaText = "${tr(lang, trip.locationEn, trip.locationVi)} • ${trip.moments.size} LOGGED MOMENTS",
                onCardClick = {},
                heightDp = 250
            )

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // FLOW 5 Actions: Add Media (F5), Timeline (F4), AI Story (H1)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Add Moment", "Thêm Khoảnh khắc"),
                    leadingIcon = Icons.Default.PhotoCamera,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.F5_CREATE_DAILY_MOMENT) },
                    modifier = Modifier.weight(1f),
                    testTag = "trip_add_moment_btn"
                )
                JourneyButton(
                    text = tr(lang, "Timeline", "Dòng thời gian"),
                    leadingIcon = Icons.Default.Timeline,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.F4_TRIP_TIMELINE) },
                    modifier = Modifier.weight(1f),
                    testTag = "trip_open_timeline_btn"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            JourneyButton(
                text = tr(lang, "Generate AI Story & Cinema Reel", "Tạo Câu chuyện AI & Phim Hành trình"),
                leadingIcon = Icons.Default.AutoAwesome,
                variant = JourneyButtonVariant.SURFACE_TONAL,
                onClick = { onNavigate(JourneyScreenId.H1_AI_STORY_PREVIEW) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(JourneySpacing.xl))
            Text(
                text = tr(lang, "LOGGED DAILY MOMENTS (${trip.moments.size})", "KHOẢNH KHẮC ĐÃ GHI LẠI (${trip.moments.size})"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(10.dp))

            trip.moments.forEach { moment ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onSelectMoment(moment.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = moment.photoRes),
                            contentDescription = moment.titleEn,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(82.dp)
                                .clip(RoundedCornerShape(JourneyRadius.md))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DAY ${moment.dayNumber} • ${moment.timeLabel} • ${moment.weatherTag}",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent
                            )
                            Text(
                                text = tr(lang, moment.titleEn, moment.titleVi),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = tr(lang, moment.noteEn, moment.noteVi),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// F4: TRIP TIMELINE
// ============================================================================
@Composable
fun TripTimelineScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectMoment: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val trip = uiState.selectedTrip

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_f4_trip_timeline")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.F4_TRIP_TIMELINE,
            language = lang,
            onBackClick = onBack,
            trailingContent = {
                JourneyButton(
                    text = tr(lang, "AI Story (H1)", "Truyện AI (H1)"),
                    leadingIcon = Icons.Default.AutoAwesome,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.H1_AI_STORY_PREVIEW) },
                    testTag = "timeline_to_story_btn"
                )
            }
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = "${trip.dateRange} • CHRONOLOGICAL AXIS",
                title = tr(lang, trip.titleEn, trip.titleVi)
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            trip.moments.forEach { moment ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Left timeline rail
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(36.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(colors.accent, CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(210.dp)
                                .background(colors.border)
                        )
                    }
                    // Moment Card
                    JourneyEditorialCard(
                        imageRes = moment.photoRes,
                        tagText = "DAY ${moment.dayNumber} • ${moment.timeLabel}",
                        title = tr(lang, moment.titleEn, moment.titleVi),
                        subtitle = tr(lang, moment.noteEn, moment.noteVi),
                        metaText = "${moment.locationTag} • ${moment.coordinates}",
                        onCardClick = { onSelectMoment(moment.id) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = JourneySpacing.md),
                        heightDp = 210
                    )
                }
            }
        }
    }
}

// ============================================================================
// F5: CREATE DAILY MOMENT
// ============================================================================
@Composable
fun CreateDailyMomentScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSaveMoment: (String, String, String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var title by remember { mutableStateOf("Golden Hour Tea by the Thu Bồn River") }
    var locationTag by remember { mutableStateOf("Minh An Ward, Hội An") }
    var note by remember {
        mutableStateOf("Oolong tea on a wooden balcony overlooking the lantern boats. AI Lens identified the 19th-century merchant roof carvings.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.F5_CREATE_DAILY_MOMENT,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "DAILY MEMORY CAPTURE", "GHI LẠI KHOẢNH KHẮC TRONG NGÀY"),
                title = tr(lang, "Preserve Sight, Sound & Story", "Lưu Giữ Hình Ảnh, Âm Thanh & Cảm Xúc")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(JourneySpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    JourneyTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = tr(lang, "Moment Headline", "Tiêu đề Khoảnh khắc"),
                        placeholder = "e.g., Sunset over Ha Long",
                        testTag = "moment_title_input"
                    )
                    JourneyTextField(
                        value = locationTag,
                        onValueChange = { locationTag = it },
                        label = tr(lang, "Location & Coordinates", "Địa điểm & Tọa độ"),
                        placeholder = "Hội An, Quảng Nam",
                        leadingIcon = Icons.Default.LocationOn
                    )
                    JourneyTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = tr(lang, "Field Reflection", "Cảm nghĩ & Ghi chú"),
                        placeholder = "Write your memory...",
                        singleLine = false
                    )
                    JourneyButton(
                        text = tr(lang, "Save Moment to Timeline (F4)", "Lưu Khoảnh khắc vào Dòng thời gian (F4)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onSaveMoment(title, note, locationTag) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "submit_moment_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// F6: MEMORY DETAIL
// ============================================================================
@Composable
fun MemoryDetailScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val moment = uiState.selectedMoment

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.F6_MEMORY_DETAIL,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyEditorialCard(
                imageRes = moment.photoRes,
                tagText = "DAY ${moment.dayNumber} • ${moment.timeLabel} • ${moment.weatherTag}",
                title = tr(lang, moment.titleEn, moment.titleVi),
                subtitle = moment.locationTag,
                metaText = moment.coordinates,
                onCardClick = {},
                heightDp = 290
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Text(
                        text = tr(lang, "JOURNAL ENTRY", "BẢN GHI KÝ ỨC"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tr(lang, moment.noteEn, moment.noteVi),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.primaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                JourneyButton(
                    text = tr(lang, "Weave into AI Story (H1)", "Đưa vào Câu chuyện AI (H1)"),
                    leadingIcon = Icons.Default.AutoAwesome,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.H1_AI_STORY_PREVIEW) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Add to Film (H2)", "Thêm vào Phim (H2)"),
                    leadingIcon = Icons.Default.MovieCreation,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.H2_VIDEO_CREATOR) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ============================================================================
// G1: DISCOVERY COLLECTION (FLOW 6: Discover -> Save Collection -> Collection Detail -> Discovery Detail)
// ============================================================================
@Composable
fun DiscoveryCollectionScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectCollection: (String) -> Unit
) {
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_g1_discovery_collection")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.G1_DISCOVERY_COLLECTION,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "CURATED DISCOVERY ANTHOLOGIES", "BỘ SƯU TẬP CHUYÊN ĐỀ DI SẢN"),
                title = tr(lang, "Architectural & Cultural Collections", "Bộ Sưu Tập Kiến Trúc & Văn Hóa")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            uiState.collections.forEach { coll ->
                JourneyEditorialCard(
                    imageRes = coll.coverRes,
                    tagText = tr(lang, coll.badgeTagEn, coll.badgeTagVi),
                    title = tr(lang, coll.titleEn, coll.titleVi),
                    subtitle = tr(lang, coll.subtitleEn, coll.subtitleVi),
                    metaText = "${coll.placeIds.size} SAVED DISCOVERIES",
                    onCardClick = { onSelectCollection(coll.id) },
                    modifier = Modifier.padding(bottom = JourneySpacing.md),
                    heightDp = 240,
                    testTag = "collection_card_${coll.id}"
                )
            }
        }
    }
}

// ============================================================================
// G2: COLLECTION DETAILS
// ============================================================================
@Composable
fun CollectionDetailsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlaceForDiscoveryDetail: (String) -> Unit
) {
    val lang = uiState.language
    val coll = uiState.selectedCollection
    val items = uiState.places.filter { coll.placeIds.contains(it.id) }.ifEmpty { uiState.places }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_g2_collection_details")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.G2_COLLECTION_DETAILS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, coll.badgeTagEn, coll.badgeTagVi),
                title = tr(lang, coll.titleEn, coll.titleVi)
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            items.forEach { place ->
                JourneyEditorialCard(
                    imageRes = place.imageRes,
                    tagText = tr(lang, place.eraEn, place.eraVi),
                    title = tr(lang, place.nameEn, place.nameVi),
                    subtitle = tr(lang, place.architecturalNotesEn, place.architecturalNotesVi),
                    metaText = "${place.coordinates} • TAP FOR DEEP MONOGRAPH (D5)",
                    onCardClick = { onSelectPlaceForDiscoveryDetail(place.id) },
                    modifier = Modifier.padding(bottom = JourneySpacing.md),
                    heightDp = 240,
                    testTag = "collection_item_${place.id}"
                )
            }
        }
    }
}

// ============================================================================
// H1: AI STORY PREVIEW (Magazine Editorial Layout)
// ============================================================================
@Composable
fun AiStoryPreviewScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val trip = uiState.selectedTrip

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_h1_ai_story_preview")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.H1_AI_STORY_PREVIEW,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyBadgePill(
                text = tr(
                    lang,
                    "SAMPLE AI EDITORIAL NARRATIVE • GENERATED FROM ${trip.moments.size} MOMENTS",
                    "BẢN MẪU TẠP CHÍ AI • TỔNG HỢP TỪ ${trip.moments.size} KHOẢNH KHẮC"
                ),
                icon = Icons.Default.AutoAwesome
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = tr(lang, trip.titleEn, trip.titleVi),
                style = MaterialTheme.typography.displayMedium,
                color = colors.primaryText
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${trip.dateRange} • ${tr(lang, trip.locationEn, trip.locationVi)}",
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            JourneyEditorialCard(
                imageRes = trip.coverRes,
                tagText = "CHAPTER I • THE NORTHERN MISTS",
                title = tr(lang, "Where Stone Tortoises Guard Scholarly Memory", "Nơi Bia Đá Rùa Vàng Lưu Giữ Nguyên Khí Quốc Gia"),
                subtitle = tr(lang, trip.storyExcerptEn, trip.storyExcerptVi),
                metaText = "21.0287° N, 105.8356° E",
                onCardClick = {},
                heightDp = 260
            )

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.xl)) {
                    Text(
                        text = tr(
                            lang,
                            "“Every journey through Vietnam is a dialogue between water and stone. From the incense-draped courtyards of Văn Miếu in Hà Nội to the 500-million-year karst monoliths of Hạ Long Bay, and finally south to the silk-lantern reflections of Chùa Cầu in Hội An, our footsteps traced centuries of poets, sailors, and master timber artisans.”",
                            "“Mỗi hành trình qua dải đất hình chữ S là cuộc đối thoại giữa nước và đá. Từ sân gạch rêu phong Văn Miếu Hà Nội đến những khối núi đá vôi 500 triệu năm giữa vịnh Hạ Long, và xuôi về miền phố cảng Hội An lung linh ánh đèn lồng bên Chùa Cầu cổ kính.”"
                        ),
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.primaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))
            JourneyButton(
                text = tr(lang, "Open Cinematic Video Creator (H2)", "Chuyển sang Dựng Phim Điện Ảnh (H2)"),
                leadingIcon = Icons.Default.MovieCreation,
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.H2_VIDEO_CREATOR) },
                modifier = Modifier.fillMaxWidth(),
                testTag = "story_open_video_creator_btn"
            )
        }
    }
}

// ============================================================================
// H2: VIDEO CREATOR (Multi-Clip Scrubber, Aspect Ratio, Acoustic Score)
// ============================================================================
@Composable
fun VideoCreatorScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onUpdateSettings: (String?, String?) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val trip = uiState.selectedTrip

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_h2_video_creator")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.H2_VIDEO_CREATOR,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyEditorialCard(
                imageRes = trip.coverRes,
                tagText = "${uiState.selectedVideoAspect} • 24FPS LUT",
                title = tr(lang, trip.titleEn, trip.titleVi),
                subtitle = "Score: ${uiState.selectedVideoMusic}",
                metaText = "00:18 / 00:45 • KODAK 2383 WARM GRADE",
                onCardClick = { onNavigate(JourneyScreenId.H3_VIDEO_PREVIEW_EXPORT) },
                heightDp = 250
            )

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Multi-clip Timeline Strip
            Text(
                text = tr(lang, "CINEMATIC CLIP SEQUENCE", "CHUỖI CẢNH QUAY ĐIỆN ẢNH"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.places.forEachIndexed { idx, place ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(74.dp)
                            .clip(RoundedCornerShape(JourneyRadius.sm))
                            .border(1.5.dp, colors.accent, RoundedCornerShape(JourneyRadius.sm))
                    ) {
                        Image(
                            painter = painterResource(id = place.imageRes),
                            contentDescription = place.nameEn,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = "0${idx + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            color = JourneyPalette.Ivory,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .background(Color(0xAA111923))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Aspect Ratio Selector
            Text(
                text = tr(lang, "ASPECT RATIO & FORMAT", "TỶ LỆ KHUNG HÌNH"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("9:16 Vertical Reel", "16:9 Cinema Scope", "4:5 Editorial").forEach { aspect ->
                    val active = uiState.selectedVideoAspect == aspect
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (active) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (active) colors.accent else colors.border),
                        modifier = Modifier.clickable { onUpdateSettings(aspect, null) }
                    ) {
                        Text(
                            text = aspect,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (active) colors.onAccent else colors.primaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Music Score Selector
            Text(
                text = tr(lang, "ACOUSTIC SOUNDTRACK", "NHẠC NỀN BẢN ĐỊA"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "Đàn Tranh & Ambient Strings",
                    "Imperial Court Percussion & Flute",
                    "Misty Highland Bamboo Echoes"
                ).forEach { score ->
                    val active = uiState.selectedVideoMusic == score
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.md),
                        color = colors.surface,
                        border = BorderStroke(1.dp, if (active) colors.accent else colors.border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onUpdateSettings(null, score) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = score,
                                    tint = colors.accent
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = score, style = MaterialTheme.typography.titleSmall, color = colors.primaryText)
                            }
                            if (active) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = colors.accent
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))
            JourneyButton(
                text = tr(lang, "Preview Full-Screen & Export 4K (H3)", "Xem Toàn Màn Hình & Xuất Phim 4K (H3)"),
                leadingIcon = Icons.Default.PlayArrow,
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.H3_VIDEO_PREVIEW_EXPORT) },
                modifier = Modifier.fillMaxWidth(),
                testTag = "video_preview_export_btn"
            )
        }
    }
}

// ============================================================================
// H3: FULL-SCREEN VIDEO PREVIEW & EXPORT (No Bottom Bar per Section 5)
// ============================================================================
@Composable
fun VideoPreviewExportScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onShowExportToast: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val lang = uiState.language
    val trip = uiState.selectedTrip

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JourneyPalette.DeepObsidian)
            .testTag("screen_h3_video_preview_export")
    ) {
        Image(
            painter = painterResource(id = trip.coverRes),
            contentDescription = trip.titleEn,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x880B1017),
                            Color(0x330B1017),
                            Color(0xEE0B1017)
                        )
                    )
                )
        )

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(JourneySpacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xAA111923), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Video Preview",
                    tint = JourneyPalette.Ivory
                )
            }
            JourneyBadgePill(
                text = "H3-VIDEO-EXP • ${uiState.selectedVideoAspect} • 4K HDR",
                containerColor = Color(0xCC111923),
                contentColor = JourneyPalette.SandGold
            )
        }

        // Bottom Cinema Title Card & Export Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(JourneySpacing.xl)
        ) {
            Text(
                text = "A JOURNEY AI ORIGINAL CHRONICLE",
                style = MaterialTheme.typography.labelMedium,
                color = JourneyPalette.SandGold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = tr(lang, trip.titleEn, trip.titleVi),
                style = MaterialTheme.typography.displayMedium,
                color = JourneyPalette.Ivory
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "♪ ${uiState.selectedVideoMusic} • ${trip.dateRange}",
                style = MaterialTheme.typography.bodyMedium,
                color = JourneyPalette.Ivory.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(JourneySpacing.lg))
            LinearProgressIndicator(
                progress = { 1.0f },
                color = JourneyPalette.SandGold,
                trackColor = JourneyPalette.Slate,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Save 4K Film to Device", "Lưu Phim 4K Về Máy"),
                    leadingIcon = Icons.Default.Download,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = {
                        onShowExportToast(
                            if (lang == com.example.model.AppLanguage.EN)
                                "Exported 4K film (FastAPI /v1/video/render handoff ready)"
                            else
                                "Đã xuất phim 4K (Sẵn sàng kết nối FastAPI /v1/video/render)"
                        )
                    },
                    modifier = Modifier.weight(1f),
                    testTag = "export_save_video_btn"
                )
                JourneyButton(
                    text = tr(lang, "Share Story", "Chia sẻ"),
                    leadingIcon = Icons.Default.Share,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.F1_JOURNAL_HOME) }
                )
            }
        }
    }
}
