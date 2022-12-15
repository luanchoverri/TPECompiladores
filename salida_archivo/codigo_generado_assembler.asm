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
out0 db ':)',0
_program dd ?,?
_i@$_for0 dd ?,?
@aux0 dd ?,?
@aux1 dd ?,?
.code
;------------ CODE ------------
start:
MOV EAX,0
MOV _i@$_for0,EAX
_label0:
MOV EAX, _i@$_for0
CMP EAX, 25
JGE _label2
MOV @aux0,1 
JMP _label1
_label2:
MOV @aux0, 0 
_label1:
MOV EAX, @aux0
CMP EAX, 0 
JE _label3
JNE _label4
_label5:
MOV EAX,_i@$_for0
ADD EAX,5
MOV @aux1,EAX
MOV EAX,@aux1
MOV _i@$_for0,EAX
JMP _label0
_label4:
invoke MessageBox, NULL, addr out0, addr out0, MB_OK
JMP _label5
_label3:
;------------ FIN ------------
invoke ExitProcess, 0
end start