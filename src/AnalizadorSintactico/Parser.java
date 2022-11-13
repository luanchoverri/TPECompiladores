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
    3,   10,   10,   10,   10,   10,   10,   16,   16,   11,
   11,   11,   11,   11,   11,   13,   13,   13,   23,   23,
   24,   24,   25,   25,   26,   14,   14,   14,   14,   28,
   28,   28,   28,   28,   27,   31,   31,   31,   31,   31,
   31,   31,   34,   34,   30,   30,   30,   30,    7,    7,
    7,   17,   17,   17,   35,   35,   18,   18,   18,   18,
   18,   18,   19,   19,   19,   36,   36,   36,   36,   37,
   37,   37,   39,   39,   40,   40,   41,   41,   41,   42,
   42,   42,   43,   43,   44,   44,   32,   32,   32,   45,
   45,   45,   46,   46,   47,   47,   48,   48,   48,   49,
   49,   49,   50,   50,   51,   51,   15,   15,   15,   55,
   55,   55,   56,   56,   56,   56,   56,   57,   61,   54,
   58,   58,   59,   59,    9,    9,    9,    9,    9,    9,
    9,    9,   20,   64,   64,   33,   33,   33,   33,   33,
   60,   62,   53,   53,   53,   53,   53,   53,   53,   53,
   52,   52,   21,   21,   21,   22,   22,   22,   22,   38,
   29,   29,   65,   65,   65,   66,   66,   66,   63,   63,
   63,   63,   63,   63,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    2,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    2,    2,    1,    3,    3,    3,    3,
    2,    2,    2,    0,    5,    3,    5,    7,    2,    5,
    5,    5,    4,    5,    1,    1,    1,    1,    1,    1,
    2,    2,    2,    1,    2,    2,    1,    1,    1,    2,
    1,    4,    4,    4,    3,    2,    5,    5,    5,    4,
    3,    5,    5,    6,    5,    3,    2,    2,    1,    2,
    1,    1,    4,    2,    4,    2,    5,    5,    4,    2,
    1,    2,    4,    2,    4,    2,    5,    5,    4,    2,
    1,    2,    4,    2,    4,    2,    5,    5,    4,    2,
    1,    2,    4,    2,    4,    2,    6,    6,    6,    4,
    4,    4,    5,   12,   11,   13,   14,    3,    3,    3,
    4,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    2,    2,    1,    4,    1,    5,   12,   11,   13,   14,
    3,    2,    1,    1,    1,    1,    1,    1,    1,    2,
    1,    2,    2,    3,    2,    2,    4,    4,    2,    3,
    3,    1,    3,    3,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,  195,    0,
    0,    0,  196,    0,    0,    0,    0,    0,   26,   18,
   19,    0,   25,   27,   30,   31,   32,   33,    0,    0,
    0,  153,   69,    0,   71,    0,  186,  187,    0,    0,
    0,    0,    0,    0,  185,    0,    0,    0,    0,  175,
    0,  173,    0,    0,    0,    0,    0,  179,    0,  176,
    0,    0,    0,    0,   10,    0,    0,    0,    6,   20,
   21,   24,   36,    0,   34,   35,    0,    0,    0,    0,
   49,    0,   70,    0,    0,    0,    0,    0,  188,  191,
  192,  194,  193,  143,  144,  189,  190,    0,    0,    0,
    0,    0,    0,   92,   87,    0,    0,    0,    0,    0,
   39,   40,  174,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   15,    0,    0,    9,    8,    7,    0,
    4,   81,    0,   23,    0,   22,    0,   42,    0,    0,
   41,    0,   46,    0,    0,   76,    0,   73,   72,   74,
   86,    0,    0,    0,    0,   94,    0,    0,   96,    0,
   90,  183,  184,   80,    0,    0,    0,  140,    0,    0,
    0,    0,    0,    0,    0,    0,  178,  177,   14,   13,
   12,   11,    3,   38,   37,   43,    0,    0,    0,   75,
   29,    0,   85,    0,    0,   83,   79,   78,   77,   82,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  142,    0,  145,  146,  147,  149,  150,    0,  148,  133,
    0,  138,    0,    0,    0,   68,   56,   58,    0,    0,
    0,   60,    0,   67,   57,   59,    0,   47,    0,   93,
   28,   84,   95,    0,    0,  127,  129,  128,    0,    0,
    0,    0,    0,    0,   16,  151,  152,    0,    0,    0,
    0,    0,   61,   62,   45,   66,   65,    0,    0,  131,
  130,  132,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   17,    0,  139,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   48,    0,    0,    0,    0,
  104,    0,  106,   99,    0,  100,  102,    0,    0,   53,
    0,  141,  162,    0,    0,  114,    0,  116,  109,    0,
  110,  112,    0,    0,    0,    0,    0,    0,    0,    0,
   98,   97,   52,   54,   50,   51,    0,   64,    0,    0,
  108,  107,    0,    0,    0,    0,    0,  163,  164,  167,
  168,  166,  165,  169,  155,  156,    0,    0,    0,    0,
  103,  105,    0,  113,   63,  115,    0,    0,    0,    0,
  171,  170,    0,    0,  135,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  172,    0,  134,    0,    0,
    0,    0,    0,  124,    0,  126,  119,    0,  120,  122,
  154,    0,  136,    0,    0,    0,    0,    0,  118,  117,
  137,    0,    0,    0,    0,  123,  125,    0,    0,  158,
    0,    0,  157,    0,    0,  159,  160,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   64,   65,   36,  254,  211,  212,
   21,   22,   74,   23,   24,  192,   25,   26,   27,   28,
   29,   30,   31,   80,  140,   81,  231,  218,   41,  233,
  338,  235,  353,  339,   87,   42,  102,   43,  103,  104,
  219,  277,  278,  279,  290,  291,  292,  354,  382,  383,
  384,  370,  355,   55,  171,   32,  120,  220,   98,  121,
  222,  286,   99,  356,   44,   45,
};
final static short yysindex[] = {                      -227,
    0,    0,  689,  -45,   22,  -37,  -15,   91,    0,   -4,
    5,  130,    0,  -41,  879, -233,  -77,  731,    0,    0,
    0, -200,    0,    0,    0,    0,    0,    0, -170, -143,
  -17,    0,    0,  -52,    0,  -12,    0,    0,  107, -136,
   -8, -161,  114,   70,    0, -108,  -39,  137,  -76,    0,
  141,    0,  100,  -44,  164,  -38,   -5,    0,  161,    0,
   21,  -47,   46,   74,    0,  750,   49,  879,    0,    0,
    0,    0,    0,   67,    0,    0,   72,  166,   83,   57,
    0,  216,    0,  284, -110,   60,  309,  340,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  107,  107,   30,
  511,  121,  134,    0,    0,  107,  107,  332,   36,  147,
    0,    0,    0,  148,  -35,  152,  336,  151,  -38,  373,
  357,  163,  358,    0,  168, -209,    0,    0,    0,  171,
    0,    0,  776,    0,  173,    0,  174,    0, -184,  297,
    0, -216,    0,  176,  170,    0,  172,    0,    0,    0,
    0,   70,   93,  940,  985,    0, -228,  985,    0,  378,
    0,    0,    0,    0,  384,   19,  387,    0,  706,  152,
  388,  389,  390,  197,   89,  -44,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  802,   80,  -38,    0,
    0,  236,    0,  196,  971,    0,    0,    0,    0,    0,
  879,  824,  397,  212,  213,  -44,  412,   22,   28,  843,
    0,  218,    0,    0,    0,    0,    0,  222,    0,    0,
  420,    0,  -29,   22,   31,    0,    0,    0,  224,  225,
  359,    0,  802,    0,    0,    0, -216,    0,  227,    0,
    0,    0,    0,  467,  226,    0,    0,    0,  424,  -44,
  -97,  107,   61,  538,    0,    0,    0,   93,  -51,  119,
  -38,  229,    0,    0,    0,    0,    0,  447,  431,    0,
    0,    0,   93,  434,  194,  490, -187,  235,  241,  149,
  244,  449,  452,    0,  255,    0,  473,  278,  907, -180,
  253,  262,  261,  -38,  479,    0,  -44,  265,   93,  843,
    0,  843,    0,    0,   95,    0,    0,  267,  106,    0,
  268,    0,    0,  269,  175,    0,  175,    0,    0,  113,
    0,    0,  466,  273,  554,  468,   44,  275,  569,  584,
    0,    0,    0,    0,    0,    0,  -38,    0,  342,  924,
    0,    0,  -44,  476,   22,  860,  280,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   93,   89,  282,  498,
    0,    0,  287,    0,    0,    0,  487,  -44,  128,  603,
    0,    0,  292,  294,    0,   89,  493,   93,  495,  622,
  639, -121,  295,  303,  501,    0,  520,    0,  306,  -44,
  307,   93,  860,    0,  860,    0,    0,  120,    0,    0,
    0,   89,    0,  504,   68,  311,  658,  674,    0,    0,
    0,   93,  554,  310,  530,    0,    0,  315,  317,    0,
  554,  533,    0,  321,  554,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  581,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  133,   -1,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   26,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  459,    0,    0,
    0,    0,    0,   48,    0,    0,    0,  139,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  944,  -33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  524,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  460,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -80,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -69,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  524,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -59,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    6,    0,    0,  454,  -55, -163, -160,   -3,
 1068,  -14,    0,    0,    0,  429, -144,   96,    0,  -32,
  240,  300,    0, -123,    0,   16,    0,  371,  111,    0,
  978,    0,  403,  274,    0, -186,  496,  556,    0,  494,
    0,    0,    0,  325,    0,    0,  302,    0,    0,    0,
  223, -174,  866,  -49,  -89,    0,  352, -279,  783,    0,
    0,    0,  565, -333,  521,  301,
};
final static int YYTABLESIZE=1318;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
  118,  110,   47,   85,  115,  170,  126,  180,   83,   83,
  117,   20,   34,   35,   70,   35,   79,   63,  188,  117,
   66,  251,   35,   78,   49,    5,  172,  193,  259,    1,
  213,   35,   40,  194,   94,   54,   95,  260,   67,  182,
   77,  182,  227,  182,   57,   68,  180,  359,  181,  255,
    9,   96,   93,   97,   13,   72,   73,  182,  182,  182,
  182,   39,   70,  174,   20,  213,   40,  252,  304,   16,
  262,  414,   40,  133,  305,  319,  166,  199,  374,  419,
  203,  320,    9,   16,  358,   75,   13,  424,  227,  186,
  186,  427,  186,  284,  186,  143,  389,   78,  100,  101,
  142,  282,   94,   94,   95,   95,  186,   16,  413,  213,
  137,  106,   76,  268,  301,  303,  107,  130,  149,  281,
   78,   89,  411,  237,  186,  136,  221,   79,   16,   70,
  213,  213,  129,  239,  397,   94,  329,   95,  330,  255,
  398,  255,  215,  227,  227,  146,   86,  108,    5,   52,
  147,   40,  155,  332,  105,  213,  249,  213,  369,   96,
   93,   97,  275,  276,  335,   20,  210,   91,  284,  284,
  227,  342,  227,   91,  202,  101,  111,  215,  410,  112,
  348,  101,  213,  226,  213,  213,  111,   61,   60,  309,
  346,   94,  111,   95,  227,  227,  121,   20,   70,  113,
  274,  348,  121,  238,  116,  293,  244,  308,  124,  153,
  125,  210,   53,  213,   16,   62,   82,  287,  407,  122,
  408,  215,   79,  139,  169,  348,  180,  180,   33,  266,
   33,  213,  109,   16,   46,  348,  348,   33,  324,   77,
   70,   48,  215,  215,   84,   38,   33,  326,  348,    9,
  348,  119,   53,   13,  182,  144,   11,  213,  182,  182,
  348,   56,  348,  348,   90,   91,   92,  215,  348,  215,
  214,  182,  182,  182,  198,   16,  348,  123,   37,   38,
  348,  363,  228,  296,   37,   38,    4,  261,    5,  154,
  101,  165,    6,  367,  215,    8,  215,  215,   11,   12,
    4,  127,  208,  186,  132,  214,    6,    7,  209,    8,
    9,   10,   11,   12,   13,  148,  300,   16,  379,  253,
   14,  347,  134,  135,  223,  215,  345,  138,  228,  128,
    6,    7,  209,    8,    9,   10,  225,   12,   13,  141,
  404,  145,  347,  215,   14,    4,   50,  208,   51,  214,
  331,    6,    7,  209,    8,    9,   10,   11,   12,   13,
  240,  334,  280,   37,   38,   14,  347,  150,  341,  215,
  214,  214,   90,   91,   92,  409,  347,  347,  288,  289,
  151,   16,  160,  228,  228,   58,   59,  380,  381,  347,
  164,  347,   89,   89,  101,  214,   83,  214,   88,   88,
  315,  347,  167,  347,  347,  168,  162,  163,  173,  347,
  228,  169,  228,  175,  216,  176,  178,  347,  177,  187,
  349,  347,  214,  179,  214,  214,  229,   62,  184,  190,
  185,  223,  189,  224,  228,  228,  196,    6,   82,  209,
    8,  349,  197,  225,   12,  200,  204,  205,  206,  216,
    4,  242,  208,  214,  207,  246,    6,    7,  209,    8,
    9,   10,   11,   12,   13,  349,  364,  247,  248,  250,
   14,  214,  229,  256,  217,  349,  349,  257,  258,  263,
  264,  272,  273,  265,  269,  294,  230,   78,  349,  297,
  349,  299,    4,  216,    5,  276,  307,  214,    6,  310,
  349,    8,  349,  349,   11,   12,   16,  311,  349,  217,
  312,  313,  314,  289,  216,  216,  349,  322,  323,  325,
  349,  327,  333,  336,  343,  337,  357,  229,  229,   16,
  344,  360,  230,  368,  223,  372,  224,  375,  376,  216,
    6,  216,  209,    8,  377,  378,  225,   12,  387,  388,
   16,  390,  392,  217,  229,  381,  229,  232,  400,  401,
  402,  403,  412,  405,  350,  420,  216,  415,  216,  216,
  421,  422,  423,  425,  217,  217,  426,   16,  229,  229,
    1,   44,  161,  182,   55,  350,  195,  230,  230,  236,
  340,  271,  321,   16,   88,  157,  161,  216,  223,  217,
  224,  217,  306,  232,    6,  399,  209,    8,   16,  350,
  225,   12,  302,  295,  230,  216,  230,  114,  152,  350,
  350,    0,    0,   16,  351,    0,  217,    0,  217,  217,
    0,    0,  350,  158,  350,  236,    0,    0,  230,  230,
    0,  216,   16,    0,  350,  351,  350,  350,    0,    0,
    0,    0,  350,    0,    0,    0,    0,  217,  232,  232,
  350,   16,  283,    0,  350,    0,    0,    0,    0,  351,
    0,    0,    0,    0,    0,  217,  346,    0,   16,  351,
  351,    0,    0,    0,    0,  232,    0,  232,    0,    0,
  236,  236,  351,  361,  351,  352,    0,   16,    0,    0,
    0,  217,    0,    0,  351,    0,  351,  351,  362,  232,
  232,    0,  351,   16,    0,    0,  352,  236,    0,  236,
  351,    0,  270,    4,  351,    5,    0,  385,   16,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
  352,  236,  236,   14,  393,   16,    4,    0,  208,    0,
  352,  352,    6,    7,  209,    8,    9,   10,   11,   12,
   13,  395,    0,  352,    0,  352,   14,    4,    0,    5,
   16,    0,    0,    6,    0,  352,    8,  352,  352,   11,
   12,    0,  416,  352,    0,    0,    0,    0,    0,   16,
    0,  352,    0,    0,    4,  352,  208,    0,  417,    0,
    6,    7,  209,    8,    9,   10,   11,   12,   13,    0,
  223,   15,  345,    0,   14,   16,    6,    7,  209,    8,
    9,   10,  225,   12,   13,    4,    0,  208,  201,    0,
   14,    6,    7,  209,    8,    9,   10,   11,   12,   13,
    4,   16,  208,    0,    0,   14,    6,    7,  209,    8,
    9,   10,   11,   12,   13,   69,    0,    0,    0,  223,
   14,  345,    0,   16,    0,    6,    7,  209,    8,    9,
   10,  225,   12,   13,  131,    0,    0,    0,  223,   14,
  345,    0,   16,    0,    6,    7,  209,    8,    9,   10,
  225,   12,   13,    0,    0,  223,    0,  345,   14,   16,
  183,    6,    7,  209,    8,    9,   10,  225,   12,   13,
    0,    0,    0,    0,  223,   14,  345,    0,   16,    0,
    6,    7,  209,    8,    9,   10,  225,   12,   13,    0,
  223,    0,  345,    0,   14,    0,    6,    7,  209,    8,
    9,   10,  225,   12,   13,    4,   16,    5,  245,    0,
   14,    6,    7,    0,    8,    9,   10,   11,   12,   13,
    0,    0,    4,   16,    5,   14,    0,    0,    6,    7,
    0,    8,    9,   10,   11,   12,   13,    0,    0,   16,
    0,    0,   14,    0,  181,    0,  181,    4,  181,    5,
    0,    0,    0,    6,    7,    0,    8,    9,   10,   11,
   12,   13,  181,  181,  181,  181,    4,   14,    5,    0,
   16,    0,    6,    7,    0,    8,    9,   10,   11,   12,
   13,    0,    0,    0,   16,    0,   14,    0,    0,  317,
    0,    0,    4,    0,    5,    0,    0,    0,    6,    7,
  285,    8,    9,   10,   11,   12,   13,    0,  366,    0,
    0,    0,   14,    0,    0,  298,    0,    0,  223,    0,
  224,    0,  155,    0,    6,    7,  209,    8,    9,   10,
  225,   12,   13,    0,    0,    0,    0,    0,   14,    0,
    4,  328,    5,    0,    0,   71,    6,    7,    0,    8,
    9,   10,   11,   12,   13,  243,    0,    0,    0,    4,
   14,  208,    0,    0,    0,    6,    7,  209,    8,    9,
   10,   11,   12,   13,    0,    0,  223,    0,  345,   14,
    0,    0,    6,    7,  209,    8,    9,   10,  225,   12,
   13,    0,    0,   71,    0,    4,   14,    5,    0,  373,
    0,    6,    7,    0,    8,    9,   10,   11,   12,   13,
    0,    0,    0,    0,    0,   14,    0,    0,    0,    0,
  391,    0,    0,  223,  234,  224,    0,  156,  159,    6,
    0,  209,    8,    0,  406,  225,   12,    0,    0,    0,
  223,    0,  224,    0,    0,    0,    6,    0,  209,    8,
    0,    0,  225,   12,  418,    0,    4,    0,    5,  181,
   71,    0,    6,  181,  181,    8,    0,    0,   11,   12,
  267,  371,    0,    0,    0,    0,  181,  181,  181,    0,
    0,  156,  191,    0,    0,  191,    0,    4,    0,    5,
    0,    0,    0,    6,    0,  386,    8,    0,    0,   11,
   12,    4,    0,    5,    0,  394,  396,    6,    0,    0,
    8,    0,    0,   11,   12,    0,    0,    0,  371,  241,
  371,    0,  241,    0,    0,  316,  318,    0,    0,   71,
    0,    0,  386,  386,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   71,    0,    0,    0,    0,  365,  365,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   56,   41,   40,   36,   54,   41,   62,   41,   61,   61,
   58,   15,   58,   61,   18,   61,   31,   59,  142,   58,
   15,  208,   61,   41,   40,    0,  116,  256,   58,  257,
  175,   61,   45,  262,   43,   40,   45,  224,  272,   41,
  257,   43,  187,   45,   40,  123,  256,  327,  258,  210,
  267,   60,   61,   62,  271,  256,  257,   59,   60,   61,
   62,   40,   66,  119,   68,  210,   45,   40,  256,   40,
   40,  405,   45,   68,  262,  256,   41,   59,  358,  413,
  170,  262,  267,   40,   41,  256,  271,  421,  233,   42,
   43,  425,   45,  254,   47,   80,  376,   41,  260,  261,
   44,   41,   43,   43,   45,   45,   59,   40,   41,  254,
   44,   42,  256,  237,  275,  276,   47,   44,   59,   59,
   41,  258,  402,   44,  139,   59,  176,  142,   40,  133,
  275,  276,   59,  189,  256,   43,  300,   45,  302,  300,
  262,  302,  175,  288,  289,  256,   36,  256,  123,   59,
  261,   45,  123,   59,   41,  300,  206,  302,  345,   60,
   61,   62,  260,  261,   59,  169,  123,  256,  329,  330,
  315,   59,  317,  262,  169,  256,   40,  210,   59,  256,
  325,  262,  327,  187,  329,  330,  256,   58,   59,   41,
  123,   43,  262,   45,  339,  340,  256,  201,  202,   59,
  250,  346,  262,  188,   41,  261,  201,   59,  256,   99,
  258,  123,  257,  358,   40,  257,  269,  269,  393,   59,
  395,  254,  237,   58,  260,  370,  260,  261,  276,  233,
  276,  376,  272,   40,  272,  380,  381,  276,  294,  257,
  244,  257,  275,  276,  257,  258,  276,  297,  393,  267,
  395,  257,  257,  271,  256,   40,  269,  402,  260,  261,
  405,  257,  407,  408,  273,  274,  275,  300,  413,  302,
  175,  273,  274,  275,  256,   40,  421,  257,  257,  258,
  425,  337,  187,  268,  257,  258,  257,  257,  259,  260,
  261,  256,  263,  343,  327,  266,  329,  330,  269,  270,
  257,  256,  259,  256,  256,  210,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  256,  123,   40,  368,  209,
  277,  325,  256,  257,  257,  358,  259,  256,  233,  256,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  257,
  390,   58,  346,  376,  277,  257,  256,  259,  258,  254,
  256,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  125,  256,  252,  257,  258,  277,  370,   59,  256,  402,
  275,  276,  273,  274,  275,  256,  380,  381,  260,  261,
   41,   40,  262,  288,  289,  256,  257,  260,  261,  393,
   59,  395,  260,  261,  261,  300,   61,  302,  260,  261,
  123,  405,  256,  407,  408,  258,  106,  107,  258,  413,
  315,  260,  317,   41,  175,   59,   59,  421,  256,  123,
  325,  425,  327,  256,  329,  330,  187,  257,  256,  258,
  257,  257,  257,  259,  339,  340,   59,  263,  269,  265,
  266,  346,   59,  269,  270,   59,   59,   59,   59,  210,
  257,  256,  259,  358,  258,   59,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  370,  125,  256,  256,   58,
  277,  376,  233,  256,  175,  380,  381,  256,   59,  256,
  256,  256,   59,  125,  258,  257,  187,   41,  393,   59,
  395,   58,  257,  254,  259,  261,  256,  402,  263,  256,
  405,  266,  407,  408,  269,  270,   40,   59,  413,  210,
   59,  257,   40,  261,  275,  276,  421,  256,  258,   41,
  425,  257,  256,  256,   59,  257,   59,  288,  289,   40,
  258,  257,  233,   58,  257,  256,  259,  256,   41,  300,
  263,  302,  265,  266,  258,   59,  269,  270,  257,  256,
   40,   59,   58,  254,  315,  261,  317,  187,  256,   59,
   41,  256,   59,  257,  325,  256,  327,  257,  329,  330,
   41,  257,  256,   41,  275,  276,  256,   40,  339,  340,
    0,  123,   59,  130,  125,  346,  158,  288,  289,  187,
  317,  125,  291,   40,   39,  100,  103,  358,  257,  300,
  259,  302,  278,  233,  263,  383,  265,  266,   40,  370,
  269,  270,  123,  262,  315,  376,  317,   53,   98,  380,
  381,   -1,   -1,   40,  325,   -1,  327,   -1,  329,  330,
   -1,   -1,  393,  123,  395,  233,   -1,   -1,  339,  340,
   -1,  402,   40,   -1,  405,  346,  407,  408,   -1,   -1,
   -1,   -1,  413,   -1,   -1,   -1,   -1,  358,  288,  289,
  421,   40,  125,   -1,  425,   -1,   -1,   -1,   -1,  370,
   -1,   -1,   -1,   -1,   -1,  376,  123,   -1,   40,  380,
  381,   -1,   -1,   -1,   -1,  315,   -1,  317,   -1,   -1,
  288,  289,  393,  125,  395,  325,   -1,   40,   -1,   -1,
   -1,  402,   -1,   -1,  405,   -1,  407,  408,  125,  339,
  340,   -1,  413,   40,   -1,   -1,  346,  315,   -1,  317,
  421,   -1,  256,  257,  425,  259,   -1,  125,   40,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
  370,  339,  340,  277,  123,   40,  257,   -1,  259,   -1,
  380,  381,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  123,   -1,  393,   -1,  395,  277,  257,   -1,  259,
   40,   -1,   -1,  263,   -1,  405,  266,  407,  408,  269,
  270,   -1,  125,  413,   -1,   -1,   -1,   -1,   -1,   40,
   -1,  421,   -1,   -1,  257,  425,  259,   -1,  125,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   -1,
  257,  123,  259,   -1,  277,   40,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  257,   -1,  259,  123,   -1,
  277,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  257,   40,  259,   -1,   -1,  277,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  125,   -1,   -1,   -1,  257,
  277,  259,   -1,   40,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  125,   -1,   -1,   -1,  257,  277,
  259,   -1,   40,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,   -1,  257,   -1,  259,  277,   40,
  125,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,   -1,  257,  277,  259,   -1,   40,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   -1,
  257,   -1,  259,   -1,  277,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  257,   40,  259,  125,   -1,
  277,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
   -1,   -1,  257,   40,  259,  277,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,   40,
   -1,   -1,  277,   -1,   41,   -1,   43,  257,   45,  259,
   -1,   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,   59,   60,   61,   62,  257,  277,  259,   -1,
   40,   -1,  263,  264,   -1,  266,  267,  268,  269,  270,
  271,   -1,   -1,   -1,   40,   -1,  277,   -1,   -1,  123,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
  258,  266,  267,  268,  269,  270,  271,   -1,  125,   -1,
   -1,   -1,  277,   -1,   -1,  273,   -1,   -1,  257,   -1,
  259,   -1,  123,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,   -1,   -1,   -1,   -1,  277,   -1,
  257,  299,  259,   -1,   -1,   18,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,  125,   -1,   -1,   -1,  257,
  277,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,  257,   -1,  259,  277,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   -1,   66,   -1,  257,  277,  259,   -1,  357,
   -1,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,
  378,   -1,   -1,  257,  187,  259,   -1,  100,  101,  263,
   -1,  265,  266,   -1,  392,  269,  270,   -1,   -1,   -1,
  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  265,  266,
   -1,   -1,  269,  270,  412,   -1,  257,   -1,  259,  256,
  133,   -1,  263,  260,  261,  266,   -1,   -1,  269,  270,
  233,  346,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,
   -1,  154,  155,   -1,   -1,  158,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,   -1,  370,  266,   -1,   -1,  269,
  270,  257,   -1,  259,   -1,  380,  381,  263,   -1,   -1,
  266,   -1,   -1,  269,  270,   -1,   -1,   -1,  393,  192,
  395,   -1,  195,   -1,   -1,  288,  289,   -1,   -1,  202,
   -1,   -1,  407,  408,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  244,   -1,   -1,   -1,   -1,  339,  340,
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
"declarativas : tipo lista_de_variables ';'",
"declarativas : tipo lista_de_variables error",
"declarativas : tipo error",
"declarativas : declaracion_func",
"declarativas : declaracion_const",
"declarativas : sentencia_when",
"bloque_ejecutables : bloque_ejecutables ejecutables",
"bloque_ejecutables : ejecutables",
"ejecutables : asignacion",
"ejecutables : salida",
"ejecutables : sentencia_If",
"ejecutables : expresion_For",
"ejecutables : sentencia_BREAK error",
"ejecutables : sentencia_CONTINUE error",
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
"ret_fun : Return '(' expresion ')' ';'",
"ret_fun : Return expresion ')' ';' error",
"ret_fun : Return '(' expresion ';' error",
"ret_fun : Return expresion ';' error",
"ret_fun : Return '(' expresion ')' error",
"cuerpo_fun : bloque_sentencias_funcion",
"ejecutables_funcion : asignacion",
"ejecutables_funcion : sentencia_if_funcion",
"ejecutables_funcion : salida",
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

