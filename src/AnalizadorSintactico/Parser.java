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
    1,    1,    1,    2,    2,    1,    3,    2,    3,    3,
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
    4,   81,    0,   23,   38,   22,    0,   42,    0,    0,
   41,    0,   46,    0,    0,   76,    0,   73,   72,   74,
   86,    0,    0,    0,    0,   94,    0,    0,   96,    0,
   90,  183,  184,   80,    0,    0,    0,  140,    0,    0,
    0,    0,    0,    0,    0,    0,  178,  177,   14,   13,
   12,   11,    3,   37,   43,    0,    0,    0,   75,   29,
    0,   85,    0,    0,   83,   79,   78,   77,   82,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  142,
    0,  145,  146,  147,  149,  150,    0,  148,  133,    0,
  138,    0,    0,    0,   68,   56,   58,    0,    0,    0,
   60,    0,   67,   57,   59,    0,   47,    0,   93,   28,
   84,   95,    0,    0,  127,  129,  128,    0,    0,    0,
    0,    0,    0,   16,  151,  152,    0,    0,    0,    0,
    0,   61,   62,   45,   66,   65,    0,    0,  131,  130,
  132,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   17,    0,  139,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   48,    0,    0,    0,    0,  104,
    0,  106,   99,    0,  100,  102,    0,    0,   53,    0,
  141,  162,    0,    0,  114,    0,  116,  109,    0,  110,
  112,    0,    0,    0,    0,    0,    0,    0,    0,   98,
   97,   52,   54,   50,   51,    0,   64,    0,    0,  108,
  107,    0,    0,    0,    0,    0,  163,  164,  167,  168,
  166,  165,  169,  155,  156,    0,    0,    0,    0,  103,
  105,    0,  113,   63,  115,    0,    0,    0,    0,  171,
  170,    0,    0,  135,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  172,    0,  134,    0,    0,    0,
    0,    0,  124,    0,  126,  119,    0,  120,  122,  154,
    0,  136,    0,    0,    0,    0,    0,  118,  117,  137,
    0,    0,    0,    0,  123,  125,    0,    0,  158,    0,
    0,  157,    0,    0,  159,  160,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   64,   65,   36,  253,  210,  211,
   21,   22,   74,   23,   24,  191,   25,   26,   27,   28,
   29,   30,   31,   80,  140,   81,  230,  217,   41,  232,
  337,  234,  352,  338,   87,   42,  102,   43,  103,  104,
  218,  276,  277,  278,  289,  290,  291,  353,  381,  382,
  383,  369,  354,   55,  171,   32,  120,  219,   98,  121,
  221,  285,   99,  355,   44,   45,
};
final static short yysindex[] = {                      -226,
    0,    0,  785,  -19,   21,  -38,  -22,   73,    0,  -16,
   18,   63,    0,  -39,  953, -221,  -64,  802,    0,    0,
    0,  -84,    0,    0,    0,    0,    0,    0, -188, -176,
    9,    0,    0,  -45,    0,  -42,    0,    0,   65, -175,
   17,  -67,   52,   28,    0, -156,  -33,   74, -133,    0,
   83,    0,   35, -105,  118,  -17,  -95,    0,  122,    0,
  -73,  -48,  -65,   32,    0,  827,  -61,  953,    0,    0,
    0,    0,    0,   57,    0,    0,  -54,  153,  -34,   71,
    0,  213,    0,  216, -157,   39,  223,  263,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   65,   65,  421,
  205,   53,   69,    0,    0,   65,   65,  282,  -12,   99,
    0,    0,    0,  109,  -30,  110,  311,  116,  -17,  343,
  328,  134,  346,    0,  151, -228,    0,    0,    0,  145,
    0,    0,  854,    0,    0,    0,  154,    0,  -97,  285,
    0, -100,    0,  155,  144,    0,  152,    0,    0,    0,
    0,   28,  143,  260,  314,    0, -229,  314,    0,  355,
    0,    0,    0,    0,  360,   50,  363,    0,  869,  110,
  364,  373,  376,  179,  335, -105,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  888,  106,  -17,    0,    0,
  243,    0,  187,  989,    0,    0,    0,    0,    0,  953,
  907,  379,  188,  190, -105,  391,   21,   41,  922,    0,
  194,    0,    0,    0,    0,    0,  197,    0,    0,  395,
    0,  -13,   21,   45,    0,    0,    0,  200,  204,  338,
    0,  888,    0,    0,    0, -100,    0,  208,    0,    0,
    0,    0,  129,  220,    0,    0,    0,  413, -105,  -57,
   65,  120,  550,    0,    0,    0,  143,   -7,  -47,  -17,
  221,    0,    0,    0,    0,    0,  436,  414,    0,    0,
    0,  143,  438,  468,  594, -213,  236,  248,  137,  249,
  440,  448,    0,  254,    0,  474,  161,  182, -210,  257,
  259,  264,  -17,  486,    0, -105,  271,  143,  922,    0,
  922,    0,    0,   61,    0,    0,  276,   78,    0,  278,
    0,    0,  274,  997,    0,  997,    0,    0,   79,    0,
    0,  477,  281,  620,  481,   94,  284,  636,  655,    0,
    0,    0,    0,    0,    0,  -17,    0,  503,  975,    0,
    0, -105,  488,   21,  938,  294,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  143,  335,  296,  513,    0,
    0,  297,    0,    0,    0,  497, -105,   85,  674,    0,
    0,  300,  302,    0,  335,  501,  143,  505,  700,  717,
 -154,  306,  312,  510,    0,  531,    0,  319, -105,  324,
  143,  938,    0,  938,    0,    0,   81,    0,    0,    0,
  335,    0,  523,  224,  351,  741,  757,    0,    0,    0,
  143,  620,  329,  569,    0,    0,  356,  358,    0,  620,
  574,    0,  361,  620,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  621,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   88,  -24,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   26,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  499,    0,    0,
    0,    0,    0,   47,    0,    0,    0,  119,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -145,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   12,  -35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  561,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  502,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -143,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -117,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  561,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -102,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -1,    0,    0,  496,  -55, -112,  378,   -3,
 1176,   16,    0,    0,    0,  475,  -68,  141,    0,  -32,
  250,  384,    0, -110,    0,  -58,    0,  432,    4,    0,
  146,    0, -163,  316,    0, -198,  535,  597,    0,  536,
    0,    0,    0,  366,    0,    0,  348,    0,    0,    0,
  258, -202,  217,  -49,  -82,    0,  380, -270,  701,    0,
    0,    0,  592, -276,  548,  275,
};
final static int YYTABLESIZE=1419;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
  118,   47,   40,   85,  115,  180,  126,  110,  250,  117,
  170,   20,   35,   66,   70,   83,  182,   49,  182,   63,
  182,  143,  235,   54,  259,    5,  192,  180,  166,  181,
    1,  187,  193,  172,  182,  182,  182,  182,   34,   86,
  117,   35,  303,   35,  258,  318,   79,   35,  304,   78,
   67,  319,  181,   83,  181,  358,  181,   57,   68,   94,
   39,   95,   70,  174,   20,   40,  133,   75,  235,  106,
  181,  181,  181,  181,  107,  130,   96,   93,   97,   76,
  251,   94,   89,   95,  261,   40,  373,  202,  186,  186,
  129,  186,  105,  186,   96,   93,   97,  149,  146,  108,
  137,  396,  153,  147,  388,  186,  212,  397,  198,   40,
   91,   78,  101,  111,  142,  136,   91,  226,  101,  331,
   61,   60,  112,  235,  235,  267,  220,  413,  237,   70,
  410,   52,  238,   16,  357,  418,  334,  341,  111,  409,
  212,  113,  214,  423,  111,  368,   78,  426,    5,  236,
  235,   53,  235,  121,  185,  248,   77,   79,  116,  121,
  281,  119,   94,  226,   95,   20,    9,  201,   16,    9,
   13,   72,   73,   13,  235,  235,  214,  308,  280,   94,
  122,   95,  225,  123,  212,   94,  328,   95,  329,  406,
  127,  407,  100,  101,  132,  307,   20,   70,  243,  273,
   16,  138,  274,  275,  292,  212,  212,  124,  295,  125,
  139,  252,  287,  288,   84,   38,  209,   62,  226,  226,
  214,   16,  141,   82,  180,  180,   11,   33,  265,  169,
  212,  182,  212,   46,   48,  182,  182,  323,  109,   70,
   53,  214,  214,  165,   16,  226,  325,  226,  182,  182,
  182,   79,  144,  270,  279,  347,   33,  212,   33,  212,
  212,  286,   33,   16,  412,   77,  214,  181,  214,  226,
  226,  181,  181,  145,   56,    9,  347,   37,   38,   13,
  362,  150,   16,  314,  181,  181,  181,  128,  212,   90,
   91,   92,  366,  214,  148,  214,  214,   37,   38,   16,
  347,  260,  186,  151,  316,  197,  212,   90,   91,   92,
  347,  347,  134,  135,  160,  213,  330,  378,   58,   59,
  346,   37,   38,  347,  214,  347,  227,  158,   50,  101,
   51,  233,  212,  333,  340,  347,  408,  347,  347,  403,
  164,  346,  214,  347,  379,  380,  345,   89,   89,  213,
    4,  347,  207,   16,  167,  347,    6,    7,  208,    8,
    9,   10,   11,   12,   13,  346,  168,  239,  214,  169,
   14,   83,  227,  173,   16,  346,  346,  266,   88,   88,
  162,  163,  155,  175,  269,    4,  176,    5,  346,  177,
  346,    6,    7,  213,    8,    9,   10,   11,   12,   13,
  346,   62,  346,  346,  178,   14,  179,  186,  346,  189,
  184,  188,   82,  195,  213,  213,  346,  222,  196,  223,
  346,  199,  203,    6,  215,  208,    8,  227,  227,  224,
   12,  204,  315,  317,  205,  228,  206,  245,  222,  213,
  223,  213,  241,  246,    6,  247,  208,    8,  249,  255,
  224,   12,  256,  257,  227,  262,  227,  209,  215,  263,
   16,    4,  264,    5,  348,  268,  213,    6,  213,  213,
    8,  272,  296,   11,   12,  271,   78,  293,  227,  227,
  222,  228,  344,  364,  364,  348,    6,    7,  208,    8,
    9,   10,  224,   12,   13,  298,  275,  213,  310,    4,
   14,    5,  215,  306,  309,    6,  311,   16,    8,  348,
  312,   11,   12,  313,  321,  213,    4,  288,    5,  348,
  348,  322,    6,  215,  215,    8,  324,  326,   11,   12,
  336,  332,  348,  335,  348,  342,  228,  228,  343,  356,
  359,  213,   16,  155,  348,  367,  348,  348,  215,  371,
  215,  374,  348,  375,  376,  377,  386,  387,  216,  389,
  348,  370,  391,  228,  348,  228,  380,  399,  400,  229,
    4,  401,    5,  349,  402,  215,    6,  215,  215,    8,
  404,  411,   11,   12,  419,  385,  254,  228,  228,   16,
  299,    4,  216,  207,  349,  393,  395,    6,    7,  208,
    8,    9,   10,   11,   12,   13,  215,  414,  370,  420,
  370,   14,  421,  422,  424,  229,  425,  231,  349,  161,
    1,   44,  385,  385,  215,  182,   55,  363,  349,  349,
  283,  339,  194,   16,  157,   88,  216,  320,  161,  398,
  294,  349,  305,  349,  114,  152,    0,    0,    0,    0,
  215,  300,  302,  349,    0,  349,  349,  216,  216,   16,
    0,  349,    0,  231,    0,    0,    0,    0,    0,  349,
  229,  229,    0,  349,  282,   16,  254,    4,  254,    5,
  154,  101,  216,    6,  216,    0,    8,    0,    0,   11,
   12,    0,    0,    0,   16,    0,    0,  229,    0,  229,
    0,    0,    0,    0,    0,  283,  283,  350,    0,  216,
    0,  216,  216,   16,    0,    0,  301,    0,  231,  231,
    0,  229,  229,    0,    4,    0,  207,    0,  350,    0,
    6,    7,  208,    8,    9,   10,   11,   12,   13,   16,
  216,    0,  345,    0,   14,  231,    0,  231,    0,    0,
    0,    0,  350,    0,    0,  351,   16,    0,  216,  222,
  360,  223,  350,  350,    0,    6,    0,  208,    8,  231,
  231,  224,   12,    0,    0,  350,  351,  350,    0,  361,
   16,    0,    0,    0,  216,    0,    0,  350,    0,  350,
  350,    0,    0,    0,    0,  350,   16,    0,  384,    0,
  351,    0,    0,  350,    0,    0,    4,  350,  207,    0,
  351,  351,    6,    7,  208,    8,    9,   10,   11,   12,
   13,    0,  392,  351,   16,  351,   14,    0,    0,    0,
    0,    0,    0,    0,    0,  351,    0,  351,  351,  394,
    0,   16,    0,  351,    0,    0,    0,    0,    0,    0,
    4,  351,  207,    0,    0,  351,    6,    7,  208,    8,
    9,   10,   11,   12,   13,  415,   16,    0,    0,    0,
   14,    0,    0,    0,    0,    0,  222,    0,  344,    0,
    0,  416,    6,    7,  208,    8,    9,   10,  224,   12,
   13,    0,    4,   16,  207,    0,   14,    0,    6,    7,
  208,    8,    9,   10,   11,   12,   13,   15,   16,    0,
    0,    4,   14,  207,    0,    0,    0,    6,    7,  208,
    8,    9,   10,   11,   12,   13,   69,   16,    0,    0,
  222,   14,  344,    0,    0,    0,    6,    7,  208,    8,
    9,   10,  224,   12,   13,    0,   16,    0,    0,    0,
   14,  131,    0,    0,    0,    0,  222,  284,  344,    0,
    0,   16,    6,    7,  208,    8,    9,   10,  224,   12,
   13,    0,  297,  222,    0,  344,   14,   16,  183,    6,
    7,  208,    8,    9,   10,  224,   12,   13,    0,    0,
    0,  200,   16,   14,    0,    0,    0,  222,  327,  344,
    0,    0,    0,    6,    7,  208,    8,    9,   10,  224,
   12,   13,    0,  222,   16,  344,    0,   14,    0,    6,
    7,  208,    8,    9,   10,  224,   12,   13,   16,    0,
    0,  244,    0,   14,    0,    0,   16,    0,    0,    0,
    0,    4,    0,    5,    0,    0,    0,    6,    7,    0,
    8,    9,   10,   11,   12,   13,  372,    0,    4,    0,
    5,   14,    0,    0,    6,    7,    0,    8,    9,   10,
   11,   12,   13,    0,    0,    0,    0,  390,   14,    0,
    0,    0,    0,    4,    0,    5,    0,    0,    0,    6,
    7,  405,    8,    9,   10,   11,   12,   13,    0,  365,
    0,    0,    0,   14,    0,    0,    0,    0,    0,    0,
    4,  417,    5,  242,    0,    0,    6,    7,    0,    8,
    9,   10,   11,   12,   13,    4,    0,    5,    0,    0,
   14,    6,    7,    0,    8,    9,   10,   11,   12,   13,
    0,    0,    0,    0,  222,   14,  223,    0,    0,    0,
    6,    7,  208,    8,    9,   10,  224,   12,   13,    0,
    0,    0,    0,    4,   14,    5,    0,    0,    0,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    4,    0,
  207,    0,    0,   14,    6,    7,  208,    8,    9,   10,
   11,   12,   13,   71,  222,    0,  344,    0,   14,    0,
    6,    7,  208,    8,    9,   10,  224,   12,   13,    4,
    0,    5,    0,    0,   14,    6,    7,    0,    8,    9,
   10,   11,   12,   13,    0,    0,    0,    0,    0,   14,
    0,  222,    0,  223,    0,    0,    0,    6,    0,  208,
    8,   71,    0,  224,   12,    4,    0,    5,    0,    0,
    0,    6,    0,  222,    8,  223,    0,   11,   12,    6,
    0,  208,    8,    0,    0,  224,   12,    0,    0,    0,
    0,    0,    0,    0,    0,  156,  159,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   71,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  156,
  190,    0,    0,  190,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  240,    0,    0,  240,
    0,    0,    0,    0,    0,    0,   71,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   71,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   56,   40,   45,   36,   54,   41,   62,   41,  207,   58,
   41,   15,   61,   15,   18,   61,   41,   40,   43,   59,
   45,   80,  186,   40,  223,    0,  256,  256,   41,  258,
  257,  142,  262,  116,   59,   60,   61,   62,   58,   36,
   58,   61,  256,   61,   58,  256,   31,   61,  262,   41,
  272,  262,   41,   61,   43,  326,   45,   40,  123,   43,
   40,   45,   66,  119,   68,   45,   68,  256,  232,   42,
   59,   60,   61,   62,   47,   44,   60,   61,   62,  256,
   40,   43,  258,   45,   40,   45,  357,  170,   42,   43,
   59,   45,   41,   47,   60,   61,   62,   59,  256,  256,
   44,  256,   99,  261,  375,   59,  175,  262,   59,   45,
  256,   41,  256,   40,   44,   59,  262,  186,  262,   59,
   58,   59,  256,  287,  288,  236,  176,  404,  187,  133,
  401,   59,  188,   40,   41,  412,   59,   59,  256,   59,
  209,   59,  175,  420,  262,  344,   41,  424,  123,   44,
  314,  257,  316,  256,  139,  205,  257,  142,   41,  262,
   41,  257,   43,  232,   45,  169,  267,  169,   40,  267,
  271,  256,  257,  271,  338,  339,  209,   41,   59,   43,
   59,   45,  186,  257,  253,   43,  299,   45,  301,  392,
  256,  394,  260,  261,  256,   59,  200,  201,  200,  249,
   40,  256,  260,  261,  260,  274,  275,  256,  267,  258,
   58,  208,  260,  261,  257,  258,  123,  257,  287,  288,
  253,   40,  257,  269,  260,  261,  269,  276,  232,  260,
  299,  256,  301,  272,  257,  260,  261,  293,  272,  243,
  257,  274,  275,  256,   40,  314,  296,  316,  273,  274,
  275,  236,   40,  125,  251,  324,  276,  326,  276,  328,
  329,  269,  276,   40,   41,  257,  299,  256,  301,  338,
  339,  260,  261,   58,  257,  267,  345,  257,  258,  271,
  336,   59,   40,  123,  273,  274,  275,  256,  357,  273,
  274,  275,  342,  326,  256,  328,  329,  257,  258,   40,
  369,  257,  256,   41,  123,  256,  375,  273,  274,  275,
  379,  380,  256,  257,  262,  175,  256,  367,  256,  257,
  324,  257,  258,  392,  357,  394,  186,  123,  256,  261,
  258,  186,  401,  256,  256,  404,  256,  406,  407,  389,
   59,  345,  375,  412,  260,  261,  123,  260,  261,  209,
  257,  420,  259,   40,  256,  424,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  369,  258,  125,  401,  260,
  277,   61,  232,  258,   40,  379,  380,  232,  260,  261,
  106,  107,  123,   41,  256,  257,   59,  259,  392,  256,
  394,  263,  264,  253,  266,  267,  268,  269,  270,  271,
  404,  257,  406,  407,   59,  277,  256,  123,  412,  258,
  257,  257,  269,   59,  274,  275,  420,  257,   59,  259,
  424,   59,   59,  263,  175,  265,  266,  287,  288,  269,
  270,   59,  287,  288,   59,  186,  258,   59,  257,  299,
  259,  301,  256,  256,  263,  256,  265,  266,   58,  256,
  269,  270,  256,   59,  314,  256,  316,  123,  209,  256,
   40,  257,  125,  259,  324,  258,  326,  263,  328,  329,
  266,   59,   59,  269,  270,  256,   41,  257,  338,  339,
  257,  232,  259,  338,  339,  345,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   58,  261,  357,   59,  257,
  277,  259,  253,  256,  256,  263,   59,   40,  266,  369,
  257,  269,  270,   40,  256,  375,  257,  261,  259,  379,
  380,  258,  263,  274,  275,  266,   41,  257,  269,  270,
  257,  256,  392,  256,  394,   59,  287,  288,  258,   59,
  257,  401,   40,  123,  404,   58,  406,  407,  299,  256,
  301,  256,  412,   41,  258,   59,  257,  256,  175,   59,
  420,  345,   58,  314,  424,  316,  261,  256,   59,  186,
  257,   41,  259,  324,  256,  326,  263,  328,  329,  266,
  257,   59,  269,  270,  256,  369,  209,  338,  339,   40,
  123,  257,  209,  259,  345,  379,  380,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  357,  257,  392,   41,
  394,  277,  257,  256,   41,  232,  256,  186,  369,   59,
    0,  123,  406,  407,  375,  130,  125,  125,  379,  380,
  253,  316,  158,   40,  100,   39,  253,  290,  103,  382,
  261,  392,  277,  394,   53,   98,   -1,   -1,   -1,   -1,
  401,  274,  275,  404,   -1,  406,  407,  274,  275,   40,
   -1,  412,   -1,  232,   -1,   -1,   -1,   -1,   -1,  420,
  287,  288,   -1,  424,  125,   40,  299,  257,  301,  259,
  260,  261,  299,  263,  301,   -1,  266,   -1,   -1,  269,
  270,   -1,   -1,   -1,   40,   -1,   -1,  314,   -1,  316,
   -1,   -1,   -1,   -1,   -1,  328,  329,  324,   -1,  326,
   -1,  328,  329,   40,   -1,   -1,  123,   -1,  287,  288,
   -1,  338,  339,   -1,  257,   -1,  259,   -1,  345,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   40,
  357,   -1,  123,   -1,  277,  314,   -1,  316,   -1,   -1,
   -1,   -1,  369,   -1,   -1,  324,   40,   -1,  375,  257,
  125,  259,  379,  380,   -1,  263,   -1,  265,  266,  338,
  339,  269,  270,   -1,   -1,  392,  345,  394,   -1,  125,
   40,   -1,   -1,   -1,  401,   -1,   -1,  404,   -1,  406,
  407,   -1,   -1,   -1,   -1,  412,   40,   -1,  125,   -1,
  369,   -1,   -1,  420,   -1,   -1,  257,  424,  259,   -1,
  379,  380,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,  123,  392,   40,  394,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  404,   -1,  406,  407,  123,
   -1,   40,   -1,  412,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  420,  259,   -1,   -1,  424,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  125,   40,   -1,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,
   -1,  125,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,  257,   40,  259,   -1,  277,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  123,   40,   -1,
   -1,  257,  277,  259,   -1,   -1,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  125,   40,   -1,   -1,
  257,  277,  259,   -1,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   -1,   40,   -1,   -1,   -1,
  277,  125,   -1,   -1,   -1,   -1,  257,  257,  259,   -1,
   -1,   40,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,  272,  257,   -1,  259,  277,   40,  125,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,  123,   40,  277,   -1,   -1,   -1,  257,  298,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,  257,   40,  259,   -1,  277,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,   -1,
   -1,  125,   -1,  277,   -1,   -1,   40,   -1,   -1,   -1,
   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,   -1,
  266,  267,  268,  269,  270,  271,  356,   -1,  257,   -1,
  259,  277,   -1,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,   -1,   -1,   -1,   -1,  377,  277,   -1,
   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  391,  266,  267,  268,  269,  270,  271,   -1,  125,
   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  411,  259,  125,   -1,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,  257,   -1,  259,   -1,   -1,
  277,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,   -1,  257,  277,  259,   -1,   -1,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   -1,
   -1,   -1,   -1,  257,  277,  259,   -1,   -1,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,  257,   -1,
  259,   -1,   -1,  277,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   18,  257,   -1,  259,   -1,  277,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  257,
   -1,  259,   -1,   -1,  277,  263,  264,   -1,  266,  267,
  268,  269,  270,  271,   -1,   -1,   -1,   -1,   -1,  277,
   -1,  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  265,
  266,   66,   -1,  269,  270,  257,   -1,  259,   -1,   -1,
   -1,  263,   -1,  257,  266,  259,   -1,  269,  270,  263,
   -1,  265,  266,   -1,   -1,  269,  270,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  100,  101,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  133,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  154,
  155,   -1,   -1,  158,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  191,   -1,   -1,  194,
   -1,   -1,   -1,   -1,   -1,   -1,  201,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  243,
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
"lista_de_variables : lista_de_variables id",
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


