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
_program dd ?,?
_a@$ dd ?,?
_b@$ dd ?,?
_e@$ dd ?,?
_c@$ dd ?,?
_d@$ dd ?,?
_fun1@$ dd ?,?
_g@$ dd ?,?
_fun2@$ dd ?,?
_k@$ dd ?,?
_l@$ dd ?,?
.code
;------------ CODE ------------
PUBLIC _fun2@$
_fun2@$ PROC
MOV _k@$,EAX
FSTP _l@$
MOV EAX,1
MOV _k@$,EAX
FLD _2_1
ret 
_fun2@$ ENDP
PUBLIC _fun1@$
_fun1@$ PROC
MOV _g@$,EAX
MOV EAX,1
MOV _g@$,EAX
MOV EAX, g@$
ret 
_fun1@$ ENDP
start:
MOV EAX,1
MOV _a@$,EAX
FLD _1_3
FADD _1_1
FSTP @aux0
FLD @aux0
FSTP _d@$
FLD 5_1
FSTP _c@$
FLD _c@$
MOV EAX, _b@$
call _fun2@$
FSTP _d@$
;------------ FIN ------------
invoke ExitProcess, 0
end start