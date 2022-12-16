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

import java.util.Stack;


//#line 29 "Parser.java"




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
    5,    5,    6,    6,    6,    6,    6,    8,    8,    3,
    3,    3,    3,   10,   10,   10,   10,   10,   16,   16,
   11,   11,   11,   11,   11,   11,   11,   13,   13,   13,
   24,   24,   25,   25,   26,   28,   28,   28,   28,   29,
   29,   14,   30,   30,   30,   30,   30,   27,   33,   33,
   33,   33,   33,   33,   33,   33,   36,   36,   32,   32,
   32,   32,    7,    7,    7,   17,   17,   17,   17,   37,
   37,   18,   18,   18,   18,   18,   18,   19,   19,   19,
   38,   38,   38,   38,   39,   39,   39,   42,   42,   43,
   43,   46,   46,   46,   47,   47,   47,   48,   48,   49,
   49,   34,   34,   34,   50,   50,   50,   51,   51,   52,
   52,   53,   53,   53,   54,   54,   54,   55,   55,   56,
   56,   44,   45,   40,   15,   15,   15,   59,   60,   60,
   60,   61,   61,   61,   61,   62,   65,   66,   66,   63,
   63,   63,   70,   70,    9,    9,    9,    9,    9,    9,
    9,    9,    9,   20,   71,   71,   71,   35,   35,   35,
   35,   64,   67,   67,   58,   58,   58,   58,   58,   58,
   58,   58,   58,   57,   57,   57,   22,   22,   22,   23,
   23,   23,   23,   21,   72,   72,   72,   41,   31,   31,
   73,   73,   73,   69,   69,   69,   68,   68,   68,   68,
   68,   68,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    4,    3,    3,    2,    1,    2,    1,
    1,    2,    2,    3,    3,    1,    1,    1,    2,    1,
    1,    1,    1,    1,    1,    2,    2,    2,    3,    1,
    2,    2,    2,    0,    3,    0,    1,    3,    1,    6,
    6,    2,    5,    5,    5,    4,    5,    1,    1,    1,
    1,    1,    1,    1,    2,    2,    2,    1,    2,    2,
    1,    1,    1,    3,    2,    4,    4,    4,    3,    3,
    2,    5,    5,    5,    4,    3,    5,    5,    6,    5,
    3,    2,    2,    1,    2,    1,    1,    4,    2,    4,
    2,    5,    5,    4,    2,    1,    2,    4,    2,    4,
    2,    5,    5,    4,    2,    1,    2,    4,    2,    4,
    2,    5,    5,    4,    2,    1,    2,    4,    2,    4,
    2,    1,    1,    1,    6,    6,    6,    1,    4,    4,
    4,    5,    5,    4,    7,    3,    3,    3,    5,    4,
    1,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    1,    4,    1,    4,    5,    5,    4,
    7,    3,    2,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    1,    2,    1,    2,    3,    2,    2,
    4,    4,    2,    5,    0,    3,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,  213,    0,
    0,    0,  214,    0,    0,    0,    0,    0,   27,   20,
   21,    0,    0,   26,   28,   31,   32,   33,   34,   35,
    0,    0,    0,  164,    0,   73,    0,    0,    0,    0,
    0,   38,  204,  205,    0,    0,    0,    0,    0,  203,
    0,    0,    0,    0,    0,  189,    0,  187,    0,  138,
    0,    0,    0,    0,    0,  193,    0,  190,    0,    0,
    0,    0,   10,    0,    0,    0,    6,   22,   23,    0,
    0,   36,   37,    0,   52,   39,    0,    0,    0,    0,
   75,    0,    0,   79,    0,    0,  206,    0,  209,  210,
  212,  154,  211,  153,  207,  208,    0,    0,    0,  133,
    0,    0,   97,    0,    0,   92,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,    0,  188,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  151,    0,  155,
  156,  157,  159,  160,  161,    0,  158,    0,    0,    0,
    0,   17,    0,    0,    9,    8,    7,    0,    0,    4,
   86,    0,   24,   25,    0,    0,    0,   72,   59,   61,
   62,    0,    0,    0,   64,    0,   71,   60,   63,    0,
   74,    0,    0,    0,   81,    0,   77,   76,   78,   91,
    0,    0,  132,    0,  134,    0,   95,    0,    0,   99,
    0,  101,  201,  202,   85,    0,    0,    0,    0,   42,
   41,    0,    0,    0,    0,    0,  172,    0,    0,    0,
    0,    0,   18,    0,  162,  163,  144,    0,  146,    0,
  192,  191,   16,   15,   13,    0,   11,   12,    3,    0,
    0,    0,    0,   65,   66,   45,   70,   69,    0,  196,
  194,   80,   90,    0,   88,   30,    0,    0,   84,   83,
   82,   87,    0,   48,    0,    0,    0,    0,    0,    0,
  142,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   19,  143,    0,    0,   14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  175,  176,  179,  180,
  181,  178,  177,  182,  166,    0,    0,   89,   98,   29,
  100,   50,   43,   51,  137,  135,    0,    0,  136,    0,
  109,    0,  111,  104,    0,  105,  107,    0,    0,   56,
    0,  152,  150,    0,  148,    0,  147,    0,    0,    0,
    0,  119,    0,  121,  114,    0,  115,  117,    0,    0,
  186,    0,  184,    0,  183,  170,  145,  140,  139,  141,
    0,    0,  103,  102,   55,   57,   53,   54,    0,  174,
  173,    0,   68,    0,    0,  113,  112,  168,    0,    0,
    0,    0,    0,    0,  185,  169,  108,  110,  149,    0,
  118,   67,  120,    0,  129,    0,  131,  124,    0,  125,
  127,  167,  165,  171,    0,    0,  123,  122,  128,  130,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   72,   73,   41,  222,  138,  139,
   21,   22,   23,   24,   25,  257,   26,   27,   28,   29,
   30,   31,   32,  126,  266,   85,  174,  127,   33,  146,
   47,  176,  373,  178,  303,  374,   96,   48,  111,  196,
   49,  112,  113,  114,  115,  147,  274,  275,  276,  289,
  290,  291,  304,  381,  382,  383,  352,  305,   61,  216,
   34,   64,  148,   65,  229,  230,  337,  107,   50,  108,
  306,   90,   51,
};
final static short yysindex[] = {                      -225,
    0,    0,  824,  -30,   27,  -22,  -21,   23,    0,   38,
  -18,  105,    0,   26,  986, -206,    2,  849,    0,    0,
    0, -173,   32,    0,    0,    0,    0,    0,    0,    0,
 -168, -121,   57,    0,   11,    0, -173,  -20,  -42, -113,
  -39,    0,    0,    0,  -52,  -42,   68,  103,  151,    0,
  142, -110,  -11,  177, -153,    0,  161,    0,  -42,    0,
  184,  -35,   41,  538,  167,    0,  176,    0,   42,  -45,
   15,   73,    0,  871,   64,  986,    0,    0,    0,  183,
  112,    0,    0,  887,    0,    0,  284,  114,  287,  296,
    0,   37,  -59,    0,   20,  298,    0,  331,    0,    0,
    0,    0,    0,    0,    0,    0,  -42,  -42,  121,    0,
  113,  118,    0,  510,  530,    0,  -42,  -42,  324,    1,
  129, -153,    0,  134,  135,  350,  364,    0,  366,  149,
  368,  175,  384,   27,   60,  902,  147,    0,  171,    0,
    0,    0,    0,    0,    0,  181,    0,  185,  186,  189,
  381,    0,  190,   44,    0,    0,    0,  191,  193,    0,
    0,  921,    0,    0,  -23,   27,   -6,    0,    0,    0,
    0,  198,  199,  322,    0,  887,    0,    0,    0,   41,
    0,  -42,  398,  192,    0,  200,    0,    0,    0,    0,
   81,  142,    0, -210,    0,  400,    0,   -7, 1044,    0,
 1044,    0,    0,    0,    0,  405,  -34,  409,  428,    0,
    0, -177,  413,  149,  937,  418,    0,  147,  121,  -42,
  131,  340,    0,  216,    0,    0,    0,   14,    0,  419,
    0,    0,    0,    0,    0,  221,    0,    0,    0,   -5,
  121,   41,  586,    0,    0,    0,    0,    0,  441,    0,
    0,    0,    0,  227,    0,    0, 1058, 1085,    0,    0,
    0,    0,  413,    0,  -66,  228,   34,  986,  971,  229,
    0,  602,  617, -129,  118,  231,  155,  232,  430,   49,
    0,    0,   75,    4,    0,  450,  453, 1039, -112,  118,
  236,  455,   27,  636,  651,  238,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  242,  147,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  667,  244,    0,  902,
    0,  902,    0,    0,   51,    0,    0,  246,   80,    0,
  247,    0,    0,  -42,    0,  248,    0,  252,   41,   -1,
 1093,    0, 1093,    0,    0,   82,    0,    0,  651,  121,
    0,  686,    0,  249,    0,    0,    0,    0,    0,    0,
  701,  716,    0,    0,    0,    0,    0,    0,  150,    0,
    0,  470,    0,  169,  273,    0,    0,    0,  735,  773,
 -109,  118,  257,   89,    0,    0,    0,    0,    0,  651,
    0,    0,    0,  636,    0,  636,    0,    0,   96,    0,
    0,    0,    0,    0,  750,  800,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  456,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  517,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  456,    0,    0,    0,  478,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  139,    0,
 1013,    0,    0,    0,  480,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   29,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  482,    0,
    0,   95,    0,    0,    0,    0,    0,  159,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, 1009,    0,
    0, -100,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  480,    0,    0,    0,  483,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  456,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  402,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -32, 1062,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  269,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  406,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -96,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -87,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -85,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -8,    0,    0,  -24,  -17,    5,  909,   -3,
  -13,    9,  349,    0,    0,  332,  101,  179,    0,  -14,
  -40,  294,  372,  323,  271,    0,    0,  422,    0,  442,
  -37,    0,  -64,    0,  -15,  204,    0, -123,  440,  -95,
   99,    0,  439, -182, -183,    0,    0,    0,  277,    0,
    0,  265,    0,    0,    0,  174,  -27,  166,  498,  347,
    0,  -61,  -97,    0,    0,    0,    0,  335,  -31,  280,
 -233,    0,  457,
};
final static int YYTABLESIZE=1363;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
   94,  133,   45,   95,   79,   45,   74,   89,  198,   39,
  219,   20,  131,   37,   78,   40,   39,   53,   55,  177,
   37,   63,  131,  143,  261,   40,   93,   38,    5,  121,
   40,    1,   39,  242,  240,  273,  272,   40,   39,  224,
   88,  207,  241,  171,  132,  253,  104,  159,  102,  142,
   38,  195,  154,   40,   37,   88,  240,  288,  287,   40,
   79,  354,  104,  125,  102,   75,   46,  162,  179,  191,
   78,   45,   20,  105,  103,  106,   39,   59,  188,  124,
  168,   58,   45,   35,   71,  203,  204,   82,  236,    9,
   81,  273,  316,   13,  184,  143,  143,  221,  254,  220,
  200,  202,  123,  124,   45,  243,  288,  333,   60,  364,
  104,  248,  102,    9,  334,  378,  158,   13,  249,   45,
  271,  142,  142,  104,   76,  102,  324,  105,  103,  106,
  125,  157,  195,  237,   83,  171,  204,  204,  367,  204,
  377,  204,   91,  345,   98,  119,  398,  403,   79,  195,
  250,    5,  195,  204,  408,   96,  404,   60,   78,  106,
  179,   96,   69,   68,  140,  106,  380,  379,  116,  350,
  126,  279,  247,  104,  116,  102,  126,  143,  325,   84,
  292,  143,  277,  117,  169,  256,   16,  256,  118,  278,
  389,  116,  104,  346,  102,  329,  185,  104,  380,  102,
    9,  186,  299,  142,   13,   97,  269,  142,   16,  357,
  152,   20,  153,  328,   43,   44,  122,   92,   44,  128,
  125,  260,  342,  344,  130,  149,   35,  198,  198,   11,
   36,  143,  143,   35,  150,   54,  140,  140,   62,  296,
   36,  163,  141,  310,  310,   36,  171,  171,   87,   52,
   62,  335,   36,  299,  299,   79,  206,  142,  142,  317,
  120,  336,  170,  286,   20,   78,  143,   35,   36,  136,
  155,  179,  179,  313,   36,  187,  169,  372,   56,  143,
   57,  143,   70,   43,   44,  399,   99,  100,  101,  315,
  296,  296,  142,  391,   43,   44,  369,   62,  151,  234,
  171,  235,  171,   79,  332,  142,  363,  142,  299,  392,
  392,  299,   16,   78,  141,  141,   43,   44,  140,  161,
  143,  143,  140,  180,  361,  179,  362,  179,  156,   70,
  182,   43,   44,  171,  171,  366,  183,  376,  299,  299,
   99,  100,  101,  297,  402,  296,  142,  142,  296,  299,
  204,  407,   42,  299,  170,  299,  189,  144,  179,  179,
   66,   67,  109,  110,  299,  299,  405,  164,  406,  181,
   80,  190,  140,  140,  195,  296,  296,  172,  110,   16,
  193,  110,  205,   42,  208,   86,  296,  169,  169,  210,
  296,  211,  296,  212,  297,  297,  141,  393,   94,   94,
  141,  296,  296,    4,  213,  134,  214,  140,  215,    6,
    7,  135,    8,    9,   10,   11,   12,   13,   93,   93,
  140,  298,  140,   14,  218,  340,  225,  166,   88,  144,
  144,    6,  217,  135,    8,  145,  226,  167,   12,  232,
  227,  169,  228,  169,  231,  233,  246,   70,  238,  297,
  141,  141,  297,  244,  245,  173,  251,  252,  255,  353,
   87,  140,  140,  259,  280,  170,  170,  262,  263,  172,
  265,  282,  298,  298,  169,  169,  270,  284,  285,  297,
  297,  307,  308,  314,  319,  141,  327,  330,  331,  339,
  297,  348,   16,  355,  297,  349,  297,  356,  141,  360,
  141,  365,  368,  370,  386,  297,  297,  145,  145,  371,
  390,  144,  401,   42,   40,  144,    1,  385,  195,  170,
   46,  170,  197,   47,   44,  175,   58,  298,   44,  340,
  298,  166,  258,  312,  264,    6,  300,  135,    8,  141,
  141,  167,   12,  209,  395,  397,  375,  173,  194,   16,
  197,  326,  170,  170,  347,  400,  129,  298,  298,  353,
  267,  353,  283,  338,  192,  144,  144,    0,  298,   16,
  385,  385,  298,    0,  298,  341,    0,   16,  137,    0,
  172,  172,    0,  298,  298,    0,    0,  300,  300,  145,
    0,    0,    0,  145,    0,    0,    4,    0,  134,    0,
  144,    0,    6,    7,  135,    8,    9,   10,   11,   12,
   13,    0,    0,  144,  301,  144,   14,  175,    0,    0,
    0,    0,    0,    0,    0,   16,  295,    0,    0,    0,
    0,    0,  199,    0,  172,    0,  172,    0,    0,    0,
    0,   16,  300,  145,  145,  300,    0,    0,    0,    0,
    0,    0,  201,    0,  144,  144,   16,    0,  173,  173,
  136,    0,    0,    0,    0,  301,  301,  172,  172,    0,
    0,    0,  300,  300,    0,   16,    0,    0,  145,    0,
    0,    0,    0,  300,  302,    0,    0,  300,    0,  300,
   16,  145,    0,  145,    0,    0,    0,    0,  300,  300,
    0,    0,    0,    0,    0,    0,   16,    0,  294,  340,
    0,  166,  173,    0,  173,    6,    0,  135,    8,    0,
  301,  167,   12,  301,  320,   16,    0,    0,  175,  175,
    0,    0,  145,  145,    0,  302,  302,    0,    0,  322,
   16,    0,    0,    0,    0,  173,  173,    0,    0,    0,
  301,  301,    0,    0,    0,   16,    0,    0,    0,    0,
    0,  301,    0,    0,    0,  301,  198,  301,    5,    0,
    0,    0,    6,  294,   16,    8,  301,  301,   11,   12,
    0,    0,  175,    0,  175,    0,  198,    0,    5,   16,
  302,  359,    6,  302,    4,    8,  134,    0,   11,   12,
    6,    7,  135,    8,    9,   10,   11,   12,   13,    0,
  384,    0,   16,    0,   14,  175,  175,    0,    0,    0,
  302,  302,    0,    0,    0,  387,    0,    0,    0,    0,
    0,  302,    0,    0,    0,  302,    0,  302,    0,   16,
  388,    0,  165,    0,  293,    0,  302,  302,    6,    7,
  135,    8,    9,   10,  167,   12,   13,  394,    4,    0,
  134,    0,   14,   16,    6,    7,  135,    8,    9,   10,
   11,   12,   13,    4,  409,  134,    0,    0,   14,    6,
    7,  135,    8,    9,   10,   11,   12,   13,   16,    0,
    0,  351,  165,   14,  293,  396,    0,    0,    6,    7,
  135,    8,    9,   10,  167,   12,   13,  165,    0,  293,
   16,    0,   14,    6,    7,  135,    8,    9,   10,  167,
   12,   13,  358,    4,  410,    5,   16,   14,    0,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
    0,   16,  165,   14,  293,    0,   15,    0,    6,    7,
  135,    8,    9,   10,  167,   12,   13,    4,    0,  134,
   16,    0,   14,    6,    7,  135,    8,    9,   10,   11,
   12,   13,    4,   77,  134,    0,   16,   14,    6,    7,
  135,    8,    9,   10,   11,   12,   13,    0,    0,    0,
    0,  165,   14,  293,    0,  160,    0,    6,    7,  135,
    8,    9,   10,  167,   12,   13,  165,    0,  293,    0,
   16,   14,    6,    7,  135,    8,    9,   10,  167,   12,
   13,    0,    0,    0,    0,   16,   14,    0,    0,  165,
    0,  293,    0,    0,    0,    6,    7,  135,    8,    9,
   10,  167,   12,   13,  223,  239,    0,    0,  132,   14,
    0,    0,    0,  200,    0,  200,  165,  200,  293,  268,
    0,    0,    6,    7,  135,    8,    9,   10,  167,   12,
   13,  200,  200,  200,  200,    0,   14,    0,   16,    0,
    4,    0,    5,   16,    0,    0,    6,    7,    0,    8,
    9,   10,   11,   12,   13,  318,    0,   16,    0,    0,
   14,    0,  199,    0,  199,    4,  199,    5,    0,    0,
    0,    6,    7,    0,    8,    9,   10,   11,   12,   13,
  199,  199,  199,  199,   16,   14,    0,    4,    0,    5,
  281,  132,   16,    6,    7,    0,    8,    9,   10,   11,
   12,   13,    0,  165,    0,  166,    0,   14,    0,    6,
    7,  135,    8,    9,   10,  167,   12,   13,    4,    0,
  134,  343,    0,   14,    6,    7,  135,    8,    9,   10,
   11,   12,   13,    0,    0,    0,    0,    4,   14,    5,
  321,  323,  309,    6,    7,    0,    8,    9,   10,   11,
   12,   13,    0,    4,    0,    5,    0,   14,    0,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,  311,
    0,    0,    0,   14,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    4,  223,    5,
  223,    0,    0,    6,    7,    0,    8,    9,   10,   11,
   12,   13,    4,    0,    5,    0,    0,   14,    6,    7,
    0,    8,    9,   10,   11,   12,   13,    0,    0,    0,
    0,    0,   14,    0,    0,  132,    0,  132,  200,  281,
  281,  132,  200,  200,  132,    0,    0,  132,  132,    0,
    0,    0,    0,    0,    0,  200,  200,  200,    0,    0,
    0,    0,    0,    0,    0,  340,    0,  166,    0,    0,
  198,    6,    5,  135,    8,    0,    6,  167,   12,    8,
    0,    0,   11,   12,  198,    0,    5,  199,    0,    0,
    6,  199,  199,    8,    0,    0,   11,   12,    0,    0,
    0,    0,    0,    0,  199,  199,  199,    0,    0,    0,
    0,  198,    0,    5,    0,    0,    0,    6,    0,  340,
    8,  166,    0,   11,   12,    6,    0,  135,    8,    0,
    0,  167,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   41,   63,   45,   41,   18,   45,   15,   39,   41,   40,
  134,   15,   58,   44,   18,   61,   40,   40,   40,   84,
   44,   40,   58,   64,   59,   61,   41,   58,    0,   41,
   61,  257,   40,   40,   58,  219,  219,   61,   40,  137,
   61,   41,  166,   84,   62,  256,   43,   72,   45,   64,
   58,  262,   70,   61,   44,   61,   58,  241,  241,   61,
   74,  295,   43,   55,   45,  272,   40,   76,   84,  107,
   74,   45,   76,   60,   61,   62,   40,   40,   59,  257,
   84,   59,   45,  257,   59,  117,  118,  256,   45,  267,
   59,  275,   59,  271,   58,  136,  137,  135,  194,   40,
  114,  115,  256,  257,   45,  167,  290,   59,   10,   59,
   43,  176,   45,  267,   40,  349,   44,  271,  180,   45,
  218,  136,  137,   43,  123,   45,  256,   60,   61,   62,
  122,   59,  262,  158,  256,  176,   42,   43,   59,   45,
   59,   47,  256,  256,   46,  256,  256,   59,  162,  262,
  182,  123,  262,   59,   59,  256,  390,   59,  162,  256,
  176,  262,   58,   59,   64,  262,  350,  350,  256,  293,
  256,   41,  176,   43,  262,   45,  262,  218,  274,  123,
  242,  222,  220,   42,   84,  199,   40,  201,   47,   59,
   41,   41,   43,  289,   45,   41,  256,   43,  382,   45,
  267,  261,  243,  218,  271,  258,  215,  222,   40,  307,
  256,  215,  258,   59,  257,  258,   40,  257,  258,   59,
  212,  256,  287,  288,   41,   59,  257,  260,  261,  269,
  276,  272,  273,  257,   59,  257,  136,  137,  257,  243,
  276,   59,   64,  257,  258,  276,  287,  288,  269,  272,
  257,  283,  276,  294,  295,  269,  256,  272,  273,  268,
  272,  258,   84,  269,  268,  269,  307,  257,  276,  123,
  256,  287,  288,  265,  276,  256,  176,  339,  256,  320,
  258,  322,  257,  257,  258,  381,  273,  274,  275,  256,
  294,  295,  307,  125,  257,  258,  334,  257,  257,  256,
  341,  258,  343,  317,  256,  320,  256,  322,  349,  374,
  375,  352,   40,  317,  136,  137,  257,  258,  218,  256,
  361,  362,  222,   40,  320,  341,  322,  343,  256,  257,
   44,  257,  258,  374,  375,  256,   41,  256,  379,  380,
  273,  274,  275,  243,  256,  349,  361,  362,  352,  390,
  256,  256,    4,  394,  176,  396,   59,   64,  374,  375,
  256,  257,  260,  261,  405,  406,  394,  256,  396,  256,
   22,   41,  272,  273,  262,  379,  380,   84,  261,   40,
  260,  261,   59,   35,  256,   37,  390,  287,  288,  256,
  394,  257,  396,   44,  294,  295,  218,  125,  260,  261,
  222,  405,  406,  257,   41,  259,   41,  307,  260,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  260,  261,
  320,  243,  322,  277,   41,  257,  256,  259,   61,  136,
  137,  263,  258,  265,  266,   64,  256,  269,  270,   59,
  256,  341,  257,  343,  256,  256,  125,  257,  256,  349,
  272,  273,  352,  256,  256,   84,   59,  258,   59,  294,
  269,  361,  362,   59,  125,  287,  288,   59,   41,  176,
   58,  256,  294,  295,  374,  375,   59,   59,  258,  379,
  380,   41,  256,  256,  256,  307,  256,  256,   59,   40,
  390,  256,   40,  256,  394,   41,  396,  256,  320,  256,
  322,  256,  256,  256,  256,  405,  406,  136,  137,  258,
   41,  218,  256,  165,   59,  222,    0,  352,   41,  341,
   41,  343,   41,   41,  256,   84,  125,  349,  123,  257,
  352,  259,  201,  263,  212,  263,  243,  265,  266,  361,
  362,  269,  270,  122,  379,  380,  343,  176,  109,   40,
  112,  275,  374,  375,  290,  382,   59,  379,  380,  394,
  214,  396,  228,  284,  108,  272,  273,   -1,  390,   40,
  405,  406,  394,   -1,  396,  123,   -1,   40,   41,   -1,
  287,  288,   -1,  405,  406,   -1,   -1,  294,  295,  218,
   -1,   -1,   -1,  222,   -1,   -1,  257,   -1,  259,   -1,
  307,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   -1,  320,  243,  322,  277,  176,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  341,   -1,  343,   -1,   -1,   -1,
   -1,   40,  349,  272,  273,  352,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  361,  362,   40,   -1,  287,  288,
  123,   -1,   -1,   -1,   -1,  294,  295,  374,  375,   -1,
   -1,   -1,  379,  380,   -1,   40,   -1,   -1,  307,   -1,
   -1,   -1,   -1,  390,  243,   -1,   -1,  394,   -1,  396,
   40,  320,   -1,  322,   -1,   -1,   -1,   -1,  405,  406,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,  123,  257,
   -1,  259,  341,   -1,  343,  263,   -1,  265,  266,   -1,
  349,  269,  270,  352,  123,   40,   -1,   -1,  287,  288,
   -1,   -1,  361,  362,   -1,  294,  295,   -1,   -1,  123,
   40,   -1,   -1,   -1,   -1,  374,  375,   -1,   -1,   -1,
  379,  380,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   -1,  390,   -1,   -1,   -1,  394,  257,  396,  259,   -1,
   -1,   -1,  263,  123,   40,  266,  405,  406,  269,  270,
   -1,   -1,  341,   -1,  343,   -1,  257,   -1,  259,   40,
  349,  125,  263,  352,  257,  266,  259,   -1,  269,  270,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   -1,
  125,   -1,   40,   -1,  277,  374,  375,   -1,   -1,   -1,
  379,  380,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,
   -1,  390,   -1,   -1,   -1,  394,   -1,  396,   -1,   40,
  125,   -1,  257,   -1,  259,   -1,  405,  406,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  123,  257,   -1,
  259,   -1,  277,   40,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  257,  125,  259,   -1,   -1,  277,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,   -1,
   -1,  256,  257,  277,  259,  123,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  257,   -1,  259,
   40,   -1,  277,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  256,  257,  125,  259,   40,  277,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,   40,  257,  277,  259,   -1,  123,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  257,   -1,  259,
   40,   -1,  277,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  257,  125,  259,   -1,   40,  277,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   -1,  257,  277,  259,   -1,  125,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  257,   -1,  259,   -1,
   40,  277,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   -1,   -1,   -1,   40,  277,   -1,   -1,  257,
   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  136,  125,   -1,   -1,   40,  277,
   -1,   -1,   -1,   41,   -1,   43,  257,   45,  259,  123,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   59,   60,   61,   62,   -1,  277,   -1,   40,   -1,
  257,   -1,  259,   40,   -1,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,  125,   -1,   40,   -1,   -1,
  277,   -1,   41,   -1,   43,  257,   45,  259,   -1,   -1,
   -1,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
   59,   60,   61,   62,   40,  277,   -1,  257,   -1,  259,
  222,  123,   40,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,   -1,  257,   -1,  259,   -1,  277,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  257,   -1,
  259,  123,   -1,  277,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,   -1,   -1,   -1,  257,  277,  259,
  272,  273,  125,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,   -1,  257,   -1,  259,   -1,  277,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,  125,
   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  320,  259,
  322,   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,  257,   -1,  259,   -1,   -1,  277,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   -1,   -1,  277,   -1,   -1,  257,   -1,  259,  256,  361,
  362,  263,  260,  261,  266,   -1,   -1,  269,  270,   -1,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
  257,  263,  259,  265,  266,   -1,  263,  269,  270,  266,
   -1,   -1,  269,  270,  257,   -1,  259,  256,   -1,   -1,
  263,  260,  261,  266,   -1,   -1,  269,  270,   -1,   -1,
   -1,   -1,   -1,   -1,  273,  274,  275,   -1,   -1,   -1,
   -1,  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  257,
  266,  259,   -1,  269,  270,  263,   -1,  265,  266,   -1,
   -1,  269,  270,
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
"lista_de_asignacion_const : lista_de_asignacion_const decl_const error",
"decl_const : id op_asignacion cte",
"decl_const : id op_asignacion '-' cte",
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
"op_asignacion : ':' '=' error",
"op_asignacion : '=' error",
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
"sentencia_when : when '(' condicion_when ')' cuerpo_when error",
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
"condicion_for : id comparador factor",
"condicion_for : id comparador '(' expresion ')'",
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
"operacion_for : signo cte",
"operacion_for : cte error",
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

