package com.dicoding.surya.mademovieapp.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.ui.main.TabAdapter
import com.dicoding.surya.mademovieapp.ui.movie.MovieFragment
import com.dicoding.surya.mademovieapp.ui.tvshow.TVShowFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TabAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(MovieFragment(), getString(R.string.movie))
        adapter.addFragment(TVShowFragment(), getString(R.string.tv_show))

        view_pager_main.adapter = adapter
        tab_layout_main.setupWithViewPager(view_pager_main)

    }


}
