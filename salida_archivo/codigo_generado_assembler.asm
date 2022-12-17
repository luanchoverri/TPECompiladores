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
_minFloat dq 1.17549435F-38
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_1_7 dq 1.7
_0_0 dq 0.0
_2_2 dq 2.2
__0 dq 0.0
_3_9 dq 3.9
_0_ dq 0.0
_5_0 dq 5.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
.code
;------------ CODE ------------
start:
FLD _0_0
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label0
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label0:
FLD _1_7
FDIV _0_0
FST @aux0
FLD @aux0
FSTP _a@$
FLD __0
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label1
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label1:
FLD _2_2
FDIV __0
FST @aux1
FLD @aux1
FSTP _b@$
FLD _0_
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label2
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label2:
FLD _3_9
FDIV _0_
FST @aux2
FLD @aux2
FSTP _c@$
FLD _5_0
FSTP _d@$
FLD _0_0
FSTP _e@$
FLD _e@$
FLDZ
FCOM
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JNE _label3
invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK
invoke ExitProcess, 0
_label3:
FLD _d@$
FDIV _e@$
FST @aux3
FLD @aux3
FSTP _f@$
;------------ FIN ------------
invoke ExitProcess, 0
end start