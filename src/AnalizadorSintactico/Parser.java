//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"

package AnalizadorSintactico;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import ArbolSintactico.NodoHijo;
import ArbolSintactico.Nodo;


//#line 27 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short id=257;
public final static short cte=258;
public final static short If=259;
public final static short then=260;
public final static short Else=261;
public final static short end_if=262;
public final static short out=263;
public final static short fun=264;
public final static short Return=265;
public final static short BREAK=266;
public final static short i32=267;
public final static short when=268;
public final static short For=269;
public final static short CONTINUE=270;
public final static short f32=271;
public final static short cadena=272;
public final static short menorigual=273;
public final static short mayorigual=274;
public final static short distinto=275;
public final static short opasignacion=276;
public final static short Const=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    2,    2,    4,    4,    4,    5,
    5,    6,    6,    6,    6,    8,    8,    3,    3,    3,
    3,   10,   10,   10,   10,   10,   16,   16,   11,   11,
   11,   11,   11,   11,   11,   13,   13,   13,   24,   24,
   25,   25,   26,   28,   28,   28,   28,   29,   29,   14,
   30,   30,   30,   30,   30,   27,   33,   33,   33,   33,
   33,   33,   33,   33,   36,   36,   32,   32,   32,   32,
    7,    7,    7,   17,   17,   17,   17,   37,   37,   18,
   18,   18,   18,   18,   18,   19,   19,   19,   38,   38,
   38,   38,   39,   39,   39,   42,   42,   43,   43,   46,
   46,   46,   47,   47,   47,   48,   48,   49,   49,   34,
   34,   34,   50,   50,   50,   51,   51,   52,   52,   53,
   53,   53,   54,   54,   54,   55,   55,   56,   56,   44,
   45,   40,   15,   15,   15,   59,   60,   60,   60,   61,
   61,   61,   61,   62,   65,   66,   63,   63,   63,   69,
   69,    9,    9,    9,    9,    9,    9,    9,    9,    9,
   20,   70,   70,   70,   35,   35,   35,   35,   64,   67,
   58,   58,   58,   58,   58,   58,   58,   58,   58,   57,
   57,   57,   22,   22,   22,   23,   23,   23,   23,   21,
   71,   71,   71,   41,   31,   31,   73,   73,   73,   72,
   72,   72,   68,   68,   68,   68,   68,   68,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    1,    1,    1,    2,    1,    1,    1,
    1,    1,    1,    2,    2,    2,    3,    1,    2,    2,
    2,    0,    3,    0,    1,    3,    1,    6,    6,    2,
    5,    5,    5,    4,    5,    1,    1,    1,    1,    1,
    1,    1,    2,    2,    2,    1,    2,    2,    1,    1,
    1,    2,    1,    4,    4,    4,    3,    3,    2,    5,
    5,    5,    4,    3,    5,    5,    6,    5,    3,    2,
    2,    1,    2,    1,    1,    4,    2,    4,    2,    5,
    5,    4,    2,    1,    2,    4,    2,    4,    2,    5,
    5,    4,    2,    1,    2,    4,    2,    4,    2,    5,
    5,    4,    2,    1,    2,    4,    2,    4,    2,    1,
    1,    1,    6,    6,    6,    1,    4,    4,    4,    5,
    5,    4,    7,    3,    3,    3,    4,    1,    4,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    2,    2,
    1,    4,    1,    4,    5,    5,    4,    7,    3,    2,
    1,    1,    1,    1,    1,    1,    1,    1,    2,    1,
    2,    1,    2,    3,    2,    2,    4,    4,    2,    5,
    0,    3,    1,    3,    3,    1,    3,    3,    1,    1,
    1,    2,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,  209,    0,
    0,    0,  210,    0,    0,    0,    0,    0,   25,   18,
   19,    0,    0,   24,   26,   29,   30,   31,   32,   33,
    0,    0,    0,  161,    0,   71,    0,    0,    0,   73,
    0,   36,  200,  201,    0,    0,    0,    0,    0,  199,
    0,    0,    0,    0,    0,  185,    0,  183,    0,  136,
    0,    0,    0,    0,    0,  189,    0,  186,    0,    0,
    0,    0,   10,    0,    0,    0,    6,   20,   21,    0,
    0,   34,   35,    0,   50,   37,    0,   72,    0,    0,
    0,    0,   77,    0,    0,    0,  202,  205,  206,  208,
  207,  150,  151,  203,  204,    0,    0,    0,  131,    0,
    0,   95,    0,    0,   90,    0,    0,    0,    0,    0,
    0,   47,    0,    0,    0,    0,  184,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  148,    0,  152,  153,
  154,  156,  157,  158,    0,  155,    0,    0,    0,    0,
   15,    0,    0,    9,    8,    7,    0,    4,   84,    0,
   22,   23,    0,    0,    0,   70,   57,   59,   60,    0,
    0,    0,   62,    0,   69,   58,   61,    0,    0,    0,
    0,   79,    0,   75,   74,   76,   89,    0,    0,  130,
    0,  132,    0,   93,    0,    0,   97,    0,   99,  197,
  198,   83,    0,    0,    0,    0,   40,   39,    0,    0,
    0,    0,    0,    0,  169,    0,    0,    0,    0,    0,
   16,    0,  159,  160,  142,    0,  144,    0,  188,  187,
   14,   13,   12,   11,    3,    0,    0,    0,    0,   63,
   64,   43,   68,   67,    0,  190,  192,   78,   88,    0,
   86,   28,    0,    0,   82,   81,   80,   85,    0,   46,
    0,    0,    0,    0,    0,    0,    0,  140,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   17,  141,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  171,  172,  175,  176,  177,  174,  173,  178,
  163,    0,    0,   87,   96,   27,   98,   48,   41,   49,
    0,    0,  133,  135,  134,    0,  107,    0,  109,  102,
    0,  103,  105,    0,    0,   54,    0,  149,  147,  146,
  145,    0,    0,    0,    0,  117,    0,  119,  112,    0,
  113,  115,    0,    0,  182,    0,  180,    0,  179,  167,
  143,  138,  137,  139,    0,    0,  101,  100,   53,   55,
   51,   52,  170,    0,   66,    0,    0,  111,  110,  165,
    0,    0,    0,    0,    0,    0,  181,  166,  106,  108,
    0,  116,   65,  118,    0,  127,    0,  129,  122,    0,
  123,  125,  164,  162,  168,    0,    0,  121,  120,  126,
  128,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   72,   73,   41,  220,  137,  138,
   21,   22,   23,   24,   25,  253,   26,   27,   28,   29,
   30,   31,   32,  125,  262,   85,  172,  126,   33,  145,
   47,  174,  365,  176,  299,  366,   95,   48,  110,  193,
   49,  111,  112,  113,  114,  146,  271,  272,  273,  285,
  286,  287,  300,  373,  374,  375,  346,  301,   61,  213,
   34,   64,  147,   65,  227,  228,  331,  106,  107,  302,
   89,   50,   51,
};
final static short yysindex[] = {                      -194,
    0,    0,  776,  -18,   34,  -32,  -37,   86,    0,   65,
  -24,   57,    0,   29,  994, -159,   47,  829,    0,    0,
    0,  -78,  123,    0,    0,    0,    0,    0,    0,    0,
  -69,  -61,   75,    0,   25,    0,  -78,  -16,   81,    0,
   41,    0,    0,    0,   81,  -55,   -8,   33,  169,    0,
  127,  -33,  -31,  177,  118,    0,  165,    0,   81,    0,
  187,  -28,  -26,  311,  212,    0,  250,    0,   71,  -44,
  109,   24,    0,  862,  117,  994,    0,    0,    0,  317,
  131,    0,    0,  884,    0,    0,  348,    0,  351,  353,
    8, -157,    0,   19,  336,  358,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   81,   81,  172,    0,  139,
  142,    0,  161, 1054,    0,   81,   81,  355,    6,  160,
  118,    0,  163,  168,  393,  399,    0,  -23,  182,  389,
  195,  404,   34,   72,  905,  525,    0,  199,    0,    0,
    0,    0,    0,    0,  203,    0,  204,  207,  210,  410,
    0,  214, -167,    0,    0,    0,  215,    0,    0,  924,
    0,    0,  -12,   34,  -15,    0,    0,    0,    0,  222,
  223,  346,    0,  884,    0,    0,    0,  -26,  421,   81,
  213,    0,  225,    0,    0,    0,    0,  151,  127,    0,
 -186,    0,  427,    0,  -38,  143,    0,  143,    0,    0,
    0,    0,  428,   21,  429,  449,    0,    0, -173,  433,
  952,  182,  436,  437,    0,  525,  172,   81,   16,  544,
    0,  236,    0,    0,    0,   61,    0,  438,    0,    0,
    0,    0,    0,    0,    0,  -10,  172,  -26,  472,    0,
    0,    0,    0,    0,  457,    0,    0,    0,    0,  243,
    0,    0, 1071, 1100,    0,    0,    0,    0,  433,    0,
  -74,  244,  994,  979,  444,  251,  255,    0,  561,  580,
 -121,  142,  258,   87,  259,  447,   49,    0,    0,  260,
  151,  476,  267, 1015, -120,  142,  261,  479,   34,  597,
  621,  265,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  266,  525,    0,    0,    0,    0,    0,    0,    0,
  637,  271,    0,    0,    0,  905,    0,  905,    0,    0,
   55,    0,    0,  272,   68,    0,  273,    0,    0,    0,
    0,  274,  -26,  -27, 1049,    0, 1049,    0,    0,   99,
    0,    0,  621,  172,    0,  652,    0,  284,    0,    0,
    0,    0,    0,    0,  673,  692,    0,    0,    0,    0,
    0,    0,    0,  500,    0,  285, 1032,    0,    0,    0,
  713,  732, -119,  142,  289,  103,    0,    0,    0,    0,
  621,    0,    0,    0,  597,    0,  597,    0,    0,  104,
    0,    0,    0,    0,    0,  747,  800,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  487,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  549,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  487,    0,    0,    0,  515,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  188,    0,
  106,    0,    0,    0,  516,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  520,
   13,    0,    0,    0,    0,  197,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1105,    0,    0,
 -108,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  516,    0,    0,    0,  521,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  487,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  439,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -5, 1075,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  307,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  446,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -106,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -101,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -84,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -11,    0,    0,  414,   15, -105, -130,   -3,
   -7,  -34,  155,    0,    0,  369,   80,  138,    0,  -35,
  -40,  269,  327,  364,  324,    0,    0,  465,    0,  384,
  -14,    0,  -77,    0,  -65,  252,    0,   -4,  483, -152,
   93,    0,  481, -135,  100,    0,    0,    0,  321,    0,
    0,  308,    0,    0,    0,  224,  -39,  300,  540,  -87,
    0,  -54,  -98,    0,    0,    0,    0,  374,  326, -250,
    0,  322,  495,
};
final static int YYTABLESIZE=1375;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
   93,   39,   55,   74,  221,   92,  175,   53,  132,  120,
   79,   20,   39,  130,   78,   63,   40,  212,  177,   38,
  124,   39,   40,  142,  238,   37,   94,   39,  141,  130,
  236,   37,   40,   40,  102,  194,  103,  222,  250,   38,
  348,  214,   40,  169,   88,  236,  204,   39,   40,    5,
   88,  104,  101,  105,  200,  200,  276,  200,  102,  200,
  103,  102,    1,  103,  160,  181,   79,  157,   37,  249,
   78,  200,   20,   45,  275,  192,  131,  185,   46,  257,
  166,  269,  156,  123,  153,   46,  124,   71,  232,  278,
  233,  188,  370,    9,  142,  142,  244,   13,  182,  141,
  141,  283,   60,  183,   59,  197,  199,  329,  177,   46,
  239,  218,   75,  358,   69,   68,   46,  268,  321,  219,
  104,  101,  105,  245,  265,   46,  361,  325,  217,  102,
  395,  103,  340,  169,  320,  339,  389,   96,  317,  319,
  192,  192,  192,  139,   58,  324,  196,   94,  196,  104,
  196,   60,   79,   94,  114,  104,   78,  369,   42,  237,
  114,  394,  399,  167,  196,  196,  196,  196,  116,   76,
  243,  124,    5,  117,  124,  142,   80,  124,   35,  142,
  141,   81,   16,  288,  141,  221,   82,  221,  252,   42,
  252,   86,    9,  102,   83,  103,   13,   84,  295,  264,
   16,  140,   97,  274,  351,  336,  338,   20,  371,  115,
  355,  151,  356,  152,  139,  139,  121,  177,  177,   54,
  390,  168,  118,  127,  278,  278,  309,  129,  142,  142,
   62,   36,   62,  141,  141,  292,  211,   36,   35,   52,
  119,   62,  169,  169,   35,  306,  306,   36,   36,  295,
  295,  311,   87,  167,  194,  194,   79,   36,  282,   20,
   78,  203,  142,   36,   98,   99,  100,  141,  200,  177,
  148,  177,  140,  140,  184,  142,  256,  142,  364,  155,
  141,   35,  141,  196,  344,   70,  292,  292,  383,  383,
   43,   44,  108,  109,  169,  139,  169,   91,   44,  139,
  177,  177,  295,   79,  328,  295,   16,   78,  149,   11,
  357,  168,   66,   67,  142,  142,  270,   42,  293,  141,
  141,   43,   44,  360,   16,  169,  169,  150,   43,   44,
  295,  295,  143,   98,   99,  100,  284,   43,   44,  292,
  295,   56,  292,   57,  295,  396,  295,  397,  139,  139,
   16,  136,  170,  140,  368,  295,  295,  140,  393,  398,
   90,  196,  167,  167,  154,  196,  196,  292,  292,  293,
  293,  270,  159,  122,  123,  161,  294,  292,  196,  196,
  196,  292,  139,  292,    9,  284,  162,  178,   13,  335,
  144,  179,  292,  292,  186,  139,  180,  139,  187,  195,
  192,    5,  109,  143,  143,    6,  140,  140,    8,  382,
  171,   11,   12,  202,  167,  205,  167,  195,  207,    5,
  168,  168,  293,    6,  208,  293,    8,  294,  294,   11,
   12,  190,  109,  135,  139,  139,  209,  200,  201,  210,
  140,  211,  170,  372,  216,  167,  167,   92,   92,   88,
  293,  293,  215,  140,  223,  140,   91,   91,  224,  225,
  293,  144,  144,  226,  293,  229,  293,  173,  230,  231,
  242,   70,  168,  372,  168,  293,  293,  240,  241,  246,
  294,   87,  248,  294,  143,  251,  255,  258,  143,  259,
  261,  279,  140,  140,  266,  267,  281,  303,  304,  310,
  171,  247,  313,  168,  168,  327,  314,  296,  294,  294,
  315,   16,  291,  323,  326,  333,  342,  330,  294,  343,
  349,  350,  294,  334,  294,  164,  354,  359,  362,    6,
  363,  134,    8,  294,  294,  165,   12,  143,  143,  378,
  381,  334,  144,  164,  392,   38,  144,    6,    1,  134,
    8,  170,  170,  165,   12,  191,   44,  173,  296,  296,
  193,   45,   42,   56,   16,  297,  254,    4,   42,  133,
  234,  143,  260,    6,    7,  134,    8,    9,   10,   11,
   12,   13,  308,   16,  143,  206,  143,   14,  367,  347,
  191,  194,  322,  341,  290,  144,  144,  391,  128,  280,
   16,  189,    0,  170,    0,  170,  332,    0,    0,  171,
  171,  296,    0,    0,  296,    0,  297,  297,    0,   16,
    0,    0,  298,  143,  143,    0,    0,    0,    0,  144,
    0,    0,    0,    0,  170,  170,   16,    0,    0,  296,
  296,    0,  144,    0,  144,  377,    0,  135,    0,  296,
    0,    0,    0,  296,    0,  296,    0,    0,    0,    0,
   16,  171,    0,  171,  296,  296,  173,  173,  277,  297,
  386,  388,  297,  298,  298,    0,   16,    0,    0,    0,
    0,  144,  144,  316,  347,    0,  347,    0,    0,    0,
    0,   16,  171,  171,    0,  377,  377,  297,  297,    0,
    0,    0,  318,    0,    0,    0,    0,  297,    0,    0,
    0,  297,   16,  297,    0,    0,    0,    0,  173,    0,
  173,    0,  297,  297,    0,    0,  298,    0,  163,  298,
  289,   16,    0,    0,    6,    7,  134,    8,    9,   10,
  165,   12,   13,  290,    0,    0,    0,    0,   14,  173,
  173,    0,   16,    0,  298,  298,    0,    0,    0,    0,
    0,  353,    0,    0,  298,    0,    0,    0,  298,    0,
  298,   16,    0,    0,    0,    0,  376,    0,    0,  298,
  298,    4,    0,  133,    0,    0,   16,    6,    7,  134,
    8,    9,   10,   11,   12,   13,    0,  379,    0,    0,
    4,   14,  133,    0,    0,    0,    6,    7,  134,    8,
    9,   10,   11,   12,   13,   16,  380,    4,    0,  133,
   14,    0,    0,    6,    7,  134,    8,    9,   10,   11,
   12,   13,    0,    0,    0,  385,    4,   14,  133,   16,
    0,    0,    6,    7,  134,    8,    9,   10,   11,   12,
   13,    0,  345,  163,  387,  289,   14,    0,    0,    6,
    7,  134,    8,    9,   10,  165,   12,   13,   16,    0,
    0,  400,    0,   14,    0,    0,    0,  163,    0,  289,
    0,    0,    0,    6,    7,  134,    8,    9,   10,  165,
   12,   13,  352,    4,    0,    5,    0,   14,   15,    6,
    7,   16,    8,    9,   10,   11,   12,   13,  163,    0,
  289,    0,    0,   14,    6,    7,  134,    8,    9,   10,
  165,   12,   13,   16,  401,    0,    0,    0,   14,    4,
    0,  133,    0,    0,    0,    6,    7,  134,    8,    9,
   10,   11,   12,   13,   16,    0,    0,    0,    4,   14,
  133,    0,    0,   77,    6,    7,  134,    8,    9,   10,
   11,   12,   13,   16,    0,    0,    0,    0,   14,  163,
    0,  289,    0,    0,    0,    6,    7,  134,    8,    9,
   10,  165,   12,   13,    0,    0,  158,    0,  163,   14,
  289,   16,    0,    0,    6,    7,  134,    8,    9,   10,
  165,   12,   13,  163,    0,  289,    0,    0,   14,    6,
    7,  134,    8,    9,   10,  165,   12,   13,   16,    0,
    0,    0,    0,   14,    0,    0,    0,    0,    0,    0,
    0,    0,    4,   16,    5,    0,    0,    0,    6,    7,
    0,    8,    9,   10,   11,   12,   13,    0,  235,    0,
    0,    0,   14,    0,   16,    0,  163,    0,  289,    0,
    0,    0,    6,    7,  134,    8,    9,   10,  165,   12,
   13,   16,    0,    0,  263,    0,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    0,    5,   16,    0,
    0,    6,    7,   16,    8,    9,   10,   11,   12,   13,
    0,    0,    0,  312,    0,   14,    0,    0,    0,    0,
   16,    0,    0,    0,    0,  195,    0,  195,    4,  195,
    5,    0,    0,    0,    6,    7,    0,    8,    9,   10,
   11,   12,   13,  195,  195,  195,  195,  337,   14,   16,
  163,    0,  164,    0,  130,    0,    6,    7,  134,    8,
    9,   10,  165,   12,   13,    0,  384,    0,    0,    0,
   14,    4,    0,  133,    0,    0,    0,    6,    7,  134,
    8,    9,   10,   11,   12,   13,  198,    0,    0,    0,
    4,   14,    5,    0,    0,    0,    6,    7,    0,    8,
    9,   10,   11,   12,   13,  305,    0,    0,    0,    0,
   14,    0,    0,    0,    0,    0,    0,    0,    4,    0,
    5,    0,    0,    0,    6,    7,    0,    8,    9,   10,
   11,   12,   13,    0,  307,    0,    0,  130,   14,    0,
    0,    0,    0,    0,    0,    4,    0,    5,    0,    0,
    0,    6,    7,    0,    8,    9,   10,   11,   12,   13,
    4,    0,    5,    0,    0,   14,    6,    7,    0,    8,
    9,   10,   11,   12,   13,    0,    0,    0,    0,    0,
   14,  334,    0,  164,    0,    0,    0,    6,    0,  134,
    8,    0,    0,  165,   12,    0,    0,    0,  334,    0,
  164,    0,    0,    0,    6,    0,  134,    8,    0,    0,
  165,   12,    0,    0,    0,  334,    0,  164,    0,    0,
  195,    6,    5,  134,    8,    0,    6,  165,   12,    8,
    0,    0,   11,   12,    0,    0,    0,  195,    0,    5,
  195,    0,    0,    6,  195,  195,    8,    0,    0,   11,
   12,    0,    0,    0,    0,    0,    0,  195,  195,  195,
    0,    0,    0,    0,    0,    0,  195,    0,    5,    0,
    0,  130,    6,  130,    0,    8,    0,  130,   11,   12,
  130,    0,    0,  130,  130,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   41,   40,   40,   15,  135,   41,   84,   40,   63,   41,
   18,   15,   40,   58,   18,   40,   61,   41,   84,   58,
   55,   40,   61,   64,   40,   44,   41,   40,   64,   58,
   58,   44,   61,   61,   43,   41,   45,  136,  191,   58,
  291,  129,   61,   84,   61,   58,   41,   40,   61,    0,
   61,   60,   61,   62,   42,   43,   41,   45,   43,   47,
   45,   43,  257,   45,   76,   58,   74,   44,   44,  256,
   74,   59,   76,   40,   59,  262,   62,   59,   45,   59,
   84,  217,   59,  257,   70,   45,  121,   59,  256,  220,
  258,  106,  343,  267,  135,  136,  174,  271,  256,  135,
  136,  237,   10,  261,   40,  113,  114,   59,  174,   45,
  165,   40,  272,   59,   58,   59,   45,  216,  271,  134,
   60,   61,   62,  178,  212,   45,   59,   41,  133,   43,
  381,   45,  285,  174,  256,  256,  256,   45,  269,  270,
  262,  262,  262,   64,   59,   59,   41,  256,   43,  256,
   45,   59,  160,  262,  256,  262,  160,   59,    4,  164,
  262,   59,   59,   84,   59,   60,   61,   62,   42,  123,
  174,  256,  123,   47,  209,  216,   22,  262,  257,  220,
  216,   59,   40,  238,  220,  316,  256,  318,  196,   35,
  198,   37,  267,   43,  256,   45,  271,  123,  239,  211,
   40,   64,  258,  218,  303,  283,  284,  211,  344,   41,
  316,  256,  318,  258,  135,  136,   40,  283,  284,  257,
  373,   84,  256,   59,  355,  356,  261,   41,  269,  270,
  257,  276,  257,  269,  270,  239,  260,  276,  257,  272,
  272,  257,  283,  284,  257,  253,  254,  276,  276,  290,
  291,  263,  269,  174,  260,  261,  264,  276,  269,  263,
  264,  256,  303,  276,  273,  274,  275,  303,  256,  335,
   59,  337,  135,  136,  256,  316,  256,  318,  333,  256,
  316,  257,  318,  123,  289,  257,  290,  291,  366,  367,
  257,  258,  260,  261,  335,  216,  337,  257,  258,  220,
  366,  367,  343,  311,  256,  346,   40,  311,   59,  269,
  256,  174,  256,  257,  355,  356,  217,  163,  239,  355,
  356,  257,  258,  256,   40,  366,  367,  257,  257,  258,
  371,  372,   64,  273,  274,  275,  237,  257,  258,  343,
  381,  256,  346,  258,  385,  385,  387,  387,  269,  270,
   40,   41,   84,  216,  256,  396,  397,  220,  256,  256,
   39,  256,  283,  284,  256,  260,  261,  371,  372,  290,
  291,  272,  256,  256,  257,   59,  239,  381,  273,  274,
  275,  385,  303,  387,  267,  286,  256,   40,  271,  123,
   64,   41,  396,  397,   59,  316,   44,  318,   41,  257,
  262,  259,  261,  135,  136,  263,  269,  270,  266,  125,
   84,  269,  270,   59,  335,  256,  337,  257,  256,  259,
  283,  284,  343,  263,  257,  346,  266,  290,  291,  269,
  270,  260,  261,  123,  355,  356,   44,  116,  117,   41,
  303,  260,  174,  344,   41,  366,  367,  260,  261,   61,
  371,  372,  258,  316,  256,  318,  260,  261,  256,  256,
  381,  135,  136,  257,  385,  256,  387,   84,   59,  256,
  125,  257,  335,  374,  337,  396,  397,  256,  256,   59,
  343,  269,  258,  346,  216,   59,   59,   59,  220,   41,
   58,  256,  355,  356,   59,   59,   59,   41,  256,  256,
  174,  180,   59,  366,  367,   59,  256,  239,  371,  372,
  256,   40,   41,  256,  256,   40,  256,  258,  381,   41,
  256,  256,  385,  257,  387,  259,  256,  256,  256,  263,
  257,  265,  266,  396,  397,  269,  270,  269,  270,  256,
   41,  257,  216,  259,  256,   59,  220,  263,    0,  265,
  266,  283,  284,  269,  270,   41,   41,  174,  290,  291,
   41,   41,  256,  125,   40,  239,  198,  257,  123,  259,
  157,  303,  209,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  259,   40,  316,  121,  318,  277,  337,  290,
  108,  111,  272,  286,  123,  269,  270,  374,   59,  226,
   40,  107,   -1,  335,   -1,  337,  281,   -1,   -1,  283,
  284,  343,   -1,   -1,  346,   -1,  290,  291,   -1,   40,
   -1,   -1,  239,  355,  356,   -1,   -1,   -1,   -1,  303,
   -1,   -1,   -1,   -1,  366,  367,   40,   -1,   -1,  371,
  372,   -1,  316,   -1,  318,  346,   -1,  123,   -1,  381,
   -1,   -1,   -1,  385,   -1,  387,   -1,   -1,   -1,   -1,
   40,  335,   -1,  337,  396,  397,  283,  284,  125,  343,
  371,  372,  346,  290,  291,   -1,   40,   -1,   -1,   -1,
   -1,  355,  356,  123,  385,   -1,  387,   -1,   -1,   -1,
   -1,   40,  366,  367,   -1,  396,  397,  371,  372,   -1,
   -1,   -1,  123,   -1,   -1,   -1,   -1,  381,   -1,   -1,
   -1,  385,   40,  387,   -1,   -1,   -1,   -1,  335,   -1,
  337,   -1,  396,  397,   -1,   -1,  343,   -1,  257,  346,
  259,   40,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  123,   -1,   -1,   -1,   -1,  277,  366,
  367,   -1,   40,   -1,  371,  372,   -1,   -1,   -1,   -1,
   -1,  125,   -1,   -1,  381,   -1,   -1,   -1,  385,   -1,
  387,   40,   -1,   -1,   -1,   -1,  125,   -1,   -1,  396,
  397,  257,   -1,  259,   -1,   -1,   40,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,  125,   -1,   -1,
  257,  277,  259,   -1,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   40,  125,  257,   -1,  259,
  277,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,   -1,   -1,  123,  257,  277,  259,   40,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,  256,  257,  123,  259,  277,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,   -1,
   -1,  125,   -1,  277,   -1,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  256,  257,   -1,  259,   -1,  277,  123,  263,
  264,   40,  266,  267,  268,  269,  270,  271,  257,   -1,
  259,   -1,   -1,  277,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   40,  125,   -1,   -1,   -1,  277,  257,
   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   40,   -1,   -1,   -1,  257,  277,
  259,   -1,   -1,  125,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   40,   -1,   -1,   -1,   -1,  277,  257,
   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,  125,   -1,  257,  277,
  259,   40,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,   -1,   -1,  277,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,   -1,
   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   40,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,   -1,  125,   -1,
   -1,   -1,  277,   -1,   40,   -1,  257,   -1,  259,   -1,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   40,   -1,   -1,  123,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,   40,   -1,
   -1,  263,  264,   40,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,  125,   -1,  277,   -1,   -1,   -1,   -1,
   40,   -1,   -1,   -1,   -1,   41,   -1,   43,  257,   45,
  259,   -1,   -1,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,   59,   60,   61,   62,  123,  277,   40,
  257,   -1,  259,   -1,   40,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   -1,  125,   -1,   -1,   -1,
  277,  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  123,   -1,   -1,   -1,
  257,  277,  259,   -1,   -1,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,  125,   -1,   -1,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,   -1,  125,   -1,   -1,  123,  277,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
  257,   -1,  259,   -1,   -1,  277,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,   -1,   -1,   -1,   -1,   -1,
  277,  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  265,
  266,   -1,   -1,  269,  270,   -1,   -1,   -1,  257,   -1,
  259,   -1,   -1,   -1,  263,   -1,  265,  266,   -1,   -1,
  269,  270,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
  257,  263,  259,  265,  266,   -1,  263,  269,  270,  266,
   -1,   -1,  269,  270,   -1,   -1,   -1,  257,   -1,  259,
  256,   -1,   -1,  263,  260,  261,  266,   -1,   -1,  269,
  270,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,
   -1,  257,  263,  259,   -1,  266,   -1,  263,  269,  270,
  266,   -1,   -1,  269,  270,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"id","cte","If","then","Else","end_if","out",
