import 'package:flutter/material.dart';

/// JOURNEY AI — PREMIUM CINEMATIC DESIGN SYSTEM
/// Brand Tagline: "Explore the World. Discover the Stories."
class JourneyPalette {
  // Core Brand Palette (Master Prompt Specification)
  static const Color midnight = Color(0xFF111923); // Midnight Navy
  static const Color slate = Color(0xFF1C2934); // Deep Slate
  static const Color ivory = Color(0xFFE9E5DC); // Ivory
  static const Color sandGold = Color(0xFFB99B72); // Champagne Gold
  static const Color sage = Color(0xFF879A91); // Sage Green
  static const Color white = Color(0xFFFFFFFF); // White

  // Supporting Atmospheric & Surface Tokens
  static const Color deepObsidian = Color(0xFF0B1017);
  static const Color elevatedSlate = Color(0xFF243442);
  static const Color champagneLight = Color(0xFFD5BC97);
  static const Color mutedSand = Color(0xFF9E825B);
  static const Color softIvory = Color(0xFFF6F4EF);
  static const Color warmParchment = Color(0xFFDFD9CC);
  static const Color mistText = Color(0xFFA5B2BC);
  static const Color mutedSlateText = Color(0xFF5A6B78);

  // Semantic Status Colors
  static const Color successJade = Color(0xFF5E9A78);
  static const Color spotifyGreen = Color(0xFF22C55E);
  static const Color warningAmber = Color(0xFFD99B4E);
  static const Color errorCrimson = Color(0xFFC85A5A);

  static const LinearGradient champagneGradient = LinearGradient(
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
    colors: [Color(0xFFD8C29D), Color(0xFFB99B72), Color(0xFF9B7D54)],
  );

  static const LinearGradient cinematicScrim = LinearGradient(
    begin: Alignment.topCenter,
    end: Alignment.bottomCenter,
    colors: [
      Color(0x14111923),
      Color(0x88111923),
      Color(0xEB111923),
    ],
  );
}

@immutable
class JourneySemanticColors extends ThemeExtension<JourneySemanticColors> {
  final Color primaryBackground;
  final Color secondaryBackground;
  final Color surface;
  final Color elevatedSurface;
  final Color glassSurface;
  final Color primaryText;
  final Color secondaryText;
  final Color border;
  final Color accent;
  final Color onAccent;
  final Color sage;
  final Color success;
  final Color warning;
  final Color error;

  const JourneySemanticColors({
    required this.primaryBackground,
    required this.secondaryBackground,
    required this.surface,
    required this.elevatedSurface,
    required this.glassSurface,
    required this.primaryText,
    required this.secondaryText,
    required this.border,
    required this.accent,
    required this.onAccent,
    required this.sage,
    required this.success,
    required this.warning,
    required this.error,
  });

  static const dark = JourneySemanticColors(
    primaryBackground: JourneyPalette.midnight,
    secondaryBackground: JourneyPalette.deepObsidian,
    surface: JourneyPalette.slate,
    elevatedSurface: JourneyPalette.elevatedSlate,
    glassSurface: Color(0xCC1C2934),
    primaryText: JourneyPalette.ivory,
    secondaryText: JourneyPalette.mistText,
    border: Color(0xFF2E3E4E),
    accent: JourneyPalette.sandGold,
    onAccent: JourneyPalette.midnight,
    sage: JourneyPalette.sage,
    success: JourneyPalette.successJade,
    warning: JourneyPalette.warningAmber,
    error: JourneyPalette.errorCrimson,
  );

  static const light = JourneySemanticColors(
    primaryBackground: JourneyPalette.ivory,
    secondaryBackground: JourneyPalette.warmParchment,
    surface: JourneyPalette.softIvory,
    elevatedSurface: JourneyPalette.white,
    glassSurface: Color(0xE6FFFFFF),
    primaryText: JourneyPalette.midnight,
    secondaryText: JourneyPalette.mutedSlateText,
    border: Color(0xFFD0C9B8),
    accent: JourneyPalette.mutedSand,
    onAccent: JourneyPalette.white,
    sage: JourneyPalette.sage,
    success: Color(0xFF3B7A57),
    warning: Color(0xFFB87729),
    error: Color(0xFFB03A3A),
  );

