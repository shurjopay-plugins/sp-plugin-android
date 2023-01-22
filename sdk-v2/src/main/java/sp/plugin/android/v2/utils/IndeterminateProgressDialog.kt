package sp.plugin.android.v2.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import sp.plugin.android.v2.R

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class IndeterminateProgressDialog(context: Context) : AlertDialog(context) {
    private val messageTextView: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null)
        messageTextView = view.findViewById(R.id.message)
        setView(view)
    }

    override fun setMessage(message: CharSequence?) {
        this.messageTextView.text = message.toString()
    }

}