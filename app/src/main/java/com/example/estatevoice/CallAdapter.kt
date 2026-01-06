package com.example.estatevoice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import java.text.SimpleDateFormat
import java.util.Locale
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CallAdapter(
    private val context: Context,
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

        holder.tvName.text = call.fullName ?: "Unknown"
        holder.tvContact.text = call.phone ?: "No Number"
        holder.tvBusiness.text = call.businessName ?: "No Business"
        holder.tvTime.text = formatTimeOnly(call.createdAt)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CallDetailActivity::class.java)

            intent.putExtra("clientID", call.id)

            intent.putExtra("name", call.fullName) ?: "Unknown"
            intent.putExtra("contact", call.phone) ?: "Unknown"
            intent.putExtra("business", call.businessName) ?: "No Business"
            intent.putExtra("email", call.email) ?: "No Email"
            intent.putExtra("role", call.role) ?: "Unknown"
            intent.putExtra("address", call.city) ?: "No Address"
            intent.putExtra("interest", call.intentLevel) ?: "Unknown"
            intent.putExtra("note", call.followUpReason) ?: "No Reasons"
            intent.putExtra("summary", call.callSummary) ?: "No Call Summary for this Time."
            intent.putExtra("time", call.createdAt) ?: "-"
            intent.putExtra("audio", call.recordingUrl) ?: "-"

            context.startActivity(intent)
        }


        val status = call.intentLevel ?: "low"

        if (status == "high") {

            // 🟢 GREEN STATE
            holder.tvStatus.text = "Qualified"

            holder.tvStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.dark_green)
            )

            holder.ivStatus.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.dark_green)
            )

            holder.layoutStatus.setBackgroundResource(R.drawable.bg_status_green)
        }
        else if (status == "medium")
        {
            // 🟢 GREEN STATE
            holder.tvStatus.text = "Qualified"

            holder.tvStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.dark_green)
            )

            holder.ivStatus.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.dark_green)
            )

            holder.layoutStatus.setBackgroundResource(R.drawable.bg_status_green)
        } else {

            // 🔴 RED STATE
            holder.tvStatus.text = "Not Qualified"

            holder.tvStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.dark_red)
            )

            holder.ivStatus.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.dark_red)
            )

            holder.layoutStatus.setBackgroundResource(R.drawable.bg_status_red)
        }


    }


    fun formatTimeOnly(dateTime: String?): String {
        if (dateTime.isNullOrEmpty()) return "--"

        return try {
            val inputFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            val outputFormat =
                SimpleDateFormat("h:mm a", Locale.getDefault())

            val date = inputFormat.parse(dateTime.substring(0, 19))
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "--"
        }
    }


    override fun getItemCount(): Int {
        return usersList.size
    }

    class CallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvContact: TextView = itemView.findViewById(R.id.tvContact)
        val tvBusiness: TextView = itemView.findViewById(R.id.tvBusiness)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val ivStatus: ImageView = itemView.findViewById(R.id.ivStatus)
        val layoutStatus: ConstraintLayout = itemView.findViewById(R.id.clStatus)

    }
}