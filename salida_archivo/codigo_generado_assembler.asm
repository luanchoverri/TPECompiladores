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
_maxFloat dq 3.40282347E+38
_minFloat dq -3.40282347E+38
out0 db 'OK ENTEROS',0
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux4 dd ?,?
.code
;------------ CODE ------------
start:
MOV EAX,3
MOV _b@$,EAX
MOV EAX,3
IMUL EAX,2
MOV @aux0,EAX
MOV EAX,@aux0
MOV _c@$,EAX
MOV EAX,-8
MOV _e@$,EAX
MOV EAX, _c@$
CMP EAX, 0 
JNE _label1
invoke MessageBox, NULL, addr errorDivCeroEntero, addr errorDivCeroEntero, MB_OK
invoke ExitProcess, 0
_label1:
MOV EAX, _e@$
CDQ
MOV EDX, 0
MOV EBX, _c@$
CDQ
IDIV EBX
MOV @aux1,EAX
MOV EAX, 1
CMP EAX, 0 
JNE _label2
invoke MessageBox, NULL, addr errorDivCeroEntero, addr errorDivCeroEntero, MB_OK
invoke ExitProcess, 0
_label2:
MOV EAX, _b@$
CDQ
MOV EDX, 0
MOV EBX, 1
CDQ
IDIV EBX
MOV @aux2,EAX
MOV EAX,@aux1
ADD EAX,@aux2
MOV @aux3,EAX
MOV EAX,@aux3
MOV _f@$,EAX
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
MOV EAX, 0
CMP EAX, 0 
JNE _label3
invoke MessageBox, NULL, addr errorDivCeroEntero, addr errorDivCeroEntero, MB_OK
invoke ExitProcess, 0
_label3:
MOV EAX, 9
CDQ
MOV EDX, 0
MOV EBX, 0
CDQ
IDIV EBX
MOV @aux4,EAX
MOV EAX,@aux4
MOV _a@$,EAX
;------------ FIN ------------
invoke ExitProcess, 0
end start