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
pop AX
CMP 1,0
JNE _label0
invoke MessageBox, NULL, addr error, addr error, MB_OK
invoke ExitProcess, 0
_label0: 
push 0
FSTP f
FLD _5_4
FSTP f
FLD 5.4
FSTP f
pop AX
CMP 1,0
JNE _label1
invoke MessageBox, NULL, addr error, addr error, MB_OK
invoke ExitProcess, 0
_label1: 
push 0
MOV e,EAX
FSTP e
MOV EAX,3
MOV e,EAX
FLD 3
FSTP e
pop AX
CMP 1,0
JNE _label2
invoke MessageBox, NULL, addr error, addr error, MB_OK
invoke ExitProcess, 0
_label2: 
push 0
MOV g$,EAX
FSTP g$
MOV EAX,1
MOV g$,EAX
FLD 1
FSTP g$
FLD i
FLD 1
;------------ FIN ------------
invoke ExitProcess, 0
end start