"fun","Return","BREAK","i32","when","For","CONTINUE","f32","cadena",
"menorigual","mayorigual","distinto","opasignacion","Const",
};
final static String yyrule[] = {
"$accept : programa",
"programa : encabezado_prog bloque_sentencias",
"encabezado_prog : id",
"bloque_sentencias : bloque_sentencias '{' sentencia '}'",
"bloque_sentencias : '{' sentencia '}'",
"bloque_sentencias : '{' sentencia",
"bloque_sentencias : sentencia '}'",
"declaracion_const : Const lista_de_asignacion_const ';'",
"declaracion_const : Const lista_de_asignacion_const error",
"declaracion_const : Const ';' error",
"lista_de_asignacion_const : decl_const",
"lista_de_asignacion_const : lista_de_asignacion_const ',' decl_const",
"decl_const : id op_asignacion cte",
"decl_const : id op_asignacion error",
"decl_const : id cte error",
"decl_const : id error",
"bloq_sentencias_For : sentencias_For",
"bloq_sentencias_For : bloq_sentencias_For sentencias_For",
"sentencia : declarativas",
"sentencia : ejecutables",
"sentencia : sentencia declarativas",
"sentencia : sentencia ejecutables",
"declarativas : tipo lista_de_variables ';'",
"declarativas : lista_de_variables ';' error",
"declarativas : declaracion_func",
"declarativas : declaracion_const",
"declarativas : sentencia_when",
"bloque_ejecutables : bloque_ejecutables ejecutables",
"bloque_ejecutables : ejecutables",
"ejecutables : asignacion",
"ejecutables : salida",
"ejecutables : sentencia_If",
"ejecutables : expresion_For",
"ejecutables : invocacion_funcion",
"ejecutables : sentencia_BREAK error",
"ejecutables : sentencia_CONTINUE error",
"lista_de_variables : id lista_de_variables",
"lista_de_variables : id ',' lista_de_variables",
"lista_de_variables : id",
"parametro : tipo id",
"parametro : id error",
"asig_fun : ':' tipo",
"asig_fun :",
"cola_func : '{' cuerpo_fun '}'",
"lista_parametros :",
"lista_parametros : parametro",
"lista_parametros : parametro ',' parametro",
"lista_parametros : error",
"encab_fun : fun id '(' lista_parametros ')' asig_fun",
"encab_fun : fun '(' lista_parametros ')' asig_fun error",
"declaracion_func : encab_fun cola_func",
"ret_fun : Return '(' expresion ')' ';'",
"ret_fun : Return expresion ')' ';' error",
"ret_fun : Return '(' expresion ';' error",
"ret_fun : Return expresion ';' error",
"ret_fun : Return '(' expresion ')' error",
"cuerpo_fun : bloque_sentencias_funcion",
"ejecutables_funcion : asignacion",
"ejecutables_funcion : sentencia_if_funcion",
"ejecutables_funcion : salida",
"ejecutables_funcion : invocacion_funcion",
"ejecutables_funcion : sentencia_for_funcion",
"ejecutables_funcion : ret_fun",
"ejecutables_funcion : sentencia_BREAK error",
"ejecutables_funcion : sentencia_CONTINUE error",
"bloq_ejecutables_fun : bloq_ejecutables_fun ejecutables_funcion",
"bloq_ejecutables_fun : ejecutables_funcion",
"bloque_sentencias_funcion : bloque_sentencias_funcion ejecutables_funcion",
"bloque_sentencias_funcion : bloque_sentencias_funcion declarativas",
"bloque_sentencias_funcion : ejecutables_funcion",
"bloque_sentencias_funcion : declarativas",
"op_asignacion : opasignacion",
"op_asignacion : ':' '='",
"op_asignacion : '='",
"asignacion : id op_asignacion expresion ';'",
"asignacion : id op_asignacion expresion error",
"asignacion : id op_asignacion for_else_cte ';'",
"asignacion : id op_asignacion invocacion_funcion",
"for_else_cte : expresion_For Else cte",
"for_else_cte : expresion_For error",
"salida : out '(' cadena ')' ';'",
"salida : out '(' cadena ')' error",
"salida : out '(' cadena error ';'",
"salida : out cadena error ';'",
"salida : '(' cadena error",
"salida : out '(' ')' error ';'",
"sentencia_If : If condicion_if cuerpo_If PR_end_if ';'",
"sentencia_If : If condicion_if then cuerpo_If PR_end_if error",
"sentencia_If : If condicion_if then cuerpo_If error",
"condicion_if : '(' expresion_relacional ')'",
"condicion_if : expresion_relacional ')'",
"condicion_if : '(' expresion_relacional",
"condicion_if : expresion_relacional",
"cuerpo_If : cuerpo_Then cuerpo_Else",
"cuerpo_If : cuerpo_Then",
"cuerpo_If : cuerpo_Else",
"cuerpo_Then : PR_then '{' bloque_ejecutables '}'",
"cuerpo_Then : PR_then ejecutables",
"cuerpo_Else : PR_else '{' bloque_ejecutables '}'",
"cuerpo_Else : PR_else ejecutables",
"sentencia_if_for : If condicion_if cuerpo_If_for PR_end_if ';'",
"sentencia_if_for : If condicion_if cuerpo_If_for PR_end_if error",
"sentencia_if_for : If condicion_if cuerpo_If_for error",
"cuerpo_If_for : cuerpo_then_for cuerpo_Else_for",
"cuerpo_If_for : cuerpo_then_for",
"cuerpo_If_for : cuerpo_Else_for error",
"cuerpo_then_for : PR_then '{' bloq_sentencias_For '}'",
"cuerpo_then_for : PR_then sentencias_For",
"cuerpo_Else_for : PR_else '{' bloq_sentencias_For '}'",
"cuerpo_Else_for : PR_else sentencias_For",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion PR_end_if ';'",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion PR_end_if error",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion error",
"cuerpo_If_funcion : cuerpo_then_funcion cuerpo_Else_funcion",
"cuerpo_If_funcion : cuerpo_then_funcion",
"cuerpo_If_funcion : cuerpo_Else_funcion error",
"cuerpo_then_funcion : PR_then '{' bloq_ejecutables_fun '}'",
"cuerpo_then_funcion : PR_then ejecutables_funcion",
"cuerpo_Else_funcion : PR_else '{' bloq_ejecutables_fun '}'",
"cuerpo_Else_funcion : PR_else ejecutables_funcion",
"sentencia_if_for_fun : If condicion_if cuerpo_If_for_fun PR_end_if ';'",
"sentencia_if_for_fun : If condicion_if cuerpo_If_for_fun PR_end_if error",
"sentencia_if_for_fun : If condicion_if cuerpo_If_for_fun error",
"cuerpo_If_for_fun : then_if_for_fun else_if_for_fun",
"cuerpo_If_for_fun : then_if_for_fun",
"cuerpo_If_for_fun : else_if_for_fun error",
"then_if_for_fun : PR_then '{' bloq_for_funcion '}'",
"then_if_for_fun : PR_then sentencias_For_funcion",
"else_if_for_fun : PR_else '{' bloq_for_funcion '}'",
"else_if_for_fun : PR_else sentencias_For_funcion",
"PR_then : then",
"PR_else : Else",
"PR_end_if : end_if",
"sentencia_when : when '(' condicion_when ')' cuerpo_when ';'",
"sentencia_when : when condicion_when ')' cuerpo_when ';' error",
"sentencia_when : when '(' condicion_when cuerpo_when ';' error",
"condicion_when : expresion_relacional",
"cuerpo_when : then '{' sentencia '}'",
"cuerpo_when : then '{' sentencia error",
"cuerpo_when : then sentencia '}' error",
"encabezado_For : For '(' detalles_for ')' cola_For",
"encabezado_For : For detalles_for ')' cola_For error",
"encabezado_For : For detalles_for cola_For error",
"encabezado_For : id ':' For '(' detalles_for ')' cola_For",
"detalles_for : asignacion_for ';' cond_op_for",
"cond_op_for : condicion_for ';' operacion_for",
"condicion_for : id comparador cte",
"cola_For : '{' bloq_sentencias_For '}' ';'",
"cola_For : sentencias_For",
"cola_For : '{' bloq_sentencias_For '}' error",
"signo : '+'",
"signo : '-'",
"sentencias_For : asignacion",
"sentencias_For : salida",
"sentencias_For : expresion_For",
"sentencias_For : sentencia_if_for",
"sentencias_For : invocacion_funcion",
"sentencias_For : sentencia_BREAK",
"sentencias_For : sentencia_CONTINUE",
"sentencias_For : declarativas error",
"sentencias_For : ret_fun error",
"expresion_For : encabezado_For",
"cola_For_funcion : '{' bloq_for_funcion '}' ';'",
"cola_For_funcion : sentencias_For_funcion",
"cola_For_funcion : '{' bloq_for_funcion '}' error",
"sentencia_for_funcion : For '(' detalles_for ')' cola_For_funcion",
"sentencia_for_funcion : For detalles_for ')' cola_For_funcion error",
"sentencia_for_funcion : For detalles_for cola_For_funcion error",
"sentencia_for_funcion : id ':' For '(' detalles_for ')' cola_For_funcion",
"asignacion_for : id op_asignacion cte",
"operacion_for : signo id",
"sentencias_For_funcion : asignacion",
"sentencias_For_funcion : salida",
"sentencias_For_funcion : sentencia_for_funcion",
"sentencias_For_funcion : ret_fun",
"sentencias_For_funcion : invocacion_funcion",
"sentencias_For_funcion : sentencia_BREAK",
"sentencias_For_funcion : sentencia_CONTINUE",
"sentencias_For_funcion : sentencia_if_for_fun",
"sentencias_For_funcion : declarativas error",
"bloq_for_funcion : sentencias_For_funcion",
"bloq_for_funcion : bloq_for_funcion sentencias_For_funcion",
"bloq_for_funcion : error",
"sentencia_BREAK : BREAK ';'",
"sentencia_BREAK : BREAK cte ';'",
"sentencia_BREAK : BREAK error",
"sentencia_CONTINUE : CONTINUE ';'",
"sentencia_CONTINUE : CONTINUE ':' id ';'",
"sentencia_CONTINUE : CONTINUE id ';' error",
"sentencia_CONTINUE : CONTINUE error",
"invocacion_funcion : id '(' list_parametros_Inv ')' ';'",
"list_parametros_Inv :",
"list_parametros_Inv : factor ',' factor",
"list_parametros_Inv : factor",
"expresion_relacional : expresion comparador expresion",
"expresion : expresion signo termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : id",
"factor : cte",
"factor : '-' cte",
"comparador : '<'",
"comparador : '>'",
"comparador : menorigual",
"comparador : mayorigual",
"comparador : '='",
"comparador : distinto",
"tipo : i32",
"tipo : f32",
};

