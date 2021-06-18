import java.awt.*;
import java.util.Arrays;

public class QuickSort
{
    public static void main(String[] args)
    {
        int[] arr = new int[]{5, 6, 1, 2, 3, 8, 9, 10};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // axis: 0 for x, 1 for y.
    public static void sort(Point[] arr, int axis)
    {
        int left = 0;
        int right = arr. length - 1;
        int pi = partition(arr, left, right, axis);
        sort(arr, left, pi - 1, axis);
        sort(arr, pi + 1, right, axis);
    }

    private static void sort(Point[] arr, int left, int right, int axis)
    {
        if(left < right)
        {
            int pi = partition(arr, left, right, axis);
            sort(arr, left, pi - 1, axis);
            sort(arr, pi + 1, right, axis);
        }
    }

    private static int partition(Point[] arr, int left, int right, int axis)
    {
        int pivot = axis == 0 ? arr[right].x : arr[right].y;
        int i = left - 1;
        for(int j = left; j < right; j++)
        {
            if((axis == 0 ? arr[j].x : arr[j].y) <= pivot)
            {
                i++;
                Point temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        Point temp = arr[right];
        arr[right] = arr[i + 1];
        arr[i + 1] = temp;
        return i + 1;
    }

    public static void sort(int[] arr)
    {
        int left = 0;
        int right = arr.length - 1;
        int pi = partition(arr, left, right);
        sort(arr, left, pi - 1);
        sort(arr, pi + 1, right);
    }

    private static void sort(int[] arr, int left, int right)
    {
        if(left < right)
        {
            int pi = partition(arr, left, right);
            sort(arr, left, pi - 1);
            sort(arr, pi + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right)
    {
        int pivot = arr[right];
        int i = left - 1;
        for(int j = left; j < right; j++)
        {
            if(arr[j] <= pivot)
            {
                i++;
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        int temp = arr[right];
        arr[right] = arr[i + 1];
        arr[i + 1] = temp;
        return i + 1;
    }
}
