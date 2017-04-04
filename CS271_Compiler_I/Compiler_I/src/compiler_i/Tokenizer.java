
package compiler_i;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author kucukdurmaza17
 */
public class Tokenizer {
    Tokenizer ( ArrayList <String> allCodes ){
        for( int i = 0; i<allCodes.size(); i++){
           if( allCodes.get(i).length()>0 && //bound check for blank lines
             ( !allCodes.get(i).contains("//") && !allCodes.get(i).contains("/*")
                   && allCodes.get(i).trim().charAt(0)!='*')) //comment check
            tokenMe.add( allCodes.get(i).replaceAll("(?!\")\\p{Punct}", " $0 "));//*** 
        }//cant believe how beautiful this method is ^! 
    }//***: replace all if not ("), then take that part add ' ' both sided put it back :D 
    private static  ArrayList<String> tokenMe = new ArrayList <String>();
    private static ArrayList <String> tokenized = new ArrayList <String>();
    private static final  String[] kw = {
        "class", "constructor", "function", "method", "field",
        "static",  "boolean", "void", 
        "true", "false", "null", "this", "let", "do", "if", 
        "else", "while", "return"
    };
    private static final String[] symbols = {"{", "}", "(", ")", "[", "]", ".",
        ",", ";",  "+", "-", "*", "/", "&", "|", "<", ">", "=", "~"};

    public void tokenizer() {  
        tokenized.add(0, "<tokens>\n");
        for(int i=0; i<tokenMe.size(); i++){
            StringTokenizer st = new StringTokenizer(tokenMe.get(i));
            while (st.hasMoreTokens()) {
                boolean flag = false;
                String next = st.nextToken(); //kw
                for( String s : kw){
                    if (next.contains(s)){
                        String tokenReady = "<keyword> " + next + " </keyword>\n";
                        tokenized.add(tokenReady);
                        flag = true;
                    }
                }
                if(flag){  }     //to avoid <kw> <identifier> doubling 
                else if (next.matches("\\p{Punct}+")){ //symbol
                    String tokenReady = " ";
                    if(next.contains("<")) 
                        tokenReady = "<symbol> &lt </symbol>\n"; 
                    else if (next.contains("<")) 
                        tokenReady = "<symbol> &gt </symbol>\n"; 
                    else if (next.contains(">"))
                        tokenReady = "<symbol> &quot </symbol>\n"; 
                    else if (next.contains("\""))
                        tokenReady = "<symbol> &quot </symbol>\n";
                    else if (next.contains("&"))
                        tokenReady = "<symbol> &amp </symbol>\n";
                    else 
                        tokenReady = "<symbol> " + next + " </symbol>\n";
                    tokenized.add(tokenReady);
                }
                else if( next.matches("\\p{Digit}+")){ //check for number chars
                    String tokenReady = "<integerConstant> " + next +
                            " </integerConstant>\n";
                    tokenized.add(tokenReady);
                }
                else if( next.contains("\"")){ //contains quotes 
                    next = next.substring(1, next.length()-1); //strip quotes
                    String tokenReady = "<stringConstant> " + next +
                            " </stringConstant>\n";
                    tokenized.add(tokenReady);
                }
                else if ( next.matches("\\p{Lower}+")){
                    
                    String tokenReady = "<identifier> " + next +
                            " </identifier>\n";
                    tokenized.add(tokenReady);
                }
            }
        }
        tokenized.add("</tokens>\n");
    }
    
    public void printTokens(){ 
        for (String st : tokenized) {
            System.out.print(st);
        }
    }
    public ArrayList<String> getTokenized(){
        return tokenized;
    }

    
    
}