//#line 864 "gramatica.y"

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;
private String ambito;
private int contadorFor;
private int contadorIf;
private int contadorWhen;
private String tipoBreak = new String();
private Stack<Integer> variablesFor;


public void activarAmbito(){this.ambito = "$"; this.contadorFor = 0; this.contadorIf = 0; this.contadorWhen = 0; variablesFor = new Stack<Integer>();} // $ va a simblizar el ambito global.

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
//#line 909 "Parser.java"
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
//#line 21 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("raiz", val_peek(0))); sintactico.setRaiz(yyval); }
break;
case 2:
//#line 25 "gramatica.y"
{sintactico.setUsoEnIndex("program",val_peek(0).ival);}
break;
case 3:
//#line 28 "gramatica.y"
{yyval = val_peek(1);}
break;
case 4:
//#line 29 "gramatica.y"
{yyval = val_peek(1);}
break;
case 5:
//#line 30 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
break;
case 6:
//#line 31 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
break;
case 7:
//#line 34 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de CONSTANTE. (Línea " + AnalizadorLexico.LINEA + ")");
							  	  yyval = new ParserVal(sintactico.crearNodoControl("lista_ctes", val_peek(1)));}
break;
case 8:
//#line 36 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
break;
case 9:
//#line 37 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
break;
case 10:
//#line 40 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 11:
//#line 41 "gramatica.y"
{ParserVal modificado = sintactico.modificarHijo(val_peek(2), sintactico.crearNodo("sentencia", val_peek(0), null));
                                                                         yyval = modificado;}
