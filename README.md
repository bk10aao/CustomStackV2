# Custom Stack

Implementation of a Java Stack backed by a singly linked list.

## Time Complexity

| Method                       |        V1        |                  V2                  |      JDK      | Winner  |
|:-----------------------------|:----------------:|:------------------------------------:|:-------------:|:-------:|
| **CustomStack()**            |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **CustomStack(int)**         |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **CustomStack(Collection)**  |      $O(m)$      |                $O(1)$                |    $O(m)$     |   V2    |
| **add(E)**                   | $O(1)$ amortized |                $O(1)$                |    $O(1)$     |   Tie   |
| **add(int, E)**              |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **addAll(Collection)**       |      $O(m)$      |                $O(m)$                |    $O(m)$     |   Tie   |
| **addAll(int, Collection)**  |    $O(n + m)$    |              $O(n + m)$              |  $O(n + m)$   |   Tie   |
| **capacity()**               |      $O(1)$      |    N/A *(No pre-allocated array)*    |    $O(1)$     | V1, JDK |
| **clear()**                  |      $O(n)$      |                $O(1)$                |    $O(n)$     |   V2    |
| **clone()**                  |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **contains(E)**              |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **containsAll(Collection)**  |    $O(n + m)$    |              $O(n + m)$              |  $O(n + m)$   |   Tie   |
| **ensureCapacity(int)**      |      $O(n)$      | N/A *(Nodes allocated individually)* |    $O(n)$     | V1, JDK |
| **empty()**                  |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **equals(Object)**           |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **get(int)**                 |      $O(1)$      |                $O(n)$                |    $O(1)$     | V1, JDK |
| **hashCode()**               |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **indexOf(Object)**          |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **indexOf(Object, int)**     |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **isEmpty()**                |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **iterator()**               |      $O(n)$      |                $O(n)$                |    $O(1)$     |   JDK   |
| **lastIndexOf(Object)**      |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **lastIndexOf(Object, int)** |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **listIterator()**           |      $O(n)$      |                $O(n)$                |    $O(1)$     |   JDK   |
| **listIterator(int)**        |      $O(n)$      |                $O(n)$                |    $O(1)$     |   JDK   |
| **peek()**                   |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **pop()**                    |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **push(E)**                  |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **remove(int)**              |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **remove(Object)**           |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **removeAll(Collection)**    |    $O(n + m)$    |              $O(n + m)$              |  $O(n + m)$   |   Tie   |
| **retainAll(Collection)**    |    $O(n + m)$    |              $O(n + m)$              |  $O(n + m)$   |   Tie   |
| **search(Object)**           |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **set(int, E)**              |      $O(1)$      |                $O(n)$                |    $O(1)$     | V1, JDK |
| **size()**                   |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **sort(Comparator)**         |  $O(n \log n)$   |            $O(n \log n)$             | $O(n \log n)$ |   Tie   |
| **spliterator()**            |      $O(n)$      |                $O(n)$                |    $O(1)$     |   JDK   |
| **subList(int, int)**        |      $O(1)$      |                $O(1)$                |    $O(1)$     |   Tie   |
| **toArray()**                |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **toArray(T[])**             |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **toString()**               |      $O(n)$      |                $O(n)$                |    $O(n)$     |   Tie   |
| **trimToSize()**             |      $O(n)$      | N/A *(No unused trailing capacity)*  |    $O(n)$     | V1, JDK |

## Space Complexity

