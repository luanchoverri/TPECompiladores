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
errorDivCeroEntero db 'ERROR EN LA EJECUCION: Division por cero para datosPrecarga enteros',0 
errorDivCeroFlotante db 'ERROR EN LA EJECUCION: Division por cero para datosPrecarga de punto flotante',0 
errorRecursion db 'ERROR EN LA EJECUCION: Recursión en invocaciones de funciones',0 
flagTry dw 0,0
_program dd ?,?
_i@$ dd ?,?
_a@$ dd ?,?
_3 db 3
_1 db 1
_@aux0 dd ?,?
_@aux1 dd ?,?
.code
;------------ CODE ------------
start:
MOV EAX, _i@$
CMP EAX, _3
JLE _label1
MOV @aux0,1 
JMP _label0
_label1:
MOV @aux0, 0 
_label0:
MOV EAX, @aux0
CMP EAX,  0
JE _label2
MOV EAX,_i@$
ADD EAX,_1
MOV @aux1,EAX
MOV EAX,_@aux1
MOV _a@$,EAX
JMP _label3
_label2:
_label3:
;------------ FIN ------------
invoke ExitProcess, 0
end start