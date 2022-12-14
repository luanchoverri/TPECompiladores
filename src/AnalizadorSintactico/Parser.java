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
    5,    5,    6,    6,    6,    6,    8,    8,    3,    3,
    3,    3,   10,   10,   10,   10,   10,   16,   16,   11,
   11,   11,   11,   11,   11,   11,   13,   13,   13,   24,
   24,   25,   25,   26,   28,   28,   28,   28,   29,   29,
   14,   30,   30,   30,   30,   30,   27,   33,   33,   33,
   33,   33,   33,   33,   33,   36,   36,   32,   32,   32,
   32,    7,    7,    7,   17,   17,   17,   17,   37,   37,
   18,   18,   18,   18,   18,   18,   19,   19,   19,   38,
   38,   38,   38,   39,   39,   39,   42,   42,   43,   43,
   46,   46,   46,   47,   47,   47,   48,   48,   49,   49,
   34,   34,   34,   50,   50,   50,   51,   51,   52,   52,
   53,   53,   53,   54,   54,   54,   55,   55,   56,   56,
   44,   45,   40,   15,   15,   15,   59,   60,   60,   60,
   61,   61,   61,   61,   62,   65,   66,   63,   63,   63,
   69,   69,    9,    9,    9,    9,    9,    9,    9,    9,
    9,   20,   70,   70,   70,   35,   35,   35,   35,   64,
   67,   58,   58,   58,   58,   58,   58,   58,   58,   58,
   57,   57,   57,   22,   22,   22,   23,   23,   23,   23,
   21,   71,   71,   71,   41,   31,   31,   73,   73,   73,
   72,   72,   72,   68,   68,   68,   68,   68,   68,   12,
   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    3,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    2,    2,    2,    3,    1,    2,
    2,    2,    0,    3,    0,    1,    3,    1,    6,    6,
    2,    5,    5,    5,    4,    5,    1,    1,    1,    1,
    1,    1,    1,    2,    2,    2,    1,    2,    2,    1,
    1,    1,    3,    2,    4,    4,    4,    3,    3,    2,
    5,    5,    5,    4,    3,    5,    5,    6,    5,    3,
    2,    2,    1,    2,    1,    1,    4,    2,    4,    2,
    5,    5,    4,    2,    1,    2,    4,    2,    4,    2,
    5,    5,    4,    2,    1,    2,    4,    2,    4,    2,
    5,    5,    4,    2,    1,    2,    4,    2,    4,    2,
    1,    1,    1,    6,    6,    6,    1,    4,    4,    4,
    5,    5,    4,    7,    3,    3,    3,    4,    1,    4,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    2,
    2,    1,    4,    1,    4,    5,    5,    4,    7,    3,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    2,
    1,    2,    1,    2,    3,    2,    2,    4,    4,    2,
    5,    0,    3,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    2,    1,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,  210,    0,
    0,    0,  211,    0,    0,    0,    0,    0,   26,   19,
   20,    0,    0,   25,   27,   30,   31,   32,   33,   34,
    0,    0,    0,  162,    0,   72,    0,    0,    0,    0,
    0,   37,  201,  202,    0,    0,    0,    0,    0,  200,
    0,    0,    0,    0,    0,  186,    0,  184,    0,  137,
    0,    0,    0,    0,    0,  190,    0,  187,    0,    0,
    0,    0,   10,    0,    0,    0,    6,   21,   22,    0,
    0,   35,   36,    0,   51,   38,    0,    0,    0,    0,
   74,    0,    0,   78,    0,    0,    0,  203,  206,  207,
  209,  208,  151,  152,  204,  205,    0,    0,    0,  132,
    0,    0,   96,    0,    0,   91,    0,    0,    0,    0,
    0,    0,   48,    0,    0,    0,    0,  185,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  149,    0,  153,
  154,  155,  157,  158,  159,    0,  156,    0,    0,    0,
    0,   16,    0,    0,    9,    8,    7,    0,    0,    4,
   85,    0,   23,   24,    0,    0,    0,   71,   58,   60,
   61,    0,    0,    0,   63,    0,   70,   59,   62,    0,
   73,    0,    0,    0,   80,    0,   76,   75,   77,   90,
    0,    0,  131,    0,  133,    0,   94,    0,    0,   98,
    0,  100,  198,  199,   84,    0,    0,    0,    0,   41,
   40,    0,    0,    0,    0,    0,    0,  170,    0,    0,
    0,    0,    0,   17,    0,  160,  161,  143,    0,  145,
    0,  189,  188,   15,   14,   13,   11,   12,    3,    0,
    0,    0,    0,   64,   65,   44,   69,   68,    0,  191,
  193,   79,   89,    0,   87,   29,    0,    0,   83,   82,
   81,   86,    0,   47,    0,    0,    0,    0,    0,    0,
    0,  141,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   18,  142,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  172,  173,  176,  177,
  178,  175,  174,  179,  164,    0,    0,   88,   97,   28,
   99,   49,   42,   50,    0,    0,  134,  136,  135,    0,
  108,    0,  110,  103,    0,  104,  106,    0,    0,   55,
    0,  150,  148,  147,  146,    0,    0,    0,    0,  118,
    0,  120,  113,    0,  114,  116,    0,    0,  183,    0,
  181,    0,  180,  168,  144,  139,  138,  140,    0,    0,
  102,  101,   54,   56,   52,   53,  171,    0,   67,    0,
    0,  112,  111,  166,    0,    0,    0,    0,    0,    0,
  182,  167,  107,  109,    0,  117,   66,  119,    0,  128,
    0,  130,  123,    0,  124,  126,  165,  163,  169,    0,
    0,  122,  121,  127,  129,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   72,   73,   41,  223,  138,  139,
   21,   22,   23,   24,   25,  257,   26,   27,   28,   29,
   30,   31,   32,  126,  266,   85,  174,  127,   33,  146,
   47,  176,  369,  178,  303,  370,   96,   48,  111,  196,
   49,  112,  113,  114,  115,  147,  275,  276,  277,  289,
  290,  291,  304,  377,  378,  379,  350,  305,   61,  216,
   34,   64,  148,   65,  230,  231,  335,  107,  108,  306,
   89,   50,   51,
};
final static short yysindex[] = {                      -219,
    0,    0,  807,  -38,   29,  -33,  -22,   44,    0,   32,
    1,  111,    0,   24,  977, -211,  -61,  840,    0,    0,
    0, -178,   23,    0,    0,    0,    0,    0,    0,    0,
 -165, -161,  -17,    0,  -36,    0, -178,  -27,   97, -130,
  -28,    0,    0,    0,   97, -129,   59,   33,   98,    0,
   51, -104,  -16,  121,  -59,    0,  116,    0,   97,    0,
  155,  -45,  -56,  478,  146,    0,  148,    0,  -47,   90,
  -42,   72,    0,  874,  -39,  977,    0,    0,    0,  161,
  -24,    0,    0,  790,    0,    0,  176,   28,  216,  225,
    0,    6, -151,    0,    4,  229,  255,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   97,   97,  124,    0,
   49,   52,    0,  822,  993,    0,   97,   97,  256,  -11,
  100,  -59,    0,  108,   82,  309,  329,    0,  -32,  127,
  318,  123,  350,   29,   83,  903,  558,    0,  137,    0,
    0,    0,    0,    0,    0,  139,    0,  143,  147,  156,
  361,    0,  169, -206,    0,    0,    0,  172,  170,    0,
    0,  920,    0,    0,   -5,   29,   11,    0,    0,    0,
    0,  177,  181,  307,    0,  790,    0,    0,    0,  -56,
    0,  380,   97,  174,    0,  183,    0,    0,    0,    0,
  145,   51,    0, -197,    0,  385,    0,  -30,  235,    0,
  235,    0,    0,    0,    0,  386,   58,  387,  407,    0,
    0, -179,  391,  946,  127,  393,  394,    0,  558,  124,
   97,  113,  574,    0,  202,    0,    0,    0,  362,    0,
  402,    0,    0,    0,    0,    0,    0,    0,    0,    5,
  124,  -56,  537,    0,    0,    0,    0,    0,  422,    0,
    0,    0,    0,  208,    0,    0, 1011, 1025,    0,    0,
    0,    0,  391,    0,  -91,  209,  977,  961,  408,  210,
  214,    0,  589,  606, -186,   52,  215,  150,  221,  416,
   65,    0,    0,  222,  145,  443,  219,  269, -176,   52,
  230,  450,   29,  622,  638,  239,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  240,  558,    0,    0,    0,
    0,    0,    0,    0,  654,  247,    0,    0,    0,  903,
    0,  903,    0,    0,   87,    0,    0,  250,  106,    0,
  253,    0,    0,    0,    0,  257,  -56,   -4,  856,    0,
  856,    0,    0,  109,    0,    0,  638,  124,    0,  670,
    0,  264,    0,    0,    0,    0,    0,    0,  685,  711,
    0,    0,    0,    0,    0,    0,    0,  471,    0,  317,
  334,    0,    0,    0,  726,  741, -119,   52,  266,  119,
    0,    0,    0,    0,  638,    0,    0,    0,  622,    0,
  622,    0,    0,  122,    0,    0,    0,    0,    0,  757,
  773,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  454,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  523,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  454,    0,    0,    0,  483,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  142,    0,
  395,    0,    0,    0,  486,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   22,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  489,
    0,   42,    0,    0,    0,    0,  157,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, 1040,    0,
    0, -118,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  486,    0,    0,    0,  492,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  454,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  411,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    2,  511,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  286,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  420,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -109,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -99,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -1,    0,    0,  -43,  -25, -158, -133,   -3,
   -7,   13,  372,    0,    0,  343,   30,  140,    0,  -37,
  -40,  274,  332,  333,  283,    0,    0,  427,    0,  389,
  367,    0,  -65,    0,  -44,  212,    0, -108,  441, -162,
  115,    0,  445, -199,  231,    0,    0,    0,  282,    0,
    0,  270,    0,    0,    0,  185, -189,  925,  500,  -97,
    0,  -58,  -89,    0,    0,    0,    0,  335,  281, -235,
    0,   16,  459,
};
final static int YYTABLESIZE=1326;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
   94,   39,  224,   93,  133,   37,   53,   37,  215,   39,
   79,   20,  131,   74,   78,   40,   46,   55,  177,   38,
  273,    5,   40,  143,  121,  220,  142,   38,  159,  207,
   40,  254,  217,   88,   39,   39,  132,    1,   37,  179,
   63,  287,  195,  171,  154,   39,  103,  225,  104,  235,
  242,  236,  240,  240,   90,   40,   40,  241,  253,  352,
   75,   76,  188,  184,  195,   88,   79,  125,   45,  324,
   78,   59,   20,   46,  162,  195,   46,  124,   35,  343,
  168,   81,   71,  201,  201,  195,  201,    9,  201,  282,
   82,   13,  117,  140,   83,  143,  143,  118,  142,  142,
  201,  103,   58,  104,  185,   84,  200,  202,  243,  186,
  248,  374,  325,  169,  237,  158,  261,  269,  105,  102,
  106,  249,  221,  333,   60,   91,  344,   46,   98,  272,
  157,  179,  203,  204,  125,  171,  393,   95,  116,  321,
  323,   46,  195,   95,    5,  362,  105,  131,  375,  399,
   40,  119,  105,  280,   79,  103,  115,  104,   78,   97,
  122,  359,  115,  360,  365,  140,  140,  373,   69,   68,
  125,  279,  247,   60,  128,    9,  125,  398,  143,   13,
  403,  142,  143,  292,  348,  142,  224,  103,  224,  104,
  329,  256,  103,  256,  104,  130,  123,  124,  251,  400,
   62,  401,  299,  141,  149,  169,  150,    9,  328,  151,
   20,   13,  268,  155,  394,  180,  161,  355,   35,  163,
   35,  340,  342,  170,  125,  282,  282,  214,   92,   44,
   36,  164,  143,  143,   54,  142,  142,   36,   52,  296,
   11,   87,  179,  179,  206,   36,  171,  171,  140,  310,
  310,   35,  140,  299,  299,  120,  182,   62,   16,  187,
   79,  195,  195,   20,   78,  315,  143,   62,  183,  142,
   36,   36,  297,  286,   16,  141,  141,  313,  368,  143,
   70,  143,  142,  181,  142,   43,   44,  189,   43,   44,
  296,  296,  109,  110,  179,  190,  179,  201,  171,   56,
  171,   57,  140,  140,  387,  387,  299,   79,   16,  299,
  195,   78,  110,  260,  205,  170,  169,  169,  143,  143,
  332,  142,  142,  297,  297,  179,  179,  156,   70,  171,
  171,   99,  100,  101,  299,  299,  140,  144,  211,   43,
   44,  339,  361,  296,  299,  152,  296,  153,  299,  140,
  299,  140,  212,   43,   44,  208,   16,  172,  141,  299,
  299,  364,  141,  210,  372,   36,   66,   67,  169,  213,
  169,  296,  296,   16,  397,   42,  297,  402,   88,  297,
  218,  296,  298,  193,  110,  296,  214,  296,  140,  140,
  219,  341,  226,   80,  227,  145,  296,  296,  228,  169,
  169,   93,   93,  229,  297,  297,   42,   95,   86,  144,
  144,  232,  141,  141,  297,  173,   92,   92,  297,  233,
  297,  105,  102,  106,  234,  238,  170,  170,   70,  297,
  297,  246,  244,  298,  298,  197,  245,  197,  250,  197,
  252,  386,   87,  255,  259,  262,  141,  263,  265,  172,
  274,  270,  271,  197,  197,  197,  197,  283,  388,  141,
  285,  141,  307,  308,  314,  318,  317,  145,  145,  319,
  327,  288,  175,  191,  331,  338,  330,  166,  170,  334,
  170,    6,  337,  135,    8,  346,  298,  167,   12,  298,
  347,  198,  144,    5,  353,  354,  144,    6,  141,  141,
    8,  222,  358,   11,   12,  363,  274,  173,  366,  170,
  170,  385,   39,  367,  298,  298,  300,   16,  137,  382,
  288,  396,    1,  192,  298,  338,   45,  166,  298,  194,
  298,    6,   46,  135,    8,   57,   42,  167,   12,  298,
  298,   43,   43,  258,  264,  312,  144,  144,  209,  194,
  145,  196,  371,  196,  145,  196,  197,  326,  129,  345,
  172,  172,  395,  284,  175,  336,  192,  300,  300,  196,
  196,  196,  196,  338,  301,  166,   16,  295,  376,    6,
  144,  135,    8,    0,    0,  167,   12,  278,    0,    0,
  338,    0,  166,  144,    0,  144,    6,   16,  135,    8,
  136,    0,  167,   12,  145,  145,    0,    0,  376,    0,
    0,    0,  172,   16,  172,    0,    0,    0,  173,  173,
  300,    0,    0,  300,    0,  301,  301,    0,   16,    0,
    0,  302,  144,  144,   99,  100,  101,    0,  145,    0,
    0,    0,    0,  172,  172,   16,    0,    0,  300,  300,
  197,  145,    0,  145,  197,  197,    0,    0,  300,  294,
    0,   16,  300,    0,  300,    0,    0,  197,  197,  197,
  173,    0,  173,  300,  300,  175,  175,   16,  301,    0,
  136,  301,  302,  302,    0,    0,    0,    0,    0,    0,
  145,  145,    0,   16,    0,    0,    0,    0,  281,    0,
    0,  173,  173,    0,    0,    0,  301,  301,    0,   16,
    0,  320,    0,    0,    0,    0,  301,    0,    0,    0,
  301,    0,  301,    0,   16,    0,    0,  175,  322,  175,
    0,  301,  301,    0,    4,  302,  134,    0,  302,    0,
    6,    7,  135,    8,    9,   10,   11,   12,   13,    0,
   16,    0,    0,    0,   14,    0,    0,    0,  175,  175,
  294,    0,    0,  302,  302,   16,  196,    0,    0,    0,
  196,  196,    0,  302,    0,    0,    0,  302,  357,  302,
   16,    0,    0,  196,  196,  196,    0,    0,  302,  302,
    0,    0,    0,  165,  380,  293,   16,    0,    0,    6,
    7,  135,    8,    9,   10,  167,   12,   13,    0,  383,
    0,    0,   16,   14,    4,    0,  134,    0,    0,    0,
    6,    7,  135,    8,    9,   10,   11,   12,   13,   16,
    4,    0,  134,    0,   14,  384,    6,    7,  135,    8,
    9,   10,   11,   12,   13,    4,   16,  134,  389,    0,
   14,    6,    7,  135,    8,    9,   10,   11,   12,   13,
    0,   16,    4,  391,  134,   14,    0,    0,    6,    7,
  135,    8,    9,   10,   11,   12,   13,  349,  165,   16,
  293,  404,   14,    0,    6,    7,  135,    8,    9,   10,
  167,   12,   13,    0,  165,   16,  293,  405,   14,    0,
    6,    7,  135,    8,    9,   10,  167,   12,   13,  356,
    4,    0,    5,   16,   14,    0,    6,    7,    0,    8,
    9,   10,   11,   12,   13,    0,  165,    0,  293,   15,
   14,    0,    6,    7,  135,    8,    9,   10,  167,   12,
   13,    4,   16,  134,  199,    0,   14,    6,    7,  135,
    8,    9,   10,   11,   12,   13,    0,    0,    0,   16,
    0,   14,    0,    0,   77,    0,    0,    4,    0,  134,
    0,    0,    0,    6,    7,  135,    8,    9,   10,   11,
   12,   13,  165,    0,  293,   16,    0,   14,    6,    7,
  135,    8,    9,   10,  167,   12,   13,  165,  160,  293,
   16,    0,   14,    6,    7,  135,    8,    9,   10,  167,
   12,   13,    0,  165,    0,  293,   16,   14,    0,    6,
    7,  135,    8,    9,   10,  167,   12,   13,    0,  165,
    0,  293,   16,   14,    0,    6,    7,  135,    8,    9,
   10,  167,   12,   13,  239,    0,  165,    0,  166,   14,
   16,    0,    6,    7,  135,    8,    9,   10,  167,   12,
   13,    0,    0,    4,   16,    5,   14,    0,  267,    6,
    7,    0,    8,    9,   10,   11,   12,   13,  198,  131,
    5,    0,    0,   14,    6,  316,    0,    8,    0,    0,
   11,   12,    0,    0,    0,    0,    4,    0,    5,    0,
    0,    0,    6,    7,    0,    8,    9,   10,   11,   12,
   13,    0,  338,    0,  166,  201,   14,    0,    6,    0,
  135,    8,    0,    0,  167,   12,    0,    0,    0,    0,
    4,    0,    5,    0,    0,  309,    6,    7,    0,    8,
    9,   10,   11,   12,   13,    0,    0,    0,    0,  311,
   14,    0,    0,    0,    0,    0,    0,    0,    0,    4,
    0,  134,  131,    0,    0,    6,    7,  135,    8,    9,
   10,   11,   12,   13,    0,    0,    4,    0,    5,   14,
    0,    0,    6,    7,    0,    8,    9,   10,   11,   12,
   13,    0,    0,    0,    0,    0,   14,    0,    0,    0,
    0,    0,    4,    0,    5,    0,    0,    0,    6,    7,
    0,    8,    9,   10,   11,   12,   13,    4,  351,    5,
    0,    0,   14,    6,    7,    0,    8,    9,   10,   11,
   12,   13,    0,    4,    0,    5,    0,   14,    0,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,  198,
    0,    5,    0,   14,    0,    6,    0,    0,    8,    0,
    0,   11,   12,    0,    0,    0,    0,  198,    0,    5,
    0,    0,    0,    6,  381,    0,    8,    0,    0,   11,
   12,  198,    0,    5,    0,    0,    0,    6,    0,    0,
    8,    0,    0,   11,   12,    0,  131,    0,  131,  390,
  392,    0,  131,    0,    0,  131,    0,    0,  131,  131,
    0,    0,    0,  351,    0,  351,    0,    0,    0,    0,
    0,    0,    0,    0,  381,  381,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   41,   40,  136,   41,   63,   44,   40,   44,   41,   40,
   18,   15,   58,   15,   18,   61,   45,   40,   84,   58,
  220,    0,   61,   64,   41,  134,   64,   58,   72,   41,
   61,  194,  130,   61,   40,   40,   62,  257,   44,   84,
   40,  241,   41,   84,   70,   40,   43,  137,   45,  256,
   40,  258,   58,   58,   39,   61,   61,  166,  256,  295,
  272,  123,   59,   58,  262,   61,   74,   55,   40,  256,
   74,   40,   76,   45,   76,  262,   45,  257,  257,  256,
   84,   59,   59,   42,   43,  262,   45,  267,   47,  223,
  256,  271,   42,   64,  256,  136,  137,   47,  136,  137,
   59,   43,   59,   45,  256,  123,  114,  115,  167,  261,
  176,  347,  275,   84,  158,   44,   59,  215,   60,   61,
   62,  180,   40,   59,   10,  256,  289,   45,  258,  219,
   59,  176,  117,  118,  122,  176,  256,  256,   41,  273,
  274,   45,  262,  262,  123,   59,  256,   58,  348,  385,
   61,  256,  262,   41,  162,   43,  256,   45,  162,   45,
   40,  320,  262,  322,   59,  136,  137,   59,   58,   59,
  256,   59,  176,   59,   59,  267,  262,   59,  219,  271,
   59,  219,  223,  242,  293,  223,  320,   43,  322,   45,
   41,  199,   43,  201,   45,   41,  256,  257,  183,  389,
  257,  391,  243,   64,   59,  176,   59,  267,   59,  257,
  214,  271,  214,  256,  377,   40,  256,  307,  257,   59,
  257,  287,  288,   84,  212,  359,  360,  260,  257,  258,
  276,  256,  273,  274,  257,  273,  274,  276,  272,  243,
  269,  269,  287,  288,  256,  276,  287,  288,  219,  257,
  258,  257,  223,  294,  295,  272,   41,  257,   40,  256,
  268,  260,  261,  267,  268,  267,  307,  257,   44,  307,
  276,  276,  243,  269,   40,  136,  137,  265,  337,  320,
  257,  322,  320,  256,  322,  257,  258,   59,  257,  258,
  294,  295,  260,  261,  339,   41,  341,  256,  339,  256,
  341,  258,  273,  274,  370,  371,  347,  315,   40,  350,
  262,  315,  261,  256,   59,  176,  287,  288,  359,  360,
  256,  359,  360,  294,  295,  370,  371,  256,  257,  370,
  371,  273,  274,  275,  375,  376,  307,   64,  257,  257,
  258,  123,  256,  347,  385,  256,  350,  258,  389,  320,
  391,  322,   44,  257,  258,  256,   40,   84,  219,  400,
  401,  256,  223,  256,  256,  276,  256,  257,  339,   41,
  341,  375,  376,   40,  256,    4,  347,  256,   61,  350,
  258,  385,  243,  260,  261,  389,  260,  391,  359,  360,
   41,  123,  256,   22,  256,   64,  400,  401,  256,  370,
  371,  260,  261,  257,  375,  376,   35,   41,   37,  136,
  137,  256,  273,  274,  385,   84,  260,  261,  389,   59,
  391,   60,   61,   62,  256,  256,  287,  288,  257,  400,
  401,  125,  256,  294,  295,   41,  256,   43,   59,   45,
  258,  125,  269,   59,   59,   59,  307,   41,   58,  176,
  220,   59,   59,   59,   60,   61,   62,  256,  125,  320,
   59,  322,   41,  256,  256,  256,   59,  136,  137,  256,
  256,  241,   84,  107,   59,  257,  256,  259,  339,  258,
  341,  263,   40,  265,  266,  256,  347,  269,  270,  350,
   41,  257,  219,  259,  256,  256,  223,  263,  359,  360,
  266,  135,  256,  269,  270,  256,  276,  176,  256,  370,
  371,   41,   59,  257,  375,  376,  243,   40,   41,  256,
  290,  256,    0,   41,  385,  257,   41,  259,  389,   41,
  391,  263,   41,  265,  266,  125,  165,  269,  270,  400,
  401,  256,  123,  201,  212,  263,  273,  274,  122,  109,
  219,   41,  341,   43,  223,   45,  112,  276,   59,  290,
  287,  288,  378,  229,  176,  285,  108,  294,  295,   59,
   60,   61,   62,  257,  243,  259,   40,   41,  348,  263,
  307,  265,  266,   -1,   -1,  269,  270,  221,   -1,   -1,
  257,   -1,  259,  320,   -1,  322,  263,   40,  265,  266,
  123,   -1,  269,  270,  273,  274,   -1,   -1,  378,   -1,
   -1,   -1,  339,   40,  341,   -1,   -1,   -1,  287,  288,
  347,   -1,   -1,  350,   -1,  294,  295,   -1,   40,   -1,
   -1,  243,  359,  360,  273,  274,  275,   -1,  307,   -1,
   -1,   -1,   -1,  370,  371,   40,   -1,   -1,  375,  376,
  256,  320,   -1,  322,  260,  261,   -1,   -1,  385,  123,
   -1,   40,  389,   -1,  391,   -1,   -1,  273,  274,  275,
  339,   -1,  341,  400,  401,  287,  288,   40,  347,   -1,
  123,  350,  294,  295,   -1,   -1,   -1,   -1,   -1,   -1,
  359,  360,   -1,   40,   -1,   -1,   -1,   -1,  125,   -1,
   -1,  370,  371,   -1,   -1,   -1,  375,  376,   -1,   40,
   -1,  123,   -1,   -1,   -1,   -1,  385,   -1,   -1,   -1,
  389,   -1,  391,   -1,   40,   -1,   -1,  339,  123,  341,
   -1,  400,  401,   -1,  257,  347,  259,   -1,  350,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   -1,
   40,   -1,   -1,   -1,  277,   -1,   -1,   -1,  370,  371,
  123,   -1,   -1,  375,  376,   40,  256,   -1,   -1,   -1,
  260,  261,   -1,  385,   -1,   -1,   -1,  389,  125,  391,
   40,   -1,   -1,  273,  274,  275,   -1,   -1,  400,  401,
   -1,   -1,   -1,  257,  125,  259,   40,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   -1,  125,
   -1,   -1,   40,  277,  257,   -1,  259,   -1,   -1,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   40,
  257,   -1,  259,   -1,  277,  125,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  257,   40,  259,  123,   -1,
  277,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   -1,   40,  257,  123,  259,  277,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  256,  257,   40,
  259,  125,  277,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,  257,   40,  259,  125,  277,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  256,
  257,   -1,  259,   40,  277,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,   -1,  257,   -1,  259,  123,
  277,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  257,   40,  259,  123,   -1,  277,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,   40,
   -1,  277,   -1,   -1,  125,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  257,   -1,  259,   40,   -1,  277,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  257,  125,  259,
   40,   -1,  277,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,  257,   -1,  259,   40,  277,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   -1,  257,
   -1,  259,   40,  277,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  125,   -1,  257,   -1,  259,  277,
   40,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   -1,  257,   40,  259,  277,   -1,  123,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,  257,   40,
  259,   -1,   -1,  277,  263,  125,   -1,  266,   -1,   -1,
  269,  270,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,
   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,  270,
  271,   -1,  257,   -1,  259,  123,  277,   -1,  263,   -1,
  265,  266,   -1,   -1,  269,  270,   -1,   -1,   -1,   -1,
  257,   -1,  259,   -1,   -1,  125,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,   -1,   -1,   -1,   -1,  125,
  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,  259,  123,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,  257,   -1,  259,  277,
   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,  270,
  271,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,  257,  294,  259,
   -1,   -1,  277,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,   -1,  257,   -1,  259,   -1,  277,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,  257,
   -1,  259,   -1,  277,   -1,  263,   -1,   -1,  266,   -1,
   -1,  269,  270,   -1,   -1,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,  350,   -1,  266,   -1,   -1,  269,
  270,  257,   -1,  259,   -1,   -1,   -1,  263,   -1,   -1,
  266,   -1,   -1,  269,  270,   -1,  257,   -1,  259,  375,
  376,   -1,  263,   -1,   -1,  266,   -1,   -1,  269,  270,
   -1,   -1,   -1,  389,   -1,  391,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  400,  401,
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

