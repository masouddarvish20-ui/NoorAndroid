package com.masouddarvish.noor.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masouddarvish.noor.ui.components.SacredHeader
import com.masouddarvish.noor.ui.theme.Gold
import java.time.LocalDate
import java.util.TimeZone
import kotlin.math.*

data class PrayerCity(val name: String, val lat: Double, val lon: Double, val tz: String)

private val prayerCities = listOf(
    PrayerCity("تهران", 35.6892, 51.3890, "Asia/Tehran"),
    PrayerCity("مشهد", 36.2605, 59.6168, "Asia/Tehran"),
    PrayerCity("اصفهان", 32.6546, 51.6680, "Asia/Tehran"),
    PrayerCity("شیراز", 29.5918, 52.5837, "Asia/Tehran"),
    PrayerCity("تبریز", 38.0800, 46.2919, "Asia/Tehran"),
    PrayerCity("قم", 34.6416, 50.8746, "Asia/Tehran"),
    PrayerCity("اهواز", 31.3183, 48.6706, "Asia/Tehran"),
    PrayerCity("رشت", 37.2808, 49.5832, "Asia/Tehran"),
    PrayerCity("کرمان", 30.2839, 57.0834, "Asia/Tehran"),
    PrayerCity("کرج", 35.8400, 50.9391, "Asia/Tehran")
)

private fun fixHour(value: Double): Double = ((value % 24.0) + 24.0) % 24.0
private fun dtr(value: Double): Double = Math.toRadians(value)
private fun rtd(value: Double): Double = Math.toDegrees(value)

private fun sunPosition(jd: Double): Pair<Double, Double> {
    val days = jd - 2451545.0
    val g = fixHour((357.529 + 0.98560028 * days) / 15.0) * 15.0
    val q = fixHour((280.459 + 0.98564736 * days) / 15.0) * 15.0
    val longitude = (q + 1.915 * sin(dtr(g)) + 0.020 * sin(dtr(2.0 * g))) % 360.0
    val obliquity = 23.439 - 0.00000036 * days
    val rightAscension = fixHour(
        rtd(atan2(cos(dtr(obliquity)) * sin(dtr(longitude)), cos(dtr(longitude)))) / 15.0
    )
    val declination = rtd(asin(sin(dtr(obliquity)) * sin(dtr(longitude))))
    val equation = q / 15.0 - rightAscension
    return declination to equation
}

private fun julian(year: Int, monthValue: Int, day: Int): Double {
    var y = year
    var month = monthValue
    if (month <= 2) {
        y--
        month += 12
    }
    val a = floor(y / 100.0)
    val b = 2.0 - a + floor(a / 4.0)
    return floor(365.25 * (y + 4716)) + floor(30.6001 * (month + 1)) + day + b - 1524.5
}

private fun midDay(jd: Double, time: Double): Double =
    fixHour(12.0 - sunPosition(jd + time / 24.0).second)

private fun timeForAngle(
    jd: Double,
    latitude: Double,
    angle: Double,
    time: Double,
    beforeNoon: Boolean
): Double {
    val declination = sunPosition(jd + time / 24.0).first
    val noon = midDay(jd, time)
    val x = (-sin(dtr(angle)) - sin(dtr(declination)) * sin(dtr(latitude))) /
        (cos(dtr(declination)) * cos(dtr(latitude)))
    val delta = rtd(acos(x.coerceIn(-1.0, 1.0))) / 15.0
    return noon + if (beforeNoon) -delta else delta
}

private fun asrTime(jd: Double, latitude: Double, time: Double): Double {
    val declination = sunPosition(jd + time / 24.0).first
    val angle = -rtd(atan(1.0 / (1.0 + tan(abs(dtr(latitude - declination))))))
    return timeForAngle(jd, latitude, -angle, time, false)
}

private fun formatTime(value: Double): String {
    val adjusted = fixHour(value + 0.5 / 60.0)
    val hour = floor(adjusted).toInt()
    val minute = floor((adjusted - hour) * 60.0).toInt()
    return "%02d:%02d".format(hour, minute)
}

private fun prayerTimes(city: PrayerCity): List<Pair<String, String>> {
    val date = LocalDate.now()
    val jd = julian(date.year, date.monthValue, date.dayOfMonth) - city.lon / (15.0 * 24.0)
    val timezoneHours = TimeZone.getTimeZone(city.tz)
        .getOffset(System.currentTimeMillis()) / 3_600_000.0

    fun adjust(value: Double): Double = value + timezoneHours - city.lon / 15.0

    val fajr = adjust(timeForAngle(jd, city.lat, 17.7, 5.0, true))
    val sunrise = adjust(timeForAngle(jd, city.lat, 0.833, 6.0, true))
    val noon = adjust(midDay(jd, 12.0))
    val asr = adjust(asrTime(jd, city.lat, 13.0))
    val sunset = adjust(timeForAngle(jd, city.lat, 0.833, 18.0, false))
    val maghrib = adjust(timeForAngle(jd, city.lat, 4.5, 18.0, false))
    val isha = adjust(timeForAngle(jd, city.lat, 14.0, 18.0, false))

    return listOf(
        "اذان صبح" to formatTime(fajr),
        "طلوع آفتاب" to formatTime(sunrise),
        "اذان ظهر" to formatTime(noon),
        "عصر" to formatTime(asr),
        "غروب آفتاب" to formatTime(sunset),
        "اذان مغرب" to formatTime(maghrib),
        "عشا" to formatTime(isha)
    )
}

