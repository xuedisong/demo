 /*
  * 图类
  */
package 邻接矩阵图;
import java.util.*;
public class AdjMGraph {
	//数据成员
	Vector<MapNode> vertices;//存放结点的顺序表
	int n;//节点数目n?=vertices.size()
	int numOfEdges;//边数
	int MaxWeight;//无穷值
	int[][] edge;//邻接矩阵
	AdjMGraph(int n,int Maxweight,Object[] V,RowColWeight E[],int e){//构造函数
		this.n=n;
		numOfEdges=0;
		MaxWeight=Maxweight;
		vertices=new Vector<MapNode>();
		edge=new int[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				if(i==j) edge[i][j]=0;
				else edge[i][j]=MaxWeight;
			}
		for(int i=0;i<n;i++)
			insertVertex(V[i]);
		for(int k=0;k<e;k++)
			insertEdge(E[k].row,E[k].col,E[k].weight);

	}
	void insertVertex(Object data){//插入节点
		vertices.addElement(new MapNode(data));
	}
	void insertEdge(int v1,int v2,int weight){//插入边
		if(v1<0||v1>=vertices.size()||v2<0||v2>=vertices.size()){
			System.out.println("参数v1或v2越界出错！");
			return;
		}
		numOfEdges++;
		edge[v1][v2]=weight;
	}
	int GetFirstVex(int v){//寻找序号为v的结点的第一个邻接结点序号，如果失败，返回-1
		if(v<0||v>vertices.size()){
			System.out.println("参数v越界出错！");
			return -1;
		}
		for(int col=0;col<vertices.size();col++)
			if(edge[v][col]>0&&edge[v][col]<MaxWeight) return col;
		return -1;
	}
	int GetNextVex(int v1,int v2){//寻找v1的邻接节点v2的下一个邻接节点序号，如果失败，返回-1
		if(v1<0||v1>=vertices.size()||v2<0||v2>vertices.size()){
			System.out.println("参数v1或v2越界出错！");
			return -1;
		}
		for(int col=v2+1;col<vertices.size();col++){
			if(edge[v1][col]>0&&edge[v1][col]<MaxWeight) return col;
		}
		return -1;
	}
	int GetOut(int k){//获取序号为k的节点的出度
		int q=0,count=0;
		if(k<0||k>=n){
			System.out.println("参数k越界出错！");
			return -1;
		}
		for(int i=0;i<n;i++){
			q=edge[k][i];
			if(q>0&&q<MaxWeight){
				count++;
			}
		}
		return count;
	}
	int GetIn(int k){//获取序号为k的节点的入度
		int q=0,count=0;
		if(k<0||k>=n){
			System.out.println("参数k越界出错！");
			return -1;
		}
		for(int i=0;i<n;i++){
			q=edge[i][k];
			if(q>0&&q<MaxWeight){
				count++;
			}
		}
		return count;
	}
	/*
	 * 非递归的深度优先遍历算法，采用栈结构存储。
	 * 并具体化访问操作，以搜索v1与v2节点之间的路径。
	 * 回溯条件：（1）访问过。
	 */
	void DepthFSearch_2(int k){//以k为初始结点
		int FirstVex;
		int[] flag=new int[n];
		for(int i=0;i<n;i++){//初始化flag数组
			flag[i]=0;
		}
		Stack<Integer> s=new Stack<Integer>();//建空栈
		s.push(k);
		flag[k]=1;
		while(!s.isEmpty()){
			while(GetFirstVex(s.lastElement())!=-1){//如果有邻接结点，则想方设法一直入栈
				k=s.lastElement();
				FirstVex=GetFirstVex(k);
				if(flag[FirstVex]==0){//如果第一个邻接结点满足要求，则入栈
					s.push(FirstVex);
					flag[FirstVex]=1;
				}
				else{//第一个邻接结点不满足入栈要求，则循环搜索下一个邻接结点
					while(GetNextVex(k,FirstVex)!=-1){//如果有下一个邻接结点
						if(flag[GetNextVex(k,FirstVex)]==0){
							s.push(GetNextVex(k,FirstVex));
							flag[GetNextVex(k,FirstVex)]=1;
							break;//找到一个满足要求的，就跳出循环
						}
						FirstVex=GetNextVex(k,FirstVex);
					}
				}
				if(k==s.lastElement())//如果遍历了所有邻接结点，都没有可入栈结点，就跳出循环
					break;
			}
			k=s.pop();//弹出坏掉的死胡同结点
			//vist start
			System.out.println(vertices.get(k).data);
			//end
		}
	}
	/*栈设计的遍历，入栈的时候访问，还是出栈的时候访问，是分成两种的，其中入栈是访问是较高效的。
	 * 以下为入栈时访问的深度搜索
	 */
	void DepthFSearch_1(int k){//以k为初始结点
		int FirstVex;
		int[] flag=new int[n];
		for(int i=0;i<n;i++){//初始化flag数组
			flag[i]=0;
		}
		Stack<Integer> s=new Stack<Integer>();//建空栈
		s.push(k);
		flag[k]=1;
		//vist start
		System.out.println(vertices.get(k).data);
		//end
		while(!s.isEmpty()){
			while(GetFirstVex(s.lastElement())!=-1){//如果有邻接结点，则想方设法一直入栈
				k=s.lastElement();
				FirstVex=GetFirstVex(k);
				if(flag[FirstVex]==0){//如果第一个邻接结点满足要求，则入栈
					s.push(FirstVex);
					flag[FirstVex]=1;
					//vist start
					System.out.println(vertices.get(FirstVex).data);
					//end
				}
				else{//第一个邻接结点不满足入栈要求，则循环搜索下一个邻接结点
					while(GetNextVex(k,FirstVex)!=-1){//如果有下一个邻接结点
						if(flag[GetNextVex(k,FirstVex)]==0){
							s.push(GetNextVex(k,FirstVex));
							flag[GetNextVex(k,FirstVex)]=1;
							//vist start
							System.out.println(vertices.get(GetNextVex(k,FirstVex)).data);
							//end
							break;//找到一个满足要求的，就跳出循环
						}
						FirstVex=GetNextVex(k,FirstVex);
					}
				}
				if(k==s.lastElement())//如果遍历了所有邻接结点，都没有可入栈结点，就跳出循环
					break;
			}
			k=s.pop();//弹出坏掉的死胡同结点
		}
	}
	/*
	 * 打印栈结构的入栈出栈情况
	 */
	void DepthFSearch_3(int k){//以k为初始结点
		int FirstVex;
		int[] flag=new int[n];
		for(int i=0;i<n;i++){//初始化flag数组
			flag[i]=0;
		}
		Stack<Integer> s=new Stack<Integer>();//建空栈
		s.push(k);
		flag[k]=1;
		//vist start
		System.out.println("压入 "+vertices.get(k).data);
		//end
		while(!s.isEmpty()){
			while(GetFirstVex(s.lastElement())!=-1){//如果有邻接结点，则想方设法一直入栈
				k=s.lastElement();
				FirstVex=GetFirstVex(k);
				if(flag[FirstVex]==0){//如果第一个邻接结点满足要求，则入栈
					s.push(FirstVex);
					flag[FirstVex]=1;
					//vist start
					System.out.println("压入 "+vertices.get(FirstVex).data);
					//end
				}
				else{//第一个邻接结点不满足入栈要求，则循环搜索下一个邻接结点
					while(GetNextVex(k,FirstVex)!=-1){//如果有下一个邻接结点
						if(flag[GetNextVex(k,FirstVex)]==0){
							s.push(GetNextVex(k,FirstVex));
							flag[GetNextVex(k,FirstVex)]=1;
							//vist start
							System.out.println("压入 "+vertices.get(GetNextVex(k,FirstVex)).data);
							//end
							break;//找到一个满足要求的，就跳出循环
						}
						FirstVex=GetNextVex(k,FirstVex);
					}
				}
				if(k==s.lastElement())//如果遍历了所有邻接结点，都没有可入栈结点，就跳出循环
					break;
			}
			k=s.pop();//弹出坏掉的死胡同结点
			//vist start
			System.out.println("弹出 "+vertices.get(k).data);
			//end
		}
	}
	/*
	 * 从k能到达l当且仅当从k遍历到l,
	 */
	void SearchPath(int k,int l){//以k为初始结点
		int FirstVex;
		int[] flag=new int[n];
		for(int i=0;i<n;i++){//初始化flag数组
			flag[i]=0;
		}
		Stack<Integer> s=new Stack<Integer>();//建空栈
		s.push(k);
		flag[k]=1;
		//vist start
		System.out.println(vertices.get(k).data);
		if(k==l)return;
		//end
		while(!s.isEmpty()){
			while(GetFirstVex(s.lastElement())!=-1){//如果有邻接结点，则想方设法一直入栈
				k=s.lastElement();
				FirstVex=GetFirstVex(k);
				if(flag[FirstVex]==0){//如果第一个邻接结点满足要求，则入栈
					s.push(FirstVex);
					flag[FirstVex]=1;
					//vist start
					System.out.println(vertices.get(FirstVex).data);
					if(FirstVex==l)return;
					//end
				}
				else{//第一个邻接结点不满足入栈要求，则循环搜索下一个邻接结点
					while(GetNextVex(k,FirstVex)!=-1){//如果有下一个邻接结点
						if(flag[GetNextVex(k,FirstVex)]==0){
							s.push(GetNextVex(k,FirstVex));
							flag[GetNextVex(k,FirstVex)]=1;
							//vist start
							System.out.println(vertices.get(GetNextVex(k,FirstVex)).data);
							if(GetNextVex(k,FirstVex)==l)return;
							//end
							break;//找到一个满足要求的，就跳出循环
						}
						FirstVex=GetNextVex(k,FirstVex);
					}
				}
				if(k==s.lastElement())//如果遍历了所有邻接结点，都没有可入栈结点，就跳出循环
					break;
			}
			k=s.pop();//弹出坏掉的死胡同结点
		}
	}
}
