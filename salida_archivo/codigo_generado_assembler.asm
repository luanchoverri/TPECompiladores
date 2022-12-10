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
out0 db 'hola',0
out1 db '¡Cadena /         	multilinea 1! ',0
out2 db 'que onda',0
.code
;------------ CODE ------------
start:
FLD _1_0
FSTP _i@$
FLD _2_0
FSTP _a@$
FLD _4_0
FSTP _j@$
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
invoke MessageBox, NULL, addr out2, addr out2, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start