//#line 764 "gramatica.y"

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;
private String ambito;
private int contadorFor;
private int contadorIf;
private int contadorWhen;



public void activarAmbito(){this.ambito = "$"; this.contadorFor = 0; this.contadorIf = 0; this.contadorWhen = 0;} // $ va a simblizar el ambito global.

public void setLexico(AnalizadorLexico lexico) { this.lexico = lexico; }

public void setSintactico(AnalizadorSintactico sintactico) { this.sintactico = sintactico; }

public AnalizadorLexico getLexico() { return this.lexico; }

public AnalizadorSintactico getSintactico() { return this.sintactico; }

public void agregarAmbito(String nuevo) {

	this.ambito = this.ambito + "_" + nuevo;

}

public String borrarAmbito(String ambito){
	if (ambito.length() > 1) { // si es 1 solo tiene el ambito global
		String [] aux = ambito.split("_"); // separo los elementos individuales del ambito
		String last = aux[aux.length - 1 ]; // obtengo el ultimo, el que tengo que eliminar
		return ambito.substring(0, ambito.length() - last.length() - 1);
	}
	return "$";
}

public int yylex() {
    int token = lexico.procesarYylex();
    if (lexico.getRefTablaSimbolos() != -1)
        yylval = new ParserVal(lexico.getRefTablaSimbolos());
    return token;
}

