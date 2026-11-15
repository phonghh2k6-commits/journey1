package com.example.model

import androidx.annotation.DrawableRes
import com.example.R

enum class AppLanguage(val code: String, val label: String) {
    EN("EN", "English"),
    VI("VI", "Tiếng Việt")
}

enum class ScreenStateMode(val labelEn: String, val labelVi: String) {
    DEFAULT("Default", "Mặc định"),
    LOADING("Loading", "Đang tải"),
    EMPTY("Empty", "Trống"),
    ERROR("Error", "Lỗi hệ thống"),
    SUCCESS("Success", "Thành công"),
    OFFLINE("Offline", "Ngoại tuyến"),
    PERMISSION_DENIED("Permission Denied", "Từ chối quyền"),
    PARTIAL_DATA("Partial Data", "Dữ liệu một phần"),
    PREMIUM_LOCKED("Premium Locked", "Khóa Premium")
}

enum class ViewportPreset(val widthDp: Int, val label: String) {
    FULL(0, "Native Device"),
    ANDROID_360(360, "360dp Android"),
    ANDROID_393(393, "393dp Android"),
    ANDROID_412(412, "412dp Android"),
    IPHONE_390(390, "390dp iPhone"),
    IPHONE_430(430, "430dp iPhone"),
    TABLET_768(768, "Tablet Split")
}

enum class PrimaryTab(
    val id: String,
    val labelEn: String,
    val labelVi: String
) {
    HOME("NAV_HOME", "Home", "Trang chủ"),
    EXPLORE("NAV_EXPLORE", "Explore", "Khám phá"),
    DISCOVER("NAV_DISCOVER", "Scan", "Quét"),
    JOURNAL("NAV_JOURNAL", "Journal", "Nhật ký"),
    PROFILE("NAV_PROFILE", "Profile", "Hồ sơ");

    val defaultScreen: JourneyScreenId
        get() = when (this) {
            HOME -> JourneyScreenId.B1_HOME_DASHBOARD
            EXPLORE -> JourneyScreenId.C1_EXPLORE_MAP
            DISCOVER -> JourneyScreenId.D1_AI_CAMERA
            JOURNAL -> JourneyScreenId.F1_JOURNAL_HOME
            PROFILE -> JourneyScreenId.I1_PROFILE
        }
}

enum class ScreenGroup(val code: String, val titleEn: String, val titleVi: String) {
    DESIGN_SYSTEM("PHASE 1", "Design System & Handoff", "Hệ thống Thiết kế & Bàn giao"),
    GROUP_A("GROUP A", "Onboarding & Auth", "Giới thiệu & Xác thực"),
    GROUP_B("GROUP B", "Home & Social Feed", "Trang chủ & Bảng tin"),
    GROUP_C("GROUP C", "Visited Places Map", "Bản đồ Nơi đã đến"),
    GROUP_D("GROUP D", "Camera", "Ống kính Chụp ảnh"),
    GROUP_E("GROUP E", "AI Assistant & Voice", "Trợ lý AI & Giọng nói"),
    GROUP_F("GROUP F", "Check-in Journal", "Nhật ký Khoảnh khắc"),
    GROUP_G("GROUP G", "Discovery Collections", "Bộ sưu tập Khám phá"),
    GROUP_H("GROUP H", "Memories & Video Studio", "Ký ức & Phim Điện ảnh"),
    GROUP_I("GROUP I", "Personal Profile", "Hồ sơ Cá nhân")
}

