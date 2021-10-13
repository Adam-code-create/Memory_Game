package uz.gita.memorygame.sharedpref

import android.content.Context
import uz.gita.memorygame.app.App

class SharedPref private constructor() {
    private val pref = App.instance.getSharedPreferences("MemoryGame", Context.MODE_PRIVATE)

    companion object {
        private lateinit var pref: SharedPref

        fun getSharedPref(): SharedPref {
            if (!::pref.isInitialized) {
                pref = SharedPref()
            }
            return pref
        }
    }
    var level : Int
        get() = pref.getInt("level", 1)
        set(value) = pref.edit().putInt("level", value).apply()

    var imgCount : Int
        get() = pref.getInt("imageCount", 0)
        set(value) = pref.edit().putInt("imageCount", value).apply()

    var steps : Int
        get() = pref.getInt("steps", 1)
        set(value) = pref.edit().putInt("steps", value).apply()

    var attempt : Int
        get() = pref.getInt("attempt", 1)
        set(value) = pref.edit().putInt("attempt", value).apply()

    var isContinue : Boolean
        get() = pref.getBoolean("isContinue", false)
        set(value) = pref.edit().putBoolean("isContinue", value).apply()

    var isNew : Boolean
        get() = pref.getBoolean("isNew", true)
        set(value) = pref.edit().putBoolean("isNew", value).apply()

    var list : String?
        get() = pref.getString("list", null)
        set(value) = pref.edit().putString("list", value).apply()

    var menu : Int
        get() = pref.getInt("menu", 0)
        set(value) = pref.edit().putInt("menu", value).apply()

    var easy : Int
        get() = pref.getInt("easy", 0)
        set(value) = pref.edit().putInt("easy", value).apply()

    var medium : Int
        get() = pref.getInt("medium", 0)
        set(value) = pref.edit().putInt("medium", value).apply()

    var hard : Int
        get() = pref.getInt("hard", 0)
        set(value) = pref.edit().putInt("hard", value).apply()




}