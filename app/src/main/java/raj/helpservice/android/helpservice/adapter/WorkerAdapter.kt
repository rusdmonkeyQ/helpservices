package raj.helpservice.android.helpservice.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.Worker

class WorkerAdapter(private val workers:ArrayList<Worker>, private val type:String, var clickOnItem: ClickOnItem) : RecyclerView.Adapter<WorkerAdapter.WorkerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_worker_item,parent,false)
        return WorkerHolder(view)
    }

    override fun getItemCount(): Int {
        return workers.size
    }

    override fun onBindViewHolder(holder: WorkerHolder, position: Int) {
        holder.bindUser(workers[position],type)
    }

    fun changeValues(worker: ArrayList<Worker>){
        workers.clear()
        workers.addAll(worker)
        notifyDataSetChanged()

    }

    inner class WorkerHolder(private val view:View) : RecyclerView.ViewHolder(view),View.OnClickListener{
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickOnItem.clickItem(workers[adapterPosition])
        }


        val textName = view.findViewById<TextView>(R.id.text_name)
        val textTypeService = view.findViewById<TextView>(R.id.text_type_service)
        val rateType = view.findViewById<TextView>(R.id.text_rate_service)
        val imageView:CircleImageView = view.findViewById(R.id.profile_image)


        fun bindUser(worker: Worker,type: String){
            textName.text = worker.shopName
            textTypeService.text = worker.offerDescription
            rateType.text = type +" Service"
            imageView.setImageResource(getDrawable(type))


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

    interface ClickOnItem{
        fun clickItem(worker:Worker)
    }

}