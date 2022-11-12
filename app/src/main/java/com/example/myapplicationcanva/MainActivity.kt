package com.example.myapplicationcanva

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.myapplicationcanva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pruebaCanva()
    }


    // --------------------------------------------------------------------------- DRAWING BAR
    private val ancho: Int = 280 // ancho en dp segun el dieño
    private val alto: Int = 314 // altura en dp segun el diseño

    private val ancho2: Int = 305 // ancho en dp segun el dieño line of references
    private val alto2: Int = 282 // altura en dp segun el diseño

    private val listsConsumeData: List<ConsumoData> = listOf(
        ConsumoData(
            periodo = "2022-07",
            consumo = 0f
        ),
        ConsumoData(
            periodo = "2022-08",
            consumo = 26f
        ),
        ConsumoData(
            periodo = "2022-09",
            consumo = 61f
        )
    )

    private val myBitmap: Bitmap = Bitmap.createBitmap(
        dpToInt(ancho),
        dpToInt(alto),
        Bitmap.Config.ARGB_8888,
    )

    private val canvasBar = Canvas(myBitmap)

    fun pruebaCanva() {
        val listConsumedValor: MutableList<Float> = mutableListOf()
        listsConsumeData.forEach() {
            listConsumedValor.add(it.consumo)
        }

        var startBars = 32 // is padding in dp, add 52dp for space

        val min = listConsumedValor.min()
        val max = listConsumedValor.max().toInt()
        val sizeMax = listConsumedValor.size

        // Bitmap of background choppy line
        val myBitmap2: Bitmap = Bitmap.createBitmap(
            dpToInt(ancho2),
            dpToInt(alto2),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(myBitmap)
        val canvas2 = Canvas(myBitmap2)

        canvas.drawColor(Color.TRANSPARENT)
        canvas2.drawColor(Color.WHITE)

        val paintTextConsumed = typePaintText(10, Color.BLACK)
        val paintTextMonth = typePaintText(14, Color.BLACK)
        val paintTextGuide = typePaintText(12, 0XFFD7DBF5.toInt())

        val paintBar = typePaint(
            0XFF6464FA.toInt(),
            dpToFloat(10)
        ) // color purple ( 0XFF######) replace # with color
        val paintBaseLine = typePaint(0XFFD7DBF5.toInt(), 5.5f) // color gray
        val paintLineColor = typePaint(0XFFD7DBF5.toInt(), 2.75f) // color gray
        val paintCoverLine =
            typePaint(Color.WHITE, dpFloatToFloat(10.2f)) // color white and very thick

        // --------------------------------------------------------------------- DRAWING CHOPPY LINE---------------------
        var aux = (max / 10) + 1
        val aux2 = (max) / 5
        binding.text1.text = aux.toString()
        binding.text2.text = aux2.toString()


        val a = 56.4f // space between lines horizontals
        val startTextGuide = dpFloatToFloat(56.4f)
        drawChoppyHorizontalLine(a, myBitmap2, paintLineColor)
        drawChoppyHorizontalLine((2 * a), myBitmap2, paintLineColor)
        drawChoppyHorizontalLine((3 * a), myBitmap2, paintLineColor)
        drawChoppyHorizontalLine((4 * a), myBitmap2, paintLineColor)
        canvas2.drawText(
            "${(aux2 * 4) + 2}",
            0f,
            startTextGuide - (dpFloatToFloat(8.4f)),
            paintTextGuide
        )
        canvas2.drawText(
            "${(aux2 * 3) + 2}",
            0f,
            startTextGuide * 2 - (dpFloatToFloat(8.4f)),
            paintTextGuide
        )
        canvas2.drawText(
            "${(aux2 * 2) + 2}",
            0f,
            startTextGuide * 3 - (dpFloatToFloat(8.4f)),
            paintTextGuide
        )
        canvas2.drawText(
            "${(aux2 * 1) + 2}",
            0f,
            startTextGuide * 4 - (dpFloatToFloat(8.4f)),
            paintTextGuide
        )

        drawChoppyVerticalLine(myBitmap2, paintLineColor)

        // ----------------------------------------------------------------------------- DRAWING BAR ---------------------

        val startY = dpToFloat(alto2 + 5)
        val endX = dpToFloat(ancho)
        val endY = dpToFloat(alto2 + 5)
        var startMonth = (listsConsumeData.size - 5)

        if (listConsumedValor.size <= 5) {
            binding.buttonMonthBack.isVisible = false
            listsConsumeData.forEach() {
                val consumedInt = ((it.consumo * 262) / max).toInt()
                //val consumedInt = listsConsumeData[it].consumo

                val consumedString = it.consumo.toString()
                val month = fetchMonthConsumed(it)
                drawBarMonth(
                    consumedString,
                    consumedInt,
                    month,
                    startBars,
                    paintBar,
                    paintTextConsumed,
                    paintTextMonth
                )
                startBars += 52
            }
        } else {
            drawRangeBars(
                startMonth,
                (startMonth + 4),
                listsConsumeData,
                max,
                startBars,
                paintBar,
                paintTextConsumed,
                paintTextMonth
            )
        }

        binding.buttonMonthBack.setOnClickListener {
            startMonth -= 1
            if (startMonth == 0) {
                binding.text2.text = "Fin!!!"
                binding.buttonMonthBack.isVisible = false
            }
            binding.text1.text = "startMonth = $startMonth"

            myBitmap.eraseColor(Color.TRANSPARENT)

            drawRangeBars(
                startMonth,
                (startMonth + 4),
                listsConsumeData,
                max,
                startBars,
                paintBar,
                paintTextConsumed,
                paintTextMonth
            )
            canvas.drawLine(
                0f,
                dpToFloat(alto2 - 1),
                dpToFloat(ancho),
                dpToFloat(alto2 - 1),
                paintBaseLine
            )
            canvas.drawLine(0f, startY, endX, endY, paintCoverLine)

        }

        canvas.drawLine(
            0f,
            dpToFloat(alto2 - 1),
            dpToFloat(ancho),
            dpToFloat(alto2 - 1),
            paintBaseLine
        )
        canvas.drawLine(0f, startY, endX, endY, paintCoverLine)

        binding.imageView.setImageBitmap(myBitmap)

        binding.imageView2.setImageBitmap(myBitmap2)
    }

    private fun drawBarMonth(
        consumed: String,
        heightBar: Int,
        month: String,
        locationBar: Int,
        paintBar: Paint,
        paintTextConsumed: Paint,
        paintTextMonth: Paint
    ) {
        val frameHeight = dpToFloat(alto2) // take the frame height

        val startLocation = dpToFloat(locationBar)
        val endLocation = dpToFloat(locationBar + 10)
        val top = frameHeight + 10 // coordinate where start the bar - base
        val bottom =
            frameHeight - dpToFloat(heightBar) // frame height minu s the bar size or consumption

        val startTextX = bottom - dpToFloat(12)
        val startTextY = startLocation - dpToFloat(11) // eje Y

        val startTextMonthX = startTextY + dpToFloat(4)
        val startTextMonthY = top + dpToFloat(20)

        // the rectangle is upside down
        canvasBar.drawRoundRect(
            startLocation,
            top,
            endLocation,
            bottom,
            27.5f,
            27.5f,
            paintBar
        )
        canvasBar.drawText( // Text description of consumption
            "$consumed km",
            startTextY,
            startTextX,
            paintTextConsumed
        )
        canvasBar.drawText(  // Text Month of consumption
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

    private fun drawChoppyVerticalLine(myBitmap: Bitmap, paint: Paint) {
        val canvas = Canvas(myBitmap)
        val posLineX = dpFloatToFloat(28f) // start of floor everyone
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

    // poniendo rangos a las barras para que se muestren por qu
    // se muestran nomal y funciona ok pero no las barra que quiero falta ponder los rangos para que se muestren lo que quiero
    private fun drawRangeBars(
        startMonth: Int,
        endMonth: Int,
        consumeData: List<ConsumoData>,
        max: Int,
        startBars: Int,
        paintBar: Paint,
        paintTextConsumed: Paint,
        paintTextMonth: Paint
    ) {
        var startBars1 = startBars
        for (x in startMonth..endMonth) {
            val consumedInt = ((consumeData[x].consumo * 262) / max).toInt()
            //val consumedInt = listsConsumeData[it].consumo
            val consumedString = consumeData[x].consumo.toString()
            val month = fetchMonthConsumed(consumeData[x])
            drawBarMonth(
                consumedString,
                consumedInt,
                month,
                startBars1,
                paintBar,
                paintTextConsumed,
                paintTextMonth
            )
            startBars1 += 52
        }
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
}