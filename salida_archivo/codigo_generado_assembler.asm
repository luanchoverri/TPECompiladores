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
out0 db 'en fun2',0
out1 db 'a era menor',0
out2 db 'ejecucion terminda',0
_program dd ?,?
_j@$ dd ?,?
_k@$ dd ?,?
_z@$_fun3 dd ?,?
_l@$_fun3 dd ?,?
_h@$_fun3 dd ?,?
_i@$_fun3 dd ?,?
_1_ dq 1.0
_c@$ dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_i@$_for0 dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux4 dd ?,?
@aux5 dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun2@$_fun3
_fun2@$_fun3 PROC
FSTP _h@$_fun3
FSTP _i@$_fun3
FLD _1_
FADD _i@$_fun3
FSTP @aux0
FLD @aux0
FSTP _h@$_fun3
FLD _h@$_fun3
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
_fun2@$_fun3 ENDP
PUBLIC _fun3@$
_fun3@$ PROC
MOV _j@$,EAX
FSTP _k@$
MOV EAX, _j@$
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun3@$ ENDP
start:
MOV EAX,2
MOV _c@$,EAX
MOV EAX,3
MOV _a@$,EAX
MOV EAX,_a@$
IMUL EAX,2
MOV @aux1,EAX
MOV EAX,@aux1
ADD EAX,5
MOV @aux2,EAX
MOV EAX,@aux2
MOV _b@$,EAX
MOV EAX,0
MOV _i@$_for0,EAX
_label1:
MOV EAX, _i@$_for0
CMP EAX, 6
JGE _label3
MOV @aux3,1 
JMP _label2
_label3:
MOV @aux3, 0 
_label2:
MOV EAX, @aux3
CMP EAX, 0 
JE _label4
JNE _label5
_label6:
MOV EAX,_i@$_for0
ADD EAX,1
MOV @aux4,EAX
MOV EAX,@aux4
MOV _i@$_for0,EAX
JMP _label1
_label5:
MOV EAX, _a@$
CMP EAX, 3
JGE _label8
MOV @aux5,1 
JMP _label7
_label8:
MOV @aux5, 0 
_label7:
MOV EAX, @aux5
CMP EAX,  0
JE _label9
MOV EAX,_b@$
MOV _a@$,EAX
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
JMP _label10
_label9:
MOV EAX,0
MOV _a@$,EAX
JMP _label6
_label10:
JMP _label6
_label4:
invoke MessageBox, NULL, addr out2, addr out2, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start