package com.halasteknoloji.meckurbanyonetim.fragment.List

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.halasteknoloji.meckurbanyonetim.models.ResponseStatus
import com.halasteknoloji.meckurbanyonetim.databinding.FragmentListBinding
import com.halasteknoloji.meckurbanyonetim.fragment.components.DeleteConfirmDialog

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null


    private val listViewModel: ListViewModel by viewModels()
    private var volunteerId: String? = null
    private val volunteerListAdapter = VolunteerListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater, container, false)

        volunteerId = arguments?.getString("VolunteerID")

        binding?.rcList?.adapter = volunteerListAdapter

        volunteerListAdapter.setDeleteClickListener { id ->

            DeleteConfirmDialog(onSubmitClickListener = {
                removeItem(id)
                it?.dismiss()
            }, onCancelClickListener = {
                it?.dismiss()
            }).show(childFragmentManager, DeleteConfirmDialog::class.java.name)

        }

        binding?.imgListClose?.setOnClickListener {
            findNavController().popBackStack()
        }

        getList()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        listViewModel.listData.observe(viewLifecycleOwner) {
            volunteerListAdapter.submitList(it)
            volunteerListAdapter.notifyItemRangeChanged(volunteerListAdapter.itemCount, it.size)
        }
    }

    private fun removeItem(ID: String?) {
        listViewModel.removeDistribution(ID = ID, volunteerId).observe(viewLifecycleOwner) {
            it?.let { globalResponse ->
                when (globalResponse.status) {

                    ResponseStatus.SUCCESS -> {
                        globalResponse.data?.let {

                            if (!it.Message.isNullOrEmpty() && it.Message.equals("OK")) {

                                if (ID != null) {
                                    listViewModel.removeData(ID)
                                    volunteerListAdapter.notifyDataSetChanged()
                                }

                            }

                        }
                    }

                    ResponseStatus.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "Silme işleme başarısız",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getList() {
        listViewModel.getList(volunteerId).observe(viewLifecycleOwner) {
            it?.let { globalResponse ->
                when (globalResponse.status) {

                    ResponseStatus.SUCCESS -> {
                        globalResponse.list?.let {

                            if (it != null && it.isNotEmpty()) {

                                listViewModel.addData(it.reversed().toMutableList())

                            }

                        }
                    }

                    ResponseStatus.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "Kullanıcı Bilgileri Geçersiz",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}