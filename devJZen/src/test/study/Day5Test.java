package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 5: 셸 정렬 · 기수 정렬 · Tim 정렬")
class Day5Test {

    // ── Shell Sort ───────────────────────────────────────────────────────────
    static int[] shellSort(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arr[i], j = i;
                while (j >= gap && arr[j - gap] > key) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = key;
            }
        }
        return arr;
    }

    @Test
    @DisplayName("셸 정렬 — 기본 동작 (gap=4 → 2 → 1 삽입 정렬)")
    void shellSort_basic() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8},
            shellSort(new int[]{8, 3, 7, 1, 5, 2, 6, 4}));
    }

    @Test
    @DisplayName("셸 정렬 — 역순 배열")
    void shellSort_reversed() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, shellSort(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    @DisplayName("셸 정렬 — 이미 정렬된 배열")
    void shellSort_alreadySorted() {
        assertArrayEquals(new int[]{1, 2, 3}, shellSort(new int[]{1, 2, 3}));
    }

    @Test
    @DisplayName("셸 정렬 — 단일 원소")
    void shellSort_single() {
        assertArrayEquals(new int[]{5}, shellSort(new int[]{5}));
    }

    // ── Radix Sort (LSD) ─────────────────────────────────────────────────────
    static int[] radixSort(int[] input) {
        int[] arr = input.clone();
        int max = Arrays.stream(arr).max().getAsInt();
        for (int exp = 1; max / exp > 0; exp *= 10)
            countByDigit(arr, exp);
        return arr;
    }

    static void countByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        for (int x : arr) count[(x / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        // 뒤에서부터 순회해 안정 정렬 유지
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[--count[digit]] = arr[i];
        }
        System.arraycopy(output, 0, arr, 0, n);
    }

    @Test
    @DisplayName("기수 정렬 — LSD 방식, 자릿수별 카운팅 정렬 O(d×n)")
    void radixSort_basic() {
        assertArrayEquals(
            new int[]{2, 24, 45, 66, 75, 90, 170, 802},
            radixSort(new int[]{170, 45, 75, 90, 802, 24, 2, 66})
        );
    }

    @Test
    @DisplayName("기수 정렬 — 한 자리 수")
    void radixSort_singleDigit() {
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, radixSort(new int[]{9, 3, 7, 1, 5}));
    }

    @Test
    @DisplayName("기수 정렬 — 역순 배열")
    void radixSort_reversed() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, radixSort(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    @DisplayName("기수 정렬 — 자릿수가 다른 혼합 배열")
    void radixSort_mixedDigits() {
        assertArrayEquals(new int[]{1, 10, 100, 1000}, radixSort(new int[]{100, 1, 1000, 10}));
    }

    // ── Tim Sort (Arrays.sort / Collections.sort) ─────────────────────────────
    @Test
    @DisplayName("Tim 정렬 — int[] 기본 타입: Dual-Pivot Quicksort 사용")
    void timSort_primitiveArray() {
        int[] arr = {5, 2, 4, 1, 3};
        Arrays.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    @DisplayName("Tim 정렬 — Integer[] 객체 배열: Tim Sort 사용")
    void timSort_objectArray() {
        Integer[] arr = {5, 2, 4, 1, 3};
        Arrays.sort(arr);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    @DisplayName("Tim 정렬 — List<Integer>: Collections.sort() = Tim Sort")
    void timSort_list() {
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 2, 4, 1, 3));
        Collections.sort(list);
        assertEquals(List.of(1, 2, 3, 4, 5), list);
    }

    @Test
    @DisplayName("Tim 정렬 — 부분 정렬된 배열 (run 활용으로 빠름)")
    void timSort_partiallySorted() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 8, 6, 7, 9, 10, 11, 12));
        Collections.sort(list);
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), list);
    }

    @Test
    @DisplayName("Tim 정렬 — Comparator 역순 정렬")
    void timSort_reverseOrder() {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));
        list.sort(Comparator.reverseOrder());
        assertEquals(List.of(5, 4, 3, 1, 1), list);
    }

    // ── 전체 정렬 알고리즘 결과 일치 검증 ───────────────────────────────────
    @Test
    @DisplayName("셸/기수/Tim 정렬 — 동일 입력에 동일한 결과 출력")
    void allSorts_sameResult() {
        int[] input    = {5, 3, 8, 1, 4, 2, 7, 6};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8};

        assertArrayEquals(expected, shellSort(input), "셸 정렬");
        assertArrayEquals(expected, radixSort(input), "기수 정렬");

        int[] timInput = input.clone();
        Arrays.sort(timInput);
        assertArrayEquals(expected, timInput, "Tim 정렬 (Arrays.sort)");
    }
}