public void yyerror(String string) {
	//sintactico.addErrorSintactico("par: " + string);
}

// retorna -1 si no existe un identificador con el lexema dado en el ambito dado. Si existe, retorna el indice de la entrada existente.
public int enAmbito(ParserVal pv){

	String lexema = sintactico.getEntradaTablaSimb(pv.ival).getLexema();
	String ambitoAux = this.ambito;

	String [] aux = ambitoAux.split("_");
	for (int i = 0 ; i < aux.length ; i++){
		int existente = sintactico.getTS().existeEntrada(lexema + "@"+ambitoAux);
		if (existente >= 0 ){
			return existente;
		}
		ambitoAux = borrarAmbito(ambitoAux);
	}
	return -1;
}
//#line 902 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 19 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("raiz", val_peek(0))); sintactico.setRaiz(yyval); }
break;
case 3:
//#line 26 "gramatica.y"
{yyval = val_peek(1);}
break;
case 4:
//#line 27 "gramatica.y"
{yyval = val_peek(1);}
break;
case 5:
//#line 28 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
break;
case 6:
//#line 29 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
break;
case 7:
//#line 32 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de CONSTANTE. (Línea " + AnalizadorLexico.LINEA + ")");
							  	  yyval = new ParserVal(sintactico.crearNodoControl("lista_ctes", val_peek(1)));}
