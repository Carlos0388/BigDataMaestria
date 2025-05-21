# Resultados (Un tabular con los datos de 30 corridas por cada algoritmo y hacer un promedio para ver su preformance) y su respectiva explicacion.

Resultados de Logistic Regresion

scala>   runMultipleEvaluations(30)
| Run   | AUC promedio            | Accuracy promedio             |
|-------|-------------------------|-------------------------------|
|Run 1: | AUC = 0.9025784085484724| Accuracy = 0.8984392336711295 |
|Run 2: | AUC = 0.9093732324413533| Accuracy = 0.898762901690945  |
|Run 3: | AUC = 0.9039656381089449| Accuracy = 0.9014031945066427 |
|Run 4: | AUC = 0.9131343369650117| Accuracy = 0.9017366780466083 |
|Run 5: | AUC = 0.9087754564635946| Accuracy = 0.8962728809821363 |
|Run 6: | AUC = 0.9078854435177982| Accuracy = 0.8976424073802899 |
|Run 7: | AUC = 0.9052430384053917| Accuracy = 0.9014346242473797 |
|Run 8: | AUC = 0.9102315613465715| Accuracy = 0.8976453274466519 |
|Run 9: | AUC = 0.9005660243568101| Accuracy = 0.8993706088992974 |
|Run 10:| AUC = 0.9042717064821624| Accuracy = 0.8983442720763724 |
|Run 11:| AUC = 0.9045968069577834| Accuracy = 0.8988943846377655 |
|Run 12:| AUC = 0.9032993272775091| Accuracy = 0.8966703256494695 |
|Run 13:| AUC = 0.9055611573539034| Accuracy = 0.8977718557998371 |
|Run 14:| AUC = 0.9065094151273877| Accuracy = 0.895472948104527  |
|Run 15:| AUC = 0.9082846402938022| Accuracy = 0.8997913872746237 |
|Run 16:| AUC = 0.9080941571453367| Accuracy = 0.8971338535414166 |
|Run 17:| AUC = 0.9089046147299094| Accuracy = 0.899162749706228  |
|Run 18:| AUC = 0.9085801612665946| Accuracy = 0.896508544490277  |
|Run 19:| AUC = 0.9094197259096215| Accuracy = 0.8972542072630647 |
|Run 20:| AUC = 0.9045450239593552| Accuracy = 0.9011471990464839 |
|Run 21:| AUC = 0.9048140934426597| Accuracy = 0.8990158636897767 |
|Run 22:| AUC = 0.9011486687614395| Accuracy = 0.9020310633213859 |
|Run 23:| AUC = 0.9090007448825609| Accuracy = 0.9016067216981132 |
|Run 24:| AUC = 0.9054106271869292| Accuracy = 0.8958722101963126 |
|Run 25:| AUC = 0.9018723938605933| Accuracy = 0.900726571767497  |
|Run 26:| AUC = 0.9025329630530448| Accuracy = 0.8985539488320355 |
|Run 27:| AUC = 0.9048213899626164| Accuracy = 0.8967761017450459 |
|Run 28:| AUC = 0.9096549458043552| Accuracy = 0.903032517354768  |
|Run 29:| AUC = 0.9017422748976868| Accuracy = 0.9016321129245699 |
|Run 30:| AUC = 0.9074583764201674| Accuracy = 0.8969746524938675 |

AUC promedio: 0.9060758784976455
Accuracy promedio: 0.8989027116161503

----------------------------------------------------------------

Resultados de Multilayer Perceptron

scala> runMultipleEvaluations(30)
| Run    | Accuracy promedio                      | 
|--------|----------------------------------------|
|Run 1:  | Test set accuracy = 0.8930453879941435 |
|Run 2:  | Test set accuracy = 0.8913219789132197 |
|Run 3:  | Test set accuracy = 0.8812518496596626 |
|Run 4:  | Test set accuracy = 0.8961170675166619 |
|Run 5:  | Test set accuracy = 0.8922146749272225 |
|Run 6:  | Test set accuracy = 0.8909237418504139 |
|Run 7:  | Test set accuracy = 0.8870991686460807 |
|Run 8:  | Test set accuracy = 0.894273453684522  |
|Run 9:  | Test set accuracy = 0.8921496698459281 |
|Run 10: | Test set accuracy = 0.8823443437591347 |
|Run 11: | Test set accuracy = 0.8899662112531218 |
|Run 12: | Test set accuracy = 0.8903958133706789 |
|Run 13: | Test set accuracy = 0.8903401962954763 |
|Run 14: | Test set accuracy = 0.8813185196085644 |
|Run 15: | Test set accuracy = 0.8855493041160793 |
|Run 16: | Test set accuracy = 0.8839484215206759 |
|Run 17: | Test set accuracy = 0.8836813268177106 |
|Run 18: | Test set accuracy = 0.8871410551309639 |
|Run 19: | Test set accuracy = 0.8792309379340595 |
|Run 20: | Test set accuracy = 0.894070619586942  |
|Run 21: | Test set accuracy = 0.8854697070326913 |
|Run 22: | Test set accuracy = 0.8798187625343534 |
|Run 23: | Test set accuracy = 0.8813571961222968 |
|Run 24: | Test set accuracy = 0.8928861039439735 |
|Run 25: | Test set accuracy = 0.8908891328210757 |
|Run 26: | Test set accuracy = 0.8929203539823009 |
|Run 27: | Test set accuracy = 0.8834821428571429 |
|Run 28: | Test set accuracy = 0.8901949331970505 |
|Run 29: | Test set accuracy = 0.8840175953079179 |
|Run 30: | Test set accuracy = 0.8839982334756367 |

Accuracy promedio: 0.8877139301235234

Comparando los resultados de **Logistic Regression** y **Multilayer Perceptron** en las 30 corridas:

| Algoritmo               | Accuracy promedio | AUC promedio      |
|-------------------------|-------------------|-------------------|
| Logistic Regression     | 0.8989            | 0.9061            |
| Multilayer Perceptron   | 0.8877            | —                 |

### Análisis

- **Logistic Regression** tiene un accuracy promedio de **0.8989** y un AUC promedio de **0.9061**.
- **Multilayer Perceptron** tiene un accuracy promedio de **0.8877**.

### Explicación

- **Logistic Regression** supera al Multilayer Perceptron tanto en accuracy como en AUC.  
- El AUC (Área Bajo la Curva ROC) es una métrica importante para clasificación binaria, y Logistic Regression también muestra un valor alto y consistente.
- Aunque el Multilayer Perceptron es un modelo más complejo y flexible, en este caso no logra superar el desempeño de la regresión logística, probablemente porque los datos no requieren una arquitectura tan compleja o porque el modelo no está suficientemente ajustado.

### Conclusión

**Logistic Regression es el mejor modelo para este conjunto de datos**, ya que obtiene mejores resultados de accuracy y AUC en promedio. Además, es más sencillo de interpretar y entrenar. Si se busca la simplicidad y rendimiento de los algoritmos, Logistic Regression es la mejor opción en este caso.

[⬅️ Volver al Índice](./README.md)