enum class JourneyScreenId(
    val code: String,
    val group: ScreenGroup,
    val titleEn: String,
    val titleVi: String,
    val showBottomBar: Boolean,
    val primaryTab: PrimaryTab? = null
) {
    // Phase 1 & Deliverable Studio
    DS_TOKENS_COMPONENTS(
        "P1-DS01",
        ScreenGroup.DESIGN_SYSTEM,
        "Design System & Tokens",
        "Hệ thống Token & Thành phần",
        showBottomBar = true,
        primaryTab = PrimaryTab.PROFILE
    ),
    DS_HANDOFF_ACCEPTANCE(
        "P6-QA01",
        ScreenGroup.DESIGN_SYSTEM,
        "Architecture & QA Report",
        "Kiến trúc & Báo cáo Nghiệm thu",
        showBottomBar = true,
        primaryTab = PrimaryTab.PROFILE
    ),

    // Group A: Onboarding & Authentication (No bottom navigation)
    A1_SPLASH("A1-SPLASH", ScreenGroup.GROUP_A, "Splash", "Màn hình Khởi động", false),
    A2_WELCOME("A2-WELCOME", ScreenGroup.GROUP_A, "Welcome", "Chào mừng", false),
    A3_ONBOARDING("A3-ONBOARD", ScreenGroup.GROUP_A, "Onboarding Slides", "Giới thiệu Tính năng", false),
    A4_PERMISSIONS("A4-PERMS", ScreenGroup.GROUP_A, "Permission Introduction", "Cấp quyền Truy cập", false),
    A5_LOGIN("A5-LOGIN", ScreenGroup.GROUP_A, "Login", "Đăng nhập", false),
    A6_REGISTER("A6-REGISTER", ScreenGroup.GROUP_A, "Register", "Đăng ký Tài khoản", false),
    A7_FORGOT_PASSWORD("A7-FORGOT", ScreenGroup.GROUP_A, "Forgot Password", "Quên Mật khẩu", false),
    A8_EMAIL_VERIFY("A8-VERIFY", ScreenGroup.GROUP_A, "Email Verification", "Xác thực Email", false),

    // Group B: Home
    B1_HOME_DASHBOARD("B1-HOME", ScreenGroup.GROUP_B, "Social Feed", "Bảng tin Khoảnh khắc", true, PrimaryTab.HOME),
    B2_GLOBAL_SEARCH("B2-SEARCH", ScreenGroup.GROUP_B, "Global Search", "Tìm kiếm Toàn cầu", true, PrimaryTab.HOME),
    B3_NOTIFICATIONS("B3-NOTIF", ScreenGroup.GROUP_B, "Notifications", "Thông báo", true, PrimaryTab.HOME),

    // Group C: Explore
    C1_EXPLORE_MAP("C1-MAP", ScreenGroup.GROUP_C, "Visited Map", "Bản đồ Nơi đã đi", true, PrimaryTab.EXPLORE),
    C2_DISCOVERY_FEED("C2-FEED", ScreenGroup.GROUP_C, "Discovery Feed", "Bảng tin Điểm đến", true, PrimaryTab.EXPLORE),
    C3_PLACE_DETAILS("C3-PLACE", ScreenGroup.GROUP_C, "Place Details", "Chi tiết Điểm đến", true, PrimaryTab.EXPLORE),
    C4_SAVED_PLACES("C4-SAVED", ScreenGroup.GROUP_C, "Saved Places", "Địa điểm Đã lưu", true, PrimaryTab.EXPLORE),

    // Group D: Camera
    D1_AI_CAMERA("D1-CAMERA", ScreenGroup.GROUP_D, "Camera", "Ống kính", true, PrimaryTab.DISCOVER),
    D2_IMAGE_PREVIEW("D2-PREVIEW", ScreenGroup.GROUP_D, "Image Preview", "Xem trước Ảnh chụp", true, PrimaryTab.DISCOVER),
    D3_AI_PROCESSING("D3-PROCESS", ScreenGroup.GROUP_D, "AI Processing", "Đang Phân tích AI", false, PrimaryTab.DISCOVER),
    D4_RECOGNITION_RESULT("D4-RESULT", ScreenGroup.GROUP_D, "Recognition Result", "Kết quả Nhận diện", true, PrimaryTab.DISCOVER),
    D5_DISCOVERY_DETAILS("D5-DETAIL", ScreenGroup.GROUP_D, "Discovery Details", "Chiều sâu Lịch sử", true, PrimaryTab.DISCOVER),

    // Group E: AI Assistant (Full-screen voice hides bottom bar)
    E1_AI_CHAT("E1-CHAT", ScreenGroup.GROUP_E, "AI Chat", "Trò chuyện AI", true, PrimaryTab.DISCOVER),
    E2_VOICE_ASSISTANT("E2-VOICE", ScreenGroup.GROUP_E, "Voice Assistant", "Thuyết minh Giọng nói", false, PrimaryTab.DISCOVER),
    E3_CONVERSATION_HISTORY("E3-HISTORY", ScreenGroup.GROUP_E, "Conversation History", "Lịch sử Hội thoại", true, PrimaryTab.DISCOVER),

    // Group F: Journal
    F1_JOURNAL_HOME("F1-JOURNAL", ScreenGroup.GROUP_F, "Journal Home", "Nhật ký Hành trình", true, PrimaryTab.JOURNAL),
    F2_CREATE_TRIP("F2-NEW-TRIP", ScreenGroup.GROUP_F, "Create Trip", "Tạo Chuyến đi Mới", true, PrimaryTab.JOURNAL),
    F3_TRIP_DETAIL("F3-TRIP", ScreenGroup.GROUP_F, "Trip Detail", "Chi tiết Chuyến đi", true, PrimaryTab.JOURNAL),
    F4_TRIP_TIMELINE("F4-TIMELINE", ScreenGroup.GROUP_F, "Trip Timeline", "Dòng thời gian", true, PrimaryTab.JOURNAL),
    F5_CREATE_DAILY_MOMENT("F5-MOMENT", ScreenGroup.GROUP_F, "Create Daily Moment", "Ghi lại Khoảnh khắc", true, PrimaryTab.JOURNAL),
    F6_MEMORY_DETAIL("F6-MEMORY", ScreenGroup.GROUP_F, "Memory Detail", "Chi tiết Ký ức", true, PrimaryTab.JOURNAL),

    // Group G: Collections
    G1_DISCOVERY_COLLECTION("G1-COLLS", ScreenGroup.GROUP_G, "Discovery Collections", "Bộ sưu tập Khám phá", true, PrimaryTab.EXPLORE),
    G2_COLLECTION_DETAILS("G2-COLL-DET", ScreenGroup.GROUP_G, "Collection Details", "Chi tiết Bộ sưu tập", true, PrimaryTab.EXPLORE),

    // Group H: Memories & Video
    H1_AI_STORY_PREVIEW("H1-STORY", ScreenGroup.GROUP_H, "AI Story Preview", "Câu chuyện AI Kể", true, PrimaryTab.JOURNAL),
    H2_VIDEO_CREATOR("H2-VIDEO-EDIT", ScreenGroup.GROUP_H, "Video Creator", "Dựng Phim Hành trình", true, PrimaryTab.JOURNAL),
    H3_VIDEO_PREVIEW_EXPORT("H3-VIDEO-EXP", ScreenGroup.GROUP_H, "Video Preview & Export", "Xem & Xuất Phim 4K", false, PrimaryTab.JOURNAL),

    // Group I: Profile
    I1_PROFILE("I1-PROFILE", ScreenGroup.GROUP_I, "Profile", "Hồ sơ Cá nhân", true, PrimaryTab.PROFILE),
    I2_SETTINGS("I2-SETTINGS", ScreenGroup.GROUP_I, "Settings", "Cài đặt Ứng dụng", true, PrimaryTab.PROFILE),
    I3_SUBSCRIPTION("I3-SUB", ScreenGroup.GROUP_I, "Subscription", "Gói hội viên Journey+", true, PrimaryTab.PROFILE),
    I4_PRIVACY_AND_DATA("I4-PRIVACY", ScreenGroup.GROUP_I, "Privacy & Data", "Quyền riêng tư & Dữ liệu", true, PrimaryTab.PROFILE)
}

