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
_h@$_f_p dd ?,?
_g@$_f dd ?,?
@aux0 dd ?,?
.code
;------------ CODE ------------
PUBLIC _p@$
_p@$ PROC
MOV _h@$_f_p,EAX
MOV EAX, 6
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_p@$ ENDP
PUBLIC _f@$
_f@$ PROC
MOV EAX, 3
call _p@$_f
MOV _g@$_f, EAX
MOV EAX, 1
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_f@$ ENDP
PUBLIC _p@$_f
_p@$_f PROC
MOV _h@$_f_p,EAX
MOV EAX,_h@$_f_p
ADD EAX,1
MOV @aux0,EAX
MOV EAX, @aux0
invoke MessageBox, NULL, addr ok, addr ok, MB_OK
ret 
_p@$_f ENDP
start:
;------------ FIN ------------
invoke ExitProcess, 0
end start