//#line 798 "gramatica.y"

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;
private String ambito;
private int contadorFor;
private int contadorIf;
private int contadorWhen;
private String tipoBreak = new String();


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
//#line 895 "Parser.java"
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
case 2:
//#line 23 "gramatica.y"
{sintactico.setUsoEnIndex("program",val_peek(0).ival);}
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
//#line 41 "gramatica.y"
{{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): separar por coma la declaracion de constantes.");}}
break;
case 13:
//#line 45 "gramatica.y"
{
						int existente = enAmbito(val_peek(2));
						if (existente < 0) {
							int i = val_peek(2).ival;
							sintactico.setTipoEnIndex(sintactico.getTipoFromTS(val_peek(0).ival), i);
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
//#line 59 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 15:
//#line 60 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 16:
//#line 61 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 17:
//#line 65 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 18:
//#line 66 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 19:
//#line 71 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 20:
//#line 72 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 21:
//#line 73 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 22:
//#line 74 "gramatica.y"
{yyval = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 23:
//#line 78 "gramatica.y"
{
							String type = val_peek(2).sval;
						 	sintactico.completarConTipos(type);
						 	sintactico.addAnalisis("Se reconoció declaraciónes de variable de tipo " + type + ". (Línea " + AnalizadorLexico.LINEA + ")");
						  }
break;
case 24:
//#line 83 "gramatica.y"
{
             						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable");
             					  	sintactico.addAnalisis("Se reconoció declaraciónes de variable SIN TIPO. (Línea " + (AnalizadorLexico.LINEA-1) + ")");
             					   	sintactico.vaciarListaVariables();
             					  }
break;
case 28:
//#line 94 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						     }
break;
case 29:
//#line 98 "gramatica.y"
{
							yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));
						     }
break;
case 35:
//#line 108 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 36:
//#line 109 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 37:
//#line 113 "gramatica.y"
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
case 38:
//#line 124 "gramatica.y"
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
case 39:
//#line 135 "gramatica.y"
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
case 40:
//#line 150 "gramatica.y"
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
case 41:
//#line 164 "gramatica.y"
{ sintactico.addErrorSintactico("SematicError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 42:
//#line 168 "gramatica.y"
{
				sintactico.setTipoGlobal(val_peek(0).sval);
			}
break;
case 43:
//#line 171 "gramatica.y"
{
				sintactico.addErrorSintactico("SematicError. ENCAB_FUN(Línea " + AnalizadorLexico.LINEA + "): falta tipo de funcion ");
	 	 		sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta tipo (Línea " + AnalizadorLexico.LINEA + ")");
			}
break;
case 44:
//#line 177 "gramatica.y"
{
					sintactico.addAnalisis("Se reconoce cuerpo de funcion (Línea " + AnalizadorLexico.LINEA + ")");
					this.ambito = borrarAmbito(this.ambito);
					sintactico.clearTipo();
 					yyval = val_peek(1);
 				}
break;
case 48:
//#line 188 "gramatica.y"
{ sintactico.addAnalisis("Se reconocen mas parametros de los deseados en la funcion (Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 49:
//#line 191 "gramatica.y"
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
case 50:
//#line 210 "gramatica.y"
{
	 								sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta identificacion (Línea " + AnalizadorLexico.LINEA + ")");
							   		sintactico.addErrorSintactico("SematicError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): funcion sin identificar.");
								}
break;
case 51:
//#line 216 "gramatica.y"
{
						Token t = sintactico.getEntradaTablaSimb(val_peek(1).ival);
						Nodo n = sintactico.crearNodoControl(t.getLexema(), val_peek(0));
						n.setTipo(t.getTipo());
						sintactico.agregarArbolFuncion(new ParserVal(n),t.getLexema());
						sintactico.clearTipo();
					}
break;
case 52:
//#line 226 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   	  sintactico.checkRetorno(val_peek(2), sintactico.getTipo());
						   	  Nodo nodoRetorno = sintactico.crearNodoControl("return",val_peek(2));
						   	  Nodo n = (Nodo) val_peek(2).obj;
						   	  nodoRetorno.setTipo(n.getTipo());
						   	  yyval = new ParserVal(nodoRetorno);}
break;
case 53:
//#line 232 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 233 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 55:
//#line 234 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 56:
//#line 235 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 64:
//#line 247 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 65:
//#line 248 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 66:
//#line 252 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 67:
//#line 256 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 68:
//#line 259 "gramatica.y"
{
											ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
											yyval = modificado;
										}
break;
case 69:
//#line 263 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("declarativa", val_peek(0), null));
										yyval = modificado;
									}
break;
case 70:
//#line 267 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 71:
//#line 268 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("declarativa", val_peek(0), null));}
break;
case 72:
//#line 273 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 73:
//#line 274 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 74:
//#line 275 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 75:
//#line 279 "gramatica.y"
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
case 76:
//#line 290 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 77:
//#line 292 "gramatica.y"
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
case 78:
//#line 301 "gramatica.y"
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
case 79:
//#line 314 "gramatica.y"
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
case 80:
//#line 324 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 81:
//#line 327 "gramatica.y"
{
						sintactico.setUsoEnIndex("cadena",val_peek(2).ival);
						sintactico.addCadena(val_peek(2).ival);
						yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 82:
//#line 331 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 83:
//#line 332 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 84:
//#line 333 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 85:
//#line 334 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 86:
//#line 335 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 87:
//#line 340 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
									sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 88:
//#line 342 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 89:
//#line 343 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 90:
//#line 347 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("condicionIf",val_peek(1)));}
break;
case 91:
//#line 348 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 92:
//#line 349 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 93:
//#line 350 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 94:
//#line 354 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if", val_peek(1), val_peek(0)));}
break;
case 95:
//#line 355 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if", val_peek(0), null));}
break;
case 96:
//#line 356 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 97:
//#line 360 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 98:
//#line 361 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 99:
//#line 365 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 100:
//#line 366 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 101:
//#line 369 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
								   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 102:
//#line 371 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 103:
//#line 372 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 104:
//#line 375 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 105:
//#line 376 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 106:
//#line 377 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 107:
//#line 380 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 108:
//#line 381 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 109:
//#line 384 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 110:
//#line 385 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 111:
//#line 388 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 112:
//#line 390 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 113:
//#line 391 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 114:
//#line 394 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 115:
//#line 395 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 116:
//#line 396 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 117:
//#line 399 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 118:
//#line 400 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 119:
//#line 403 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 120:
//#line 404 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 121:
//#line 407 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
									}
break;
case 122:
//#line 410 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 123:
//#line 411 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 124:
//#line 414 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(1),val_peek(0)));}
break;
case 125:
//#line 415 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo-if",val_peek(0),null));}
break;
case 126:
//#line 416 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 127:
//#line 419 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 128:
//#line 420 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 129:
//#line 423 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 130:
//#line 424 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 131:
//#line 426 "gramatica.y"
{this.contadorIf++; agregarAmbito("if-then"+contadorIf);}
break;
case 132:
//#line 429 "gramatica.y"
{this.ambito = borrarAmbito(this.ambito); agregarAmbito("if-else"+this.contadorIf);}
break;
case 133:
//#line 432 "gramatica.y"
{this.ambito = borrarAmbito(this.ambito);}
break;
case 134:
//#line 439 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
								  yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 135:
//#line 441 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 136:
//#line 442 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 137:
//#line 445 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("condicionWhen",val_peek(0))); agregarAmbito("when"+this.contadorWhen); this.contadorWhen++;}
break;
case 138:
//#line 449 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1))); this.ambito = borrarAmbito(this.ambito); }
break;
case 139:
//#line 450 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 140:
//#line 451 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 141:
//#line 458 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
							  	yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
							  	this.ambito = borrarAmbito(this.ambito);
							}
break;
case 142:
//#line 462 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 143:
//#line 463 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 144:
//#line 464 "gramatica.y"
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
case 145:
//#line 483 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));

						}
break;
case 146:
//#line 488 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 147:
//#line 492 "gramatica.y"
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
case 148:
//#line 526 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 149:
//#line 527 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 150:
//#line 528 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): Falta el ; al final del bloque del for");}
break;
case 151:
//#line 531 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 152:
//#line 532 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 160:
//#line 543 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 161:
//#line 544 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 163:
//#line 550 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 164:
//#line 551 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 165:
//#line 552 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): falta ; al final del bloque del for."); }
break;
case 166:
//#line 555 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        							yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										this.ambito = borrarAmbito(this.ambito);
									}
break;
case 167:
//#line 559 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 168:
//#line 560 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 169:
//#line 561 "gramatica.y"
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
case 170:
//#line 580 "gramatica.y"
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
						sintactico.setUsoEnIndex("cte",val_peek(0).ival);
						yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));
					} else {
						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): la variable utilizada para el for loop ya ha sido declarada.");
					}
				   }
