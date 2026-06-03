package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 3: 선택/삽입/퀵 정렬 · 트리 · BST · 힙 · 그래프")
class Day3Test {

    // ── Selection Sort ───────────────────────────────────────────────────────
    static int[] selectionSort(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[minIdx]) minIdx = j;
            int tmp = arr[i]; arr[i] = arr[minIdx]; arr[minIdx] = tmp;
        }
        return arr;
    }

    @Test
    @DisplayName("선택 정렬 — 매 회전마다 최솟값을 앞으로 이동")
    void selectionSort_minToFront() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, selectionSort(new int[]{5, 3, 1, 4, 2}));
    }

    @Test
    @DisplayName("선택 정렬 — 최선/최악 모두 O(n²): 항상 전체를 비교")
    void selectionSort_alwaysNSquared() {
        assertArrayEquals(new int[]{1, 2, 3}, selectionSort(new int[]{1, 2, 3}));
    }

    // ── Insertion Sort ───────────────────────────────────────────────────────
    static int[] insertionSort(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > key) { arr[j + 1] = arr[j]; j--; }
            arr[j + 1] = key;
        }
        return arr;
    }

    @Test
    @DisplayName("삽입 정렬 — 정렬된 부분 배열에 새 원소를 올바른 위치에 삽입")
    void insertionSort_insertsCorrectly() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, insertionSort(new int[]{5, 3, 1, 4, 2}));
    }

    @Test
    @DisplayName("삽입 정렬 — 안정 정렬: 동일 값의 상대 순서 유지")
    void insertionSort_stable() {
        assertArrayEquals(new int[]{1, 1, 2, 3}, insertionSort(new int[]{3, 1, 1, 2}));
    }

    @Test
    @DisplayName("삽입 정렬 — 이미 정렬된 배열: while 루프 미실행으로 O(n) 최선")
    void insertionSort_bestCase() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, insertionSort(new int[]{1, 2, 3, 4, 5}));
    }

    // ── Quick Sort ───────────────────────────────────────────────────────────
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) { i++; int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp; }
        }
        int tmp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }

    @Test
    @DisplayName("퀵 정렬 — 피벗 기준으로 작은 값은 왼쪽, 큰 값은 오른쪽 분할")
    void quickSort_partitions() {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{1, 1, 2, 3, 6, 8, 10}, arr);
    }

    @Test
    @DisplayName("퀵 정렬 — partition 후 피벗은 최종 위치에 고정")
    void quickSort_pivotInFinalPosition() {
        int[] arr = {3, 1, 2};
        int pi = partition(arr, 0, arr.length - 1);
        // pivot=2: 왼쪽은 모두 ≤2, 오른쪽은 모두 >2
        for (int i = 0; i < pi; i++) assertTrue(arr[i] <= arr[pi]);
        for (int i = pi + 1; i < arr.length; i++) assertTrue(arr[i] > arr[pi]);
    }

    @Test
    @DisplayName("퀵 정렬 — 단일 원소: 분할 기저 조건 (low >= high)")
    void quickSort_singleElement() {
        int[] arr = {1};
        quickSort(arr, 0, 0);
        assertArrayEquals(new int[]{1}, arr);
    }

    // ── Tree Traversal ───────────────────────────────────────────────────────
    static class TreeNode {
        String val; TreeNode left, right;
        TreeNode(String v) { val = v; }
        TreeNode(String v, TreeNode l, TreeNode r) { val = v; left = l; right = r; }
    }

    static List<String> preOrder(TreeNode n) {
        if (n == null) return List.of();
        List<String> res = new ArrayList<>();
        res.add(n.val); res.addAll(preOrder(n.left)); res.addAll(preOrder(n.right));
        return res;
    }

    static List<String> inOrder(TreeNode n) {
        if (n == null) return List.of();
        List<String> res = new ArrayList<>();
        res.addAll(inOrder(n.left)); res.add(n.val); res.addAll(inOrder(n.right));
        return res;
    }

    static List<String> postOrder(TreeNode n) {
        if (n == null) return List.of();
        List<String> res = new ArrayList<>();
        res.addAll(postOrder(n.left)); res.addAll(postOrder(n.right)); res.add(n.val);
        return res;
    }

    //        A
    //       / \
    //      B   C
    //     / \   \
    //    D   E   F
    TreeNode buildTree() {
        return new TreeNode("A",
            new TreeNode("B", new TreeNode("D"), new TreeNode("E")),
            new TreeNode("C", null, new TreeNode("F")));
    }

    @Test
    @DisplayName("전위 순회 (Pre-order) — 루트 → 왼쪽 → 오른쪽: A B D E C F")
    void tree_preOrder() {
        assertEquals(List.of("A", "B", "D", "E", "C", "F"), preOrder(buildTree()));
    }

    @Test
    @DisplayName("중위 순회 (In-order) — 왼쪽 → 루트 → 오른쪽: D B E A C F")
    void tree_inOrder() {
        assertEquals(List.of("D", "B", "E", "A", "C", "F"), inOrder(buildTree()));
    }

    @Test
    @DisplayName("후위 순회 (Post-order) — 왼쪽 → 오른쪽 → 루트: D E B F C A")
    void tree_postOrder() {
        assertEquals(List.of("D", "E", "B", "F", "C", "A"), postOrder(buildTree()));
    }

    @Test
    @DisplayName("트리 — 리프 노드는 자식이 없음")
    void tree_leafNodes() {
        TreeNode root = buildTree();
        TreeNode d = root.left.left;
        assertNull(d.left);
        assertNull(d.right);
    }

    // ── BST ──────────────────────────────────────────────────────────────────
    static class IntNode {
        int val; IntNode left, right;
        IntNode(int v) { val = v; }
        IntNode(int v, IntNode l, IntNode r) { val = v; left = l; right = r; }
    }

    static IntNode bstSearch(IntNode root, int key) {
        if (root == null || root.val == key) return root;
        return key < root.val ? bstSearch(root.left, key) : bstSearch(root.right, key);
    }

    //        8
    //       / \
    //      3   10
    //     / \    \
    //    1   6    14
    //       / \
    //      4   7
    IntNode buildBST() {
        IntNode n4 = new IntNode(4), n7 = new IntNode(7);
        IntNode n6 = new IntNode(6, n4, n7);
        IntNode n3 = new IntNode(3, new IntNode(1), n6);
        IntNode n10 = new IntNode(10, null, new IntNode(14));
        return new IntNode(8, n3, n10);
    }

    @Test
    @DisplayName("BST — 대소 관계 성질: 왼쪽 < 루트 < 오른쪽")
    void bst_orderingProperty() {
        IntNode root = buildBST();
        assertTrue(root.left.val < root.val);   // 3 < 8
        assertTrue(root.right.val > root.val);  // 10 > 8
        assertTrue(root.left.left.val < root.left.val);   // 1 < 3
        assertTrue(root.left.right.val > root.left.val);  // 6 > 3
    }

    @Test
    @DisplayName("BST 탐색 — 존재하는 키: 대소 비교로 탐색 경로를 절반씩 줄임")
    void bst_searchFound() {
        IntNode result = bstSearch(buildBST(), 6);
        assertNotNull(result);
        assertEquals(6, result.val);
    }

    @Test
    @DisplayName("BST 탐색 — 없는 키는 null 반환")
    void bst_searchNotFound() {
        assertNull(bstSearch(buildBST(), 5));
    }

    // ── Heap (PriorityQueue) ──────────────────────────────────────────────────
    @Test
    @DisplayName("최소 힙 — 루트가 항상 최솟값: poll 시 최솟값 반환")
    void heap_minHeapProperty() {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(5); minHeap.offer(1); minHeap.offer(3);
        assertEquals(1, minHeap.poll());
        assertEquals(3, minHeap.poll());
        assertEquals(5, minHeap.poll());
    }

    @Test
    @DisplayName("최대 힙 — reverseOrder로 루트가 항상 최댓값")
    void heap_maxHeapProperty() {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(5); maxHeap.offer(1); maxHeap.offer(3);
        assertEquals(5, maxHeap.poll());
        assertEquals(3, maxHeap.poll());
    }

    @Test
    @DisplayName("힙 — peek은 루트를 제거하지 않고 조회 O(1)")
    void heap_peekDoesNotRemove() {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(2); minHeap.offer(4); minHeap.offer(1);
        assertEquals(1, minHeap.peek());
        assertEquals(3, minHeap.size());
    }

    // ── Graph DFS / BFS ──────────────────────────────────────────────────────
    static List<Integer> dfs(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        List<Integer> order = new ArrayList<>();
        dfsHelper(graph, visited, start, order);
        return order;
    }

    static void dfsHelper(List<List<Integer>> g, boolean[] visited, int v, List<Integer> order) {
        visited[v] = true;
        order.add(v);
        for (int next : g.get(v)) if (!visited[next]) dfsHelper(g, visited, next, order);
    }

    static List<Integer> bfs(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        List<Integer> order = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start); visited[start] = true;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            order.add(v);
            for (int next : graph.get(v))
                if (!visited[next]) { visited[next] = true; queue.offer(next); }
        }
        return order;
    }

    //   0
    //  / \
    // 1   2
    //  \ /
    //   3
    List<List<Integer>> buildGraph() {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < 4; i++) g.add(new ArrayList<>());
        g.get(0).addAll(List.of(1, 2));
        g.get(1).addAll(List.of(0, 3));
        g.get(2).addAll(List.of(0, 3));
        g.get(3).addAll(List.of(1, 2));
        return g;
    }

    @Test
    @DisplayName("DFS — 재귀(스택)로 깊이 우선 탐색: 모든 정점 방문")
    void graph_dfsVisitsAll() {
        List<Integer> result = dfs(buildGraph(), 0);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(List.of(0, 1, 2, 3)));
        assertEquals(0, result.get(0));
    }

    @Test
    @DisplayName("BFS — 큐로 너비 우선 탐색: 인접 노드가 깊은 노드보다 먼저 방문")
    void graph_bfsLevelOrder() {
        List<Integer> result = bfs(buildGraph(), 0);
        assertEquals(4, result.size());
        assertEquals(0, result.get(0));
        // 0의 인접(1, 2)이 더 먼 노드(3)보다 먼저 방문
        assertTrue(result.indexOf(1) < result.indexOf(3));
        assertTrue(result.indexOf(2) < result.indexOf(3));
    }
}
