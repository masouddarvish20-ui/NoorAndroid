package com.masouddarvish.noor.ui.screens

import java.time.LocalDate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.masouddarvish.noor.BuildConfig
import com.masouddarvish.noor.data.*
import com.masouddarvish.noor.model.Screen
import com.masouddarvish.noor.ui.components.*
import com.masouddarvish.noor.ui.theme.*

@Composable
fun HomeScreen(onNavigate: (Screen) -> Unit) {
    val dayOfWeek = remember { java.time.LocalDate.now().dayOfWeek.value }
    val prayer = remember(dayOfWeek) { ReligiousContent.dailyPrayer(dayOfWeek) }
    val dhikr = remember(dayOfWeek) { ReligiousContent.dailyDhikr(dayOfWeek) }
    val today = remember { PersianDate.today() }
    val todayEvent = remember(today) { solarEvent(today.month, today.day) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { BrandHero() }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = .45f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "ذکران امروز",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${today.day} ${today.monthName} ${today.year}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Surface(
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .10f)
                    ) {
                        Text(
                            text = todayEvent?.title ?: "امروز مناسبت ثابت شمسی ویژه‌ای ثبت نشده است.",
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
                            textAlign = TextAlign.Center,
                            color = if (todayEvent?.holiday == true) Color(0xFFE57373) else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = .22f)
                    )
                    Text(
                        "دعای امروز • ${prayer.first}",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        prayer.second,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        prayer.third,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "ذکر روز",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        dhikr.second,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                }
            }
        }
        }
    }

private val ayatLines = listOf(
    "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ" to "خداست که هیچ معبودی جز او نیست؛ زنده و برپادارنده است.",
    "لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ" to "نه خواب سبک او را فرا می‌گیرد و نه خواب سنگین.",
    "لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ" to "آنچه در آسمان‌ها و آنچه در زمین است از آنِ اوست.",
    "مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ" to "کیست که جز به اذن او نزدش شفاعت کند؟",
    "يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ" to "آنچه پیش روی آنان و آنچه پشت سرشان است می‌داند.",
    "وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ" to "و آنان جز به آنچه او بخواهد به چیزی از علمش احاطه نمی‌یابند.",
    "وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ" to "کرسی او آسمان‌ها و زمین را فرا گرفته است.",
    "وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ" to "و نگهداری آن دو بر او دشوار نیست، و او بلندمرتبه و بزرگ است.",
    "لَا إِكْرَاهَ فِي الدِّينِ ۖ قَدْ تَبَيَّنَ الرُّشْدُ مِنَ الْغَيِّ" to "در دین هیچ اجباری نیست؛ راه درست از راه انحرافی آشکار شده است.",
    "فَمَنْ يَكْفُرْ بِالطَّاغُوتِ وَيُؤْمِنْ بِاللَّهِ" to "پس هر کس به طاغوت کفر ورزد و به خدا ایمان آورد،",
    "فَقَدِ اسْتَمْسَكَ بِالْعُرْوَةِ الْوُثْقَىٰ لَا انْفِصَامَ لَهَا" to "به دستاویزی استوار چنگ زده است که گسستنی برای آن نیست.",
    "وَاللَّهُ سَمِيعٌ عَلِيمٌ" to "و خدا شنوا و داناست."
)

