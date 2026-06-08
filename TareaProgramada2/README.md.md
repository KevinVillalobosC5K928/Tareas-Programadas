Integrante:  
Kevin Villalobos Alfaro.  
C5K928.

Para compilar el programa debe asegurarse de tener correctamente guardados el archivo .gif, puesto puede generar distintos problemas, además asegurarse de no tener distintas versiones de java, pues un problema que experimente es que yo tengo java 21, pero Imagen.jar esta en java 8, en todo caso se necesita compilarlo y ejecutarlo en la opcion antigua, para ser sincero algo a mejorar es la compilación, pues todavía no me queda claro cómo compilarlo, pero una vez cambiado la versión a la antigua, no debería de haber mayor problema.

Compilación:

1. Ingresar el comando: javac \-cp ".;Imagen.jar" \*.java (no hay opción para GUI)  
2. Si no funciona, use la ruta completa al JDK de BlueJ

Ejecución:

1. Abrir el proyecto en Bluej  
2. Compilar todas las clases  
3. Darle Click derecho a Main y pulsar void main(String\[\] args)  
4. De ser necesario ingresar {dibujo.gif} en el argumento

Algo a mejorar es el orden, puesto que con el ejemplo de dibujo proporcionado, en el penúltimo paso, dice “Mover del recipiente 3 al 1” y en mi codigo lanza “1 al 3”, que no parece ser importante en imágenes pequeñas, pues el resultado es el mismo. Tampoco he logrado que funcione con imágenes de distinto tamaño, seguramente sea por la diferencia de tamaño, consecuentemente cambia la estructura visual