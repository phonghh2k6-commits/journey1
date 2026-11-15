package com.example.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.example.model.AppLanguage
import com.example.model.JourneyScreenId
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
import java.util.Locale
import kotlin.math.sin

/**
 * Saves the captured square photo (from live camera PreviewView bitmap, gallery URI, or fallback)
 * and burns optional overlays (Location, Current Time, Current Temperature, Sticker, Spotify Track).
 */
private fun burnOverlaysOnSquareBitmap(
    context: Context,
    livePreviewBitmap: Bitmap?,
    sourceUriString: String?,
    fallbackResId: Int,
    locationLabel: String?,
    timeLabel: String?,
    temperatureLabel: String?,
    stickerLabel: String?,
    spotifyTrackLabel: String?
): String? {
    return runCatching {
        val rawBitmap = livePreviewBitmap
            ?: if (!sourceUriString.isNullOrBlank()) {
                val parsedUri = Uri.parse(sourceUriString)
                context.contentResolver.openInputStream(parsedUri)?.use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
            } else {
                null
            }
            ?: BitmapFactory.decodeResource(context.resources, fallbackResId)
            ?: return null

        val targetSize = 720
        val minDim = minOf(rawBitmap.width, rawBitmap.height).coerceAtLeast(1)
        val cropX = ((rawBitmap.width - minDim) / 2).coerceAtLeast(0)
        val cropY = ((rawBitmap.height - minDim) / 2).coerceAtLeast(0)
        val cropped = Bitmap.createBitmap(rawBitmap, cropX, cropY, minDim, minDim)
        val output = Bitmap.createScaledBitmap(cropped, targetSize, targetSize, true)
            .copy(Bitmap.Config.ARGB_8888, true)

        val canvas = AndroidCanvas(output)

        // Top-left Sticker + Time + Temperature pill if any is enabled
        val metaParts = buildList {
            if (!stickerLabel.isNullOrBlank()) add(stickerLabel)
            if (!timeLabel.isNullOrBlank()) add("🕒 $timeLabel")
            if (!temperatureLabel.isNullOrBlank()) add("🌡️ $temperatureLabel")
        }
        if (metaParts.isNotEmpty()) {
            val metaText = metaParts.joinToString("  ·  ")
            val topBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.argb(185, 15, 23, 42)
                style = Paint.Style.FILL
            }
            val topTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.WHITE
                textSize = 21f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            val textWidth = topTextPaint.measureText(metaText).coerceAtMost(targetSize - 80f)
            val topRect = RectF(24f, 24f, 24f + textWidth + 36f, 74f)
            canvas.drawRoundRect(topRect, 25f, 25f, topBgPaint)
            canvas.drawText(metaText.take(42), 42f, 56f, topTextPaint)
        }

        // Bottom overlays: Spotify track and/or Location
        var bottomY = targetSize - 28f
        if (!locationLabel.isNullOrBlank()) {
            val locBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.argb(195, 15, 23, 42)
                style = Paint.Style.FILL
            }
            val locTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.rgb(245, 230, 179)
                textSize = 21f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            val locText = "📍 ${locationLabel.take(38)}"
            val locW = locTextPaint.measureText(locText).coerceAtMost(targetSize - 80f)
            val locRect = RectF(24f, bottomY - 48f, 24f + locW + 36f, bottomY)
            canvas.drawRoundRect(locRect, 24f, 24f, locBgPaint)
            canvas.drawText(locText, 42f, bottomY - 17f, locTextPaint)
            bottomY -= 58f
        }

        if (!spotifyTrackLabel.isNullOrBlank()) {
            val spBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.argb(215, 20, 83, 45)
                style = Paint.Style.FILL
            }
            val spTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.rgb(187, 247, 208)
                textSize = 20f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            val spText = "🎵 ${spotifyTrackLabel.take(38)}"
            val spW = spTextPaint.measureText(spText).coerceAtMost(targetSize - 80f)
            val spRect = RectF(24f, bottomY - 48f, 24f + spW + 36f, bottomY)
            canvas.drawRoundRect(spRect, 24f, 24f, spBgPaint)
            canvas.drawText(spText, 42f, bottomY - 17f, spTextPaint)
        }

        val outFile = File(context.cacheDir, "journey_capture_${System.currentTimeMillis()}.jpg")
        FileOutputStream(outFile).use { fos ->
            output.compress(Bitmap.CompressFormat.JPEG, 92, fos)
        }
        Uri.fromFile(outFile).toString()
    }.getOrNull()
}

