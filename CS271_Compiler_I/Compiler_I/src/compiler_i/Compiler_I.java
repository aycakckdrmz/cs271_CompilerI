/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler_i;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author kucukdurmaza17
 */
public class Compiler_I {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        /* TEST_CASE_1 " test String.replace() method"  
        // expected: output "  ( test )  deneme  ( 0 )   { }  "
        String st = " (test) deneme (0) {}  ";
        String replaced = st.replace("("," ( ").replace(")", " ) ").replace("{", " { ");
        System.out.println(replaced);
        // output "  ( test )  deneme  ( 0 )   { }  " - PASSED 
        */
        
        /* TEST_CASE_2 "test StringTokenizerLibrary" 
        //expected: tokenize it possible white space removed? 
        StringTokenizer st = new StringTokenizer("  ( test )  deneme  ( 0 )   { }  ");
        while (st.hasMoreTokens()) {
                System.out.println(st.nextToken());
        }
        // test outputs tokens perfecly with no white space - PASSED 
        */
        
        /* TEST_CASE_3 "tokenizing demonstration"
        //expected: output "<symbol> ( </symbol>"
        StringTokenizer st = new StringTokenizer("  (  ");
        while (st.hasMoreTokens()) {
            String next = st.nextToken();
            if (next.equals("(")){
                String tokenized = "<symbol> " + next + " </symbol>";
                System.out.println(tokenized);
            }
        }
        //test outputs expected - PASSED
        */
        
        /* TEST_CASE_4 "ReplaceAll() method and regex test"
        //expected: outputing  ( test )  deneme  ( 0 )   { x }  
        String st2 = " (test) deneme (0) {x}  ";
        String replaced2 = st2.replaceAll("\\p{Punct}+", " $0 ");
        System.out.println(replaced2);
        // outputs "  ( test )  deneme  ( 0 )   { x }   " - PASSED 
        */
        
        /*TEST_CASE_5 "Test stringTokenizer with regex to do it all at once" 
        //expected: 5 different symbol tokens
        StringTokenizer st = new StringTokenizer("  (  * ) { / ");
        while (st.hasMoreTokens()) {
            String next = st.nextToken();
            if (next.matches("\\p{Punct}+")){
                String tokenized = "<symbol> " + next + " </symbol>\n";
                System.out.println(tokenized);
            }
        // PASSED! so perfect im loving it :D 
        }*/
        
        /*TEST_CASE_6 "testing tokenizer constructor"
        // expected space added after every symbol
        ArrayList<String> test = new ArrayList<String> ();
        test.add("(x) {y} *xy-x");
        test.add("if a(c) then x%test");
        Tokenizer tk = new Tokenizer (test);
        tk.printTokens();
        //Output: "( x ) { y }  * xy - x \n  if a ( c )  then x % test" -PASSED
        */
        
        /* TEST_CASE_7 "test keyword tokenizing "
        //expected: 4 kw tokens 
        StringTokenizer st = new StringTokenizer(" var x int test char");
        String [] stAr = {"var", "int", "char"};
        while (st.hasMoreTokens()) {
            String next = st.nextToken();
            for( String s : stAr){
                if (next.contains(s)){
                    String tokenized = "<keyword> " + next + " </keyword>\n";
                    System.out.println(tokenized);
                }
            }
        } // PASSED! 
                */
        
        /* TEST_CASE_8 "tokenizer last test"
        //expected to pass every feature of tokenizer 
        ArrayList<String> test = new ArrayList<String> ();
        test.add("(x) {y}21 *xy-x if return \"testString\"  265 ");
        test.add("if a(c) AC then 852 x%test");
        test.add("null /void");
        FileIOParser parse = new FileIOParser ("Square.jack");
        //Tokenizer tk = new Tokenizer (test);
        //tk.tokenizer();
        //tk.printTokens();   
        ArrayList<String> test2 = parse.getCommandList();
        Tokenizer tk2 = new Tokenizer (test2);
        tk2.tokenizer();
        tk2.printTokens();        
        parse.writeIntoFile(tk2.getTokenized());
        System.out.println(test2.size());
        // PASSED
        */        
        
        /*  TEST_CASE_9 "test split method"
        String st = "<keyword> do </keyword>";
        System.out.println(st.split(" ")[1]);
        //PASSED
        */
        
        
        
        FileIOParser jack2token = new FileIOParser ("Square.jack");
        Tokenizer tk = new Tokenizer (jack2token.getCommandList());
        tk.tokenizer();
        tk.printTokens();        
        jack2token.writeIntoFile(tk.getTokenized(), "TokenizedSquare");
        FileIOParser tokens = new FileIOParser ("SquareT.xml");
        CompilerParser cp = new CompilerParser (tokens.getCommandList());
        cp.compilerClass();
        tokens.writeIntoFile(cp.getParsed(), "ParsedSquare");
        
        
        
        
        
        
        
        
    }
    
}
