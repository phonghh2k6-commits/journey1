package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.JourneyScreenId
import com.example.ui.components.JourneyBadgePill
import com.example.ui.components.JourneyButton
import com.example.ui.components.JourneyButtonVariant
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
// A1: SPLASH SCREEN (No Bottom Bar per Section 5, FLOW 1 Entry)
// ============================================================================
@Composable
fun SplashScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val lang = uiState.language

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JourneyPalette.Midnight)
            .testTag("screen_a1_splash")
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_hero_halong_bay),
            contentDescription = "Journey AI Splash",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x99111923),
                            Color(0xCC111923),
                            Color(0xF5111923)
                        )
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(JourneySpacing.xxl)
        ) {
            JourneyBadgePill(
                text = "A1-SPLASH • VIETNAMESE & GLOBAL EDITION",
                containerColor = Color(0xAA1C2934),
                contentColor = JourneyPalette.SandGold
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .border(2.dp, JourneyPalette.SandGold, CircleShape)
                        .background(JourneyPalette.Midnight)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_launcher_compass),
                        contentDescription = "Journey AI Emblem",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(72.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "JOURNEY AI",
                    style = MaterialTheme.typography.displayLarge,
                    color = JourneyPalette.Ivory
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tr(lang, "Explore the World. Discover the Stories.", "Khám phá Thế giới. Lắng nghe Câu chuyện."),
                    style = MaterialTheme.typography.titleMedium,
                    color = JourneyPalette.SandGold,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                JourneyButton(
                    text = tr(lang, "Begin Journey (A2 Welcome)", "Bắt đầu Hành trình (A2 Chào mừng)"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.A2_WELCOME) },
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "splash_continue_btn"
                )
                Spacer(modifier = Modifier.height(8.dp))
                JourneyButton(
                    text = tr(lang, "Skip to Home Dashboard (B1)", "Vào thẳng Trang chủ (B1)"),
                    variant = JourneyButtonVariant.GHOST,
                    onClick = { onNavigate(JourneyScreenId.B1_HOME_DASHBOARD) }
                )
            }
        }
    }
}

// ============================================================================
// A2: WELCOME SCREEN (Language Selection VI/EN & Editorial Invitation)
// ============================================================================
@Composable
fun WelcomeScreen(
    uiState: JourneyUiState,
    onToggleLanguage: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.xl)
            .testTag("screen_a2_welcome"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JourneyBadgePill(text = "A2-WELCOME • BILINGUAL EDITION")
            JourneyButton(
                text = if (lang == com.example.model.AppLanguage.EN) "Tiếng Việt (VI)" else "English (EN)",
                leadingIcon = Icons.Default.Language,
                variant = JourneyButtonVariant.SECONDARY,
                onClick = onToggleLanguage
            )
        }

        Column(modifier = Modifier.padding(vertical = JourneySpacing.xl)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .clip(RoundedCornerShape(JourneyRadius.xl))
                    .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.xl))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hoian_lanterns),
                    contentDescription = "Hoi An Lanterns",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(JourneySpacing.xl))
            Text(
                text = tr(lang, "Every Monument Holds a Living Memory.", "Mỗi Di Sản Đều Ẩn Chứa Một Ký Ức Sống Động."),
                style = MaterialTheme.typography.displayMedium,
                color = colors.primaryText
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = tr(
                    lang,
                    "Point your lens at centuries-old pagodas, karst cathedrals, and imperial stelae. Let Journey AI narrate their stories and compose your personal travel anthology.",
                    "Hướng ống kính về những ngôi chùa cổ, vịnh đá vôi kỳ vĩ hay bia đá ngàn năm để lắng nghe câu chuyện lịch sử và lưu giữ nhật ký điện ảnh."
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = colors.secondaryText
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            JourneyButton(
                text = tr(lang, "Explore Capabilities (A3 Onboarding)", "Khám phá Tính năng (A3)"),
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.A3_ONBOARDING) },
                modifier = Modifier.fillMaxWidth(),
                testTag = "welcome_to_onboarding_btn"
            )
            JourneyButton(
                text = tr(lang, "Sign In to Existing Account (A5)", "Đăng nhập Tài khoản (A5)"),
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.A5_LOGIN) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ============================================================================
// A3: ONBOARDING SLIDES (3 Interactive Editorial Capabilities Slides)
// ============================================================================
@Composable
fun OnboardingSlidesScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var slideIndex by remember { mutableIntStateOf(0) }

    val slides = listOf(
        Triple(
            R.drawable.img_hanoi_temple,
            tr(lang, "01 / AI Visual Monument Recognition", "01 / Nhận Diện Kiến Trúc & Cổ Vật Bằng AI"),
            tr(
                lang,
                "Instant architectural and historical decoding for temples, stelae, and UNESCO landscapes across Vietnam and beyond.",
                "Giải mã tức thì niên đại, điển tích và kiến trúc chùa tháp, văn bia cùng các di sản UNESCO."
            )
        ),
        Triple(
            R.drawable.img_hero_halong_bay,
            tr(lang, "02 / Binaural AI Voice Travel Guide", "02 / Thuyết Minh Giọng Nói AI Đa Ngữ"),
            tr(
                lang,
                "Walk hands-free while an editorial scholar voice narrates local folklore, geology, and dynastic history.",
                "Tự do dạo bước và lắng nghe giọng đọc truyền cảm kể lại truyền thuyết địa phương và lịch sử các triều đại."
            )
        ),
        Triple(
            R.drawable.img_sapa_terraces,
            tr(lang, "03 / Travel Journal & 4K Story Reels", "03 / Nhật Ký Hành Trình & Dựng Phim 4K"),
            tr(
                lang,
                "Transform daily moments and GPS footprints into illustrated essays and 24fps cinematic travel films.",
                "Biến những khoảnh khắc mỗi ngày thành trang viết nghệ thuật và thước phim hành trình chuẩn điện ảnh."
            )
        )
    )

    val current = slides[slideIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.lg)
            .testTag("screen_a3_onboarding"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A3_ONBOARDING,
            language = lang,
            onBackClick = onBack
        )

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(JourneyRadius.xl))
            ) {
                Image(
                    painter = painterResource(id = current.first),
                    contentDescription = current.second,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Slide indicators
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                slides.indices.forEach { idx ->
                    Box(
                        modifier = Modifier
                            .width(if (idx == slideIndex) 32.dp else 10.dp)
                            .height(6.dp)
                            .background(
                                if (idx == slideIndex) colors.accent else colors.border,
                                RoundedCornerShape(4.dp)
                            )
                            .clickable { slideIndex = idx }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))
            Text(
                text = current.second,
                style = MaterialTheme.typography.headlineLarge,
                color = colors.primaryText
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = current.third,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.secondaryText
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (slideIndex < slides.lastIndex) {
                JourneyButton(
                    text = tr(lang, "Next Slide", "Tiếp theo"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { slideIndex++ },
                    modifier = Modifier.weight(1f),
                    testTag = "onboarding_next_slide_btn"
                )
            } else {
                JourneyButton(
                    text = tr(lang, "Configure Permissions (A4)", "Thiết lập Quyền truy cập (A4)"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.A4_PERMISSIONS) },
                    modifier = Modifier.weight(1f),
                    testTag = "onboarding_to_permissions_btn"
                )
            }
            JourneyButton(
                text = tr(lang, "Login (A5)", "Đăng nhập (A5)"),
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.A5_LOGIN) }
            )
        }
    }
}

