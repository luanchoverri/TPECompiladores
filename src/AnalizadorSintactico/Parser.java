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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    2,    2,    4,    4,    3,    3,
    3,    3,    6,    6,    6,    6,    7,    7,    7,    7,
    7,    9,    9,    9,   16,   16,   17,   17,   18,   18,
   19,   10,   10,   10,   10,   21,   21,   21,   21,   21,
   21,   20,   20,   23,   23,   23,   11,   11,   11,   11,
   12,   12,   12,   12,   12,   12,   13,   13,   13,   13,
   13,   13,   25,   25,   26,   26,   15,   15,   15,   15,
   28,   29,   29,   30,   30,   30,   30,   30,   27,   31,
   31,    5,    5,    5,    5,   14,   33,   33,   33,   34,
   34,   34,   34,   24,   24,   24,   24,   35,   22,   22,
   36,   36,   36,   37,   37,   37,   32,   32,   32,   32,
   32,   32,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    2,    2,    2,    0,
    5,    3,    5,    7,    2,    5,    4,    4,    3,    5,
    1,    2,    1,    1,    2,    1,    4,    4,    6,    4,
    5,    5,    5,    4,    3,    5,    6,    8,    8,    5,
    8,    4,    3,    1,    3,    1,    7,    2,    5,    6,
    1,    4,    1,   12,   12,   11,   13,   14,    3,    1,
    1,    1,    1,    1,    2,    1,    2,    3,    2,    2,
    4,    4,    2,    3,    2,    2,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  113,    0,    0,
  114,    0,    0,    0,    0,    9,   10,    0,   16,   17,
   18,   19,   20,   21,    0,   86,   44,    0,   46,    0,
  104,  105,    0,    0,    0,    0,    0,    0,  103,    0,
    0,    0,    0,    0,    0,   68,    0,    0,    0,    0,
    0,    6,   11,   12,   15,   22,    0,    0,    0,    0,
    0,   35,    0,   45,    0,    0,    0,    0,  106,  109,
  110,  112,  111,   80,   81,  107,  108,    0,    0,    0,
    0,    0,    0,   64,    0,   82,    0,   83,   84,   95,
    0,    0,    0,    0,    0,   25,   26,    0,    0,    0,
    0,    0,    4,   55,    0,   14,    0,   13,    0,   28,
    0,    0,   27,    0,   32,    0,    0,   50,    0,   48,
   47,   94,    0,    0,    0,   89,    0,   87,   93,    0,
   90,    0,    0,    7,   85,   62,  101,  102,   54,    0,
    0,    0,   79,    0,    0,    0,    0,    3,   24,   23,
   29,    0,    0,    0,    0,   60,    0,    0,   88,    0,
    0,   63,    8,   53,   52,   51,   56,   69,    0,    0,
    0,    0,    0,   41,    0,    0,    0,   43,    0,   33,
    0,   49,    0,   66,    0,   57,   92,   91,    0,   70,
    0,    0,    0,    0,    0,   42,   31,    0,    0,    0,
    0,    0,   67,    0,    0,    0,    0,   39,    0,   34,
    0,   65,   61,   59,   58,    0,    0,    0,   38,    0,
   37,    0,    0,    0,    0,   40,   36,    0,    0,    0,
   73,    0,    0,    0,    0,    0,    0,   76,    0,    0,
    0,    0,   75,   74,    0,    0,   72,   77,   78,
};
final static short yydgoto[] = {                          2,
    3,   14,   15,  170,  134,   85,   86,   18,   57,   19,
   20,   21,   22,   23,   24,   25,   61,  112,   62,  177,
  178,   35,   30,   36,   87,  185,   46,  171,  232,   26,
   78,   79,   88,   89,   37,   38,   39,
};
final static short yysindex[] = {                      -225,
    0,    0,  242,   23,   13,  -36,  -13,    0,   47,   74,
    0,  257, -232,  -68,  272,    0,    0, -135,    0,    0,
    0,    0,    0,    0,   89,    0,    0,  -51,    0,   58,
    0,    0,    7, -186,  -14,   24,   50,   53,    0, -146,
  -35,   73, -138,  119, -128,    0,   43, -126,  302, -121,
  257,    0,    0,    0,    0,    0,  -42, -120,   81, -119,
   71,    0,  101,    0,   86, -159,   11,  105,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    7,   84,
  -45,  -47,  181,    0, -114,    0, -107,    0,    0,    0,
    7,    7,   91,  -34, -103,    0,    0, -102,  -28,   90,
 -101,   43,    0,    0,  318,    0,  -97,    0,  -96,    0,
 -160,   37,    0, -179,    0,  -92, -100,    0,  -91,    0,
    0,    0,   53,   80, -196,    0,  107,    0,    0,  109,
    0,  -87,  100,    0,    0,    0,    0,    0,    0,  112,
  -44,  116,    0,  -82,  115,  117,  -73,    0,    0,    0,
    0,  222,   76,   43,  124,    0,  132,  127,    0,  -69,
  129,    0,    0,    0,    0,    0,    0,    0,  181,  181,
  -66, -128,   68,    0,   28,  222,   66,    0, -179,    0,
  -65,    0,  181,    0, -166,    0,    0,    0,  135,    0,
  136, -128, -128,    7,   18,    0,    0,  155,  139,  149,
  140,  -18,    0,   80,  141,  143,   26,    0,  144,    0,
 -128,    0,    0,    0,    0,  -55,   80,   80,    0,   78,
    0,  145,   39,  -52,  -49,    0,    0,   80,  181,  166,
    0,  -15,  259,  260,   87,  205,  106,    0,  166,  166,
  304,  204,    0,    0,  120,  166,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  361,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   54,  -40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  250,    0,
    0,    0,    0,    0,   -8,    0,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -17,    9,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   93,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    6,  -75,  390,  443,  451,    5,    0,    0,
    0,    0,    0,  347,    0,    0, -105,    0,  -25,    0,
  211,    3,   -9,    0,  300,    0,  -29,  219, -141,    0,
 -142,  346,    0,    0,  371,  327,   42,
};
final static int YYTABLESIZE=636;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        100,
  100,  109,  100,   41,  100,   95,  141,  133,  153,   64,
  132,  131,  145,  128,  166,   99,  108,   49,  100,  100,
  100,  100,   99,   99,    5,   99,   43,   99,   74,   60,
   75,    1,   67,  104,  104,  115,  104,  101,  104,   50,
  215,   99,   99,   99,   99,   76,   73,   77,   98,   98,
  104,   34,   33,   74,   51,   75,  105,   34,  209,  156,
   74,  216,   75,   13,  157,  158,  220,  194,   74,  121,
   75,   69,   34,  198,  224,  225,  208,   58,   13,  230,
   28,  124,  100,   29,  219,  235,   45,    8,  237,  201,
   90,   11,  147,   97,   91,  202,  118,  244,  245,   92,
  100,  119,   34,   29,  249,   99,    8,  200,   96,   93,
   11,   59,   96,   48,  114,  151,   59,   97,   60,  179,
   55,   56,   74,   13,   75,  193,  192,  180,   44,   59,
  102,   98,  137,  138,  104,  110,  227,  113,  111,   13,
  116,  135,  191,  117,  181,  122,   83,    5,  136,  139,
   64,   71,  142,  236,   13,  143,  146,  176,  149,  152,
  150,  229,  205,  206,  154,  159,  155,  160,   63,  161,
  164,   13,  210,  168,  167,  172,   97,  195,   76,   73,
   77,  222,  182,   60,  173,  186,  187,  188,   13,  190,
  197,   96,  199,  203,  204,   59,  207,  211,  213,  217,
  218,  223,  221,  228,  233,   13,   83,  234,  129,  130,
  126,  165,  127,  106,  107,  100,  100,   63,  100,  100,
   13,  140,  100,  100,  162,  100,  100,  100,  100,  100,
  100,  144,  100,  100,  100,   40,   94,  214,   99,   99,
  238,   99,   99,   42,   13,   99,   99,  104,   99,   99,
   99,   99,   99,   99,  183,   99,   99,   99,   70,   71,
   72,   13,  247,   31,   32,   98,  120,   98,   98,   31,
   32,   98,   98,  212,   98,   98,   98,   98,   98,   98,
    4,   13,    5,   80,   31,   32,    6,    7,  229,   81,
    8,    9,   10,   82,   11,    4,   13,    5,   27,  239,
  240,    6,    7,   44,   81,    8,    9,   10,   82,   11,
   97,   13,   97,   97,   65,   32,   97,   97,   27,   97,
   97,   97,   97,   97,   97,   96,   10,   96,   96,  242,
   47,   96,   96,  226,   96,   96,   96,   96,   96,   96,
    4,   13,    5,  241,  246,   58,    6,    7,   71,   81,
    8,    9,   10,   82,   11,    8,    4,   13,    5,   11,
    1,  243,    6,    7,   12,   81,    8,    9,   10,   82,
   11,    4,   30,    5,  169,  248,   66,    6,    7,  125,
   81,    8,    9,   10,   82,   11,  196,  189,    4,   98,
    5,   70,   71,   72,    6,    7,   52,   81,    8,    9,
   10,   82,   11,   68,  123,    4,    0,    5,    0,    0,
    0,    6,    7,    0,   81,    8,    9,   10,   82,   11,
    0,    0,    4,    0,    5,   84,  103,    0,    6,    7,
    0,   81,    8,    9,   10,   82,   11,    4,    0,    5,
    0,    0,  148,    6,    7,   16,   81,    8,    9,   10,
   82,   11,    0,   17,   16,    0,    0,   53,    0,    0,
    0,    4,   17,    5,    0,   54,    0,    6,    7,   84,
   81,    8,    9,   10,   82,   11,    0,  174,    4,    0,
    5,    0,    0,    0,    6,    7,  175,    0,    8,    9,
   10,   53,   11,   16,    0,    0,    0,    0,    4,   54,
    5,   17,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,    4,    0,    5,    0,    0,    0,    6,
    7,    0,  163,    8,    9,   10,    0,   11,    4,    0,
    5,    0,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,    0,    0,    0,  184,   53,    0,    0,
    0,    0,    0,    0,    0,   54,    0,    0,    4,  163,
    5,    0,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,    0,    4,    0,    5,    0,    0,    0,
    6,    7,    0,    0,    8,    9,   10,    0,   11,  163,
    0,    0,    0,    0,   16,    0,    0,    0,    0,    0,
    0,    0,   17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  231,    0,    0,    0,    0,    0,   53,  231,
    0,    0,    0,    0,    0,  163,   54,    0,  231,  231,
    0,    0,    0,    0,    0,  231,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   44,   43,   40,   45,   41,   41,   83,  114,   61,
   58,   59,   41,   59,   59,   45,   59,   12,   59,   60,
   61,   62,   40,   41,    0,   43,   40,   45,   43,   25,
   45,  257,   30,   42,   43,   61,   45,   47,   47,  272,
   59,   59,   60,   61,   62,   60,   61,   62,   40,   41,
   59,   45,   40,   43,  123,   45,   51,   45,   41,  256,
   43,  204,   45,   40,  261,  262,   41,   40,   43,   59,
   45,  258,   45,  179,  217,  218,   59,  257,   40,   41,
   58,   79,  123,   61,   59,  228,   40,  267,  230,  256,
   41,  271,  102,   40,   42,  262,  256,  239,  240,   47,
   58,  261,   45,   61,  246,  123,  267,  183,   40,  256,
  271,   41,   40,   40,   44,  111,   41,  256,  114,   44,
  256,  257,   43,   40,   45,   58,   59,  153,  257,   41,
  257,  123,   91,   92,  256,  256,   59,  257,   58,   40,
   40,  256,  172,   58,  154,   41,  123,  123,  256,   59,
   61,   59,  256,  229,   40,  258,  258,  152,  256,  123,
  257,  123,  192,  193,  257,   59,  258,   59,  269,  257,
   59,   40,  198,  256,   59,   59,  123,  175,   60,   61,
   62,  211,   59,  179,  258,   59,  256,   59,   40,  256,
  125,  123,  258,   59,   59,   41,  194,   59,   59,   59,
   58,  257,   59,   59,  257,   40,  123,  257,  256,  257,
  256,  256,  258,  256,  257,  256,  257,  269,  259,  260,
   40,  256,  263,  264,  125,  266,  267,  268,  269,  270,
  271,  260,  273,  274,  275,  272,  272,  256,  256,  257,
  256,  259,  260,  257,   40,  263,  264,  256,  266,  267,
  268,  269,  270,  271,  123,  273,  274,  275,  273,  274,
  275,   40,   59,  257,  258,  257,  256,  259,  260,  257,
  258,  263,  264,  125,  266,  267,  268,  269,  270,  271,
  257,   40,  259,  260,  257,  258,  263,  264,  123,  266,
  267,  268,  269,  270,  271,  257,   40,  259,  276,   41,
   41,  263,  264,  257,  266,  267,  268,  269,  270,  271,
  257,   40,  259,  260,  257,  258,  263,  264,  276,  266,
  267,  268,  269,  270,  271,  257,  269,  259,  260,  125,
  257,  263,  264,  256,  266,  267,  268,  269,  270,  271,
  257,   40,  259,  257,   41,  257,  263,  264,  256,  266,
  267,  268,  269,  270,  271,  267,  257,   40,  259,  271,
    0,  256,  263,  264,  123,  266,  267,  268,  269,  270,
  271,  257,  123,  259,  260,  256,   30,  263,  264,   80,
  266,  267,  268,  269,  270,  271,  176,  169,  257,   44,
  259,  273,  274,  275,  263,  264,  125,  266,  267,  268,
  269,  270,  271,   33,   78,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
   -1,   -1,  257,   -1,  259,   36,  125,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,  257,   -1,  259,
   -1,   -1,  125,  263,  264,    3,  266,  267,  268,  269,
  270,  271,   -1,    3,   12,   -1,   -1,   15,   -1,   -1,
   -1,  257,   12,  259,   -1,   15,   -1,  263,  264,   80,
  266,  267,  268,  269,  270,  271,   -1,  256,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,  265,   -1,  267,  268,
  269,   49,  271,   51,   -1,   -1,   -1,   -1,  257,   49,
  259,   51,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,  133,  267,  268,  269,   -1,  271,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,   -1,   -1,   -1,  157,  105,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  105,   -1,   -1,  257,  170,
  259,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,  264,   -1,   -1,  267,  268,  269,   -1,  271,  200,
   -1,   -1,   -1,   -1,  152,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  152,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  223,   -1,   -1,   -1,   -1,   -1,  176,  230,
   -1,   -1,   -1,   -1,   -1,  236,  176,   -1,  239,  240,
   -1,   -1,   -1,   -1,   -1,  246,
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
"fun","Return","BREAK","i32","when","For","CONTINUE","f32","cadena",
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
"ejecutables : asignacion",
"ejecutables : salida",
"ejecutables : sentencia_If",
"ejecutables : expresion_For",
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
"sentencia_when : when condicion_for",
"sentencia_when : when '(' condicion_for then error",
"sentencia_when : when '(' condicion_for ')' cuerpo_when error",
"cuerpo_when : bloque_sentencias_For",
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

