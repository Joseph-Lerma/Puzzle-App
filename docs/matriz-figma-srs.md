# Matriz de correspondencia Figma – SRS (T-70)

Fuente de requisitos: SRS unificado (29 de octubre de 2026), secciones 3.2 y 3.5.4.
Fuente de diseño: Figma «Puzzle», página *Page 1* (marcos de 360×800).
Alcance de la tarjeta: RF-01 a RF-51, salvo RF-38 y RF-50 (fuera de alcance), más RNF-19 y RNF-20.

Estado: **✅ cubierto** · **⚠️ parcial** (falta algo en el diseño) · **❌ sin pantalla**.

## 1. Cuenta y acceso

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-01 | Registro con nombre, correo y contraseña o Google | 02 Registro, 02b Registro · Error, 31 Aviso de privacidad | ⚠️ | Falta el botón «Registrarse con Google» en 02. |
| RF-02 | Inicio de sesión con correo o Google | 01 Login, 01b Login · Error | ⚠️ | Falta el botón «Continuar con Google» en 01. 01b ya muestra «Correo o contraseña incorrectos». |
| RF-03 | Jugar como invitado | 01 Login («Jugar como invitado»), 26 Invitado · Función bloqueada | ✅ | |
| RF-04 | Recuperar contraseña con reenvío a los 60 s | 21 Recuperar contraseña | ✅ | Muestra «¿No te llegó? Reenviar». |
| RF-05 | Cerrar sesión | 22 Configuración | ✅ | |
| RF-06 | Splash y onboarding de 2 pantallas | 19 Splash, 20 y 20b Onboarding | ✅ | |
| RF-07 | Eliminar cuenta | 22 Configuración, 30 Eliminar cuenta | ✅ | |

## 2. Motor del puzzle

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-08 | Tablero N×N | 05 Partida | ✅ | |
| RF-09 | Mezcla con solución | 05 Partida | ✅ | Es lógica del motor; no requiere pantalla propia. |
| RF-10 | Mover piezas adyacentes | 04b Cómo se juega, 05 Partida | ✅ | |
| RF-11 | Movimientos y tiempo | 05 Partida | ✅ | |
| RF-12 | Detectar fin | 06 Resultado | ✅ | |
| RF-13 | Progreso | 05 Partida («11/15 · 73 %») | ✅ | |
| RF-14 | Pausa | 13 Pausa | ✅ | |
| RF-15 | Pistas (máximo 2) | 05 Partida, 05b Pista activa | ✅ | |
| RF-16 | Vista previa al mantener presionado | 05 Partida | ✅ | |
| RF-17 | Reiniciar o rendirse | 05 Partida, 13 Pausa | ⚠️ | No hay opción «Rendirse» ni la vista de la solución. |
| RF-18 | Tutorial «Cómo se juega» | 04b Cómo se juega, 13 Pausa | ✅ | |

## 3. Modos, imágenes y resultados

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-19 | Modo Clásico | 04 Modos de juego, 05 Partida | ✅ | |
| RF-20 | Modo Contrarreloj y «Tiempo agotado» | 04 Modos de juego, 14 Tiempo agotado | ✅ | |
| RF-21 | Resultado con puntos, récord y logros | 06 Resultado | ⚠️ | Falta la lista de logros desbloqueados. |
| RF-22 | Racha en Inicio | — | ❌ | Prioridad Baja; si se implementa, agregarla en 03 Inicio. |

## 4. Duelo VS

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-23 | Retar a un amigo | 07 Crear desafío, 25b Duelos sin amigos | ✅ | |
| RF-24 | Aceptar o rechazar; expira a los 2 min | 15 Desafío recibido, 15b Aviso, 15c No disponible, 17 Mis duelos | ✅ | |
| RF-25 | Misma imagen y mezcla | 16b Cuenta regresiva | ✅ | |
| RF-26 | Sala de espera y cuenta de 3 s | 16 Sala de espera, 16b Cuenta regresiva | ✅ | |
| RF-27 | Avance del rival en vivo | 08 Partida en vivo | ⚠️ | Falta mostrar los puntos estimados del rival. |
| RF-28 | «Esperando rival» con 15 s | 08b Rival sin conexión | ⚠️ | Falta la cuenta regresiva de 15 s. |
| RF-29 | Desconexiones y abandono | 08c Sin conexión, 08d Confirmar abandono, 09d Victoria por abandono | ✅ | |
| RF-30 | Fin y ganador | 09 Resultado, 09e Validando | ✅ | |
| RF-31 | Resultado con desglose, trofeos y revancha | 09 Resultado | ⚠️ | Falta mostrar los trofeos sumados (+30, +10 o 0). |
| RF-32 | Rival al azar | — | ❌ | Falta la pantalla «Buscar rival» con su búsqueda de 30 s. |

