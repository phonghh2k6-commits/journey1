# JOURNEY AI — Master Product Design System & Architecture Handoff

**Product:** JOURNEY AI  
**Tagline:** Explore the World. Discover the Stories. / Khám phá Thế giới. Lắng nghe Câu chuyện.  
**Target Stack:** Flutter (iOS & Android) • FastAPI (AI Vision, RAG Assistant & Media Engine) • Supabase (Auth, PostgreSQL + PostGIS, Storage)

---

## 1. Brand Palette & Semantic Color Tokens (Phase 1)

| Token Name | Brand Reference | Dark Mode (`#111923` Base) | Light Mode (`#E9E5DC` Base) |
| :--- | :--- | :--- | :--- |
| `primaryBackground` | Midnight / Ivory | `#111923` | `#E9E5DC` |
| `secondaryBackground` | Obsidian / Warm Parchment | `#0B1017` | `#DFD9CC` |
| `surface` | Slate / Soft Ivory | `#1C2934` | `#F6F4EF` |
| `elevatedSurface` | Elevated Slate / Pure White | `#243442` | `#FFFFFF` |
| `primaryText` | Ivory / Midnight | `#E9E5DC` | `#111923` |
| `secondaryText` | Mist / Muted Slate | `#A5B2BC` | `#5A6B78` |
| `border` | Hairline Divider | `#2E3E4E` | `#D0C9B8` |
| `accent` | Sand Gold | `#B99B72` | `#9E825B` |
| `sage` | Sage | `#879A91` | `#879A91` |
| `success` | Jade | `#5E9A78` | `#3B7A57` |
| `warning` | Amber | `#D99B4E` | `#B87729` |
| `error` | Crimson | `#C85A5A` | `#B03A3A` |

---

## 2. Complete Screen Inventory (34 Screen IDs across Groups A–I)

### Phase 1 & Phase 6 Deliverable Screens
- `P1-DS01`: Design System, Brand Swatches, Semantic Tokens & Component Library
- `P6-QA01`: Flutter / FastAPI / Supabase Architecture Blueprint & UI/UX Acceptance Report

### Group A — Onboarding & Authentication (Bottom Navigation Hidden)
- `A1-SPLASH`: Splash Screen
- `A2-WELCOME`: Bilingual Welcome (EN/VI)
- `A3-ONBOARD`: 3-Slide Editorial Capability Walkthrough
- `A4-PERMS`: Camera, Location & Microphone Permission Introduction
- `A5-LOGIN`: Supabase Auth Login
- `A6-REGISTER`: Explorer Account Registration
- `A7-FORGOT`: Password Recovery
- `A8-VERIFY`: 6-Digit Email OTP Verification

### Group B — Home
- `B1-HOME`: Home Dashboard (Full-bleed Ha Long Bay Hero, Atelier Strip, Nearby Stories, Collections)
- `B2-SEARCH`: Global Search with Historical Era & Region Filters
- `B3-NOTIF`: Cultural Dispatch & AI Story Ready Notifications

### Group C — Explore
- `C1-MAP`: Interactive Topographic Explore Map with PostGIS Pins & Bottom Preview Sheet
- `C2-FEED`: Editorial Discovery Feed
- `C3-PLACE`: Place Details Dossier
- `C4-SAVED`: Saved Places & Offline Heritage Packs

### Group D — AI Visual Discovery
- `D1-CAMERA`: Full-Screen AI Camera Viewfinder (Bottom Navigation Hidden)
- `D2-PREVIEW`: Captured Frame & EXIF Coordinate Verification (Bottom Navigation Hidden)
- `D3-PROCESS`: Simulated AI Vision Recognition Pipeline (Bottom Navigation Hidden)
- `D4-RESULT`: Recognition Result Card & Confidence Score
- `D5-DETAIL`: Deep Architectural & Historical Monograph

### Group E — AI Assistant & Voice Guide
- `E1-CHAT`: Contextual AI Scholar Chat
- `E2-VOICE`: Full-Screen Binaural AI Voice Travel Guide (Bottom Navigation Hidden)
- `E3-HISTORY`: Saved Conversation History & Field Notes

### Group F — Personal Travel Journal
- `F1-JOURNAL`: Travel Journal Home
- `F2-NEW-TRIP`: Create New Trip Folio
- `F3-TRIP`: Trip Detail & Moment Stream
- `F4-TIMELINE`: Chronological Trip Timeline
- `F5-MOMENT`: Create Daily Moment
- `F6-MEMORY`: Memory Detail

### Group G — Discovery Collections
- `G1-COLLS`: Curated Discovery Collections
- `G2-COLL-DET`: Collection Details

### Group H — Memories & Cinematic Video Studio
- `H1-STORY`: AI-Generated Illustrated Story Preview
- `H2-VIDEO-EDIT`: Multi-Clip Travel Video Creator (Aspect Ratio & Acoustic Soundtrack)
- `H3-VIDEO-EXP`: Full-Screen 4K Video Preview & Export (Bottom Navigation Hidden)

### Group I — Profile & Membership
- `I1-PROFILE`: Explorer Profile & Statistics
- `I2-SETTINGS`: Theme, Language (VI/EN), & Voice Speed Settings
- `I3-SUB`: Journey+ Membership & Plan Comparison
- `I4-PRIVACY`: Privacy, Permissions & Supabase RLS Data Controls
