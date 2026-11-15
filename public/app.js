/**
 * JOURNEY AI — Mobile-First Social Travel, Locket Camera (1-Tap Photo & 5-10s Hold Video) & Vietnam Map
 * 5 Clear Tabs:
 *  1. Trang chủ (home): Xem Tin (Stories) & Bài đăng của bạn bè hoặc của mình (hỗ trợ Quyền riêng tư & Xóa đồng bộ)
 *  2. Khám phá (explore): Bản đồ Việt Nam những nơi đã đi / đã đến (OpenStreetMap chuẩn, kèm Hoàng Sa & Trường Sa)
 *  3. Ống kính (discover): Chụp ảnh 1 chạm hoặc Ấn giữ 5-10s quay video ngắn kiểu Locket + Chọn quyền riêng tư / người xem
 *  4. Nhật ký (journal): Những bức ảnh / video & địa điểm mình đã check-in (Xóa đồng bộ với Trang chủ)
 *  5. Hồ sơ (profile): Giao diện thẻ Hồ sơ hiện đại, thay đổi Avatar, Tên hiển thị & Thông tin cá nhân
 */

const DESTINATIONS = {
  sapa: {
    id: 'sapa',
    rank: '#04 TRENDING',
    nameVi: 'Ruộng Bậc Thang Sa Pa',
    nameEn: 'Sa Pa',
    province: 'Lào Cai',
    categoryVi: 'Kiệt tác Tây Bắc',
    rating: '4.9',
    visitedDate: '19/10/2026',
    coords: '22.3364° N, 103.8438° E',
    lat: 22.3364,
    lng: 103.8438,
    pinIcon: '🌾',
    image: 'images/img_sapa_terraces.jpg',
    shortDescVi:
      'Những nấc thang vàng óng chạm mây do bàn tay người H’Mông và Dao kiến tạo trên sườn núi Hoàng Liên Sơn.',
    storyVi:
      'Trên những sườn núi dốc đứng ở độ cao hơn 1.500 mét tại Lào Cai, đồng bào H’Mông và Dao Đỏ đã biến địa hình hiểm trở thành những đường vân sóng lúa uốn lượn tuyệt mỹ.'
  },
  hanoi_temple: {
    id: 'hanoi_temple',
    rank: '#03 TRENDING',
    nameVi: 'Văn Miếu – Quốc Tử Giám',
    nameEn: 'Temple of Literature',
    province: 'Hà Nội',
    categoryVi: 'Di sản Thủ đô',
    rating: '4.8',
    visitedDate: '28/10/2026',
    coords: '21.0278° N, 105.8355° E',
    lat: 21.0278,
    lng: 105.8355,
    pinIcon: '⛩️',
    image: 'images/img_hanoi_temple.jpg',
    shortDescVi:
      'Được xây dựng năm 1070, biểu tượng ngàn năm của truyền thống hiếu học với Khuê Văn Các và 82 bia Tiến sĩ.',
    storyVi:
      'Được khởi dựng vào năm 1070 dưới triều vua Lý Thánh Tông tại Hà Nội, Văn Miếu – Quốc Tử Giám là trường đại học quốc gia đầu tiên của Việt Nam với 82 tấm bia Tiến sĩ đặt trên lưng rùa đá.'
  },
  halong: {
    id: 'halong',
    rank: '#01 TRENDING',
    nameVi: 'Vịnh Hạ Long',
    nameEn: 'Ha Long Bay',
    province: 'Quảng Ninh',
    categoryVi: 'Di sản Thiên nhiên UNESCO',
    rating: '4.9',
    visitedDate: '12/11/2026',
    coords: '20.9101° N, 107.1839° E',
    lat: 20.9101,
    lng: 107.1839,
    pinIcon: '⛵',
    image: 'images/img_hero_halong_bay.jpg',
    shortDescVi:
      'Hơn 1.600 đảo đá vôi kỳ vĩ vươn lên từ làn nước ngọc lục bảo, nơi lưu giữ dấu ấn địa chất 500 triệu năm.',
    storyVi:
      'Vịnh Hạ Long (Quảng Ninh) là kiệt tác kiến tạo địa chất suốt hơn 500 triệu năm với hàng ngàn đảo đá vôi kỳ vĩ trên vùng biển Đông Bắc Việt Nam.'
  },
  hoian: {
    id: 'hoian',
    rank: '#02 TRENDING',
    nameVi: 'Phố Cổ Hội An',
    nameEn: 'Hoi An',
    province: 'Quảng Nam',
    categoryVi: 'Thương cảng Di sản Cổ',
    rating: '4.9',
    visitedDate: '05/11/2026',
    coords: '15.8801° N, 108.3380° E',
    lat: 15.8801,
    lng: 108.338,
    pinIcon: '🏮',
    image: 'images/img_hoian_lanterns.jpg',
    shortDescVi:
      'Thương cảng quốc tế thế kỷ XVI–XVII với những dãy nhà tường vàng cổ kính và sông Hoài rực rỡ đèn lồng lụa.',
    storyVi:
      'Từ thế kỷ XVI đến XVII, Phố cổ Hội An (Quảng Nam) từng là thương cảng sầm uất bậc nhất Đông Nam Á. Khi màn đêm buông xuống, hàng ngàn chiếc đèn lồng thủ công thắp sáng dòng sông Hoài.'
  }
};

const VIETNAM_PRESET_PLACES = [
  { nameVi: 'Cầu Rồng — Đà Nẵng', province: 'Đà Nẵng', lat: 16.0611, lng: 108.2272, pinIcon: '🌉' },
  { nameVi: 'Kinh Thành Huế', province: 'Thừa Thiên Huế', lat: 16.4698, lng: 107.5784, pinIcon: '🏯' },
  { nameVi: 'Tràng An — Ninh Bình', province: 'Ninh Bình', lat: 20.2506, lng: 105.9745, pinIcon: '🚣' },
  { nameVi: 'Quảng Trường Lâm Viên — Đà Lạt', province: 'Lâm Đồng', lat: 11.9404, lng: 108.4368, pinIcon: '🌲' },
  { nameVi: 'Bến Nhà Rồng — TP. Hồ Chí Minh', province: 'TP. Hồ Chí Minh', lat: 10.7683, lng: 106.7068, pinIcon: '🏙️' },
  { nameVi: 'Đảo Ngọc Phú Quốc', province: 'Kiên Giang', lat: 10.227, lng: 103.9632, pinIcon: '🏝️' }
];

const FRIENDS_LIST = [];
const FRIEND_STORIES = {};
const DEFAULT_POSTS = [];
const DEFAULT_CHECKINS = [];

class JourneyAppController {
  constructor() {
    this.activeTab = 'home';
    this.feedFilter = 'all'; // 'all' | 'friends' | 'mine'
    this.theme = localStorage.getItem('journey_theme') || 'dark';

    // Automatically purge legacy demo records saved in localStorage from previous sessions
    this.purgeLegacyDemoData();

    const savedProfile = this.loadJson('journey_user_profile', null);
    this.profile = savedProfile || {
      name: 'Bích An',
      username: '@journey.me',
      bio: 'Lưu giữ khoảnh khắc, mở rộng thế giới của riêng bạn 🇻🇳',
      city: 'Hà Nội, Việt Nam',
      email: 'bachan@gmail.com',
      avatarImg: '',
      avatarText: 'BA'
    };

    this.posts = this.loadJson('journey_feed_posts', DEFAULT_POSTS);
    this.checkins = this.loadJson('journey_user_checkins', DEFAULT_CHECKINS);
    this.customPlaces = this.loadJson('journey_custom_places', []);

    // Reconcile orphaned posts that the user already deleted from check-ins earlier
    this.reconcileDeletedMoments();

    // Locket Camera & Microphone Permission State
    this.cameraStream = null;
    this.cameraFacingMode = 'environment';
    this.cameraPermState = localStorage.getItem('journey_cam_perm') || 'prompt'; // 'prompt' | 'granted' | 'denied'
    this.micPermState = localStorage.getItem('journey_mic_perm') || 'prompt'; // 'prompt' | 'granted' | 'denied'
    this.permissionGateBypassed = false;
    this.capturedDataUrl = null;
    this.capturedVideoUrl = null;
    this.capturedMediaType = 'photo'; // 'photo' | 'video'
    this.capturedVideoDuration = 0;
    this.selectedPlaceId = 'halong';
    this.fallbackPreviewIndex = 0;

    // Optional Camera Overlays State (Location is OFF by default; user decides!)
    this.camAttachLocation = false;
    this.camLocationName = '';
    this.camCoordsLabel = '';
    this.isFetchingCamGps = false;
    this.camEnableTime = true;
    this.camEnableTemp = true;
    this.camTemperatureText = '26°C ☀️';
    this.camSticker = '';
    this.camSpotifyTrack = '';
    this.camActiveWidget = 'none'; // 'none' | 'location' | 'spotify' | 'sticker' | 'temp'

    // Privacy / Audience State
    this.postPrivacy = 'public'; // 'public' | 'custom' | 'private'
    this.selectedFriends = [];

    // Hold-to-record Video State (5-10s)
    this.isRecordingVideo = false;
    this.holdTimer = null;
    this.recordingInterval = null;
    this.recordingStartTime = 0;
    this.mediaRecorder = null;
    this.recordedChunks = [];
    this.recordingAnimFrame = null;

    // Leaflet Map Instance & Multi-Layer State ('standard' | 'satellite' | 'terrain')
    this.leafletMap = null;
    this.leafletMarkers = {};
    this.categoryLeafletMarkers = {
      restaurants: [],
      museums: [],
      nature: []
    };
    this.leafletRouteLine = null;
    this.leafletTileLayers = {};
    this.activeLeafletTileLayer = null;
    this.mapLayerMode = localStorage.getItem('journey_map_layer') || 'standard';
    this.visibleMapCategories = this.loadJson('journey_map_categories', {
      restaurants: true,
      museums: true,
      nature: true
    });

    // Pull-to-Refresh State for Home Feed
    this.isRefreshingFeed = false;
    this.pullStartY = 0;
    this.pullDistance = 0;
    this.isPullingFeed = false;

    // Locket Friends, Invite Link & Direct Messaging State (real user data only)
    this.friendsList = this.loadJson('journey_friends_list', []);
    this.pendingFriendRequests = this.loadJson('journey_friend_requests', []);
    this.chatMessagesByFriend = this.loadJson('journey_locket_chats', {});

    this.init();
  }

  purgeLegacyDemoData() {
    try {
      if (localStorage.getItem('journey_purged_demo_v1') === '1') return;

      const demoPostIds = new Set([
        'moment-default-1',
        'moment-friend-2',
        'moment-default-3',
        'moment-friend-4',
        'moment-default-hoian',
        'moment-default-hanoi'
      ]);
      const demoFriendIds = new Set(['lan_anh', 'hoang_nam', 'thao_tien', 'tuan_kiet']);
      const demoReqIds = new Set(['req_bao_ngoc', 'req_minh_khoi']);

      const rawPosts = this.loadJson('journey_feed_posts', []);
      if (Array.isArray(rawPosts)) {
        const cleanedPosts = rawPosts.filter(
          (p) =>
            p &&
            !demoPostIds.has(p.id) &&
            !demoPostIds.has(p.momentId) &&
            !String(p.id || '').startsWith('friend-live-')
        );
        this.saveJson('journey_feed_posts', cleanedPosts);
      }

      const rawCheckins = this.loadJson('journey_user_checkins', []);
      if (Array.isArray(rawCheckins)) {
        const cleanedCheckins = rawCheckins.filter(
          (c) => c && !demoPostIds.has(c.id) && !demoPostIds.has(c.momentId)
        );
        this.saveJson('journey_user_checkins', cleanedCheckins);
      }

      const rawFriends = this.loadJson('journey_friends_list', []);
      if (Array.isArray(rawFriends)) {
        const cleanedFriends = rawFriends.filter((f) => f && !demoFriendIds.has(f.id));
        this.saveJson('journey_friends_list', cleanedFriends);
      }

      const rawRequests = this.loadJson('journey_friend_requests', []);
      if (Array.isArray(rawRequests)) {
        const cleanedReqs = rawRequests.filter((r) => r && !demoReqIds.has(r.id));
        this.saveJson('journey_friend_requests', cleanedReqs);
      }

      const rawChats = this.loadJson('journey_locket_chats', {});
      if (rawChats && typeof rawChats === 'object') {
        demoFriendIds.forEach((id) => {
          delete rawChats[id];
        });
        this.saveJson('journey_locket_chats', rawChats);
      }

      const rawProfile = this.loadJson('journey_user_profile', null);
      if (rawProfile && rawProfile.email === 'minh.nguyen@journey.ai') {
        localStorage.removeItem('journey_user_profile');
      }

      localStorage.setItem('journey_purged_demo_v1', '1');
    } catch (_e) {
      // Ignore storage errors
    }
  }

  loadJson(key, fallback) {
    try {
      const raw = localStorage.getItem(key);
      return raw ? JSON.parse(raw) : fallback;
    } catch (_e) {
      return fallback;
    }
  }

  saveJson(key, value) {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (_e) {
      // Ignore storage quota errors
    }
  }

  /**
   * Ensures that if a user deleted a photo in Check-ins previously,
   * any corresponding post on Home Feed is also removed so deleted photos never linger.
   */
  reconcileDeletedMoments() {
    const checkinImages = new Set(this.checkins.map((c) => c.image));
    const checkinIds = new Set(this.checkins.map((c) => c.momentId || c.id));

    const beforeCount = this.posts.length;
    this.posts = this.posts.filter((p) => {
      if (!p.isMine) return true;
      const key = p.momentId || p.id;
      if (checkinIds.has(key)) return true;
      if (p.image && checkinImages.has(p.image)) return true;
      return false;
    });

    if (this.posts.length !== beforeCount) {
      this.saveJson('journey_feed_posts', this.posts);
    }
  }

  init() {
    this.applyTheme(this.theme);
    this.syncHeaderProfile();
    this.setupHomePullToRefresh();
    this.renderHomeTab();
    this.updateHeaderUnreadBadge();
    this.checkIncomingInviteUrl();

    // Auto-resume live camera if page visibility changes or exits iOS native video overlay
    document.addEventListener('visibilitychange', () => {
      if (!document.hidden && this.activeTab === 'discover' && !this.capturedDataUrl && !this.capturedVideoUrl) {
        this.ensureVideoPlaying();
      }
    });
  }

  applyTheme(theme) {
    this.theme = theme === 'dark' ? 'dark' : 'light';
    document.documentElement.setAttribute('data-theme', this.theme);
    localStorage.setItem('journey_theme', this.theme);
    const btnTheme = document.getElementById('btnThemeToggle');
    if (btnTheme) {
      btnTheme.textContent = this.theme === 'dark' ? '☀️' : '🌙';
    }
  }

  toggleTheme() {
    this.applyTheme(this.theme === 'dark' ? 'light' : 'dark');
    this.showToast(this.theme === 'dark' ? '🌙 Đã bật giao diện Tối' : '☀️ Đã bật giao diện Sáng');
  }

  getInitials(name) {
    if (!name) return 'MN';
    const parts = name.trim().split(/\s+/);
    if (parts.length === 1) return parts[0].slice(0, 2).toUpperCase();
    return (parts[0][0] + parts[parts.length - 1][0]).toUpperCase();
  }

  syncHeaderProfile() {
    this.profile.avatarText = this.getInitials(this.profile.name);
    const sub = document.getElementById('headerUserSubtitle');
    if (sub) {
      sub.textContent = 'Explore the World. Discover the Stories.';
    }
    const avatarCircle = document.getElementById('headerAvatarCircle');
    if (avatarCircle) {
      if (this.profile.avatarImg) {
        avatarCircle.innerHTML = `<img src="${this.profile.avatarImg}" alt="${this.profile.name}" />`;
      } else {
        avatarCircle.textContent = this.profile.avatarText;
      }
    }
  }

  switchTab(tabId) {
    if (this.activeTab === 'discover' && tabId !== 'discover') {
      this.stopHoldVideoRecording(true);
      this.stopCameraStream();
    }
    this.activeTab = tabId;

    document.querySelectorAll('.nav-tab').forEach((btn) => {
      btn.classList.toggle('active', btn.getAttribute('data-tab') === tabId);
    });

    const viewport = document.getElementById('mainViewport');
    if (viewport) viewport.scrollTop = 0;

    if (tabId === 'home') {
      this.renderHomeTab();
    } else if (tabId === 'explore') {
      this.renderExploreTab();
    } else if (tabId === 'discover') {
      this.renderLocketCameraTab();
    } else if (tabId === 'journal') {
      this.renderJournalTab();
    } else if (tabId === 'profile') {
      this.renderProfileTab();
    }
  }

  getPrivacyBadgeHtml(post) {
    const privacy = post.privacy || 'public';
    if (privacy === 'private') {
      return '<span class="feed-privacy-pill">🔒 Chỉ mình tôi</span>';
    }
    if (privacy === 'custom') {
      const list =
        post.allowedFriends && post.allowedFriends.length > 0
          ? post.allowedFriends.join(', ')
          : 'Bạn bè đã chọn';
      return `<span class="feed-privacy-pill" title="Chia sẻ riêng với: ${list}">👥 Chỉ: ${list}</span>`;
    }
    return '<span class="feed-privacy-pill">🌎 Tất cả bạn bè</span>';
  }

  /* =========================================================================
     TAB 1: TRANG CHỦ — XEM TIN (STORIES) & BÀI ĐĂNG BẠN BÈ / CỦA MÌNH
     ========================================================================= */
  renderHomeTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const filteredPosts = this.posts.filter((p) => {
      if (this.feedFilter === 'mine') return p.isMine;
      if (this.feedFilter === 'friends') return !p.isMine;
      return true;
    });

    const storiesHtml = this.posts
      .slice(0, 8)
      .map(
        (post) => `
        <div class="story-item" onclick="window.journeyApp.openStoryModal('${post.id}')">
          <div class="story-ring">
            <div class="story-avatar">
              <img src="${post.image}" alt="${post.isMine ? this.profile.name : post.authorName}" />
            </div>
          </div>
          <span class="story-name">${post.isMine ? 'Bạn' : post.authorName}</span>
        </div>
      `
      )
      .join('');

    const postsHtml = filteredPosts
      .map((post) => {
        const displayAuthor = post.isMine ? this.profile.name : post.authorName;
        const displayAvatarImg = post.isMine ? this.profile.avatarImg : post.avatarImg;
        const displayAvatarText = post.isMine ? this.profile.avatarText : post.avatarText;

        const avatarHtml = displayAvatarImg
          ? `<img src="${displayAvatarImg}" alt="${displayAuthor}" />`
          : displayAvatarText;

        const mediaHtml =
          post.mediaType === 'video' && post.videoUrl
            ? `
              <video
                src="${post.videoUrl}"
                poster="${post.image}"
                class="feed-video-player"
                autoplay
                loop
                muted
                playsinline
                webkit-playsinline="true"
              ></video>
              <span class="feed-video-badge">🎥 Video · ${post.videoDuration || 6}s</span>
            `
            : `<img src="${post.image}" alt="${post.placeName || 'Khoảnh khắc'}" class="feed-photo-img" loading="lazy" />`;

        const commentsPreview =
          post.comments && post.comments.length > 0
            ? `<div style="margin-top:8px;padding-top:8px;border-top:1px dashed var(--border-subtle);display:flex;flex-direction:column;gap:4px;">
                ${post.comments
                  .slice(-2)
                  .map(
                    (c) =>
                      `<div style="font-size:0.78rem;color:var(--text-secondary);"><strong style="color:var(--text-primary);">${c.user}:</strong> ${c.text}</div>`
                  )
                  .join('')}
              </div>`
            : '';

        const deleteBtnHtml = post.isMine
          ? `<button class="feed-action-btn delete-btn" onclick="window.journeyApp.deleteMoment('${post.momentId || post.id}')" title="Xóa ảnh/video này">
               <span>🗑️</span>
               <span>Xóa</span>
             </button>`
          : '';

        const metaBadgeItems = [];
        if (post.stickerTag) metaBadgeItems.push(post.stickerTag);
        if (post.timestampLabel) metaBadgeItems.push(`🕒 ${post.timestampLabel}`);
        if (post.temperatureTag) metaBadgeItems.push(`🌡️ ${post.temperatureTag}`);

        const topOverlayHtml =
          post.placeName || metaBadgeItems.length > 0
            ? `<div class="feed-timestamp-overlay-pill">
                 ${post.placeName ? `<span class="feed-timestamp-overlay-place">📍 ${post.placeName}</span>` : ''}
                 ${metaBadgeItems.length > 0 ? `<span class="feed-timestamp-overlay-date">${metaBadgeItems.join(' · ')}</span>` : ''}
               </div>`
            : '';

        const spotifyOverlayHtml = post.spotifyTrack
          ? `<div style="position:absolute;bottom:${post.caption ? '54px' : '14px'};left:50%;transform:translateX(-50%);background:rgba(20,83,45,0.88);border:1px solid #22C55E;color:#BBF7D0;font-size:0.73rem;font-weight:700;padding:5px 12px;border-radius:999px;max-width:88%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;z-index:3;">
               🎵 ${post.spotifyTrack}
             </div>`
          : '';

        const subHeaderMeta = [
          post.placeName ? `📍 ${post.placeName}` : '',
          post.time || 'Vừa xong'
        ]
          .filter(Boolean)
          .join(' · ');

        return `
        <article class="feed-post-card">
          <div class="feed-post-header">
            <div class="feed-author-row">
              <div class="feed-author-avatar">${avatarHtml}</div>
              <div class="feed-author-info">
                <div class="feed-author-name">
                  <span>${displayAuthor}</span>
                  ${post.isMine ? '<span style="font-size:0.64rem;padding:2px 6px;border-radius:999px;background:var(--accent-gold-soft);color:var(--accent-gold-dark);">Bạn</span>' : ''}
                  ${this.getPrivacyBadgeHtml(post)}
                </div>
                <div class="feed-post-meta">
                  ${subHeaderMeta}
                </div>
              </div>
            </div>
          </div>

          <div class="feed-photo-wrap" ondblclick="window.journeyApp.toggleLikePost('${post.id}')">
            ${mediaHtml}
            ${topOverlayHtml}
            ${spotifyOverlayHtml}
            ${post.caption ? `<div class="feed-locket-caption-pill">${post.caption}</div>` : ''}
          </div>

          <div class="feed-post-footer">
            <div class="feed-actions-bar">
              <div class="feed-actions-left">
                <button class="feed-action-btn ${post.liked ? 'liked' : ''}" onclick="window.journeyApp.toggleLikePost('${post.id}')">
                  <span>${post.liked ? '❤️' : '🤍'}</span>
                  <span>${post.likes}</span>
                </button>
                <button class="feed-action-btn" onclick="window.journeyApp.openCommentModal('${post.id}')">
                  <span>💬</span>
                  <span>${post.comments ? post.comments.length : 0}</span>
                </button>
                ${
                  !post.isMine
                    ? `<button class="feed-action-btn" onclick="window.journeyApp.openChatWithAuthor('${displayAuthor}', '${post.placeName || ''}')">
                         <span>✉️</span>
                         <span>Nhắn tin</span>
                       </button>`
                    : ''
                }
              </div>
              ${deleteBtnHtml}
            </div>
            ${commentsPreview}
          </div>
        </article>
      `;
      })
      .join('');

    const nearbyPlacesHtml = Object.values(DESTINATIONS)
      .map(
        (d) => `
        <div
          onclick="window.journeyApp.viewPlaceOnMap('${d.id}')"
          style="min-width:165px;width:165px;height:145px;border-radius:18px;overflow:hidden;position:relative;cursor:pointer;border:1px solid var(--border-color);flex-shrink:0;"
        >
          <img src="${d.image}" alt="${d.name}" style="width:100%;height:100%;object-fit:cover;" />
          <div style="position:absolute;inset:0;background:linear-gradient(180deg, rgba(11,16,23,0.05) 35%, rgba(11,16,23,0.92) 100%);"></div>
          <div style="position:absolute;bottom:10px;left:10px;right:10px;">
            <div style="font-size:0.8rem;font-weight:800;color:#fff;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">📍 ${d.name}</div>
            <div style="font-size:0.68rem;color:rgba(233,229,220,0.85);margin-top:2px;">${d.province} · ⭐ ${d.rating}</div>
          </div>
        </div>
      `
      )
      .join('');

