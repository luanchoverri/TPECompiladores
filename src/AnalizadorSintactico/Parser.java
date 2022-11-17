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
    3,   10,   10,   10,   10,   10,   16,   16,   11,   11,
   11,   11,   11,   11,   11,   13,   13,   13,   24,   24,
   25,   25,   26,   26,   27,   14,   14,   14,   14,   29,
   29,   29,   29,   29,   28,   32,   32,   32,   32,   32,
   32,   32,   32,   35,   35,   31,   31,   31,   31,    7,
    7,    7,   17,   17,   17,   36,   36,   18,   18,   18,
   18,   18,   18,   19,   19,   19,   37,   37,   37,   37,
   38,   38,   38,   40,   40,   41,   41,   42,   42,   42,
   43,   43,   43,   44,   44,   45,   45,   33,   33,   33,
   46,   46,   46,   47,   47,   48,   48,   49,   49,   49,
   50,   50,   50,   51,   51,   52,   52,   15,   15,   15,
   56,   56,   56,   57,   57,   57,   57,   57,   58,   62,
   55,   59,   59,   60,   60,    9,    9,    9,    9,    9,
    9,    9,    9,    9,   20,   65,   65,   34,   34,   34,
   34,   34,   61,   63,   54,   54,   54,   54,   54,   54,
   54,   54,   54,   53,   53,   22,   22,   22,   23,   23,
   23,   23,   21,   21,   66,   66,   39,   30,   30,   68,
   68,   68,   67,   67,   67,   64,   64,   64,   64,   64,
   64,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    1,    1,    1,    2,    1,    1,    1,
    1,    1,    1,    2,    2,    2,    3,    1,    3,    3,
    2,    2,    2,    0,    5,    3,    5,    7,    2,    5,
    5,    5,    4,    5,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    2,    1,    2,    2,    1,    1,    1,
    2,    1,    4,    4,    4,    3,    2,    5,    5,    5,
    4,    3,    5,    5,    6,    5,    3,    2,    2,    1,
    2,    1,    1,    4,    2,    4,    2,    5,    5,    4,
    2,    1,    2,    4,    2,    4,    2,    5,    5,    4,
    2,    1,    2,    4,    2,    4,    2,    5,    5,    4,
    2,    1,    2,    4,    2,    4,    2,    6,    6,    6,
    4,    4,    4,    5,   12,   11,   13,   14,    3,    3,
    3,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    2,    1,    4,    1,    5,   12,   11,
   13,   14,    3,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    1,    2,    2,    3,    2,    2,    4,
    4,    2,    5,    4,    3,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,  202,    0,
    0,    0,  203,    0,    0,    0,    0,    0,   25,   18,
   19,    0,    0,   24,   26,   29,   30,   31,   32,   33,
    0,    0,    0,  155,    0,   70,    0,    0,    0,   72,
    0,   36,  193,  194,    0,    0,    0,    0,    0,  192,
    0,    0,    0,    0,    0,  178,    0,  176,    0,    0,
    0,    0,    0,  182,    0,  179,    0,    0,    0,    0,
   10,    0,    0,    0,    6,   20,   21,    0,    0,   34,
   35,    0,    0,    0,    0,   49,   37,    0,    0,    0,
    0,   71,    0,    0,    0,    0,    0,  195,  198,  199,
  201,  200,  144,  145,  196,  197,    0,    0,    0,    0,
    0,    0,   93,   88,    0,    0,    0,    0,    0,   39,
   40,  177,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   15,    0,    0,    9,    8,    7,    0,    4,
   82,    0,   22,   23,   42,    0,    0,   41,    0,   46,
  184,    0,    0,    0,    0,   77,    0,   74,   73,   75,
   87,    0,    0,    0,    0,    0,   95,    0,    0,   97,
    0,   91,  190,  191,   81,    0,    0,    0,  141,    0,
    0,    0,    0,    0,    0,    0,    0,  181,  180,   14,
   13,   12,   11,    3,   43,    0,    0,  183,  185,    0,
   76,   28,    0,   86,    0,    0,   84,   80,   79,   78,
   83,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  143,    0,  146,  147,  148,  150,  151,  152,    0,
  149,  134,    0,  139,    0,    0,    0,   69,   56,   58,
   59,    0,    0,    0,   61,    0,   68,   57,   60,    0,
   47,    0,   94,   27,   85,   96,    0,    0,  128,  130,
  129,    0,    0,    0,    0,    0,    0,   16,  153,  154,
    0,    0,    0,    0,    0,   62,   63,   45,   67,   66,
    0,    0,  132,  131,  133,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   17,    0,  140,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   48,    0,
    0,    0,    0,  105,    0,  107,  100,    0,  101,  103,
    0,    0,   53,    0,  142,  164,    0,    0,    0,  115,
    0,  117,  110,    0,  111,  113,    0,    0,    0,    0,
    0,    0,    0,    0,   99,   98,   52,   54,   50,   51,
    0,   65,    0,    0,  109,  108,    0,    0,    0,    0,
    0,  165,  166,  169,  170,  171,  168,  167,  172,  157,
  158,    0,    0,    0,    0,  104,  106,    0,  114,   64,
  116,    0,    0,    0,    0,  174,  173,    0,    0,  136,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  175,    0,  135,    0,    0,    0,    0,    0,  125,    0,
  127,  120,    0,  121,  123,  156,    0,  137,    0,    0,
    0,    0,    0,  119,  118,  138,    0,    0,    0,    0,
  124,  126,    0,    0,  160,    0,    0,  159,    0,    0,
  161,  162,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   70,   71,   41,  267,  222,  223,
   21,   22,   23,   24,   25,  203,   26,   27,   28,   29,
   30,   31,   32,   33,   85,  147,   86,  244,  230,   47,
  246,  352,  248,  368,  353,   96,   48,  111,   49,  112,
  113,  231,  290,  291,  292,  303,  304,  305,  369,  397,
  398,  399,  385,  370,   61,  182,   34,  129,  232,  107,
  130,  234,  299,  108,  371,   89,   50,   51,
};
final static short yysindex[] = {                      -233,
    0,    0,  794,  -35,    6,  -21,  -24,  -32,    0,  -20,
   12,   89,    0,  -45,  940, -235,  -74,  825,    0,    0,
    0, -201,   24,    0,    0,    0,    0,    0,    0,    0,
 -163, -142,    3,    0,   51,    0, -201,  126,  -55,    0,
   80,    0,    0,    0,  133, -132,   -7,  118,   86,    0,
   39, -112,  -38,  122,  -86,    0,  121,    0,  374,  -42,
  175,   18,  -37,    0,  177,    0,    2,   72,   83,  124,
    0,  842,   88,  940,    0,    0,    0,  236,   94,    0,
    0,  108,  314,  140,  167,    0,    0,  316,  344,  355,
  364,    0,  352, -143,  113,  349,  371,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  133,  133,  975,  240,
  151,  162,    0,    0,  133,  133,  368,   -9,  174,    0,
    0,    0,  188,  -31,  172,  390,  195,   18,  415,  400,
  207,  412,    0,  216, -191,    0,    0,    0,  220,    0,
    0,  857,    0,    0,    0,  -68,  356,    0, -150,    0,
    0,  419,  133,  225,  214,    0,  226,    0,    0,    0,
    0,   39,  109,  -36, 1009,  404,    0, -228,  404,    0,
  429,    0,    0,    0,    0,  430,   -1,  431,    0,  874,
  172,  432,  435,  437,  254,  574,  -42,    0,    0,    0,
    0,    0,    0,    0,    0,  890,  288,    0,    0,   18,
    0,    0,  152,    0,  248, 1027,    0,    0,    0,    0,
    0,  940,  956,  456,  261,  262,  -42,  461,    6,  110,
  906,    0,  264,    0,    0,    0,    0,    0,    0,  266,
    0,    0,  464,    0,  -11,    6,   48,    0,    0,    0,
    0,  274,  275,  407,    0,  890,    0,    0,    0, -150,
    0,  276,    0,    0,    0,    0,  198,  277,    0,    0,
    0,  481,  -42,  128,  133,   60,  589,    0,    0,    0,
  109,  -48,  135,   18,  278,    0,    0,    0,    0,    0,
  502,  485,    0,    0,    0,  109,  491,  610,  656, -221,
  289,  296,   61,  309,  508,  509,    0,  312,    0,  531,
   96,  320, -192,  313,  317,  326,   18,  534,    0,  -42,
  321,  109,  906,    0,  906,    0,    0,   16,    0,    0,
  331,   79,    0,  335,    0,    0,  337,  -19,  294,    0,
  294,    0,    0,  120,    0,    0,  529,  334,  672,  542,
   19,  353,  688,  707,    0,    0,    0,    0,    0,    0,
   18,    0,  487,  992,    0,    0,  -42,  548,    6,  921,
  359,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  109,  574,  362,  568,    0,    0,  361,    0,    0,
    0,  552,  -42,  142,  722,    0,    0,  363,  367,    0,
  574,  565,  109,  569,  741,  760, -147,  365,  375,  573,
    0,  592,    0,  378,  -42,  380,  109,  921,    0,  921,
    0,    0,  138,    0,    0,    0,  574,    0,  576,   50,
  381,  777,  809,    0,    0,    0,  109,  672,  383,  595,
    0,    0,  384,  389,    0,  672,  599,    0,  397,  672,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  583,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  655,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  583,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  180,    0,
   37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   66,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  537,    0,    0,    0,    0,    0,    0,  415,
    0,    0,   98,    0,    0,    0,  189,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -134,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  281,   31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  598,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  583,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  541,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -72,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -62,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  598,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  386,    0,    0,  523,  -51, -109, 1000,   -3,
   22,   52,   26,    0,    0,  507, -178,   85,    0,  -40,
  185,  315,  376,    0,  -76,    0,  -28,    0,  476,  -34,
    0,  246,    0,  350,  340,    0, -174,  571,  637,    0,
  572,    0,    0,    0,  392,    0,    0,  382,    0,    0,
    0,  287,  -52,  379,  -58,  -94,    0,  417, -257,  930,
    0,    0,    0,  628, -328,    0,  332,  586,
};
final static int YYTABLESIZE=1357;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
   94,  124,  119,   38,   38,   92,   95,  224,   37,  181,
  127,   20,   92,   69,   76,   55,  135,  239,   53,   60,
   38,   39,   39,    1,   40,   40,   58,  204,   38,   42,
  183,  177,   37,  205,  317,  103,   73,  104,  272,   77,
  318,   40,  224,   83,  264,   45,  272,   78,   74,   40,
   46,   63,  105,  102,  106,   35,  150,  210,   16,  373,
   42,  273,   87,  333,  191,    5,  192,  239,   76,  334,
   20,  187,  197,  163,  346,  126,  185,  189,   40,  189,
  115,  189,   79,  374,   84,  116,  214,  275,  224,   16,
  428,  429,   80,   77,   37,  189,  189,  189,  189,  434,
  295,  322,  103,  103,  104,  104,   82,  439,  412,  224,
  224,  442,  156,   81,  413,  389,    9,  157,  294,  321,
   13,   92,  239,  239,   46,   98,  114,   92,  233,  126,
  167,  170,   40,  404,  224,   16,  224,  349,   76,  193,
  193,  221,  193,  117,  193,  226,   67,   66,  252,  265,
  239,  103,  239,  104,   46,  103,  193,  104,  262,  426,
  362,  120,  224,   77,  224,  224,   88,  139,  251,  121,
   46,  159,  360,  281,  239,  239,   20,   46,  356,  122,
  226,  362,  138,  102,  384,  266,  167,  202,    5,  102,
  202,   16,  238,  112,  224,  122,  425,  195,    9,  112,
   84,  122,   13,  343,  287,  344,  362,   83,   20,   76,
  149,   68,  224,   91,   59,  125,  362,  362,  329,  128,
  300,   35,  306,   56,  254,   57,  226,  254,  180,  362,
  293,  362,   54,  118,   77,  131,   59,   16,  224,   36,
   36,  362,  279,  362,  362,   35,  176,  226,  226,  362,
   52,  340,  309,   76,  209,  338,   36,  362,  132,   82,
   42,  362,   43,   44,   36,   99,  100,  101,   62,    9,
  225,  345,  226,   13,  226,    4,  253,  219,   77,   16,
  240,    6,    7,  220,    8,    9,   10,   11,   12,   13,
  187,  187,  189,   36,  143,   14,  189,  189,  382,  378,
  226,   84,  226,  226,  274,  225,  235,   35,  359,  189,
  189,  189,    6,    7,  220,    8,    9,   10,  237,   12,
   13,  188,  284,  188,  394,  188,   14,  133,   83,  134,
  240,  250,  226,   16,  348,  361,   93,   44,  136,  188,
  188,  188,  188,  141,   64,   65,  419,   36,   11,  144,
  226,  225,  328,  193,  236,  422,  361,  423,    6,   16,
  220,    8,  169,  145,  237,   12,   43,   44,  158,   90,
  227,  146,  225,  225,  151,  355,  226,  109,  110,  137,
  241,  361,   43,   44,  152,  240,  240,  288,  289,   43,
   44,  361,  361,  424,  301,  302,  148,  225,  153,  225,
   72,  395,  396,  154,  361,  227,  361,  160,  164,  155,
    5,  161,  171,  240,    6,  240,  361,    8,  361,  361,
   11,   12,  110,  363,  361,  225,  175,  225,  225,  178,
  241,  180,  361,  105,  102,  106,  361,  240,  240,   90,
   90,  247,  331,   16,  363,  179,  173,  174,   89,   89,
   92,  227,  184,  283,    4,  186,    5,  225,  187,  142,
    6,    7,  188,    8,    9,   10,   11,   12,   13,  363,
  189,  190,  227,  227,   14,  225,   68,  198,  196,  363,
  363,  200,   91,  201,  199,  241,  241,  207,  208,  211,
  215,  280,  363,  216,  363,  217,  164,  227,    5,  227,
  228,  225,    6,  255,  363,    8,  363,  363,   11,   12,
  242,  218,  363,  241,  259,  241,  260,  261,  263,  269,
  363,  270,  271,  364,  363,  227,   16,  227,  227,  276,
  277,  278,  285,  282,  307,  228,  188,  241,  241,  286,
  188,  188,   83,  310,  364,  249,  330,  332,  312,  289,
  328,  320,  236,  188,  188,  188,    6,  227,  220,    8,
  242,  229,  237,   12,  323,  213,  324,  325,  326,  364,
  327,  243,  336,  302,  339,  227,  328,  341,  236,  364,
  364,  228,    6,  337,  220,    8,  347,  357,  237,   12,
  350,  358,  364,  351,  364,  249,  229,  257,  380,  380,
  372,  227,  228,  228,  364,  383,  364,  364,  391,  375,
  393,  379,  364,   16,  387,  242,  242,  390,  392,  402,
  364,  243,  403,  405,  364,  396,  407,  228,   16,  228,
  415,  416,  417,  418,  427,  436,  420,  430,  435,  440,
  437,   38,  229,  242,  438,  242,   99,  100,  101,   16,
  249,  249,  441,  365,    1,  228,  163,  228,  228,   44,
  164,  193,    5,  229,  229,   55,    6,  242,  242,    8,
  354,  245,   11,   12,  365,  206,  243,  243,  249,  168,
  249,   97,  319,  172,  414,  335,  123,  228,  229,    0,
  229,  308,  162,    0,    0,   16,  221,    0,    0,  365,
    0,    0,  249,  249,  243,  228,  243,    0,    0,  365,
  365,   16,    0,  296,  366,    0,  229,    0,  229,  229,
    0,  245,  365,    0,  365,    0,    0,   16,  243,  243,
    0,  228,  313,    0,  365,  366,  365,  365,  386,    0,
    0,    0,  365,  328,    0,  236,   16,    0,  229,    6,
  365,  220,    8,    0,  365,  237,   12,    0,    0,    0,
  366,   16,    0,  401,    0,    0,  229,    0,    0,    0,
  366,  366,    0,  409,  411,    0,  245,  245,  315,    0,
   16,    0,    0,  366,    0,  366,  386,    0,  386,    0,
    0,    0,  229,    0,  360,  366,    0,  366,  366,   16,
  401,  401,    0,  366,  245,    0,  245,    0,    0,    0,
    0,  366,  376,    0,  367,  366,   16,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  245,  245,
    4,  377,  219,   16,    0,  367,    6,    7,  220,    8,
    9,   10,   11,   12,   13,    4,  400,  219,   16,    0,
   14,    6,    7,  220,    8,    9,   10,   11,   12,   13,
  367,    0,    0,  408,   16,   14,    4,    0,  219,    0,
  367,  367,    6,    7,  220,    8,    9,   10,   11,   12,
   13,   16,  410,  367,    0,  367,   14,    0,    0,    0,
    0,    0,    0,    0,    0,  367,   16,  367,  367,    0,
    0,  431,    0,  367,    0,    0,    0,    0,    0,    0,
    0,  367,    4,   16,  219,  367,   15,    0,    6,    7,
  220,    8,    9,   10,   11,   12,   13,    0,  235,   16,
  359,    0,   14,  432,    6,    7,  220,    8,    9,   10,
  237,   12,   13,    0,    4,   16,  219,    0,   14,   75,
    6,    7,  220,    8,    9,   10,   11,   12,   13,    0,
   16,    0,    0,    4,   14,  219,  140,    0,    0,    6,
    7,  220,    8,    9,   10,   11,   12,   13,  235,   16,
  359,  194,    0,   14,    6,    7,  220,    8,    9,   10,
  237,   12,   13,    0,    0,   16,  212,  235,   14,  359,
    0,    0,    0,    6,    7,  220,    8,    9,   10,  237,
   12,   13,    0,    0,   16,    0,  235,   14,  359,    0,
    0,    0,    6,    7,  220,    8,    9,   10,  237,   12,
   13,   16,    0,  235,    0,  359,   14,    0,    0,    6,
    7,  220,    8,    9,   10,  237,   12,   13,   16,    0,
    4,    0,    5,   14,    0,    0,    6,    7,    0,    8,
    9,   10,   11,   12,   13,  235,   16,  359,    0,    0,
   14,    6,    7,  220,    8,    9,   10,  237,   12,   13,
  258,    4,    0,    5,    0,   14,    0,    6,    7,    0,
    8,    9,   10,   11,   12,   13,    0,  166,    4,    0,
    5,   14,    0,    0,    6,    7,    0,    8,    9,   10,
   11,   12,   13,    4,    0,    5,  381,    0,   14,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
    4,  166,    5,   14,    0,    0,    6,    7,    0,    8,
    9,   10,   11,   12,   13,    0,  235,    0,  236,    0,
   14,  256,    6,    7,  220,    8,    9,   10,  237,   12,
   13,    0,    4,    0,  219,    0,   14,    0,    6,    7,
  220,    8,    9,   10,   11,   12,   13,  235,    0,  359,
    0,    0,   14,    6,    7,  220,    8,    9,   10,  237,
   12,   13,    0,    0,    0,    0,    4,   14,    5,    0,
  298,    0,    6,    7,    0,    8,    9,   10,   11,   12,
   13,    0,    4,    0,    5,  311,   14,    0,    6,    7,
  268,    8,    9,   10,   11,   12,   13,    0,    0,    0,
    0,  164,   14,    5,  165,  110,    0,    6,    0,    0,
    8,  342,    0,   11,   12,    0,    0,    0,  328,    0,
  236,    0,    0,    0,    6,    0,  220,    8,    0,    0,
  237,   12,    0,    0,    0,  164,  297,    5,    0,    0,
    0,    6,    0,    0,    8,    0,    0,   11,   12,    0,
    0,    0,    0,  164,    0,    5,    0,  314,  316,    6,
    0,    0,    8,    0,    0,   11,   12,    0,    0,    0,
    0,  388,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  268,    0,  268,    0,    0,    0,    0,    0,
    0,    0,  406,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  421,    0,    0,    0,
    0,    0,  297,  297,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  433,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   41,   60,   41,   40,   40,   61,   41,  186,   44,   41,
   62,   15,   61,   59,   18,   40,   68,  196,   40,   40,
   40,   58,   58,  257,   61,   61,   59,  256,   40,    4,
  125,   41,   44,  262,  256,   43,  272,   45,   58,   18,
  262,   61,  221,   41,  219,   40,   58,   22,  123,   61,
   45,   40,   60,   61,   62,  257,   85,   59,   40,   41,
   35,  236,   37,  256,  256,    0,  258,  246,   72,  262,
   74,   41,  149,  108,   59,   58,  128,   41,   61,   43,
   42,   45,   59,  341,   33,   47,  181,   40,  267,   40,
   41,  420,  256,   72,   44,   59,   60,   61,   62,  428,
   41,   41,   43,   43,   45,   45,  257,  436,  256,  288,
  289,  440,  256,  256,  262,  373,  267,  261,   59,   59,
  271,  256,  301,  302,   45,  258,   41,  262,  187,   58,
  109,  110,   61,  391,  313,   40,  315,   59,  142,   42,
   43,  123,   45,  256,   47,  186,   58,   59,  200,   40,
  329,   43,  331,   45,   45,   43,   59,   45,  217,  417,
  339,   40,  341,  142,  343,  344,   41,   44,  197,  256,
   45,   59,  123,  250,  353,  354,  180,   45,   59,   59,
  221,  360,   59,  256,  359,  220,  165,  166,  123,  262,
  169,   40,  196,  256,  373,  256,   59,  146,  267,  262,
  149,  262,  271,  313,  263,  315,  385,   41,  212,  213,
   44,  257,  391,  269,  257,   41,  395,  396,  123,  257,
  269,  257,  274,  256,  203,  258,  267,  206,  260,  408,
  265,  410,  257,  272,  213,   59,  257,   40,  417,  276,
  276,  420,  246,  422,  423,  257,  256,  288,  289,  428,
  272,  310,  281,  257,  256,  307,  276,  436,  257,  257,
  235,  440,  257,  258,  276,  273,  274,  275,  257,  267,
  186,  256,  313,  271,  315,  257,  125,  259,  257,   40,
  196,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  260,  261,  256,  276,   59,  277,  260,  261,  357,  351,
  341,  250,  343,  344,  257,  221,  257,  257,  259,  273,
  274,  275,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   41,  125,   43,  383,   45,  277,  256,   41,  258,
  246,   44,  373,   40,  256,  339,  257,  258,  256,   59,
   60,   61,   62,  256,  256,  257,  405,  276,  269,  256,
  391,  267,  257,  256,  259,  408,  360,  410,  263,   40,
  265,  266,  123,  256,  269,  270,  257,  258,  256,   38,
  186,   58,  288,  289,   59,  256,  417,  260,  261,  256,
  196,  385,  257,  258,   41,  301,  302,  260,  261,  257,
  258,  395,  396,  256,  260,  261,  257,  313,   44,  315,
   15,  260,  261,   40,  408,  221,  410,   59,  257,   58,
  259,   41,  262,  329,  263,  331,  420,  266,  422,  423,
  269,  270,  261,  339,  428,  341,   59,  343,  344,  256,
  246,  260,  436,   60,   61,   62,  440,  353,  354,  260,
  261,  196,  123,   40,  360,  258,  115,  116,  260,  261,
   61,  267,  258,  256,  257,   41,  259,  373,   59,   74,
  263,  264,  256,  266,  267,  268,  269,  270,  271,  385,
   59,  256,  288,  289,  277,  391,  257,   59,  123,  395,
  396,  257,  269,  258,  153,  301,  302,   59,   59,   59,
   59,  246,  408,   59,  410,   59,  257,  313,  259,  315,
  186,  417,  263,  256,  420,  266,  422,  423,  269,  270,
  196,  258,  428,  329,   59,  331,  256,  256,   58,  256,
  436,  256,   59,  339,  440,  341,   40,  343,  344,  256,
  256,  125,  256,  258,  257,  221,  256,  353,  354,   59,
  260,  261,   41,   59,  360,  196,  301,  302,   58,  261,
  257,  256,  259,  273,  274,  275,  263,  373,  265,  266,
  246,  186,  269,  270,  256,  180,   59,   59,  257,  385,
   40,  196,  256,  261,   41,  391,  257,  257,  259,  395,
  396,  267,  263,  258,  265,  266,  256,   59,  269,  270,
  256,  258,  408,  257,  410,  246,  221,  212,  353,  354,
   59,  417,  288,  289,  420,   58,  422,  423,   41,  257,
   59,  125,  428,   40,  256,  301,  302,  256,  258,  257,
  436,  246,  256,   59,  440,  261,   58,  313,   40,  315,
  256,   59,   41,  256,   59,   41,  257,  257,  256,   41,
  257,   59,  267,  329,  256,  331,  273,  274,  275,   40,
  301,  302,  256,  339,    0,  341,   59,  343,  344,  123,
  257,  139,  259,  288,  289,  125,  263,  353,  354,  266,
  331,  196,  269,  270,  360,  169,  301,  302,  329,  109,
  331,   45,  291,  112,  398,  304,   59,  373,  313,   -1,
  315,  275,  107,   -1,   -1,   40,  123,   -1,   -1,  385,
   -1,   -1,  353,  354,  329,  391,  331,   -1,   -1,  395,
  396,   40,   -1,  125,  339,   -1,  341,   -1,  343,  344,
   -1,  246,  408,   -1,  410,   -1,   -1,   40,  353,  354,
   -1,  417,  123,   -1,  420,  360,  422,  423,  360,   -1,
   -1,   -1,  428,  257,   -1,  259,   40,   -1,  373,  263,
  436,  265,  266,   -1,  440,  269,  270,   -1,   -1,   -1,
  385,   40,   -1,  385,   -1,   -1,  391,   -1,   -1,   -1,
  395,  396,   -1,  395,  396,   -1,  301,  302,  123,   -1,
   40,   -1,   -1,  408,   -1,  410,  408,   -1,  410,   -1,
   -1,   -1,  417,   -1,  123,  420,   -1,  422,  423,   40,
  422,  423,   -1,  428,  329,   -1,  331,   -1,   -1,   -1,
   -1,  436,  125,   -1,  339,  440,   40,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  353,  354,
  257,  125,  259,   40,   -1,  360,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  257,  125,  259,   40,   -1,
  277,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  385,   -1,   -1,  123,   40,  277,  257,   -1,  259,   -1,
  395,  396,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   40,  123,  408,   -1,  410,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  420,   40,  422,  423,   -1,
   -1,  125,   -1,  428,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  436,  257,   40,  259,  440,  123,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   -1,  257,   40,
  259,   -1,  277,  125,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,  257,   40,  259,   -1,  277,  125,
  263,  264,  265,  266,  267,  268,  269,  270,  271,   -1,
   40,   -1,   -1,  257,  277,  259,  125,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  257,   40,
  259,  125,   -1,  277,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,   -1,   40,  123,  257,  277,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,   -1,   40,   -1,  257,  277,  259,   -1,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   40,   -1,  257,   -1,  259,  277,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   40,   -1,
  257,   -1,  259,  277,   -1,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,  257,   40,  259,   -1,   -1,
  277,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  125,  257,   -1,  259,   -1,  277,   -1,  263,  264,   -1,
  266,  267,  268,  269,  270,  271,   -1,  123,  257,   -1,
  259,  277,   -1,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,  125,   -1,  277,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
  257,  123,  259,  277,   -1,   -1,  263,  264,   -1,  266,
  267,  268,  269,  270,  271,   -1,  257,   -1,  259,   -1,
  277,  125,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,  257,   -1,  259,   -1,  277,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  257,   -1,  259,
   -1,   -1,  277,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,   -1,   -1,   -1,  257,  277,  259,   -1,
  271,   -1,  263,  264,   -1,  266,  267,  268,  269,  270,
  271,   -1,  257,   -1,  259,  286,  277,   -1,  263,  264,
  221,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   -1,  257,  277,  259,  260,  261,   -1,  263,   -1,   -1,
  266,  312,   -1,  269,  270,   -1,   -1,   -1,  257,   -1,
  259,   -1,   -1,   -1,  263,   -1,  265,  266,   -1,   -1,
  269,  270,   -1,   -1,   -1,  257,  267,  259,   -1,   -1,
   -1,  263,   -1,   -1,  266,   -1,   -1,  269,  270,   -1,
   -1,   -1,   -1,  257,   -1,  259,   -1,  288,  289,  263,
   -1,   -1,  266,   -1,   -1,  269,  270,   -1,   -1,   -1,
   -1,  372,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  313,   -1,  315,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  393,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  407,   -1,   -1,   -1,
   -1,   -1,  343,  344,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  427,
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

