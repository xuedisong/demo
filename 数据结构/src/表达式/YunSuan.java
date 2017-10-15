package 表达式;

import java.util.Stack;

public class YunSuan{
	public static double evaluateExpression(String expression){
    Stack<Double> operandStack = new Stack<Double>();        //存放操作数的栈
    Stack<Character> operatorStack = new Stack<Character>();//存放运算符的栈
    
    String[] charArr = expression.split(" ");        //将字符串分割成单个字符
    for(int i = 0; i < charArr.length; i++){
        if(charArr[i].trim().equals("")){        //如果字符串为空，则跳过此次循环
            continue;
        }else if(charArr[i].trim().equals("+") || charArr[i].trim().equals("-")){
            //如果字符串为"+"或者"-"，则执行栈中已存数据的加减乘除计算
            while(!operatorStack.isEmpty() &&
                    (operatorStack.peek() == '+' ||
                    operatorStack.peek() == '-' ||
                    operatorStack.peek() == '*' ||
                    operatorStack.peek() == '/')){
                processOneOperator(operandStack,operatorStack);
            }
            operatorStack.push(charArr[i].charAt(0));//将操作符压入操作符栈中
        }else if(charArr[i].trim().equals("*") || charArr[i].trim().equals("/")){
            //如果字符串为"*"或者"/"，则执行栈中已存数据的乘除计算
            while(!operatorStack.isEmpty() &&
                    (operatorStack.peek() == '*' ||
                    operatorStack.peek() == '/')){
                processOneOperator(operandStack, operatorStack);
            }
            operatorStack.push(charArr[i].charAt(0));
        }else if(charArr[i].trim().equals("(")){
            //如果遇到左括号，则将左括号压入操作符栈中
            operatorStack.push('(');
        }else if(charArr[i].trim().equals(")")){
            //如果遇到右括号，则计算栈中的数据，直到遇到左括号为止
            while(operatorStack.peek() != '('){
                processOneOperator(operandStack,operatorStack);
            }
            operatorStack.pop();//将进行过计算的左括号弹出
        }else{
            //如果遇到的是操作数，则将操作数直接压入操作数栈中
            operandStack.push(Double.parseDouble(charArr[i]));
        }
    }
    //对栈中数据进行计算，知道栈为空为止
    while(!operatorStack.isEmpty()){
        processOneOperator(operandStack,operatorStack);
    }
    //此时操作数栈中的栈顶元素也就是计算结果
    return operandStack.pop();
}

/**
 * 对操作符栈顶的一个操作符进行计算
 * @param operandStack
 * @param operatorStack
 */
public static void processOneOperator(Stack<Double> operandStack,Stack<Character> operatorStack){
    char op = operatorStack.pop();     //取操作符的栈顶元素
    double op1 = operandStack.pop();    //取操作数的栈顶元素
    double op2 = operandStack.pop();    //取操作数的栈顶元素
    if(op == '+'){                    //如果操作数为+，则执行两个操作数的求和操作，并将结果压入操作数栈中
        operandStack.push(op2 + op1);
    }else if(op == '-'){
        operandStack.push(op2 - op1);
    }else if(op == '*'){
        operandStack.push(op2 * op1);
    }else if(op == '/'){
        operandStack.push(op2 / op1);
    }
}
public static void main(String[] args) {

    // TODO Auto-generated method stub

    System.out.println("119.1+(28.2+37.3*(46.4-55.5)-4.6+(3/2)+1) = " + evaluateExpression("119.1 + ( 28.2 + 37.3 * ( 46.4 - 55.5 ) - 4.6 + ( 3 / 2 ) + 1 )"));

}
}