break;
case 12:
//#line 43 "gramatica.y"
{{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): separar por coma la declaracion de constantes.");}}
break;
case 13:
//#line 47 "gramatica.y"
{
						int existente = enAmbito(val_peek(2));
						if (existente < 0) {
							int i = val_peek(2).ival;
							sintactico.setTipoEnIndex(sintactico.getTipoFromTS(val_peek(0).ival), i);
							String type = sintactico.getTipoFromTS(val_peek(0).ival);
                                                        if (type.equals("i32"))
                                                        	sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
														else
															sintactico.verificarRangoFlotante(val_peek(0).ival);
							sintactico.setUsoEnIndex("const", i);
							sintactico.setLexemaEnIndex(val_peek(2).ival, "@"+this.ambito);
							sintactico.setUsoEnIndex("cte",val_peek(0).ival);
							yyval = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja(val_peek(2).ival)), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));
						} else {
							sintactico.addErrorSintactico("SemanticError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
						}

					}
break;
case 14:
//#line 66 "gramatica.y"
{
										int existente = enAmbito(val_peek(3));
											if (existente < 0) {
												int i = val_peek(3).ival;
												sintactico.setTipoEnIndex(sintactico.getTipoFromTS(val_peek(0).ival), i);
												sintactico.setNegativoTablaSimb(val_peek(0).ival);
												String type = sintactico.getTipoFromTS(val_peek(0).ival);
												if (type.equals("i32"))
													sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
												else
													sintactico.verificarRangoFlotante(val_peek(0).ival);
												sintactico.setUsoEnIndex("const", i);
												sintactico.setLexemaEnIndex(val_peek(3).ival, "@"+this.ambito);
												sintactico.setUsoEnIndex("cte neg",val_peek(0).ival);
												yyval = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja(val_peek(3).ival)), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));
											} else {
												sintactico.addErrorSintactico("SemanticError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
											}
		   }
