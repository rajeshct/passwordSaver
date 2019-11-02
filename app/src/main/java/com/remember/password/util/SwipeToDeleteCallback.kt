package com.remember.password.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.remember.password.base.BaseDiAdapter


class SwipeToDeleteCallback(var baseAdapter: BaseDiAdapter<*>, private val icon: Drawable) :
    ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
    private val background: ColorDrawable = ColorDrawable(Color.RED)

    /**
     * Called when ItemTouchHelper wants to move the dragged item from its old position to
     * the new position.
     *
     *
     * If this method returns true, ItemTouchHelper assumes `viewHolder` has been moved
     * to the adapter position of `target` ViewHolder
     * ([ ViewHolder#getAdapterPosition()][ViewHolder.getAdapterPosition]).
     *
     *
     * If you don't support drag & drop, this method will never be called.
     *
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
     * @param viewHolder   The ViewHolder which is being dragged by the user.
     * @param target       The ViewHolder over which the currently active item is being
     * dragged.
     * @return True if the `viewHolder` has been moved to the adapter position of
     * `target`.
     * @see .onMoved
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    /**
     * Called when a ViewHolder is swiped by the user.
     *
     *
     * If you are returning relative directions ([.START] , [.END]) from the
     * [.getMovementFlags] method, this method
     * will also use relative directions. Otherwise, it will use absolute directions.
     *
     *
     * If you don't support swiping, this method will never be called.
     *
     *
     * ItemTouchHelper will keep a reference to the View until it is detached from
     * RecyclerView.
     * As soon as it is detached, ItemTouchHelper will call
     * [.clearView].
     *
     * @param viewHolder The ViewHolder which has been swiped by the user.
     * @param direction  The direction to which the ViewHolder is swiped. It is one of
     * [.UP], [.DOWN],
     * [.LEFT] or [.RIGHT]. If your
     * [.getMovementFlags]
     * method
     * returned relative flags instead of [.LEFT] / [.RIGHT];
     * `direction` will be relative as well. ([.START] or [                   ][.END]).
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        baseAdapter.deleteItem(position)
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (viewHolder is BaseDiAdapter.BaseViewHolder && viewHolder.isHeader) {
            return
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20
        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight
        when {
            dX > 0 -> {
                // Swiping to the right
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                    itemView.left, itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
                )
            }
            dX < 0 -> {
                // Swiping to the left
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom
                )
            }
            else -> {
                // view is unSwiped
                background.setBounds(0, 0, 0, 0)
            }
        }
        background.draw(c)
        icon.draw(c)
    }

}