package entrance.view.javafx.window.viewer

import entrance.domain.image.ImageFile
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.ProgressBar
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class ImageViewModel (
        private val imageView: ImageView,
        private val loadingImageProgressBar: ProgressBar,
        private val imageFileList: List<ImageFile>,
        initialImageFile: ImageFile
) {
    private var index: Int = imageFileList.indexOf(initialImageFile)
    private var zooming: Boolean = false
    private val zoomScale = SimpleDoubleProperty(1.0)

    private var previousImage: Image
    private var image: Image
    private var nextImage: Image
    
    init {
        loadingImageProgressBar.managedProperty().bind(loadingImageProgressBar.visibleProperty())
        imageView.managedProperty().bind(imageView.visibleProperty())
        
        previousImage = toJavaFxImage(previousIndex())
        image = toJavaFxImage(index)
        nextImage = toJavaFxImage(nextIndex())
        switchImage()
        
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
        index = previousIndex()

        nextImage = image
        image = previousImage
        previousImage = toJavaFxImage(previousIndex())
        
        switchImage()
    }

    /**
     * 次の画像を読み込む.
     */
    fun loadNextImage() {
        index = nextIndex()
        
        previousImage = image
        image = nextImage
        nextImage = toJavaFxImage(nextIndex())

        switchImage()
    }
    
    private fun toJavaFxImage(index: Int): Image = Image(imageFileList[index].uri.toString(), true)

    private fun previousIndex(): Int = if (index - 1 < 0) imageFileList.size - 1 else index - 1
    private fun nextIndex(): Int = if (imageFileList.size <= index + 1) 0 else index + 1
    
    private fun switchImage() {
        loadingImageProgressBar.visibleProperty().bind(image.progressProperty().lessThan(1.0))
        loadingImageProgressBar.progressProperty().bind(image.progressProperty())

        imageView.visibleProperty().bind(image.progressProperty().isEqualTo(1.0, 0.0))
        imageView.image = image

        reset()
    }
}