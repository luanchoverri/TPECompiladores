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
    3,   10,   10,   10,   10,   10,   10,   15,   15,   11,
   11,   11,   11,   11,   11,   11,   12,   12,   12,   23,
   23,   24,   24,   26,   26,   27,   13,   13,   13,   13,
   29,   29,   29,   29,   29,   28,   32,   32,   32,   32,
   32,   32,   32,   32,   35,   35,   31,   31,   31,   31,
    7,    7,    7,   16,   16,   16,   36,   36,   17,   17,
   17,   17,   17,   17,   18,   18,   18,   37,   37,   37,
   37,   38,   38,   38,   40,   40,   41,   41,   42,   42,
   42,   43,   43,   43,   44,   44,   45,   45,   33,   33,
   33,   46,   46,   46,   47,   47,   48,   48,   49,   49,
   49,   50,   50,   50,   51,   51,   52,   52,   14,   14,
   14,   56,   56,   56,   57,   57,   57,   57,   57,   58,
   62,   55,   59,   59,   60,   60,    9,    9,    9,    9,
    9,    9,    9,    9,    9,   19,   65,   65,   34,   34,
   34,   34,   34,   61,   63,   54,   54,   54,   54,   54,
   54,   54,   54,   54,   53,   53,   21,   21,   21,   22,
   22,   22,   22,   20,   20,   66,   66,   39,   30,   30,
   68,   68,   68,   67,   67,   67,   64,   64,   64,   64,
   64,   64,   25,   25,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    3,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    2,    2,    2,    3,    1,    3,
    3,    2,    2,    2,    0,    5,    3,    5,    7,    2,
    5,    5,    5,    4,    5,    1,    1,    1,    1,    1,
    1,    1,    2,    2,    2,    1,    2,    2,    1,    1,
    1,    2,    1,    4,    4,    4,    3,    2,    5,    5,
    5,    4,    3,    5,    5,    6,    5,    3,    2,    2,
    1,    2,    1,    1,    4,    2,    4,    2,    5,    5,
    4,    2,    1,    2,    4,    2,    4,    2,    5,    5,
    4,    2,    1,    2,    4,    2,    4,    2,    5,    5,
    4,    2,    1,    2,    4,    2,    4,    2,    6,    6,
    6,    4,    4,    4,    5,   12,   11,   13,   14,    3,
    3,    3,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    2,    1,    4,    1,    5,   12,
   11,   13,   14,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    2,    2,    3,    2,    2,
    4,    4,    2,    5,    4,    3,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   26,   18,
   19,    0,   25,   27,   30,   31,   32,   33,   34,    0,
    0,    0,  156,    0,   71,    0,    0,    0,   73,    0,
   37,  194,  195,    0,    0,    0,    0,    0,  193,    0,
    0,    0,    0,    0,  179,    0,  177,    0,    0,    0,
    0,    0,    0,  183,    0,  180,    0,    0,    0,    0,
    0,   10,    0,    0,    0,    6,   20,   21,    0,   35,
   36,    0,  203,  204,    0,    0,    0,   50,   38,    0,
    0,    0,    0,   72,    0,    0,    0,    0,    0,  196,
  199,  200,  202,  201,  145,  146,  197,  198,    0,    0,
    0,    0,    0,    0,   94,   89,    0,    0,    0,    0,
    0,   40,   41,  178,   22,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   23,   15,    0,    0,    9,
    8,    7,    0,    4,   83,    0,   24,   43,    0,    0,
    0,   47,   42,  185,    0,    0,    0,    0,   78,    0,
   75,   74,   76,   88,    0,    0,    0,    0,    0,   96,
    0,    0,   98,    0,   92,  191,  192,   82,    0,    0,
    0,  142,    0,    0,    0,    0,    0,    0,    0,    0,
  182,  181,   14,   13,   12,   11,    3,   44,    0,    0,
  184,  186,    0,   77,   29,    0,   87,    0,    0,   85,
   81,   80,   79,   84,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  144,    0,  147,  148,  149,  151,
  152,  153,    0,  150,  135,    0,  140,    0,    0,    0,
   70,   57,   59,   60,    0,    0,    0,   62,    0,   69,
   58,   61,    0,   48,    0,   95,   28,   86,   97,    0,
    0,  129,  131,  130,    0,    0,    0,    0,    0,    0,
   16,  154,  155,    0,    0,    0,    0,    0,   63,   64,
   46,   68,   67,    0,    0,  133,  132,  134,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
    0,  141,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   49,    0,    0,    0,    0,  106,    0,  108,  101,
    0,  102,  104,    0,    0,   54,    0,  143,  165,    0,
    0,    0,  116,    0,  118,  111,    0,  112,  114,    0,
    0,    0,    0,    0,    0,    0,    0,  100,   99,   53,
   55,   51,   52,    0,   66,    0,    0,  110,  109,    0,
    0,    0,    0,    0,  166,  167,  170,  171,  172,  169,
  168,  173,  158,  159,    0,    0,    0,    0,  105,  107,
    0,  115,   65,  117,    0,    0,    0,    0,  175,  174,
    0,    0,  137,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  176,    0,  136,    0,    0,    0,    0,
    0,  126,    0,  128,  121,    0,  122,  124,  157,    0,
  138,    0,    0,    0,    0,    0,  120,  119,  139,    0,
    0,    0,    0,  125,  127,    0,    0,  161,    0,    0,
  160,    0,    0,  162,  163,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   71,   72,   40,  270,  225,  226,
   21,   22,   23,   24,  206,   25,   26,   27,   28,   29,
   30,   31,   32,   86,   87,  150,   88,  247,  233,   46,
  249,  355,  251,  371,  356,   98,   47,  113,   48,  114,
  115,  234,  293,  294,  295,  306,  307,  308,  372,  400,
  401,  402,  388,  373,   61,  185,   33,  132,  235,  109,
  133,  237,  302,  110,  374,   91,   49,   50,
};
final static short yysindex[] = {                      -224,
    0,    0,  822,  -37,   69,  -23,    3,   93, -216,    6,
   19,  105, -216,   -1,  989, -220,  -54,  837,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0, -200,
 -173,    4,    0,   47,    0, -216,   59,  -34,    0,   31,
    0,    0,    0,  101, -130,   -7, -166,  104,    0,   66,
 -107,  -21,  150,  -78,    0,  135,    0,  147,   73,  -47,
  178,  -11,   44,    0,  214,    0,   49,  226,   16,   55,
  -28,    0,  852,   74,  989,    0,    0,    0,   80,    0,
    0,   84,    0,    0,  257,  159,  116,    0,    0,  316,
  337,  336,  348,    0,  332, -190,   43,  338,  361,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  101,  101,
 1004, 1038,  145,  151,    0,    0,  101,  101,  355,  -13,
  169,    0,    0,    0,    0,  158,  -31,  167,  365,  173,
  -11,  392,  378,  188,  393,    0,    0,  195, -129,    0,
    0,    0,  198,    0,    0,  867,    0,    0, -223,  334,
 -146,    0,    0,    0,  400,  101,  206,  196,    0,  210,
    0,    0,    0,    0,   66,  166,  -36, 1058,  647,    0,
 -199,  647,    0,  410,    0,    0,    0,    0,  413,  -40,
  415,    0,  903,  167,  416,  422,  424,  234,  504,  -47,
    0,    0,    0,    0,    0,    0,    0,    0,  919,  160,
    0,    0,  -11,    0,    0, 1072,    0,  244, 1088,    0,
    0,    0,    0,    0,  989,  957,  442,  247,  249,  -47,
  448,   69,   86,  935,    0,  254,    0,    0,    0,    0,
    0,    0,  255,    0,    0,  455,    0,  -35,   69,   24,
    0,    0,    0,    0,  259,  260,  395,    0,  919,    0,
    0,    0, -146,    0,  265,    0,    0,    0,    0,  584,
  262,    0,    0,    0,  465,  -47, -145,  101,  138,  601,
    0,    0,    0,  166,   28,  -88,  -11,  269,    0,    0,
    0,    0,    0,  487,  471,    0,    0,    0,  166,  477,
  616,  632, -169,  280,  286,  370,  290,  488,  489,    0,
  295,    0,  516,   21,  219, -150,  297,  303,  302,  -11,
  520,    0,  -47,  307,  166,  935,    0,  935,    0,    0,
   39,    0,    0,  306,  109,    0,  311,    0,    0,  309,
  -29,  313,    0,  313,    0,    0,  111,    0,    0,  509,
  319,  655,  512,  551,  323,  671,  687,    0,    0,    0,
    0,    0,    0,  -11,    0,  184, 1018,    0,    0,  -47,
  523,   69,  974,  329,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  166,  504,  333,  552,    0,    0,
  344,    0,    0,    0,  535,  -47,  122,  717,    0,    0,
  346,  349,    0,  504,  545,  166,  553,  736,  753, -126,
  351,  357,  557,    0,  576,    0,  363,  -47,  369,  166,
  974,    0,  974,    0,    0,  112,    0,    0,    0,  504,
    0,  561,  567,  371,  783,  806,    0,    0,    0,  166,
  655,  374,  581,    0,    0,  375,  377,    0,  655,  590,
    0,  379,  655,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  575,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  638,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  575,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  126,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   42,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  517,    0,    0,    0,    0,    0,
    0,  602,    0,    0,   37,    0,    0,    0,  133,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -114,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   96,  -27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  583,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  575,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  519,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -64,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  583,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -63,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   22,    0,    0,  502,  -56,  -93, -132,   -3,
  327,   26,    0,    0,  474,  -22,  114,    0,  -39,  175,
  305,  366,    0,  -84,  499,    0,  -46,    0, -181,  -32,
    0,  193,    0,  130,  321,    0, -188,  539,  609,    0,
  546,    0,    0,    0,  372,    0,    0,  352,    0,    0,
    0,  268,  -90,  266,  -58,  -79,    0,  385, -254,  310,
    0,    0,    0,  608, -358,    0,   70,  564,
};
final static int YYTABLESIZE=1358;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
   96,  127,   37,   37,   37,  130,   36,   97,   36,  184,
   37,   20,  139,  188,   77,  143,   52,  248,  213,  121,
   38,   38,  275,   39,   39,   39,   94,  180,  275,   41,
  142,   39,    1,  267,   58,  105,   73,  106,   68,  152,
   34,    5,   54,   83,   85,   60,  129,   84,  186,   39,
  276,   74,  107,  104,  108,   80,  207,   70,   63,   41,
   16,   89,  208,  278,  432,  159,  200,  248,   75,   77,
  160,   20,  437,  129,  188,   45,   39,  166,  194,  194,
  442,  194,   81,  194,  445,  105,  320,  106,   94,  377,
   36,  271,  321,  111,  112,  194,  146,  349,  190,   90,
  190,  162,  190,   45,  217,  336,   92,  117,   44,   79,
   82,  337,  118,   45,  291,  292,  190,  190,  190,  190,
   83,  392,  248,  248,   84,  268,  194,  100,  195,  415,
   45,  236,  107,  104,  108,  416,  189,  300,  189,  407,
  189,   93,   77,  332,  116,   45,  255,   93,  119,  229,
  248,   57,  248,  254,  189,  189,  189,  189,  317,  319,
  370,  265,   67,   66,    5,  429,  227,  352,  284,  359,
  428,  304,  305,  387,  248,  248,  242,  123,  298,   20,
  105,  370,  106,  271,  229,  271,  176,  177,  103,  122,
  269,  113,  123,  124,  103,  241,  297,  113,  123,   85,
   85,  227,  151,  253,  216,  125,  370,  290,  105,   59,
  106,   20,   77,  300,  300,  212,  370,  370,  128,   34,
  309,   34,  346,   16,  347,  202,  242,  141,  183,  370,
  229,  370,  188,  188,   93,  296,  260,  312,   35,   35,
   35,  370,  179,  370,  370,  282,   35,  227,   51,  370,
  120,  229,  229,  341,  343,   69,   77,  370,   16,   53,
   82,  370,   59,   41,   35,  101,  102,  103,  227,  227,
   83,  137,  134,  138,   84,   62,  229,  331,  229,  239,
  277,  242,  242,    6,  136,  223,    8,   95,   43,  240,
   12,   35,  194,  227,  348,  227,  303,  381,  161,   11,
  131,  385,  228,   34,  229,  135,  229,  229,  382,  242,
  140,  242,  243,  190,  149,   42,   43,  190,  190,  365,
  425,  227,  426,  227,  227,   42,   43,  397,  252,  145,
  190,  190,  190,  242,  242,  147,  229,  228,  364,  148,
  365,  334,   42,   43,   78,  101,  102,  103,   55,  422,
   56,  189,   16,  227,  229,  189,  189,   42,   43,  364,
   64,   65,  243,  230,  351,  365,  358,  427,  189,  189,
  189,  227,  153,  244,  154,  365,  365,  155,  252,  156,
  229,  398,  399,  228,  364,   91,   91,  157,  365,  158,
  365,  250,   90,   90,  364,  364,  163,  227,  230,   78,
  365,  164,  365,  365,  228,  228,  174,  364,  365,  364,
  325,  112,  105,  178,  106,  182,  365,  243,  243,  364,
  365,  364,  364,  244,  181,   94,  183,  364,  324,  228,
  187,  228,  189,  252,  252,  364,  190,  170,  173,  364,
  331,  283,  239,  191,  230,  243,    6,  243,  223,    8,
  193,  192,  240,   12,   69,  366,  199,  228,  201,  228,
  228,  252,  203,  252,   93,  230,  230,  204,  210,  243,
  243,  211,   78,  214,  218,  331,  366,  239,  244,  244,
  219,    6,  220,  223,    8,  252,  252,  240,   12,  228,
  230,  221,  230,  231,  170,  205,  333,  335,  205,  258,
  262,  366,  263,  245,  264,  266,  244,  228,  244,  272,
  273,  366,  366,  274,  279,  280,  367,  288,  230,  281,
  230,  230,  285,  289,  366,  310,  366,   85,  231,  313,
  244,  244,  257,  228,  315,  257,  366,  367,  366,  366,
  292,  323,   78,   16,  366,  326,  327,  328,  383,  383,
  230,  329,  366,  245,  232,  330,  366,  305,  339,  340,
  342,  350,  367,  344,  246,  354,  353,  360,  230,  331,
  375,  239,  367,  367,  231,    6,  361,  223,    8,  378,
  386,  240,   12,  301,  390,  367,   78,  367,  393,  232,
   16,  376,  394,  396,  230,  231,  231,  367,  314,  367,
  367,  395,  405,  408,  406,  367,   16,  431,  245,  245,
  410,  399,  418,  367,  246,  419,  420,  367,  421,  430,
  231,  439,  231,   16,  345,  423,  224,  433,  389,  438,
  443,  440,  441,   39,  444,  232,  245,    1,  245,   45,
   16,  164,  187,   56,  196,  209,  368,  198,  231,  171,
  231,  231,   99,  404,  357,   16,  232,  232,  338,  175,
  245,  245,  311,  412,  414,  322,  126,  368,  417,  246,
  246,   16,  165,  224,    0,    0,  389,    0,  389,    0,
  231,  232,    0,  232,  391,    0,   16,    0,    0,  363,
  404,  404,  368,    0,   16,    0,    0,  246,  231,  246,
    0,    0,  368,  368,    0,  409,    0,  369,  287,  232,
   16,  232,  232,    0,    0,  368,    0,  368,    0,  424,
    0,  246,  246,    0,  231,  299,   16,  368,  369,  368,
  368,    0,    0,    0,    0,  368,    0,    0,  316,  436,
    0,  232,    0,  368,    0,    0,    0,  368,    0,    0,
    0,    0,    0,  369,  318,    0,   16,    0,    0,  232,
    4,    0,  222,  369,  369,    0,    6,    7,  223,    8,
    9,   10,   11,   12,   13,   16,  369,  363,  369,    0,
   14,    0,    0,    0,    0,  232,    0,    0,  369,    0,
  369,  369,   16,    0,    0,  379,  369,    0,    0,    0,
    0,    0,    0,    0,  369,    0,    0,    4,  369,  222,
    0,  380,    0,    6,    7,  223,    8,    9,   10,   11,
   12,   13,   16,  238,    0,  362,    0,   14,    0,    6,
    7,  223,    8,    9,   10,  240,   12,   13,    0,  286,
    4,  403,    5,   14,    0,   16,    6,    7,    0,    8,
    9,   10,   11,   12,   13,    0,    0,    4,  411,  222,
   14,   16,    0,    6,    7,  223,    8,    9,   10,   11,
   12,   13,    4,    0,  222,  413,   16,   14,    6,    7,
  223,    8,    9,   10,   11,   12,   13,    0,    4,    0,
  222,   16,   14,    0,    6,    7,  223,    8,    9,   10,
   11,   12,   13,  167,    0,    5,   16,  434,   14,    6,
    0,  238,    8,  362,    0,   11,   12,    6,    7,  223,
    8,    9,   10,  240,   12,   13,    0,    4,    0,  222,
  435,   14,    0,    6,    7,  223,    8,    9,   10,   11,
   12,   13,   16,    4,   15,  222,    0,   14,    0,    6,
    7,  223,    8,    9,   10,   11,   12,   13,   16,    0,
    0,   76,    0,   14,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  238,   16,  362,  144,    0,    0,    6,
    7,  223,    8,    9,   10,  240,   12,   13,    0,    0,
    0,  197,  238,   14,  362,    0,   16,    0,    6,    7,
  223,    8,    9,   10,  240,   12,   13,    0,    0,  238,
    0,  362,   14,   16,    0,    6,    7,  223,    8,    9,
   10,  240,   12,   13,    0,  215,    0,    0,   16,   14,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  238,
    0,  362,    0,   16,    0,    6,    7,  223,    8,    9,
   10,  240,   12,   13,    0,    0,    0,   16,    0,   14,
    0,    0,  238,    0,  362,    0,    0,    0,    6,    7,
  223,    8,    9,   10,  240,   12,   13,   16,    4,    0,
    5,  261,   14,    0,    6,    7,    0,    8,    9,   10,
   11,   12,   13,    4,    0,    5,    0,   16,   14,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    4,    0,
    5,   16,    0,   14,    6,    7,    0,    8,    9,   10,
   11,   12,   13,    4,    0,    5,  169,   16,   14,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
    0,    0,  384,   14,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    4,
  172,    5,    0,    0,    0,    6,    7,    0,    8,    9,
   10,   11,   12,   13,    0,  238,    0,  239,    0,   14,
  169,    6,    7,  223,    8,    9,   10,  240,   12,   13,
    0,    4,    0,  222,    0,   14,  256,    6,    7,  223,
    8,    9,   10,   11,   12,   13,    0,    0,    0,    0,
    0,   14,  259,    4,    0,    5,    0,    0,    0,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
  238,    0,  362,   14,    0,    0,    6,    7,  223,    8,
    9,   10,  240,   12,   13,    4,    0,    5,    0,    0,
   14,    6,    7,    0,    8,    9,   10,   11,   12,   13,
  167,    0,    5,  168,  112,   14,    6,    0,    0,    8,
    0,    0,   11,   12,  331,    0,  239,    0,    0,    0,
    6,    0,  223,    8,    0,    0,  240,   12,    0,    0,
    0,    0,    0,    0,  167,    0,    5,    0,    0,    0,
    6,    0,    0,    8,    0,    0,   11,   12,    0,    0,
    0,    0,    0,    0,  167,    0,    5,    0,    0,    0,
    6,    0,    0,    8,    0,    0,   11,   12,  167,    0,
    5,    0,    0,    0,    6,    0,    0,    8,    0,    0,
   11,   12,    0,    0,  167,    0,    5,    0,    0,    0,
    6,    0,    0,    8,    0,    0,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   40,   60,   40,   40,   40,   62,   44,   40,   44,   41,
   40,   15,   69,   41,   18,   44,   40,  199,   59,   41,
   58,   58,   58,   61,   61,   61,   61,   41,   58,    4,
   59,   61,  257,  222,    9,   43,   15,   45,   13,   86,
  257,    0,   40,  267,   41,   40,   58,  271,  128,   61,
  239,  272,   60,   61,   62,  256,  256,   59,   40,   34,
   40,   36,  262,   40,  423,  256,  151,  249,  123,   73,
  261,   75,  431,   58,  131,   45,   61,  110,   42,   43,
  439,   45,  256,   47,  443,   43,  256,   45,   61,  344,
   44,  224,  262,  260,  261,   59,   75,   59,   41,   41,
   43,   59,   45,   45,  184,  256,   37,   42,   40,   59,
  257,  262,   47,   45,  260,  261,   59,   60,   61,   62,
  267,  376,  304,  305,  271,   40,  256,  258,  258,  256,
   45,  190,   60,   61,   62,  262,   41,  270,   43,  394,
   45,  256,  146,  123,   41,   45,  203,  262,  256,  189,
  332,   59,  334,  200,   59,   60,   61,   62,  291,  292,
  342,  220,   58,   59,  123,  420,  189,   59,  253,   59,
   59,  260,  261,  362,  356,  357,  199,  256,   41,  183,
   43,  363,   45,  316,  224,  318,  117,  118,  256,   40,
  223,  256,  256,   59,  262,  199,   59,  262,  262,   41,
   41,  224,   44,   44,  183,   59,  388,  266,   43,  257,
   45,  215,  216,  346,  347,  256,  398,  399,   41,  257,
  277,  257,  316,   40,  318,  156,  249,  256,  260,  411,
  270,  413,  260,  261,  269,  268,  215,  284,  276,  276,
  276,  423,  256,  425,  426,  249,  276,  270,  272,  431,
  272,  291,  292,  310,  313,  257,  260,  439,   40,  257,
  257,  443,  257,  238,  276,  273,  274,  275,  291,  292,
  267,  256,   59,  258,  271,  257,  316,  257,  318,  259,
  257,  304,  305,  263,   59,  265,  266,  257,  258,  269,
  270,  276,  256,  316,  256,  318,  269,  354,  256,  269,
  257,  360,  189,  257,  344,  257,  346,  347,  125,  332,
  256,  334,  199,  256,   58,  257,  258,  260,  261,  342,
  411,  344,  413,  346,  347,  257,  258,  386,  199,  256,
  273,  274,  275,  356,  357,  256,  376,  224,  342,  256,
  363,  123,  257,  258,   18,  273,  274,  275,  256,  408,
  258,  256,   40,  376,  394,  260,  261,  257,  258,  363,
  256,  257,  249,  189,  256,  388,  256,  256,  273,  274,
  275,  394,  257,  199,   59,  398,  399,   41,  249,   44,
  420,  260,  261,  270,  388,  260,  261,   40,  411,   58,
  413,  199,  260,  261,  398,  399,   59,  420,  224,   73,
  423,   41,  425,  426,  291,  292,  262,  411,  431,  413,
   41,  261,   43,   59,   45,  258,  439,  304,  305,  423,
  443,  425,  426,  249,  256,   61,  260,  431,   59,  316,
  258,  318,   41,  304,  305,  439,   59,  111,  112,  443,
  257,  249,  259,  256,  270,  332,  263,  334,  265,  266,
  256,   59,  269,  270,  257,  342,  123,  344,   59,  346,
  347,  332,  257,  334,  269,  291,  292,  258,   59,  356,
  357,   59,  146,   59,   59,  257,  363,  259,  304,  305,
   59,  263,   59,  265,  266,  356,  357,  269,  270,  376,
  316,  258,  318,  189,  168,  169,  304,  305,  172,  256,
   59,  388,  256,  199,  256,   58,  332,  394,  334,  256,
  256,  398,  399,   59,  256,  256,  342,  256,  344,  125,
  346,  347,  258,   59,  411,  257,  413,   41,  224,   59,
  356,  357,  206,  420,   58,  209,  423,  363,  425,  426,
  261,  256,  216,   40,  431,  256,   59,   59,  356,  357,
  376,  257,  439,  249,  189,   40,  443,  261,  256,  258,
   41,  256,  388,  257,  199,  257,  256,   59,  394,  257,
   59,  259,  398,  399,  270,  263,  258,  265,  266,  257,
   58,  269,  270,  274,  256,  411,  260,  413,  256,  224,
   40,   41,   41,   59,  420,  291,  292,  423,  289,  425,
  426,  258,  257,   59,  256,  431,   40,   41,  304,  305,
   58,  261,  256,  439,  249,   59,   41,  443,  256,   59,
  316,   41,  318,   40,  315,  257,  123,  257,  363,  256,
   41,  257,  256,   59,  256,  270,  332,    0,  334,  123,
   40,   59,   41,  125,  143,  172,  342,  149,  344,  111,
  346,  347,   44,  388,  334,   40,  291,  292,  307,  114,
  356,  357,  278,  398,  399,  294,   59,  363,  401,  304,
  305,   40,  109,  123,   -1,   -1,  411,   -1,  413,   -1,
  376,  316,   -1,  318,  375,   -1,   40,   -1,   -1,  123,
  425,  426,  388,   -1,   40,   -1,   -1,  332,  394,  334,
   -1,   -1,  398,  399,   -1,  396,   -1,  342,  125,  344,
   40,  346,  347,   -1,   -1,  411,   -1,  413,   -1,  410,
   -1,  356,  357,   -1,  420,  125,   40,  423,  363,  425,
  426,   -1,   -1,   -1,   -1,  431,   -1,   -1,  123,  430,
   -1,  376,   -1,  439,   -1,   -1,   -1,  443,   -1,   -1,
   -1,   -1,   -1,  388,  123,   -1,   40,   -1,   -1,  394,
  257,   -1,  259,  398,  399,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   40,  411,  123,  413,   -1,
  277,   -1,   -1,   -1,   -1,  420,   -1,   -1,  423,   -1,
  425,  426,   40,   -1,   -1,  125,  431,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  439,   -1,   -1,  257,  443,  259,
   -1,  125,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   40,  257,   -1,  259,   -1,  277,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   -1,  256,
  257,  125,  259,  277,   -1,   40,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,   -1,   -1,  257,  123,  259,
  277,   40,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  257,   -1,  259,  123,   40,  277,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   -1,  257,   -1,
  259,   40,  277,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,   40,  125,  277,  263,
   -1,  257,  266,  259,   -1,  269,  270,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,  257,   -1,  259,
  125,  277,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   40,  257,  123,  259,   -1,  277,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,   -1,
   -1,  125,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   40,  259,  125,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,  125,  257,  277,  259,   -1,   40,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   -1,   -1,  257,
   -1,  259,  277,   40,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,  123,   -1,   -1,   40,  277,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,  259,   -1,   40,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,   -1,   40,   -1,  277,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   40,  257,   -1,
  259,  125,  277,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,   -1,   40,  277,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,  257,   -1,
  259,   40,   -1,  277,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,  123,   40,  277,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,   -1,  125,  277,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  123,  259,   -1,   -1,   -1,  263,  264,   -1,  266,  267,
  268,  269,  270,  271,   -1,  257,   -1,  259,   -1,  277,
  123,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   -1,  257,   -1,  259,   -1,  277,  125,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,   -1,
   -1,  277,  125,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
  257,   -1,  259,  277,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  257,   -1,  259,   -1,   -1,
  277,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
  257,   -1,  259,  260,  261,  277,  263,   -1,   -1,  266,
   -1,   -1,  269,  270,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,  265,  266,   -1,   -1,  269,  270,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,   -1,  266,   -1,   -1,  269,  270,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,   -1,  266,   -1,   -1,  269,  270,  257,   -1,
  259,   -1,   -1,   -1,  263,   -1,   -1,  266,   -1,   -1,
  269,  270,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,   -1,  266,   -1,   -1,  269,  270,
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
"bloque_sentencias_For : sentencias_For",
"bloque_sentencias_For : bloque_sentencias_For sentencias_For",
"sentencia : declarativas",
"sentencia : ejecutables",
"sentencia : sentencia declarativas",
"sentencia : sentencia ejecutables",
"declarativas : i32 lista_de_variables ';'",
"declarativas : f32 lista_de_variables ';'",
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
"bloque_sentencias_ejecutables_funcion : bloque_sentencias_ejecutables_funcion ejecutables_funcion",
"bloque_sentencias_ejecutables_funcion : ejecutables_funcion",
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
"for_else_cte : expresion_For Else cte",
"for_else_cte : expresion_For error",
"salida : out '(' cadena ')' ';'",
"salida : out '(' cadena ')' error",
"salida : out '(' cadena error ';'",
"salida : out cadena error ';'",
"salida : '(' cadena error",
"salida : out '(' ')' error ';'",
"sentencia_If : If condicion_if cuerpo_If end_if ';'",
"sentencia_If : If condicion_if then cuerpo_If end_if error",
"sentencia_If : If condicion_if then cuerpo_If error",
"condicion_if : '(' expresion_relacional ')'",
"condicion_if : expresion_relacional ')'",
"condicion_if : '(' expresion_relacional",
"condicion_if : expresion_relacional",
"cuerpo_If : cuerpo_Then cuerpo_Else",
"cuerpo_If : cuerpo_Then",
"cuerpo_If : cuerpo_Else",
"cuerpo_Then : then '{' bloque_ejecutables '}'",
"cuerpo_Then : then ejecutables",
"cuerpo_Else : Else '{' bloque_ejecutables '}'",
"cuerpo_Else : Else ejecutables",
"sentencia_if_for : If condicion_if cuerpo_If_for end_if ';'",
"sentencia_if_for : If condicion_if cuerpo_If_for end_if error",
"sentencia_if_for : If condicion_if cuerpo_If_for error",
"cuerpo_If_for : cuerpo_then_for cuerpo_Else_for",
"cuerpo_If_for : cuerpo_then_for",
"cuerpo_If_for : cuerpo_Else_for error",
"cuerpo_then_for : then '{' bloque_sentencias_For '}'",
"cuerpo_then_for : then sentencias_For",
"cuerpo_Else_for : Else '{' bloque_sentencias_For '}'",
"cuerpo_Else_for : Else sentencias_For",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion end_if ';'",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion end_if error",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion error",
"cuerpo_If_funcion : cuerpo_then_funcion cuerpo_Else_funcion",
"cuerpo_If_funcion : cuerpo_then_funcion",
"cuerpo_If_funcion : cuerpo_Else_funcion error",
"cuerpo_then_funcion : then '{' bloque_sentencias_ejecutables_funcion '}'",
"cuerpo_then_funcion : then ejecutables_funcion",
"cuerpo_Else_funcion : Else '{' bloque_sentencias_ejecutables_funcion '}'",
"cuerpo_Else_funcion : Else ejecutables_funcion",
"sentencia_if_for_funcion : If condicion_if cuerpo_If_for_funcion end_if ';'",
"sentencia_if_for_funcion : If condicion_if cuerpo_If_for_funcion end_if error",
"sentencia_if_for_funcion : If condicion_if cuerpo_If_for_funcion error",
"cuerpo_If_for_funcion : then_if_for_funcion else_if_for_funcion",
"cuerpo_If_for_funcion : then_if_for_funcion",
"cuerpo_If_for_funcion : else_if_for_funcion error",
"then_if_for_funcion : then '{' bloque_sentencias_For_funcion '}'",
"then_if_for_funcion : then sentencias_For_funcion",
"else_if_for_funcion : Else '{' bloque_sentencias_For_funcion '}'",
"else_if_for_funcion : Else sentencias_For_funcion",
"sentencia_when : when '(' condicion_for ')' cuerpo_when ';'",
"sentencia_when : when condicion_for ')' cuerpo_when ';' error",
"sentencia_when : when '(' condicion_for cuerpo_when ';' error",
"cuerpo_when : then '{' sentencia '}'",
"cuerpo_when : then '{' sentencia error",
"cuerpo_when : then sentencia '}' error",
"encabezado_For : For '(' detalles_for ')' cola_For",
"encabezado_For : For id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For error",
"encabezado_For : For id op_asignacion cte ';' condicion_for ';' signo id cola_For error",
"encabezado_For : For '(' id op_asignacion cte ':' condicion_for ':' signo id ')' cola_For error",
"encabezado_For : id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For",
"detalles_for : asignacion_for ';' condicion_operacion_for",
"condicion_operacion_for : condicion_for ';' operacion_for",
"condicion_for : id comparador cte",
"cola_For : '{' bloque_sentencias_For '}' ';'",
"cola_For : sentencias_For",
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
"cola_For_funcion : '{' bloque_sentencias_For_funcion '}' ';'",
"cola_For_funcion : sentencias_For_funcion",
"sentencia_for_funcion : For '(' detalles_for ')' cola_For_funcion",
"sentencia_for_funcion : For id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For_funcion error",
"sentencia_for_funcion : For id op_asignacion cte ';' condicion_for ';' signo id cola_For_funcion error",
"sentencia_for_funcion : For '(' id op_asignacion cte ':' condicion_for ':' signo id ')' cola_For_funcion error",
"sentencia_for_funcion : id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For_funcion",
"asignacion_for : id op_asignacion cte",
"operacion_for : signo id",
"sentencias_For_funcion : asignacion",
"sentencias_For_funcion : salida",
"sentencias_For_funcion : sentencia_for_funcion",
"sentencias_For_funcion : ret_fun",
"sentencias_For_funcion : invocacion_funcion",
"sentencias_For_funcion : sentencia_BREAK",
"sentencias_For_funcion : sentencia_CONTINUE",
"sentencias_For_funcion : sentencia_if_for_funcion",
"sentencias_For_funcion : declarativas error",
"bloque_sentencias_For_funcion : sentencias_For_funcion",
"bloque_sentencias_For_funcion : bloque_sentencias_For_funcion sentencias_For_funcion",
"sentencia_BREAK : BREAK ';'",
"sentencia_BREAK : BREAK cte ';'",
"sentencia_BREAK : BREAK error",
"sentencia_CONTINUE : CONTINUE ';'",
"sentencia_CONTINUE : CONTINUE ':' id ';'",
"sentencia_CONTINUE : CONTINUE id ';' error",
"sentencia_CONTINUE : CONTINUE error",
"invocacion_funcion : id '(' list_parametros ')' ';'",
"invocacion_funcion : id '(' ')' ';'",
"list_parametros : factor ',' factor",
"list_parametros : factor",
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

