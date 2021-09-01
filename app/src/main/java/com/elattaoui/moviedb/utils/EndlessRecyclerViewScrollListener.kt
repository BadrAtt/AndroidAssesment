package com.elattaoui.moviedb.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * improved version of:
 * https://gist.github.com/nesquena/d09dc68ff07e845cc622
 */
abstract class EndlessRecyclerViewScrollListener(
    private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 2

    // The current offset index of data you have loaded
    private var currentPage = 1

    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true

    // Sets the starting page index
    private var startingPageIndex = 1


    init {
        if (layoutManager is GridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        var lastVisibleItemPosition = 0
        val totalItemCount: Int = layoutManager.itemCount
        if (totalItemCount >= visibleThreshold) {
            if (layoutManager is GridLayoutManager) {
                lastVisibleItemPosition =
                    layoutManager.findLastVisibleItemPosition()
            }

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                currentPage = startingPageIndex
                previousTotalItemCount = totalItemCount
                if (totalItemCount == 0) {
                    loading = true
                }
            }
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            // threshold should reflect how many total columns there are too

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            // threshold should reflect how many total columns there are too
            if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                currentPage++
                onLoadMore(currentPage)
                loading = true
            }
        }
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int)
}
