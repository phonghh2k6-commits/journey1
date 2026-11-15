import 'package:flutter/material.dart';
import 'core/theme/journey_theme.dart';
import 'core/widgets/journey_components.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const JourneyAiApp());
}

class JourneyPlaceModel {
  final String id;
  final String name;
  final String province;
  final String category;
  final String distance;
  final String rating;
  final String weather;
  final String imageAsset;
  final String story;
  bool isSaved;

  JourneyPlaceModel({
    required this.id,
    required this.name,
    required this.province,
    required this.category,
    required this.distance,
    required this.rating,
    required this.weather,
    required this.imageAsset,
    required this.story,
    this.isSaved = false,
  });
}

class JourneyMomentModel {
  final String id;
  final String placeName;
  final String dateLabel;
  final String imageAsset;
  final String caption;
  final String spotifyTrack;
  final String temperatureTag;
  bool isFavorite;

  JourneyMomentModel({
    required this.id,
    required this.placeName,
    required this.dateLabel,
    required this.imageAsset,
    required this.caption,
    this.spotifyTrack = '',
    this.temperatureTag = '28°C ☀️',
    this.isFavorite = false,
  });
}

class JourneyAiApp extends StatefulWidget {
  const JourneyAiApp({super.key});

  @override
  State<JourneyAiApp> createState() => _JourneyAiAppState();
}

class _JourneyAiAppState extends State<JourneyAiApp> {
  bool _isDarkMode = true;
  bool _isVietnamese = true;
  int _selectedTab = 0;

  String _userName = 'Bích An';
  String _userHandle = '@journey.me';
  String _userEmail = 'bachan@gmail.com';
  String _userCity = 'Hà Nội, Việt Nam';
  String _userBio = 'Lưu giữ khoảnh khắc, mở rộng thế giới của riêng bạn 🇻🇳';

  final List<JourneyPlaceModel> _places = [
    JourneyPlaceModel(
      id: 'halong',
      name: 'Vịnh Hạ Long',
      province: 'Quảng Ninh',
      category: 'Thiên nhiên',
      distance: '1.2 km',
      rating: '4.9 (12.5k)',
      weather: '28°C',
      imageAsset: 'assets/images/img_hero_halong_bay.jpg',
      story: 'Kỳ quan thiên nhiên thế giới của Việt Nam với gần 2.000 đảo đá vôi kỳ vĩ trên làn nước ngọc bích.',
      isSaved: true,
    ),
    JourneyPlaceModel(
      id: 'hoian',
      name: 'Phố cổ Hội An',
      province: 'Quảng Nam',
      category: 'Di tích',
      distance: '1.5 km',
      rating: '4.8 (8.7k)',
      weather: '27°C',
      imageAsset: 'assets/images/img_hoian_lanterns.jpg',
      story: 'Thương cảng cổ thế kỷ XVI–XVII rực rỡ ánh đèn lồng bên dòng sông Hoài thơ mộng.',
      isSaved: true,
    ),
    JourneyPlaceModel(
      id: 'sapa',
      name: 'Sapa',
      province: 'Lào Cai',
      category: 'Thiên nhiên',
      distance: '1.8 km',
      rating: '4.8 (7.1k)',
      weather: '21°C',
      imageAsset: 'assets/images/img_sapa_terraces.jpg',
      story: 'Ruộng bậc thang Mường Hoa uốn lượn giữa biển mây Hoàng Liên Sơn.',
    ),
    JourneyPlaceModel(
      id: 'hanoi',
      name: 'Chùa Một Cột & Văn Miếu',
      province: 'Hà Nội',
      category: 'Văn hóa',
      distance: '1.2 km',
      rating: '4.9 (9.4k)',
      weather: '30°C',
      imageAsset: 'assets/images/img_hanoi_temple.jpg',
      story: 'Biểu tượng ngàn năm văn hiến của kinh thành Thăng Long — Hà Nội.',
    ),
  ];

  final List<JourneyMomentModel> _moments = [
    JourneyMomentModel(
      id: 'm1',
      placeName: 'Hạ Long, Quảng Ninh',
      dateLabel: '15 Th04, 2025',
      imageAsset: 'assets/images/img_hero_halong_bay.jpg',
      caption: 'Bình minh trên vịnh di sản tuyệt đẹp ✨',
      spotifyTrack: 'Đừng Làm Trái Tim Anh Đau - Sơn Tùng M-TP',
    ),
    JourneyMomentModel(
      id: 'm2',
      placeName: 'Hà Nội',
      dateLabel: '12 Th04, 2025',
      imageAsset: 'assets/images/img_hanoi_temple.jpg',
      caption: 'Chiều thu cổ kính bên mái ngói rêu phong',
    ),
    JourneyMomentModel(
      id: 'm3',
      placeName: 'Hội An',
      dateLabel: '08 Th04, 2025',
      imageAsset: 'assets/images/img_hoian_lanterns.jpg',
      caption: 'Phố cổ lung linh sắc đèn lồng',
    ),
    JourneyMomentModel(
      id: 'm4',
      placeName: 'Sapa',
      dateLabel: '05 Th04, 2025',
      imageAsset: 'assets/images/img_sapa_terraces.jpg',
      caption: 'Săn mây buổi sớm trên thung lũng Mường Hoa',
    ),
  ];

  void _addCapturedMoment({
    required String placeName,
    required String caption,
    required String spotifyTrack,
    required String temperatureTag,
  }) {
    setState(() {
      _moments.insert(
        0,
        JourneyMomentModel(
          id: 'm_${Date.now().millisecondsSinceEpoch}',
          placeName: placeName.isEmpty ? 'Khoảnh khắc đã chụp' : placeName,
          dateLabel: 'Hôm nay',
          imageAsset: 'assets/images/img_hero_halong_bay.jpg',
          caption: caption.isEmpty ? 'Khoảnh khắc mới trên Journey AI ✨' : caption,
          spotifyTrack: spotifyTrack,
          temperatureTag: temperatureTag,
        ),
      );
      _selectedTab = 3;
    });
  }

