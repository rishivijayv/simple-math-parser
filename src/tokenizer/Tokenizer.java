package tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private String allowedNonNumericChars;

    public Tokenizer(String allowedNonNumericChars){
        this.allowedNonNumericChars = allowedNonNumericChars;
    }

    /*
     * Takes an infix math expression string and splits it into number and operator tokens
     * */
    public List<String> tokenize(String infixExpression){
        char[] infix = infixExpression.replaceAll("\\s+", "").toCharArray();
        List<String> tokens = new ArrayList<>();

        StringBuilder rollingNum = new StringBuilder();

        for(char c : infix){
            if(this.allowedNonNumericChars.contains(Character.toString(c))){
                // Non-number encountered, means the rolling number token has finished
                if(rollingNum.length() != 0){
                    tokens.add(rollingNum.toString());
                    rollingNum.setLength(0);
                }
                tokens.add(Character.toString(c));
            }else{
                rollingNum.append(c);
            }
        }

        // Append the final rolling number (if it exists)
        if(rollingNum.length() != 0){
            tokens.add(rollingNum.toString());
        }

        return tokens;
    }
}