@Composable
fun AyatKursiScreen() {
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("noor", 0) }
    var fav by remember { mutableStateOf(prefs.getBoolean("fav_ayat", false)) }
    val arabicSize = prefs.getInt("arabicFontSize", prefs.getInt("fontSize", 22))
    val persianSize = prefs.getInt("persianFontSize", 16)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SacredHeader("آیت‌الکرسی", "سوره بقره • آیات ۲۵۵ و ۲۵۶") }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "عربی و ترجمه فارسی زیر هر بخش",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                FavoriteButton(fav) {
                    fav = !fav
                    prefs.edit().putBoolean("fav_ayat", fav).apply()
                }
            }
        }
        item { AudioButton("https://cdn.islamic.network/quran/audio/128/ar.alafasy/262.mp3") }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(30.dp),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "۞ بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۞",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                    ayatLines.forEachIndexed { index, (ar, fa) ->
                        Spacer(Modifier.height(16.dp))
                        Text(
                            ar,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = arabicSize.sp,
                            lineHeight = (arabicSize + 16).sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            fa,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = persianSize.sp,
                            lineHeight = (persianSize + 10).sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (index < ayatLines.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(top = 14.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = .22f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProtectionScreen() {
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("noor", 0) }
    val size = prefs.getInt("arabicFontSize", prefs.getInt("fontSize", 22))

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { SacredHeader("آیات محافظت", "آیات منتخب قرآن • متن عربی و معنی فارسی") }
        items(ReligiousContent.protection) { item ->
            var fav by remember(item.id) { mutableStateOf(prefs.getBoolean("fav_${item.id}", false)) }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(26.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = .30f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            item.title,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 20.sp
                        )
                        FavoriteButton(fav) {
                            fav = !fav
                            prefs.edit().putBoolean("fav_${item.id}", fav).apply()
                        }
                    }
                    if (!item.audioUrl.isNullOrBlank()) {
                        Spacer(Modifier.height(10.dp))
                        AudioButton(item.audioUrl)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        item.arabic,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        lineHeight = (size + 14).sp,
                        fontSize = size.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (item.translation.isNotBlank()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 14.dp),
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = .20f)
                        )
                        Text(
                            item.translation,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuranScreen(onNavigate: (Screen) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SacredHeader("قرآن منتخب", "مطالعه آیات و سوره‌های منتخب") }
        item { FeatureCard("آیت‌الکرسی", "سوره بقره، آیات ۲۵۵ و ۲۵۶ • معنی و تلاوت") { onNavigate(Screen.AyatKursi) } }
        item { ReadingCard("سوره حمد", ReligiousContent.FATIHA, 21) }
        item { ReadingCard("سوره اخلاص", ReligiousContent.IKHLAS, 21, ReligiousContent.IKHLAS_FA) }
    }
}

@Composable
fun PrayersScreen() {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SacredHeader("دعا و نیایش", "ذکرها و نیایش‌های موجود در ذکران") }
        item { ReadingCard("صلوات", "اللهم صل علی محمد و آل محمد", 22) }
        item { ReadingCard("استغفار", "أَسْتَغْفِرُ اللَّهَ وَ أَتُوبُ إِلَيْهِ", 22) }
        item { ReadingCard("ذکر یونسیه", "لا إله إلا أنت سبحانك إني كنت من الظالمين", 22) }
    }
}