// ============================================================================
// DOMAIN MODELS FOR REALISTIC VIETNAMESE & INTERNATIONAL EDITORIAL SAMPLE DATA
// ============================================================================

data class PlaceItem(
    val id: String,
    val nameEn: String,
    val nameVi: String,
    val regionEn: String,
    val regionVi: String,
    val categoryEn: String,
    val categoryVi: String,
    val eraEn: String,
    val eraVi: String,
    val coordinates: String,
    val mapXPercent: Float,
    val mapYPercent: Float,
    val confidenceScore: Float,
    val rating: Float,
    val distanceKm: String,
    val audioDuration: String,
    @DrawableRes val imageRes: Int,
    val summaryEn: String,
    val summaryVi: String,
    val architecturalNotesEn: String,
    val architecturalNotesVi: String,
    val culturalStoryEn: String,
    val culturalStoryVi: String,
    val isSaved: Boolean = false
)

data class TripJournal(
    val id: String,
    val titleEn: String,
    val titleVi: String,
    val dateRange: String,
    val locationEn: String,
    val locationVi: String,
    @DrawableRes val coverRes: Int,
    val daysCount: Int,
    val momentsCount: Int,
    val discoveriesCount: Int,
    val storyExcerptEn: String,
    val storyExcerptVi: String,
    val moments: List<DailyMoment>
)

data class DailyMoment(
    val id: String,
    val dayNumber: Int,
    val timeLabel: String,
    val titleEn: String,
    val titleVi: String,
    val locationTag: String,
    val coordinates: String,
    val noteEn: String,
    val noteVi: String,
    @DrawableRes val photoRes: Int,
    val weatherTag: String
)

data class DiscoveryCollection(
    val id: String,
    val titleEn: String,
    val titleVi: String,
    val subtitleEn: String,
    val subtitleVi: String,
    @DrawableRes val coverRes: Int,
    val placeIds: List<String>,
    val badgeTagEn: String,
    val badgeTagVi: String
)

data class ChatMessageItem(
    val id: String,
    val isUser: Boolean,
    val textEn: String,
    val textVi: String,
    val timestamp: String,
    val referencedPlaceId: String? = null
)

data class SavedConversation(
    val id: String,
    val titleEn: String,
    val titleVi: String,
    val dateLabel: String,
    val locationTag: String,
    val messagesCount: Int,
    val previewEn: String,
    val previewVi: String
)

data class PrototypeFlowSpec(
    val flowNumber: Int,
    val titleEn: String,
    val titleVi: String,
    val steps: List<JourneyScreenId>,
    val descriptionEn: String,
    val descriptionVi: String
)

object JourneySampleRepository {

