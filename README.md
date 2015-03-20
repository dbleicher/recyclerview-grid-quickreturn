#recyclerview-grid-quickreturn

An example of implementing QuickReturn on a RecyclerView 
using a StaggeredGridLayoutManager to display CardViews inside a SwipeRefreshLayout.

This is an example project that shows one approach (probably not the best one!) of 
implementing the QuickReturn pattern with a RecyclerView that uses the
StaggeredGridLayoutManager.  For grins, the whole thing also supports Pull-to-Refresh
using a SwipeRefreshLayout. By the way, the cells within the layout are CardViews, and use 
the `card_view:cardUseCompatPadding="true"` attribute to display properly on Lollipop.

The idea is that the QR view should not cover the top items in the RV.  This is now accomplished with a custom
(but trivial) ItemDecoration.  As a result, the adapter and layoutmanager are plain vanilla.  I'm sure there's
a better way, so if you find it, please explain it to me!  :-)

Here's what it looks like with expandable cells:  https://www.youtube.com/watch?v=ulf4v3Qzn4o

Here's an older animation:

![rsqr](https://cloud.githubusercontent.com/assets/3764409/4998140/88d948ee-69a3-11e4-95ba-076da0a6ad95.gif)

##Changes
1. (2015-1-2) Added TargetedSwipeRefreshLayout (TSRL) to permit multiple views within the SwipeRefreshLayout.
1. (2015-1-2) Removed topview detection from onScrollListener (no longer needed with TSRL).
2. (2014-11-14) Modified the spacing hack to use an ItemDecorator (cleaner approach).

##TODO
1. (2014-11-14) ~~It's been suggested that I look into using ItemDecoration instead of adjusting cell margins during onBind.~~
2. (2015-03-18) Fixed using recyclerview-v7 R22 ~~Need to address issue of inserting items at top of grid.~~


##License

```
Copyright 2014-2015 David Bleicher

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
