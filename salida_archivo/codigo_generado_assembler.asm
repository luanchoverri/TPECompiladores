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
ok db 'OK',0 
mem2bytes dw ?
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_1_1 dd ?,?
_1_2 dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
.code
;------------ CODE ------------
start:
MOV EAX,1
ADD EAX,1
MOV @aux0,EAX
MOV EAX,@aux0
MOV _d@$,EAX
FLD _1_1
FADD _1_2
FSTP @aux1
FLD @aux1
FSTP _a@$
MOV EAX, 1
CMP EAX, 0 
JNE _label0
invoke MessageBox, NULL, addr errorDivCeroEntero, addr errorDivCeroEntero, MB_OK
invoke ExitProcess, 0
_label0:
MOV EAX, 2
MOV EDX, 0
MOV EBX, 1
IDIV EBX
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
MOV @aux2,EAX
MOV EAX,@aux2
MOV _e@$,EAX
;------------ FIN ------------
invoke ExitProcess, 0
end start