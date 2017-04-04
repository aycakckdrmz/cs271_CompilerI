
package compiler_i;

import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * @author kucukdurmaza17
 */
public class CompilerParser {
    CompilerParser(ArrayList <String> s){        
        tokens = s;        
    }
    private ArrayList <String> parsed = new ArrayList <String>();
    private ArrayList <String> tokens = new ArrayList <String>();
    private String subrRegex = "constructor|function|method";
    private String statementsRegex = "if|let|while|do|return";
    private String cVDRegex = "static|field";
    public void compilerClass(){
        int index = 1; //skip <tokens>
        parsed.add("<class>\n");
        parsed.add("    "+tokens.get(index));
        parsed.add("    "+tokens.get(++index));
        parsed.add("    "+tokens.get(++index));
        while(index+1 < tokens.size() && tokens.get(++index).split(" ")[1].matches(cVDRegex))
            index = classVarDec(index);
        while(index+1 < tokens.size() && tokens.get(index).split(" ")[1].matches(subrRegex))
            index = subroutineDec(index);
        //parsed.add(tokens.get(++index)); // push "}"
        parsed.add("</class>\n"); // The End whoooo !! 
    } 
    //all fuctions will return index so we can keep track of it
    public int expressionList(int index){
        boolean noMoreExp = false;
        parsed.add("               <expressionList>\n");
        String st = "";
        while (index < tokens.size() && !noMoreExp ){
            if(tokens.get(index+1).contains(")")||tokens.get(index+1).contains(";")){
                noMoreExp = true;
            }
            else
                index = expression(index);
        }
        parsed.add("               </expressionList>\n");
        return index;
    }
    public int expression(int index){
        boolean  noMoreTerm = false;
        parsed.add("              <expression>\n");
        while (index < tokens.size()-1 && !noMoreTerm ){
            if(tokens.get(index+1).contains(")")||tokens.get(index+1).contains(";")
                   ||tokens.get(index+1).contains(",")){
                noMoreTerm = true;
            }
            if (tokens.get(index).contains("symbol"))
                parsed.add( "                 "+tokens.get(index));
            else {
                parsed.add( "                 <term>\n"); 
                parsed.add( "                    "+tokens.get(index));
                parsed.add( "                 </term>\n");
            }
            index++;
        }
        parsed.add( "                 "+tokens.get(index));//push ')'
        parsed.add("              </expression>\n"); 
        return index;
    }
 
    public int subroutineBody(int index){
        String temp = "";
        parsed.add("    <subroutineBody>\n");
        parsed.add("    "+tokens.get(++index)); //push "{"
        index = letDoRetunStatements(index);
        index = ifWhileStatements(index);
        parsed.add("    </subroutineBody>\n");
        return index;
    }
    public int paramaterList(int index){
        boolean noMoreParam = false;
        parsed.add("        "+tokens.get(index)); //push "("
        parsed.add("        <parameterList>\n");
        while(!noMoreParam && index < tokens.size()){
            if(tokens.get(index+1).contains(")"))
                noMoreParam = true;
            else
                parsed.add("            "+tokens.get(++index));
        }
        parsed.add("        </parameterList>\n");
        parsed.add("        "+tokens.get(++index)); //push ")"
        return index;
    }    
    public int classVarDec(int index){
        boolean noMoreCVD = false;
        parsed.add("    <classVarDec>\n");
        parsed.add("        "+tokens.get(index));
        while(!noMoreCVD && index < tokens.size()){
            if(tokens.get(index+1).contains(";"))
                noMoreCVD = true;
            else
                parsed.add("        "+tokens.get(++index));
        }
        parsed.add("        "+tokens.get(++index));
        parsed.add("    </classVarDec>\n");
        return index;
    }    
    public int subroutineDec (int index){ 
        parsed.add("  <subroutineDec>\n");
        while(!tokens.get(index).contains("("))
            parsed.add("        "+tokens.get(index++)); //push till parameters
        index = paramaterList(index); //1 SR Dec = bunch stuff + paramList + subrBody
        index = subroutineBody(index);
        parsed.add("  </subroutineDec>\n");
        return index;
    }
    public ArrayList <String> getParsed(){
        return parsed;
    }
    public int letStatement(int index){
        parsed.add("        <letStatement>\n");
        parsed.add("            "+tokens.get(index)); //push let
        parsed.add("            "+tokens.get(++index)); //push identifier 
        parsed.add("            "+tokens.get(++index)); //push =
        index = expression(++index);
        parsed.add("        </letStatement>\n");
        return index;
    }
    
    public int doStatement ( int index ){
        parsed.add("        <doStatement>\n");
        parsed.add("            "+tokens.get(index)); 
        while(index-1<tokens.size()&&!tokens.get(index+1).contains("(")){
            parsed.add("            "+tokens.get(++index)); 
       }
        parsed.add("            "+tokens.get(++index)); //push '('
        index = expressionList(++index);
        parsed.add("            "+tokens.get(index)); //push ')'
        parsed.add("            "+tokens.get(++index)); //push';'
        parsed.add("        </doStatement>\n");
        return index;
    }
    
    public int ifStatement (int index){
        parsed.add("        <ifStatement>\n");
        parsed.add("            "+tokens.get(index)); //push if
        parsed.add("            "+tokens.get(++index)); // '('
        index = expression(++index);
        parsed.add("            "+tokens.get(index)); // push ')'
        parsed.add("            "+tokens.get(++index)); //push'{'
        index = letDoRetunStatements(index);
        parsed.add("            "+tokens.get(index)); //push '}'
        parsed.add("        </ifStatement>\n");
        return index;
    }
    public int returnStatement (int index){
        parsed.add("        <returnStatement>\n");
        parsed.add("            "+tokens.get(index)); //push return
        parsed.add("            "+tokens.get(++index)); //push 'this'
        parsed.add("            "+tokens.get(++index)); //push ';'
        parsed.add("        </returnStatement>\n");
        return index;
    }
    public int letDoRetunStatements (int index){
        boolean noMoreStats = false;
        parsed.add("      <statements>\n");
        while (!noMoreStats && index < tokens.size()){
            if(tokens.get(index).contains("}"))
                noMoreStats = true;
            else{  
                if(tokens.get(index).contains("let"))
                    index = letStatement(index);
                else if(tokens.get(index).contains("do"))
                    index = doStatement(index);
                else if(tokens.get(index).contains("return"))
                    index = returnStatement(index);
            }
            index++;
        }
        parsed.add("      </statements>\n");
        return index;
    }
    public int whileStatement (int index){
        parsed.add("        <whileStatement>\n");
        parsed.add("            "+tokens.get(index)); //push while
        parsed.add("            "+tokens.get(++index)); // '('
        index = expression(++index);
        parsed.add("            "+tokens.get(index)); // push ')'
        parsed.add("            "+tokens.get(++index)); //push'{'
        index = letDoRetunStatements(index);
        parsed.add("            "+tokens.get(index)); //push '}'
        parsed.add("        </whileStatement>\n");
        return index;
    }
    
    public int ifWhileStatements (int index){
        boolean noMoreStats = false;
        while (!noMoreStats && index < tokens.size()){
            if(tokens.get(index).contains("}"))
                noMoreStats = true;
            else{  
                if(tokens.get(index).contains("if"))
                    index = ifStatement(index);
                else if(tokens.get(index).contains("while"))
                    index = whileStatement(index);
            }
            index++;
        }
        return index;
    }
}
