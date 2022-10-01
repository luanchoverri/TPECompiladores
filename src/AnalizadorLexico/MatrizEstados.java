package AnalizadorLexico;

public class MatrizEstados {

    private int[][] matriz;


    public MatrizEstados() {
        this.matriz = new int[][]{
                //         L    d   .    _   F   <   >   =   +   -    *   /    '  \n   ;   :   ,  (    )   {   }  ot   !  BL   $
                // 	  	   0    1   2    3   4   5   6   7   8   9   10  11   12  13  14  15  16  17  18  19  20  21  22  23  24
                /*0*/    {11,   2,  1,  -1, 11,  8,  7, 10,  0,  0,  0,  0,  12,  0,  0,  0,  0,  0,  0,  0,  0,  -1, -1,  0,  0  },
                /*1*/    { 0,   3,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*2*/    { 0,   2,  3,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*3*/    { 0,   3,  0,   0,  4,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*4*/    {-1,   6, -1,  -1, -1, -1, -1, -1,  5,  5, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1  },
                /*5*/    {-1,   6, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1  },
                /*6*/    { 0,   6,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*7*/    { 0,   0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*8*/    { 0,   0,  0,   0,  0,  9,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*9*/    { 9,   9,  9,   9,  9,  9,  9,  9,  9,  9,  9,  9,   9,  9,  9,  9,  0,  9,  9,  9,  9,   9,  9,  9,  9  },
                /*10*/   { 0,   0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*11*/   {11,  11,  0,  11,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0,  0,  0,  0,  0,  0,   0,  0,  0,  0  },
                /*12*/   {12,  12, 12,  12, 12, 12, 12, 12, 12, 12, 12, 13,   0, -1, 12, 12, 12, 12, 12, 12, 12,  12, 12, 12, 12  },
                /*13*/   {12,  12, 12,  12, 12, 12, 12, 12, 12, 12, 12, 13,  12, 14, 12, 12, 12, 12, 12, 12, 12,  12, 12, 13, 12  },
                /*14*/   {12,  12, 12,  12, 12, 12, 12, 12, 12, 12, 12, 13,   0, -1, 12, 12, 12, 12, 12, 12, 12,  12, 12, 12, 12}
        };

    }

    public int getEstado(int estado, int simbolo){
        return matriz[estado][simbolo];
    }

    public int getColCaracter(Character caracter){

        if (caracter == null)
            return -1;

        if (caracter == 'F')
            return 4;

        if (Character.isLetter(caracter))
            return 0;

        if (Character.isDigit(caracter))
            return 1;

        switch (caracter) {
            case '.': return 2;
            case '_': return 3;
            case '<': return 5;
            case '>': return 6;
            case '=': return 7;
            case '+': return 8;
            case '-': return 9;
            case '*': return 10;
            case '/': return 11;
            case 39 : return 12;   // comilla simple (ASCII)
            case '\n': return 13;
            case ';': return 14;
            case ':': return 15;
            case ',': return 16;
            case '(': return 17;
            case ')': return 18;
            case '{': return 19;
            case '}': return 20;
            case '!': return 22;
            case 9: return 23;      // Tabulación (ASCII)
            case 32: return 23;     // Espacio en blanco (ASCII)
            case '$': return 24;
        }

        return 21; //Otros simbolos

    }




}
