package com.example.b_my_friend.ui.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.b_my_friend.R
import com.example.b_my_friend.data.SessionManager
import com.example.b_my_friend.data.model.User
import com.example.b_my_friend.db.AccountDataBase
import com.example.b_my_friend.networking.Message
import com.example.b_my_friend.networking.NetworkService
import com.example.b_my_friend.networking.UserAuth
import com.example.b_my_friend.ui.page.PageViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_settings_app_bar_t.*
import kotlinx.android.synthetic.main.fragment_settings_app_bar_t.view.*
import kotlinx.android.synthetic.main.update_email_dialog.*
import kotlinx.android.synthetic.main.update_email_dialog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class SettingsFragmentAppBar : Fragment() {

    private val RESULT_LOAD_IMG = 1
    private var base64Image: String = ""

    val accountDB: AccountDataBase by lazy {
        Room.databaseBuilder(requireContext(), AccountDataBase::class.java, "account.db").build() }
    val token by lazy { SessionManager(requireContext()).fetchAuthToken()!! }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //for refresh icon on top
        val refreshActivity = requireActivity().findViewById<SwipeRefreshLayout>(R.id.refreshActivity)
        settings_app_bar_setting.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                _, verticalOffset ->
            refreshActivity.isEnabled = verticalOffset == 0
        })

        GlobalScope.launch(Dispatchers.IO) {  //change avatarPath so long
            val account = accountDB.dao().getAccountInfo()
            val bitmap = BitmapFactory.decodeFile(account.imgPath)
            launch(Dispatchers.Main) {
                Log.e("setting", account.toString())
                if (bitmap != null)
                    view.avatarSetting.setImageBitmap(bitmap)
                view.nickname.text = account.name
                view.settingsEmail.text = account.email
                if (account.emailVerifiedAt == null) {
                    view.verify.visibility = View.VISIBLE
                    view.verify.setOnClickListener {
                        sendEmailVerification(token)
                    }
                }
            }

        }


        changeNick.setOnClickListener {
            dialogChangeNickname().show()
        }


        changeEmail.setOnClickListener {
            dialogChangeEmail().show()
        }


        fab_changeAvatar.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data!!.data
                val selectedImage = MediaStore.Images.Media.getBitmap(
                    requireActivity().applicationContext.contentResolver, imageUri)
                val stream = ByteArrayOutputStream()
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, stream)
                val image = stream.toByteArray()
                if (image.size < 2000000) {   //limit to 2mb
                    GlobalScope.launch(Dispatchers.IO) {
                        base64Image = Base64.encodeToString(image, Base64.DEFAULT)
                        base64Image = base64Image.replace("\\s".toRegex(), "")
                        updateAvatar(requireActivity(), token, base64Image)  // context because after check photo from gallery Fragment not attached to an context
                    }
                    requireActivity().recreate()
                }else{
                    Toast.makeText(requireContext(), "Image must be until 2 mb", Toast.LENGTH_LONG).show()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_settings_app_bar_t, container, false)
        childFragmentManager.beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()
        return v

    }

    private fun sendEmailVerification(token: String){
        val call = NetworkService().getService().sendEmailVerification("Bearer $token")
        call.enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                verify.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Mail for verify already send on your email", Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context, "Please, connect to the internet", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun dialogChangeNickname(): AlertDialog  {
        val updateNick = EditText(requireContext())
        updateNick.setText(requireView().nickname.text)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Update Nickname")
        builder.setView(updateNick)
        builder.setPositiveButton("Ok") { _, _ ->
            val newNick = updateNick.text.toString()
            if (newNick != "") {
                val call = NetworkService().getService().updateNickname("Bearer $token", newNick)
                call.enqueue(object : Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        GlobalScope.launch {
                            accountDB.dao().updateNickname(newNick)
                            accountDB.close()
                        }
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Toast.makeText(context, "Please, connect to the internet", Toast.LENGTH_LONG).show()
                    }
                })
                requireActivity().recreate()

            } else
                Toast.makeText(requireContext(), "the field must be filled", Toast.LENGTH_LONG).show()
        }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        return builder.create()
    }


    private fun dialogChangeEmail(): AlertDialog  {
        val builderView = LayoutInflater.from(context).inflate(R.layout.update_email_dialog, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Update Email")
        //val dialogView: View = R.id.updateEmailDialog
        builder.setView(builderView)
        builderView.newEmail.setText(requireView().settingsEmail.text)
        builder.setPositiveButton("Ok") { _, _ ->
            val newEmail = builderView.newEmail.text.toString()
            val password = builderView.password.text.toString()
            if (newEmail  != "" || password != "") {
                val call = NetworkService().getService().updateEmail("Bearer $token", newEmail, password)
                call.enqueue(object : Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        if (response.isSuccessful){
                            Toast.makeText(requireContext(), "Mail for verify already send on your email", Toast.LENGTH_LONG).show()
                        }else
                            Toast.makeText(requireContext(), "Email or password not correct", Toast.LENGTH_LONG).show()
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Toast.makeText(context, "Please, connect to the internet", Toast.LENGTH_LONG).show()
                    }
                })
            } else
                Toast.makeText(requireContext(), "the fields must be filled", Toast.LENGTH_LONG).show()
        }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        return builder.create()
    }


    private fun updateAvatar(activity: FragmentActivity, token: String, img: String){
        val call = NetworkService().getService().updateAvatar("Bearer $token", img)
        call.enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                GlobalScope.launch {
                    val viewModel= ViewModelProvider(activity).get(PageViewModel::class.java)
                    val account = accountDB.dao().getAccountInfo()
                    val file = File(activity.cacheDir.absolutePath, "${account.id}.jpg")
                    file.writeBase64toBitmap(account.avatar, Bitmap.CompressFormat.JPEG, 85)
                    accountDB.dao().updateAvatar(img, file.absolutePath)
                    viewModel.setDataUser(account)
                    accountDB.close()
                }
            }
            override fun onFailure(call: Call<Message>, t: Throwable) {}
        })
    }

    private fun File.writeBase64toBitmap(avatar: String?, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            val imageByteArray = Base64.decode(avatar, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            if (avatar != "")
                bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}