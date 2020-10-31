package com.example.b_my_friend.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.b_my_friend.R
import com.example.b_my_friend.ui.login.LoginActivity


class LogoutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.dialog_text)
            .setPositiveButton(R.string.yes) { dialog, which ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                // FirebaseAuth.getInstance().signOut()
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(R.string.no) { dialog, which ->
                dismiss()
            }

        return builder.create()
    }

}
