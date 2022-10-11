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
   19,   10,   10,   10,   10,   21,   21,   21,   21,   21,
   21,   20,   20,   23,   23,   23,   11,   11,   11,   12,
   12,   12,   12,   12,   12,   13,   13,   13,   13,   13,
   13,   25,   25,   26,   26,   15,   15,   15,   15,   28,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   27,   30,   30,   32,   32,   32,   32,   32,   32,   32,
    5,    5,    5,    5,   14,   14,   33,   33,   33,   34,
   34,   34,   34,   34,   34,   24,   24,   24,   24,   35,
   22,   22,   36,   36,   36,   37,   37,   37,   31,   31,
   31,   31,   31,   31,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    2,    2,    2,    0,
    5,    3,    5,    7,    2,    5,    4,    4,    3,    5,
    1,    2,    1,    1,    2,    1,    4,    4,    2,    5,
    5,    5,    4,    3,    5,    6,    8,    8,    5,    8,
    4,    3,    1,    3,    1,    7,    2,    5,    6,    1,
    9,    9,    9,    9,    9,    9,    9,   11,   11,   11,
    3,    1,    1,    4,    4,    4,    4,    6,    6,    6,
    1,    1,    1,    2,    2,    3,    2,    2,    2,    2,
    2,    4,    4,    4,    4,    3,    2,    2,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  125,    0,    0,
  126,    0,    0,    0,    0,    0,    9,   10,    0,   16,
   17,   18,   19,   20,   21,    0,    0,    0,    0,   44,
    0,   46,   49,    0,  116,  117,    0,    0,    0,    0,
    0,    0,  115,    0,    0,    0,    0,    0,    0,   67,
    0,    0,    0,    0,    0,    0,    0,    6,   11,   12,
   15,   22,    0,    0,    0,    0,    0,   35,    0,    0,
    0,    0,    0,    0,   91,   95,   92,   93,    0,    0,
    0,    0,   45,    0,    0,  118,  121,  122,  124,  123,
   82,   83,  119,  120,    0,    0,    0,    0,   63,    0,
  107,    0,    0,    0,    0,    0,   25,   26,    0,    0,
    0,    0,    0,    0,    4,   54,    0,    0,   14,    0,
   13,    0,   28,    0,    0,   27,    0,   32,   99,   98,
   97,  101,    0,  100,    0,    0,    7,    0,    8,   96,
   94,    0,    0,    0,    0,   48,   47,  106,    0,    0,
    0,    0,   61,  113,  114,   53,    0,    0,    0,   81,
    0,    0,    0,    0,    0,    0,    0,    3,   24,   23,
   29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   59,    0,    0,   62,   52,   51,   50,
   55,   68,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,   43,    0,   33,  104,  105,
  102,  103,   87,    0,    0,   84,   86,   85,    0,    0,
    0,    0,    0,   65,    0,   56,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   42,   31,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   66,    0,    0,    0,    0,    0,    0,    0,    0,   39,
    0,   34,   90,   88,   89,    0,    0,    0,    0,   64,
   60,   58,   57,    0,    0,    0,    0,    0,    0,    0,
   38,    0,   37,    0,    0,    0,    0,   76,   77,   73,
   71,   75,   74,   72,    0,   40,   36,    0,    0,    0,
    0,    0,    0,    0,   80,    0,   79,   78,
};
final static short yydgoto[] = {                          2,
    3,  194,   16,   72,  139,   74,   75,   19,   63,   20,
   21,   22,   23,   24,   25,   26,   67,  125,   68,  205,
  206,   39,   34,   40,  100,  225,  165,  195,   27,   95,
   96,   76,   77,   78,   41,   42,   43,
};
final static short yysindex[] = {                      -241,
    0,    0,  382,   53,   -4,  -29,   23,    0,   31,   45,
    0,  467, -206, -226,  -49,  403,    0,    0, -199,    0,
    0,    0,    0,    0,    0,   38,   94,  -43,   46,    0,
  -53,    0,    0,  -39,    0,    0,  -39, -167,  -33,   30,
   58,   35,    0, -155,  -27,   66, -146,   96, -141,    0,
  127,   13,   62,  424, -133,   89,  467,    0,    0,    0,
    0,    0,  -42, -125,   77, -121,   40,    0,  116,  110,
  210,  119,   80, -116,    0,    0,    0,    0,  101, -127,
   13,  103,    0,   85,  104,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -39,  -39,  141,  210,    0, -110,
    0,  -39,  -39,   88,  -10, -108,    0,    0, -107,  -38,
   57, -141,   95, -141,    0,    0,  -97,  448,    0, -106,
    0,  -96,    0, -229,   26,    0, -177,    0,    0,    0,
    0,    0,  106,    0,  111,   49,    0,  107,    0,    0,
    0,  -97,  122,  123,  -97,    0,    0,    0,   35,    4,
 -129,  167,    0,    0,    0,    0,  108,  117,  112,    0,
  -93,  339,  113,  124,  120,  121,  125,    0,    0,    0,
    0,  355,   56,  -78,  135,  -67,  285,  -65,  -61,  139,
  -97,  124,  142,    0,  233,  144,    0,    0,    0,    0,
    0,    0,  382,  -49,  -51,    4,    4,   33,    4,    4,
 -141,    0,   19,  355,   81,    0, -177,    0,    0,    0,
    0,    0,    0,  149,  -46,    0,    0,    0, -141,  150,
   50, -141,  210,    0, -217,    0,  152,    0,   12,   97,
    4,  118,  157,  158,  340,  -39,    3,    0,    0,  361,
  160,  347,  161,  359, -141,    4,  360,  189,  362,  137,
    0,  384,  386,  166,  387,  388,  391,    4,   24,    0,
  380,    0,    0,    0,    0,    4,  385,  184,    4,    0,
    0,    0,    0,  191,  193,   55,  194,  195,  198,  188,
    0,  138,    0,  206,    4,   84,  208,    0,    0,    0,
    0,    0,    0,    0,  425,    0,    0,  427,  213,  215,
  431,  219,  215,  442,    0,  219,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  484,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   64,  -36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  363,    0,    0,    0,    0,    0,
    0,    0,  267,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   79,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -8,   15,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  145,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  297,
    0,    0,    0,    0,    0,  319,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  482,   18,  -37,  625,  581,  628,   -7,    0,    0,
  488,    0,    0,   17,    0,    0, -109,    0,  -47,    0,
  283,   68,    0,    0,  392,    0,   -9,  298,    0,  479,
  445,    0,    0,    0,  457,  400,   10,
};
final static int YYTABLESIZE=848;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
    5,  122,  162,  112,  112,   38,  112,   83,  112,   91,
   45,   92,  243,  106,   80,    1,  121,  173,   66,  128,
   33,  112,  112,  112,  112,  112,   93,   90,   94,   54,
  158,  111,  111,  136,  111,   37,  111,    8,  249,  110,
   38,   11,   56,  261,  250,   91,   91,   92,   92,  111,
  111,  111,  111,  111,  110,  110,   61,   62,  236,    5,
  152,  260,   47,   38,  282,   55,   91,   33,   92,   13,
   49,  112,  110,   57,  118,   91,  102,   92,   65,   64,
   65,  103,  281,  127,   52,   81,  112,   14,   13,    8,
   86,  231,   91,   11,   92,  291,   65,  240,  101,  207,
  104,   84,  163,  109,  166,  107,   14,  178,  246,  108,
   31,  154,  155,   32,  111,   48,  171,   83,  108,   66,
  114,  109,  116,    5,  300,  208,  184,   91,  117,   92,
  123,  185,  186,   13,  124,  126,  108,  110,  140,  141,
  142,  143,  145,  147,  148,  153,  156,  159,  172,  169,
  160,   14,   98,  164,  198,   93,   90,   94,   13,   51,
  170,  181,  192,  150,  174,  179,  188,  135,  134,  176,
  191,  196,  221,  177,  131,  190,   14,  209,  199,  200,
   13,  182,  197,  201,  111,  248,  109,   32,  212,  204,
  217,  235,  262,  211,  218,  273,  297,  219,   14,   66,
  222,  108,  226,   70,  228,  239,   13,  241,  245,  244,
  251,  242,  247,  119,  120,   82,   71,   35,   36,  112,
  112,  161,  112,  112,   14,   79,  112,  112,   13,  112,
  112,  112,  112,  112,  112,  267,  112,  112,  112,   87,
   88,   89,   44,  138,  105,  157,   14,  111,  111,   13,
  111,  111,   35,   36,  111,  111,    5,  111,  111,  111,
  111,  111,  111,   98,  111,  111,  111,   14,  252,   51,
  237,  110,   13,  110,  110,   35,   36,  110,  110,   46,
  110,  110,  110,  110,  110,  110,    4,   48,    5,   97,
   14,  187,    6,    7,   64,   69,    8,    9,   10,   70,
   11,   51,   51,  259,    8,    4,    7,    5,   11,   28,
  290,    6,    7,  270,   69,    8,    9,   10,   70,   11,
  109,   29,  109,  109,    7,   56,  109,  109,   30,  109,
  109,  109,  109,  109,  109,  108,   71,  108,  108,  290,
  146,  108,  108,  216,  108,  108,  108,  108,  108,  108,
    4,   48,    5,  253,   71,  223,    6,    7,   78,   69,
    8,    9,   10,   70,   11,  132,  133,  175,   87,   88,
   89,  129,  189,  130,  255,    4,   78,    5,   13,   48,
   48,    6,    7,   28,   69,    8,    9,   10,   70,   11,
  210,    7,  272,  296,   13,   10,   14,    4,  258,    5,
   70,   65,   30,    6,    7,  264,   69,    8,    9,   10,
   70,   11,   14,  256,  257,  263,  265,  266,  269,   71,
  271,   13,  276,    4,  274,    5,  275,  277,  278,    6,
    7,  279,   69,    8,    9,   10,   70,   11,  283,   14,
  286,   78,   13,  285,  295,    4,  288,    5,  289,  292,
  293,    6,    7,  294,   69,    8,    9,   10,   70,   11,
   14,   12,  298,   13,  301,  302,    4,  303,    5,  304,
  305,  306,    6,    7,  307,   69,    8,    9,   10,   70,
   11,   14,  308,    1,   15,   30,  238,   13,  151,    4,
  227,    5,  109,   85,  149,    6,    7,   53,   69,    8,
    9,   10,   70,   11,   12,   14,   13,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   53,    0,    0,    0,
    0,    0,    0,    7,   14,    7,    0,   58,    0,    7,
    7,    0,    7,    7,    7,    7,    7,    7,    0,  113,
  213,    0,  214,    0,    0,  215,    0,    0,  115,    0,
    0,    0,    0,   71,    0,   71,    0,    0,    0,   71,
   71,    0,   71,   71,   71,   71,   71,   71,  144,    0,
    0,    0,  168,    0,    0,   78,    0,   78,    0,    0,
    0,   78,   78,   17,   78,   78,   78,   78,   78,   78,
    0,    0,   17,    0,    0,    4,   59,    5,  193,    0,
    0,    6,    7,    0,  167,    8,    9,   10,    0,   11,
  202,    4,    0,    5,    0,    0,    0,    6,    7,  203,
    0,    8,    9,   10,    0,   11,    0,    0,    0,  180,
   18,    0,  183,    0,   59,    0,    0,   17,    4,   18,
    5,    0,    0,   60,    6,    7,    0,    0,    8,    9,
   10,   73,   11,    0,    0,    0,    0,    0,    0,    4,
    0,    5,    0,    0,   99,    6,    7,    0,  220,    8,
    9,   10,    0,   11,  229,  230,  232,  233,  234,    0,
    4,   60,    5,    0,   18,    0,    6,    7,    0,    0,
    8,    9,   10,    0,   11,  137,    0,    0,   59,  232,
    0,    0,    0,    0,    4,    0,    5,    0,    0,  254,
    6,    7,    0,    0,    8,    9,   10,    0,   11,    0,
    0,   99,  137,    4,  268,    5,    0,    0,    0,    6,
    7,    0,    0,    8,    9,   10,  280,   11,    0,    0,
    0,    0,   17,    0,  284,   60,    0,  287,    0,    0,
    0,    0,   17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  299,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   17,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   59,    0,    0,    0,    0,   18,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  224,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   60,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  137,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          9,
    0,   44,   41,   40,   41,   45,   43,   61,   45,   43,
   40,   45,   59,   41,   58,  257,   59,  127,   26,   67,
    4,   58,   59,   60,   61,   62,   60,   61,   62,   12,
   41,   40,   41,   71,   43,   40,   45,  267,  256,   49,
   45,  271,  269,   41,  262,   43,   43,   45,   45,   58,
   59,   60,   61,   62,   40,   41,  256,  257,   40,   59,
   98,   59,   40,   45,   41,  272,   43,   51,   45,   40,
   40,   59,   58,  123,   57,   43,   42,   45,   41,  257,
   41,   47,   59,   44,   40,   40,  123,   58,   40,  267,
  258,   59,   43,  271,   45,   41,   41,  207,   41,   44,
  256,   34,  112,   40,  114,   40,   58,   59,   59,  256,
   58,  102,  103,   61,  123,  257,  124,   61,   40,  127,
   59,   58,  256,  123,   41,  173,  256,   43,   40,   45,
  256,  261,  262,   40,   58,  257,   58,  123,   59,  256,
   40,  269,   40,   59,   41,  256,   59,  256,  123,  256,
  258,   58,  123,   59,  164,   60,   61,   62,   40,  257,
  257,   40,  256,   96,   59,   59,   59,   58,   59,   59,
   59,   59,  182,  125,   59,   59,   58,  256,   59,   59,
   40,   59,   59,   59,   58,  223,  123,   61,  256,  172,
  256,  201,  240,   59,  256,   59,   59,   59,   58,  207,
   59,  123,   59,   59,  256,  125,   40,   59,   59,  219,
   59,  258,  222,  256,  257,  269,  123,  257,  258,  256,
  257,  260,  259,  260,   58,  269,  263,  264,   40,  266,
  267,  268,  269,  270,  271,  245,  273,  274,  275,  273,
  274,  275,  272,  125,  272,  256,   58,  256,  257,   40,
  259,  260,  257,  258,  263,  264,  256,  266,  267,  268,
  269,  270,  271,  123,  273,  274,  275,   58,  257,  257,
  203,  257,   40,  259,  260,  257,  258,  263,  264,  257,
  266,  267,  268,  269,  270,  271,  257,  257,  259,  260,
   58,  125,  263,  264,  257,  266,  267,  268,  269,  270,
  271,  257,  257,  236,  267,  257,   40,  259,  271,  257,
  256,  263,  264,  125,  266,  267,  268,  269,  270,  271,
  257,  269,  259,  260,   58,  269,  263,  264,  276,  266,
  267,  268,  269,  270,  271,  257,   40,  259,  260,  256,
  256,  263,  264,   59,  266,  267,  268,  269,  270,  271,
  257,  257,  259,  257,   58,  123,  263,  264,   40,  266,
  267,  268,  269,  270,  271,  256,  257,  257,  273,  274,
  275,  256,  256,  258,  257,  257,   58,  259,   40,  257,
  257,  263,  264,  257,  266,  267,  268,  269,  270,  271,
  256,  125,  256,  256,   40,  269,   58,  257,   59,  259,
  256,   41,  276,  263,  264,   59,  266,  267,  268,  269,
  270,  271,   58,  257,  257,  256,  256,   59,   59,  123,
   59,   40,  257,  257,   41,  259,   41,   41,   41,  263,
  264,   41,  266,  267,  268,  269,  270,  271,   59,   58,
  257,  123,   40,   59,  257,  257,  256,  259,  256,  256,
  256,  263,  264,  256,  266,  267,  268,  269,  270,  271,
   58,  123,  257,   40,  257,   41,  257,   41,  259,  257,
  256,   41,  263,  264,  256,  266,  267,  268,  269,  270,
  271,   58,   41,    0,    3,  123,  204,   40,   97,  257,
  193,  259,   48,   37,   95,  263,  264,   10,  266,  267,
  268,  269,  270,  271,  123,   58,   40,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   29,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   58,  259,   -1,  125,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   52,
  256,   -1,  258,   -1,   -1,  261,   -1,   -1,  125,   -1,
   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   81,   -1,
   -1,   -1,  125,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,    3,  266,  267,  268,  269,  270,  271,
   -1,   -1,   12,   -1,   -1,  257,   16,  259,  260,   -1,
   -1,  263,  264,   -1,  117,  267,  268,  269,   -1,  271,
  256,  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,
   -1,  267,  268,  269,   -1,  271,   -1,   -1,   -1,  142,
    3,   -1,  145,   -1,   54,   -1,   -1,   57,  257,   12,
  259,   -1,   -1,   16,  263,  264,   -1,   -1,  267,  268,
  269,   27,  271,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,  259,   -1,   -1,   40,  263,  264,   -1,  181,  267,
  268,  269,   -1,  271,  196,  197,  198,  199,  200,   -1,
  257,   54,  259,   -1,   57,   -1,  263,  264,   -1,   -1,
  267,  268,  269,   -1,  271,   71,   -1,   -1,  118,  221,
   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,  231,
  263,  264,   -1,   -1,  267,  268,  269,   -1,  271,   -1,
   -1,   97,   98,  257,  246,  259,   -1,   -1,   -1,  263,
  264,   -1,   -1,  267,  268,  269,  258,  271,   -1,   -1,
   -1,   -1,  162,   -1,  266,  118,   -1,  269,   -1,   -1,
   -1,   -1,  172,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  193,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  204,   -1,   -1,   -1,   -1,  162,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  172,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  185,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  193,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  204,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  223,
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
"ret_fun : Return '(' expresion ')' ';'",
"ret_fun : Return expresion ')' ';'",
"ret_fun : Return '(' expresion ';'",
"ret_fun : Return expresion ';'",
"ret_fun : Return '(' expresion ')' error",
"ret_fun : error",
"cuerpo_fun : sentencia ret_fun",
"cuerpo_fun : ret_fun",
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

