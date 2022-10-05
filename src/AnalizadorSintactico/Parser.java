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
import AnalizadorSintactico.ParserVal;

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
public final static short if=259;
public final static short then=260;
public final static short else=261;
public final static short endif=262;
public final static short out=263;
public final static short fun=264;
public final static short return=265;
public final static short break=266;
public final static short i32=267;
public final static short when=268;
public final static short for=269;
public final static short continue=270;
public final static short f32=271;
public final static short cadena=272;
public final static short menorigual=273;
public final static short mayorigual=274;
public final static short distinto=275;
public final static short opasignacion=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    2,    2,    1,    1,    1,    1,    1,
    4,    4,    3,    3,    6,    6,    6,    6,    7,    7,
    7,    7,    7,    9,    9,    9,   16,   16,   17,   17,
   17,   17,   10,   10,   19,   11,   11,   11,   11,   11,
   12,   12,   12,   12,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   21,   22,   15,   15,   15,
   15,   23,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   25,   25,   26,   26,   26,   26,   26,   26,
   26,    5,    5,    5,    5,   14,   27,   27,   27,   28,
   28,   28,   28,   28,   28,   20,   29,   18,   18,   31,
   31,   31,   32,   32,   32,   30,   30,   30,   30,   30,
   30,    8,    8,
};
final static short yylen[] = {                            2,
    3,    2,    3,    1,    2,    3,    2,    1,    3,    3,
    1,    2,    1,    1,    3,    3,    2,    1,    1,    1,
    1,    1,    1,    1,    3,    3,    3,    3,    6,    9,
    9,    4,    9,    8,    1,    4,    4,    3,    3,    2,
    5,    5,    5,    4,    3,    5,    8,   10,    3,    5,
    6,    8,    8,   10,   10,    1,    1,    7,    3,    5,
    6,    1,    9,    9,    9,    9,    9,    9,    9,   11,
   11,   11,    1,    1,    4,    4,    4,    4,    6,    6,
    6,    1,    1,    1,    1,    2,    2,    2,    2,    2,
    2,    4,    4,    4,    4,    1,    3,    5,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,  112,    0,    0,  113,
    2,    0,    0,    0,    0,    0,    0,   13,   14,    0,
   18,   19,   20,   21,   22,   23,    0,    0,    0,  104,
    0,   35,    0,    0,    0,   40,    0,    0,    0,  102,
  103,    0,    0,    0,   96,    0,    0,    0,    0,    0,
    0,    0,    0,    7,    0,    4,    0,    0,    3,    1,
    0,    5,   17,   24,    0,    0,    0,    0,    0,    0,
    0,    0,   11,   83,   82,   86,   84,   85,    0,    0,
    0,   28,    0,    0,    0,  105,   38,   39,    0,    0,
    0,    0,    0,  108,  109,  111,  106,  107,  110,    0,
   49,    0,    0,    0,   27,    0,   59,    0,    0,    0,
   10,    6,   45,    0,    9,   16,    0,   15,    0,    0,
    0,    0,    0,   89,   88,   87,   91,    0,   90,    0,
    0,    0,   12,    0,    0,    0,    0,    0,   37,   36,
  100,  101,    0,    0,   97,   44,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   26,   25,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   73,   74,    0,    0,   50,    0,   56,
    0,   43,   42,   41,   46,   60,    0,   62,    0,    0,
    0,    0,    0,    0,    0,   32,    0,    0,    0,    0,
    0,   94,   95,   92,   93,   77,   78,    0,    0,   75,
   76,    0,    0,    0,    0,    0,    0,   51,    0,   61,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,    0,    0,    0,    0,    0,    0,    0,
    0,   29,    0,    0,    0,   81,   79,   80,    0,    0,
    0,    0,   52,   57,    0,   53,   47,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   34,    0,    0,    0,
    0,    0,    0,    0,   68,   69,   65,   63,   67,   66,
   64,    0,    0,    0,   33,    0,    0,    0,    0,   54,
   55,   48,    0,   30,   31,    0,    0,   72,    0,   71,
   70,
};
final static short yydgoto[] = {                          2,
  180,   16,   17,   72,   73,   18,   19,   20,   65,   21,
   22,   23,   24,   25,   26,   27,   68,   43,   38,  154,
  181,  265,  189,   28,  224,   76,   77,   78,   45,  100,
   39,   40,
};
final static short yysindex[] = {                      -228,
  115,    0,  -39,   86,  -36, -220,    0,   97,  -33,    0,
    0,  222, -232, -219,  -42,  252,    0,    0,    0, -139,
    0,    0,    0,    0,    0,    0,   -2,   52,  -53,    0,
   -5,    0,  140, -210, -177,    0, -165,  153,   56,    0,
    0,  123,   54, -150,    0, -147,  -32,   57,  123, -143,
  -37,  -48,   60,    0,  136,    0, -135,   87,    0,    0,
 -132,    0,    0,    0,    3,   74, -123,  274,  236,   16,
  149,   67,    0,    0,    0,    0,    0,    0,   95, -133,
  -48,    0,  123,  100,   98,    0,    0,    0,   46,  132,
  132,  -19,  -26,    0,    0,    0,    0,    0,    0,  123,
    0,   85,  -31, -110,    0,  -17,    0,  123,    6,  123,
    0,    0,    0, -109,    0,    0, -107,    0, -106, -159,
    4,  107,  302,    0,    0,    0,    0,   91,    0,  -34,
   37,   94,    0, -109,  114,   28,   26, -109,    0,    0,
    0,    0, -100,  170,    0,    0,  102,   61,  106,  -98,
  189,  108,   40,  110,  119,  120,    0,    0,   36, -159,
  124,  -91,  123,  141,  -73,   69,  -72,  -70,  211,  -69,
  131, -109,   40,    0,    0,  132,  137,    0,  317,    0,
  -65,    0,    0,    0,    0,    0,  317,    0,  -61,   26,
   26,   13,   26,   26,  123,    0,  -60, -159,  158,  159,
  123,    0,    0,    0,    0,    0,    0,  142,  -46,    0,
    0,  123,  143,   43,   56,  123, -172,    0,  144,    0,
  -51,  -44,   26,  -40,   -6,   33,  145,  167,   88,  192,
  254,  318,  117,  305,  129,  312,  123,   26,  328,  329,
  317,   84,    0,  335,  350,  157,  353,  361,  381,   26,
  366,    0, -159,  301,  369,    0,    0,    0,   26,  372,
  175,   26,    0,    0, -195,    0,    0,  179,  180,  -23,
  186,  187,  191,  188, -159,  327,    0,  326,  197,   26,
  -15,  198,  400,  105,    0,    0,    0,    0,    0,    0,
    0,  420,  339,  207,    0,  424,  213,  212,  430,    0,
    0,    0,  218,    0,    0,  212,  434,    0,  218,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -29,    0,
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
    0,    0,    0,    0,   -7,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   99,    0,
    0,
};
final static short yygindex[] = {                         0,
    1,   15,  409,  405,  -49,   -8,   29,  -27,    0,    0,
  392,    0,    0,   25,    0,    0,    0,  463,    0,   62,
  299,    0,  293,    0,  360,    0,    0,    0,    0,    0,
  306,   39,
};
final static int YYTABLESIZE=664;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         67,
   33,   15,   83,   47,   80,   35,   52,   35,  104,  148,
  108,   99,  235,   99,  144,   99,   60,  288,   34,   74,
   14,  137,  133,  151,  167,  298,   55,   36,    1,   99,
   99,   99,   99,   98,   81,   98,   48,   98,   66,   57,
   97,   99,   98,  103,  161,   83,  119,  160,  103,   58,
   35,   98,   98,   98,   98,  174,   75,  175,   85,    4,
  283,  118,   74,   74,  153,   44,  284,   83,  174,   50,
  175,  223,   35,  130,  129,   36,   13,    4,    8,   83,
   86,  133,  123,  240,   35,  174,  173,  175,  241,  242,
   87,   13,  159,  162,   14,  168,  105,   90,  191,   75,
   75,  238,   91,   93,  140,  101,   13,    7,  102,   14,
  106,   10,  107,   97,   99,   98,   63,   64,  110,  184,
  113,   63,   74,  115,   14,   42,  114,  204,  141,  142,
   35,  120,  197,  121,  134,  135,   49,  138,   70,   63,
  137,   35,  267,  146,    4,  149,  163,   51,  157,  165,
  158,  188,  170,  172,   13,  178,   70,  186,  196,   75,
  182,  169,   83,  302,  185,  199,  190,   35,  193,  152,
  229,  155,   14,   11,   71,   13,   35,  194,  195,   83,
  201,  198,  202,  205,   35,  206,  211,  188,   13,  212,
  218,  132,   83,   14,  220,  216,  228,   35,  230,  231,
  233,  237,  243,  250,   63,  244,   14,  251,   51,   13,
  252,  234,  245,   59,  192,   79,  247,   29,   30,   29,
   30,   70,  166,   51,  147,  276,   99,   14,   13,   31,
   99,    9,  287,  143,  214,   46,   32,   12,   32,  103,
  287,  264,  150,   99,   99,   99,   14,  293,   98,  253,
  248,   51,   98,   94,   95,   96,  227,  103,  116,  117,
  112,   13,   41,   30,    7,   98,   98,   98,   10,  210,
    7,  127,  128,  236,   10,    8,    4,  239,    4,   14,
    8,    8,    4,    4,   41,   30,    4,    4,    4,  249,
    4,   13,   12,    3,  126,    4,   41,   30,  260,    5,
    6,  139,   69,    7,    8,    9,   70,   10,    3,   14,
    4,   12,  254,   13,    5,    6,  183,   69,    7,    8,
    9,   70,   10,    3,  203,    4,   94,   95,   96,    5,
    6,   14,   69,    7,    8,    9,   70,   10,   63,  266,
   63,   13,   41,   30,   63,   63,   54,   63,   63,   63,
   63,   63,   63,   41,   30,   70,   13,   70,  255,   14,
  301,   70,   70,  257,   70,   70,   70,   70,   70,   70,
  259,    3,  256,    4,   14,  268,   61,    5,    6,   41,
   30,    7,    8,    9,  258,   10,  262,  263,   41,   30,
  269,  111,    3,  271,    4,   82,   41,   30,    5,    6,
   53,  272,    7,    8,    9,    3,   10,    4,   88,   41,
   30,    5,    6,  270,   69,    7,    8,    9,   70,   10,
   56,  273,   53,  275,   62,  277,    3,  278,    4,  179,
  280,  281,    5,    6,  285,  286,    7,    8,    9,   12,
   10,  289,  290,  109,  292,    3,  291,    4,  187,  294,
  295,    5,    6,  296,  299,    7,    8,    9,  300,   10,
  303,  304,  305,   62,  306,   37,  207,  308,  208,  307,
  309,  209,  136,  310,  311,  131,   56,  217,    3,  219,
    4,  215,    0,    0,    5,    6,    0,    0,    7,    8,
    9,  124,   10,  125,    0,   84,  176,    0,    0,    0,
   89,    0,    0,    0,   92,  156,    0,    0,    3,    0,
    4,   92,    0,   37,    5,    6,    0,    0,    7,    8,
    9,    0,   10,    0,    0,  171,    0,    0,    0,  177,
    3,   62,    4,    0,    0,    0,    5,    6,  122,    0,
    7,    8,    9,    0,   10,   84,    0,    0,    0,  221,
  222,    0,  225,  226,    0,    0,    0,    0,    3,    0,
    4,    0,  145,  213,    5,    6,  164,    0,    7,    8,
    9,    0,   10,    3,    0,    4,    0,    0,    0,    5,
    6,    0,  246,    7,    8,    9,    0,   10,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  261,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  274,
    0,    0,    0,    0,    0,    0,    0,    0,  279,    0,
    0,  282,    0,    0,    0,  200,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  297,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  232,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         27,
   40,    1,   40,   40,   58,   45,   40,   45,   41,   41,
   59,   41,   59,   43,   41,   45,   59,   41,   58,   28,
   58,   41,   72,   41,   59,   41,   12,    3,  257,   59,
   60,   61,   62,   41,   40,   43,  257,   45,   41,  272,
   60,   61,   62,   42,   41,   40,   44,   44,   47,  269,
   45,   59,   60,   61,   62,   43,   28,   45,  269,   40,
  256,   59,   71,   72,   59,    4,  262,   40,   43,    8,
   45,   59,   45,   58,   59,   51,   40,   58,   59,   40,
  258,  131,   68,  256,   45,   43,   59,   45,  261,  262,
  256,   40,  120,  121,   58,   59,   40,   42,   59,   71,
   72,   59,   47,   42,   59,  256,   40,  267,  256,   58,
   49,  271,  256,   60,   61,   62,  256,  257,   59,   59,
  256,   40,  131,  256,   58,   40,   40,   59,   90,   91,
   45,   58,  160,  257,   40,  269,   40,   40,   40,   58,
   41,   45,   59,   59,  125,  256,   40,  257,  256,   59,
  257,  151,   59,   40,   40,  256,   58,  256,  123,  131,
   59,  125,   40,   59,   59,  257,   59,   45,   59,  108,
  198,  110,   58,   59,  123,   40,   45,   59,   59,   40,
   40,   58,  256,  256,   45,  256,  256,  187,   40,   59,
  256,  125,   40,   58,  256,   59,  257,   45,   41,   41,
   59,   59,   59,   59,  123,  257,   58,   41,  257,   40,
  123,  258,  257,  256,  153,  269,  257,  257,  258,  257,
  258,  123,  257,  257,  256,  253,  256,   58,   40,  269,
  260,  269,  256,  260,  173,  272,  276,  123,  276,  272,
  256,  241,  260,  273,  274,  275,   58,  275,  256,   58,
  257,  257,  260,  273,  274,  275,  195,  256,  256,  257,
  125,   40,  257,  258,  267,  273,  274,  275,  271,   59,
  267,  256,  257,  212,  271,  256,  257,  216,  259,   58,
  261,  262,  263,  264,  257,  258,  267,  268,  269,  257,
  271,   40,  123,  257,   59,  259,  257,  258,  237,  263,
  264,  256,  266,  267,  268,  269,  270,  271,  257,   58,
  259,  123,   59,   40,  263,  264,  256,  266,  267,  268,
  269,  270,  271,  257,  256,  259,  273,  274,  275,  263,
  264,   58,  266,  267,  268,  269,  270,  271,  257,  256,
  259,   40,  257,  258,  263,  264,  125,  266,  267,  268,
  269,  270,  271,  257,  258,  257,   40,  259,   41,   58,
  256,  263,  264,   59,  266,  267,  268,  269,  270,  271,
   59,  257,  256,  259,   58,   41,  125,  263,  264,  257,
  258,  267,  268,  269,  256,  271,   59,   59,  257,  258,
   41,  256,  257,   41,  259,  256,  257,  258,  263,  264,
    9,   41,  267,  268,  269,  257,  271,  259,  256,  257,
  258,  263,  264,  257,  266,  267,  268,  269,  270,  271,
   12,   41,   31,   58,   16,  125,  257,   59,  259,  260,
   59,  257,  263,  264,  256,  256,  267,  268,  269,  123,
  271,  256,  256,   52,  257,  257,  256,  259,  260,  123,
  125,  263,  264,  257,  257,  267,  268,  269,   59,  271,
   41,  123,  256,   55,   41,    3,  256,  256,  258,  257,
   41,  261,   81,  256,   41,   71,   68,  179,  257,  187,
  259,  176,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,  256,  271,  258,   -1,   33,  137,   -1,   -1,   -1,
   38,   -1,   -1,   -1,   42,  114,   -1,   -1,  257,   -1,
  259,   49,   -1,   51,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,   -1,   -1,  134,   -1,   -1,   -1,  138,
  257,  123,  259,   -1,   -1,   -1,  263,  264,  265,   -1,
  267,  268,  269,   -1,  271,   83,   -1,   -1,   -1,  190,
  191,   -1,  193,  194,   -1,   -1,   -1,   -1,  257,   -1,
  259,   -1,  100,  172,  263,  264,  265,   -1,  267,  268,
  269,   -1,  271,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,  223,  267,  268,  269,   -1,  271,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  238,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  250,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,   -1,
   -1,  262,   -1,   -1,   -1,  163,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  280,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  201,
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
null,null,null,null,null,null,null,"id","cte","if","then","else","endif","out",
"fun","return","break","i32","when","for","continue","f32","cadena",
"menorigual","mayorigual","distinto","opasignacion",
};
final static String yyrule[] = {
"$accept : programa",
"programa : id bloque_sentencias ';'",
"programa : id ';'",
"programa : id bloque_sentencias error",
"bloque : sentencias",
"bloque : bloque sentencias",
"bloque_sentencias : '{' bloque '}'",
"bloque_sentencias : '{' '}'",
"bloque_sentencias : sentencias",
"bloque_sentencias : bloque '}' error",
"bloque_sentencias : '{' bloque error",
"bloque_sentencias_for : sentencias_for",
"bloque_sentencias_for : bloque_sentencias_for sentencias_for",
"sentencias : sentencias_declarativas",
"sentencias : sentencias_ejecutables",
"sentencias_declarativas : tipo lista_de_variables ';'",
"sentencias_declarativas : tipo lista_de_variables error",
"sentencias_declarativas : tipo error",
"sentencias_declarativas : declaracion_func",
"sentencias_ejecutables : asignacion",
"sentencias_ejecutables : salida",
"sentencias_ejecutables : sentencia_if",
"sentencias_ejecutables : expresion_for",
"sentencias_ejecutables : sentencia_when",
"lista_de_variables : id",
"lista_de_variables : lista_de_variables ',' id",
"lista_de_variables : lista_de_variables id error",
"encabezado_func : fun id '('",
"encabezado_func : id '(' error",
"parametro : tipo id ')' ':' tipo '{'",
"parametro : tipo id ',' tipo id ')' ':' tipo '{'",
"parametro : tipo id tipo id ')' ':' tipo '{' error",
"parametro : ')' ':' tipo '{'",
"declaracion_func : encabezado_func parametro bloque return '(' expresion ')' ';' '}'",
"declaracion_func : encabezado_func parametro return '(' expresion ')' ';' '}'",
"op_asignacion : opasignacion",
"asignacion : id op_asignacion expresion ';'",
"asignacion : id op_asignacion expresion error",
"asignacion : id expresion error",
"asignacion : id op_asignacion error",
"asignacion : id expresion_for",
"salida : out '(' cadena ')' ';'",
"salida : out '(' cadena ')' error",
"salida : out '(' cadena error ';'",
"salida : out cadena error ';'",
"salida : '(' cadena error",
"salida : out '(' ')' error ';'",
"sentencia_if : if '(' condicion ')' then cuerpo_if endif ';'",
"sentencia_if : if '(' condicion ')' then cuerpo_if else cuerpo_else endif ';'",
"sentencia_if : if condicion error",
"sentencia_if : if '(' condicion then error",
"sentencia_if : if '(' condicion ')' cuerpo_if error",
"sentencia_if : if '(' condicion ')' then cuerpo_if error ';'",
"sentencia_if : if '(' condicion ')' then cuerpo_if endif error",
"sentencia_if : if '(' condicion ')' then cuerpo_if else cuerpo_else error ';'",
"sentencia_if : if '(' condicion ')' then cuerpo_if else cuerpo_else endif error",
"cuerpo_if : bloque_sentencias",
"cuerpo_else : bloque_sentencias",
"sentencia_when : when '(' condicion ')' then cuerpo_when ';'",
"sentencia_when : when condicion error",
"sentencia_when : when '(' condicion then error",
"sentencia_when : when '(' condicion ')' cuerpo_when error",
"cuerpo_when : bloque_sentencias",
"encabezado_for : for '(' asignacion ';' condicion ';' signo id ')'",
"encabezado_for : for asignacion ';' condicion ';' signo id ')' error",
"encabezado_for : for '(' asignacion ';' condicion ';' signo id error",
"encabezado_for : for '(' asignacion condicion ';' signo id ')' error",
"encabezado_for : for '(' asignacion ';' condicion signo id ')' error",
"encabezado_for : for '(' ';' condicion ';' signo id ')' error",
"encabezado_for : for '(' asignacion ';' ';' signo id ')' error",
"encabezado_for : id ':' for '(' asignacion ';' condicion ';' signo id ')'",
"encabezado_for : ':' for '(' asignacion ';' condicion ';' signo id ')' error",
"encabezado_for : id for '(' asignacion ';' condicion ';' signo id ')' error",
"signo : '+'",
"signo : '-'",
"cuerpo_for : '{' bloque_sentencias_for '}' ';'",
"cuerpo_for : bloque_sentencias_for '}' ';' error",
"cuerpo_for : '{' bloque_sentencias_for ';' error",
"cuerpo_for : '{' bloque_sentencias_for '}' error",
"cuerpo_for : '{' bloque_sentencias_for '}' else cte ';'",
"cuerpo_for : '{' bloque_sentencias_for '}' else ';' error",
"cuerpo_for : '{' bloque_sentencias_for '}' cte ';' error",
"sentencias_for : sentencias_ejecutables",
"sentencias_for : sentencias_declarativas",
"sentencias_for : sentencia_break",
"sentencias_for : sentencia_continue",
"expresion_for : encabezado_for cuerpo_for",
"sentencia_break : break ';'",
"sentencia_break : break cte",
"sentencia_break : break error",
"sentencia_continue : continue ';'",
"sentencia_continue : continue error",
"sentencia_continue : continue ':' id ';'",
"sentencia_continue : continue ':' ';' error",
"sentencia_continue : continue id ';' error",
"sentencia_continue : continue ':' id error",
"condicion : expresion_relacional",
"expresion_relacional : expresion comparador expresion",
"expresion : '(' expresion ')' signo termino",
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

//#line 215 "gramatica.y"

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


//#line 571 "Parser.java"
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
case 3:
//#line 21 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' ."); }
break;
case 9:
//#line 31 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque."); }
break;
case 10:
//#line 32 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): bloque mal cerrado."); }
break;
case 15:
//#line 49 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 16:
//#line 50 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' al final de la declaración de variable."); }
break;
case 17:
//#line 51 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta el nombre de la variable"); }
break;
case 26:
//#line 64 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identificadores."); }
break;
case 28:
//#line 68 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta fun en la definición de la función."); }
break;
case 31:
//#line 73 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre parametros."); }
break;
case 35:
//#line 83 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 37:
//#line 87 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
break;
case 38:
//#line 88 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el operador de asignación."); }
break;
case 39:
//#line 89 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
break;
case 42:
//#line 94 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 43:
//#line 95 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 44:
//#line 96 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 45:
//#line 97 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 46:
//#line 98 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 47:
//#line 102 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 48:
//#line 103 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una sentencia if. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 49:
//#line 104 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de if debe estar entre paréntesis."); }
break;
case 50:
//#line 105 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 51:
//#line 106 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 52:
//#line 107 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endif."); }
break;
case 53:
//#line 108 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endif."); }
break;
case 54:
//#line 109 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endif."); }
break;
case 55:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endIF."); }
break;
case 59:
//#line 120 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis."); }
break;
case 60:
//#line 121 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 61:
//#line 122 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 64:
//#line 128 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
break;
case 65:
//#line 129 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
break;
case 66:
//#line 130 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 67:
//#line 131 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 68:
//#line 132 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
break;
case 69:
//#line 133 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
break;
case 71:
//#line 135 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
break;
case 72:
//#line 136 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
break;
case 76:
//#line 145 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
break;
case 77:
//#line 146 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
break;
case 78:
//#line 147 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 80:
//#line 149 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
break;
case 81:
//#line 150 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el else. "); }
break;
case 89:
//#line 165 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de break."); }
break;
case 91:
//#line 169 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de continue."); }
break;
case 93:
//#line 171 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
break;
case 94:
//#line 172 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
break;
case 95:
//#line 173 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 106:
//#line 196 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 107:
//#line 197 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 108:
//#line 198 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 109:
//#line 199 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 110:
//#line 200 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 111:
//#line 201 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 112:
//#line 204 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 113:
//#line 208 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 950 "Parser.java"
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
