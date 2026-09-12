package com.masouddarvish.noor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masouddarvish.noor.R
import com.masouddarvish.noor.ui.theme.Gold

@Composable fun BrandHero(){
 Card(Modifier.fillMaxWidth().shadow(8.dp,RoundedCornerShape(30.dp)),shape=RoundedCornerShape(30.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.primary)){
  Column(Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.surfaceVariant))).padding(vertical=22.dp,horizontal=18.dp),horizontalAlignment=Alignment.CenterHorizontally){
   Surface(shape=RoundedCornerShape(28.dp),color=MaterialTheme.colorScheme.surface.copy(alpha=.14f),border=androidx.compose.foundation.BorderStroke(1.dp,MaterialTheme.colorScheme.secondary.copy(alpha=.55f))){
    Image(painterResource(R.drawable.ic_noor),"نشان ذکران",Modifier.size(126.dp).padding(6.dp),contentScale=ContentScale.Fit)
   }
   Spacer(Modifier.height(8.dp)); Text("ذکران",fontSize=36.sp,fontWeight=FontWeight.Black,color=MaterialTheme.colorScheme.secondary); Text("همراه معنوی هر روز",color=MaterialTheme.colorScheme.onPrimary.copy(alpha=.92f),fontSize=15.sp)
  }
 }
}
@Composable fun SacredHeader(title:String, subtitle:String="بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"){
 Card(Modifier.fillMaxWidth().shadow(6.dp,RoundedCornerShape(26.dp)),shape=RoundedCornerShape(topStart=42.dp,topEnd=42.dp,bottomStart=22.dp,bottomEnd=22.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.primary)){
  Box(Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.surfaceVariant,MaterialTheme.colorScheme.primary)))){
   Text("۞",Modifier.align(Alignment.TopStart).padding(12.dp).alpha(.28f),fontSize=42.sp,color=MaterialTheme.colorScheme.secondary); Text("۞",Modifier.align(Alignment.TopEnd).padding(12.dp).alpha(.28f),fontSize=42.sp,color=MaterialTheme.colorScheme.secondary)
   Column(Modifier.fillMaxWidth().padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){Text(title,fontSize=26.sp,fontWeight=FontWeight.Black,color=MaterialTheme.colorScheme.secondary,textAlign=TextAlign.Center);Spacer(Modifier.height(5.dp));Text(subtitle,color=Color.White,fontSize=14.sp,textAlign=TextAlign.Center)}
  }
 }
}
@Composable fun FeatureCard(title:String,subtitle:String,onClick:()->Unit){Card(Modifier.fillMaxWidth().clickable(onClick=onClick).border(1.dp,MaterialTheme.colorScheme.secondary.copy(alpha=.28f),RoundedCornerShape(22.dp)),shape=RoundedCornerShape(22.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)){Column(Modifier.fillMaxWidth().padding(18.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("✦",color=MaterialTheme.colorScheme.secondary,fontSize=24.sp);Text(title,fontWeight=FontWeight.Bold,fontSize=19.sp,color=MaterialTheme.colorScheme.onSurface,textAlign=TextAlign.Center);Spacer(Modifier.height(5.dp));Text(subtitle,style=MaterialTheme.typography.bodyMedium,color=MaterialTheme.colorScheme.onSurfaceVariant,textAlign=TextAlign.Center)}}}
@Composable fun ReadingCard(title:String,text:String,fontSize:Int=22,translation:String=""){
 Card(Modifier.fillMaxWidth().border(1.dp,MaterialTheme.colorScheme.secondary.copy(alpha=.32f),RoundedCornerShape(32.dp)),shape=RoundedCornerShape(32.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)){Column(Modifier.padding(22.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("۞  $title  ۞",color=MaterialTheme.colorScheme.secondary,fontWeight=FontWeight.Bold,fontSize=19.sp,modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center);HorizontalDivider(Modifier.padding(vertical=12.dp),color=MaterialTheme.colorScheme.secondary.copy(alpha=.35f));Text(text,modifier=Modifier.fillMaxWidth(),fontSize=fontSize.sp,lineHeight=(fontSize+18).sp,textAlign=TextAlign.Center,color=MaterialTheme.colorScheme.onSurface);if(translation.isNotBlank()){HorizontalDivider(Modifier.padding(vertical=16.dp),color=MaterialTheme.colorScheme.secondary.copy(alpha=.25f));Text("ترجمه فارسی",fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.secondary,textAlign=TextAlign.Center);Spacer(Modifier.height(7.dp));Text(translation,modifier=Modifier.fillMaxWidth(),fontSize=17.sp,lineHeight=30.sp,textAlign=TextAlign.Center,color=MaterialTheme.colorScheme.onSurfaceVariant)}}}
}
@Composable fun AyatHomeCard(onClick:()->Unit){
 Card(Modifier.fillMaxWidth().clickable(onClick=onClick).shadow(8.dp,RoundedCornerShape(30.dp)),shape=RoundedCornerShape(30.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.primary)){
  Box(Modifier.fillMaxWidth().height(270.dp).background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.surfaceVariant)))){Text("۞",Modifier.align(Alignment.Center).alpha(.12f),fontSize=170.sp,color=MaterialTheme.colorScheme.secondary);Column(Modifier.fillMaxSize().padding(22.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){Text("آیت‌الکرسی",fontSize=30.sp,fontWeight=FontWeight.Black,color=MaterialTheme.colorScheme.secondary);Spacer(Modifier.height(12.dp));Text("اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",fontSize=23.sp,lineHeight=38.sp,fontWeight=FontWeight.Bold,textAlign=TextAlign.Center,color=Color.White);Spacer(Modifier.height(10.dp));Text("خداست که هیچ معبودی جز او نیست؛ زنده و برپادارنده است.",fontSize=15.sp,lineHeight=25.sp,textAlign=TextAlign.Center,color=Color.White.copy(alpha=.88f));Spacer(Modifier.height(15.dp));Button(onClick=onClick,colors=ButtonDefaults.buttonColors(containerColor=MaterialTheme.colorScheme.secondary,contentColor=MaterialTheme.colorScheme.onSecondary)){Text("خواندن آیت‌الکرسی",fontWeight=FontWeight.Bold)}}}
 }
}
@Composable fun FavoriteButton(selected:Boolean,onClick:()->Unit){IconButton(onClick){Icon(if(selected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,if(selected)"حذف از علاقه‌مندی" else "افزودن به علاقه‌مندی")}}

private object PersistentAudioPlayer {
    var player: android.media.MediaPlayer? = null
    var currentUrl: String? = null
    var playing by androidx.compose.runtime.mutableStateOf(false)
    var loading by androidx.compose.runtime.mutableStateOf(false)
    var durationMs by androidx.compose.runtime.mutableIntStateOf(0)
    var positionMs by androidx.compose.runtime.mutableIntStateOf(0)
    var repeat by androidx.compose.runtime.mutableStateOf(false)
    var speed by androidx.compose.runtime.mutableFloatStateOf(1f)

    fun toggle(url: String) {
        val active = player
        if (active != null && currentUrl == url) {
            if (loading) return
            if (playing) { active.pause(); playing = false } else { active.start(); playing = true }
            return
        }
        player?.release(); player=null; currentUrl=url; loading=true; playing=false; positionMs=0; durationMs=0
        runCatching {
            android.media.MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener { mp ->
                    loading=false; durationMs=mp.duration.coerceAtLeast(0)
                    if (android.os.Build.VERSION.SDK_INT >= 23) runCatching { mp.playbackParams = mp.playbackParams.setSpeed(speed) }
                    mp.start(); playing=true
                }
                setOnCompletionListener { mp -> if(repeat){mp.seekTo(0);mp.start();playing=true}else{playing=false;positionMs=0;mp.seekTo(0)} }
                setOnErrorListener { mp,_,_-> loading=false;playing=false;mp.release();player=null;true }
                prepareAsync(); player=this
            }
        }.onFailure { loading=false;playing=false;player=null }
    }
    fun seek(ms:Int){ runCatching { player?.seekTo(ms) }; positionMs=ms }
    fun toggleRepeat(){ repeat=!repeat }
    fun cycleSpeed(){ speed=when(speed){1f->1.25f;1.25f->1.5f;else->1f}; if(android.os.Build.VERSION.SDK_INT>=23) runCatching{player?.let{it.playbackParams=it.playbackParams.setSpeed(speed)}} }
}

