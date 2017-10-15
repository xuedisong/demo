/*我
 * Hafuman测试程序
 */
package 二叉树;
import java.util.*;
public class HuffTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BiTree B1=new BiTree();
		char[] key={'A','B','C','D','E'};
		int[] w={2,4,5,7,8};
		Map<Character, Integer> m=new HashMap<Character, Integer>();
		for(int i=0;i<w.length;i++){
			m.put(key[i],w[i]);
		}
		BiTreeNode root=B1.HuffmanTree(w);//链接哈夫曼树
		System.out.println("HuffmanTree:");
		B1.printTree(root, 0);//打印哈夫曼树
		System.out.println("\nHuffmanCode：");
		Vector<Integer> v=new Vector<Integer>();
		B1.HuffmanCode(root, v,m);
	}
}
