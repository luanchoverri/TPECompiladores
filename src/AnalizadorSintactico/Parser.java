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
    3,   10,   10,   10,   10,   10,   10,   11,   11,   11,
   11,   13,   13,   13,   20,   20,   21,   21,   22,   22,
   23,   14,   14,   14,   14,   25,   25,   25,   25,   25,
   24,   28,   28,   28,   28,   28,   31,   31,   27,   27,
   27,   27,   29,   29,   29,   29,   29,   29,   33,   33,
   34,   34,   35,   35,   35,   35,   35,   35,   36,   36,
   37,   37,   40,   40,   30,   30,   30,   30,   30,   39,
   39,   39,   39,   39,   39,   39,   39,   38,   38,    7,
    7,    7,   16,   16,   16,   16,   17,   17,   17,   17,
   17,   17,   18,   18,   18,   18,   18,   18,   45,   45,
   46,   46,   15,   15,   15,   15,   47,   47,   47,   48,
   48,   49,   49,   49,   49,   49,   41,   42,   42,    9,
    9,    9,    9,    9,   19,   43,   43,   43,   44,   44,
   44,   44,   32,   32,   32,   32,   51,   26,   26,   52,
   52,   52,   53,   53,   53,   50,   50,   50,   50,   50,
   50,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    2,    2,    2,    0,
    5,    3,    5,    7,    2,    5,    4,    4,    3,    5,
    1,    1,    1,    1,    1,    1,    2,    1,    2,    2,
    1,    1,    6,    8,    8,    5,    8,    4,    3,    1,
    3,    1,    6,    8,    8,    5,    8,    4,    3,    1,
    3,    1,    4,    1,   12,   12,   11,   13,   14,    1,
    1,    1,    1,    1,    1,    1,    2,    1,    2,    1,
    2,    1,    4,    4,    6,    4,    5,    5,    5,    4,
    3,    5,    6,    8,    8,    5,    8,    4,    3,    1,
    3,    1,    7,    7,    7,    7,    3,    3,    3,    4,
    1,   12,   12,   11,   13,   14,    3,    1,    1,    1,
    1,    1,    2,    2,    1,    2,    3,    2,    2,    4,
    4,    2,    3,    2,    2,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  172,    0,    0,
  173,    0,    0,    0,    0,    0,   26,   18,   19,    0,
   25,   27,   28,   29,   30,   31,    0,  145,  100,    0,
  102,    0,  163,  164,    0,    0,    0,    0,    0,    0,
  162,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   10,    0,    0,    0,    6,   20,   21,
   24,   32,    0,    0,    0,    0,    0,   45,    0,  101,
    0,    0,    0,    0,  165,  168,  169,  171,  170,  138,
  139,  166,  167,    0,    0,    0,    0,    0,    0,    0,
  120,    0,  140,    0,  141,  142,    0,  154,    0,    0,
    0,    0,    0,   35,   36,    0,    0,    0,    0,    0,
    0,   15,    0,    0,    9,    8,    7,    0,    4,  111,
    0,   23,    0,   22,    0,   38,    0,    0,   37,    0,
   42,    0,    0,  106,    0,  104,  103,  153,    0,    0,
    0,    0,    0,  148,    0,  146,  152,    0,  149,    0,
    0,   16,  143,  144,  118,  160,  161,  110,    0,    0,
    0,  137,    0,    0,    0,    0,    0,   14,   13,   12,
   11,    3,   34,   33,   39,    0,    0,    0,    0,  116,
    0,    0,    0,   49,    0,  147,    0,    0,  119,   17,
  109,  108,  107,  112,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   62,   52,   54,    0,   56,
    0,   61,   53,   55,    0,   43,    0,  105,    0,  122,
    0,  113,   48,    0,   47,  151,  150,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   41,   60,   59,    0,    0,    0,    0,    0,   50,   46,
  128,  127,  129,  125,  123,  126,  124,    0,    0,    0,
    0,    0,    0,   70,    0,    0,    0,   44,    0,  121,
  117,  115,  114,    0,    0,    0,    0,    0,   58,    0,
   68,    0,    0,    0,    0,    0,    0,    0,   66,    0,
    0,   69,   57,    0,    0,    0,    0,    0,  131,    0,
    0,    0,    0,    0,   72,    0,   63,    0,    0,    0,
    0,    0,    0,  134,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  133,  132,    0,    0,   71,
   67,   65,   64,    0,    0,    0,    0,  130,  135,    0,
    0,    0,    0,  136,    0,    0,    0,    0,    0,   90,
   91,   93,   92,   96,   84,    0,   94,   95,    0,    0,
    0,    0,    0,   98,    0,   97,   87,    0,    0,    0,
    0,    0,    0,   80,    0,   99,   86,   85,    0,    0,
    0,    0,   78,   83,   88,   89,   76,    0,    0,   79,
    0,    0,   82,   73,    0,    0,    0,   81,   77,   75,
   74,
};
final static short yydgoto[] = {                          2,
    3,   15,  196,   17,   53,   54,   32,  151,  299,   92,
   93,   20,   63,   21,   22,   23,   24,   25,   26,   27,
   67,  128,   68,  209,   94,   37,  211,  264,  213,  353,
  280,   38,  265,  306,  354,  373,  392,  363,  355,  356,
   48,   84,   95,   96,   97,  221,  197,  300,   28,   85,
   39,   40,   41,
};
final static short yysindex[] = {                      -233,
    0,    0,  566,  -25,   49,  -28,  -33,    0,  -26,  -12,
    0,  -38,  691, -227,  -59,  588,    0,    0,    0, -119,
    0,    0,    0,    0,    0,    0,   68,    0,    0,  -53,
    0,  106,    0,    0,  -40, -189,   -4,  -31,   31,  -15,
    0, -179,  -30,   83, -127,  421, -118,  131,  -24,  -71,
   18,  -68,   67,    0,  604,  -65,  691,    0,    0,    0,
    0,    0,   75,  -58,  141,  -56,   54,    0,  162,    0,
  145, -221,  111,  163,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -40,  -40,  233,   70,  115,  -43,  490,
    0,  -51,    0,  -50,    0,    0,  -47,    0,  -40,  -40,
  151,   19,  -44,    0,    0,   14,  -39,   61,  169,   99,
  -24,    0,  124, -159,    0,    0,    0,  105,    0,    0,
  621,    0,  130,    0,  132,    0, -223,  203,    0, -136,
    0,  139,  118,    0,  136,    0,    0,    0,  -15,   69,
 -160,  -40,  122,    0,  338,    0,    0,  339,    0,  146,
  258,    0,    0,    0,    0,    0,    0,    0,  343,  -41,
  345,    0,  646,  528,  646,  356,  173,    0,    0,    0,
    0,    0,    0,    0,    0,  661,  125,  -24,  373,    0,
  277,  377,  434,    0,  379,    0,  186,  393,    0,    0,
    0,    0,    0,    0,  691,  676,  394,  646,  396,  397,
 -118,  117,   -8,   49,    9,    0,    0,    0,  337,    0,
  661,    0,    0,    0, -136,    0,  205,    0,  490,    0,
 -182,    0,    0,   44,    0,    0,    0,  550,  218,  220,
  425,  229,  230,  428, -118, -118,  -42,   77,  -24,  231,
    0,    0,    0,  448,  432,  293,  435,   73,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,  447,  449,
  468,  723,  113,    0,  253,  254,  -24,    0, -118,    0,
    0,    0,    0,  256,   69,   69,  259, -134,    0,  724,
    0,  460,  262,  472,   84,  275,  276,  -24,    0,  741,
  479,    0,    0, -118,  119,   69,  490,  318,    0,  283,
  510,  512,  297,  113,    0, -120,    0,  507, -118, -118,
  310,  334,  313,    0,  318,  318,  515,  744,  517,  128,
   69,  520,  513,  539,  533,    0,    0,  352, -118,    0,
    0,    0,    0,  353,   69,   69,  318,    0,    0,  535,
  142,  355,  365,    0,   69,   49,  509,  350,  367,    0,
    0,    0,    0,    0,    0,  368,    0,    0,  584,  585,
  372,  157,  389,    0,  374,    0,    0,  350,  350,  590,
  414,  509,  376,    0,  574,    0,    0,    0,  380,  350,
 -112,  440,    0,    0,    0,    0,    0,  455,  575,    0,
  509,  -98,    0,    0,  471,  576,  137,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  637,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  180,   22,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   20,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  516,    0,    0,    0,    0,    0,
   28,    0,    0,  201,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   45,  -10,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  518,    0,    0,    0,    0,    0,    0,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  378,    0,    0,  522,  -45, -197,  -35,   -3,
  918,  303,    0,    0,    0,  761,  832,    0,  606,    0,
 -107,    0,  328,    0,  927,    6,    0, -133,    0,  773,
  341, -187,  385,    0,    0,  270,    0, -330,  685, -228,
  -46,  719,  878,  963,  556,    0, -139, -269,    0,  603,
  615,  578,   80,
};
final static int YYTABLESIZE=1358;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
  107,  164,   91,  110,   36,  114,   45,   70,   14,   18,
  103,   43,   59,   47,  150,  149,  238,  193,   70,    5,
   52,  246,  177,    1,  199,  200,   99,   50,  313,  157,
  157,  100,   30,  109,  134,   31,   31,   73,   80,  135,
   81,  382,  212,    8,   56,  327,  328,   11,  240,  237,
   91,   59,   31,   18,  152,   82,   79,   83,  231,  160,
  395,  159,  159,   57,  159,  167,  159,  344,   75,  163,
  163,   98,  163,  247,  163,  109,  101,  243,   31,  248,
  159,  159,  159,  159,  158,  158,  163,  158,   35,  158,
  140,   90,  143,   36,   65,  180,  169,  130,  170,  312,
  181,  182,  250,  158,  158,  158,  158,  244,   65,  142,
  118,   80,  157,   81,   36,  190,   14,   59,  125,  365,
   64,  289,  104,   14,  298,  117,  290,  291,  105,  279,
    8,  273,  217,  124,   11,  319,   61,   62,   46,  378,
  379,  320,    5,  387,  159,  220,  293,  183,  388,  389,
   36,  386,   14,   80,  234,   81,  305,  396,  362,   18,
   18,   18,  185,  397,   80,   65,   81,  158,  215,  137,
  279,  108,  206,  146,  236,  235,  310,  309,  156,  157,
  184,   14,  348,  152,  293,  111,  333,  115,  259,  260,
  120,   18,   59,  266,   18,  401,   14,  126,  127,  263,
  129,  132,  133,  138,  153,  154,  297,  242,  155,  158,
  190,  161,  147,  148,  192,   69,   33,   34,   51,  156,
  163,  283,  284,   44,   59,    4,  261,    5,   86,   70,
   46,    6,    7,   87,   88,    8,    9,   10,   89,   11,
  155,  102,  303,   42,   49,   12,  157,  308,  157,  157,
   29,   29,  157,  157,  157,  157,  157,  157,  157,  157,
  157,  152,  322,  323,  347,  239,  157,   29,   76,   77,
   78,  162,   14,  112,  159,  113,  190,  159,  159,  372,
  159,  159,  340,  163,  159,  159,  159,  159,  159,  159,
  159,  159,  159,   29,  159,  159,  159,   14,  159,  249,
  158,  158,  156,  158,  158,   33,   34,  158,  158,  158,
  158,  158,  158,  158,  158,  158,   14,  158,  158,  158,
  165,  158,  116,  155,   64,  176,   33,   34,  272,   66,
  122,  123,   14,  203,    8,  204,  262,  349,   11,    6,
    4,   87,    5,  349,  349,  205,    6,    7,   87,   88,
    8,    9,   10,   89,   11,   90,  166,   14,  349,  349,
   12,   51,   71,   34,  349,  349,  136,  349,  349,  203,
  144,  204,  145,   14,   10,    6,  349,   87,  349,  168,
   16,  205,  189,  332,  349,  173,   69,  349,  174,   14,
   55,  349,  400,  179,  131,  178,  186,  187,  203,  219,
  346,  191,  188,  194,    6,    7,   87,   88,    8,    9,
  205,   89,   11,  203,  201,  346,  371,  270,   12,    6,
    7,   87,   88,    8,    9,  205,   89,   11,   14,  175,
  202,  218,   66,   12,  121,  222,  156,  225,  156,  156,
  297,  226,  156,  156,  156,  156,  156,  156,  156,  156,
  156,  227,  230,   14,  232,  233,  156,  155,  325,  155,
  155,  241,  245,  155,  155,  155,  155,  155,  155,  155,
  155,  155,  347,  253,  224,  254,   80,  155,   81,   14,
   82,   79,   83,  255,  256,  257,  258,  267,   65,    4,
  269,    5,  223,  271,   14,    6,    7,   87,   88,    8,
    9,   10,   89,   11,  216,  275,  276,  277,  281,   12,
   14,  282,  285,  375,    4,  288,    5,   66,  294,  295,
    6,    7,   87,   88,    8,    9,   10,   89,   11,   14,
  296,  301,  302,    4,   12,    5,  372,  307,  314,    6,
    7,   87,   88,    8,    9,   10,   89,   11,   14,    4,
  315,    5,  316,   12,  317,    6,    7,   87,   88,    8,
    9,   10,   89,   11,  390,  321,  324,   14,  326,   12,
  336,  268,  228,  329,    4,  331,    5,  391,  335,  337,
    6,    7,   87,   88,    8,    9,   10,   89,   11,   14,
    4,  338,    5,  345,   12,  398,    6,    7,   87,   88,
    8,    9,   10,   89,   11,   14,  203,  339,  346,  341,
   12,  359,    6,    7,   87,   88,    8,    9,  205,   89,
   11,  360,  366,  367,  368,  369,   12,   14,  370,  377,
  380,  383,  384,  394,  399,  385,    1,   72,   40,  171,
  381,  141,   51,   14,  318,  203,  278,  346,  106,   74,
  195,    6,    7,   87,   88,    8,    9,  205,   89,   11,
   14,  139,    0,    0,    0,   12,    0,    0,    0,    0,
  203,    0,  346,    0,  252,    0,    6,    7,   87,   88,
    8,    9,  205,   89,   11,   14,    0,    0,   13,    0,
   12,    0,    0,   76,   77,   78,  203,    0,  346,    0,
   14,    0,    6,    7,   87,   88,    8,    9,  205,   89,
   11,  203,   58,  346,    0,   14,   12,    6,    7,   87,
   88,    8,    9,  205,   89,   11,    0,  203,  119,  346,
   14,   12,    0,    6,    7,   87,   88,    8,    9,  205,
   89,   11,    0,    0,    0,  172,    4,   12,    5,    0,
    0,    0,    6,    7,   87,   88,    8,    9,   10,   89,
   11,    0,   14,   14,    0,  203,   12,  346,  195,    0,
    0,    6,    7,   87,   88,    8,    9,  205,   89,   11,
   14,    0,    0,   14,    4,   12,    5,  198,    0,    0,
    6,    7,    0,    0,    8,    9,   10,    0,   11,    0,
  229,    0,    0,    0,   12,  251,    4,    0,    5,    0,
    0,    0,    6,    7,    0,    0,    8,    9,   10,    0,
   11,    0,    4,    0,    5,    0,   12,    0,    6,    7,
    0,    0,    8,    9,   10,    0,   11,    0,    0,    0,
    0,    0,   12,    0,    4,  263,    5,    0,  292,    0,
    6,    7,    0,    0,    8,    9,   10,    0,   11,    0,
    4,    0,    5,  304,   12,    0,    6,    7,  330,    0,
    8,    9,   10,    0,   11,    0,    0,    4,    0,    5,
   12,    0,    0,    6,    7,    0,    0,    8,    9,   10,
    0,   11,    0,    0,    0,    0,    0,   12,    0,    0,
    0,    0,    4,    0,    5,    0,    0,    0,    6,    7,
    0,    0,    8,    9,   10,    0,   11,  203,    0,  204,
   19,    0,   12,    6,    7,   87,    0,    8,    9,  205,
   19,   11,    4,   60,    5,    0,  207,   12,    6,    7,
    0,    0,    8,    9,   10,    0,   11,    4,  214,    5,
    0,    0,   12,    6,    7,    0,    0,    8,    9,   10,
    0,   11,    0,    0,    0,    0,    0,   12,    0,    0,
    0,  207,   60,    0,   19,    0,  274,    0,    0,  203,
  203,  204,  204,  214,    0,    6,    6,   87,   87,    0,
    0,  205,  205,  286,  287,    0,    0,  203,  207,  204,
  203,    0,  204,    6,    0,   87,    6,  208,   87,  205,
  214,    0,  205,    0,  311,    0,    0,    0,    0,    0,
    0,    0,  207,  207,    0,    0,    0,    0,    0,    0,
    0,  364,    0,    0,  214,  214,    0,    0,   60,  334,
  207,    0,  208,    0,    0,    0,  374,  376,    0,    0,
  207,    0,  214,  342,  343,  374,  364,    0,    0,    0,
    0,    0,  214,  361,  207,    0,  376,    0,    0,  208,
    0,    0,  393,    0,    0,  364,  214,    0,  207,  376,
   19,   19,   19,    0,    0,    0,    0,    0,    0,    0,
  214,    0,    0,  208,  208,    0,    0,    0,    0,    0,
    0,  350,  210,    0,    0,    0,    0,  350,  350,    0,
    0,  208,   19,   60,    0,   19,    0,    0,    0,    0,
    0,  208,  350,  350,    0,    0,    0,    0,  350,  350,
    0,  350,  350,    0,    0,  208,    0,  210,    0,    0,
  350,    0,  350,    0,    0,   60,    0,    0,  350,  208,
    0,  350,    0,    0,    0,  350,    0,    0,    0,    0,
    0,    0,    0,    0,  210,    0,    0,    0,    0,    0,
    0,    0,  351,    0,    0,    0,    0,    0,  351,  351,
    0,    0,    0,    0,    0,    0,    0,    0,  210,  210,
    0,    0,    0,  351,  351,    0,    0,    0,    0,  351,
  351,    0,  351,  351,    0,    0,  210,    0,    0,    0,
    0,  351,    0,  351,    0,    0,  210,    0,  357,  351,
    0,    0,  351,    0,  357,  357,  351,    0,    0,    0,
  210,    0,    0,    0,    0,    0,    0,    0,    0,  357,
  357,    0,    0,    0,  210,  357,  357,    0,  357,  357,
    0,    0,    0,    0,    0,    0,    0,  357,    0,  357,
    0,    0,    0,    0,    0,  357,    0,  352,  357,    0,
    0,    0,  357,  352,  352,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  352,  352,
    0,    0,    0,    0,  352,  352,    0,  352,  352,    0,
    0,    0,    0,  358,    0,    0,  352,    0,  352,  358,
  358,    0,    0,    0,  352,    0,    0,  352,    0,    0,
    0,  352,    0,    0,  358,  358,    0,    0,    0,    0,
  358,  358,    0,  358,  358,    0,    0,    0,    0,    0,
    0,    0,  358,    0,  358,    0,    0,    0,    0,    0,
  358,    0,    0,  358,    0,    0,    0,  358,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   47,   41,   38,   49,   45,   51,   40,   61,   40,   13,
   41,   40,   16,   40,   58,   59,  204,   59,   61,    0,
   59,  219,  130,  257,  164,  165,   42,   40,  298,   40,
   41,   47,   58,   58,  256,   61,   61,   32,   43,  261,
   45,  372,  176,  267,  272,  315,  316,  271,   40,   58,
   86,   55,   61,   57,   90,   60,   61,   62,  198,   41,
  391,   40,   41,  123,   43,  111,   45,  337,  258,   42,
   43,   41,   45,  256,   47,   58,  256,  211,   61,  262,
   59,   60,   61,   62,   40,   41,   59,   43,   40,   45,
   85,  123,   87,   45,   41,  256,  256,   44,  258,  297,
  261,  262,   59,   59,   60,   61,   62,  215,   41,   40,
   44,   43,  123,   45,   45,  151,   40,  121,   44,  348,
  257,  256,   40,   40,   41,   59,  261,  262,  256,  263,
  267,   59,  178,   59,  271,  256,  256,  257,  257,  368,
  369,  262,  123,  256,  123,  181,  280,  142,  261,  262,
   45,  380,   40,   43,  201,   45,  290,  256,  346,  163,
  164,  165,   41,  262,   43,   41,   45,  123,   44,   59,
  304,   41,  176,   59,   58,   59,   58,   59,   99,  100,
   59,   40,   41,  219,  318,  257,   59,  256,  235,  236,
  256,  195,  196,  239,  198,   59,   40,  256,   58,  123,
  257,   40,   58,   41,  256,  256,  123,  211,  256,   59,
  246,  256,  256,  257,  256,  269,  257,  258,  257,   40,
  260,  267,  269,  257,  228,  257,  269,  259,  260,   61,
  257,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   40,  272,  288,  272,  257,  277,  257,  294,  259,  260,
  276,  276,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  297,  309,  310,  123,  257,  277,  276,  273,  274,
  275,  258,   40,  256,  256,  258,  312,  256,  257,  123,
  259,  260,  329,  256,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  276,  273,  274,  275,   40,  277,  256,
  256,  257,  123,  259,  260,  257,  258,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   40,  273,  274,  275,
  260,  277,  256,  123,  257,  123,  257,  258,  256,   27,
  256,  257,   40,  257,  267,  259,  260,  341,  271,  263,
  257,  265,  259,  347,  348,  269,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  123,  258,   40,  362,  363,
  277,  257,  257,  258,  368,  369,  256,  371,  372,  257,
  256,  259,  258,   40,  269,  263,  380,  265,  382,  256,
    3,  269,  125,  256,  388,  256,  269,  391,  257,   40,
   13,  395,  256,  258,   67,  257,   59,   59,  257,  123,
  259,   59,  257,   59,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  257,   59,  259,  260,  125,  277,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,  127,
  258,   59,  130,  277,   57,   59,  257,   59,  259,  260,
  123,  256,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   59,   59,   40,   59,   59,  277,  257,  125,  259,
  260,  125,  258,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  123,  256,   41,  256,   43,  277,   45,   40,
   60,   61,   62,   59,  256,  256,   59,  257,   41,  257,
   59,  259,   59,   59,   40,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  177,   59,   58,   40,  256,  277,
   40,  258,  257,  125,  257,  257,  259,  215,   59,  258,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   40,
   59,  257,  257,  257,  277,  259,  123,   59,  256,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,  257,
   41,  259,   41,  277,  258,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  125,   59,  257,   40,  256,  277,
   58,  244,  195,   59,  257,   59,  259,  123,   59,   41,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   40,
  257,   59,  259,   59,  277,  125,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   40,  257,  256,  259,  257,
  277,  257,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  257,  256,  256,   41,   41,  277,   40,  257,  256,
   41,  256,   59,   59,   59,  256,    0,   32,  123,  118,
  371,   86,  125,   40,  304,  257,  262,  259,   46,   35,
  123,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   40,   84,   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,
  257,   -1,  259,   -1,  125,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   40,   -1,   -1,  123,   -1,
  277,   -1,   -1,  273,  274,  275,  257,   -1,  259,   -1,
   40,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  257,  125,  259,   -1,   40,  277,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,  257,  125,  259,
   40,  277,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,   -1,   -1,  125,  257,  277,  259,   -1,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   40,   40,   -1,  257,  277,  259,  123,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   40,   -1,   -1,   40,  257,  277,  259,  260,   -1,   -1,
  263,  264,   -1,   -1,  267,  268,  269,   -1,  271,   -1,
  125,   -1,   -1,   -1,  277,  256,  257,   -1,  259,   -1,
   -1,   -1,  263,  264,   -1,   -1,  267,  268,  269,   -1,
  271,   -1,  257,   -1,  259,   -1,  277,   -1,  263,  264,
   -1,   -1,  267,  268,  269,   -1,  271,   -1,   -1,   -1,
   -1,   -1,  277,   -1,  257,  123,  259,   -1,  125,   -1,
  263,  264,   -1,   -1,  267,  268,  269,   -1,  271,   -1,
  257,   -1,  259,  123,  277,   -1,  263,  264,  125,   -1,
  267,  268,  269,   -1,  271,   -1,   -1,  257,   -1,  259,
  277,   -1,   -1,  263,  264,   -1,   -1,  267,  268,  269,
   -1,  271,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,   -1,  267,  268,  269,   -1,  271,  257,   -1,  259,
    3,   -1,  277,  263,  264,  265,   -1,  267,  268,  269,
   13,  271,  257,   16,  259,   -1,  176,  277,  263,  264,
   -1,   -1,  267,  268,  269,   -1,  271,  257,  176,  259,
   -1,   -1,  277,  263,  264,   -1,   -1,  267,  268,  269,
   -1,  271,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,
   -1,  211,   55,   -1,   57,   -1,  258,   -1,   -1,  257,
  257,  259,  259,  211,   -1,  263,  263,  265,  265,   -1,
   -1,  269,  269,  275,  276,   -1,   -1,  257,  238,  259,
  257,   -1,  259,  263,   -1,  265,  263,  176,  265,  269,
  238,   -1,  269,   -1,  296,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  347,   -1,   -1,  262,  263,   -1,   -1,  121,  321,
  280,   -1,  211,   -1,   -1,   -1,  362,  363,   -1,   -1,
  290,   -1,  280,  335,  336,  371,  372,   -1,   -1,   -1,
   -1,   -1,  290,  345,  304,   -1,  382,   -1,   -1,  238,
   -1,   -1,  388,   -1,   -1,  391,  304,   -1,  318,  395,
  163,  164,  165,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  318,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,
   -1,  341,  176,   -1,   -1,   -1,   -1,  347,  348,   -1,
   -1,  280,  195,  196,   -1,  198,   -1,   -1,   -1,   -1,
   -1,  290,  362,  363,   -1,   -1,   -1,   -1,  368,  369,
   -1,  371,  372,   -1,   -1,  304,   -1,  211,   -1,   -1,
  380,   -1,  382,   -1,   -1,  228,   -1,   -1,  388,  318,
   -1,  391,   -1,   -1,   -1,  395,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  238,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  341,   -1,   -1,   -1,   -1,   -1,  347,  348,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  262,  263,
   -1,   -1,   -1,  362,  363,   -1,   -1,   -1,   -1,  368,
  369,   -1,  371,  372,   -1,   -1,  280,   -1,   -1,   -1,
   -1,  380,   -1,  382,   -1,   -1,  290,   -1,  341,  388,
   -1,   -1,  391,   -1,  347,  348,  395,   -1,   -1,   -1,
  304,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  362,
  363,   -1,   -1,   -1,  318,  368,  369,   -1,  371,  372,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  380,   -1,  382,
   -1,   -1,   -1,   -1,   -1,  388,   -1,  341,  391,   -1,
   -1,   -1,  395,  347,  348,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  362,  363,
   -1,   -1,   -1,   -1,  368,  369,   -1,  371,  372,   -1,
   -1,   -1,   -1,  341,   -1,   -1,  380,   -1,  382,  347,
  348,   -1,   -1,   -1,  388,   -1,   -1,  391,   -1,   -1,
   -1,  395,   -1,   -1,  362,  363,   -1,   -1,   -1,   -1,
  368,  369,   -1,  371,  372,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  380,   -1,  382,   -1,   -1,   -1,   -1,   -1,
  388,   -1,   -1,  391,   -1,   -1,   -1,  395,
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
"ejecutables : asignacion",
"ejecutables : salida",
"ejecutables : sentencia_If",
"ejecutables : expresion_For",
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
"cuerpo_fun : bloque_sentencias_funcion",
"ejecutables_funcion : asignacion",
"ejecutables_funcion : sentencia_if_funcion",
"ejecutables_funcion : salida",
"ejecutables_funcion : sentencia_for_funcion",
"ejecutables_funcion : ret_fun",
"bloque_sentencias_ejecutables_funcion : bloque_sentencias_ejecutables_funcion ejecutables_funcion",
"bloque_sentencias_ejecutables_funcion : ejecutables_funcion",
"bloque_sentencias_funcion : bloque_sentencias_funcion ejecutables_funcion",
"bloque_sentencias_funcion : bloque_sentencias_funcion declarativas",
"bloque_sentencias_funcion : ejecutables_funcion",
"bloque_sentencias_funcion : declarativas",
"sentencia_if_funcion : If condicion_if then cuerpo_If_funcion end_if ';'",
"sentencia_if_funcion : If condicion_if then cuerpo_If_funcion Else cuerpo_Else_funcion end_if ';'",
"sentencia_if_funcion : If condicion_if then cuerpo_If_funcion Else cuerpo_Else_funcion end_if error",
"sentencia_if_funcion : If condicion_if then cuerpo_If_funcion error",
"sentencia_if_funcion : If condicion_if then cuerpo_If_funcion Else cuerpo_Else_funcion error ';'",
"sentencia_if_funcion : If condicion_if cuerpo_If_funcion error",
"cuerpo_If_funcion : '{' bloque_sentencias_ejecutables_funcion '}'",
"cuerpo_If_funcion : ejecutables_funcion",
"cuerpo_Else_funcion : '{' bloque_sentencias_ejecutables_funcion '}'",
"cuerpo_Else_funcion : ejecutables_funcion",
"sentencia_if_for_funcion : If condicion_if then cuerpo_If_for_funcion end_if ';'",
"sentencia_if_for_funcion : If condicion_if then cuerpo_If_for_funcion Else cuerpo_Else_for_funcion end_if ';'",
"sentencia_if_for_funcion : If condicion_if then cuerpo_If_for_funcion Else cuerpo_Else_for_funcion end_if error",
"sentencia_if_for_funcion : If condicion_if then cuerpo_If_for_funcion error",
"sentencia_if_for_funcion : If condicion_if then cuerpo_If_for_funcion Else cuerpo_Else_for_funcion error ';'",
"sentencia_if_for_funcion : If condicion_if cuerpo_If_for_funcion error",
"cuerpo_If_for_funcion : '{' bloque_sentencias_For_funcion '}'",
"cuerpo_If_for_funcion : sentencias_For_funcion",
"cuerpo_Else_for_funcion : '{' bloque_sentencias_For_funcion '}'",
"cuerpo_Else_for_funcion : sentencias_For_funcion",
"cola_For_funcion : '{' bloque_sentencias_For_funcion '}' ';'",
"cola_For_funcion : sentencias_For_funcion",
"sentencia_for_funcion : For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For_funcion",
"sentencia_for_funcion : For id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For_funcion error",
"sentencia_for_funcion : For id op_asignacion cte ';' condicion_for ';' signo id cola_For_funcion error",
"sentencia_for_funcion : For '(' id op_asignacion cte ':' condicion_for ':' signo id ')' cola_For_funcion error",
"sentencia_for_funcion : id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For_funcion",
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
"op_asignacion : opasignacion",
"op_asignacion : ':' '='",
"op_asignacion : '='",
"asignacion : id op_asignacion expresion ';'",
"asignacion : id op_asignacion expresion error",
"asignacion : id op_asignacion expresion_For Else cte ';'",
"asignacion : id op_asignacion expresion_For error",
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
"cuerpo_If : '{' bloque_sentencias_For '}'",
"cuerpo_If : sentencias_For",
"cuerpo_Else : '{' bloque_sentencias_For '}'",
"cuerpo_Else : sentencias_For",
"sentencia_when : when '(' condicion_for ')' then cuerpo_when ';'",
"sentencia_when : when condicion_for ')' then cuerpo_when ';' error",
"sentencia_when : when '(' condicion_for then cuerpo_when ';' error",
"sentencia_when : when '(' condicion_for ')' cuerpo_when ';' error",
"cuerpo_when : '{' sentencia '}'",
"cuerpo_when : '{' sentencia error",
"cuerpo_when : sentencia '}' error",
"cola_For : '{' bloque_sentencias_For '}' ';'",
"cola_For : sentencias_For",
"encabezado_For : For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For",
"encabezado_For : For id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For error",
"encabezado_For : For id op_asignacion cte ';' condicion_for ';' signo id cola_For error",
"encabezado_For : For '(' id op_asignacion cte ':' condicion_for ':' signo id ')' cola_For error",
"encabezado_For : id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For",
"condicion_for : id comparador cte",
"signo : '+'",
"signo : '-'",
"sentencias_For : ejecutables",
"sentencias_For : sentencia_BREAK",
"sentencias_For : sentencia_CONTINUE",
"sentencias_For : declarativas error",
"sentencias_For : ret_fun error",
"expresion_For : encabezado_For",
"sentencia_BREAK : BREAK ';'",
"sentencia_BREAK : BREAK cte ';'",
"sentencia_BREAK : BREAK error",
"sentencia_CONTINUE : CONTINUE ';'",
"sentencia_CONTINUE : CONTINUE ':' id ';'",
"sentencia_CONTINUE : CONTINUE id ';' error",
"sentencia_CONTINUE : CONTINUE error",
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

