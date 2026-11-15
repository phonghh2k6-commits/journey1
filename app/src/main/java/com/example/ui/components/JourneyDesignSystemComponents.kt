package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.model.JourneySampleRepository
import com.example.model.JourneyScreenId
import com.example.model.PrimaryTab
import com.example.model.PrototypeFlowSpec
import com.example.model.ScreenGroup
import com.example.model.ScreenStateMode
import com.example.model.ViewportPreset
import com.example.ui.theme.JourneyElevation
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme

// ============================================================================
// BILINGUAL STRING HELPER
// ============================================================================
@Composable
fun tr(lang: AppLanguage, en: String, vi: String): String {
    return if (lang == AppLanguage.EN) en else vi
}

// ============================================================================
// BUTTON VARIANTS (Primary Sand Gold, Secondary Outline, Ghost, Danger)
// ============================================================================
enum class JourneyButtonVariant {
    PRIMARY,
    SECONDARY,
    GHOST,
    SURFACE_TONAL
}

@Composable
fun JourneyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: JourneyButtonVariant = JourneyButtonVariant.PRIMARY,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String = "journey_button"
) {
    val colors = JourneyTheme.colors
    val shape = RoundedCornerShape(JourneyRadius.pill)

    when (variant) {
        JourneyButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.accent,
                    contentColor = colors.onAccent,
                    disabledContainerColor = colors.subtleBorder,
                    disabledContentColor = colors.mutedText
                ),
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 14.dp),
                modifier = modifier
                    .minimumInteractiveComponentSize()
                    .testTag(testTag)
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = text,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = text,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        JourneyButtonVariant.SECONDARY -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled,
                shape = shape,
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.7f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.primaryText
                ),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 13.dp),
                modifier = modifier
                    .minimumInteractiveComponentSize()
                    .testTag(testTag)
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = text,
                        tint = colors.accent,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        JourneyButtonVariant.SURFACE_TONAL -> {
            Button(
                onClick = onClick,
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.elevatedSurface,
                    contentColor = colors.primaryText
                ),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
                modifier = modifier
                    .minimumInteractiveComponentSize()
                    .testTag(testTag)
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = text,
                        tint = colors.accent,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text = text, style = MaterialTheme.typography.labelLarge)
            }
        }

        JourneyButtonVariant.GHOST -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .minimumInteractiveComponentSize()
                    .clip(shape)
                    .clickable(enabled = enabled, onClick = onClick)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .testTag(testTag)
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = text,
                        tint = colors.accent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.accent
                )
            }
        }
    }
}

// ============================================================================
// INPUT COMPONENT VARIANT
// ============================================================================
@Composable
fun JourneyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    testTag: String = "journey_input"
) {
    val colors = JourneyTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.bodySmall) },
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.mutedText
            )
        },
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = label,
                    tint = colors.accent
                )
            }
        } else null,
        singleLine = singleLine,
        shape = RoundedCornerShape(JourneyRadius.md),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colors.surface,
            unfocusedContainerColor = colors.surface.copy(alpha = 0.7f),
            focusedBorderColor = colors.accent,
            unfocusedBorderColor = colors.border,
            focusedTextColor = colors.primaryText,
            unfocusedTextColor = colors.primaryText,
            focusedLabelColor = colors.accent,
            unfocusedLabelColor = colors.secondaryText,
            cursorColor = colors.accent
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}

// ============================================================================
// EDITORIAL SECTION HEADER & BADGE PILLS
// ============================================================================
@Composable
fun JourneySectionHeader(
    eyebrow: String,
    title: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val colors = JourneyTheme.colors
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = eyebrow.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = colors.primaryText
            )
        }
        if (actionLabel != null && onActionClick != null) {
            JourneyButton(
                text = actionLabel,
                onClick = onActionClick,
                variant = JourneyButtonVariant.GHOST,
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                testTag = "section_action_${eyebrow.lowercase().replace(" ", "_")}"
            )
        }
    }
}