  void _openAiAssistantSheet(BuildContext context) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: JourneyPalette.midnight,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(28)),
      ),
      builder: (_) => const _AiAssistantBottomSheet(),
    );
  }

  void _openSubscriptionSheet(BuildContext context) {
    showModalBottomSheet(
      context: context,
      backgroundColor: JourneyPalette.midnight,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(28)),
      ),
      builder: (_) => const _PremiumSubscriptionSheet(),
    );
  }

  @override
  Widget build(BuildContext context) {
    final initials = _userName.trim().isNotEmpty
        ? _userName.trim().split(' ').map((e) => e[0]).take(2).join().toUpperCase()
        : 'BA';

    return MaterialApp(
      title: 'Journey AI',
      debugShowCheckedModeBanner: false,
      themeMode: _isDarkMode ? ThemeMode.dark : ThemeMode.light,
      theme: JourneyTheme.buildLightTheme(),
      darkTheme: JourneyTheme.buildDarkTheme(),
      home: Builder(
        builder: (innerContext) => Scaffold(
          body: SafeArea(
            bottom: false,
            child: Column(
              children: [
                JourneyAppBar(
                  userName: _userName,
                  avatarInitials: initials,
                  onTapPremium: () => _openSubscriptionSheet(innerContext),
                  onTapNotifications: () => _openAiAssistantSheet(innerContext),
                  onTapAvatar: () => setState(() => _selectedTab = 4),
                ),
                Expanded(
                  child: IndexedStack(
                    index: _selectedTab,
                    children: [
                      _HomeTab(
                        userName: _userName,
                        places: _places,
                        moments: _moments,
                        onOpenCamera: () => setState(() => _selectedTab = 2),
                        onOpenMap: () => setState(() => _selectedTab = 1),
                        onOpenJournal: () => setState(() => _selectedTab = 3),
                        onOpenAiChat: () => _openAiAssistantSheet(innerContext),
                        onToggleSavePlace: (id) {
                          setState(() {
                            final idx = _places.indexWhere((p) => p.id == id);
                            if (idx >= 0) _places[idx].isSaved = !_places[idx].isSaved;
                          });
                        },
                      ),
                      _ExploreMapTab(
                        places: _places,
                        onOpenCamera: () => setState(() => _selectedTab = 2),
                        onToggleSavePlace: (id) {
                          setState(() {
                            final idx = _places.indexWhere((p) => p.id == id);
                            if (idx >= 0) _places[idx].isSaved = !_places[idx].isSaved;
                          });
                        },
                      ),
                      _AiCameraDiscoverTab(
                        momentsCount: _moments.length,
                        onPublishMoment: _addCapturedMoment,
                        onOpenAiRecognition: () => _openAiAssistantSheet(innerContext),
                      ),
                      _JournalTab(
                        moments: _moments,
                        onOpenCamera: () => setState(() => _selectedTab = 2),
                        onDeleteMoment: (id) {
                          setState(() {
                            _moments.removeWhere((m) => m.id == id);
                          });
                        },
                      ),
                      _ProfileTab(
                        userName: _userName,
                        userHandle: _userHandle,
                        userEmail: _userEmail,
                        userCity: _userCity,
                        userBio: _userBio,
                        momentsCount: _moments.length,
                        isDarkMode: _isDarkMode,
                        isVietnamese: _isVietnamese,
                        onToggleTheme: () => setState(() => _isDarkMode = !_isDarkMode),
                        onToggleLanguage: () => setState(() => _isVietnamese = !_isVietnamese),
                        onSaveProfile: (name, handle, email, city, bio) {
                          setState(() {
                            _userName = name;
                            _userHandle = handle;
                            _userEmail = email;
                            _userCity = city;
                            _userBio = bio;
                          });
                        },
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
          bottomNavigationBar: JourneyBottomNav(
            currentIndex: _selectedTab,
            isVietnamese: _isVietnamese,
            onTap: (idx) => setState(() => _selectedTab = idx),
          ),
        ),
      ),
    );
  }
}

class Date {
  static DateTime now() => DateTime.now();
}

/// ============================================================================
/// TAB 1: HOME — TRANG CHỦ (Cinematic Hero + Quick Actions + Nearby + Recent Trips)
/// ============================================================================
class _HomeTab extends StatelessWidget {
  final String userName;
  final List<JourneyPlaceModel> places;
  final List<JourneyMomentModel> moments;
  final VoidCallback onOpenCamera;
  final VoidCallback onOpenMap;
  final VoidCallback onOpenJournal;
  final VoidCallback onOpenAiChat;
  final ValueChanged<String> onToggleSavePlace;

  const _HomeTab({
    required this.userName,
    required this.places,
    required this.moments,
    required this.onOpenCamera,
    required this.onOpenMap,
    required this.onOpenJournal,
    required this.onOpenAiChat,
    required this.onToggleSavePlace,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    final heroPlace = places.first;

    return ListView(
      padding: const EdgeInsets.fromLTRB(16, 6, 16, 28),
      children: [
        Text(
          'Xin chào, $userName!',
          style: TextStyle(
            fontFamily: 'Georgia',
            fontSize: 28,
            fontWeight: FontWeight.w700,
            color: colors.primaryText,
          ),
        ),
        const SizedBox(height: 12),
        JourneySearchBar(
          hintText: 'Tìm kiếm địa điểm, vật thể, câu chuyện...',
          onTapScan: onOpenCamera,
        ),
        const SizedBox(height: 16),

        // Hero Section — Vịnh Hạ Long
        Container(
          height: 250,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(JourneyRadius.xl),
            border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.45)),
          ),
          clipBehavior: Clip.antiAlias,
          child: Stack(
            children: [
              Positioned.fill(
                child: Image.asset(
                  heroPlace.imageAsset,
                  fit: BoxFit.cover,
                  errorBuilder: (_, __, ___) => Container(color: JourneyPalette.slate),
                ),
              ),
              Positioned.fill(
                child: Container(
                  decoration: const BoxDecoration(gradient: JourneyPalette.cinematicScrim),
                ),
              ),
              Positioned(
                top: 14,
                right: 14,
                child: Container(
                  padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                  decoration: BoxDecoration(
                    color: Colors.black.withOpacity(0.55),
                    borderRadius: BorderRadius.circular(JourneyRadius.pill),
                    border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.5)),
                  ),
                  child: Row(
                    children: [
                      const Icon(Icons.wb_sunny_rounded, size: 15, color: Color(0xFFFBBF24)),
                      const SizedBox(width: 5),
                      Text(
                        heroPlace.weather,
                        style: const TextStyle(
                          fontSize: 12.5,
                          fontWeight: FontWeight.w700,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Positioned(
                left: 18,
                right: 18,
                bottom: 18,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      heroPlace.name,
                      style: const TextStyle(
                        fontFamily: 'Georgia',
                        fontSize: 32,
                        fontWeight: FontWeight.w700,
                        color: JourneyPalette.ivory,
                      ),
                    ),
                    const SizedBox(height: 4),
                    const Text(
                      'Kỳ quan thiên nhiên thế giới của Việt Nam',
                      style: TextStyle(fontSize: 13.5, color: JourneyPalette.ivory),
                    ),
                    const SizedBox(height: 14),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        PrimaryButton(
                          text: 'Khám phá ngay',
                          leadingIcon: Icons.near_me_rounded,
                          trailingIcon: Icons.arrow_forward_rounded,
                          onPressed: onOpenMap,
                        ),
                        Row(
                          children: List.generate(
                            5,
                            (idx) => Container(
                              width: idx == 1 ? 8 : 6,
                              height: idx == 1 ? 8 : 6,
                              margin: const EdgeInsets.symmetric(horizontal: 3),
                              decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                color: idx == 1
                                    ? JourneyPalette.sandGold
                                    : Colors.white.withOpacity(0.45),
                              ),
                            ),
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
        const SizedBox(height: 20),

        // 4 Circular Quick Action Buttons
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _QuickActionCircle(
              icon: Icons.camera_alt_rounded,
              label: 'Chụp\nkhám phá',
              onTap: onOpenCamera,
            ),
            _QuickActionCircle(
              icon: Icons.chat_bubble_outline_rounded,
              label: 'Hỏi AI',
              onTap: onOpenAiChat,
            ),
            _QuickActionCircle(
              icon: Icons.location_on_outlined,
              label: 'Khám phá\nquanh đây',
              onTap: onOpenMap,
            ),
            _QuickActionCircle(
              icon: Icons.bookmark_border_rounded,
              label: 'Nhật ký',
              onTap: onOpenJournal,
            ),
          ],
        ),
        const SizedBox(height: 24),

        // Section: Khám phá gần bạn
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              'Khám phá gần bạn',
              style: TextStyle(
                fontFamily: 'Georgia',
                fontSize: 22,
                fontWeight: FontWeight.w700,
                color: colors.primaryText,
              ),
            ),
            TextButton(
              onPressed: onOpenMap,
              child: Text(
                'Xem tất cả >',
                style: TextStyle(color: colors.accent, fontWeight: FontWeight.w600),
              ),
            ),
          ],
        ),
        const SizedBox(height: 8),
        SizedBox(
          height: 165,
          child: ListView.separated(
            scrollDirection: Axis.horizontal,
            itemCount: places.length,
            separatorBuilder: (_, __) => const SizedBox(width: 12),
            itemBuilder: (context, index) {
              final place = places[index];
              return PlaceCard(
                title: place.name,
                province: '${place.province} • ${place.distance}',
                rating: place.rating,
                imageAsset: place.imageAsset,
                isSaved: place.isSaved,
                onTap: onOpenMap,
                onToggleSave: () => onToggleSavePlace(place.id),
              );
            },
          ),
        ),
        const SizedBox(height: 24),

        // Section: Hành trình gần đây
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              'Hành trình gần đây',
              style: TextStyle(
                fontFamily: 'Georgia',
                fontSize: 22,
                fontWeight: FontWeight.w700,
                color: colors.primaryText,
              ),
            ),
            TextButton(
              onPressed: onOpenJournal,
              child: Text(
                'Xem tất cả >',
                style: TextStyle(color: colors.accent, fontWeight: FontWeight.w600),
              ),
            ),
          ],
        ),
        const SizedBox(height: 8),
        GestureDetector(
          onTap: onOpenJournal,
          child: Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: colors.surface,
              borderRadius: BorderRadius.circular(JourneyRadius.lg),
              border: Border.all(color: colors.border),
            ),
            child: Row(
              children: [
                ClipRRect(
                  borderRadius: BorderRadius.circular(14),
                  child: Image.asset(
                    'assets/images/img_hero_halong_bay.jpg',
                    width: 88,
                    height: 68,
                    fit: BoxFit.cover,
                    errorBuilder: (_, __, ___) => Container(
                      width: 88,
                      height: 68,
                      color: colors.elevatedSurface,
                    ),
                  ),
                ),
                const SizedBox(width: 14),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Chuyến đi Hạ Long',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w700,
                          color: colors.primaryText,
                        ),
                      ),
                      const SizedBox(height: 6),
                      Text(
                        '📅 12 – 14/04/2025   ·   🖼️ ${moments.length} ảnh',
                        style: TextStyle(fontSize: 12, color: colors.secondaryText),
                      ),
                    ],
                  ),
                ),
                Container(
                  width: 36,
                  height: 36,
                  decoration: const BoxDecoration(
                    shape: BoxShape.circle,
                    gradient: JourneyPalette.champagneGradient,
                  ),
                  child: const Icon(
                    Icons.arrow_forward_rounded,
                    size: 18,
                    color: JourneyPalette.midnight,
                  ),
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}

class _QuickActionCircle extends StatelessWidget {
  final IconData icon;
  final String label;
  final VoidCallback onTap;

  const _QuickActionCircle({
    required this.icon,
    required this.label,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return GestureDetector(
      onTap: onTap,
      child: Column(
        children: [
          Container(
            width: 62,
            height: 62,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: colors.surface,
              border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.45), width: 1.4),
              boxShadow: [
                BoxShadow(
                  color: JourneyPalette.sandGold.withOpacity(0.14),
                  blurRadius: 14,
                ),
              ],
            ),
            child: Icon(icon, color: JourneyPalette.sandGold, size: 26),
          ),
          const SizedBox(height: 8),
          Text(
            label,
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 12,
              fontWeight: FontWeight.w600,
              color: colors.primaryText,
              height: 1.25,
            ),
          ),
        ],
      ),
    );
  }
}

/// ============================================================================
/// TAB 2: EXPLORE — KHÁM PHÁ BẢN ĐỒ VIỆT NAM
/// ============================================================================
class _ExploreMapTab extends StatefulWidget {
  final List<JourneyPlaceModel> places;
  final VoidCallback onOpenCamera;
  final ValueChanged<String> onToggleSavePlace;

  const _ExploreMapTab({
    required this.places,
    required this.onOpenCamera,
    required this.onToggleSavePlace,
  });

  @override
  State<_ExploreMapTab> createState() => _ExploreMapTabState();
}

class _ExploreMapTabState extends State<_ExploreMapTab> {
  String _selectedCategory = 'Việt Nam';
  String _selectedMarkerCity = 'Hà Nội';

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    final categories = ['Việt Nam', 'Thành phố', 'Danh lam thắng cảnh', 'Thiên nhiên', 'Ẩm thực'];

    return ListView(
      padding: const EdgeInsets.fromLTRB(16, 6, 16, 28),
      children: [
        JourneySearchBar(
          hintText: 'Tìm kiếm địa điểm, tỉnh thành tại Việt Nam',
          onTapScan: widget.onOpenCamera,
        ),
        const SizedBox(height: 12),
        SizedBox(
          height: 38,
          child: ListView.separated(
            scrollDirection: Axis.horizontal,
            itemCount: categories.length,
            separatorBuilder: (_, __) => const SizedBox(width: 8),
            itemBuilder: (context, index) {
              final cat = categories[index];
              return CategoryChip(
                label: cat,
                icon: index == 0 ? Icons.location_on : Icons.landscape_outlined,
                selected: _selectedCategory == cat,
                onTap: () => setState(() => _selectedCategory = cat),
              );
            },
          ),
        ),
        const SizedBox(height: 14),

        // Interactive Vietnam Map Container with glowing silhouette & photo markers
        Container(
          height: 310,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(JourneyRadius.xl),
            color: const Color(0xFF0E1D28),
            border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.45)),
          ),
          clipBehavior: Clip.antiAlias,
          child: Stack(
            children: [
              Positioned.fill(
                child: Image.asset(
                  'assets/images/img_sapa_terraces.jpg',
                  fit: BoxFit.cover,
                  color: const Color(0xAA0C1821),
                  colorBlendMode: BlendMode.darken,
                  errorBuilder: (_, __, ___) => Container(color: const Color(0xFF0E1D28)),
                ),
              ),
              // Weather Pill Top-Right
              Positioned(
                top: 14,
                right: 14,
                child: Container(
                  padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                  decoration: BoxDecoration(
                    color: Colors.black.withOpacity(0.65),
                    borderRadius: BorderRadius.circular(16),
                    border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.45)),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      const Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Icon(Icons.wb_sunny_rounded, size: 16, color: Color(0xFFFBBF24)),
                          SizedBox(width: 5),
                          Text(
                            '32°C',
                            style: TextStyle(
                              fontSize: 15,
                              fontWeight: FontWeight.w800,
                              color: Colors.white,
                            ),
                          ),
                        ],
                      ),
                      Text(
                        _selectedMarkerCity,
                        style: const TextStyle(fontSize: 11, color: JourneyPalette.ivory),
                      ),
                    ],
                  ),
                ),
              ),
              // Circular Photo Markers for Hà Nội, Đà Nẵng, TP. Hồ Chí Minh
              Positioned(
                top: 32,
                left: 110,
                child: _MapPhotoMarker(
                  label: 'Hà Nội',
                  imageAsset: 'assets/images/img_hanoi_temple.jpg',
                  selected: _selectedMarkerCity == 'Hà Nội',
                  onTap: () => setState(() => _selectedMarkerCity = 'Hà Nội'),
                ),
              ),
              Positioned(
                top: 118,
                left: 165,
                child: _MapPhotoMarker(
                  label: 'Đà Nẵng',
                  imageAsset: 'assets/images/img_hoian_lanterns.jpg',
                  selected: _selectedMarkerCity == 'Đà Nẵng',
                  onTap: () => setState(() => _selectedMarkerCity = 'Đà Nẵng'),
                ),
              ),
              Positioned(
                bottom: 58,
                left: 145,
                child: _MapPhotoMarker(
                  label: 'TP. Hồ Chí Minh',
                  imageAsset: 'assets/images/img_hero_halong_bay.jpg',
                  selected: _selectedMarkerCity == 'TP. Hồ Chí Minh',
                  onTap: () => setState(() => _selectedMarkerCity = 'TP. Hồ Chí Minh'),
                ),
              ),
              // Bottom-left summary glass badge
              Positioned(
                left: 14,
                bottom: 14,
                child: Container(
                  padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 10),
                  decoration: BoxDecoration(
                    color: Colors.black.withOpacity(0.65),
                    borderRadius: BorderRadius.circular(18),
                    border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.45)),
                  ),
                  child: const Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Icon(Icons.map_rounded, color: JourneyPalette.sandGold, size: 20),
                      SizedBox(width: 10),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Khám phá Việt Nam',
                            style: TextStyle(
                              fontSize: 13,
                              fontWeight: FontWeight.w700,
                              color: Colors.white,
                            ),
                          ),
                          Text(
                            '100+ địa điểm nổi bật',
                            style: TextStyle(fontSize: 11, color: JourneyPalette.ivory),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
        const SizedBox(height: 22),

        // Địa điểm nổi bật
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              'Địa điểm nổi bật',
              style: TextStyle(
                fontFamily: 'Georgia',
                fontSize: 22,
                fontWeight: FontWeight.w700,
                color: colors.primaryText,
              ),
            ),
            Text(
              'Xem tất cả >',
              style: TextStyle(color: colors.accent, fontWeight: FontWeight.w600),
            ),
          ],
        ),
        const SizedBox(height: 10),
        SizedBox(
          height: 205,
          child: ListView.separated(
            scrollDirection: Axis.horizontal,
            itemCount: widget.places.length,
            separatorBuilder: (_, __) => const SizedBox(width: 12),
            itemBuilder: (context, index) {
              final p = widget.places[index];
              return PlaceCard(
                title: p.name,
                province: p.province,
                rating: p.rating,
                imageAsset: p.imageAsset,
                isSaved: p.isSaved,
                onTap: () {},
                onToggleSave: () => widget.onToggleSavePlace(p.id),
              );
            },
          ),
        ),
      ],
    );
  }
}