//#line 332 "gramatica.y"

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


//#line 813 "Parser.java"
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
//#line 28 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
break;
case 6:
//#line 29 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
break;
case 7:
//#line 32 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de CONSTANTE. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 8:
//#line 33 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
break;
case 9:
//#line 34 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
break;
case 13:
//#line 42 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 14:
//#line 43 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 15:
//#line 44 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 22:
//#line 60 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 23:
//#line 61 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
break;
case 24:
//#line 62 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el identificador de variable"); }
break;
case 34:
//#line 76 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores."); }
break;
case 35:
//#line 79 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" ); }
break;
case 36:
//#line 80 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 38:
//#line 84 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 40:
//#line 89 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 41:
//#line 91 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 44:
//#line 97 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 46:
//#line 101 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") "); }
break;
case 47:
//#line 102 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 48:
//#line 103 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 49:
//#line 104 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 50:
//#line 105 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 63:
//#line 129 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 64:
//#line 130 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 65:
//#line 131 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 66:
//#line 132 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 67:
//#line 133 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 68:
//#line 134 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 73:
//#line 145 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 74:
//#line 146 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 75:
//#line 147 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 76:
//#line 148 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 77:
//#line 149 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 78:
//#line 150 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 85:
//#line 166 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 86:
//#line 167 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 87:
//#line 168 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 88:
//#line 169 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 89:
//#line 170 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 97:
//#line 181 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 100:
//#line 191 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 101:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 102:
//#line 193 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 104:
//#line 198 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 106:
//#line 200 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  "); }
break;
case 108:
//#line 204 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 109:
//#line 205 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 110:
//#line 206 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 111:
//#line 207 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 112:
//#line 208 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 113:
//#line 212 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 114:
//#line 213 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 115:
//#line 214 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 116:
//#line 215 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 117:
//#line 216 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 118:
//#line 217 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 123:
//#line 228 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");}
break;
case 124:
//#line 229 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 125:
//#line 230 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 126:
//#line 231 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta la declaración de then."); }
break;
case 128:
//#line 235 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 129:
//#line 236 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 132:
//#line 244 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 133:
//#line 245 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 134:
//#line 246 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 135:
//#line 247 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 136:
//#line 248 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 143:
//#line 264 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 144:
//#line 265 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 146:
//#line 272 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 147:
//#line 273 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 148:
//#line 274 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 149:
//#line 277 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 150:
//#line 278 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 151:
//#line 279 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 152:
//#line 280 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 154:
//#line 284 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 155:
//#line 285 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 156:
//#line 286 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 164:
//#line 302 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                  }
break;
case 165:
//#line 307 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                    }
break;
case 166:
//#line 312 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 167:
//#line 313 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 168:
//#line 314 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 169:
//#line 315 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 170:
//#line 316 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 171:
//#line 317 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 172:
//#line 320 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 173:
//#line 324 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 1334 "Parser.java"
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
