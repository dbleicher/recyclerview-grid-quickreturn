recyclerview-grid-quickreturn
=============================

An example of implementing QuickReturn on a RecyclerView 
using a StaggeredGridLayoutManager inside a SwipeRefreshLayout.

This is an example project that shows one approach (probably not the best one!) of 
implementing the QuickReturn pattern with a RecyclerView that uses the
StaggeredGridLayoutManager.  For grins, the whole thing also supports Pull-to-Refresh
using a SwipeRefreshLayout.

The idea is that the QR view should not cover the top items in the RV.  This is accomplished with a hack that
adjusts the marginTop of any cells in the top row of the RV.  I'm sure there's a better way, so if you find it, please explain it to me!  :-)

Thanks!
