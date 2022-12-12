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
_b@$ dd ?,?
_a@$ dd ?,?
_j@$ dd ?,?
_k@$ dd ?,?
_z@$_fun3 dd ?,?
_l@$_fun3 dd ?,?
_h@$_fun3 dd ?,?
_i@$_fun3 dd ?,?
_1_0 dq 1.0
_f@$ dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun2@$_fun3
_fun2@$_fun3 PROC
MOV _h@$_fun3,EAX
FSTP _i@$_fun3
MOV EAX,1
MOV _h@$_fun3,EAX
FLD _1_0
FADD _i@$_fun3
FSTP @aux0
FLD @aux0
FSTP _i@$_fun3
FLD _i@$_fun3
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
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
MOV EAX,_b@$
ADD EAX,1
MOV @aux1,EAX
MOV EAX,@aux1
MOV _b@$,EAX
MOV EAX,4
MOV _f@$,EAX
;------------ FIN ------------
invoke ExitProcess, 0
end start