//#line 479 "gramatica.y"

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

//#line 863 "Parser.java"
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
{yyval = new ParserVal(sintactico.crearNodoControl("primera_sentencia", val_peek(1)));}
break;
case 4:
//#line 27 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("primera_sentencia", val_peek(1)));}
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
							  yyval = new ParserVal(sintactico.crearNodoControl("lista_ctes", val_peek(1)));
							}
break;
case 8:
//#line 35 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
break;
case 9:
//#line 36 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
break;
case 10:
//#line 39 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declaracion_constante", val_peek(0), null));}
break;
case 11:
//#line 40 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declaracion_constante", val_peek(0), val_peek(2)));}
break;
case 12:
//#line 44 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja(val_peek(2).ival)), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));}
break;
case 13:
//#line 45 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 14:
//#line 46 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 15:
//#line 47 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 16:
//#line 51 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 17:
//#line 52 "gramatica.y"
{ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
                                                              yyval = modificado;}
break;
case 18:
//#line 58 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 19:
//#line 59 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 20:
//#line 60 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 21:
//#line 64 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 22:
//#line 71 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")");
						   sintactico.completarConTipos("i32");}
break;
case 23:
//#line 73 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 24:
//#line 74 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable"); }
break;
case 28:
//#line 81 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 29:
//#line 85 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 35:
//#line 93 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 36:
//#line 94 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 38:
//#line 98 "gramatica.y"
{
                   				   sintactico.addListaVariables(val_peek(2).ival);
                   				   sintactico.setUso("Variable", val_peek(2).ival);}