// ============================================================================
// D1: CAMERA SCREEN (CHỤP ẢNH / QUAY NGẮN TỨC THÌ + TÙY CHỌN VỊ TRÍ THỰC, NHẠC SPOTIFY, NHÃN DÁN, NHIỆT ĐỘ, THỜI GIAN)
// ============================================================================
@SuppressLint("MissingPermission")
@Composable
fun AiCameraScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onImagePicked: (String?) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit,
    onUpdateLiveLocation: (String) -> Unit = {},
    onPublishLocketPost: (
        caption: String,
        placeId: String,
        imageUri: String?,
        privacy: String,
        isVideo: Boolean,
        videoDurationSec: Int,
        customLocationName: String,
        timeWatermark: String,
        temperatureTag: String,
        stickerTag: String,
        spotifyTrack: String
    ) -> Unit = { _, _, _, _, _, _, _, _, _, _, _ -> }
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val fallbackPlace = uiState.selectedPlace

    var isPhotoCaptured by remember { mutableStateOf(uiState.capturedImageUri != null) }
    var isVideoMode by remember { mutableStateOf(false) }
    var isRecordingVideo by remember { mutableStateOf(false) }
    var recordedSeconds by remember { mutableStateOf(0) }
    var captionText by remember { mutableStateOf("") }
    var useFrontCamera by remember { mutableStateOf(false) }

    // Optional Overlays State (User chooses what to attach!)
    var enableTimeOverlay by remember { mutableStateOf(true) }
    var currentTimeText by remember {
        mutableStateOf(SimpleDateFormat("HH:mm · dd/MM", Locale.getDefault()).format(Date()))
    }

    var enableTempOverlay by remember { mutableStateOf(true) }
    var selectedTempText by remember { mutableStateOf("26°C ☀️") }
    val presetTemps = listOf("26°C ☀️", "23°C 🌤️", "29°C 🔥", "20°C 🌧️", "17°C ❄️")

    var enableLocationOverlay by remember { mutableStateOf(false) }
    var locationText by remember {
        mutableStateOf(uiState.liveGpsCoordinates?.takeIf { it.isNotBlank() } ?: "")
    }
    var isFetchingGps by remember { mutableStateOf(false) }

    var selectedSticker by remember { mutableStateOf("") }
    val stickerOptions = listOf(
        "✨ Chill",
        "☕ Cà phê",
        "🍜 Ăn ngon",
        "🌅 Hoàng hôn",
        "🎉 Vui vẻ",
        "❤️ Yêu thương",
        "💪 Năng lượng",
        "🌿 Bình yên"
    )

    var selectedSpotifyTrack by remember { mutableStateOf("") }
    val spotifyNowPlayingTracks = listOf(
        Triple("💿 Đừng Làm Trái Tim Anh Đau", "Sơn Tùng M-TP · 04:39", "🟢 Now Playing"),
        Triple("🌿 Bình Yên", "Vũ. ft. Binz · 03:45", "Playing Next"),
        Triple("🎸 Một Đêm Say", "Thịnh Suy · 03:18", "Recent"),
        Triple("✨ Chúng Ta Của Tương Lai", "Sơn Tùng M-TP · 04:09", "Recent"),
        Triple("🎧 Thích Em Hơi Nhiều", "Wren Evans · 02:54", "Recent")
    )

    // Active expandable widget panel: NONE | LOCATION | SPOTIFY | STICKER | TEMP
    var activeWidgetDrawer by remember { mutableStateOf("NONE") }

    // Privacy selector state
    var privacyMode by remember { mutableStateOf("PUBLIC") } // PUBLIC | CUSTOM | PRIVATE
    var selectedFriends by remember { mutableStateOf(setOf("Lan Anh", "Hoàng Nam")) }
    val allFriends = listOf("Lan Anh", "Hoàng Nam", "Thảo Tiên", "Tuấn Kiệt")

    // Camera & Microphone Permissions
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var hasMicPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var hasRequestedInitialPermissions by remember { mutableStateOf(false) }
    var permissionDeniedOnce by remember { mutableStateOf(false) }
    var bypassPermissionGate by remember { mutableStateOf(false) }

    // Keep PreviewView remembered with COMPATIBLE (TextureView) mode so camera binds immediately in-place
    // when permission is granted without requiring a tab switch, and previewView.bitmap is always available.
    val previewView = remember(context) {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }

    val bindCameraUseCase: () -> Unit = {
        if (hasCameraPermission && !isPhotoCaptured) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                runCatching {
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val selector = if (useFrontCamera) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview
                    )
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }

    // Re-bind camera immediately whenever permission is granted, camera flips, or user retakes photo
    LaunchedEffect(hasCameraPermission, useFrontCamera, isPhotoCaptured) {
        if (hasCameraPermission && !isPhotoCaptured) {
            bindCameraUseCase()
        }
    }

    val fetchCurrentGpsAddress: () -> Unit = {
        isFetchingGps = true
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { loc ->
                isFetchingGps = false
                if (loc != null) {
                    val resolvedName = runCatching {
                        @Suppress("DEPRECATION")
                        val list = Geocoder(context, Locale.getDefault())
                            .getFromLocation(loc.latitude, loc.longitude, 1)
                        val addr = list?.firstOrNull()
                        if (addr != null) {
                            listOfNotNull(
                                addr.subLocality ?: addr.thoroughfare,
                                addr.locality ?: addr.subAdminArea,
                                addr.adminArea
                            ).distinct().joinToString(", ").ifBlank {
                                String.format(Locale.US, "%.4f°N, %.4f°E", loc.latitude, loc.longitude)
                            }
                        } else {
                            String.format(Locale.US, "%.4f°N, %.4f°E", loc.latitude, loc.longitude)
                        }
                    }.getOrDefault(
                        String.format(Locale.US, "%.4f°N, %.4f°E", loc.latitude, loc.longitude)
                    )
                    locationText = resolvedName
                    onUpdateLiveLocation(resolvedName)
                } else if (locationText.isBlank()) {
                    locationText = uiState.liveGpsCoordinates?.takeIf { it.isNotBlank() } ?: "Vị trí hiện tại của bạn"
                }
            }
            .addOnFailureListener {
                isFetchingGps = false
                if (locationText.isBlank()) {
                    locationText = uiState.liveGpsCoordinates?.takeIf { it.isNotBlank() } ?: "Vị trí hiện tại của bạn"
                }
            }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        hasLocationPermission = granted
        if (granted) {
            enableLocationOverlay = true
            fetchCurrentGpsAddress()
        }
    }

    val cameraAndMicPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val camGranted = permissionsMap[Manifest.permission.CAMERA]
            ?: (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        val micGranted = permissionsMap[Manifest.permission.RECORD_AUDIO]
            ?: (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
        hasCameraPermission = camGranted
        hasMicPermission = micGranted
        if (camGranted) {
            bypassPermissionGate = true
            bindCameraUseCase()
        } else {
            permissionDeniedOnce = true
        }
    }

    // Re-check permissions when returning from system dialog or Settings
    DisposableEffect(lifecycleOwner, context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val camNow = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                val micNow = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
                hasCameraPermission = camNow
                hasMicPermission = micNow
                if (camNow && !isPhotoCaptured) {
                    bindCameraUseCase()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val performPhotoCapture: (String?) -> Unit = { galleryUriStr ->
        val freshTime = SimpleDateFormat("HH:mm · dd/MM", Locale.getDefault()).format(Date())
        currentTimeText = freshTime
        val liveBmp = if (galleryUriStr == null && hasCameraPermission) {
            runCatching { previewView.bitmap }.getOrNull()
        } else {
            null
        }
        val stampedUri = burnOverlaysOnSquareBitmap(
            context = context,
            livePreviewBitmap = liveBmp,
            sourceUriString = galleryUriStr,
            fallbackResId = fallbackPlace.imageRes,
            locationLabel = locationText.takeIf { enableLocationOverlay && it.isNotBlank() },
            timeLabel = freshTime.takeIf { enableTimeOverlay },
            temperatureLabel = selectedTempText.takeIf { enableTempOverlay },
            stickerLabel = selectedSticker.takeIf { it.isNotBlank() },
            spotifyTrackLabel = selectedSpotifyTrack.takeIf { it.isNotBlank() }
        ) ?: galleryUriStr
        onImagePicked(stampedUri)
        isVideoMode = false
        isPhotoCaptured = true
        bypassPermissionGate = true
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            performPhotoCapture(uri.toString())
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission && !hasRequestedInitialPermissions) {
            hasRequestedInitialPermissions = true
            cameraAndMicPermissionLauncher.launch(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            )
        }
    }

    // Hold-to-record 5-10s timer
    LaunchedEffect(isRecordingVideo) {
        if (isRecordingVideo) {
            recordedSeconds = 0
            while (isRecordingVideo && recordedSeconds < 10) {
                delay(1000L)
                recordedSeconds += 1
            }
            if (isRecordingVideo && recordedSeconds >= 10) {
                isRecordingVideo = false
                isVideoMode = true
                performPhotoCapture(null)
                isVideoMode = true
            }
        }
    }

    val currentHasCameraPermission by rememberUpdatedState(hasCameraPermission)
    val currentBypassGate by rememberUpdatedState(bypassPermissionGate)
    val currentPerformCapture by rememberUpdatedState(performPhotoCapture)

    val computedPrivacyLabel = when (privacyMode) {
        "PRIVATE" -> "🔒 Chỉ mình tôi"
        "CUSTOM" -> "👥 Chỉ: ${selectedFriends.joinToString(", ").ifEmpty { "Bạn bè" }}"
        else -> "🌎 Tất cả bạn bè"
    }

    val showPermissionGate = !hasCameraPermission && !bypassPermissionGate && !isPhotoCaptured

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = JourneySpacing.md, vertical = 8.dp)
            .testTag("screen_d1_ai_camera"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 1. Minimal Status Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tr(lang, "📷 Instant Camera", "📷 Máy ảnh khoảnh khắc"),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText
                )
                JourneyBadgePill(
                    text = when {
                        isRecordingVideo -> "🔴 REC ${recordedSeconds}s / 10s"
                        hasCameraPermission -> tr(lang, "● Live Camera", "● Sẵn sàng chụp")
                        else -> tr(lang, "Camera Access", "Cấp quyền máy ảnh")
                    },
                    containerColor = if (isRecordingVideo) Color(0x33DC2626) else colors.accentContainer,
                    contentColor = if (isRecordingVideo) Color(0xFFEF4444) else colors.accent
                )
            }

            // 1B. Clean Camera Permission Card (only if Camera is not yet granted)
            if (showPermissionGate) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("locket_permission_gate_card")
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(56.dp)
                                .background(colors.accentContainer, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = colors.accent,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Text(
                            text = tr(lang, "Allow Camera Access", "Cho phép truy cập Máy ảnh"),
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.primaryText
                        )

                        Text(
                            text = tr(
                                lang,
                                "Grant camera access to snap photos and record short moments immediately.",
                                "Cấp quyền máy ảnh để chụp ảnh trực tiếp ngay lập tức mà không cần chuyển trang."
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )

                        JourneyButton(
                            text = tr(lang, "🔓 Allow Camera Now", "🔓 Cấp quyền Máy ảnh ngay"),
                            variant = JourneyButtonVariant.PRIMARY,
                            onClick = {
                                cameraAndMicPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "request_camera_mic_permissions_btn"
                        )

                        if (permissionDeniedOnce) {
                            JourneyButton(
                                text = tr(lang, "⚙️ System Settings", "⚙️ Mở Cài đặt quyền"),
                                variant = JourneyButtonVariant.SECONDARY,
                                onClick = {
                                    runCatching {
                                        val intent = Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", context.packageName, null)
                                        ).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                testTag = "open_system_permission_settings_btn"
                            )
                        }

                        JourneyButton(
                            text = tr(lang, "🖼️ Pick Photo from Device", "🖼️ Hoặc chọn ảnh từ thư viện"),
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "gate_pick_gallery_btn"
                        )
                    }
                }
            }

            // 2. Responsive 1:1 Square Viewfinder
            if (!showPermissionGate) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(32.dp))
                        .background(JourneyPalette.DeepObsidian)
                        .border(
                            width = 2.5.dp,
                            color = if (isRecordingVideo) Color(0xFFEF4444) else colors.accent.copy(alpha = 0.65f),
                            shape = RoundedCornerShape(32.dp)
                        )
                ) {
                    if (!isPhotoCaptured && hasCameraPermission) {
                        AndroidView(
                            factory = { previewView },
                            update = { bindCameraUseCase() },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else if (uiState.capturedImageUri != null) {
                        AsyncImage(
                            model = uiState.capturedImageUri,
                            contentDescription = "Ảnh đã chụp",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = fallbackPlace.imageRes),
                            contentDescription = "Khung ngắm",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Top Overlay inside Viewfinder: Time + Temperature + Sticker pills
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val topPills = buildList {
                                if (selectedSticker.isNotBlank()) add(selectedSticker)
                                if (enableTimeOverlay) add("🕒 $currentTimeText")
                                if (enableTempOverlay) add("🌡️ $selectedTempText")
                            }
                            if (topPills.isNotEmpty()) {
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = Color(0xB80F172A),
                                    border = BorderStroke(1.dp, Color(0x44FFFFFF))
                                ) {
                                    Text(
                                        text = topPills.joinToString("  ·  "),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = JourneyPalette.Ivory,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.width(1.dp))
                            }

                            if (isRecordingVideo) {
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = Color(0xCCDC2626)
                                ) {
                                    Text(
                                        text = "🔴 ${recordedSeconds}s",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        if (isRecordingVideo) {
                            LinearProgressIndicator(
                                progress = { (recordedSeconds / 10f).coerceIn(0f, 1f) },
                                color = Color(0xFFEF4444),
                                trackColor = Color(0x44FFFFFF),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(999.dp))
                            )
                        }
                    }

                    // Bottom Overlay inside Viewfinder: Optional Spotify Pill + Optional Real Location Pill
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (selectedSpotifyTrack.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(JourneyRadius.pill),
                                color = Color(0xD914532D),
                                border = BorderStroke(1.dp, Color(0xFF22C55E))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "🎵 $selectedSpotifyTrack",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFFBBF7D0),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        if (enableLocationOverlay && locationText.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(JourneyRadius.pill),
                                color = Color(0xCC0F172A),
                                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.7f)),
                                modifier = Modifier.testTag("camera_date_location_watermark")
                            ) {
                                Text(
                                    text = "📍 $locationText",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFF3E5AB),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // 2B. Clean Widget & Overlay Bar (camera-widgets-strip: Spotify Music, Location, Stickers, Temperature, Time)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .testTag("camera_widgets_strip"),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. Spotify Music Widget Button
                    val musicActive = selectedSpotifyTrack.isNotBlank() || activeWidgetDrawer == "SPOTIFY"
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (musicActive) Color(0xFF15803D) else colors.surface,
                        border = BorderStroke(1.dp, if (musicActive) Color(0xFF22C55E) else colors.border),
                        modifier = Modifier
                            .clickable {
                                activeWidgetDrawer = if (activeWidgetDrawer == "SPOTIFY") "NONE" else "SPOTIFY"
                            }
                            .testTag("spotify_music_widget_btn")
                    ) {
                        Text(
                            text = if (selectedSpotifyTrack.isNotBlank()) {
                                "🎵 Spotify Music · ${selectedSpotifyTrack.substringBefore(" - ")}"
                            } else {
                                "🎵 Spotify Music"
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = if (musicActive) Color.White else colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }

                    // 2. Optional Real GPS Location Toggle
                    val locActive = enableLocationOverlay
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (locActive) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (locActive) colors.accent else colors.border),
                        modifier = Modifier
                            .clickable {
                                if (!enableLocationOverlay) {
                                    enableLocationOverlay = true
                                    activeWidgetDrawer = "LOCATION"
                                    if (hasLocationPermission) {
                                        fetchCurrentGpsAddress()
                                    } else {
                                        locationPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                } else {
                                    activeWidgetDrawer = if (activeWidgetDrawer == "LOCATION") "NONE" else "LOCATION"
                                }
                            }
                            .testTag("toggle_location_chip")
                    ) {
                        Text(
                            text = if (locActive) "📍 Đã gắn vị trí" else "📍 Gắn vị trí",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (locActive) colors.onAccent else colors.primaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }

                    // 3. Stickers Chip
                    val stickerActive = selectedSticker.isNotBlank()
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (stickerActive) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (stickerActive) colors.accent else colors.border),
                        modifier = Modifier
                            .clickable {
                                activeWidgetDrawer = if (activeWidgetDrawer == "STICKER") "NONE" else "STICKER"
                            }
                            .testTag("toggle_sticker_chip")
                    ) {
                        Text(
                            text = if (stickerActive) selectedSticker else "✨ Nhãn dán",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (stickerActive) colors.onAccent else colors.primaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }

                    // 4. Temperature Chip
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (enableTempOverlay) colors.accentContainer else colors.surface,
                        border = BorderStroke(1.dp, if (enableTempOverlay) colors.accent else colors.border),
                        modifier = Modifier
                            .clickable {
                                activeWidgetDrawer = if (activeWidgetDrawer == "TEMP") "NONE" else "TEMP"
                            }
                            .testTag("toggle_temp_chip")
                    ) {
                        Text(
                            text = if (enableTempOverlay) "🌡️ $selectedTempText" else "🌡️ Nhiệt độ: Tắt",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (enableTempOverlay) colors.accent else colors.secondaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }

                    // 5. Current Time Chip
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (enableTimeOverlay) colors.accentContainer else colors.surface,
                        border = BorderStroke(1.dp, if (enableTimeOverlay) colors.accent else colors.border),
                        modifier = Modifier
                            .clickable {
                                enableTimeOverlay = !enableTimeOverlay
                                currentTimeText = SimpleDateFormat("HH:mm · dd/MM", Locale.getDefault()).format(Date())
                            }
                            .testTag("toggle_time_chip")
                    ) {
                        Text(
                            text = if (enableTimeOverlay) "🕒 $currentTimeText" else "🕒 Thời gian: Tắt",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (enableTimeOverlay) colors.accent else colors.secondaryText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }

                // Expandable Drawer for the selected widget (Location / Spotify / Sticker / Temp)
                if (activeWidgetDrawer != "NONE") {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            when (activeWidgetDrawer) {
                                "LOCATION" -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (isFetchingGps) "📡 Đang định vị GPS hiện tại..." else "📍 Vị trí hiện tại của bạn",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText
                                        )
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Text(
                                                text = "🎯 Định vị lại",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = colors.accent,
                                                modifier = Modifier.clickable {
                                                    enableLocationOverlay = true
                                                    if (hasLocationPermission) {
                                                        fetchCurrentGpsAddress()
                                                    } else {
                                                        locationPermissionLauncher.launch(
                                                            arrayOf(
                                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                                            )
                                                        )
                                                    }
                                                }
                                            )
                                            Text(
                                                text = "✕ Bỏ vị trí",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = colors.secondaryText,
                                                modifier = Modifier.clickable {
                                                    enableLocationOverlay = false
                                                    locationText = ""
                                                    activeWidgetDrawer = "NONE"
                                                }
                                            )
                                        }
                                    }
                                    JourneyTextField(
                                        value = locationText,
                                        onValueChange = {
                                            locationText = it
                                            enableLocationOverlay = it.isNotBlank()
                                        },
                                        label = tr(lang, "Current Location", "Tên địa điểm hiện tại (hoặc để trống)"),
                                        placeholder = tr(lang, "Enter your current spot...", "Nhập vị trí bạn đang ở...")
                                    )
                                }

                                "SPOTIFY" -> {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("spotify_music_drawer")
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = "🟢 Spotify Music · Đang phát (Now Playing)",
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = colors.primaryText
                                                )
                                                Text(
                                                    text = "Chọn bài hát đang phát để gắn lên ảnh hoặc video của bạn",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = colors.secondaryText
                                                )
                                            }
                                            if (selectedSpotifyTrack.isNotBlank()) {
                                                Text(
                                                    text = "✕ Gỡ nhạc",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color(0xFFEF4444),
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.clickable {
                                                        selectedSpotifyTrack = ""
                                                    }
                                                )
                                            }
                                        }

                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(6.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            spotifyNowPlayingTracks.forEach { (titleWithIcon, artistMeta, statusLabel) ->
                                                val cleanTitle = titleWithIcon.substringAfter(" ")
                                                val cleanArtist = artistMeta.substringBefore(" · ")
                                                val fullTrackLabel = "$cleanTitle - $cleanArtist"
                                                val chosen = selectedSpotifyTrack == fullTrackLabel
                                                Surface(
                                                    shape = RoundedCornerShape(12.dp),
                                                    color = if (chosen) Color(0x2415803D) else colors.elevatedSurface,
                                                    border = BorderStroke(
                                                        1.dp,
                                                        if (chosen) Color(0xFF22C55E) else colors.border
                                                    ),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable {
                                                            selectedSpotifyTrack = if (chosen) "" else fullTrackLabel
                                                        }
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(horizontal = 10.dp, vertical = 8.dp),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = titleWithIcon,
                                                                style = MaterialTheme.typography.labelMedium,
                                                                fontWeight = FontWeight.Bold,
                                                                color = colors.primaryText,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                            Text(
                                                                text = artistMeta,
                                                                style = MaterialTheme.typography.labelSmall,
                                                                color = colors.secondaryText,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                        }
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Surface(
                                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                                            color = if (chosen) Color(0xFF16A34A) else colors.surface,
                                                            border = BorderStroke(
                                                                1.dp,
                                                                if (chosen) Color(0xFF22C55E) else colors.border
                                                            )
                                                        ) {
                                                            Text(
                                                                text = if (chosen) "● Đang gắn" else statusLabel,
                                                                style = MaterialTheme.typography.labelSmall,
                                                                color = if (chosen) Color.White else colors.secondaryText,
                                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        JourneyTextField(
                                            value = selectedSpotifyTrack,
                                            onValueChange = { selectedSpotifyTrack = it },
                                            label = "Tên bài hát / Link Spotify",
                                            placeholder = "Hoặc nhập tên bài hát - nghệ sĩ / dán link Spotify..."
                                        )
                                    }
                                }

                                "STICKER" -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "✨ Chọn nhãn dán cảm xúc",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText
                                        )
                                        if (selectedSticker.isNotBlank()) {
                                            Text(
                                                text = "✕ Bỏ nhãn",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = colors.secondaryText,
                                                modifier = Modifier.clickable { selectedSticker = "" }
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        stickerOptions.forEach { st ->
                                            val chosen = selectedSticker == st
                                            Surface(
                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                color = if (chosen) colors.accent else colors.elevatedSurface,
                                                border = BorderStroke(1.dp, if (chosen) colors.accent else colors.border),
                                                modifier = Modifier.clickable {
                                                    selectedSticker = if (chosen) "" else st
                                                }
                                            ) {
                                                Text(
                                                    text = st,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = if (chosen) colors.onAccent else colors.primaryText,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                "TEMP" -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "🌡️ Nhiệt độ & Thời tiết hiện tại",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText
                                        )
                                        Text(
                                            text = if (enableTempOverlay) "✕ Ẩn nhiệt độ" else "✓ Hiện nhiệt độ",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = colors.accent,
                                            modifier = Modifier.clickable {
                                                enableTempOverlay = !enableTempOverlay
                                            }
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        presetTemps.forEach { t ->
                                            val chosen = enableTempOverlay && selectedTempText == t
                                            Surface(
                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                color = if (chosen) colors.accent else colors.elevatedSurface,
                                                border = BorderStroke(1.dp, if (chosen) colors.accent else colors.border),
                                                modifier = Modifier.clickable {
                                                    selectedTempText = t
                                                    enableTempOverlay = true
                                                }
                                            ) {
                                                Text(
                                                    text = t,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = if (chosen) colors.onAccent else colors.primaryText,
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

                // 3. Caption Input + Controls (Post or Shutter)
                if (isPhotoCaptured) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            JourneyTextField(
                                value = captionText,
                                onValueChange = { captionText = it },
                                label = tr(lang, "Caption", "Ghi chú cho khoảnh khắc"),
                                placeholder = tr(
                                    lang,
                                    "Write a note...",
                                    "Viết cảm nghĩ của bạn..."
                                ),
                                testTag = "locket_caption_input"
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf(
                                    "PUBLIC" to "🌎 Tất cả bạn bè",
                                    "CUSTOM" to "👥 Chọn bạn",
                                    "PRIVATE" to "🔒 Chỉ mình tôi"
                                ).forEach { (modeKey, label) ->
                                    val selected = privacyMode == modeKey
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (selected) colors.sage.copy(alpha = 0.16f) else colors.elevatedSurface,
                                        border = BorderStroke(1.dp, if (selected) colors.sage else colors.border),
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { privacyMode = modeKey }
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (selected) colors.sage else colors.secondaryText,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }

                            if (privacyMode == "CUSTOM") {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    allFriends.forEach { friendName ->
                                        val isChosen = selectedFriends.contains(friendName)
                                        Surface(
                                            shape = RoundedCornerShape(JourneyRadius.pill),
                                            color = if (isChosen) colors.accent else colors.elevatedSurface,
                                            border = BorderStroke(1.dp, if (isChosen) colors.accent else colors.border),
                                            modifier = Modifier.clickable {
                                                selectedFriends = if (isChosen && selectedFriends.size > 1) {
                                                    selectedFriends - friendName
                                                } else {
                                                    selectedFriends + friendName
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = "${if (isChosen) "✓" else "+"} $friendName",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (isChosen) colors.onAccent else colors.primaryText,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                JourneyButton(
                                    text = tr(lang, "Retake", "🔄 Chụp lại"),
                                    variant = JourneyButtonVariant.SECONDARY,
                                    onClick = {
                                        isPhotoCaptured = false
                                        isVideoMode = false
                                        recordedSeconds = 0
                                        onImagePicked(null)
                                    },
                                    modifier = Modifier.weight(1f),
                                    testTag = "locket_retake_btn"
                                )
                                JourneyButton(
                                    text = tr(lang, "🚀 Post Moment", "🚀 Đăng khoảnh khắc"),
                                    variant = JourneyButtonVariant.PRIMARY,
                                    onClick = {
                                        val finalDuration = if (isVideoMode) recordedSeconds.coerceIn(5, 10) else 0
                                        onPublishLocketPost(
                                            captionText,
                                            if (enableLocationOverlay && locationText.isNotBlank()) fallbackPlace.id else "",
                                            uiState.capturedImageUri,
                                            computedPrivacyLabel,
                                            isVideoMode,
                                            finalDuration,
                                            locationText.takeIf { enableLocationOverlay && it.isNotBlank() } ?: "",
                                            currentTimeText.takeIf { enableTimeOverlay } ?: "",
                                            selectedTempText.takeIf { enableTempOverlay } ?: "",
                                            selectedSticker,
                                            selectedSpotifyTrack
                                        )
                                        captionText = ""
                                        isPhotoCaptured = false
                                        isVideoMode = false
                                        recordedSeconds = 0
                                    },
                                    modifier = Modifier.weight(1.5f),
                                    testTag = "locket_publish_btn"
                                )
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: Pick Photo from Gallery
                        IconButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier
                                .size(52.dp)
                                .background(colors.surface, CircleShape)
                                .border(1.dp, colors.border, CircleShape)
                                .testTag("camera_gallery_picker_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Chọn ảnh từ máy",
                                tint = colors.primaryText
                            )
                        }

                        // Center: Instant Shutter Button (Tap = Photo, Hold = Short Video)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(82.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 4.dp,
                                    color = if (isRecordingVideo) Color(0xFFEF4444) else colors.accent,
                                    shape = CircleShape
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            if (!currentHasCameraPermission && !currentBypassGate) {
                                                cameraAndMicPermissionLauncher.launch(
                                                    arrayOf(
                                                        Manifest.permission.CAMERA,
                                                        Manifest.permission.RECORD_AUDIO
                                                    )
                                                )
                                            } else {
                                                currentPerformCapture(null)
                                            }
                                        },
                                        onLongPress = {
                                            if (!currentHasCameraPermission && !currentBypassGate) {
                                                cameraAndMicPermissionLauncher.launch(
                                                    arrayOf(
                                                        Manifest.permission.CAMERA,
                                                        Manifest.permission.RECORD_AUDIO
                                                    )
                                                )
                                            } else {
                                                currentTimeText = SimpleDateFormat("HH:mm · dd/MM", Locale.getDefault()).format(Date())
                                                isRecordingVideo = true
                                            }
                                        },
                                        onPress = {
                                            tryAwaitRelease()
                                            if (isRecordingVideo) {
                                                isRecordingVideo = false
                                                if (recordedSeconds < 5) recordedSeconds = 5
                                                currentPerformCapture(null)
                                                isVideoMode = true
                                            }
                                        }
                                    )
                                }
                                .padding(7.dp)
                                .background(
                                    if (isRecordingVideo) Color(0xFFEF4444) else colors.primaryText,
                                    CircleShape
                                )
                                .testTag("camera_shutter_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Chạm chụp ảnh hoặc ấn giữ quay video",
                                tint = colors.primaryBackground,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Right: Flip Front/Back Camera
                        IconButton(
                            onClick = {
                                useFrontCamera = !useFrontCamera
                            },
                            modifier = Modifier
                                .size(52.dp)
                                .background(colors.surface, CircleShape)
                                .border(1.dp, colors.border, CircleShape)
                                .testTag("camera_flip_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Đổi camera trước / sau",
                                tint = colors.accent
                            )
                        }
                    }

                    Text(
                        text = tr(
                            lang,
                            "Tap to snap · Hold to record short video",
                            "Chạm để chụp · Ấn giữ để quay video ngắn"
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.secondaryText
                    )
                }
            }
        }
    }
}

// ============================================================================
// D2: IMAGE PREVIEW (Displays gallery-picked URI via Coil or captured frame)
// ============================================================================
@Composable
fun ImagePreviewScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D2_IMAGE_PREVIEW,
            language = lang,
            onBackClick = onBack
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(JourneyRadius.xl))
                .border(1.5.dp, colors.accent, RoundedCornerShape(JourneyRadius.xl))
        ) {
            if (uiState.capturedImageUri != null) {
                AsyncImage(
                    model = uiState.capturedImageUri,
                    contentDescription = place.nameEn,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = place.imageRes),
                    contentDescription = place.nameEn,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            JourneyBadgePill(
                text = tr(lang, "FRAME VERIFIED • EXIF GPS EMBEDDED", "KHUNG HÌNH HỢP LỆ • ĐÃ GẮN TỌA ĐỘ EXIF"),
                containerColor = Color(0xCC111923),
                contentColor = JourneyPalette.SandGold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(JourneySpacing.md)
            )
        }

        Spacer(modifier = Modifier.height(JourneySpacing.lg))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            JourneyButton(
                text = tr(lang, "Retake Photo", "Chụp lại"),
                leadingIcon = Icons.Default.Refresh,
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.D1_AI_CAMERA) },
                modifier = Modifier.weight(1f)
            )
            JourneyButton(
                text = tr(lang, "Analyze Monument", "Phân tích AI"),
                leadingIcon = Icons.Default.Check,
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.D3_AI_PROCESSING) },
                modifier = Modifier.weight(1f),
                testTag = "preview_analyze_btn"
            )
        }
    }
}

