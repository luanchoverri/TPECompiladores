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
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_1_0 db 1.0
_5_7 db 5.7
_1_5 db 1.5
_i@$_for0 dd ?,?
_1 db 1
_3 db 3
_1 db 1
_2 db 2
_t1@$_for0_if_then1 dd ?,?
_@aux0 dd ?,?
_@aux1 dd ?,?
_@aux2 dd ?,?
_@aux4 dd ?,?
_@aux5 dd ?,?
_@aux6 dd ?,?
_@aux7 dd ?,?
.code
;------------ CODE ------------
start:
FLD _1_0
FSTP _a@$
FLD _a@$
FADD _5_7
FSTP @aux0
FLD _@aux0
FSTP _b@$
FLD _a@$
FIMUL_b@$
FSTP @aux1
FLD _1_5
FLDZ 
FCOMPP 
FSTSW @aux3
MOV EAX,@aux3
SAHF 
JNE _label0
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr program, MB_OK
invoke ExitProcess, 0
_label0:
FLD _a@$
FIDIV_1_5
FSTP @aux2
FLD @aux1
FADD _@aux2
FSTP @aux4
FLD _@aux4
FSTP _c@$
MOV EAX,_1
MOV _i@$_for0,EAX
_label1:
MOV EAX, _i@$_for0
CMP EAX, _3
JGE _label3
MOV @aux5,1 
JMP _label2
_label3:
MOV @aux5, 0 
_label2:
MOV EAX, @aux5
CMP EAX, 0 
JE _label4
JNE _label5
_label6:
MOV EAX,_i@$_for0
SUB EAX,_uno
MOV @aux6,EAX
MOV EAX,_@aux6
MOV _i@$_for0,EAX
JMP _label1
_label5:
MOV EAX, _i@$_for0
CMP EAX, _1
JGE _label8
MOV @aux7,1 
JMP _label7
_label8:
MOV @aux7, 0 
_label7:
MOV EAX, @aux7
CMP EAX,  0
JE _label9
JMP _t1
JMP _label10
_label9:
_label10:
JMP _label6
_label4:
;------------ FIN ------------
invoke ExitProcess, 0
end start