break;
case 39:
//#line 101 "gramatica.y"
{
                   				  sintactico.addListaVariables(val_peek(0).ival);
                                                  sintactico.setUso("Variable", val_peek(0).ival);}
break;
case 40:
//#line 113 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" ); }
break;
case 41:
//#line 114 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 43:
//#line 118 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 45:
//#line 123 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 46:
//#line 125 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")");
 						yyval = val_peek(1);}
break;
case 47:
//#line 130 "gramatica.y"
{yyval = val_peek(0);}
break;
case 49:
//#line 132 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 51:
//#line 136 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   yyval = new ParserVal(sintactico.crearNodoControl("return",val_peek(2)));}
break;
case 52:
//#line 138 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 53:
//#line 139 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 140 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 55:
//#line 141 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 63:
//#line 153 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 64:
//#line 154 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 65:
//#line 158 "gramatica.y"
{
													ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
													yyval = modificado;
											 	}
break;
case 66:
//#line 162 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 67:
//#line 165 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 68:
//#line 169 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 69:
//#line 173 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 70:
//#line 174 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 71:
//#line 183 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 72:
//#line 184 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 73:
//#line 185 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 74:
//#line 189 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(3).ival));
						yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
					   }
break;
case 75:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 76:
//#line 193 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", val_peek(3), val_peek(1)));}
break;
case 77:
//#line 197 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), val_peek(0)));}
break;
case 78:
//#line 198 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 79:
//#line 201 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 80:
//#line 202 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 81:
//#line 203 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 82:
//#line 204 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 83:
//#line 205 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 84:
//#line 206 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 85:
//#line 211 "gramatica.y"
{ 	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
										sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 86:
//#line 213 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 87:
//#line 214 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 88:
//#line 218 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cond",val_peek(1)));}
break;
case 89:
//#line 219 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 90:
//#line 220 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 91:
//#line 221 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 92:
//#line 225 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(1), val_peek(0)));}
break;
case 93:
//#line 226 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(0), null));}
break;
case 94:
//#line 227 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 95:
//#line 231 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 96:
//#line 232 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 97:
//#line 236 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 98:
//#line 237 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 99:
//#line 240 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 										   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 100:
//#line 242 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 101:
//#line 243 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 102:
//#line 246 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 103:
//#line 247 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 104:
//#line 248 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 105:
//#line 251 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 106:
//#line 252 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 107:
//#line 255 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 108:
//#line 256 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 109:
//#line 259 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 											   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 110:
//#line 261 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 111:
//#line 262 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 112:
//#line 265 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 113:
//#line 266 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 114:
//#line 267 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 115:
//#line 270 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 116:
//#line 271 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 117:
//#line 274 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 118:
//#line 275 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 119:
//#line 278 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
												  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 120:
//#line 280 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 121:
//#line 281 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 122:
//#line 284 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 123:
//#line 285 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 124:
//#line 286 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 125:
//#line 289 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 126:
//#line 290 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 127:
//#line 293 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 128:
//#line 294 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 129:
//#line 300 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
									yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 130:
//#line 302 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 131:
//#line 303 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 132:
//#line 307 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 133:
//#line 308 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 134:
//#line 309 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 135:
//#line 316 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
									}