// ============================================================================
// D3: AI PROCESSING
// ============================================================================
@Composable
fun AiProcessingScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D3_AI_PROCESSING,
            language = lang,
            onBackClick = onBack
        )

        Spacer(modifier = Modifier.height(JourneySpacing.lg))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .border(2.dp, colors.accent, CircleShape)
        ) {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.nameEn,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99111923))
            )
            CircularProgressIndicator(
                color = colors.accent,
                strokeWidth = 4.dp,
                modifier = Modifier.size(190.dp)
            )
            Text(
                text = "${(place.confidenceScore * 100).toInt()}%",
                style = MaterialTheme.typography.displayMedium,
                color = JourneyPalette.Ivory
            )
        }

        Spacer(modifier = Modifier.height(JourneySpacing.xl))

        JourneyBadgePill(
            text = tr(lang, "AI VISION PIPELINE", "QUY TRÌNH THỊ GIÁC AI")
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = tr(lang, "Matching Architectural & Historical Signatures...", "Đang Đối Chiếu Dấu Ấn Kiến Trúc & Niên Đại..."),
            style = MaterialTheme.typography.headlineLarge,
            color = colors.primaryText
        )
        Spacer(modifier = Modifier.height(JourneySpacing.md))

        Surface(
            shape = RoundedCornerShape(JourneyRadius.lg),
            color = colors.surface,
            border = BorderStroke(1.dp, colors.border),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                listOf(
                    "1. Spatial EXIF & PostGIS Coordinate Lock (${place.coordinates})" to "100%",
                    "2. Timber / Limestone Structural Feature Extraction" to "100%",
                    "3. Vietnam National Heritage Archive Cross-Reference" to "98%"
                ).forEach { (step, pct) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = step, style = MaterialTheme.typography.bodySmall, color = colors.primaryText, modifier = Modifier.weight(1f))
                        Text(text = pct, style = MaterialTheme.typography.labelSmall, color = colors.accent)
                    }
                    LinearProgressIndicator(
                        progress = { 0.98f },
                        color = colors.accent,
                        trackColor = colors.subtleBorder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(JourneySpacing.xl))
        JourneyButton(
            text = tr(lang, "View Recognition Result", "Xem Kết quả Nhận diện"),
            variant = JourneyButtonVariant.PRIMARY,
            onClick = { onNavigate(JourneyScreenId.D4_RECOGNITION_RESULT) },
            modifier = Modifier.fillMaxWidth(),
            testTag = "processing_view_result_btn"
        )
    }
}