<<<<<<< HEAD
//#line 494 "gramatica.y"
=======
//#line 546 "gramatica.y"
>>>>>>> rama_fran

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;
private String ambito;

public void activarAmbito(){this.ambito = "$";} // $ va a simblizar el ambito global.

public void setLexico(AnalizadorLexico lexico) { this.lexico = lexico; }

public void setSintactico(AnalizadorSintactico sintactico) { this.sintactico = sintactico; }

public AnalizadorLexico getLexico() { return this.lexico; }

public AnalizadorSintactico getSintactico() { return this.sintactico; }

public void agregarAmbito(String nuevo) {

	this.ambito = this.ambito + "#" + nuevo;

}

public String borrarAmbito(String ambito){
	if (ambito.length() > 1) { // si es 1 solo tiene el ambito global
		String [] aux = ambito.split("#"); // separo los elementos individuales del ambito
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

	String [] aux = ambitoAux.split("#");
	for (int i = 0 ; i < aux.length ; i++){
		int existente = sintactico.getTS().existeEntrada(lexema + ambitoAux);
		if (existente >= 0 ){
			return existente;
		}
		ambitoAux = borrarAmbito(ambitoAux);
	}
	return -1;
}

//#line 897 "Parser.java"
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
//#line 45 "gramatica.y"
{
						int existente = enAmbito(val_peek(2));
						if (existente < 0) {
							String type = sintactico.getTipoFromTS(val_peek(0).ival);
							Atributo id = sintactico.getEntradaTablaSimb(val_peek(2).ival);
							id.setUso("const");
							id.setTipo(type);
							yyval = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja(val_peek(2).ival)), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));
						} else {
							sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
						}

					}