class _MapPhotoMarker extends StatelessWidget {
  final String label;
  final String imageAsset;
  final bool selected;
  final VoidCallback onTap;

  const _MapPhotoMarker({
    required this.label,
    required this.imageAsset,
    required this.selected,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
            width: 46,
            height: 46,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              border: Border.all(
                color: selected ? JourneyPalette.sandGold : Colors.white,
                width: 2.5,
              ),
              boxShadow: [
                BoxShadow(
                  color: JourneyPalette.sandGold.withOpacity(0.45),
                  blurRadius: 10,
                ),
              ],
            ),
            clipBehavior: Clip.antiAlias,
            child: Image.asset(
              imageAsset,
              fit: BoxFit.cover,
              errorBuilder: (_, __, ___) => Container(color: JourneyPalette.slate),
            ),
          ),
          const SizedBox(width: 6),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
            decoration: BoxDecoration(
              color: Colors.black.withOpacity(0.75),
              borderRadius: BorderRadius.circular(JourneyRadius.pill),
              border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.55)),
            ),
            child: Text(
              '$label ♦',
              style: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w700,
                color: Colors.white,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

/// ============================================================================
/// TAB 3: QUÉT / AI CAMERA — MÁY ẢNH KHOẢNH KHẮC + SPOTIFY MUSIC + GPS + NHẬN DIỆN AI
/// ============================================================================
class _AiCameraDiscoverTab extends StatefulWidget {
  final int momentsCount;
  final void Function({
    required String placeName,
    required String caption,
    required String spotifyTrack,
    required String temperatureTag,
  }) onPublishMoment;
  final VoidCallback onOpenAiRecognition;

  const _AiCameraDiscoverTab({
    required this.momentsCount,
    required this.onPublishMoment,
    required this.onOpenAiRecognition,
  });

  @override
  State<_AiCameraDiscoverTab> createState() => _AiCameraDiscoverTabState();
}

class _AiCameraDiscoverTabState extends State<_AiCameraDiscoverTab> {
  String _activeDrawer = 'NONE'; // NONE | SPOTIFY | LOCATION
  String _selectedSpotifyTrack = '';
  String _locationText = '';
  final String _temperatureTag = '28°C ☀️';

  final List<(String, String, String)> _spotifyTracks = const [
    ('Đừng Làm Trái Tim Anh Đau', 'Sơn Tùng M-TP · 04:39', 'Now Playing'),
    ('Bình Yên', 'Vũ. ft. Binz · 03:45', 'Playing Next'),
    ('Một Đêm Say', 'Thịnh Suy · 03:18', 'Recent'),
    ('Chúng Ta Của Tương Lai', 'Sơn Tùng M-TP · 04:09', 'Recent'),
  ];

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return ListView(
      padding: const EdgeInsets.fromLTRB(16, 6, 16, 24),
      children: [
        Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: colors.surface.withOpacity(0.92),
            borderRadius: BorderRadius.circular(JourneyRadius.xl),
            border: Border.all(color: JourneyPalette.sandGold.withOpacity(0.45)),
          ),
          child: Column(
            children: [
              // Top Camera Header Row
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Row(
                    children: [
                      Icon(Icons.camera_alt_rounded, color: colors.primaryText, size: 22),
                      const SizedBox(width: 8),
                      Text(
                        'Máy ảnh Khoảnh khắc',
                        style: TextStyle(
                          fontSize: 17,
                          fontWeight: FontWeight.w800,
                          color: colors.primaryText,
                        ),
                      ),
                    ],
                  ),
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                    decoration: BoxDecoration(
                      gradient: JourneyPalette.champagneGradient,
                      borderRadius: BorderRadius.circular(JourneyRadius.pill),
                    ),
                    child: const Text(
                      '+ Sẵn sàng chụp',
                      style: TextStyle(
                        fontSize: 12,
                        fontWeight: FontWeight.w700,
                        color: JourneyPalette.midnight,
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 14),

              // 1:1 Square Viewfinder with Focus Brackets & Metadata Pill
              AspectRatio(
                aspectRatio: 1.0,
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(24),
                    border: Border.all(color: JourneyPalette.sandGold, width: 2),
                  ),
                  clipBehavior: Clip.antiAlias,
                  child: Stack(
                    children: [
                      Positioned.fill(
                        child: Image.asset(
                          'assets/images/img_hero_halong_bay.jpg',
                          fit: BoxFit.cover,
                          errorBuilder: (_, __, ___) => Container(color: JourneyPalette.midnight),
                        ),
                      ),
                      Positioned(
                        top: 12,
                        left: 12,
                        child: Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                          decoration: BoxDecoration(
                            color: Colors.black.withOpacity(0.65),
                            borderRadius: BorderRadius.circular(JourneyRadius.pill),
                          ),
                          child: Text(
                            '🕒 09:41   |   🖼️ ${widget.momentsCount}/64   |   🌡️ $_temperatureTag',
                            style: const TextStyle(
                              fontSize: 12,
                              fontWeight: FontWeight.w700,
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                      if (_selectedSpotifyTrack.isNotEmpty)
                        Positioned(
                          bottom: 12,
                          left: 12,
                          right: 12,
                          child: Container(
                            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 7),
                            decoration: BoxDecoration(
                              color: const Color(0xDD14532D),
                              borderRadius: BorderRadius.circular(JourneyRadius.pill),
                              border: Border.all(color: JourneyPalette.spotifyGreen),
                            ),
                            child: Text(
                              '💿 $_selectedSpotifyTrack',
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
                              style: const TextStyle(
                                fontSize: 12,
                                fontWeight: FontWeight.w700,
                                color: Colors.white,
                              ),
                            ),
                          ),
                        ),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 14),

              // Camera Widgets Strip: Spotify Music, Gắn vị trí, Nhận diện AI
               SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: Row(
                  children: [
                    CategoryChip(
                      label: _selectedSpotifyTrack.isNotEmpty
                          ? 'Spotify Music · ${_selectedSpotifyTrack.split(' - ').first}'
                          : 'Spotify Music',
                      icon: Icons.music_note_rounded,
                      selected: _activeDrawer == 'SPOTIFY' || _selectedSpotifyTrack.isNotEmpty,
                      onTap: () => setState(() {
                        _activeDrawer = _activeDrawer == 'SPOTIFY' ? 'NONE' : 'SPOTIFY';
                      }),
                    ),
                    const SizedBox(width: 8),
                    CategoryChip(
                      label: _locationText.isNotEmpty ? _locationText : 'Gắn vị trí',
                      icon: Icons.location_pin,
                      selected: _activeDrawer == 'LOCATION' || _locationText.isNotEmpty,
                      onTap: () => setState(() {
                        _activeDrawer = _activeDrawer == 'LOCATION' ? 'NONE' : 'LOCATION';
                        if (_locationText.isEmpty) _locationText = 'Vị trí hiện tại của bạn';
                      }),
                    ),
                    const SizedBox(width: 8),
                    CategoryChip(
                      label: 'Nhận diện',
                      icon: Icons.wb_sunny_outlined,
                      selected: true,
                      onTap: widget.onOpenAiRecognition,
                    ),
                  ],
                ),
              ),

              // Expandable Spotify Music Drawer
              if (_activeDrawer == 'SPOTIFY') ...[
                const SizedBox(height: 12),
                Container(
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: colors.elevatedSurface,
                    borderRadius: BorderRadius.circular(16),
                    border: Border.all(color: JourneyPalette.spotifyGreen.withOpacity(0.5)),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        '🟢 Spotify Music · Đang phát (Now Playing)',
                        style: TextStyle(fontSize: 13, fontWeight: FontWeight.w700),
                      ),
                      const SizedBox(height: 8),
                      ..._spotifyTracks.map((track) {
                        final full = '${track.$1} - ${track.$2.split(' · ').first}';
                        final selected = _selectedSpotifyTrack == full;
                        return ListTile(
                          dense: true,
                          contentPadding: EdgeInsets.zero,
                          leading: const Icon(Icons.album_rounded, color: JourneyPalette.spotifyGreen),
                          title: Text(track.$1, style: const TextStyle(fontWeight: FontWeight.w700)),
                          subtitle: Text(track.$2),
                          trailing: Text(
                            selected ? '● Đang gắn' : track.$3,
                            style: TextStyle(
                              fontSize: 11,
                              fontWeight: FontWeight.w700,
                              color: selected ? JourneyPalette.spotifyGreen : colors.secondaryText,
                            ),
                          ),
                          onTap: () => setState(() {
                            _selectedSpotifyTrack = selected ? '' : full;
                          }),
                        );
                      }),
                    ],
                  ),
                ),
              ],
              const SizedBox(height: 18),

              // Shutter Controls Row (Thư viện | Chụp ảnh | Làm lại)
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  _CameraActionCircle(
                    icon: Icons.photo_library_outlined,
                    label: 'Thư viện',
                    onTap: () => widget.onPublishMoment(
                      placeName: _locationText,
                      caption: 'Khoảnh khắc từ thư viện ảnh',
                      spotifyTrack: _selectedSpotifyTrack,
                      temperatureTag: _temperatureTag,
                    ),
                  ),
                  GestureDetector(
                    onTap: () => widget.onPublishMoment(
                      placeName: _locationText,
                      caption: 'Khoảnh khắc chụp trực tiếp ✨',
                      spotifyTrack: _selectedSpotifyTrack,
                      temperatureTag: _temperatureTag,
                    ),
                    child: Column(
                      children: [
                        Container(
                          width: 82,
                          height: 82,
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            color: JourneyPalette.midnight,
                            border: Border.all(color: JourneyPalette.sandGold, width: 4),
                            boxShadow: [
                              BoxShadow(
                                color: JourneyPalette.sandGold.withOpacity(0.45),
                                blurRadius: 18,
                              ),
                            ],
                          ),
                          child: const Icon(
                            Icons.camera_alt_rounded,
                            color: JourneyPalette.ivory,
                            size: 34,
                          ),
                        ),
                        const SizedBox(height: 6),
                        Text(
                          'Chụp ảnh',
                          style: TextStyle(
                            fontSize: 13,
                            fontWeight: FontWeight.w700,
                            color: colors.primaryText,
                          ),
                        ),
                      ],
                    ),
                  ),
                  _CameraActionCircle(
                    icon: Icons.refresh_rounded,
                    label: 'Làm lại',
                    onTap: () => setState(() {
                      _selectedSpotifyTrack = '';
                      _locationText = '';
                      _activeDrawer = 'NONE';
                    }),
                  ),
                ],
              ),
              const SizedBox(height: 14),
              GestureDetector(
                onTap: widget.onOpenAiRecognition,
                child: Container(
                  padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
                  decoration: BoxDecoration(
                    color: colors.elevatedSurface.withOpacity(0.8),
                    borderRadius: BorderRadius.circular(JourneyRadius.pill),
                    border: Border.all(color: colors.border),
                  ),
                  child: const Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Icon(Icons.auto_awesome, size: 16, color: JourneyPalette.sandGold),
                      SizedBox(width: 6),
                      Text(
                        'Chụp để khám phá, AI sẽ nhận diện ngay!',
                        style: TextStyle(fontSize: 12.5, fontWeight: FontWeight.w600),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}

class _CameraActionCircle extends StatelessWidget {
  final IconData icon;
  final String label;
  final VoidCallback onTap;

  const _CameraActionCircle({
    required this.icon,
    required this.label,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return GestureDetector(
      onTap: onTap,
      child: Column(
        children: [
          Container(
            width: 54,
            height: 54,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: JourneyPalette.ivory,
              border: Border.all(color: JourneyPalette.sandGold),
            ),
            child: Icon(icon, color: JourneyPalette.midnight, size: 24),
          ),
          const SizedBox(height: 6),
          Text(
            label,
            style: TextStyle(fontSize: 12, color: colors.primaryText),
          ),
        ],
      ),
    );
  }
}

/// ============================================================================
/// TAB 4: JOURNAL — NHẬT KÝ HÀNH TRÌNH (2-Column Photo Grid + Filter Chips)
/// ============================================================================
class _JournalTab extends StatefulWidget {
  final List<JourneyMomentModel> moments;
  final VoidCallback onOpenCamera;
  final ValueChanged<String> onDeleteMoment;

  const _JournalTab({
    required this.moments,
    required this.onOpenCamera,
    required this.onDeleteMoment,
  });

  @override
  State<_JournalTab> createState() => _JournalTabState();
}

class _JournalTabState extends State<_JournalTab> {
  String _selectedSubTab = 'PHOTOS';

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return ListView(
      padding: const EdgeInsets.fromLTRB(16, 6, 16, 28),
      children: [
        Row(
          crossAxisAlignment: CrossAxisAlignment.end,
          children: [
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Nhật ký hành trình',
                    style: TextStyle(
                      fontFamily: 'Georgia',
                      fontSize: 28,
                      fontWeight: FontWeight.w700,
                      color: colors.primaryText,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    'Những khoảnh khắc đẹp nhất trong hành trình của bạn',
                    style: TextStyle(fontSize: 13, color: colors.secondaryText),
                  ),
                ],
              ),
            ),
            const SizedBox(width: 10),
            PrimaryButton(
              text: 'Chụp mới',
              leadingIcon: Icons.camera_alt_rounded,
              onPressed: widget.onOpenCamera,
            ),
          ],
        ),
        const SizedBox(height: 16),
        SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: Row(
            children: [
              CategoryChip(
                label: 'Ảnh của tôi (${widget.moments.length})',
                icon: Icons.photo_library_outlined,
                selected: _selectedSubTab == 'PHOTOS',
                onTap: () => setState(() => _selectedSubTab = 'PHOTOS'),
              ),
              const SizedBox(width: 8),
              CategoryChip(
                label: 'Chuyến đi (12)',
                icon: Icons.flight_takeoff_rounded,
                selected: _selectedSubTab == 'TRIPS',
                onTap: () => setState(() => _selectedSubTab = 'TRIPS'),
              ),
              const SizedBox(width: 8),
              CategoryChip(
                label: 'Khoảnh khắc (87)',
                icon: Icons.star_rounded,
                selected: _selectedSubTab == 'FAVORITES',
                onTap: () => setState(() => _selectedSubTab = 'FAVORITES'),
              ),
            ],
          ),
        ),
        const SizedBox(height: 16),
        GridView.builder(
          shrinkWrap: true,
          physics: const NeverScrollableScrollPhysics(),
          itemCount: widget.moments.length,
          gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 2,
            mainAxisSpacing: 12,
            crossAxisSpacing: 12,
            childAspectRatio: 0.92,
          ),
          itemBuilder: (context, index) {
            final item = widget.moments[index];
            return JournalPhotoCard(
              placeName: item.placeName,
              dateLabel: item.dateLabel,
              imageAsset: item.imageAsset,
              onTap: () {},
              onDelete: () => widget.onDeleteMoment(item.id),
            );
          },
        ),
      ],
    );
  }
}

