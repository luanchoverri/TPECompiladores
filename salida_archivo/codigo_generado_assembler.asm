;------------ INCLUDES y LIBRERIAS ------------
.386
.model flat, stdcall
.stack 200h
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
include \masm32\include\masm32.inc
includelib \masm32\lib\masm32.lib
;------------ DATA ------------
.data
;------------ ERRORES DE EJECUCION ------------
errorOverflow db 'ERROR EN LA EJECUCION: Overflow de datos en constantes de punto flotante (f32)',0 
errorDivCeroEntero db 'ERROR EN LA EJECUCION: Division por cero para constante de enteros',0 
errorDivCeroFlotante db 'ERROR EN LA EJECUCION: Division por cero para constante de punto flotante',0 
errorRecursion db 'ERROR EN LA EJECUCION: Recursión en invocaciones de funciones',0 
ok db 'OK',0 
mem2bytes dw ?
_maxFloat dq 3.40282347E+38
_minFloat dq -3.40282347E+38
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_g@$ dd ?,?
_h@$ dd ?,?
_i@$ dd ?,?
_j@$ dd ?,?
_k@$ dd ?,?
_3_40282347F38 dq 3.40282347E38
__3_40282347F_38 dq -3.40282347E-38
__1_17549435F_38 dq -1.17549435E-38
_1_17549435F_38 dq 1.17549435E-38
_3_40282347F38 dq 3.40282347E38
.code
;------------ CODE ------------
start:
FLD _3_40282347F38
FSTP _g@$
FLD __3_40282347F_38
FSTP _h@$
FLD __1_17549435F_38
FSTP _i@$
FLD _1_17549435F_38
FSTP _j@$
FLD _3_40282347F38
FSTP _k@$
;------------ FIN ------------
invoke ExitProcess, 0
end start