break;
case 13:
//#line 58 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 14:
//#line 59 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 15:
//#line 60 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 16:
//#line 64 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 17:
//#line 65 "gramatica.y"
{ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
                                                              yyval = modificado;}
break;
case 18:
//#line 71 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 19:
//#line 72 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 20:
//#line 73 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 21:
//#line 77 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 22:
//#line 84 "gramatica.y"
{ String type = val_peek(2).sval;
						   sintactico.addAnalisis("Se reconoció declaraciónes de variable de tipo " + type + ". (Línea " + AnalizadorLexico.LINEA + ")");
						   sintactico.completarConTipos(type);
						   }
break;
case 23:
//#line 88 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable");
             					   sintactico.addAnalisis("Se reconoció declaraciónes de variable SIN TIPO. (Línea " + (AnalizadorLexico.LINEA-1) + ")");
             					   sintactico.vaciarListaVariables();
             					 }
break;
case 27:
//#line 98 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 28:
//#line 102 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 34:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 35:
//#line 111 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 36:
//#line 115 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores.");
						 	int existente = enAmbito(val_peek(1));
							if (existente < 0) {
								sintactico.modificarLexema(val_peek(1).ival, this.ambito);
								sintactico.addListaVariables(val_peek(1).ival);
								sintactico.setUso("var", val_peek(1).ival);
							} else {
								sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
							}
						}
