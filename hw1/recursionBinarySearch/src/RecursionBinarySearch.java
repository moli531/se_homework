/**
 * @author moli
 */
public class RecursionBinarySearch {

  public static void main(String[] args) {

    int[] nums = new int[] {4, 6, 9, 19, 30, 40, 500, 3450, 50004, 4334343};

    System.out.println(binarySearch(nums, 0, nums.length - 1, 30));

    System.out.println(binarySearch(nums, 50004));
  }

  private static int binarySearch(int[] nums, int p, int r, int k) {

    if (p > r) {
        return -1;
    }

    int mid = (p + r) / 2;

    if (nums[mid] == k) {
        return mid;
    }

    if (k > nums[mid]) {
        return binarySearch(nums, mid + 1, r, k);
    } else {
        return binarySearch(nums, p, mid - 1, k);
    }
  }

  private static int binarySearch(int[] nums, int k) {

    int p = 0;

    int r = nums.length - 1;

    while (p <= r) {

      int mid = (p + r) / 2;

      if (nums[mid] == k) {
          return mid;
      }

      if (k > nums[p]) {
          p = mid + 1;
      } else {
          r = mid - 1;
      }
    }

    return -1;
  }
}