//#line 455 "gramatica.y"

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


//#line 840 "Parser.java"
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
{ sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 23:
//#line 72 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
break;
case 24:
//#line 73 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el identificador de variable"); }
break;
case 28:
//#line 80 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 29:
//#line 84 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 34:
//#line 91 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 35:
//#line 92 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 38:
//#line 97 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores."); }
break;
case 39:
//#line 100 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" ); }
break;
case 40:
//#line 101 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 42:
//#line 105 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 44:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 45:
//#line 112 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")");
 						yyval = val_peek(1);}
break;
case 46:
//#line 117 "gramatica.y"
{yyval = val_peek(0);}
break;
case 48:
//#line 119 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 50:
//#line 123 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   yyval = new ParserVal(sintactico.crearNodoControl("return",val_peek(2)));}
break;
case 51:
//#line 125 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 52:
//#line 126 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 53:
//#line 127 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 128 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 61:
//#line 139 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 62:
//#line 140 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 63:
//#line 144 "gramatica.y"
{
													ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
													yyval = modificado;
											 	}
break;
case 64:
//#line 148 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 65:
//#line 151 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 66:
//#line 155 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 67:
//#line 159 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 68:
//#line 160 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 69:
//#line 169 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 70:
//#line 170 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 71:
//#line 171 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 72:
//#line 175 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(3).ival));
						yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
					   }