break;
case 37:
//#line 125 "gramatica.y"
{
							int existente = enAmbito(val_peek(2));
							if (existente < 0) {
								sintactico.modificarLexema(val_peek(2).ival, this.ambito);
								sintactico.addListaVariables(val_peek(2).ival);
								sintactico.setUso("var", val_peek(2).ival);
							} else {
								sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
							}
						  }
break;
case 38:
//#line 135 "gramatica.y"
{
                   					int existente = enAmbito(val_peek(0));
                   					if (existente < 0) {
                   						sintactico.modificarLexema(val_peek(0).ival, this.ambito);
                   						sintactico.addListaVariables(val_peek(0).ival);
							    	sintactico.setUso("var", val_peek(0).ival);
                   					} else {
                   						sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
                   					}
                   				  }
break;
case 39:
<<<<<<< HEAD
//#line 121 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" );
				        Atributo id = sintactico.getEntradaTablaSimb(val_peek(1).ival);
				        id.setUso("id fun");
                                        id.setTipo(sintactico.getTipo());
				      }
break;
case 40:
//#line 126 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 41:
//#line 129 "gramatica.y"
{String type = val_peek(1).sval;
                            Atributo id = sintactico.getEntradaTablaSimb(val_peek(0).ival);
                            id.setUso("param");
                            id.setTipo(type);}
