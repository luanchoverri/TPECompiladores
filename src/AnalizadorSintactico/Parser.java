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
    3,   10,   10,   10,   10,   10,   10,   16,   16,   11,
   11,   11,   11,   11,   11,   11,   13,   13,   13,   24,
   24,   25,   25,   26,   26,   27,   14,   14,   14,   14,
   29,   29,   29,   29,   29,   28,   32,   32,   32,   32,
   32,   32,   32,   32,   35,   35,   31,   31,   31,   31,
    7,    7,    7,   17,   17,   17,   36,   36,   18,   18,
   18,   18,   18,   18,   19,   19,   19,   37,   37,   37,
   37,   38,   38,   38,   40,   40,   41,   41,   42,   42,
   42,   43,   43,   43,   44,   44,   45,   45,   33,   33,
   33,   46,   46,   46,   47,   47,   48,   48,   49,   49,
   49,   50,   50,   50,   51,   51,   52,   52,   15,   15,
   15,   56,   56,   56,   57,   57,   57,   57,   57,   58,
   62,   55,   59,   59,   60,   60,    9,    9,    9,    9,
    9,    9,    9,    9,    9,   20,   65,   65,   34,   34,
   34,   34,   34,   61,   63,   54,   54,   54,   54,   54,
   54,   54,   54,   54,   53,   53,   22,   22,   22,   23,
   23,   23,   23,   21,   21,   66,   66,   39,   30,   30,
   68,   68,   68,   67,   67,   67,   64,   64,   64,   64,
   64,   64,   12,   12,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    2,    2,    3,    3,    3,    1,
    3,    3,    3,    3,    2,    1,    2,    1,    1,    2,
    2,    3,    3,    2,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    2,    2,    1,    3,    3,    3,
    3,    2,    2,    2,    0,    5,    3,    5,    7,    2,
    5,    5,    5,    4,    5,    1,    1,    1,    1,    1,
    1,    1,    2,    2,    2,    1,    2,    2,    1,    1,
    1,    2,    1,    4,    4,    4,    3,    2,    5,    5,
    5,    4,    3,    5,    5,    6,    5,    3,    2,    2,
    1,    2,    1,    1,    4,    2,    4,    2,    5,    5,
    4,    2,    1,    2,    4,    2,    4,    2,    5,    5,
    4,    2,    1,    2,    4,    2,    4,    2,    5,    5,
    4,    2,    1,    2,    4,    2,    4,    2,    6,    6,
    6,    4,    4,    4,    5,   12,   11,   13,   14,    3,
    3,    3,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    2,    1,    4,    1,    5,   12,
   11,   13,   14,    3,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    2,    2,    3,    2,    2,
    4,    4,    2,    5,    4,    3,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,  203,    0,
    0,    0,  204,    0,    0,    0,    0,    0,   26,   18,
   19,    0,   25,   27,   30,   31,   32,   33,   34,    0,
    0,    0,  156,   71,    0,    0,   73,    0,  194,  195,
    0,    0,    0,    0,    0,  193,    0,    0,    0,    0,
    0,  179,    0,  177,    0,    0,    0,    0,    0,  183,
    0,  180,    0,    0,    0,    0,   10,    0,    0,    0,
    6,   20,   21,   24,   37,    0,   35,   36,    0,    0,
    0,    0,   50,    0,    0,    0,    0,   72,    0,    0,
    0,    0,    0,  196,  199,  200,  202,  201,  145,  146,
  197,  198,    0,    0,    0,    0,    0,    0,   94,   89,
    0,    0,    0,    0,    0,   40,   41,  178,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   15,    0,
    0,    9,    8,    7,    0,    4,   83,    0,   23,    0,
   22,    0,   43,    0,    0,   42,    0,   47,  185,    0,
    0,    0,    0,   78,    0,   75,   74,   76,   88,    0,
    0,    0,    0,   96,    0,    0,   98,    0,   92,  191,
  192,   82,    0,    0,    0,  142,    0,    0,    0,    0,
    0,    0,    0,    0,  182,  181,   14,   13,   12,   11,
    3,   39,   38,   44,    0,    0,  184,  186,    0,   77,
   29,    0,   87,    0,    0,   85,   81,   80,   79,   84,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  144,    0,  147,  148,  149,  151,  152,  153,    0,  150,
  135,    0,  140,    0,    0,    0,   70,   57,   59,   60,
    0,    0,    0,   62,    0,   69,   58,   61,    0,   48,
    0,   95,   28,   86,   97,    0,    0,  129,  131,  130,
    0,    0,    0,    0,    0,    0,   16,  154,  155,    0,
    0,    0,    0,    0,   63,   64,   46,   68,   67,    0,
    0,  133,  132,  134,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   17,    0,  141,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   49,    0,    0,
    0,    0,  106,    0,  108,  101,    0,  102,  104,    0,
    0,   54,    0,  143,  165,    0,    0,  116,    0,  118,
  111,    0,  112,  114,    0,    0,    0,    0,    0,    0,
    0,    0,  100,   99,   53,   55,   51,   52,    0,   66,
    0,    0,  110,  109,    0,    0,    0,    0,    0,  166,
  167,  170,  171,  172,  169,  168,  173,  158,  159,    0,
    0,    0,    0,  105,  107,    0,  115,   65,  117,    0,
    0,    0,    0,  175,  174,    0,    0,  137,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  176,    0,
  136,    0,    0,    0,    0,    0,  126,    0,  128,  121,
    0,  122,  124,  157,    0,  138,    0,    0,    0,    0,
    0,  120,  119,  139,    0,    0,    0,    0,  125,  127,
    0,    0,  161,    0,    0,  160,    0,    0,  162,  163,
};
final static short yydgoto[] = {                          2,
    3,   17,   18,   19,   66,   67,   38,  266,  221,  222,
   21,   22,   76,   23,   24,  202,   25,   26,   27,   28,
   29,   30,   31,   32,   82,  145,   83,  243,  229,   43,
  245,  350,  247,  366,  351,   92,   44,  107,   45,  108,
  109,  230,  289,  290,  291,  302,  303,  304,  367,  395,
  396,  397,  383,  368,   57,  179,   33,  125,  231,  103,
  126,  233,  298,  104,  369,   85,   46,   47,
};
final static short yysindex[] = {                      -204,
    0,    0,  815,  -37,   32,  -34,   -8,   26,    0,   30,
   40,   86,    0,  -28,  982, -197,  -26,  847,    0,    0,
    0, -213,    0,    0,    0,    0,    0,    0,    0, -188,
 -169,  -21,    0,    0,   50,  -45,    0,  -41,    0,    0,
   67, -157,  -10,  -63,   74,    0,   61, -151,  -32,  100,
 -130,    0,   87,    0,  110,  -99,  119,    3,  -88,    0,
  114,    0,  -82,    1,  -79,   35,    0,  877,  -76,  982,
    0,    0,    0,    0,    0,   84,    0,    0,  -74,  127,
  -70,   81,    0,  135,  162,  167,  183,    0,  209, -152,
   12,  269,  295,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   67,   67,   97,   53,   77,   83,    0,    0,
   67,   67,  286,  -30,  105,    0,    0,    0,  117,  -33,
  108,  311,  123,    3,  336,  327,  131,  334,    0,  140,
 -145,    0,    0,    0,  137,    0,    0,  912,    0,  144,
    0,  152,    0, -211,  279,    0, -179,    0,    0,  351,
   67,  155,  147,    0,  161,    0,    0,    0,    0,   61,
   89,  138,  230,    0, -237,  230,    0,  361,    0,    0,
    0,    0,  365,   48,  366,    0,  927,  108,  369,  370,
  374,  164,  563,  -99,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  749,  112,    0,    0,    3,    0,
    0, 1017,    0,  185, 1047,    0,    0,    0,    0,    0,
  982,  946,  378,  186,  187,  -99,  386,   32,   36,  831,
    0,  191,    0,    0,    0,    0,    0,    0,  194,    0,
    0,  393,    0,  -35,   32,   42,    0,    0,    0,    0,
  198,  201,  333,    0,  749,    0,    0,    0, -179,    0,
  202,    0,    0,    0,    0,  579,  203,    0,    0,    0,
  403,  -99,  -61,   67,  145,  595,    0,    0,    0,   89,
   60,  -59,    3,  207,    0,    0,    0,    0,    0,  428,
  411,    0,    0,    0,   89,  414,  611,  626, -226,  212,
  223,  389,  224,  422,  427,    0,  233,    0,  451,  399,
  998, -217,  237,  238,  243,    3,  461,    0,  -99,  248,
   89,  831,    0,  831,    0,    0,   55,    0,    0,  250,
   64,    0,  251,    0,    0,  256, 1063,    0, 1063,    0,
    0,   91,    0,    0,  459,  263,  643,  464,  529,  267,
  662,  677,    0,    0,    0,    0,    0,    0,    3,    0,
 1012, 1033,    0,    0,  -99,  467,   32,  963,  270,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   89,
  563,  271,  488,    0,    0,  273,    0,    0,    0,  476,
  -99,  -47,  692,    0,    0,  280,  292,    0,  563,  477,
   89,  491,  714,  730, -215,  289,  300,  500,    0,  520,
    0,  309,  -99,  314,   89,  963,    0,  963,    0,    0,
   93,    0,    0,    0,  563,    0,  507,  544,  316,  776,
  799,    0,    0,    0,   89,  643,  321,  537,    0,    0,
  322,  324,    0,  643,  540,    0,  335,  643,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  583,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -42,    0,   57,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   28,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  470,
    0,    0,    0,    0,    0,  549,    0,    0,  120,    0,
    0,    0,  113,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -214,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1090,
  -27,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  535,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  471,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -193,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -173,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  535,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -166,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  299,    0,    0,  460,  -51, -176, 1027,   -3,
  283,  -14,    0,    0,    0,  431, -146,  -56,    0,  -36,
  126,  216,  288,    0, -125,    0,  -65,    0,  347,    2,
    0,  387,    0,  211,  272,    0, -208,  493,  566,    0,
  496,    0,    0,    0,  318,    0,    0,  308,    0,    0,
    0,  217, -249,  342,  -55,  -92,    0,  338, -305,  181,
    0,    0,    0,  559, -380,    0,  363,  513,
};
final static int YYTABLESIZE=1369;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         20,
  120,   90,   35,   42,   35,   49,  123,  178,  115,  263,
  174,   20,  131,  188,   72,   88,  148,   81,  203,   80,
   36,  196,  271,   37,  204,   37,  272,    5,  180,  316,
   65,   51,   99,  372,  100,  317,  223,  427,  331,   91,
  410,   93,   74,   75,  332,  432,  411,   93,  238,  101,
   98,  102,    1,  437,   99,    9,  100,  440,  122,   13,
  122,   37,  103,   37,   72,  387,   20,   77,  103,   56,
  157,   41,  182,  223,   69,  264,   42,   79,  135,   59,
   42,  274,  113,  402,   54,  213,   78,    9,  113,  123,
   84,   13,   16,  134,   42,  123,   70,  190,  238,  190,
   94,  190,  111,  154,  113,  161,  209,  112,  155,  424,
  188,   42,  189,  344,  110,  190,  190,  190,  190,  223,
   88,   80,  347,  280,  147,  117,  224,  142,  232,  194,
  250,   99,   81,  100,   72,  341,   16,  342,  239,  116,
  223,  223,  141,   63,   62,  118,  225,  251,  382,  354,
    5,  423,   80,  238,  238,  249,  420,   55,  421,  121,
  261,  194,  194,  224,  194,  223,  194,  223,  124,  101,
   98,  102,  127,   20,  128,  166,  132,   16,  194,  137,
  238,  143,  238,  225,  144,  294,  146,   99,  239,  100,
  360,  237,  223,  149,  223,  223,  105,  106,  287,  288,
  300,  301,  150,  293,  238,  238,  286,   20,   72,  224,
  151,  360,  393,  394,  308,   89,   40,   91,   91,  163,
  265,  305,  152,   87,  223,  173,  177,   11,   64,  225,
  224,  224,  188,  188,   81,   79,  360,   48,   34,  114,
   34,  278,  223,  239,  239,    9,  360,  360,   50,   13,
  225,  225,   72,  338,  336,  224,  129,  224,  130,  360,
  163,  360,   95,   96,   97,  292,  153,  156,  223,   16,
  239,  360,  239,  360,  360,  225,   34,  225,   34,  360,
  361,   52,  224,   53,  224,  224,   55,  360,   39,   40,
  133,  360,   39,   40,  239,  239,   58,  376,  273,  380,
   73,  361,  225,  208,  225,  225,   39,   40,  226,    4,
  343,    5,  190,   68,  224,    6,  190,  190,    8,  346,
  240,   11,   12,   39,   40,  392,  361,  158,  299,  190,
  190,  190,  224,  359,  225,  159,  361,  361,  168,  139,
  140,   60,   61,  106,  172,  226,  353,  417,  422,  361,
   73,  361,  225,    4,  359,    5,  162,  106,  224,    6,
  175,  361,    8,  361,  361,   11,   12,  177,  138,  361,
  240,   88,   90,   90,  176,  194,  183,  361,  225,  359,
  181,  361,   95,   96,   97,  184,  185,  164,  167,  359,
  359,  226,  186,   64,    4,  187,    5,   86,  227,  192,
    6,  195,  359,    8,  359,  248,   11,   12,  193,  197,
  241,  199,  226,  226,  359,   87,  359,  359,  200,  206,
   73,  217,  359,  207,  210,  240,  240,  214,  215,  321,
  359,   99,  216,  100,  359,  227,  258,  226,   16,  226,
  254,  259,  260,  262,  164,  201,  268,  320,  201,  269,
  297,  270,  240,  275,  240,  248,  276,  277,  284,  281,
  241,  285,  362,  306,  226,  310,  226,  226,   80,  309,
  228,  311,  288,  170,  171,  212,  240,  240,  319,  322,
  323,  227,  242,  362,  253,  324,    4,  253,    5,  325,
  326,  340,    6,  334,   73,    8,  226,  301,   11,   12,
  335,  337,  227,  227,  339,  345,  348,  228,  362,  256,
  248,  248,  349,  198,  226,  241,  241,  355,  362,  362,
  356,  327,  370,  373,  381,  385,  388,  227,  389,  227,
  390,  362,  242,  362,  391,  403,  400,  248,   73,  248,
  226,  244,  241,  362,  241,  362,  362,  401,  405,  394,
  386,  362,  363,  228,  227,  413,  227,  227,  414,  362,
  415,  248,  248,  362,  416,  425,  241,  241,   16,  371,
  418,  404,  428,  363,  228,  228,  433,  434,  435,  436,
  438,  246,    1,   16,  426,  419,  227,  242,  242,  187,
  439,  244,   45,  164,  190,   56,  205,  165,  363,  228,
  352,  228,   16,  169,  227,  431,   93,  318,  363,  363,
  333,  307,  412,  119,  242,  160,  242,    0,   16,    0,
    0,  363,    0,  363,  364,    0,  228,    0,  228,  228,
  227,  279,    0,  363,   16,  363,  363,    0,  242,  242,
    0,  363,    0,    0,    0,  364,  244,  244,    0,  363,
   16,  220,    0,  363,    0,  234,    0,  235,  228,    0,
    0,    6,    0,  219,    8,   16,  358,  236,   12,    0,
  364,    0,    0,  244,    0,  244,  228,    0,    0,    0,
  364,  364,   16,  365,    0,  220,  328,  330,    0,    0,
    0,    0,    0,  364,    0,  364,    0,  244,  244,  384,
    0,   16,  228,  283,  365,  364,    0,  364,  364,    0,
    0,    0,    0,  364,    0,    0,   16,    0,    0,  295,
    0,  364,    0,    0,  399,  364,    0,    0,    0,  365,
    0,   16,    0,  312,  407,  409,    0,  378,  378,  365,
  365,    0,    0,    0,    0,    0,    0,  384,  314,  384,
    0,    0,  365,   16,  365,    0,    0,    0,    0,    0,
    0,  399,  399,    0,  365,  358,  365,  365,    0,   16,
    0,    0,  365,    0,    0,    0,    0,    0,    0,    0,
  365,    0,    0,    0,  365,    4,  374,  218,   16,    0,
    0,    6,    7,  219,    8,    9,   10,   11,   12,   13,
  234,  375,  357,    0,    0,   14,    6,    7,  219,    8,
    9,   10,  236,   12,   13,   16,  398,    0,    0,    4,
   14,  218,    0,    0,    0,    6,    7,  219,    8,    9,
   10,   11,   12,   13,  282,    4,  406,    5,   16,   14,
    0,    6,    7,    0,    8,    9,   10,   11,   12,   13,
    0,    4,  408,  218,   16,   14,    0,    6,    7,  219,
    8,    9,   10,   11,   12,   13,    0,    4,    0,  218,
   16,   14,    0,    6,    7,  219,    8,    9,   10,   11,
   12,   13,    4,    0,  218,    0,   16,   14,    6,    7,
  219,    8,    9,   10,   11,   12,   13,    0,    0,  234,
  429,  357,   14,    0,    0,    6,    7,  219,    8,    9,
   10,  236,   12,   13,    0,    0,   16,    0,    4,   14,
  218,    0,    0,  430,    6,    7,  219,    8,    9,   10,
   11,   12,   13,    4,    0,  218,    0,   15,   14,    6,
    7,  219,    8,    9,   10,   11,   12,   13,  234,    0,
  357,   16,    0,   14,    6,    7,  219,    8,    9,   10,
  236,   12,   13,    0,    0,    0,   16,    0,   14,    0,
  234,   71,  357,    0,    0,    0,    6,    7,  219,    8,
    9,   10,  236,   12,   13,   16,  234,    0,  357,    0,
   14,    0,    6,    7,  219,    8,    9,   10,  236,   12,
   13,  136,   16,    0,    0,  234,   14,  235,    0,    0,
    0,    6,    7,  219,    8,    9,   10,  236,   12,   13,
    0,   16,    0,    0,    0,   14,    0,    0,    0,    0,
    0,    0,  234,    0,  357,    0,  191,   16,    6,    7,
  219,    8,    9,   10,  236,   12,   13,    0,    0,  211,
    0,   16,   14,    0,    0,  234,   16,  357,    0,    0,
    0,    6,    7,  219,    8,    9,   10,  236,   12,   13,
  257,    4,   16,    5,    0,   14,    0,    6,    7,    0,
    8,    9,   10,   11,   12,   13,   16,    4,    0,  218,
    0,   14,    0,    6,    7,  219,    8,    9,   10,   11,
   12,   13,   16,    4,    0,    5,    0,   14,    0,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
  329,    0,    0,   14,    0,    0,    0,    0,    0,    0,
  189,    0,  189,    4,  189,    5,  377,    0,    0,    6,
    7,  252,    8,    9,   10,   11,   12,   13,  189,  189,
  189,  189,    0,   14,    0,    0,    0,  379,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    4,    0,
    5,  255,    0,    0,    6,    7,    0,    8,    9,   10,
   11,   12,   13,    4,    0,    5,    0,    0,   14,    6,
    7,    0,    8,    9,   10,   11,   12,   13,    0,    0,
    0,    0,    4,   14,    5,    0,    0,    0,    6,    7,
    0,    8,    9,   10,   11,   12,   13,    0,    0,  234,
    0,  357,   14,    0,    0,    6,    7,  219,    8,    9,
   10,  236,   12,   13,    0,    0,    0,    0,    4,   14,
    5,    0,    0,    0,    6,    7,  267,    8,    9,   10,
   11,   12,   13,    0,  234,    0,  235,    0,   14,    0,
    6,    0,  219,    8,    0,    0,  236,   12,  234,    0,
  235,    0,    0,    4,    6,    5,  219,    8,    0,    6,
  236,   12,    8,    0,    0,   11,   12,    0,    0,  234,
    0,  235,  296,    0,    0,    6,    0,  219,    8,    0,
    0,  236,   12,    4,    0,    5,    0,    0,    0,    6,
    0,    0,    8,  313,  315,   11,   12,    0,    0,  234,
    0,  235,    0,    0,    0,    6,    0,  219,    8,    0,
    0,  236,   12,    0,    0,    0,    0,    0,  267,    0,
  267,    0,    0,    0,    0,  189,    0,    0,    0,  189,
  189,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  189,  189,  189,    0,    0,  296,  296,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   56,   38,   40,   45,   40,   40,   58,   41,   41,  218,
   41,   15,   64,   41,   18,   61,   82,   32,  256,   41,
   58,  147,   58,   61,  262,   61,  235,    0,  121,  256,
   59,   40,   43,  339,   45,  262,  183,  418,  256,   38,
  256,  256,  256,  257,  262,  426,  262,  262,  195,   60,
   61,   62,  257,  434,   43,  267,   45,  438,   58,  271,
   58,   61,  256,   61,   68,  371,   70,  256,  262,   40,
   59,   40,  124,  220,  272,   40,   45,  257,   44,   40,
   45,   40,  256,  389,   59,  178,  256,  267,  262,  256,
   41,  271,   40,   59,   45,  262,  123,   41,  245,   43,
  258,   45,   42,  256,  256,  104,   59,   47,  261,  415,
  256,   45,  258,   59,   41,   59,   60,   61,   62,  266,
   61,   41,   59,  249,   44,  256,  183,   44,  184,  144,
  196,   43,  147,   45,  138,  312,   40,  314,  195,   40,
  287,  288,   59,   58,   59,   59,  183,  199,  357,   59,
  123,   59,   41,  300,  301,   44,  406,  257,  408,   41,
  216,   42,   43,  220,   45,  312,   47,  314,  257,   60,
   61,   62,   59,  177,  257,  123,  256,   40,   59,  256,
  327,  256,  329,  220,   58,   41,  257,   43,  245,   45,
  337,  195,  339,   59,  341,  342,  260,  261,  260,  261,
  260,  261,   41,   59,  351,  352,  262,  211,  212,  266,
   44,  358,  260,  261,  280,  257,  258,  260,  261,  123,
  219,  273,   40,  269,  371,  256,  260,  269,  257,  266,
  287,  288,  260,  261,  249,  257,  383,  272,  276,  272,
  276,  245,  389,  300,  301,  267,  393,  394,  257,  271,
  287,  288,  256,  309,  306,  312,  256,  314,  258,  406,
  123,  408,  273,  274,  275,  264,   58,  256,  415,   40,
  327,  418,  329,  420,  421,  312,  276,  314,  276,  426,
  337,  256,  339,  258,  341,  342,  257,  434,  257,  258,
  256,  438,  257,  258,  351,  352,  257,  349,  257,  355,
   18,  358,  339,  256,  341,  342,  257,  258,  183,  257,
  256,  259,  256,   15,  371,  263,  260,  261,  266,  256,
  195,  269,  270,  257,  258,  381,  383,   59,  269,  273,
  274,  275,  389,  337,  371,   41,  393,  394,  262,  256,
  257,  256,  257,  261,   59,  220,  256,  403,  256,  406,
   68,  408,  389,  257,  358,  259,  260,  261,  415,  263,
  256,  418,  266,  420,  421,  269,  270,  260,   70,  426,
  245,   61,  260,  261,  258,  256,   41,  434,  415,  383,
  258,  438,  273,  274,  275,   59,  256,  105,  106,  393,
  394,  266,   59,  257,  257,  256,  259,   35,  183,  256,
  263,  123,  406,  266,  408,  195,  269,  270,  257,   59,
  195,  257,  287,  288,  418,  269,  420,  421,  258,   59,
  138,  258,  426,   59,   59,  300,  301,   59,   59,   41,
  434,   43,   59,   45,  438,  220,   59,  312,   40,  314,
  256,  256,  256,   58,  162,  163,  256,   59,  166,  256,
  270,   59,  327,  256,  329,  245,  256,  125,  256,  258,
  245,   59,  337,  257,  339,  285,  341,  342,   41,   59,
  183,   58,  261,  111,  112,  177,  351,  352,  256,  256,
   59,  266,  195,  358,  202,   59,  257,  205,  259,  257,
   40,  311,  263,  256,  212,  266,  371,  261,  269,  270,
  258,   41,  287,  288,  257,  256,  256,  220,  383,  211,
  300,  301,  257,  151,  389,  300,  301,   59,  393,  394,
  258,  123,   59,  257,   58,  256,  256,  312,   41,  314,
  258,  406,  245,  408,   59,   59,  257,  327,  256,  329,
  415,  195,  327,  418,  329,  420,  421,  256,   58,  261,
  370,  426,  337,  266,  339,  256,  341,  342,   59,  434,
   41,  351,  352,  438,  256,   59,  351,  352,   40,   41,
  257,  391,  257,  358,  287,  288,  256,   41,  257,  256,
   41,  195,    0,   40,   41,  405,  371,  300,  301,   41,
  256,  245,  123,   59,  135,  125,  166,  105,  383,  312,
  329,  314,   40,  108,  389,  425,   41,  290,  393,  394,
  303,  274,  396,   55,  327,  103,  329,   -1,   40,   -1,
   -1,  406,   -1,  408,  337,   -1,  339,   -1,  341,  342,
  415,  245,   -1,  418,   40,  420,  421,   -1,  351,  352,
   -1,  426,   -1,   -1,   -1,  358,  300,  301,   -1,  434,
   40,  123,   -1,  438,   -1,  257,   -1,  259,  371,   -1,
   -1,  263,   -1,  265,  266,   40,  123,  269,  270,   -1,
  383,   -1,   -1,  327,   -1,  329,  389,   -1,   -1,   -1,
  393,  394,   40,  337,   -1,  123,  300,  301,   -1,   -1,
   -1,   -1,   -1,  406,   -1,  408,   -1,  351,  352,  358,
   -1,   40,  415,  125,  358,  418,   -1,  420,  421,   -1,
   -1,   -1,   -1,  426,   -1,   -1,   40,   -1,   -1,  125,
   -1,  434,   -1,   -1,  383,  438,   -1,   -1,   -1,  383,
   -1,   40,   -1,  123,  393,  394,   -1,  351,  352,  393,
  394,   -1,   -1,   -1,   -1,   -1,   -1,  406,  123,  408,
   -1,   -1,  406,   40,  408,   -1,   -1,   -1,   -1,   -1,
   -1,  420,  421,   -1,  418,  123,  420,  421,   -1,   40,
   -1,   -1,  426,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  434,   -1,   -1,   -1,  438,  257,  125,  259,   40,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  257,  125,  259,   -1,   -1,  277,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   40,  125,   -1,   -1,  257,
  277,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  256,  257,  123,  259,   40,  277,
   -1,  263,  264,   -1,  266,  267,  268,  269,  270,  271,
   -1,  257,  123,  259,   40,  277,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,  271,   -1,  257,   -1,  259,
   40,  277,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  257,   -1,  259,   -1,   40,  277,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   -1,   -1,  257,
  125,  259,  277,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,   40,   -1,  257,  277,
  259,   -1,   -1,  125,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,   -1,  123,  277,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  257,   -1,
  259,   40,   -1,  277,  263,  264,  265,  266,  267,  268,
  269,  270,  271,   -1,   -1,   -1,   40,   -1,  277,   -1,
  257,  125,  259,   -1,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   40,  257,   -1,  259,   -1,
  277,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  125,   40,   -1,   -1,  257,  277,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   -1,   40,   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  259,   -1,  125,   40,  263,  264,
  265,  266,  267,  268,  269,  270,  271,   -1,   -1,  123,
   -1,   40,  277,   -1,   -1,  257,   40,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  125,  257,   40,  259,   -1,  277,   -1,  263,  264,   -1,
  266,  267,  268,  269,  270,  271,   40,  257,   -1,  259,
   -1,  277,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   40,  257,   -1,  259,   -1,  277,   -1,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
  123,   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   43,  257,   45,  259,  125,   -1,   -1,  263,
  264,  125,  266,  267,  268,  269,  270,  271,   59,   60,
   61,   62,   -1,  277,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  259,  125,   -1,   -1,  263,  264,   -1,  266,  267,  268,
  269,  270,  271,  257,   -1,  259,   -1,   -1,  277,  263,
  264,   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,   -1,  257,  277,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,  257,
   -1,  259,  277,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,   -1,   -1,  257,  277,
  259,   -1,   -1,   -1,  263,  264,  220,  266,  267,  268,
  269,  270,  271,   -1,  257,   -1,  259,   -1,  277,   -1,
  263,   -1,  265,  266,   -1,   -1,  269,  270,  257,   -1,
  259,   -1,   -1,  257,  263,  259,  265,  266,   -1,  263,
  269,  270,  266,   -1,   -1,  269,  270,   -1,   -1,  257,
   -1,  259,  266,   -1,   -1,  263,   -1,  265,  266,   -1,
   -1,  269,  270,  257,   -1,  259,   -1,   -1,   -1,  263,
   -1,   -1,  266,  287,  288,  269,  270,   -1,   -1,  257,
   -1,  259,   -1,   -1,   -1,  263,   -1,  265,  266,   -1,
   -1,  269,  270,   -1,   -1,   -1,   -1,   -1,  312,   -1,
  314,   -1,   -1,   -1,   -1,  256,   -1,   -1,   -1,  260,
  261,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  341,  342,
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
"bloque_ejecutables : bloque_ejecutables ejecutables",
"bloque_ejecutables : ejecutables",
"ejecutables : asignacion",
"ejecutables : salida",
"ejecutables : sentencia_If",
"ejecutables : expresion_For",
"ejecutables : invocacion_funcion",
"ejecutables : sentencia_BREAK error",
"ejecutables : sentencia_CONTINUE error",
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