// ============================================================================
// D4: RECOGNITION RESULT
// ============================================================================
@Composable
fun RecognitionResultScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit,
    onSaveToCollection: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_d4_recognition_result")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D4_RECOGNITION_RESULT,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyBadgePill(
                text = tr(
                    lang,
                    "RECOGNITION MATCH • ${(place.confidenceScore * 100).toInt()}% CONFIDENCE",
                    "KẾT QUẢ NHẬN DIỆN • ĐỘ CHÍNH XÁC ${(place.confidenceScore * 100).toInt()}%"
                ),
                icon = Icons.Default.Verified
            )
            Spacer(modifier = Modifier.height(10.dp))

            JourneyEditorialCard(
                imageRes = place.imageRes,
                tagText = tr(lang, place.eraEn, place.eraVi),
                title = tr(lang, place.nameEn, place.nameVi),
                subtitle = tr(lang, place.summaryEn, place.summaryVi),
                metaText = "${place.coordinates} • ${tr(lang, place.regionEn, place.regionVi)}",
                isSaved = place.isSaved,
                onCardClick = { onNavigate(JourneyScreenId.D5_DISCOVERY_DETAILS) },
                heightDp = 270
            )

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Text(
                text = tr(lang, "CONTINUE YOUR DISCOVERY", "TIẾP TỤC HÀNH TRÌNH KHÁM PHÁ"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Deep History", "Chiều sâu Lịch sử"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.D5_DISCOVERY_DETAILS) },
                    modifier = Modifier.weight(1f),
                    testTag = "result_open_d5_btn"
                )
                JourneyButton(
                    text = tr(lang, "Ask AI Chat", "Hỏi Trợ lý AI"),
                    leadingIcon = Icons.Default.AutoAwesome,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                    modifier = Modifier.weight(1f),
                    testTag = "result_open_e1_btn"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Voice Guide", "Nghe Thuyết minh"),
                    leadingIcon = Icons.Default.GraphicEq,
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Save to Collection", "Lưu Bộ sưu tập"),
                    leadingIcon = Icons.Default.CollectionsBookmark,
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = {
                        val firstColl = uiState.collections.first().id
                        onSaveToCollection(firstColl)
                    },
                    modifier = Modifier.weight(1f),
                    testTag = "result_save_collection_btn"
                )
            }
        }
    }
}

