/*
 * 结点数组类
 */
package 邻接表图;

public class AdjLHeight {
	Object data;
	int sorce;
	Edge adj;
	AdjLHeight(Object a,int b){
		data=a;
		sorce=b;
		adj=null;
	}
	void copy(AdjLHeight A){
		data=A.data;
		sorce=A.sorce;
		adj=A.adj;
	}
}