    val places = listOf(
        PlaceItem(
            id = "place_hoian",
            nameEn = "Chùa Cầu — Japanese Covered Bridge",
            nameVi = "Chùa Cầu Hội An",
            regionEn = "Hội An Ancient Town, Quảng Nam",
            regionVi = "Phố cổ Hội An, Quảng Nam",
            categoryEn = "16th Century Heritage Monument",
            categoryVi = "Di sản Kiến trúc Thế kỷ 16",
            eraEn = "Restored 1593 • Merchant Guild Era",
            eraVi = "Khởi dựng 1593 • Thương cảng Quốc tế",
            coordinates = "15.8771° N, 108.3260° E",
            mapXPercent = 0.58f,
            mapYPercent = 0.52f,
            confidenceScore = 0.98f,
            rating = 4.9f,
            distanceKm = "180m away",
            audioDuration = "04:15",
            imageRes = R.drawable.img_hoian_lanterns,
            summaryEn = "An iconic covered timber bridge constructed in the 1590s by Japanese merchants to link their quarter with the Chinese neighborhood across the Thu Bồn tributary.",
            summaryVi = "Biểu tượng giao thoa văn hóa được các thương nhân Nhật Bản dựng lên vào cuối thế kỷ 16, nối liền khu phố Nhật và khu phố Hoa bên dòng sông Thu Bồn.",
            architecturalNotesEn = "Crafted with aged ironwood pillars, yin-yang terracotta roof tiles, and guarded by carved wooden dog (Inu) and monkey (Saru) statues marking the lunar years of construction.",
            architecturalNotesVi = "Kết cấu gỗ lim vững chãi trên trụ đá, mái lợp ngói âm dương thanh nhã, hai đầu cầu có tượng Khỉ và Chó bằng gỗ ghi dấu năm khởi công và hoàn thành.",
            culturalStoryEn = "Unlike ordinary bridges, its northern side houses a temple dedicated to Bắc Đế Trấn Vũ, protector of sailors and river trade against seasonal floods.",
            culturalStoryVi = "Không chỉ là nhịp cầu giao thương, phần gian giữa thờ Bắc Đế Trấn Vũ — vị thần trị thủy mang lại bình an cho thương thuyền vượt biển.",
            isSaved = true
        ),
        PlaceItem(
            id = "place_halong",
            nameEn = "Vịnh Hạ Long — Sung Sot Grotto & Karsts",
            nameVi = "Vịnh Hạ Long — Đảo Bồ Hòn",
            regionEn = "Quảng Ninh Province, Gulf of Tonkin",
            regionVi = "Tỉnh Quảng Ninh, Vịnh Bắc Bộ",
            categoryEn = "UNESCO Natural World Heritage",
            categoryVi = "Di sản Thiên nhiên Thế giới UNESCO",
            eraEn = "500 Million Years • Limestone Karst Evolution",
            eraVi = "500 Triệu Năm • Địa chất Karst Đá vôi",
            coordinates = "20.9101° N, 107.1839° E",
            mapXPercent = 0.72f,
            mapYPercent = 0.24f,
            confidenceScore = 0.99f,
            rating = 4.95f,
            distanceKm = "1.2km away",
            audioDuration = "05:40",
            imageRes = R.drawable.img_hero_halong_bay,
            summaryEn = "Over 1,600 monolithic limestone islands and islets rising dramatically from emerald waters, shaped by tropical karst erosion over half a billion years.",
            summaryVi = "Quần thể hơn 1.600 đảo đá vôi kỳ vĩ vươn lên từ làn nước ngọc bích, nơi lưu giữ dấu tích văn hóa biển cổ đại Cái Bèo và Hạ Long.",
            architecturalNotesEn = "Tower karst fengcong and fenglin formations featuring hidden marine notches, phreatic cave chambers, and endemic cliff-dwelling cycads.",
            architecturalNotesVi = "Địa hình karst ngập nước đặc trưng với hệ thống hang động thạch nhũ nguyên sơ, tùng áng nước mặn và thảm thực vật đặc hữu trên vách đá.",
            culturalStoryEn = "Local legend speaks of the Mother Dragon (Hạ Long) descending from heaven with jewel-spewing offspring to form an impenetrable jade fortress against maritime invaders.",
            culturalStoryVi = "Truyền thuyết kể rằng đàn Rồng đáp xuống nhả châu ngọc tạo thành bức tường thành vững chắc bảo vệ bờ cõi cư dân vùng biển Đông Bắc.",
            isSaved = true
        ),
        PlaceItem(
            id = "place_hanoi_temple",
            nameEn = "Văn Miếu — Temple of Literature & Imperial Academy",
            nameVi = "Văn Miếu — Quốc Tử Giám",
            regionEn = "Đống Đa District, Hà Nội",
            regionVi = "Quận Đống Đa, Thủ đô Hà Nội",
            categoryEn = "Imperial Confucian Sanctuary (1070 AD)",
            categoryVi = "Trường Đại học Đầu tiên của Việt Nam (1070)",
            eraEn = "Lý Dynasty • Emperor Lý Thánh Tông",
            eraVi = "Triều Lý • Vua Lý Thánh Tông",
            coordinates = "21.0287° N, 105.8356° E",
            mapXPercent = 0.44f,
            mapYPercent = 0.28f,
            confidenceScore = 0.97f,
            rating = 4.88f,
            distanceKm = "420m away",
            audioDuration = "06:10",
            imageRes = R.drawable.img_hanoi_temple,
            summaryEn = "Vietnam's first national university, founded in 1070 and expanded in 1076, comprising five walled courtyards symbolizing the classical elements and scholarly enlightenment.",
            summaryVi = "Quần thể kiến trúc biểu trưng cho truyền thống hiếu học ngàn năm, gồm năm lớp không gian tĩnh mặc dẫn lối từ Văn Miếu Môn đến Khuê Văn Các.",
            architecturalNotesEn = "Features the iconic Khuê Văn Các (Pavilion of the Constellation of Literature) and 82 Doctor Stelae resting on stone tortoises, recognized by UNESCO Memory of the World.",
            architecturalNotesVi = "Nổi bật với Khuê Văn Các 8 mái cân xứng và 82 bia Tiến sĩ đặt trên lưng rùa đá — Di sản Tư liệu Thế giới được UNESCO vinh danh.",
            culturalStoryEn = "Each stone stele records the lineage and royal examination results of laureates across three centuries of the Lê and Mạc dynasties.",
            culturalStoryVi = "Trên mỗi tấm bia khắc câu铭 bất hủ: 'Hiền tài là nguyên khí của quốc gia, nguyên khí thịnh thì thế nước mạnh mà vươn cao.'",
            isSaved = false
        ),
        PlaceItem(
            id = "place_sapa",
            nameEn = "Mù Cang Chải & Hoàng Liên Terraces",
            nameVi = "Ruộng bậc thang Mù Cang Chải & Sa Pa",
            regionEn = "Northwest Highlands, Lào Cai – Yên Bái",
            regionVi = "Vùng cao Tây Bắc, Lào Cai – Yên Bái",
            categoryEn = "Living Agricultural Heritage Landscape",
            categoryVi = "Di tích Quốc gia Đặc biệt Danh thắng Tây Bắc",
            eraEn = "300+ Years • H'Mông & Dao Engineering",
            eraVi = "Hơn 300 Năm • Nghệ thuật Canh tác Bản địa",
            coordinates = "21.8482° N, 104.0881° E",
            mapXPercent = 0.26f,
            mapYPercent = 0.16f,
            confidenceScore = 0.96f,
            rating = 4.92f,
            distanceKm = "2.4km away",
            audioDuration = "04:50",
            imageRes = R.drawable.img_sapa_terraces,
            summaryEn = "Monumental sculpted mountain slopes irrigated by gravity-fed bamboo aqueducts from high-altitude cloud forests.",
            summaryVi = "Tuyệt tác điêu khắc đất trời trên sườn núi Hoàng Liên Sơn, kết tinh trí tuệ dẫn thủy nhập điền từ rừng già của đồng bào H'Mông.",
            architecturalNotesEn = "Hand-carved contour dykes maintain exact water levels across 45-degree inclines without modern concrete reinforcement.",
            architecturalNotesVi = "Những đường vân uốn lượn giữ nước mùa đổ ải tháng 5 và hóa thành biển vàng óng ả khi mùa lúa chín tháng 9 về.",
            culturalStoryEn = "Every harvest season is celebrated with the Gầu Tào festival and traditional芦笙 (Khèn) melodies echoing across the misty valleys.",
            culturalStoryVi = "Mỗi bậc ruộng là một chương ký ức gia tộc truyền qua nhiều thế hệ, gắn liền với tiếng khèn Mông và lễ mừng cơm mới.",
            isSaved = true
        )
    )

