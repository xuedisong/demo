/*
 * 主函数测试
 */
package 邻接表图;

import 邻接矩阵图.RowColWeight;

public class MapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int e=11;
		int n=6;
		Object a[]={'A','B','C','D','E','F'};
		RowColWeight[] rcw={
				new RowColWeight(0,1,1),
				new RowColWeight(1,3,1),new RowColWeight(1,5,1),
				new RowColWeight(2,1,1),new RowColWeight(2,5,1),
				new RowColWeight(3,2,1),new RowColWeight(3,4,1),new RowColWeight(3,5,1),
				new RowColWeight(4,0,1),
				new RowColWeight(5,0,1),new RowColWeight(5,4,1)
				};
		AdjLGraph L1=new AdjLGraph(a,n,rcw,e);
		
		//打印图
		L1.printAdjLGraph();
		
		//8-14(1)测试删除边
		int b1=0;
		int b2=1;
		System.out.println("\n8-14(1)\n测试删除边<"+b1+","+b2+">");
		L1.DeleteEdge(b1, b2);
		L1.printAdjLGraph();
		
		//8-12 8-14(3)测试删除点
		int d=2;
		System.out.println("\n8-12 8-14(3)\n测试删除点\n"+d);
		L1.DeleteVex(d);
		L1.printAdjLGraph();
	
		//8-11(2) 8-14(2)测试非递归深度优先
		System.out.println("\n8-11(2) 8-14(2)\n栈结构实现深度搜索");
		L1.DepthFSearch(1);
		//8-14(2)测试广度优先
		System.out.println("\n8-14(2)\n队列结构实现广度搜索");
		L1.BroadFSearch(1);		
	}

}