/// ============================================================================
/// TAB 5: PROFILE — HỒ SƠ CÁ NHÂN (Sunrise Cover + 4 Stats + Info Cards + Friends)
/// ============================================================================
class _ProfileTab extends StatelessWidget {
  final String userName;
  final String userHandle;
  final String userEmail;
  final String userCity;
  final String userBio;
  final int momentsCount;
  final bool isDarkMode;
  final bool isVietnamese;
  final VoidCallback onToggleTheme;
  final VoidCallback onToggleLanguage;
  final void Function(String, String, String, String, String) onSaveProfile;

  const _ProfileTab({
    required this.userName,
    required this.userHandle,
    required this.userEmail,
    required this.userCity,
    required this.userBio,
    required this.momentsCount,
    required this.isDarkMode,
    required this.isVietnamese,
    required this.onToggleTheme,
    required this.onToggleLanguage,
    required this.onSaveProfile,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return ListView(
      padding: const EdgeInsets.fromLTRB(16, 6, 16, 28),
      children: [
        // Centered Avatar + Name + Handle + Bio
        Column(
          children: [
            Container(
              width: 108,
              height: 108,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                border: Border.all(color: Colors.white, width: 3),
                boxShadow: [
                  BoxShadow(
                    color: JourneyPalette.sandGold.withOpacity(0.35),
                    blurRadius: 18,
                  ),
                ],
              ),
              clipBehavior: Clip.antiAlias,
              child: Image.asset(
                'assets/images/img_sapa_terraces.jpg',
                fit: BoxFit.cover,
                errorBuilder: (_, __, ___) => Container(color: colors.surface),
              ),
            ),
            const SizedBox(height: 12),
            Text(
              userName,
              style: TextStyle(
                fontFamily: 'Georgia',
                fontSize: 28,
                fontWeight: FontWeight.w700,
                color: colors.primaryText,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              '$userHandle   |   📍 Việt Nam',
              style: TextStyle(fontSize: 13, color: colors.secondaryText),
            ),
            const SizedBox(height: 8),
            Text(
              userBio,
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 13.5, color: colors.primaryText),
            ),
          ],
        ),
        const SizedBox(height: 18),

        // 4-Column Stats Row
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            _ProfileStatCol(icon: Icons.flight_takeoff_rounded, value: '12', label: 'Chuyến đi'),
            _ProfileStatCol(icon: Icons.camera_alt_outlined, value: '245', label: 'Khám phá'),
            _ProfileStatCol(icon: Icons.photo_outlined, value: '$momentsCount', label: 'Khoảnh khắc'),
            _ProfileStatCol(icon: Icons.star_rounded, value: '4.9', label: 'Đánh giá'),
          ],
        ),
        const SizedBox(height: 18),
        Center(
          child: PrimaryButton(
            text: 'Chỉnh sửa hồ sơ',
            leadingIcon: Icons.photo_outlined,
            onPressed: () {},
          ),
        ),
        const SizedBox(height: 18),

        // Glass Info Rows
        Container(
          decoration: BoxDecoration(
            color: colors.surface,
            borderRadius: BorderRadius.circular(20),
            border: Border.all(color: colors.border),
          ),
          child: Column(
            children: [
              ListTile(
                leading: const Icon(Icons.person_rounded, color: JourneyPalette.sandGold),
                title: const Text('Thông tin cá nhân', style: TextStyle(fontSize: 12)),
                subtitle: Text(userName, style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w700)),
                trailing: const Icon(Icons.chevron_right_rounded),
              ),
              Divider(height: 1, color: colors.border),
              ListTile(
                leading: const Icon(Icons.email_outlined, color: JourneyPalette.sandGold),
                title: const Text('Email', style: TextStyle(fontSize: 12)),
                subtitle: Text(userEmail, style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w700)),
                trailing: const Icon(Icons.chevron_right_rounded),
              ),
              Divider(height: 1, color: colors.border),
              ListTile(
                leading: const Icon(Icons.location_on_outlined, color: JourneyPalette.sandGold),
                title: const Text('Vị trí', style: TextStyle(fontSize: 12)),
                subtitle: Text(userCity, style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w700)),
                trailing: const Icon(Icons.chevron_right_rounded),
              ),
            ],
          ),
        ),
        const SizedBox(height: 14),
        SwitchListTile(
          title: Text(
            'Giao diện Midnight Dark Mode',
            style: TextStyle(color: colors.primaryText, fontWeight: FontWeight.w600),
          ),
          value: isDarkMode,
          onChanged: (_) => onToggleTheme(),
        ),
      ],
    );
  }
}