    val trips = listOf(
        TripJournal(
            id = "trip_heritage_north_central",
            titleEn = "Mists of Tonkin & Lanterns of Faifo",
            titleVi = "Sương mờ Bắc Bộ & Đêm đèn lồng Hội An",
            dateRange = "Nov 04 – Nov 13, 2026",
            locationEn = "Hà Nội • Hạ Long • Hội An",
            locationVi = "Hà Nội • Hạ Long • Hội An",
            coverRes = R.drawable.img_hero_halong_bay,
            daysCount = 10,
            momentsCount = 18,
            discoveriesCount = 12,
            storyExcerptEn = "Ten days tracing Vietnam's imperial scholars, emerald karst cathedrals, and silk-lantern river ports through the lens of Journey AI.",
            storyExcerptVi = "Mười ngày lắng nghe nhịp thở di sản từ mái ngói Văn Miếu rêu phong, vịnh đá vôi bảng lảng sương chiều đến phố cảng Faifo lung linh ánh nến.",
            moments = listOf(
                DailyMoment(
                    id = "m_01",
                    dayNumber = 1,
                    timeLabel = "07:15 AM",
                    titleEn = "Morning Incense at Khuê Văn Các",
                    titleVi = "Nắng sớm bên gác Khuê Văn",
                    locationTag = "Văn Miếu, Hà Nội",
                    coordinates = "21.0287° N, 105.8356° E",
                    noteEn = "Scanned the 1484 Doctor Stele with Journey AI Lens. The translation revealed the exact royal examination poem inscribed during Emperor Lê Thánh Tông's reign.",
                    noteVi = "Dùng ống kính Journey AI quét văn bia năm Hồng Đức 1484. Từng dòng chữ Hán cổ hiện lên cùng lời dịch trang nhã và âm thanh thuyết minh trầm ấm.",
                    photoRes = R.drawable.img_hanoi_temple,
                    weatherTag = "21°C • Golden Mist"
                ),
                DailyMoment(
                    id = "m_02",
                    dayNumber = 4,
                    timeLabel = "05:30 PM",
                    titleEn = "Drifting Past Limestone Monoliths",
                    titleVi = "Hoàng hôn buông trên vịnh Bái Tử Long",
                    locationTag = "Vịnh Hạ Long, Quảng Ninh",
                    coordinates = "20.9101° N, 107.1839° E",
                    noteEn = "The wooden junk anchored in a silent lagoon. Voice Guide narrated the geological transition from ancient coral reefs to towering karsts.",
                    noteVi = "Con thuyền gỗ thả neo giữa vùng vịnh tĩnh lặng. Trợ lý giọng nói kể câu chuyện kiến tạo địa chất hàng trăm triệu năm giữa tiếng sóng vỗ mạn thuyền.",
                    photoRes = R.drawable.img_hero_halong_bay,
                    weatherTag = "24°C • Amber Dusk"
                ),
                DailyMoment(
                    id = "m_03",
                    dayNumber = 8,
                    timeLabel = "08:10 PM",
                    titleEn = "Lantern Reflections on the Thu Bồn",
                    titleVi = "Hoa đăng trôi bên Chùa Cầu cổ kính",
                    locationTag = "Minh An, Hội An",
                    coordinates = "15.8771° N, 108.3260° E",
                    noteEn = "Captured the timber joinery of the Japanese Bridge in low light. AI identified the Saru (Monkey) guardian statue and saved it to my Timber Architecture collection.",
                    noteVi = "Ghi lại kết cấu vì kèo gỗ Chùa Cầu trong ánh đèn lồng ấm áp. AI nhận diện ngay tượng Linh Hầu trấn giữ đầu cầu phía Tây.",
                    photoRes = R.drawable.img_hoian_lanterns,
                    weatherTag = "25°C • River Breeze"
                )
            )
        ),
        TripJournal(
            id = "trip_highland_cloud",
            titleEn = "Cloud Harvest in the Northwest",
            titleVi = "Mùa vàng trên Đỉnh sương Tây Bắc",
            dateRange = "Sep 18 – Sep 23, 2026",
            locationEn = "Sa Pa • Mù Cang Chải",
            locationVi = "Sa Pa • Mù Cang Chải",
            coverRes = R.drawable.img_sapa_terraces,
            daysCount = 6,
            momentsCount = 9,
            discoveriesCount = 7,
            storyExcerptEn = "Walking along hand-carved mountain contours at 1,800 meters where golden rice terraces meet early autumn cloud seas.",
            storyExcerptVi = "Dạo bước giữa những nấc thang vàng óng ở độ cao 1.800m, nơi biển mây ôm ấp bản làng của người H'Mông và người Dao Đỏ.",
            moments = listOf(
                DailyMoment(
                    id = "m_04",
                    dayNumber = 2,
                    timeLabel = "06:05 AM",
                    titleEn = "First Light Over La Pán Tẩn",
                    titleVi = "Bình minh trên đồi Mâm Xôi La Pán Tẩn",
                    locationTag = "Mù Cang Chải, Yên Bái",
                    coordinates = "21.8482° N, 104.0881° E",
                    noteEn = "Sunrise shafts pierced the valley mist. Used AI Voice Guide offline pack to learn about traditional bamboo water conduits.",
                    noteVi = "Tia nắng đầu tiên xuyên qua biển sương. Gói thuyết minh ngoại tuyến giúp khám phá kỹ thuật dẫn nước tre nứa kỳ công của bản làng.",
                    photoRes = R.drawable.img_sapa_terraces,
                    weatherTag = "16°C • Alpine Dawn"
                )
            )
        )
    )

