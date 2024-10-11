# Proyecto: Aplicación de Distribuidora de Alimentos

## Descripción del Proyecto
Este proyecto tiene como objetivo el desarrollo de una aplicación para una empresa de distribución de alimentos que ha incluido un servicio de despacho a domicilio basado en reglas de negocio específicas. Además, se busca gestionar el cálculo automáticos del costo del despacho en función de la distancia recorrida y el monto de compra.

## Requerimientos del Proyecto

### Requerimientos Funcionales
1. **Cálculo Automático del Costo de Despacho**
   - La aplicación debe calcular automáticamente el costo de despacho en base a las siguientes reglas:
     - **Compras mayores a $50,000**: Despacho gratuito dentro de un radio de 20 km.
     - **Compras entre $25,000 y $49,999**: Cobro de $150 por kilómetro recorrido.
     - **Compras menores a $25,000**: Cobro de $300 por kilómetro recorrido.

2. **Visualización de la Ubicación en un Mapa**
   - La aplicación debe mostrar la ubicación actual del dispositivo en un mapa utilizando Google Maps API o cualquier servicio de mapas compatible con Android Studio o AppInventor.

3. **Compatibilidad con Android Lollipop y Oreo**
   - El administrador del local utiliza Android Lollipop, mientras que la mayoría de los clientes potenciales usan Android Oreo. La aplicación debe funcionar correctamente en ambos sistemas operativos.

### Requerimientos No Funcionales
1. **Seguridad**
   - Asegurar que las credenciales de Gmail y los datos sensibles se manejen de manera segura en la aplicación.

2. **Performance**
   - La aplicación debe ser rápida y eficiente, sin demoras perceptibles durante el cálculo de costos de despacho o la visualización de la ubicación en el mapa.

3. **Usabilidad**
   - La aplicación debe ser fácil de usar, con una interfaz clara e intuitiva, accesible para los usuarios sin formación técnica.

4. **Documentación**
   - Se debe documentar detalladamente el proceso de creación, compilación y ejecución del código fuente, para facilitar la comprensión y futuras modificaciones.

## Plan de Desarrollo

### Tareas a Realizar
1. **Generar la Aplicación con Android Studio**
   - Crear una aplicación móvil que integre un mapa que muestre la ubicación actual del dispositivo usando GPS.
   
2. **Cálculo de Costo de Despacho**
   - Desarrollar la lógica de negocio para calcular automáticamente el costo de despacho basado en la distancia y el monto de compra. Para ello, se utilizará la API de Google Maps o el servicio de mapas elegido, para determinar la distancia entre el punto de origen (almacén) y la ubicación del cliente.
   
3. **Pruebas del Producto**
   - Elaborar un plan de pruebas, describiendo detalladamente los casos de prueba, los datos de entrada y los resultados esperados.

## Estructura del Repositorio
- `MainActivity.kt`: Gestión del login y autenticación con Firebase.
- `README.md`: Documentación del proceso de desarrollo, compilación y pruebas.
- `LICENSE`: Licencia del proyecto.

## Documentación del Desarrollo
1. **Creación del Código Fuente**
   - Se comienza con la creación de la interfaz gráfica en Android Studio utilizando XML para las vistas, y luego se integra la lógica de autenticación y visualización del mapa con las clases correspondientes.
   
2. **Compilación del Proyecto**
   - El proyecto se compila utilizando Android Studio, asegurando que no haya errores en las dependencias y que las APIs de Google Maps y Firebase Authentication estén correctamente configuradas.

3. **Ejecución del Proyecto**
   - Se ejecuta la aplicación en un emulador o dispositivo físico, verificando que el cálculo de despacho funcione correctamente, la ubicación se muestre en el mapa y las alertas por temperatura sean emitidas en los casos correspondientes.

## Plan de Pruebas

| ID de Prueba | Funcionalidad Probada  | Datos de Entrada                               | Resultado Esperado                                                                                       |
|--------------|------------------------|------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| 1            | Ubicacion actual       | Iniciar aplicativo        | Al arrancar el aplicativo, se debe mostrar la ubicacion actual.                          |
| 2            | Calculo costo despacho | Monto de la compra y dirección        | Al presionar boton "buscar dirección y calcular despacho" deberia desplegarse un label que indique la distancia en kilometros y el costo del despacho.                 |


## Autor
- Nicole Díaz

## Licencia
Este proyecto está licenciado bajo los términos de la [Licencia MIT](LICENSE).
