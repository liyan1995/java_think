package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * create by YanL on 2019/2/18
 */
public class Sort {
    private static final int SIZE = 10000;
    private static final int RANGE = 100000;
    private static final int[] a = new int[SIZE];
    private static Random random = new Random(47);

    static {
        for (int i = 0; i < SIZE; i++) {
            a[i] = random.nextInt(RANGE);
        }
    }

    /**
     * 冒泡排序---
     * O(N^2)
     *
     * @param source
     */
    private static void bubbleSort(int[] source) {
        System.out.println();
        System.out.println("================冒泡排序================");
        for (int i = 0; i < source.length - 1; i++) {
            for (int j = 0; j < source.length - i - 1; j++) {
                if (source[j] > source[j + 1]) {
                    int temp = source[j];
                    source[j] = source[j + 1];
                    source[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 插入排序---
     * loop-1 缓存位置p的值
     * loop-2 倒序比较位置p和p之前排序完成的数组[0-p],如果>source[p]交换位置,<source[p] break;
     * <p>
     * O(N^2)复杂度
     *
     * @param source 源数组
     */
    private static void insertionSort(int[] source) {
        System.out.println();
        System.out.println("================插入排序================");
        // 遍历数组
        for (int p = 1; p < source.length; p++) {
            int j;
            int temp = source[p];
            // 倒序遍历，如果到位置0或者找到插入位置
            for (j = p; j > 0 && temp < source[j - 1]; j--) {
                // 后移
                source[j] = source[j - 1];
            }
            // 插入位置赋值
            source[j] = temp;
        }
    }

    /**
     * 希尔排序---
     * 插入排序的改进，有个间隔gap作为插入排序的增量比较进行交换，gap的变化规则是/=2(该规则shell提出，但不是最好的),Hib-bard 2^k-1目前认为比较好
     * loop-1 gap的递进规则
     * loop-2 loop-3 以gap为增量的插入排序
     *
     * @param source 源数组
     */
    private static void shellSort(int[] source) {
        System.out.println();
        System.out.println("================希尔排序================");
        // 增量gap递进
        for (int gap = source.length / 2; gap > 0; gap /= 2) {
            // 增量插入排序
            for (int i = gap; i < source.length; i++) {
                int j;
                int temp = source[i];
                for (j = i; j > gap && temp < source[j - gap]; j -= gap) {
                    source[j] = source[j - gap];
                }
                source[j] = temp;
            }
        }
    }

    /**
     * 归并排序---分而治之
     * https://images2015.cnblogs.com/blog/1024555/201612/1024555-20161218163120151-452283750.png
     * O(N*logN)
     *
     * @param source 源数组
     */
    private static void mergeSort(int[] source) {
        System.out.println();
        System.out.println("================归并排序================");
        int[] temp = new int[source.length]; // 和source大小一样的数组空间
        separate(source, 0, source.length - 1, temp);
    }

    /**
     * 分而
     *
     * @param source 源数组
     * @param left   最左下标
     * @param right  最右下标
     * @param temp   临时数组
     */
    private static void separate(int[] source, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            separate(source, left, mid, temp); // 分为左半部分
            separate(source, (mid + 1), right, temp); // 分为右半部分
            together(source, left, mid, right, temp); // 合并
        }
    }

    /**
     * 治之(合并)
     *
     * @param source 源数组
     * @param left   最左下标
     * @param mid    最中下标
     * @param right  最右下标
     * @param temp   临时数组
     */
    private static void together(int[] source, int left, int mid, int right, int[] temp) {
        // i代表左数组指针 j代表右数组指针 k为临时数组初始指针
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) { // 当左右指针都未到数组尾部时，合并较小值入临时数组
            if (source[i] < source[j]) {
                temp[k++] = source[i++];
            } else {
                temp[k++] = source[j++];
            }
        }
        while (i <= mid) { // 左指针未到末尾，右指针到末尾，则将左数组入临时数组
            temp[k++] = source[i++];
        }
        while (j <= right) { // 右指针未到末尾，左指针到末尾，则将右数组入临时数组
            temp[k++] = source[j++];
        }
        k = 0;
        while (left <= right) { // 排序好的临时数组拷贝至源数组
            source[left++] = temp[k++];
        }
    }

    private static void quickSort(int[] source) {
        System.out.println();
        System.out.println("================快速排序================");
        quickSort(source, 0, source.length - 1);
    }

    private static void quickSort(int[] source, int left, int right) {
        int i = left;
        int j = right;
        int t = source[left];
        if (left < right) {
            return;
        }
        while (i != j) {
            while (source[j] >= t && i < j) {
                j--;
            }
            while (source[i] <= t && i < j) {
                i++;
            }
            if (i < j) {
                int temp = source[i];
                source[i] = source[j];
                source[j] = temp;
            }
        }
        source[left] = source[i];
        source[i] = t;
        quickSort(source, 0, i - 1);
        quickSort(source, i + 1, right);
    }

    private static void sort(int[] source) {
        System.out.println();
        System.out.println("================快速排序-分治实现================");
        sort(Arrays.stream(source).boxed().collect(Collectors.toList()));
    }

    private static void sort(List<Integer> items) {
        if (items.size() > 1) {
            List<Integer> smaller = new ArrayList<>();
            List<Integer> same = new ArrayList<>();
            List<Integer> bigger = new ArrayList<>();

            Integer choseElement = items.get(items.size() / 2);
            for (Integer i : items) {
                if (i < choseElement) {
                    smaller.add(i);
                } else if (i > choseElement) {
                    bigger.add(i);
                } else {
                    same.add(i);
                }
            }
            sort(smaller);
            sort(bigger);

            items.clear();
            items.addAll(smaller);
            items.addAll(same);
            items.addAll(bigger);
        }
    }

    private static void test(String methodName, int[] source) {
        long start = System.nanoTime();
        switch (methodName) {
            case "insertionSort":
                insertionSort(source);
                break;
            case "shellSort":
                shellSort(source);
                break;
            case "mergeSort":
                mergeSort(source);
                break;
            case "bubbleSort":
                bubbleSort(source);
                break;
            case "quickSort":
                quickSort(source);
                break;
            case "sort":
                sort(source);
                break;
        }
        long end = System.nanoTime();
        System.out.println("用时: " + (end - start) + " ns");
        System.out.println("排序后: " + Arrays.toString(a));
    }

    public static void main(String[] args) {
        new Sort();
        System.out.println("排序前: " + Arrays.toString(a));
        test("insertionSort", a);
        test("bubbleSort", a);
        test("shellSort", a);
        test("mergeSort", a);
        test("quickSort", a);
        test("sort", a);
    }
}
