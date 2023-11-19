package com.example.marvelapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.marvelapp.R
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity
import com.example.marvelapp.databinding.FragmentCharacterListBinding
import com.example.marvelapp.ui.activity.DashboardActivity
import com.example.marvelapp.ui.adapter.CharacterAdapter
import com.example.marvelapp.ui.viewmodel.MarvelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    private val marvelVM by viewModels<MarvelViewModel>()
    private var _activity: DashboardActivity? = null

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(mutableListOf()) { message: String?, data: CharacterWithBookMarkEntity ->
            when (message) {
                "BOOK_MARK" -> marvelVM.toggleBookMark(data)
                "ROOT" -> _activity?.replaceFragment(
                    CharacterDetailFragment.newInstance(data.character?.id),
                    true
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activity = requireActivity() as DashboardActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCharacters.apply {
            setHasFixedSize(true)
            adapter = characterAdapter
        }
        binding.srlList.setOnRefreshListener {
            marvelVM.refresh()
            binding.srlList.isRefreshing = false
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem = menu.findItem(R.id.action_search)
                var searchView: SearchView? = null
                if (searchItem != null) {
                    searchView = searchItem.actionView as SearchView?
                }
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        characterAdapter.filter.filter(newText)
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        marvelVM.charactersLD?.observe(viewLifecycleOwner) { apiResponse ->
            characterAdapter.setData(apiResponse)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
