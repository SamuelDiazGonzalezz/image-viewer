# image-viewer

## Descripción

Image Viewer es un visor de imágenes sencillo hecho en Java con Swing. Por ahora no carga imágenes reales, sino que usa un `MockImageLoader` que genera una lista circular de "imágenes" representadas por colores (rojo, verde, azul, amarillo y naranja).

Se puede pasar de una imagen a otra arrastrando con el ratón hacia la izquierda o hacia la derecha. Mientras arrastras se ve cómo entra la imagen siguiente o la anterior, y al soltar el ratón, si has arrastrado más de la mitad del ancho de la ventana, se cambia de imagen. Si no, vuelve a la imagen que estaba.

El proyecto sigue el patrón Modelo-Vista-Presentador (MVP):

- **Modelo**: `Image` e `ImageLoader`. Cada imagen sabe cuál es la siguiente y la anterior.
- **Vista**: la interfaz `ImageDisplay` y su implementación en Swing, `SwingImageDisplay`.
- **Presentador**: `ImagePresenter`, que recibe los eventos de la vista (arrastrar y soltar) y decide qué imagen hay que pintar.
- `Main` y `MainFrame` montan la ventana y conectan todo.

## Mejoras realizadas

- **Uso de `MouseAdapter`**: antes en `SwingImageDisplay` se implementaban `MouseListener` y `MouseMotionListener` con muchos métodos vacíos. Ahora se usa un solo `MouseAdapter` y solo se sobrescriben los métodos que se usan (`mousePressed`, `mouseReleased` y `mouseDragged`).
- **Tamaño de pintado dinámico**: el método `paint(Graphics g)` pintaba siempre un rectángulo de 800x600 fijo. Ahora usa `getWidth()` y `getHeight()`, así que si se cambia el tamaño de la ventana la imagen se adapta.
- **Fondo limpio**: antes de pintar las imágenes se rellena el panel de negro para que no queden restos de pintados anteriores.
- **Color por defecto**: se usa `getOrDefault` en el mapa de colores para que, si llega un id que no existe, se pinte en gris en vez de dar un `NullPointerException`.
- **Más imágenes de prueba**: se han añadido "yellow" y "orange" al `MockImageLoader` y al mapa de colores.
- **Botones de navegación**: se ha añadido una barra abajo en `MainFrame` con los botones `<` y `>` para ir a la imagen anterior y a la siguiente sin tener que arrastrar. Para esto se añadieron los métodos `next()` y `prev()` en `ImagePresenter`, y `onPrev()` / `onNext()` en `MainFrame`.
- **Atributo `final`**: la lista `paints` ahora es `final` porque nunca se reasigna.

## Cómo ejecutarlo

Ejecutar la clase `software.ulpgc.imageviewer.swing.Main`. 