break;
case 136:
//#line 319 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 137:
//#line 320 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 138:
//#line 321 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 139:
//#line 322 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 140:
//#line 326 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));}
break;
case 141:
//#line 329 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 142:
//#line 332 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("cond", new ParserVal(sintactico.crearNodo(val_peek(1).sval,identificador,constante))));}
break;
case 143:
//#line 337 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 144:
//#line 338 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 145:
//#line 341 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 146:
//#line 342 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 154:
//#line 353 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 155:
//#line 354 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 157:
//#line 360 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 158:
//#line 361 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 159:
//#line 365 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										}
break;
case 160:
//#line 368 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 161:
//#line 369 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 162:
//#line 370 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 163:
//#line 371 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 164:
//#line 374 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));}
break;
case 165:
//#line 379 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo(val_peek(1).sval,new ParserVal(sintactico.crearHoja(val_peek(0).ival)),null))));}
break;
case 174:
//#line 390 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 175:
//#line 394 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 176:
//#line 395 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 177:
//#line 403 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
				yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 178:
//#line 405 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                		yyval = new ParserVal(sintactico.crearNodoControl("break", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 179:
//#line 407 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 180:
//#line 410 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
						yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 181:
//#line 412 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   				yyval = new ParserVal(sintactico.crearNodoControl("continue", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 182:
//#line 414 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 183:
//#line 415 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 184:
//#line 418 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(4).ival, val_peek(2)));}
break;
case 185:
//#line 419 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(3).ival, null));}
break;
case 186:
//#line 422 "gramatica.y"
{
						yyval = new ParserVal(sintactico.crearNodo("param", val_peek(2), val_peek(0)));
					}
break;
case 187:
//#line 425 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("param", val_peek(0), null));}
break;
case 188:
//#line 429 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 189:
//#line 433 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 191:
//#line 438 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 192:
//#line 439 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 194:
//#line 444 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));}
break;
case 195:
//#line 445 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
                  }
break;
case 196:
//#line 451 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(1).ival));
                    }
break;
case 197:
//#line 458 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 198:
//#line 459 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 199:
//#line 460 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 200:
//#line 461 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 201:
//#line 462 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 202:
//#line 463 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 203:
//#line 467 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 204:
//#line 471 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 1738 "Parser.java"
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