// ============================================================================
// D5: DISCOVERY DETAILS
// ============================================================================
@Composable
fun DiscoveryDetailsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D5_DISCOVERY_DETAILS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "MONOGRAPH DOSSIER • ${place.coordinates}", "HỒ SƠ CHUYÊN KHẢO • ${place.coordinates}"),
                title = tr(lang, place.nameEn, place.nameVi)
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
                        text = tr(lang, "1. CHRONOLOGY & DYNASTIC CONTEXT", "1. NIÊN BIỂU & BỐI CẢNH TRIỀU ĐẠI"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.eraEn, place.eraVi),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.primaryText
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.summaryEn, place.summaryVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = tr(lang, "2. STRUCTURAL & ARTISANAL ANATOMY", "2. GIẢI PHẪU KIẾN TRÚC & CHẠM KHẮC"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.architecturalNotesEn, place.architecturalNotesVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = tr(lang, "3. INTANGIBLE FOLKLORE & SPIRITUALITY", "3. TÍN NGƯỠNG & DI SẢN PHI VẬT THỂ"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                JourneyButton(
                    text = tr(lang, "Log in Travel Journal", "Ghi vào Nhật ký"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.F5_CREATE_DAILY_MOMENT) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Discuss with AI", "Trò chuyện AI"),
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ============================================================================
// E1: AI CONVERSATIONAL ASSISTANT (With Real Microphone Speech-to-Text Input)
// ============================================================================
@Composable
fun AiChatScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit,
    onSaveConversation: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val context = LocalContext.current
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var draftText by remember { mutableStateOf("") }
    var isListeningMic by remember { mutableStateOf(false) }

    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        isListeningMic = granted
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp)
            .testTag("screen_e1_ai_chat")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.E1_AI_CHAT,
            language = lang,
            onBackClick = onBack,
            trailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    JourneyButton(
                        text = tr(lang, "Voice", "Giọng nói"),
                        leadingIcon = Icons.Default.Mic,
                        variant = JourneyButtonVariant.SECONDARY,
                        onClick = { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) },
                        testTag = "chat_to_voice_btn"
                    )
                    JourneyButton(
                        text = tr(lang, "Save", "Lưu lại"),
                        leadingIcon = Icons.Default.BookmarkAdd,
                        variant = JourneyButtonVariant.SURFACE_TONAL,
                        onClick = onSaveConversation,
                        testTag = "chat_save_conversation_btn"
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = JourneySpacing.md),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            JourneyBadgePill(
                text = tr(
                    lang,
                    "ACTIVE MONUMENT: ${uiState.selectedPlace.nameEn}",
                    "ĐỊA DANH ĐANG MỞ: ${uiState.selectedPlace.nameVi}"
                )
            )

            uiState.chatMessages.forEach { msg ->
                val isUser = msg.isUser
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isUser) 18.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 18.dp
                        ),
                        color = if (isUser) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (isUser) colors.accent else colors.border),
                        modifier = Modifier.fillMaxWidth(0.86f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isUser) tr(lang, "YOU • ${msg.timestamp}", "BẠN • ${msg.timestamp}")
                                else "JOURNEY AI SCHOLAR • ${msg.timestamp}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isUser) colors.onAccent.copy(alpha = 0.75f) else colors.accent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tr(lang, msg.textEn, msg.textVi),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isUser) colors.onAccent else colors.primaryText
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            Row(
                modifier = Modifier.padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    tr(lang, "Decode roof symbolism", "Giải mã mái ngói âm dương"),
                    tr(lang, "Best golden hour photo angle?", "Góc chụp hoàng hôn đẹp nhất?")
                ).forEach { prompt ->
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier.clickable { onSendMessage(prompt) }
                    ) {
                        Text(
                            text = prompt,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.accent,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        val granted = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                        if (!granted) {
                            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        } else {
                            draftText = if (lang == AppLanguage.EN) {
                                "What is the historical origin of ${uiState.selectedPlace.nameEn}?"
                            } else {
                                "Nguồn gốc lịch sử của ${uiState.selectedPlace.nameVi} là gì?"
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Input",
                        tint = if (isListeningMic) colors.accent else colors.secondaryText
                    )
                }

                JourneyTextField(
                    value = draftText,
                    onValueChange = { draftText = it },
                    label = tr(lang, "Ask about history, architecture, or local lore...", "Đặt câu hỏi về lịch sử, kiến trúc hoặc văn hóa..."),
                    placeholder = tr(lang, "Type a question...", "Nhập câu hỏi..."),
                    modifier = Modifier.weight(1f),
                    testTag = "chat_message_input"
                )
                IconButton(
                    onClick = {
                        onSendMessage(draftText)
                        draftText = ""
                    },
                    modifier = Modifier
                        .size(52.dp)
                        .background(colors.accent, CircleShape)
                        .testTag("chat_send_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = colors.onAccent
                    )
                }
            }
        }
    }
}

