package com.xently.persona.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.xently.persona.data.TaskResult
import com.xently.persona.data.errorMessage
import com.xently.persona.databinding.PeopleListFragmentBinding
import com.xently.xui.Fragment
import com.xently.xui.utils.ui.fragment.setupToolbar
import com.xently.xui.utils.ui.fragment.showSnackBar
import com.xently.xui.utils.ui.view.hideViews
import com.xently.xui.utils.ui.view.showViews
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PeopleListFragment : Fragment() {

    @Inject
    lateinit var personAdapter: PersonAdapter
    private val viewModel: PeopleListViewModel by viewModels()
    private var _binding: PeopleListFragmentBinding? = null
    private val binding: PeopleListFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PeopleListFragmentBinding.inflate(inflater, container, false).apply {
            setupToolbar(toolbar)
            persons.adapter = personAdapter
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showViews(binding.noRecords)
        viewModel.run {
            getPeople()
            getObservablePeople().observe(viewLifecycleOwner, Observer {
                if (it.isNullOrEmpty()) showViews(binding.noRecords)
                personAdapter.submitList(it)
            })
            peopleResults.observe(viewLifecycleOwner, Observer {
                binding.run {
                    when (it) {
                        is TaskResult.Success -> hideViews(progress)
                        is TaskResult.Error -> {
                            hideViews(progress)
                            it.errorMessage?.let { message ->
                                showSnackBar(message, Snackbar.LENGTH_LONG)
                            }
                        }
                        TaskResult -> showViews(progress)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}