break;
case 73:
//#line 178 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 74:
//#line 179 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", val_peek(3), val_peek(1)));}
break;
case 75:
//#line 183 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), val_peek(0)));}
break;
case 76:
//#line 184 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 77:
//#line 187 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 78:
//#line 188 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 79:
//#line 189 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 80:
//#line 190 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 81:
//#line 191 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 82:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 83:
//#line 197 "gramatica.y"
{ 	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
										sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 84:
//#line 199 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 85:
//#line 200 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 86:
//#line 204 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cond",val_peek(1)));}
break;
case 87:
//#line 205 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 88:
//#line 206 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 89:
//#line 207 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 90:
//#line 211 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(1), val_peek(0)));}
break;
case 91:
//#line 212 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(0), null));}
break;
case 92:
//#line 213 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 93:
//#line 217 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 94:
//#line 218 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 95:
//#line 222 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 96:
//#line 223 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 97:
//#line 226 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 										   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 98:
//#line 228 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 99:
//#line 229 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 100:
//#line 232 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 101:
//#line 233 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 102:
//#line 234 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 103:
//#line 237 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 104:
//#line 238 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 105:
//#line 241 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 106:
//#line 242 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 107:
//#line 245 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 											   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 108:
//#line 247 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 109:
//#line 248 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 110:
//#line 251 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 111:
//#line 252 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 112:
//#line 253 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 113:
//#line 256 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 114:
//#line 257 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 115:
//#line 260 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 116:
//#line 261 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 117:
//#line 264 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
												  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 118:
//#line 266 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 119:
//#line 267 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 120:
//#line 270 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 121:
//#line 271 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 122:
//#line 272 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 123:
//#line 275 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 124:
//#line 276 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 125:
//#line 279 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 126:
//#line 280 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 127:
//#line 286 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
									yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 128:
//#line 288 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 129:
//#line 289 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 130:
//#line 293 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 131:
//#line 294 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 132:
//#line 295 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 133:
//#line 302 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
									}