@Composable
fun CounterScreen() {
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("noor", 0) }
    val allDhikrs = ReligiousContent.dhikrs.take(3)
    var selected by remember { mutableIntStateOf(prefs.getInt("dhikr_index", 0).coerceIn(0, allDhikrs.lastIndex)) }
    var count by remember(selected) { mutableIntStateOf(prefs.getInt("counter_$selected", 0)) }
    var target by remember(selected) { mutableIntStateOf(prefs.getInt("target_$selected", allDhikrs[selected].defaultTarget)) }
        val todayKey = remember { LocalDate.now().toString() }
    var todayTotal by remember { mutableIntStateOf(prefs.getInt("daily_total_$todayKey", 0)) }
    val item = allDhikrs[selected]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SacredHeader("ذکر و صلوات", "ذکر شما • با نیت خیر و آرامش") }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(26.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = .6f))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("ذکر شما", color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(item.title, color = MaterialTheme.colorScheme.secondary, fontSize = 21.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.height(7.dp))
                    Text(item.text, color = MaterialTheme.colorScheme.onSurface, fontSize = 21.sp, lineHeight = 34.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    Text("$count", fontSize = 66.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface)
                    LinearProgressIndicator(
                        progress = { (count.toFloat() / target.coerceAtLeast(1)).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            count++
                            todayTotal++
                            prefs.edit().putInt("counter_$selected", count).putInt("daily_total_$todayKey", todayTotal).apply()
                        },
                        modifier = Modifier.fillMaxWidth().height(76.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
                    ) { Text("ذکر +۱", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
                    Text("امروز: $todayTotal ذکر", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    Row {
                        listOf(33, 70, 100, 313).forEach {
                            TextButton(onClick = { target = it; prefs.edit().putInt("target_$selected", it).apply() }) {
                                Text("$it", color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                    TextButton(onClick = { count = 0; prefs.edit().putInt("counter_$selected", 0).apply() }) {
                        Text("صفر کردن", color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
        item {
            Text(
                "انتخاب ذکر",
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        items(allDhikrs.size) { index ->
            val entry = allDhikrs[index]
            Card(
                onClick = {
                    selected = index
                    prefs.edit().putInt("dhikr_index", index).apply()
                },
                colors = CardDefaults.cardColors(containerColor = if (index == selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(15.dp)) {
                    Text(entry.title, fontWeight = FontWeight.Bold, color = if (index == selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface)
                    Text(entry.text, color = if (index == selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

private data class CalEvent(val title: String, val holiday: Boolean = false)

private fun solarEvent(month: Int, day: Int): CalEvent? = when (month to day) {
    1 to 1 -> CalEvent("آغاز نوروز", true)
    1 to 2 -> CalEvent("عید نوروز", true)
    1 to 3 -> CalEvent("عید نوروز", true)
    1 to 4 -> CalEvent("عید نوروز", true)
    1 to 12 -> CalEvent("روز جمهوری اسلامی", true)
    1 to 13 -> CalEvent("روز طبیعت", true)
    1 to 25 -> CalEvent("روز بزرگداشت عطار نیشابوری")
    2 to 1 -> CalEvent("روز بزرگداشت سعدی")
    2 to 25 -> CalEvent("روز بزرگداشت فردوسی")
    3 to 14 -> CalEvent("رحلت امام خمینی (ره)", true)
    3 to 15 -> CalEvent("قیام ۱۵ خرداد", true)
    6 to 1 -> CalEvent("روز بزرگداشت ابوعلی سینا و روز پزشک")
    6 to 8 -> CalEvent("روز مبارزه با تروریسم")
    6 to 27 -> CalEvent("روز شعر و ادب فارسی")
    7 to 13 -> CalEvent("روز نیروی انتظامی")
    7 to 20 -> CalEvent("روز بزرگداشت حافظ")
    8 to 13 -> CalEvent("روز دانش‌آموز")
    9 to 16 -> CalEvent("روز دانشجو")
    9 to 30 -> CalEvent("شب یلدا")
    10 to 9 -> CalEvent("روز بصیرت")
    11 to 22 -> CalEvent("پیروزی انقلاب اسلامی", true)
    12 to 5 -> CalEvent("روز بزرگداشت خواجه نصیرالدین طوسی و روز مهندس")
    12 to 29 -> CalEvent("ملی شدن صنعت نفت", true)
    else -> null
}

@Composable
fun CalendarScreen() {
    val today = remember { PersianDate.today() }
    var year by remember { mutableIntStateOf(today.year) }
    var month by remember { mutableIntStateOf(today.month) }
    var selectedDay by remember { mutableIntStateOf(today.day) }

    val monthLength = PersianDate.monthLength(year, month)
    if (selectedDay > monthLength) selectedDay = monthLength
    val firstGregorian = remember(year, month) { PersianDate.toGregorian(year, month, 1) }
    val offset = remember(firstGregorian) { (firstGregorian.dayOfWeek.value + 1) % 7 }
    val weekdays = listOf("ش", "ی", "د", "س", "چ", "پ", "ج")
    val cells = remember(year, month, offset, monthLength) {
        val raw = List(offset) { 0 } + (1..monthLength).toList()
        raw + List((7 - raw.size % 7) % 7) { 0 }
    }
    val selectedGregorian = remember(year, month, selectedDay) { PersianDate.toGregorian(year, month, selectedDay) }
    val selectedEvent = solarEvent(month, selectedDay)
    val prayer = remember(selectedGregorian) { ReligiousContent.dailyPrayer((selectedGregorian.dayOfWeek.value % 7) + 1) }
    val dhikr = remember(selectedGregorian) { ReligiousContent.dailyDhikr((selectedGregorian.dayOfWeek.value % 7) + 1) }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SacredHeader("تقویم شمسی", "${PersianDate.MONTHS[month - 1]} $year") }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = .55f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = { val m = PersianDate.shiftMonth(year, month, -1); year = m.first; month = m.second; selectedDay = 1 }) { Text("ماه قبل", color = MaterialTheme.colorScheme.onSurface) }
                        Text("${PersianDate.MONTHS[month - 1]} $year", color = MaterialTheme.colorScheme.secondary, fontSize = 22.sp, fontWeight = FontWeight.Black)
                        TextButton(onClick = { val m = PersianDate.shiftMonth(year, month, 1); year = m.first; month = m.second; selectedDay = 1 }) { Text("ماه بعد", color = MaterialTheme.colorScheme.onSurface) }
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.secondary.copy(alpha = .25f))
                    Row(Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        weekdays.forEachIndexed { index, label ->
                            Text(
                                label,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = if (index == 6) Color(0xFFFF8A80) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    cells.chunked(7).forEach { week ->
                        Row(Modifier.fillMaxWidth()) {
                            week.forEachIndexed { columnIndex, day ->
                                if (day == 0) {
                                    Spacer(Modifier.weight(1f).aspectRatio(1f))
                                } else {
                                    val isToday = year == today.year && month == today.month && day == today.day
                                    val isSelected = day == selectedDay
                                    val holiday = columnIndex == 6 || solarEvent(month, day)?.holiday == true
                                    Surface(
                                        onClick = { selectedDay = day },
                                        modifier = Modifier.weight(1f).padding(2.dp).aspectRatio(1f),
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                                        color = when {
                                            isToday -> Gold
                                            isSelected -> MaterialTheme.colorScheme.primary
                                            else -> Color.Transparent
                                        },
                                        border = if (holiday) BorderStroke(1.dp, Color(0xFFFF8A80).copy(alpha = .7f)) else null
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                "$day",
                                                fontWeight = if (isToday || isSelected) FontWeight.Black else FontWeight.Medium,
                                                color = when {
                                                    isToday -> Color(0xFF153A32)
                                                    holiday -> Color(0xFFFF8A80)
                                                    else -> MaterialTheme.colorScheme.onSurface
                                                }
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
        item {
            Card(Modifier.fillMaxWidth(), shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("$selectedDay ${PersianDate.MONTHS[month - 1]} $year", fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    Text("تاریخ میلادی: ${selectedGregorian.dayOfMonth}/${selectedGregorian.monthValue}/${selectedGregorian.year}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        selectedEvent?.title ?: if (selectedGregorian.dayOfWeek.value == 5) "جمعه • تعطیل هفتگی" else "مناسبت ثابت ثبت‌شده‌ای برای این روز وجود ندارد.",
                        fontWeight = FontWeight.Bold,
                        color = if (selectedEvent?.holiday == true || selectedGregorian.dayOfWeek.value == 5) Color(0xFFE57373) else MaterialTheme.colorScheme.onSurface
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.secondary.copy(alpha = .20f))
                    Text("دعای روز", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    Text(prayer.second, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Text(prayer.third, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Text("ذکر روز", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    Text(dhikr.second, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        item {
            OutlinedButton(onClick = { year = today.year; month = today.month; selectedDay = today.day }, modifier = Modifier.fillMaxWidth()) {
                Text("بازگشت به امروز")
            }
        }
        item {
            Text(
                "جمعه‌ها و تعطیلات رسمیِ ثابت شمسی مشخص شده‌اند. مناسبت‌های قمری متغیر بدون داده رسمی سالانه به‌صورت حدسی نمایش داده نمی‌شوند.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MoreScreen(onNavigate: (Screen)->Unit){
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)){
        item{ SacredHeader("بیشتر", "امکانات کاربردی ذکران") }
        item{ FeatureCard("اوقات شرعی","زمان‌های نماز") { onNavigate(Screen.PrayerTimes) } }
        item{ FeatureCard("قبله‌نما","جهت قبله") { onNavigate(Screen.Qibla) } }
        item{ FeatureCard("علاقه‌مندی‌ها","آیات ذخیره شده") { onNavigate(Screen.Favorites) } }
        item{ FeatureCard("تنظیمات","تم و اندازه متن") { onNavigate(Screen.Settings) } }
        item{ FeatureCard("درباره ذکران","اطلاعات برنامه") { onNavigate(Screen.About) } }
    }
}

@Composable
fun FavoritesScreen() {
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("noor", 0) }
    val favs = ReligiousContent.protection.filter { prefs.getBoolean("fav_${it.id}", false) }.toMutableList()
    if (prefs.getBoolean("fav_ayat", false) && favs.none { it.id == "ayat" }) favs.add(0, ReligiousContent.protection.first())

    if (favs.isEmpty()) {
        SimpleScreen("علاقه‌مندی‌ها", "هنوز موردی ذخیره نکرده‌اید. از آیکون قلب کنار آیات استفاده کنید.")
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(favs) { ReadingCard(it.title, it.arabic, 18, it.translation) }
        }
    }
}

@Composable
fun SettingsScreen(themeIndex: Int, onTheme: (Int) -> Unit) {
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("noor", 0) }
    var arabicSize by remember { mutableIntStateOf(prefs.getInt("arabicFontSize", prefs.getInt("fontSize", 22))) }
    var persianSize by remember { mutableIntStateOf(prefs.getInt("persianFontSize", 16)) }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { SacredHeader("تنظیمات ذکران", "ظاهر و خوانایی را مطابق سلیقه خود تنظیم کنید") }
        item {
            Text(
                "انتخاب تم",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        items(NoorPalettes.size) { i ->
            val p = NoorPalettes[i]
            Card(
                onClick = { onTheme(i) },
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(22.dp),
                border = if (themeIndex == i) BorderStroke(2.dp, MaterialTheme.colorScheme.secondary) else BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = .22f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
                            color = p.primary,
                            modifier = Modifier.size(42.dp),
                            border = BorderStroke(3.dp, p.accent)
                        ) {}
                        Text(if (themeIndex == i) "فعال" else "انتخاب", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                    }
                    Text(p.name, fontWeight = FontWeight.Black, fontSize = 18.sp)
                }
            }
        }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(22.dp)) {
                Column(Modifier.padding(18.dp)) {
                    Text("اندازه متن عربی: $arabicSize", fontWeight = FontWeight.Bold)
                    Slider(
                        value = arabicSize.toFloat(),
                        onValueChange = { arabicSize = it.toInt(); prefs.edit().putInt("arabicFontSize", arabicSize).apply() },
                        valueRange = 18f..34f,
                        steps = 7
                    )
                    Text(
                        "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = arabicSize.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    HorizontalDivider(Modifier.padding(vertical = 12.dp))
                    Text("اندازه ترجمه فارسی: $persianSize", fontWeight = FontWeight.Bold)
                    Slider(
                        value = persianSize.toFloat(),
                        onValueChange = { persianSize = it.toInt(); prefs.edit().putInt("persianFontSize", persianSize).apply() },
                        valueRange = 14f..24f,
                        steps = 4
                    )
                    Text(
                        "خداست که هیچ معبودی جز او نیست.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = persianSize.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun MemorialScreen() {
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("noor", 0) }
    var salawat by remember { mutableIntStateOf(prefs.getInt("memorial_salawat", 0)) }
    var intention by remember { mutableStateOf(prefs.getString("memorial_page_intention", "پدر و مادرم") ?: "پدر و مادرم") }
    var customName by remember { mutableStateOf(prefs.getString("memorial_page_name", "") ?: "") }
    val targetText = when (intention) {
        "پدر و مادرم" -> "به روح پدر و مادرم"
        "همه درگذشتگان" -> "به روح همه درگذشتگان"
        else -> if (customName.isBlank()) "با نیت شخصی" else "به روح $customName"
    }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = .6f))
            ) {
                Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("هدیه ثواب", fontSize = 26.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(8.dp))
                    Text(targetText, color = MaterialTheme.colorScheme.onSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        listOf("پدر و مادرم", "همه درگذشتگان", "نام دلخواه").forEach { label ->
                            FilterChip(
                                selected = intention == label,
                                onClick = {
                                    intention = label
                                    prefs.edit().putString("memorial_page_intention", label).apply()
                                },
                                label = { Text(label, fontSize = 11.sp) }
                            )
                        }
                    }
                    if (intention == "نام دلخواه") {
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = customName,
                            onValueChange = {
                                customName = it
                                prefs.edit().putString("memorial_page_name", it).apply()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("نام فرد درگذشته") },
                            singleLine = true
                        )
                    }
                }
            }
        }
        item { ReadingCard("سوره حمد", ReligiousContent.FATIHA, 22) }
        item {
            Card {
                Column(Modifier.fillMaxWidth().padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("صلوات هدیه‌شده", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("اللهم صل علی محمد و آل محمد", fontSize = 21.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    Text("$salawat", fontSize = 52.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    Button(onClick = { salawat++; prefs.edit().putInt("memorial_salawat", salawat).apply() }, modifier = Modifier.fillMaxWidth().height(62.dp)) {
                        Text("یک صلوات برای این نیت", fontWeight = FontWeight.Bold)
                    }
                    Text("مجموع ثبت‌شده برای هدیه ثواب: $salawat", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
                    TextButton(onClick = { salawat = 0; prefs.edit().putInt("memorial_salawat", 0).apply() }) {
                        Text("شروع شمارش دوباره")
                    }
                }
            }
        }
        item {
            Card {
                Column(Modifier.padding(18.dp)) {
                    Text("دعای خیر", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "خدایا پدران و مادران درگذشته و همه اموات را مشمول رحمت و آمرزش خود قرار ده و یاد و نیکی آنان را در دل بازماندگان زنده بدار.",
                        fontSize = 17.sp,
                        lineHeight = 29.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        item {
            Text(
                "نیت، امری قلبی است؛ این بخش برای آسان‌تر شدن قرائت، ذکر و ثبت شمارش فراهم شده است.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AboutScreen() {
    val version = BuildConfig.VERSION_NAME
    SimpleScreen(
        "درباره ذکران",
        "ذکران، همراه معنوی روزانه با تمرکز بر آیت‌الکرسی، دعا، اذکار و ابزارهای کاربردی است.\n\nنسخه برنامه: $version\nتوسعه‌دهنده: مسعود درویش\n\nدر این نسخه، صفحه «ذکران امروز»، تقویم غنی‌تر، هدیه ثواب، چهار تم متنوع و خوانش حرفه‌ای‌تر آیت‌الکرسی در مرکز تجربه برنامه قرار گرفته‌اند.\n\nاین برنامه با عشق و احترام، به یاد پدر و مادرم که از دنیا رفته‌اند ساخته شده و با نیت خیر و هدیه ثواب به روح آنان در اختیار همه قرار می‌گیرد.\n\nذکران رایگان است و تبلیغ اجباری، خرید درون‌برنامه‌ای و بخش پولی ندارد."
    )
}

@Composable
private fun SimpleScreen(title: String, text: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { SacredHeader(title) }
        item {
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)) {
                Text(
                    text,
                    fontSize = 17.sp,
                    lineHeight = 29.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }
        }
    }
}
