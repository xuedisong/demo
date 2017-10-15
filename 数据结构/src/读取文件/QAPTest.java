package 读取文件;
import java.io.BufferedReader;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  
  
public class QAPTest {  
    public static void main(String[] args) throws IOException {  

        File f = new File("D://mazeData.txt");  
        BufferedReader buf = new BufferedReader(new FileReader(f));  
  
        int[][] city= new int[128][126];  
        int line = 0;  
  
        String str = null;  
  
        while ((buf.read()) != -1) {  
  
            str = buf.readLine();  
  
            String[] date = str.split(" ");  
  
            for (int i = 0; i < date.length; i++) {  
                city[line][i] = Integer.parseInt(date[i]);  
                System.out.print(city[line][i]);  
            }  
  
            line++;  
  
        }  
  
    }  
  
}