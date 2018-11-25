package raj.helpservice.android.helpservice.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.data.RequestedService

class VendorOpenRequestAdapter(clickRequestService: ClickRequestService) : RecyclerView.Adapter<VendorOpenRequestAdapter.VendorOpenRequestHolder>() {
    val requestList = ArrayList<RequestedService>()
    var clickRequestService:ClickRequestService? = null
    init {
        this.clickRequestService = clickRequestService
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorOpenRequestHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.open_request_item,parent,false)
        return VendorOpenRequestHolder(view)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: VendorOpenRequestHolder, position: Int) {
        holder.bind(requestList[position])
    }

    fun changeValue(list:ArrayList<RequestedService>){
        requestList.clear()
        requestList.addAll(list)
        this.notifyDataSetChanged()
    }

    inner class VendorOpenRequestHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener {
        override fun onClick(v: View?) {
        clickRequestService?.clickOnItemRequestService(requestList[adapterPosition])
        }

        init {
            itemView?.setOnClickListener(this)
        }
            val pincode = itemView?.findViewById<TextView>(R.id.open_request_pincode)
            val consumerName = itemView?.findViewById<TextView>(R.id.consumer_name)
            val lookingFor = itemView?.findViewById<TextView>(R.id.service_type)
            val phone = itemView?.findViewById<TextView>(R.id.phone_service)
            val createdOn = itemView?.findViewById<TextView>(R.id.open_request_created)

        fun bind(requestService: RequestedService){
            pincode?.text = requestService.pincode
            consumerName?.text = requestService.consumerName
            lookingFor?.text = requestService.serviceTypeName
            phone?.text = requestService.mobileNumber
            createdOn?.text =requestService.createdOn

        }

    }


    interface ClickRequestService{
        fun clickOnItemRequestService(requestService: RequestedService)
    }
}