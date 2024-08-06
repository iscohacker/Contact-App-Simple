package com.example.contactapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.adapter.RvAdapter
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.databinding.ItemDilogBinding
import com.example.contactapp.models.MyContact
import com.example.contactapp.utils.Mys

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onResume()
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Mys.init(this)
        val list = Mys.contactList
        if (list.isEmpty()) {
            binding.rv.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.VISIBLE
        } else {
            binding.rv.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE

            rvAdapter = RvAdapter(this, list)
            binding.rv.adapter = rvAdapter
        }
    }

    override fun moreClick(myContact: MyContact, position: Int, imageView: ImageView) {
        Mys.init(this)
        val list = Mys.contactList
        val menu = PopupMenu(this@MainActivity, imageView)
        menu.menuInflater.inflate(R.menu.my_menu, menu.menu)
        menu.show()
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_delete -> {
                    list.removeAt(position)
                    Mys.contactList = list
                    onResume()
                }

                R.id.menu_edit -> {
                    val dialog = AlertDialog.Builder(this, R.style.NewDialog).create()
                    val customStyle = ItemDilogBinding.inflate(layoutInflater)
                    customStyle.newPhone.setText(myContact.phone)
                    customStyle.newName.setText(myContact.name)
                    customStyle.btnSave.setOnClickListener {
                        list[position].name = customStyle.newName.text.toString()
                        list[position].phone = customStyle.newPhone.text.toString()
                        Mys.contactList = list
                        onResume()
                        dialog.cancel()
                    }
                    customStyle.btnCancel.setOnClickListener {
                        dialog.cancel()
                    }
                    dialog.setView(customStyle.root)
                    dialog.show()
                }
            }
            true
        }
    }

    override fun itemClick(myContact: MyContact) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${myContact.phone}")
        startActivity(intent)
    }
}
