package com.demir.findjob.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import com.demir.findjob.R
import com.demir.findjob.databinding.FragmentJobDetailBinding
import com.demir.findjob.model.Job
import com.demir.findjob.model.JobToSave
import com.demir.findjob.viewmodel.FindJobViewModel
import com.google.android.material.snackbar.Snackbar

class JobDetailFragment : Fragment(R.layout.fragment_job_detail) {

    private var _binding: FragmentJobDetailBinding? = null
    private val binding get() = _binding!!
    private val args: JobDetailFragmentArgs by navArgs()
    private lateinit var currentJob: Job
    private lateinit var viewModel: FindJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJobDetailBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FindJobViewModel::class.java]
        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener {
            addJobToFavorite(view)
        }

    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
           // setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }

    private fun addJobToFavorite(view: View) {
        val job = JobToSave(
            0,
            currentJob.candidateRequiredLocation, currentJob.category,
            currentJob.companyLogoUrl, currentJob.companyName,
            currentJob.description, currentJob.id, currentJob.jobType,
            currentJob.publicationDate, currentJob.salary, currentJob.title, currentJob.url
        )

        viewModel.insertJob(job)
        Snackbar.make(view, "Job saved successfully", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}