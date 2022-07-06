package com.halasteknoloji.meckurbanyonetim.fragment.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halasteknoloji.meckurbanyonetim.models.Volunteer
import com.halasteknoloji.meckurbanyonetim.databinding.LayoutListItemBinding

class VolunteerListAdapter : ListAdapter<Volunteer, VolunteerListAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var onDeleteClickListener: ((ID: String?) -> Unit)? = null

    fun setDeleteClickListener(onDeleteClickListener: ((ID: String?) -> Unit)) {
        this.onDeleteClickListener = onDeleteClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ListViewHolder(private val itemBinding: LayoutListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bindView(Volunteer: Volunteer?) {
            itemBinding.apply {
                txtListFullName.text = "${Volunteer?.Name} ${Volunteer?.SurName}"
                txtListTc.text = "${Volunteer?.TCID}"
                txtListCity.text = "${Volunteer?.City}"
                txtListCount.text = "${Volunteer?.PackageCount}"


                if (Volunteer?.State == false) {
                    txtListFullName.alpha = 0.2F
                    txtListTc.alpha = 0.2F
                    txtListCity.alpha = 0.2F
                    txtListCount.alpha = 0.2F
                    imgListDelete.alpha = 0.2F
                    txtListTcTitle.alpha = 0.2F
                    txtListCountTxt.alpha = 0.2F
                } else {

                    txtListFullName.alpha = 1F
                    txtListTc.alpha = 1F
                    txtListCity.alpha = 1F
                    txtListCount.alpha = 1F
                    imgListDelete.alpha = 1F
                    txtListTcTitle.alpha = 1F
                    txtListCountTxt.alpha = 1F
                    imgListDelete.setOnClickListener {
                        onDeleteClickListener?.invoke(Volunteer?.ID)
                    }
                }

            }
        }

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Volunteer> =
            object : DiffUtil.ItemCallback<Volunteer>() {
                override fun areItemsTheSame(
                    oldVolunteer: Volunteer, newVolunteer: Volunteer
                ): Boolean {
                    return oldVolunteer.GonulluID.equals(newVolunteer.GonulluID)
                }

                override fun areContentsTheSame(
                    oldVolunteer: Volunteer, newVolunteer: Volunteer
                ): Boolean {
                    return oldVolunteer.GonulluID.equals(newVolunteer.GonulluID)
                }

            }
    }
}