//#line 860 "Parser.java"
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
//#line 165 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 70:
//#line 166 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 71:
//#line 167 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 72:
//#line 172 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(3).ival));
						yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
					   }
break;
case 73:
//#line 175 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 74:
//#line 176 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", val_peek(3), val_peek(1)));}
break;
case 75:
//#line 180 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), val_peek(0)));}
break;
case 76:
//#line 181 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 77:
//#line 184 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 78:
//#line 185 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 79:
//#line 186 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 80:
//#line 187 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 81:
//#line 188 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 82:
//#line 189 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 83:
//#line 194 "gramatica.y"
{ 	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
										sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 84:
//#line 196 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 85:
//#line 197 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 86:
//#line 201 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cond",val_peek(1)));}
break;
case 87:
//#line 202 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 88:
//#line 203 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 89:
//#line 204 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 90:
//#line 208 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(1), val_peek(0)));}
break;
case 91:
//#line 209 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(0), null));}
break;
case 92:
//#line 210 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 93:
//#line 214 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 94:
//#line 215 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 95:
//#line 219 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 96:
//#line 220 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 97:
//#line 223 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 										   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 98:
//#line 225 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 99:
//#line 226 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 100:
//#line 229 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 101:
//#line 230 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 102:
//#line 231 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 103:
//#line 234 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 104:
//#line 235 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 105:
//#line 238 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 106:
//#line 239 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 107:
//#line 242 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 											   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 108:
//#line 244 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 109:
//#line 245 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 110:
//#line 248 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 111:
//#line 249 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 112:
//#line 250 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 113:
//#line 253 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 114:
//#line 254 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 115:
//#line 257 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 116:
//#line 258 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 117:
//#line 261 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
												  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 118:
//#line 263 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 119:
//#line 264 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 120:
//#line 267 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 121:
//#line 268 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 122:
//#line 269 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 123:
//#line 272 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 124:
//#line 273 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 125:
//#line 276 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 126:
//#line 277 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 127:
//#line 283 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
									yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 128:
//#line 285 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 129:
//#line 286 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 130:
//#line 290 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 131:
//#line 291 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 132:
//#line 292 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 133:
//#line 299 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
									}
