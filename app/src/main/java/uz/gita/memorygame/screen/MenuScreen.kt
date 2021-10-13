package uz.gita.memorygame.screen
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import uz.gita.memorygame.databinding.ScreenMenuBinding
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygame.R
import uz.gita.memorygame.sharedpref.SharedPref

@AndroidEntryPoint
class MenuScreen : Fragment (R.layout.screen_menu) {
    val shared = SharedPref.getSharedPref()
    private val vb  by viewBinding (ScreenMenuBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared = SharedPref.getSharedPref()
        vb.easy.setOnClickListener {
            shared.menu = 1
            shared.isNew = shared.easy < 2
            if (!shared.isNew && shared.menu ==1) {
                showDialog(3,4)
            }else {
                shared.menu = 1
                levelChoose(3, 4)
            }

        }
        vb.medium.setOnClickListener {
            shared.menu =2
            shared.isNew = shared.medium<2
            if (!shared.isNew && shared.menu == 2) {
                showDialog(4,5)
            }else{
                shared.menu =2
                levelChoose(4, 5)
            }
        }
        vb.hard.setOnClickListener {
            shared.menu = 3
            shared.isNew = shared.hard<2
            if (!shared.isNew && shared.menu == 3) {
                showDialog(5,6)
            }else{
                levelChoose(5, 6)
            }
        }

    }
    private fun levelChoose(x : Int, y :Int){
        findNavController().navigate(MenuScreenDirections.actionMenuScreenToEasyScreen(x, y))
    }
    private fun showDialog(x: Int, y: Int){
        val dialog  = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_menu)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val btnContinue = dialog.findViewById<TextView>(R.id.btn_continue)
        val btnNewGame = dialog.findViewById<TextView>(R.id.btn_new_game)

       btnContinue.setOnClickListener {

           levelChoose(x, y)
           dialog.dismiss()
       }

        btnNewGame.setOnClickListener {

            shared.isNew = true
            levelChoose(x, y)
            dialog.dismiss()
        }

        dialog.show()
    }
}