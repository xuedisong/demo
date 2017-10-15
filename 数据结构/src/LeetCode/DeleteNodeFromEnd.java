package LeetCode;

/*
 * 序号：19
 * 题目：删除单链表倒数第四个节点，要求只用一次遍历
 * 我的：建立一个数组，一次遍历，得出所有几点数目减去倒数第N个中的N，得M，然后再正序遍历p指针至第M-1个，删除其后继节点
 * 正确：p正向跑至N时，head和p一块移动，当p到达null时，head到达要删除的结点位置。
 * 细节：需要用C风格的类设计，因为LinkedList类是直接按照索引删除的。无法体现两个指针。
 * 结果：对边界实例会错误，需要将过程分类精细些。
 */

public class DeleteNodeFromEnd {

	// 算法实现方法
	public static void deleteNodeFromEnd(Node head, int n) {
		Node p = head;
		Node q = head;
		for (int i = 0; p != null; i++, p = p.next) {
			if (i > n)
				q = q.next;
		}
		q.next = q.next.next;
	}

	// 测试方法
	public static void main(String[] args) {
		Node nodeRear = null;
		Node nodeFront = null;
		for (int i = 5; i > 0; i--) {
			nodeFront = new Node(i, nodeRear);
			nodeRear = nodeFront;
		}
		Node head = nodeFront;
		deleteNodeFromEnd(head, 2);
		Node p=head;
		for (int i = 0; i < 4; i++) {
			System.out.println(p.value);
			p=p.next;
		}
	}
}

// 链表节点类
class Node {
	int value;
	Node next;

	Node(int value, Node next) {
		this.value = value;
		this.next = next;
	}
}