private fun timeLabel(ms:Int):String { val total=(ms/1000).coerceAtLeast(0); return "%d:%02d".format(total/60,total%60) }

@Composable fun AudioButton(url:String){
    val playing = PersistentAudioPlayer.playing && PersistentAudioPlayer.currentUrl == url
    val loading = PersistentAudioPlayer.loading && PersistentAudioPlayer.currentUrl == url
    val active = PersistentAudioPlayer.currentUrl == url
    androidx.compose.runtime.LaunchedEffect(active,playing){
        while(active){ if(playing) PersistentAudioPlayer.positionMs=runCatching{PersistentAudioPlayer.player?.currentPosition?:0}.getOrDefault(0); kotlinx.coroutines.delay(500) }
    }
    Card(Modifier.fillMaxWidth(),shape=RoundedCornerShape(24.dp),border=androidx.compose.foundation.BorderStroke(1.dp,MaterialTheme.colorScheme.secondary.copy(alpha=.45f)),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)){
        Column(Modifier.fillMaxWidth().padding(14.dp),horizontalAlignment=Alignment.CenterHorizontally){
            Text("تلاوت آیه ۲۵۵",fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.secondary)
            if(active && PersistentAudioPlayer.durationMs>0){
                Slider(value=PersistentAudioPlayer.positionMs.toFloat().coerceAtMost(PersistentAudioPlayer.durationMs.toFloat()),onValueChange={PersistentAudioPlayer.seek(it.toInt())},valueRange=0f..PersistentAudioPlayer.durationMs.toFloat(),modifier=Modifier.fillMaxWidth())
                Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text(timeLabel(PersistentAudioPlayer.positionMs),fontSize=12.sp);Text(timeLabel(PersistentAudioPlayer.durationMs),fontSize=12.sp)}
            }
            Button(onClick={PersistentAudioPlayer.toggle(url)},modifier=Modifier.fillMaxWidth().height(54.dp),shape=RoundedCornerShape(20.dp),colors=ButtonDefaults.buttonColors(containerColor=MaterialTheme.colorScheme.primary,contentColor=MaterialTheme.colorScheme.onPrimary)){
                Text(when { loading->"در حال آماده‌سازی…";playing->"❚❚ مکث";active->"▶ ادامه تلاوت";else->"▶ پخش تلاوت" },fontWeight=FontWeight.Bold)
            }
            if(active){Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly){TextButton({PersistentAudioPlayer.toggleRepeat()}){Text(if(PersistentAudioPlayer.repeat)"تکرار: روشن" else "تکرار: خاموش")};TextButton({PersistentAudioPlayer.cycleSpeed()}){Text("سرعت ${PersistentAudioPlayer.speed}×")}}}
        }
    }
}