// ============================================================================
// E2: REAL TEXT-TO-SPEECH (TTS) & MICROPHONE AI VOICE TRAVEL GUIDE
// ============================================================================
@Composable
fun VoiceAssistantScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onTogglePlay: () -> Unit,
    onCycleSpeed: () -> Unit,
    onSaveConversation: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val context = LocalContext.current
    val lang = uiState.language
    val place = uiState.selectedPlace

    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context, lang) {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val targetLocale = if (lang == AppLanguage.VI) Locale("vi", "VN") else Locale.US
                engine?.language = targetLocale
                engine?.setSpeechRate(uiState.voiceSpeedMultiplier)
                if (uiState.isVoicePlaying) {
                    val narration = if (lang == AppLanguage.VI) place.culturalStoryVi else place.culturalStoryEn
                    engine?.speak(narration, TextToSpeech.QUEUE_FLUSH, null, "journey_narration")
                }
            }
        }
        ttsEngine = engine
        onDispose {
            engine?.stop()
            engine?.shutdown()
        }
    }

    LaunchedEffect(uiState.isVoicePlaying, uiState.voiceSpeedMultiplier, lang) {
        ttsEngine?.setSpeechRate(uiState.voiceSpeedMultiplier)
        if (uiState.isVoicePlaying) {
            val narration = if (lang == AppLanguage.VI) place.culturalStoryVi else place.culturalStoryEn
            ttsEngine?.speak(narration, TextToSpeech.QUEUE_FLUSH, null, "journey_narration")
        } else {
            ttsEngine?.stop()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "voice_wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        JourneyPalette.Midnight,
                        JourneyPalette.DeepObsidian
                    )
                )
            )
            .padding(JourneySpacing.lg)
            .testTag("screen_e2_voice_assistant")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(44.dp)
                        .background(JourneyPalette.Slate, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = JourneyPalette.Ivory
                    )
                }
                JourneyBadgePill(
                    text = "BINAURAL SPATIAL VOICE GUIDE",
                    containerColor = JourneyPalette.Slate,
                    contentColor = JourneyPalette.SandGold
                )
                IconButton(
                    onClick = { onNavigate(JourneyScreenId.E3_CONVERSATION_HISTORY) },
                    modifier = Modifier
                        .size(44.dp)
                        .background(JourneyPalette.Slate, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History",
                        tint = JourneyPalette.SandGold
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = place.coordinates,
                    style = MaterialTheme.typography.labelMedium,
                    color = JourneyPalette.SandGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tr(lang, place.nameEn, place.nameVi),
                    style = MaterialTheme.typography.displaySmall,
                    color = JourneyPalette.Ivory
                )
                Spacer(modifier = Modifier.height(24.dp))

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                ) {
                    val barCount = 32
                    val barWidth = size.width / (barCount * 2f)
                    val centerY = size.height / 2f
                    for (i in 0 until barCount) {
                        val wave = if (uiState.isVoicePlaying) {
                            (sin(phase + i * 0.35f) * 0.5f + 0.5f)
                        } else 0.15f
                        val barHeight = (20f + wave * (size.height * 0.75f))
                        val x = (i * 2 + 0.5f) * barWidth
                        drawLine(
                            color = if (i % 3 == 0) JourneyPalette.SandGold else JourneyPalette.Ivory.copy(alpha = 0.7f),
                            start = Offset(x, centerY - barHeight / 2f),
                            end = Offset(x, centerY + barHeight / 2f),
                            strokeWidth = barWidth * 0.85f,
                            cap = StrokeCap.Round
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = JourneyPalette.Slate.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, JourneyPalette.SandGold.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        Text(
                            text = tr(lang, "LIVE NARRATION TRANSCRIPT", "PHỤ ĐỀ THUYẾT MINH TRỰC TIẾP"),
                            style = MaterialTheme.typography.labelSmall,
                            color = JourneyPalette.SandGold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                            style = MaterialTheme.typography.headlineMedium,
                            color = JourneyPalette.Ivory
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JourneyButton(
                        text = "${uiState.voiceSpeedMultiplier}x",
                        variant = JourneyButtonVariant.SECONDARY,
                        onClick = onCycleSpeed
                    )

                    IconButton(
                        onClick = onTogglePlay,
                        modifier = Modifier
                            .size(72.dp)
                            .background(JourneyPalette.SandGold, CircleShape)
                            .testTag("voice_play_pause_btn")
                    ) {
                        Icon(
                            imageVector = if (uiState.isVoicePlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause Voice Guide",
                            tint = JourneyPalette.Midnight,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    JourneyButton(
                        text = tr(lang, "Save Audio Note", "Lưu Bản ghi"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = onSaveConversation,
                        testTag = "voice_save_conversation_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// E3: CONVERSATION HISTORY
// ============================================================================
@Composable
fun ConversationHistoryScreen(
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
            .padding(bottom = 28.dp)
            .testTag("screen_e3_conversation_history")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.E3_CONVERSATION_HISTORY,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "SCHOLAR ARCHIVE • ${uiState.savedConversations.size} SESSIONS", "LƯU TRỮ HỘI THOẠI • ${uiState.savedConversations.size} PHIÊN"),
                title = tr(lang, "Saved AI Conversations & Field Notes", "Lịch Sử Trò Chuyện & Ghi Chú Thực Địa")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            uiState.savedConversations.forEach { conv ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onNavigate(JourneyScreenId.E1_AI_CHAT) }
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            JourneyBadgePill(text = conv.locationTag)
                            Text(
                                text = "${conv.dateLabel} • ${conv.messagesCount} msgs",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.secondaryText
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tr(lang, conv.titleEn, conv.titleVi),
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.primaryText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tr(lang, conv.previewEn, conv.previewVi),
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.secondaryText
                        )
                    }
                }
            }
        }
    }
}
