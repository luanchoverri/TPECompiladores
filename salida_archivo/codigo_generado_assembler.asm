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
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_8_3 dq 8.3
_2_3 dq 2.3
_2_0 dq 2.0
__8_3 dq -8.3
_1_ dq 1.0
_3_9 dq 3.9
__0 dq 0.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux4 dd ?,?
.code
;------------ CODE ------------
start:
FLD _8_3
FSTP _b@$
FLD _2_3
FMUL _2_0
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JG _label0
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label0:
FSTP @aux0
FLD @aux0
FSTP _c@$
FLD __8_3
FSTP _e@$
FLD _c@$
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label1
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label1:
FLD _e@$
FDIV _c@$
FST @aux1
FLD _1_
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label2
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label2:
FLD _b@$
FDIV _1_
FST @aux2
FLD @aux1
FADD @aux2
FSTP @aux3
FLD @aux3
FSTP _f@$
FLD __0
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label3
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label3:
FLD _3_9
FDIV __0
FST @aux4
FLD @aux4
FSTP _a@$
;------------ FIN ------------
invoke ExitProcess, 0
end start