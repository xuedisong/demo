/*
 * 邻接表存储的图类
 */
package 邻接表图;

import java.util.Stack;
import 邻接矩阵图.RowColWeight;

public class AdjLGraph {
	int MaxVertices;// 结点上限
	AdjLHeight a[];// 结点数组，包含序号，data，指针
	int numOfVerts;// 当前结点个数
	int numOfEdges;

	AdjLGraph(Object v[], int n, RowColWeight d[], int e) {// 构造函数
		MaxVertices = n;
		numOfVerts = 0;
		numOfEdges = 0;
		a = new AdjLHeight[MaxVertices];
		for (int i = 0; i < n; i++) {
			// a[i]=new AdjLHeight();
			insertVertex(i, v[i]);
		}
		for (int k = 0; k < e; k++)
			insertEdge(d[k].row, d[k].col);
	}

	void insertVertex(int i, Object vertex) {// 插入结点操作函数
		if (i >= 0 && i < MaxVertices) {
			a[i] = new AdjLHeight(vertex, i);
			numOfVerts++;
		} else
			System.out.println("结点越界");
	}

	void insertEdge(int v1, int v2) {// 插入边操作函数
		Edge p;
		if (v1 < 0 || v1 >= numOfVerts || v2 < 0 || v2 >= numOfVerts) {
			System.out.println("参数v1或v2越界出错！");
			return;
		}
		p = new Edge();
		p.dest = v2;

		p.next = a[v1].adj;
		a[v1].adj = p;
		numOfEdges++;
	}

	void printAdjLGraph() {// 打印函数
		System.out.println("结点集合为：");
		for (int i = 0; i < numOfVerts; i++)
			System.out.print(a[i].data + "\t");
		System.out.print("\n邻接表为：\n");
		for (int i = 0; i < numOfVerts; i++) {
			System.out.print(a[i].data + "\t");
			Edge p = a[i].adj;
			while (p != null) {
				System.out.print(p.dest + "\t");
				p = p.next;
			}
			System.out.println();
		}
	}

	void DeleteVex(int v) {// 删除结点操作函数
		Edge temp = null;
		while (a[0].adj != null) {// 删除v行
			a[0].adj = a[0].adj.next;
			// free
			numOfEdges--;
		}
		for (int i = 0; i < numOfVerts; i++) {
			temp = a[i].adj;
			if (temp != null && temp.dest == v) {// 第一邻接点就是v
				a[i].adj = temp.next;
				// free
				numOfEdges--;
			} else
				while (temp != null && temp.next != null) {// 第一邻接点不是v
					if (temp.next.dest == v) {
						temp.next = temp.next.next;
						// free
						numOfEdges--;
						break;
					}
					temp = temp.next;
				}
		}
		/*
		 * 将每个不是v行的行中，dest信息进行更新
		 */

		for (int i = 0; i < numOfVerts; i++) {
			Edge p = a[i].adj;
			while (p != null) {
				if (p.dest > v)
					p.dest--;
				p = p.next;
			}
		}

		for (int i = v; i < numOfVerts - 1; i++) {
			a[i].copy(a[i + 1]);
		}
		a[numOfVerts - 1] = null;
		numOfVerts--;
	}

	void DeleteEdge(int v1, int v2) {// 删除边操作函数
		Edge curr, pre;
		if (v1 < 0 || v1 >= numOfVerts || v2 < 0 || v2 >= numOfVerts) {
			System.out.println("参数v1或v2越界出错！");
			return;
		}

		pre = null;
		curr = a[v1].adj;
		while (curr != null && curr.dest != v2) {
			pre = curr;
			curr = curr.next;
		}

		if (curr != null && curr.dest == v2 && pre == null) {
			a[v1].adj = curr.next;
			// free
			numOfEdges--;
		} else if (curr != null && curr.dest == v2 && pre != null) {
			pre.next = curr.next;
			// free
			numOfEdges--;
		} else
			System.out.println("边<v1,v2>不存在！");
	}

	int GetFirstVex(int v) {// 取第一个邻接结点
		Edge p;

		if (v < 0 || v >= numOfVerts) {
			System.out.println("参数v1或v2越界出错！");
			return -1;
		}

		p = a[v].adj;
		if (p != null)
			return p.dest;
		else
			return -1;
	}

