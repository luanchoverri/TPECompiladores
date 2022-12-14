<<<<<<< Updated upstream
=======
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
_maxFloat dq 3.402823466E38
_minFloat dq -1.175494351E-38
out0 db 'entre en el then <=',0
out1 db 'afuera del if',0
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
@aux0 dd ?,?
.code
;------------ CODE ------------
start:
MOV EAX,0
MOV _a@$,EAX
MOV EAX,1
MOV _b@$,EAX
MOV EAX, _a@$
CMP EAX, 1
JG _label1
MOV @aux0,1 
JMP _label0
_label1:
MOV @aux0, 0 
_label0:
CMP EAX, <=
CMP EAX,  0
JE _label2
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
JMP _label3
_label2:
_label3:
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start
>>>>>>> Stashed changes