  @override
  JourneySemanticColors copyWith({
    Color? primaryBackground,
    Color? secondaryBackground,
    Color? surface,
    Color? elevatedSurface,
    Color? glassSurface,
    Color? primaryText,
    Color? secondaryText,
    Color? border,
    Color? accent,
    Color? onAccent,
    Color? sage,
    Color? success,
    Color? warning,
    Color? error,
  }) {
    return JourneySemanticColors(
      primaryBackground: primaryBackground ?? this.primaryBackground,
      secondaryBackground: secondaryBackground ?? this.secondaryBackground,
      surface: surface ?? this.surface,
      elevatedSurface: elevatedSurface ?? this.elevatedSurface,
      glassSurface: glassSurface ?? this.glassSurface,
      primaryText: primaryText ?? this.primaryText,
      secondaryText: secondaryText ?? this.secondaryText,
      border: border ?? this.border,
      accent: accent ?? this.accent,
      onAccent: onAccent ?? this.onAccent,
      sage: sage ?? this.sage,
      success: success ?? this.success,
      warning: warning ?? this.warning,
      error: error ?? this.error,
    );
  }

  @override
  JourneySemanticColors lerp(
    ThemeExtension<JourneySemanticColors>? other,
    double t,
  ) {
    if (other is! JourneySemanticColors) return this;
    return JourneySemanticColors(
      primaryBackground: Color.lerp(primaryBackground, other.primaryBackground, t)!,
      secondaryBackground: Color.lerp(secondaryBackground, other.secondaryBackground, t)!,
      surface: Color.lerp(surface, other.surface, t)!,
      elevatedSurface: Color.lerp(elevatedSurface, other.elevatedSurface, t)!,
      glassSurface: Color.lerp(glassSurface, other.glassSurface, t)!,
      primaryText: Color.lerp(primaryText, other.primaryText, t)!,
      secondaryText: Color.lerp(secondaryText, other.secondaryText, t)!,
      border: Color.lerp(border, other.border, t)!,
      accent: Color.lerp(accent, other.accent, t)!,
      onAccent: Color.lerp(onAccent, other.onAccent, t)!,
      sage: Color.lerp(sage, other.sage, t)!,
      success: Color.lerp(success, other.success, t)!,
      warning: Color.lerp(warning, other.warning, t)!,
      error: Color.lerp(error, other.error, t)!,
    );
  }
}

class JourneySpacing {
  static const double xxs = 4.0;
  static const double xs = 8.0;
  static const double sm = 12.0;
  static const double md = 16.0;
  static const double lg = 20.0;
  static const double xl = 24.0;
  static const double xxl = 32.0;
  static const double section = 40.0;
}

class JourneyRadius {
  static const double xs = 8.0;
  static const double sm = 12.0;
  static const double md = 16.0;
  static const double lg = 22.0;
  static const double xl = 28.0;
  static const double pill = 999.0;
}

class JourneyTheme {
  static ThemeData buildDarkTheme() {
    return ThemeData(
      useMaterial3: true,
      brightness: Brightness.dark,
      scaffoldBackgroundColor: JourneyPalette.midnight,
      colorScheme: const ColorScheme.dark(
        primary: JourneyPalette.sandGold,
        onPrimary: JourneyPalette.midnight,
        secondary: JourneyPalette.sage,
        surface: JourneyPalette.slate,
        onSurface: JourneyPalette.ivory,
        error: JourneyPalette.errorCrimson,
      ),
      extensions: const [JourneySemanticColors.dark],
    );
  }

  static ThemeData buildLightTheme() {
    return ThemeData(
      useMaterial3: true,
      brightness: Brightness.light,
      scaffoldBackgroundColor: JourneyPalette.ivory,
      colorScheme: const ColorScheme.light(
        primary: JourneyPalette.mutedSand,
        onPrimary: JourneyPalette.white,
        secondary: JourneyPalette.sage,
        surface: JourneyPalette.softIvory,
        onSurface: JourneyPalette.midnight,
        error: Color(0xFFB03A3A),
      ),
      extensions: const [JourneySemanticColors.light],
    );
  }
}
