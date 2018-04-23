import java.io.*;

public class Parser {

    public static int charClass;
    public static String lexeme = "";
    public static char nextChar;
    public static int lexLen;
    public static int token;
    public static int nextToken;
    public static final int LETTER = 0;
    public static final int DIGIT = 1;
    public static final int UNKNOWN = 99;
    public static final int INT_LIT = 10;
    public static final int IDENT = 11;
    public static final int ASSIGN_OP = 20;
    public static final int ADD_OP = 21;
    public static final int SUB_OP = 22;
    public static final int MULT_OP = 23;
    public static final int DIV_OP = 24;
    public static final int LEFT_PAREN = 25;
    public static final int RIGHT_PAREN = 26;
    public static final int EQUALS = 27;
    public static final int NOT_OP = 28;
    public static final int LESS_THAN = 29;
    public static final int GREATER_THAN = 30;
    public static final int LEFT_CURL = 31;
    public static final int RIGHT_CURL = 32;
    public static final int LEFT_BRACKET = 33;
    public static final int RIGHT_BRACKET = 34;
    public static final int SQUARED = 35;
    public static final int SEMI_COLON = 36;
    public static final int COMMA = 37;
    public static final int PERIOD = 38;
    public static final int DOUBLE_QUOTE = 39;
    public static final int SINGLE_QUOTE = 40;
    public static final int KEYWORD = 41;
    public static final int NEW_LN = 42;
    public static final int CARRIAGE = 43;
    public static final int COLON = 44;
    public static final int EOF = -1;

    public static File outFile = new File("output.txt");
    public static BufferedWriter bufferedWriter;

    static {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Reader br;

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            br = new BufferedReader(new FileReader(file));
            getChar();
            do{
                lexeme = "";
                lex();
            } while (nextToken != EOF);
        }
        catch (IOException e){

        }

    }


    public static int lookup(char ch){
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;

            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;

            case '+':
                addChar();
                nextToken = ADD_OP;
                break;

            case '-':
                addChar();
                nextToken = SUB_OP;
                break;

            case '*':
                addChar();
                nextToken = MULT_OP;
                break;

            case '/':
                addChar();
                nextToken = DIV_OP;
                break;

            case '=':
                addChar();
                nextToken = EQUALS;
                break;

            case '!':
                addChar();
                nextToken = NOT_OP;
                break;

            case '<':
                addChar();
                nextToken = LESS_THAN;
                break;

            case '>':
                addChar();
                nextToken = GREATER_THAN;
                break;

            case '{':
                addChar();
                nextToken = LEFT_CURL;
                break;

            case '}':
                addChar();
                nextToken = RIGHT_CURL;
                break;

            case '[':
                addChar();
                nextToken = LEFT_BRACKET;
                break;

            case ']':
                addChar();
                nextToken = RIGHT_BRACKET;
                break;

            case '^':
                addChar();
                nextToken = SQUARED;
                break;

            case ';':
                addChar();
                nextToken = SEMI_COLON;
                break;

            case ',':
                addChar();
                nextToken = COMMA;
                break;

            case '.':
                addChar();
                nextToken = PERIOD;
                break;

            case '"':
                addChar();
                nextToken = DOUBLE_QUOTE;
                break;

            case ':':
                addChar();
                nextToken = COLON;
                break;

            case '\'':
                addChar();
                nextToken = SINGLE_QUOTE;
                break;

            case '\n':
                addChar();
                nextToken = NEW_LN;
                break;

            case '\r':
                addChar();
                nextToken = CARRIAGE;
                break;

            default:
                if(!inString) {
                    System.out.println("ERROR");
                    System.exit(0);
                }
                break;
        }
        return nextToken;
    }

    public static void addChar(){
        if(lexLen <= 98){
            lexeme += nextChar;
        }
        else {
            System.out.println("Error - lexeme is too long");
        }
    }

    public static void getChar() throws IOException{
        int r;
        if((r = br.read()) != -1){
            nextChar = (char) r;
            if(Character.isLetter(nextChar)){
                charClass = LETTER;
            }
            else if(Character.isDigit(nextChar)){
                charClass = DIGIT;
            }
            else {
                charClass = UNKNOWN;
            }
        }
        else{
            charClass = EOF;
        }
    }

    public static void getNonBlank() throws IOException{
        while(Character.isSpaceChar(nextChar)){
            getChar();
        }
    }

    public static int lex() throws IOException{
        lexLen = 0;
        getNonBlank();
        switch (charClass){
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT){
                    addChar();
                    getChar();
                }
                lexeme = lexeme.toLowerCase();
                if(lexeme.equals("beg") || lexeme.equals("end") || lexeme.equals("fnl") || lexeme.equals("int") || lexeme.equals("dbl") || lexeme.equals("str")
                        || lexeme.equals("flt") || lexeme.equals("boo") || lexeme.equals("inp") || lexeme.equals("han") || lexeme.equals("pnt") || lexeme.equals("if")
                        || lexeme.equals("el") || lexeme.equals("for") || lexeme.equals("whl") || lexeme.equals("cat") || lexeme.equals("sub") || lexeme.equals("chat")
                        || lexeme.equals("beg") || lexeme.equals("end")) {
                    nextToken = KEYWORD;
                }
                else{
                    nextToken = IDENT;
                }
                break;

            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            case EOF:
                nextToken = EOF;
                lexeme += "EOF";
                break;
        }
        bufferedWriter.write(nextToken + "\n");
        bufferedWriter.write(lexeme + "\n");
        bufferedWriter.flush();
        System.out.println("Token: " + nextToken + " lexeme: " + lexeme);
        return nextToken;
    }

}
