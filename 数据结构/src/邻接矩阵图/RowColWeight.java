/*
 * 由边，及边的两个端结点作为数据成员封装的类
 */
package 邻接矩阵图;

public class RowColWeight {
	public int row;
	public int col;
	int weight;
	public RowColWeight(int r,int c,int w){
		row=r;
		col=c;
		weight=w;
	}
}
