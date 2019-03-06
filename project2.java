import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class project2 {
    static ArrayList<String> tokOut = new ArrayList<String>();
    static ArrayList<String> ID = new ArrayList<String>();
    static ArrayList<String> fn = new ArrayList<String>();
    static ArrayList<String> num = new ArrayList<String>();
    
    static String[] keywords = {"else","if","int","return","void","while","float"};
    static String[] symbols = {"+","-","*","/","<",">","=",";",",","(",")","[","]","{","}"};
    static String[] doubleSymbols = {"<=",">=","==","!="};
    static int tokCounter = 0;
    static int indexOf = 0;
    static String currentToken;
    
    
    
    /*public static ArrayList<String> readFromFile(String name)
    {
        ArrayList<String> text = new ArrayList<String>();
        try
        {
            File inFile = new File(name);
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine())
            {
                text.add(sc.nextLine());
            }
        }
        catch(Exception e)
        {
            System.out.println("Problem opening file!!");
        }

        return text;
    }*/
    
    public static ArrayList<String> tokenizeCode(ArrayList<String> theCode)
    {
        ArrayList<String> to = new ArrayList<>();
        
        boolean comment = false; /* comment */
        boolean line_comment = false; // line comment
        // Everything happens here!
        int y = 0;
        int commentOpenCounter = 1;// counter for the nested comment
        int commentCloseCounter = 1;
        //int commentDepth = -1;
        for(String line : theCode)
        {
            line_comment = false;
            int x = 0;
            //System.out.println("Next Line========================================================================");
            
            int commentDepth = -1;
            
            //System.out.println("\nInput : " + line);
            while(x < line.length())
            {
                boolean found = false;
                
                if(x < line.length() - 1 && commentDepth !=0)
                {
                  if(line.substring(x, x+2).equals("/*"))
                  {
                  
                     comment = true;
                     found = true;
                     commentOpenCounter++;
                     //System.out.println(commentOpenCounter);
                     //y = x;
                     x+=2;
                     
                     
                  }
                  else if(line.substring(x, x+2).equals("//"))
                  {
                      line_comment = true;
                      found = true;
                      x+=2;
                  }
                  else if(line.substring(x, x +2).equals("*/"))
                  {
                     if(commentOpenCounter-commentCloseCounter != 0)
                     {
                        comment = false;
                        found = true;
                        commentCloseCounter++;
                        //System.out.println(commentCloseCounter);
                        x+=2;
                     }
                     else
                     {
                        commentDepth = 0;
                     }
                     /*else if(commentDepthCounter ==0)
                     {
                        comment = false;
                        found = true;
                        //line.replace(line.substring(y,x),"");
                        System.out.println(commentDepthCounter);
                        //System.out.println("after delete: " + line);
                        x++;
                      }*/ 
                        
                  }
                }
                
                if(!comment && !line_comment)
                {
                  if(!found)
                  {
                    for(String symbol : doubleSymbols)
                    {
                       if((x + symbol.length()) <= line.length())
                       {
                          if(line.substring(x, x+symbol.length()).equals(symbol)){
                               
                               //System.out.println("doubleSymbol: " + symbol);
                               
                               to.add(symbol);
                               x += 2;
                               found = true;
                          }
                       }
                    }
                  }
                  if(!found)
                  {
                     for(String word : keywords)
                     {
                        if((x + word.length()) <= line.length())
                        {
                            if(line.substring(x, x+word.length()).equals(word)){
                                 //System.out.println("Keyword: " + word);
                                 to.add(word);
                                 x += (word.length());
                                 found = true;
                            }
                         }
                          
                     }
                  }
                  if(!found)
                  {
                     for(String symbol : symbols)
                     {
                        if((x + symbol.length()) <= line.length())
                        {
                            if(line.substring(x, x+symbol.length()).equals(symbol))
                            {
                                 //System.out.println("Symbol: " + symbol);
                                 to.add(symbol);
                                 x++;
                                 found = true;
                            }
                        }
                            
                     }
                  }   
                  if(!found)
                  {
                     int l = 0;
                     while(line.charAt(x) >= 'a' && line.charAt(x) <= 'z' & x < line.length())
                     {
                        l++;
                        x++;
                     }
                     if (l > 0)
                     {
                        //System.out.println("Identifier: " + line.substring(x - l, x));
                        to.add(line.substring(x - l, x));
                        ID.add(line.substring(x - l, x));
                        found = true; 
                     }
                  }
                
                
                  if(!found)
                  {
                     if(x < line.length() && (line.charAt(x) >= '0' && line.charAt(x) <= '9'))
                     {
                        int l = 1;
                        x++;
                        boolean fl_num = false;
                        while(x < line.length() && ((line.charAt(x) >= '0' && line.charAt(x) <= '9') || (line.charAt(x) == '.') || (line.charAt(x) == 'E' && fl_num)))
                        {
                          if(line.charAt(x) == '.')
                             fl_num = true;
                          l++;
                          x++;
                        }
                        if(fl_num == true)
                        {
                           to.add(line.substring(x - l, x));
                           fn.add(line.substring(x - l, x));
                        }
                        else
                        {
                           to.add(line.substring(x - l, x));
                           num.add(line.substring(x - l, x));
                        }
                        //System.out.println((fl_num ? "Float Number: " : "Int Number: ") + line.substring(x - l, x));
                        //to.add(line.substring(x - l, x));
                        found = true; 
                      }
                    }
                
                }
            
                if(!found)
                {
                    if(line.charAt(x) != ' ' && !comment && !line_comment)
                        //System.out.println("Unexpected Character: " + line.substring(x,x+1));
                    x++;
                }
            }
        }//end of for loop
        return to;
        //System.out.println(theCode);
    }
    
    
    public static void main(String[] args)
    {
      
        ArrayList<String> text = new ArrayList<String>();
        try
        {
            File inFile = new File(args[0]);
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine())
            {
                text.add(sc.nextLine());
            }
        }
        catch(Exception e)
        {
            System.out.println("Problem opening file!!");
        }
        
        //ArrayList<String> tokOut = new ArrayList<String>();
      
      
      
        /*System.out.println("Enter name of file to analyze.");
        Scanner rd = new Scanner(System.in);
        String name = rd.nextLine();
        ArrayList<String> code = readFromFile(name);*/
        tokOut = tokenizeCode(text);
        tokOut.add("$");
        System.out.println("Tokens from Lexical Analyzer below");
        System.out.println(tokOut);
                   
        
        program();
        if(currentToken == "$")
        {
            System.out.println("ACCEPT");
        }
        else
            System.out.println("REJECT");
        
        
        
    }
    
   
    //=====parser start===================
    public static void program()
    {
      currentToken = tokOut.get(indexOf);
      
      declaration_list();
    }
    //====================================
    public static void declaration_list()
    {
      currentToken = tokOut.get(indexOf);
      
      declaration();
      declaration_list2();
    }
    //====================================
    public static void declaration()
    {
      currentToken = tokOut.get(indexOf);
      
      type_specifier();
      
      if(currentToken.equals(ID))
      {
         indexOf++;
         G();
      }
    }
    //====================================
    public static void declaration_list2()
    {
      currentToken = tokOut.get(indexOf);
      
      declaration();
      declaration_list2();
      
      if(currentToken != "$")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
      
    }
    //====================================
    public static void type_specifier()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "int" || currentToken == "float" || currentToken == "void")
      {
         indexOf++;
      }
    }
    //====================================
    public static void G()
    {
      currentToken = tokOut.get(indexOf);
      
      A();
      
      if(currentToken == "(")
      {
         indexOf++;
         params();
         if(currentToken == ")")
         {
          indexOf++;
          compound_stmt();
         }
      }
    }
    //=====================================
    public static void A()
    {
      currentToken = tokOut.get(indexOf);
      
      if(currentToken == ";")
      {
         indexOf++;
      }
      
      if(currentToken == "[")
      {
         indexOf++;
         if(currentToken.equals(num))
         {
            indexOf++;
            if(currentToken == "]")
            {
               indexOf++;
               if(currentToken == ";")
               {
                  indexOf++;
               }
            }
         }
      }
    }
    //=====================================
    public static void params()
    {
      currentToken = tokOut.get(indexOf);
      
      if(currentToken == "void")
      {
         indexOf++;
         voidParamCheck();
      }
      
      if(currentToken == "int")
      {
         indexOf++;
      
         if(currentToken.equals(ID))
         {
            indexOf++;
            B();
            param_list2();
         }
      }
      if(currentToken == "float")
      {
         indexOf++;
      
         if(currentToken.equals(ID))
         {
            indexOf++;
            B();
            param_list2();
         }
      }
    }
    //=====================================
    public static void compound_stmt()
    {
      currentToken = tokOut.get(indexOf);
      
      if(currentToken == "{")
      {
         indexOf++;
         local_declaration();
         allStatements();
         if(currentToken == "}")
            indexOf++;
      }
    }
    //======================================
    public static void voidParamCheck()
    {
      currentToken = tokOut.get(indexOf);
      
      if(currentToken.equals(ID))
      {
         indexOf++;
         B();
         param_list2();
      }
      if(currentToken != ")")
      {
         System.out.println("REJECT");
         System.exit(0);
      }   
    }
    //======================================
    public static void B()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "[")
      {
         indexOf++;
         if(currentToken == "]")
            indexOf++;
      }
      if(currentToken != "," && currentToken != ")")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //======================================
    public static void param_list2()
    {
      currentToken = tokOut.get(indexOf);
      
      if(currentToken == ",")
      {
         indexOf++;
         param();
         param_list2();
      }
      
      if(currentToken != ")")
      {
         System.out.println("REJECT");
         System.exit(0);
      }  
    }
    //=======================================
    public static void local_declaration()
    {
      currentToken = tokOut.get(indexOf);
      
      newDeclaration();
      local_declaration();
      
      if(currentToken != "}" && !currentToken.equals(ID) && currentToken != "(" && !currentToken.equals(num) && !currentToken.equals(fn) && currentToken != ";" && currentToken != "{" && currentToken != "if" && currentToken != "while" && currentToken != "return" )
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //=======================================
    public static void allStatements()
    {
      currentToken = tokOut.get(indexOf);
      
      statement();
      allStatements();
      
      if(currentToken != "}")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //=========================================
    public static void param()
    {
      currentToken = tokOut.get(indexOf);
      
      type_specifier();
      if(currentToken.equals(ID))
      {
         indexOf++;
         B();   
      }
    }
    //=========================================
    public static void newDeclaration()
    {
      currentToken = tokOut.get(indexOf);
      type_specifier();
      if(currentToken.equals(ID))
      {
         indexOf++;
         A();
      }
    }
    //=========================================
    public static void statement()
    {
      currentToken = tokOut.get(indexOf);
      
      expression_stmt();
      compound_stmt();
      selection_stmt();
      iterate_stmt();
      return_stmt();
    }
    //=========================================
    public static void expression_stmt()
    {
      currentToken = tokOut.get(indexOf);
      
      expression();
      if(currentToken == ";")
         indexOf++;
    }
    //=========================================
    public static void selection_stmt()
    {
      currentToken = tokOut.get(indexOf);
      
      if(currentToken == "if")
      {
         indexOf++;
         if(currentToken == "(")
         {
            indexOf++;
            expression();
            if(currentToken == ")")
            {
               indexOf++;
               statement();
               else_stmt();
            }
         }
      }
    }
    //=========================================
    public static void iterate_stmt()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "while")
      {
         indexOf++;
         if(currentToken == "(")
         {
            indexOf++;
            expression();
            if(currentToken == ")")
            {
               indexOf++;
               statement();
            }
         }
      }
    }
    //==========================================
    public static void return_stmt()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "return")
      {
         indexOf++;
         D();
      }
    }
    //==========================================
    public static void expression()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken.equals(ID))
      {
         indexOf++;
         remainingIDComponent();
      }
      if(currentToken == "(")
      {
         indexOf++;
         expression();
         if(currentToken == ")")
         {
            indexOf++;
            remainingExpressionComponent();
         }
      }
      if(currentToken.equals(num))
      {
         indexOf++;
         remainingExpressionComponent();
      } 
      if(currentToken.equals(fn))
      {
         indexOf++;
         remainingExpressionComponent();
      }
    }
    //=============================================
    public static void else_stmt()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "else")
      {
         indexOf++;
         statement();
      }
    }
    //=============================================
    public static void D()
    {
      currentToken = tokOut.get(indexOf);
      expression();
      if(currentToken == ";")
         indexOf++;
    }
    //=============================================
    public static void remainingIDComponent()
    {
      currentToken = tokOut.get(indexOf);
      variableArrayCheck();
      remainingVariableComponent();
      if(currentToken == "(")
      {
         indexOf++;
         arguments();
         if(currentToken == ")")
         {
            indexOf++;
            remainingExpressionComponent();
         }
      }
    }
    //=============================================
    public static void remainingExpressionComponent()
    {
      currentToken = tokOut.get(indexOf);
      moreTerms();
      moreAddExpression();
      relation();
    }
    //=============================================
    public static void variableArrayCheck()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "[")
      {
         indexOf++;
         expression();
         if(currentToken == "]")
         {
            indexOf++;
         }
      }
      if(currentToken != "=" && currentToken != "*" && currentToken != "/")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //==============================================
    public static void remainingVariableComponent()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "=")
      {
         indexOf++;
         expression();
      }
      else
         remainingExpressionComponent();
    }
    //==============================================
    public static void arguments()
    {
      currentToken = tokOut.get(indexOf);
      allArgs();
      if(currentToken != ")")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //==============================================
    public static void moreTerms()
    {
      currentToken = tokOut.get(indexOf);
      timeOperations();
      factor();
      moreTerms();
      if(currentToken != "<=" &&currentToken != "<" && currentToken != ">" && currentToken != ">=" && currentToken != "==" && currentToken != "!=" && currentToken != ")" && currentToken != ";" && currentToken != "]" && currentToken != "+" && currentToken != "-" && currentToken != ",")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //===============================================
    public static void moreAddExpression()
    {
      currentToken = tokOut.get(indexOf);
      addOperations();
      term();
      moreAddExpression();
      if(currentToken != "<=" &&currentToken != "<" && currentToken != ">" && currentToken != ">=" && currentToken != "==" && currentToken != "!=" && currentToken != ")" && currentToken != ";" && currentToken != "]" && currentToken != ",")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //=================================================
    public static void relation()
    {
      currentToken = tokOut.get(indexOf);
      operation();
      addExpression();
      if(currentToken != ")" && currentToken != ";" && currentToken != "]" && currentToken != ",")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //=================================================
    public static void allArgs()
    {
      currentToken = tokOut.get(indexOf);
      expression();
      moreArgs();
    }
    //===================================================
    public static void timeOperations()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "*" || currentToken == "/")
         indexOf++;
    }
    //==================================================
    public static void factor()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "(")
      {
         indexOf++;
         expression();
         if(currentToken == ")")
            indexOf++;
      }
      else if(currentToken.equals(num))
      {
         indexOf++;
      }
      else if(currentToken.equals(fn))
      {
         indexOf++;
      }
      else
      {
         variable();
         call();////////////////////////////////////////////////
      }
    }
    //====================================================
    public static void addOperations()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "+" || currentToken == "-")
         indexOf++;
    }
    //====================================================
    public static void term()
    {
      currentToken = tokOut.get(indexOf);
      factor();
      moreTerms();
    }
    //==========================================
    public static void operation()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == "<=" || currentToken == "<" || currentToken == ">" || currentToken == ">=" || currentToken == "==" || currentToken == "!=")
         indexOf++;
    }
    //====================================================
    public static void addExpression()
    {
      currentToken = tokOut.get(indexOf);
      term();
      moreAddExpression();
    }
    //==================================================
    public static void moreArgs()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken == ",")
      {
         indexOf++;
         expression();
         moreArgs();
      }
      if(currentToken != ")")
      {
         System.out.println("REJECT");
         System.exit(0);
      }
    }
    //====================================================
    public static void variable()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken.equals(ID))
      {
         indexOf++;
         variableArrayCheck();
      }
    }
    //================================================
    public static void call()
    {
      currentToken = tokOut.get(indexOf);
      if(currentToken.equals(ID))
      {
         indexOf++;
         if(currentToken == "(")
         {
            indexOf++;
            arguments();
            if(currentToken == ")")
            {
               indexOf++;
            }
         }
      }
    }
}