## 5. Galería y puzzles propios

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-33 | Explicar el permiso antes del aviso del sistema | — | ❌ | Falta la pantalla que explica el permiso de cámara y galería. |
| RF-34 | Subir o tomar foto | 10 Mis fotos, 25 Mis fotos vacía | ⚠️ | Solo dice «Elegir foto»; falta la opción «Tomar foto». |
| RF-35 | Recortar, rotar y voltear | 23 Recortar foto | ⚠️ | Faltan los botones Rotar y Voltear. |
| RF-36 | Crear puzzle con nombre, dificultad y modo | 10b Crear puzzle desde foto | ⚠️ | Falta el campo «Nombre del puzzle». |
| RF-37 | Pestañas Mis fotos, Completados, Favoritos | 10 Mis fotos, 25 Mis fotos vacía | ⚠️ | Faltan las pestañas Completados y Favoritos. |

## 6. Amigos y ranking

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-39 | Código de amigo | 18 Agregar amigo, 12 Perfil | ✅ | |
| RF-40 | Buscar por código o nombre y enviar solicitud | 18 Agregar amigo | ⚠️ | Solo busca por código exacto; falta la búsqueda por nombre y el estado «Pendiente». |
| RF-41 | Aceptar o rechazar solicitudes | 18 Agregar amigo, 18b Mis amigos | ✅ | |
| RF-42 | Ranking Global, Amigos y Duelos VS | 11 Ranking | ⚠️ | Falta la pestaña «Duelos VS» y el podio top 3. |
| RF-43 | Posición propia y top 10 | 11 Ranking | ⚠️ | Falta la fila fija del jugador. |
| RF-44 | Cierre de temporada | 11 Ranking | ⚠️ | Falta poder consultar la temporada anterior. |

## 7. Perfil y logros

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-45 | Perfil | 12 Perfil | ⚠️ | Faltan los trofeos de la temporada y el total. |
| RF-46 | Logros | — | ❌ | Falta la pantalla de logros (bloqueados y desbloqueados). |
| RF-47 | Historial de duelos | 17 Mis duelos (pestaña Historial), 12 Perfil | ✅ | |
| RF-48 | Editar nombre y avatar | 12 Perfil | ⚠️ | Falta la pantalla de edición. |

## 8. Configuración y notificaciones

| RF | Requisito | Pantallas Figma | Estado | Observación |
|---|---|---|---|---|
| RF-49 | Sonidos, vibración, ayudas y notificaciones | 22 Configuración | ⚠️ | Faltan los interruptores de cada tipo de notificación. |
| RF-51 | Notificaciones push | 15b Aviso de desafío | ⚠️ | Falta el aviso de solicitud de amistad y de resultado de duelo. |

## 9. Requisitos no funcionales de la tarjeta

| RNF | Requisito | Cómo se verifica en Figma | Estado |
|---|---|---|---|
| RNF-17 | Aviso de privacidad antes del registro (Ley 1581) | 02 Registro + 31 Aviso de privacidad | ✅ |
| RNF-19 | Primer 3×3 sin ayuda en menos de 5 min | Prueba de usabilidad con el prototipo (T-09) | Pendiente de T-09 |
| RNF-20 | Botones ≥ 44×44 px y contraste AA 4,5:1 | Revisión con el plugin de contraste | ✅ Botones de 52 px de alto; texto principal #1E1B2E sobre #F6F4FF |

## 10. Resumen

- Requisitos revisados: 49 (RF-01 a RF-51 sin RF-38 ni RF-50).
- ✅ Cubiertos: 26 · ⚠️ Parciales: 19 · ❌ Sin pantalla: 4 (RF-22, RF-32, RF-33, RF-46).
- Diferencia con el SRS: la pantalla **04c** dice «Catálogo · 12 imágenes incluidas», pero el SRS usa la **API de imágenes** (RF-52). Hay que cambiar ese texto.

### Pendientes para el diseño (por prioridad)

1. **Alta:** botones de Google en 01 y 02 (RF-01, RF-02); pantalla de permiso de cámara y galería (RF-33); campo de nombre en 10b (RF-36); trofeos en 09 (RF-31).
2. **Media:** «Rendirse» en 13 (RF-17); Rotar y Voltear en 23 (RF-35); pestañas en 10 (RF-37); pestaña Duelos VS en 11 (RF-42); búsqueda por nombre en 18 (RF-40); cuenta de 15 s en 08b (RF-28).
3. **Baja:** racha (RF-22), rival al azar (RF-32), logros (RF-46), top 10 (RF-43).
