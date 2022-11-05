package com.example.myapplicationcanva

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.myapplicationcanva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val ancho: Int = 280 // ancho en dp segun el dieño
    private val alto: Int = 282 // altura en dp segun el diseño

    private val ancho2: Int = 305 // ancho en dp segun el dieño line of references
    private val alto2: Int = 282 // altura en dp segun el diseño

    private val colorFondo = Color.GRAY
    private var anchox: Int = 300


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val card: CardView = findViewById(R.id.cardview)
        anchox = card.width
        pruebaCanva()
    }

    private fun dpToFloat(dp: Int): Float{
        return dp*2.75f
    }
    private fun dpFloatToFloat(dp: Float): Float{
        return dp*2.75f
    }
    private fun dpToInt(dp: Int): Int{
        return (dp*2.75).toInt()
    }
    private fun tipoPintura(color: Int, grosor: Float): Paint{
        val paint: Paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = grosor
        paint.color = color
        paint.isAntiAlias = true
        return paint
    }
    private fun typePaintText(color: Int): Paint{
        val paint: Paint = Paint()
        paint.color = color
        paint.textSize = dpToFloat(10)
        paint.isAntiAlias = true
        return paint
    }

    // --------------------------------------------------------------------------------- DRAWING BAR
    private fun dibujarBarra(consumed: String, heightBar: Int, locationBar: Int, mybitmap: Bitmap, paintBar: Paint, paintText: Paint){
        val canva = Canvas(mybitmap)

        val width = 20  // ancho de la barra 20dp
        val height = 262 // altura maxima de la barra 262dp
        val frameHeight = dpToFloat(alto) // take the frame height

        val startLocation = dpToFloat(locationBar)
        val endLocation = dpToFloat(locationBar+10)
        val widthBar = locationBar+10 // 10 because the stroke is 10

        binding.text1.text = frameHeight.toString()

        val left = startLocation //
        val right = endLocation

        val top = frameHeight + 10 // coordinate where start the bar - base
        val bottom = frameHeight - dpToFloat(heightBar) // frame height minus the bar size or consumption
        val startTextX = bottom - dpToFloat(12)
        val startTextY = left-30

        // rec ( left, top, right, bottom , paint)
        canva.drawRoundRect(left,top,right,bottom,27.5f,27.5f,paintBar)
        canva.drawText("$consumed km",startTextY, startTextX, paintText)  // queda el inicio -30
    }


    private fun dibujarRectanguloRedondeado(mybitmap: Bitmap, paint: Paint){
        val canva = Canvas(mybitmap)
        val ancho1 = 20f
        val ancho2 = 320f

        canva.drawRoundRect(30f,10f,20f,320f,10f,10f,paint)
        //canva.drawText("hola",30,40,30f,30f,paint)
    }
    // ------------------------------------------------------------------------- DRAWING CHOPPY LINE
    private fun drawChoppyHorizontalLine(posYDrawLine: Float, mybitmap: Bitmap, paint: Paint){
        val canva = Canvas(mybitmap)
        val posLineY = dpFloatToFloat(posYDrawLine) // start of floor everyone
        var aux: Float
        for (a in 0..83){
            if (a%2 == 0){
                aux = a * 10f // width mini line and space (10f)
                canva.drawLine(aux, posLineY, (aux+10), posLineY, paint)
            }
        }
    }

    private fun drawChoppyVerticalLine(posXDrawLine: Float, mybitmap: Bitmap, paint: Paint){
        val canva = Canvas(mybitmap)
        val posLineX = dpFloatToFloat(posXDrawLine) // start of floor everyone
        var aux: Float
        for (a in 0..83){
            if (a%2 == 0){
                aux = a * 10f // width mini line and space (10f)
                canva.drawLine(posLineX, aux, posLineX, (aux+10), paint)
            }
        }

    }

    fun fetchMonthConsumed(consumed: ConsumoData): String{
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

    private val path = Path()

    fun onDraw(){

    }

    fun pruebaCanva(){

        //val lista: List<Int> = listOf(131, 262, 200, 100, 240)
        val listaConsumoData: List<ConsumoData> = listOf(
            ConsumoData(
                periodo = "2022-05",
                consumo = 131
            ),
            ConsumoData(
                periodo = "2022-06",
                consumo = 262
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
                consumo = 240
            ),
            /*
            ConsumoData(
                periodo = "2022-10",
                consumo = 91
            ),
            ConsumoData(
                periodo = "2022-11",
                consumo = 240
            ),

             */

        )

        val mybitmap: Bitmap = Bitmap.createBitmap(dpToInt(ancho),dpToInt(alto), Bitmap.Config.ARGB_8888)
        val mybitmap2: Bitmap = Bitmap.createBitmap(dpToInt(ancho2),dpToInt(alto2), Bitmap.Config.ARGB_8888)
        val canva = Canvas(mybitmap)
        val canva2 = Canvas(mybitmap2)
        canva.drawColor(Color.TRANSPARENT)
        canva2.drawColor(Color.TRANSPARENT)



        //val imagen: ImageView  = findViewById(R.id.image_view)
        anchox = canva.width
        print("ancho! : $anchox y $ancho")



        val paint0 = typePaintText(Color.BLACK)
        val paint1 = tipoPintura(0XFF6464FA.toInt(), dpToFloat(10))
        val paint2 = tipoPintura(0XFFD7DBF5.toInt(),5.5f)
        val paintLineColor = tipoPintura(0XFFD7DBF5.toInt(),2.75f)
        val paintLineTransparent = tipoPintura(Color.GREEN,2.75f)
        //0xFF00FF00 -16722681  #D7DBF5


        //val rect: Rect = Rect(10,10,30,200)
        canva.drawText("asdf",44f,20f,paint0)
        //canva.drawText("asdf",187f,390f,paint0)

        // --------------------------------------------------------------------- DRAWING CHOPPY LINE
        val a = 56.4f // space between lines horizontals
        drawChoppyHorizontalLine(a, mybitmap2, paintLineColor)
        drawChoppyHorizontalLine((2*a), mybitmap2, paintLineColor)
        drawChoppyHorizontalLine((3*a), mybitmap2, paintLineColor)
        drawChoppyHorizontalLine((4*a), mybitmap2, paintLineColor)

        drawChoppyVerticalLine(28f, mybitmap2, paintLineColor)

        val lista: List<Int> = listOf(131, 262, 200, 100, 240)
        var startBars = 32 // is padding in dp, add 52dp for space

        // ----------------------------------------------------------------------------- DRAWING BAR

        listaConsumoData.forEach(){
            var consumed = it.consumo.toString()
            dibujarBarra(consumed, it.consumo, startBars, mybitmap, paint1, paint0)
            startBars += 52
        }
        canva.drawLine(0f,dpToFloat(alto-1),dpToFloat(ancho),dpToFloat(alto-1),paint2)
/*
        dibujarBarra(262,21,mybitmap, paint1)
        dibujarBarra(131,73,mybitmap, paint1)
        dibujarBarra(131,125,mybitmap, paint1)
        dibujarBarra(131,177,mybitmap, paint1)
*/
        //dibujarRectanguloRedondeado(mybitmap,paint0)
        //dibujarRectanguloRedondeado(mybitmap,paint1)
        //canva.drawText("hola",50,90,20f,20f,paint0)
        //canva.drawRoundRect(10f,10f,30f,200f,5f,5f,paint0)
        //canva.drawRoundRect(20f,15f,35f,210f,5f,5f,paint1)
        //imagen.setImageBitmap(mybitmap)
        binding.imageView.setImageBitmap(mybitmap)
        binding.imageView2.setImageBitmap(mybitmap2)
    }
}