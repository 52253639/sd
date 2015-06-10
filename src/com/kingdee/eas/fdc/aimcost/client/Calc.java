package com.kingdee.eas.fdc.aimcost.client;
import java.math.BigDecimal;
import java.util.Stack;

public class Calc {  
  
    /** 
     * 常用加减乘除括号字符 
     */  
    private static final String OPERATOR = "+-*/()";  
    /** 
     * 算法符号的优先级二维表 
     */  
    private static int[][] OPERATORCOMPARE = new int[][] { { 0, 0, -1, -1, -1, 1 },  
            { 0, 0, -1, -1, -1, 1 }, { 1, 1, 0, 0, -1, 1 },  
            { 1, 1, 0, 0, -1, 1 }, { 1, 1, 1, 1, 0, 1 },  
            { -1, -1, -1, -1, -1, 0 } };  
    /** 
     * 特殊字符用于标记给定表达式结尾 
     */  
    private static final char ENDCHAR='\n';  
      
    /** 
     * JAVA 执行入口 
     *  
     * @param args 
     */  
    public static void main(String[] args) {  
        println(OPERATOR, OPERATORCOMPARE);  
        String solution = "((2*(19-13*(1+2)/39)/6+4)-5)/5+((2+3)*2-5)";  
        System.out.println(solution+"="+calc(solution));  
    }  
  
    /** 
     * @param solution 
     */  
    public static BigDecimal calc(String solution) {  
        //需要给表达式添加结束符  
        solution+=ENDCHAR;  
        Stack<BigDecimal> digital = new Stack<BigDecimal>();  
        Stack<Character> opeators = new Stack<Character>();  
        int begin = 0;  
        int length = 0;  
        char current = 0;  
        for (int i = 0; i < solution.length(); i++) {  
            current = solution.charAt(i);  
            //解析数字  
            if (('0' <= current && current <= '9')||current=='.') {  
                length++;  
                continue;  
            } else {  
                //解析的数字压入数字栈  
                if (length > 0) {  
                    digital.add(new BigDecimal(solution.substring(begin, begin + length)));  
                    begin += length;  
                    length = 0;  
                }  
                begin++;  
                //如果扫描结束,但数字栈及符号栈还有数据需要计算,比如1+2+(1+3),不进行此步骤"[3, 4],[+]"为两个栈的元素  
                if (ENDCHAR == current) {  
                       while(!opeators.isEmpty()){  
                           operator(digital, opeators);  
                       }  
                } else {  
                    //处理当前操作字符,无限循环,处理当前操作字符后跳出循环  
                    while (true) {  
                        if (opeators.isEmpty()) {  
                            opeators.push(current);  
                            break;  
                        } else {  
                            if (OPERATORCOMPARE[OPERATOR.indexOf(current)][OPERATOR  
                                    .indexOf(opeators.lastElement())] > 0) {  
                                opeators.push(current);  
                                break;  
                            } else if (opeators.lastElement() == '('  
                                    && current == ')') {  
                                opeators.pop();  
                                break;  
                            } else if (opeators.lastElement() == '('  
                                    && current != ')') {  
                                opeators.push(current);  
                                break;  
                            } else {  
                                operator(digital, opeators);  
                            }  
                        }  
                    }  
                }  
  
            }  
        }  
        System.out.println(digital);  
        System.out.println(opeators);  
        return digital.pop();  
    }  
  
    /** 
     * 从符号栈弹出一个符号,从数字栈弹出两个数字,进行数学运算并压入数字栈 
     * @param digital 
     * @param opeators 
     */  
    private static void operator(Stack<BigDecimal> digital,  
            Stack<Character> opeators) {  
        BigDecimal number1=digital.pop();  
        BigDecimal number2=digital.pop();  
        switch (opeators.pop()) {  
        case '+':  
            digital.push(number2.add(number1));  
            break;  
        case '-':  
            digital.push(number2.subtract(number1));  
            break;  
        case '*':  
            digital.push(number2.multiply(number1));  
            break;  
        case '/':  
            digital.push(number2.divide(number1,4,BigDecimal.ROUND_HALF_UP));  
            break;  
        }  
    }  
  
    /** 
     * 打印符号优先级二维表 
     *  
     * @param operator 
     * @param operatorCompare 
     */  
    private static void println(String operator, int[][] operatorCompare) {  
        System.out.print(" \t");  
        for (int i = 0; i < operator.length(); i++) {  
            System.out.print(operator.charAt(i));  
            System.out.print('\t');  
        }  
        for (int i = 0; i < operatorCompare.length; i++) {  
            System.out.println();  
            System.out.print(operator.charAt(i));  
            System.out.print('\t');  
            for (int j = 0; j < operatorCompare[i].length; j++) {  
                System.out.print(operatorCompare[i][j] == 0 ? '='  
                        : (operatorCompare[i][j] > 0 ? '>' : '<'));  
                System.out.print('\t');  
            }  
        }  
        System.out.println();  
    }  
  
} 