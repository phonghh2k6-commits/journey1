package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Journey AI", appName)
  }

  @Test
  fun `verify JourneyViewModel default state and moment synchronization`() {
    val vm = com.example.viewmodel.JourneyViewModel()
    assertEquals(true, vm.uiState.value.isDarkTheme)
    assertEquals("Bích An", vm.uiState.value.userName)

    vm.publishLocketPost(
      caption = "Bình minh Vịnh Hạ Long",
      placeId = "place_halong",
      imageUri = null,
      customLocationName = "Vịnh Hạ Long",
      spotifyTrack = "Đừng Làm Trái Tim Anh Đau - Sơn Tùng M-TP"
    )

    val createdPost = vm.uiState.value.socialPosts.first()
    assertEquals("Đừng Làm Trái Tim Anh Đau - Sơn Tùng M-TP", createdPost.spotifyTrack)

    vm.deleteMoment(createdPost.id)
    assertEquals(0, vm.uiState.value.socialPosts.count { it.id == createdPost.id })
  }
}