//#line 467 "gramatica.y"

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


//#line 863 "Parser.java"
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
//#line 44 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja(val_peek(2).ival)), new ParserVal(sintactico.crearHoja(val_peek(0).ival))));}
break;
case 13:
//#line 45 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
break;
case 14:
//#line 46 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
break;
case 15:
//#line 47 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
break;
case 16:
//#line 51 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 17:
//#line 52 "gramatica.y"
{ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
                                                              yyval = modificado;}
break;
case 18:
//#line 58 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 19:
//#line 59 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 20:
//#line 60 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 21:
//#line 64 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 22:
//#line 71 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 23:
//#line 72 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
break;
case 24:
//#line 73 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el identificador de variable"); }
break;
case 28:
//#line 80 "gramatica.y"
{
							ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
							yyval = modificado;
						}
break;
case 29:
//#line 84 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 35:
//#line 92 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 36:
//#line 93 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 39:
//#line 98 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores."); }
break;
case 40:
//#line 101 "gramatica.y"
{ sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" ); }
break;
case 41:
//#line 102 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
break;
case 43:
//#line 106 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
break;
case 45:
//#line 111 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
break;
case 46:
//#line 113 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")");
 						yyval = val_peek(1);}
break;
case 47:
//#line 118 "gramatica.y"
{yyval = val_peek(0);}
break;
case 49:
//#line 120 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
break;
case 51:
//#line 124 "gramatica.y"
{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   yyval = new ParserVal(sintactico.crearNodoControl("return",val_peek(2)));}
break;
case 52:
//#line 126 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 53:
//#line 127 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 54:
//#line 128 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
break;
case 55:
//#line 129 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
break;
case 63:
//#line 141 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
break;
case 64:
//#line 142 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
break;
case 65:
//#line 146 "gramatica.y"
{
													ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
													yyval = modificado;
											 	}
