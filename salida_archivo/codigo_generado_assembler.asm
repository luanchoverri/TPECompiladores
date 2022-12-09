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
errorOverflow db 'ERROR EN LA EJECUCION: Overflow de de datosPrecarga de punto flotante (f32)',0 
errorDivCeroEntero db 'ERROR EN LA EJECUCION: Division por cero para constante de enteros',0 
errorDivCeroFlotante db 'ERROR EN LA EJECUCION: Division por cero para constante de punto flotante',0 
errorRecursion db 'ERROR EN LA EJECUCION: Recursión en invocaciones de funciones',0 
ok db 'OK',0 
mem2bytes dw ?
_program dd ?,?
_i@$ dd ?,?
_a@$ dd ?,?
_j@$ dd ?,?
_1_0 dd ?,?
_2_0 dd ?,?
_4_0 dd ?,?
_k@$ dd ?,?
_l@$ dd ?,?
_p@$_fun2 dd ?,?
_q@$_fun2 dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun1@$_fun2
_fun1@$_fun2 PROC
FSTP _p@$_fun2
FSTP _q@$_fun2
FLD _1_0
FSTP _p@$_fun2
FLD _q@$_fun2
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun1@$_fun2 ENDP
PUBLIC _fun2@$
_fun2@$ PROC
FSTP _k@$
FSTP _l@$
FLD _1_0
FSTP _k@$
FLD _i@$
FLD _k@$
call _fun1@$_fun2
FSTP _l@$
FLD _l@$
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun2@$ ENDP
start:
FLD _1_0
FSTP _i@$
FLD _2_0
FSTP _a@$
FLD _4_0
FSTP _j@$
FLD _j@$
FLD _i@$
call _fun2@$
FSTP _a@$
;------------ FIN ------------
invoke ExitProcess, 0
end start