break;
case 42:
//#line 133 "gramatica.y"
=======
//#line 149 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" );
					String lexema = sintactico.getEntradaTablaSimb(val_peek(1).ival).getLexema();
					int existente = enAmbito(val_peek(1));
					if (existente < 0) { /* no existe el id en el ambito*/
						sintactico.modificarLexema(val_peek(1).ival, this.ambito);
						sintactico.setUso("func", val_peek(1).ival);
						agregarAmbito(lexema);
					} else {
						sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): el identificador ya ha sido utilizado.");
					}
				     }
break;
case 40:
//#line 160 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 41:
//#line 163 "gramatica.y"
{
				int existente = enAmbito(val_peek(0));
				if (existente < 0) {
					sintactico.modificarLexema(val_peek(0).ival, this.ambito);
					sintactico.setUso("param", val_peek(0).ival);
				} else {
					sintactico.addErrorSintactico("SyntaxError. ENC_FUN/PARAMS (Línea " + AnalizadorLexico.LINEA + "): el identificador ya ha sido utilizado.");
				}
			}
break;
case 42:
//#line 172 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 43:
//#line 137 "gramatica.y"
{sintactico.setTipo(val_peek(0).sval); }
break;
case 44:
<<<<<<< HEAD
//#line 138 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 45:
//#line 140 "gramatica.y"
=======
//#line 177 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 45:
//#line 179 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")");
						this.ambito = borrarAmbito(this.ambito);
 						yyval = val_peek(1);}
