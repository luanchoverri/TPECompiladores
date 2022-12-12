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
out0 db 'for',0
out1 db 'aca',0
out2 db 'aca1',0
out3 db 'aca2',0
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_3_2 dq 3.2
_2_0 dq 2.0
_5_1 dq 5.1
_i@$_for0 dd ?,?
_3_9 dq 3.9
_0_0 dq 0.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux5 dd ?,?
@aux4 dd ?,?
@aux6 dd ?,?
.code
;------------ CODE ------------
start:
FLD _3_2
FSTP _a@$
FLD _a@$
FMUL _2_0
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNO _label0
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label0:
FSTP @aux0
FLD @aux0
FADD _5_1
FSTP @aux1
FLD @aux1
FSTP _b@$
MOV EAX,0
MOV _i@$_for0,EAX
_label1:
MOV EAX, _i@$_for0
CMP EAX, 6
JGE _label3
MOV @aux2,1 
JMP _label2
_label3:
MOV @aux2, 0 
_label2:
MOV EAX, @aux2
CMP EAX, 0 
JE _label4
JNE _label5
_label6:
MOV EAX,_i@$_for0
ADD EAX,1
MOV @aux3,EAX
MOV EAX,@aux3
MOV _i@$_for0,EAX
JMP _label1
_label5:
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
FLD _a@$
FLD _3_9
FCOMPP 
FSTSW mem2bytes
MOV EAX, mem2bytes
SAHF 
JAE _label8
FLD1
FSTP @aux4 
JMP _label7
_label8:
FLDZ 
FSTP @aux4 
_label7:
FLDZ 
FLD @aux4
FCOMPP 
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF 
JE _label9
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
FLD _b@$
FSTP _a@$
invoke MessageBox, NULL, addr out2, addr out2, MB_OK
JMP _label10
_label9:
invoke MessageBox, NULL, addr out3, addr out3, MB_OK
FLD _0_0
FSTP _a@$
JMP _label6
_label10:
JMP _label6
_label4:
;------------ FIN ------------
invoke ExitProcess, 0
end start