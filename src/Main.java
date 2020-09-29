import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import evaluator.MathEvaluator;
import tokenizer.Tokenizer;
import parser.Parser;

public class Main {

    private static Tokenizer tokenizer;
    private static Parser parser;
    private static MathEvaluator evaluator;
    private static final String ALLOWED_MATH_OPERATORS = "+-*/";
    private static final String ASSIGNMENT_OPERATOR = "=";

    public Main(){
        tokenizer = new Tokenizer(ALLOWED_MATH_OPERATORS + ASSIGNMENT_OPERATOR + "()");
        Map<String, Integer> mathPrecedence = new HashMap<>();
        mathPrecedence.put("*", 2);
        mathPrecedence.put("/", 2);
        mathPrecedence.put("+", 1);
        mathPrecedence.put("-", 1);
        parser = new Parser(mathPrecedence, ALLOWED_MATH_OPERATORS);
        evaluator = new MathEvaluator();
    }

    public static void main(String[] args){
        Main m = new Main();
        Scanner scan = new Scanner(System.in);
        System.out.print(">>> ");
        String expr = scan.nextLine();
        Map<String, Integer> environment = new HashMap<>();

        while(!expr.trim().equalsIgnoreCase("exit")){
            List<String> tokens = tokenizer.tokenize(expr);
            if(m.isAssignment(tokens)){
                m.updateEnvironment(tokens, environment);
            }else{
                try {
                    String postfix = parser.parseToPostfix(tokens, environment);
                    System.out.println(evaluator.evaluate(postfix));
                }catch(Exception e){
                    System.out.println("An error occured while parsing the expression. Please try again.");
                }
            }
            System.out.print(">>> ");
            expr = scan.nextLine();
        }
    }

    private boolean isAssignment(List<String> tokens){
        return tokens.size() > 1 && tokens.get(1).equals("=");
    }

    private void updateEnvironment(List<String> tokens, Map<String, Integer> env){
        try {
            int value = evaluator.evaluate(parser.parseToPostfix(tokens.subList(2, tokens.size()), env));
            env.put(tokens.get(0), value);
        }catch(Exception e){
            System.out.println("An error occured while parsing the expression. Please try again.");
        }
    }
}
