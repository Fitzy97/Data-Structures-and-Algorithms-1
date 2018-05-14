import java.lang.management.*;   // NOTE

public class Sort {

    public static void main (String[] args) {

	ThreadMXBean bean = ManagementFactory.getThreadMXBean();   // NOTE

	int N = Integer.parseInt(args[0]);

	System.out.println ("Generating " + N + " random numbers");
	int[] a = new int[N];
	for (int i=0; i<N; ++i) 
		a[i] = (int)(Math.random()*(1<<30));

	// To test a particular sorting algorithm, I make a copy of the original
	// array, and then record the current "user time".  After running the
	// sort, I compute the elapsed time by taking the difference between the
	// time and the time from before the sort.

	//INSERTIONSORT DRIVER
	{
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
	    
	    //insertionSort(c);
	    System.out.printf ("Insertion sort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
	}
	    
	// Here is a section of code that you could use to start a mergesort.

	//MERGESORT DRIVER
	{
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) 
	    	c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
	    
	    mergeSort(c);

	    System.out.printf ("Mergesort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
    }

	//IN-PLACE MERGESORT DRIVER
	{
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) 
	    	c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
	    
	    inPlaceMergeSort(c);

	    System.out.printf ("In-Place Mergesort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
    }

    //ITERATIVE MERGESORT DRIVER
	{
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) 
	    	c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)

	    iterativeMergeSort(c);

	    System.out.printf ("Iterative Mergesort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
    }

    //QUICKSORT DRIVER
    {
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) 
	    	c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
	    
	    //System.out.println("Unsorted Array:");
	    //print(c);
	    quickSort(c);
	    //System.out.println("Sorted Array:");
	    //print(c);
	    System.out.printf ("Quicksort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
    }

    //RANDOMIZED QUICKSORT DRIVER
    {
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) 
	    	c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
	    
	    //System.out.println("Unsorted Array:");
	    //print(c);
	    randomizedQuickSort(c);
	    //System.out.println("Sorted Array:");
	    //print(c);
	    System.out.printf ("Randomized Quicksort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
    }
    
    //END QUICKSORT DRIVER
    {
	    int[] c = new int[N];
	    for (int i=0; i<N; ++i) 
	    	c[i] = a[i];
	    
	    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
	    
	    //System.out.println("Unsorted Array:");
	    //print(c);
	    //endQuickSort(c);
	    //System.out.println("Sorted Array:");
	    //print(c);
	    System.out.printf ("End Quicksort took %f seconds.\n",     // NOTE
			       (bean.getCurrentThreadUserTime()-t) / 1e9);
		}
    }

    /////////////////////////////////////////////////////////////////////////
    // This method prints the contents of an array.  You might use it during
    // debugging.
    /////////////////////////////////////////////////////////////////////////

    public static void print(int[] a) {
		for (int i=0; i<a.length; ++i)
		    System.out.println (a[i]);
		System.out.println();
    }

    /////////////////////////////////////////////////////////////////////////
    // This method compares the contents of two arrays.  You might use it
    // during debugging to compare the results of two different algorithms.
    /////////////////////////////////////////////////////////////////////////

    public static void check(int[] a, int[] b) {
	for (int i=0; i<a.length; ++i)
	    if (a[i] != b[i]) 
		throw new RuntimeException ("Error in sorting results");
    }

    /////////////////////////////////////////////////////////////////////////
    // Here's the insertion sort.
    /////////////////////////////////////////////////////////////////////////

    public static void insertionSort(int[] a) {

		int n = a.length;

		for (int i=1; i<n; ++i) {
		    
		    int t = a[i];
		    int j = i-1;
		    while (j >= 0 && t < a[j]) {
			a[j+1] = a[j];
			j--;
		    }
		    a[j+1] = t;
		}
    }

    /////////////////////////////////////////////////////////////////////////
    // Wrapper mergeSort method to accomodate driver code.
    /////////////////////////////////////////////////////////////////////////
	public static void mergeSort(int[] a) {
		mergeSort(a, 0, a.length-1);
	}