| Method                       |          V1           |                  V2                  |          JDK          | Winner  |
|:-----------------------------|:---------------------:|:------------------------------------:|:---------------------:|:-------:|
| **CustomStack()**            |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **CustomStack(int)**         |        $O(n)$         |                $O(1)$                |        $O(n)$         |   Tie   |
| **CustomStack(Collection)**  |        $O(n)$         |                $O(n)$                |        $O(n)$         |   Tie   |
| **add(E)**                   |   $O(1)$ amortized    |                $O(1)$                |   $O(1)$ amortized    |   Tie   |
| **add(int, E)**              |   $O(1)$ auxiliary    |                $O(n)$                |   $O(1)$ auxiliary    |   Tie   |
| **addAll(Collection)**       |        $O(m)$         |                $O(m)$                |        $O(m)$         |   Tie   |
| **addAll(int, Collection)**  |   $O(m)$ auxiliary    |              $O(n + m)$              |   $O(m)$ auxiliary    |   Tie   |
| **capacity()**               |        $O(1)$         |    N/A *(No pre-allocated array)*    |        $O(1)$         | V1, JDK |
| **clear()**                  |        $O(1)$         |                $O(1)$                |        $O(1)$         |   V2    |
| **clone()**                  |        $O(n)$         |                $O(n)$                |        $O(n)$         |   Tie   |
| **contains(E)**              |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **containsAll(Collection)**  |        $O(m)$         |                $O(m)$                |        $O(m)$         |   Tie   |
| **ensureCapacity(int)**      |        $O(n)$         | N/A *(Nodes allocated individually)* |        $O(n)$         | V1, JDK |
| **empty() / isEmpty()**      |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **equals(Object)**           |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **get(int)**                 |        $O(1)$         |                $O(n)$                |        $O(1)$         | V1, JDK |
| **hashCode()**               |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **indexOf(Object)**          |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **indexOf(Object, int)**     |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **iterator()**               |        $O(n)$         |                $O(n)$                |        $O(1)$         |   JDK   |
| **lastIndexOf(Object)**      |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **lastIndexOf(Object, int)** |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **listIterator()**           |        $O(n)$         |                $O(n)$                |        $O(1)$         |   JDK   |
| **listIterator(int)**        |        $O(n)$         |                $O(n)$                |        $O(1)$         |   JDK   |
| **peek()**                   |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **pop()**                    |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **push(E)**                  |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **remove(int)**              |        $O(1)$         |                $O(n)$                |        $O(1)$         |   Tie   |
| **remove(Object)**           |        $O(1)$         |                $O(n)$                |        $O(1)$         |   Tie   |
| **removeAll(Collection)**    |        $O(m)$         |                $O(m)$                |        $O(m)$         |   Tie   |
| **retainAll(Collection)**    |        $O(m)$         |                $O(m)$                |        $O(m)$         |   Tie   |
| **search(Object)**           |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **set(int, E)**              |        $O(1)$         |                $O(n)$                |        $O(1)$         | V1, JDK |
| **size()**                   |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **sort(Comparator)**         | $O(\log n)$ to $O(n)$ |                $O(n)$                | $O(\log n)$ to $O(n)$ |   Tie   |
| **spliterator()**            |        $O(n)$         |                $O(n)$                |        $O(1)$         |   JDK   |
| **subList(int, int)**        |        $O(1)$         |                $O(1)$                |        $O(1)$         |   Tie   |
| **toArray()**                |        $O(n)$         |                $O(n)$                |        $O(n)$         |   Tie   |
| **toArray(T[])**             |        $O(n)$         |                $O(n)$                |        $O(n)$         |   Tie   |
| **toString()**               |        $O(n)$         |                $O(n)$                |        $O(n)$         |   Tie   |
| **trimToSize()**             |        $O(n)$         | N/A *(No unused trailing capacity)*  |        $O(n)$         | V1, JDK |

Notes:
- n: The number of elements in the stack.
- m: Number of elements in the input collection.

# Performance Comparison

Geometric means (ns/op) calculated across all tested collection sizes, averaged over 10 benchmark runs. Margins under
1.10x are treated as noise-level ties because the source data lacks per-run variance metrics.

## V2 vs JDK

