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
out0 db 'entre en funcion',0
out1 db 'entre al if',0
_program dd ?,?
_i@$_g dd ?,?
_1_0 dq 1.0
_a@$ dd ?,?
_b@$ dd ?,?
@aux0 dd ?,?
.code
;------------ CODE ------------
PUBLIC _g@$
_g@$ PROC
MOV _i@$_g,EAX
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
MOV EAX, _i@$_g
CMP EAX, 1
JNE _label1
MOV @aux0,1 
JMP _label0
_label1:
MOV @aux0, 0 
_label0:
MOV EAX, @aux0
CMP EAX,  0
JE _label2
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
FLD _1_0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
JMP _label3
_label2:
_label3:
_g@$ ENDP
start:
MOV EAX,1
MOV _a@$,EAX
MOV EAX, _a@$
call _g@$
FSTP _b@$
;------------ FIN ------------
invoke ExitProcess, 0
end start