// ============================================================================
// A4: PERMISSION INTRODUCTION (Camera, Location, Microphone Toggles)
// ============================================================================
@Composable
fun PermissionIntroductionScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onTogglePermission: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
            .testTag("screen_a4_permissions")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A4_PERMISSIONS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "SENSOR & PRIVACY TRANSPARENCY", "MINH BẠCH QUYỀN TRUY CẬP THIẾT BỊ"),
                title = tr(lang, "Enable Spatial & Visual Discovery", "Cấp Quyền Cho Ống Kính & Bản Đồ")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            val perms = listOf(
                Triple(
                    "CAMERA",
                    tr(lang, "Camera Access (AI Lens)", "Quyền Máy ảnh (Ống kính AI)"),
                    uiState.cameraPermissionGranted to Icons.Default.CameraAlt
                ),
                Triple(
                    "LOCATION",
                    tr(lang, "Precise Location (Explore Map & EXIF)", "Vị trí Chính xác (Bản đồ Di sản)"),
                    uiState.locationPermissionGranted to Icons.Default.LocationOn
                ),
                Triple(
                    "MIC",
                    tr(lang, "Microphone (Hands-Free Voice Guide)", "Micro (Hỏi đáp Giọng nói)"),
                    uiState.micPermissionGranted to Icons.Default.Mic
                )
            )

            perms.forEach { (key, title, statePair) ->
                val (granted, icon) = statePair
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, if (granted) colors.accent else colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(JourneySpacing.lg),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = colors.accent
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                        }
                        Switch(
                            checked = granted,
                            onCheckedChange = { onTogglePermission(key) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.onAccent,
                                checkedTrackColor = colors.accent
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))
            JourneyButton(
                text = tr(lang, "Continue to Authentication (A5)", "Tiếp tục Đăng nhập (A5)"),
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.A5_LOGIN) },
                modifier = Modifier.fillMaxWidth(),
                testTag = "permissions_to_login_btn"
            )
        }
    }
}

