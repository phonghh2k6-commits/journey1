package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ui.theme.JourneyElevation
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme

// ============================================================================
// JOURNEY AI — CORE COMPONENT LIBRARY (Components.kt)
// Implements Primary, Secondary, Ghost Buttons, Minimalist Inputs,
// Filter Chips, Metric Stat Blocks, and Editorial Cards for Phase 1 & Phase 2
// ============================================================================

@Composable
fun JourneyPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String = "journey_primary_btn"
) {
    JourneyButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = JourneyButtonVariant.PRIMARY,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        testTag = testTag
    )
}

@Composable
fun JourneySecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String = "journey_secondary_btn"
) {
    JourneyButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = JourneyButtonVariant.SECONDARY,
        leadingIcon = leadingIcon,
        enabled = enabled,
        testTag = testTag
    )
}

@Composable
fun JourneyGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String = "journey_ghost_btn"
) {
    JourneyButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = JourneyButtonVariant.GHOST,
        leadingIcon = leadingIcon,
        enabled = enabled,
        testTag = testTag
    )
}

@Composable
fun JourneySearchInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailingAction: (@Composable () -> Unit)? = null,
    testTag: String = "journey_search_input_bar"
) {
    val colors = JourneyTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.mutedText
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = placeholder,
                tint = colors.accent,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = trailingAction,
        singleLine = true,
        shape = RoundedCornerShape(JourneyRadius.pill),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colors.surface,
            unfocusedContainerColor = colors.surface.copy(alpha = 0.85f),
            focusedBorderColor = colors.accent,
            unfocusedBorderColor = colors.border,
            focusedTextColor = colors.primaryText,
            unfocusedTextColor = colors.primaryText,
            cursorColor = colors.accent
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
fun JourneyFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    testTag: String = "journey_filter_chip"
) {
    val colors = JourneyTheme.colors
    Surface(
        shape = RoundedCornerShape(JourneyRadius.pill),
        color = if (selected) colors.accent else colors.surface,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) colors.accent else colors.border
        ),
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(JourneyRadius.pill))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = label,
                    tint = if (selected) colors.onAccent else colors.accent,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) colors.onAccent else colors.primaryText
            )
        }
    }
}

@Composable
fun JourneyCompactLandmarkRowCard(
    @DrawableRes imageRes: Int,
    name: String,
    region: String,
    era: String,
    coordinates: String,
    distance: String,
    audioDuration: String,
    isSaved: Boolean,
    onClick: () -> Unit,
    onSaveClick: () -> Unit,
    onAudioClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "compact_landmark_row"
) {
    val colors = JourneyTheme.colors
    Surface(
        shape = RoundedCornerShape(JourneyRadius.lg),
        color = colors.surface,
        border = BorderStroke(1.dp, colors.border),
        shadowElevation = JourneyElevation.subtle,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(JourneyRadius.lg))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(JourneySpacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(JourneyRadius.md))
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(Color(0xCC111923))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = distance,
                        style = MaterialTheme.typography.labelSmall,
                        color = JourneyPalette.SandGold
                    )
                }
            }

            Spacer(modifier = Modifier.width(JourneySpacing.md))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = era.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.accent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.primaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = colors.sage,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = region,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.secondaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = colors.accentContainer,
                        modifier = Modifier.clickable(onClick = onAudioClick)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = "Audio Guide",
                                tint = colors.accent,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = audioDuration,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent
                            )
                        }
                    }
                    Text(
                        text = coordinates,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.mutedText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(
                onClick = onSaveClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save Landmark",
                    tint = if (isSaved) colors.accent else colors.secondaryText
                )
            }
        }
    }
}

@Composable
fun JourneyStatPillCard(
    value: String,
    label: String,
    sublabel: String,
    modifier: Modifier = Modifier
) {
    val colors = JourneyTheme.colors
    Surface(
        shape = RoundedCornerShape(JourneyRadius.lg),
        color = colors.surface,
        border = BorderStroke(1.dp, colors.border),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.displaySmall,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = colors.primaryText
            )
            Text(
                text = sublabel,
                style = MaterialTheme.typography.labelSmall,
                color = colors.secondaryText
            )
        }
    }
}