    val collections = listOf(
        DiscoveryCollection(
            id = "coll_imperial",
            titleEn = "Imperial Timber & Pagoda Joinery",
            titleVi = "Kiến trúc Gỗ Cổ & Chùa Tháp Việt Nam",
            subtitleEn = "14 Architectural Scans • Dynastic Craftsmanship",
            subtitleVi = "14 Điểm quét AI • Tinh hoa Chạm khắc Cổ truyền",
            coverRes = R.drawable.img_hanoi_temple,
            placeIds = listOf("place_hoian", "place_hanoi_temple"),
            badgeTagEn = "ARCHITECTURAL SERIES",
            badgeTagVi = "CHUYÊN ĐỀ KIẾN TRÚC"
        ),
        DiscoveryCollection(
            id = "coll_unesco_seascapes",
            titleEn = "Sacred Karsts & Highland Contours",
            titleVi = "Kỳ quan Địa chất & Danh thắng Tây Bắc",
            subtitleEn = "9 Landmarks • Geological & Living Landscapes",
            subtitleVi = "9 Địa danh • Di sản Thiên nhiên & Văn hóa",
            coverRes = R.drawable.img_hero_halong_bay,
            placeIds = listOf("place_halong", "place_sapa"),
            badgeTagEn = "UNESCO CURATION",
            badgeTagVi = "DI SẢN THẾ GIỚI"
        ),
        DiscoveryCollection(
            id = "coll_twilight_ports",
            titleEn = "Ancient Maritime Silk Ports",
            titleVi = "Thương cảng Cổ trên Con đường Tơ lụa",
            subtitleEn = "8 Discoveries • Lanterns, Guilds & River Trade",
            subtitleVi = "8 Khám phá • Hội quán, Đèn lồng & Bến thuyền",
            coverRes = R.drawable.img_hoian_lanterns,
            placeIds = listOf("place_hoian", "place_halong"),
            badgeTagEn = "CULTURAL HERITAGE",
            badgeTagVi = "VĂN HÓA THƯƠNG CẢNG"
        )
    )

    val initialChatMessages = listOf(
        ChatMessageItem(
            id = "chat_1",
            isUser = false,
            textEn = "Good evening in Hội An. Based on your visual scan of Chùa Cầu (Japanese Covered Bridge), would you like to explore the symbolism of the wooden Monkey and Dog guardians or find the quietest lantern viewpoint nearby?",
            textVi = "Chào buổi tối tại Hội An. Từ kết quả nhận diện Chùa Cầu vừa rồi, bạn muốn tìm hiểu ý nghĩa đôi tượng Khỉ - Chó bằng gỗ hay khám phá góc ngắm đèn lồng yên tĩnh nhất bên sông Hoài?",
            timestamp = "20:14",
            referencedPlaceId = "place_hoian"
        ),
        ChatMessageItem(
            id = "chat_2",
            isUser = true,
            textEn = "Tell me why the bridge has Monkey and Dog statues at each entrance.",
            textVi = "Hãy giải thích vì sao hai đầu cầu lại đặt tượng Khỉ và tượng Chó?",
            timestamp = "20:15"
        ),
        ChatMessageItem(
            id = "chat_3",
            isUser = false,
            textEn = "In Japanese imperial chronology, construction began in the Year of the Monkey (Thân - 1593) and concluded in the Year of the Dog (Tuất - 1595). Additionally, the central chamber worships Bắc Đế Trấn Vũ rather than Buddha, making it a rare fusion of Shinto guardian lore, Taoist water protection, and Vietnamese timber craftsmanship.",
            textVi = "Theo lịch pháp cổ Nhật Bản, công trình khởi công vào năm Thân (1593) và hoàn tất vào năm Tuất (1595). Đặc biệt, gian chính giữa không thờ Phật mà thờ Bắc Đế Trấn Vũ — vị thần bảo hộ trị thủy, tạo nên sự giao hòa độc bản giữa tín ngưỡng Nhật - Hoa - Việt.",
            timestamp = "20:15",
            referencedPlaceId = "place_hoian"
        )
    )

    val savedConversations = listOf(
        SavedConversation(
            id = "conv_01",
            titleEn = "Chùa Cầu Guardian Chronology & Joinery",
            titleVi = "Niên đại Linh vật & Kết cấu gỗ Chùa Cầu",
            dateLabel = "Today • 20:15",
            locationTag = "Hội An, Quảng Nam",
            messagesCount = 6,
            previewEn = "Explored the 1593–1595 Monkey and Dog zodiac markers and Bắc Đế Trấn Vũ shrine.",
            previewVi = "Giải mã biểu tượng năm Thân – năm Tuất và tín ngưỡng thờ thần trị thủy Bắc Đế."
        ),
        SavedConversation(
            id = "conv_02",
            titleEn = "Reading the 1484 Doctor Stele at Văn Miếu",
            titleVi = "Dịch thuật Văn bia Tiến sĩ khoa Nhâm Tuất",
            dateLabel = "Nov 05 • 08:40",
            locationTag = "Đống Đa, Hà Nội",
            messagesCount = 9,
            previewEn = "Translated classical Sino-Vietnamese calligraphy by Thân Nhân Trung on scholarly virtue.",
            previewVi = "Phân tích bài ký của Thân Nhân Trung khắc trên bia đá đặt trên lưng rùa."
        ),
        SavedConversation(
            id = "conv_03",
            titleEn = "Karst Geology of Sung Sot Lagoon",
            titleVi = "Địa chất học Karst Vịnh Hạ Long",
            dateLabel = "Nov 08 • 17:20",
            locationTag = "Vịnh Hạ Long",
            messagesCount = 5,
            previewEn = "Voice tour transcript covering marine erosion notches and endemic cliff flora.",
            previewVi = "Bản ghi thuyết minh giọng nói về quá trình phong hóa đá vôi 500 triệu năm."
        )
    )

