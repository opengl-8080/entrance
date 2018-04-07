package entrance.view.javafx.window.viewer

import entrance.domain.image.Image
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.image.ImageView
import javafx.scene.image.Image as JavaFxImage

class ImageViewModel (
    private val imageView: ImageView,
    private val imageList: List<Image>,
    initialImage: Image
) {
    private var index: Int = imageList.indexOf(initialImage)
    private var zooming: Boolean = false
    private val zoomScale = SimpleDoubleProperty(1.0)
    
    init {
        loadImage(initialImage)
        this.imageView.scaleXProperty().bind(zoomScale)
        this.imageView.scaleYProperty().bind(zoomScale)
    }
    
    /**
     * 画像の位置・ズームを初期状態に戻す.
     */
    fun reset() {
        zoomScale.set(1.0)
        finishZoom()
        resetTranslation()
    }

    /**
     * ズーム操作が終了したことを通知する.
     *
     *
     * この操作が終了するまで、ズーム操作中の扱いになり、 [.isZooming] は true を返します.
     *
     */
    fun finishZoom() {
        zooming = false
        if (!isZoomed()) {
            resetTranslation()
        }
    }

    /**
     * ズーム操作が終了し、画像が拡大表示されていることを確認する.
     * @return 拡大表示されている場合は true
     */
    fun isZoomed(): Boolean {
        return !isZooming() && 1.0 < zoomScale.get()
    }

    /**
     * 現在ズーム操作の最中かどうかを確認する.
     * @return ズーム中の場合は true
     */
    fun isZooming(): Boolean {
        return zooming
    }

    /**
     * 画像の位置を初期位置に戻す.
     */
    private fun resetTranslation() {
        imageView.translateX = 0.0
        imageView.translateY = 0.0
    }

    /**
     * 画像の位置を移動させる.
     * @param dx 横軸方向の変位
     * @param dy 縦軸方向の変位
     */
    fun translate(dx: Double, dy: Double) {
        imageView.translateX = imageView.translateX + dx
        imageView.translateY = imageView.translateY + dy
    }

    /**
     * 画像を１段階拡大する.
     */
    fun zoomIn() {
        zoom(1.1)
    }

    /**
     * 画像を１段階縮小する.
     */
    fun zoomOut() {
        zoom(0.9)
    }

    /**
     * 拡大率を指定して画像を拡大・縮小する.
     *
     *
     * 現在のスケールに、指定した拡大率を乗算した値が新しいスケールとして設定されます.<br></br>
     * つまり、 0.0 - 1.0 の間は縮小、 1.0 より大きい場合は拡大になります.
     *
     *
     * 拡大・縮小の結果が規定のサイズを超える場合は、規定のサイズ内に収まるように
     * スケールは調整されます.
     *
     * @param rate 拡大率 (0.0 より大きい値)
     */
    fun zoom(rate: Double) {
        var scale = zoomScale.get() * rate
        if (scale < 1.0) {
            scale = 1.0
        } else if (5.0 < scale) {
            scale = 5.0
        }
        zoomScale.set(scale)
        zooming = true
    }

    /**
     * 前の画像を読み込む.
     */
    fun loadPreviousImage() {
        index--
        if (index < 0) {
            index = imageList.size - 1
        }
        
        loadImage(imageList[index])
    }

    /**
     * 次の画像を読み込む.
     */
    fun loadNextImage() {
        index++
        if (imageList.size <= index) {
            index = 0
        }
        
        loadImage(imageList[index])
    }
    
    private fun loadImage(image: Image) {
        imageView.image = JavaFxImage(image.stringPath)
        reset()
    }
}