    /////////////////////////////////////////////////////////////////////////
    // Standard mergeSort, essentially the same implementation as the one from class.
    /////////////////////////////////////////////////////////////////////////
	public static void mergeSort(int[] a, int start, int end) {

		if (start < end) {
			int mid = (start + end)/2;

			mergeSort(a, start, mid);
			mergeSort(a, mid+1, end);

			merge(a, start, mid, end);
		}

	}

    /////////////////////////////////////////////////////////////////////////
    // Standard merge.  Creates two arrays to hold the left and right half of the original array,
    // then copies those two arrays into the original in sorted order.
    /////////////////////////////////////////////////////////////////////////
	public static void merge(int[] a, int start, int mid, int end) {

		int[] L = new int[mid + 1 - start];
    	for (int i = 0; i < L.length; i++)
   			L[i] = a[i + start];

   		int[] R = new int[end - mid];
   		for (int i = 0; i < R.length; i++)
   			R[i] = a[i + mid + 1];

   		int i = 0;
   		int j = 0;

		for (int c = start; c <= end; c++) {
			if (i >= L.length && j >= R.length)
				break;
			if (i >= L.length) {
				a[c] = R[j];
				j++;
			}
			else if (j >= R.length) {
				a[c] = L[i];
				i++;
			}
			else if (L[i] < R[j]) {
				a[c] = L[i];
				i++;
			}
			else {
				a[c] = R[j];
				j++;
			}
		}

	}

    /////////////////////////////////////////////////////////////////////////
    // Wrapper inPlaceMergeSort method to accomodate driver code.
    /////////////////////////////////////////////////////////////////////////
	public static void inPlaceMergeSort(int[] a) {
		inPlaceMergeSort(a, 0, a.length-1);
	}

    /////////////////////////////////////////////////////////////////////////
    // In-place mergeSort.
    /////////////////////////////////////////////////////////////////////////
    public static void inPlaceMergeSort(int[] a, int start, int end) {

		if (start < end) {
			int mid = (start + end)/2;

			inPlaceMergeSort(a, start, mid);
			inPlaceMergeSort(a, mid+1, end);

			inPlaceMerge(a, start, mid, end);
		}

    }

    /////////////////////////////////////////////////////////////////////////
    // In-place merge.  Uses only the existing array for space.
    /////////////////////////////////////////////////////////////////////////
    public static void inPlaceMerge(int[] a, int start, int mid, int end) {

		int i = start;
		int j = mid+1;

		// Compares the value in the first half of the array with the value in the second half.
		// If the first-half value is less, then simply move on to the next value.
		// If the first-half value is greater, move the second-half value into the first-half
		// value's spot, and shift everything else up.
   		while (i <= mid && j <= end) {
   			if (a[i] <= a[j])
   				i++;
   			else {
   				int temp = a[j];
   				for (int c = j; c > i; c--) {
   					a[c] = a[c-1];
   				}
   				a[i] = temp;
   				j++;
   				i++;
   				mid++;
   			}

   		}

    }

    /////////////////////////////////////////////////////////////////////////
    // Iterative mergeSort.  Creates a new array for every increase in blockSize, and copies the
    // original array into the new, empty one so that each pair of blocks is in order.
    //
    // NOTE: I couldn't get this to completely work -- it definitely works with any array with 
    // 2^n elements, where n >= 1, and also seems to work with some arrays that don't settle this
    // condition.  It's issue is that it cannot fully account for the elements that have not been 
    // included in a blockSize.
    /////////////////////////////////////////////////////////////////////////
    public static void iterativeMergeSort(int[] a) {

    	int blockSize = 1;

    	while (blockSize < a.length) {

    		// i equals the last index of that pair of blocks.  For instance if blockSize == 2, i
    		// starts out as 3, then becomes 7, then 11.
    		int i;
    		for (i = blockSize*2-1; i < a.length; i+=blockSize*2) {
    			// Merge the two blocks immiediately preceding i.
    			inPlaceMerge(a, i-(blockSize*2-1), (i-(blockSize*2-1)+i)/2, i);
    		}
    		if (i != a.length) { // If there are leftover elements in the array that did not get merged.
    			// Merge the leftover elements, that should be sorted from previous iterations.
    			inPlaceMerge(a, i+1, ((i+1) + a.length-1)/2, a.length-1);
    		}

    		blockSize*=2;
    	}
    	// Merge the leftover elements with the rest of the array.
    	inPlaceMerge(a, 0, blockSize/2-1, a.length-1);

    }

