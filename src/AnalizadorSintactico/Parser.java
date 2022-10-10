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
public final static short IF=259;
public final static short then=260;
public final static short ELSE=261;
public final static short end_if=262;
public final static short out=263;
public final static short fun=264;
public final static short RETURN=265;
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
    7,    9,    9,    9,   16,   16,   17,   17,   17,   17,
   10,   10,   19,   11,   11,   11,   11,   11,   11,   12,
   12,   12,   12,   12,   12,   13,   13,   13,   13,   13,
   13,   13,   21,   21,   22,   22,   15,   15,   15,   15,
   24,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   23,   26,   26,   28,   28,   28,   28,   28,   28,
   28,    5,    5,    5,   14,   14,   29,   29,   29,   30,
   30,   30,   30,   30,   30,   20,   20,   20,   20,   31,
   18,   18,   32,   32,   32,   33,   33,   33,   27,   27,
   27,   27,   27,   27,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    6,    9,    9,    4,
    9,    8,    1,    4,    4,    4,    3,    3,    2,    5,
    5,    5,    4,    3,    5,    6,    8,    4,    6,    6,
    8,    8,    3,    1,    3,    1,    7,    2,    5,    6,
    1,    9,    9,    9,    9,    9,    9,    9,   11,   11,
   11,    3,    1,    1,    4,    4,    4,    4,    6,    6,
    6,    1,    1,    1,    2,    3,    2,    2,    2,    2,
    2,    4,    4,    4,    4,    3,    2,    2,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  115,    0,    0,
  116,    0,    0,    0,    0,    0,    9,   10,    0,   16,
   17,   18,   19,   20,   21,    0,    0,    0,  107,    0,
   33,    0,    0,    0,   39,    0,    0,    0,  105,  106,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    6,   11,   12,
   15,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   82,   85,   83,   84,    0,    0,    0,   26,
    0,    0,  108,   37,   73,   74,    0,   38,    0,    0,
    0,    0,  111,  112,  114,  113,  109,  110,    0,    0,
    0,   54,    0,   97,    0,    0,    0,   25,    0,    0,
    0,    0,    0,    0,    4,   44,    0,    0,   14,    0,
   13,    0,    0,    0,    0,    0,   89,   88,   87,   91,
    0,   90,    0,    0,    7,    0,    8,   86,    0,    0,
    0,    0,    0,    0,   35,   34,  103,  104,   96,    0,
    0,    0,   48,   43,    0,    0,    0,   72,    0,    0,
    0,    0,    0,    0,    0,    3,   24,   23,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   53,   42,   41,
   40,   45,   59,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   30,    0,    0,    0,    0,    0,   94,   95,
   92,   93,   78,    0,    0,   75,   77,   76,    0,    0,
    0,    0,   49,    0,   56,    0,   50,   46,    0,   60,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,    0,    0,    0,    0,    0,    0,    0,
    0,   27,    0,    0,    0,   81,   79,   80,    0,    0,
    0,    0,   55,   51,   52,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   32,    0,    0,    0,    0,
    0,   67,   68,   64,   62,   66,   65,   63,    0,    0,
    0,   31,    0,    0,    0,    0,    0,   28,   29,    0,
    0,   71,    0,   70,   69,
};
final static short yydgoto[] = {                          2,
    3,  195,   16,   71,  137,   17,   73,   19,   63,   20,
   21,   22,   23,   24,   25,   26,   66,   36,   37,   43,
  103,  226,  163,  196,   27,   87,   99,   74,   75,   76,
   44,   38,   39,
};
final static short yysindex[] = {                      -230,
    0,    0,   92,  -38,   -9,  -40, -180,    0,  -35,   -5,
    0,  138, -219, -179,  -24,  108,    0,    0, -159,    0,
    0,    0,    0,    0,    0,   29,  360,  -55,    0,   -3,
    0, -156,  -52, -155,    0,   23,   96,   33,    0,    0,
  146,  -39,  184,   63, -147,  -31,   70,   95, -143,    0,
  -32,  -47,   56,  125, -138,   88,  138,    0,    0,    0,
    0,    0,  129,   73, -136,   62,  -29,   76,  117,  462,
  368,   74,    0,    0,    0,    0,   94, -131,  -47,    0,
   99,  146,    0,    0,    0,    0,  146,    0,  101,  146,
  146,  102,    0,    0,    0,    0,    0,    0,  146,  388,
  462,    0, -114,    0,   86,  -11, -105,    0, -106,  -30,
  -46, -143,  -41, -143,    0,    0, -104,  153,    0, -102,
    0,  -99, -217,  -16,  119,  -91,    0,    0,    0,    0,
  103,    0,  -14,  265,    0,  105,    0,    0, -104,  128,
   -4, -104,   28,   33,    0,    0,    0,    0,    0,   28,
 -205,  402,    0,    0,  112,   64,  118,    0,  -89,   79,
  120,   71,  121,  130,  133,    0,    0,    0,   61, -217,
  136,  -70,  146,  155,  -57,  110,  -56,  242,  -50,  -48,
  145, -104,   71,  150,  168,  416,  122,    0,    0,    0,
    0,    0,    0,   92,  -24,  -44,   28,   28,   36,   28,
   28, -143,    0,  -18, -217,  218,   84,  146,    0,    0,
    0,    0,    0,  276,  -45,    0,    0,    0, -143,  285,
   49, -143,    0,  462,    0, -204,    0,    0,  286,    0,
  107,  134,   28,  141,  154,  156,  355,  374,  296,  367,
  371,  162,  171,  372,  176,  375, -143,   28,  376,  430,
  377,  127,    0,  392,  396,  181,  398,  399,  407,   28,
  391,    0, -217,  326,  400,    0,    0,    0,   28,  403,
  198,   28,    0,    0,    0,    0,  201,  202,   66,  207,
  210,  211,  212, -217,  345,    0,  346,  215,   28,   81,
  216,    0,    0,    0,    0,    0,    0,    0,  434,  353,
  221,    0,  439,  222,  225,  441,  228,    0,    0,  225,
  444,    0,  228,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  487,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,  322,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   17,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  446,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  341,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   47,   24,    0,    0,    0,    0,    0,  300,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  131,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  438,    0,    0,    0,    0,    0,
    0,    0,  454,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   35,   21,  -27,  494,   18,  542,   -7,    0,    0,
  612,    0,    0,   45,    0,    0,    0,  424,    0,    0,
  389,    0,   -1,  297,    0,  540,  442,    0,    0,    0,
  451,  408,  -51,
};
final static int YYTABLESIZE=829;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
  102,   32,   78,   85,   49,   86,   34,   50,   82,  107,
  160,  112,   34,  245,   82,   34,    5,  162,   65,   33,
   97,   96,   98,  101,  171,  111,    1,  170,   33,  156,
   41,   57,   54,   59,   52,   34,   79,   15,  147,  148,
  102,  102,  134,  102,  177,  102,   36,  110,   35,    8,
  185,  251,   55,   11,  183,  186,  187,  252,  102,  102,
  102,  102,  102,  101,  101,   85,  101,   86,  101,   64,
   85,   59,   86,  152,   90,    5,   47,  118,   85,   91,
   86,  101,  101,  101,  101,  101,   36,  106,  106,   56,
  106,   85,  106,   86,  233,   35,   61,   62,   57,   80,
  126,   13,   83,  104,   36,   36,  295,  248,  105,  108,
  161,   35,  164,   48,  114,  169,  172,  116,   13,   14,
  124,  305,  191,  102,  241,  102,   85,  117,   86,  198,
  123,   13,  138,  139,  129,   59,   14,  140,  142,    5,
   34,  153,  149,   85,  154,   86,  101,   13,  101,   14,
  157,  158,   51,  167,   97,   96,   98,  168,  173,  146,
  199,  175,  204,  180,   13,   14,  193,  182,  211,   36,
  189,   36,  122,  174,  133,  132,  192,   13,  197,  200,
  228,  221,   14,  203,   12,  276,  206,  121,  201,   61,
   34,  202,   13,  205,  208,   14,  250,  239,  209,  212,
  237,   12,  265,  219,   85,  217,   86,  218,  222,   51,
   14,  230,  244,   77,   12,   48,   81,  246,   28,   29,
  249,   48,   56,   13,   28,   29,  223,   28,   29,  159,
   30,   45,   58,   93,   94,   95,   10,   31,  238,   30,
  106,   14,  176,   31,  155,  270,   31,   40,   29,  115,
    8,   51,   48,   51,   11,  285,  102,  102,  240,  102,
  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,    5,  102,  102,  102,  300,  166,   84,  101,
  101,    5,  101,  101,  101,  101,  101,  101,  101,  101,
  101,  101,  101,  101,  101,    8,  101,  101,  101,   11,
  216,  106,   36,   36,   13,   36,  101,   36,   36,   36,
   36,   36,   36,   36,   36,   36,   36,   36,    4,  190,
    5,  294,   14,  179,    6,    7,  125,   48,    8,    9,
   10,  127,   11,  128,  243,    4,  294,    5,  194,  100,
  100,    6,    7,  247,  253,    8,    9,   10,    4,   11,
    5,   88,   40,   29,    6,    7,  145,  100,    8,    9,
   10,   99,   11,  254,    4,  210,    5,   93,   94,   95,
    6,    7,  130,  131,    8,    9,   10,  227,   11,   99,
   98,    4,  275,    5,  119,  120,   61,    6,    7,  178,
  255,    8,    9,   10,    4,   11,    5,  257,   98,   13,
    6,    7,   40,   29,    8,    9,   10,   13,   11,    4,
  258,    5,  259,  260,  261,    6,    7,   14,  262,    8,
    9,   10,  100,   11,  263,   14,  266,   13,   42,  264,
  267,  268,  277,  269,  272,  274,  278,  279,  280,  281,
   67,   13,    5,  100,   99,   14,    6,  282,  284,   68,
  286,    9,   10,   69,  290,   13,  292,  293,  287,   14,
   89,  289,  296,   98,   42,  297,  298,  301,  299,   13,
  302,  303,  306,   14,  307,  308,  309,   62,  311,  310,
  312,  313,   70,  314,  315,    7,    1,   14,  151,  109,
  229,   92,  136,   69,  144,   62,    0,  213,    0,  214,
    0,   13,  215,    7,    0,  143,    0,    0,    0,    0,
  101,   69,    0,    0,    0,    0,    0,    0,    0,   14,
   72,   67,  150,    5,    0,    0,  188,    6,    0,    0,
   68,    0,    9,   10,   69,    0,  102,    0,  224,    0,
    0,    0,    0,    0,   18,    0,    0,    0,    0,    0,
    0,    0,    0,   18,  273,    0,  100,   60,  100,  100,
   62,    0,  100,  135,    0,  100,    0,  100,  100,  100,
    7,    0,    0,    0,    0,    0,   69,    0,   99,    0,
   99,   99,    0,    0,   99,    0,    0,   99,    0,   99,
   99,   99,    0,  102,  135,   60,  207,   98,   18,   98,
   98,    0,    0,   98,    0,    0,   98,   18,   98,   98,
   98,    0,    0,    0,    0,    0,   67,    0,    5,    0,
    0,   53,    6,    0,   67,   68,    5,    9,   10,   69,
    6,  242,    0,   68,    0,    9,   10,   69,    0,    0,
    0,   53,    0,    0,   67,    0,    5,    0,    0,    0,
    6,    0,    0,   68,    0,    9,   10,   69,   67,   60,
    5,    0,    0,  113,    6,    0,    0,   68,    0,    9,
   10,   69,   67,    0,    5,    0,    0,    0,    6,  225,
    0,   68,    0,    9,   10,   69,   67,    0,    5,    0,
  141,    0,    6,    0,   62,   68,   62,    9,   10,   69,
   62,   18,    7,   62,    7,   62,   62,   62,    7,    0,
   69,    7,   69,    7,    7,    7,   69,  135,   67,   69,
    5,   69,   69,   69,    6,    0,    0,   68,  165,    9,
   10,   69,    0,    0,    0,   18,  231,  232,  234,  235,
  236,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  181,    0,    0,  184,    0,    0,    0,    0,    0,    0,
  234,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  256,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  271,    0,    0,
    0,    0,    0,  220,    0,    0,    0,    0,    0,  283,
    0,    0,    0,    0,    0,    0,    0,    0,  288,    0,
    0,  291,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  304,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   58,   43,   40,   45,   45,    9,   61,   41,
   41,   59,   45,   59,   61,   45,    0,   59,   26,   58,
   60,   61,   62,    0,   41,   58,  257,   44,   58,   41,
   40,  123,   12,   16,   40,   45,   40,    3,   90,   91,
   40,   41,   70,   43,   59,   45,    0,   49,    4,  267,
  256,  256,  272,  271,   59,  261,  262,  262,   58,   59,
   60,   61,   62,   40,   41,   43,   43,   45,   45,   41,
   43,   54,   45,  101,   42,   59,  257,   57,   43,   47,
   45,   58,   59,   60,   61,   62,   40,   42,   43,  269,
   45,   43,   47,   45,   59,   51,  256,  257,  123,  256,
   66,   40,  258,   41,   58,   59,   41,   59,  256,   40,
  112,   67,  114,  257,   59,  123,  124,  256,   40,   58,
  257,   41,   59,  123,   41,  125,   43,   40,   45,   59,
   58,   40,   59,   40,   59,  118,   58,  269,   40,  123,
   45,  256,   41,   43,   59,   45,  123,   40,  125,   58,
  256,  258,  257,  256,   60,   61,   62,  257,   40,   59,
  162,   59,  170,   59,   40,   58,  256,   40,   59,  123,
   59,  125,   44,  265,   58,   59,   59,   40,   59,   59,
   59,  183,   58,  123,  123,   59,  257,   59,   59,   59,
   45,   59,   40,   58,   40,   58,  224,  205,  256,  256,
  202,  123,   41,   59,   43,  256,   45,  256,   59,  257,
   58,  256,  258,  269,  123,  257,  269,  219,  257,  258,
  222,  257,  269,   40,  257,  258,   59,  257,  258,  260,
  269,  272,  125,  273,  274,  275,  269,  276,  257,  269,
  272,   58,  257,  276,  256,  247,  276,  257,  258,  125,
  267,  257,  257,  257,  271,  263,  256,  257,   41,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  256,  273,  274,  275,  284,  125,  256,  256,
  257,  265,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  267,  273,  274,  275,  271,
   59,  256,  256,  257,   40,  259,  123,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  257,  256,
  259,  256,   58,   59,  263,  264,  265,  257,  267,  268,
  269,  256,  271,  258,   59,  257,  256,  259,  260,   40,
   41,  263,  264,   59,   59,  267,  268,  269,  257,  271,
  259,  256,  257,  258,  263,  264,  256,   58,  267,  268,
  269,   40,  271,  257,  257,  256,  259,  273,  274,  275,
  263,  264,  256,  257,  267,  268,  269,  256,  271,   58,
   40,  257,  256,  259,  256,  257,  256,  263,  264,  125,
  257,  267,  268,  269,  257,  271,  259,  257,   58,   40,
  263,  264,  257,  258,  267,  268,  269,   40,  271,  257,
  257,  259,  257,   59,   41,  263,  264,   58,  123,  267,
  268,  269,  123,  271,   58,   58,  256,   40,    5,   59,
   59,  256,   41,   59,   59,   59,   41,  257,   41,   41,
  257,   40,  259,  260,  123,   58,  263,   41,   58,  266,
  125,  268,  269,  270,  257,   40,  256,  256,   59,   58,
   37,   59,  256,  123,   41,  256,  256,  123,  257,   40,
  125,  257,  257,   58,   41,  123,  256,   40,  257,   41,
  256,   41,  123,  256,   41,   40,    0,   58,  100,   48,
  194,   41,  125,   40,   87,   58,   -1,  256,   -1,  258,
   -1,   40,  261,   58,   -1,   82,   -1,   -1,   -1,   -1,
  123,   58,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   27,  257,   99,  259,   -1,   -1,  125,  263,   -1,   -1,
  266,   -1,  268,  269,  270,   -1,   43,   -1,  123,   -1,
   -1,   -1,   -1,   -1,    3,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   12,  125,   -1,  257,   16,  259,  260,
  123,   -1,  263,   70,   -1,  266,   -1,  268,  269,  270,
  125,   -1,   -1,   -1,   -1,   -1,  123,   -1,  257,   -1,
  259,  260,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
  269,  270,   -1,  100,  101,   54,  173,  257,   57,  259,
  260,   -1,   -1,  263,   -1,   -1,  266,   66,  268,  269,
  270,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,
   -1,   10,  263,   -1,  257,  266,  259,  268,  269,  270,
  263,  208,   -1,  266,   -1,  268,  269,  270,   -1,   -1,
   -1,   30,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,   -1,  266,   -1,  268,  269,  270,  257,  118,
  259,   -1,   -1,   52,  263,   -1,   -1,  266,   -1,  268,
  269,  270,  257,   -1,  259,   -1,   -1,   -1,  263,  186,
   -1,  266,   -1,  268,  269,  270,  257,   -1,  259,   -1,
   79,   -1,  263,   -1,  257,  266,  259,  268,  269,  270,
  263,  160,  257,  266,  259,  268,  269,  270,  263,   -1,
  257,  266,  259,  268,  269,  270,  263,  224,  257,  266,
  259,  268,  269,  270,  263,   -1,   -1,  266,  117,  268,
  269,  270,   -1,   -1,   -1,  194,  197,  198,  199,  200,
  201,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  139,   -1,   -1,  142,   -1,   -1,   -1,   -1,   -1,   -1,
  221,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  233,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  248,   -1,   -1,
   -1,   -1,   -1,  182,   -1,   -1,   -1,   -1,   -1,  260,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  269,   -1,
   -1,  272,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  289,
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
null,null,null,null,null,null,null,"id","cte","IF","then","ELSE","end_if","out",
"fun","RETURN","BREAK","i32","when","FOR","CONTINUE","f32","cadena",
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
"ejecutables : sentencia_IF",
"ejecutables : expresion_FOR",
"ejecutables : sentencia_when",
"lista_de_variables : id",
"lista_de_variables : lista_de_variables ',' id",
"lista_de_variables : lista_de_variables id error",
"encabezado_func : fun id '('",
"encabezado_func : id '(' error",
"parametro : tipo id ')' ':' tipo '{'",
"parametro : tipo id ',' tipo id ')' ':' tipo '{'",
"parametro : tipo id tipo id ')' ':' tipo '{' error",
"parametro : ')' ':' tipo '{'",
"declaracion_func : encabezado_func parametro bloque_sentencias RETURN '(' expresion ')' ';' '}'",
"declaracion_func : encabezado_func parametro RETURN '(' expresion ')' ';' '}'",
"op_asignacion : opasignacion",
"asignacion : id op_asignacion expresion ';'",
"asignacion : id op_asignacion expresion error",
"asignacion : id ':' '=' expresion",
"asignacion : id expresion error",
"asignacion : id op_asignacion error",
"asignacion : id expresion_FOR",
"salida : out '(' cadena ')' ';'",
"salida : out '(' cadena ')' error",
"salida : out '(' cadena error ';'",
"salida : out cadena error ';'",
"salida : '(' cadena error",
"salida : out '(' ')' error ';'",
"sentencia_IF : IF condicion_if then cuerpo_IF end_if ';'",
"sentencia_IF : IF condicion_if then cuerpo_IF ELSE cuerpo_ELSE end_if ';'",
"sentencia_IF : IF condicion_if cuerpo_IF error",
"sentencia_IF : IF condicion_if then cuerpo_IF error ';'",
"sentencia_IF : IF condicion_if then cuerpo_IF end_if error",
"sentencia_IF : IF condicion_if then cuerpo_IF ELSE cuerpo_ELSE error ';'",
"sentencia_IF : IF condicion_if then cuerpo_IF ELSE cuerpo_ELSE end_if error",
"cuerpo_IF : '{' bloque_sentencias_FOR '}'",
"cuerpo_IF : sentencias_FOR",
"cuerpo_ELSE : '{' bloque_sentencias_FOR '}'",
"cuerpo_ELSE : sentencias_FOR",
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
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' ELSE cte ';'",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' ELSE ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' cte ';' error",
"sentencias_FOR : ejecutables",
"sentencias_FOR : sentencia_BREAK",
"sentencias_FOR : sentencia_CONTINUE",
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

