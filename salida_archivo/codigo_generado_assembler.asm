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
out0 db 'empece',0
out1 db 'termine',0
out2 db 'OK 1',0
out3 db 'OK 2',0
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_e@$ dd ?,?
_f@$ dd ?,?
_g@$ dd ?,?
_h@$ dd ?,?
_i@$ dd ?,?
_j@$ dd ?,?
__3_40282346F38 dq 3.40282347E+38
_12_0 dq 12.0
@aux0 dd ?,?
.code
;------------ CODE ------------
start:
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
FLD __3_40282346F38
FSTP _b@$
FLD _12_0
FSTP _c@$
FLD _b@$
FLD _c@$
FMUL 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _labelCheckMaxOk
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_labelCheckMaxOk:
invoke MessageBox, NULL, addr out2, addr out2, MB_OK
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _labelCheckMinOk
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_labelCheckMinOk:
invoke MessageBox, NULL, addr out3, addr out3, MB_OK
FSTP @aux0
FLD @aux0
FSTP _d@$
invoke MessageBox, NULL, addr out1, addr out1, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start