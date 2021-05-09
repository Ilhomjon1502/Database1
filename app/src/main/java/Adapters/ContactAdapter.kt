package Adapters

import Models.Contact
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.dbcreatetableandquery.R
import com.ilhomjon.dbcreatetableandquery.databinding.ItemContactBinding

class ContactAdapter(val list: List<Contact>, var onMyItemListener: OnMyItemListener): RecyclerView.Adapter<ContactAdapter.VH>() {

    inner class VH(var itemContactBinding: ItemContactBinding):RecyclerView.ViewHolder(itemContactBinding.root){

        fun onBind(contact: Contact, position: Int){
            itemContactBinding.txtName.text = contact.name
            itemContactBinding.txtNumber.text = contact.phoneNumber

            itemContactBinding.imageMore.setOnClickListener {
                onMyItemListener.onClickMyItem(contact, position, itemContactBinding.imageMore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int  = list.size

    interface OnMyItemListener{
        fun onClickMyItem(contact: Contact, position: Int, imageView: ImageView)
    }
}