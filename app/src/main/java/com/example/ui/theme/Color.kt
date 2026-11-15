package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ============================================================================
// PHASE 1: JOURNEY AI BRAND PALETTE & SEMANTIC COLOR TOKENS
// ============================================================================

object JourneyPalette {
    val Midnight = Color(0xFF111923)
    val Slate = Color(0xFF1C2934)
    val Ivory = Color(0xFFE9E5DC)
    val SandGold = Color(0xFFB99B72)
    val Sage = Color(0xFF879A91)
    val White = Color(0xFFFFFFFF)

    // Extended Luxury Tones
    val DeepObsidian = Color(0xFF0B1017)
    val ElevatedSlate = Color(0xFF243442)
    val MutedSand = Color(0xFF9E825B)
    val SoftIvory = Color(0xFFF6F4EF)
    val WarmParchment = Color(0xFFDFD9CC)
    val CharcoalText = Color(0xFF16202B)
    val MutedSlateText = Color(0xFF5A6B78)
    val MistText = Color(0xFFA5B2BC)

    // Status Semantic Colors
    val SuccessJade = Color(0xFF5E9A78)
    val WarningAmber = Color(0xFFD99B4E)
    val ErrorCrimson = Color(0xFFC85A5A)
}

@Immutable
data class JourneySemanticColors(
    val isDark: Boolean,
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val surface: Color,
    val elevatedSurface: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val mutedText: Color,
    val border: Color,
    val subtleBorder: Color,
    val accent: Color,
    val accentContainer: Color,
    val onAccent: Color,
    val sage: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val scrim: Color
)

val DarkJourneyColors = JourneySemanticColors(
    isDark = true,
    primaryBackground = JourneyPalette.Midnight,          // #111923
    secondaryBackground = JourneyPalette.DeepObsidian,    // #0B1017
    surface = JourneyPalette.Slate,                       // #1C2934
    elevatedSurface = JourneyPalette.ElevatedSlate,       // #243442
    primaryText = JourneyPalette.Ivory,                   // #E9E5DC
    secondaryText = JourneyPalette.MistText,              // #A5B2BC
    mutedText = JourneyPalette.Sage,                      // #879A91
    border = Color(0xFF2E3E4E),
    subtleBorder = Color(0xFF22303D),
    accent = JourneyPalette.SandGold,                     // #B99B72
    accentContainer = Color(0xFF332D24),
    onAccent = JourneyPalette.Midnight,
    sage = JourneyPalette.Sage,                           // #879A91
    success = JourneyPalette.SuccessJade,
    warning = JourneyPalette.WarningAmber,
    error = JourneyPalette.ErrorCrimson,
    scrim = Color(0xCC0B1017)
)

val LightJourneyColors = JourneySemanticColors(
    isDark = false,
    primaryBackground = JourneyPalette.Ivory,             // #E9E5DC
    secondaryBackground = JourneyPalette.WarmParchment,   // #DFD9CC
    surface = JourneyPalette.SoftIvory,                   // #F6F4EF
    elevatedSurface = JourneyPalette.White,               // #FFFFFF
    primaryText = JourneyPalette.Midnight,                // #111923
    secondaryText = JourneyPalette.MutedSlateText,        // #5A6B78
    mutedText = JourneyPalette.Sage,                      // #879A91
    border = Color(0xFFD0C9B8),
    subtleBorder = Color(0xFFE0DACB),
    accent = JourneyPalette.MutedSand,                    // #9E825B (accessible contrast on Ivory)
    accentContainer = Color(0xFFEAE1D0),
    onAccent = JourneyPalette.White,
    sage = JourneyPalette.Sage,                           // #879A91
    success = Color(0xFF3B7A57),
    warning = Color(0xFFB87729),
    error = Color(0xFFB03A3A),
    scrim = Color(0x99111923)
)

// ============================================================================
// SPACING, RADIUS & ELEVATION SCALES
// ============================================================================

object JourneySpacing {
    val xxs: Dp = 4.dp
    val xs: Dp = 8.dp
    val sm: Dp = 12.dp
    val md: Dp = 16.dp
    val lg: Dp = 20.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
    val section: Dp = 40.dp
    val hero: Dp = 56.dp
}

object JourneyRadius {
    val xs: Dp = 6.dp
    val sm: Dp = 10.dp
    val md: Dp = 16.dp
    val lg: Dp = 22.dp
    val xl: Dp = 28.dp
    val pill: Dp = 999.dp
}

object JourneyElevation {
    val flat: Dp = 0.dp
    val subtle: Dp = 2.dp
    val card: Dp = 6.dp
    val floating: Dp = 14.dp
    val modal: Dp = 24.dp
}

val LocalJourneyColors = staticCompositionLocalOf { DarkJourneyColors }
