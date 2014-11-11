recyclerview-grid-quickreturn
=============================

An example of implementing QuickReturn on a RecyclerView 
using a StaggeredGridLayoutManager inside a SwipeRefreshLayout.

This is an example project that shows one approach (probably not the best one!) of 
implementing the QuickReturn pattern with a RecyclerView that uses the
StaggeredGridLayoutManager.  The idea is that the QR view should not
cover the top items in the RV.  This is accomplished with a hack that
adjusts the marginTop of any cells in the top row of the RV.