    main.innerHTML = `
      <!-- Pull-to-Refresh Indicator for Home Feed -->
      <div id="pullToRefreshBanner" class="pull-to-refresh-banner ${this.isRefreshingFeed ? 'refreshing' : ''}" role="status" aria-live="polite">
        <div class="pull-to-refresh-pill">
          <span id="pullToRefreshSpinner" class="ptr-spinner">🔄</span>
          <span id="pullToRefreshText">${this.isRefreshingFeed ? 'Đang làm mới bảng tin...' : 'Kéo xuống để làm mới bảng tin'}</span>
        </div>
      </div>

      <!-- 0. Premium Cinematic Hero Section, Search Bar & 4 Quick Actions -->
      <section aria-label="Khám phá nổi bật" style="display:flex;flex-direction:column;gap:14px;">
        <h1 class="serif-title" style="font-size:1.7rem;font-weight:700;color:var(--text-primary);margin:2px 0 0;">
          Xin chào, ${this.profile.name}!
        </h1>

        <div
          onclick="window.journeyApp.switchTab('explore')"
          style="display:flex;align-items:center;justify-content:space-between;padding:11px 16px;border-radius:999px;background:var(--surface-card);border:1px solid var(--border-gold);cursor:pointer;"
        >
          <span style="font-size:0.84rem;color:var(--text-secondary);">🔍 Tìm kiếm địa điểm, vật thể, câu chuyện...</span>
          <span onclick="event.stopPropagation(); window.journeyApp.switchTab('discover')" style="font-size:1rem;color:var(--accent-gold);font-weight:800;" title="Chụp AI">📷</span>
        </div>

        <!-- Vịnh Hạ Long Hero Card -->
        <div
          onclick="window.journeyApp.viewPlaceOnMap('halong')"
          style="position:relative;height:225px;border-radius:22px;overflow:hidden;border:1px solid var(--border-gold);cursor:pointer;box-shadow:var(--shadow-card);"
        >
          <img src="images/img_hero_halong_bay.jpg" alt="Vịnh Hạ Long" style="width:100%;height:100%;object-fit:cover;" />
          <div style="position:absolute;inset:0;background:linear-gradient(180deg, rgba(11,16,23,0.12) 0%, rgba(11,16,23,0.48) 52%, rgba(11,16,23,0.92) 100%);"></div>
          <div style="position:absolute;top:14px;right:14px;padding:5px 12px;border-radius:999px;background:rgba(17,25,35,0.78);border:1px solid var(--border-gold);color:#E9E5DC;font-size:0.75rem;font-weight:700;">
            ☀️ 28°C
          </div>
          <div style="position:absolute;bottom:16px;left:18px;right:18px;">
            <h2 class="serif-title" style="font-size:1.85rem;font-weight:700;color:#E9E5DC;line-height:1.1;">Vịnh Hạ Long</h2>
            <p style="font-size:0.8rem;color:rgba(233,229,220,0.9);margin:4px 0 12px;">Kỳ quan thiên nhiên thế giới của Việt Nam</p>
            <div style="display:flex;align-items:center;justify-content:space-between;">
              <span style="display:inline-flex;align-items:center;gap:6px;padding:8px 16px;border-radius:999px;background:linear-gradient(135deg,#D4BA94,#B99B72);color:#111923;font-size:0.78rem;font-weight:800;">
                ➤ Khám phá ngay →
              </span>
              <div style="display:flex;gap:5px;">
                <span style="width:6px;height:6px;border-radius:50%;background:rgba(255,255,255,0.4);"></span>
                <span style="width:8px;height:8px;border-radius:50%;background:#B99B72;"></span>
                <span style="width:6px;height:6px;border-radius:50%;background:rgba(255,255,255,0.4);"></span>
                <span style="width:6px;height:6px;border-radius:50%;background:rgba(255,255,255,0.4);"></span>
              </div>
            </div>
          </div>
        </div>

        <!-- 4 Circular Quick Actions -->
        <div style="display:grid;grid-template-columns:repeat(4,1fr);gap:8px;text-align:center;padding:4px 0;">
          <div onclick="window.journeyApp.switchTab('discover')" style="cursor:pointer;display:flex;flex-direction:column;align-items:center;gap:6px;">
            <div style="width:54px;height:54px;border-radius:50%;background:var(--surface-card);border:1.5px solid var(--border-gold);display:flex;align-items:center;justify-content:center;font-size:1.3rem;">📷</div>
            <span style="font-size:0.72rem;font-weight:700;color:var(--text-primary);">Chụp khám phá</span>
          </div>
          <div onclick="window.journeyApp.openFriendsAndChatHub('friends')" style="cursor:pointer;display:flex;flex-direction:column;align-items:center;gap:6px;">
            <div style="width:54px;height:54px;border-radius:50%;background:var(--surface-card);border:1.5px solid var(--border-gold);display:flex;align-items:center;justify-content:center;font-size:1.3rem;">💬</div>
            <span style="font-size:0.72rem;font-weight:700;color:var(--text-primary);">Hỏi AI &amp; Chat</span>
          </div>
          <div onclick="window.journeyApp.switchTab('explore')" style="cursor:pointer;display:flex;flex-direction:column;align-items:center;gap:6px;">
            <div style="width:54px;height:54px;border-radius:50%;background:var(--surface-card);border:1.5px solid var(--border-gold);display:flex;align-items:center;justify-content:center;font-size:1.3rem;">📍</div>
            <span style="font-size:0.72rem;font-weight:700;color:var(--text-primary);">Khám phá quanh đây</span>
          </div>
          <div onclick="window.journeyApp.switchTab('journal')" style="cursor:pointer;display:flex;flex-direction:column;align-items:center;gap:6px;">
            <div style="width:54px;height:54px;border-radius:50%;background:var(--surface-card);border:1.5px solid var(--border-gold);display:flex;align-items:center;justify-content:center;font-size:1.3rem;">🔖</div>
            <span style="font-size:0.72rem;font-weight:700;color:var(--text-primary);">Nhật ký</span>
          </div>
        </div>

        <!-- Khám phá gần bạn -->
        <div>
          <div class="section-header" style="margin-bottom:8px;">
            <h2 class="section-title serif-title">Khám phá gần bạn</h2>
            <button class="section-link" onclick="window.journeyApp.switchTab('explore')">Xem tất cả ›</button>
          </div>
          <div style="display:flex;gap:12px;overflow-x:auto;padding-bottom:4px;">
            ${nearbyPlacesHtml}
          </div>
        </div>
      </section>

      <!-- 1. Clean Stories Strip -->
      <section aria-label="Khoảnh khắc">
        <div class="section-header" style="margin-bottom:6px;">
          <h2 class="section-title serif-title">Khoảnh Khắc</h2>
          <div style="display:flex;gap:8px;align-items:center;">
            <button class="chip-btn" onclick="window.journeyApp.openFriendsAndChatHub('friends')">
              👥 Bạn bè (${this.friendsList.length})
            </button>
            <button class="section-link" onclick="window.journeyApp.switchTab('discover')">+ Chụp mới</button>
          </div>
        </div>
        <div class="stories-strip">
          <div class="story-item" onclick="window.journeyApp.switchTab('discover')">
            <div class="story-ring add-story">
              <div class="story-avatar">📷</div>
            </div>
            <span class="story-name">Chụp mới</span>
          </div>
          ${storiesHtml}
        </div>
      </section>

      <!-- 2. Clean Filter Bar -->
      <div class="explore-map-controls" style="border-radius:16px;border:1px solid var(--border-color);padding:7px 10px;">
        <button class="chip-btn ${this.feedFilter === 'all' ? 'active' : ''}" onclick="window.journeyApp.setFeedFilter('all')">
          🌐 Tất cả (${this.posts.length})
        </button>
        <button class="chip-btn ${this.feedFilter === 'friends' ? 'active' : ''}" onclick="window.journeyApp.setFeedFilter('friends')">
          👥 Bạn bè (${this.posts.filter((p) => !p.isMine).length})
        </button>
        <button class="chip-btn ${this.feedFilter === 'mine' ? 'active' : ''}" onclick="window.journeyApp.setFeedFilter('mine')">
          👤 Của tôi (${this.posts.filter((p) => p.isMine).length})
        </button>
        <button
          type="button"
          id="btnRefreshHomeFeed"
          class="feed-refresh-chip-btn"
          onclick="window.journeyApp.refreshHomeFeed()"
        >
          <span>🔄</span>
          <span>Làm mới</span>
        </button>
      </div>

      <!-- 3. Feed Cards -->
      <section aria-label="Bảng tin bài đăng" style="display:flex;flex-direction:column;gap:14px;">
        ${postsHtml || '<div style="text-align:center;padding:28px 18px;background:var(--surface-card);border-radius:20px;border:1px solid var(--border-color);color:var(--text-secondary);">Chưa có khoảnh khắc nào. Hãy bấm <strong>Ống kính 📷</strong> để chụp bức ảnh đầu tiên!</div>'}
      </section>
    `;
  }

  setupHomePullToRefresh() {
    const viewport = document.getElementById('mainViewport') || document.body;

    viewport.addEventListener(
      'touchstart',
      (e) => {
        if (this.activeTab !== 'home' || this.isRefreshingFeed) return;
        const scrollTop = viewport.scrollTop || window.scrollY || 0;
        if (scrollTop <= 5 && e.touches && e.touches.length === 1) {
          this.pullStartY = e.touches[0].clientY;
          this.pullDistance = 0;
          this.isPullingFeed = true;
        }
      },
      { passive: true }
    );

    viewport.addEventListener(
      'touchmove',
      (e) => {
        if (!this.isPullingFeed || this.activeTab !== 'home' || this.isRefreshingFeed) return;
        const currentY = e.touches && e.touches[0] ? e.touches[0].clientY : this.pullStartY;
        const deltaY = currentY - this.pullStartY;
        const scrollTop = viewport.scrollTop || window.scrollY || 0;

        if (deltaY > 8 && scrollTop <= 5) {
          this.pullDistance = Math.min(110, deltaY * 0.55);
          const banner = document.getElementById('pullToRefreshBanner');
          const textEl = document.getElementById('pullToRefreshText');
          const spinnerEl = document.getElementById('pullToRefreshSpinner');

          if (banner) {
            banner.classList.add('pulling');
          }
          if (spinnerEl) {
            spinnerEl.style.transform = `rotate(${Math.round(this.pullDistance * 3.2)}deg)`;
          }
          if (textEl) {
            textEl.textContent =
              this.pullDistance >= 52
                ? 'Thả tay để tải khoảnh khắc mới nhất từ bạn bè ✨'
                : 'Kéo xuống để làm mới bảng tin...';
          }
        } else if (deltaY < 0) {
          this.isPullingFeed = false;
          this.pullDistance = 0;
          const banner = document.getElementById('pullToRefreshBanner');
          if (banner && !this.isRefreshingFeed) banner.classList.remove('pulling');
        }
      },
      { passive: true }
    );

    viewport.addEventListener(
      'touchend',
      () => {
        if (!this.isPullingFeed || this.activeTab !== 'home') return;
        const triggered = this.pullDistance >= 52;
        this.isPullingFeed = false;
        this.pullDistance = 0;

        if (triggered) {
          this.refreshHomeFeed();
        } else {
          const banner = document.getElementById('pullToRefreshBanner');
          if (banner && !this.isRefreshingFeed) {
            banner.classList.remove('pulling');
          }
        }
      },
      { passive: true }
    );
  }

  refreshHomeFeed() {
    if (this.isRefreshingFeed) return;
    this.isRefreshingFeed = true;

    const banner = document.getElementById('pullToRefreshBanner');
    const textEl = document.getElementById('pullToRefreshText');
    if (banner) {
      banner.classList.remove('pulling');
      banner.classList.add('refreshing');
    }
    if (textEl) {
      textEl.textContent = 'Đang đồng bộ bảng tin...';
    }

    setTimeout(() => {
      this.posts = this.loadJson('journey_feed_posts', []);
      this.checkins = this.loadJson('journey_user_checkins', []);
      this.friendsList = this.loadJson('journey_friends_list', []);
      this.pendingFriendRequests = this.loadJson('journey_friend_requests', []);
      this.reconcileDeletedMoments();
      this.updateHeaderUnreadBadge();

      this.isRefreshingFeed = false;
      if (this.activeTab === 'home') {
        this.renderHomeTab();
      }
      this.showToast(`✓ Đã làm mới bảng tin (${this.posts.length} khoảnh khắc)`);
    }, 350);
  }

  setFeedFilter(filter) {
    this.feedFilter = filter;
    this.renderHomeTab();
  }

  toggleLikePost(postId) {
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;
    post.liked = !post.liked;
    post.likes += post.liked ? 1 : -1;
    this.saveJson('journey_feed_posts', this.posts);
    if (this.activeTab === 'home') {
      this.renderHomeTab();
    }
  }

  /**
   * Synchronized deletion: removes the photo/video from BOTH
   * Home Feed (`this.posts`) AND Check-in Journal (`this.checkins`).
   */
  deleteMoment(momentOrItemId) {
    const targetPost = this.posts.find(
      (p) => p.id === momentOrItemId || p.momentId === momentOrItemId
    );
    const targetCheckin = this.checkins.find(
      (c) => c.id === momentOrItemId || c.momentId === momentOrItemId
    );

    const targetImage =
      (targetPost && targetPost.image) || (targetCheckin && targetCheckin.image) || null;
    const targetCaption =
      (targetPost && targetPost.caption) || (targetCheckin && targetCheckin.caption) || null;

    this.posts = this.posts.filter((p) => {
      if (p.id === momentOrItemId || p.momentId === momentOrItemId) return false;
      if (p.isMine && targetImage && p.image === targetImage && p.caption === targetCaption) {
        return false;
      }
      return true;
    });

    this.checkins = this.checkins.filter((c) => {
      if (c.id === momentOrItemId || c.momentId === momentOrItemId) return false;
      if (targetImage && c.image === targetImage && c.caption === targetCaption) {
        return false;
      }
      return true;
    });

    this.saveJson('journey_feed_posts', this.posts);
    this.saveJson('journey_user_checkins', this.checkins);
    this.closeModal();
    this.showToast('🗑️ Đã xóa ảnh/video khỏi cả Trang chủ và Nhật ký!');

    if (this.activeTab === 'home') {
      this.renderHomeTab();
    } else if (this.activeTab === 'journal') {
      this.renderJournalTab();
    } else if (this.activeTab === 'explore') {
      this.renderExploreTab();
    }
  }

  deleteCheckin(checkinId) {
    this.deleteMoment(checkinId);
  }

  openCommentModal(postId) {
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;

    const commentsList =
      post.comments && post.comments.length > 0
        ? post.comments
            .map(
              (c) => `
            <div style="padding:8px 10px;border-radius:12px;background:var(--bg-elevated);border:1px solid var(--border-subtle);font-size:0.84rem;">
              <strong style="color:var(--text-primary);">${c.user}:</strong>
              <span style="color:var(--text-secondary);margin-left:4px;">${c.text}</span>
            </div>
          `
            )
            .join('')
        : '<div style="font-size:0.84rem;color:var(--text-muted);text-align:center;padding:12px;">Chưa có bình luận nào. Hãy là người đầu tiên!</div>';

    this.openModal(`
      <div class="modal-header-row">
        <h3 class="serif-title" style="font-size:1.15rem;">💬 Bình luận bài đăng của ${post.isMine ? this.profile.name : post.authorName}</h3>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div style="display:flex;flex-direction:column;gap:8px;max-height:240px;overflow-y:auto;margin-bottom:12px;">
        ${commentsList}
      </div>
      <div style="display:flex;gap:8px;">
        <input id="commentInputBox" class="form-input" type="text" placeholder="Viết bình luận của bạn..." style="flex:1;" />
        <button class="btn-save-profile" style="width:auto;padding:10px 16px;" onclick="window.journeyApp.submitComment('${post.id}')">Gửi</button>
      </div>
    `);
  }

  submitComment(postId) {
    const input = document.getElementById('commentInputBox');
    if (!input || !input.value.trim()) return;
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;
    if (!post.comments) post.comments = [];
    post.comments.push({
      user: this.profile.name,
      text: input.value.trim()
    });
    this.saveJson('journey_feed_posts', this.posts);
    this.openCommentModal(postId);
    if (this.activeTab === 'home') {
      this.renderHomeTab();
    }
  }

  openStoryModal(storyId) {
    const post = this.posts.find((p) => p.id === storyId);
    const st = post
      ? {
          id: post.id,
          author: post.isMine ? this.profile.name : post.authorName,
          avatarText: post.isMine ? this.profile.avatarText : post.avatarText,
          time: post.time || 'Hôm nay',
          placeName: post.placeName,
          placeId: post.placeId || 'halong',
          image: post.image,
          caption: post.caption
        }
      : FRIEND_STORIES[storyId];
    if (!st) return;
    this.openModal(`
      <div class="modal-header-row">
        <div style="display:flex;align-items:center;gap:8px;">
          <div class="feed-author-avatar">${st.avatarText}</div>
          <div>
            <div style="font-weight:800;font-size:0.92rem;">${st.author}</div>
            <div style="font-size:0.74rem;color:var(--text-secondary);">📍 ${st.placeName} · ${st.time}</div>
          </div>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div class="feed-photo-wrap" style="width:100%;margin:0 0 12px;border-radius:20px;overflow:hidden;">
        <img src="${st.image}" alt="${st.placeName}" class="feed-photo-img" />
        <div class="feed-locket-caption-pill">${st.caption}</div>
      </div>
      <div style="display:flex;justify-content:space-between;align-items:center;gap:8px;flex-wrap:wrap;">
        <div style="display:flex;gap:6px;">
          <button class="chip-btn" onclick="window.journeyApp.showToast('❤️ Đã thả tim khoảnh khắc của ${st.author}!')">❤️ Thả tim</button>
        </div>
        <button class="chip-btn active" onclick="window.journeyApp.closeModal(); window.journeyApp.viewPlaceOnMap('${st.placeId}')">
          🗺️ Xem trên Bản đồ VN
        </button>
      </div>
    `);
  }

  /* =========================================================================
     TAB 2: KHÁM PHÁ — BẢN ĐỒ VIỆT NAM NHỮNG NƠI ĐÃ ĐI / ĐÃ ĐẾN
     ========================================================================= */
  getAllVisitedPlaces() {
    const baseList = [
      DESTINATIONS.sapa,
      DESTINATIONS.hanoi_temple,
      DESTINATIONS.halong,
      DESTINATIONS.hoian
    ];
    return [...baseList, ...this.customPlaces];
  }

  renderExploreTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const allPlaces = this.getAllVisitedPlaces();
    const provincesCount = new Set(allPlaces.map((p) => p.province)).size;

    const chipsHtml = allPlaces
      .map(
        (p, idx) => `
        <button class="chip-btn ${idx === 0 ? 'active' : ''}" data-place-chip="${p.id}" onclick="window.journeyApp.flyToTrendingPlace('${p.id}')">
          ${p.pinIcon || '📍'} ${p.nameVi}
        </button>
      `
      )
      .join('');

    const visitedCardsHtml = allPlaces
      .map(
        (p) => `
        <div style="display:flex;align-items:center;gap:12px;padding:10px;border-radius:16px;background:var(--surface-card);border:1px solid var(--border-color);box-shadow:var(--shadow-card);cursor:pointer;" onclick="window.journeyApp.flyToTrendingPlace('${p.id}')">
          <img src="${p.image}" alt="${p.nameVi}" style="width:60px;height:60px;border-radius:12px;object-fit:cover;flex-shrink:0;" />
          <div style="flex:1;min-width:0;">
            <div style="display:flex;align-items:center;gap:6px;">
              <span style="font-size:0.66rem;font-weight:800;padding:2px 7px;border-radius:999px;background:var(--accent-sage-soft);color:var(--accent-sage);">✓ Đã đến</span>
              <span style="font-size:0.7rem;color:var(--text-muted);">${p.visitedDate || 'Tháng 11/2026'}</span>
            </div>
            <div class="serif-title" style="font-size:1rem;font-weight:700;color:var(--text-primary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${p.nameVi}</div>
            <div style="font-size:0.74rem;color:var(--text-secondary);">📍 ${p.province} · ${p.coords}</div>
          </div>
          <button class="chip-btn" onclick="event.stopPropagation(); window.journeyApp.openDestinationModal('${p.id}')">Chi tiết</button>
        </div>
      `
      )
      .join('');

    main.innerHTML = `
      <!-- Thống kê Dấu chân Hành trình Việt Nam -->
      <div class="visited-stats-row">
        <div class="visited-stat-box">
          <div class="visited-stat-num">${allPlaces.length}</div>
          <div class="visited-stat-label">Nơi đã đến</div>
        </div>
        <div class="visited-stat-box">
          <div class="visited-stat-num">${provincesCount}</div>
          <div class="visited-stat-label">Tỉnh / Thành VN</div>
        </div>
        <div class="visited-stat-box">
          <div class="visited-stat-num">${this.checkins.length}</div>
          <div class="visited-stat-label">Ảnh Check-in</div>
        </div>
      </div>

      <!-- Bản đồ Việt Nam những nơi đã đi (.explore-section) -->
      <section class="explore-section" id="exploreMapSection" aria-label="Bản đồ Việt Nam những nơi đã đi">
        <div class="section-header">
          <div>
            <h2 class="section-title serif-title">Bản Đồ Việt Nam — Nơi Đã Đi 🇻🇳</h2>
            <p style="font-size:0.74rem;color:var(--text-secondary);">Tìm nhanh thành phố, địa danh hoặc chạm cột mốc trên bản đồ</p>
          </div>
          <div style="display:flex;gap:6px;flex-shrink:0;">
            <button class="chip-btn" onclick="window.journeyApp.openAddPlaceModal()">+ Thêm nơi</button>
            <button class="chip-btn active" onclick="window.journeyApp.fitAllTrendingMarkers()">Toàn cảnh VN</button>
          </div>
        </div>

        <!-- Search Bar at Top of Map View for Quick Jump to Vietnam Cities & Landmarks -->
        <div class="map-search-bar-container" id="mapSearchBarContainer">
          <div class="map-search-input-wrap">
            <span class="map-search-icon" aria-hidden="true">🔍</span>
            <input
              type="search"
              id="mapSearchInput"
              class="map-search-input"
              placeholder="Tìm thành phố, địa danh VN (Hà Nội, Đà Nẵng, Hội An, Phú Quốc...)"
              aria-label="Tìm thành phố hoặc địa danh tại Việt Nam"
              autocomplete="off"
              oninput="window.journeyApp.onMapSearchInput(this.value)"
              onfocus="window.journeyApp.onMapSearchInput(this.value)"
              onkeydown="window.journeyApp.onMapSearchKeydown(event)"
            />
            <button
              type="button"
              id="mapSearchClearBtn"
              class="map-search-clear-btn"
              title="Xóa tìm kiếm"
              aria-label="Xóa nội dung tìm kiếm"
              onclick="window.journeyApp.clearMapSearch()"
            >
              ✕
            </button>
            <button
              type="button"
              id="mapSearchSubmitBtn"
              class="map-search-submit-btn"
              onclick="window.journeyApp.submitMapSearch()"
            >
              Đi tới
            </button>
          </div>

          <!-- Quick City / Landmark Jump Pills -->
          <div class="map-search-quick-tags" aria-label="Gợi ý thành phố và địa danh nổi bật">
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('halong')">⛵ Vịnh Hạ Long</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('vanmieu')">⛩️ Hà Nội · Văn Miếu</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('sapa')">🌾 Sa Pa</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('danang')">🌉 Đà Nẵng</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('hoian')">🏮 Phố Cổ Hội An</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('hue')">🏯 Cố Đô Huế</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('dalat')">🌲 Đà Lạt</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('tphcm')">🏙️ TP. Hồ Chí Minh</button>
            <button type="button" class="map-search-quick-tag" onclick="window.journeyApp.quickJumpVietnamLocation('phuquoc')">🏝️ Phú Quốc</button>
          </div>

          <!-- Autocomplete Suggestions Dropdown -->
          <div id="mapSearchSuggestions" class="map-search-suggestions" role="listbox" aria-label="Kết quả gợi ý địa điểm Việt Nam"></div>
        </div>

        <div class="leaflet-explore-wrapper">
          <!-- Dedicated '+' and '-' Zoom Controls for Mobile Accessibility -->
          <div class="map-zoom-controls-group" role="group" aria-label="Điều khiển phóng to thu nhỏ bản đồ">
            <button
              type="button"
              id="mapZoomInBtn"
              class="map-zoom-btn"
              title="Phóng to bản đồ (+)"
              aria-label="Phóng to bản đồ (+)"
              onclick="window.journeyApp.zoomInExploreMap()"
            >
              +
            </button>
            <span id="mapZoomLevelBadge" class="map-zoom-level-pill" aria-live="polite">5x</span>
            <button
              type="button"
              id="mapZoomOutBtn"
              class="map-zoom-btn"
              title="Thu nhỏ bản đồ (-)"
              aria-label="Thu nhỏ bản đồ (-)"
              onclick="window.journeyApp.zoomOutExploreMap()"
            >
              &minus;
            </button>
          </div>

          <!-- Layer Toggle: Standard / Satellite / Terrain -->
          <div class="map-layer-toggle-group" role="group" aria-label="Map Layer Toggle">
            <button
              type="button"
              class="map-layer-btn ${this.mapLayerMode === 'standard' ? 'active' : ''}"
              data-map-layer="standard"
              onclick="window.journeyApp.switchMapLayer('standard')"
            >
              🗺️ Standard
            </button>
            <button
              type="button"
              class="map-layer-btn ${this.mapLayerMode === 'satellite' ? 'active' : ''}"
              data-map-layer="satellite"
              onclick="window.journeyApp.switchMapLayer('satellite')"
            >
              🛰️ Satellite
            </button>
            <button
              type="button"
              class="map-layer-btn ${this.mapLayerMode === 'terrain' ? 'active' : ''}"
              data-map-layer="terrain"
              onclick="window.journeyApp.switchMapLayer('terrain')"
            >
              ⛰️ Terrain
            </button>
          </div>

          <!-- Category Filter Overlay: Restaurants / Museums / Nature Spots -->
          <div class="map-category-filter-overlay" id="mapCategoryFilterOverlay" role="group" aria-label="Bộ lọc loại địa điểm trên bản đồ">
            <span class="map-cat-filter-label">Lọc:</span>
            <button
              type="button"
              class="map-cat-filter-btn ${this.visibleMapCategories.nature !== false ? 'active' : 'hidden-cat'}"
              data-map-category="nature"
              aria-pressed="${this.visibleMapCategories.nature !== false}"
              onclick="window.journeyApp.toggleMapCategoryFilter('nature')"
            >
              <span>🌿 Nature Spots</span>
              <span class="map-cat-count-badge">3</span>
            </button>
            <button
              type="button"
              class="map-cat-filter-btn ${this.visibleMapCategories.museums !== false ? 'active' : 'hidden-cat'}"
              data-map-category="museums"
              aria-pressed="${this.visibleMapCategories.museums !== false}"
              onclick="window.journeyApp.toggleMapCategoryFilter('museums')"
            >
              <span>🏛️ Museums</span>
              <span class="map-cat-count-badge">3</span>
            </button>
            <button
              type="button"
              class="map-cat-filter-btn ${this.visibleMapCategories.restaurants !== false ? 'active' : 'hidden-cat'}"
              data-map-category="restaurants"
              aria-pressed="${this.visibleMapCategories.restaurants !== false}"
              onclick="window.journeyApp.toggleMapCategoryFilter('restaurants')"
            >
              <span>🍽️ Restaurants</span>
              <span class="map-cat-count-badge">3</span>
            </button>
            <button
              type="button"
              class="map-cat-filter-btn"
              title="Hiện tất cả loại địa điểm"
              onclick="window.journeyApp.resetMapCategoryFilters()"
            >
              <span>✨ Tất cả</span>
            </button>
          </div>

          <div id="leafletExploreMap" class="leaflet-explore-map" role="region" aria-label="Leaflet Vietnam Map"></div>
          <div class="explore-map-controls" id="exploreMapChipsBar">
            ${chipsHtml}
          </div>
        </div>
      </section>

      <!-- Danh sách Cột mốc Đã đặt chân tới -->
      <section style="display:flex;flex-direction:column;gap:8px;">
        <div class="section-header">
          <h2 class="section-title serif-title">Cột Mốc Đã Đặt Chân Tới (${allPlaces.length})</h2>
          <button class="section-link" onclick="window.journeyApp.switchTab('journal')">Xem Nhật ký →</button>
        </div>
        ${visitedCardsHtml}
      </section>
    `;

    setTimeout(() => {
      this.initExploreLeafletMap();
    }, 60);
  }

  initExploreLeafletMap() {
    const mapContainer = document.getElementById('leafletExploreMap');
    if (!mapContainer || typeof window.L === 'undefined') return;

    if (this.leafletMap) {
      this.leafletMap.remove();
      this.leafletMap = null;
    }

    // Centered on Vietnam (from Lào Cai / Sa Pa to Cà Mau + Hoàng Sa & Trường Sa)
    this.leafletMap = window.L.map('leafletExploreMap', {
      center: [16.2, 107.2],
      zoom: 5,
      minZoom: 4,
      maxZoom: 18,
      zoomControl: false,
      scrollWheelZoom: true
    });

    // Free, no-API-key tile layers for Standard, Satellite, and Terrain views
    this.leafletTileLayers = {
      standard: window.L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap — Standard View 🇻🇳',
        maxZoom: 19
      }),
      satellite: window.L.tileLayer(
        'https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}',
        {
          attribution: '&copy; Esri World Imagery — Satellite View 🛰️',
          maxZoom: 18
        }
      ),
      terrain: window.L.tileLayer(
        'https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}',
        {
          attribution: '&copy; OpenTopo / Esri Topographic — Terrain View ⛰️',
          maxZoom: 18
        }
      )
    };

    const initialKey = this.leafletTileLayers[this.mapLayerMode] ? this.mapLayerMode : 'standard';
    this.activeLeafletTileLayer = this.leafletTileLayers[initialKey];
    this.activeLeafletTileLayer.addTo(this.leafletMap);

    this.injectCustomTrendingMarkers(this.leafletMap);

    this.leafletMap.on('zoomend', () => {
      this.updateMapZoomButtonsState();
    });

    // Fit full S-shape of Vietnam nicely
    this.leafletMap.fitBounds(
      [
        [8.5, 102.2],
        [23.4, 114.6]
      ],
      { padding: [16, 16] }
    );

    this.updateMapZoomButtonsState();

    setTimeout(() => {
      if (this.leafletMap) {
        this.leafletMap.invalidateSize();
        this.updateMapZoomButtonsState();
      }
    }, 180);
  }

  zoomInExploreMap() {
    if (!this.leafletMap) return;
    this.leafletMap.zoomIn(1);
    this.updateMapZoomButtonsState();
  }

  zoomOutExploreMap() {
    if (!this.leafletMap) return;
    this.leafletMap.zoomOut(1);
    this.updateMapZoomButtonsState();
  }

  updateMapZoomButtonsState() {
    if (!this.leafletMap) return;
    const currentZoom = Math.round(this.leafletMap.getZoom() || 5);
    const minZoom = this.leafletMap.getMinZoom() ?? 4;
    const maxZoom = this.leafletMap.getMaxZoom() ?? 18;

    const zoomInBtn = document.getElementById('mapZoomInBtn');
    const zoomOutBtn = document.getElementById('mapZoomOutBtn');
    const zoomBadge = document.getElementById('mapZoomLevelBadge');

    if (zoomBadge) {
      zoomBadge.textContent = `${currentZoom}x`;
    }
    if (zoomInBtn) {
      zoomInBtn.disabled = currentZoom >= maxZoom;
    }
    if (zoomOutBtn) {
      zoomOutBtn.disabled = currentZoom <= minZoom;
    }
  }

  switchMapLayer(layerKey) {
    const validKey = ['standard', 'satellite', 'terrain'].includes(layerKey)
      ? layerKey
      : 'standard';
    this.mapLayerMode = validKey;
    localStorage.setItem('journey_map_layer', validKey);

    document.querySelectorAll('[data-map-layer]').forEach((btn) => {
      btn.classList.toggle('active', btn.getAttribute('data-map-layer') === validKey);
    });

    if (this.leafletMap && this.leafletTileLayers && this.leafletTileLayers[validKey]) {
      if (this.activeLeafletTileLayer && this.leafletMap.hasLayer(this.activeLeafletTileLayer)) {
        this.leafletMap.removeLayer(this.activeLeafletTileLayer);
      }
      this.activeLeafletTileLayer = this.leafletTileLayers[validKey];
      this.activeLeafletTileLayer.addTo(this.leafletMap);
    }

    const labelMap = {
      standard: '🗺️ Standard (Bản đồ Tiêu chuẩn)',
      satellite: '🛰️ Satellite (Ảnh Vệ tinh)',
      terrain: '⛰️ Terrain (Địa hình Độ cao)'
    };
    this.showToast(`Đã chuyển lớp bản đồ: ${labelMap[validKey]}`);
  }

  /**
   * Returns all categorized Vietnam map locations across 'nature', 'museums', and 'restaurants'.
   */
  getCategorizedMapLocations() {
    const baseVisited = this.getAllVisitedPlaces().map((p) => {
      let categoryKey = p.mapCategory || 'nature';
      if (p.id === 'hanoi_temple' || p.id === 'hoian') {
        categoryKey = 'museums';
      } else if (p.id === 'halong' || p.id === 'sapa') {
        categoryKey = 'nature';
      }
      const categoryBadgeMap = {
        nature: '🌿 Nature Spot',
        museums: '🏛️ Museum & Heritage',
        restaurants: '🍽️ Restaurant & Cuisine'
      };
      return {
        ...p,
        mapCategory: categoryKey,
        categoryBadge: categoryBadgeMap[categoryKey] || '🌿 Nature Spot'
      };
    });

    const curatedSpots = [
      {
        id: 'nature_trangan',
        nameVi: 'Quần Thể Danh Thắng Tràng An',
        nameEn: 'Trang An Landscape Complex',
        province: 'Ninh Bình',
        lat: 20.2506,
        lng: 105.9745,
        coords: '20.2506° N, 105.9745° E',
        pinIcon: '🌿',
        rating: '4.9',
        image: 'images/img_hero_halong_bay.jpg',
        mapCategory: 'nature',
        categoryBadge: '🌿 Nature Spot'
      },
      {
        id: 'museum_hue',
        nameVi: 'Bảo Tàng Cổ Vật Cung Đình Huế',
        nameEn: 'Hue Royal Antiquities Museum',
        province: 'Thừa Thiên Huế',
        lat: 16.4698,
        lng: 107.5784,
        coords: '16.4698° N, 107.5784° E',
        pinIcon: '🏛️',
        rating: '4.8',
        image: 'images/img_hanoi_temple.jpg',
        mapCategory: 'museums',
        categoryBadge: '🏛️ Museum & Heritage'
      },
      {
        id: 'rest_hanoi_chaca',
        nameVi: 'Chả Cá Lã Vọng — Phố Cổ Hà Nội',
        nameEn: 'Cha Ca La Vong Restaurant',
        province: 'Hà Nội',
        lat: 21.0352,
        lng: 105.849,
        coords: '21.0352° N, 105.8490° E',
        pinIcon: '🍽️',
        rating: '4.9',
        image: 'images/img_hanoi_temple.jpg',
        mapCategory: 'restaurants',
        categoryBadge: '🍽️ Restaurant & Cuisine'
      },
      {
        id: 'rest_hoian_caolau',
        nameVi: 'Cao Lầu & Cơm Gà Bà Buội — Hội An',
        nameEn: 'Hoi An Heritage Restaurant',
        province: 'Quảng Nam',
        lat: 15.8778,
        lng: 108.3282,
        coords: '15.8778° N, 108.3282° E',
        pinIcon: '🍜',
        rating: '4.9',
        image: 'images/img_hoian_lanterns.jpg',
        mapCategory: 'restaurants',
        categoryBadge: '🍽️ Restaurant & Cuisine'
      },
      {
        id: 'rest_saigon_benthanh',
        nameVi: 'Nhà Hàng Ẩm Thực Bến Thành',
        nameEn: 'Ben Thanh Culinary House',
        province: 'TP. Hồ Chí Minh',
        lat: 10.7731,
        lng: 106.6983,
        coords: '10.7731° N, 106.6983° E',
        pinIcon: '🥢',
        rating: '4.8',
        image: 'images/img_sapa_terraces.jpg',
        mapCategory: 'restaurants',
        categoryBadge: '🍽️ Restaurant & Cuisine'
      }
    ];

    const existingIds = new Set(baseVisited.map((p) => p.id));
    const combined = [...baseVisited];
    curatedSpots.forEach((spot) => {
      if (!existingIds.has(spot.id)) {
        existingIds.add(spot.id);
        combined.push(spot);
      }
    });
    return combined;
  }

  /**
   * Programmatically injects custom markers categorized into 'nature', 'museums', and 'restaurants',
   * plus Vietnam sovereignty markers for Quần đảo Hoàng Sa & Quần đảo Trường Sa.
   */
  injectCustomTrendingMarkers(map) {
    if (!map || typeof window.L === 'undefined') return;

    this.leafletMarkers = {};
    this.categoryLeafletMarkers = {
      restaurants: [],
      museums: [],
      nature: []
    };

    const allLocations = this.getCategorizedMapLocations();
    const routeCoords = [];

    allLocations.forEach((place) => {
      const catKey = place.mapCategory || 'nature';
      const isCatVisible = this.visibleMapCategories[catKey] !== false;

      if (isCatVisible && DESTINATIONS[place.id]) {
        routeCoords.push([place.lat, place.lng]);
      }

      const customIcon = window.L.divIcon({
        className: 'custom-landmark-marker',
        html: `
          <div class="marker-hover-container" title="${place.nameVi} (${place.categoryBadge || ''})">
            <div class="marker-pin-pill">
              <span class="marker-pin-emoji">${place.pinIcon || '📍'}</span>
              <span>${place.nameVi}</span>
            </div>
            <div class="marker-pin-stem"></div>
            <div class="marker-ground-shadow"></div>
          </div>
        `,
        iconSize: [148, 48],
        iconAnchor: [74, 42],
        popupAnchor: [0, -38]
      });

      const detailTargetId = DESTINATIONS[place.id] ? place.id : 'halong';
      const popupHtml = `
        <div class="journey-marker-popup">
          <div class="journey-marker-popup">
            <div class="journey-popup-thumb-wrap">
              <img src="${place.image}" alt="${place.nameEn || place.nameVi}" class="journey-popup-thumb" />
              <span class="journey-popup-rank-badge">${place.categoryBadge || '✓ Đã đến'} · ${place.province}</span>
            </div>
            <div class="journey-popup-body">
              <h4 class="journey-popup-title">${place.nameVi}</h4>
              <p class="journey-popup-subtitle">${place.nameEn || place.province} · ★ ${place.rating || '5.0'}</p>
              <button
                type="button"
                class="journey-popup-view-details-btn"
                onclick="window.journeyApp.openDestinationModal('${detailTargetId}')"
              >
                View Details
              </button>
            </div>
          </div>
        </div>
      `;

      const marker = window.L.marker([place.lat, place.lng], {
        icon: customIcon,
        title: `${place.nameVi} (${place.categoryBadge || place.province})`
      });

      marker.bindPopup(popupHtml, {
        maxWidth: 235,
        minWidth: 205,
        closeButton: true
      });

      if (isCatVisible) {
        marker.addTo(map);
      }

      this.leafletMarkers[place.id] = marker;
      if (!this.categoryLeafletMarkers[catKey]) {
        this.categoryLeafletMarkers[catKey] = [];
      }
      this.categoryLeafletMarkers[catKey].push(marker);
    });

    // Vietnam Sovereign Archipelagos on Biển Đông (Hoàng Sa & Trường Sa)
    const islands = [
      {
        name: '🇻🇳 QĐ. Hoàng Sa (Việt Nam)',
        lat: 16.53,
        lng: 111.62,
        desc: 'Huyện đảo Hoàng Sa, TP. Đà Nẵng, Việt Nam'
      },
      {
        name: '🇻🇳 QĐ. Trường Sa (Việt Nam)',
        lat: 9.65,
        lng: 114.28,
        desc: 'Huyện đảo Trường Sa, Tỉnh Khánh Hòa, Việt Nam'
      }
    ];

    islands.forEach((isl) => {
      const islandIcon = window.L.divIcon({
        className: 'vn-island-label',
        html: `<div class="vn-island-pill">${isl.name}</div>`,
        iconSize: [150, 24],
        iconAnchor: [75, 12]
      });
      window.L.marker([isl.lat, isl.lng], { icon: islandIcon, title: isl.desc })
        .addTo(map)
        .bindPopup(`<div style="padding:10px;font-weight:700;text-align:center;">${isl.name}<br/><span style="font-size:0.75rem;font-weight:500;color:var(--text-secondary);">${isl.desc}</span></div>`);
    });

    if (routeCoords.length > 1) {
      this.leafletRouteLine = window.L.polyline(routeCoords, {
        color: '#2E6F5E',
        weight: 3,
        dashArray: '6, 8',
        opacity: 0.85
      }).addTo(map);
    }
  }

  toggleMapCategoryFilter(categoryKey) {
    if (!['restaurants', 'museums', 'nature'].includes(categoryKey)) return;

    const currentVal = this.visibleMapCategories[categoryKey] !== false;
    this.visibleMapCategories[categoryKey] = !currentVal;
    this.saveJson('journey_map_categories', this.visibleMapCategories);

    this.applyMapCategoryFilterState();

    const labelMap = {
      restaurants: '🍽️ Restaurants (Ẩm thực)',
      museums: '🏛️ Museums (Bảo tàng & Di tích)',
      nature: '🌿 Nature Spots (Thiên nhiên)'
    };
    const actionText = this.visibleMapCategories[categoryKey] ? 'Đã hiển thị' : 'Đã ẩn';
    this.showToast(`${actionText}: ${labelMap[categoryKey]}`);
  }

  resetMapCategoryFilters() {
    this.visibleMapCategories = {
      restaurants: true,
      museums: true,
      nature: true
    };
    this.saveJson('journey_map_categories', this.visibleMapCategories);
    this.applyMapCategoryFilterState();
    this.showToast('✨ Đã hiển thị tất cả loại địa điểm trên bản đồ!');
  }

  applyMapCategoryFilterState() {
    ['nature', 'museums', 'restaurants'].forEach((catKey) => {
      const isVisible = this.visibleMapCategories[catKey] !== false;
      const btn = document.querySelector(`[data-map-category="${catKey}"]`);
      if (btn) {
        btn.classList.toggle('active', isVisible);
        btn.classList.toggle('hidden-cat', !isVisible);
        btn.setAttribute('aria-pressed', String(isVisible));
      }

      const markers = (this.categoryLeafletMarkers && this.categoryLeafletMarkers[catKey]) || [];
      markers.forEach((marker) => {
        if (!this.leafletMap) return;
        if (isVisible) {
          if (!this.leafletMap.hasLayer(marker)) {
            marker.addTo(this.leafletMap);
          }
        } else if (this.leafletMap.hasLayer(marker)) {
          this.leafletMap.removeLayer(marker);
        }
      });
    });

    // Update bottom chip bar to reflect visible categories
    const chipsBar = document.getElementById('exploreMapChipsBar');
    if (chipsBar) {
      const visibleSpots = this.getCategorizedMapLocations().filter(
        (p) => this.visibleMapCategories[p.mapCategory || 'nature'] !== false
      );
      chipsBar.innerHTML = visibleSpots
        .map(
          (p, idx) => `
          <button class="chip-btn ${idx === 0 ? 'active' : ''}" data-place-chip="${p.id}" onclick="window.journeyApp.flyToTrendingPlace('${p.id}')">
            ${p.pinIcon || '📍'} ${p.nameVi}
          </button>
        `
        )
        .join('');
    }
  }

  flyToTrendingPlace(placeId) {
    const allLocations = this.getCategorizedMapLocations();
    const place =
      allLocations.find((p) => p.id === placeId) ||
      this.getAllVisitedPlaces().find((p) => p.id === placeId) ||
      DESTINATIONS[placeId];
    if (!place) return;

    // Ensure the place's category is visible if clicked
    if (place.mapCategory && this.visibleMapCategories[place.mapCategory] === false) {
      this.visibleMapCategories[place.mapCategory] = true;
      this.applyMapCategoryFilterState();
    }

    document.querySelectorAll('[data-place-chip]').forEach((btn) => {
      btn.classList.toggle('active', btn.getAttribute('data-place-chip') === placeId);
    });

    if (this.leafletMap && this.leafletMarkers[placeId]) {
      this.leafletMap.flyTo([place.lat, place.lng], 10, { duration: 0.85 });
      this.leafletMarkers[placeId].openPopup();
    }
  }

  normalizeViText(str) {
    if (!str) return '';
    return String(str)
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/đ/g, 'd')
      .replace(/Đ/g, 'D')
      .toLowerCase()
      .trim();
  }

  getVietnamSearchCatalog() {
    const visitedPlaces = this.getAllVisitedPlaces().map((p) => ({
      key: p.id,
      placeId: p.id,
      nameVi: p.nameVi,
      province: p.province || 'Việt Nam',
      lat: p.lat,
      lng: p.lng,
      pinIcon: p.pinIcon || '📍',
      isVisited: true,
      keywords: `${p.nameVi} ${p.nameEn || ''} ${p.province || ''}`
    }));

    const extraGazetteer = [
      {
        key: 'vanmieu',
        placeId: 'hanoi_temple',
        nameVi: 'Văn Miếu – Quốc Tử Giám · Hà Nội',
        province: 'Hà Nội',
        lat: 21.0278,
        lng: 105.8355,
        pinIcon: '⛩️',
        isVisited: true,
        keywords: 'ha noi van mieu quoc tu giam thu do ho guom'
      },
      {
        key: 'danang',
        placeId: null,
        nameVi: 'Cầu Rồng — TP. Đà Nẵng',
        province: 'Đà Nẵng',
        lat: 16.0611,
        lng: 108.2272,
        pinIcon: '🌉',
        isVisited: false,
        keywords: 'da nang cau rong cau vang ba na hills bien my khe'
      },
      {
        key: 'hue',
        placeId: null,
        nameVi: 'Kinh Thành Huế — Cố Đô Huế',
        province: 'Thừa Thiên Huế',
        lat: 16.4698,
        lng: 107.5784,
        pinIcon: '🏯',
        isVisited: false,
        keywords: 'hue co do dai noi song huong thua thien hue'
      },
      {
        key: 'ninhbinh',
        placeId: null,
        nameVi: 'Quần Thể Danh Thắng Tràng An',
        province: 'Ninh Bình',
        lat: 20.2506,
        lng: 105.9745,
        pinIcon: '🚣',
        isVisited: false,
        keywords: 'ninh binh trang an tam coc bai dinh hang mua'
      },
      {
        key: 'dalat',
        placeId: null,
        nameVi: 'Quảng Trường Lâm Viên — Đà Lạt',
        province: 'Lâm Đồng',
        lat: 11.9404,
        lng: 108.4368,
        pinIcon: '🌲',
        isVisited: false,
        keywords: 'da lat lam dong ho xuan huong langbiang'
      },
      {
        key: 'nhatrang',
        placeId: null,
        nameVi: 'Vịnh Nha Trang',
        province: 'Khánh Hòa',
        lat: 12.2388,
        lng: 109.1967,
        pinIcon: '🏖️',
        isVisited: false,
        keywords: 'nha trang khanh hoa vinpearl thap ba ponagar'
      },
      {
        key: 'tphcm',
        placeId: null,
        nameVi: 'Bến Nhà Rồng — TP. Hồ Chí Minh',
        province: 'TP. Hồ Chí Minh',
        lat: 10.7683,
        lng: 106.7068,
        pinIcon: '🏙️',
        isVisited: false,
        keywords: 'tp ho chi minh sai gon saigon hcm ben thanh nguyen hue'
      },
      {
        key: 'phuquoc',
        placeId: null,
        nameVi: 'Đảo Ngọc Phú Quốc',
        province: 'Kiên Giang',
        lat: 10.227,
        lng: 103.9632,
        pinIcon: '🏝️',
        isVisited: false,
        keywords: 'phu quoc kien giang dao ngoc rach gia'
      },
      {
        key: 'hagiang',
        placeId: null,
        nameVi: 'Cột Cờ Lũng Cú — Hà Giang',
        province: 'Hà Giang',
        lat: 23.3633,
        lng: 105.3168,
        pinIcon: '🏔️',
        isVisited: false,
        keywords: 'ha giang lung cu dong van ma pi leng nho que'
      },
      {
        key: 'cantho',
        placeId: null,
        nameVi: 'Chợ Nổi Cái Răng — Cần Thơ',
        province: 'Cần Thơ',
        lat: 10.0036,
        lng: 105.7448,
        pinIcon: '🛶',
        isVisited: false,
        keywords: 'can tho ben ninh kieu cho noi cai rang mien tay'
      },
      {
        key: 'hoangsa',
        placeId: null,
        nameVi: 'Quần đảo Hoàng Sa (Việt Nam)',
        province: 'Đà Nẵng',
        lat: 16.53,
        lng: 111.62,
        pinIcon: '🇻🇳',
        isVisited: true,
        keywords: 'hoang sa quan dao hoang sa bien dong viet nam'
      },
      {
        key: 'truongsa',
        placeId: null,
        nameVi: 'Quần đảo Trường Sa (Việt Nam)',
        province: 'Khánh Hòa',
        lat: 9.65,
        lng: 114.28,
        pinIcon: '🇻🇳',
        isVisited: true,
        keywords: 'truong sa quan dao truong sa khanh hoa bien dong viet nam'
      }
    ];

    const seenKeys = new Set(visitedPlaces.map((v) => v.key));
    const merged = [...visitedPlaces];
    extraGazetteer.forEach((item) => {
      if (!seenKeys.has(item.key)) {
        seenKeys.add(item.key);
        merged.push(item);
      }
    });
    return merged;
  }

  onMapSearchInput(rawQuery) {
    const query = (rawQuery || '').trim();
    const clearBtn = document.getElementById('mapSearchClearBtn');
    const suggestionsEl = document.getElementById('mapSearchSuggestions');

    if (clearBtn) {
      clearBtn.classList.toggle('visible', query.length > 0);
    }
    if (!suggestionsEl) return;

    if (!query) {
      suggestionsEl.classList.remove('open');
      suggestionsEl.innerHTML = '';
      return;
    }

    const normQuery = this.normalizeViText(query);
    const catalog = this.getVietnamSearchCatalog();
    const matches = catalog.filter((item) => {
      const haystack = this.normalizeViText(`${item.nameVi} ${item.province} ${item.keywords}`);
      return haystack.includes(normQuery);
    });

    if (matches.length === 0) {
      suggestionsEl.innerHTML = `
        <div style="padding:10px 12px;font-size:0.78rem;color:var(--text-secondary);">
          Không tìm thấy "${this.escapeHtml(query)}". Hãy thử: <strong>Hà Nội, Vịnh Hạ Long, Sa Pa, Đà Nẵng, Hội An, Huế, Đà Lạt, Phú Quốc</strong>.
        </div>
      `;
      suggestionsEl.classList.add('open');
      return;
    }

    suggestionsEl.innerHTML = matches
      .slice(0, 7)
      .map(
        (item) => `
        <button
          type="button"
          class="map-search-suggestion-item"
          onclick="window.journeyApp.quickJumpVietnamLocation('${item.key}')"
        >
          <div style="display:flex;align-items:center;gap:9px;min-width:0;">
            <span style="font-size:1.1rem;flex-shrink:0;">${item.pinIcon}</span>
            <div style="min-width:0;">
              <div style="font-size:0.82rem;font-weight:700;color:var(--text-primary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                ${item.nameVi}
              </div>
              <div style="font-size:0.7rem;color:var(--text-secondary);">
                📍 ${item.province} · ${item.lat.toFixed(2)}°N, ${item.lng.toFixed(2)}°E
              </div>
            </div>
          </div>
          <span style="font-size:0.66rem;font-weight:800;padding:3px 8px;border-radius:999px;background:${item.isVisited ? 'var(--accent-sage-soft)' : 'rgba(185,155,114,0.18)'};color:${item.isVisited ? 'var(--accent-sage)' : 'var(--accent-gold)'};flex-shrink:0;">
            ${item.isVisited ? '✓ Đã đến' : 'Bay tới →'}
          </span>
        </button>
      `
      )
      .join('');
    suggestionsEl.classList.add('open');
  }

  onMapSearchKeydown(event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      this.submitMapSearch();
    } else if (event.key === 'Escape') {
      const suggestionsEl = document.getElementById('mapSearchSuggestions');
      if (suggestionsEl) suggestionsEl.classList.remove('open');
    }
  }

  clearMapSearch() {
    const input = document.getElementById('mapSearchInput');
    const clearBtn = document.getElementById('mapSearchClearBtn');
    const suggestionsEl = document.getElementById('mapSearchSuggestions');
    if (input) {
      input.value = '';
      input.focus();
    }
    if (clearBtn) clearBtn.classList.remove('visible');
    if (suggestionsEl) {
      suggestionsEl.classList.remove('open');
      suggestionsEl.innerHTML = '';
    }
  }

  submitMapSearch() {
    const input = document.getElementById('mapSearchInput');
    const query = input ? input.value.trim() : '';
    if (!query) {
      this.showToast('🔍 Nhập tên thành phố hoặc địa danh Việt Nam để di chuyển nhanh!');
      if (input) input.focus();
      return;
    }

    const normQuery = this.normalizeViText(query);
    const catalog = this.getVietnamSearchCatalog();
    const match = catalog.find((item) => {
      const haystack = this.normalizeViText(`${item.nameVi} ${item.province} ${item.keywords}`);
      return haystack.includes(normQuery);
    });

    if (match) {
      this.quickJumpVietnamLocation(match.key);
    } else {
      this.showToast(`📍 Không tìm thấy "${query}" — Hãy thử Hà Nội, Hạ Long, Đà Nẵng, Hội An, Phú Quốc!`);
    }
  }

  quickJumpVietnamLocation(locationKey) {
    const catalog = this.getVietnamSearchCatalog();
    const item =
      catalog.find((c) => c.key === locationKey || c.placeId === locationKey) ||
      catalog[0];
    if (!item) return;

    const input = document.getElementById('mapSearchInput');
    const clearBtn = document.getElementById('mapSearchClearBtn');
    const suggestionsEl = document.getElementById('mapSearchSuggestions');

    if (input) input.value = `${item.nameVi}`;
    if (clearBtn) clearBtn.classList.add('visible');
    if (suggestionsEl) suggestionsEl.classList.remove('open');

    // If this location already has a visited marker on the map, fly to it and open its popup
    if (item.placeId && this.leafletMarkers && this.leafletMarkers[item.placeId]) {
      this.flyToTrendingPlace(item.placeId);
      this.showToast(`📍 Đã di chuyển tới: ${item.nameVi} (${item.province})`);
      return;
    }

    if (this.leafletMap && typeof window.L !== 'undefined') {
      if (this.searchHighlightMarker && this.leafletMap.hasLayer(this.searchHighlightMarker)) {
        this.leafletMap.removeLayer(this.searchHighlightMarker);
      }

      const searchIcon = window.L.divIcon({
        className: 'custom-landmark-marker',
        html: `
          <div class="marker-hover-container" title="${item.nameVi}">
            <div class="marker-pin-pill" style="border-color:#F59E0B;">
              <span class="marker-pin-emoji">${item.pinIcon || '📍'}</span>
              <span>${item.nameVi}</span>
            </div>
            <div class="marker-pin-stem"></div>
            <div class="marker-ground-shadow"></div>
          </div>
        `,
        iconSize: [160, 48],
        iconAnchor: [80, 42],
        popupAnchor: [0, -38]
      });

      this.searchHighlightMarker = window.L.marker([item.lat, item.lng], {
        icon: searchIcon
      }).addTo(this.leafletMap);

      this.searchHighlightMarker
        .bindPopup(
          `
          <div style="padding:8px 6px;text-align:center;">
            <div style="font-size:0.9rem;font-weight:800;color:var(--text-primary);margin-bottom:3px;">${item.pinIcon} ${item.nameVi}</div>
            <div style="font-size:0.74rem;color:var(--text-secondary);margin-bottom:8px;">📍 ${item.province} · ${item.lat.toFixed(4)}°N, ${item.lng.toFixed(4)}°E</div>
            <button type="button" class="journey-popup-view-details-btn" onclick="window.journeyApp.openAddPlaceModal()">+ Đánh dấu đã đến đây</button>
          </div>
        `
        )
        .openPopup();

      this.leafletMap.flyTo([item.lat, item.lng], 10, { duration: 0.85 });
      this.showToast(`📍 Đã nhảy tới: ${item.nameVi} (${item.province})`);
    }
  }

  fitAllTrendingMarkers() {
    if (!this.leafletMap || typeof window.L === 'undefined') return;
    this.leafletMap.flyToBounds(
      [
        [8.5, 102.2],
        [23.4, 114.6]
      ],
      { padding: [18, 18], duration: 0.85 }
    );
  }

  viewPlaceOnMap(placeId) {
    this.switchTab('explore');
    setTimeout(() => {
      this.flyToTrendingPlace(placeId);
    }, 280);
  }

  openAddPlaceModal() {
    const presetButtons = VIETNAM_PRESET_PLACES.map(
      (p, idx) => `
      <button type="button" class="chip-btn" onclick="window.journeyApp.fillVietnamPreset(${idx})">
        ${p.pinIcon} ${p.nameVi}
      </button>
    `
    ).join('');

    this.openModal(`
      <div class="modal-header-row">
        <h3 class="serif-title" style="font-size:1.15rem;">📍 Đánh dấu nơi mới đã đến tại Việt Nam</h3>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div style="margin-bottom:10px;">
        <div style="font-size:0.74rem;font-weight:700;color:var(--text-secondary);margin-bottom:6px;">Chọn nhanh địa danh Việt Nam:</div>
        <div style="display:flex;gap:6px;flex-wrap:wrap;">
          ${presetButtons}
        </div>
      </div>
      <div class="profile-form" style="padding:0;">
        <div class="form-group">
          <label class="form-label">Tên địa danh / Thành phố</label>
          <input id="newPlaceName" class="form-input" type="text" placeholder="VD: Cầu Rồng Đà Nẵng, Đà Lạt, Phú Quốc..." />
        </div>
        <div class="form-group">
          <label class="form-label">Tỉnh / Thành Việt Nam</label>
          <input id="newPlaceProvince" class="form-input" type="text" placeholder="VD: Đà Nẵng, Lâm Đồng, Kiên Giang..." />
        </div>
        <div class="profile-form-grid-2">
          <div class="form-group">
            <label class="form-label">Vĩ độ (Latitude)</label>
            <input id="newPlaceLat" class="form-input" type="number" step="0.0001" value="16.0611" />
          </div>
          <div class="form-group">
            <label class="form-label">Kinh độ (Longitude)</label>
            <input id="newPlaceLng" class="form-input" type="number" step="0.0001" value="108.2272" />
          </div>
        </div>
        <button class="btn-save-profile" onclick="window.journeyApp.saveNewVisitedPlace()">
          🗺️ Lưu vào Bản đồ Việt Nam
        </button>
      </div>
    `);
  }

  fillVietnamPreset(idx) {
    const p = VIETNAM_PRESET_PLACES[idx];
    if (!p) return;
    const nameEl = document.getElementById('newPlaceName');
    const provEl = document.getElementById('newPlaceProvince');
    const latEl = document.getElementById('newPlaceLat');
    const lngEl = document.getElementById('newPlaceLng');
    if (nameEl) nameEl.value = p.nameVi;
    if (provEl) provEl.value = p.province;
    if (latEl) latEl.value = p.lat;
    if (lngEl) lngEl.value = p.lng;
  }

  saveNewVisitedPlace() {
    const nameEl = document.getElementById('newPlaceName');
    const provEl = document.getElementById('newPlaceProvince');
    const latEl = document.getElementById('newPlaceLat');
    const lngEl = document.getElementById('newPlaceLng');
    if (!nameEl || !nameEl.value.trim()) {
      this.showToast('⚠️ Vui lòng nhập tên địa điểm!');
      return;
    }
    const nameVi = nameEl.value.trim();
    const province = provEl && provEl.value.trim() ? provEl.value.trim() : 'Việt Nam';
    const lat = parseFloat(latEl ? latEl.value : '16.0611') || 16.0611;
    const lng = parseFloat(lngEl ? lngEl.value : '108.2272') || 108.2272;

    const newPlace = {
      id: 'custom_' + Date.now(),
      rank: 'ĐÃ ĐẾN',
      nameVi,
      nameEn: nameVi,
      province,
      categoryVi: 'Điểm check-in của tôi',
      rating: '5.0',
      visitedDate: new Date().toLocaleDateString('vi-VN'),
      coords: `${lat.toFixed(4)}° N, ${lng.toFixed(4)}° E`,
      lat,
      lng,
      pinIcon: '📌',
      image: 'images/img_hero_halong_bay.jpg',
      shortDescVi: `Cột mốc hành trình tại ${nameVi}, ${province}.`,
      storyVi: `Bạn đã đánh dấu đặt chân đến ${nameVi} (${province}) trên bản đồ Việt Nam.`
    };

    this.customPlaces.push(newPlace);
    this.saveJson('journey_custom_places', this.customPlaces);
    this.closeModal();
    this.showToast(`🎉 Đã thêm "${nameVi}" vào Bản đồ Việt Nam!`);
    if (this.activeTab === 'explore') {
      this.renderExploreTab();
    }
  }

  /* =========================================================================
     PERMISSION HANDLER FOR CAMERA & MICROPHONE BEFORE OPENING LOCKET VIEW
     ========================================================================= */
  async queryBrowserPermissions() {
    if (this.cameraStream && this.cameraStream.active) {
      this.cameraPermState = 'granted';
      localStorage.setItem('journey_cam_perm', 'granted');
      return;
    }
    if (!navigator.permissions || typeof navigator.permissions.query !== 'function') {
      return;
    }
    try {
      const camRes = await navigator.permissions.query({ name: 'camera' });
      if (camRes && camRes.state) {
        if (!(this.cameraStream && this.cameraStream.active)) {
          this.cameraPermState = camRes.state;
          localStorage.setItem('journey_cam_perm', camRes.state);
        }
        camRes.onchange = () => {
          if (camRes.state === 'granted' || !(this.cameraStream && this.cameraStream.active)) {
            this.cameraPermState = camRes.state;
            localStorage.setItem('journey_cam_perm', camRes.state);
            if (this.activeTab === 'discover') this.renderLocketCameraTab();
          }
        };
      }
    } catch (_e) {
      // Safari / WebView fallback
    }

    try {
      const micRes = await navigator.permissions.query({ name: 'microphone' });
      if (micRes && micRes.state) {
        this.micPermState = micRes.state;
        localStorage.setItem('journey_mic_perm', micRes.state);
      }
    } catch (_e) {
      // Safari / WebView fallback
    }
  }

  async requestCameraAndMicPermissions() {
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
      this.cameraPermState = 'denied';
      this.micPermState = 'denied';
      this.permissionGateBypassed = true;
      this.showToast('ℹ️ Thiết bị không hỗ trợ camera trực tiếp — Đã mở chế độ chụp mẫu.');
      this.renderLocketCameraTab();
      return false;
    }

    const reqBtn = document.getElementById('btnRequestCamMicPerm');
    if (reqBtn) {
      reqBtn.disabled = true;
      reqBtn.textContent = '⏳ Đang kết nối Máy ảnh...';
    }

    this.stopCameraStream();

    // Try Camera + Microphone first, then fallback to Camera-only so camera ALWAYS works immediately
    let acquiredStream = null;
    try {
      acquiredStream = await navigator.mediaDevices.getUserMedia({
        video: {
          facingMode: this.cameraFacingMode,
          width: { ideal: 720 },
          height: { ideal: 720 }
        },
        audio: true
      });
      this.micPermState = 'granted';
      localStorage.setItem('journey_mic_perm', 'granted');
    } catch (_bothErr) {
      try {
        acquiredStream = await navigator.mediaDevices.getUserMedia({
          video: {
            facingMode: this.cameraFacingMode,
            width: { ideal: 720 },
            height: { ideal: 720 }
          },
          audio: false
        });
        this.micPermState = 'denied';
        localStorage.setItem('journey_mic_perm', 'denied');
      } catch (_camErr) {
        this.cameraPermState = 'denied';
        localStorage.setItem('journey_cam_perm', 'denied');
        this.showToast('⚠️ Quyền Máy ảnh bị từ chối. Vui lòng cấp quyền hoặc chọn ảnh từ thư viện.');
        this.renderLocketCameraTab();
        return false;
      }
    }

    if (acquiredStream) {
      this.cameraStream = acquiredStream;
      this.cameraPermState = 'granted';
      this.permissionGateBypassed = true;
      localStorage.setItem('journey_cam_perm', 'granted');

      // Immediately reveal viewfinder & live controls in-place without waiting or switching tabs!
      const gateEl = document.getElementById('locketPermissionGate');
      const vfEl = document.getElementById('locketViewfinder');
      const liveCtrlEl = document.getElementById('locketLiveControls');
      const videoEl = document.getElementById('locketLiveVideo');
      const fallbackImg = document.getElementById('locketFallbackImg');
      const badgeEl = document.getElementById('locketModeBadge');

      if (gateEl) gateEl.style.display = 'none';
      if (vfEl) vfEl.style.display = 'block';
      if (liveCtrlEl) liveCtrlEl.style.display = 'flex';
      if (badgeEl) badgeEl.textContent = '● SẴN SÀNG CHỤP';

      if (videoEl) {
        videoEl.srcObject = acquiredStream;
        videoEl.style.display = 'block';
        videoEl.play().catch(() => {});
      }
      if (fallbackImg) fallbackImg.style.display = 'none';

      this.bindShutterPressAndHoldEvents();
      this.showToast('📷 Máy ảnh đã sẵn sàng! Chạm nút tròn để chụp ngay.');
      return true;
    }
    return false;
  }

  openPermissionGateModal() {
    this.permissionGateBypassed = false;
    this.renderLocketCameraTab();
  }

  /* =========================================================================
     TAB 3: ỐNG KÍNH — CHỤP ẢNH TỨC THÌ + TÙY CHỌN GẮN VỊ TRÍ THỰC, NHẠC SPOTIFY, NHÃN DÁN, NHIỆT ĐỘ, THỜI GIAN
     ========================================================================= */
  renderLocketCameraTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    // Non-blocking permission check so DOM renders synchronously and camera stream never stalls
    this.queryBrowserPermissions();

    const currentPlace = DESTINATIONS.halong;
    const hasCaptured = Boolean(this.capturedDataUrl || this.capturedVideoUrl);
    const hasReadyCamera =
      this.cameraPermState === 'granted' ||
      Boolean(this.cameraStream && this.cameraStream.active);
    const showPermissionGate = !hasReadyCamera && !this.permissionGateBypassed && !hasCaptured;

    const friendsChipsHtml = this.friendsList.map((f) => {
      const isSelected = this.selectedFriends.includes(f.name);
      return `
        <button
          type="button"
          class="friend-select-chip ${isSelected ? 'selected' : ''}"
          onclick="window.journeyApp.toggleAllowedFriend('${f.name}')"
        >
          <span>${isSelected ? '✓' : '+'}</span>
          <span>${f.name}</span>
        </button>
      `;
    }).join('');

    const nowTimeStr = new Date().toLocaleTimeString('vi-VN', {
      hour: '2-digit',
      minute: '2-digit'
    });

    const topMetaParts = [];
    if (this.camSticker) topMetaParts.push(this.camSticker);
    if (this.camEnableTime) topMetaParts.push(`🕒 ${nowTimeStr}`);
    if (this.camEnableTemp && this.camTemperatureText) topMetaParts.push(`🌡️ ${this.camTemperatureText}`);

    const stickerOptions = [
      '✨ Chill',
      '☕ Cà phê',
      '🍜 Ăn ngon',
      '🌅 Hoàng hôn',
      '🎉 Vui vẻ',
      '❤️ Yêu thương',
      '💪 Năng lượng',
      '🌿 Bình yên'
    ];
    const spotifyNowPlayingTracks = [
      {
        id: 'sp_1',
        title: 'Đừng Làm Trái Tim Anh Đau',
        artist: 'Sơn Tùng M-TP',
        duration: '04:39',
        coverIcon: '💿',
        status: 'Now Playing'
      },
      {
        id: 'sp_2',
        title: 'Bình Yên',
        artist: 'Vũ. ft. Binz',
        duration: '03:45',
        coverIcon: '🌿',
        status: 'Playing Next'
      },
      {
        id: 'sp_3',
        title: 'Một Đêm Say',
        artist: 'Thịnh Suy',
        duration: '03:18',
        coverIcon: '🎸',
        status: 'Recent'
      },
      {
        id: 'sp_4',
        title: 'Chúng Ta Của Tương Lai',
        artist: 'Sơn Tùng M-TP',
        duration: '04:09',
        coverIcon: '✨',
        status: 'Recent'
      },
      {
        id: 'sp_5',
        title: 'Thích Em Hơi Nhiều',
        artist: 'Wren Evans',
        duration: '02:54',
        coverIcon: '🎧',
        status: 'Recent'
      }
    ];
    const tempPresets = ['26°C ☀️', '23°C 🌤️', '29°C 🔥', '20°C 🌧️', '17°C ❄️'];

    let widgetDrawerHtml = '';
    if (this.camActiveWidget === 'location') {
      widgetDrawerHtml = `
        <div class="camera-widget-drawer" data-testid="location-widget-drawer">
          <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:6px;">
            <strong style="font-size:0.78rem;color:var(--text-primary);">
              ${this.isFetchingCamGps ? '📡 Đang lấy tọa độ GPS hiện tại...' : '📍 Gắn vị trí hiện tại của bạn'}
            </strong>
            <div style="display:flex;gap:8px;">
              <button type="button" class="section-link" style="font-size:0.72rem;" onclick="window.journeyApp.detectRealGpsForCamera()">🎯 Định vị GPS</button>
              <button type="button" class="section-link" style="font-size:0.72rem;color:var(--text-secondary);" onclick="window.journeyApp.clearCameraLocation()">✕ Không gắn vị trí</button>
            </div>
          </div>
          <input
            type="text"
            class="form-input"
            value="${this.camLocationName}"
            placeholder="Nhập vị trí hiện tại (hoặc để trống nếu không gắn)..."
            oninput="window.journeyApp.onInputCameraLocation(this.value)"
          />
        </div>
      `;
    } else if (this.camActiveWidget === 'spotify') {
      widgetDrawerHtml = `
        <div id="spotifyMusicDrawer" class="camera-widget-drawer spotify-music-drawer" data-testid="spotify-music-drawer">
          <div class="spotify-drawer-header">
            <div style="display:flex;align-items:center;gap:7px;">
              <span class="spotify-eq-bars" aria-hidden="true">
                <span></span><span></span><span></span>
              </span>
              <div>
                <div style="font-size:0.8rem;font-weight:800;color:var(--text-primary);">
                  Spotify Music · Đang phát (Now Playing)
                </div>
                <div style="font-size:0.68rem;color:var(--text-secondary);">
                  Chọn bài hát đang phát để gắn lên ảnh hoặc video của bạn
                </div>
              </div>
            </div>
            ${
              this.camSpotifyTrack
                ? `<button type="button" class="section-link" style="font-size:0.72rem;color:#EF4444;" onclick="window.journeyApp.selectCameraSpotify('')">✕ Gỡ nhạc</button>`
                : `<button type="button" class="section-link" style="font-size:0.72rem;color:var(--text-secondary);" onclick="window.journeyApp.toggleCameraWidget('spotify')">Đóng</button>`
            }
          </div>

          <div class="spotify-track-list" role="listbox" aria-label="Spotify Now Playing Tracks">
            ${spotifyNowPlayingTracks
              .map((item) => {
                const fullLabel = `${item.title} - ${item.artist}`;
                const isSelected = this.camSpotifyTrack === fullLabel;
                return `
                  <button
                    type="button"
                    class="spotify-track-item ${isSelected ? 'selected' : ''}"
                    data-testid="spotify-track-${item.id}"
                    onclick="window.journeyApp.selectCameraSpotify('${fullLabel}')"
                  >
                    <div class="spotify-track-cover">${item.coverIcon}</div>
                    <div class="spotify-track-info">
                      <div class="spotify-track-title">${item.title}</div>
                      <div class="spotify-track-artist">${item.artist} · ${item.duration}</div>
                    </div>
                    <span class="spotify-track-badge ${isSelected ? 'active' : ''}">
                      ${isSelected ? '● Đang gắn' : item.status === 'Now Playing' ? '🟢 Now Playing' : '+ Chọn'}
                    </span>
                  </button>
                `;
              })
              .join('')}
          </div>

          <div style="margin-top:6px;">
            <input
              id="spotifyCustomTrackInput"
              type="text"
              class="form-input"
              style="padding:8px 11px;font-size:0.78rem;"
              value="${this.camSpotifyTrack}"
              placeholder="Hoặc nhập tên bài hát - nghệ sĩ / dán link Spotify..."
              oninput="window.journeyApp.onInputCameraSpotify(this.value)"
            />
          </div>
        </div>
      `;
    } else if (this.camActiveWidget === 'sticker') {
      widgetDrawerHtml = `
        <div class="camera-widget-drawer">
          <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:6px;">
            <strong style="font-size:0.78rem;color:var(--text-primary);">✨ Chọn nhãn dán cảm xúc</strong>
            ${
              this.camSticker
                ? `<button type="button" class="section-link" style="font-size:0.72rem;color:var(--text-secondary);" onclick="window.journeyApp.selectCameraSticker('')">✕ Bỏ nhãn</button>`
                : ''
            }
          </div>
          <div style="display:flex;gap:6px;flex-wrap:wrap;">
            ${stickerOptions
              .map(
                (st) => `
                  <button
                    type="button"
                    class="chip-btn ${this.camSticker === st ? 'active' : ''}"
                    onclick="window.journeyApp.selectCameraSticker('${st}')"
                  >
                    ${st}
                  </button>
                `
              )
              .join('')}
          </div>
        </div>
      `;
    } else if (this.camActiveWidget === 'temp') {
      widgetDrawerHtml = `
        <div class="camera-widget-drawer">
          <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:6px;">
            <strong style="font-size:0.78rem;color:var(--text-primary);">🌡️ Nhiệt độ hiện tại</strong>
            <button type="button" class="section-link" style="font-size:0.72rem;" onclick="window.journeyApp.toggleCameraTempOverlay()">
              ${this.camEnableTemp ? '✕ Ẩn nhiệt độ' : '✓ Hiện nhiệt độ'}
            </button>
          </div>
          <div style="display:flex;gap:6px;flex-wrap:wrap;">
            ${tempPresets
              .map(
                (t) => `
                  <button
                    type="button"
                    class="chip-btn ${this.camEnableTemp && this.camTemperatureText === t ? 'active' : ''}"
                    onclick="window.journeyApp.selectCameraTemp('${t}')"
                  >
                    ${t}
                  </button>
                `
              )
              .join('')}
          </div>
        </div>
      `;
    }

    main.innerHTML = `
      <div class="locket-camera-screen">
        <!-- Minimal Header -->
        <div class="locket-top-pill">
          <span style="font-size:0.92rem;font-weight:800;color:var(--text-primary);">📷 Máy ảnh khoảnh khắc</span>
          <span class="locket-badge" id="locketModeBadge">
            ${
              showPermissionGate
                ? '🔒 CẤP QUYỀN MÁY ẢNH'
                : '● SẴN SÀNG CHỤP'
            }
          </span>
        </div>

        <!-- Clean Camera Permission Card -->
        <div id="locketPermissionGate" class="locket-permission-gate" style="display:${showPermissionGate ? 'flex' : 'none'};">
          <div class="locket-perm-icon-ring">📷</div>
          <div>
            <h3 class="serif-title" style="font-size:1.12rem;margin-bottom:4px;">Cho phép truy cập Máy ảnh</h3>
            <p style="font-size:0.78rem;color:var(--text-secondary);line-height:1.45;">
              Bấm nút bên dưới để mở máy ảnh và chụp trực tiếp ngay lập tức mà không cần chuyển tab.
            </p>
          </div>

          <button
            type="button"
            id="btnRequestCamMicPerm"
            class="btn-save-profile"
            style="width:100%;padding:12px 16px;"
            onclick="window.journeyApp.requestCameraAndMicPermissions()"
          >
            🔓 Cho phép Máy ảnh ngay
          </button>

          <div style="display:flex;gap:8px;width:100%;">
            <label class="chip-btn" style="flex:1;text-align:center;padding:10px 8px;cursor:pointer;">
              🖼️ Hoặc chọn ảnh/video từ điện thoại
              <input type="file" accept="image/*,video/*" style="display:none;" onchange="window.journeyApp.handlePhotoFileUpload(event)" />
            </label>
          </div>
        </div>

        <!-- 1:1 Responsive Square Viewfinder -->
        <div class="locket-viewfinder" id="locketViewfinder" style="display:${showPermissionGate ? 'none' : 'block'};">
          <video
            id="locketLiveVideo"
            class="locket-video ${this.cameraFacingMode === 'user' ? 'mirrored' : ''}"
            autoplay
            playsinline
            webkit-playsinline="true"
            x5-playsinline="true"
            muted
            disablepictureinpicture
          ></video>

          <img
            id="locketFallbackImg"
            src="${this.capturedDataUrl || currentPlace.image}"
            alt="Viewfinder"
            class="locket-preview-img"
            style="${hasCaptured && this.capturedMediaType === 'photo' ? 'display:block;' : 'display:none;'}"
          />

          <video
            id="locketRecordedVideoPreview"
            src="${this.capturedVideoUrl || ''}"
            class="locket-preview-video"
            autoplay
            loop
            muted
            playsinline
            webkit-playsinline="true"
            style="${hasCaptured && this.capturedMediaType === 'video' ? 'display:block;' : 'display:none;'}"
          ></video>

          <canvas id="locketCaptureCanvas" width="640" height="640" style="display:none;"></canvas>

          <div class="locket-viewfinder-overlay">
            <div>
              <div style="display:flex;justify-content:space-between;align-items:center;gap:6px;">
                <span id="locketTopMetaPill" style="background:rgba(15,23,42,0.75);backdrop-filter:blur(8px);color:#fff;font-size:0.72rem;font-weight:700;padding:5px 11px;border-radius:999px;display:${topMetaParts.length > 0 ? 'inline-block' : 'none'};">
                  ${topMetaParts.join('  ·  ')}
                </span>
                <span id="locketCameraStatus" style="background:rgba(15,23,42,0.65);backdrop-filter:blur(8px);color:#E2E8F0;font-size:0.68rem;font-weight:700;padding:4px 9px;border-radius:999px;">
                  ${hasCaptured ? '✨ Đã chụp' : '🟢 Chạm để chụp'}
                </span>
              </div>
              <div class="locket-rec-progress-wrap" id="locketRecProgressWrap">
                <div class="locket-rec-progress-bar" id="locketRecProgressBar"></div>
              </div>
            </div>

            <div style="display:flex;flex-direction:column;gap:6px;align-items:flex-start;">
              <div
                id="locketLiveSpotifyPill"
                data-testid="viewfinder-spotify-overlay"
                style="display:${this.camSpotifyTrack ? 'inline-flex' : 'none'};align-items:center;gap:7px;background:rgba(16,69,39,0.9);backdrop-filter:blur(8px);border:1px solid #22C55E;color:#DCFCE7;font-size:0.73rem;font-weight:700;padding:6px 12px;border-radius:999px;max-width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;box-shadow:0 4px 12px rgba(0,0,0,0.28);"
              >
                <span class="spotify-disc-spin">💿</span>
                <span id="locketLiveSpotifyText" style="overflow:hidden;text-overflow:ellipsis;">${this.camSpotifyTrack}</span>
              </div>

              <div
                id="locketLiveLocationPill"
                style="display:${this.camAttachLocation && this.camLocationName ? 'inline-flex' : 'none'};align-items:center;gap:6px;background:rgba(15,23,42,0.82);border:1px solid rgba(212,175,55,0.65);color:#F3E5AB;font-size:0.74rem;font-weight:700;padding:5px 12px;border-radius:999px;max-width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"
              >
                📍 <span id="locketLiveTimestampPlace">${this.camLocationName}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Optional Widgets Bar (Location, Spotify Music, Stickers, Temperature, Time) -->
        <div class="camera-widgets-strip" style="display:${showPermissionGate ? 'none' : 'flex'};">
          <button
            type="button"
            id="btnSpotifyMusicWidget"
            data-testid="spotify-music-widget-btn"
            class="chip-btn spotify-widget-btn ${this.camSpotifyTrack || this.camActiveWidget === 'spotify' ? 'active' : ''}"
            aria-expanded="${this.camActiveWidget === 'spotify' ? 'true' : 'false'}"
            onclick="window.journeyApp.toggleCameraWidget('spotify')"
          >
            🎵 ${this.camSpotifyTrack ? `Spotify Music · ${this.camSpotifyTrack.split(' - ')[0]}` : 'Spotify Music'}
          </button>

          <button
            type="button"
            class="chip-btn ${this.camAttachLocation ? 'active' : ''}"
            onclick="window.journeyApp.toggleCameraWidget('location')"
          >
            📍 ${this.camAttachLocation && this.camLocationName ? 'Đã gắn vị trí' : 'Gắn vị trí'}
          </button>

          <button
            type="button"
            class="chip-btn ${this.camSticker ? 'active' : ''}"
            onclick="window.journeyApp.toggleCameraWidget('sticker')"
          >
            ${this.camSticker ? this.camSticker : '✨ Nhãn dán'}
          </button>

          <button
            type="button"
            class="chip-btn ${this.camEnableTemp ? 'active' : ''}"
            onclick="window.journeyApp.toggleCameraWidget('temp')"
          >
            🌡️ ${this.camEnableTemp ? this.camTemperatureText : 'Nhiệt độ'}
          </button>

          <button
            type="button"
            class="chip-btn ${this.camEnableTime ? 'active' : ''}"
            onclick="window.journeyApp.toggleCameraTimeOverlay()"
          >
            🕒 ${this.camEnableTime ? nowTimeStr : 'Thời gian: Tắt'}
          </button>
        </div>

        ${widgetDrawerHtml}

        <!-- Post Capture Panel (Caption + Privacy + Post Button) -->
        <div id="locketPostCaptureControls" style="width:100%;display:${hasCaptured && !showPermissionGate ? 'flex' : 'none'};flex-direction:column;align-items:center;gap:10px;">
          <div class="locket-publish-panel">
            <input
              id="locketCaptionInput"
              type="text"
              class="form-input"
              placeholder="Viết ghi chú cho khoảnh khắc này..."
              maxlength="100"
            />

            <div class="privacy-selector-row">
              <button
                type="button"
                class="privacy-option-btn ${this.postPrivacy === 'public' ? 'active' : ''}"
                onclick="window.journeyApp.setPostPrivacy('public')"
              >
                🌎 Tất cả bạn bè
              </button>
              <button
                type="button"
                class="privacy-option-btn ${this.postPrivacy === 'custom' ? 'active' : ''}"
                onclick="window.journeyApp.setPostPrivacy('custom')"
              >
                👥 Chọn bạn
              </button>
              <button
                type="button"
                class="privacy-option-btn ${this.postPrivacy === 'private' ? 'active' : ''}"
                onclick="window.journeyApp.setPostPrivacy('private')"
              >
                🔒 Chỉ mình tôi
              </button>
            </div>

            <div id="customFriendsPicker" style="display:${this.postPrivacy === 'custom' ? 'block' : 'none'};">
              <div class="friends-MultiSelect-row">
                ${friendsChipsHtml}
              </div>
            </div>

            <div style="display:flex;gap:8px;margin-top:4px;">
              <button type="button" class="chip-btn" style="padding:11px 16px;" onclick="window.journeyApp.retakeLocketPhoto()">
                🔄 Chụp lại
              </button>
              <button type="button" class="btn-save-profile" style="flex:1;padding:11px 16px;" onclick="window.journeyApp.publishLocketPhoto()">
                🚀 Đăng khoảnh khắc
              </button>
            </div>
          </div>
        </div>

        <!-- Shutter Controls (Tap = Photo, Hold = Short Video) -->
        <div id="locketLiveControls" style="width:100%;display:${!hasCaptured && !showPermissionGate ? 'flex' : 'none'};flex-direction:column;align-items:center;gap:8px;">
          <div class="locket-controls-bar">
            <label class="locket-side-btn" title="Chọn ảnh/video từ điện thoại" style="cursor:pointer;">
              🖼️
              <input type="file" accept="image/*,video/*" style="display:none;" onchange="window.journeyApp.handlePhotoFileUpload(event)" />
            </label>

            <button
              type="button"
              id="locketShutterBtn"
              class="locket-shutter-outer"
              aria-label="Chạm chụp ảnh hoặc ấn giữ quay video"
            >
              <div class="locket-shutter-inner"></div>
            </button>

            <button type="button" class="locket-side-btn" onclick="window.journeyApp.flipLocketCamera()" title="Đổi camera Trước / Sau">
              🔄
            </button>
          </div>
          <p id="locketHintText" style="font-size:0.74rem;color:var(--text-secondary);text-align:center;">
            Chạm để chụp ảnh · Ấn giữ để quay video ngắn
          </p>
        </div>
      </div>
    `;

    this.bindShutterPressAndHoldEvents();

    if (!hasCaptured && !showPermissionGate) {
      this.startCameraStream();
    }
  }

  toggleCameraWidget(widgetName) {
    if (widgetName === 'location' && !this.camAttachLocation) {
      this.camAttachLocation = true;
      this.camActiveWidget = 'location';
      if (!this.camLocationName) {
        this.detectRealGpsForCamera();
      } else {
        this.renderLocketCameraTab();
      }
      return;
    }
    this.camActiveWidget = this.camActiveWidget === widgetName ? 'none' : widgetName;
    this.renderLocketCameraTab();
  }

  detectRealGpsForCamera() {
    this.camAttachLocation = true;
    if (!navigator.geolocation) {
      this.camLocationName = this.camLocationName || 'Vị trí hiện tại';
      this.renderLocketCameraTab();
      return;
    }
    this.isFetchingCamGps = true;
    this.renderLocketCameraTab();

    navigator.geolocation.getCurrentPosition(
      async (pos) => {
        this.isFetchingCamGps = false;
        const lat = pos.coords.latitude;
        const lng = pos.coords.longitude;
        this.camCoordsLabel = `${lat.toFixed(4)}°N, ${lng.toFixed(4)}°E`;

        // Reverse geocode real place name + fetch real temperature
        try {
          const resp = await fetch(
            `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}&accept-language=vi`
          );
          if (resp.ok) {
            const data = await resp.json();
            const addr = data.address || {};
            const parts = [
              addr.suburb || addr.quarter || addr.road || addr.village,
              addr.city_district || addr.district || addr.town,
              addr.city || addr.state
            ].filter(Boolean);
            this.camLocationName =
              parts.slice(0, 2).join(', ') || data.display_name?.split(',').slice(0, 2).join(',') || this.camCoordsLabel;
          } else {
            this.camLocationName = this.camCoordsLabel;
          }
        } catch (_e) {
          this.camLocationName = this.camCoordsLabel;
        }

        // Also try fetching live temperature from Open-Meteo
        try {
          const wResp = await fetch(
            `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lng}&current_weather=true`
          );
          if (wResp.ok) {
            const wData = await wResp.json();
            if (wData && wData.current_weather && typeof wData.current_weather.temperature === 'number') {
              const t = Math.round(wData.current_weather.temperature);
              this.camTemperatureText = `${t}°C ☀️`;
            }
          }
        } catch (_we) {
          // Ignore weather error
        }

        this.showToast(`📍 Đã gắn vị trí: ${this.camLocationName}`);
        if (this.activeTab === 'discover') {
          this.renderLocketCameraTab();
        }
      },
      () => {
        this.isFetchingCamGps = false;
        this.showToast('ℹ️ Không lấy được GPS tự động — Bạn có thể tự nhập tên vị trí.');
        if (this.activeTab === 'discover') {
          this.renderLocketCameraTab();
        }
      },
      { enableHighAccuracy: true, timeout: 8000 }
    );
  }

  clearCameraLocation() {
    this.camAttachLocation = false;
    this.camLocationName = '';
    this.camCoordsLabel = '';
    this.camActiveWidget = 'none';
    this.renderLocketCameraTab();
  }

  onInputCameraLocation(val) {
    this.camLocationName = val;
    this.camAttachLocation = Boolean(val && val.trim());
    const pillEl = document.getElementById('locketLiveLocationPill');
    const textEl = document.getElementById('locketLiveTimestampPlace');
    if (pillEl && textEl) {
      textEl.textContent = val.trim();
      pillEl.style.display = this.camAttachLocation ? 'inline-flex' : 'none';
    }
  }

  selectCameraSpotify(song) {
    if (song && this.camSpotifyTrack === song) {
      this.camSpotifyTrack = '';
      this.showToast('🔇 Đã gỡ nhạc Spotify khỏi khung chụp.');
    } else {
      this.camSpotifyTrack = song || '';
      if (this.camSpotifyTrack) {
        this.showToast(`🎵 Đã gắn nhạc: ${this.camSpotifyTrack}`);
      }
    }
    this.renderLocketCameraTab();
  }

  onInputCameraSpotify(val) {
    this.camSpotifyTrack = val;
    const pillEl = document.getElementById('locketLiveSpotifyPill');
    const textEl = document.getElementById('locketLiveSpotifyText');
    if (pillEl && textEl) {
      textEl.textContent = val.trim();
      pillEl.style.display = val.trim() ? 'inline-flex' : 'none';
    }
  }

  selectCameraSticker(st) {
    this.camSticker = this.camSticker === st ? '' : st;
    this.renderLocketCameraTab();
  }

  selectCameraTemp(t) {
    this.camTemperatureText = t;
    this.camEnableTemp = true;
    this.renderLocketCameraTab();
  }

  toggleCameraTempOverlay() {
    this.camEnableTemp = !this.camEnableTemp;
    this.renderLocketCameraTab();
  }

  toggleCameraTimeOverlay() {
    this.camEnableTime = !this.camEnableTime;
    this.renderLocketCameraTab();
  }

  bindShutterPressAndHoldEvents() {
    const shutterBtn = document.getElementById('locketShutterBtn');
    if (!shutterBtn || shutterBtn.dataset.bound === '1') return;
    shutterBtn.dataset.bound = '1';

    let pointerActive = false;

    const onPressStart = (e) => {
      if (e.cancelable) e.preventDefault();
      if (pointerActive) return;
      pointerActive = true;
      this.pressStartTime = Date.now();

      clearTimeout(this.holdTimer);
      this.holdTimer = setTimeout(() => {
        if (pointerActive) {
          this.startHoldVideoRecording();
        }
      }, 260);
    };

    const onPressEnd = (e) => {
      if (e && e.cancelable) e.preventDefault();
      if (!pointerActive) return;
      pointerActive = false;
      clearTimeout(this.holdTimer);

      if (this.isRecordingVideo) {
        this.stopHoldVideoRecording(false);
      } else {
        this.captureLocketShutter();
      }
    };

    shutterBtn.addEventListener('pointerdown', onPressStart);
    shutterBtn.addEventListener('pointerup', onPressEnd);
    shutterBtn.addEventListener('pointercancel', onPressEnd);
    shutterBtn.addEventListener('contextmenu', (e) => e.preventDefault());
  }

  async startCameraStream() {
    const videoEl = document.getElementById('locketLiveVideo');
    const fallbackImg = document.getElementById('locketFallbackImg');
    const statusEl = document.getElementById('locketCameraStatus');
    if (!videoEl) return;

    videoEl.setAttribute('playsinline', '');
    videoEl.setAttribute('webkit-playsinline', 'true');
    videoEl.setAttribute('x5-playsinline', 'true');
    videoEl.playsInline = true;
    videoEl.muted = true;
    videoEl.controls = false;
    videoEl.disablePictureInPicture = true;
    videoEl.classList.toggle('mirrored', this.cameraFacingMode === 'user');

    videoEl.onpause = () => {
      if (this.activeTab === 'discover' && !this.capturedDataUrl && !this.capturedVideoUrl && this.cameraStream) {
        videoEl.play().catch(() => {});
      }
    };

    if (this.cameraStream && this.cameraStream.active) {
      videoEl.srcObject = this.cameraStream;
      videoEl.style.display = 'block';
      await videoEl.play().catch(() => {});
      if (fallbackImg && !this.capturedDataUrl) fallbackImg.style.display = 'none';
      if (statusEl) statusEl.textContent = '🟢 Sẵn sàng chụp';
      return;
    }

    this.stopCameraStream();

    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia && !this.permissionGateBypassed) {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: {
            facingMode: this.cameraFacingMode,
            width: { ideal: 720 },
            height: { ideal: 720 }
          },
          audio: false
        });
        this.cameraStream = stream;
        this.cameraPermState = 'granted';
        localStorage.setItem('journey_cam_perm', 'granted');
        videoEl.srcObject = stream;
        videoEl.style.display = 'block';
        await videoEl.play().catch(() => {});
        if (fallbackImg && !this.capturedDataUrl) fallbackImg.style.display = 'none';
        if (statusEl) statusEl.textContent = '🟢 Sẵn sàng chụp';
        return;
      } catch (_err) {
        // Fallback preview
      }
    }

    videoEl.style.display = 'none';
    if (fallbackImg) {
      fallbackImg.src = DESTINATIONS.halong.image;
      fallbackImg.style.display = 'block';
    }
  }

  ensureVideoPlaying() {
    const videoEl = document.getElementById('locketLiveVideo');
    if (!videoEl) return;
    if (this.cameraStream && videoEl.paused) {
      videoEl.play().catch(() => {});
    } else if (!this.cameraStream && this.activeTab === 'discover' && !this.capturedDataUrl && !this.capturedVideoUrl) {
      this.startCameraStream();
    }
  }

  stopCameraStream() {
    if (this.cameraStream) {
      this.cameraStream.getTracks().forEach((track) => track.stop());
      this.cameraStream = null;
    }
  }

  getActiveTimestampAndLocationInfo() {
    const now = new Date();
    const dateStr = now.toLocaleDateString('vi-VN');
    const timeStr = now.toLocaleTimeString('vi-VN', {
      hour: '2-digit',
      minute: '2-digit'
    });
    const locName = this.camAttachLocation && this.camLocationName ? this.camLocationName.trim() : '';

    return {
      locationText: locName,
      coordsText: this.camAttachLocation ? this.camCoordsLabel : '',
      dateStr,
      timeStr: this.camEnableTime ? `${timeStr} · ${dateStr}` : '',
      temperatureText: this.camEnableTemp ? this.camTemperatureText : '',
      stickerText: this.camSticker || '',
      spotifyText: this.camSpotifyTrack ? this.camSpotifyTrack.trim() : '',
      fullTimestamp: `${timeStr} · ${dateStr}`
    };
  }

  /**
   * Burns only the user-selected optional overlays (Sticker, Time, Temperature, Location, Spotify)
   * onto the 640x640 image canvas.
   */
  burnTimestampAndLocationOnCanvas(ctx, width, height) {
    if (!ctx) return;
    const info = this.getActiveTimestampAndLocationInfo();

    ctx.save();
    const topMeta = [];
    if (info.stickerText) topMeta.push(info.stickerText);
    if (info.timeStr) topMeta.push(`🕒 ${info.timeStr}`);
    if (info.temperatureText) topMeta.push(`🌡️ ${info.temperatureText}`);

    if (topMeta.length > 0) {
      const text = topMeta.join('  ·  ');
      ctx.font = 'bold 18px -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif';
      const w = Math.min(width - 48, ctx.measureText(text).width + 32);
      ctx.fillStyle = 'rgba(15, 23, 42, 0.76)';
      ctx.beginPath();
      ctx.roundRect(20, 20, w, 40, 20);
      ctx.fill();
      ctx.fillStyle = '#FFFFFF';
      ctx.textBaseline = 'middle';
      ctx.fillText(text, 36, 40);
    }

    let bottomY = height - 24;
    if (info.locationText) {
      const locStr = `📍 ${info.locationText}`;
      ctx.font = 'bold 18px -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif';
      const w = Math.min(width - 48, ctx.measureText(locStr).width + 32);
      ctx.fillStyle = 'rgba(15, 23, 42, 0.82)';
      ctx.beginPath();
      ctx.roundRect(20, bottomY - 40, w, 40, 20);
      ctx.fill();
      ctx.fillStyle = '#F3E5AB';
      ctx.textBaseline = 'middle';
      ctx.fillText(locStr, 36, bottomY - 20);
      bottomY -= 48;
    }

    if (info.spotifyText) {
      const spStr = `🎵 ${info.spotifyText}`;
      ctx.font = 'bold 17px -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif';
      const w = Math.min(width - 48, ctx.measureText(spStr).width + 32);
      ctx.fillStyle = 'rgba(20, 83, 45, 0.88)';
      ctx.beginPath();
      ctx.roundRect(20, bottomY - 40, w, 40, 20);
      ctx.fill();
      ctx.fillStyle = '#BBF7D0';
      ctx.textBaseline = 'middle';
      ctx.fillText(spStr, 36, bottomY - 20);
    }
    ctx.restore();
  }

  flipLocketCamera() {
    this.cameraFacingMode =
      this.cameraFacingMode === 'environment' ? 'user' : 'environment';

    if (this.cameraStream) {
      this.stopCameraStream();
      this.startCameraStream();
      this.showToast(
        this.cameraFacingMode === 'user'
          ? '🤳 Đã chuyển sang Camera trước'
          : '📷 Đã chuyển sang Camera sau'
      );
    } else {
      this.startCameraStream();
    }
  }

  drawCurrentFrameToCanvas() {
    const videoEl = document.getElementById('locketLiveVideo');
    const fallbackImg = document.getElementById('locketFallbackImg');
    const canvas = document.getElementById('locketCaptureCanvas');
    if (!canvas) return null;

    const ctx = canvas.getContext('2d');
    canvas.width = 640;
    canvas.height = 640;

    if (this.cameraStream && videoEl && videoEl.videoWidth > 0 && videoEl.videoHeight > 0) {
      const vw = videoEl.videoWidth;
      const vh = videoEl.videoHeight;
      const size = Math.min(vw, vh);
      const sx = (vw - size) / 2;
      const sy = (vh - size) / 2;

      ctx.save();
      if (this.cameraFacingMode === 'user') {
        ctx.translate(640, 0);
        ctx.scale(-1, 1);
      }
      ctx.drawImage(videoEl, sx, sy, size, size, 0, 0, 640, 640);
      ctx.restore();
      this.burnTimestampAndLocationOnCanvas(ctx, 640, 640);
      return canvas.toDataURL('image/jpeg', 0.92);
    }

    if (fallbackImg && fallbackImg.complete && fallbackImg.naturalWidth > 0) {
      const iw = fallbackImg.naturalWidth;
      const ih = fallbackImg.naturalHeight;
      const size = Math.min(iw, ih);
      const sx = (iw - size) / 2;
      const sy = (ih - size) / 2;
      ctx.drawImage(fallbackImg, sx, sy, size, size, 0, 0, 640, 640);
      this.burnTimestampAndLocationOnCanvas(ctx, 640, 640);
      return canvas.toDataURL('image/jpeg', 0.92);
    }

    return fallbackImg ? fallbackImg.getAttribute('src') : null;
  }

  captureLocketShutter() {
    const dataUrl = this.drawCurrentFrameToCanvas();
    this.capturedDataUrl = dataUrl || DESTINATIONS.halong.image;
    this.capturedVideoUrl = null;
    this.capturedMediaType = 'photo';
    this.capturedVideoDuration = 0;

    this.updateCameraUiAfterCapture();
    this.showToast('📸 Đã chụp ảnh! Bạn có thể viết ghi chú rồi bấm Đăng nhé.');
  }

  startHoldVideoRecording() {
    if (this.isRecordingVideo) return;
    this.isRecordingVideo = true;
    this.recordingStartTime = Date.now();
    this.recordedChunks = [];

    const shutterBtn = document.getElementById('locketShutterBtn');
    const badgeEl = document.getElementById('locketModeBadge');
    const statusEl = document.getElementById('locketCameraStatus');
    const progressWrap = document.getElementById('locketRecProgressWrap');
    const progressBar = document.getElementById('locketRecProgressBar');

    if (shutterBtn) shutterBtn.classList.add('recording');
    if (badgeEl) {
      badgeEl.textContent = '🔴 REC 0.0s / 10s';
      badgeEl.classList.add('recording');
    }
    if (statusEl) {
      statusEl.textContent = '🔴 Đang quay video...';
    }
    if (progressWrap) progressWrap.classList.add('active');
    if (progressBar) progressBar.style.width = '0%';

    this.capturedDataUrl = this.drawCurrentFrameToCanvas();

    const canvas = document.getElementById('locketCaptureCanvas');
    const renderLoop = () => {
      if (!this.isRecordingVideo) return;
      this.drawCurrentFrameToCanvas();
      this.recordingAnimFrame = requestAnimationFrame(renderLoop);
    };
    this.recordingAnimFrame = requestAnimationFrame(renderLoop);

    try {
      const streamToRecord =
        canvas && typeof canvas.captureStream === 'function'
          ? canvas.captureStream(24)
          : this.cameraStream;

      if (
        streamToRecord &&
        this.cameraStream &&
        streamToRecord !== this.cameraStream &&
        typeof this.cameraStream.getAudioTracks === 'function'
      ) {
        this.cameraStream.getAudioTracks().forEach((audioTrack) => {
          streamToRecord.addTrack(audioTrack);
        });
      }

      if (streamToRecord && typeof window.MediaRecorder !== 'undefined') {
        const mimeTypes = [
          'video/mp4',
          'video/webm;codecs=vp9',
          'video/webm;codecs=vp8',
          'video/webm'
        ];
        const supportedMime = mimeTypes.find((m) => window.MediaRecorder.isTypeSupported(m)) || '';
        this.mediaRecorder = supportedMime
          ? new window.MediaRecorder(streamToRecord, { mimeType: supportedMime })
          : new window.MediaRecorder(streamToRecord);

        this.mediaRecorder.ondataavailable = (ev) => {
          if (ev.data && ev.data.size > 0) {
            this.recordedChunks.push(ev.data);
          }
        };
        this.mediaRecorder.start(200);
      }
    } catch (_e) {
      this.mediaRecorder = null;
    }

    clearInterval(this.recordingInterval);
    this.recordingInterval = setInterval(() => {
      const elapsedSec = (Date.now() - this.recordingStartTime) / 1000;
      const pct = Math.min(100, (elapsedSec / 10) * 100);
      if (progressBar) progressBar.style.width = `${pct}%`;
      if (badgeEl) {
        badgeEl.textContent = `🔴 REC ${elapsedSec.toFixed(1)}s / 10s`;
      }
      if (elapsedSec >= 10) {
        this.stopHoldVideoRecording(false);
      }
    }, 100);
  }

  stopHoldVideoRecording(cancelOnly = false) {
    if (!this.isRecordingVideo) return;
    this.isRecordingVideo = false;

    clearInterval(this.recordingInterval);
    if (this.recordingAnimFrame) {
      cancelAnimationFrame(this.recordingAnimFrame);
      this.recordingAnimFrame = null;
    }

    const elapsedSec = Math.max(1, Math.min(10, Math.round((Date.now() - this.recordingStartTime) / 1000)));
    const EnforceDuration = Math.max(5, Math.min(10, elapsedSec));

    const finishRecording = (videoBlobUrl) => {
      if (cancelOnly) return;
      this.capturedMediaType = 'video';
      this.capturedVideoDuration = EnforceDuration;
      this.capturedVideoUrl = videoBlobUrl || '';
      if (!this.capturedDataUrl) {
        this.capturedDataUrl = DESTINATIONS.halong.image;
      }
      this.updateCameraUiAfterCapture();
      this.showToast(`🎥 Đã quay video (${EnforceDuration}s)!`);
    };

    if (this.mediaRecorder && this.mediaRecorder.state !== 'inactive') {
      this.mediaRecorder.onstop = () => {
        if (this.recordedChunks.length > 0) {
          const blob = new Blob(this.recordedChunks, {
            type: this.mediaRecorder.mimeType || 'video/webm'
          });
          const url = URL.createObjectURL(blob);
          finishRecording(url);
        } else {
          finishRecording('');
        }
      };
      try {
        this.mediaRecorder.stop();
      } catch (_e) {
        finishRecording('');
      }
    } else {
      finishRecording('');
    }
  }

  updateCameraUiAfterCapture() {
    const videoEl = document.getElementById('locketLiveVideo');
    const fallbackImg = document.getElementById('locketFallbackImg');
    const recVideoEl = document.getElementById('locketRecordedVideoPreview');
    const statusEl = document.getElementById('locketCameraStatus');
    const badgeEl = document.getElementById('locketModeBadge');
    const progressWrap = document.getElementById('locketRecProgressWrap');
    const postControls = document.getElementById('locketPostCaptureControls');
    const liveControls = document.getElementById('locketLiveControls');
    const shutterBtn = document.getElementById('locketShutterBtn');

    if (shutterBtn) shutterBtn.classList.remove('recording');
    if (progressWrap) progressWrap.classList.remove('active');
    if (badgeEl) {
      badgeEl.classList.remove('recording');
      badgeEl.textContent = this.capturedMediaType === 'video' ? `🎥 VIDEO ${this.capturedVideoDuration}S` : '✨ ĐÃ CHỤP';
    }

    if (videoEl) videoEl.style.display = 'none';

    if (this.capturedMediaType === 'video' && this.capturedVideoUrl && recVideoEl) {
      recVideoEl.src = this.capturedVideoUrl;
      recVideoEl.style.display = 'block';
      recVideoEl.play().catch(() => {});
      if (fallbackImg) fallbackImg.style.display = 'none';
    } else if (fallbackImg) {
      if (recVideoEl) recVideoEl.style.display = 'none';
      fallbackImg.src = this.capturedDataUrl;
      fallbackImg.style.display = 'block';
    }

    if (statusEl) {
      statusEl.textContent = '✨ Đã chụp';
    }

    if (liveControls) liveControls.style.display = 'none';
    if (postControls) postControls.style.display = 'flex';
  }

  setPostPrivacy(privacyMode) {
    this.postPrivacy = privacyMode;
    const pickerEl = document.getElementById('customFriendsPicker');

    document.querySelectorAll('.privacy-option-btn').forEach((btn) => {
      const text = btn.textContent || '';
      const isMatch =
        (privacyMode === 'public' && text.includes('Tất cả')) ||
        (privacyMode === 'custom' && text.includes('Chọn bạn')) ||
        (privacyMode === 'private' && text.includes('Chỉ mình tôi'));
      btn.classList.toggle('active', isMatch);
    });

    if (pickerEl) {
      pickerEl.style.display = privacyMode === 'custom' ? 'block' : 'none';
    }
  }

  toggleAllowedFriend(friendName) {
    if (this.selectedFriends.includes(friendName)) {
      if (this.selectedFriends.length > 1) {
        this.selectedFriends = this.selectedFriends.filter((n) => n !== friendName);
      }
    } else {
      this.selectedFriends.push(friendName);
    }
    this.setPostPrivacy('custom');
    const pickerEl = document.getElementById('customFriendsPicker');
    if (pickerEl) {
      const row = pickerEl.querySelector('.friends-MultiSelect-row');
      if (row) {
        row.innerHTML = this.friendsList.map((f) => {
          const isSelected = this.selectedFriends.includes(f.name);
          return `
            <button
              type="button"
              class="friend-select-chip ${isSelected ? 'selected' : ''}"
              onclick="window.journeyApp.toggleAllowedFriend('${f.name}')"
            >
              <span>${isSelected ? '✓' : '+'}</span>
              <span>${f.name}</span>
            </button>
          `;
        }).join('');
      }
    }
  }

  handlePhotoFileUpload(event) {
    const file = event.target.files && event.target.files[0];
    if (!file) return;

    if (file.type.startsWith('video/')) {
      const videoUrl = URL.createObjectURL(file);
      this.capturedMediaType = 'video';
      this.capturedVideoUrl = videoUrl;
      this.capturedVideoDuration = 8;
      this.capturedDataUrl = DESTINATIONS.halong.image;
      this.updateCameraUiAfterCapture();
      this.showToast('🎥 Đã tải video lên!');
      return;
    }

    const reader = new FileReader();
    reader.onload = (e) => {
      const rawDataUrl = e.target.result;
      const tempImg = new Image();
      tempImg.onload = () => {
        const canvas = document.getElementById('locketCaptureCanvas');
        if (canvas) {
          const ctx = canvas.getContext('2d');
          canvas.width = 640;
          canvas.height = 640;
          const iw = tempImg.naturalWidth || 640;
          const ih = tempImg.naturalHeight || 640;
          const size = Math.min(iw, ih);
          const sx = (iw - size) / 2;
          const sy = (ih - size) / 2;
          ctx.drawImage(tempImg, sx, sy, size, size, 0, 0, 640, 640);
          this.burnTimestampAndLocationOnCanvas(ctx, 640, 640);
          this.capturedDataUrl = canvas.toDataURL('image/jpeg', 0.92);
        } else {
          this.capturedDataUrl = rawDataUrl;
        }
        this.capturedVideoUrl = null;
        this.capturedMediaType = 'photo';
        this.updateCameraUiAfterCapture();
        this.showToast('🖼️ Đã tải ảnh thành công!');
      };
      tempImg.src = rawDataUrl;
    };
    reader.readAsDataURL(file);
  }

  retakeLocketPhoto() {
    this.capturedDataUrl = null;
    this.capturedVideoUrl = null;
    this.capturedMediaType = 'photo';
    this.capturedVideoDuration = 0;

    const videoEl = document.getElementById('locketLiveVideo');
    const fallbackImg = document.getElementById('locketFallbackImg');
    const recVideoEl = document.getElementById('locketRecordedVideoPreview');
    const postControls = document.getElementById('locketPostCaptureControls');
    const liveControls = document.getElementById('locketLiveControls');
    const badgeEl = document.getElementById('locketModeBadge');
    const statusEl = document.getElementById('locketCameraStatus');

    if (recVideoEl) {
      recVideoEl.pause();
      recVideoEl.style.display = 'none';
    }
    if (postControls) postControls.style.display = 'none';
    if (liveControls) liveControls.style.display = 'flex';
    if (badgeEl) badgeEl.textContent = '● SẴN SÀNG CHỤP';
    if (statusEl) statusEl.textContent = '🟢 Chạm để chụp';

    if (this.cameraStream && videoEl) {
      videoEl.style.display = 'block';
      if (fallbackImg) fallbackImg.style.display = 'none';
      videoEl.play().catch(() => {});
    } else {
      this.startCameraStream();
    }
  }

  publishLocketPhoto() {
    const info = this.getActiveTimestampAndLocationInfo();
    const captionEl = document.getElementById('locketCaptionInput');
    const caption =
      captionEl && captionEl.value.trim()
        ? captionEl.value.trim()
        : '';

    const imageSrc = this.capturedDataUrl || DESTINATIONS.halong.image;
    const todayStr = info.dateStr;
    const sharedMomentId = 'moment-' + Date.now();

    const newPost = {
      id: sharedMomentId,
      momentId: sharedMomentId,
      isMine: true,
      authorName: this.profile.name,
      authorHandle: this.profile.username,
      avatarText: this.profile.avatarText,
      avatarImg: this.profile.avatarImg,
      placeId: info.locationText ? 'custom' : '',
      placeName: info.locationText || '',
      time: 'Vừa xong',
      dateStr: todayStr,
      timestampLabel: info.timeStr || '',
      temperatureTag: info.temperatureText || '',
      stickerTag: info.stickerText || '',
      spotifyTrack: info.spotifyText || '',
      coordsLabel: info.coordsText || '',
      mediaType: this.capturedMediaType,
      image: imageSrc,
      videoUrl: this.capturedVideoUrl || '',
      videoDuration: this.capturedVideoDuration || 0,
      caption,
      privacy: this.postPrivacy,
      allowedFriends: this.postPrivacy === 'custom' ? [...this.selectedFriends] : [],
      likes: 1,
      liked: true,
      comments: []
    };
    this.posts.unshift(newPost);
    this.saveJson('journey_feed_posts', this.posts);

    const newCheckin = {
      id: sharedMomentId,
      momentId: sharedMomentId,
      placeId: info.locationText ? 'custom' : '',
      placeName: info.locationText || 'Khoảnh khắc của tôi',
      province: '',
      date: info.fullTimestamp,
      timestampLabel: info.timeStr || info.fullTimestamp,
      temperatureTag: info.temperatureText || '',
      stickerTag: info.stickerText || '',
      spotifyTrack: info.spotifyText || '',
      coordsLabel: info.coordsText || '',
      mediaType: this.capturedMediaType,
      image: imageSrc,
      videoUrl: this.capturedVideoUrl || '',
      videoDuration: this.capturedVideoDuration || 0,
      privacy: this.postPrivacy,
      allowedFriends: this.postPrivacy === 'custom' ? [...this.selectedFriends] : [],
      caption
    };
    this.checkins.unshift(newCheckin);
    this.saveJson('journey_user_checkins', this.checkins);

    this.capturedDataUrl = null;
    this.capturedVideoUrl = null;
    this.capturedMediaType = 'photo';
    this.capturedVideoDuration = 0;

    this.showToast('🚀 Đã đăng khoảnh khắc lên Bảng tin!');
    this.switchTab('home');
  }

  /* =========================================================================
     TAB 4: NHẬT KÝ — NHỮNG BỨC ẢNH / VIDEO & NƠI MÌNH ĐÃ CHECK-IN
     ========================================================================= */
  renderJournalTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const cardsHtml = this.checkins
      .map((chk) => {
        const momentKey = chk.momentId || chk.id;
        const mediaBadge =
          chk.mediaType === 'video'
            ? `<span style="position:absolute;bottom:8px;left:8px;background:rgba(17,25,35,0.8);color:#fff;font-size:0.62rem;font-weight:800;padding:2px 7px;border-radius:999px;">🎥 ${chk.videoDuration || 6}s</span>`
            : `<span style="position:absolute;bottom:8px;left:8px;background:rgba(15,23,42,0.82);border:1px solid rgba(212,175,55,0.55);color:#F3E5AB;font-size:0.62rem;font-weight:800;padding:2px 7px;border-radius:999px;">📍 ${chk.placeName}</span>`;

        return `
        <article class="checkin-card" onclick="window.journeyApp.openCheckinDetailModal('${momentKey}')">
          <div class="checkin-img-wrap">
            <img src="${chk.image}" alt="${chk.placeName}" loading="lazy" />
            <span class="checkin-date-badge">📅 ${chk.timestampLabel || chk.date}</span>
            ${mediaBadge}
            <button
              type="button"
              class="checkin-quick-delete"
              title="Xóa ảnh này khỏi cả Nhật ký & Trang chủ"
              onclick="event.stopPropagation(); window.journeyApp.deleteMoment('${momentKey}')"
            >
              🗑️
            </button>
          </div>
          <div class="checkin-body">
            <div class="checkin-place">📍 ${chk.placeName}${chk.province ? `, ${chk.province}` : ''}</div>
            <div class="checkin-note">${chk.caption}</div>
          </div>
        </article>
      `;
      })
      .join('');

    main.innerHTML = `
      <section style="display:flex;flex-direction:column;gap:12px;">
        <div class="section-header">
          <div>
            <h2 class="section-title serif-title" style="font-size:1.35rem;">Nhật ký hành trình</h2>
            <p style="font-size:0.78rem;color:var(--text-secondary);">
              Những khoảnh khắc đẹp nhất trong hành trình của bạn (${this.checkins.length})
            </p>
          </div>
          <button class="chip-btn active" onclick="window.journeyApp.switchTab('discover')">
            📷 Chụp mới
          </button>
        </div>

        <div style="display:flex;gap:8px;overflow-x:auto;padding-bottom:2px;">
          <button type="button" class="chip-btn active">🖼️ Ảnh của tôi (${this.checkins.length || 324})</button>
          <button type="button" class="chip-btn" onclick="window.journeyApp.switchTab('explore')">✈️ Chuyến đi (12)</button>
          <button type="button" class="chip-btn" onclick="window.journeyApp.switchTab('home')">⭐ Khoảnh khắc (${this.posts.length || 87})</button>
        </div>

        <div class="checkin-grid">
          ${cardsHtml || '<div style="grid-column:1/-1;text-align:center;padding:24px;color:var(--text-secondary);">Chưa có ảnh check-in nào. Hãy chạm nút Chụp để lưu giữ khoảnh khắc đầu tiên!</div>'}
        </div>

        <div onclick="window.journeyApp.switchTab('discover')" style="cursor:pointer;display:flex;align-items:center;gap:12px;padding:14px 16px;border-radius:18px;background:var(--surface-card);border:1px solid var(--border-gold);">
          <div style="width:42px;height:42px;border-radius:14px;background:var(--accent-gold-soft);display:flex;align-items:center;justify-content:center;font-size:1.25rem;">
            ✨
          </div>
          <div style="flex:1;">
            <div style="font-size:0.88rem;font-weight:800;color:var(--text-primary);">Hành trình của bạn thật tuyệt vời!</div>
            <div style="font-size:0.74rem;color:var(--text-secondary);">Cùng tạo thêm những kỷ niệm đáng nhớ nhé.</div>
          </div>
          <span style="color:var(--accent-gold);font-weight:800;">›</span>
        </div>
      </section>
    `;
  }

  openCheckinDetailModal(checkinId) {
    const chk = this.checkins.find((c) => c.id === checkinId || c.momentId === checkinId);
    if (!chk) return;
    const momentKey = chk.momentId || chk.id;

    const mediaPreview =
      chk.mediaType === 'video' && chk.videoUrl
        ? `<video src="${chk.videoUrl}" poster="${chk.image}" class="feed-video-player" autoplay loop muted playsinline webkit-playsinline="true"></video>`
        : `<img src="${chk.image}" alt="${chk.placeName}" class="feed-photo-img" />`;

    this.openModal(`
      <div class="modal-header-row">
        <div>
          <h3 class="serif-title" style="font-size:1.15rem;">📍 ${chk.placeName}</h3>
          <span style="font-size:0.75rem;color:var(--text-secondary);">Check-in ngày ${chk.date} · ${chk.province || 'Việt Nam'}</span>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div class="feed-photo-wrap" style="width:100%;margin:0 0 12px;border-radius:18px;overflow:hidden;">
        ${mediaPreview}
        <div class="feed-locket-caption-pill">${chk.caption}</div>
      </div>
      <div style="display:flex;justify-content:space-between;gap:8px;">
        <button class="chip-btn" style="color:var(--danger);border-color:var(--danger);" onclick="window.journeyApp.deleteMoment('${momentKey}')">
          🗑️ Xóa đồng bộ (Nhật ký &amp; Trang chủ)
        </button>
        <button class="chip-btn active" onclick="window.journeyApp.closeModal(); window.journeyApp.viewPlaceOnMap('${chk.placeId || 'halong'}')">
          🗺️ Xem Bản đồ
        </button>
      </div>
    `);
  }

  /* =========================================================================
     TAB 5: HỒ SƠ — GIAO DIỆN THẺ HỒ SƠ HIỆN ĐẠI, THAY AVATAR & THÔNG TIN CÁ NHÂN
     ========================================================================= */
  renderProfileTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const avatarContent = this.profile.avatarImg
      ? `<img src="${this.profile.avatarImg}" alt="${this.profile.name}" />`
      : `<span>${this.profile.avatarText}</span>`;

    const myPostsCount = this.posts.filter((p) => p.isMine).length;
    const allPlacesCount = this.getAllVisitedPlaces().length;

    main.innerHTML = `
      <section class="profile-editor-card" aria-label="Hồ sơ cá nhân">
        <!-- Banner nền sang trọng -->
        <div class="profile-cover-banner"></div>

        <!-- Cụm Avatar căn giữa chuẩn xác với huy hiệu máy ảnh nằm góc dưới phải -->
        <div class="profile-avatar-upload">
          <label class="profile-avatar-wrapper" title="Chạm để đổi ảnh đại diện">
            <div class="profile-avatar-Editable">
              ${avatarContent}
            </div>
            <span class="profile-avatar-badge">📷</span>
            <input type="file" accept="image/*" style="display:none;" onchange="window.journeyApp.handleAvatarUpload(event)" />
          </label>

          <h2 class="serif-title" style="font-size:1.35rem;font-weight:700;text-align:center;color:var(--text-primary);">
            ${this.profile.name}
          </h2>
          <div style="display:flex;align-items:center;gap:6px;margin-top:2px;flex-wrap:wrap;justify-content:center;">
            <span style="font-size:0.76rem;font-weight:700;color:var(--accent-sage);background:var(--accent-sage-soft);padding:2px 9px;border-radius:999px;">
              ${this.profile.username}
            </span>
            <span style="font-size:0.76rem;color:var(--text-secondary);">📍 ${this.profile.city}</span>
          </div>

          <p style="font-size:0.8rem;color:var(--text-secondary);text-align:center;margin-top:6px;max-width:340px;">
            ${this.profile.bio}
          </p>

          <div style="display:flex;gap:8px;margin-top:10px;flex-wrap:wrap;justify-content:center;">
            <label class="chip-btn active" style="cursor:pointer;">
              🖼️ Đổi ảnh đại diện
              <input type="file" accept="image/*" style="display:none;" onchange="window.journeyApp.handleAvatarUpload(event)" />
            </label>
            ${
              this.profile.avatarImg
                ? `<button type="button" class="chip-btn" onclick="window.journeyApp.resetAvatarToInitials()">Dùng chữ viết tắt (${this.profile.avatarText})</button>`
                : ''
            }
          </div>

          <!-- Thống kê nhanh của hồ sơ (4 cột chuẩn thiết kế) -->
          <div class="profile-stats-bar" style="grid-template-columns:repeat(4,1fr);">
            <div class="profile-stat-item">
              <div class="profile-stat-val">12</div>
              <div class="profile-stat-lbl">Chuyến đi</div>
            </div>
            <div class="profile-stat-item">
              <div class="profile-stat-val">${allPlacesCount || 245}</div>
              <div class="profile-stat-lbl">Khám phá</div>
            </div>
            <div class="profile-stat-item">
              <div class="profile-stat-val">${myPostsCount || this.checkins.length}</div>
              <div class="profile-stat-lbl">Khoảnh khắc</div>
            </div>
            <div class="profile-stat-item">
              <div class="profile-stat-val">4.9 ★</div>
              <div class="profile-stat-lbl">Đánh giá</div>
            </div>
          </div>
        </div>

        <!-- Biểu mẫu chỉnh sửa thông tin cá nhân gọn đẹp -->
        <div class="profile-form">
          <div class="profile-form-grid-2">
            <div class="form-group">
              <label class="form-label" for="profName">👤 Họ và tên hiển thị</label>
              <input id="profName" class="form-input" type="text" value="${this.profile.name}" placeholder="Nhập tên của bạn" />
            </div>

            <div class="form-group">
              <label class="form-label" for="profUsername">🏷️ ID / Username</label>
              <input id="profUsername" class="form-input" type="text" value="${this.profile.username}" placeholder="@username" />
            </div>
          </div>

          <div class="form-group">
            <label class="form-label" for="profBio">✨ Giới thiệu bản thân (Bio)</label>
            <textarea id="profBio" class="form-input" rows="2" placeholder="Viết đôi dòng giới thiệu...">${this.profile.bio}</textarea>
          </div>

          <div class="profile-form-grid-2">
            <div class="form-group">
              <label class="form-label" for="profCity">📍 Thành phố / Quê quán</label>
              <input id="profCity" class="form-input" type="text" value="${this.profile.city}" placeholder="Hà Nội, Việt Nam" />
            </div>

            <div class="form-group">
              <label class="form-label" for="profEmail">✉️ Email liên hệ</label>
              <input id="profEmail" class="form-input" type="email" value="${this.profile.email}" placeholder="email@example.com" />
            </div>
          </div>

          <!-- Khung Đường Link Kết Bạn & Thành phần friends-list Ngay Trong Hồ Sơ -->
          <div class="locket-friends-banner-card" style="margin-top:4px;">
            <div style="display:flex;align-items:center;justify-content:space-between;gap:8px;">
              <div>
                <div style="font-size:0.86rem;font-weight:800;color:var(--text-primary);">
                  🔗 Đường Link Kết Bạn Của Bạn
                </div>
                <div style="font-size:0.72rem;color:var(--text-secondary);">
                  Gửi đường link này cho bạn bè để kết nối và chia sẻ khoảnh khắc cùng nhau
                </div>
              </div>
              <button type="button" class="invite-action-btn" onclick="window.journeyApp.openFriendsAndChatHub('friends')">
                💬 Hộp thư (${this.friendsList.length})
              </button>
            </div>
            <div class="invite-link-box">
              <span class="invite-link-url">${this.getMyInviteLink()}</span>
              <button type="button" class="invite-action-btn" onclick="window.journeyApp.copyMyInviteLink()">
                📋 Sao chép
              </button>
              <button type="button" class="invite-action-btn secondary" onclick="window.journeyApp.shareMyInviteLink()">
                📤 Chia sẻ
              </button>
            </div>
          </div>

          <!-- Thành phần friends-list: Hiển thị danh sách bạn bè đã kết nối, trạng thái Online & nút Add Friend -->
          ${this.renderProfileFriendsListComponent()}

          <div style="display:flex;align-items:center;justify-content:space-between;padding:10px 14px;border-radius:14px;background:var(--surface-subtle);border:1px solid var(--border-color);">
            <span style="font-size:0.82rem;font-weight:700;">🎨 Giao diện Sáng / Tối</span>
            <button type="button" class="chip-btn" onclick="window.journeyApp.toggleTheme()">
              ${this.theme === 'dark' ? '🌙 Chế độ Tối' : '☀️ Chế độ Sáng'}
            </button>
          </div>

          <button type="button" class="btn-save-profile" onclick="window.journeyApp.saveProfileChanges()">
            💾 Lưu thông tin cá nhân
          </button>
        </div>
      </section>
    `;
  }

  fetchConnectedFriends() {
    const stored = this.loadJson('journey_friends_list', null);
    if (Array.isArray(stored)) {
      this.friendsList = stored;
    }
    return (this.friendsList || []).map((friend) => ({
      ...friend,
      online: Boolean(friend.online),
      statusText: friend.online ? 'Đang hoạt động (Online)' : 'Ngoại tuyến (Offline)'
    }));
  }

  fetchPendingFriendRequests() {
    const stored = this.loadJson('journey_friend_requests', null);
    if (Array.isArray(stored)) {
      this.pendingFriendRequests = stored;
    }
    return this.pendingFriendRequests || [];
  }

  renderProfileFriendsListComponent() {
    const connectedFriends = this.fetchConnectedFriends();
    const pendingRequests = this.fetchPendingFriendRequests();
    const onlineCount = connectedFriends.filter((f) => f.online).length;
    const currentQuery = this.friendsSearchQuery || '';

    const pendingRequestsHtml =
      pendingRequests.length > 0
        ? `
          <div class="pending-requests-list" role="list">
            ${pendingRequests
              .map(
                (req) => `
              <div
                class="pending-request-item"
                data-request-id="${req.id}"
                data-testid="pending-request-item-${req.id}"
              >
                <div style="display:flex;align-items:center;gap:10px;min-width:0;flex:1;">
                  <div class="feed-author-avatar">${req.avatarText}</div>
                  <div style="min-width:0;flex:1;">
                    <div style="display:flex;align-items:center;gap:6px;flex-wrap:wrap;">
                      <span style="font-size:0.85rem;font-weight:800;color:var(--text-primary);">${req.name}</span>
                      <span style="font-size:0.72rem;color:var(--accent-sage);font-weight:700;">${req.handle}</span>
                    </div>
                    <div style="font-size:0.72rem;color:var(--text-secondary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                      📍 ${req.city || 'Việt Nam'} · ${req.mutualNote || 'Lời mời kết bạn'}
                    </div>
                  </div>
                </div>
                <div class="pending-request-actions">
                  <button
                    type="button"
                    class="btn-accept-request"
                    data-testid="accept-request-${req.id}"
                    onclick="window.journeyApp.acceptFriendRequest('${req.id}', true)"
                  >
                    ✓ Đồng ý (Accept)
                  </button>
                  <button
                    type="button"
                    class="btn-decline-request"
                    data-testid="decline-request-${req.id}"
                    onclick="window.journeyApp.declineFriendRequest('${req.id}', true)"
                  >
                    ✕ Từ chối (Decline)
                  </button>
                </div>
              </div>
            `
              )
              .join('')}
          </div>
        `
        : `
          <div class="pending-requests-empty" data-testid="pending-requests-empty">
            ✓ Không có lời mời kết bạn nào đang chờ xử lý.
          </div>
        `;

    const friendItemsHtml = connectedFriends
      .map(
        (friend) => `
        <div
          class="friends-list-item"
          data-friend-id="${friend.id}"
          data-friend-name="${friend.name}"
          data-friend-handle="${friend.handle}"
          data-friend-city="${friend.city || friend.latestLocketPlace || 'Việt Nam'}"
        >
          <div style="display:flex;align-items:center;gap:10px;min-width:0;flex:1;">
            <div class="friend-avatar-status-wrap">
              <div class="feed-author-avatar">${friend.avatarText}</div>
              <span
                class="status-indicator ${friend.online ? 'online' : 'offline'}"
                title="${friend.online ? 'Online' : 'Offline'}"
                aria-label="${friend.online ? 'Online' : 'Offline'}"
              ></span>
            </div>
            <div style="min-width:0;flex:1;">
              <div style="display:flex;align-items:center;gap:6px;flex-wrap:wrap;">
                <span class="friend-item-name" style="font-size:0.86rem;font-weight:800;color:var(--text-primary);">${friend.name}</span>
                <span style="font-size:0.72rem;color:var(--accent-sage);font-weight:700;">${friend.handle}</span>
                <span class="friend-status-badge ${friend.online ? 'online' : 'offline'}">
                  ${friend.online ? '● Online' : '○ Offline'}
                </span>
              </div>
              <div style="font-size:0.73rem;color:var(--text-secondary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                📍 ${friend.city || friend.latestLocketPlace || 'Việt Nam'} · ${friend.statusText}
              </div>
            </div>
          </div>
          <div style="display:flex;align-items:center;gap:6px;flex-shrink:0;">
            <button
              type="button"
              class="invite-action-btn"
              onclick="window.journeyApp.openFriendChatModal('${friend.id}')"
            >
              💬 Nhắn tin
            </button>
          </div>
        </div>
      `
      )
      .join('');

    return `
      <section
        id="profileFriendsListSection"
        class="friends-list"
        data-testid="friends-list"
        aria-label="Danh sách bạn bè đã kết nối"
      >
        <div class="friends-list-header">
          <div class="friends-list-title-wrap">
            <div class="friends-list-title">
              <span>👥 Bạn bè đã kết nối (${connectedFriends.length})</span>
              <span class="friends-online-summary" data-testid="friends-online-count">
                <span class="friend-online-dot"></span> ${onlineCount} Online
              </span>
            </div>
            <div style="font-size:0.73rem;color:var(--text-secondary);">
              Danh sách bạn bè chia sẻ khoảnh khắc và trò chuyện trực tiếp cùng bạn
            </div>
          </div>
          <button
            id="btnProfileAddFriend"
            type="button"
            class="btn-add-friend"
            onclick="window.journeyApp.handleProfileAddFriend()"
          >
            ➕ Thêm bạn bè (Add Friend)
          </button>
        </div>

        <!-- Mục Lời mời kết bạn đang chờ (Pending Friend Requests Section) -->
        <div
          id="pendingFriendRequestsSection"
          class="pending-friend-requests"
          data-testid="pending-friend-requests"
          aria-label="Lời mời kết bạn đang chờ"
        >
          <div class="pending-requests-header">
            <div class="pending-requests-title">
              <span>💌 Lời mời kết bạn đang chờ (Pending Friend Requests)</span>
              <span class="pending-requests-badge" data-testid="pending-requests-count">
                ${pendingRequests.length}
              </span>
            </div>
          </div>
          ${pendingRequestsHtml}
        </div>

        <!-- Thanh tìm kiếm & lọc bạn bè theo tên (Searchable Filter) -->
        <div class="friends-search-filter-bar">
          <div class="friends-search-input-wrap">
            <span class="friends-search-icon" aria-hidden="true">🔍</span>
            <input
              id="friendsSearchFilterInput"
              class="friends-search-input"
              data-testid="friends-search-input"
              type="search"
              value="${currentQuery}"
              placeholder="Tìm bạn bè theo tên, @username hoặc địa điểm..."
              aria-label="Tìm kiếm bạn bè theo tên"
              oninput="window.journeyApp.onFilterFriendsList(this.value)"
            />
            <button
              id="friendsSearchClearBtn"
              type="button"
              class="friends-search-clear-btn ${currentQuery ? 'visible' : ''}"
              title="Xóa tìm kiếm"
              aria-label="Xóa từ khóa tìm kiếm bạn bè"
              onclick="window.journeyApp.clearFriendsListSearch()"
            >
              ✕
            </button>
          </div>
          <span id="friendsFilterResultCount" class="friends-filter-meta" data-testid="friends-filter-count">
            Hiển thị ${connectedFriends.length}/${connectedFriends.length}
          </span>
        </div>

        <div class="friends-add-bar">
          <input
            id="profileAddFriendInput"
            class="form-input"
            type="text"
            placeholder="Nhập @username, tên bạn bè hoặc dán link kết bạn..."
            style="flex:1;padding:9px 12px;font-size:0.8rem;"
            onkeydown="if(event.key==='Enter'){event.preventDefault();window.journeyApp.handleProfileAddFriend();}"
          />
        </div>

        <div id="profileFriendsListItems" class="friends-list-items" role="list">
          ${friendItemsHtml}
        </div>

        <div id="friendsListEmptyState" class="friends-empty-state" data-testid="friends-empty-state">
          🔍 Không tìm thấy bạn bè nào khớp với tên vừa nhập.
        </div>
      </section>
    `;
  }

  onFilterFriendsList(rawQuery) {
    this.friendsSearchQuery = rawQuery || '';
    const normQuery = this.normalizeViText(this.friendsSearchQuery);
    const clearBtn = document.getElementById('friendsSearchClearBtn');
    const countEl = document.getElementById('friendsFilterResultCount');
    const emptyEl = document.getElementById('friendsListEmptyState');
    const itemEls = document.querySelectorAll('#profileFriendsListItems .friends-list-item');

    if (clearBtn) {
      clearBtn.classList.toggle('visible', Boolean(this.friendsSearchQuery.trim()));
    }

    let visibleCount = 0;
    const totalCount = itemEls.length;

    itemEls.forEach((itemEl) => {
      const name = itemEl.getAttribute('data-friend-name') || '';
      const handle = itemEl.getAttribute('data-friend-handle') || '';
      const city = itemEl.getAttribute('data-friend-city') || '';
      const haystack = this.normalizeViText(`${name} ${handle} ${city}`);
      const isMatch = !normQuery || haystack.includes(normQuery);

      itemEl.style.display = isMatch ? 'flex' : 'none';
      if (isMatch) visibleCount += 1;
    });

    if (countEl) {
      countEl.textContent = `Hiển thị ${visibleCount}/${totalCount}`;
    }
    if (emptyEl) {
      emptyEl.style.display = visibleCount === 0 && totalCount > 0 ? 'block' : 'none';
    }
  }

  clearFriendsListSearch() {
    this.friendsSearchQuery = '';
    const inputEl = document.getElementById('friendsSearchFilterInput');
    if (inputEl) {
      inputEl.value = '';
      inputEl.focus();
    }
    this.onFilterFriendsList('');
  }

  handleProfileAddFriend() {
    const inputEl = document.getElementById('profileAddFriendInput');
    const rawValue = inputEl ? inputEl.value.trim() : '';
    if (rawValue) {
      this.addFriendFromInviteCode(rawValue);
      if (inputEl) inputEl.value = '';
      return;
    }
    this.openFriendsAndChatHub('friends');
  }

  handleAvatarUpload(event) {
    const file = event.target.files && event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (e) => {
      this.profile.avatarImg = e.target.result;
      this.saveJson('journey_user_profile', this.profile);
      this.syncHeaderProfile();
      this.renderProfileTab();
      this.showToast('📷 Đã cập nhật ảnh đại diện mới!');
    };
    reader.readAsDataURL(file);
  }

  resetAvatarToInitials() {
    this.profile.avatarImg = '';
    this.saveJson('journey_user_profile', this.profile);
    this.syncHeaderProfile();
    this.renderProfileTab();
    this.showToast('✓ Đã chuyển về avatar chữ viết tắt.');
  }

  saveProfileChanges() {
    const nameEl = document.getElementById('profName');
    const userEl = document.getElementById('profUsername');
    const bioEl = document.getElementById('profBio');
    const cityEl = document.getElementById('profCity');
    const emailEl = document.getElementById('profEmail');

    if (nameEl && nameEl.value.trim()) {
      this.profile.name = nameEl.value.trim();
    }
    if (userEl && userEl.value.trim()) {
      const val = userEl.value.trim();
      this.profile.username = val.startsWith('@') ? val : '@' + val;
    }
    if (bioEl) this.profile.bio = bioEl.value.trim();
    if (cityEl) this.profile.city = cityEl.value.trim();
    if (emailEl) this.profile.email = emailEl.value.trim();

    this.profile.avatarText = this.getInitials(this.profile.name);
    this.saveJson('journey_user_profile', this.profile);
    this.syncHeaderProfile();
    this.renderProfileTab();
    this.showToast('✅ Đã lưu thông tin hồ sơ cá nhân!');
  }

  /* =========================================================================
     LOCKET FRIENDS, INVITE LINK & DIRECT MESSAGING (CHAT) METHODS
     ========================================================================= */
  getMyInviteLink() {
    const cleanHandle = (this.profile.username || '@minh.journey').replace(/^@/, '');
    const origin =
      typeof window !== 'undefined' && window.location && window.location.origin && window.location.origin !== 'null'
        ? window.location.origin
        : 'https://journey-ai.vn';
    return `${origin}/?invite=${encodeURIComponent(cleanHandle)}`;
  }

  copyMyInviteLink() {
    const link = this.getMyInviteLink();
    if (navigator.clipboard && navigator.clipboard.writeText) {
      navigator.clipboard
        .writeText(link)
        .then(() => {
          this.showToast('📋 Đã sao chép đường link kết bạn! Gửi cho bạn bè ngay nhé.');
        })
        .catch(() => {
          this.showToast(`🔗 Link kết bạn: ${link}`);
        });
    } else {
      this.showToast(`🔗 Link kết bạn: ${link}`);
    }
  }

  shareMyInviteLink() {
    const link = this.getMyInviteLink();
    if (navigator.share) {
      navigator
        .share({
          title: `Kết bạn với ${this.profile.name} trên Journey AI`,
          text: `Kết bạn với ${this.profile.name} (${this.profile.username}) để xem khoảnh khắc và nhắn tin trực tiếp nhé!`,
          url: link
        })
        .catch(() => {});
    } else {
      this.copyMyInviteLink();
    }
  }

  updateHeaderUnreadBadge() {
    const badge = document.getElementById('headerUnreadBadge');
    if (!badge) return;
    const reqCount = this.pendingFriendRequests ? this.pendingFriendRequests.length : 0;
    const totalCount = reqCount;
    badge.textContent = String(totalCount);
    badge.style.display = totalCount > 0 ? 'inline-flex' : 'none';
  }

  checkIncomingInviteUrl() {
    try {
      const params = new URLSearchParams(window.location.search);
      const requestHandle = params.get('request');
      if (requestHandle) {
        const cleanReq = requestHandle.replace(/^@/, '').trim();
        if (cleanReq) {
          setTimeout(() => {
            this.receiveNewFriendRequest({
              name: cleanReq,
              handle: `@${cleanReq.toLowerCase().replace(/[^a-z0-9._-]/g, '')}`,
              avatarText: this.getInitials(cleanReq),
              city: params.get('city') || 'Việt Nam',
              mutualNote: 'Gửi lời mời qua đường link kết bạn'
            });
          }, 300);
          return;
        }
      }
      const inviteHandle = params.get('invite');
      if (inviteHandle) {
        const clean = inviteHandle.replace(/^@/, '').trim();
        if (clean && `@${clean}` !== this.profile.username) {
          setTimeout(() => {
            this.addFriendFromInviteCode(clean);
          }, 400);
        }
      }
    } catch (_e) {
      // Ignore URL parse error
    }
  }

  addFriendFromInviteCode(rawInput) {
    const raw = (rawInput || '').trim();
    if (!raw) {
      this.showToast('⚠️ Vui lòng nhập đường link kết bạn hoặc @username của bạn bè!');
      return;
    }

    let handlePart = raw;
    if (raw.includes('invite=')) {
      const afterInvite = raw.split('invite=')[1] || '';
      handlePart = decodeURIComponent(afterInvite.split('&')[0] || '');
    } else if (raw.includes('/')) {
      const segs = raw.split('/').filter(Boolean);
      handlePart = segs[segs.length - 1] || raw;
    }

    const cleanId = handlePart.replace(/^@/, '').trim().toLowerCase().replace(/[^a-z0-9._-]/g, '') || 'ban_moi';
    const handle = `@${cleanId}`;

    const existing = this.friendsList.find(
      (f) => f.handle.toLowerCase() === handle.toLowerCase() || f.name.toLowerCase() === raw.toLowerCase()
    );
    if (existing) {
      this.showToast(`🤝 Bạn và ${existing.name} (${existing.handle}) đã là bạn bè! Đang mở khung chat...`);
      this.openFriendChatModal(existing.id);
      return;
    }

    const displayName = raw.includes('http') || raw.startsWith('@')
      ? cleanId
          .split(/[._-]/)
          .map((w) => (w ? w[0].toUpperCase() + w.slice(1) : ''))
          .join(' ') || 'Bạn Mới'
      : raw;

    const newFriend = {
      id: `friend_${Date.now()}`,
      name: displayName,
      handle,
      avatarText: this.getInitials(displayName),
      city: 'Việt Nam 🇻🇳',
      online: true,
      latestLocketPlace: 'Vịnh Hạ Long',
      latestLocketImg: 'images/img_hero_halong_bay.jpg'
    };

    this.friendsList.unshift(newFriend);
    this.saveJson('journey_friends_list', this.friendsList);

    if (!this.chatMessagesByFriend[newFriend.id]) {
      this.chatMessagesByFriend[newFriend.id] = [
        {
          id: `welcome_${Date.now()}`,
          fromMe: false,
          text: `Chào ${this.profile.name}! Mình vừa kết bạn qua đường link của bạn nè 🤝✨`,
          time: 'Vừa xong',
          locketTag: '🔗 Đã kết bạn'
        }
      ];
      this.saveJson('journey_locket_chats', this.chatMessagesByFriend);
    }

    this.showToast(`🎉 Đã kết bạn thành công với ${newFriend.name} (${newFriend.handle})!`);
    if (this.activeTab === 'home') this.renderHomeTab();
    else if (this.activeTab === 'profile') this.renderProfileTab();
    this.openFriendsAndChatHub('friends');
  }

  acceptFriendRequest(reqId, stayInPlace = false) {
    const req = this.pendingFriendRequests.find((r) => r.id === reqId);
    if (!req) return;

    this.pendingFriendRequests = this.pendingFriendRequests.filter((r) => r.id !== reqId);
    this.saveJson('journey_friend_requests', this.pendingFriendRequests);

    const newFriend = {
      id: req.id.replace('req_', ''),
      name: req.name,
      handle: req.handle,
      avatarText: req.avatarText,
      city: req.city,
      online: true,
      latestLocketPlace: req.city,
      latestLocketImg: 'images/img_sapa_terraces.jpg'
    };

    this.friendsList.unshift(newFriend);
    this.saveJson('journey_friends_list', this.friendsList);

    this.chatMessagesByFriend[newFriend.id] = [
      {
        id: `msg_${Date.now()}`,
        fromMe: false,
        text: `Yay! Cảm ơn ${this.profile.name} đã đồng ý kết bạn nhé 🥰📸`,
        time: 'Vừa xong',
        locketTag: `📍 ${req.city}`
      }
    ];
    this.saveJson('journey_locket_chats', this.chatMessagesByFriend);

    this.updateHeaderUnreadBadge();
    if (this.activeTab === 'home') this.renderHomeTab();
    else if (this.activeTab === 'profile') this.renderProfileTab();
    if (!stayInPlace) {
      this.openFriendsAndChatHub('friends');
    }
    this.showToast(`🤝 Đã chấp nhận lời mời kết bạn của ${req.name}!`);
  }

  declineFriendRequest(reqId, stayInPlace = false) {
    const req = this.pendingFriendRequests.find((r) => r.id === reqId);
    this.pendingFriendRequests = this.pendingFriendRequests.filter((r) => r.id !== reqId);
    this.saveJson('journey_friend_requests', this.pendingFriendRequests);
    this.updateHeaderUnreadBadge();
    if (this.activeTab === 'home') this.renderHomeTab();
    else if (this.activeTab === 'profile') this.renderProfileTab();
    if (!stayInPlace) {
      this.openFriendsAndChatHub('friends');
    }
    this.showToast(req ? `Đã từ chối lời mời kết bạn của ${req.name}.` : 'Đã ẩn lời mời kết bạn.');
  }

  openFriendsAndChatHub(subTab = 'friends') {
    const inviteUrl = this.getMyInviteLink();

    const requestsHtml =
      this.pendingFriendRequests.length > 0
        ? `
          <div style="margin-bottom:14px;">
            <div style="font-size:0.76rem;font-weight:800;color:var(--text-secondary);margin-bottom:6px;text-transform:uppercase;">
              💌 Lời mời kết bạn (${this.pendingFriendRequests.length})
            </div>
            <div style="display:flex;flex-direction:column;gap:8px;">
              ${this.pendingFriendRequests
                .map(
                  (req) => `
                  <div class="friend-row-card">
                    <div style="display:flex;align-items:center;gap:9px;min-width:0;">
                      <div class="feed-author-avatar">${req.avatarText}</div>
                      <div style="min-width:0;">
                        <div style="font-size:0.84rem;font-weight:800;color:var(--text-primary);">${req.name} <span style="font-size:0.72rem;color:var(--accent-sage);">${req.handle}</span></div>
                        <div style="font-size:0.7rem;color:var(--text-secondary);">📍 ${req.city} · ${req.mutualNote}</div>
                      </div>
                    </div>
                    <div style="display:flex;gap:5px;flex-shrink:0;">
                      <button type="button" class="invite-action-btn" onclick="window.journeyApp.acceptFriendRequest('${req.id}')">✓ Đồng ý</button>
                      <button type="button" class="invite-action-btn secondary" onclick="window.journeyApp.declineFriendRequest('${req.id}')">✕</button>
                    </div>
                  </div>
                `
                )
                .join('')}
            </div>
          </div>
        `
        : '';

    const friendsRowsHtml = this.friendsList
      .map((f) => {
        const msgs = this.chatMessagesByFriend[f.id] || [];
        const lastMsg = msgs.length > 0 ? msgs[msgs.length - 1] : null;
        return `
          <div class="friend-row-card" style="cursor:pointer;" onclick="window.journeyApp.openFriendChatModal('${f.id}')">
            <div style="display:flex;align-items:center;gap:10px;min-width:0;flex:1;">
              <div class="feed-author-avatar">${f.avatarText}</div>
              <div style="min-width:0;flex:1;">
                <div style="display:flex;align-items:center;gap:5px;">
                  ${f.online ? '<span class="friend-online-dot"></span>' : ''}
                  <span style="font-size:0.86rem;font-weight:800;color:var(--text-primary);">${f.name}</span>
                  <span style="font-size:0.7rem;color:var(--text-muted);">${f.handle}</span>
                </div>
                <div style="font-size:0.73rem;color:var(--text-secondary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                  ${
                    subTab === 'messages' && lastMsg
                      ? `${lastMsg.fromMe ? 'Bạn: ' : ''}${lastMsg.text}`
                      : `📍 ${f.city || f.latestLocketPlace || 'Việt Nam'}`
                  }
                </div>
              </div>
            </div>
            <button type="button" class="invite-action-btn" onclick="event.stopPropagation(); window.journeyApp.openFriendChatModal('${f.id}')">
              💬 Nhắn tin
            </button>
          </div>
        `;
      })
      .join('');

    this.openModal(`
      <div class="modal-header-row">
        <div>
          <h3 class="serif-title" style="font-size:1.2rem;">
            ${subTab === 'messages' ? '💬 Hộp Thư Trò Chuyện' : '👥 Bạn Bè & Đường Link Kết Bạn'}
          </h3>
          <span style="font-size:0.74rem;color:var(--text-secondary);">
            Kết nối bạn bè qua link và nhắn tin trực tiếp trên từng khoảnh khắc
          </span>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>

      <!-- Chuyển tab Bạn bè / Hộp thư -->
      <div style="display:flex;gap:6px;margin-bottom:12px;">
        <button
          type="button"
          class="chip-btn ${subTab === 'friends' ? 'active' : ''}"
          style="flex:1;justify-content:center;"
          onclick="window.journeyApp.openFriendsAndChatHub('friends')"
        >
          👥 Bạn bè &amp; Kết bạn (${this.friendsList.length})
        </button>
        <button
          type="button"
          class="chip-btn ${subTab === 'messages' ? 'active' : ''}"
          style="flex:1;justify-content:center;"
          onclick="window.journeyApp.openFriendsAndChatHub('messages')"
        >
          💬 Hộp thư (${this.friendsList.length})
        </button>
      </div>

      <!-- Đường Link Kết Bạn Cá Nhân -->
      <div style="padding:11px 12px;border-radius:16px;background:var(--surface-subtle);border:1px solid var(--border-color);margin-bottom:12px;">
        <div style="font-size:0.75rem;font-weight:800;color:var(--text-primary);margin-bottom:6px;">
          🔗 Đường link kết bạn của bạn:
        </div>
        <div class="invite-link-box" style="background:var(--surface-card);">
          <span class="invite-link-url">${inviteUrl}</span>
          <button type="button" class="invite-action-btn" onclick="window.journeyApp.copyMyInviteLink()">📋 Copy</button>
          <button type="button" class="invite-action-btn secondary" onclick="window.journeyApp.shareMyInviteLink()">📤 Gửi</button>
        </div>

        <!-- Ô nhập link kết bạn hoặc @username của bạn bè -->
        <div style="display:flex;gap:6px;margin-top:8px;">
          <input
            type="text"
            id="addFriendLinkOrIdInput"
            class="form-input"
            placeholder="Dán link kết bạn hoặc nhập @username / tên bạn bè..."
            style="flex:1;padding:8px 11px;font-size:0.78rem;"
            onkeydown="if(event.key==='Enter'){event.preventDefault();window.journeyApp.submitAddFriendInput();}"
          />
          <button type="button" class="invite-action-btn" onclick="window.journeyApp.submitAddFriendInput()">
            + Kết bạn
          </button>
        </div>
      </div>

      ${requestsHtml}

      <div>
        <div style="font-size:0.76rem;font-weight:800;color:var(--text-secondary);margin-bottom:6px;text-transform:uppercase;">
          ${subTab === 'messages' ? '💬 Cuộc trò chuyện gần đây' : `🤝 Danh sách bạn bè (${this.friendsList.length})`}
        </div>
        <div style="display:flex;flex-direction:column;gap:8px;max-height:260px;overflow-y:auto;">
          ${friendsRowsHtml}
        </div>
      </div>
    `);
  }

  submitAddFriendInput() {
    const input = document.getElementById('addFriendLinkOrIdInput');
    if (!input) return;
    this.addFriendFromInviteCode(input.value);
  }

  openChatWithAuthor(authorName, placeName = '') {
    const found =
      this.friendsList.find((f) => f.name.toLowerCase() === authorName.toLowerCase()) ||
      this.friendsList[0];
    if (found) {
      this.openFriendChatModal(found.id, placeName);
    }
  }

  openFriendChatModal(friendId, locketContextPlace = '') {
    const friend =
      this.friendsList.find((f) => f.id === friendId) ||
      this.friendsList[0];
    if (!friend) return;

    if (!this.chatMessagesByFriend[friend.id]) {
      this.chatMessagesByFriend[friend.id] = [];
    }
    const messages = this.chatMessagesByFriend[friend.id];

    const messagesHtml =
      messages.length > 0
        ? messages
            .map(
              (m) => `
              <div class="chat-bubble-row ${m.fromMe ? 'mine' : 'theirs'}">
                ${
                  m.locketTag
                    ? `<span style="font-size:0.64rem;font-weight:700;color:var(--accent-gold);margin-bottom:2px;">${m.locketTag}</span>`
                    : ''
                }
                <div class="chat-bubble">${this.escapeHtml(m.text)}</div>
                <span class="chat-bubble-meta">${m.fromMe ? 'Bạn' : friend.name} · ${m.time || 'Vừa xong'}</span>
              </div>
            `
            )
            .join('')
        : `<div style="text-align:center;padding:24px 10px;color:var(--text-muted);font-size:0.8rem;">Hãy gửi tin nhắn hoặc biểu tượng cảm xúc đầu tiên cho ${friend.name}!</div>`;

    const activeLocketBanner = locketContextPlace || friend.latestLocketPlace || 'Việt Nam';

    this.openModal(`
      <div class="modal-header-row">
        <div style="display:flex;align-items:center;gap:9px;">
          <button
            type="button"
            class="btn-close-modal"
            title="Quay lại danh sách bạn bè"
            onclick="window.journeyApp.openFriendsAndChatHub('messages')"
          >
            ←
          </button>
          <div class="feed-author-avatar">${friend.avatarText}</div>
          <div>
            <div style="font-size:0.94rem;font-weight:800;color:var(--text-primary);display:flex;align-items:center;gap:5px;">
              <span>${friend.name}</span>
              ${friend.online ? '<span class="friend-online-dot" title="Đang hoạt động"></span>' : ''}
            </div>
            <div style="font-size:0.72rem;color:var(--text-secondary);">
              ${friend.handle} · 📸 Khoảnh khắc tại ${activeLocketBanner}
            </div>
          </div>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>

      <!-- Khung ngữ cảnh ảnh khoảnh khắc -->
      <div style="display:flex;align-items:center;justify-content:space-between;gap:10px;padding:8px 10px;border-radius:14px;background:var(--surface-subtle);border:1px solid var(--border-color);margin-bottom:8px;">
        <div style="display:flex;align-items:center;gap:8px;min-width:0;">
          <img src="${friend.latestLocketImg || 'images/img_hero_halong_bay.jpg'}" alt="${activeLocketBanner}" style="width:38px;height:38px;border-radius:10px;object-fit:cover;flex-shrink:0;" />
          <div style="min-width:0;">
            <div style="font-size:0.72rem;font-weight:800;color:var(--text-primary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
              📸 Phản hồi ảnh: ${activeLocketBanner}
            </div>
            <div style="font-size:0.66rem;color:var(--text-secondary);">Chạm biểu tượng để thả cảm xúc nhanh</div>
          </div>
        </div>
        <div style="display:flex;gap:3px;flex-shrink:0;">
          <button type="button" class="locket-quick-emoji-btn" onclick="window.journeyApp.sendDirectMessageToFriend('${friend.id}', '❤️ Thả tim cho khoảnh khắc của bạn!', '📸 ${activeLocketBanner}')">❤️</button>
          <button type="button" class="locket-quick-emoji-btn" onclick="window.journeyApp.sendDirectMessageToFriend('${friend.id}', '🔥 Ảnh đẹp quá!', '📸 ${activeLocketBanner}')">🔥</button>
          <button type="button" class="locket-quick-emoji-btn" onclick="window.journeyApp.sendDirectMessageToFriend('${friend.id}', '😍 Góc chụp đỉnh thật sự!', '📸 ${activeLocketBanner}')">😍</button>
        </div>
      </div>

      <!-- Danh sách tin nhắn -->
      <div id="locketChatMessagesBox" class="locket-chat-messages">
        ${messagesHtml}
      </div>

      <!-- Thanh nhập tin nhắn -->
      <div style="display:flex;gap:8px;align-items:center;">
        <input
          type="text"
          id="locketChatModalInput"
          class="form-input"
          placeholder="Nhắn tin cho ${friend.name}..."
          style="flex:1;"
          onkeydown="if(event.key==='Enter'){event.preventDefault();window.journeyApp.sendDirectMessageToFriend('${friend.id}', null, '📸 ${activeLocketBanner}');}"
        />
        <button
          type="button"
          class="btn-save-profile"
          style="width:auto;padding:10px 16px;"
          onclick="window.journeyApp.sendDirectMessageToFriend('${friend.id}', null, '📸 ${activeLocketBanner}')"
        >
          Gửi ➤
        </button>
      </div>
    `);

    setTimeout(() => {
      const box = document.getElementById('locketChatMessagesBox');
      if (box) box.scrollTop = box.scrollHeight;
      const input = document.getElementById('locketChatModalInput');
      if (input) input.focus();
    }, 50);
  }

  sendDirectMessageToFriend(friendId, customText = null, locketTag = '') {
    const friend = this.friendsList.find((f) => f.id === friendId);
    if (!friend) return;

    const inputEl = document.getElementById('locketChatModalInput');
    const rawText = customText !== null ? customText : inputEl ? inputEl.value.trim() : '';
    if (!rawText) return;

    const now = new Date();
    const timeStr = `${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;

    if (!this.chatMessagesByFriend[friend.id]) {
      this.chatMessagesByFriend[friend.id] = [];
    }

    this.chatMessagesByFriend[friend.id].push({
      id: `msg_${Date.now()}`,
      fromMe: true,
      text: rawText,
      time: timeStr,
      locketTag: locketTag || `📸 ${friend.latestLocketPlace || 'Khoảnh khắc'}`
    });
    this.saveJson('journey_locket_chats', this.chatMessagesByFriend);

    this.openFriendChatModal(friend.id);
  }

  sendQuickLocketReplyFromFeed(postId, emojiOnly = null) {
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;

    const inputEl = document.getElementById(`locketReplyInput_${postId}`);
    const typed = inputEl ? inputEl.value.trim() : '';
    const textToSend = emojiOnly
      ? `${emojiOnly} Đã thả cảm xúc vào khoảnh khắc của bạn${post.placeName ? ' tại ' + post.placeName : ''}!`
      : typed;

    if (!textToSend) {
      this.openChatWithAuthor(post.authorName, post.placeName);
      return;
    }

    if (inputEl) inputEl.value = '';

    let friend = this.friendsList.find(
      (f) => f.name.toLowerCase() === (post.authorName || '').toLowerCase()
    );
    if (!friend) {
      friend = this.friendsList[0];
    }
    if (!friend) return;

    this.sendDirectMessageToFriend(friend.id, textToSend, post.placeName ? `📸 ${post.placeName}` : '📸 Khoảnh khắc');
    this.showToast(`💬 Đã gửi tin nhắn cho ${friend.name}!`);
  }

  /* =========================================================================
     MODALS, AUDIO STORY & TOAST UTILITIES
     ========================================================================= */
  openDestinationModal(destId) {
    const allPlaces = this.getAllVisitedPlaces();
    const d = allPlaces.find((p) => p.id === destId) || DESTINATIONS[destId] || DESTINATIONS.halong;

    this.openModal(`
      <div class="modal-header-row">
        <div>
          <span style="font-size:0.7rem;font-weight:800;color:var(--accent-gold-dark);">${d.categoryVi}</span>
          <h3 class="serif-title" style="font-size:1.25rem;">${d.nameVi} (${d.province})</h3>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <img src="${d.image}" alt="${d.nameVi}" style="width:100%;height:200px;object-fit:cover;border-radius:16px;margin-bottom:12px;" />
      <p style="font-size:0.86rem;line-height:1.55;color:var(--text-secondary);margin-bottom:14px;">
        ${d.storyVi || d.shortDescVi}
      </p>
      <div style="display:flex;gap:8px;flex-wrap:wrap;">
        <button class="chip-btn active" onclick="window.journeyApp.playDestinationAudio('${d.id}')">
          🔊 Nghe thuyết minh
        </button>
        <button class="chip-btn" onclick="window.journeyApp.closeModal(); window.journeyApp.switchTab('discover')">
          📷 Mở Ống kính chụp ảnh
        </button>
        <button class="chip-btn" onclick="window.journeyApp.closeModal(); window.journeyApp.viewPlaceOnMap('${d.id}')">
          🗺️ Xem trên Bản đồ VN
        </button>
      </div>
    `);
  }

  playDestinationAudio(destId) {
    const allPlaces = this.getAllVisitedPlaces();
    const d = allPlaces.find((p) => p.id === destId) || DESTINATIONS[destId] || DESTINATIONS.halong;
    this.showToast(`🔊 Đang đọc thuyết minh: ${d.nameVi}`);

    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      const utter = new SpeechSynthesisUtterance(`${d.nameVi}. ${d.storyVi || d.shortDescVi}`);
      utter.lang = 'vi-VN';
      utter.rate = 1.0;
      window.speechSynthesis.speak(utter);
    }
  }

  openModal(html) {
    const overlay = document.getElementById('modalOverlay');
    const content = document.getElementById('modalContent');
    if (!overlay || !content) return;
    content.innerHTML = html;
    overlay.classList.add('open');
  }

  closeModal() {
    const overlay = document.getElementById('modalOverlay');
    if (overlay) overlay.classList.remove('open');
  }

  /**
   * Handles receiving a real incoming friend request (e.g. via invite link or user request payload)
   * and triggers a Toast Notification with a quick action button to open Pending Friend Requests.
   */
  receiveNewFriendRequest(customRequester = null) {
    if (!customRequester || !customRequester.name) {
      return null;
    }

    const cleanHandle = (customRequester.handle || `@${customRequester.name.toLowerCase().replace(/\s+/g, '.')}`).startsWith('@')
      ? customRequester.handle
      : `@${customRequester.handle || customRequester.name.toLowerCase().replace(/\s+/g, '.')}`;

    if (
      this.pendingFriendRequests.some((r) => r.handle === cleanHandle) ||
      this.friendsList.some((f) => f.handle === cleanHandle)
    ) {
      return null;
    }

    const newReq = {
      id: `req_${Date.now()}`,
      name: customRequester.name,
      handle: cleanHandle,
      avatarText: customRequester.avatarText || this.getInitials(customRequester.name),
      city: customRequester.city || 'Việt Nam',
      mutualNote: customRequester.mutualNote || 'Qua đường link kết bạn'
    };

    this.pendingFriendRequests.unshift(newReq);
    this.saveJson('journey_friend_requests', this.pendingFriendRequests);
    this.updateHeaderUnreadBadge();

    if (this.activeTab === 'profile') {
      this.renderProfileTab();
    } else if (this.activeTab === 'home') {
      this.renderHomeTab();
    }

    this.showFriendRequestToast(newReq);
    return newReq;
  }

  showFriendRequestToast(req) {
    const senderName = req && req.name ? `${req.name} (${req.handle || ''})` : 'Một người bạn mới';
    this.showToast(`💌 Lời mời kết bạn mới từ ${senderName}!`, {
      actionLabel: 'Xem lời mời →',
      actionTestId: 'toast-open-pending-requests-btn',
      durationMs: 6500,
      onAction: () => {
        this.openPendingFriendRequestsSection();
      }
    });
  }

  openPendingFriendRequestsSection() {
    this.hideToast();
    this.closeModal();

    if (this.activeTab !== 'profile') {
      this.switchTab('profile');
    } else {
      this.renderProfileTab();
    }

    setTimeout(() => {
      const pendingEl = document.getElementById('pendingFriendRequestsSection');
      if (pendingEl) {
        pendingEl.scrollIntoView({ behavior: 'smooth', block: 'center' });
        pendingEl.classList.remove('highlight-pulse');
        void pendingEl.offsetWidth;
        pendingEl.classList.add('highlight-pulse');
      }
    }, 120);
  }

  hideToast() {
    const toast = document.getElementById('toastBanner');
    if (!toast) return;
    clearTimeout(this._toastTimer);
    toast.classList.remove('show');
  }

  showToast(message, options = {}) {
    const toast = document.getElementById('toastBanner');
    if (!toast) return;

    const {
      actionLabel = '',
      actionTestId = 'toast-action-btn',
      onAction = null,
      durationMs = 3200
    } = options || {};

    this._activeToastAction = typeof onAction === 'function' ? onAction : null;

    if (actionLabel && this._activeToastAction) {
      toast.setAttribute('data-testid', 'friend-request-toast');
      toast.innerHTML = `
        <span class="toast-message-text">${this.escapeHtml(message)}</span>
        <button
          type="button"
          id="toastActionOpenPendingBtn"
          class="toast-action-btn"
          data-testid="${actionTestId}"
          onclick="window.journeyApp.triggerActiveToastAction()"
        >
          ${this.escapeHtml(actionLabel)}
        </button>
        <button
          type="button"
          class="toast-dismiss-btn"
          aria-label="Đóng thông báo"
          onclick="window.journeyApp.hideToast()"
        >
          ✕
        </button>
      `;
    } else {
      toast.removeAttribute('data-testid');
      toast.innerHTML = `<span class="toast-message-text">${this.escapeHtml(message)}</span>`;
    }

    toast.classList.add('show');
    clearTimeout(this._toastTimer);
    this._toastTimer = setTimeout(() => {
      toast.classList.remove('show');
    }, durationMs);
  }

  triggerActiveToastAction() {
    const fn = this._activeToastAction;
    this._activeToastAction = null;
    this.hideToast();
    if (typeof fn === 'function') {
      fn();
    }
  }
}

window.addEventListener('DOMContentLoaded', () => {
  window.journeyApp = new JourneyAppController();
});