break;
case 8:
//#line 34 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
break;
case 9:
//#line 35 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
break;
case 10:
//#line 38 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 11:
//#line 39 "gramatica.y"
{ParserVal modificado = sintactico.modificarHijo(val_peek(2), sintactico.crearNodo("sentencia", val_peek(0), null));
                                                                         yyval = modificado;}
break;
case 12:
//#line 44 "gramatica.y"
{
						int existente = enAmbito(val_peek(2));
						if (existente < 0) {
							int i = val_peek(2).ival;
							sintactico.setTipoEnIndex(sintactico.getTipoFromTS(val_peek(0).ival), i);
							sintactico.setUsoEnIndex("const", i);
							sintactico.setLexemaEnIndex(val_peek(2).ival, "@"+this.ambito);
							yyval = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja(val_peek(2).ival)), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));
						} else {
							sintactico.addErrorSintactico("SemanticError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
						}

					}
break;
case 13:
//#line 57 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 14:
//#line 58 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 15:
//#line 59 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 16:
//#line 63 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 17:
//#line 64 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 18:
//#line 69 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 19:
//#line 70 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 20:
//#line 71 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 21:
//#line 72 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 22:
//#line 76 "gramatica.y"
{
							String type = val_peek(2).sval;
						 	sintactico.completarConTipos(type);
						 	sintactico.addAnalisis("Se reconoció declaraciónes de variable de tipo " + type + ". (Línea " + AnalizadorLexico.LINEA + ")");
						  }
