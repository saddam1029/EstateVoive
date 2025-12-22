package com.example.estatevoice

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CallAdapter(
    private val usersList: List<ClientModel>
) : RecyclerView.Adapter<CallAdapter.CallViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CallViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_call_logs, parent, false)
        return CallViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CallViewHolder,
        position: Int
    ) {
        val call = usersList[position]

        holder.tvName.text = call.customer_name ?: "Unknown"
        val status = call.customer_was_satisfied ?: false

        if (status == true) {

            // 🟢 GREEN STATE
            holder.tvStatus.text = "Qualified"

            holder.tvStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )

            holder.ivStatus.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )

            holder.layoutStatus.setBackgroundResource(R.drawable.bg_status_green)
        } else {

            // 🔴 RED STATE
            holder.tvStatus.text = "Not Qualified"

            holder.tvStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            )

            holder.ivStatus.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            )

            holder.layoutStatus.setBackgroundResource(R.drawable.bg_status_red)
        }


    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class CallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val ivStatus: ImageView = itemView.findViewById(R.id.ivStatus)
        val layoutStatus: ConstraintLayout = itemView.findViewById(R.id.clStatus)

    }
}