
public class SelectionSort {

    public static void sSort(int[] a) {

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

    public static void main(String[] args) {

	int[] a = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	SelectionSort.sSort(a);
	System.out.print("[");
	for (int i=0; i < a.length; i++)
	    System.out.print(a[i] + ", ");
	System.out.print("]");

    }
}