//#line 251 "gramatica.y"

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


//#line 619 "Parser.java"
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
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el identificador de variable"); }
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
//#line 96 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN5(Línea " + AnalizadorLexico.LINEA + "): no se reconoce return"); }
break;
case 44:
//#line 104 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 45:
//#line 105 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 46:
//#line 106 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 48:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 51:
//#line 115 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 52:
//#line 116 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 53:
//#line 117 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 54:
//#line 118 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 55:
//#line 119 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 56:
//#line 123 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 57:
//#line 124 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 58:
//#line 125 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 59:
//#line 126 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 60:
//#line 127 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 61:
//#line 128 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 67:
//#line 142 "gramatica.y"
{ sintactico.addErrorSintactico( " SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis." ); }
break;
case 68:
//#line 143 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 69:
//#line 144 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 72:
//#line 151 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
break;
case 73:
//#line 152 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
break;
case 74:
//#line 153 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 75:
//#line 154 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 76:
//#line 155 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
break;
case 77:
//#line 156 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
break;
case 79:
//#line 158 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
break;
case 80:
//#line 159 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
break;
case 85:
//#line 170 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
break;
case 86:
//#line 171 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
break;
case 87:
//#line 172 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 89:
//#line 175 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
break;
case 90:
//#line 176 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta el Else. "); }
break;
case 94:
//#line 182 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias declarativas adentro del FOR"); }
break;
case 99:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 101:
//#line 196 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
break;
case 103:
//#line 198 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
break;
case 104:
//#line 199 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
break;
case 105:
//#line 200 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 107:
//#line 204 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 108:
//#line 205 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 109:
//#line 206 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 117:
//#line 222 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                  }
break;
case 118:
//#line 227 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                    }
break;
case 119:
//#line 232 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 120:
//#line 233 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 121:
//#line 234 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 122:
//#line 235 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 123:
//#line 236 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 124:
//#line 237 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 125:
//#line 240 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 126:
//#line 244 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 1052 "Parser.java"
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
