package com.example.partescnp.utils



import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.partescnp.R

class PersonAdapter(private val cursor: Cursor) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        if (cursor.moveToPosition(position)) {
            holder.nameTextView.text = cursor.getString(0) // Nombre
            holder.lastNameTextView.text = cursor.getString(1) // Primer Apellido
        }
    }

    override fun getItemCount(): Int = cursor.count

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(android.R.id.text1)
        val lastNameTextView: TextView = view.findViewById(android.R.id.text2)
    }
}
