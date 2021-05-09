package com.ilhomjon.dbcreatetableandquery

import Adapters.ContactAdapter
import DB.MyDbHelper
import Models.Contact
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import com.ilhomjon.dbcreatetableandquery.databinding.ActivityMainBinding
import com.ilhomjon.dbcreatetableandquery.databinding.MyDialogBinding

class MainActivity : AppCompatActivity() {

    lateinit var contactAdapter:ContactAdapter
    lateinit var myDbHelper: MyDbHelper
    lateinit var list:ArrayList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)

        list = myDbHelper.getAllContact()
        contactAdapter = ContactAdapter(list, object : ContactAdapter.OnMyItemListener{
            override fun onClickMyItem(contact: Contact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(this@MainActivity, imageView)
                popupMenu.inflate(R.menu.popup_menu)

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when(item?.itemId){
                            R.id.edit ->{
                                val dialog = AlertDialog.Builder(this@MainActivity)
                                val myDialogBinding = MyDialogBinding.inflate(layoutInflater, null, false)
                                dialog.setView(myDialogBinding.root)
                                myDialogBinding.edtName.setText(contact.name)
                                myDialogBinding.edtNumber.setText(contact.phoneNumber)

                                dialog.setPositiveButton("Edit", object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        contact.name = myDialogBinding.edtName.text.toString()
                                        contact.phoneNumber = myDialogBinding.edtNumber.text.toString()
                                        myDbHelper.upDataContact(contact)
                                        list.set(position, contact)
                                        contactAdapter.notifyItemChanged(position)
                                    }
                                })

                                dialog.show()
                            }
                            R.id.delete ->{
                                myDbHelper.deleteContact(contact)
                                list.remove(contact)
                                contactAdapter.notifyItemRemoved(list.size)
                                contactAdapter.notifyItemRangeChanged(position, list.size)
                            }
                        }
                        return true
                    }
                })

                popupMenu.show()
            }
        })
        binding.rv.adapter = contactAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId
        when(itemId){
            R.id.menu_add ->{
                val dialog = AlertDialog.Builder(this)
                val myDialogBinding = MyDialogBinding.inflate(layoutInflater, null, false)
                dialog.setView(myDialogBinding.root)

                dialog.setPositiveButton("Save", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val name = myDialogBinding.edtName.text.toString()
                        val number = myDialogBinding.edtNumber.text.toString()
                        val contact = Contact(name, number)
                        myDbHelper.addContact(contact)
                        list.add(contact)
                        contactAdapter.notifyItemInserted(list.size)
                    }
                })

                dialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}