| Method                     | Custom V2 (ns)     | JDK Stack (ns) |     Margin     |          Winner          |             Note              |
|:---------------------------|:-------------------|:---------------|:--------------:|:------------------------:|:-----------------------------:|
| `Constructor()`            | 25.3               | 30.9           |     0.82x      | Statistically Equivalent |                               |
| `add(E)`                   | 109,141.8          | 106,259.1      |     1.03x      | Statistically Equivalent |                               |
| `add(int, E)`              | 42,850.4           | 2,074.9        |     20.65x     |           JDK            |                               |
| `addAll(Collection)`       | 97,643.6           | 62,356.5       |     1.57x      |           JDK            |                               |
| `addAll(int, Collection)`  | 52,825.3           | 3,831.9        |     13.79x     |           JDK            |                               |
| `clear()`                  | 26.0               | 13,201.5       |     0.00x      |            V2            |  Custom Significantly Faster  |
| `clone()`                  | $1.87 \times 10^9$ | 9,110.0        |  204,908.58x   |            V2            |   Critical Regression in V2   |
| `contains(Object)`         | 47,173.4           | 17,024.4       |     2.77x      |           JDK            |                               |
| `containsAll(Collection)`  | 204,392.7          | 53,761.1       |     3.80x      |           JDK            |                               |
| `empty()`                  | 29.1               | 30.5           |     0.96x      | Statistically Equivalent |                               |
| `equals(Object)`           | $1.83 \times 10^9$ | 70,108.6       |   26,047.57x   |            V2            |   Critical Regression in V2   |
| `get(int)`                 | 42,345.9           | 42.7           |    991.36x     |           JDK            |                               |
| `hashCode()`               | $1.81 \times 10^9$ | 48,068.1       |   37,682.21x   |            V2            |   Critical Regression in V2   |
| `indexOf(Object)`          | 78,954.7           | 50,992.5       |     1.55x      |           JDK            |                               |
| `indexOf(Object, int)`     | 79,114.4           | 51,312.6       |     1.54x      |           JDK            |                               |
| `isEmpty()`                | 24.3               | 30.7           |     0.79x      |            V2            |                               |
| `iterator()`               | $1.82 \times 10^9$ | 25,969.5       |   70,029.33x   |            V2            |   Critical Regression in V2   |
| `lastIndexOf(Object, int)` | 42,192.9           | 47,926.1       |     0.88x      | Statistically Equivalent |                               |
| `listIterator()`           | $1.81 \times 10^9$ | 27,443.8       |   65,866.66x   |            V2            |   Critical Regression in V2   |
| `listIterator(int)`        | $4.48 \times 10^8$ | 16,492.5       |   27,181.23x   |            V2            |   Critical Regression in V2   |
| `peek()`                   | 23.8               | 35.4           |     0.67x      |            V2            |                               |
| `pop()`                    | 24.5               | 35.1           |     0.70x      |            V2            |                               |
| `push(E)`                  | 109,684.0          | 106,703.3      |     1.03x      | Statistically Equivalent |                               |
| `remove(int)`              | 44,181.9           | 2,346.6        |     18.83x     |           JDK            |                               |
| `remove(Object)`           | 54,709.8           | 59,924.3       |     0.91x      | Statistically Equivalent |                               |
| `removeAll(Collection)`    | 254,225.0          | 257,245.3      |     0.99x      | Statistically Equivalent |                               |
| `retainAll(Collection)`    | 252,837.6          | 248,304.3      |     1.02x      | Statistically Equivalent |                               |
| `search(Object)`           | 45,054.1           | 10,196.0       |     4.42x      |           JDK            |                               |
| `set(int, E)`              | 44,858.6           | 48.8           |    918.36x     |           JDK            |                               |
| `size()`                   | 27.8               | 32.5           |     0.86x      | Statistically Equivalent |                               |
| `sort(Comparator)`         | $1.82 \times 10^9$ | 58,291.6       |   31,241.57x   |            V2            | **Critical Regression in V2** |
| `spliterator()`            | $1.80 \times 10^9$ | 34.3           | 52,420,732.00x |            V2            | **Critical Regression in V2** |
| `subList(int, int)`        | 26.5               | 60.9           |     0.44x      |            V2            |                               |
| `toArray()`                | $1.81 \times 10^9$ | 5,845.0        |  310,181.99x   |            V2            | **Critical Regression in V2** |
| `toArray(T[])`             | $1.80 \times 10^9$ | 24,687.9       |   72,920.01x   |            V2            | **Critical Regression in V2** |
| `toString()`               | $1.80 \times 10^9$ | 851,541.5      |   2,114.67x    |            V2            | **Critical Regression in V2** |

## V1 vs V2

