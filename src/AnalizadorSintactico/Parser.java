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

import java.util.Vector;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorLexico.Atributo;

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
public final static short FOR=269;
public final static short CONTINUE=270;
public final static short f32=271;
public final static short cadena=272;
public final static short menorigual=273;
public final static short mayorigual=274;
public final static short distinto=275;
public final static short opasignacion=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    2,    2,    4,    4,    3,    3,
    3,    3,    6,    6,    6,    6,    7,    7,    7,    7,
    7,    9,    9,    9,   16,   16,   17,   17,   18,   18,
   19,   10,   10,   10,   10,   20,   20,   20,   20,   20,
   22,   22,   22,   11,   11,   11,   12,   12,   12,   12,
   12,   12,   13,   13,   13,   13,   13,   13,   24,   24,
   25,   25,   15,   15,   15,   15,   27,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   26,   29,   29,
   31,   31,   31,   31,   31,   31,   31,    5,    5,    5,
    5,   14,   14,   32,   32,   32,   33,   33,   33,   33,
   33,   33,   23,   23,   23,   23,   34,   21,   21,   35,
   35,   35,   36,   36,   36,   30,   30,   30,   30,   30,
   30,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    2,    2,    2,    0,
    5,    3,    5,    7,    2,    6,    5,    5,    4,    6,
    1,    2,    1,    4,    4,    2,    5,    5,    5,    4,
    3,    5,    6,    8,    8,    5,    8,    4,    3,    1,
    3,    1,    7,    2,    5,    6,    1,    9,    9,    9,
    9,    9,    9,    9,   11,   11,   11,    3,    1,    1,
    4,    4,    4,    4,    6,    6,    6,    1,    1,    1,
    2,    2,    3,    2,    2,    2,    2,    2,    4,    4,
    4,    4,    3,    2,    2,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  122,    0,    0,
  123,    0,    0,    0,    0,    0,    9,   10,    0,   16,
   17,   18,   19,   20,   21,    0,    0,    0,    0,   41,
    0,   43,   46,    0,  113,  114,    0,    0,    0,    0,
    0,    0,  112,    0,    0,    0,    0,    0,    0,   64,
    0,    0,    0,    0,    0,    0,    0,    6,   11,   12,
   15,   22,    0,    0,    0,    0,    0,   35,    0,    0,
    0,    0,    0,    0,   88,   92,   89,   90,    0,    0,
    0,    0,   42,    0,    0,  115,  118,  119,  121,  120,
   79,   80,  116,  117,    0,    0,    0,    0,   60,    0,
  104,    0,    0,    0,    0,    0,   25,   26,    0,    0,
    0,    0,    0,    0,    4,   51,    0,    0,   14,    0,
   13,    0,   28,    0,    0,   27,    0,   32,   96,   95,
   94,   98,    0,   97,    0,    0,    7,    0,    8,   93,
   91,    0,    0,    0,    0,   45,   44,  103,    0,    0,
    0,    0,   58,  110,  111,   50,    0,    0,    0,   78,
    0,    0,    0,    0,    0,    0,    0,    3,   24,   23,
   29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   56,    0,    0,   59,   49,   48,   47,
   52,   65,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   33,  101,  102,   99,  100,   84,
    0,    0,   81,   83,   82,    0,    0,    0,    0,    0,
   62,    0,   53,    0,   66,    0,    0,    0,    0,    0,
    0,    0,    0,   31,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   34,   87,   85,   86,    0,
    0,    0,    0,   61,   57,   55,   54,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,    0,    0,    0,
    0,   73,   74,   70,   68,   72,   71,   69,    0,   38,
    0,   37,    0,    0,    0,    0,    0,   40,   36,    0,
    0,   77,    0,   76,   75,
};
final static short yydgoto[] = {                          2,
    3,  194,   16,   72,  139,   74,   75,   19,   63,   20,
   21,   22,   23,   24,   25,   26,   67,  125,   68,  203,
   39,   34,   40,  100,  222,  165,  195,   27,   95,   96,
   76,   77,   78,   41,   42,   43,
};
final static short yysindex[] = {                      -215,
    0,    0,  335,  120,  106,  -34,  -40,    0,   16,   19,
    0,  420, -190, -212,  -28,  354,    0,    0, -189,    0,
    0,    0,    0,    0,    0,  159,   86,  -54,   30,    0,
  -49,    0,    0,  163,    0,    0,  163, -168,  -12,   26,
   60,  -13,    0, -151,  -33,   67, -143,  380, -142,    0,
  245,  -41,   57,  379, -138,   81,  420,    0,    0,    0,
    0,    0,   45, -137,   72, -132,   62,    0,  -46,  359,
  206,  102,   73, -125,    0,    0,    0,    0,   93, -133,
  -41,   97,    0,   35,   98,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  163,  163,  117,  206,    0, -115,
    0,  163,  163,   84,  -32, -111,    0,    0, -118,   12,
   49, -142,   64, -142,    0,    0, -110,  399,    0, -106,
    0, -105,    0, -169,   31,    0, -174,    0,    0,    0,
    0,    0,   94,    0,   76,   41,    0,   96,    0,    0,
    0, -110,  116,   79, -110,    0,    0,    0,  -13,   32,
 -170,  140,    0,    0,    0,    0,   99,   13,  103,    0,
  -95,  320,  104,   89,  105,  108,  109,    0,    0,    0,
    0,  420,   68,  -91,  111,  -87,  134,  -85,  -84,  114,
 -110,   89,  115,    0,  165,  126,    0,    0,    0,    0,
    0,    0,  335,  -28,  -80,   32,   32,   -4,   32,   32,
 -142,  274,   61, -174,    0,    0,    0,    0,    0,    0,
  128,  -44,    0,    0,    0, -142,  130,   28, -142,  206,
    0, -198,    0,  131,    0,  -66,  -65,   32,  -62,  -58,
  -55,  137,  143,    0,  162,  -52,  147,  -45,  148, -142,
   32,  184,  226,  299,  123,    0,  307,  350,  145,  364,
  372,  374,   32,  163,   -5,    0,    0,    0,    0,   32,
  366,  166,   32,    0,    0,    0,    0,  182,  188,   -9,
  189,  190,  191,  192,   20,    0,  389,  193,   32,   -6,
  194,    0,    0,    0,    0,    0,    0,    0,  411,    0,
  142,    0,  412,  197,  199,  415,  203,    0,    0,  199,
  421,    0,  203,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  461,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,  -38,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  343,    0,    0,    0,    0,    0,
    0,    0,  289,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   71,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -15,   11,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  208,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  259,    0,    0,    0,    0,    0,
    0,    0,  304,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  464,    5,  -61,  633,  617,  649,   -7,    0,    0,
  576,    0,    0,   23,    0,    0, -116,    0,  -51,    0,
  -20,    0,    0,  371,    0,  500,  278,    0,  511,  432,
    0,    0,    0,  444,  387,   25,
};
final static int YYTABLESIZE=853;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
    5,  109,  109,   80,  109,   45,  109,  106,  158,  136,
  173,   83,  131,   84,  238,  128,   54,  112,   66,  109,
  109,  109,  109,  109,  108,  108,   33,  108,  102,  108,
   91,  285,   92,  103,  295,  277,  152,   91,   91,   92,
   92,    1,  108,  108,  108,  108,  108,   93,   90,   94,
  107,  107,  162,  276,  228,   49,   56,  244,   52,    5,
  291,  118,   91,  245,   92,   13,   61,   62,  107,   81,
   91,  190,   92,   33,   91,  150,   92,   91,  290,   92,
   13,   55,   64,   14,  109,  184,  241,  235,  122,   86,
  185,  186,    8,  147,   57,  106,   11,    8,   14,  178,
  101,   11,   65,  121,  104,  127,  107,  108,   65,   83,
  105,  204,  108,  106,   48,  114,  171,  116,  123,   66,
  117,  205,  164,    5,  126,   13,  154,  155,  105,  124,
  141,  140,  142,  107,  176,  143,  145,  182,  148,  160,
  153,   13,  156,   14,  159,   37,   51,  197,   98,  169,
   38,  170,  174,  172,  179,  181,   13,  188,  243,   14,
  192,  191,  196,  199,  206,  177,  200,  201,  209,  208,
  214,  215,  216,  219,   14,  225,  202,   31,  106,   13,
   32,  267,  254,  256,  223,  234,  236,   38,  240,  246,
  247,  248,  213,  105,  250,  253,   66,   14,  251,   65,
  299,  252,   65,  257,   13,  258,  260,   38,   71,  129,
  259,  130,  255,  237,   79,   51,   46,  109,  109,   82,
  109,  109,   14,  157,  109,  109,  138,  109,  109,  109,
  109,  109,  109,  275,  109,  109,  109,   44,  105,   98,
  108,  108,  263,  108,  108,   13,  284,  108,  108,  284,
  108,  108,  108,  108,  108,  108,    5,  108,  108,  108,
   87,   88,   89,   14,  187,   13,   67,  107,  189,  107,
  107,  161,   48,  107,  107,   51,  107,  107,  107,  107,
  107,  107,    4,   14,    5,   97,   51,  220,    6,    7,
  146,   69,    8,    9,   10,   70,   11,    4,   68,    5,
  119,  120,  111,    6,    7,   32,   69,    8,    9,   10,
   70,   11,  106,   13,  106,  106,   68,   56,  106,  106,
   48,  106,  106,  106,  106,  106,  106,  105,    7,  105,
  105,   14,  175,  105,  105,   48,  105,  105,  105,  105,
  105,  105,    4,   75,    5,   48,    7,  268,    6,    7,
  264,   69,    8,    9,   10,   70,   11,  265,    4,   13,
    5,   75,   35,   36,    6,    7,  207,   69,    8,    9,
   10,   70,   11,    4,   13,    5,   28,   14,  266,    6,
    7,   68,   69,    8,    9,   10,   70,   11,   29,  210,
  269,  211,   14,   13,  212,   30,    4,  298,    5,   35,
   36,  270,    6,    7,  271,   69,    8,    9,   10,   70,
   11,   14,  272,    7,  273,   64,  135,  134,   13,   35,
   36,    4,  280,    5,  279,    8,   75,    6,    7,   11,
   69,    8,    9,   10,   70,   11,   14,  282,   13,   93,
   90,   94,   12,  283,  286,  287,  288,  292,  289,  293,
  296,  297,  300,  301,  302,  303,   14,   12,  304,   13,
    1,  305,    4,   67,    5,   30,   15,  151,    6,    7,
  224,   69,    8,    9,   10,   70,   11,   14,   58,  109,
   85,  149,    4,    0,    5,    0,    0,    0,    6,    7,
    0,   69,    8,    9,   10,   70,   11,    0,    0,    0,
    0,   28,    0,  115,    0,    0,    0,    0,   50,    0,
    0,    0,    0,   10,    0,   68,    0,   68,    0,    0,
   30,   68,   68,  168,   68,   68,   68,   68,   68,   68,
    4,    0,    5,    0,    0,    0,    6,    7,  233,    0,
    8,    9,   10,    0,   11,    7,    0,    7,  110,    0,
    0,    7,    7,    0,    7,    7,    7,    7,    7,    7,
   75,    0,   75,    0,    0,    0,   75,   75,    0,   75,
   75,   75,   75,   75,   75,    0,    4,    0,    5,  193,
    0,    0,    6,    7,    0,   53,    8,    9,   10,    0,
   11,    4,    0,    5,    0,    0,    0,    6,    7,    0,
    0,    8,    9,   10,   53,   11,    0,    0,    0,    0,
    4,  163,    5,  166,  132,  133,    6,    7,    0,   17,
    8,    9,   10,    0,   11,    0,    0,  113,   17,    0,
    0,    0,   59,    0,    0,    4,    0,    5,    0,    0,
    0,    6,    7,    0,    0,    8,    9,   10,    0,   11,
    0,   18,   87,   88,   89,    4,  144,    5,    0,   73,
   18,    6,    7,  198,   60,    8,    9,   10,    0,   11,
   59,    0,   99,   17,    0,    0,    4,    0,    5,    0,
    0,  218,    6,    7,    0,    0,    8,    9,   10,    0,
   11,    0,  167,    0,    0,    0,    0,    0,    0,    0,
  232,    0,   60,  137,    0,   18,  226,  227,  229,  230,
  231,    0,    0,    0,    0,  239,    0,  180,  242,    0,
  183,    0,    0,    0,    0,    0,    0,    0,  229,   99,
  137,    0,    0,    0,   59,    0,    0,    0,  249,  261,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  262,    0,    0,    0,    0,  217,    0,    0,    0,
    0,    0,    0,  274,    0,    0,   60,    0,    0,    0,
  278,    0,    0,  281,    0,    0,    0,    0,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   17,  294,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
   18,    0,    0,    0,    0,    0,    0,  221,   59,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   18,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,  137,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   41,   58,   43,   40,   45,   41,   41,   71,
  127,   61,   59,   34,   59,   67,   12,   59,   26,   58,
   59,   60,   61,   62,   40,   41,    4,   43,   42,   45,
   43,   41,   45,   47,   41,   41,   98,   43,   43,   45,
   45,  257,   58,   59,   60,   61,   62,   60,   61,   62,
   40,   41,   41,   59,   59,   40,  269,  256,   40,   59,
   41,   57,   43,  262,   45,   40,  256,  257,   58,   40,
   43,   59,   45,   51,   43,   96,   45,   43,   59,   45,
   40,  272,  257,   58,  123,  256,   59,  204,   44,  258,
  261,  262,  267,   59,  123,   40,  271,  267,   58,   59,
   41,  271,   41,   59,  256,   44,   40,  123,   41,   61,
   40,   44,  256,   58,  257,   59,  124,  256,  256,  127,
   40,  173,   59,  123,  257,   40,  102,  103,   58,   58,
  256,   59,   40,  123,   59,  269,   40,   59,   41,  258,
  256,   40,   59,   58,  256,   40,  257,   59,  123,  256,
   45,  257,   59,  123,   59,   40,   40,   59,  220,   58,
  256,   59,   59,   59,  256,  125,   59,   59,  256,   59,
  256,  256,   59,   59,   58,  256,  172,   58,  123,   40,
   61,   59,   40,  235,   59,  125,   59,   45,   59,   59,
  257,  257,   59,  123,  257,   59,  204,   58,  257,   41,
   59,  257,   41,  256,   40,   59,   59,   45,  123,  256,
  256,  258,  233,  258,  269,  257,  257,  256,  257,  269,
  259,  260,   58,  256,  263,  264,  125,  266,  267,  268,
  269,  270,  271,  254,  273,  274,  275,  272,  272,  123,
  256,  257,   59,  259,  260,   40,  256,  263,  264,  256,
  266,  267,  268,  269,  270,  271,  256,  273,  274,  275,
  273,  274,  275,   58,  125,   40,   59,  257,  256,  259,
  260,  260,  257,  263,  264,  257,  266,  267,  268,  269,
  270,  271,  257,   58,  259,  260,  257,  123,  263,  264,
  256,  266,  267,  268,  269,  270,  271,  257,   40,  259,
  256,  257,   58,  263,  264,   61,  266,  267,  268,  269,
  270,  271,  257,   40,  259,  260,   58,  269,  263,  264,
  257,  266,  267,  268,  269,  270,  271,  257,   40,  259,
  260,   58,  257,  263,  264,  257,  266,  267,  268,  269,
  270,  271,  257,   40,  259,  257,   58,   41,  263,  264,
  125,  266,  267,  268,  269,  270,  271,   59,  257,   40,
  259,   58,  257,  258,  263,  264,  256,  266,  267,  268,
  269,  270,  271,  257,   40,  259,  257,   58,  256,  263,
  264,  123,  266,  267,  268,  269,  270,  271,  269,  256,
   41,  258,   58,   40,  261,  276,  257,  256,  259,  257,
  258,  257,  263,  264,   41,  266,  267,  268,  269,  270,
  271,   58,   41,  125,   41,  257,   58,   59,   40,  257,
  258,  257,  257,  259,   59,  267,  123,  263,  264,  271,
  266,  267,  268,  269,  270,  271,   58,  256,   40,   60,
   61,   62,  123,  256,  256,  256,  256,   59,  257,  257,
  257,   41,   41,  257,  256,   41,   58,  123,  256,   40,
    0,   41,  257,  256,  259,  123,    3,   97,  263,  264,
  193,  266,  267,  268,  269,  270,  271,   58,  125,   48,
   37,   95,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   -1,  257,   -1,  125,   -1,   -1,   -1,   -1,    9,   -1,
   -1,   -1,   -1,  269,   -1,  257,   -1,  259,   -1,   -1,
  276,  263,  264,  125,  266,  267,  268,  269,  270,  271,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,   -1,
  267,  268,  269,   -1,  271,  257,   -1,  259,   49,   -1,
   -1,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,   -1,  257,   -1,  259,  260,
   -1,   -1,  263,  264,   -1,   10,  267,  268,  269,   -1,
  271,  257,   -1,  259,   -1,   -1,   -1,  263,  264,   -1,
   -1,  267,  268,  269,   29,  271,   -1,   -1,   -1,   -1,
  257,  112,  259,  114,  256,  257,  263,  264,   -1,    3,
  267,  268,  269,   -1,  271,   -1,   -1,   52,   12,   -1,
   -1,   -1,   16,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,   -1,   -1,  267,  268,  269,   -1,  271,
   -1,    3,  273,  274,  275,  257,   81,  259,   -1,   27,
   12,  263,  264,  164,   16,  267,  268,  269,   -1,  271,
   54,   -1,   40,   57,   -1,   -1,  257,   -1,  259,   -1,
   -1,  182,  263,  264,   -1,   -1,  267,  268,  269,   -1,
  271,   -1,  117,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  201,   -1,   54,   71,   -1,   57,  196,  197,  198,  199,
  200,   -1,   -1,   -1,   -1,  216,   -1,  142,  219,   -1,
  145,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  218,   97,
   98,   -1,   -1,   -1,  118,   -1,   -1,   -1,  228,  240,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  241,   -1,   -1,   -1,   -1,  181,   -1,   -1,   -1,
   -1,   -1,   -1,  253,   -1,   -1,  118,   -1,   -1,   -1,
  260,   -1,   -1,  263,   -1,   -1,   -1,   -1,  162,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  172,  279,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  193,
  162,   -1,   -1,   -1,   -1,   -1,   -1,  185,  202,   -1,
  172,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  193,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  202,   -1,  220,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=276;
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
"fun","Return","BREAK","i32","when","FOR","CONTINUE","f32","cadena",
"menorigual","mayorigual","distinto","opasignacion",
};
final static String yyrule[] = {
"$accept : programa",
"programa : encabezado_prog bloque_sentencias",
"encabezado_prog : id",
"bloque_sentencias : bloque_sentencias '{' sentencia '}'",
"bloque_sentencias : '{' sentencia '}'",
"bloque_sentencias : '{' sentencia",
"bloque_sentencias : sentencia '}'",
"bloque_sentencias_FOR : sentencias_FOR",
"bloque_sentencias_FOR : bloque_sentencias_FOR sentencias_FOR",
"sentencia : declarativas",
"sentencia : ejecutables",
"sentencia : sentencia declarativas",
"sentencia : sentencia ejecutables",
"declarativas : tipo lista_de_variables ';'",
"declarativas : tipo lista_de_variables error",
"declarativas : tipo error",
"declarativas : declaracion_func",
"ejecutables : asignacion",
"ejecutables : salida",
"ejecutables : sentencia_If",
"ejecutables : expresion_FOR",
"ejecutables : sentencia_when",
"lista_de_variables : id",
"lista_de_variables : lista_de_variables ',' id",
"lista_de_variables : lista_de_variables id error",
"encabezado_func : fun id '('",
"encabezado_func : fun '(' error",
"parametro : tipo id",
"parametro : id error",
"asig_fun : ':' tipo",
"asig_fun :",
"cola_func : ')' asig_fun '{' cuerpo_fun '}'",
"declaracion_func : encabezado_func parametro cola_func",
"declaracion_func : encabezado_func parametro ',' parametro cola_func",
"declaracion_func : encabezado_func parametro ',' parametro ',' parametro cola_func",
"declaracion_func : encabezado_func cola_func",
"cuerpo_fun : sentencia Return '(' expresion ')' ';'",
"cuerpo_fun : sentencia Return expresion ')' ';'",
"cuerpo_fun : sentencia Return '(' expresion ';'",
"cuerpo_fun : sentencia Return expresion ';'",
"cuerpo_fun : sentencia Return '(' expresion ')' error",
"op_asignacion : opasignacion",
"op_asignacion : ':' '='",
"op_asignacion : '='",
"asignacion : id op_asignacion expresion ';'",
"asignacion : id op_asignacion expresion error",
"asignacion : id expresion_FOR",
"salida : out '(' cadena ')' ';'",
"salida : out '(' cadena ')' error",
"salida : out '(' cadena error ';'",
"salida : out cadena error ';'",
"salida : '(' cadena error",
"salida : out '(' ')' error ';'",
"sentencia_If : If condicion_if then cuerpo_If end_if ';'",
"sentencia_If : If condicion_if then cuerpo_If Else cuerpo_Else end_if ';'",
"sentencia_If : If condicion_if then cuerpo_If Else cuerpo_Else end_if error",
"sentencia_If : If condicion_if then cuerpo_If error",
"sentencia_If : If condicion_if then cuerpo_If Else cuerpo_Else error ';'",
"sentencia_If : If condicion_if cuerpo_If error",
"cuerpo_If : '{' bloque_sentencias_FOR '}'",
"cuerpo_If : sentencias_FOR",
"cuerpo_Else : '{' bloque_sentencias_FOR '}'",
"cuerpo_Else : sentencias_FOR",
"sentencia_when : when '(' condicion_for ')' then cuerpo_when ';'",
"sentencia_when : when condicion_for",
"sentencia_when : when '(' condicion_for then error",
"sentencia_when : when '(' condicion_for ')' cuerpo_when error",
"cuerpo_when : bloque_sentencias",
"encabezado_FOR : FOR '(' asignacion ';' condicion_for ';' signo id ')'",
"encabezado_FOR : FOR asignacion ';' condicion_for ';' signo id ')' error",
"encabezado_FOR : FOR '(' asignacion ';' condicion_for ';' signo id error",
"encabezado_FOR : FOR '(' asignacion condicion_for ';' signo id ')' error",
"encabezado_FOR : FOR '(' asignacion ';' condicion_for signo id ')' error",
"encabezado_FOR : FOR '(' ';' condicion_for ';' signo id ')' error",
"encabezado_FOR : FOR '(' asignacion ';' ';' signo id ')' error",
"encabezado_FOR : id ':' FOR '(' asignacion ';' condicion_for ';' signo id ')'",
"encabezado_FOR : ':' FOR '(' asignacion ';' condicion_for ';' signo id ')' error",
"encabezado_FOR : id FOR '(' asignacion ';' condicion_for ';' signo id ')' error",
"condicion_for : id comparador cte",
"signo : '+'",
"signo : '-'",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' ';'",
"cuerpo_FOR : bloque_sentencias_FOR '}' ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' Else cte ';'",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' Else ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' cte ';' error",
"sentencias_FOR : ejecutables",
"sentencias_FOR : sentencia_BREAK",
"sentencias_FOR : sentencia_CONTINUE",
"sentencias_FOR : declarativas error",
"expresion_FOR : encabezado_FOR cuerpo_FOR",
"expresion_FOR : encabezado_FOR sentencias_FOR ';'",
"sentencia_BREAK : BREAK ';'",
"sentencia_BREAK : BREAK cte",
"sentencia_BREAK : BREAK error",
"sentencia_CONTINUE : CONTINUE ';'",
"sentencia_CONTINUE : CONTINUE error",
"sentencia_CONTINUE : CONTINUE ':' id ';'",
"sentencia_CONTINUE : CONTINUE ':' ';' error",
"sentencia_CONTINUE : CONTINUE id ';' error",
"sentencia_CONTINUE : CONTINUE ':' id error",
"condicion_if : '(' expresion_relacional ')'",
"condicion_if : expresion_relacional ')'",
"condicion_if : '(' expresion_relacional",
"condicion_if : expresion_relacional",
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

//#line 238 "gramatica.y"

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;

public void setLexico(AnalizadorLexico lexico) { this.lexico = lexico; }

public void setSintactico(AnalizadorSintactico sintactico) { this.sintactico = sintactico; }

public AnalizadorLexico getLexico() { return this.lexico; }

public AnalizadorSintactico getSintactico() { return this.sintactico; }

public int yylex() {
    int token = lexico.procesarYylex();
    if (lexico.getRefTablaSimbolos() != -1)
        yylval = new ParserVal(lexico.getRefTablaSimbolos());
    return token;
}

public void yyerror(String string) {
	//sintactico.addErrorSintactico("par: " + string);
}


//#line 618 "Parser.java"
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
case 5:
//#line 32 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
break;
case 6:
//#line 33 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
break;
case 13:
//#line 51 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 14:
//#line 52 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
break;
case 15:
//#line 53 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el nombre de la variable"); }
break;
case 24:
//#line 66 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores."); }
break;
case 25:
//#line 69 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" ); }
break;
case 26:
//#line 70 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 28:
//#line 74 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 30:
//#line 79 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 31:
//#line 81 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 34:
//#line 87 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 36:
//#line 91 "gramatica.y"
{ sintactico.addAnalisis("SyntaxError. RETURN_FUN(Línea " + AnalizadorLexico.LINEA + "): reconoce retorno de funcion"); }
break;
case 37:
//#line 92 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 38:
//#line 93 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 39:
//#line 94 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 40:
//#line 95 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 41:
//#line 97 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 42:
//#line 98 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 43:
//#line 99 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 45:
//#line 103 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 48:
//#line 108 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 49:
//#line 109 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 50:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 51:
//#line 111 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 52:
//#line 112 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 53:
//#line 116 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 54:
//#line 117 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 55:
//#line 118 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 56:
//#line 119 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 57:
//#line 120 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 58:
//#line 121 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 64:
//#line 135 "gramatica.y"
{ sintactico.addErrorSintactico( " SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis." ); }
break;
case 65:
//#line 136 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 66:
//#line 137 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 69:
//#line 144 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
break;
case 70:
//#line 145 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
break;
case 71:
//#line 146 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 72:
//#line 147 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 73:
//#line 148 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
break;
case 74:
//#line 149 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
break;
case 76:
//#line 151 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
break;
case 77:
//#line 152 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
break;
case 82:
//#line 163 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
break;
case 83:
//#line 164 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
break;
case 84:
//#line 165 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 86:
//#line 168 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
break;
case 87:
//#line 169 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta el Else. "); }
break;
case 91:
//#line 175 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias declarativas adentro del FOR"); }
break;
case 96:
//#line 185 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 98:
//#line 189 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
break;
case 100:
//#line 191 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
break;
case 101:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
break;
case 102:
//#line 193 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 104:
//#line 197 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 105:
//#line 198 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 106:
//#line 199 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 116:
//#line 219 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 117:
//#line 220 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 118:
//#line 221 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 119:
//#line 222 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 120:
//#line 223 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 121:
//#line 224 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 122:
//#line 227 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 123:
//#line 231 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 1033 "Parser.java"
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
