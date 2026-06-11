# 🏆 World Cup 2026 App

A full-featured Android app for the FIFA World Cup 2026 (June 11 – July 19, 2026).

## Features
- 📅 **Fixtures** — All 104 matches with live score updates, group filter
- 📊 **Standings** — Group tables for all 12 groups (A–L), live points
- 🌍 **Teams** — All 48 teams with flags, group, confederation, search
- 🏆 **Knockout** — Round of 32 through Final bracket

## API
Uses the **free open-source World Cup 2026 REST API** (no API key needed for read access).

## How to Build Locally (Android Studio)
1. Open Android Studio → **File > Open** → select this folder
2. Wait for Gradle sync
3. Click **▶ Run** or **Build > Build APK**

## How to Build via GitHub Actions (Auto APK)
1. Create a GitHub account at [github.com](https://github.com)
2. Create a new repository: **+ > New repository**
3. Push this code:
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/YOUR_USERNAME/worldcup2026.git
   git push -u origin main
   ```
4. Go to your repo → **Actions** tab
5. The workflow runs automatically! ✅
6. When done, click the workflow run → **Artifacts** section → download APK

## Play Store Publishing
1. Build the AAB: `./gradlew bundleRelease`
2. Sign it with your keystore (Android Studio: **Build > Generate Signed Bundle**)
3. Upload to [Google Play Console](https://play.google.com/console)

## Tech Stack
- Kotlin
- MVVM + LiveData + ViewModel
- Retrofit 2 + OkHttp
- Navigation Component
- Glide (image loading)
- Material Design 3