| Method                     | V1 (ns)   | V2 (ns)            |     Margin     |          Winner          |            Note            |
|:---------------------------|:----------|:-------------------|:--------------:|:------------------------:|:--------------------------:|
| `Constructor()`            | 26.4      | 25.3               |     0.96x      | Statistically Equivalent |                            |
| `add(E)`                   | 104,179.2 | 109,141.8          |     1.05x      | Statistically Equivalent |                            |
| `add(int, E)`              | 2,079.2   | 42,850.4           |     20.61x     |            V1            |                            |
| `addAll(Collection)`       | 61,796.2  | 97,643.6           |     1.58x      |            V1            |                            |
| `addAll(int, Collection)`  | 3,741.0   | 52,825.3           |     14.12x     |            V1            |                            |
| `clear()`                  | 12,089.5  | 26.0               |     0.00x      |            V2            |  V2 Significantly Faster   |
| `clone()`                  | 8,618.8   | $1.87 \times 10^9$ |  216,587.11x   |            V1            | Critical Regression in V2  |
| `contains(Object)`         | 16,110.1  | 47,173.4           |     2.93x      |            V1            |                            |
| `containsAll(Collection)`  | 52,469.9  | 204,392.7          |     3.90x      |            V1            |                            |
| `empty()`                  | 26.3      | 29.1               |     1.11x      | Statistically Equivalent |                            |
| `equals(Object)`           | 69,105.9  | $1.83 \times 10^9$ |   26,425.52x   |        Custom V1         | Critical Regression in V2  |
| `get(int)`                 | 31.8      | 42,345.9           |   1,330.95x    |        Custom V1         | Critical Regression in V2  |
| `hashCode()`               | 47,316.1  | $1.81 \times 10^9$ |   38,281.08x   |        Custom V1         | Critical Regression in V2  |
| `indexOf(Object)`          | 50,364.1  | 78,954.7           |     1.57x      |        Custom V1         |                            |
| `indexOf(Object, int)`     | 50,635.5  | 79,114.4           |     1.56x      |        Custom V1         |                            |
| `isEmpty()`                | 26.3      | 24.3               |     0.92x      | Statistically Equivalent |                            |
| `iterator()`               | 24,869.6  | $1.82 \times 10^9$ |   73,126.48x   |        Custom V1         | Critical Regression in V2  |
| `lastIndexOf(Object, int)` | 47,218.7  | 42,192.9           |     0.89x      | Statistically Equivalent |                            |
| `listIterator()`           | 26,183.6  | $1.81 \times 10^9$ |   69,036.63x   |        Custom V1         | Critical Regression in V2  |
| `listIterator(int)`        | 16,133.1  | $4.48 \times 10^8$ |   27,786.68x   |        Custom V1         | Critical Regression in V2  |
| `peek()`                   | 26.7      | 23.8               |     0.89x      | Statistically Equivalent |                            |
| `pop()`                    | 26.8      | 24.5               |     0.91x      | Statistically Equivalent |                            |
| `push(E)`                  | 105,257.4 | 109,684.0          |     1.04x      | Statistically Equivalent |                            |
| `remove(int)`              | 2,106.8   | 44,181.9           |     20.97x     |        Custom V1         |  V1 Significantly faster   |
| `remove(Object)`           | 58,541.1  | 54,709.8           |     0.93x      | Statistically Equivalent |                            |
| `removeAll(Collection)`    | 254,554.2 | 254,225.0          |     1.00x      | Statistically Equivalent |                            |
| `retainAll(Collection)`    | 243,912.9 | 252,837.6          |     1.04x      | Statistically Equivalent |                            |
| `search(Object)`           | 9,935.7   | 45,054.1           |     4.53x      |        Custom V1         |                            |
| `set(int, E)`              | 34.2      | 44,858.6           |   1,313.08x    |        Custom V1         | Critical Regression in V2* |
| `size()`                   | 26.5      | 27.8               |     1.05x      | Statistically Equivalent |                            |
| `sort(Comparator)`         | 56,875.5  | $1.82 \times 10^9$ |   32,019.43x   |        Custom V1         | Critical Regression in V2* |
| `spliterator()`            | 27.1      | $1.80 \times 10^9$ | 66,491,198.94x |        Custom V1         | Critical Regression in V2* |
| `subList(int, int)`        | 27.5      | 26.5               |     0.97x      | Statistically Equivalent |                            |
| `toArray()`                | 5,911.7   | $1.81 \times 10^9$ |  306,682.10x   |        Custom V1         | Critical Regression in V2  |
| `toArray(T[])`             | 24,512.8  | $1.80 \times 10^9$ |   73,440.75x   |        Custom V1         | Critical Regression in V2  |
| `toString()`               | 866,374.5 | $1.80 \times 10^9$ |   2,078.47x    |        Custom V1         | Critical Regression in V2  |

