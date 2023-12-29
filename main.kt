import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.random.Random
import kotlin.math.PI

// Inicialización del canvas
val canvas = initializeCanvas()

fun initializeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = window.innerWidth.toInt()
    context.canvas.height = window.innerHeight.toInt()
    document.body!!.appendChild(canvas)
    return canvas
}

// Clase ColorWaves
class ColorWaves {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height
    val width = canvas.width
    var radius = 10.0
    var hue = 0
    var textHue = 0.0
    var textX = 50.0
    var textDirection = 1

    fun wave() {
        context.save()

        context.beginPath()

        val x = width * Random.nextDouble()
        val y = height * Random.nextDouble()

        context.arc(x, y, radius, 0.0, 2 * PI)

        hue += (Random.nextDouble() * 10).toInt()
        context.fillStyle = "hsl($hue, 50%, 50%)"

        context.fill()

        context.restore()

        radius += 0.5
        if (radius > 100) {
            radius = 10.0
        }

        // Actualizar y dibujar texto
        updateAndDrawText()
    }

    fun updateAndDrawText() {
        textX += textDirection
        if (textX > width - 200 || textX < 50) {
            textDirection *= -1
        }

        textHue += 0.5
        if (textHue > 360) textHue = 0.0

        context.font = "40px Georgia"
        context.fillStyle = "hsl($textHue, 50%, 50%)"
        context.fillText("Felices Fiestas", textX, 50.0)
    }

    fun blank() {
        context.fillStyle = "rgba(255,255,255,0.1)"
        context.fillRect(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    fun run() {
        window.setInterval({ wave() }, 30)
        window.setInterval({ blank() }, 50)
    }
}

// Clase FancyLines
class FancyLines {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height
    val width = canvas.width
    var x = width * Random.nextDouble()
    var y = height * Random.nextDouble()
    var hue = 0

    fun line() {
        context.save()

        context.beginPath()

        context.lineWidth = 20.0 * Random.nextDouble()
        context.moveTo(x, y)

        x = width * Random.nextDouble()
        y = height * Random.nextDouble()

        context.bezierCurveTo(width * Random.nextDouble(), height * Random.nextDouble(),
                              width * Random.nextDouble(), height * Random.nextDouble(), x, y)

        hue += (Random.nextDouble() * 10).toInt()

        context.strokeStyle = "hsl($hue, 50%, 50%)"
        context.shadowColor = "white"
        context.shadowBlur = 10.0

        context.stroke()

        context.restore()
    }

    fun run() {
        window.setInterval({ line() }, 40)
    }
}

// Función main para ejecutar ambas animaciones
fun main(args: Array<String>) {
    ColorWaves().run()
    //FancyLines().run()
}
