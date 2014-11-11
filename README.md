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

License
========

```
Copyright 2014 David Bleicher

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```