break;
case 134:
//#line 302 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 135:
//#line 303 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 136:
//#line 304 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 137:
//#line 305 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 138:
//#line 309 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));}
break;
case 139:
//#line 312 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 140:
//#line 315 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("cond", new ParserVal(sintactico.crearNodo(val_peek(1).sval,identificador,constante))));}
break;
case 141:
//#line 320 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 142:
//#line 321 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 143:
//#line 324 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 144:
//#line 325 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 151:
//#line 335 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 152:
//#line 336 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 154:
//#line 342 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 155:
//#line 343 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 156:
//#line 347 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										}
break;
case 157:
//#line 350 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 158:
//#line 351 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 159:
//#line 352 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 160:
//#line 353 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 161:
//#line 356 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));}
break;
case 162:
//#line 361 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo(val_peek(1).sval,new ParserVal(sintactico.crearHoja(val_peek(0).ival)),null))));}
break;
case 170:
//#line 371 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 171:
//#line 375 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 172:
//#line 376 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 173:
//#line 384 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
				yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 174:
//#line 386 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                		yyval = new ParserVal(sintactico.crearNodoControl("break", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 175:
//#line 388 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 176:
//#line 391 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
						yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 177:
//#line 393 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   				yyval = new ParserVal(sintactico.crearNodoControl("continue", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 178:
//#line 395 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 179:
//#line 396 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 180:
//#line 402 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 181:
//#line 407 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 183:
//#line 413 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 184:
//#line 414 "gramatica.y"
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
//#line 1708 "Parser.java"
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
