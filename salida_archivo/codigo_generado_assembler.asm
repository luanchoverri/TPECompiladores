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
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_g@$ dd ?,?
_3_4 dd ?,?
_1_0 dd ?,?
_3_5 dd ?,?
__0 dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
.code
;------------ CODE ------------
start:
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
FLD _3_4
FDIV _1_0
FST @aux0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
FLD @aux0
FSTP _f@$
FLDZ
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label1
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label1:
FLD _3_5
FDIV __0
FST @aux2
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
FLD @aux2
FSTP _d@$
;------------ FIN ------------
invoke ExitProcess, 0
end start