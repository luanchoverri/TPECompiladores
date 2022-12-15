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
__1_0 dq -1.0
_0_1 dq 0.1
_2_0 dq 2.0
_0_0 dq 0.0
_0_ dq 0.0
__0 dq 0.0
_3_F_5 dq 3.0F-5
_2_F34 dq 2.0F+34
_2_5F_1 dq 2.5E-1
_1_2F10 dq 1.2E10
.code
;------------ CODE ------------
start:
FLD __1_0
FSTP _a@$
FLD _0_1
FSTP _b@$
FLD _2_0
FSTP _c@$
FLD _0_0
FSTP _d@$
FLD _0_
FSTP _e@$
FLD __0
FSTP _f@$
FLD _3_F_5
FSTP _g@$
FLD _2_F34
FSTP _h@$
FLD _2_5F_1
FSTP _i@$
FLD _1_2F10
FSTP _j@$
;------------ FIN ------------
invoke ExitProcess, 0
end start