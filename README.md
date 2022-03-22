# Splio
Project for connecting to velib in Paris to display informations about the available velib in the closest distance

https://opendata.paris.fr/explore/dataset/velib-disponibilite-en-temps-reel/information/?disjunctive.name&disjunctive.is_installed&disjunctive.is_renting&disjunctive.is_returning&disjunctive.nom_arrondissement_communes

# Technology
The project is developed in Java 8, Spark 2.2.0 and Kafka 0.10. 

It is tested with Junit 4.12 and Mockito 2.26.0.

# Component architecture
The project was developed using the hexagonal architecture. The goal is to facilitate the switch of layer. To change between a file or URL or a kafka file, you can just change the layer used.


There are advantage and disadvantage in using the hexagonal architecture :

advantage | disadvantage
--- | --- 
+ Evolutivity : it is easy to add a new technical layer  | - Evolutivity : Every layer should respect the norm defined by the Port design pattern
+ Testability : Each layer can be tested separately | 
+ Reliability : The layers are abstracted from their implementation. |

# Packaging
To package the fat jar, use the following maven command :
``` maven
  mvn package 
```

The fat jar will be created in the target directory.

# Launch the application :
To launch the application, use the following maven command :
``` maven
  java -jar splio-1.0.0.jar
```

# Launch the tests :
To launch the test, use the following maven command :
``` maven
  mvn clean install
```