class _ProfileStatCol extends StatelessWidget {
  final IconData icon;
  final String value;
  final String label;

  const _ProfileStatCol({
    required this.icon,
    required this.value,
    required this.label,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return Column(
      children: [
        Icon(icon, color: JourneyPalette.sandGold, size: 20),
        const SizedBox(height: 4),
        Text(
          value,
          style: TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.w800,
            color: colors.primaryText,
          ),
        ),
        Text(
          label,
          style: TextStyle(fontSize: 11.5, color: colors.secondaryText),
        ),
      ],
    );
  }
}

class _AiAssistantBottomSheet extends StatelessWidget {
  const _AiAssistantBottomSheet();

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            '✨ Trợ lý AI & Thuyết minh Giọng nói',
            style: TextStyle(
              fontFamily: 'Georgia',
              fontSize: 22,
              fontWeight: FontWeight.w700,
              color: JourneyPalette.ivory,
            ),
          ),
          const SizedBox(height: 8),
          const Text(
            'Hỏi về lịch sử, kiến trúc, ẩm thực hoặc bật AI Voice Guide cho mọi điểm đến tại Việt Nam.',
            style: TextStyle(fontSize: 13.5, color: JourneyPalette.mistText),
          ),
          const SizedBox(height: 16),
          PrimaryButton(
            text: 'Đóng',
            onPressed: () => Navigator.of(context).pop(),
          ),
        ],
      ),
    );
  }
}

class _PremiumSubscriptionSheet extends StatelessWidget {
  const _PremiumSubscriptionSheet();

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            '👑 Journey AI Premium',
            style: TextStyle(
              fontFamily: 'Georgia',
              fontSize: 22,
              fontWeight: FontWeight.w700,
              color: JourneyPalette.sandGold,
            ),
          ),
          const SizedBox(height: 8),
          const Text(
            'Mở khóa AI Camera nhận diện không giới hạn, AI Voice Guide đa ngôn ngữ và tạo Phim kỷ niệm 4K.',
            style: TextStyle(fontSize: 13.5, color: JourneyPalette.ivory),
          ),
          const SizedBox(height: 16),
          PrimaryButton(
            text: 'Trải nghiệm ngay',
            onPressed: () => Navigator.of(context).pop(),
          ),
        ],
      ),
    );
  }
}
