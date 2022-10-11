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
    3,   10,   10,   10,   10,   10,   11,   11,   11,   11,
   11,   13,   13,   13,   20,   20,   21,   21,   22,   22,
   23,   14,   14,   14,   14,   25,   25,   25,   25,   25,
   25,   24,   24,    7,    7,    7,   15,   15,   15,   15,
   16,   16,   16,   16,   16,   16,   17,   17,   17,   17,
   17,   17,   28,   28,   29,   29,   19,   19,   19,   19,
   31,   32,   32,   33,   33,   33,   33,   33,   30,   34,
   34,    9,    9,    9,    9,   18,   36,   36,   36,   37,
   37,   37,   37,   27,   27,   27,   27,   38,   26,   26,
   39,   39,   39,   40,   40,   40,   35,   35,   35,   35,
   35,   35,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    2,    1,    1,    1,    1,    1,    1,
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
    2,    0,    0,    0,    0,    0,    0,  123,    0,    0,
  124,    0,    0,    0,    0,    0,   26,   18,   19,    0,
   25,   27,   28,   29,   30,   31,    0,   96,   54,    0,
   56,    0,  114,  115,    0,    0,    0,    0,    0,    0,
  113,    0,    0,    0,    0,    0,    0,   78,    0,    0,
    0,    0,    0,   10,    0,    0,    0,    6,   20,   21,
   24,   32,    0,    0,    0,    0,    0,   45,    0,   55,
    0,    0,    0,    0,  116,  119,  120,  122,  121,   90,
   91,  117,  118,    0,    0,    0,    0,    0,    0,   74,
    0,   92,    0,   93,   94,  105,    0,    0,    0,    0,
    0,   35,   36,    0,    0,    0,    0,    0,   15,    0,
    0,    9,    8,    7,    0,    4,   65,    0,   23,    0,
   22,    0,   38,    0,    0,   37,    0,   42,    0,    0,
   60,    0,   58,   57,  104,    0,    0,    0,   99,    0,
   97,  103,    0,  100,    0,    0,   16,   95,   72,  111,
  112,   64,    0,    0,    0,   89,    0,    0,    0,    0,
   14,   13,   12,   11,    3,   34,   33,   39,    0,    0,
    0,    0,   70,    0,    0,   98,    0,    0,   73,   17,
   63,   62,   61,   66,   79,    0,    0,    0,    0,    0,
   51,    0,    0,    0,   53,    0,   43,    0,   59,    0,
   76,    0,   67,  102,  101,    0,   80,    0,    0,    0,
    0,    0,   52,   41,    0,    0,    0,    0,    0,   77,
    0,    0,    0,    0,   49,    0,   44,    0,   75,   71,
   69,   68,    0,    0,    0,   48,    0,   47,    0,    0,
    0,    0,   50,   46,    0,    0,    0,   83,    0,    0,
    0,    0,    0,    0,   86,    0,    0,    0,    0,   85,
   84,    0,    0,   82,   87,   88,
};
final static short yydgoto[] = {                          2,
    3,   15,   16,   17,   53,   54,   32,  187,  147,   91,
   92,   20,   63,   21,   22,   23,   24,   25,   26,   27,
   67,  125,   68,  194,  195,   37,   38,   93,  202,   48,
  188,  249,   28,   84,   85,   94,   95,   39,   40,   41,
};
final static short yysindex[] = {                      -232,
    0,    0,  254,  -28,  -13,  -36,  -32,    0,   29,   44,
    0,  -53,  300, -215,  -63,  269,    0,    0,    0, -182,
    0,    0,    0,    0,    0,    0,  141,    0,    0,  -54,
    0,   10,    0,    0,  130, -169,  111,   21,   62,   -6,
    0, -151,  -31,   70, -143,  117, -140,    0,   40, -136,
  -44, -142,   41,    0,  285, -133,  300,    0,    0,    0,
    0,    0,  101, -130,   69, -124,   49,    0,   94,    0,
   78, -190,    5,   98,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  130,  130,   82,  -50,  -47,  215,    0,
 -116,    0, -115,    0,    0,    0,  130,  130,   83,    8,
 -113,    0,    0, -112,  -39,   86, -110,   40,    0, -107,
 -161,    0,    0,    0, -106,    0,    0,  322,    0, -103,
    0, -102,    0, -179,   38,    0, -189,    0,  -95, -104,
    0,  -92,    0,    0,    0,   -6,   59, -222,    0,  104,
    0,    0,  108,    0,  -89,   97,    0,    0,    0,    0,
    0,    0,  110,   76,  121,    0,  -75,  112,  124,  -74,
    0,    0,    0,    0,    0,    0,    0,    0,  231,   55,
   40,  127,    0,  136,  128,    0,  -68,  132,    0,    0,
    0,    0,    0,    0,    0,  215,  215,  -67, -140,   50,
    0,   85,  231,   68,    0, -189,    0,  -66,    0,  215,
    0, -203,    0,    0,    0,  135,    0,  137, -140, -140,
  130,   13,    0,    0,  154,  139,  157,  142,   91,    0,
   59,  143,  145,   22,    0,  148,    0, -140,    0,    0,
    0,    0,  -46,   59,   59,    0,   99,    0,  159,   36,
  113,  116,    0,    0,   59,  215,  173,    0,   56,  221,
  356,  144,  198,  153,    0,  173,  173,  370,  358,    0,
    0,  163,  173,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  415,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,  -40,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   15,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  299,    0,    0,    0,    0,    0,
   73,    0,    0,   67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -17,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  140,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   16,    0,    0,  314,  -14,  -76,  420,  457,
  488,    4,    0,    0,    0,    0,    0,  399,    0,    0,
 -109,    0,  -51,    0,  240,  386,    0,  349,    0,  391,
  259, -177,    0, -183,  400,    0,    0,  413,  365,   14,
};
final static int YYTABLESIZE=683;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        110,
  110,  158,  110,   43,  110,   52,   70,   45,  141,  101,
  145,  144,  146,  106,    5,  128,   31,  170,  110,  110,
  110,  110,  109,  109,    1,  109,   35,  109,   55,   30,
   66,   36,   31,  173,  107,   97,  111,  233,  174,  175,
   98,  109,  109,  109,  109,  108,  108,   80,  154,   81,
  241,  242,  218,  226,   36,   80,   56,   81,  219,   57,
   14,  252,  237,  134,   80,  131,   81,   64,   47,  254,
  132,  225,  118,   61,   62,   14,  247,    8,  261,  262,
  236,   11,  110,   50,  115,  266,  215,    8,   75,   65,
  107,   11,  127,  160,  162,   65,  163,  106,  196,  114,
   31,   80,   96,   81,   99,  109,  106,  210,  209,  102,
  150,  151,  103,  112,  114,  114,   46,  114,  197,  114,
  108,   14,  117,  217,  211,  123,  124,  168,  108,   36,
   66,  114,  126,  129,  183,  130,   14,    5,  135,  148,
  149,  152,  155,   89,  122,  156,   70,  159,  161,  232,
   51,   14,  166,   80,  167,   81,  198,  244,  246,  121,
  169,  171,  176,  227,   69,  172,  177,  178,  181,  253,
   82,   79,   83,  107,   36,   14,   82,   79,   83,  184,
  185,   65,  189,  190,  193,  199,  203,  204,  207,  106,
  205,  216,  214,  220,   65,  221,   14,  228,   81,   66,
  230,  234,  235,   51,   89,  139,  238,  140,  142,  143,
  240,  109,   14,  110,   69,  110,  110,  245,  110,  110,
  157,  179,  110,  110,   44,  110,  110,  110,  110,  110,
  110,   29,  110,  110,  110,   42,  110,   14,  109,  109,
  100,  109,  109,   33,   34,  109,  109,   29,  109,  109,
  109,  109,  109,  109,   14,  109,  109,  109,  200,  109,
  133,  256,  108,  153,  108,  108,   71,   34,  108,  108,
   14,  108,  108,  108,  108,  108,  108,    4,   10,    5,
   86,  229,  108,    6,    7,   46,   87,    8,    9,   10,
   88,   11,    4,   14,    5,  246,  113,   12,    6,    7,
   49,   87,    8,    9,   10,   88,   11,  107,   14,  107,
  107,  255,   12,  107,  107,   29,  107,  107,  107,  107,
  107,  107,  259,  106,   14,  106,  106,  107,  114,  106,
  106,  182,  106,  106,  106,  106,  106,  106,    4,   14,
    5,   33,   34,  106,    6,    7,  231,   87,    8,    9,
   10,   88,   11,    4,  243,    5,  119,  120,   12,    6,
    7,   14,   87,    8,    9,   10,   88,   11,    4,  250,
    5,  186,  251,   12,    6,    7,   13,   87,    8,    9,
   10,   88,   11,   76,   77,   78,   33,   34,   12,   76,
   77,   78,    4,   58,    5,   81,  257,   64,    6,    7,
  258,   87,    8,    9,   10,   88,   11,    8,  260,  116,
  263,   11,   12,    4,    1,    5,  264,   73,  265,    6,
    7,   40,   87,    8,    9,   10,   88,   11,  164,    4,
   72,    5,  213,   12,  138,    6,    7,  105,   87,    8,
    9,   10,   88,   11,  206,  104,  165,   74,  136,   12,
    0,    0,    0,    0,    4,    0,    5,   90,    0,   18,
    6,    7,    0,   87,    8,    9,   10,   88,   11,   18,
  137,    4,   59,    5,   12,    0,    0,    6,    7,    0,
   87,    8,    9,   10,   88,   11,  191,    4,    0,    5,
   19,   12,    0,    6,    7,  192,    0,    8,    9,   10,
   19,   11,    0,   60,    0,   90,    0,   12,    0,    0,
    4,   59,    5,   18,    0,    0,    6,    7,    0,    0,
    8,    9,   10,    0,   11,    4,    0,    5,    0,    0,
   12,    6,    7,    0,    0,    8,    9,   10,    0,   11,
    0,    4,   60,    5,   19,   12,    0,    6,    7,    0,
    0,    8,    9,   10,    0,   11,    4,    0,    5,    0,
    0,   12,    6,    7,    0,  180,    8,    9,   10,    0,
   11,    0,    0,    0,   59,    0,   12,  212,    4,  208,
    5,    0,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,  201,    0,    0,  224,    0,   12,  222,
  223,    0,    0,    0,    0,   60,  180,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  239,    0,
    0,    0,    0,    0,    0,   18,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  180,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   59,
    0,    0,    0,    0,    0,    0,   19,    0,    0,  248,
    0,    0,    0,    0,    0,    0,  248,    0,    0,    0,
    0,    0,  180,    0,    0,  248,  248,    0,    0,    0,
   60,    0,  248,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   41,   43,   40,   45,   59,   61,   40,   59,   41,
   58,   59,   89,   58,    0,   67,   61,  127,   59,   60,
   61,   62,   40,   41,  257,   43,   40,   45,   13,   58,
   27,   45,   61,  256,   49,   42,   51,  221,  261,  262,
   47,   59,   60,   61,   62,   40,   41,   43,   41,   45,
  234,  235,  256,   41,   45,   43,  272,   45,  262,  123,
   40,  245,   41,   59,   43,  256,   45,  257,   40,  247,
  261,   59,   57,  256,  257,   40,   41,  267,  256,  257,
   59,  271,  123,   40,   44,  263,  196,  267,  258,   41,
   40,  271,   44,  108,  256,   41,  258,   58,   44,   59,
   61,   43,   41,   45,  256,  123,   40,   58,   59,   40,
   97,   98,  256,  256,   42,   43,  257,   45,  170,   47,
  257,   40,  256,  200,   40,  256,   58,  124,  123,   45,
  127,   59,  257,   40,   59,   58,   40,  123,   41,  256,
  256,   59,  256,  123,   44,  258,   61,  258,  256,   59,
  257,   40,  256,   43,  257,   45,  171,   59,  123,   59,
  123,  257,   59,  215,  269,  258,   59,  257,   59,  246,
   60,   61,   62,  123,   45,   40,   60,   61,   62,   59,
  256,   41,   59,  258,  169,   59,   59,  256,  256,  123,
   59,  258,  125,   59,   41,   59,   40,   59,   59,  196,
   59,   59,   58,  257,  123,  256,   59,  258,  256,  257,
  257,  256,   40,  258,  269,  256,  257,   59,  259,  260,
  260,  125,  263,  264,  257,  266,  267,  268,  269,  270,
  271,  276,  273,  274,  275,  272,  277,   40,  256,  257,
  272,  259,  260,  257,  258,  263,  264,  276,  266,  267,
  268,  269,  270,  271,   40,  273,  274,  275,  123,  277,
  256,   41,  257,  256,  259,  260,  257,  258,  263,  264,
   40,  266,  267,  268,  269,  270,  271,  257,  269,  259,
  260,  125,  277,  263,  264,  257,  266,  267,  268,  269,
  270,  271,  257,   40,  259,  123,  256,  277,  263,  264,
  257,  266,  267,  268,  269,  270,  271,  257,   40,  259,
  260,  256,  277,  263,  264,  276,  266,  267,  268,  269,
  270,  271,  125,  257,   40,  259,  260,  277,  256,  263,
  264,  256,  266,  267,  268,  269,  270,  271,  257,   40,
  259,  257,  258,  277,  263,  264,  256,  266,  267,  268,
  269,  270,  271,  257,  256,  259,  256,  257,  277,  263,
  264,   40,  266,  267,  268,  269,  270,  271,  257,  257,
  259,  260,  257,  277,  263,  264,  123,  266,  267,  268,
  269,  270,  271,  273,  274,  275,  257,  258,  277,  273,
  274,  275,  257,  125,  259,  256,   41,  257,  263,  264,
  257,  266,  267,  268,  269,  270,  271,  267,  256,  125,
   41,  271,  277,  257,    0,  259,   59,   32,  256,  263,
  264,  123,  266,  267,  268,  269,  270,  271,  115,  257,
   32,  259,  193,  277,   86,  263,  264,   47,  266,  267,
  268,  269,  270,  271,  186,   46,  125,   35,   84,  277,
   -1,   -1,   -1,   -1,  257,   -1,  259,   38,   -1,    3,
  263,  264,   -1,  266,  267,  268,  269,  270,  271,   13,
   85,  257,   16,  259,  277,   -1,   -1,  263,  264,   -1,
  266,  267,  268,  269,  270,  271,  256,  257,   -1,  259,
    3,  277,   -1,  263,  264,  265,   -1,  267,  268,  269,
   13,  271,   -1,   16,   -1,   86,   -1,  277,   -1,   -1,
  257,   55,  259,   57,   -1,   -1,  263,  264,   -1,   -1,
  267,  268,  269,   -1,  271,  257,   -1,  259,   -1,   -1,
  277,  263,  264,   -1,   -1,  267,  268,  269,   -1,  271,
   -1,  257,   55,  259,   57,  277,   -1,  263,  264,   -1,
   -1,  267,  268,  269,   -1,  271,  257,   -1,  259,   -1,
   -1,  277,  263,  264,   -1,  146,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,  118,   -1,  277,  192,  257,  189,
  259,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,  174,   -1,   -1,  211,   -1,  277,  209,
  210,   -1,   -1,   -1,   -1,  118,  187,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  228,   -1,
   -1,   -1,   -1,   -1,   -1,  169,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  217,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  193,
   -1,   -1,   -1,   -1,   -1,   -1,  169,   -1,   -1,  240,
   -1,   -1,   -1,   -1,   -1,   -1,  247,   -1,   -1,   -1,
   -1,   -1,  253,   -1,   -1,  256,  257,   -1,   -1,   -1,
  193,   -1,  263,
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

