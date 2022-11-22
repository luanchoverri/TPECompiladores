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
;------------ CODE ------------
.code
MOV EAX,1
MOV a~$,EAX
_franchu:
MOV EAX,1
MOV i~$#for0,EAX
_label0:
MOV EAX, i~$#for0
CMP EAX, 3
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
MOV EAX,i~$#for0
SUB EAX,uno
MOV @aux1,EAX
MOV EAX,@aux1
MOV i~$#for0,EAX
JMP _label0
_label4:
MOV EAX,i~$#for0
MOV a~$,EAX
JMP _franchu
JMP _label5
_label3:
;------------ FIN ------------
invoke ExitProcess, 0
end start