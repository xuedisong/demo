/*
 * 测试主函数
 */
package 邻接矩阵图;

public class MapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int e=5;
		Object a[]={'A','B','C','D','E'};
		RowColWeight[] rcw={new RowColWeight(0,1,10),new RowColWeight(0,4,20),
				new RowColWeight(1,3,30),new RowColWeight(2,1,40),new RowColWeight(3,2,50)};
		int n=5;
		AdjMGraph A1=new AdjMGraph(n,10000,a,rcw,e);
		
		//打印图
		System.out.println("结点集合为：");
		for(int i=0;i<A1.vertices.size();i++)
			System.out.print(A1.vertices.get(i).data+"  ");
		System.out.print("\n权值集合为：\n");
		for(int i = 0;i<A1.vertices.size();i++){
			for(int j=0;j<A1.vertices.size();j++)
				System.out.print(A1.edge[i][j]+"  ");
			System.out.println();
		}
		
		int k=0;
		//8-9测试入度函数
		System.out.println("\n测试入度出度函数\n\n序号为"+k+"的节点的入度为："+A1.GetIn(k));
		
		//8-10测试出度函数
		System.out.println("序号为"+k+"的节点的出度为："+A1.GetOut(k));
		
		//8-11测试深度优先遍历
		System.out.println("\n测试遍历函数\n\n现在开始遍历……\n");
		System.out.println("初始搜索节点为"+A1.vertices.get(k).data);
		System.out.println("栈结构的深度搜索入栈访问");		
		A1.DepthFSearch_1(k);
		System.out.println("栈结构的深度搜索出栈访问");
		A1.DepthFSearch_2(k);
		System.out.println("栈的内部流");
		A1.DepthFSearch_3(k);
		
		//8-13测试路径函数
		int l=3;
		System.out.println("\n开始深度搜索从"+A1.vertices.get(k).data+"到"+A1.vertices.get(l).data+"的路径……\n");
		A1.SearchPath(k, l);
		
	}

}