break;
case 66:
//#line 150 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 67:
//#line 153 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 68:
//#line 157 "gramatica.y"
{
									ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
									yyval = modificado;
								}
break;
case 69:
//#line 161 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 70:
//#line 162 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 71:
//#line 171 "gramatica.y"
{ yyval.sval = new String("=:"); }
break;
case 72:
//#line 172 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 73:
//#line 173 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
break;
case 74:
//#line 177 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(3).ival));
						yyval = new ParserVal(sintactico.crearNodo("=:", identificador , val_peek(1)));
					   }
break;
case 75:
//#line 180 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
break;
case 76:
//#line 181 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("=:", val_peek(3), val_peek(1)));}
break;
case 77:
//#line 185 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("else", val_peek(2), val_peek(0)));}
break;
case 78:
//#line 186 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
break;
case 79:
//#line 189 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja(val_peek(2).ival))));}
break;
case 80:
//#line 190 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
break;
case 81:
//#line 191 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
break;
case 82:
//#line 192 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
break;
case 83:
//#line 193 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
break;
case 84:
//#line 194 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
break;
case 85:
//#line 199 "gramatica.y"
{ 	yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));
										sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
break;
case 86:
//#line 201 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 87:
//#line 202 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 88:
//#line 206 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cond",val_peek(1)));}
break;
case 89:
//#line 207 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 90:
//#line 208 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 91:
//#line 209 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
break;
case 92:
//#line 213 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(1), val_peek(0)));}
break;
case 93:
//#line 214 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo", val_peek(0), null));}
break;
case 94:
//#line 215 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
break;
case 95:
//#line 219 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 96:
//#line 220 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 97:
//#line 224 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 98:
//#line 225 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 99:
//#line 228 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 										   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 100:
//#line 230 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 101:
//#line 231 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 102:
//#line 234 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 103:
//#line 235 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 104:
//#line 236 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 105:
//#line 239 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 106:
//#line 240 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 107:
//#line 243 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 108:
//#line 244 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 109:
//#line 247 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 											   yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 110:
//#line 249 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 111:
//#line 250 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 112:
//#line 253 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 113:
//#line 254 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 114:
//#line 255 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 115:
//#line 258 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 116:
//#line 259 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(0)));}
break;
case 117:
//#line 262 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(1)));}
break;
case 118:
//#line 263 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else",val_peek(0)));}
break;
case 119:
//#line 266 "gramatica.y"
{ sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
												  yyval = new ParserVal(sintactico.crearNodo("if",val_peek(3),val_peek(2)));}