break;
case 15:
//#line 85 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 16:
//#line 86 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 17:
//#line 87 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 18:
//#line 91 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 19:
//#line 92 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 20:
//#line 97 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 21:
//#line 98 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 22:
//#line 99 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 23:
//#line 100 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 24:
//#line 104 "gramatica.y"
{
							String type = val_peek(2).sval;
						 	sintactico.completarConTipos(type);
						 	sintactico.addAnalisis("Se reconoció declaraciónes de variable de tipo " + type + ". (Línea " + AnalizadorLexico.LINEA + ")");
						  }
break;
case 25:
//#line 109 "gramatica.y"
{
             						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable");
             					  	sintactico.addAnalisis("Se reconoció declaraciónes de variable SIN TIPO. (Línea " + (AnalizadorLexico.LINEA-1) + ")");
             					   	sintactico.vaciarListaVariables();
             					  }
break;
case 29:
//#line 120 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						     }
break;
case 30:
//#line 124 "gramatica.y"
{
							yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));
						     }
break;
case 36:
//#line 134 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 37:
//#line 135 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 38:
//#line 139 "gramatica.y"
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
case 39:
//#line 150 "gramatica.y"
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
case 40:
//#line 161 "gramatica.y"
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
case 41:
//#line 176 "gramatica.y"
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
case 42:
//#line 190 "gramatica.y"
{ sintactico.addErrorSintactico("SematicError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 43:
//#line 194 "gramatica.y"
{
				sintactico.setTipoGlobal(val_peek(0).sval);
			}
break;
case 44:
//#line 197 "gramatica.y"
{
				sintactico.addErrorSintactico("SematicError. ENCAB_FUN(Línea " + AnalizadorLexico.LINEA + "): falta tipo de funcion ");
	 	 		sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta tipo (Línea " + AnalizadorLexico.LINEA + ")");
			}
break;
case 45:
//#line 203 "gramatica.y"
{
					sintactico.addAnalisis("Se reconoce cuerpo de funcion (Línea " + AnalizadorLexico.LINEA + ")");
					this.ambito = borrarAmbito(this.ambito);
					sintactico.clearTipo();
 					yyval = val_peek(1);
 				}
break;
case 49:
//#line 214 "gramatica.y"
{ sintactico.addAnalisis("Se reconocen mas parametros de los deseados en la funcion (Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 50:
//#line 217 "gramatica.y"
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
case 51:
//#line 236 "gramatica.y"
{
	 								sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta identificacion (Línea " + AnalizadorLexico.LINEA + ")");
							   		sintactico.addErrorSintactico("SematicError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): funcion sin identificar.");
								}
break;
case 52:
//#line 242 "gramatica.y"
{
						Token t = sintactico.getEntradaTablaSimb(val_peek(1).ival);
						Nodo n = sintactico.crearNodoControl(t.getLexema(), val_peek(0));
						n.setTipo(t.getTipo());
						sintactico.agregarArbolFuncion(new ParserVal(n),t.getLexema());
						sintactico.clearTipo();
					}
break;
case 53:
//#line 252 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   	  sintactico.checkRetorno(val_peek(2), sintactico.getTipo());
						   	  Nodo nodoRetorno = sintactico.crearNodoControl("return",val_peek(2));
						   	  Nodo n = (Nodo) val_peek(2).obj;
						   	  nodoRetorno.setTipo(n.getTipo());
						   	  yyval = new ParserVal(nodoRetorno);}
break;
case 54:
//#line 258 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 55:
//#line 259 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 56:
//#line 260 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 57:
//#line 261 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 65:
//#line 273 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 66:
//#line 274 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 67:
//#line 278 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 68:
//#line 282 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 69:
//#line 285 "gramatica.y"
{
											ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
											yyval = modificado;
										}
break;
case 70:
//#line 289 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("declarativa", val_peek(0), null));
										yyval = modificado;
									}
break;
case 71:
//#line 293 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 72:
//#line 294 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 73:
//#line 299 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 74:
//#line 300 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 75:
//#line 301 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 76:
//#line 305 "gramatica.y"
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
case 77:
//#line 316 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 78:
//#line 318 "gramatica.y"
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
case 79:
//#line 327 "gramatica.y"
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
case 80:
//#line 340 "gramatica.y"
{ 	Nodo for_else = sintactico.crearNodo("for_else", val_peek(2), new ParserVal(sintactico.crearHoja(val_peek(0).ival)));
						String cteElse = sintactico.getTipoFromTS(val_peek(0).ival);
						if (cteElse.equals(tipoBreak)) {
							for_else.setTipo(cteElse);
                                                	yyval = new ParserVal(for_else);
						}else{
                                               		sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "):  los tipos en el BREAK/ELSE del FOR no coinciden");
                                                }

					}
break;
case 81:
//#line 350 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 82:
//#line 353 "gramatica.y"
{
						sintactico.setUsoEnIndex("cadena",val_peek(2).ival);
						sintactico.addCadena(val_peek(2).ival);
						yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 83:
//#line 357 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 84:
//#line 358 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 85:
//#line 359 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 86:
//#line 360 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 87:
//#line 361 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 88:
//#line 366 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
									sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 89:
//#line 368 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 90:
//#line 369 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 91:
//#line 373 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("condicionIf",val_peek(1)));}
break;
case 92:
//#line 374 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 93:
//#line 375 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 94:
//#line 376 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 95:
//#line 380 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if", val_peek(1), val_peek(0)));}
break;
case 96:
//#line 381 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if", val_peek(0), null));}
break;
case 97:
//#line 382 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 98:
//#line 386 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 99:
//#line 387 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 100:
//#line 391 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 101:
//#line 392 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 102:
//#line 395 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
								   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 103:
//#line 397 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 104:
//#line 398 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 105:
//#line 401 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 106:
//#line 402 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 107:
//#line 403 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 108:
//#line 406 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 109:
//#line 407 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 110:
//#line 410 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 111:
//#line 411 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 112:
//#line 414 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 113:
//#line 416 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 114:
//#line 417 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 115:
//#line 420 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 116:
//#line 421 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 117:
//#line 422 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 118:
//#line 425 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 119:
//#line 426 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 120:
//#line 429 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 121:
//#line 430 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 122:
//#line 433 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
									}
break;
case 123:
//#line 436 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 124:
//#line 437 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 125:
//#line 440 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 126:
//#line 441 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 127:
//#line 442 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 128:
//#line 445 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 129:
//#line 446 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 130:
//#line 449 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 131:
//#line 450 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 132:
//#line 452 "gramatica.y"
{this.contadorIf++; agregarAmbito("if-then"+contadorIf);}
break;
case 133:
//#line 455 "gramatica.y"
{this.ambito = borrarAmbito(this.ambito); agregarAmbito("if-else"+this.contadorIf);}
break;
case 134:
//#line 458 "gramatica.y"
{this.ambito = borrarAmbito(this.ambito);}
break;
case 135:
//#line 465 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
								  yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 136:
//#line 467 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 137:
//#line 468 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta punto y coma pero se reconoce sentencia when."); }
break;
case 138:
//#line 471 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("condicionWhen",val_peek(0))); agregarAmbito("when"+this.contadorWhen); this.contadorWhen++;}
break;
case 139:
//#line 475 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1))); this.ambito = borrarAmbito(this.ambito); }
break;
case 140:
//#line 476 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 141:
//#line 477 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 142:
//#line 484 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
							  	yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
							  	this.ambito = borrarAmbito(this.ambito);
							}
