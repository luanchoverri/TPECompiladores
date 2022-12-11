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
_maxFloat dq 3.402823466E37
_minFloat dq 1.175494351E-38
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_e@$ dd ?,?
_3_2 dd 3.2
__0 dd 0.0
@aux0 dd ?,?
.code
;------------ CODE ------------
start:
FLD __0
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label0
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label0:
FLD _3_2
FDIV __0
FST @aux0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
FLD @aux0
FSTP _a@$
;------------ FIN ------------
invoke ExitProcess, 0
end start