# Performance Charts

#### Note: The following performance charts are designed to be viewed in dark mode.

# Heat Maps

## V2 vs JDK

![heatmap.png](PerformanceCharts/V2_JDK/heatmap.png)

## V1 vs V2

![heatmap.png](PerformanceCharts/V1_V2/heatmap.png)

## V1 vs JDK

![heatmap.png](PerformanceCharts/V1_JDK/heatmap.png)

## V1 vs V2 vs JDK

![plot_Constructor__.png](PerformanceCharts/V1_V2_JDK/plot_Constructor__.png)
![plot_add_E_.png](PerformanceCharts/V1_V2_JDK/plot_add_E_.png)
![plot_add_int__E__.png](PerformanceCharts/V1_V2_JDK/plot_add_int__E_.png)
![plot_addAll_Collection_.png](PerformanceCharts/V1_V2_JDK/plot_addAll_Collection_.png)
![plot_addAll_int__Collection_.png](PerformanceCharts/V1_V2_JDK/plot_addAll_int__Collection_.png)
![plot_clear__.png](PerformanceCharts/V1_V2_JDK/plot_clear__.png)
![plot_clone__.png](PerformanceCharts/V1_V2_JDK/plot_clone__.png)
![plot_contains_Object_.png](PerformanceCharts/V1_V2_JDK/plot_contains_Object_.png)
![plot_containsAll_Collection_.png](PerformanceCharts/V1_V2_JDK/plot_containsAll_Collection_.png)
![plot_empty__.png](PerformanceCharts/V1_V2_JDK/plot_empty__.png)
![plot_equals_Object_.png](PerformanceCharts/V1_V2_JDK/plot_equals_Object_.png)
![plot_get_int_.png](PerformanceCharts/V1_V2_JDK/plot_get_int_.png)
![plot_hashCode_.png](PerformanceCharts/V1_V2_JDK/plot_hashCode__.png)
![plot_indexOf_Object_.png](PerformanceCharts/V1_V2_JDK/plot_indexOf_Object_.png)
![plot_indexOf_Object__int_.png](PerformanceCharts/V1_V2_JDK/plot_indexOf_Object__int_.png)
![plot_isEmpty__.png](PerformanceCharts/V1_V2_JDK/plot_isEmpty__.png)
![plot_iterator__.png](PerformanceCharts/V1_V2_JDK/plot_iterator__.png)
![plot_lastIndexOf_Object__int_.png](PerformanceCharts/V1_V2_JDK/plot_lastIndexOf_Object__int_.png)
![plot_listIterator__.png](PerformanceCharts/V1_V2_JDK/plot_listIterator__.png)
![plot_listIterator_int_.png](PerformanceCharts/V1_V2_JDK/plot_listIterator_int_.png)
![plot_peek__.png](PerformanceCharts/V1_V2_JDK/plot_peek__.png)
![plot_pop__.png](PerformanceCharts/V1_V2_JDK/plot_pop__.png)
![plot_push_E_.png](PerformanceCharts/V1_V2_JDK/plot_push_E_.png)
![plot_remove_int_.png](PerformanceCharts/V1_V2_JDK/plot_remove_int_.png)
![plot_remove_Object_.png](PerformanceCharts/V1_V2_JDK/plot_remove_Object_.png)
![plot_removeAll_Collection_.png](PerformanceCharts/V1_V2_JDK/plot_removeAll_Collection_.png)
![plot_retainAll_Collection_.png](PerformanceCharts/V1_V2_JDK/plot_retainAll_Collection_.png)
![plot_search_Object_.png](PerformanceCharts/V1_V2_JDK/plot_search_Object_.png)
![plot_set_int__E_.png](PerformanceCharts/V1_V2_JDK/plot_set_int__E_.png)
![plot_size__.png](PerformanceCharts/V1_V2_JDK/plot_size__.png)
![plot_sort_Comparator_.png](PerformanceCharts/V1_V2_JDK/plot_sort_Comparator_.png)
![plot_spliterator__.png](PerformanceCharts/V1_V2_JDK/plot_spliterator__.png)
![plot_subList_int__int_.png](PerformanceCharts/V1_V2_JDK/plot_subList_int__int_.png)
![plot_toArray__.png](PerformanceCharts/V1_V2_JDK/plot_toArray__.png)
![plot_toArray_T__.png](PerformanceCharts/V1_V2_JDK/plot_toArray_T___.png)
![plot_toString__.png](PerformanceCharts/V1_V2_JDK/plot_toString__.png)

