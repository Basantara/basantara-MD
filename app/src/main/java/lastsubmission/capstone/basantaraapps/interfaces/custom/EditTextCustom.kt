package lastsubmission.capstone.basantaraapps.interfaces.custom

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatEditText
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import lastsubmission.capstone.basantaraapps.R

class EditTextCustom: AppCompatEditText {
    private lateinit var lockIcon: Drawable
    private var isPasswordVisible = false

    // Konstruktor pertama
    constructor(context: Context) : super(context) {
        initialize()
    }

    // Konstruktor kedua
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    // Konstruktor ketiga
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    // Mengatur tampilan dari EditText
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialize() {
        lockIcon = ContextCompat.getDrawable(context, R.drawable.baseline_lock_24) as Drawable
        compoundDrawablePadding = 12
        setCompoundDrawablesWithIntrinsicBounds(null, null, lockIcon, null)
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Menambahkan listener untuk ikon mata pada EditText
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = 2
                if (event.rawX >= (right - compoundDrawables[drawableRight].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        // Menambahkan listener untuk perubahan teks pada EditText
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (charSequence.isNullOrEmpty() || charSequence.length < 8) {
                    error = context.getString(R.string.invalid_password)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    // Fungsi untuk mengatur visibilitas kata sandi
    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        inputType = if (isPasswordVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        setSelection(text!!.length)
        setCompoundDrawablesWithIntrinsicBounds(null, null, lockIcon, null)
    }
}