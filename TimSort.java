import java.util.Arrays;

public class TimSort {
    private static final int MIN_MERGE = 32;

    public static void timSort(int[] arr) {
        int n = arr.length;
        int[] copyArr = Arrays.copyOf(arr, n);

        for (int i = 0; i < n; i += MIN_MERGE) {
            insertionSort(copyArr, i, Math.min(i + MIN_MERGE - 1, n - 1));
        }

        for (int size = MIN_MERGE; size < n; size *= 2) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                merge(copyArr, arr, left, mid, right);
            }
            int[] temp = copyArr;
            copyArr = arr;
            arr = temp;
        }
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int keyItem = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > keyItem) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = keyItem;
        }
    }

    private static void merge(int[] srcArr, int[] destArr,
                              int leftStart, int midPoint,
                              int rightEnd) {

        // Copy both halves into a temporary array
        System.arraycopy(srcArr, leftStart,
                         destArr, leftStart,
                         midPoint - leftStart + 1);

        System.arraycopy(srcArr, midPoint + 1,
                         destArr, midPoint + 1,
                         rightEnd - midPoint);

        // Merge the temporary arrays back into the original array
        int leftIndex = leftStart;
        int rightIndex = midPoint + 1;

        for (int i = leftStart; i <= rightEnd; i++) {
            if (leftIndex <= midPoint &&
                    (rightIndex > rightEnd || srcArr[leftIndex] <= srcArr[rightIndex])) {
                destArr[i] = srcArr[leftIndex];
                leftIndex++;
            } else {
                destArr[i] = srcArr[rightIndex];
                rightIndex++;
            }
        }
    }

    public static void main(String[] args) {
        // Example usage
        int[] arrToSort = {5,3,-2,-8,-6,-10};
        
        System.out.println("Array before sorting:");
        System.out.println(Arrays.toString(arrToSort));
        
        timSort(arrToSort);
        
        System.out.println("Array after sorting:");
        System.out.println(Arrays.toString(arrToSort));
    }
}
