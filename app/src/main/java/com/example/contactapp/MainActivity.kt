package com.example.contactapp
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.databinding.ItemDilogBinding
import com.example.contactapp.databinding.ItemRvBinding
import com.example.contactapp.models.MyContact
import com.example.contactapp.utils.MyData

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.linerRv.removeAllViews()
        if (MyData.list.isEmpty()) {
            binding.linerRv.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.VISIBLE
        } else {
            binding.linerRv.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE
            for (i in MyData.list.indices) {
                val itemRv = ItemRvBinding.inflate(layoutInflater)
                itemRv.root.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${MyData.list[i].phone}")
                    startActivity(intent)
                }
                itemRv.root.setOnLongClickListener {
                    val menu = PopupMenu(this@MainActivity, itemRv.root)
                    menu.menuInflater.inflate(R.menu.my_menu, menu.menu)
                    menu.show()
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menu_delete -> {
                                if (MyData.list[i] == MyContact(itemRv.tvName.text.toString(), itemRv.tvPhone.text.toString())) {
                                    MyData.list.removeAt(i)
                                    binding.linerRv.removeView(itemRv.root)
                                    if (MyData.list.isEmpty()) {
                                        binding.linerRv.visibility = View.GONE
                                        binding.tvEmpty.visibility = View.VISIBLE
                                        binding.imgEmpty.visibility = View.VISIBLE
                                    } else {
                                        onResume()
                                    }
                                }
                            }
                            R.id.menu_edit -> {
                                val dialog = AlertDialog.Builder(this, R.style.NewDialog).create()
                                val customStyle = ItemDilogBinding.inflate(layoutInflater)
                                customStyle.newPhone.setText(MyData.list[i].phone)
                                customStyle.newName.setText(MyData.list[i].name)
                                customStyle.btnSave.setOnClickListener {
                                    MyData.list[i].name = customStyle.newName.text.toString()
                                    MyData.list[i].phone = customStyle.newPhone.text.toString()
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

                    true
                }
                itemRv.tvName.text = MyData.list[i].name
                itemRv.tvPhone.text = MyData.list[i].phone
                binding.linerRv.addView(itemRv.root)
            }
        }
    }
}