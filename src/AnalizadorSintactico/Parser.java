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

boolean yydebug = true;        //do I want debug output?
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
public final static short endIF=262;
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
    0,    0,    0,    2,    2,    1,    1,    1,    1,    1,
    4,    4,    6,    6,    3,    3,    8,    8,    8,    8,
    7,    7,    7,    7,    7,   10,   10,   10,   17,   17,
   18,   18,   18,   18,   11,   11,   20,   12,   12,   12,
   12,   12,   13,   13,   13,   13,   13,   13,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   22,   23,   16,
   16,   16,   16,   24,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   26,   26,   27,   27,   27,   27,
   27,   27,   27,    5,    5,    5,    5,   15,   28,   28,
   28,   29,   29,   29,   29,   29,   29,   21,   30,   19,
   19,   32,   32,   32,   33,   33,   33,   31,   31,   31,
   31,   31,   31,    9,    9,
};
final static short yylen[] = {                            2,
    3,    2,    3,    1,    2,    3,    2,    1,    3,    3,
    1,    2,    1,    2,    1,    1,    3,    3,    2,    1,
    1,    1,    1,    1,    1,    1,    3,    3,    3,    3,
    6,    9,    9,    4,    9,    8,    1,    4,    4,    3,
    3,    2,    5,    5,    5,    4,    3,    5,    8,   10,
    3,    5,    6,    8,    8,   10,   10,    1,    1,    7,
    3,    5,    6,    1,    9,    9,    9,    9,    9,    9,
    9,   11,   11,   11,    1,    1,    4,    4,    4,    4,
    6,    6,    6,    1,    1,    1,    2,    2,    2,    2,
    2,    2,    2,    4,    4,    4,    4,    1,    3,    5,
    1,    3,    3,    1,    1,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,  114,    0,    0,  115,
    2,    0,    0,    0,    0,    0,    0,   16,   15,    0,
   20,   21,   22,   23,   24,   25,    0,    0,    0,  106,
    0,   37,    0,    0,    0,   42,    0,    0,    0,  104,
  105,    0,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,    0,    7,    0,    4,    0,    0,    3,    1,
    0,    5,   19,   26,    0,    0,    0,    0,    0,    0,
    0,    0,   11,   84,    0,   88,   85,   86,    0,    0,
    0,   30,    0,    0,    0,  107,   40,   41,    0,    0,
    0,    0,    0,  110,  111,  113,  108,  109,  112,    0,
   51,    0,    0,    0,   29,    0,   61,    0,    0,    0,
   10,    6,   47,    0,    9,   18,    0,   17,    0,    0,
    0,    0,    0,   91,   90,   89,   93,    0,   92,    0,
    0,    0,   12,   87,    0,    0,    0,    0,    0,   39,
   38,  102,  103,    0,    0,   99,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   28,   27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   75,   76,    0,    0,   52,    0,
    0,    0,   13,    0,   45,   44,   43,   48,   62,    0,
   64,    0,    0,    0,    0,    0,    0,    0,   34,    0,
    0,    0,    0,    0,   96,   97,   94,   95,   79,   80,
    0,    0,   77,   78,    0,    0,    0,    0,    0,    0,
   14,   53,    0,   63,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   60,    0,    0,    0,
    0,    0,    0,    0,    0,   31,    0,    0,    0,   83,
   81,   82,    0,    0,    0,    0,   54,    0,    0,   55,
   49,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   36,    0,    0,    0,    0,    0,    0,    0,   70,   71,
   67,   65,   69,   68,   66,    0,    0,    0,   35,    0,
    0,    0,    0,   56,   57,   50,    0,   32,   33,    0,
    0,   74,    0,   73,   72,
};
final static short yydgoto[] = {                          2,
  191,   16,   17,   72,   73,  182,   18,   19,   20,   65,
   21,   22,   23,   24,   25,   26,   27,   68,   43,   38,
  155,  184,  269,  192,   28,  228,   76,   77,   78,   45,
  100,   39,   40,
};
final static short yysindex[] = {                      -235,
  122,    0,  -35,  118,  -39, -224,    0,  130,   -9,    0,
    0,  203, -229, -213,    6,  216,    0,    0,    0, -109,
    0,    0,    0,    0,    0,    0,   31,   37,  -42,    0,
    2,    0,   97, -203, -184,    0, -166,  126,   57,    0,
    0,  142,  351, -150,    0, -140,  -37,   80,  142, -131,
  -32,  -19,   72,    0,  138,    0, -123,   98,    0,    0,
 -116,    0,    0,    0,   19,   86, -103,  237,  -41,  -44,
  151,   52,    0,    0,  -99,    0,    0,    0,  121, -104,
  -19,    0,  142,  127,  129,    0,    0,    0,   30,    9,
    9,   -2,  -29,    0,    0,    0,    0,    0,    0,  142,
    0,  108,   23,  -84,    0,  -21,    0,  142,   12,  142,
    0,    0,    0,  -81,    0,    0,  -77,    0,  -74, -186,
  -14,  144,  259,    0,    0,    0,    0,  131,    0,    3,
   21,  133,    0,    0,  -81,  145,   56,   79,  -81,    0,
    0,    0,    0,  -70,  272,    0,    0,  135,   54,  136,
  -68,  170,  140,  105,  141,  146,  148,    0,    0,   66,
 -186,  143,  -54,  142,  176,   78,   73,  103,  110,  -50,
  117,  162,  -81,  105,    0,    0,    9,  196,    0,  -11,
  316,  316,    0,  124,    0,    0,    0,    0,    0,  303,
    0,  147,   79,   79,   43,   79,   79,  142,    0,  -28,
 -186,  301,  336,  142,    0,    0,    0,    0,    0,    0,
  298,   -4,    0,    0,  142,  319,   55,   57,  142, -144,
    0,    0,  333,    0,  139,  159,   79,  166,  167,  168,
  339,  363,  305,  373,  376,  391,  180,  381,  186,  384,
  142,   79,  385,  386,  316,   77,    0,  405,  406,  191,
  408,  409,  410,   79,  394,    0, -186,  328,  395,    0,
    0,    0,   79,  396,  199,   79,    0,  316, -212,    0,
    0,  201,  202,   41,  205,  207,  209,  211, -186,  346,
    0,  334,  219,   79,   70,  220,  419,   90,    0,    0,
    0,    0,    0,    0,    0,  440,  359,  233,    0,  449,
  234,  236,  452,    0,    0,    0,  239,    0,    0,  236,
  456,    0,  239,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -36,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -24,    0,
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
    0, -133,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    8,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -159,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   81,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  101,    0,    0,
};
final static short yygindex[] = {                         0,
  497,   16,   75,  428,  -48,  264,  -26,    4,  -27,    0,
    0,  455,    0,    0,   22,    0,    0,    0,  500,    0,
   -1,  329,    0,  321,    0,  350,    0,    0,    0,    0,
    0,  335,   61,
};
final static int YYTABLESIZE=704;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         67,
   47,   74,   44,  104,   33,  105,   50,   83,  213,   35,
  105,  145,   35,  130,  129,   80,  101,  126,  101,  152,
  101,    1,   34,  133,   36,   14,  162,   55,   83,  161,
   52,   75,   48,   35,  101,  101,  101,  101,  138,  108,
   93,   81,   57,  287,   74,   74,   34,  106,  100,  288,
  100,   83,  100,   35,  239,   58,   35,   97,   99,   98,
   13,  168,  119,  149,   60,   85,  100,  100,  100,  100,
  154,   66,   36,   86,   75,   75,   13,  118,   14,  169,
    7,  292,  133,  123,   10,  175,   56,  176,  141,   87,
   62,   13,  160,  163,   14,   83,   59,  175,   90,  176,
   35,  227,   59,   91,   74,  101,  153,    4,  156,   14,
  302,  244,  187,  242,  174,  102,  245,  246,  183,  105,
   65,  175,   58,  176,  107,    4,    8,   58,   58,   62,
  110,  207,  113,  200,   75,  271,   83,  114,   65,  115,
   72,   35,   56,  120,   83,  170,   63,   64,  306,   35,
  142,  143,  195,  121,  183,  221,  134,   42,   72,   71,
  135,   13,   35,  194,  136,   83,  147,  138,  139,   49,
   35,  150,  217,  233,   35,   51,  132,   13,  158,   14,
   11,   83,  159,  164,  173,  179,   35,  189,  199,  166,
   13,  171,    4,  185,  188,   14,  231,   62,  193,  196,
  201,   36,  202,   65,  197,  210,  198,  211,   14,   13,
  212,  127,  128,  240,  124,  204,  125,  243,  183,  105,
  215,   29,   30,   72,   29,   30,   79,   14,  232,  280,
  144,  101,   46,   31,  103,  101,    9,   51,  151,  264,
   32,  221,   13,   32,   12,   29,   30,   51,  101,  101,
  101,  297,    7,  238,  219,   13,   10,   31,   51,  167,
   14,   59,  112,  100,   32,   41,   30,  100,   41,   30,
   94,   95,   96,   14,  116,  117,   13,    3,  148,    4,
  100,  100,  100,    5,    6,  140,   69,    7,    8,    9,
   70,   10,   12,    3,   14,    4,  291,    7,   13,    5,
    6,   10,   69,    7,    8,    9,   70,   10,    3,  186,
    4,   13,   41,   30,    5,    6,   14,   69,    7,    8,
    9,   70,   10,    8,    4,  291,    4,   54,  206,   14,
    4,    4,  270,  205,    4,    4,    4,   65,    4,   65,
   61,  234,   13,   65,   65,  305,   65,   65,   65,   65,
   65,   65,   82,   41,   30,   13,  237,   72,  208,   72,
   14,   41,   30,   72,   72,  209,   72,   72,   72,   72,
   72,   72,  214,   14,   41,   30,  235,  241,    3,  222,
    4,   88,   41,   30,    5,    6,   41,   30,    7,    8,
    9,  247,   10,  111,    3,  248,    4,  254,   41,   30,
    5,    6,  224,  255,    7,    8,    9,    3,   10,    4,
   97,   99,   98,    5,    6,  249,   69,    7,    8,    9,
   70,   10,  251,  252,  253,   12,    3,  256,    4,  190,
  257,  259,    5,    6,  258,  260,    7,    8,    9,  261,
   10,  262,  263,  266,  267,  272,  273,  274,  275,  276,
  277,  279,  281,  282,  284,  285,  289,  290,  299,    3,
  293,    4,  294,   53,  295,    5,    6,  296,  298,    7,
    8,    9,    3,   10,    4,  300,  303,  304,    5,    6,
  307,  308,    7,    8,    9,   53,   10,  177,  309,  310,
  311,  312,  313,    3,  314,    4,  315,   15,  131,    5,
    6,  122,   37,    7,    8,    9,  109,   10,  268,  220,
  223,  218,    0,    0,    0,    3,    0,    4,    0,    0,
    0,    5,    6,  165,    0,    7,    8,    9,  180,   10,
    4,  181,   84,    0,    5,  137,    0,   89,    0,    8,
    9,   92,  225,  226,    0,  229,  230,    0,   92,    0,
   37,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    0,    4,    0,    0,    0,    5,    6,    0,  157,    7,
    8,    9,  180,   10,    4,    0,  250,    0,    5,    0,
    0,    0,   84,    8,    9,    0,    0,    0,    0,  172,
    0,  265,    0,  178,    0,    0,    0,    0,    0,  146,
    0,    0,    0,  278,    0,    0,    0,    0,    0,    0,
    0,    0,  283,    0,    0,  286,    0,    0,    0,    0,
    0,    0,    0,   94,   95,   96,    0,  216,    0,    0,
    0,    0,    0,  301,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  203,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  236,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         27,
   40,   28,    4,   41,   40,   42,    8,   40,   59,   45,
   47,   41,   45,   58,   59,   58,   41,   59,   43,   41,
   45,  257,   58,   72,    3,   58,   41,   12,   40,   44,
   40,   28,  257,   45,   59,   60,   61,   62,   41,   59,
   42,   40,  272,  256,   71,   72,   58,   49,   41,  262,
   43,   40,   45,   45,   59,  269,   45,   60,   61,   62,
   40,   59,   44,   41,   59,  269,   59,   60,   61,   62,
   59,   41,   51,  258,   71,   72,   40,   59,   58,   59,
  267,   41,  131,   68,  271,   43,   12,   45,   59,  256,
   16,   40,  120,  121,   58,   40,  256,   43,   42,   45,
   45,   59,  262,   47,  131,  256,  108,   40,  110,   58,
   41,  256,   59,   59,   59,  256,  261,  262,  145,   40,
   40,   43,  256,   45,  256,   58,   59,  261,  262,   55,
   59,   59,  256,  161,  131,   59,   40,   40,   58,  256,
   40,   45,   68,   58,   40,  125,  256,  257,   59,   45,
   90,   91,  154,  257,  181,  182,  256,   40,   58,  123,
   40,   40,   45,   59,  269,   40,   59,   41,   40,   40,
   45,  256,  174,  201,   45,  257,  125,   40,  256,   58,
   59,   40,  257,   40,   40,  256,   45,  256,  123,   59,
   40,   59,  125,   59,   59,   58,  198,  123,   59,   59,
   58,  180,  257,  123,   59,  256,   59,  258,   58,   40,
  261,  256,  257,  215,  256,   40,  258,  219,  245,  256,
   59,  257,  258,  123,  257,  258,  269,   58,  257,  257,
  260,  256,  272,  269,  272,  260,  269,  257,  260,  241,
  276,  268,   40,  276,  123,  257,  258,  257,  273,  274,
  275,  279,  267,  258,   59,   40,  271,  269,  257,  257,
   58,  256,  125,  256,  276,  257,  258,  260,  257,  258,
  273,  274,  275,   58,  256,  257,   40,  257,  256,  259,
  273,  274,  275,  263,  264,  256,  266,  267,  268,  269,
  270,  271,  123,  257,   58,  259,  256,  267,   40,  263,
  264,  271,  266,  267,  268,  269,  270,  271,  257,  256,
  259,   40,  257,  258,  263,  264,   58,  266,  267,  268,
  269,  270,  271,  256,  257,  256,  259,  125,  256,   58,
  263,  264,  256,  256,  267,  268,  269,  257,  271,  259,
  125,   41,   40,  263,  264,  256,  266,  267,  268,  269,
  270,  271,  256,  257,  258,   40,   59,  257,  256,  259,
   58,  257,  258,  263,  264,  256,  266,  267,  268,  269,
  270,  271,  256,   58,  257,  258,   41,   59,  257,  256,
  259,  256,  257,  258,  263,  264,  257,  258,  267,  268,
  269,   59,  271,  256,  257,  257,  259,   59,  257,  258,
  263,  264,  256,   41,  267,  268,  269,  257,  271,  259,
   60,   61,   62,  263,  264,  257,  266,  267,  268,  269,
  270,  271,  257,  257,  257,  123,  257,  123,  259,  260,
   58,   41,  263,  264,   59,  256,  267,  268,  269,   59,
  271,  256,   59,   59,   59,   41,   41,  257,   41,   41,
   41,   58,  125,   59,   59,  257,  256,  256,  125,  257,
  256,  259,  256,    9,  256,  263,  264,  257,  123,  267,
  268,  269,  257,  271,  259,  257,  257,   59,  263,  264,
   41,  123,  267,  268,  269,   31,  271,  138,  256,   41,
  257,  256,   41,  257,  256,  259,   41,    1,   71,  263,
  264,  265,    3,  267,  268,  269,   52,  271,  245,  181,
  190,  177,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,   -1,  267,  268,  269,  257,  271,
  259,  260,   33,   -1,  263,   81,   -1,   38,   -1,  268,
  269,   42,  193,  194,   -1,  196,  197,   -1,   49,   -1,
   51,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,  259,   -1,   -1,   -1,  263,  264,   -1,  114,  267,
  268,  269,  257,  271,  259,   -1,  227,   -1,  263,   -1,
   -1,   -1,   83,  268,  269,   -1,   -1,   -1,   -1,  135,
   -1,  242,   -1,  139,   -1,   -1,   -1,   -1,   -1,  100,
   -1,   -1,   -1,  254,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  263,   -1,   -1,  266,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,  173,   -1,   -1,
   -1,   -1,   -1,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  164,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  180,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  204,
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
null,null,null,null,null,null,null,"id","cte","IF","then","ELSE","endIF","out",
"fun","RETURN","BREAK","i32","when","FOR","CONTINUE","f32","cadena",
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
"bloque_sentencias_FOR : sentencias_FOR",
"bloque_sentencias_FOR : bloque_sentencias_FOR sentencias_FOR",
"bloque_sentencias_ejecutables : sentencias_ejecutables",
"bloque_sentencias_ejecutables : bloque_sentencias_ejecutables sentencias_ejecutables",
"sentencias : sentencias_declarativas",
"sentencias : sentencias_ejecutables",
"sentencias_declarativas : tipo lista_de_variables ';'",
"sentencias_declarativas : tipo lista_de_variables error",
"sentencias_declarativas : tipo error",
"sentencias_declarativas : declaracion_func",
"sentencias_ejecutables : asignacion",
"sentencias_ejecutables : salida",
"sentencias_ejecutables : sentencia_IF",
"sentencias_ejecutables : expresion_FOR",
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
"declaracion_func : encabezado_func parametro bloque RETURN '(' expresion ')' ';' '}'",
"declaracion_func : encabezado_func parametro RETURN '(' expresion ')' ';' '}'",
"op_asignacion : opasignacion",
"asignacion : id op_asignacion expresion ';'",
"asignacion : id op_asignacion expresion error",
"asignacion : id expresion error",
"asignacion : id op_asignacion error",
"asignacion : id expresion_FOR",
"salida : out '(' cadena ')' ';'",
"salida : out '(' cadena ')' error",
"salida : out '(' cadena error ';'",
"salida : out cadena error ';'",
"salida : '(' cadena error",
"salida : out '(' ')' error ';'",
"sentencia_IF : IF '(' condicion ')' then cuerpo_IF endIF ';'",
"sentencia_IF : IF '(' condicion ')' then cuerpo_IF ELSE cuerpo_ELSE endIF ';'",
"sentencia_IF : IF condicion error",
"sentencia_IF : IF '(' condicion then error",
"sentencia_IF : IF '(' condicion ')' cuerpo_IF error",
"sentencia_IF : IF '(' condicion ')' then cuerpo_IF error ';'",
"sentencia_IF : IF '(' condicion ')' then cuerpo_IF endIF error",
"sentencia_IF : IF '(' condicion ')' then cuerpo_IF ELSE cuerpo_ELSE error ';'",
"sentencia_IF : IF '(' condicion ')' then cuerpo_IF ELSE cuerpo_ELSE endIF error",
"cuerpo_IF : bloque_sentencias_ejecutables",
"cuerpo_ELSE : bloque_sentencias_ejecutables",
"sentencia_when : when '(' condicion ')' then cuerpo_when ';'",
"sentencia_when : when condicion error",
"sentencia_when : when '(' condicion then error",
"sentencia_when : when '(' condicion ')' cuerpo_when error",
"cuerpo_when : bloque_sentencias",
"encabezado_FOR : FOR '(' asignacion ';' condicion ';' signo id ')'",
"encabezado_FOR : FOR asignacion ';' condicion ';' signo id ')' error",
"encabezado_FOR : FOR '(' asignacion ';' condicion ';' signo id error",
"encabezado_FOR : FOR '(' asignacion condicion ';' signo id ')' error",
"encabezado_FOR : FOR '(' asignacion ';' condicion signo id ')' error",
"encabezado_FOR : FOR '(' ';' condicion ';' signo id ')' error",
"encabezado_FOR : FOR '(' asignacion ';' ';' signo id ')' error",
"encabezado_FOR : id ':' FOR '(' asignacion ';' condicion ';' signo id ')'",
"encabezado_FOR : ':' FOR '(' asignacion ';' condicion ';' signo id ')' error",
"encabezado_FOR : id FOR '(' asignacion ';' condicion ';' signo id ')' error",
"signo : '+'",
"signo : '-'",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' ';'",
"cuerpo_FOR : bloque_sentencias_FOR '}' ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' ELSE cte ';'",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' ELSE ';' error",
"cuerpo_FOR : '{' bloque_sentencias_FOR '}' cte ';' error",
"sentencias_FOR : sentencias_ejecutables",
"sentencias_FOR : sentencia_BREAK",
"sentencias_FOR : sentencia_CONTINUE",
"sentencias_FOR : sentencias_declarativas error",
"expresion_FOR : encabezado_FOR cuerpo_FOR",
"sentencia_BREAK : BREAK ';'",
"sentencia_BREAK : BREAK cte",
"sentencia_BREAK : BREAK error",
"sentencia_CONTINUE : CONTINUE ';'",
"sentencia_CONTINUE : CONTINUE error",
"sentencia_CONTINUE : CONTINUE ':' id ';'",
"sentencia_CONTINUE : CONTINUE ':' ';' error",
"sentencia_CONTINUE : CONTINUE id ';' error",
"sentencia_CONTINUE : CONTINUE ':' id error",
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

