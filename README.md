## Centro Logístico de Distribución Avanzada



Sistema de gestión para un centro de distribución logística, desarrollado en Java como Trabajo Practico Obligatorio de la materia Programación II. El sistema permite administrar el inventario, controlar el stock critico, gestionar la trazabilidad de movimientos, calcular rutas de recolección dentro del almacén y manejar la línea de expedición de pedidos.





**Repositorio**





GitHub - matucastelli/Mate-y-Java





**Integrantes del grupo**



* Tomás Giorgi
* Matías Castelli



**Nombre del grupo: Mate y Java**



**Alternativa elegida**



**Alternativa C: Centro Logístico de Distribución Avanzada.** Gestión del almacenamiento, stock y rutas de despacho de una cadena de suministros.







**Estructuras de datos utilizadas**



Todas las estructuras fueron implementadas desde cero cada una con su propia interfaz y clase de implementación:



1. Pila (Registra la trazabilidad de los movimientos, todo lo que son ingresos, egresos, alta de productos, etc.. Permite deshacer el último movimiento)
2. Cola (Línea de expedición con orden FIFO. También usada internamente en recorrido BFS sobre el grafo del almacén)
3. Cola con prioridad (Monitorea el stock crítico, ordenándolos de menor a mayor por stock)
4. Diccionario (Árbol AVL, localiza productos por código con búsqueda)
5. Grafo (lista de adyacencia, Mapeo de pasillos del almacén. Permite calcular la ruta mínima de recolección mediante BFS)



**Funcionalidades implementadas**



**Inventario**



* Alta de producto, con validación de códigos duplicados.
* Baja de producto, con confirmación previa.
* Búsqueda de producto por código.
* Registro de ingreso y egreso de stock, con validación de cantidades.
* Listado del inventario completo ordenado por código.
* Consulta de la cantidad total de productos distintos.



**Stock critico**





* Listado de los N productos con menor stock disponible.
* Verificación de si un producto puntual se encuentra en estado critico.
* Trazabilidad
* Registro de cada movimiento de stock (ingreso/egreso) con fecha, cantidad y usuario.
* Visualización del historial completo de movimientos.
* Reversión del ultimo movimiento registrado (undo).



**Línea de expedición**



* Encolado de pedidos listos para despacho.
* Despacho del próximo pedido en espera (orden FIFO).
* Consulta de los pedidos pendientes en la cola.



**Rutas del almacén**





* Carga de pasillos (vértices) y conexiones entre pasillos (aristas).
* Visualización del mapa completo de pasillos y sus conexiones.
* Calculo de la ruta mínima entre dos pasillos mediante BFS.
* Flujo de pedido.
* Procesamiento completo de un pedido: localización del producto, verificación de stock disponible, alerta de stock critico, calculo de la ruta de recolección desde la entrada del deposito, actualización de stock, registro en trazabilidad y encolado en la línea de expedición.



**Instrucciones básicas sobre el programa**



A la hora de poner el código del producto se usa la terminología PXXX y para el lote lo mismo LXXX. Para crear un pasillo o nombrarlo se hace de la siguiente manera:

Pasillo-A para poner un ejemplo. 





**Como probar el sistema**



El proyecto incluye un documento de casos de prueba (Documento\_Casos\_de\_Prueba.docx) con 16 escenarios verificados, cubriendo entradas validas, casos limite (estructuras vacías, códigos inexistentes, cantidades invalidas) y el flujo completo de procesamiento de un pedido. Las pruebas pueden reproducirse manualmente siguiendo las opciones numeradas del menú principal.



**Actividades realizadas por cada integrante**



Tomás Giorgi: Cola de prioridad, Grafo, Línea de expedición, Monitoreo de stock critico, Registro de trazabilidad, Main.

Matías Castelli: Pila, Cola, Diccionario AVL, Movimiento, Pedido, Producto, Gestor de inventario, Main.

