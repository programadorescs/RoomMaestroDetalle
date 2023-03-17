# Maestro-Detalle en Room usando el patrón MVVM e inyección de dependencia con Dagger Hilt
Este es un ejemplo de implementación de un maestro-detalle usando la biblioteca de persistencia de Android Room, el patrón de arquitectura MVVM (Modelo-Vista-ViewModel) y Dagger Hilt para la inyección de dependencias. Adicionalmente usaremos Google AdMob para mostrar anuncios intersticiales.

## Características

- Implementación de Room para el manejo de la base de datos.
- Utilización del patrón de arquitectura MVVM para la separación de responsabilidades.
- Utilización de Dagger Hilt para la inyección de dependencias.
- Implementación de un RecyclerView para mostrar la lista de elementos del maestro.
- Implementación de un Fragment para mostrar los detalles del elemento seleccionado del maestro.

## Requisitos

- Android Studio Electric Eel | 2022.1.1 Patch 1 o superior.
- Gradle 7.5 o superior.
- Kotlin 1.8.10 o superior.

## Dependencias

- Room: Para la implementación de la base de datos.
- ViewModel y LiveData: Para la implementación del patrón MVVM.
- Dagger Hilt: Para la inyección de dependencias.

## Estructura del proyecto

- core: Contiene las clases comunes para la implementación de mensajes, fechas, publicidad (admod) y demas utilidades.
- data: Contiene las clases para la implementación de la base de datos y el repositorio.
- di: Contiene las clases para la configuración de Dagger Hilt.
- ui: Contiene las clases para la implementación de la interfaz de usuario, incluyendo los Fragments y los ViewModels.

## Implementación
### Base de datos

La implementación de la base de datos se encuentra en el paquete **data**. Se utiliza la librería Room para la creación de la base de datos y la definición de las entidades y los DAOs. En este proyecto se utilizan tres tablas Producto, Pedido y Detalle_Pedido con sus respectivos campos.

### Repositorio

El repositorio se encarga de manejar la fuente de datos y proveer los datos necesarios a la interfaz de usuario. Se encuentra en el paquete **data** y se comunica con la base de datos a través de los DAOs. En este proyecto, temenos dos repositorios (PedidoRepository y ProductoRepository).

### ViewModel

Los ViewModels se encargan de manejar la lógica de la interfaz de usuario y proveer los datos necesarios. Se encuentra en el paquete **ui.viewmodel** y se comunican con el repositorio para obtener los datos. En este proyecto, tenemos tres viewmodels (PedidoViewModel, ProductoViewModel y ReportePedidoViewModel).
### Dagger Hilt

Dagger Hilt es una librería de inyección de dependencias para Android. En este proyecto se utiliza para la inyección de dependencias de los ViewModels y el repositorio. La configuración de Dagger Hilt se encuentra en el paquete **di**. Se define un módulo para la base de datos y otro módulo para el repositorio. También se define un componente para la inyección de dependencias de los ViewModels.
### Interfaz de usuario

La interfaz de usuario se encuentra en el paquete **ui**. Se utiliza un RecyclerView para mostrar la lista de elementos del maestro y un Fragment para mostrar los detalles del elemento seleccionado del maestro. El Fragment utiliza un ViewModel para obtener los datos necesarios además de un fragment para realizar el crud de la tabla Producto.

## Entidades de nuestra base de datos

![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/ER_Pedido.png)

## Imagenes de la app

### Menú principal
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104345_pe.pcs.roommaestrodetalle.jpg)

### Catálogo de productos para realizar pedidos

![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104357_pe.pcs.roommaestrodetalle.jpg)

### Indicar la cantidad y/o modificar el precio

![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104401_pe.pcs.roommaestrodetalle.jpg)

### Lista de productos a confirmar, tiene la opcion de agregar un nombre de cliente
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104424_pe.pcs.roommaestrodetalle.jpg)

### Confirmación del pedido
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104446_pe.pcs.roommaestrodetalle.jpg)

### Muestra la publicidad de Google Admob
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104453_pe.pcs.roommaestrodetalle.jpg)

### Registro o actualizacion de un producto
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104532_pe.pcs.roommaestrodetalle.jpg)

### Lista de productos
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104543_pe.pcs.roommaestrodetalle.jpg)

### Reporte de pedidos según fechas
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104735_pe.pcs.roommaestrodetalle.jpg)

### Muestra el detalle de un pedido realizado
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104742_pe.pcs.roommaestrodetalle.jpg)

### Anular un pedido según confirmación
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104755_pe.pcs.roommaestrodetalle.jpg)

### Ahora el pedido 3 está anulado
![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/Screenshot_20230309_104802_pe.pcs.roommaestrodetalle.jpg)

## Conclusiones

Este proyecto es un ejemplo de cómo implementar un maestro-detalle utilizando Room como base de datos e inyección de dependencia con dagger hilt.
