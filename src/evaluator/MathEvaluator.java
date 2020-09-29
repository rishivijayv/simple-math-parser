package evaluator;

import java.util.Stack;


public class MathEvaluator {

    public int evaluate(String postfix){
        Stack<String> answerStack = new Stack<>();
        String[] symbols = postfix.split("\\s+");

        for(String symbol : symbols){
            // Is a number
            if(symbol.matches("[0-9]+")){
                answerStack.push(symbol);
            }else{
                // Is a binary operator. Pop the last two elements pushed to stack and perform operation
                int right = Integer.parseInt(answerStack.pop());
                int left = Integer.parseInt(answerStack.pop());
                int answer = performBinaryOperation(left, right, symbol);
                answerStack.push(Integer.toString(answer));
            }
        }

        // The last remaining number on the answer stack is the final answer
        return Integer.parseInt(answerStack.pop());
    }

    private int performBinaryOperation(int leftNum, int rightNum, String operation){
        int answer = 0;
        switch(operation){
            case "+":
                answer = leftNum + rightNum;
                break;
            case "-":
                answer = leftNum - rightNum;
                break;
            case "*":
                answer = leftNum * rightNum;
                break;
            case "/":
                answer = leftNum / rightNum;
        }

        return answer;
    }
}
