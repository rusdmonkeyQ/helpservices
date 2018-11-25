package raj.helpservice.android.helpservice.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.data.Profession

class ProfessionAdapter(private val proffesions:ArrayList<Profession>,private val onClick:ClickOnProfession) : RecyclerView.Adapter<ProfessionAdapter.ProfessionHolder>() {

    private var choosenPosition:Int? = null

    interface ClickOnProfession{
        fun clickOnProfession(profession: Profession)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProfessionHolder {
        val view:View = LayoutInflater.from(p0.context).inflate(R.layout.profession_item,p0,false)
        return ProfessionHolder(view)
    }

    override fun getItemCount(): Int {
        return proffesions.size
    }

    override fun onBindViewHolder(p0: ProfessionHolder, p1: Int) {
        p0.bindProfession(proffesions[p1],p1)

    }

   inner class ProfessionHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private var prof:Profession? = null
        private var v:View? = itemView
        private var textProffession:TextView? = v?.findViewById(R.id.text_profession)
        private var imageProfession:ImageView? = v?.findViewById(R.id.image_proffesion)
        init {
         v?.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onClick.clickOnProfession(profession = proffesions[adapterPosition])
            choosenPosition = adapterPosition
            notifyDataSetChanged()
        }


        fun bindProfession(profession: Profession,position:Int){
            textProffession?.text = profession.serviceName
            prof = profession
            if (position == choosenPosition) {
                when(profession.serviceTypeID){
                    "1"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_plumbing_active))
                    "2"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_electrical_active))
                    "3"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_painting_active))
                    "4"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_carpenter_active))
                    "5"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_mason_active))
                }
            }
            else {
                when(profession.serviceTypeID){
                    "1"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_plumbing))
                    "2"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_electrical))
                    "3"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_painting))
                    "4"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_carpenter))
                    "5"->imageProfession?.setImageDrawable(v?.context?.resources?.getDrawable(R.drawable.ic_mason))
                }
            }


        }

    }
}