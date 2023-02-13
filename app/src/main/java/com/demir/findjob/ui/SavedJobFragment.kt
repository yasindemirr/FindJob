package com.demir.findjob.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.findjob.R
import com.demir.findjob.adapters.FindSavedJobAdapter
import com.demir.findjob.databinding.FragmentSavedJobBinding
import com.demir.findjob.model.JobToSave
import com.demir.findjob.viewmodel.FindJobViewModel

class SavedJobFragment : Fragment(R.layout.fragment_saved_job), FindSavedJobAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FindJobViewModel
    private lateinit var jobAdapter: FindSavedJobAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedJobBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FindJobViewModel::class.java]
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        jobAdapter = FindSavedJobAdapter(this)

        binding.rvJobsSaved.apply {

            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(
                object : DividerItemDecoration(
                    activity, LinearLayout.VERTICAL) {})
            adapter = jobAdapter
        }

        viewModel.getAllJob().observe(viewLifecycleOwner, { jobToSave ->
            jobAdapter.differ.submitList(jobToSave)
            updateUI(jobToSave)
        })
    }

    private fun updateUI(list: List<JobToSave>) {

        if (list.isNotEmpty()) {
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else {
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }


    override fun onItemClick(job: JobToSave, view: View, position: Int) {
        deleteJob(job)
    }


    private fun deleteJob(job: JobToSave) {

        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Delete Job")
            setMessage("Are you sure you want to permanently delete this job?")
            setPositiveButton("DELETE") { _, _ ->
                viewModel.deleteJob(job)
                Toast.makeText(activity,"Job deleted", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}