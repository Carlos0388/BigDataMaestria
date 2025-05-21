# MARCO TEORICO

## Support Vector Machines (SVM)
Las Máquinas de Vector de Soporte son un algoritmo de aprendizaje supervisado que busca un hiperplano óptimo para separar clases, maximizando el margen entre ellas para problemas no lineales.
> Alpaydin, E. (2020). Introduction to machine learning (4th ed.). MIT Press.

Un ejemplo viable sobre la utilización de este algoritmo es decidir si un correo es spam o no, **SVM** analiza palabras clave dentro del correo (como “**gratis**” o “**urgente**”) y traza una línea para separar los correos “*spam*” de los “no *spam*”, en principio todos serian marcados como "*spam*" pero en caso de no aceptar o aceptar va registrando el comportamiento para saber identificarlo, aunque contenga dichas palabras.

## Decision Trees
Son modelos de aprendizaje supervisado que representan decisiones como una estructura jerárquica de nodos, donde cada nodo corresponde a una prueba sobre una característica, cada rama a un resultado de dicha prueba, y las hojas a las clases o valores predichos.
> Alpaydin, E. (2020). Introduction to machine learning (4th ed.). MIT Press.

El algoritmo de árbol de decisiones tiene este nombre por la forma y secuencia de sus divisiones que se asemeja a un árbol con un centro y se va ramificando (Vease *FIGURA 1*). El algoritmo es muy utilizado en toma de decisiones secuenciales.

![whatis-decision-tree](https://github.com/user-attachments/assets/b44d16c1-e7dc-43f6-818f-160cd76357be)
>FIGURA 1

## Logistic Regression
La Regresión Logística es un modelo de clasificación supervisada que estima la probabilidad de que una instancia pertenezca a una clase específica, utilizando una función logística para transformar una combinación lineal de las características en un valor entre 0 y 1. Es particularmente útil en problemas de clasificación binaria.
> Goodfellow, I., Bengio, Y., & Courville, A. (2016). Deep learning. MIT Press. 

El algoritmo funciona como una balanza que analiza y dependiendo de los datos proporcionados predice qué tan probable es que algo sea de una categoría u otra, por ejemplo aprobar un examen, dependiendo de las notas a lo largo de todo el curso, asistencias y participaciones puede asegurar un porcentaje final.

## Multilayer Perceptrons (MLP)
El Perceptrón Multicapa es una red neuronal artificial compuesta por múltiples capas de nodos interconectados, capaces de aprender representaciones no lineales de los datos. Cada nodo procesa una combinación ponderada de las entradas, aplica una función de activación y propaga el resultado a la siguiente capa, ajustándose mediante retro propagación.
> Goodfellow, I., Bengio, Y., & Courville, A. (2016). Deep learning. MIT Press. 

El más complejo y utilizado por las inteligencias artificiales, es como un cerebro artificial con muchas capas de “*neuronas*” que trabajan juntas para encontrar patrones difíciles. Probando y corrigiendo errores poco a poco, se retro alimenta de cada iteración, por eso se dice que **aprende**.

[⬅️ Volver al Índice](./README.md)