package com.masouddarvish.noor.model

sealed class Screen(val title: String) {
    data object Home : Screen("ذکران")
    data object AyatKursi : Screen("آیت‌الکرسی")
    data object Counter : Screen("ذکر و اذکار")
    data object More : Screen("بیشتر")
    data object PrayerTimes : Screen("اوقات شرعی")
    data object Qibla : Screen("قبله‌نما")
    data object Favorites : Screen("علاقه‌مندی‌ها")
    data object Settings : Screen("تنظیمات")
    data object About : Screen("درباره ذکران")
}
