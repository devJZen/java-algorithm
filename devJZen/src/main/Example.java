import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Java 기본 개념 실습 예제
// 참고: https://docs.oracle.com/javase/tutorial/java/javaOO/index.html
public class Example {

    // --- class, field, 생성자 ---
    String label;

    Example(String label) {
        this.label = label;
    }

    // @Override: Object.toString() 재정의
    @Override
    public String toString() {
        return "Example(" + label + ")";
    }

    // void: 반환값 없는 메서드
    static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    // int 반환 메서드
    static int sum(int[] arr) {
        int total = 0;
        for (int x : arr) total += x;
        return total;
    }

    // static void main: JVM 진입점 — 인스턴스 없이 호출 가능
    public static void main(String[] args) {

        // 기본 자료형
        int n     = 10;
        double pi = 3.14159;
        boolean ok = true;
        char grade = 'A';
        System.out.println("int=" + n + " double=" + pi + " boolean=" + ok + " char=" + grade);

        // 배열 int[]
        int[] arr = {5, 3, 1, 4, 2};
        printArray(arr);
        Arrays.sort(arr);
        System.out.print("sorted: ");
        printArray(arr);
        System.out.println("sum=" + sum(arr));

        // List<Integer>
        List<Integer> list = new ArrayList<>(Arrays.asList(10, 20, 30));
        list.add(40);
        list.remove(0);                    // 인덱스 0 제거
        System.out.println("list=" + list + " size=" + list.size());

        // 객체 생성 및 toString
        Example ex = new Example("study");
        System.out.println(ex);
    }
}