// ============================================================================
// A5: LOGIN (Supabase Auth Handoff Ready)
// ============================================================================
@Composable
fun LoginScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var email by remember { mutableStateOf(uiState.userEmail) }
    var password by remember { mutableStateOf("••••••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
            .testTag("screen_a5_login")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A5_LOGIN,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = "SUPABASE AUTH • JWT SESSION",
                title = tr(lang, "Welcome Back to Journey AI", "Chào Mừng Trở Lại Journey AI")
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
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "explorer@journey.ai",
                        leadingIcon = Icons.Default.Email,
                        testTag = "login_email_input"
                    )
                    JourneyTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = tr(lang, "Password", "Mật khẩu"),
                        placeholder = "••••••••",
                        leadingIcon = Icons.Default.Lock
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        JourneyButton(
                            text = tr(lang, "Forgot Password? (A7)", "Quên mật khẩu? (A7)"),
                            variant = JourneyButtonVariant.GHOST,
                            onClick = { onNavigate(JourneyScreenId.A7_FORGOT_PASSWORD) }
                        )
                        JourneyButton(
                            text = tr(lang, "Create Account (A6)", "Đăng ký mới (A6)"),
                            variant = JourneyButtonVariant.GHOST,
                            onClick = { onNavigate(JourneyScreenId.A6_REGISTER) }
                        )
                    }

                    JourneyButton(
                        text = tr(lang, "Sign In & Enter Home (B1)", "Đăng Nhập & Vào Trang Chủ (B1)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.B1_HOME_DASHBOARD) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "login_submit_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// A6: REGISTER
// ============================================================================
@Composable
fun RegisterScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var fullName by remember { mutableStateOf(uiState.userName) }
    var email by remember { mutableStateOf(uiState.userEmail) }
    var password by remember { mutableStateOf("••••••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A6_REGISTER,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "CREATE PERSONAL ARCHIVE", "TẠO HỒ SƠ KHÁM PHÁ"),
                title = tr(lang, "Join the Journey AI Atelier", "Đăng Ký Tài Khoản Journey AI")
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
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = tr(lang, "Explorer Name", "Họ và Tên"),
                        placeholder = "Minh Nguyễn",
                        leadingIcon = Icons.Default.Person
                    )
                    JourneyTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "minh.nguyen@journey.ai",
                        leadingIcon = Icons.Default.Email
                    )
                    JourneyTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = tr(lang, "Password", "Mật khẩu"),
                        placeholder = "••••••••••••",
                        leadingIcon = Icons.Default.Lock
                    )

                    JourneyButton(
                        text = tr(lang, "Register & Verify Email (A8)", "Đăng ký & Xác thực Email (A8)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.A8_EMAIL_VERIFY) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "register_submit_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// A7: FORGOT PASSWORD
// ============================================================================
@Composable
fun ForgotPasswordScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var email by remember { mutableStateOf(uiState.userEmail) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A7_FORGOT_PASSWORD,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "CREDENTIAL RECOVERY", "KHÔI PHỤC MẬT KHẨU"),
                title = tr(lang, "Send Recovery Code", "Gửi Mã Khôi Phục Qua Email")
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
                        value = email,
                        onValueChange = { email = it },
                        label = "Registered Email",
                        placeholder = "minh.nguyen@journey.ai",
                        leadingIcon = Icons.Default.Email
                    )
                    JourneyButton(
                        text = tr(lang, "Send OTP & Verify (A8)", "Gửi Mã Xác Thực (A8)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.A8_EMAIL_VERIFY) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ============================================================================
// A8: EMAIL VERIFICATION
// ============================================================================
@Composable
fun EmailVerificationScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var otpCode by remember { mutableStateOf("8 4 2 9 1 6") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A8_EMAIL_VERIFY,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = "SUPABASE OTP VERIFICATION",
                title = tr(lang, "Verify Your Explorer Identity", "Xác Thực Mã Bảo Mật 6 Số")
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
                    Text(
                        text = tr(
                            lang,
                            "We sent a 6-digit verification code to ${uiState.userEmail}.",
                            "Mã xác thực 6 chữ số đã được gửi tới ${uiState.userEmail}."
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                    JourneyTextField(
                        value = otpCode,
                        onValueChange = { otpCode = it },
                        label = "6-Digit OTP",
                        placeholder = "8 4 2 9 1 6"
                    )
                    JourneyButton(
                        text = tr(lang, "Verify & Enter Home Dashboard (B1)", "Xác Thực & Vào Trang Chủ (B1)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.B1_HOME_DASHBOARD) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "verify_email_submit_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// I1: PERSONAL PROFILE EDITOR (HỒ SƠ: CHỈ THAY AVATAR, TÊN & THÔNG TIN CÁ NHÂN)
// ============================================================================
@Composable
fun ProfileScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit,
    onUpdateProfile: (String, String, String, String, String, String?) -> Unit = { _, _, _, _, _, _ -> }
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    var name by remember(uiState.userName) { mutableStateOf(uiState.userName) }
    var handle by remember(uiState.userHandle) { mutableStateOf(uiState.userHandle) }
    var bio by remember(uiState.userBio) { mutableStateOf(uiState.userBio) }
    var city by remember(uiState.userCity) { mutableStateOf(uiState.userCity) }
    var email by remember(uiState.userEmail) { mutableStateOf(uiState.userEmail) }
    var avatarUri by remember(uiState.userAvatarUri) { mutableStateOf(uiState.userAvatarUri) }

    val avatarPickerLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            avatarUri = uri.toString()
            onUpdateProfile(name, handle, bio, city, email, avatarUri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i1_profile")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md, vertical = 12.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                shadowElevation = com.example.ui.theme.JourneyElevation.card,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Rich Cover Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        JourneyPalette.Midnight,
                                        colors.sage,
                                        colors.accent
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // 1. Editable Avatar Circle
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier
                                .size(96.dp)
                                .clickable {
                                    avatarPickerLauncher.launch(
                                        androidx.activity.result.PickVisualMediaRequest(
                                            androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                                .testTag("profile_avatar_picker_btn")
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(colors.sage, CircleShape)
                                    .border(3.dp, colors.accent, CircleShape)
                            ) {
                                if (avatarUri != null) {
                                    coil.compose.AsyncImage(
                                        model = avatarUri,
                                        contentDescription = name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Text(
                                        text = uiState.userInitials,
                                        style = MaterialTheme.typography.headlineLarge,
                                        color = Color.White
                                    )
                                }
                            }
                            Surface(
                                shape = CircleShape,
                                color = colors.accent,
                                border = BorderStroke(2.dp, colors.surface),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = "Đổi ảnh đại diện",
                                        tint = colors.onAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = "$handle · 📍 $city",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.sage
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = bio,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }

                        // 3-Column Stats Bar
                        val myPostsCount = uiState.socialPosts.count { it.isMine }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(colors.elevatedSurface)
                                .border(1.dp, colors.border, RoundedCornerShape(16.dp))
                                .padding(vertical = 10.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$myPostsCount",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colors.primaryText
                                )
                                Text(
                                    text = "Khoảnh khắc",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.secondaryText
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$myPostsCount",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colors.primaryText
                                )
                                Text(
                                    text = "Ảnh Check-in",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.secondaryText
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${uiState.places.size}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colors.primaryText
                                )
                                Text(
                                    text = "Điểm đến VN",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.secondaryText
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            JourneyButton(
                                text = tr(lang, "📷 Change Avatar", "📷 Đổi ảnh đại diện"),
                                variant = JourneyButtonVariant.SECONDARY,
                                onClick = {
                                    avatarPickerLauncher.launch(
                                        androidx.activity.result.PickVisualMediaRequest(
                                            androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                            )
                            if (avatarUri != null) {
                                JourneyButton(
                                    text = tr(lang, "Use Initials", "Dùng chữ tắt"),
                                    variant = JourneyButtonVariant.GHOST,
                                    onClick = {
                                        avatarUri = null
                                        onUpdateProfile(name, handle, bio, city, email, null)
                                    }
                                )
                            }
                        }

                        HorizontalDivider(color = colors.subtleBorder)

                    // 2. Personal Information Fields
                    JourneyTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = tr(lang, "Display Name", "Họ và tên hiển thị"),
                        placeholder = "Minh Nguyễn",
                        leadingIcon = Icons.Default.Person,
                        testTag = "profile_name_input"
                    )

                    JourneyTextField(
                        value = handle,
                        onValueChange = { handle = it },
                        label = tr(lang, "Username (@id)", "ID / Tên người dùng"),
                        placeholder = "@minh.journey",
                        testTag = "profile_handle_input"
                    )

                    JourneyTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = tr(lang, "Short Bio", "Giới thiệu bản thân (Bio)"),
                        placeholder = "Viết đôi dòng giới thiệu...",
                        singleLine = false,
                        testTag = "profile_bio_input"
                    )

                    JourneyTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = tr(lang, "Hometown / City", "Thành phố / Quê quán"),
                        placeholder = "Hà Nội, Việt Nam",
                        leadingIcon = Icons.Default.LocationOn,
                        testTag = "profile_city_input"
                    )

                    JourneyTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = tr(lang, "Email / Contact", "Email / Liên hệ"),
                        placeholder = "minh.nguyen@journey.ai",
                        leadingIcon = Icons.Default.Email,
                        testTag = "profile_email_input"
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    JourneyButton(
                        text = tr(lang, "💾 Save Personal Info", "💾 Lưu thông tin cá nhân"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = {
                            onUpdateProfile(name, handle, bio, city, email, avatarUri)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "profile_save_btn"
                    )

                    HorizontalDivider(color = colors.subtleBorder)

                    // 3. Invite Link & Friends-List Component in Profile Section
                    val cleanProfileHandle = handle.replace("@", "").trim().ifBlank { "minh.journey" }
                    val personalInviteLink = "https://journey-ai.vn/?invite=$cleanProfileHandle"
                    var copiedProfileLink by remember { mutableStateOf(false) }

                    // Connected friends list & pending friend requests state (real user state)
                    var connectedFriends by remember {
                        mutableStateOf(emptyList<Triple<String, String, Pair<Boolean, String>>>())
                    }
                    var pendingFriendRequests by remember {
                        mutableStateOf(emptyList<Triple<String, String, String>>())
                    }
                    var newFriendInput by remember { mutableStateOf("") }
                    var showAddFriendField by remember { mutableStateOf(false) }
                    var profileFriendNotice by remember { mutableStateOf<String?>(null) }
                    var friendRequestToastSender by remember {
                        mutableStateOf<Pair<String, String>?>(null)
                    }
                    var highlightPendingSection by remember { mutableStateOf(false) }

                    // Toast Notification when receiving a new friend request, with action button to open Pending Requests
                    if (friendRequestToastSender != null) {
                        val (senderName, senderHandle) = friendRequestToastSender!!
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = Color(0xFF111923),
                            border = BorderStroke(1.5.dp, Color(0xFFD4AF37)),
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("friend_request_toast")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = tr(
                                        lang,
                                        "💌 New friend request from $senderName ($senderHandle)",
                                        "💌 Lời mời kết bạn mới từ $senderName ($senderHandle)!"
                                    ),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE9E5DC),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = Color(0xFFF59E0B),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(JourneyRadius.pill))
                                        .clickable {
                                            highlightPendingSection = true
                                            friendRequestToastSender = null
                                        }
                                        .testTag("toast_open_pending_requests_btn")
                                ) {
                                    Text(
                                        text = tr(lang, "Open Pending →", "Xem lời mời →"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111923),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "✕",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE9E5DC),
                                    modifier = Modifier
                                        .clickable { friendRequestToastSender = null }
                                        .padding(4.dp)
                                        .testTag("toast_dismiss_friend_request_btn")
                                )
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("profile_invite_link_card")
                    ) {
                        Text(
                            text = tr(
                                lang,
                                "🔗 Friend Invite Link",
                                "🔗 Đường Link Kết Bạn Của Bạn"
                            ),
                            style = MaterialTheme.typography.titleSmall,
                            color = colors.primaryText
                        )
                        Text(
                            text = tr(
                                lang,
                                "Share this link so friends can connect and message you.",
                                "Gửi đường link này cho bạn bè để kết nối và trò chuyện trực tiếp cùng bạn."
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = colors.elevatedSurface,
                            border = BorderStroke(1.dp, colors.accent),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = personalInviteLink,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = colors.accent,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                JourneyButton(
                                    text = if (copiedProfileLink) tr(lang, "✓ Copied", "✓ Đã chép")
                                    else tr(lang, "📋 Copy Link", "📋 Sao chép"),
                                    variant = JourneyButtonVariant.SECONDARY,
                                    onClick = { copiedProfileLink = true },
                                    testTag = "profile_copy_invite_link_btn"
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = colors.subtleBorder)

                    // 4. `friends-list` UI Component in Profile Section with Pending Friend Requests & Searchable Filter
                    var friendSearchQuery by remember { mutableStateOf("") }
                    val normalizedFriendQuery = friendSearchQuery.trim().lowercase()
                    val filteredConnectedFriends = connectedFriends.filter { (friendName, friendHandle, statusPair) ->
                        if (normalizedFriendQuery.isEmpty()) {
                            true
                        } else {
                            friendName.lowercase().contains(normalizedFriendQuery) ||
                                friendHandle.lowercase().contains(normalizedFriendQuery) ||
                                statusPair.second.lowercase().contains(normalizedFriendQuery)
                        }
                    }

                    val onlineFriendsCount = connectedFriends.count { it.third.first }
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = colors.elevatedSurface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("friends_list")
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = tr(
                                                lang,
                                                "👥 Connected Friends (${connectedFriends.size})",
                                                "👥 Bạn bè đã kết nối (${connectedFriends.size})"
                                            ),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = colors.primaryText
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                            color = Color(0x2222C55E),
                                            border = BorderStroke(1.dp, Color(0x6622C55E))
                                        ) {
                                            Text(
                                                text = "● $onlineFriendsCount Online",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF16A34A),
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = tr(
                                            lang,
                                            "Friends sharing moments and direct messages with you",
                                            "Danh sách bạn bè đã kết nối & trạng thái trực tuyến"
                                        ),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = colors.secondaryText
                                    )
                                }

                                JourneyButton(
                                    text = tr(lang, "+ Add Friend", "+ Thêm bạn bè"),
                                    variant = JourneyButtonVariant.PRIMARY,
                                    onClick = {
                                        val clean = newFriendInput.trim()
                                        if (clean.isNotEmpty()) {
                                            val rawHandle = clean.substringAfter("invite=", clean).replace("@", "").trim()
                                            val display = rawHandle
                                                .split(".", "_", "-")
                                                .joinToString(" ") { part -> part.replaceFirstChar { it.uppercase() } }
                                                .ifBlank { clean }
                                            connectedFriends = listOf(
                                                Triple(display, "@$rawHandle", true to "Việt Nam · Đang hoạt động")
                                            ) + connectedFriends
                                            newFriendInput = ""
                                            profileFriendNotice = "🎉 Đã kết bạn với $display (@$rawHandle)!"
                                        } else {
                                            showAddFriendField = !showAddFriendField
                                        }
                                    },
                                    testTag = "profile_add_friend_btn"
                                )
                            }

                            // Pending Friend Requests Section inside friends-list
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = colors.surface,
                                border = BorderStroke(
                                    if (highlightPendingSection) 2.dp else 1.dp,
                                    if (highlightPendingSection) Color(0xFFF59E0B) else colors.accent.copy(alpha = 0.55f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("pending_friend_requests_section")
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = tr(
                                                    lang,
                                                    "💌 Pending Friend Requests (${pendingFriendRequests.size})",
                                                    "💌 Lời mời kết bạn đang chờ (${pendingFriendRequests.size})"
                                                ),
                                                style = MaterialTheme.typography.labelLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = colors.primaryText
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                color = colors.accentContainer,
                                                border = BorderStroke(1.dp, colors.accent)
                                            ) {
                                                Text(
                                                    text = "${pendingFriendRequests.size}",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = colors.accent,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }

                                    if (pendingFriendRequests.isEmpty()) {
                                        Text(
                                            text = tr(
                                                lang,
                                                "✓ No pending friend requests.",
                                                "✓ Không có lời mời kết bạn nào đang chờ xử lý."
                                            ),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = colors.secondaryText,
                                            modifier = Modifier.testTag("pending_friend_requests_empty")
                                        )
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            pendingFriendRequests.forEach { (reqName, reqHandle, reqMutual) ->
                                                val cleanReqKey = reqHandle.replace("@", "")
                                                val reqInitials = reqName
                                                    .split(" ")
                                                    .mapNotNull { it.firstOrNull()?.uppercase() }
                                                    .take(2)
                                                    .joinToString("")
                                                    .ifBlank { "LM" }

                                                Surface(
                                                    shape = RoundedCornerShape(12.dp),
                                                    color = colors.elevatedSurface,
                                                    border = BorderStroke(1.dp, colors.border),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .testTag("pending_request_item_$cleanReqKey")
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(horizontal = 10.dp, vertical = 8.dp),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                            modifier = Modifier.weight(1f)
                                                        ) {
                                                            Box(
                                                                contentAlignment = Alignment.Center,
                                                                modifier = Modifier
                                                                    .size(36.dp)
                                                                    .clip(CircleShape)
                                                                    .background(colors.accentContainer, CircleShape)
                                                            ) {
                                                                Text(
                                                                    text = reqInitials,
                                                                    style = MaterialTheme.typography.labelMedium,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = colors.accent
                                                                )
                                                            }
                                                            Column(modifier = Modifier.weight(1f)) {
                                                                Row(
                                                                    verticalAlignment = Alignment.CenterVertically,
                                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                                ) {
                                                                    Text(
                                                                        text = reqName,
                                                                        style = MaterialTheme.typography.labelLarge,
                                                                        fontWeight = FontWeight.Bold,
                                                                        color = colors.primaryText
                                                                    )
                                                                    Text(
                                                                        text = reqHandle,
                                                                        style = MaterialTheme.typography.labelSmall,
                                                                        color = colors.accent
                                                                    )
                                                                }
                                                                Text(
                                                                    text = "📍 $reqMutual",
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    color = colors.secondaryText,
                                                                    maxLines = 1,
                                                                    overflow = TextOverflow.Ellipsis
                                                                )
                                                            }
                                                        }

                                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                            Surface(
                                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                                color = Color(0xFF16A34A),
                                                                modifier = Modifier
                                                                    .clip(RoundedCornerShape(JourneyRadius.pill))
                                                                    .clickable {
                                                                        pendingFriendRequests =
                                                                            pendingFriendRequests.filterNot { it.first == reqName }
                                                                        connectedFriends = listOf(
                                                                            Triple(
                                                                                reqName,
                                                                                reqHandle,
                                                                                true to "Vừa đồng ý kết bạn · Đang hoạt động"
                                                                            )
                                                                        ) + connectedFriends
                                                                        profileFriendNotice =
                                                                            "🤝 Đã chấp nhận lời mời kết bạn của $reqName ($reqHandle)!"
                                                                    }
                                                                    .testTag("accept_friend_request_btn_$cleanReqKey")
                                                            ) {
                                                                Text(
                                                                    text = tr(lang, "✓ Accept", "✓ Đồng ý"),
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = Color.White,
                                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                                                )
                                                            }

                                                            Surface(
                                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                                color = Color(0x22EF4444),
                                                                border = BorderStroke(1.dp, Color(0x66EF4444)),
                                                                modifier = Modifier
                                                                    .clip(RoundedCornerShape(JourneyRadius.pill))
                                                                    .clickable {
                                                                        pendingFriendRequests =
                                                                            pendingFriendRequests.filterNot { it.first == reqName }
                                                                        profileFriendNotice =
                                                                            "Đã từ chối lời mời kết bạn của $reqName."
                                                                    }
                                                                    .testTag("decline_friend_request_btn_$cleanReqKey")
                                                            ) {
                                                                Text(
                                                                    text = tr(lang, "✕ Decline", "✕ Từ chối"),
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = Color(0xFFEF4444),
                                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Searchable Filter Bar for Friends-List
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    JourneyTextField(
                                        value = friendSearchQuery,
                                        onValueChange = { friendSearchQuery = it },
                                        label = tr(
                                            lang,
                                            "🔍 Search friends by name",
                                            "🔍 Tìm bạn bè theo tên hoặc @username"
                                        ),
                                        placeholder = tr(
                                            lang,
                                            "Type a friend's name (e.g. Lan Anh, Đức Huy)...",
                                            "Nhập tên bạn bè (VD: Lan Anh, Đức Huy)..."
                                        ),
                                        testTag = "friends_search_filter_input"
                                    )
                                }
                                if (friendSearchQuery.isNotBlank()) {
                                    JourneyButton(
                                        text = tr(lang, "✕ Clear", "✕ Xóa"),
                                        variant = JourneyButtonVariant.SECONDARY,
                                        onClick = { friendSearchQuery = "" },
                                        testTag = "friends_search_clear_btn"
                                    )
                                }
                            }

                            if (showAddFriendField || newFriendInput.isNotEmpty()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        JourneyTextField(
                                            value = newFriendInput,
                                            onValueChange = { newFriendInput = it },
                                            label = tr(lang, "Friend @username or invite link", "Nhập @username hoặc link kết bạn"),
                                            placeholder = "@khanhlinh.vn",
                                            testTag = "profile_add_friend_input"
                                        )
                                    }
                                }
                            }

                            if (profileFriendNotice != null) {
                                Text(
                                    text = profileFriendNotice!!,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.sage,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Connected Friend Items with Online Status Indicators
                            if (filteredConnectedFriends.isEmpty()) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = colors.surface,
                                    border = BorderStroke(1.dp, colors.border),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("friends_search_empty_state")
                                ) {
                                    Text(
                                        text = tr(
                                            lang,
                                            "🔍 No friends found matching \"$friendSearchQuery\".",
                                            "🔍 Không tìm thấy bạn bè nào khớp với \"$friendSearchQuery\"."
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.secondaryText,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)
                                    )
                                }
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    filteredConnectedFriends.forEach { (friendName, friendHandle, statusPair) ->
                                        val isOnline = statusPair.first
                                        val statusSubtitle = statusPair.second
                                        val initials = friendName
                                            .split(" ")
                                            .mapNotNull { it.firstOrNull()?.uppercase() }
                                            .take(2)
                                            .joinToString("")
                                            .ifBlank { "BN" }

                                        Surface(
                                            shape = RoundedCornerShape(14.dp),
                                            color = colors.surface,
                                            border = BorderStroke(1.dp, colors.border),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("profile_friend_item_${friendHandle.replace("@", "")}")
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    // Avatar + Online Status Indicator Dot
                                                    Box(modifier = Modifier.size(42.dp)) {
                                                        Box(
                                                            contentAlignment = Alignment.Center,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .clip(CircleShape)
                                                                .background(colors.accentContainer, CircleShape)
                                                                .border(1.dp, colors.accent, CircleShape)
                                                        ) {
                                                            Text(
                                                                text = initials,
                                                                style = MaterialTheme.typography.labelMedium,
                                                                fontWeight = FontWeight.Bold,
                                                                color = colors.accent
                                                            )
                                                        }
                                                        Box(
                                                            modifier = Modifier
                                                                .size(12.dp)
                                                                .align(Alignment.BottomEnd)
                                                                .clip(CircleShape)
                                                                .background(
                                                                    if (isOnline) Color(0xFF22C55E) else Color(0xFF94A3B8),
                                                                    CircleShape
                                                                )
                                                                .border(2.dp, colors.surface, CircleShape)
                                                                .testTag(
                                                                    "friend_status_indicator_${friendHandle.replace("@", "")}"
                                                                )
                                                        )
                                                    }

                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                        ) {
                                                            Text(
                                                                text = friendName,
                                                                style = MaterialTheme.typography.labelLarge,
                                                                fontWeight = FontWeight.Bold,
                                                                color = colors.primaryText
                                                            )
                                                            Text(
                                                                text = friendHandle,
                                                                style = MaterialTheme.typography.labelSmall,
                                                                color = colors.accent
                                                            )
                                                        }
                                                        Text(
                                                            text = "📍 $statusSubtitle",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = colors.secondaryText,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                                }

                                                Surface(
                                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                                    color = if (isOnline) Color(0x2222C55E) else colors.elevatedSurface,
                                                    border = BorderStroke(
                                                        1.dp,
                                                        if (isOnline) Color(0x6622C55E) else colors.border
                                                    )
                                                ) {
                                                    Text(
                                                        text = if (isOnline) "● Online" else "○ Offline",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (isOnline) Color(0xFF16A34A) else colors.secondaryText,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    }
                }
            }
        }
    }
}

// ============================================================================
// I2: SETTINGS
// ============================================================================
@Composable
fun SettingsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onCycleVoiceSpeed: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i2_settings")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I2_SETTINGS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "PREFERENCES & LOCALIZATION", "TÙY CHỈNH HỆ THỐNG"),
                title = tr(lang, "Application Settings", "Cài Đặt Ứng Dụng")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    // Dark / Light Mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, "Dark Mode (Midnight #111923)", "Chế độ Tối (Midnight #111923)"),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = tr(lang, "Switch between Midnight Dark and Warm Ivory Light theme", "Chuyển đổi giữa giao diện Tối điện ảnh và Sáng màu ngà"),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        Switch(
                            checked = uiState.isDarkTheme,
                            onCheckedChange = { onToggleTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.onAccent,
                                checkedTrackColor = colors.accent
                            )
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)

                    // Language EN / VI
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, "Display & Voice Language", "Ngôn ngữ Hiển thị & Thuyết minh"),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = "Current: ${uiState.language.label}",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        JourneyButton(
                            text = if (lang == com.example.model.AppLanguage.EN) "Switch to Tiếng Việt" else "Chuyển sang English",
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = onToggleLanguage
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)

                    // Voice Narration Speed
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, "AI Voice Guide Cadence", "Tốc độ Thuyết minh Giọng nói"),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = "${uiState.voiceSpeedMultiplier}x speed",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        JourneyButton(
                            text = "${uiState.voiceSpeedMultiplier}x",
                            variant = JourneyButtonVariant.SURFACE_TONAL,
                            onClick = onCycleVoiceSpeed
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))
            JourneyButton(
                text = tr(lang, "Privacy & Data Controls (I4)", "Quyền riêng tư & Dữ liệu (I4)"),
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.I4_PRIVACY_AND_DATA) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ============================================================================
// I3: SUBSCRIPTION & PLAN COMPARISON (FLOW 7: Profile -> Subscription -> Plan Comparison)
// ============================================================================
@Composable
fun SubscriptionScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlan: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    val plans = listOf(
        Triple(
            "EXPLORER_FREE",
            tr(lang, "Explorer Tier • Free", "Gói Khám Phá • Miễn Phí"),
            listOf(
                tr(lang, "5 AI Monument Recognition captures / day", "5 lượt chụp nhận diện di sản AI / ngày"),
                tr(lang, "Standard 1080p travel journal stories", "Nhật ký hành trình & câu chuyện AI tiêu chuẩn"),
                tr(lang, "Online map & community discovery feed", "Bản đồ trực tuyến & bảng tin khám phá")
            )
        ),
        Triple(
            "JOURNEY_PRO_MONTHLY",
            tr(lang, "Journey+ Atelier • 199.000₫ / mo ($7.99)", "Hội Viên Journey+ • 199.000₫ / tháng"),
            listOf(
                tr(lang, "Unlimited AI Architectural & Artifact Recognition", "Nhận diện Kiến trúc & Cổ vật AI không giới hạn"),
                tr(lang, "Full Binaural Voice Travel Guide (VI & EN)", "Thuyết minh giọng nói chuyên sâu song ngữ Việt - Anh"),
                tr(lang, "Offline Vietnam & UNESCO Heritage Maps", "Tải bản đồ & dữ liệu di sản ngoại tuyến")
            )
        ),
        Triple(
            "JOURNEY_PRO_ANNUAL",
            tr(lang, "Journey+ Patron Annual • 1.490.000₫ / yr ($59.99)", "Journey+ Năm (Tiết kiệm 38%) • 1.490.000₫ / năm"),
            listOf(
                tr(lang, "Everything in Journey+ Monthly included", "Bao gồm toàn bộ đặc quyền gói Journey+ Tháng"),
                tr(lang, "4K 60fps Cinematic Travel Film Export & LUTs", "Xuất phim hành trình 4K 60fps & bộ lọc màu điện ảnh"),
                tr(lang, "Priority FastAPI Vision RAG & Cloud Archive", "Ưu tiên xử lý AI tốc độ cao & lưu trữ đám mây")
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i3_subscription")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I3_SUBSCRIPTION,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "FLOW 7 • PLAN COMPARISON", "LUỒNG 7 • SO SÁNH GÓI HỘI VIÊN"),
                title = tr(lang, "Elevate Every Expedition with Journey+", "Nâng Tầm Trải Nghiệm Cùng Journey+")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            plans.forEach { (planId, title, perks) ->
                val isSelected = uiState.selectedPlanTier == planId
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.xl),
                    color = if (isSelected) colors.accentContainer else colors.surface,
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) colors.accent else colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onSelectPlan(planId) }
                        .testTag("plan_card_${planId.lowercase()}")
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.xl)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge,
                                color = colors.primaryText,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected) {
                                JourneyBadgePill(
                                    text = tr(lang, "ACTIVE PLAN", "ĐANG CHỌN"),
                                    icon = Icons.Default.Star
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        perks.forEach { perk ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = perk,
                                    tint = colors.accent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = perk,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.secondaryText
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        JourneyButton(
                            text = if (isSelected) tr(lang, "Selected Plan", "Gói Đang Chọn")
                            else tr(lang, "Select $planId", "Chọn gói này"),
                            variant = if (isSelected) JourneyButtonVariant.PRIMARY else JourneyButtonVariant.SECONDARY,
                            onClick = { onSelectPlan(planId) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// I4: PRIVACY AND DATA
// ============================================================================
@Composable
fun PrivacyAndDataScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onTogglePermission: (String) -> Unit,
    onShowToast: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i4_privacy")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I4_PRIVACY_AND_DATA,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = "SUPABASE RLS & ZERO-TRUST PRIVACY",
                title = tr(lang, "Privacy, Hardware Permissions & Data Sovereignty", "Quyền Riêng Tư & Chủ Quyền Dữ Liệu")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Text(
                        text = tr(lang, "DATA PROTECTION ARCHITECTURE", "KIẾN TRÚC BẢO MẬT DỮ LIỆU"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tr(
                            lang,
                            "• Personal journals and EXIF coordinates are protected by Supabase PostgreSQL Row-Level Security (RLS).\n• Visual recognition frames sent to FastAPI are ephemeral unless explicitly saved to a Discovery Collection.\n• Full compliance with Vietnam Decree 13/2023/ND-CP on Personal Data Protection and GDPR.",
                            "• Nhật ký cá nhân và tọa độ EXIF được bảo vệ bằng cơ chế Row-Level Security (RLS) trên Supabase.\n• Ảnh chụp nhận diện qua FastAPI không tự động lưu trữ nếu bạn không đưa vào Bộ sưu tập.\n• Tuân thủ đầy đủ Nghị định 13/2023/NĐ-CP về Bảo vệ dữ liệu cá nhân."
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                JourneyButton(
                    text = tr(lang, "Export Archive (JSON/ZIP)", "Xuất Dữ Liệu Cá Nhân"),
                    leadingIcon = Icons.Default.Download,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = {
                        onShowToast(
                            if (lang == com.example.model.AppLanguage.EN)
                                "Prepared personal archive export package"
                            else
                                "Đã chuẩn bị gói xuất dữ liệu cá nhân"
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Clear AI Cache", "Xóa Bộ Nhớ Đệm"),
                    leadingIcon = Icons.Default.DeleteSweep,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = {
                        onShowToast(
                            if (lang == com.example.model.AppLanguage.EN)
                                "Cleared temporary vision cache"
                            else
                                "Đã dọn sạch bộ nhớ đệm nhận diện"
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
