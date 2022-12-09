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
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_e@$ dd ?,?
_1_0 dd ?,?
_0_0 dd ?,?
_6_2 dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
.code
;------------ CODE ------------
start:
FLD _1_0
FSTP _a@$
FLD _0_0
FSTP _b@$
FLD _6_2
FSTP _e@$
FLD _a@$
FIMUL _b@$
FSTP @aux0
FLD1
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label0
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label0:
FLD _a@$
FDIV _e@$
FST @aux1
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
FLD @aux0
FADD @aux1
FSTP @aux2
FLD @aux2
FSTP _c@$
;------------ FIN ------------
invoke ExitProcess, 0
end start