package AnalizadorLexico.AccionesSemanticas;

public class MatrizAccionesSemanticas {


    // Estado -> Fila ; Simbolo -> Columna (En la matriz de estados)
    private AccionSemantica[][] matriz;

    // Constructor
    public MatrizAccionesSemanticas() {
        // -- Acciones semanticas SIMPLES --

        // AS1 -> Inicializa el buffer
        AccionSemanticaSimple AS1 = new InicializarBuffer(this);

        // AS7 -> Agrega char leido al String (Buffer)
        AccionSemanticaSimple AS7 = new AgregarChar(this);

        // AS3 -> Devuelve el ID del Token de los simbolos (operadores aritmeticos, comparadores, etc)
        AccionSemanticaSimple AS3 = new ObtenerIdToken(this, this.getTablaSimbolos());

        // AS8 -> Controla si es palabra reservada
        AccionSemanticaSimple AS8 = new ChequearPalabraReservada(this, this.getTablaSimbolos());

        // AS10 -> Controla el rango de los enteros. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemanticaSimple AS10 = new RangoEntero(this, this.getTablaSimbolos());

        // AS11 -> Controla el rango de los flotantes. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemanticaSimple AS11 = new RangoFlotante(this, this.getTablaSimbolos());

        // -- Acciones semanticas COMPUESTAS --




        this.matriz ={
                //REEMPLAZAR POR ACCIONES SEMANTICAS
//                //        L    d   .   _   F   <   >   =   +   -   *   /   '   \n   ;   :   ,  (    )   {   }  ot   !  BL  $
//                // 	  	  0    1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  23  24
//                /*0*/    { 6,   1,  9, -1,  6,  3,  2,  5,  0,  0,  0,  0,  7,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1,  0,  0  },
//                /*1*/    { 0,   1, 10,  0,  0,  0,  0,  0,  0,	0,  0,  0,  0,	0,  0,  0,  0,	0,  0,  0,  0,	0,  0,  0,  0  },
//                /*2*/    { 0,   0,	0,  0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0  },
//                /*3*/    { 0,   0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0  },
//                /*4*/    { 4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	0,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4  },
//                /*5*/    { 0,   0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0  },
//                /*6*/    { 6,	6,	0,	6,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0  },
//                /*7*/    { 7,	7,	7,	7,	7,	7,	7,	7,	7,	7,	7,	8,	0,	p,	7,	7,	7,	7,	7,	7,	7,	7,	7,	7,	7  },
//                /*8*/    { 7,	7,	7,	7,	7,	7,	7,	7,	7,	7,	7,	8,	0,	8,	7,	7,	7,	7,	7,	7,	7,	7,	7,	7,	7  },
//                /*9*/    {-1,  10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  },
//                /*10*/   { 0,  10,  0,	0, 11,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0  },
//                /*11*/   {-1,  13, -1, -1, -1, -1, -1, -1, 12, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  },
//                /*12*/   {-1,  13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  },
//                /*13*/   { 0,  13,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0  };
//
        }
    }

    // -- MÉTODOS -- // 

    // Getter
    public AccionSemantica get(int estado, int simbolo) {
        return matriz[estado][simbolo];
    }


}

    