//#line 252 "gramatica.y"

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


//#line 574 "Parser.java"
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
case 51:
//#line 106 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN5(Línea " + AnalizadorLexico.LINEA + "): no se reconoce return"); }
break;
case 54:
//#line 114 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 55:
//#line 115 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 56:
//#line 116 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 58:
//#line 121 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 60:
//#line 123 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  "); }
break;
case 62:
//#line 127 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 63:
//#line 128 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 64:
//#line 129 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 65:
//#line 130 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 66:
//#line 131 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 67:
//#line 135 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 68:
//#line 136 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 69:
//#line 137 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 70:
//#line 138 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 71:
//#line 139 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 72:
//#line 140 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 78:
//#line 152 "gramatica.y"
{ sintactico.addErrorSintactico( " SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis." ); }
break;
case 79:
//#line 153 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 80:
//#line 154 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 84:
//#line 165 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 85:
//#line 166 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 86:
//#line 167 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 87:
//#line 168 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 95:
//#line 185 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 99:
//#line 194 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 102:
//#line 199 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 103:
//#line 200 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 105:
//#line 204 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 106:
//#line 205 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 107:
//#line 206 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 115:
//#line 222 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                  }
break;
case 116:
//#line 227 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                    }
break;
case 117:
//#line 232 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 118:
//#line 233 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 119:
//#line 234 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 120:
//#line 235 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 121:
//#line 236 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 122:
//#line 237 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 123:
//#line 240 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 124:
//#line 244 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 991 "Parser.java"
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
