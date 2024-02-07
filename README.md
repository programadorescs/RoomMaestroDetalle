# Maestro-Detalle en Room usando el patrón MVVM e inyección de dependencia con Dagger Hilt
Este es un ejemplo de implementación de un maestro-detalle usando la biblioteca de persistencia de Android Room, el patrón de arquitectura MVVM (Modelo-Vista-ViewModel) y Dagger Hilt para la inyección de dependencias. Adicionalmente usaremos Google AdMob para mostrar anuncios intersticiales.

## Características

- Implementación de Room para el manejo de la base de datos.
- Utilización del patrón de arquitectura MVVM para la separación de responsabilidades.
- Utilización de Dagger Hilt para la inyección de dependencias.
- Implementación de un RecyclerView para mostrar la lista de elementos del maestro.
- Implementación de Google AdMob para mostrar publicidad interstitial.

## Requisitos

- Android Studio Hedgehog | 2023.1.1 Patch 2 o superior.
- Android Gradle Plugin Version 8.2.2
- Gradle Version 8.2
- Kotlin 1.9.22 o superior.

## Dependencias

- Room: Para la implementación de la base de datos.
- ViewModel, LiveData y StateFlow: Para la implementación del patrón MVVM.
- Dagger Hilt: Para la inyección de dependencias.
- Google AdMob

## Estructura del proyecto

- data: Contiene las clases para la implementación de la base de datos, entidades, dao y el repositorio.
- di: Inyecta el modulo (provee room mediante dagger hilt).
- domain: Contiene los modelos de datos, el repositorio y los Use Case.
- ui: Contiene las clases para la implementación de la interfaz de usuario, incluyendo los Fragment, Activity y los ViewModels. Además estará el core de la ui que implementa las utilidades para manejar mensajes, fechas, publicidad (admob) entre otras utilidades.

## Entidades de nuestra base de datos

![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/ER_Pedido.png)

## Estructura de la app

![Image text](https://github.com/programadorescs/RoomMaestroDetalle/blob/master/app/src/main/assets/estructura_app_masterdetail.png)

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

Este proyecto es un ejemplo de cómo implementar un maestro-detalle utilizando Room como base de datos, inyección de dependencia con dagger hilt y clean arquitecture.
