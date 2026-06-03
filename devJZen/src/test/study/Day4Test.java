package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 4: 스택 · 큐 · 덱 · 병합/카운팅/힙 정렬")
class Day4Test {

    // ── Stack (Custom Array-based) ───────────────────────────────────────────
    static class ArrayStack<T> {
        private final Object[] data;
        private int top;
        ArrayStack(int size) { data = new Object[size]; top = -1; }
        void push(T item) { data[++top] = item; }
        @SuppressWarnings("unchecked")
        T pop() { return (T) data[top--]; }
        @SuppressWarnings("unchecked")
        T peek() { return (T) data[top]; }
        boolean isEmpty() { return top == -1; }
        int size() { return top + 1; }
    }

    @Test
    @DisplayName("스택 — LIFO: 마지막에 넣은 것이 먼저 나옴")
    void stack_lifo() {
        ArrayStack<Integer> stack = new ArrayStack<>(5);
        stack.push(1); stack.push(2); stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("스택 — peek은 제거하지 않음")
    void stack_peek() {
        ArrayStack<Integer> stack = new ArrayStack<>(3);
        stack.push(10);
        assertEquals(10, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("스택 — ArrayDeque 활용 (Vector 상속 Stack보다 권장)")
    void stack_arrayDeque() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1); stack.push(2); stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    // ── Queue (CircularQueue) ─────────────────────────────────────────────────
    static class CircularQueue {
        private final int[] data;
        private int front, rear, size;
        CircularQueue(int cap) { data = new int[cap]; }
        void offer(int item) { data[rear] = item; rear = (rear + 1) % data.length; size++; }
        int poll() { int item = data[front]; front = (front + 1) % data.length; size--; return item; }
        int peek() { return data[front]; }
        boolean isEmpty() { return size == 0; }
        int size() { return size; }
    }

    @Test
    @DisplayName("원형 큐 — FIFO: 먼저 넣은 것이 먼저 나옴")
    void circularQueue_fifo() {
        CircularQueue q = new CircularQueue(5);
        q.offer(1); q.offer(2); q.offer(3);
        assertEquals(1, q.poll());
        assertEquals(2, q.poll());
        assertEquals(3, q.poll());
        assertTrue(q.isEmpty());
    }

    @Test
    @DisplayName("원형 큐 — rear가 배열 끝을 넘어 0으로 순환")
    void circularQueue_wraparound() {
        CircularQueue q = new CircularQueue(3);
        q.offer(10); q.offer(20);
        q.poll();               // front 이동: front=1
        q.offer(30); q.offer(40); // rear가 0→1로 순환
        assertEquals(20, q.poll());
        assertEquals(30, q.poll());
    }

    @Test
    @DisplayName("큐 — ArrayDeque 표준 라이브러리 활용")
    void queue_arrayDeque() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(1); queue.offer(2); queue.offer(3);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
    }

    // ── Deque ────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("덱 — 앞/뒤 양쪽에서 삽입·삭제 가능")
    void deque_bothEnds() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerFirst(1);  // [1]
        deque.offerLast(2);   // [1, 2]
        deque.offerFirst(0);  // [0, 1, 2]
        assertEquals(0, deque.peekFirst());
        assertEquals(2, deque.peekLast());
        deque.pollFirst();    // [1, 2]
        deque.pollLast();     // [1]
        assertEquals(1, deque.peekFirst());
    }

    @Test
    @DisplayName("덱 — 스택으로 사용 (LIFO)")
    void deque_asStack() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(1); deque.push(2); deque.push(3);
        assertEquals(3, deque.pop());
    }

    @Test
    @DisplayName("덱 — 큐로 사용 (FIFO)")
    void deque_asQueue() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerLast(1); deque.offerLast(2); deque.offerLast(3);
        assertEquals(1, deque.pollFirst());
        assertEquals(2, deque.pollFirst());
    }

    // ── Merge Sort ───────────────────────────────────────────────────────────
    static int[] mergeSort(int[] input) {
        int[] arr = input.clone();
        mergeSortHelper(arr, 0, arr.length - 1);
        return arr;
    }

    static void mergeSortHelper(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSortHelper(arr, left, mid);
        mergeSortHelper(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right)
            tmp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        while (i <= mid)   tmp[k++] = arr[i++];
        while (j <= right) tmp[k++] = arr[j++];
        System.arraycopy(tmp, 0, arr, left, tmp.length);
    }

    @Test
    @DisplayName("병합 정렬 — 항상 O(n log n) 보장")
    void mergeSort_basic() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, mergeSort(new int[]{5, 3, 1, 4, 2}));
    }

    @Test
    @DisplayName("병합 정렬 — 안정 정렬 (중복 원소 순서 유지)")
    void mergeSort_stable() {
        assertArrayEquals(new int[]{1, 2, 2, 3, 3, 5}, mergeSort(new int[]{3, 2, 1, 3, 2, 5}));
    }

    @Test
    @DisplayName("병합 정렬 — 역순 배열")
    void mergeSort_reversed() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, mergeSort(new int[]{5, 4, 3, 2, 1}));
    }

    // ── Counting Sort ────────────────────────────────────────────────────────
    static int[] countingSort(int[] input, int maxVal) {
        int[] arr = input.clone();
        int[] count = new int[maxVal + 1];
        for (int x : arr) count[x]++;
        int idx = 0;
        for (int i = 0; i <= maxVal; i++)
            while (count[i]-- > 0) arr[idx++] = i;
        return arr;
    }

    @Test
    @DisplayName("카운팅 정렬 — O(n + k), 비교 없이 등장 횟수로 정렬")
    void countingSort_basic() {
        assertArrayEquals(
            new int[]{1, 2, 2, 3, 3, 4, 8},
            countingSort(new int[]{4, 2, 2, 8, 3, 3, 1}, 8)
        );
    }

    @Test
    @DisplayName("카운팅 정렬 — 중복 원소 정확히 처리")
    void countingSort_duplicates() {
        assertArrayEquals(new int[]{1, 1, 1, 2, 2}, countingSort(new int[]{2, 1, 2, 1, 1}, 2));
    }

    // ── Heap Sort ────────────────────────────────────────────────────────────
    static int[] heapSort(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        // 최대 힙 구성
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        // 루트(최댓값)를 끝으로 옮기고 힙 재구성
        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0]; arr[0] = arr[i]; arr[i] = tmp;
            heapify(arr, i, 0);
        }
        return arr;
    }

    static void heapify(int[] arr, int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left  < n && arr[left]  > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;
        if (largest != i) {
            int tmp = arr[i]; arr[i] = arr[largest]; arr[largest] = tmp;
            heapify(arr, n, largest);
        }
    }

    @Test
    @DisplayName("힙 정렬 — 항상 O(n log n), 추가 메모리 O(1) 제자리 정렬")
    void heapSort_basic() {
        assertArrayEquals(new int[]{1, 3, 4, 5, 8}, heapSort(new int[]{5, 3, 8, 1, 4}));
    }

    @Test
    @DisplayName("힙 정렬 — 역순 배열")
    void heapSort_reversed() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, heapSort(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    @DisplayName("힙 정렬 — 단일 원소")
    void heapSort_single() {
        assertArrayEquals(new int[]{7}, heapSort(new int[]{7}));
    }

    @Test
    @DisplayName("힙 정렬 — heapify 후 루트가 최댓값")
    void heapSort_heapifyRoot() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        assertEquals(9, arr[0]);  // 최대 힙의 루트는 최댓값
    }
}
