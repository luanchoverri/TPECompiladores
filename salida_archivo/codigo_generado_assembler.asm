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
_maxFloat dq 3.40282347E38
_minFloat dq 1.17549435E-38
out0 db 'hola when',0
out1 db 'hola if',0
_program dd ?,?
_i@$ dd ?,?
_x@$ dd ?,?
_y@$ dd ?,?
__1_0 dq -1.0
_z@$ dd ?,?
__0 dq 0.0
_a@$_when0 dd ?,?
_b@$_when0 dd ?,?
_c@$_when0 dd ?,?
_k@$_when0_fun2 dd ?,?
_l@$_when0_fun2 dd ?,?
_1_0 dq 1.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun2@$_when0
_fun2@$_when0 PROC
MOV EAX,1
MOV _k@$_when0_fun2,EAX
FLD _l@$_when0_fun2
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun2@$_when0 ENDP
PUBLIC _fun1@$
_fun1@$ PROC
MOV EAX, 0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun1@$ ENDP
start:
MOV EAX,1
MOV _i@$,EAX
MOV EAX,-12
MOV _x@$,EAX
FLD __1_0
FSTP _y@$
FLD __0
FSTP _z@$
MOV EAX, _i@$
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
MOV EAX,6
MOV _a@$_when0,EAX
MOV EAX,2
IMUL EAX,_a@$_when0
MOV @aux1,EAX
MOV EAX,@aux1
MOV _b@$_when0,EAX
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
MOV EAX, _i@$
CMP EAX, _b@$_when0
JGE _label5
MOV @aux2,1 
JMP _label4
_label5:
MOV @aux2, 0 
_label4:
MOV EAX, @aux2
CMP EAX,  0
JE _label6
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
JMP _label7
_label6:
_label7:
FLD _1_0
MOV EAX, _a@$_when0
call _fun2@$_when0
FSTP _c@$_when0
JMP _label8
_label2:
_label8:
;------------ FIN ------------
invoke ExitProcess, 0
end start