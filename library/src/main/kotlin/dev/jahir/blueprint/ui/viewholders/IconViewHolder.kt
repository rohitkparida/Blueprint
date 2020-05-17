package dev.jahir.blueprint.ui.viewholders

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import dev.jahir.blueprint.R
import dev.jahir.blueprint.data.models.Icon
import dev.jahir.blueprint.extensions.asAdaptive
import dev.jahir.frames.extensions.context.drawable
import dev.jahir.frames.extensions.context.preferences
import dev.jahir.frames.extensions.views.context
import dev.jahir.frames.extensions.views.findView


class IconViewHolder(itemView: View) : SectionedViewHolder(itemView) {

    private val iconView: AppCompatImageView? by itemView.findView(R.id.icon)

    fun bind(icon: Icon, animate: Boolean = true, onClick: ((Icon, Drawable?) -> Unit)? = null) {
        setIconDrawable(icon, context.preferences.animationsEnabled && animate, onClick)
        if (onClick == null) {
            iconView?.disableClick()
            itemView.disableClick()
        }
    }

    private fun View.disableClick() {
        background = null
        isClickable = false
        isLongClickable = false
        isFocusable = false
        isEnabled = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isContextClickable = false
        }
    }

    private fun setIconDrawable(
        icon: Icon,
        animate: Boolean,
        onClick: ((Icon, Drawable?) -> Unit)? = null
    ) {
        iconView?.apply {
            scaleX = 0F
            scaleY = 0F
            alpha = 0F
            val actualDrawable = context.drawable(icon.resId)?.asAdaptive(context)
            setImageDrawable(actualDrawable)
            itemView.setOnClickListener { onClick?.invoke(icon, actualDrawable) }
            if (animate) {
                animate().scaleX(1F)
                    .scaleY(1F)
                    .alpha(1F)
                    .setStartDelay(75)
                    .setDuration(200)
                    .start()
            } else {
                scaleX = 1F
                scaleY = 1F
                alpha = 1F
            }
        }
    }
}