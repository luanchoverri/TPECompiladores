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
_maxFloat dq 3.402823466E38
_minFloat dq -1.175494351E-38
out0 db 'OK',0
_program dd ?,?
_g@$ dd ?,?
_h@$ dd ?,?
_i@$ dd ?,?
_j@$ dd ?,?
_k@$ dd ?,?
_z@$ dd ?,?
_11_4 dq 11.4
_10_2 dq 10.2
_1_8 dq 1.8
_1_2 dq 1.2
_1_0 dq 1.0
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
.code
;------------ CODE ------------
start:
FLD _11_4
FSTP _j@$
FLD _10_2
FADD _1_8
FSTP @aux0
FLD @aux0
FSTP _i@$
FLD _i@$
FADD _j@$
FSTP @aux1
FLD @aux1
FSTP _g@$
FLD _g@$
FADD _1_2
FSTP @aux2
FLD @aux2
FSTP _k@$
FLD _1_0
FADD _g@$
FSTP @aux3
FLD @aux3
FSTP _z@$
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
;------------ FIN ------------
invoke ExitProcess, 0
end start