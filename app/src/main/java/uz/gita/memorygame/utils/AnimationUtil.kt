package uz.gita.memorygame.utils

import android.view.View
import android.widget.ImageView
import uz.gita.memorygame.R
import uz.gita.memorygame.data.CardData

fun ImageView.closeAnimation(block: () -> Unit){
    this.animate().setDuration(250).rotationY(91f).withEndAction{
        rotationY = 89f
        this.scaleType = ImageView.ScaleType.FIT_START
        this.setImageResource(R.drawable.image_back)
        this.animate().setDuration(250).rotationY(0f).withEndAction{
            block()
        }.start()
    }.start()
}

fun ImageView.closeAnimation() {
    this.animate().setDuration(250).rotationY(91f).withEndAction {
        rotationY = 89f
        this.scaleType = ImageView.ScaleType.FIT_START
        setImageResource(R.drawable.image_back)
        this.animate().setDuration(250).rotationY(0f).start()
    }.start()
}

fun ImageView.openAnimation(block : () -> Unit){
    this.animate().setDuration(250).rotationY(89f).withEndAction {
        rotationY = 91f
        this.scaleType = ImageView.ScaleType.FIT_START
        setImageResource((this.tag as CardData).imageUrl)
        this.animate().setDuration(250).rotationY(180f).withEndAction {
            block()
        }.start()
    }.start()
}

fun ImageView.remove (block : ImageView.()-> Unit){
    this.animate().setDuration(250).rotationY(0f).rotationX(0f).withEndAction {
        this.gone()
        block(this)
    }.start()
}

fun View.gone(){
    this.visibility = View.INVISIBLE
}

fun View.visible (){
    this.visibility = View.VISIBLE
}