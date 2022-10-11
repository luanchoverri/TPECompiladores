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
   28,   28,   28,   29,   29,   30,   30,   30,   30,   30,
   27,   31,   31,    5,    5,    5,    5,   14,   33,   33,
   33,   34,   34,   34,   34,   24,   24,   24,   24,   35,
   22,   22,   36,   36,   36,   37,   37,   37,   32,   32,
   32,   32,   32,   32,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    2,    2,    2,    0,
    5,    3,    5,    7,    2,    5,    4,    4,    3,    5,
    1,    2,    1,    1,    2,    1,    4,    4,    6,    4,
    5,    5,    5,    4,    3,    5,    6,    8,    8,    5,
    8,    4,    3,    1,    3,    1,    7,    7,    7,    7,
    3,    3,    3,    4,    1,   12,   12,   11,   13,   14,
    3,    1,    1,    1,    1,    1,    2,    1,    2,    3,
    2,    2,    4,    4,    2,    3,    2,    2,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  115,    0,    0,
  116,    0,    0,    0,    0,    9,   10,    0,   16,   17,
   18,   19,   20,   21,    0,   88,   44,    0,   46,    0,
  106,  107,    0,    0,    0,    0,    0,    0,  105,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,   11,   12,   15,   22,    0,    0,    0,    0,
    0,   35,    0,   45,    0,    0,    0,    0,  108,  111,
  112,  114,  113,   82,   83,  109,  110,    0,    0,    0,
    0,    0,    0,   64,    0,   84,    0,   85,   86,   97,
    0,    0,    0,    0,    0,   25,   26,    0,    0,    0,
    0,    0,    0,    4,   55,    0,   14,    0,   13,    0,
   28,    0,    0,   27,    0,   32,    0,    0,   50,    0,
   48,   47,   96,    0,    0,    0,   91,    0,   89,   95,
    0,   92,    0,    0,    7,   87,   62,  103,  104,   54,
    0,    0,    0,   81,    0,    0,    0,    0,    0,    3,
   24,   23,   29,    0,    0,    0,    0,   60,    0,    0,
   90,    0,    0,   63,    8,   53,   52,   51,   56,    0,
    0,    0,    0,    0,    0,    0,    0,   41,    0,    0,
    0,   43,    0,   33,    0,   49,    0,   66,    0,   57,
   94,   93,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,   31,    0,    0,    0,    0,    0,
   72,   71,   73,   69,   67,   70,   68,    0,    0,    0,
    0,   39,    0,   34,    0,   65,   61,   59,   58,    0,
    0,    0,   38,    0,   37,    0,    0,    0,    0,   40,
   36,    0,    0,    0,   75,    0,    0,    0,    0,    0,
    0,   78,    0,    0,    0,    0,   77,   76,    0,    0,
   74,   79,   80,
};
final static short yydgoto[] = {                          2,
    3,   14,  171,  134,  245,   85,   86,   18,   57,   19,
   20,   21,   22,   23,   24,   25,   61,  113,   62,  181,
  182,   35,   30,   36,   87,  189,   46,  172,  246,   26,
   78,   79,   88,   89,   37,   38,   39,
};
final static short yysindex[] = {                      -219,
    0,    0,  242,  -31,   28,  -36,   17,    0,   44,   58,
    0,  272, -212,  -61,  316,    0,    0, -179,    0,    0,
    0,    0,    0,    0,  138,    0,    0,  -51,    0,   73,
    0,    0,    7, -192,  -14,   24,   47,   50,    0, -157,
  -35,   65, -144,  151, -140,   80,   -5, -134,  333, -123,
  272,    0,    0,    0,    0,    0,  -42, -119,   83, -113,
   86,    0,  105,    0,   88, -153,   48,  107,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    7,   84,
  -50,  -47,  181,    0, -107,    0, -106,    0,    0,    0,
    7,    7,   92,  -34, -104,    0,    0, -105,  -28, -103,
   93, -102,   -5,    0,    0,  351,    0,  -98,    0,  -97,
    0, -231,   36,    0, -196,    0,  -92, -101,    0,  -89,
    0,    0,    0,   50,   71, -136,    0,  112,    0,    0,
  114,    0,  -91,  100,    0,    0,    0,    0,    0,    0,
  116,   11,  117,    0,  257,  204,  257,  119,  -84,    0,
    0,    0,    0,  222,   87,   -5,  121,    0,  115,  122,
    0,  -74,  124,    0,    0,    0,    0,    0,    0,  272,
  367,  125,  257,  126,  127, -140,   77,    0,   42,  222,
   64,    0, -196,    0,  -67,    0,  181,    0, -193,    0,
    0,    0,  294,  -62,  -60,  136,  -59,  -58,  140, -140,
 -140,    7,   31,    0,    0,  159,  142,  130,  144,   60,
    0,    0,    0,    0,    0,    0,    0,   71,  146,  205,
   70,    0,  286,    0, -140,    0,    0,    0,    0,   89,
   71,   71,    0,   63,    0,  290,   39,  101,  104,    0,
    0,   71,  181,  147,    0,  106,  334,  335,  120,  164,
  132,    0,  147,  147,  349,  343,    0,    0,  152,  147,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  392,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   54,  -40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   15,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  280,    0,
    0,    0,    0,    0,   -8,    0,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -17,    9,    0,    0,    0,    0,    0,
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
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  190, -154,  464,  515,  591,  -11,    0,    0,
    0,    0,    0,  382,    0,    0,  -83,    0,  -45,    0,
  240,  -12,  -22,    0,  342,    0,  -37,  -88, -158,    0,
 -177,  385,    0,    0,  403,  359,   51,
};
final static int YYTABLESIZE=784;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        102,
  102,  110,  102,   41,  102,   95,  142,   99,  129,   64,
  133,  132,  146,   60,    5,  116,  109,   67,  102,  102,
  102,  102,  101,  101,  102,  101,   28,  101,   74,   29,
   75,  155,  208,  106,  106,    8,  106,    1,  106,   11,
  230,  101,  101,  101,  101,   76,   73,   77,  100,  100,
  106,   34,  101,  238,  239,   29,   43,  174,  175,   50,
   58,   51,  209,   13,  249,   69,  125,   33,  210,  168,
    8,  223,   34,   74,   11,   75,   55,   56,   13,  244,
  149,  202,  102,   45,  196,  251,   34,   90,  250,  222,
   74,   91,   75,   99,  258,  259,   92,   48,   93,  206,
  153,  263,  119,   60,   96,  101,  122,  120,   98,  184,
  234,   97,   74,   74,   75,   75,   44,   34,  229,  158,
  100,  241,  103,   13,  159,  160,   59,   59,  233,  115,
  183,  100,  105,  185,  201,  200,  111,    5,  199,   13,
  112,  138,  139,  114,  117,  118,   83,  123,  136,  137,
  140,  143,  144,   64,   13,  148,  147,  151,  154,  152,
  224,  243,  219,  220,  156,  163,  203,   63,  157,   13,
  161,   60,  162,  177,  166,  169,   99,  176,   59,  186,
  190,  191,  192,  195,  197,  198,   13,  236,  205,  221,
  207,   98,   15,  213,  215,  214,  216,  217,  218,   59,
  225,   49,  227,   13,  231,  127,   83,  128,  130,  131,
   76,   73,   77,  107,  108,  102,  102,   63,  102,  102,
   13,  141,  102,  102,  164,  102,  102,  102,  102,  102,
  102,  145,  102,  102,  102,   40,   94,  187,  101,  101,
  106,  101,  101,   13,   27,  101,  101,  106,  101,  101,
  101,  101,  101,  101,  226,  101,  101,  101,   70,   71,
   72,   13,  232,   31,   32,  100,  167,  100,  100,  243,
   27,  100,  100,   42,  100,  100,  100,  100,  100,  100,
    4,   13,    5,   80,   31,   32,    6,    7,  256,   81,
    8,    9,   10,   82,   11,    4,   13,    5,   31,   32,
   44,    6,    7,  121,   81,    8,    9,   10,   82,   11,
   99,   13,   99,   99,   47,  228,   99,   99,  240,   99,
   99,   99,   99,   99,   99,   98,  170,   98,   98,   65,
   32,   98,   98,   13,   98,   98,   98,   98,   98,   98,
    4,   10,    5,  180,  235,  237,    6,    7,  242,   81,
    8,    9,   10,   82,   11,   13,    4,  247,    5,  193,
  248,  252,    6,    7,   12,   81,    8,    9,   10,   82,
   11,    4,   13,    5,  253,  254,  255,    6,    7,  170,
   81,    8,    9,   10,   82,   11,    4,  257,    5,  260,
   13,    1,    6,    7,   58,   81,    8,    9,   10,   82,
   11,  261,   30,    4,    8,    5,   13,  262,   11,    6,
    7,   66,   81,    8,    9,   10,   82,   11,  212,  204,
    4,  126,    5,   70,   71,   72,    6,    7,   98,   81,
    8,    9,   10,   82,   11,   68,  124,    4,    0,    5,
   52,    0,    0,    6,    7,    0,   81,    8,    9,   10,
   82,   11,    0,    0,    0,    0,    0,  104,    0,    0,
    4,    0,    5,  173,    0,    0,    6,    7,    0,    0,
    8,    9,   10,    0,   11,  150,    0,  178,    4,    0,
    5,    0,    0,    0,    6,    7,  179,    0,    8,    9,
   10,  194,   11,    0,    0,    0,    0,    0,    4,   84,
    5,    0,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,    4,    0,    5,    0,   16,    0,    6,
    7,    0,    0,    8,    9,   10,   16,   11,    4,   53,
    5,    0,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,   84,    0,    0,  135,    0,    0,  211,
    4,    0,    5,    0,    0,    0,    6,    7,    0,    0,
    8,    9,   10,   53,   11,   16,    0,    0,    0,    0,
    0,    0,    4,    0,    5,    0,    0,    0,    6,    7,
    0,    0,    8,    9,   10,    0,   11,    0,    0,    4,
    0,    5,    0,   17,    0,    6,    7,  165,    0,    8,
    9,   10,   17,   11,    0,   54,    0,    4,    0,    5,
    0,    0,    0,    6,    7,    0,    0,    8,    9,   10,
   53,   11,  188,    4,    0,    5,    0,    0,    0,    6,
    7,    0,    0,    8,    9,   10,    0,   11,    0,   54,
    0,   17,    0,    0,    0,    0,    0,    0,    0,    0,
  135,    0,    0,    0,    0,    0,    0,    0,    0,   16,
   16,   16,    0,    0,    0,    0,    0,    0,   16,    0,
    0,  165,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   16,   53,    0,   16,    0,    0,
    0,    0,    0,    0,   53,    0,   54,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  135,   53,    0,    0,
    0,    0,    0,  165,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   17,   17,   17,    0,    0,
    0,    0,    0,    0,   17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   17,   54,    0,   17,    0,    0,    0,    0,    0,    0,
   54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   54,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   44,   43,   40,   45,   41,   41,   45,   59,   61,
   58,   59,   41,   25,    0,   61,   59,   30,   59,   60,
   61,   62,   40,   41,   47,   43,   58,   45,   43,   61,
   45,  115,  187,   42,   43,  267,   45,  257,   47,  271,
  218,   59,   60,   61,   62,   60,   61,   62,   40,   41,
   59,   45,   58,  231,  232,   61,   40,  146,  147,  272,
  257,  123,  256,   40,  242,  258,   79,   40,  262,   59,
  267,   41,   45,   43,  271,   45,  256,  257,   40,   41,
  103,   40,  123,   40,  173,  244,   45,   41,  243,   59,
   43,   42,   45,   40,  253,  254,   47,   40,  256,  183,
  112,  260,  256,  115,   40,  123,   59,  261,   40,  155,
   41,  256,   43,   43,   45,   45,  257,   45,   59,  256,
   41,   59,  257,   40,  261,  262,   41,   41,   59,   44,
   44,  123,  256,  156,   58,   59,  256,  123,  176,   40,
   58,   91,   92,  257,   40,   58,  123,   41,  256,  256,
   59,  256,  258,   61,   40,  258,  260,  256,  123,  257,
  206,  123,  200,  201,  257,  257,  179,  269,  258,   40,
   59,  183,   59,  258,   59,   59,  123,   59,   41,   59,
   59,  256,   59,   59,   59,   59,   40,  225,  125,  202,
  258,  123,    3,  256,   59,  256,  256,  256,   59,   41,
   59,   12,   59,   40,   59,  256,  123,  258,  256,  257,
   60,   61,   62,  256,  257,  256,  257,  269,  259,  260,
   40,  256,  263,  264,  125,  266,  267,  268,  269,  270,
  271,  260,  273,  274,  275,  272,  272,  123,  256,  257,
   51,  259,  260,   40,  276,  263,  264,  256,  266,  267,
  268,  269,  270,  271,  125,  273,  274,  275,  273,  274,
  275,   40,   58,  257,  258,  257,  256,  259,  260,  123,
  276,  263,  264,  257,  266,  267,  268,  269,  270,  271,
  257,   40,  259,  260,  257,  258,  263,  264,  125,  266,
  267,  268,  269,  270,  271,  257,   40,  259,  257,  258,
  257,  263,  264,  256,  266,  267,  268,  269,  270,  271,
  257,   40,  259,  260,  257,  256,  263,  264,  256,  266,
  267,  268,  269,  270,  271,  257,  123,  259,  260,  257,
  258,  263,  264,   40,  266,  267,  268,  269,  270,  271,
  257,  269,  259,  154,   59,  257,  263,  264,   59,  266,
  267,  268,  269,  270,  271,   40,  257,  257,  259,  170,
  257,  256,  263,  264,  123,  266,  267,  268,  269,  270,
  271,  257,   40,  259,   41,   41,  257,  263,  264,  123,
  266,  267,  268,  269,  270,  271,  257,  256,  259,   41,
   40,    0,  263,  264,  257,  266,  267,  268,  269,  270,
  271,   59,  123,  257,  267,  259,   40,  256,  271,  263,
  264,   30,  266,  267,  268,  269,  270,  271,  125,  180,
  257,   80,  259,  273,  274,  275,  263,  264,   44,  266,
  267,  268,  269,  270,  271,   33,   78,  257,   -1,  259,
  125,   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,
  257,   -1,  259,  260,   -1,   -1,  263,  264,   -1,   -1,
  267,  268,  269,   -1,  271,  125,   -1,  256,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,  265,   -1,  267,  268,
  269,  125,  271,   -1,   -1,   -1,   -1,   -1,  257,   36,
  259,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,  257,   -1,  259,   -1,    3,   -1,  263,
  264,   -1,   -1,  267,  268,  269,   12,  271,  257,   15,
  259,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,   80,   -1,   -1,   83,   -1,   -1,  256,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,   -1,   -1,
  267,  268,  269,   49,  271,   51,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,   -1,  267,  268,  269,   -1,  271,   -1,   -1,  257,
   -1,  259,   -1,    3,   -1,  263,  264,  134,   -1,  267,
  268,  269,   12,  271,   -1,   15,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,  269,
  106,  271,  159,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,   -1,  267,  268,  269,   -1,  271,   -1,   49,
   -1,   51,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  187,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  145,
  146,  147,   -1,   -1,   -1,   -1,   -1,   -1,  154,   -1,
   -1,  208,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  170,  171,   -1,  173,   -1,   -1,
   -1,   -1,   -1,   -1,  180,   -1,  106,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  243,  193,   -1,   -1,
   -1,   -1,   -1,  250,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  145,  146,  147,   -1,   -1,
   -1,   -1,   -1,   -1,  154,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  170,  171,   -1,  173,   -1,   -1,   -1,   -1,   -1,   -1,
  180,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  193,
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

//#line 243 "gramatica.y"

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


//#line 583 "Parser.java"
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
case 67:
//#line 141 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");}
break;
case 68:
//#line 142 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir paréntesis la condicion"); }
break;
case 69:
//#line 143 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la condicion."); }
break;
case 70:
//#line 144 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 72:
//#line 148 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 73:
//#line 149 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 76:
//#line 157 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 77:
//#line 158 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 78:
//#line 159 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 79:
//#line 160 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 87:
//#line 177 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 91:
//#line 186 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 94:
//#line 191 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 95:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 97:
//#line 196 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 98:
//#line 197 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 99:
//#line 198 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 107:
//#line 214 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                  }
break;
case 108:
//#line 219 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                    }
break;
case 109:
//#line 224 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 110:
//#line 225 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 111:
//#line 226 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 112:
//#line 227 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 113:
//#line 228 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 114:
//#line 229 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 115:
//#line 232 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 116:
//#line 236 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 988 "Parser.java"
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
