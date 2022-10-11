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
   28,   29,   29,   29,   29,   27,   30,   30,    5,    5,
    5,    5,   14,   32,   32,   32,   33,   33,   33,   33,
   33,   33,   24,   24,   24,   24,   34,   22,   22,   35,
   35,   35,   36,   36,   36,   31,   31,   31,   31,   31,
   31,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    1,    2,    1,    1,
    2,    2,    3,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    2,    2,    2,    0,
    5,    3,    5,    7,    2,    5,    4,    4,    3,    5,
    1,    2,    1,    1,    2,    1,    4,    4,    6,    4,
    5,    5,    5,    4,    3,    5,    6,    8,    8,    5,
    8,    4,    3,    1,    3,    1,    7,    2,    5,    6,
    1,   15,   12,   17,   14,    3,    1,    1,    1,    1,
    1,    2,    1,    2,    3,    2,    2,    2,    4,    4,
    4,    4,    3,    2,    2,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,  112,    0,    0,
  113,    0,    0,    0,    0,    9,   10,    0,   16,   17,
   18,   19,   20,   21,    0,   83,   44,    0,   46,    0,
  103,  104,    0,    0,    0,    0,    0,    0,  102,    0,
    0,    0,    0,    0,    0,   68,    0,    0,    0,    0,
    6,   11,   12,   15,   22,    0,    0,    0,    0,    0,
   35,    0,   45,    0,    0,    0,    0,  105,  108,  109,
  111,  110,   77,   78,  106,  107,    0,    0,    0,    0,
    0,    0,   64,    0,   79,    0,   80,   81,   94,    0,
    0,    0,    0,    0,   25,   26,    0,    0,    0,    4,
   55,    0,   14,    0,   13,    0,   28,    0,    0,   27,
    0,   32,    0,    0,   50,    0,   48,   47,   93,    0,
    0,    0,   86,    0,   84,   88,    0,   87,    0,    0,
    7,   82,   62,  100,  101,   54,    0,    0,    0,   76,
    0,    0,    0,    0,    3,   24,   23,   29,    0,    0,
    0,    0,   60,    0,    0,   85,    0,    0,    0,   63,
    8,   53,   52,   51,   56,   69,    0,    0,    0,    0,
   41,    0,    0,    0,   43,    0,   33,    0,   49,    0,
   66,    0,   57,   91,   92,   89,   90,    0,   70,    0,
    0,    0,   42,   31,    0,    0,    0,    0,    0,   67,
    0,    0,   39,    0,   34,    0,   65,   61,   59,   58,
    0,   38,    0,   37,    0,    0,   40,   36,    0,    0,
    0,    0,    0,    0,   73,    0,    0,    0,   75,    0,
    0,   72,    0,   74,
};
final static short yydgoto[] = {                          2,
    3,  168,   15,  130,  131,   84,   85,   18,   56,   19,
   20,   21,   22,   23,   24,   25,   60,  109,   61,  174,
  175,   35,   30,   36,   86,  182,   46,  169,   26,   77,
   78,   87,   88,   37,   38,   39,
};
final static short yysindex[] = {                      -166,
    0,    0,  292,  -49,   60,  -34,   15,    0,   16,   44,
    0,  352, -160,   -8,  308,    0,    0, -192,    0,    0,
    0,    0,    0,    0,  126,    0,    0,    7,    0,   30,
    0,    0,  133, -141,  -12,   26,   78,   48,    0, -138,
  -33,   80, -135,  128, -134,    0, -132,  326, -129,  352,
    0,    0,    0,    0,    0,   10, -128,   64, -127,   53,
    0,   89,    0,   75, -163,  -16,   90,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  133,  133,   71,  -46,
  -48,  225,    0, -121,    0, -119,    0,    0,    0,  133,
  133,   79,  -41, -117,    0,    0, -118,  -37,  -42,    0,
    0,  339,    0, -114,    0, -113,    0, -157,   20,    0,
 -200,    0, -111, -122,    0, -110,    0,    0,    0,   48,
   -3, -173,    0,   92,    0,    0,   94,    0,  -44,   86,
    0,    0,    0,    0,    0,    0,   95,   13,   96,    0,
 -100,  251,   99,  -97,    0,    0,    0,    0,  274,   72,
  -42,  103,    0,  101,  104,    0,  -91,   47,  -90,    0,
    0,    0,    0,    0,    0,    0,  292,   -8,  -88,  111,
    0,  105,  274,   46,    0, -200,    0,  -86,    0,  225,
    0, -183,    0,    0,    0,    0,    0,  114,    0, -134,
  133,   18,    0,    0,  135,  121,  118,  123,   50,    0,
  124,   33,    0,  125,    0, -134,    0,    0,    0,    0,
   -3,    0,   73,    0,  127,  -80,    0,    0,   -3,  144,
  -70,  141,  150,  225,    0,  161,  177,  225,    0,  136,
  206,    0,  137,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  192,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   41,  -38,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   74,    0,    0,
    0,    0,    0,   -6,    0,    0,   56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -15,
   11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   77,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  195,    8, -142,  420,  520,  589,   -7,    0,    0,
    0,    0,    0,  169,    0,    0,  -77,    0,  -43,    0,
   27,    2,  -64,    0,  168,    0,  -31,   35,    0, -149,
  159,    0,    0,  171,  129,   12,
};
final static int YYTABLESIZE=762;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        138,
    5,   99,   99,  142,   99,   41,   99,   94,   28,  129,
  128,   29,  125,   98,  159,  143,  112,   59,   29,   48,
   99,   99,   99,   99,   98,   98,   73,   98,   74,   98,
   73,   66,   74,  150,  144,  103,  103,  197,  103,   73,
  103,   74,  118,   98,   98,   98,   98,   75,   72,   76,
   97,   97,  103,  106,   43,   45,   57,  102,  204,    5,
   73,  216,   74,   54,   55,   13,    8,   63,  105,  221,
   11,  164,  198,  213,   34,   73,  203,   74,  199,  121,
   96,  227,  153,   47,   99,  231,  178,  154,  155,   90,
    1,  212,  115,   58,   91,   95,  111,  116,  195,   33,
  148,  134,  135,   59,   34,  186,  177,   98,  210,    8,
   13,   49,   58,   11,   50,  176,   68,   92,   89,   95,
   96,  108,   44,    5,   99,   13,  101,  107,  113,  110,
  119,  218,  114,   97,  132,   71,  133,  136,  139,  140,
   13,  146,  149,  147,  191,  151,   62,  152,   82,   34,
  156,  205,  157,  162,  165,  166,  173,   13,  201,   63,
  170,  179,  183,   96,  184,  187,   58,  189,   59,  190,
  194,  196,  200,  192,  215,   58,  220,   34,   95,  206,
   13,  208,  211,  214,  222,  219,  223,   75,   72,   76,
  226,    1,  202,   82,  232,  234,   30,   14,   65,  193,
   13,  188,   97,   67,    0,  120,    0,  126,  127,  123,
  160,  124,  158,    0,  137,    0,   13,   99,   99,    0,
   99,   99,  141,  180,   99,   99,   27,   99,   99,   99,
   99,   99,   99,   27,   99,   99,   99,   40,   93,  117,
   98,   98,  207,   98,   98,   13,  122,   98,   98,  103,
   98,   98,   98,   98,   98,   98,    5,   98,   98,   98,
   69,   70,   71,  224,   13,  103,  104,   97,  163,   97,
   97,   42,   44,   97,   97,   62,   97,   97,   97,   97,
   97,   97,    4,  228,    5,   79,   64,   32,    6,    7,
   13,   80,    8,    9,   10,   81,   11,   96,   10,   96,
   96,  230,  185,   96,   96,  209,   96,   96,   96,   96,
   96,   96,   95,   13,   95,   95,   31,   32,   95,   95,
    0,   95,   95,   95,   95,   95,   95,    4,  217,    5,
  233,   13,   71,    6,    7,    0,   80,    8,    9,   10,
   81,   11,    4,    0,    5,    0,    0,   13,    6,    7,
    0,   80,    8,    9,   10,   81,   11,    4,    0,    5,
    0,   31,   32,    6,    7,   13,   80,    8,    9,   10,
   81,   11,    0,   12,    4,    0,    5,    0,   13,    0,
    6,    7,   57,   80,    8,    9,   10,   81,   11,   31,
   32,   13,    8,    0,    0,    0,   11,    4,    0,    5,
   69,   70,   71,    6,    7,    0,   80,    8,    9,   10,
   81,   11,    0,    0,   12,    0,    0,    4,    0,    5,
    0,    0,    0,    6,    7,    0,   80,    8,    9,   10,
   81,   11,   51,    4,    0,    5,    0,    0,    0,    6,
    7,    0,   80,    8,    9,   10,   81,   11,    0,    0,
  100,    0,    0,    0,    0,   83,    0,    0,    0,    0,
    0,    0,    4,  145,    5,    0,    0,    0,    6,    7,
    0,   80,    8,    9,   10,   81,   11,    0,    0,    0,
    0,    4,    0,    5,    0,    0,    0,    6,    7,    0,
   80,    8,    9,   10,   81,   11,    0,    0,   83,    0,
    0,    0,    0,    0,    0,    0,    0,    4,    0,    5,
  167,    0,    0,    6,    7,    0,    0,    8,    9,   10,
    0,   11,   16,    0,    0,    0,    0,    0,    0,  171,
    4,   16,    5,    0,   52,    0,    6,    7,  172,    0,
    8,    9,   10,    0,   11,    0,    0,    0,    4,  161,
    5,    0,    0,    0,    6,    7,    0,    0,    8,    9,
   10,    0,   11,    0,    4,    0,    5,   52,    0,   16,
    6,    7,    0,  181,    8,    9,   10,    0,   11,    0,
    0,    0,    4,    0,    5,    0,    0,    0,    6,    7,
    0,   17,    8,    9,   10,    4,   11,    5,    0,    0,
   17,    6,    7,   53,    0,    8,    9,   10,    4,   11,
    5,    0,    0,    0,    6,    7,  161,    0,    8,    9,
   10,   52,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   53,    0,   17,    0,
    0,  225,    0,    0,    0,  229,  161,    0,    0,    0,
  161,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   16,    0,    0,    0,    0,    0,    0,   16,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   16,    0,    0,    0,
   53,    0,   52,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   17,    0,    0,    0,    0,    0,    0,   17,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   17,    0,    0,    0,    0,
    0,   53,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   41,   41,   43,   40,   45,   41,   58,   58,
   59,   61,   59,   45,   59,   58,   60,   25,   61,   12,
   59,   60,   61,   62,   40,   41,   43,   43,   45,   45,
   43,   30,   45,  111,   99,   42,   43,  180,   45,   43,
   47,   45,   59,   59,   60,   61,   62,   60,   61,   62,
   40,   41,   59,   44,   40,   40,  257,   50,   41,   59,
   43,  211,   45,  256,  257,   40,  267,   61,   59,  219,
  271,   59,  256,   41,   45,   43,   59,   45,  262,   78,
   40,  224,  256,   40,  123,  228,  151,  261,  262,   42,
  257,   59,  256,   41,   47,   40,   44,  261,  176,   40,
  108,   90,   91,  111,   45,   59,  150,  123,   59,  267,
   40,  272,   41,  271,  123,   44,  258,  256,   41,   40,
  256,   58,  257,  123,  257,   40,  256,  256,   40,  257,
   41,   59,   58,  123,  256,   59,  256,   59,  256,  258,
   40,  256,  123,  257,   40,  257,  269,  258,  123,   45,
   59,  195,   59,   59,   59,  256,  149,   40,  190,   61,
  258,   59,   59,  123,  256,  256,   41,  256,  176,   59,
  125,  258,   59,  172,  206,   41,  257,   45,  123,   59,
   40,   59,   59,   59,   41,   59,  257,   60,   61,   62,
   41,    0,  191,  123,   59,   59,  123,    3,   30,  173,
   40,  167,   44,   33,   -1,   77,   -1,  256,  257,  256,
  125,  258,  257,   -1,  256,   -1,   40,  256,  257,   -1,
  259,  260,  260,  123,  263,  264,  276,  266,  267,  268,
  269,  270,  271,  276,  273,  274,  275,  272,  272,  256,
  256,  257,  125,  259,  260,   40,   79,  263,  264,  256,
  266,  267,  268,  269,  270,  271,  256,  273,  274,  275,
  273,  274,  275,  123,   40,  256,  257,  257,  256,  259,
  260,  257,  257,  263,  264,  269,  266,  267,  268,  269,
  270,  271,  257,  123,  259,  260,  257,  258,  263,  264,
   40,  266,  267,  268,  269,  270,  271,  257,  269,  259,
  260,  125,  256,  263,  264,  256,  266,  267,  268,  269,
  270,  271,  257,   40,  259,  260,  257,  258,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,  257,  256,  259,
  125,   40,  256,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,  257,   -1,  259,   -1,   -1,   40,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,  257,   -1,  259,
   -1,  257,  258,  263,  264,   40,  266,  267,  268,  269,
  270,  271,   -1,  123,  257,   -1,  259,   -1,   40,   -1,
  263,  264,  257,  266,  267,  268,  269,  270,  271,  257,
  258,   40,  267,   -1,   -1,   -1,  271,  257,   -1,  259,
  273,  274,  275,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,   -1,   -1,  123,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,   -1,  266,  267,  268,  269,
  270,  271,  125,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
  125,   -1,   -1,   -1,   -1,   36,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  125,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,   -1,
  266,  267,  268,  269,  270,  271,   -1,   -1,   79,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,
  260,   -1,   -1,  263,  264,   -1,   -1,  267,  268,  269,
   -1,  271,    3,   -1,   -1,   -1,   -1,   -1,   -1,  256,
  257,   12,  259,   -1,   15,   -1,  263,  264,  265,   -1,
  267,  268,  269,   -1,  271,   -1,   -1,   -1,  257,  130,
  259,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,  268,
  269,   -1,  271,   -1,  257,   -1,  259,   48,   -1,   50,
  263,  264,   -1,  154,  267,  268,  269,   -1,  271,   -1,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,    3,  267,  268,  269,  257,  271,  259,   -1,   -1,
   12,  263,  264,   15,   -1,  267,  268,  269,  257,  271,
  259,   -1,   -1,   -1,  263,  264,  197,   -1,  267,  268,
  269,  102,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   48,   -1,   50,   -1,
   -1,  222,   -1,   -1,   -1,  226,  227,   -1,   -1,   -1,
  231,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  142,   -1,   -1,   -1,   -1,   -1,   -1,  149,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  167,   -1,   -1,   -1,
  102,   -1,  173,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  142,   -1,   -1,   -1,   -1,   -1,   -1,  149,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  167,   -1,   -1,   -1,   -1,
   -1,  173,
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
"cuerpo_when : bloque_sentencias",
"encabezado_For : For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' '{' bloque_sentencias_For '}' ';'",
"encabezado_For : For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' sentencias_For",
"encabezado_For : id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' '{' bloque_sentencias_For '}' ';'",
"encabezado_For : id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' sentencias_For",
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

//#line 244 "gramatica.y"

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


//#line 567 "Parser.java"
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
case 82:
//#line 176 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 86:
//#line 185 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 88:
//#line 189 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
break;
case 90:
//#line 191 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
break;
case 91:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
break;
case 92:
//#line 193 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
break;
case 94:
//#line 197 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 95:
//#line 198 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 96:
//#line 199 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 104:
//#line 215 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                  }
break;
case 105:
//#line 220 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                    }
break;
case 106:
//#line 225 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 107:
//#line 226 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 108:
//#line 227 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 109:
//#line 228 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 110:
//#line 229 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 111:
//#line 230 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 112:
//#line 233 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 113:
//#line 237 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 952 "Parser.java"
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