private fun qiblaBearing(latitude: Double, longitude: Double): Double {
    val kaabaLatitude = dtr(21.4225)
    val longitudeDelta = dtr(39.8262 - longitude)
    val y = sin(longitudeDelta)
    val x = cos(dtr(latitude)) * tan(kaabaLatitude) -
        sin(dtr(latitude)) * cos(longitudeDelta)
    return (rtd(atan2(y, x)) + 360.0) % 360.0
}

@Composable
fun PrayerTimesScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("noor", 0) }
    var cityIndex by remember {
        mutableIntStateOf(prefs.getInt("prayer_city", 0).coerceIn(prayerCities.indices))
    }
    var menuOpen by remember { mutableStateOf(false) }
    val city = prayerCities[cityIndex]
    val today = LocalDate.now()
    val times = remember(cityIndex, today) { prayerTimes(city) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SacredHeader("اوقات شرعی", "محاسبه روزانه بر اساس شهر انتخابی") }
        item {
            Box {
                OutlinedButton(
                    onClick = { menuOpen = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("شهر: ${city.name}", fontWeight = FontWeight.Bold)
                }
                DropdownMenu(
                    expanded = menuOpen,
                    onDismissRequest = { menuOpen = false }
                ) {
                    prayerCities.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = { Text(item.name) },
                            onClick = {
                                cityIndex = index
                                prefs.edit().putInt("prayer_city", index).apply()
                                menuOpen = false
                            }
                        )
                    }
                }
            }
        }
        times.forEach { (name, time) ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0A3F36)),
                    border = BorderStroke(1.dp, Gold.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(name, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(time, color = Gold, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
        item {
            Text(
                "زمان‌ها محاسباتی‌اند و ممکن است با تقویم رسمی محل چند دقیقه اختلاف داشته باشند.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun QiblaScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("noor", 0) }
    var cityIndex by remember {
        mutableIntStateOf(prefs.getInt("prayer_city", 0).coerceIn(prayerCities.indices))
    }
    var menuOpen by remember { mutableStateOf(false) }
    val city = prayerCities[cityIndex]
    val bearing = qiblaBearing(city.lat, city.lon)
    var azimuth by remember { mutableFloatStateOf(0f) }
    var sensorAvailable by remember { mutableStateOf(true) }

    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        sensorAvailable = rotationSensor != null
        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
            override fun onSensorChanged(event: SensorEvent) {
                val rotationMatrix = FloatArray(9)
                val orientation = FloatArray(3)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                SensorManager.getOrientation(rotationMatrix, orientation)
                azimuth = ((Math.toDegrees(orientation[0].toDouble()).toFloat() + 360f) % 360f)
            }
        }
        if (rotationSensor != null) {
            sensorManager.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        }
        onDispose { sensorManager.unregisterListener(listener) }
    }

    val rotation = (bearing.toFloat() - azimuth + 360f) % 360f

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { SacredHeader("قبله‌نما", "جهت کعبه بر اساس شهر و قطب‌نمای گوشی") }
        item {
            Box {
                OutlinedButton(
                    onClick = { menuOpen = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("شهر: ${city.name}", fontWeight = FontWeight.Bold)
                }
                DropdownMenu(
                    expanded = menuOpen,
                    onDismissRequest = { menuOpen = false }
                ) {
                    prayerCities.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = { Text(item.name) },
                            onClick = {
                                cityIndex = index
                                prefs.edit().putInt("prayer_city", index).apply()
                                menuOpen = false
                            }
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0A3F36)),
                border = BorderStroke(1.5.dp, Gold)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("جهت قبله", color = Gold, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    Spacer(Modifier.height(28.dp))
                    Icon(
                        imageVector = Icons.Filled.Navigation,
                        contentDescription = "قبله",
                        modifier = Modifier.size(120.dp).rotate(rotation),
                        tint = Gold
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "${bearing.roundToInt()}° از شمال",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if (sensorAvailable) "گوشی را صاف نگه دارید و آرام بچرخانید."
                        else "حسگر قطب‌نما در این دستگاه در دسترس نیست.",
                        color = Color(0xFFE8DDB8),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        item {
            Text(
                "برای دقت بهتر، قطب‌نمای گوشی را کالیبره کنید و از اجسام فلزی فاصله بگیرید.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
