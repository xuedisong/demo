package 迷宫;
import java.io.*;
import java.util.Stack;
class Step{
    int x,y,d;
    public Step(int x,int y,int d) {
        this.x = x;//横坐标
        this.y = y;//纵坐标
        this.d = d;//方向
    }
}

public class MazeTest {
	
    public static void main(String[] args) throws IOException{
    	int[][] Temp=duQv("D:\\mazeData.txt");
    	//maze添加围墙
    	int[][] maze=new int[130][128];
    	for(int i=0;i<128;i++){
    		maze[0][i]=1;
    		maze[129][i]=1;
    	}
    	for(int i=0;i<130;i++){
    		maze[i][0]=1;
    		maze[i][127]=1;
    	}
    	for(int i=0;i<128;i++){
    		for(int j=0;j<126;j++){
    			maze[i+1][j+1]=Temp[i][j];
    		}
    	}
    	
        int[][] move = {{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},};
        Stack s = new Stack();
        Stack s1 = new Stack();
        int a = path(maze, move, s);
        //逆置栈
        while(!s.isEmpty()){
            s1.push((Step)s.pop());
        }
        while(!s1.isEmpty()){
            Step step = (Step)s1.pop();
            System.out.print(step.x+":"+step.y+"   ");
        }
 
    }
    
	public static int[][] duQv(String wenjian){
		// 初始化一个用于存储txt数据的数组
		int[][] maze = new int[128][126];
		String[][] zfsz= new String[128][126];
		int index = 0;
		BufferedReader br = null;
		try {
			// 读文件了. 路径就是那个txt文件路径,将txt内容沾到word查看格式，并在关闭后，运行程序，以防止大量数据记事本莫名卡顿。
			br  = new BufferedReader(new FileReader(new File(wenjian)));
			String str = null;
			// 按行读取
			while((str=br.readLine()) != null){
        	
				zfsz[index]=str.split(" ");
				String[] date=zfsz[index];
				//字符数组转化成整形数组开始
				for (int i = 0; i < date.length; i++) {  
					maze[index][i] = Integer.parseInt(date[i]);  
					//System.out.println(maze[index][i]);  
				}
				//字符数组转化成整形数组结束
      
				index++;
			}                                               
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return maze;
	}
	
    public static int path(int[][] maze,int[][] move,Stack s){
        Step temp = new Step(1,1,-1); //起点
        s.push(temp);
        s.push(new Step(1,1,-1));
        while(!s.isEmpty()){
            temp = (Step) s.pop();
            Step temp1=(Step)s.peek();
            int x = temp1.x;
            int y = temp1.y;
            int d = temp.d+1;
            while(d<8){
                int i = x + move[d][0];
                int j = y + move[d][1];
                if(maze[i][j] == 0){    //该点可达
                    temp = new Step(i,j,d); //到达新点
                    s.push(temp);
                    x = i;
                    y = j;
                    maze[x][y] = -1;  //到达新点，标志已经到达
                    if(x == 128 && y == 126){
                        return 1;  //到达出口，迷宫有路，返回1
                    }else{
                        d = 0;  //重新初始化方向
                    }
                }else{
                    d++; //改变方向
                }
            }
        }
        return 0;
    }

}


