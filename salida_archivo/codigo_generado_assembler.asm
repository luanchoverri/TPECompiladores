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
_h@$ dd ?,?
_i@$ dd ?,?
_j@$ dd ?,?
_k@$ dd ?,?
_l@$ dd ?,?
_m@$ dd ?,?
_n@$ dd ?,?
__3_2F10 dq -3.2E10
__12_0 dq -12.0
_3_4 dq 3.4
_2_0 dq 2.0
__5_9 dq -5.9
__0_1 dq -0.1
_2_1F13 dq 2.1E13
_3_2 dq 3.2
_3_F_5 dq 3.0F-5
_15_0 dq 15.0
_3_40282346F38 dq 3.40282346E38
_12_0 dq 12.0
__3_40282346F38 dq -3.40282346E38
__1_17549436F_38 dq -1.17549436E-38
_1_17549436F_38 dq 1.17549436E-38
@aux0 dd ?,?
@aux1 dd ?,?
@aux2 dd ?,?
@aux3 dd ?,?
@aux4 dd ?,?
@aux5 dd ?,?
@aux6 dd ?,?
@aux7 dd ?,?
@aux8 dd ?,?
@aux9 dd ?,?
.code
;------------ CODE ------------
start:
FLD __3_2F10
FLD __12_0
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
FLD @aux0
FSTP _a@$
FLD _3_4
FLD _2_0
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label2
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label2:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label3
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label3:
FSTP @aux1
FLD @aux1
FSTP _e@$
FLD __5_9
FLD __0_1
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label4
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label4:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label5
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label5:
FSTP @aux2
FLD @aux2
FSTP _f@$
FLD _2_1F13
FLD _3_2
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label6
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label6:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label7
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label7:
FSTP @aux3
FLD @aux3
FSTP _g@$
FLD _3_F_5
FSTP _h@$
FLD _15_0
FSTP _i@$
FLD _h@$
FLD _i@$
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label8
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label8:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label9
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label9:
FSTP @aux4
FLD @aux4
FSTP _j@$
FLD _3_40282346F38
FLD _12_0
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label10
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label10:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label11
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label11:
FSTP @aux5
FLD @aux5
FSTP _l@$
FLD __3_40282346F38
FLD _12_0
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label12
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label12:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label13
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label13:
FSTP @aux6
FLD @aux6
FSTP _m@$
FLD __1_17549436F_38
FLD _12_0
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label14
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label14:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label15
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label15:
FSTP @aux7
FLD @aux7
FSTP _n@$
FLD _1_17549436F_38
FLD __12_0
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label16
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label16:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label17
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label17:
FSTP @aux8
FLD @aux8
FSTP _k@$
FLD __3_40282346F38
FSTP _b@$
FLD _12_0
FSTP _c@$
FLD _b@$
FLD _c@$
FMUL 
FABS 
FCOM _maxFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JB _label18
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label18:
FCOM _minFloat
FSTSW mem2bytes
MOV AX, mem2bytes
SAHF
JA _label19
invoke MessageBox, NULL, addr errorOverflow, addr errorOverflow, MB_OK
invoke ExitProcess, 0
_label19:
FSTP @aux9
FLD @aux9
FSTP _d@$
;------------ FIN ------------
invoke ExitProcess, 0
end start