	int GetNextVex(int v1, int v2) {// 取下一个邻接结点
		Edge p;

		if (v1 < 0 || v1 >= numOfVerts || v2 < 0 || v2 > numOfVerts) {
			System.out.println("参数v1或v2越界出错！");
			return -1;
		}

		p = a[v1].adj;
		while (p != null) {
			if (p.dest != v2) {
				p = p.next;
				continue;
			} else
				break;
		}

		p = p.next;
		if (p != null)
			return p.dest;
		else
			return -1;
	}

	/*
	 * 非递归深度优先遍历
	 */
	void DepthFSearch(int k) {// 以k为初始结点
		int n = numOfVerts;
		int FirstVex;
		int[] flag = new int[numOfVerts];
		for (int i = 0; i < n; i++) {// 初始化flag数组
			flag[i] = 0;
		}
		Stack<Integer> s = new Stack<Integer>();// 建空栈
		s.push(k);
		flag[k] = 1;
		// vist start
		System.out.println(a[k].data);
		// end
		while (!s.isEmpty()) {
			while (GetFirstVex(s.lastElement()) != -1) {// 如果有邻接结点，则想方设法一直入栈
				k = s.lastElement();
				FirstVex = GetFirstVex(k);
				if (flag[FirstVex] == 0) {// 如果第一个邻接结点满足要求，则入栈
					s.push(FirstVex);
					flag[FirstVex] = 1;
					// vist start
					System.out.println(a[FirstVex].data);
					// end
				} else {// 第一个邻接结点不满足入栈要求，则循环搜索下一个邻接结点
					while (GetNextVex(k, FirstVex) != -1) {// 如果有下一个邻接结点
						if (flag[GetNextVex(k, FirstVex)] == 0) {
							s.push(GetNextVex(k, FirstVex));
							flag[GetNextVex(k, FirstVex)] = 1;
							// vist start
							System.out.println(a[GetNextVex(k, FirstVex)].data);
							// end
							break;// 找到一个满足要求的，就跳出循环
						}
						FirstVex = GetNextVex(k, FirstVex);
					}
				}
				if (k == s.lastElement())// 如果遍历了所有邻接结点，都没有可入栈结点，就跳出循环
					break;
			}
			k = s.pop();// 弹出坏掉的死胡同结点
			// vist start
			System.out.println("弹出 " + a[k].data);
			// end
		}
	}

	/*
	 * 广度优先算法，用队列来实现。 目的：广度优先遍历打印连通图的各个结点。
	 */
	void BroadFSearch(int v) {// v作为起始点，
		int FLAG = 0, temp = 0;// 记录队头位置
		int First = 0;// 第一个邻接结点序号
		int front = 0;// 队头指针
		int rear = 0;// 队尾指针
		int[] queue = new int[numOfVerts];// 队列
		int[] flag = new int[numOfVerts];// 标记节点是否访问过，0未访问，1访问
		for (int i = 0; i < numOfVerts; i++) {// 初始化标签数组及队列
			flag[i] = 0;
			queue[i] = 0;
		}
		queue[front] = v;
		front++;// 结点v入队列
		flag[v] = 1;
		// 访问start
		System.out.println(a[v].data);
		// end
		while (rear != front) {
			// 循环入队列
			while (GetFirstVex(queue[rear]) != -1) {
				FLAG = front;// 记录一下队头位置
				First = GetFirstVex(queue[rear]);
				if (flag[First] == 0) {
					queue[front] = First;
					front++;
					flag[First] = 1;
					// 访问start
					System.out.println(a[First].data);
					// end
				}
				temp = First;
				while (GetNextVex(queue[rear], temp) != -1) {
					temp = GetNextVex(queue[rear], temp);
					if (flag[temp] == 0) {
						queue[front] = temp;
						front++;
						flag[temp] = 1;
						// 访问start
						System.out.println(a[temp].data);
						// end
					}
				}
				if (FLAG == front) {// 如果队头位置没有移动，说明再也没有符合入队要求的点了，此时应该跳出入队循环
					break;
				}
			}
			// v出队列
			// vist start
			System.out.println("出队 " + a[rear].data);
			// end
			rear++;
		}
	}

}
