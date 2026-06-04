/*
 * Date: 2026-06-02
 * @Author: gyeonghyeonlim
 * Note: 작성자명 바꿔주세요.
 * Assistant: 패키지 및 라이브러리 설정 Gemini, ChatGPT
 * */

import java.util.Arrays;

public class Main {
	static void bubbleSort(int[] arr) {
		int n = arr.length;

		for (int i = 0; i < n - 1; i++){
			boolean swapped = false;

			for (int j = 0; j < n - 1 - i; j++) {
				
				if (arr[j] > arr[j + 1]) {
					int tmp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = tmp;
					swapped = true;
				}
				
			}
			if(!swapped) break;
		}
	
	}

	//2. 배열
	static class Leet1{
		static int[] twoSum(int[] nums, int target) {

		
			for (int i = 0; i < nums.length; i++)
			for (int j = i + 1; j < nums.length; j++)
					if(nums[i] + nums[j] == target)
						return new int[]{i,j};
			return new int[]{};
			
		
		}
	}

	//3. 연결 리스트
	static class Node {
		int data;
		Node next;
		Node(int data) { this.data = data; }
	}

	static class LinkedList {
		Node head;

		void addFirst(int data) {
			Node node = new Node(data);
			node.next = head;
			head = node;
		}

		void addLast(int data) {
			Node node = new Node(data);
			if (head == null) {
				head = node;
				return;
			}
			Node cur = head;
			while (cur.next != null) {
				cur = cur.next;
			}
			cur.next = node;

		}

		void print() {
			Node cur = head;
			while (cur != null) {
				System.out.print(cur.data + " → ");
				cur = cur.next;
			}
			System.out.println("null");
		}
	}

	//4. 이중 연결 리스트
	static class DNode {
		int data;
		DNode prev, next;
		DNode(int data) { this.data = data; }
	}

	static class DoublyLinkedList {
		DNode head, tail;

		void addLast(int data) {
			DNode node = new DNode(data);
			if (tail == null) { head = tail = node; return; }
			tail.next = node;
			node.prev = tail;
			tail = node;
		}

		void delete(DNode node) {
			if (node.prev != null) node.prev.next = node.next;
			if (node.next != null) node.next.prev = node.prev;
			else tail = node.prev;
		}

		void print() {
			DNode cur = head;
			while (cur != null) {
				System.out.print(cur.data + " ↔ ");
				cur = cur.next;
			}
			System.out.println("null");
		}

		
	}


	public static void main(String[] args) {
	//bubbleSort
        int[] arr = {5, 3, 1, 4, 2};

        bubbleSort(arr);

        for (int x : arr) {
            System.out.print(x + " ");
        }

	int[] nums = {2,7,11,15};
	int target = 9;

	System.out.println();
	
	//2. 배열
	int[] result = Leet1.twoSum(nums, target);
	
	System.out.println(Arrays.toString(result));
	System.out.println("");

	//3. 연결 리스트
	LinkedList list = new LinkedList();

	list.addFirst(10);
	list.addLast(20);
	list.addLast(30);

	list.print();

	System.out.println("");

	//4. 이중 연결 리스트
	DoublyLinkedList DLL = new DoublyLinkedList();

	DLL.addLast(10);
	DLL.addLast(20);
	DLL.addLast(30);

	DLL.print();
	DLL.delete(DLL.head.next);
	DLL.print();

	}




	
	
}