break;
case 23:
//#line 81 "gramatica.y"
{
             						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable");
             					  	sintactico.addAnalisis("Se reconoció declaraciónes de variable SIN TIPO. (Línea " + (AnalizadorLexico.LINEA-1) + ")");
             					   	sintactico.vaciarListaVariables();
             					  }
break;
case 27:
//#line 92 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						     }
break;
case 28:
//#line 96 "gramatica.y"
{
							yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));
						     }
break;
case 34:
//#line 106 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 35:
//#line 107 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 36:
//#line 111 "gramatica.y"
{
							sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores.");
						 	int existente = enAmbito(val_peek(1));
							if (existente < 0) {
								sintactico.setLexemaEnIndex(val_peek(1).ival, "@"+this.ambito);
								sintactico.addListaVariables(val_peek(1).ival);
								sintactico.setUsoEnIndex("var", val_peek(1).ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
							}
						}
break;
case 37:
//#line 122 "gramatica.y"
{
							int existente = enAmbito(val_peek(2));
							if (existente < 0) {
								sintactico.setLexemaEnIndex(val_peek(2).ival, "@"+this.ambito);

								sintactico.addListaVariables(val_peek(2).ival);
								sintactico.setUsoEnIndex("var", val_peek(2).ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
							}
						  }
break;
case 38:
//#line 133 "gramatica.y"
{
                   					int existente = enAmbito(val_peek(0));
                   					if (existente < 0) {
                   						sintactico.setLexemaEnIndex(val_peek(0).ival, "@"+this.ambito);

                   						sintactico.addListaVariables(val_peek(0).ival);
							    	sintactico.setUsoEnIndex("var", val_peek(0).ival);
                   					} else {
                   						sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
                   					}
                   				  }
break;
case 39:
//#line 148 "gramatica.y"
{
				int existente = enAmbito(val_peek(0));
				if (existente < 0) {
					sintactico.setTipoEnIndex(val_peek(1).sval, val_peek(0).ival);
					sintactico.setLexemaEnIndex(val_peek(0).ival, "@"+this.ambito);
					sintactico.setUsoEnIndex("param", val_peek(0).ival);

					sintactico.addListaVariables(val_peek(0).ival);

				} else {
					sintactico.addErrorSintactico("SematicError. ENC_FUN/PARAMS (Línea " + AnalizadorLexico.LINEA + "): el identificador ya ha sido utilizado.");
				}
			}
break;
case 40:
//#line 162 "gramatica.y"
{ sintactico.addErrorSintactico("SematicError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 41:
//#line 166 "gramatica.y"
{
				sintactico.setTipoGlobal(val_peek(0).sval);
			}
break;
case 42:
//#line 169 "gramatica.y"
{
				sintactico.addErrorSintactico("SematicError. ENCAB_FUN(Línea " + AnalizadorLexico.LINEA + "): falta tipo de funcion ");
	 	 		sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta tipo (Línea " + AnalizadorLexico.LINEA + ")");
			}
break;
case 43:
//#line 175 "gramatica.y"
{
					sintactico.addAnalisis("Se reconoce cuerpo de funcion (Línea " + AnalizadorLexico.LINEA + ")");
					this.ambito = borrarAmbito(this.ambito);
 					yyval = val_peek(1);
 				}
break;
case 47:
//#line 185 "gramatica.y"
{ sintactico.addAnalisis("Se reconocen mas parametros de los deseados en la funcion (Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 48:
//#line 188 "gramatica.y"
{
								sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" );

								String lexema = sintactico.getEntradaTablaSimb(val_peek(4).ival).getLexema();

								int existente = enAmbito(val_peek(4));
								if (existente < 0) { /* no existe el id en el ambito*/
									sintactico.setTipoEnIndex(sintactico.getTipo(), val_peek(4).ival);
									sintactico.setLexemaEnIndex(val_peek(4).ival, "@"+this.ambito);
									sintactico.setUsoEnIndex("func", val_peek(4).ival);
									agregarAmbito(lexema);
									sintactico.setUsoParam(sintactico.getEntradaTablaSimb(val_peek(4).ival).getLexema());
									sintactico.vaciarListaVariables();
									yyval = new ParserVal(val_peek(4).ival);
								} else {
									sintactico.addErrorSintactico("SematicError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): el identificador ya ha sido utilizado.");
								}

							}
break;
case 49:
//#line 207 "gramatica.y"
{
	 								sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta identificacion (Línea " + AnalizadorLexico.LINEA + ")");
							   		sintactico.addErrorSintactico("SematicError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): funcion sin identificar.");
								}
break;
case 50:
//#line 213 "gramatica.y"
{
						Token t = sintactico.getEntradaTablaSimb(val_peek(1).ival);
						Nodo n = sintactico.crearNodoControl(t.getLexema(), val_peek(0));
						n.setTipo(t.getTipo());
						sintactico.agregarArbolFuncion(new ParserVal(n),t.getLexema());
						sintactico.clearTipo();
					}
break;
case 51:
//#line 223 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   	  sintactico.checkRetorno(val_peek(2), sintactico.getTipo());
						   	  Nodo nodoRetorno = sintactico.crearNodoControl("return",val_peek(2));
						   	  Nodo n = (Nodo) val_peek(2).obj;
						   	  nodoRetorno.setTipo(n.getTipo());
						   	  yyval = new ParserVal(nodoRetorno);}
break;
case 52:
//#line 229 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 53:
//#line 230 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 231 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 55:
//#line 232 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 63:
//#line 244 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 64:
//#line 245 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 65:
//#line 249 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 66:
//#line 253 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 67:
//#line 256 "gramatica.y"
{
											ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
											yyval = modificado;
										}
break;
case 68:
//#line 260 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("declarativa", val_peek(0), null));
										yyval = modificado;
									}
break;
case 69:
//#line 264 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 70:
//#line 265 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 71:
//#line 270 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 72:
//#line 271 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 73:
//#line 272 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 74:
//#line 276 "gramatica.y"
{
							int existente = enAmbito(val_peek(3));
							if (existente >= 0) {
								ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
								yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
								sintactico.eliminarEntrada(val_peek(3).ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): variable no declarada.");
							}
					  	}
break;
case 75:
//#line 287 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 76:
//#line 289 "gramatica.y"
{
           					int existente = enAmbito(val_peek(3));
						if (existente >= 0) {
							ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
							yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
							sintactico.eliminarEntrada(val_peek(3).ival);
						} else {
							sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): variable no declarada.");}
						}
break;
case 77:
//#line 298 "gramatica.y"
{
	   						int existente = enAmbito(val_peek(2));
							if (existente >= 0) {
								ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
								yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(0)));
								sintactico.eliminarEntrada(val_peek(2).ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): variable no declarada.");
							}
						 }
break;
case 78:
//#line 311 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));}
break;
case 79:
//#line 312 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 80:
//#line 315 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 81:
//#line 316 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 82:
//#line 317 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 83:
//#line 318 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 84:
//#line 319 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 85:
//#line 320 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 86:
//#line 325 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
									sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 87:
//#line 327 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 88:
//#line 328 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 89:
//#line 332 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("condicionIf",val_peek(1)));}
break;
case 90:
//#line 333 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 91:
//#line 334 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 92:
//#line 335 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 93:
//#line 339 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if", val_peek(1), val_peek(0)));}
break;
case 94:
//#line 340 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if", val_peek(0), null));}
break;
case 95:
//#line 341 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 96:
//#line 345 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 97:
//#line 346 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 98:
//#line 350 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 99:
//#line 351 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 100:
//#line 354 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
								   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 101:
//#line 356 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 102:
//#line 357 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 103:
//#line 360 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 104:
//#line 361 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 105:
//#line 362 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 106:
//#line 365 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 107:
//#line 366 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 108:
//#line 369 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 109:
//#line 370 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 110:
//#line 373 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 111:
//#line 375 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 112:
//#line 376 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 113:
//#line 379 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 114:
//#line 380 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 115:
//#line 381 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 116:
//#line 384 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 117:
//#line 385 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 118:
//#line 388 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 119:
//#line 389 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 120:
//#line 392 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
									}
break;
case 121:
//#line 395 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 122:
//#line 396 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 123:
//#line 399 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 124:
//#line 400 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 125:
//#line 401 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 126:
//#line 404 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 127:
//#line 405 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 128:
//#line 408 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 129:
//#line 409 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 130:
//#line 411 "gramatica.y"
{this.contadorIf++; agregarAmbito("if-then"+contadorIf);}
break;
case 131:
//#line 414 "gramatica.y"
{this.ambito = borrarAmbito(this.ambito); agregarAmbito("if-else"+this.contadorIf);}
break;
case 132:
//#line 417 "gramatica.y"
{this.ambito = borrarAmbito(this.ambito);}
break;
case 133:
//#line 424 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
								  yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 134:
//#line 426 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 135:
//#line 427 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 136:
//#line 430 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("condicionWhen",val_peek(0))); agregarAmbito("when"+this.contadorWhen); this.contadorWhen++;}
break;
case 137:
//#line 434 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1))); this.ambito = borrarAmbito(this.ambito); }
break;
case 138:
//#line 435 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 139:
//#line 436 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 140:
//#line 443 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
							  	yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
							  	this.ambito = borrarAmbito(this.ambito);
							}
