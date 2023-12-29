import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.random.Random
import kotlin.math.PI

val canvas = initializeCanvas()
var clickCount = 0
val colorWaves = ColorWaves()
val fancyLines = FancyLines()
var textX = 50.0
var textDirection = 1
var textHue = 0.0

fun initializeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = window.innerWidth.toInt()
    context.canvas.height = window.innerHeight.toInt()
    document.body!!.appendChild(canvas)
    return canvas
}

class ColorWaves {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height
    val width = canvas.width
    var radius = 10.0
    var hue = 0

    fun wave() {
        if (clickCount != 1 && clickCount != 4) return

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
    }

    fun blank() {
        if (clickCount != 1 && clickCount != 4) return

        context.fillStyle = "rgba(255,255,255,0.1)"
        context.fillRect(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    fun run() {
        window.setInterval({ wave() }, 100)
        window.setInterval({ blank() }, 250)
    }
}

class FancyLines {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height
    val width = canvas.width
    var x = width * Random.nextDouble()
    var y = height * Random.nextDouble()
    var hue = 0

    fun line() {
        if (clickCount != 2 && clickCount != 4) return

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
        window.setInterval({ line() }, 100)
    }
}

fun updateAndDrawText() {
    if (clickCount != 3 && clickCount != 4) return

    textX += textDirection
    if (textX > canvas.width - 200 || textX < 50) {
        textDirection *= -1
    }

    textHue += 0.5
    if (textHue > 360) textHue = 0.0

    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.font = "40px Georgia"
    context.fillStyle = "hsl($textHue, 50%, 50%)"
    context.fillText("Felices Fiestas", textX, 50.0)
}

fun displayInitialText() {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.font = "40px Georgia"
    context.fillStyle = "white"
    context.fillText("Haz click aqui", canvas.width / 2.0 - 100, canvas.height / 2.0)
}

fun clearCanvas() {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
}

fun main(args: Array<String>) {
    displayInitialText()

    canvas.addEventListener("click", {
        clickCount++
        clearCanvas()

        when (clickCount) {
            1 -> colorWaves.run()
            2 -> fancyLines.run()
            3 -> window.setInterval({ updateAndDrawText() }, 50)
            4 -> {
                colorWaves.run()
                fancyLines.run()
                window.setInterval({ updateAndDrawText() }, 50)
            }
            else -> {
                clickCount = 0
                displayInitialText()
            }
        }
    })
}
