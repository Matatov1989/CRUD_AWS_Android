package com.matatov.crud_aws_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val dataSet: ArrayList<UserData>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var listenerEdit: onItemClickListenerEdit
    private lateinit var listenerRemove: onItemClickListenerRemove

    //interface for listener click edit button
    interface onItemClickListenerEdit {
        fun onItemClick(position: Int)
    }

    //interface for listener click remove button
    interface onItemClickListenerRemove {
        fun onItemClick(position: Int)
    }

    //listener click to edit button on user card
    fun setOnItemClickListenerEdit(listener: onItemClickListenerEdit) {
        listenerEdit = listener
    }

    //listener click to remove button on user card
    fun setOnItemClickListenerRemove(listener: onItemClickListenerRemove) {
        listenerRemove = listener
    }

    class ViewHolder(
        view: View,
        listenerEdit: onItemClickListenerEdit,
        listenerRemove: onItemClickListenerRemove
    ) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewAge: TextView
        val textViewCountry: TextView

        private val btnEditUser: ImageButton
        private val btnRemoveUser: ImageButton

        init {
            //Define click listener for the ViewHolder's View.
            textViewName = view.findViewById(R.id.textViewName)
            textViewAge = view.findViewById(R.id.textViewAge)
            textViewCountry = view.findViewById(R.id.textViewCountry)

            btnEditUser = view.findViewById(R.id.btnEditUser)
            btnRemoveUser = view.findViewById(R.id.btnRemoveUser)

            //click to edit button (edit user data)
            btnEditUser.setOnClickListener {
                listenerEdit.onItemClick(absoluteAdapterPosition)
            }

            //click to button the remove user
            btnRemoveUser.setOnClickListener {
                listenerRemove.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    //Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_user_view, viewGroup, false)

        return ViewHolder(view, listenerEdit, listenerRemove)
    }

    //Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Get element from your dataset at this position and replace the
        //contents of the view with that element
        viewHolder.textViewName.text = dataSet.get(position).name
        viewHolder.textViewAge.text = dataSet.get(position).age.toString()
        viewHolder.textViewCountry.text = dataSet.get(position).country
    }

    //Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    //update recycler view after insert a new user
    fun insertItem(newList: ArrayList<UserData>, position: Int) {
        dataSet.clear()
        dataSet.addAll(newList)
        notifyItemInserted(position)
    }

    //update recycler view after remove the user
    fun removeItem(newList: ArrayList<UserData>, position: Int) {
        dataSet.clear()
        dataSet.addAll(newList)
        notifyItemRemoved(position)
    }

    //update recycler view after update date the user
    fun updateItem(newList: ArrayList<UserData>, position: Int) {
        dataSet.clear()
        dataSet.addAll(newList)
        notifyItemChanged(position)
    }
}
