package raj.helpservice.android.helpservice.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.data.ConsumerRequest


class ConsumerAdapter(val list:ArrayList<ConsumerRequest>,val assignOrClose:ConsumerAdapter.AssignOrClose ) : RecyclerView.Adapter<ConsumerAdapter.ConsumerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsumerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.consumer_list_request_item,parent,false)
        return ConsumerHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ConsumerHolder, position: Int) {
        holder.bindVendor(list[position])
    }

    fun updateData(updatedList:ArrayList<ConsumerRequest>){
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }


    inner class ConsumerHolder(itemView :View) :RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        val textServiceType   = itemView.findViewById<TextView>(R.id.service_type)
        val textCreatedOn   = itemView.findViewById<TextView>(R.id.created_on)
        val pincode  = itemView.findViewById<TextView>(R.id.pincode)
        val mobileNumber = itemView.findViewById<TextView>(R.id.mobile_number)
        val vendorName = itemView.findViewById<TextView>(R.id.vendor_name)
        val vendorStatus = itemView.findViewById<TextView>(R.id.vendor_status)
        val assign = itemView.findViewById<Button>(R.id.button_assign)
        val close = itemView.findViewById<Button>(R.id.button_close)
        val circleImageView = itemView.findViewById<CircleImageView>(R.id.request_circler_image)

        override fun onClick(v: View?) {
            if (v?.id == R.id.button_close){
                assignOrClose.clickOnCloseButton(list[adapterPosition])
            }
            else if (v?.id == R.id.button_assign){
                assignOrClose.clickOnAssignButton(list[adapterPosition])
            }
        }
        init {
            this.assign.setOnClickListener(this@ConsumerHolder)
            this.close.setOnClickListener(this@ConsumerHolder)
        }




        fun bindVendor(request:ConsumerRequest){
            textServiceType.text = request.serviceTypeName
            textCreatedOn.text = request.createdOn
            pincode.text = request.pincode
            mobileNumber.text = request.vendorPhone
            vendorName.text = request.vendorName
            vendorStatus.text = request.status

            if (request.status != null && request.status == "Open"){
                close.visibility = View.VISIBLE
            }
            else if (request.status != null && request.status == "Closed")
                close.visibility = View.GONE
            if (request.vendorSelected == "0"){
                assign.visibility = View.VISIBLE

            }else if (request.vendorSelected =="1" )
                assign.visibility = View.GONE

            circleImageView.setImageResource(getDrawable(request.vendorTypeName!!))

        }

    }


    private fun getDrawable(profession: String):Int {
        when (profession) {
            "Plumber" -> return R.drawable.ic_plumbing_active
            "Electrician" -> return R.drawable.ic_electrical_active
            "Painter" -> return R.drawable.ic_painting_active
            "Carpenter" -> return R.drawable.ic_carpenter_active
            "Mason" -> return R.drawable.ic_mason_active
        }
        return R.drawable.ic_user_login
    }


    interface AssignOrClose{
        fun clickOnAssignButton(consumerRequest: ConsumerRequest)
        fun clickOnCloseButton(consumerRequest: ConsumerRequest)
    }
}