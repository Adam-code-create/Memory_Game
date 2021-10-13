package uz.gita.memorygame.screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygame.R
import uz.gita.memorygame.data.CardData
import uz.gita.memorygame.databinding.ScreenEasyBinding
import uz.gita.memorygame.sharedpref.SharedPref
import uz.gita.memorygame.utils.*
import uz.gita.memorygame.viewmodels.EasyViewModel

@AndroidEntryPoint
class EasyScreen : Fragment (R.layout.screen_easy) {
    private val vb by viewBinding(ScreenEasyBinding::bind)
    private lateinit var handler : Handler
    private var x = 0
    private var y = 0
    private var imageCount = 0
    private var _height = 0
    private var _width = 0
    private val imageList = ArrayList<ImageView>()
    private val disappearedImages = ArrayList<String>()
    private val viewModel : EasyViewModel  by viewModels()
    private var isAnimated = false
    private var firstPos = -1
    private var secondPos = -1
    private var levelNumber = 1
    private var attemptCount = 0
    private var steps = 0
    private val gson = Gson()
    private val args : MenuScreenArgs by navArgs()
    private val shared = SharedPref.getSharedPref()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadSize()
        vb.reload.setOnClickListener {
           restart()
        }
        vb.menu.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadSize(){
        handler = Handler(Looper.getMainLooper())
        y = args.height
        x = args.width
        imageCount = x * y
        vb.main.post {
            _height = vb.main.height / (y+4)
            _width = vb.main.width / (x+1)
            vb.container.layoutParams.apply {
                height = y * _height
                width = x * _width
            }
            loadData()
            viewModel.loadImages(imageCount)
        }

        viewModel.imageLiveData.observe(viewLifecycleOwner, imageObserver)
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(){
        for (i in 0 until y){
            for (j in 0 until x){
                val image = ImageView(requireContext())
                vb.container.addView(image)
                image.layoutParams.apply {
                    height = _height
                    width = _width
                }
                image.y = i * (_height) * 1f
                image.x = j * _width * 1f
                image.scaleType = ImageView.ScaleType.FIT_START
                image.setImageResource(R.drawable.image_back)
                imageList.add(image)

            }
        }
        loadDataByMenu()
    }
    private fun restart(){
        vb.container.removeAllViews()
        imageList.clear()
        attemptCount = 1
        vb.attempt.text = attemptCount.toString()
        steps = 1
        vb.step.text = steps.toString()
        firstPos = -1
        secondPos = -1
        isAnimated = false
        loadSize()
    }
    private fun check (){
        val media = MediaPlayer.create(requireContext(), R.raw.correct)
        val firstImageTag =imageList[firstPos].tag as CardData
        val secondImageTag = imageList[secondPos].tag as CardData
        if (firstImageTag.amount == secondImageTag.amount){
            attemptCount++
            shared.attempt = attemptCount
            vb.attempt.text = attemptCount.toString()
           media.start()
            handler.postDelayed({
                imageList[firstPos].remove {
                 //   disappearedImages.add("$firstPos")
                //   val listImg :String = gson.toJson(disappearedImages)
                 //   shared.list = listImg
                    scaleX = 1f
                    scaleY = 1f
                }
                imageList[secondPos].remove {
                 //   disappearedImages.add("$secondPos")
                 //   val listImg :String = gson.toJson(disappearedImages)
                 //   shared.list = listImg
                    scaleX = 1f
                    scaleY = 1f
                    imageCount -= 2
                    if (imageCount == 0) finishGame()
                        firstPos = -1
                        secondPos = -1
                        isAnimated = false

                }
            }, 250)
        }else {
            handler.postDelayed({
                attemptCount++
                shared.attempt = attemptCount
                vb.attempt.text = attemptCount.toString()
                imageList[firstPos].closeAnimation()
                imageList[secondPos].closeAnimation{
                    firstPos = -1
                    secondPos = -1
                    isAnimated = false
                }
            }, 250)


        }

    }
    private fun finishGame(){
        for (image in imageList) {
            image.visible()
        }
        showDialog()
    }
    private fun continueGame(){
        val jSontext = shared.list
        val listImg = gson.fromJson(jSontext,Array<String>::class.java)
        if (listImg != null){
            for (i in imageList.indices){
                for (j in listImg.indices)
                    if (i == listImg[j].toInt()){
                        imageList[i].gone()
                    }
            }
        }

    }

    private val imageObserver = Observer<ArrayList<CardData>>{
        val media = MediaPlayer.create(requireContext(), R.raw.click)
        for ( i in imageList.indices){
            imageList[i].apply {
                tag = it[i]
                setOnClickListener {
                    media.start()
                    steps++
                    shared.steps = steps
                    vb.step.text = steps.toString()
                    if (isAnimated) {
                        return@setOnClickListener
                    }
                    if (firstPos == -1){
                        firstPos = i
                        if (rotationY == 0f)this.openAnimation {  }
                    } else if (i != firstPos) {
                        isAnimated = true
                        secondPos = i
                        if (rotationY == 0f) this.openAnimation{
                            check()
                        }

                    }

                }
            }
    }
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog(){
        val media = MediaPlayer.create(requireContext(), R.raw.congrat)
        media.start()
        val dialog  = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_final)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setCancelable(false)
        val level = dialog.findViewById<TextView>(R.id.level)
        val reload = dialog.findViewById<ImageView>(R.id.reloadDialog)
        val menu = dialog.findViewById<ImageView>(R.id.menuDialog)

        reload.setOnClickListener {
            restart()
            dialog.dismiss()
        }
        if (levelNumber == 10) level.text = "Home"
        level.setOnClickListener {
            levelNumber++
            takeLevelByMenu(levelNumber)
            if (levelNumber < 11){
                vb.levelText.text = levelNumber.toString()
                restart()
                dialog.dismiss()
            }else{
                takeLevelByMenu(1)
                findNavController().popBackStack()
                dialog.dismiss()
            }

        }
        menu.setOnClickListener {
            findNavController().popBackStack()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun takeLevelByMenu(level :Int){
        shared.isNew = false
        when (shared.menu){

            1 ->{
                shared.easy = level
            }
            2 -> {
                shared.medium = level
            }
            3 ->{
                shared.hard = level
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun loadDataByMenu(){
        if (shared.menu == 1){
            if (!shared.isNew){
                vb.levelText.text ="${shared.easy}/10"
                levelNumber = shared.easy
            }else{
                vb.levelText.text ="$levelNumber/10"
                shared.easy = 1

                // vb.step.text = shared.steps.toString()
                // vb.attempt.text = shared.attempt.toString()
            }
        } else if (shared.menu == 2){
            if (!shared.isNew){
                vb.levelText.text ="${shared.medium}/10"
                levelNumber = shared.medium
            }else{
                vb.levelText.text ="$levelNumber/10"
                shared.medium = 1

                // vb.step.text = shared.steps.toString()
                // vb.attempt.text = shared.attempt.toString()
            }
        } else{
            if (!shared.isNew){
                vb.levelText.text ="${shared.hard}/10"
                levelNumber = shared.hard
            }else{
                vb.levelText.text ="$levelNumber/10"
                shared.hard = 1

                // vb.step.text = shared.steps.toString()
                // vb.attempt.text = shared.attempt.toString()
            }
        }




    }
}