    /////////////////////////////////////////////////////////////////////////
    // Wrapper method for quickSort.
    /////////////////////////////////////////////////////////////////////////
    public static void quickSort(int[] a) {
    	quickSort(a, 0, a.length-1);
    }

    /////////////////////////////////////////////////////////////////////////
    // Standard quickSort.  Runs pretty much the same as the quickSort implemented in class.
    /////////////////////////////////////////////////////////////////////////
    public static void quickSort(int[] a, int start, int end) {

    	if (start < end) {

    		int p = partition(a, start, end);

    		quickSort(a, start, p-1);
    		quickSort(a, p+1, end);

    	}

    }

    /////////////////////////////////////////////////////////////////////////
    // Standard partition method, with pivot chosen as the first element in the array.
    /////////////////////////////////////////////////////////////////////////
    public static int partition(int[] a, int start, int end) {

    	int pivot = a[start];
    	int lastSmall = start;

    	for (int i = start+1; i <= end; i++) {
    		if (a[i] < pivot) {
    			swap(a, lastSmall+1, i);
    			lastSmall++;
    		}
    	}

    	swap(a, start, lastSmall);

    	return lastSmall;

    }

    public static void swap(int[] a, int p, int q) {
    	int temp = a[p];
    	a[p] = a[q];
    	a[q] = temp;
    }

    /////////////////////////////////////////////////////////////////////////
    // Wrapper method for randomizedQuickSort.
    /////////////////////////////////////////////////////////////////////////
    public static void randomizedQuickSort(int[] a) {
    	randomizedQuickSort(a, 0, a.length-1);
    }

    /////////////////////////////////////////////////////////////////////////
    // quickSort implemented with the pivot chosen as a random element in the array, then
    // swapped with the first element and quickSorted.  Reduces the runtime of a worst-case
    // scenario (when the array is already sorted).
    /////////////////////////////////////////////////////////////////////////
    public static void randomizedQuickSort(int[] a, int start, int end) {

    	if (start < end) {

    		int p = randomizedPartition(a, start, end);

    		randomizedQuickSort(a, start, p-1);
    		randomizedQuickSort(a, p+1, end);

    	}
    }

    public static int randomizedPartition(int[] a, int start, int end) {

		int randomIndex = (int)((Math.random() * (end-start)) + start);
    	int pivot = a[randomIndex];
    	swap(a, start, randomIndex);
    	int lastSmall = start;

    	for (int i = start+1; i <= end; i++) {
    		if (a[i] < pivot) {
    			swap(a, lastSmall+1, i);
    			lastSmall++;
    		}
    	}

    	swap(a, start, lastSmall);

    	return lastSmall;

    }

    /////////////////////////////////////////////////////////////////////////
    // Wrapper method for endQuickSort.
    /////////////////////////////////////////////////////////////////////////
    public static void endQuickSort(int[] a) {
    	endQuickSort(a, 0, a.length-1);
    }

    /////////////////////////////////////////////////////////////////////////
    // quickSort implemented with the pivot chosen as the last element in the array.
    /////////////////////////////////////////////////////////////////////////
    public static void endQuickSort(int[] a, int start, int end) {

    	if (start < end) {

    		int p = endPartition(a, start, end);

    		endQuickSort(a, start, p-1);
    		endQuickSort(a, p+1, end);

    	}

    }

    public static int endPartition(int[] a, int start, int end) {

    	int pivot = a[end];
    	int firstBig = end;

    	for (int i = end; i >= 0; i--) {
    		if (a[i] > pivot) {
    			swap(a, firstBig-1, i);
    			firstBig--;
    		}
    	}

    	swap(a, firstBig, end);

    	return firstBig;

    }

}

