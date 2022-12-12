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
_h@$ dd ?,?
_p@$ dd ?,?
_1_ dq 1.0
_j@$ dd ?,?
_k@$ dd ?,?
_z@$_fun3 dd ?,?
_l@$_fun3 dd ?,?
_c@$ dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_i@$_for0 dd ?,?
_i@$_for1 dd ?,?
_r@$ dd ?,?
_q@$ dd ?,?
_m@$ dd ?,?
__1 dq 0.1
_3_ dq 3.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux4 dd ?,?
@aux5 dd ?,?
@aux6 dd ?,?
@aux7 dd ?,?
@aux8 dd ?,?
@aux9 dd ?,?
@aux10 dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun2@$
_fun2@$ PROC
FSTP _h@$
FSTP _p@$
FLD _1_
FADD _p@$
FSTP @aux0
FLD @aux0
FSTP _h@$
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
FLD _h@$
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_fun2@$ ENDP
PUBLIC _fun3@$
_fun3@$ PROC
MOV _j@$,EAX
MOV _k@$,EBX
MOV EAX,1
MOV _z@$_fun3,EAX
MOV EAX,2
MOV _l@$_fun3,EAX
MOV EAX, 1
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
MOV EAX,1
MOV _i@$_for1,EAX
_label11:
MOV EAX, _i@$_for1
CMP EAX, 3
JGE _label13
MOV @aux6,1 
JMP _label12
_label13:
MOV @aux6, 0 
_label12:
MOV EAX, @aux6
CMP EAX, 0 
JE _label14
JNE _label15
_label16:
MOV EAX,_i@$_for1
ADD EAX,1
MOV @aux7,EAX
MOV EAX,@aux7
MOV _i@$_for1,EAX
JMP _label11
_label15:
MOV EAX, _i@$_for1
CMP EAX, 1
JNE _label18
MOV @aux8,1 
JMP _label17
_label18:
MOV @aux8, 0 
_label17:
MOV EAX, @aux8
CMP EAX,  0
JE _label19
JMP _label14
JMP _label20
_label19:
_label20:
MOV EAX,_i@$_for1
ADD EAX,1
MOV @aux9,EAX
MOV EAX,@aux9
MOV _i@$_for1,EAX
JMP _label16
_label14:
MOV EAX, _b@$
CMP EAX, _a@$
JGE _label22
MOV @aux10,1 
JMP _label21
_label22:
MOV @aux10, 0 
_label21:
MOV EAX, @aux10
CMP EAX,  0
JE _label23
MOV EAX,_a@$
MOV _b@$,EAX
JMP _label24
_label23:
MOV EAX,_b@$
MOV _a@$,EAX
_label24:
FLD __1
FSTP _q@$
FLD _3_
FSTP _m@$
FLD _m@$
FLD _q@$
call _fun2@$
FSTP _r@$
invoke MessageBox, NULL, addr out2, addr out2, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start