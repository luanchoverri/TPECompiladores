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
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_g@$ dd ?,?
_1_0 dq 1.0
_2_2 dq 2.2
_5_3 dq 5.3
_10_2 dq 10.2
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
.code
;------------ CODE ------------
start:
FLD _1_0
FLD _2_2
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label0
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label0:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label1
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label1:
FSTP @aux0
FLD _10_2
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label2
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label2:
FLD _5_3
FDIV _10_2
FST @aux1
FLD @aux0
FADD @aux1
FSTP @aux2
FLD @aux2
FSTP _a@$
;------------ FIN ------------
invoke ExitProcess, 0
end start