package com.masouddarvish.noor.data

import java.time.LocalDate

data class PersianDate(val year:Int,val month:Int,val day:Int){
    val monthName:String get()=MONTHS[month-1]
    override fun toString()="$day $monthName $year"
    companion object{
        val MONTHS=listOf("فروردین","اردیبهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند")
        fun today()=fromGregorian(LocalDate.now())
        fun fromGregorian(date:LocalDate):PersianDate{
            val gy=date.year; val gm=date.monthValue; val gd=date.dayOfMonth
            val gdm=intArrayOf(0,31,59,90,120,151,181,212,243,273,304,334)
            var jy:Int; val gy2=if(gm>2) gy+1 else gy
            var days=355666 + 365*gy + (gy2+3)/4 - (gy2+99)/100 + (gy2+399)/400 + gd + gdm[gm-1]
            jy=-1595 + 33*(days/12053); days%=12053; jy += 4*(days/1461); days%=1461
            if(days>365){jy+=(days-1)/365;days=(days-1)%365}
            val jm:Int; val jd:Int
            if(days<186){jm=1+days/31;jd=1+days%31}else{jm=7+(days-186)/30;jd=1+(days-186)%30}
            return PersianDate(jy,jm,jd)
        }
        fun toGregorian(jy:Int,jm:Int,jd:Int):LocalDate{
            var jy0=jy+1595
            var days=-355668 + 365*jy0 + (jy0/33)*8 + ((jy0%33+3)/4) + jd + if(jm<7)(jm-1)*31 else (jm-7)*30+186
            var gy=400*(days/146097); days%=146097
            if(days>36524){gy+=100*(--days/36524);days%=36524;if(days>=365)days++}
            gy+=4*(days/1461);days%=1461
            if(days>365){gy+=(days-1)/365;days=(days-1)%365}
            var gd=days+1
            val leap=gy%4==0 && gy%100!=0 || gy%400==0
            val sal=intArrayOf(0,31,if(leap)29 else 28,31,30,31,30,31,31,30,31,30,31)
            var gm=1
            while(gm<=12 && gd>sal[gm]){gd-=sal[gm];gm++}
            return LocalDate.of(gy,gm,gd)
        }
        fun isLeap(year:Int):Boolean { val a=year-474; val b=474+(a%2820+2820)%2820; return ((b+38)*682)%2816<682 }
        fun monthLength(year:Int,month:Int)=when{month<=6->31;month<=11->30;isLeap(year)->30;else->29}
        fun shiftMonth(year:Int,month:Int,delta:Int):Pair<Int,Int>{val z=year*12+(month-1)+delta;return Pair(Math.floorDiv(z,12),Math.floorMod(z,12)+1)}
    }
}
