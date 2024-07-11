package com.example.contactapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityAddBinding
import com.example.contactapp.models.MyContact
import com.example.contactapp.utils.Mys

class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Mys.init(this)
        val list = Mys.contactList

        binding.apply {
            btnSave.setOnClickListener {

                if (list.isNotEmpty()) {
                    for (i in list.indices) {
                        if (edtPhone.text.toString() != list[i].phone && edtName.text.isNotBlank() && edtPhone.text.isNotBlank()) {
                            list.add(MyContact(edtName.text.toString(), edtPhone.text.toString()))
                            Mys.contactList = list
                            finish()
                        } else {
                            Toast.makeText(this@AddActivity, "Ushbu telefon raqam bilan kontakt saqlangan,\nyoki ma'lumotlar to'liq kiritilmagan!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    if (edtName.text.isNotBlank() && edtPhone.text.isNotBlank()) {
                        list.add(MyContact(edtName.text.toString(), edtPhone.text.toString()))
                        Mys.contactList = list
                        finish()
                    } else {
                        Toast.makeText(this@AddActivity, "Ma'lumotlar to'liq kiritilmagan!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}