break;
case 46:
<<<<<<< HEAD
//#line 145 "gramatica.y"
{yyval = val_peek(0);}
break;
case 48:
//#line 147 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 50:
//#line 151 "gramatica.y"
=======
//#line 185 "gramatica.y"
{yyval = val_peek(0);}
break;
case 48:
//#line 187 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 50:
//#line 191 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   yyval = new ParserVal(sintactico.crearNodoControl("return",val_peek(2)));}
break;
case 51:
<<<<<<< HEAD
//#line 153 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 52:
//#line 154 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 53:
//#line 155 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 156 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 62:
//#line 168 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 63:
//#line 169 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 64:
//#line 173 "gramatica.y"
=======
//#line 193 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 52:
//#line 194 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 53:
//#line 195 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 196 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 62:
//#line 208 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 63:
//#line 209 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 64:
//#line 213 "gramatica.y"
>>>>>>> rama_fran
{
													ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
													yyval = modificado;
											 	}
break;
case 65:
<<<<<<< HEAD
//#line 177 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 66:
//#line 180 "gramatica.y"
=======
//#line 217 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 66:
//#line 220 "gramatica.y"
>>>>>>> rama_fran
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 67:
<<<<<<< HEAD
//#line 184 "gramatica.y"
=======
//#line 224 "gramatica.y"
>>>>>>> rama_fran
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 68:
<<<<<<< HEAD
//#line 188 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 69:
//#line 189 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 70:
//#line 198 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 71:
//#line 199 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 72:
//#line 200 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 73:
//#line 204 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(3).ival));
						yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
					   }
