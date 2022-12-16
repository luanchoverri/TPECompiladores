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
_program dd ?,?
_i@$ dd ?,?
_a@$_when0 dd ?,?
_b@$_when0 dd ?,?
_c@$_when0 dd ?,?
_2_0 dq 2.0
_z@$_when0 dd ?,?
_x@$_when0 dd ?,?
_k@$_when0 dd ?,?
_l@$_when0 dd ?,?
@aux0 dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun2@$_when0
_fun2@$_when0 PROC
MOV _k@$_when0,EAX
FSTP _l@$_when0
MOV EAX,1
MOV _k@$_when0,EAX
FLD _l@$_when0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun2@$_when0 ENDP
start:
MOV EAX,1
MOV _i@$,EAX
MOV EAX, 1
CMP EAX, 3
JGE _label1
MOV @aux0,1 
JMP _label0
_label1:
MOV @aux0, 0 
_label0:
MOV EAX, @aux0
CMP EAX,  0
JE _label2
;------------ FIN ------------
invoke ExitProcess, 0
end start