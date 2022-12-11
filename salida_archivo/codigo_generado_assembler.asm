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
_3_41 dd 3.41
@aux0 dd ?,?
.code
;------------ CODE ------------
start:
FLD _3_2
FMUL _3_41
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label0
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label0:
FSTP @aux0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
FLD @aux0
FSTP _a@$
;------------ FIN ------------
invoke ExitProcess, 0
end start