break;
case 74:
//#line 207 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 75:
//#line 208 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", val_peek(3), val_peek(1)));}
break;
case 76:
//#line 212 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), val_peek(0)));}
break;
case 77:
//#line 213 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 78:
//#line 216 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 79:
//#line 217 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 80:
//#line 218 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 81:
//#line 219 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 82:
//#line 220 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 83:
//#line 221 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 84:
//#line 226 "gramatica.y"
=======
//#line 228 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 69:
//#line 229 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 70:
//#line 238 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 71:
//#line 239 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 72:
//#line 240 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 73:
//#line 245 "gramatica.y"
{
						int existente = enAmbito(val_peek(3));
						if (existente >= 0) {
							ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
							yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
							sintactico.eliminarEntrada(val_peek(3).ival);
						}
					   }
break;
case 74:
//#line 253 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 75:
//#line 254 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", val_peek(3), val_peek(1)));}
break;
case 76:
//#line 258 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), val_peek(0)));}
break;
case 77:
//#line 259 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 78:
//#line 262 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 79:
//#line 263 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 80:
//#line 264 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 81:
//#line 265 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 82:
//#line 266 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 83:
//#line 267 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 84:
//#line 272 "gramatica.y"
>>>>>>> rama_fran
{ 	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
										sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 85:
<<<<<<< HEAD
//#line 228 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 86:
//#line 229 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 87:
//#line 233 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cond",val_peek(1)));}
break;
case 88:
//#line 234 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 89:
//#line 235 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 90:
//#line 236 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 91:
//#line 240 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(1), val_peek(0)));}
break;
case 92:
//#line 241 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(0), null));}
break;
case 93:
//#line 242 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 94:
//#line 246 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 95:
//#line 247 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 96:
//#line 251 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 97:
//#line 252 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 98:
//#line 255 "gramatica.y"
=======
//#line 274 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 86:
//#line 275 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 87:
//#line 279 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cond",val_peek(1)));}
break;
case 88:
//#line 280 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 89:
//#line 281 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 90:
//#line 282 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 91:
//#line 286 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(1), val_peek(0)));}
break;
case 92:
//#line 287 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(0), null));}
break;
case 93:
//#line 288 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 94:
//#line 292 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 95:
//#line 293 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 96:
//#line 297 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 97:
//#line 298 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 98:
//#line 301 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 										   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 99:
<<<<<<< HEAD
//#line 257 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 100:
//#line 258 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 101:
//#line 261 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 102:
//#line 262 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 103:
//#line 263 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 104:
//#line 266 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 105:
//#line 267 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 106:
//#line 270 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 107:
//#line 271 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 108:
//#line 274 "gramatica.y"
=======
//#line 303 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 100:
//#line 304 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 101:
//#line 307 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 102:
//#line 308 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 103:
//#line 309 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 104:
//#line 312 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 105:
//#line 313 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 106:
//#line 316 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 107:
//#line 317 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 108:
//#line 320 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 											   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 109:
<<<<<<< HEAD
//#line 276 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 110:
//#line 277 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 111:
//#line 280 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 112:
//#line 281 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 113:
//#line 282 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 114:
//#line 285 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 115:
//#line 286 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 116:
//#line 289 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 117:
//#line 290 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 118:
//#line 293 "gramatica.y"
=======
//#line 322 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 110:
//#line 323 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 111:
//#line 326 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 112:
//#line 327 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 113:
//#line 328 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 114:
//#line 331 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 115:
//#line 332 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 116:
//#line 335 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 117:
//#line 336 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 118:
//#line 339 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
												  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 119:
<<<<<<< HEAD
//#line 295 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 120:
//#line 296 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 121:
//#line 299 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 122:
//#line 300 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 123:
//#line 301 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 124:
//#line 304 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 125:
//#line 305 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 126:
//#line 308 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 127:
//#line 309 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 128:
//#line 315 "gramatica.y"
=======
//#line 341 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 120:
//#line 342 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 121:
//#line 345 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 122:
//#line 346 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 123:
//#line 347 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 124:
//#line 350 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 125:
//#line 351 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 126:
//#line 354 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 127:
//#line 355 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 128:
//#line 361 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio una sentencia when");
									yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 129:
<<<<<<< HEAD
//#line 317 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 130:
//#line 318 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 131:
//#line 322 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 132:
//#line 323 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 133:
//#line 324 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 134:
//#line 331 "gramatica.y"
=======
//#line 363 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 130:
//#line 364 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 131:
//#line 368 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 132:
//#line 369 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 133:
//#line 370 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 134:
//#line 378 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
									}