break;
case 141:
//#line 447 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 142:
//#line 448 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 143:
//#line 449 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");

									int existente = sintactico.encontrarTag(val_peek(6).ival, this.ambito);
									if (existente >= 0) {
										if (sintactico.getEntradaTablaSimb(existente).getUso().equals("tag")) {
											ParserVal nodoTag = new ParserVal(sintactico.crearNodoControl("etiqueta", new ParserVal(sintactico.crearHoja(existente))));
											yyval = new ParserVal( sintactico.crearNodo("for-etiquetado", nodoTag , new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)))));
											sintactico.eliminarEntrada(val_peek(6).ival);
										} else {
											sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador utilizado no es una etiqueta.");
										}
									} else {
										sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): etiqueta invalida");
									}
									this.ambito = borrarAmbito(this.ambito);
								}
break;
case 144:
//#line 468 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));
							sintactico.addErrorSintactico("SematicError. El ambito es "+this.ambito);
						}
break;
case 145:
//#line 473 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 146:
//#line 477 "gramatica.y"
{
						int existente = enAmbito(val_peek(2));
						if (existente >= 0) {
							if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
								String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
								String [] aux = lexExistente.split("@");

								String ambitoExistente = aux[1];

								if ( ambitoExistente.equals(this.ambito)) {
									sintactico.setUsoEnIndex("i32",val_peek(0).ival);
									String typeOP2 = sintactico.getTipoFromTS(val_peek(0).ival);
                                                                        String typeOP1 = sintactico.getTipoFromTS(existente);
                                                                        if (typeOP1.equals(typeOP2)) {
										ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
										ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
										yyval = new ParserVal(sintactico.crearNodoControl("condicionFor", new ParserVal(sintactico.crearNodo(val_peek(1).sval,identificador,constante))));
										sintactico.eliminarEntrada(val_peek(2).ival);
									}else{
									sintactico.addErrorSintactico("SematicError. se reconoce FOR pero hay un problema de tipos en la condicion " + AnalizadorLexico.LINEA);
									}
								} else {
									sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): COND la variable utilizada no corresponde a este for loop");
								}
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): COND la variable utilizada en la condicion debe ser la declarada en el for loop.");
							}
						} else {
							sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): COND la variable usada no ha sido declarada.");
						}

				     	}