break;
case 120:
//#line 268 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
break;
case 121:
//#line 269 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
break;
case 122:
//#line 272 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(1),val_peek(0)));}
break;
case 123:
//#line 273 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("cuerpo",val_peek(0),null));}
break;
case 124:
//#line 274 "gramatica.y"
{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
break;
case 125:
//#line 277 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(1)));}
break;
case 126:
//#line 278 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then", val_peek(0)));}
break;
case 127:
//#line 281 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(1)));}
break;
case 128:
//#line 282 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("else", val_peek(0)));}
break;
case 129:
//#line 288 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia when");
									yyval = new ParserVal(sintactico.crearNodo("when", val_peek(3), val_peek(1)));}
break;
case 130:
//#line 290 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
break;
case 131:
//#line 291 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
break;
case 132:
//#line 295 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("then",val_peek(1)));}
break;
case 133:
//#line 296 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
break;
case 134:
//#line 297 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
break;
case 135:
//#line 304 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
									}
break;
case 136:
//#line 307 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 137:
//#line 308 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 138:
//#line 309 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 139:
//#line 310 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 140:
//#line 314 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("encabezado for",val_peek(2), val_peek(0)));}
break;
case 141:
//#line 317 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("condicion y operacion for",  val_peek(2), val_peek(0)));}
break;
case 142:
//#line 320 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("cond", new ParserVal(sintactico.crearNodo(val_peek(1).sval,identificador,constante))));}
break;
case 143:
//#line 325 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 144:
//#line 326 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 145:
//#line 329 "gramatica.y"
{yyval.sval = new String("+");}
break;
case 146:
//#line 330 "gramatica.y"
{yyval.sval = new String("-");}
break;
case 154:
//#line 341 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 155:
//#line 342 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
break;
case 157:
//#line 348 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(2)));}
break;
case 158:
//#line 349 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("cuerpoFor",val_peek(0)));}
break;
case 159:
//#line 353 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        									yyval = new ParserVal(sintactico.crearNodo("For",val_peek(2),val_peek(0)));
										}
