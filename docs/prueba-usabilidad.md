# Prueba de usabilidad del prototipo (T-09)

Requisitos: **RNF-19** (un usuario nuevo completa su primer 3×3 sin ayuda en menos de 5 min) y **RNF-20** (botones ≥ 44×44 px y contraste AA).
Prototipo: Figma «Puzzle», página *Page 1*, en modo presentación desde el teléfono (app de Figma) o el computador.

## 1. Participantes

- Mínimo **5** personas que **no** hayan visto el proyecto.
- Mezcla de edades y de experiencia con juegos en el celular.
- Cada sesión dura unos 15 minutos.

## 2. Antes de empezar (leer al participante)

> Vamos a probar el diseño de una app, no a ti. No hay respuestas malas.
> Piensa en voz alta: dime qué buscas y qué esperas que pase.
> No te puedo ayudar durante las tareas; si te quedas atascado, dime «me rindo».
> Tomaré notas, pero no voy a grabar tu nombre ni tu cara. ¿Estás de acuerdo?

## 3. Tareas

Para cada tarea: tomar el tiempo, anotar si la terminó **sin ayuda** y escribir dónde dudó.

| # | Tarea (lo que se le dice) | Termina cuando… | Requisito |
|---|---|---|---|
| 1 | «Entra a la app sin crear una cuenta.» | Llega a 03 Inicio con «Jugar como invitado». | RF-03 |
| 2 | «Juega una partida fácil.» | Llega a 05 Partida en 3×3 Clásico. | RF-19 |
| 3 | «Arma el puzzle hasta terminarlo.» | Llega a 06 Resultado. **Debe ser en menos de 5 min.** | RNF-19 |
| 4 | «Pide una pista durante la partida.» | Toca «Pista» y ve 05b. | RF-15 |
| 5 | «Pausa la partida y mira cómo se juega.» | Llega a 13 Pausa y luego a 04b. | RF-14, RF-18 |
| 6 | «Crea una cuenta.» | Llena 02 Registro y acepta el aviso (31). | RF-01, RNF-17 |
| 7 | «Agrega a un amigo con el código LM4K7XQ2.» | Llega a 18 y toca «Agregar». | RF-40 |
| 8 | «Reta a un amigo a un duelo.» | Llega a 16 Sala de espera. | RF-23 |

## 4. Al final (preguntas cortas, de 1 a 5)

1. ¿Qué tan fácil fue entender cómo se mueven las piezas?
2. ¿Qué tan fácil fue encontrar lo que buscabas?
3. ¿Los textos y botones se leían bien?
4. ¿Qué cambiarías?

## 5. Revisión de accesibilidad (RNF-20)

Hacerla una vez, sin participantes:

- [ ] Todos los botones y elementos tocables miden al menos 44×44 px.
- [ ] El texto tiene contraste de al menos 4,5:1 con su fondo (plugin «Contrast» de Figma).
- [ ] Los botones rojos (#E0384C) y amarillos (#FFB020) se revisan aparte, porque son los que más fallan.

## 6. Hoja de resultados

Copiar una fila por participante:

| Participante | Edad | T1 | T2 | T3 (tiempo) | T4 | T5 | T6 | T7 | T8 | Dónde dudó |
|---|---|---|---|---|---|---|---|---|---|---|
| P1 | | ✅/❌ | | 0:00 | | | | | | |
| P2 | | | | | | | | | | |
| P3 | | | | | | | | | | |
| P4 | | | | | | | | | | |
| P5 | | | | | | | | | | |

## 7. Criterio de aceptación

- **RNF-19 se cumple** si al menos **4 de los 5** participantes terminan la tarea 3 sin ayuda en menos de 5 min.
- Cada problema que se repite en 2 o más participantes se anota como hallazgo, se corrige en Figma y se registra abajo.

## 8. Hallazgos y cambios aplicados

| # | Hallazgo | Participantes | Cambio en Figma | Pantalla |
|---|---|---|---|---|
| 1 | | | | |