break;
case 143:
//#line 488 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 144:
//#line 489 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 145:
//#line 490 "gramatica.y"
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
case 146:
//#line 509 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));

						}
break;
case 147:
//#line 514 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 148:
//#line 518 "gramatica.y"
{
						int existente = enAmbito(val_peek(2));
						if (existente >= 0) {
							if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
								String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
								String [] aux = lexExistente.split("@");

								String ambitoExistente = aux[1];

								if ( ambitoExistente.equals(this.ambito)) {
									sintactico.setUsoEnIndex("i32",val_peek(0).ival);
									Nodo op2 = (Nodo) val_peek(0).obj;
									String typeOP2 = op2.getTipo();
                                                                        String typeOP1 = sintactico.getTipoFromTS(existente);
                                                                        if (typeOP1.equals(typeOP2)) {
										ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
										ParserVal constante = new ParserVal(op2);
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
case 149:
//#line 550 "gramatica.y"
{
					int existente = enAmbito(val_peek(4));
					if (existente >= 0) {
						if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
							String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
							String [] aux = lexExistente.split("@");

							String ambitoExistente = aux[1];

							if ( ambitoExistente.equals(this.ambito)) {
								/*sintactico.setUsoEnIndex("i32",$3.ival);*/
								Nodo op2 = (Nodo) val_peek(1).obj;
								String typeOP2 = op2.getTipo();
								String typeOP1 = sintactico.getTipoFromTS(existente);
								if (typeOP1.equals(typeOP2)) {
									ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
									ParserVal constante = new ParserVal(op2);
									yyval = new ParserVal(sintactico.crearNodoControl("condicionFor", new ParserVal(sintactico.crearNodo(val_peek(3).sval,identificador,constante))));
									sintactico.eliminarEntrada(val_peek(4).ival);
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
case 150:
//#line 584 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2))); this.variablesFor.pop();}
break;
case 151:
//#line 585 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0))); this.variablesFor.pop();}
break;
case 152:
//#line 586 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): Falta el ; al final del bloque del for");}
break;
case 153:
//#line 589 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 154:
//#line 590 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 162:
//#line 601 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 163:
//#line 602 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 165:
//#line 608 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 166:
//#line 609 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 167:
//#line 610 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): falta ; al final del bloque del for."); }
break;
case 168:
//#line 613 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        							yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										this.ambito = borrarAmbito(this.ambito);
									}
break;
case 169:
//#line 617 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 170:
//#line 618 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 171:
//#line 619 "gramatica.y"
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
case 172:
//#line 638 "gramatica.y"
{
					agregarAmbito("for"+this.contadorFor);
					this.contadorFor++;
					int existente = enAmbito(val_peek(2));
					if (existente < 0){
						sintactico.setTipoEnIndex("i32", val_peek(2).ival);
						sintactico.setTipoEnIndex("i32", val_peek(0).ival);
						sintactico.setLexemaEnIndex(val_peek(2).ival, "@"+this.ambito);
						sintactico.setUsoEnIndex("for_var", val_peek(2).ival);
						this.variablesFor.push(val_peek(2).ival);
						ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
						ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
						sintactico.setUsoEnIndex("cte",val_peek(0).ival);
						yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));
					} else {
						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): la variable utilizada para el for loop ya ha sido declarada.");
					}
				   }
break;
case 173:
//#line 661 "gramatica.y"
{
						String type = sintactico.getTipoFromTS(val_peek(0).ival);
						if (type.equals("i32")){
							ParserVal id = new ParserVal(sintactico.crearHoja(this.variablesFor.peek()));
							ParserVal cte = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
							ParserVal op = new ParserVal(sintactico.crearNodo(val_peek(1).sval, id, cte));
							ParserVal asig =  new ParserVal(sintactico.crearNodo("=:",id,op));
							sintactico.setUsoEnIndex("cte",val_peek(0).ival);

							yyval = new ParserVal(sintactico.crearNodoControl("operacionFor", asig));
						} else {
						sintactico.addErrorSintactico("SemanicError. (Línea " + (AnalizadorLexico.LINEA) + ") no se permiten flotantes en el valor de incremento/decremento");
						}

					}
break;
case 174:
//#line 676 "gramatica.y"
{sintactico.addErrorSintactico("SemanticError. (Línea " + (AnalizadorLexico.LINEA) + "): Falta el signo en la operacion de incremento/decremento del for.");}
break;
case 183:
//#line 687 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 184:
//#line 691 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 185:
//#line 692 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 186:
//#line 696 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): no se permiten cuerpos de for vacios.");}
break;
case 187:
//#line 701 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
					yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 188:
//#line 703 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                			yyval = new ParserVal(sintactico.crearNodoControl("breakValor", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));
                			sintactico.setUsoEnIndex("cte",val_peek(1).ival);
                			tipoBreak = sintactico.getTipoFromTS(val_peek(1).ival);
                			}
break;
case 189:
//#line 709 "gramatica.y"
{	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 190:
//#line 712 "gramatica.y"
{
							sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
							yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 191:
//#line 715 "gramatica.y"
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
case 192:
//#line 725 "gramatica.y"
{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 193:
//#line 726 "gramatica.y"
{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 194:
//#line 729 "gramatica.y"
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
case 196:
//#line 762 "gramatica.y"
{
		    				yyval = new ParserVal(sintactico.crearNodoParam("paramInv", val_peek(2), val_peek(0)));
		    				NodoHijo aux = (NodoHijo)val_peek(2).obj;
						sintactico.addListaVariables(aux.getRefTablaSimbolos());
						NodoHijo aux1 = (NodoHijo)val_peek(0).obj;
                                                sintactico.addListaVariables(aux1.getRefTablaSimbolos());

					}
break;
case 197:
//#line 770 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodoParam("paramInv", val_peek(0), null));

		    				NodoHijo aux = (NodoHijo)val_peek(0).obj;
                                         	sintactico.addListaVariables(aux.getRefTablaSimbolos());

		 			   }
break;
case 198:
//#line 779 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 199:
//#line 783 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 201:
//#line 788 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 202:
//#line 789 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 204:
//#line 793 "gramatica.y"
{
				int existente = enAmbito(val_peek(0));
				if (existente >= 0) {
					String type = sintactico.getTipoFromTS(existente);
					Nodo n = sintactico.crearHoja(existente);
					n.setTipo(type);
					yyval = new ParserVal(n);
					sintactico.eliminarEntrada(val_peek(0).ival);
				} else {
					sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable no declarada.");
				}
				}
break;
case 205:
//#line 805 "gramatica.y"
{
				String lexema = sintactico.getEntradaTablaSimb(val_peek(0).ival).getLexema();
                                int existente = sintactico.getTS().existeEntrada(lexema);
				if (existente >= 0 && existente < val_peek(0).ival) {
					yyval = new ParserVal(sintactico.crearHoja(existente));
					sintactico.setUsoEnIndex("cte",existente);
					sintactico.eliminarEntrada(val_peek(0).ival);
				} else {
					String type = sintactico.getTipoFromTS(val_peek(0).ival);
					if (type.equals("i32"))
					     sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
					else
						sintactico.verificarRangoFlotante(val_peek(0).ival);

					sintactico.setUsoEnIndex("cte",val_peek(0).ival);
					Nodo n = sintactico.crearHoja(val_peek(0).ival);
					n.setTipo(type);

					yyval = new ParserVal(n);
				}
                  	}
break;
case 206:
//#line 826 "gramatica.y"
{
				sintactico.setNegativoTablaSimb(val_peek(0).ival);
				String lexema = sintactico.getEntradaTablaSimb(val_peek(0).ival).getLexema();
				int existente = sintactico.getTS().existeEntrada(lexema);
                                if (existente >= 0  && existente < val_peek(0).ival) {
                                	yyval = new ParserVal(sintactico.crearHoja(existente));
                                	sintactico.setUsoEnIndex("cte neg",existente);
                                        sintactico.eliminarEntrada(val_peek(0).ival);
                                }else{
                                	String type = sintactico.getTipoFromTS(val_peek(0).ival);
									if (type.equals("i32"))
					     				sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
									else
										sintactico.verificarRangoFlotante(val_peek(0).ival);

									Nodo n = sintactico.crearHoja(val_peek(0).ival);
									n.setTipo(type);
									yyval = new ParserVal(n);
									sintactico.setUsoEnIndex("cte neg",val_peek(0).ival);
								}
                   	}
break;
case 207:
//#line 850 "gramatica.y"
{ yyval.sval = new String("<") ; }
break;
case 208:
//#line 851 "gramatica.y"
{ yyval.sval = new String(">") ; }
break;
case 209:
//#line 852 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 210:
//#line 853 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 211:
//#line 854 "gramatica.y"
{ yyval.sval = new String("=") ; }
break;
case 212:
//#line 855 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 213:
//#line 859 "gramatica.y"
{ yyval.sval = new String("i32"); }
break;
case 214:
//#line 860 "gramatica.y"
{ yyval.sval = new String("f32"); }
break;
//#line 2181 "Parser.java"
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
