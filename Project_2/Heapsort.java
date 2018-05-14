import java.lang.management.*;   // NOTE

public class Heapsort {

    public static void main (String[] args) {
	
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();   // NOTE
		
		int N = Integer.parseInt(args[0]);
		int branch = Integer.parseInt(args[1]);
		
		System.out.println ("Generating " + N + " random numbers");
		int[] a = new int[N];
		for (int i=0; i<N; ++i) 
			a[i] = (int)(Math.random()*(1<<30));

		// To test a particular sorting algorithm, I make a copy of the original
		// array, and then record the current "user time".  After running the
		// sort, I compute the elapsed time by taking the difference between the
		// time and the time from before the sort.

		int[] arr1 = new int[N];
		for (int i=0; i<N; ++i) 
			arr1[i] = a[i];
		
		long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
		
		insertionSort(arr1);
		System.out.printf ("Insertion sort took %f seconds.\n",     // NOTE
				   (bean.getCurrentThreadUserTime()-t) / 1e9);
		
		int[] arr2 = new int[N];
		for (int i=0; i<N; ++i) 
			arr2[i] = a[i];
		
		t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
		
		selectionSort(arr2);
		System.out.printf ("Selection sort took %f seconds.\n",     // NOTE
				   (bean.getCurrentThreadUserTime()-t) / 1e9);

		int[] arr3 = new int[N];
		for (int i=0; i<N; ++i) 
			arr3[i] = a[i];
		
		t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
		
		heapsort(arr3, branch);
		System.out.printf ("Heapsort took %f seconds.\n",     // NOTE
				   (bean.getCurrentThreadUserTime()-t) / 1e9);
		
		check(arr1, arr2);
		check(arr2, arr3);
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

		print(a);
    }

    public static void selectionSort(int[] a) {
    	for(int i = 0; i < a.length; i++) {
	    	int smallest = a[i];
	    	int index = i;
	    	for(int p = i+1; p < a.length; p++) {
				if (a[p] < smallest) {
		    		smallest = a[p];
		    		index = p;
				}
	    	}

	    	int temp = a[i];
	    	a[i] = smallest;
	    	a[index] = temp;
		}
    }

    public static void swap(int[] a, int i, int j)
    {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
    }

    public static void heapify(int[] heap, int index, int size, int branch)
    {
    	//Formula for finding leftChild depends on branch
		int leftChild = index * branch + 1;

		// check if there are any children
		if (leftChild < size)
		{
			// get index of largest child -- uses a FOR loop instead of simply checking the next index
			int largest = leftChild;
			for (int i = 1; i < branch; i++) {
				if (leftChild + i < size && heap[leftChild + i] > heap[largest])
					largest = leftChild + i;
			}

			// check if value at index is < largest child
			if (heap[index] < heap[largest])
			{
				// swap down and continue at the location we swapped to
				swap(heap, index, largest);
				heapify(heap, largest, size, branch);
			}
		}
    }

    //Unchanged from given buildHeap
    public static void buildHeap(int[] heap, int branch)
    {
		for (int i = heap.length / 2 - 1; i >= 0; i--)
		{
			heapify(heap, i, heap.length, branch);
		}
    }

    //Unchanged from given heapsort
    public static void heapsort(int[] heap, int branch)
    {
    	buildHeap(heap, branch);
    	for (int i = heap.length-1; i > 0; i--) {
    		swap(heap, 0, i);
    		heapify(heap, 0, i, branch);
    	}
    }

}