//#line 241 "gramatica.y"

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


//#line 545 "Parser.java"
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
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") "); }
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
//#line 111 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 50:
//#line 113 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  "); }
break;
case 52:
//#line 117 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 53:
//#line 118 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 54:
//#line 119 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 55:
//#line 120 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 56:
//#line 121 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 57:
//#line 125 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 58:
//#line 126 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 59:
//#line 127 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 60:
//#line 128 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 61:
//#line 129 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 62:
//#line 130 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 68:
//#line 142 "gramatica.y"
{ sintactico.addErrorSintactico( " SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis." ); }
break;
case 69:
//#line 143 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 70:
//#line 144 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 74:
//#line 155 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 75:
//#line 156 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 76:
//#line 157 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 77:
//#line 158 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 85:
//#line 175 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 89:
//#line 184 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 92:
//#line 189 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 93:
//#line 190 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 95:
//#line 194 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 96:
//#line 195 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 97:
//#line 196 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 105:
//#line 212 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                  }
break;
case 106:
//#line 217 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                    }
break;
case 107:
//#line 222 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 108:
//#line 223 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 109:
//#line 224 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 110:
//#line 225 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 111:
//#line 226 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 112:
//#line 227 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 113:
//#line 230 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 114:
//#line 234 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 938 "Parser.java"
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
