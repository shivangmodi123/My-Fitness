package com.myfitness.ui.booking_list.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myfitness.ui.booking_list.model.User
import com.myfitness.ui.retrofit.repository.UserRepository

class UserViewModel : ViewModel() {

    private val userRepository: UserRepository = UserRepository()
    val userList: MutableLiveData<List<User>?> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    val isLastPage: MutableLiveData<Boolean> = MutableLiveData()

    private var isLoadingData = false
    private var currentPage = 1
    private var totalPages = 1

    fun isLastPage(): Boolean {
        return currentPage >= totalPages
    }

    fun isLoading(): Boolean {
        return isLoadingData
    }

    fun getUsers(page: Int, results: Int) {
        isLoadingData = true

        userRepository.getUsers(page, results) { users, throwable ->
            if (users != null) {
                if (page == 1) {
                    userList.value = users
                } else {
                    val updatedList = userList.value?.toMutableList()
                    updatedList?.addAll(users)
                    userList.value = updatedList
                }

                val totalPages = calculateTotalPages(users.size)
                this.currentPage = page
                this.totalPages = totalPages
            } else if (throwable != null) {
                error.value = throwable
            }

            isLoadingData = false
        }
    }

    private fun calculateTotalPages(userCount: Int): Int {
        return (userCount + PAGE_SIZE - 1)
    }
}