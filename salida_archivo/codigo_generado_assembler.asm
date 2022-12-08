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
;------------ CODE ------------
start:
MOV EAX,_1
MOV _i@$_for0,EAX
_label0:
MOV EAX, _i@$_for0
CMP EAX, _3
JGE _label2
MOV @aux0,1 
JMP _label1
_label2:
MOV @aux0, 0 
_label1:
MOV EAX, @aux0
CMP EAX, 0 
JE _label3
JNE _label4
_label5:
MOV EAX,_i@$_for0
ADD EAX,_uno
MOV @aux1,EAX
MOV EAX,_@aux1
MOV _i@$_for0,EAX
JMP _label0
_label4:
MOV EAX, _i@$_for0
CMP EAX, _1
JNE _label7
MOV @aux2,1 
JMP _label6
_label7:
MOV @aux2, 0 
_label6:
MOV EAX, @aux2
CMP EAX,  0
JE _label8
JMP _label9
_label8:
_label9:
JMP _label5
_label3:
MOV EAX,_1
MOV _i@$_for1,EAX
_label10:
MOV EAX, _i@$_for1
CMP EAX, _3
JGE _label12
MOV @aux3,1 
JMP _label11
_label12:
MOV @aux3, 0 
_label11:
MOV EAX, @aux3
CMP EAX, 0 
JE _label13
JNE _label14
_label15:
MOV EAX,_i@$_for1
ADD EAX,_uno
MOV @aux4,EAX
MOV EAX,_@aux4
MOV _i@$_for1,EAX
JMP _label10
_label14:
JMP _label15
_label13:
;------------ FIN ------------
invoke ExitProcess, 0
end start