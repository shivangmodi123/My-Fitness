package com.myfitness.ui.booking_list.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfitness.databinding.FragmentBookingListBinding
import com.myfitness.ui.booking_list.adapter.BookingListItemAdapter
import com.myfitness.ui.booking_list.model.User
import com.myfitness.ui.booking_list.viewmodel.UserViewModel

class BookingListFragment : Fragment() {

    private var fragmentUserDetailBinding: FragmentBookingListBinding? = null
    private lateinit var userViewModel: UserViewModel
    private val userList = mutableListOf<User>()
    private lateinit var adapter: BookingListItemAdapter
    private var progressBar: ProgressBar? = null
    private lateinit var paginationListener: PaginationListener
    private val PAGE_SIZE = 10


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBookingListBinding.inflate(inflater, container, false)
        fragmentUserDetailBinding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = fragmentUserDetailBinding?.progressBar

        adapter = BookingListItemAdapter(userList)
        fragmentUserDetailBinding?.rvUserDetail?.layoutManager =
            LinearLayoutManager(requireContext())
        fragmentUserDetailBinding?.rvUserDetail?.adapter = adapter

        paginationListener = object :
            PaginationListener(fragmentUserDetailBinding?.rvUserDetail?.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                val nextPage = paginationListener.getCurrentPage() + 1
                userViewModel.getUsers(nextPage, PAGE_SIZE)
                paginationListener.setCurrentPage(nextPage)
            }

            override fun isLastPage(): Boolean {
                return userViewModel.isLastPage()
            }

            override fun isLoading(): Boolean {
                return userViewModel.isLoading()
            }
        }

        fragmentUserDetailBinding?.rvUserDetail?.addOnScrollListener(paginationListener)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        /** Call the API and update the userList **/
        getUserData()
    }

    abstract class PaginationListener(private val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {

        private var isLoading = false
        private var isLastPage = false
        private var currentPage = 1

        abstract fun loadMoreItems()

        abstract fun isLastPage(): Boolean

        abstract fun isLoading(): Boolean

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    loadMoreItems()
                }
            }
        }

        fun setCurrentPage(page: Int) {
            currentPage = page
        }

        fun getCurrentPage(): Int {
            return currentPage
        }

        fun setLoading(isLoading: Boolean) {
            this.isLoading = isLoading
        }

        fun setLastPage(isLastPage: Boolean) {
            this.isLastPage = isLastPage
        }
    }


    /** Make the API call and update the userList
        For example, you can use the UserViewModel to fetch data
        and update the userList in the observer
     **/

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData() {
        progressBar?.visibility = View.VISIBLE

        userViewModel.userList.observe(viewLifecycleOwner) { users ->
            userList.clear()
            userList.addAll(users ?: emptyList())
            adapter.notifyDataSetChanged()
            paginationListener.setLoading(false)
            progressBar?.visibility = View.GONE
        }

        userViewModel.error.observe(viewLifecycleOwner) { throwable ->
            // Handle error
            paginationListener.setLoading(false)
            progressBar?.visibility = View.GONE
        }

        val page = paginationListener.getCurrentPage()
        userViewModel.getUsers(page, PAGE_SIZE)
    }


    override fun onDestroy() {
        super.onDestroy()
        fragmentUserDetailBinding = null
        progressBar = null
    }
}