@Composable
fun JourneyBadgePill(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = JourneyTheme.colors.accentContainer,
    contentColor: Color = JourneyTheme.colors.accent,
    icon: ImageVector? = null
) {
    Surface(
        shape = RoundedCornerShape(JourneyRadius.pill),
        color = containerColor,
        border = BorderStroke(0.8.dp, contentColor.copy(alpha = 0.35f)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = contentColor,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor
            )
        }
    }
}

// ============================================================================
// EDITORIAL PHOTOGRAPHY CARD WITH SUBTLE SCRIM
// ============================================================================
@Composable
fun JourneyEditorialCard(
    @DrawableRes imageRes: Int,
    tagText: String,
    title: String,
    subtitle: String,
    metaText: String,
    isSaved: Boolean = false,
    onCardClick: () -> Unit,
    onSaveClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    heightDp: Int = 260,
    testTag: String = "editorial_card"
) {
    val colors = JourneyTheme.colors
    Surface(
        shape = RoundedCornerShape(JourneyRadius.lg),
        color = colors.surface,
        border = BorderStroke(1.dp, colors.border.copy(alpha = 0.55f)),
        shadowElevation = JourneyElevation.card,
        modifier = modifier
            .fillMaxWidth()
            .height(heightDp.dp)
            .clip(RoundedCornerShape(JourneyRadius.lg))
            .clickable(onClick = onCardClick)
            .testTag(testTag)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Warm editorial dark scrim for legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x220B1017),
                                Color(0x660B1017),
                                Color(0xEB0B1017)
                            )
                        )
                    )
            )

            // Top row: category badge + save button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(JourneySpacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                JourneyBadgePill(
                    text = tagText,
                    containerColor = Color(0xAA111923),
                    contentColor = JourneyPalette.SandGold
                )
                if (onSaveClick != null) {
                    IconButton(
                        onClick = onSaveClick,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0x99111923), CircleShape)
                            .testTag("${testTag}_save")
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save place",
                            tint = if (isSaved) JourneyPalette.SandGold else JourneyPalette.Ivory,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Bottom editorial caption
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(JourneySpacing.lg)
            ) {
                Text(
                    text = metaText,
                    style = MaterialTheme.typography.labelSmall,
                    color = JourneyPalette.SandGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = JourneyPalette.Ivory,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = JourneyPalette.Ivory.copy(alpha = 0.82f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// ============================================================================
// TOP SCREEN APP BAR (Clean Consumer App Header)
// ============================================================================
@Composable
fun JourneyTopBar(
    screenId: JourneyScreenId,
    language: AppLanguage,
    onBackClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    val colors = JourneyTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = JourneySpacing.md, vertical = JourneySpacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(42.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.primaryText,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column {
                Text(
                    text = tr(language, screenId.group.titleEn, screenId.group.titleVi).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.accent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = tr(language, screenId.titleEn, screenId.titleVi),
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.primaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (trailingContent != null) {
            trailingContent()
        }
    }
}

// ============================================================================
// SECTION 5: 5-DESTINATION PRIMARY BOTTOM NAVIGATION WITH CENTRAL CAMERA LENS
// ============================================================================
@Composable
fun JourneyBottomNavigationBar(
    currentScreen: JourneyScreenId,
    language: AppLanguage,
    onTabSelected: (PrimaryTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = JourneyTheme.colors
    val activeTab = currentScreen.primaryTab ?: PrimaryTab.HOME

    Surface(
        color = colors.secondaryBackground.copy(alpha = 0.96f),
        border = BorderStroke(1.dp, colors.border),
        shadowElevation = JourneyElevation.floating,
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("journey_bottom_navigation")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryTab.entries.forEach { tab ->
                val isSelected = activeTab == tab
                val label = tr(language, tab.labelEn, tab.labelVi)

                if (tab == PrimaryTab.DISCOVER) {
                    // Prominent Central AI Camera Action
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clickable { onTabSelected(tab) }
                            .testTag("nav_tab_discover_camera")
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(54.dp)
                                .shadow(10.dp, CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            JourneyPalette.SandGold,
                                            Color(0xFFD5BA91)
                                        )
                                    ),
                                    CircleShape
                                )
                                .border(2.dp, colors.primaryBackground, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = label,
                                tint = JourneyPalette.Midnight,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.accent
                        )
                    }
                } else {
                    val icon = when (tab) {
                        PrimaryTab.HOME -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
                        PrimaryTab.EXPLORE -> if (isSelected) Icons.Filled.Explore else Icons.Outlined.Explore
                        PrimaryTab.JOURNAL -> if (isSelected) Icons.Filled.MenuBook else Icons.Outlined.MenuBook
                        PrimaryTab.PROFILE -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
                        PrimaryTab.DISCOVER -> Icons.Filled.CameraAlt
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clip(RoundedCornerShape(JourneyRadius.md))
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("nav_tab_${tab.name.lowercase()}")
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = if (isSelected) colors.accent else colors.secondaryText,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) colors.primaryText else colors.secondaryText,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(3.dp))
                            Box(
                                modifier = Modifier
                                    .width(16.dp)
                                    .height(2.dp)
                                    .background(colors.accent, CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// SECTION 7: ALL 9 SCREEN STATES BANNER & INTERACTIVE STATE OVERLAY
// ============================================================================
@Composable
fun JourneyScreenStateOverlay(
    stateMode: ScreenStateMode,
    language: AppLanguage,
    onResetToDefault: () -> Unit,
    onNavigateToSubscription: () -> Unit,
    onNavigateToPermissions: () -> Unit,
    defaultContent: @Composable () -> Unit
) {
    val colors = JourneyTheme.colors

    when (stateMode) {
        ScreenStateMode.DEFAULT -> {
            defaultContent()
        }

        ScreenStateMode.SUCCESS -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Surface(
                    color = colors.success.copy(alpha = 0.16f),
                    border = BorderStroke(1.dp, colors.success.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(JourneyRadius.md),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = JourneySpacing.md, vertical = JourneySpacing.xs)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = colors.success
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(language, "STATE: SUCCESS SYNCHRONIZED", "TRẠNG THÁI: ĐỒNG BỘ THÀNH CÔNG"),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.success
                            )
                            Text(
                                text = tr(
                                    language,
                                    "Supabase cloud journal & offline heritage pack verified.",
                                    "Nhật ký đám mây Supabase & gói di sản ngoại tuyến đã đồng bộ."
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.primaryText
                            )
                        }
                        JourneyButton(
                            text = tr(language, "Dismiss", "Đóng"),
                            onClick = onResetToDefault,
                            variant = JourneyButtonVariant.GHOST
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    defaultContent()
                }
            }
        }

        ScreenStateMode.PARTIAL_DATA -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Surface(
                    color = colors.warning.copy(alpha = 0.16f),
                    border = BorderStroke(1.dp, colors.warning.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(JourneyRadius.md),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = JourneySpacing.md, vertical = JourneySpacing.xs)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "Partial Data",
                            tint = colors.warning
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(
                                    language,
                                    "PARTIAL DATA • AI CONFIDENCE 64%",
                                    "DỮ LIỆU MỘT PHẦN • ĐỘ TIN CẬY AI 64%"
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.warning
                            )
                            Text(
                                text = tr(
                                    language,
                                    "Low-light angle detected. Showing cached architectural metadata while acoustic guide buffers.",
                                    "Góc chụp thiếu sáng. Đang hiển thị siêu dữ liệu lưu sẵn trong khi tải thuyết minh."
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.primaryText
                            )
                        }
                        JourneyButton(
                            text = tr(language, "Full Mode", "Đầy đủ"),
                            onClick = onResetToDefault,
                            variant = JourneyButtonVariant.GHOST
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    defaultContent()
                }
            }
        }

        ScreenStateMode.LOADING -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(JourneySpacing.xl)
            ) {
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.xl),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(JourneySpacing.xxl)
                    ) {
                        CircularProgressIndicator(
                            color = colors.accent,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(JourneySpacing.lg))
                        Text(
                            text = tr(language, "SYNTHESIZING CULTURAL ARCHIVE", "ĐANG TỔNG HỢP TƯ LIỆU DI SẢN"),
                            style = MaterialTheme.typography.labelMedium,
                            color = colors.accent
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tr(
                                language,
                                "Loading high-resolution spatial tiles, dynasty chronology, and bilingual voice narration...",
                                "Đang tải bản đồ không gian độ phân giải cao, niên biểu triều đại và giọng đọc thuyết minh..."
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.secondaryText
                        )
                        Spacer(modifier = Modifier.height(JourneySpacing.lg))
                        // Skeleton shimmer bars
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(if (it == 2) 0.65f else 1f)
                                    .height(14.dp)
                                    .background(colors.elevatedSurface, RoundedCornerShape(JourneyRadius.xs))
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        Spacer(modifier = Modifier.height(JourneySpacing.md))
                        JourneyButton(
                            text = tr(language, "Return to Default State", "Về Trạng thái Mặc định"),
                            onClick = onResetToDefault,
                            variant = JourneyButtonVariant.SECONDARY
                        )
                    }
                }
            }
        }

        ScreenStateMode.EMPTY -> {
            StateCardTemplate(
                icon = Icons.Default.CompassCalibration,
                accentColor = colors.sage,
                badge = tr(language, "EMPTY STATE SPEC", "TRẠNG THÁI TRỐNG"),
                title = tr(language, "Your Horizon Awaits First Discovery", "Chân trời Mới Đang Chờ Bạn Khám Phá"),
                body = tr(
                    language,
                    "No landmarks, journal moments, or saved collections match this view yet. Point the AI Camera at any monument or start a new Vietnam itinerary.",
                    "Chưa có địa danh, khoảnh khắc nhật ký hoặc bộ sưu tập nào tại đây. Hãy mở Ống kính AI hoặc tạo hành trình mới."
                ),
                primaryActionLabel = tr(language, "Populate Sample Data", "Khôi phục Dữ liệu Mẫu"),
                onPrimaryAction = onResetToDefault
            )
        }

        ScreenStateMode.ERROR -> {
            StateCardTemplate(
                icon = Icons.Default.ErrorOutline,
                accentColor = colors.error,
                badge = tr(language, "FASTAPI / VISION PIPELINE ERROR", "LỖI KẾT NỐI DỊCH VỤ"),
                title = tr(language, "Unable to Complete Recognition Pass", "Không Thể Hoàn Tất Phân Tích Hình Ảnh"),
                body = tr(
                    language,
                    "FastAPI gateway timeout (HTTP 504) or corrupted image upload frame. Your local capture is safely preserved in offline draft storage.",
                    "Quá thời gian phản hồi từ cổng FastAPI (HTTP 504) hoặc khung hình tải lên bị gián đoạn. Ảnh chụp đã được lưu an toàn vào bộ nhớ nháp."
                ),
                primaryActionLabel = tr(language, "Retry Request", "Thử lại Kết nối"),
                onPrimaryAction = onResetToDefault
            )
        }

        ScreenStateMode.OFFLINE -> {
            StateCardTemplate(
                icon = Icons.Default.CloudOff,
                accentColor = colors.warning,
                badge = tr(language, "OFFLINE EXPEDITION MODE", "CHẾ ĐỘ NGOẠI TUYẾN"),
                title = tr(language, "Disconnected in Remote Highlands", "Mất Kết Nối Mạng Tại Vùng Cao"),
                body = tr(
                    language,
                    "Active network unavailable. Journey AI has switched to your cached Vietnam Heritage Pack (Hà Nội, Hạ Long, Hội An, Sa Pa) and local SQLite journal queue.",
                    "Không có kết nối Internet. Journey AI đã tự động chuyển sang Gói Di sản Ngoại tuyến và hàng đợi đồng bộ cục bộ."
                ),
                primaryActionLabel = tr(language, "Reconnect Online", "Kết nối Lại"),
                onPrimaryAction = onResetToDefault
            )
        }

        ScreenStateMode.PERMISSION_DENIED -> {
            StateCardTemplate(
                icon = Icons.Default.WarningAmber,
                accentColor = colors.warning,
                badge = tr(language, "HARDWARE PERMISSION REQUIRED", "YÊU CẦU QUYỀN TRUY CẬP THIẾT BỊ"),
                title = tr(language, "Camera, Location or Microphone Restricted", "Quyền Máy Ảnh, Vị Trí Hoặc Micro Bị Hạn Chế"),
                body = tr(
                    language,
                    "Visual landmark recognition requires Camera access, spatial map discovery requires Location, and hands-free Voice Guide requires Microphone permission.",
                    "Nhận diện địa danh cần quyền Máy ảnh, bản đồ khám phá cần Vị trí và thuyết minh giọng nói cần quyền Micro."
                ),
                primaryActionLabel = tr(language, "Open Permission Setup (A4)", "Mở Cấp quyền Truy cập (A4)"),
                onPrimaryAction = {
                    onResetToDefault()
                    onNavigateToPermissions()
                },
                secondaryActionLabel = tr(language, "Grant & Resume", "Cấp quyền & Tiếp tục"),
                onSecondaryAction = onResetToDefault
            )
        }

        ScreenStateMode.PREMIUM_LOCKED -> {
            StateCardTemplate(
                icon = Icons.Default.Lock,
                accentColor = colors.accent,
                badge = tr(language, "JOURNEY+ ATELIER EXCLUSIVE", "ĐẶC QUYỀN HỘI VIÊN JOURNEY+"),
                title = tr(language, "Unlock 4K Cinema Export & Offline Voice Packs", "Mở Khóa Xuất Phim 4K & Thuyết Minh Ngoại Tuyến"),
                body = tr(
                    language,
                    "Unlimited AI architectural captures, lossless 60fps travel film rendering, and regional Vietnamese scholar narrations are included with Journey+ Membership.",
                    "Chụp kiến trúc AI không giới hạn, dựng phim hành trình 4K 60fps và kho thuyết minh chuyên sâu dành riêng cho hội viên Journey+."
                ),
                primaryActionLabel = tr(language, "Compare Journey+ Plans (I3)", "Xem Gói Hội Viên Journey+ (I3)"),
                onPrimaryAction = {
                    onResetToDefault()
                    onNavigateToSubscription()
                },
                secondaryActionLabel = tr(language, "Preview Unlocked Screen", "Xem trước Màn hình Mở khóa"),
                onSecondaryAction = onResetToDefault
            )
        }
    }
}

@Composable
private fun StateCardTemplate(
    icon: ImageVector,
    accentColor: Color,
    badge: String,
    title: String,
    body: String,
    primaryActionLabel: String,
    onPrimaryAction: () -> Unit,
    secondaryActionLabel: String? = null,
    onSecondaryAction: (() -> Unit)? = null
) {
    val colors = JourneyTheme.colors
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(JourneySpacing.xl)
    ) {
        Surface(
            shape = RoundedCornerShape(JourneyRadius.xl),
            color = colors.surface,
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.45f)),
            shadowElevation = JourneyElevation.card,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(JourneySpacing.xxl)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(64.dp)
                        .background(accentColor.copy(alpha = 0.14f), CircleShape)
                        .border(1.dp, accentColor.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = badge,
                        tint = accentColor,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(JourneySpacing.md))
                JourneyBadgePill(
                    text = badge,
                    containerColor = accentColor.copy(alpha = 0.15f),
                    contentColor = accentColor
                )
                Spacer(modifier = Modifier.height(JourneySpacing.sm))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = colors.primaryText
                )
                Spacer(modifier = Modifier.height(JourneySpacing.xs))
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.secondaryText
                )
                Spacer(modifier = Modifier.height(JourneySpacing.xl))
                JourneyButton(
                    text = primaryActionLabel,
                    onClick = onPrimaryAction,
                    variant = JourneyButtonVariant.PRIMARY,
                    modifier = Modifier.fillMaxWidth()
                )
                if (secondaryActionLabel != null && onSecondaryAction != null) {
                    Spacer(modifier = Modifier.height(JourneySpacing.xs))
                    JourneyButton(
                        text = secondaryActionLabel,
                        onClick = onSecondaryAction,
                        variant = JourneyButtonVariant.GHOST
                    )
                }
            }
        }
    }
}