//#line 226 "gramatica.y"

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


//#line 606 "Parser.java"
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
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
break;
case 6:
//#line 33 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
break;
case 13:
//#line 51 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 14:
//#line 52 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
break;
case 15:
//#line 53 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA) + "): falta el nombre de la variable"); }
break;
case 24:
//#line 66 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIFicadores."); }
break;
case 26:
//#line 70 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta fun en la definición de la función."); }
break;
case 29:
//#line 75 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre parametros."); }
break;
case 33:
//#line 85 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 35:
//#line 89 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la asignación."); }
break;
case 36:
//#line 90 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): error en el op de asignacion"); }
break;
case 37:
//#line 91 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el operador de asignación."); }
break;
case 38:
//#line 92 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la asignación."); }
break;
case 41:
//#line 97 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 42:
//#line 98 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 43:
//#line 99 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 44:
//#line 100 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 45:
//#line 101 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 46:
//#line 105 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 47:
//#line 106 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 48:
//#line 107 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 49:
//#line 108 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de end_if."); }
break;
case 50:
//#line 109 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 51:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de end_if."); }
break;
case 52:
//#line 111 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 58:
//#line 123 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis."); }
break;
case 59:
//#line 124 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 60:
//#line 125 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 63:
//#line 132 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
break;
case 64:
//#line 133 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
break;
case 65:
//#line 134 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 66:
//#line 135 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 67:
//#line 136 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
break;
case 68:
//#line 137 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
break;
case 70:
//#line 139 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
break;
case 71:
//#line 140 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
break;
case 76:
//#line 151 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
break;
case 77:
//#line 152 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
break;
case 78:
//#line 153 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 80:
//#line 156 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
break;
case 81:
//#line 157 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el ELSE. "); }
break;
case 89:
//#line 173 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 91:
//#line 177 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
break;
case 93:
//#line 179 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
break;
case 94:
//#line 180 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
break;
case 95:
//#line 181 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 97:
//#line 185 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN IF "); }
break;
case 98:
//#line 186 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN IF "); }
break;
case 99:
//#line 187 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN IF "); }
break;
case 109:
//#line 207 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 110:
//#line 208 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 111:
//#line 209 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 112:
//#line 210 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 113:
//#line 211 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 114:
//#line 212 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 115:
//#line 215 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 116:
//#line 219 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 989 "Parser.java"
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