    val requiredFlows = listOf(
        PrototypeFlowSpec(
            flowNumber = 1,
            titleEn = "FLOW 1: Onboarding & Authentication",
            titleVi = "LUỒNG 1: Khởi động & Xác thực",
            steps = listOf(
                JourneyScreenId.A1_SPLASH,
                JourneyScreenId.A2_WELCOME,
                JourneyScreenId.A3_ONBOARDING,
                JourneyScreenId.A4_PERMISSIONS,
                JourneyScreenId.A5_LOGIN,
                JourneyScreenId.B1_HOME_DASHBOARD
            ),
            descriptionEn = "Splash → Welcome → Onboarding → Permissions → Login/Register → Home",
            descriptionVi = "Khởi động → Chào mừng → Giới thiệu → Cấp quyền → Đăng nhập → Trang chủ"
        ),
        PrototypeFlowSpec(
            flowNumber = 2,
            titleEn = "FLOW 2: AI Visual Recognition",
            titleVi = "LUỒNG 2: Nhận diện Thị giác AI",
            steps = listOf(
                JourneyScreenId.B1_HOME_DASHBOARD,
                JourneyScreenId.D1_AI_CAMERA,
                JourneyScreenId.D2_IMAGE_PREVIEW,
                JourneyScreenId.D3_AI_PROCESSING,
                JourneyScreenId.D4_RECOGNITION_RESULT,
                JourneyScreenId.D5_DISCOVERY_DETAILS
            ),
            descriptionEn = "Home → Discover Camera → Image Preview → AI Processing → Recognition Result → Discovery Detail",
            descriptionVi = "Trang chủ → Ống kính AI → Xem ảnh → Phân tích AI → Kết quả → Chiều sâu Lịch sử"
        ),
        PrototypeFlowSpec(
            flowNumber = 3,
            titleEn = "FLOW 3: AI Chat & Voice Guide",
            titleVi = "LUỒNG 3: Trò chuyện AI & Thuyết minh Giọng nói",
            steps = listOf(
                JourneyScreenId.D4_RECOGNITION_RESULT,
                JourneyScreenId.E1_AI_CHAT,
                JourneyScreenId.E2_VOICE_ASSISTANT,
                JourneyScreenId.E3_CONVERSATION_HISTORY
            ),
            descriptionEn = "Recognition Result → AI Chat → Voice Assistant → Save Conversation",
            descriptionVi = "Kết quả Nhận diện → Trò chuyện AI → Thuyết minh Giọng nói → Lưu Hội thoại"
        ),
        PrototypeFlowSpec(
            flowNumber = 4,
            titleEn = "FLOW 4: Interactive Map Exploration",
            titleVi = "LUỒNG 4: Bản đồ Khám phá Tương tác",
            steps = listOf(
                JourneyScreenId.C1_EXPLORE_MAP,
                JourneyScreenId.C3_PLACE_DETAILS,
                JourneyScreenId.E1_AI_CHAT,
                JourneyScreenId.C4_SAVED_PLACES
            ),
            descriptionEn = "Explore Map → Select Place → Place Detail → Ask AI → Save Place",
            descriptionVi = "Bản đồ → Chọn Địa điểm → Chi tiết → Hỏi AI → Lưu Địa điểm"
        ),
        PrototypeFlowSpec(
            flowNumber = 5,
            titleEn = "FLOW 5: Travel Journal & AI Story",
            titleVi = "LUỒNG 5: Nhật ký & Câu chuyện AI",
            steps = listOf(
                JourneyScreenId.F1_JOURNAL_HOME,
                JourneyScreenId.F2_CREATE_TRIP,
                JourneyScreenId.F3_TRIP_DETAIL,
                JourneyScreenId.F5_CREATE_DAILY_MOMENT,
                JourneyScreenId.F4_TRIP_TIMELINE,
                JourneyScreenId.H1_AI_STORY_PREVIEW,
                JourneyScreenId.H2_VIDEO_CREATOR,
                JourneyScreenId.H3_VIDEO_PREVIEW_EXPORT
            ),
            descriptionEn = "Journal → Create Trip → Trip Detail → Add Media → Timeline → AI Story → Video Export",
            descriptionVi = "Nhật ký → Tạo Chuyến đi → Chi tiết → Thêm Khoảnh khắc → Dòng thời gian → Câu chuyện AI"
        ),
        PrototypeFlowSpec(
            flowNumber = 6,
            titleEn = "FLOW 6: Curated Discovery Collections",
            titleVi = "LUỒNG 6: Bộ sưu tập Di sản",
            steps = listOf(
                JourneyScreenId.D4_RECOGNITION_RESULT,
                JourneyScreenId.G1_DISCOVERY_COLLECTION,
                JourneyScreenId.G2_COLLECTION_DETAILS,
                JourneyScreenId.D5_DISCOVERY_DETAILS
            ),
            descriptionEn = "Discover → Save Collection → Collection Detail → Discovery Detail",
            descriptionVi = "Khám phá → Lưu Bộ sưu tập → Chi tiết Bộ sưu tập → Chi tiết Khám phá"
        ),
        PrototypeFlowSpec(
            flowNumber = 7,
            titleEn = "FLOW 7: Profile & Journey+ Membership",
            titleVi = "LUỒNG 7: Hồ sơ & Gói Hội viên Journey+",
            steps = listOf(
                JourneyScreenId.I1_PROFILE,
                JourneyScreenId.I3_SUBSCRIPTION,
                JourneyScreenId.I2_SETTINGS,
                JourneyScreenId.I4_PRIVACY_AND_DATA
            ),
            descriptionEn = "Profile → Subscription → Plan Comparison → Settings & Privacy",
            descriptionVi = "Hồ sơ → Gói Hội viên → So sánh Gói → Cài đặt & Quyền riêng tư"
        )
    )

