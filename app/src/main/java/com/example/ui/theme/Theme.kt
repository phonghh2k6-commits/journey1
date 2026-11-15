package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val DarkM3Scheme = darkColorScheme(
    primary = JourneyPalette.SandGold,
    onPrimary = JourneyPalette.Midnight,
    primaryContainer = DarkJourneyColors.accentContainer,
    onPrimaryContainer = JourneyPalette.Ivory,
    secondary = JourneyPalette.Sage,
    onSecondary = JourneyPalette.Midnight,
    tertiary = JourneyPalette.Ivory,
    background = DarkJourneyColors.primaryBackground,
    onBackground = DarkJourneyColors.primaryText,
    surface = DarkJourneyColors.surface,
    onSurface = DarkJourneyColors.primaryText,
    surfaceVariant = DarkJourneyColors.elevatedSurface,
    onSurfaceVariant = DarkJourneyColors.secondaryText,
    outline = DarkJourneyColors.border,
    error = DarkJourneyColors.error
)

private val LightM3Scheme = lightColorScheme(
    primary = LightJourneyColors.accent,
    onPrimary = JourneyPalette.White,
    primaryContainer = LightJourneyColors.accentContainer,
    onPrimaryContainer = JourneyPalette.Midnight,
    secondary = JourneyPalette.Sage,
    onSecondary = JourneyPalette.White,
    tertiary = JourneyPalette.Slate,
    background = LightJourneyColors.primaryBackground,
    onBackground = LightJourneyColors.primaryText,
    surface = LightJourneyColors.surface,
    onSurface = LightJourneyColors.primaryText,
    surfaceVariant = LightJourneyColors.elevatedSurface,
    onSurfaceVariant = LightJourneyColors.secondaryText,
    outline = LightJourneyColors.border,
    error = LightJourneyColors.error
)

val JourneyShapes = Shapes(
    extraSmall = RoundedCornerShape(JourneyRadius.xs),
    small = RoundedCornerShape(JourneyRadius.sm),
    medium = RoundedCornerShape(JourneyRadius.md),
    large = RoundedCornerShape(JourneyRadius.lg),
    extraLarge = RoundedCornerShape(JourneyRadius.xl)
)

object JourneyTheme {
    val colors: JourneySemanticColors
        @Composable
        @ReadOnlyComposable
        get() = LocalJourneyColors.current
}

@Composable
fun JourneyAITheme(
    darkTheme: Boolean = false,
    fontScaleStep: Int = 0,
    content: @Composable () -> Unit
) {
    val semanticColors = if (darkTheme) DarkJourneyColors else LightJourneyColors
    val m3Scheme = if (darkTheme) DarkM3Scheme else LightM3Scheme
    val scaleMultiplier = when (fontScaleStep) {
        1 -> 1.12f
        2 -> 1.24f
        else -> 1.0f
    }

    val scaledTypography = JourneyTypography.copy(
        displayLarge = JourneyTypography.displayLarge.copy(fontSize = JourneyTypography.displayLarge.fontSize * scaleMultiplier),
        displayMedium = JourneyTypography.displayMedium.copy(fontSize = JourneyTypography.displayMedium.fontSize * scaleMultiplier),
        displaySmall = JourneyTypography.displaySmall.copy(fontSize = JourneyTypography.displaySmall.fontSize * scaleMultiplier),
        headlineLarge = JourneyTypography.headlineLarge.copy(fontSize = JourneyTypography.headlineLarge.fontSize * scaleMultiplier),
        headlineMedium = JourneyTypography.headlineMedium.copy(fontSize = JourneyTypography.headlineMedium.fontSize * scaleMultiplier),
        headlineSmall = JourneyTypography.headlineSmall.copy(fontSize = JourneyTypography.headlineSmall.fontSize * scaleMultiplier),
        titleLarge = JourneyTypography.titleLarge.copy(fontSize = JourneyTypography.titleLarge.fontSize * scaleMultiplier),
        titleMedium = JourneyTypography.titleMedium.copy(fontSize = JourneyTypography.titleMedium.fontSize * scaleMultiplier),
        titleSmall = JourneyTypography.titleSmall.copy(fontSize = JourneyTypography.titleSmall.fontSize * scaleMultiplier),
        bodyLarge = JourneyTypography.bodyLarge.copy(fontSize = JourneyTypography.bodyLarge.fontSize * scaleMultiplier),
        bodyMedium = JourneyTypography.bodyMedium.copy(fontSize = JourneyTypography.bodyMedium.fontSize * scaleMultiplier),
        bodySmall = JourneyTypography.bodySmall.copy(fontSize = JourneyTypography.bodySmall.fontSize * scaleMultiplier),
        labelLarge = JourneyTypography.labelLarge.copy(fontSize = JourneyTypography.labelLarge.fontSize * scaleMultiplier),
        labelMedium = JourneyTypography.labelMedium.copy(fontSize = JourneyTypography.labelMedium.fontSize * scaleMultiplier),
        labelSmall = JourneyTypography.labelSmall.copy(fontSize = JourneyTypography.labelSmall.fontSize * scaleMultiplier)
    )

    CompositionLocalProvider(LocalJourneyColors provides semanticColors) {
        MaterialTheme(
            colorScheme = m3Scheme,
            typography = scaledTypography,
            shapes = JourneyShapes,
            content = content
        )
    }
}

// Keep alias for template compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    JourneyAITheme(darkTheme = darkTheme, content = content)
}
