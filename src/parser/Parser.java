package parser;

import java.util.List;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;


public class Parser {

    private Map<String, Integer> mathPrecedence;
    private String allowedMathOperators;

    public Parser(Map<String, Integer> mathPrecedence, String allowedMathOperators){
        this.mathPrecedence = mathPrecedence;
        this.allowedMathOperators = allowedMathOperators;
    }

    /*
    * Parses the tokenized infix math expression into a postfix (Reverse Polish Notation)
    * expression that can be easily evaluated
    *
    * Implements the Shunting-Yeard algorithm as found on brilliant.org:
    * https://brilliant.org/wiki/shunting-yard-algorithm/
    * */
    public String parseToPostfix(List<String> tokens, Map<String, Integer> env) throws Exception{
        Stack<String> operatorStack = new Stack<>();
        Queue<String> outputQueue = new LinkedList<>();

        for(String token : tokens){
            // Checks if the token is a number
            if(env.containsKey(token)){
                // Token might be a variable in the environment. So first, look this up
                outputQueue.add(Integer.toString(env.get(token)));
            }else if(token.matches("[0-9]+")){
                outputQueue.add(token);
            }else if(allowedMathOperators.contains(token)){
                while(higherPrecedenceOperatorOnTopOfStack(operatorStack, token)){
                    outputQueue.add(operatorStack.pop());
                }
                // All operations to occur "before" the operation of this operator have been added to output queue.
                operatorStack.push(token);
            }else if(token.equals("(")){
                operatorStack.push(token);
            }else if(token.equals(")")){
                // Look for first openinig bracket while adding operators between these brackets to the output queue
                while(openingBracketNotOnTopOfStack(operatorStack)){
                    outputQueue.add(operatorStack.pop());
                }

                // either found an opening bracket or the stack is empty
                if(operatorStack.empty()){
                    // bracket mismatch;
                    throw new Exception();
                }
                // The closing bracket was found. So, discard it
                operatorStack.pop();
            }else {
                // This token is not a valid token. throw an exception
                throw new Exception();
            }
        }

        while(!operatorStack.empty()){
            outputQueue.add(operatorStack.pop());
        }

        // Return a space separated RPN
        StringBuilder postfix = new StringBuilder();

        while(!outputQueue.isEmpty()){
            postfix.append(outputQueue.poll());
            if(!outputQueue.isEmpty()){
                // Only add space if this was not the last element of the queue
                postfix.append(" ");
            }
        }

        return postfix.toString();
    }


    private boolean higherPrecedenceOperatorOnTopOfStack(Stack<String> operatorStack, String operator){
        if(!operatorStack.empty()){
            return allowedMathOperators.contains(operatorStack.peek()) &&
                    mathPrecedence.get(operatorStack.peek()) > mathPrecedence.get(operator);
        }else {
            return false;
        }
    }

    private boolean openingBracketNotOnTopOfStack(Stack<String> operatorStack){
        return !operatorStack.empty() && !operatorStack.peek().equals("(");
    }

}