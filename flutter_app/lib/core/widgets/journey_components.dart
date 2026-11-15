import 'package:flutter/material.dart';
import '../theme/journey_theme.dart';

enum JourneyButtonVariant { primary, secondary, ghost, tonal }

/// 1. AppBar — Cinematic Top Header with Compass Emblem, Title + Tagline, Premium Pill, Bell & Avatar
class JourneyAppBar extends StatelessWidget {
  final String userName;
  final String avatarInitials;
  final int unreadCount;
  final VoidCallback onTapPremium;
  final VoidCallback onTapNotifications;
  final VoidCallback onTapAvatar;

  const JourneyAppBar({
    super.key,
    required this.userName,
    required this.avatarInitials,
    this.unreadCount = 2,
    required this.onTapPremium,
    required this.onTapNotifications,
    required this.onTapAvatar,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return Padding(
      padding: const EdgeInsets.fromLTRB(16, 10, 16, 8),
      child: Row(
        children: [
          Container(
            width: 44,
            height: 44,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: JourneyPalette.midnight,
              border: Border.all(color: JourneyPalette.sandGold, width: 1.5),
              boxShadow: [
                BoxShadow(
                  color: JourneyPalette.sandGold.withOpacity(0.22),
                  blurRadius: 10,
                ),
              ],
            ),
            child: const Icon(
              Icons.explore_outlined,
              color: JourneyPalette.sandGold,
              size: 24,
            ),
          ),
          const SizedBox(width: 10),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Journey AI',
                  style: TextStyle(
                    fontFamily: 'Georgia',
                    fontSize: 20,
                    fontWeight: FontWeight.w700,
                    color: colors.primaryText,
                    height: 1.1,
                  ),
                ),
                const SizedBox(height: 2),
                Text(
                  'Explore the World. Discover the Stories.',
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: TextStyle(
                    fontSize: 11,
                    color: colors.secondaryText,
                  ),
                ),
              ],
            ),
          ),
          const SizedBox(width: 6),
          GestureDetector(
            onTap: onTapPremium,
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 7),
              decoration: BoxDecoration(
                gradient: JourneyPalette.champagneGradient,
                borderRadius: BorderRadius.circular(JourneyRadius.pill),
                boxShadow: [
                  BoxShadow(
                    color: JourneyPalette.sandGold.withOpacity(0.28),
                    blurRadius: 8,
                    offset: const Offset(0, 2),
                  ),
                ],
              ),
              child: const Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Icon(Icons.workspace_premium, size: 15, color: JourneyPalette.midnight),
                  SizedBox(width: 4),
                  Text(
                    'Premium',
                    style: TextStyle(
                      fontSize: 12,
                      fontWeight: FontWeight.w700,
                      color: JourneyPalette.midnight,
                    ),
                  ),
                ],
              ),
            ),
          ),
          const SizedBox(width: 8),
          GestureDetector(
            onTap: onTapNotifications,
            child: Stack(
              clipBehavior: Clip.none,
              children: [
                Container(
                  width: 38,
                  height: 38,
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    color: colors.surface,
                    border: Border.all(color: colors.border),
                  ),
                  child: Icon(
                    Icons.notifications_none_rounded,
                    color: colors.primaryText,
                    size: 20,
                  ),
                ),
                if (unreadCount > 0)
                  Positioned(
                    right: -1,
                    top: -1,
                    child: Container(
                      width: 10,
                      height: 10,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        color: const Color(0xFFEF4444),
                        border: Border.all(color: colors.primaryBackground, width: 1.5),
                      ),
                    ),
                  ),
              ],
            ),
          ),
          const SizedBox(width: 8),
          GestureDetector(
            onTap: onTapAvatar,
            child: Container(
              width: 38,
              height: 38,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: colors.elevatedSurface,
                border: Border.all(color: JourneyPalette.sandGold, width: 1.5),
              ),
              alignment: Alignment.center,
              child: Text(
                avatarInitials,
                style: TextStyle(
                  fontSize: 12,
                  fontWeight: FontWeight.w800,
                  color: colors.primaryText,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

/// 2. PrimaryButton & SecondaryButton (Unified Button System)
class PrimaryButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final IconData? leadingIcon;
  final IconData? trailingIcon;

  const PrimaryButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.leadingIcon,
    this.trailingIcon,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onPressed,
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 13),
        decoration: BoxDecoration(
          gradient: JourneyPalette.champagneGradient,
          borderRadius: BorderRadius.circular(JourneyRadius.pill),
          boxShadow: [
            BoxShadow(
              color: JourneyPalette.sandGold.withOpacity(0.3),
              blurRadius: 12,
              offset: const Offset(0, 4),
            ),
          ],
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            if (leadingIcon != null) ...[
              Icon(leadingIcon, size: 17, color: JourneyPalette.midnight),
              const SizedBox(width: 8),
            ],
            Text(
              text,
              style: const TextStyle(
                fontSize: 13.5,
                fontWeight: FontWeight.w700,
                color: JourneyPalette.midnight,
              ),
            ),
            if (trailingIcon != null) ...[
              const SizedBox(width: 8),
              Icon(trailingIcon, size: 17, color: JourneyPalette.midnight),
            ],
          ],
        ),
      ),
    );
  }
}

class SecondaryButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final IconData? leadingIcon;

  const SecondaryButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.leadingIcon,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return OutlinedButton.icon(
      onPressed: onPressed,
      style: OutlinedButton.styleFrom(
        foregroundColor: colors.primaryText,
        side: BorderSide(color: colors.accent.withOpacity(0.7)),
        padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 12),
        shape: const StadiumBorder(),
      ),
      icon: leadingIcon != null
          ? Icon(leadingIcon, size: 17, color: colors.accent)
          : const SizedBox.shrink(),
      label: Text(
        text,
        style: const TextStyle(fontSize: 13, fontWeight: FontWeight.w600),
      ),
    );
  }
}

class JourneyButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final JourneyButtonVariant variant;
  final IconData? leadingIcon;

  const JourneyButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.variant = JourneyButtonVariant.primary,
    this.leadingIcon,
  });

  @override
  Widget build(BuildContext context) {
    if (variant == JourneyButtonVariant.primary) {
      return PrimaryButton(
        text: text,
        onPressed: onPressed,
        leadingIcon: leadingIcon,
      );
    }
    return SecondaryButton(
      text: text,
      onPressed: onPressed,
      leadingIcon: leadingIcon,
    );
  }
}

/// 3. SearchBar — Cinematic Glass Search Input with Scan Icon
class JourneySearchBar extends StatelessWidget {
  final String hintText;
  final ValueChanged<String>? onChanged;
  final VoidCallback? onTapScan;

  const JourneySearchBar({
    super.key,
    required this.hintText,
    this.onChanged,
    this.onTapScan,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 4),
      decoration: BoxDecoration(
        color: colors.surface.withOpacity(0.88),
        borderRadius: BorderRadius.circular(JourneyRadius.pill),
        border: Border.all(color: colors.accent.withOpacity(0.35)),
      ),
      child: Row(
        children: [
          Icon(Icons.search_rounded, color: colors.accent, size: 22),
          const SizedBox(width: 10),
          Expanded(
            child: TextField(
              onChanged: onChanged,
              style: TextStyle(color: colors.primaryText, fontSize: 14),
              decoration: InputDecoration(
                hintText: hintText,
                hintStyle: TextStyle(color: colors.secondaryText, fontSize: 13.5),
                border: InputBorder.none,
                isDense: true,
              ),
            ),
          ),
          if (onTapScan != null)
            IconButton(
              onPressed: onTapScan,
              icon: Icon(Icons.qr_code_scanner_rounded, color: colors.accent, size: 20),
              tooltip: 'Quét AI',
            ),
        ],
      ),
    );
  }
}

/// 4. CategoryChip — Active Champagne Gold or Dark Glass Pill
class CategoryChip extends StatelessWidget {
  final String label;
  final IconData? icon;
  final bool selected;
  final VoidCallback onTap;