break;
case 147:
//#line 511 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 148:
//#line 512 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 149:
//#line 513 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): Falta el ; al final del bloque del for");}
break;
case 150:
//#line 516 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 151:
//#line 517 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 159:
//#line 528 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 160:
//#line 529 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 162:
//#line 535 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 163:
//#line 536 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 164:
//#line 537 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): falta ; al final del bloque del for."); }
break;
case 165:
//#line 540 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        							yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										this.ambito = borrarAmbito(this.ambito);
									}
break;
case 166:
//#line 544 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 167:
//#line 545 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 168:
//#line 546 "gramatica.y"
{
		       									sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");
											int existente = enAmbito(val_peek(6));
											if (existente >= 0) {
												if (sintactico.getEntradaTablaSimb(existente).getUso().equals("tag")) {
													yyval = new ParserVal( sintactico.crearNodo("for-etiquetado", new ParserVal(sintactico.crearHoja(existente)), new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)))));
													sintactico.eliminarEntrada(val_peek(6).ival);
												} else {
													sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador utilizado no es una etiqueta.");
												}
											} else {
												sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): etiqueta invalida");
											}
											this.ambito = borrarAmbito(this.ambito);
										}
break;
case 169:
//#line 565 "gramatica.y"
{
					agregarAmbito("for"+this.contadorFor);
					this.contadorFor++;
					int existente = enAmbito(val_peek(2));
					if (existente < 0){
						sintactico.setTipoEnIndex("i32", val_peek(2).ival);
						sintactico.setTipoEnIndex("i32", val_peek(0).ival);
						sintactico.setLexemaEnIndex(val_peek(2).ival, "@"+this.ambito);
						sintactico.setUsoEnIndex("for_var", val_peek(2).ival);
						ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
						ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
						yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));
					} else {
						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): la variable utilizada para el for loop ya ha sido declarada.");
					}
				   }
break;
case 170:
//#line 586 "gramatica.y"
{
					int existente = enAmbito(val_peek(0));
					if (existente >= 0) {
						if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
							String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
							String [] aux = lexExistente.split("@");
                                                        String ambitoExistente = aux[1];
							if ( ambitoExistente.equals(this.ambito)) {
							 	/* TODO FRAN HACER QUE LA OPERACION FOR TENGA UNA ASIGNACION ENTRE EL ID Y EL ID +/- 1 (+i ; -i)*/
							 	ParserVal hoja = new ParserVal(sintactico.crearHoja(existente));/* variableFor*/
							 	Nodo n = sintactico.crearNodoControl("uno",null);
							 	n.setTipo("i32");
							 	ParserVal uno = new ParserVal(n);
							 	ParserVal operacion = new ParserVal(sintactico.crearNodo(val_peek(1).sval, hoja, uno));
								yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo("=:",new ParserVal(sintactico.crearHoja(existente)),operacion))));
								sintactico.eliminarEntrada(val_peek(0).ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): OP la variable utilizada no corresponde a este for loop");
							}
						} else {
							sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): OP la variable utilizada en la condicion debe ser la declarada en el for loop.");
						}
					} else {
						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): OP la variable usada no ha sido declarada.");
					}
					}
break;
case 179:
//#line 622 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 180:
//#line 626 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 181:
//#line 627 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 182:
//#line 631 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): no se permiten cuerpos de for vacios.");}
break;
case 183:
//#line 636 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
					yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 184:
//#line 638 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                			yyval = new ParserVal(sintactico.crearNodoControl("breakValor", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 185:
//#line 640 "gramatica.y"
{	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 186:
//#line 643 "gramatica.y"
{
							sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
							yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 187:
//#line 646 "gramatica.y"
{ 	sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   					int existente = enAmbito(val_peek(1));
							if (existente < 0 ) {
								sintactico.setLexemaEnIndex(val_peek(1).ival,"@"+this.ambito);
								sintactico.setUsoEnIndex("tag",val_peek(1).ival);
								yyval = new ParserVal(sintactico.crearNodoControl("continue-etiqueta", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador de la etiqueta ya ha sido utilizado.");
							}
                   					}
break;
case 188:
//#line 656 "gramatica.y"
{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 189:
//#line 657 "gramatica.y"
{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 190:
//#line 660 "gramatica.y"
{
								int existente = enAmbito(val_peek(4));
								if (existente >= 0) {
									Token idFuncInvocada = sintactico.getEntradaTablaSimb(existente);
									if (idFuncInvocada.getUso().equals("func")) {
										sintactico.checkParametros(idFuncInvocada.getLexema());
										yyval = new ParserVal(sintactico.crearNodoFunc(existente, val_peek(2)));
										sintactico.eliminarEntrada(val_peek(4).ival);
									} else {
										sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador no corresponde a una funcion.");
									}
								} else {
									sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): funcion no declarada.");
								}

							}
break;
case 192:
//#line 693 "gramatica.y"
{
		    				yyval = new ParserVal(sintactico.crearNodoParam("paramInv", val_peek(2), val_peek(0)));
		    				NodoHijo aux = (NodoHijo)val_peek(2).obj;
						sintactico.addListaVariables(aux.getRefTablaSimbolos());
						NodoHijo aux1 = (NodoHijo)val_peek(0).obj;
                                                sintactico.addListaVariables(aux1.getRefTablaSimbolos());

					}
break;
case 193:
//#line 701 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodoParam("paramInv", val_peek(0), null));

		    				NodoHijo aux = (NodoHijo)val_peek(0).obj;
                                         	sintactico.addListaVariables(aux.getRefTablaSimbolos());

		 			   }
break;
case 194:
//#line 710 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 195:
//#line 714 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 197:
//#line 719 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 198:
//#line 720 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 200:
//#line 724 "gramatica.y"
{
				int existente = enAmbito(val_peek(0));
				if (existente >= 0) {

					yyval = new ParserVal(sintactico.crearHoja(existente));
					sintactico.eliminarEntrada(val_peek(0).ival);
				} else {
					sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable no declarada.");
				}
				}
break;
case 201:
//#line 734 "gramatica.y"
{

				String type = sintactico.getTipoFromTS(val_peek(0).ival);
				if (type.equals("i32"))
				     sintactico.verificarRangoEnteroLargo(val_peek(0).ival);

				yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
                  	}
break;
case 202:
//#line 742 "gramatica.y"
{
				sintactico.setNegativoTablaSimb(val_peek(0).ival);

				yyval = new ParserVal(sintactico.crearHoja(val_peek(1).ival));
                   	}
break;
case 203:
//#line 750 "gramatica.y"
{ yyval.sval = new String("<") ; }
break;
case 204:
//#line 751 "gramatica.y"
{ yyval.sval = new String(">") ; }
break;
case 205:
//#line 752 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 206:
//#line 753 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 207:
//#line 754 "gramatica.y"
{ yyval.sval = new String("=") ; }
break;
case 208:
//#line 755 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 209:
//#line 759 "gramatica.y"
{ yyval.sval = new String("i32"); }
break;
case 210:
//#line 760 "gramatica.y"
{ yyval.sval = new String("f32"); }
break;
//#line 2061 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