    val friendStories = listOf(
        FriendStoryItem(
            id = "story_lan_anh",
            authorName = "Lan Anh",
            avatarInitials = "LA",
            placeId = "place_hoian",
            placeName = "Phố Cổ Hội An",
            timeAgo = "18 phút trước",
            caption = "Đêm phố cổ lung linh đèn lồng lụa, thả hoa đăng trên sông Hoài 🏮✨",
            photoRes = R.drawable.img_hoian_lanterns
        ),
        FriendStoryItem(
            id = "story_hoang_nam",
            authorName = "Hoàng Nam",
            avatarInitials = "HN",
            placeId = "place_halong",
            placeName = "Vịnh Hạ Long",
            timeAgo = "1 giờ trước",
            caption = "Bình minh trên boong tàu giữa kỳ quan thiên nhiên thế giới ⛵🌊",
            photoRes = R.drawable.img_hero_halong_bay
        ),
        FriendStoryItem(
            id = "story_thao_tien",
            authorName = "Thảo Tiên",
            avatarInitials = "TT",
            placeId = "place_sapa",
            placeName = "Ruộng bậc thang Sa Pa",
            timeAgo = "3 giờ trước",
            caption = "Săn mây buổi sớm trên thung lũng Mường Hoa - Sa Pa 🌾☁️",
            photoRes = R.drawable.img_sapa_terraces
        ),
        FriendStoryItem(
            id = "story_tuan_kiet",
            authorName = "Tuấn Kiệt",
            avatarInitials = "TK",
            placeId = "place_hanoi_temple",
            placeName = "Văn Miếu – Quốc Tử Giám",
            timeAgo = "5 giờ trước",
            caption = "Chiều thu Hà Nội ghé thăm Khuê Văn Các cổ kính ⛩️🍂",
            photoRes = R.drawable.img_hanoi_temple
        )
    )

    val initialSocialPosts = listOf(
        SocialPostItem(
            id = "post_1",
            isMine = true,
            authorName = "Minh Nguyễn",
            authorHandle = "@minh.journey",
            avatarInitials = "MN",
            placeId = "place_halong",
            placeName = "Vịnh Hạ Long, Quảng Ninh",
            timeAgo = "Vừa xong",
            dateLabel = "14/11/2026",
            caption = "Chèo kayak xuyên hang Luồn, nước trong xanh như ngọc bích! ⛵✨",
            photoRes = R.drawable.img_hero_halong_bay,
            photoUri = null,
            likesCount = 28,
            isLiked = true,
            comments = listOf(
                SocialCommentItem("Lan Anh", "Ảnh nét quá Minh ơi! Góc chụp đỉnh thật 😍"),
                SocialCommentItem("Hoàng Nam", "Cuối tuần này mình cũng ra Hạ Long nè!")
            ),
            timestampWatermark = "14/11/2026 · 08:30",
            coordinatesWatermark = "20.9101° N, 107.1839° E"
        ),
        SocialPostItem(
            id = "post_2",
            isMine = false,
            authorName = "Lan Anh",
            authorHandle = "@lananh.wander",
            avatarInitials = "LA",
            placeId = "place_hoian",
            placeName = "Phố Cổ Hội An, Quảng Nam",
            timeAgo = "42 phút trước",
            dateLabel = "14/11/2026",
            caption = "Một góc phố đèn lồng rực rỡ bên bờ sông Hoài đêm rằm 🏮💛",
            photoRes = R.drawable.img_hoian_lanterns,
            photoUri = null,
            likesCount = 64,
            isLiked = false,
            comments = listOf(
                SocialCommentItem("Thảo Tiên", "Nhớ món Cao lầu Hội An quá đi!")
            ),
            timestampWatermark = "14/11/2026 · 19:45",
            coordinatesWatermark = "15.8771° N, 108.3260° E"
        ),
        SocialPostItem(
            id = "post_3",
            isMine = true,
            authorName = "Minh Nguyễn",
            authorHandle = "@minh.journey",
            avatarInitials = "MN",
            placeId = "place_sapa",
            placeName = "Bản Cát Cát — Sa Pa, Lào Cai",
            timeAgo = "Hôm qua",
            dateLabel = "13/11/2026",
            caption = "Mùa vàng trên rẻo cao Tây Bắc, không khí se lạnh 14°C cực thích 🌾🏔️",
            photoRes = R.drawable.img_sapa_terraces,
            photoUri = null,
            likesCount = 45,
            isLiked = true,
            comments = emptyList(),
            timestampWatermark = "13/11/2026 · 06:50",
            coordinatesWatermark = "22.3264° N, 103.8315° E"
        ),
        SocialPostItem(
            id = "post_4",
            isMine = false,
            authorName = "Hoàng Nam",
            authorHandle = "@nam.explorer",
            avatarInitials = "HN",
            placeId = "place_hanoi_temple",
            placeName = "Văn Miếu – Quốc Tử Giám, Hà Nội",
            timeAgo = "2 ngày trước",
            dateLabel = "12/11/2026",
            caption = "Nét kiến trúc cổ kính gần 1.000 năm tuổi giữa lòng thủ đô ⛩️📖",
            photoRes = R.drawable.img_hanoi_temple,
            photoUri = null,
            likesCount = 51,
            isLiked = false,
            comments = emptyList(),
            timestampWatermark = "12/11/2026 · 15:20",
            coordinatesWatermark = "21.0287° N, 105.8356° E"
        )
    )
}

data class FriendStoryItem(
    val id: String,
    val authorName: String,
    val avatarInitials: String,
    val placeId: String,
    val placeName: String,
    val timeAgo: String,
    val caption: String,
    @DrawableRes val photoRes: Int
)

data class SocialCommentItem(
    val authorName: String,
    val text: String
)

data class SocialPostItem(
    val id: String,
    val isMine: Boolean,
    val authorName: String,
    val authorHandle: String,
    val avatarInitials: String,
    val placeId: String = "",
    val placeName: String = "",
    val timeAgo: String,
    val dateLabel: String,
    val caption: String,
    @DrawableRes val photoRes: Int,
    val photoUri: String? = null,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val comments: List<SocialCommentItem> = emptyList(),
    val privacyLabel: String = "🌎 Tất cả bạn bè",
    val isVideo: Boolean = false,
    val videoDurationSec: Int = 0,
    val timestampWatermark: String = "",
    val coordinatesWatermark: String = "",
    val temperatureTag: String = "",
    val stickerTag: String = "",
    val spotifyTrack: String = ""
)



