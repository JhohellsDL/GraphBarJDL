package com.example.myapplicationcanva

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationcanva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val ancho: Int = 280 // ancho en dp segun el dieño
    private val alto: Int = 314 // altura en dp segun el diseño

    private val ancho2: Int = 305 // ancho en dp segun el dieño line of references
    private val alto2: Int = 282 // altura en dp segun el diseño

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pruebaCanva()
    }

    private fun dpToFloat(dp: Int): Float {
        return dp * 2.75f
    }

    private fun dpFloatToFloat(dp: Float): Float {
        return dp * 2.75f
    }

    private fun dpToInt(dp: Int): Int {
        return (dp * 2.75).toInt()
    }

    // Config for painting default lines black
    private fun typePaint(
        color: Int = Color.BLACK,
        thickness: Float = 1f
    ): Paint {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = thickness
        paint.color = color
        paint.isAntiAlias = true

        return paint
    }

    private fun typePaintText(
        size: Int = 10,
        color: Int = Color.BLACK
    ): Paint {
        val paint: Paint = Paint()
        paint.color = color
        paint.textSize = dpToFloat(size)
        paint.isAntiAlias = true
        return paint
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------------------- DRAWING BAR
    private fun drawBarMonth(
        consumed: String,
        heightBar: Int,
        month: String,
        locationBar: Int,
        mybitmap: Bitmap,
        paintBar: Paint,
        paintTextConsumed: Paint,
        paintTextMonth: Paint
    ) {
        val canva = Canvas(mybitmap)

        val frameHeight = dpToFloat(alto2) // take the frame height

        val widthBar = locationBar + 10 // 10 because the stroke is 10
        val startLocation = dpToFloat(locationBar)
        val endLocation = dpToFloat(locationBar + 10)

        val top = frameHeight + 10 // coordinate where start the bar - base
        val bottom = frameHeight - dpToFloat(heightBar) // frame height minus the bar size or consumption

        val startTextX = bottom - dpToFloat(12)
        val startTextY = startLocation - dpToFloat(11) // eje Y

        val startTextMonthX = startTextY + dpToFloat(4)
        val startTextMonthY = top + dpToFloat(20)

        // the rectangle is upside down
        canva.drawRoundRect(
            startLocation,
            top,
            endLocation,
            bottom,
            27.5f,
            27.5f,
            paintBar
        )
        canva.drawText( // Text description of consumption
            "$consumed km",
            startTextY,
            startTextX,
            paintTextConsumed
        )
        canva.drawText(  // Text Month of consumption
            month,
            startTextMonthX,
            startTextMonthY,
            paintTextMonth
        )
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------- DRAWING CHOPPY LINE
    private fun drawChoppyHorizontalLine(posYDrawLine: Float, myBitmap: Bitmap, paint: Paint) {
        val canvas = Canvas(myBitmap)
        val posLineY = dpFloatToFloat(posYDrawLine) // start of floor everyone
        var aux: Float
        for (a in 0..83) {
            if (a % 2 == 0) {
                aux = a * 10f // width mini line and space (10f)
                canvas.drawLine(aux, posLineY, (aux + 10), posLineY, paint)
            }
        }
    }

    private fun drawChoppyVerticalLine(posXDrawLine: Float, myBitmap: Bitmap, paint: Paint) {
        val canvas = Canvas(myBitmap)
        val posLineX = dpFloatToFloat(posXDrawLine) // start of floor everyone
        var aux: Float
        for (a in 0..83) {
            if (a % 2 == 0) {
                aux = a * 10f // width mini line and space (10f)
                canvas.drawLine(posLineX, aux, posLineX, (aux + 10), paint)
            }
        }

    }

    private fun fetchMonthConsumed(consumed: ConsumoData): String {
        val nroMes: Int = consumed.periodo.split("-")[1].toInt()
        val month = when (nroMes) {
            1 -> "Ene"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Abr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Ago"
            9 -> "Set"
            10 -> "Oct"
            11 -> "Nov"
            else -> "Dic"
        }
        return month
    }

    fun pruebaCanva() {

        val listsConsumeData: List<ConsumoData> = listOf(
            ConsumoData(
                periodo = "2022-05",
                consumo = 500
            ),
            ConsumoData(
                periodo = "2022-06",
                consumo = 1000
            ),
            ConsumoData(
                periodo = "2022-07",
                consumo = 200
            ),
            ConsumoData(
                periodo = "2022-08",
                consumo = 100
            ),
            ConsumoData(
                periodo = "2022-09",
                consumo = 600
            ),
            ConsumoData(
                periodo = "2022-10",
                consumo = 91
            ),
            ConsumoData(
                periodo = "2022-11",
                consumo = 240
            ),

        )

        val cont = listsConsumeData.size
        val plusWidth = (cont-5)*52

        val myBitmap: Bitmap
        if (cont <= 5){
            myBitmap = Bitmap.createBitmap(
                dpToInt(ancho),
                dpToInt(alto),
                Bitmap.Config.ARGB_8888
            )
        }else{
            myBitmap = Bitmap.createBitmap(
                dpToInt(ancho+plusWidth),
                dpToInt(alto),
                Bitmap.Config.ARGB_8888
            )

            binding.scrollViewBar.setBackgroundColor(Color.GREEN)
            binding.scrollViewBar.scrollTo(0,0)
        }
        // Bitmap of graph bar

        // Bitmap of background choppy line
        val myBitmap2: Bitmap =
            Bitmap.createBitmap(
                dpToInt(ancho2),
                dpToInt(alto2),
                Bitmap.Config.ARGB_8888
            )
        val canvas = Canvas(myBitmap)
        val canvas2 = Canvas(myBitmap2)
        
        canvas.drawColor(Color.GRAY)
        canvas2.drawColor(Color.TRANSPARENT)
        
        val paintTextConsumed = typePaintText(10, Color.BLACK)
        val paintTextMonth = typePaintText(14, Color.BLACK)
        val paintTextGuide = typePaintText(12, 0XFFD7DBF5.toInt())

        val paintBar = typePaint(0XFF6464FA.toInt(), dpToFloat(10)) // color purple ( 0XFF######) replace # with color
        val paintBaseLine = typePaint(0XFFD7DBF5.toInt(), 5.5f) // color gray
        val paintLineColor = typePaint(0XFFD7DBF5.toInt(), 2.75f) // color gray
        val paintCoverLine = typePaint(Color.WHITE, dpFloatToFloat(10.2f)) // color white and very thick

        // --------------------------------------------------------------------- DRAWING CHOPPY LINE---------------------
        val a = 56.4f // space between lines horizontals
        val startTextGuide = dpFloatToFloat(56.4f)
        drawChoppyHorizontalLine(a, myBitmap2, paintLineColor)
        drawChoppyHorizontalLine((2 * a), myBitmap2, paintLineColor)
        drawChoppyHorizontalLine((3 * a), myBitmap2, paintLineColor)
        drawChoppyHorizontalLine((4 * a), myBitmap2, paintLineColor)
        canvas2.drawText("1000",0f,startTextGuide-(dpFloatToFloat(8.4f)),paintTextGuide)
        canvas2.drawText("400",0f,startTextGuide*2-(dpFloatToFloat(8.4f)),paintTextGuide)
        canvas2.drawText("300",0f,startTextGuide*3-(dpFloatToFloat(8.4f)),paintTextGuide)
        canvas2.drawText("100",0f,startTextGuide*4-(dpFloatToFloat(8.4f)),paintTextGuide)

        drawChoppyVerticalLine(28f, myBitmap2, paintLineColor)

        // ----------------------------------------------------------------------------- DRAWING BAR ---------------------

        var startBars = 32 // is padding in dp, add 52dp for space

        val listConsumedInt: MutableList<Int> = mutableListOf()
        listsConsumeData.forEach(){
            listConsumedInt.add(it.consumo)
        }

        val min = listConsumedInt.min()
        val max = listConsumedInt.max()


        if (listConsumedInt.size <= 5){
            listsConsumeData.forEach() {
                val consumedInt = (it.consumo * 262)/max
                //val consumedInt = listsConsumeData[it].consumo

                val consumedString = it.consumo.toString()
                val month = fetchMonthConsumed(it)
                drawBarMonth(
                    consumedString,
                    consumedInt,
                    month,
                    startBars,
                    myBitmap,
                    paintBar,
                    paintTextConsumed,
                    paintTextMonth
                )
                startBars += 52
            }
        }else{
            listsConsumeData.forEach() {
                val consumedInt = (it.consumo * 262)/max
                //val consumedInt = listsConsumeData[it].consumo

                val consumedString = it.consumo.toString()
                val month = fetchMonthConsumed(it)
                drawBarMonth(
                    consumedString,
                    consumedInt,
                    month,
                    startBars,
                    myBitmap,
                    paintBar,
                    paintTextConsumed,
                    paintTextMonth
                )
                startBars += 52
            /*
            repeat(5){
                cont --
                val consumedInt = (listsConsumeData[cont].consumo * 262)/max
                //val consumedInt = listsConsumeData[it].consumo

                val consumedString = listsConsumeData[cont].consumo.toString()
                val month = fetchMonthConsumed(listsConsumeData[cont])
                drawBarMonth(
                    consumedString,
                    consumedInt,
                    month,
                    startBars,
                    myBitmap,
                    paintBar,
                    paintTextConsumed,
                    paintTextMonth
                )
                startBars += 52
*/
            }
        }

        /*
        listsConsumeData.forEach() {
            val consumed = it.consumo.toString()
            val month = fetchMonthConsumed(it)
            drawBarMonth(
                consumed,
                it.consumo,
                month,
                startBars,
                myBitmap,
                paintBar,
                paintTextConsumed,
                paintTextMonth
            )
            startBars += 52
        }
        */

        val startY = dpToFloat(alto2 + 5)
        val endX = dpToFloat(ancho)
        val endY = dpToFloat(alto2 + 5)

        canvas.drawLine(0f, dpToFloat(alto2 - 1), dpToFloat(ancho), dpToFloat(alto2 - 1), paintBaseLine)
        canvas.drawLine(0f, startY, endX, endY, paintCoverLine)

        binding.imageView.setImageBitmap(myBitmap)
        binding.imageView2.setImageBitmap(myBitmap2)
    }
}