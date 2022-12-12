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
out0 db 'OK ENTERO',0
out1 db 'OK FLOTANTE',0
_program dd ?,?
_g@$ dd ?,?
_h@$ dd ?,?
_i@$ dd ?,?
_j@$ dd ?,?
_k@$ dd ?,?
_z@$ dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_1_1 dq 1.1
_10_2 dq 10.2
_1_8 dq 1.8
__3_0 dq -3.0
_3_0 dq 3.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux4 dd ?,?
@aux5 dd ?,?
@aux6 dd ?,?
@aux7 dd ?,?
.code
;------------ CODE ------------
start:
MOV EAX,11
MOV _a@$,EAX
MOV EAX,10
IMUL EAX,8
MOV @aux0,EAX
MOV EAX,@aux0
MOV _b@$,EAX
MOV EAX,_b@$
IMUL EAX,_a@$
MOV @aux1,EAX
MOV EAX,@aux1
MOV _c@$,EAX
MOV EAX,-3
IMUL EAX,_a@$
MOV @aux2,EAX
MOV EAX,@aux2
MOV _d@$,EAX
MOV EAX,_a@$
IMUL EAX,3
MOV @aux3,EAX
MOV EAX,@aux3
MOV _e@$,EAX
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
FLD _1_1
FSTP _j@$
FLD _10_2
FMUL _1_8
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNO _label4
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label4:
FSTP @aux4
FLD @aux4
FSTP _i@$
FLD _i@$
FMUL _j@$
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNO _label5
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label5:
FSTP @aux5
FLD @aux5
FSTP _g@$
FLD __3_0
FMUL _j@$
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNO _label6
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label6:
FSTP @aux6
FLD @aux6
FSTP _h@$
FLD _j@$
FMUL _3_0
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNO _label7
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label7:
FSTP @aux7
FLD @aux7
FSTP _z@$
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start