break;
case 134:
//#line 305 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 135:
//#line 306 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 136:
//#line 307 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 137:
//#line 308 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 138:
//#line 312 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));}
break;
case 139:
//#line 315 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 140:
//#line 318 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("cond", new ParserVal(sintactico.crearNodo(val_peek(1).sval,identificador,constante))));}
break;
case 141:
//#line 323 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 142:
//#line 324 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 143:
//#line 327 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 144:
//#line 328 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 151:
//#line 338 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 152:
//#line 339 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 154:
//#line 345 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 155:
//#line 346 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 156:
//#line 350 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										}
break;
case 157:
//#line 353 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 158:
//#line 354 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 159:
//#line 355 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 160:
//#line 356 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 161:
//#line 359 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));}
break;
case 162:
//#line 364 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo(val_peek(1).sval,new ParserVal(sintactico.crearHoja(val_peek(0).ival)),null))));}
break;
case 170:
//#line 374 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 171:
//#line 378 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 172:
//#line 379 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 173:
//#line 387 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
				yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 174:
//#line 389 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                		yyval = new ParserVal(sintactico.crearNodoControl("break", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 175:
//#line 391 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 176:
//#line 394 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
						yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 177:
//#line 396 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   				yyval = new ParserVal(sintactico.crearNodoControl("continue", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 178:
//#line 398 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 179:
//#line 399 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 180:
//#line 405 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 181:
//#line 409 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 183:
//#line 414 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 184:
//#line 415 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 186:
//#line 420 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));}
break;
case 187:
//#line 421 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
                  }
break;
case 188:
//#line 427 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(1).ival));
                    }
break;
case 189:
//#line 434 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 190:
//#line 435 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 191:
//#line 436 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 192:
//#line 437 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 193:
//#line 438 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 194:
//#line 439 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 195:
//#line 443 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 196:
//#line 447 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 1688 "Parser.java"
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
