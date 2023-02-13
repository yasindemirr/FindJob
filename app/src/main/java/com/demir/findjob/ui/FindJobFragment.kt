package com.demir.findjob.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demir.findjob.R
import com.demir.findjob.adapters.FindJobAdapter
import com.demir.findjob.databinding.FragmentFindJobBinding
import com.demir.findjob.utils.Constants
import com.demir.findjob.viewmodel.FindJobViewModel


class FindJobFragment : Fragment(R.layout.fragment_find_job),
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentFindJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var findJobViewModel: FindJobViewModel
    private lateinit var jobAdapter: FindJobAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private var page = 1
    private var limit = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFindJobBinding.inflate(
            inflater,
            container,
            false
        )

        swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            Color.GREEN, Color.RED,
            Color.BLUE, Color.CYAN
        )

        swipeLayout.post {
            swipeLayout.isRefreshing = true
            setUpRecyclerView()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findJobViewModel = ViewModelProvider(this)[FindJobViewModel::class.java]

        setUpRecyclerView()

        binding.swipeContainer.setOnRefreshListener {
            fetchingData()
        }
    }


    private fun setUpRecyclerView() {
        jobAdapter = FindJobAdapter()
        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(
                object : DividerItemDecoration(
                    activity, LinearLayout.VERTICAL
                ) {})
            adapter = jobAdapter
        }


        fetchingData()
    }


    private fun fetchingData() {
        activity?.let {
            if (Constants.isNetworkAvailable(requireActivity())) {

                findJobViewModel.findJobResult()
                    .observe(viewLifecycleOwner, { remoteJob ->
                        if (remoteJob != null) {
                            jobAdapter.differ.submitList(remoteJob.jobs)
                            swipeLayout.isRefreshing = false
                        }
                    })
            } else {
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
                swipeLayout.isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        setUpRecyclerView()
    }

}