//#line 218 "gramatica.y"

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
	sintactico.addErrorSintactico("par: " + string);
}


//#line 581 "Parser.java"
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
//#line 32 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque."); }
break;
case 10:
//#line 33 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): bloque mal cerrado."); }
break;
case 17:
//#line 52 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 18:
//#line 53 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' al final de la declaración de variable."); }
break;
case 19:
//#line 54 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta el nombre de la variable"); }
break;
case 28:
//#line 67 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIFicadores."); }
break;
case 30:
//#line 71 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta fun en la definición de la función."); }
break;
case 33:
//#line 76 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre parametros."); }
break;
case 37:
//#line 86 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 39:
//#line 90 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
break;
case 40:
//#line 91 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el operador de asignación."); }
break;
case 41:
//#line 92 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
break;
case 44:
//#line 97 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 45:
//#line 98 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 46:
//#line 99 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 47:
//#line 100 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 48:
//#line 101 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 49:
//#line 105 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 50:
//#line 106 "gramatica.y"
{ sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 51:
//#line 107 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de IF debe estar entre paréntesis."); }
break;
case 52:
//#line 108 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 53:
//#line 109 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 54:
//#line 110 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endIF."); }
break;
case 55:
//#line 111 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endIF."); }
break;
case 56:
//#line 112 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endIF."); }
break;
case 57:
//#line 113 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endIF."); }
break;
case 61:
//#line 123 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis."); }
break;
case 62:
//#line 124 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
break;
case 63:
//#line 125 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
break;
case 66:
//#line 131 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
break;
case 67:
//#line 132 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
break;
case 68:
//#line 133 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 69:
//#line 134 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 70:
//#line 135 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
break;
case 71:
//#line 136 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
break;
case 73:
//#line 138 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
break;
case 74:
//#line 139 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
break;
case 78:
//#line 148 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
break;
case 79:
//#line 149 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
break;
case 80:
//#line 150 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 82:
//#line 152 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
break;
case 83:
//#line 153 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el ELSE. "); }
break;
case 87:
//#line 159 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias declarativas adentro del FOR"); }
break;
case 91:
//#line 168 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 93:
//#line 172 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
break;
case 95:
//#line 174 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
break;
case 96:
//#line 175 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
break;
case 97:
//#line 176 "gramatica.y"
{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 108:
//#line 199 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 109:
//#line 200 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 110:
//#line 201 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 111:
//#line 202 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 112:
//#line 203 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 113:
//#line 204 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 114:
//#line 207 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 115:
//#line 211 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 964 "Parser.java"
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