break;
case 171:
//#line 602 "gramatica.y"
{
					int existente = enAmbito(val_peek(0));
					if (existente >= 0) {
						if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
							String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
							String [] aux = lexExistente.split("@");
                                                        String ambitoExistente = aux[1];
							if ( ambitoExistente.equals(this.ambito)) {
							 	ParserVal hoja = new ParserVal(sintactico.crearHoja(existente));/* variableFor*/
							 	Nodo n = sintactico.crearNodoControl("1",null);
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
case 180:
//#line 637 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 181:
//#line 641 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 182:
//#line 642 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 183:
//#line 646 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): no se permiten cuerpos de for vacios.");}
break;
case 184:
//#line 651 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
					yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 185:
//#line 653 "gramatica.y"
{	sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                			yyval = new ParserVal(sintactico.crearNodoControl("breakValor", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));
                			sintactico.setUsoEnIndex("cte",val_peek(1).ival);
                			tipoBreak = sintactico.getTipoFromTS(val_peek(1).ival);
                			}
break;
case 186:
//#line 659 "gramatica.y"
{	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 187:
//#line 662 "gramatica.y"
{
							sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
							yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 188:
//#line 665 "gramatica.y"
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
case 189:
//#line 675 "gramatica.y"
{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 190:
//#line 676 "gramatica.y"
{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 191:
//#line 679 "gramatica.y"
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
case 193:
//#line 712 "gramatica.y"
{
		    				yyval = new ParserVal(sintactico.crearNodoParam("paramInv", val_peek(2), val_peek(0)));
		    				NodoHijo aux = (NodoHijo)val_peek(2).obj;
						sintactico.addListaVariables(aux.getRefTablaSimbolos());
						NodoHijo aux1 = (NodoHijo)val_peek(0).obj;
                                                sintactico.addListaVariables(aux1.getRefTablaSimbolos());

					}
break;
case 194:
//#line 720 "gramatica.y"
{	yyval = new ParserVal(sintactico.crearNodoParam("paramInv", val_peek(0), null));

		    				NodoHijo aux = (NodoHijo)val_peek(0).obj;
                                         	sintactico.addListaVariables(aux.getRefTablaSimbolos());

		 			   }
break;
case 195:
//#line 729 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 196:
//#line 733 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 198:
//#line 738 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 199:
//#line 739 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 201:
//#line 743 "gramatica.y"
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
case 202:
//#line 753 "gramatica.y"
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
					sintactico.setUsoEnIndex("cte",val_peek(0).ival);
					yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
				}
                  	}
break;
case 203:
//#line 768 "gramatica.y"
{
				sintactico.setNegativoTablaSimb(val_peek(0).ival);
				String lexema = sintactico.getEntradaTablaSimb(val_peek(0).ival).getLexema();
				int existente = sintactico.getTS().existeEntrada(lexema);
                                if (existente >= 0  && existente < val_peek(0).ival) {
                                	yyval = new ParserVal(sintactico.crearHoja(existente));
                                	sintactico.setUsoEnIndex("cte neg",existente);
                                        sintactico.eliminarEntrada(val_peek(0).ival);
                                }else{
					yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					sintactico.setUsoEnIndex("cte neg",val_peek(0).ival);
				}
                   	}
break;
case 204:
//#line 784 "gramatica.y"
{ yyval.sval = new String("<") ; }
break;
case 205:
//#line 785 "gramatica.y"
{ yyval.sval = new String(">") ; }
break;
case 206:
//#line 786 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 207:
//#line 787 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 208:
//#line 788 "gramatica.y"
{ yyval.sval = new String("=") ; }
break;
case 209:
//#line 789 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 210:
//#line 793 "gramatica.y"
{ yyval.sval = new String("i32"); }
break;
case 211:
//#line 794 "gramatica.y"
{ yyval.sval = new String("f32"); }
break;
//#line 2094 "Parser.java"
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