// ============================================================================
// COMPACT PROTOTYPE COMMAND DOCK & DRAWER
// (Allows instant inspection of all 34 screens, 7 flows, 9 states, Dark/Light, EN/VI, Responsive widths)
// ============================================================================
@Composable
fun JourneyPrototypeCommandBar(
    currentScreen: JourneyScreenId,
    isDarkTheme: Boolean,
    language: AppLanguage,
    stateMode: ScreenStateMode,
    viewportPreset: ViewportPreset,
    activeFlow: PrototypeFlowSpec?,
    activeFlowStepIndex: Int,
    isInspectorExpanded: Boolean,
    onToggleInspector: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onAdvanceFlow: () -> Unit,
    onExitFlow: () -> Unit
) {
    val colors = JourneyTheme.colors
    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = colors.secondaryBackground,
            border = BorderStroke(0.8.dp, colors.border),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left: Screen Jump & Inspector Drawer button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(JourneyRadius.pill))
                        .background(colors.surface)
                        .border(1.dp, colors.accent.copy(alpha = 0.5f), RoundedCornerShape(JourneyRadius.pill))
                        .clickable { onToggleInspector() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("prototype_inspector_toggle")
                ) {
                    Icon(
                        imageVector = Icons.Default.Layers,
                        contentDescription = "Design System & 34 Screens Inspector",
                        tint = colors.accent,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${currentScreen.code} • 34 Screens / 7 Flows",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.primaryText
                    )
                }

                // Right quick toggles: State indicator, EN/VI, Dark/Light
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (stateMode != ScreenStateMode.DEFAULT) {
                        JourneyBadgePill(
                            text = stateMode.name,
                            containerColor = colors.warning.copy(alpha = 0.2f),
                            contentColor = colors.warning
                        )
                    }
                    // Language Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(JourneyRadius.pill))
                            .background(colors.surface)
                            .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.pill))
                            .clickable { onToggleLanguage() }
                            .padding(horizontal = 9.dp, vertical = 5.dp)
                            .testTag("toggle_language_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Switch Language",
                            tint = colors.accent,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = language.code,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.primaryText
                        )
                    }
                    // Dark/Light Theme Pill
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier
                            .size(30.dp)
                            .background(colors.surface, CircleShape)
                            .border(1.dp, colors.border, CircleShape)
                            .testTag("toggle_theme_button")
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Dark or Light Mode",
                            tint = colors.accent,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }

        // Active Flow Stepper Banner (if user is running one of FLOW 1..7)
        AnimatedVisibility(
            visible = activeFlow != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            if (activeFlow != null) {
                Surface(
                    color = colors.accentContainer,
                    border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${tr(language, activeFlow.titleEn, activeFlow.titleVi)} (${activeFlowStepIndex + 1}/${activeFlow.steps.size})",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent
                            )
                            Text(
                                text = tr(language, activeFlow.descriptionEn, activeFlow.descriptionVi),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.primaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            JourneyButton(
                                text = if (activeFlowStepIndex + 1 < activeFlow.steps.size) {
                                    tr(language, "Next Step", "Bước tiếp")
                                } else {
                                    tr(language, "Finish", "Hoàn tất")
                                },
                                onClick = onAdvanceFlow,
                                variant = JourneyButtonVariant.PRIMARY,
                                testTag = "guided_flow_next_button"
                            )
                            IconButton(onClick = onExitFlow) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Exit Guided Flow",
                                    tint = colors.secondaryText,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