  const CategoryChip({
    super.key,
    required this.label,
    this.icon,
    required this.selected,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return GestureDetector(
      onTap: onTap,
      child: AnimatedContainer(
        duration: const Duration(milliseconds: 180),
        padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 9),
        decoration: BoxDecoration(
          gradient: selected ? JourneyPalette.champagneGradient : null,
          color: selected ? null : colors.surface,
          borderRadius: BorderRadius.circular(JourneyRadius.pill),
          border: Border.all(
            color: selected ? JourneyPalette.sandGold : colors.border,
          ),
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            if (icon != null) ...[
              Icon(
                icon,
                size: 15,
                color: selected ? JourneyPalette.midnight : colors.accent,
              ),
              const SizedBox(width: 6),
            ],
            Text(
              label,
              style: TextStyle(
                fontSize: 12.5,
                fontWeight: selected ? FontWeight.w700 : FontWeight.w600,
                color: selected ? JourneyPalette.midnight : colors.primaryText,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

/// 5. PlaceCard — Scenic Destination Card with Province Badge, Rating & Favorite
class PlaceCard extends StatelessWidget {
  final String title;
  final String province;
  final String rating;
  final String imageAsset;
  final bool isSaved;
  final VoidCallback onTap;
  final VoidCallback? onToggleSave;

  const PlaceCard({
    super.key,
    required this.title,
    required this.province,
    required this.rating,
    required this.imageAsset,
    this.isSaved = false,
    required this.onTap,
    this.onToggleSave,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return GestureDetector(
      onTap: onTap,
      child: Container(
        width: 175,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(JourneyRadius.lg),
          color: colors.surface,
          border: Border.all(color: colors.border),
        ),
        clipBehavior: Clip.antiAlias,
        child: Stack(
          children: [
            Positioned.fill(
              child: Image.asset(
                imageAsset,
                fit: BoxFit.cover,
                errorBuilder: (_, __, ___) => Container(color: colors.elevatedSurface),
              ),
            ),
            Positioned.fill(
              child: Container(
                decoration: const BoxDecoration(gradient: JourneyPalette.cinematicScrim),
              ),
            ),
            Positioned(
              top: 10,
              right: 10,
              child: GestureDetector(
                onTap: onToggleSave,
                child: Container(
                  width: 30,
                  height: 30,
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    color: Colors.black.withOpacity(0.45),
                  ),
                  child: Icon(
                    isSaved ? Icons.favorite : Icons.favorite_border,
                    size: 16,
                    color: isSaved ? JourneyPalette.sandGold : Colors.white,
                  ),
                ),
              ),
            ),
            Positioned(
              left: 12,
              right: 12,
              bottom: 12,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      const Icon(Icons.location_on, size: 12, color: JourneyPalette.sandGold),
                      const SizedBox(width: 3),
                      Expanded(
                        child: Text(
                          province,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(
                            fontSize: 11,
                            color: JourneyPalette.ivory,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 3),
                  Text(
                    title,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.w700,
                      color: Colors.white,
                    ),
                  ),
                  const SizedBox(height: 3),
                  Row(
                    children: [
                      const Icon(Icons.star_rounded, size: 14, color: Color(0xFFFBBF24)),
                      const SizedBox(width: 4),
                      Text(
                        rating,
                        style: const TextStyle(
                          fontSize: 11.5,
                          fontWeight: FontWeight.w600,
                          color: JourneyPalette.ivory,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

/// 6. JournalPhotoCard — 2-Column Photo Journal Card with Place & Date
class JournalPhotoCard extends StatelessWidget {
  final String placeName;
  final String dateLabel;
  final String imageAsset;
  final VoidCallback onTap;
  final VoidCallback? onDelete;

  const JournalPhotoCard({
    super.key,
    required this.placeName,
    required this.dateLabel,
    required this.imageAsset,
    required this.onTap,
    this.onDelete,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return GestureDetector(
      onTap: onTap,
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: colors.surface,
          border: Border.all(color: colors.border),
        ),
        clipBehavior: Clip.antiAlias,
        child: Stack(
          children: [
            Positioned.fill(
              child: Image.asset(
                imageAsset,
                fit: BoxFit.cover,
                errorBuilder: (_, __, ___) => Container(color: colors.elevatedSurface),
              ),
            ),
            Positioned.fill(
              child: Container(
                decoration: const BoxDecoration(gradient: JourneyPalette.cinematicScrim),
              ),
            ),
            if (onDelete != null)
              Positioned(
                top: 8,
                right: 8,
                child: GestureDetector(
                  onTap: onDelete,
                  child: Container(
                    width: 28,
                    height: 28,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color: Colors.black.withOpacity(0.45),
                    ),
                    child: const Icon(Icons.more_horiz, size: 16, color: Colors.white),
                  ),
                ),
              ),
            Positioned(
              left: 10,
              right: 10,
              bottom: 10,
              child: Row(
                children: [
                  Container(
                    padding: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      color: Colors.black.withOpacity(0.5),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: const Icon(Icons.photo_outlined, size: 14, color: Colors.white),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          placeName,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(
                            fontSize: 13,
                            fontWeight: FontWeight.w700,
                            color: Colors.white,
                          ),
                        ),
                        Text(
                          dateLabel,
                          style: const TextStyle(
                            fontSize: 10.5,
                            color: JourneyPalette.ivory,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

/// 7. BottomNavigation — 5-Tab Bar with Center Elevated Champagne Gold 'Quét' Button
class JourneyBottomNav extends StatelessWidget {
  final int currentIndex;
  final ValueChanged<int> onTap;
  final bool isVietnamese;

  const JourneyBottomNav({
    super.key,
    required this.currentIndex,
    required this.onTap,
    this.isVietnamese = true,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    final items = [
      (Icons.home_outlined, Icons.home_rounded, isVietnamese ? 'Trang chủ' : 'Home'),
      (Icons.explore_outlined, Icons.explore_rounded, isVietnamese ? 'Khám phá' : 'Explore'),
      (Icons.camera_alt_rounded, Icons.camera_alt_rounded, isVietnamese ? 'Quét' : 'Scan'),
      (Icons.menu_book_outlined, Icons.menu_book_rounded, isVietnamese ? 'Nhật ký' : 'Journal'),
      (Icons.person_outline_rounded, Icons.person_rounded, isVietnamese ? 'Hồ sơ' : 'Profile'),
    ];

    return Container(
      decoration: BoxDecoration(
        color: colors.secondaryBackground.withOpacity(0.96),
        border: Border(top: BorderSide(color: colors.border)),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.35),
            blurRadius: 18,
            offset: const Offset(0, -4),
          ),
        ],
      ),
      child: SafeArea(
        top: false,
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 6),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: List.generate(items.length, (index) {
              final item = items[index];
              final selected = currentIndex == index;
              if (index == 2) {
                return GestureDetector(
                  onTap: () => onTap(index),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Transform.translate(
                        offset: const Offset(0, -10),
                        child: Container(
                          width: 58,
                          height: 58,
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            gradient: JourneyPalette.champagneGradient,
                            border: Border.all(
                              color: colors.primaryBackground,
                              width: 3,
                            ),
                            boxShadow: [
                              BoxShadow(
                                color: JourneyPalette.sandGold.withOpacity(0.45),
                                blurRadius: 16,
                                spreadRadius: 1,
                              ),
                            ],
                          ),
                          child: const Icon(
                            Icons.camera_alt_rounded,
                            color: JourneyPalette.midnight,
                            size: 26,
                          ),
                        ),
                      ),
                      Transform.translate(
                        offset: const Offset(0, -6),
                        child: Text(
                          item.$3,
                          style: TextStyle(
                            fontSize: 11,
                            fontWeight: FontWeight.w700,
                            color: selected ? JourneyPalette.sandGold : colors.primaryText,
                          ),
                        ),
                      ),
                    ],
                  ),
                );
              }
              return InkWell(
                borderRadius: BorderRadius.circular(JourneyRadius.md),
                onTap: () => onTap(index),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Icon(
                        selected ? item.$2 : item.$1,
                        color: selected ? JourneyPalette.sandGold : colors.secondaryText,
                        size: 23,
                      ),
                      const SizedBox(height: 4),
                      Text(
                        item.$3,
                        style: TextStyle(
                          fontSize: 11,
                          fontWeight: selected ? FontWeight.w700 : FontWeight.w500,
                          color: selected ? JourneyPalette.sandGold : colors.secondaryText,
                        ),
                      ),
                      const SizedBox(height: 3),
                      AnimatedContainer(
                        duration: const Duration(milliseconds: 180),
                        width: selected ? 20 : 0,
                        height: 2.5,
                        decoration: BoxDecoration(
                          color: JourneyPalette.sandGold,
                          borderRadius: BorderRadius.circular(2),
                        ),
                      ),
                    ],
                  ),
                ),
              );
            }),
          ),
        ),
      ),
    );
  }
}

/// 8. LoadingState, EmptyState & ErrorState
class JourneyEmptyState extends StatelessWidget {
  final IconData icon;
  final String title;
  final String subtitle;
  final String? actionLabel;
  final VoidCallback? onAction;

  const JourneyEmptyState({
    super.key,
    required this.icon,
    required this.title,
    required this.subtitle,
    this.actionLabel,
    this.onAction,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return Container(
      padding: const EdgeInsets.all(24),
      decoration: BoxDecoration(
        color: colors.surface,
        borderRadius: BorderRadius.circular(JourneyRadius.lg),
        border: Border.all(color: colors.border),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon, size: 40, color: colors.accent),
          const SizedBox(height: 10),
          Text(
            title,
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w700,
              color: colors.primaryText,
            ),
          ),
          const SizedBox(height: 6),
          Text(
            subtitle,
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 13, color: colors.secondaryText),
          ),
          if (actionLabel != null && onAction != null) ...[
            const SizedBox(height: 14),
            PrimaryButton(text: actionLabel!, onPressed: onAction!),
          ],
        ],
      ),
    );
  }
}