break;
case 160:
//#line 356 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 161:
//#line 357 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 162:
//#line 358 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
break;
case 163:
//#line 359 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
break;
case 164:
//#line 362 "gramatica.y"
{	ParserVal identificador = new ParserVal(sintactico.crearHoja(val_peek(2).ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
					yyval = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));}
break;
case 165:
//#line 367 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo(val_peek(1).sval,new ParserVal(sintactico.crearHoja(val_peek(0).ival)),null))));}
break;
case 174:
//#line 378 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
break;
case 175:
//#line 382 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("sentencia", val_peek(0), null));}
break;
case 176:
//#line 383 "gramatica.y"
{
										ParserVal modificado = sintactico.modificarHijo(val_peek(1), sintactico.crearNodo("sentencia", val_peek(0), null));
										yyval = modificado;
									}
break;
case 177:
//#line 391 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
				yyval = new ParserVal(sintactico.crearNodoControl("break",null));}
break;
case 178:
//#line 393 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                		yyval = new ParserVal(sintactico.crearNodoControl("break", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 179:
//#line 395 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
break;
case 180:
//#line 398 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
						yyval = new ParserVal(sintactico.crearNodoControl("continue",null));}
break;
case 181:
//#line 400 "gramatica.y"
{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   				yyval = new ParserVal(sintactico.crearNodoControl("continue", new ParserVal(sintactico.crearHoja(val_peek(1).ival))));}
break;
case 182:
//#line 402 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
break;
case 183:
//#line 403 "gramatica.y"
{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
break;
case 184:
//#line 406 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(4).ival, val_peek(2)));}
break;
case 185:
//#line 407 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodoFunc(val_peek(3).ival, null));}
break;
case 186:
//#line 410 "gramatica.y"
{
						yyval = new ParserVal(sintactico.crearNodo("param", val_peek(2), val_peek(0)));
					}
break;
case 187:
//#line 413 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("param", val_peek(0), null));}
break;
case 188:
//#line 417 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0)));}
break;
case 189:
//#line 421 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo(val_peek(1).sval, val_peek(2), val_peek(0))); }
break;
case 191:
//#line 426 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("*",val_peek(2),val_peek(0)));}
break;
case 192:
//#line 427 "gramatica.y"
{yyval = new ParserVal(sintactico.crearNodo("/",val_peek(2),val_peek(0)));}
break;
case 194:
//#line 432 "gramatica.y"
{ yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));}
break;
case 195:
//#line 433 "gramatica.y"
{
                        sintactico.setTipo(sintactico.getTipoFromTS(val_peek(0).ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(0).ival));
                  }
break;
case 196:
//#line 439 "gramatica.y"
{
                        sintactico.setNegativoTablaSimb(val_peek(0).ival);
                        yyval = new ParserVal(sintactico.crearHoja(val_peek(1).ival));
                    }
break;
case 197:
//#line 446 "gramatica.y"
{ yyval.sval = new String("<"); }
break;
case 198:
//#line 447 "gramatica.y"
{ yyval.sval = new String(">"); }
break;
case 199:
//#line 448 "gramatica.y"
{ yyval.sval = new String("<="); }
break;
case 200:
//#line 449 "gramatica.y"
{ yyval.sval = new String(">="); }
break;
case 201:
//#line 450 "gramatica.y"
{ yyval.sval = new String("="); }
break;
case 202:
//#line 451 "gramatica.y"
{ yyval.sval = new String("=!"); }
break;
case 203:
//#line 455 "gramatica.y"
{
                    sintactico.setTipo("i32");
                    yyval.sval = new String("i32");
                }
break;
case 204:
//#line 459 "gramatica.y"
{
                    sintactico.setTipo("f32");
                    yyval.sval = new String("f32");
                }
break;
//#line 1729 "Parser.java"
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
