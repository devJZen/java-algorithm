package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 2: 버블 정렬 · 배열 · 연결 리스트 · 해시 테이블 · 레코드")
class Day2Test {

    // ── Bubble Sort ──────────────────────────────────────────────────────────
    static int[] bubbleSort(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return arr;
    }

    @Test
    @DisplayName("버블 정렬 — 인접 원소 비교·교환 후 오름차순 정렬")
    void bubbleSort_sorted() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, bubbleSort(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    @DisplayName("버블 정렬 — 이미 정렬된 배열: swapped=false로 조기 종료 (최선 O(n))")
    void bubbleSort_earlyExit() {
        assertArrayEquals(new int[]{1, 2, 3}, bubbleSort(new int[]{1, 2, 3}));
    }

    @Test
    @DisplayName("버블 정렬 — 한 사이클 후 최댓값이 맨 뒤로 이동")
    void bubbleSort_maxBubblesUp() {
        int[] arr = {3, 1, 2};
        bubbleSort(arr);
        // 정렬 후 마지막 원소가 최댓값
        int[] sorted = bubbleSort(new int[]{3, 1, 2});
        assertEquals(sorted[sorted.length - 1], 3);
    }

    // ── Array ────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("배열 — 인덱스로 O(1) 임의 접근")
    void array_indexAccess() {
        int[] arr = {10, 20, 30, 40, 50};
        assertEquals(30, arr[2]);
        assertEquals(10, arr[0]);
        assertEquals(50, arr[arr.length - 1]);
    }

    @Test
    @DisplayName("배열 — 선형 탐색 O(n): 순서대로 비교해 원소를 찾음")
    void array_linearSearch() {
        int[] arr = {10, 20, 30, 40, 50};
        int target = 30, foundIdx = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) { foundIdx = i; break; }
        }
        assertEquals(2, foundIdx);
    }

    @Test
    @DisplayName("배열 — 크기 고정: length는 선언 시 결정됨")
    void array_fixedSize() {
        int[] arr = new int[5];
        assertEquals(5, arr.length);
    }

    // ── Linked List ──────────────────────────────────────────────────────────
    static class Node { int data; Node next; Node(int d) { data = d; } }

    static class SimpleLinkedList {
        Node head;

        void addFirst(int d) { Node n = new Node(d); n.next = head; head = n; }

        void addLast(int d) {
            Node n = new Node(d);
            if (head == null) { head = n; return; }
            Node c = head;
            while (c.next != null) c = c.next;
            c.next = n;
        }

        int get(int i) { Node c = head; for (int k = 0; k < i; k++) c = c.next; return c.data; }
        int size() { int s = 0; Node c = head; while (c != null) { s++; c = c.next; } return s; }
    }

    @Test
    @DisplayName("연결 리스트 — addFirst O(1): 헤드 교체만으로 앞 삽입")
    void linkedList_addFirst() {
        SimpleLinkedList list = new SimpleLinkedList();
        list.addFirst(30); list.addFirst(20); list.addFirst(10);
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(2));
    }

    @Test
    @DisplayName("연결 리스트 — addLast: 끝까지 순회 후 연결")
    void linkedList_addLast() {
        SimpleLinkedList list = new SimpleLinkedList();
        list.addLast(1); list.addLast(2); list.addLast(3);
        assertEquals(3, list.size());
        assertEquals(3, list.get(2));
    }

    @Test
    @DisplayName("연결 리스트 — 접근은 O(n): head부터 순차 탐색")
    void linkedList_sequentialAccess() {
        SimpleLinkedList list = new SimpleLinkedList();
        list.addLast(10); list.addLast(20); list.addLast(30);
        // 인덱스 2에 접근하려면 head → next → next 3번 이동
        assertEquals(30, list.get(2));
    }

    // ── Doubly Linked List ───────────────────────────────────────────────────
    static class DNode { int data; DNode prev, next; DNode(int d) { data = d; } }

    static class DoublyLinkedList {
        DNode head, tail;

        void addLast(int d) {
            DNode n = new DNode(d);
            if (tail == null) { head = tail = n; return; }
            tail.next = n; n.prev = tail; tail = n;
        }

        void delete(DNode node) {
            if (node.prev != null) node.prev.next = node.next; else head = node.next;
            if (node.next != null) node.next.prev = node.prev; else tail = node.prev;
        }

        int size() { int s = 0; DNode c = head; while (c != null) { s++; c = c.next; } return s; }
    }

    @Test
    @DisplayName("이중 연결 리스트 — 노드 삭제 O(1): prev 포인터로 앞 노드를 바로 참조")
    void doublyLinkedList_delete() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addLast(10); list.addLast(20); list.addLast(30);
        list.delete(list.head.next);  // 20 삭제
        assertEquals(2, list.size());
        assertEquals(30, list.head.next.data);
    }

    @Test
    @DisplayName("이중 연결 리스트 — prev 포인터: 양방향 탐색 가능")
    void doublyLinkedList_prevPointer() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addLast(1); list.addLast(2); list.addLast(3);
        assertEquals(2, list.tail.prev.data);
        assertNull(list.head.prev);
    }

    // ── Circular Linked List ─────────────────────────────────────────────────
    static class CNode { int data; CNode next; CNode(int d) { data = d; } }

    static class CircularLinkedList {
        CNode head;

        void add(int d) {
            CNode n = new CNode(d);
            if (head == null) { head = n; n.next = head; return; }
            CNode c = head;
            while (c.next != head) c = c.next;
            c.next = n; n.next = head;
        }

        boolean isCircular() {
            if (head == null) return false;
            CNode c = head.next;
            while (c != head) { if (c == null) return false; c = c.next; }
            return true;
        }

        int get(int i) { CNode c = head; for (int k = 0; k < i; k++) c = c.next; return c.data; }
    }

    @Test
    @DisplayName("원형 연결 리스트 — 마지막 노드의 next가 head를 가리킴")
    void circularLinkedList_lastPointsToHead() {
        CircularLinkedList list = new CircularLinkedList();
        list.add(10); list.add(20); list.add(30);
        assertTrue(list.isCircular());
    }

    @Test
    @DisplayName("원형 연결 리스트 — 끝이 없는 순환 구조: n번 이동하면 head로 복귀")
    void circularLinkedList_wraparound() {
        CircularLinkedList list = new CircularLinkedList();
        list.add(10); list.add(20); list.add(30);
        // 노드 3개: head → 20 → 30 → head(10)
        assertEquals(list.head.data, list.head.next.next.next.data);
    }

    // ── Hash Table (HashMap) ─────────────────────────────────────────────────
    @Test
    @DisplayName("해시 테이블 — 키로 값을 O(1) 평균 탐색")
    void hashMap_putGet() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        map.put("banana", 5);
        assertEquals(3, map.get("apple"));
        assertTrue(map.containsKey("banana"));
    }

    @Test
    @DisplayName("해시 테이블 — remove: 키-값 쌍 삭제")
    void hashMap_remove() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("key", 42);
        map.remove("key");
        assertFalse(map.containsKey("key"));
        assertNull(map.get("key"));
    }

    @Test
    @DisplayName("해시 테이블 — 같은 키로 put하면 값 덮어쓰기")
    void hashMap_overwrite() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 1);
        map.put("x", 2);
        assertEquals(2, map.get("x"));
        assertEquals(1, map.size());
    }

    // ── Record ───────────────────────────────────────────────────────────────
    record Point(int x, int y) {}
    record Pair<A, B>(A first, B second) {}

    @Test
    @DisplayName("레코드 — 필드명으로 접근 (튜플의 인덱스 접근보다 의미 전달이 높음)")
    void record_fieldAccess() {
        Point p = new Point(3, 4);
        assertEquals(3, p.x());
        assertEquals(4, p.y());
    }

    @Test
    @DisplayName("레코드 — 불변(immutable): equals는 값 기반으로 자동 구현")
    void record_equality() {
        assertEquals(new Point(1, 2), new Point(1, 2));
        assertNotEquals(new Point(1, 2), new Point(2, 1));
    }

    @Test
    @DisplayName("제네릭 Pair 레코드 — 타입 파라미터로 범용 사용")
    void record_genericPair() {
        Pair<String, Integer> pair = new Pair<>("score", 100);
        assertEquals("score", pair.first());
        assertEquals(100, pair.second());
    }
}