## V2 vs JDK

![plot_Constructor__.png](PerformanceCharts/V2_JDK/plot_Constructor__.png)
![plot_add_E_.png](PerformanceCharts/V2_JDK/plot_add_E_.png)
![plot_add_int__E__.png](PerformanceCharts/V2_JDK/plot_add_int__E_.png)
![plot_addAll_Collection_.png](PerformanceCharts/V2_JDK/plot_addAll_Collection_.png)
![plot_addAll_int__Collection_.png](PerformanceCharts/V2_JDK/plot_addAll_int__Collection_.png)
![plot_clear__.png](PerformanceCharts/V2_JDK/plot_clear__.png)
![plot_clone__.png](PerformanceCharts/V2_JDK/plot_clone__.png)
![plot_contains_Object_.png](PerformanceCharts/V2_JDK/plot_contains_Object_.png)
![plot_containsAll_Collection_.png](PerformanceCharts/V2_JDK/plot_containsAll_Collection_.png)
![plot_empty__.png](PerformanceCharts/V2_JDK/plot_empty__.png)
![plot_equals_Object_.png](PerformanceCharts/V2_JDK/plot_equals_Object_.png)
![plot_get_int_.png](PerformanceCharts/V2_JDK/plot_get_int_.png)
![plot_hashCode_.png](PerformanceCharts/V2_JDK/plot_hashCode__.png)
![plot_indexOf_Object_.png](PerformanceCharts/V2_JDK/plot_indexOf_Object_.png)
![plot_indexOf_Object__int_.png](PerformanceCharts/V2_JDK/plot_indexOf_Object__int_.png)
![plot_isEmpty__.png](PerformanceCharts/V2_JDK/plot_isEmpty__.png)
![plot_iterator__.png](PerformanceCharts/V2_JDK/plot_iterator__.png)
![plot_lastIndexOf_Object__int_.png](PerformanceCharts/V2_JDK/plot_lastIndexOf_Object__int_.png)
![plot_listIterator__.png](PerformanceCharts/V2_JDK/plot_listIterator__.png)
![plot_listIterator_int_.png](PerformanceCharts/V2_JDK/plot_listIterator_int_.png)
![plot_peek__.png](PerformanceCharts/V2_JDK/plot_peek__.png)
![plot_pop__.png](PerformanceCharts/V2_JDK/plot_pop__.png)
![plot_push_E_.png](PerformanceCharts/V2_JDK/plot_push_E_.png)
![plot_remove_int_.png](PerformanceCharts/V2_JDK/plot_remove_int_.png)
![plot_remove_Object_.png](PerformanceCharts/V2_JDK/plot_remove_Object_.png)
![plot_removeAll_Collection_.png](PerformanceCharts/V2_JDK/plot_removeAll_Collection_.png)
![plot_retainAll_Collection_.png](PerformanceCharts/V2_JDK/plot_retainAll_Collection_.png)
![plot_search_Object_.png](PerformanceCharts/V2_JDK/plot_search_Object_.png)
![plot_set_int__E_.png](PerformanceCharts/V2_JDK/plot_set_int__E_.png)
![plot_size__.png](PerformanceCharts/V2_JDK/plot_size__.png)
![plot_sort_Comparator_.png](PerformanceCharts/V2_JDK/plot_sort_Comparator_.png)
![plot_spliterator__.png](PerformanceCharts/V2_JDK/plot_spliterator__.png)
![plot_subList_int__int_.png](PerformanceCharts/V2_JDK/plot_subList_int__int_.png)
![plot_toArray__.png](PerformanceCharts/V2_JDK/plot_toArray__.png)
![plot_toArray_T__.png](PerformanceCharts/V2_JDK/plot_toArray_T___.png)
![plot_toString__.png](PerformanceCharts/V2_JDK/plot_toString__.png)

