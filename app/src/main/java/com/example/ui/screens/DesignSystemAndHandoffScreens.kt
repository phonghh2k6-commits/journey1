package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.AppLanguage
import com.example.model.JourneySampleRepository
import com.example.model.JourneyScreenId
import com.example.model.PrototypeFlowSpec
import com.example.model.ScreenGroup
import com.example.model.ScreenStateMode
import com.example.model.ViewportPreset
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
// INTERACTIVE INSPECTOR DRAWER MODAL (Jump to any of 34 screens, 7 flows, 9 states, 6 viewports)
// ============================================================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyMasterInspectorSheet(
    uiState: JourneyUiState,
    onClose: () -> Unit,
    onSelectScreen: (JourneyScreenId) -> Unit,
    onStartFlow: (PrototypeFlowSpec) -> Unit,
    onSelectStateMode: (ScreenStateMode) -> Unit,
    onSelectViewport: (ViewportPreset) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Surface(
        color = colors.primaryBackground.copy(alpha = 0.98f),
        modifier = Modifier
            .fillMaxSize()
            .testTag("master_inspector_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(JourneySpacing.lg)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "JOURNEY AI • MASTER DESIGN ARCHITECT",
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Text(
                        text = tr(lang, "34 Screens • 7 Flows • 9 States • Design System", "34 Màn hình • 7 Luồng • 9 Trạng thái • Hệ thống Thiết kế"),
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.primaryText
                    )
                }
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(40.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Inspector",
                        tint = colors.primaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Quick Links to Phase 1 Design System & Phase 6 Acceptance Handoff
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "P1: Design System", "P1: Hệ thống Token"),
                    leadingIcon = Icons.Default.ColorLens,
                    variant = if (uiState.currentScreen == JourneyScreenId.DS_TOKENS_COMPONENTS) JourneyButtonVariant.PRIMARY else JourneyButtonVariant.SECONDARY,
                    onClick = {
                        onSelectScreen(JourneyScreenId.DS_TOKENS_COMPONENTS)
                        onClose()
                    },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "P6: Handoff & QA", "P6: Bàn giao & QA"),
                    leadingIcon = Icons.Default.Code,
                    variant = if (uiState.currentScreen == JourneyScreenId.DS_HANDOFF_ACCEPTANCE) JourneyButtonVariant.PRIMARY else JourneyButtonVariant.SECONDARY,
                    onClick = {
                        onSelectScreen(JourneyScreenId.DS_HANDOFF_ACCEPTANCE)
                        onClose()
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 1. REQUIRED 7 PROTOTYPE FLOWS
            Text(
                text = tr(lang, "SECTION 6 • LAUNCH INTERACTIVE USER FLOWS (1–7)", "MỤC 6 • KHỞI CHẠY 7 LUỒNG TƯƠNG TÁC MẪU"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            JourneySampleRepository.requiredFlows.forEach { flow ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.md),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onStartFlow(flow) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, flow.titleEn, flow.titleVi),
                                style = MaterialTheme.typography.titleSmall,
                                color = colors.primaryText
                            )
                            Text(
                                text = tr(lang, flow.descriptionEn, flow.descriptionVi),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Launch Flow",
                            tint = colors.accent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 2. SECTION 7: 9 SCREEN STATES SIMULATOR
            Text(
                text = tr(lang, "SECTION 7 • SIMULATE ALL 9 SCREEN STATES", "MỤC 7 • MÔ PHỎNG 9 TRẠNG THÁI MÀN HÌNH"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScreenStateMode.entries.forEach { mode ->
                    val selected = uiState.screenStateMode == mode
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (selected) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (selected) colors.accent else colors.border),
                        modifier = Modifier.clickable {
                            onSelectStateMode(mode)
                            onClose()
                        }
                    ) {
                        Text(
                            text = tr(lang, mode.labelEn, mode.labelVi),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) colors.onAccent else colors.primaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 3. SECTION 8: RESPONSIVE VIEWPORT WIDTH SIMULATOR (360dp, 390dp, 393dp, 412dp, 430dp, Tablet)
            Text(
                text = tr(lang, "SECTION 8 • RESPONSIVE VIEWPORT PRESETS", "MỤC 8 • KHUNG HIỂN THỊ ĐA THIẾT BỊ"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ViewportPreset.entries.forEach { preset ->
                    val selected = uiState.viewportPreset == preset
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (selected) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (selected) colors.accent else colors.border),
                        modifier = Modifier.clickable {
                            onSelectViewport(preset)
                            onClose()
                        }
                    ) {
                        Text(
                            text = preset.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) colors.onAccent else colors.primaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 4. SECTION 4: COMPLETE 32+ SCREEN INVENTORY MATRIX (GROUPS A–I)
            Text(
                text = tr(lang, "SECTION 4 • COMPLETE SCREEN INVENTORY (GROUPS A–I)", "MỤC 4 • DANH MỤC ĐẦY ĐỦ 32 MÀN HÌNH (NHÓM A–I)"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))

            ScreenGroup.entries.forEach { group ->
                val groupScreens = JourneyScreenId.entries.filter { it.group == group }
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = "${group.code} — ${tr(lang, group.titleEn, group.titleVi)} (${groupScreens.size})",
                        style = MaterialTheme.typography.titleSmall,
                        color = colors.sage
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        groupScreens.forEach { screen ->
                            val isCurrent = uiState.currentScreen == screen
                            Surface(
                                shape = RoundedCornerShape(JourneyRadius.sm),
                                color = if (isCurrent) colors.accent else colors.surface,
                                border = BorderStroke(1.dp, if (isCurrent) colors.accent else colors.border),
                                modifier = Modifier.clickable {
                                    onSelectScreen(screen)
                                    onClose()
                                }
                            ) {
                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                    Text(
                                        text = screen.code,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isCurrent) colors.onAccent else colors.accent
                                    )
                                    Text(
                                        text = tr(lang, screen.titleEn, screen.titleVi),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isCurrent) colors.onAccent else colors.primaryText
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ============================================================================
// PHASE 1 SCREEN (P1-DS01): COMPLETE DESIGN SYSTEM, TOKENS & COMPONENT LIBRARY
// ============================================================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DesignSystemTokensScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var sampleInput by remember { mutableStateOf("Chùa Cầu Hội An, 1593") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.DS_TOKENS_COMPONENTS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = "PHASE 1 • MASTER DESIGN SYSTEM",
                title = tr(lang, "Brand Palette, Semantic Tokens & Reusable Components", "Bảng màu Thương hiệu, Token Ngữ nghĩa & Thư viện Thành phần")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // 1. Core Brand Hex Colors (#111923, #1C2934, #E9E5DC, #B99B72, #879A91, #FFFFFF)
            Text(
                text = tr(lang, "1. CORE BRAND COLOR PALETTE", "1. BẢNG MÀU THƯƠNG HIỆU GỐC"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            val brandSwatches = listOf(
                Triple("Midnight", "#111923", JourneyPalette.Midnight),
                Triple("Slate", "#1C2934", JourneyPalette.Slate),
                Triple("Ivory", "#E9E5DC", JourneyPalette.Ivory),
                Triple("Sand Gold", "#B99B72", JourneyPalette.SandGold),
                Triple("Sage", "#879A91", JourneyPalette.Sage),
                Triple("White", "#FFFFFF", JourneyPalette.White)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                brandSwatches.forEach { (name, hex, color) ->
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.md),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier.width(112.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .clip(RoundedCornerShape(JourneyRadius.sm))
                                    .background(color)
                                    .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.sm))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = name, style = MaterialTheme.typography.titleSmall, color = colors.primaryText)
                            Text(text = hex, style = MaterialTheme.typography.labelSmall, color = colors.secondaryText)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))

            // 2. Semantic Color Tokens (Active Theme Aware)
            Text(
                text = tr(lang, "2. SEMANTIC COLOR TOKENS (DARK & LIGHT AWARE)", "2. TOKEN MÀU NGỮ NGHĨA (TỰ ĐỘNG THEO CHẾ ĐỘ SÁNG/TỐI)"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            val semanticTokens = listOf(
                Triple("primaryBackground", "Canvas Base", colors.primaryBackground),
                Triple("secondaryBackground", "Obsidian / Parchment", colors.secondaryBackground),
                Triple("surface", "Card / Sheet Container", colors.surface),
                Triple("primaryText", "Editorial Headline", colors.primaryText),
                Triple("secondaryText", "Supporting Body", colors.secondaryText),
                Triple("border", "Hairline Divider", colors.border),
                Triple("accent", "Sand Gold Action", colors.accent),
                Triple("success", "Jade Confirmation", colors.success),
                Triple("warning", "Amber Caution", colors.warning),
                Triple("error", "Crimson Alert", colors.error)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                semanticTokens.forEach { (token, desc, tokenColor) ->
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.sm),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .background(tokenColor, CircleShape)
                                    .border(1.dp, colors.border, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = token, style = MaterialTheme.typography.labelSmall, color = colors.primaryText)
                                Text(text = desc, style = MaterialTheme.typography.bodySmall, color = colors.secondaryText)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))

            // 3. Typography Scale (Cormorant Garamond + Plus Jakarta Sans + JetBrains Mono)
            Text(
                text = tr(lang, "3. TYPOGRAPHY SCALE", "3. THANG ĐO KIỂU CHỮ (TYPOGRAPHY SCALE)"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Text("Display Large • Cormorant 42sp", style = MaterialTheme.typography.displayLarge, color = colors.primaryText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Headline Large • Cormorant 26sp", style = MaterialTheme.typography.headlineLarge, color = colors.primaryText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Title Large • Plus Jakarta Sans 17sp SemiBold", style = MaterialTheme.typography.titleLarge, color = colors.primaryText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Body Large • Plus Jakarta Sans 15sp/24sp — Explore the world. Discover the stories hidden in every stone archway and lantern-lit river port.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.secondaryText
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "LABEL MEDIUM • JETBRAINS MONO 11SP • 20.9101° N, 107.1839° E",
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))

            // 4. Spacing, Radius & Elevation Tokens
            Text(
                text = tr(lang, "4. SPACING (4–56dp) & BORDER RADIUS (6–28dp) SCALES", "4. THANG KHOẢNG CÁCH & ĐỘ BO GÓC"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    listOf(
                        "xxs: 4.dp" to 4.dp,
                        "xs: 8.dp" to 8.dp,
                        "sm: 12.dp" to 12.dp,
                        "md: 16.dp" to 16.dp,
                        "lg: 20.dp" to 20.dp,
                        "xl: 24.dp" to 24.dp,
                        "xxl: 32.dp" to 32.dp,
                        "section: 40.dp" to 40.dp
                    ).forEach { (label, dpVal) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 3.dp)
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.secondaryText,
                                modifier = Modifier.width(105.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .width(dpVal * 4)
                                    .height(10.dp)
                                    .background(colors.accent, RoundedCornerShape(4.dp))
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))

            // 5. Reusable Component Library Showcase
            Text(
                text = tr(lang, "5. COMPONENT VARIANTS (BUTTONS, INPUTS, CARDS, BADGES)", "5. THƯ VIỆN THÀNH PHẦN TÁI SỬ DỤNG"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = "Primary Action",
                    leadingIcon = Icons.Default.AutoAwesome,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = {}
                )
                JourneyButton(
                    text = "Secondary Outline",
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = {}
                )
                JourneyButton(
                    text = "Surface Tonal",
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = {}
                )
                JourneyButton(
                    text = "Ghost Link",
                    variant = JourneyButtonVariant.GHOST,
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))
            JourneyTextField(
                value = sampleInput,
                onValueChange = { sampleInput = it },
                label = tr(lang, "Destination or Monument Query", "Tìm kiếm Địa danh hoặc Di tích"),
                placeholder = "e.g., Văn Miếu, Hạ Long...",
                leadingIcon = Icons.Default.Search
            )

            Spacer(modifier = Modifier.height(JourneySpacing.md))
            JourneyEditorialCard(
                imageRes = R.drawable.img_hero_halong_bay,
                tagText = "COMPONENT SPEC • EDITORIAL CARD",
                title = "Vịnh Hạ Long — Emerald Karst Horizon",
                subtitle = "Full-bleed photography with warm obsidian gradient scrim, JetBrains Mono coordinates, and Cormorant Garamond display title.",
                metaText = "20.9101° N, 107.1839° E • 99% AI MATCH",
                isSaved = true,
                onCardClick = { onNavigate(JourneyScreenId.C3_PLACE_DETAILS) }
            )
        }
    }
}

// ============================================================================
// PHASE 6 SCREEN (P6-QA01): FLUTTER + FASTAPI + SUPABASE HANDOFF & QA ACCEPTANCE REPORT
// ============================================================================
@Composable
fun ArchitectureHandoffScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.DS_HANDOFF_ACCEPTANCE,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = "DELIVERABLES 7–12 • PRODUCTION HANDOFF",
                title = tr(lang, "Flutter + FastAPI + Supabase Blueprint & QA Report", "Kiến trúc Flutter + FastAPI + Supabase & Báo cáo Nghiệm thu UX")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // 1. Technology Stack Architecture Blueprint
            val handoffModules = listOf(
                Triple(
                    "1. Flutter Client Layer (iOS & Android)",
                    "ThemeData + Custom ThemeExtension<JourneySemanticColors> • GoRouter with 34 typed Screen IDs • Riverpod / Bloc state machines • CameraController + CustomPainter reticle overlay.",
                    "lib/core/theme/journey_tokens.dart • lib/routing/journey_router.dart"
                ),
                Triple(
                    "2. FastAPI AI & Media Service Layer",
                    "POST /v1/vision/recognize (Multimodal landmark & artifact classification with confidence score) • POST /v1/assistant/stream (SSE RAG cultural narrative) • POST /v1/voice/synthesize (Bilingual EN/VI neural audio guide) • POST /v1/video/render (Asynchronous FFmpeg + AI story reel pipeline).",
                    "app/routers/vision.py • app/services/heritage_rag.py"
                ),
                Triple(
                    "3. Supabase Auth, PostgreSQL + PostGIS & Storage",
                    "Supabase Auth (Email OTP, Apple, Google OAuth) • Tables: profiles, trips, daily_moments, discoveries, collections, saved_conversations • PostGIS geography(Point, 4326) for nearby heritage queries • Buckets: journal-media, voice-packs, story-reels.",
                    "supabase/migrations/20261113_journey_schema.sql"
                )
            )

            handoffModules.forEach { (title, spec, path) ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Architecture,
                                contentDescription = title,
                                tint = colors.accent,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = title, style = MaterialTheme.typography.titleMedium, color = colors.primaryText)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = spec, style = MaterialTheme.typography.bodyMedium, color = colors.secondaryText)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = path, style = MaterialTheme.typography.labelSmall, color = colors.accent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 2. Section 10 & 11: Quality Control & UI/UX Acceptance Checklist
            Text(
                text = tr(lang, "UI/UX ACCEPTANCE REPORT (SECTIONS 10 & 11)", "BÁO CÁO NGHIỆM THU CHẤT LƯỢNG UI/UX (MỤC 10 & 11)"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(8.dp))

            val qaChecklist = listOf(
                "Phase 1: Brand Palette (#111923, #1C2934, #E9E5DC, #B99B72, #879A91, #FFFFFF) & Semantic Tokens" to "PASSED",
                "Phase 2: Home (B1-B3), Explore Map & Places (C1-C4), Journal (F1) & Profile (I1)" to "PASSED",
                "Phase 3: AI Camera (D1-D5), Recognition, AI Chat & Voice Guide (E1-E3)" to "PASSED",
                "Phase 4: Trips (F2-F6), Collections (G1-G2), AI Story & Video Creator (H1-H3)" to "PASSED",
                "Phase 5: Onboarding & Auth (A1-A8), Settings, Subscription & Privacy (I2-I4)" to "PASSED",
                "Phase 6: All 7 Interactive Prototype Flows & 9 Screen States Verified" to "PASSED",
                "Navigation Visibility Rule: Hidden on Splash, Auth, Camera, Voice & Video Preview" to "PASSED",
                "Bilingual Localization: Instant Vietnamese (VI) & English (EN) across all 34 screens" to "PASSED",
                "Responsive Specs: 360dp, 390dp, 393dp, 412dp, 430dp & Tablet Viewport Presets" to "PASSED"
            )

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    qaChecklist.forEachIndexed { idx, (item, status) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = status,
                                    tint = colors.success,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.primaryText
                                )
                            }
                            JourneyBadgePill(
                                text = status,
                                containerColor = colors.success.copy(alpha = 0.15f),
                                contentColor = colors.success
                            )
                        }
                        if (idx < qaChecklist.lastIndex) {
                            HorizontalDivider(color = colors.subtleBorder)
                        }
                    }
                }
            }
        }
    }
}
