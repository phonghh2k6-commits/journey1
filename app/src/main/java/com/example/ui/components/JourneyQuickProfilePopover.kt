package com.example.ui.components

import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.AppLanguage
import com.example.model.JourneyScreenId
import com.example.ui.theme.JourneyElevation
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState
import java.util.Locale

// ============================================================================
// TOP APPLICATION HEADER BAR (Modeled after VitaPulse Clean Header Reference)
// Left: Brand Icon + App Name + Badge + Explorer Name
// Right: Quick AI Camera Pill + Light/Dark Toggle + Avatar Pill ("MN ⌄")
// ============================================================================
@Composable
fun JourneyAppHeaderBar(
    uiState: JourneyUiState,
    onToggleQuickProfile: () -> Unit,
    onToggleTheme: () -> Unit,
    onOpenAiCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Surface(
        color = colors.elevatedSurface,
        border = BorderStroke(0.8.dp, colors.border.copy(alpha = 0.6f)),
        shadowElevation = JourneyElevation.subtle,
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("journey_app_header_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Brand Icon + Title + User Name & Handle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(JourneyPalette.Midnight)
                        .border(1.5.dp, colors.accent, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_launcher_compass),
                        contentDescription = "Journey AI",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Journey AI",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.primaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Explore the World. Discover the Stories.",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.secondaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Right: Premium Gold Pill + Light/Dark Toggle + Avatar Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = JourneyPalette.SandGold,
                    border = BorderStroke(1.dp, Color(0xFFD5BA91)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(JourneyRadius.pill))
                        .clickable { onOpenAiCamera() }
                        .testTag("header_quick_ai_lens_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "👑 Premium",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = JourneyPalette.Midnight
                        )
                    }
                }

                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier
                        .size(36.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("header_theme_toggle_btn")
                ) {
                    Icon(
                        imageVector = if (uiState.isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Light or Dark Mode",
                        tint = colors.accent,
                        modifier = Modifier.size(17.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = colors.surface,
                    border = BorderStroke(
                        1.2.dp,
                        if (uiState.isQuickProfileOpen) colors.accent else colors.border
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(JourneyRadius.pill))
                        .clickable { onToggleQuickProfile() }
                        .testTag("header_profile_popover_trigger")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 4.dp, end = 7.dp, top = 3.dp, bottom = 3.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(colors.accent, CircleShape)
                        ) {
                            if (uiState.userAvatarUri != null) {
                                coil.compose.AsyncImage(
                                    model = uiState.userAvatarUri,
                                    contentDescription = uiState.userName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(30.dp)
                                )
                            } else {
                                Text(
                                    text = uiState.userInitials,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onAccent
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = if (uiState.isQuickProfileOpen) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Toggle Quick Profile Popover",
                            tint = colors.primaryText,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// FLOATING QUICK PROFILE & SETTINGS CARD POPOVER
// Matches the exact card structure of the user's VitaPulse reference image:
// 1. User Identity Header (MN • Minh Nguyễn • Hội viên Journey+ • 28 điểm di sản)
// 2. Chế độ xem tối giản (Minimal View Toggle Card)
// 3. Thuyết minh giọng nói AI (Voice Guide Toggle + "Nghe thử" Test Audio Button)
// 4. Giao diện & Ngôn ngữ (Light/Dark + VI/EN)
// 5. Cỡ chữ hiển thị (Vừa / Lớn / Rất lớn Segmented Control)
// 6. Quick Action Links (Bộ sưu tập & Địa điểm đã lưu, Thông báo di sản, Quyền riêng tư & Dữ liệu)
// ============================================================================
@Composable
fun JourneyQuickProfilePopover(
    uiState: JourneyUiState,
    onDismiss: () -> Unit,
    onToggleMinimalView: () -> Unit,
    onToggleVoicePlayback: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onSelectFontScale: (Int) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = JourneyTheme.colors
    val lang = uiState.language

    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context) {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                engine?.language = if (lang == AppLanguage.VI) Locale("vi", "VN") else Locale.US
            }
        }
        ttsEngine = engine
        onDispose {
            engine?.stop()
            engine?.shutdown()
        }
    }

    AnimatedVisibility(
        visible = uiState.isQuickProfileOpen,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = colors.elevatedSurface,
            border = BorderStroke(1.dp, colors.border),
            shadowElevation = JourneyElevation.modal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .testTag("journey_quick_profile_popover")
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1. Explorer Identity Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(46.dp)
                            .background(colors.accentContainer, CircleShape)
                            .border(1.dp, colors.accent.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Text(
                            text = "MN",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = colors.accent
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = uiState.userName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = colors.primaryText
                        )
                        Text(
                            text = tr(
                                lang,
                                "Journey+ Patron • 28 Heritage Scans",
                                "Hội viên Journey+ • 28 điểm di sản đã quét"
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                    }
                }

                // 2. Chế độ xem tối giản (Minimal View Card)
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.md),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.subtleBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RemoveRedEye,
                                    contentDescription = null,
                                    tint = colors.accent,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = tr(lang, "Minimalist View Mode", "Chế độ xem tối giản"),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.primaryText
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (uiState.isMinimalViewEnabled) colors.accent else colors.elevatedSurface,
                                border = BorderStroke(
                                    1.dp,
                                    if (uiState.isMinimalViewEnabled) colors.accent else colors.border
                                ),
                                modifier = Modifier
                                    .clickable { onToggleMinimalView() }
                                    .testTag("popover_minimal_view_toggle")
                            ) {
                                Text(
                                    text = if (uiState.isMinimalViewEnabled) {
                                        tr(lang, "Enabled", "Đang bật")
                                    } else {
                                        tr(lang, "Disabled", "Đang tắt")
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (uiState.isMinimalViewEnabled) colors.onAccent else colors.primaryText,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = tr(
                                lang,
                                "Keep only Featured Monument, Today's Journal & Quick AI Scan with large touch targets.",
                                "Chỉ giữ lại Điểm di sản chính, Nhật ký hôm nay & Quét AI nhanh với nút bấm cỡ lớn."
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                    }
                }

                // 3. Thuyết minh Giọng nói AI (Voice Narration Confirmation + "Nghe thử" Button)
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.md),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.subtleBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = null,
                                    tint = colors.accent,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = tr(lang, "AI Voice Narration", "Giọng nói thuyết minh"),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.primaryText
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (uiState.isVoicePlaying) colors.accent else colors.elevatedSurface,
                                border = BorderStroke(
                                    1.dp,
                                    if (uiState.isVoicePlaying) colors.accent else colors.border
                                ),
                                modifier = Modifier
                                    .clickable { onToggleVoicePlayback() }
                                    .testTag("popover_voice_toggle")
                            ) {
                                Text(
                                    text = if (uiState.isVoicePlaying) {
                                        tr(lang, "Enabled", "Đang bật")
                                    } else {
                                        tr(lang, "Muted", "Đang tắt")
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (uiState.isVoicePlaying) colors.onAccent else colors.primaryText,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tr(
                                    lang,
                                    "Read historical stories aloud when scanning monuments or opening map pins.",
                                    "Đọc to câu chuyện lịch sử khi quét di tích hoặc chạm vào điểm đến."
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = colors.elevatedSurface,
                                border = BorderStroke(1.dp, colors.border),
                                modifier = Modifier
                                    .clickable {
                                        val sample = if (lang == AppLanguage.VI) {
                                            "Chào mừng Minh Quân đến với Journey AI. Chùa Cầu Hội An cách bạn một trăm tám mươi mét."
                                        } else {
                                            "Welcome to Journey AI. The Japanese Covered Bridge in Hoi An is one hundred eighty meters away."
                                        }
                                        ttsEngine?.language = if (lang == AppLanguage.VI) Locale("vi", "VN") else Locale.US
                                        ttsEngine?.speak(sample, TextToSpeech.QUEUE_FLUSH, null, "popover_test")
                                    }
                                    .testTag("popover_test_voice_btn")
                            ) {
                                Text(
                                    text = tr(lang, "Test Voice", "Nghe thử"),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = colors.primaryText,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                )
                            }
                        }
                    }
                }

                // 4. Cỡ chữ hiển thị (Vừa / Lớn / Rất lớn — 3-Segment Control exactly like reference)
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.md),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.subtleBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TextFields,
                                contentDescription = null,
                                tint = colors.accent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tr(lang, "Display Text Size", "Cỡ chữ hiển thị"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.primaryText
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                0 to tr(lang, "Medium", "Vừa"),
                                1 to tr(lang, "Large", "Lớn"),
                                2 to tr(lang, "Extra Large", "Rất lớn")
                            ).forEach { (step, label) ->
                                val selected = uiState.fontScaleStep == step
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (selected) colors.accent else colors.elevatedSurface,
                                    border = BorderStroke(
                                        1.dp,
                                        if (selected) colors.accent else colors.border
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { onSelectFontScale(step) }
                                        .testTag("popover_font_scale_$step")
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = if (selected) colors.onAccent else colors.primaryText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 5. Giao diện Sáng/Tối & Ngôn ngữ (Quick Segmented Row)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onToggleTheme() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = if (uiState.isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = colors.accent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (uiState.isDarkTheme) tr(lang, "Dark Mode", "Giao diện Tối")
                                else tr(lang, "Light Mode", "Giao diện Sáng"),
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.primaryText
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onToggleLanguage() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = colors.accent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (lang == AppLanguage.VI) "Tiếng Việt (VI)" else "English (EN)",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.primaryText
                            )
                        }
                    }
                }

                HorizontalDivider(color = colors.subtleBorder)

                // 6. Multi-Platform Public Share & Download Box (iOS / Android / Web)
                val publicShareUrl = "https://ais-pre-gbtnbd7odkjdopaa2pnjsy-314739119631.asia-northeast1.run.app"
                var copiedLink by remember { mutableStateOf(false) }

                Surface(
                    shape = RoundedCornerShape(JourneyRadius.md),
                    color = colors.accentContainer.copy(alpha = 0.55f),
                    border = BorderStroke(1.2.dp, colors.accent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = tr(
                                lang,
                                "📲 Share & Install on iOS / Android / Web",
                                "📲 Chia sẻ cho mọi người tải về (iOS / Android / Web)"
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = colors.primaryText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tr(
                                lang,
                                "iOS users: Open link in Safari → Share → Add to Home Screen. Android users: Install Web App or download .APK.",
                                "Người dùng iPhone (iOS): Mở link bằng Safari → Chia sẻ → Thêm vào Màn hình chính. Android: Cài đặt Web App hoặc tải file .APK."
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = colors.accent,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                                        clipboard?.setPrimaryClip(
                                            android.content.ClipData.newPlainText("Journey AI Link", publicShareUrl)
                                        )
                                        copiedLink = true
                                    }
                                    .testTag("popover_copy_public_link_btn")
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 9.dp, horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = if (copiedLink) {
                                            tr(lang, "✓ Link Copied!", "✓ Đã sao chép Link!")
                                        } else {
                                            tr(lang, "Copy Public Link", "Sao chép Link Tải")
                                        },
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onAccent
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = colors.elevatedSurface,
                                border = BorderStroke(1.dp, colors.border),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        val sendIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(
                                                android.content.Intent.EXTRA_TEXT,
                                                "Tải & trải nghiệm ứng dụng du lịch thông minh JOURNEY AI (Hỗ trợ iOS, Android & Web): $publicShareUrl"
                                            )
                                        }
                                        context.startActivity(
                                            android.content.Intent.createChooser(sendIntent, "Chia sẻ Journey AI")
                                        )
                                    }
                                    .testTag("popover_share_public_link_btn")
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 9.dp, horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = tr(lang, "Share to Friends", "Gửi cho bạn bè"),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colors.primaryText
                                    )
                                }
                            }
                        }
                    }
                }

                // 7. Quick Navigation Rows matching VitaPulse bottom rows
                val savedCount = uiState.places.count { it.isSaved }
                val quickLinks = listOf(
                    Triple(
                        Icons.Default.CollectionsBookmark,
                        tr(lang, "Saved Places & Collections ($savedCount)", "Hồ sơ Di sản & Địa điểm đã lưu ($savedCount)"),
                        JourneyScreenId.C4_SAVED_PLACES
                    ),
                    Triple(
                        Icons.Default.NotificationsNone,
                        tr(lang, "Proximity Heritage Alerts", "Cài đặt thông báo di sản gần bạn"),
                        JourneyScreenId.B3_NOTIFICATIONS
                    ),
                    Triple(
                        Icons.Default.Security,
                        tr(lang, "Privacy & Data Sovereignty", "Quyền riêng tư & Dữ liệu"),
                        JourneyScreenId.I4_PRIVACY_AND_DATA
                    )
                )

                quickLinks.forEach { (icon, title, target) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onDismiss()
                                onNavigate(target)
                            }
                            .padding(vertical = 8.dp, horizontal = 6.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = colors.accent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.primaryText
                        )
                    }
                }
            }
        }
    }
}