## V1 vs V2

![plot_Constructor__.png](PerformanceCharts/V1_V2/plot_Constructor__.png)
![plot_add_E_.png](PerformanceCharts/V1_V2/plot_add_E_.png)
![plot_add_int__E__.png](PerformanceCharts/V1_V2/plot_add_int__E_.png)
![plot_addAll_Collection_.png](PerformanceCharts/V1_V2/plot_addAll_Collection_.png)
![plot_addAll_int__Collection_.png](PerformanceCharts/V1_V2/plot_addAll_int__Collection_.png)
![plot_clear__.png](PerformanceCharts/V1_V2/plot_clear__.png)
![plot_clone__.png](PerformanceCharts/V1_V2/plot_clone__.png)
![plot_contains_Object_.png](PerformanceCharts/V1_V2/plot_contains_Object_.png)
![plot_containsAll_Collection_.png](PerformanceCharts/V1_V2/plot_containsAll_Collection_.png)
![plot_empty__.png](PerformanceCharts/V1_V2/plot_empty__.png)
![plot_equals_Object_.png](PerformanceCharts/V1_V2/plot_equals_Object_.png)
![plot_get_int_.png](PerformanceCharts/V1_V2/plot_get_int_.png)
![plot_hashCode_.png](PerformanceCharts/V1_V2/plot_hashCode__.png)
![plot_indexOf_Object_.png](PerformanceCharts/V1_V2/plot_indexOf_Object_.png)
![plot_indexOf_Object__int_.png](PerformanceCharts/V1_V2/plot_indexOf_Object__int_.png)
![plot_isEmpty__.png](PerformanceCharts/V1_V2/plot_isEmpty__.png)
![plot_iterator__.png](PerformanceCharts/V1_V2/plot_iterator__.png)
![plot_lastIndexOf_Object__int_.png](PerformanceCharts/V1_V2/plot_lastIndexOf_Object__int_.png)
![plot_listIterator__.png](PerformanceCharts/V1_V2/plot_listIterator__.png)
![plot_listIterator_int_.png](PerformanceCharts/V1_V2/plot_listIterator_int_.png)
![plot_peek__.png](PerformanceCharts/V1_V2/plot_peek__.png)
![plot_pop__.png](PerformanceCharts/V1_V2/plot_pop__.png)
![plot_push_E_.png](PerformanceCharts/V1_V2/plot_push_E_.png)
![plot_remove_int_.png](PerformanceCharts/V1_V2/plot_remove_int_.png)
![plot_remove_Object_.png](PerformanceCharts/V1_V2/plot_remove_Object_.png)
![plot_removeAll_Collection_.png](PerformanceCharts/V1_V2/plot_removeAll_Collection_.png)
![plot_retainAll_Collection_.png](PerformanceCharts/V1_V2/plot_retainAll_Collection_.png)
![plot_search_Object_.png](PerformanceCharts/V1_V2/plot_search_Object_.png)
![plot_set_int__E_.png](PerformanceCharts/V1_V2/plot_set_int__E_.png)
![plot_size__.png](PerformanceCharts/V1_V2/plot_size__.png)
![plot_sort_Comparator_.png](PerformanceCharts/V1_V2/plot_sort_Comparator_.png)
![plot_spliterator__.png](PerformanceCharts/V1_V2/plot_spliterator__.png)
![plot_subList_int__int_.png](PerformanceCharts/V1_V2/plot_subList_int__int_.png)
![plot_toArray__.png](PerformanceCharts/V1_V2/plot_toArray__.png)
![plot_toArray_T__.png](PerformanceCharts/V1_V2/plot_toArray_T___.png)
![plot_toString__.png](PerformanceCharts/V1_V2/plot_toString__.png)