break;
case 135:
<<<<<<< HEAD
//#line 334 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 136:
//#line 335 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 137:
//#line 336 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 138:
//#line 337 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 139:
//#line 341 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));}
break;
case 140:
//#line 344 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 141:
//#line 347 "gramatica.y"
=======
//#line 381 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 136:
//#line 382 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 137:
//#line 383 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 138:
//#line 384 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 139:
//#line 388 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));}
break;
case 140:
//#line 391 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 141:
//#line 395 "gramatica.y"
>>>>>>> rama_fran
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("cond", new ParserVal(sintactico.crearNodo(val_peek(1).sval,identificador,constante))));}
break;
case 142:
<<<<<<< HEAD
//#line 352 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 143:
//#line 353 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 144:
//#line 356 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 145:
//#line 357 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 153:
//#line 368 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 154:
//#line 369 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 156:
//#line 375 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 157:
//#line 376 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 158:
//#line 380 "gramatica.y"
=======
//#line 400 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 143:
//#line 401 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 144:
//#line 404 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 145:
//#line 405 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 153:
//#line 416 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 154:
//#line 417 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 156:
//#line 423 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 157:
//#line 424 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 158:
//#line 428 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										}
break;
case 159:
<<<<<<< HEAD
//#line 383 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 160:
//#line 384 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 161:
//#line 385 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 162:
//#line 386 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 163:
//#line 389 "gramatica.y"
=======
//#line 431 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 160:
//#line 432 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 161:
//#line 433 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 162:
//#line 434 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 163:
//#line 438 "gramatica.y"
>>>>>>> rama_fran
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));}
break;
case 164:
<<<<<<< HEAD
//#line 394 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo(val_peek(1).sval,new ParserVal(sintactico.crearHoja(val_peek(0).ival)),null))));}
break;
case 173:
//#line 405 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 174:
//#line 409 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 175:
//#line 410 "gramatica.y"
=======
//#line 444 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo(val_peek(1).sval,new ParserVal(sintactico.crearHoja(val_peek(0).ival)),null))));}
break;
case 173:
//#line 455 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 174:
//#line 459 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 175:
//#line 460 "gramatica.y"
>>>>>>> rama_fran
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 176:
<<<<<<< HEAD
//#line 418 "gramatica.y"
=======
//#line 468 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
				yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 177:
<<<<<<< HEAD
//#line 420 "gramatica.y"
=======
//#line 470 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                		yyval = new ParserVal(sintactico.crearNodoControl("break", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 178:
<<<<<<< HEAD
//#line 422 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 179:
//#line 425 "gramatica.y"
=======
//#line 472 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 179:
//#line 476 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
						yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 180:
<<<<<<< HEAD
//#line 427 "gramatica.y"
=======
//#line 478 "gramatica.y"
>>>>>>> rama_fran
{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   				yyval = new ParserVal(sintactico.crearNodoControl("continue", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 181:
<<<<<<< HEAD
//#line 429 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 182:
//#line 430 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 183:
//#line 433 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(4).ival, val_peek(2)));}
break;
case 184:
//#line 434 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(3).ival, null));}
break;
case 185:
//#line 437 "gramatica.y"
=======
//#line 480 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 182:
//#line 481 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 183:
//#line 485 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(4).ival, val_peek(2)));}
break;
case 184:
//#line 486 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(3).ival, null));}
break;
case 185:
//#line 489 "gramatica.y"
>>>>>>> rama_fran
{
						yyval = new ParserVal(sintactico.crearNodo("param", val_peek(2), val_peek(0)));
					}
break;
case 186:
<<<<<<< HEAD
//#line 440 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("param", val_peek(0), null));}
break;
case 187:
//#line 444 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 188:
//#line 448 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 190:
//#line 453 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 191:
//#line 454 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 193:
//#line 459 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));}
break;
case 194:
//#line 460 "gramatica.y"
=======
//#line 492 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("param", val_peek(0), null));}
break;
case 187:
//#line 496 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 188:
//#line 500 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 190:
//#line 505 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 191:
//#line 506 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 193:
//#line 511 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));}
break;
case 194:
//#line 512 "gramatica.y"
>>>>>>> rama_fran
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
                  }
break;
case 195:
<<<<<<< HEAD
//#line 466 "gramatica.y"
=======
//#line 518 "gramatica.y"
>>>>>>> rama_fran
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(1).ival));
                    }
break;
case 196:
<<<<<<< HEAD
//#line 473 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 197:
//#line 474 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 198:
//#line 475 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 199:
//#line 476 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 200:
//#line 477 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 201:
//#line 478 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 202:
//#line 482 "gramatica.y"
=======
//#line 525 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 197:
//#line 526 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 198:
//#line 527 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 199:
//#line 528 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 200:
//#line 529 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 201:
//#line 530 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 202:
//#line 534 "gramatica.y"
>>>>>>> rama_fran
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 203:
<<<<<<< HEAD
//#line 486 "gramatica.y"
=======
//#line 538 "gramatica.y"
>>>>>>> rama_fran
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
<<<<<<< HEAD
//#line 1765 "Parser.java"
=======
//#line 1840 "Parser.java"
>>>>>>> rama_fran
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
