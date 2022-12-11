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
_minFloat dq -3.402823466E-38
out0 db 'era 2',0
out1 db 'no era 2',0
out2 db 'todo ok',0
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_i@$_f_for0 dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
.code
;------------ CODE ------------
PUBLIC _f@$
_f@$ PROC
MOV EAX,3
MOV _i@$_f_for0,EAX
_label0:
MOV EAX, _i@$_f_for0
CMP EAX, 0
JL _label2
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
MOV EAX,_i@$_f_for0
SUB EAX,1
MOV @aux1,EAX
MOV EAX,@aux1
MOV _i@$_f_for0,EAX
JMP _label0
_label4:
MOV EAX, _i@$_f_for0
CMP EAX, 2
JNE _label7
MOV @aux2,1 
JMP _label6
_label7:
MOV @aux2, 0 
_label6:
MOV EAX, @aux2
CMP EAX,  0
JE _label8
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
JMP _label9
_label8:
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
_label9:
JMP _label5
_label3:
MOV EAX,_a@$
ADD EAX,1
MOV @aux3,EAX
MOV EAX, @aux3
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_f@$ ENDP
start:
MOV EAX,1
MOV _a@$,EAX
MOV EAX,0
MOV _b@$,EAX
call _f@$
MOV _b@$,EAX
invoke MessageBox, NULL, addr out2, addr out2, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start