## V1 vs JDK

![plot_Constructor__.png](PerformanceCharts/V1_JDK/plot_Constructor__.png)
![plot_add_E_.png](PerformanceCharts/V1_JDK/plot_add_E_.png)
![plot_add_int__E__.png](PerformanceCharts/V1_JDK/plot_add_int__E_.png)
![plot_addAll_Collection_.png](PerformanceCharts/V1_JDK/plot_addAll_Collection_.png)
![plot_addAll_int__Collection_.png](PerformanceCharts/V1_JDK/plot_addAll_int__Collection_.png)
![plot_clear__.png](PerformanceCharts/V1_JDK/plot_clear__.png)
![plot_clone__.png](PerformanceCharts/V1_JDK/plot_clone__.png)
![plot_contains_Object_.png](PerformanceCharts/V1_JDK/plot_contains_Object_.png)
![plot_containsAll_Collection_.png](PerformanceCharts/V1_JDK/plot_containsAll_Collection_.png)
![plot_empty__.png](PerformanceCharts/V1_JDK/plot_empty__.png)
![plot_equals_Object_.png](PerformanceCharts/V1_JDK/plot_equals_Object_.png)
![plot_get_int_.png](PerformanceCharts/V1_JDK/plot_get_int_.png)
![plot_hashCode_.png](PerformanceCharts/V1_JDK/plot_hashCode__.png)
![plot_indexOf_Object_.png](PerformanceCharts/V1_JDK/plot_indexOf_Object_.png)
![plot_indexOf_Object__int_.png](PerformanceCharts/V1_JDK/plot_indexOf_Object__int_.png)
![plot_isEmpty__.png](PerformanceCharts/V1_JDK/plot_isEmpty__.png)
![plot_iterator__.png](PerformanceCharts/V1_JDK/plot_iterator__.png)
![plot_lastIndexOf_Object__int_.png](PerformanceCharts/V1_JDK/plot_lastIndexOf_Object__int_.png)
![plot_listIterator__.png](PerformanceCharts/V1_JDK/plot_listIterator__.png)
![plot_listIterator_int_.png](PerformanceCharts/V1_JDK/plot_listIterator_int_.png)
![plot_peek__.png](PerformanceCharts/V1_JDK/plot_peek__.png)
![plot_pop__.png](PerformanceCharts/V1_JDK/plot_pop__.png)
![plot_push_E_.png](PerformanceCharts/V1_JDK/plot_push_E_.png)
![plot_remove_int_.png](PerformanceCharts/V1_JDK/plot_remove_int_.png)
![plot_remove_Object_.png](PerformanceCharts/V1_JDK/plot_remove_Object_.png)
![plot_removeAll_Collection_.png](PerformanceCharts/V1_JDK/plot_removeAll_Collection_.png)
![plot_retainAll_Collection_.png](PerformanceCharts/V1_JDK/plot_retainAll_Collection_.png)
![plot_search_Object_.png](PerformanceCharts/V1_JDK/plot_search_Object_.png)
![plot_set_int__E_.png](PerformanceCharts/V1_JDK/plot_set_int__E_.png)
![plot_size__.png](PerformanceCharts/V1_JDK/plot_size__.png)
![plot_sort_Comparator_.png](PerformanceCharts/V1_JDK/plot_sort_Comparator_.png)
![plot_spliterator__.png](PerformanceCharts/V1_JDK/plot_spliterator__.png)
![plot_subList_int__int_.png](PerformanceCharts/V1_JDK/plot_subList_int__int_.png)
![plot_toArray__.png](PerformanceCharts/V1_JDK/plot_toArray__.png)
![plot_toArray_T__.png](PerformanceCharts/V1_JDK/plot_toArray_T___.png)
![plot_toString__.png](PerformanceCharts/V1_JDK/plot_toString__.png)