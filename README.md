##########################################
##										##
##			Projet Informatique			##
##										##
##	Grapheur d'expression mathématiques	##
##										##
##	Télécom Lille : Harmonisation		##
##	Professeur : Mr Wanouss				##
##	Rendu : 02/12/16					##
##										##
##	Équipe : 	Bourgeois Pierre		##
##				Courand Alexandre		##
##				Marcaille Florent		##
##										##
##	Répartition :						##
##		Analyse Lexicale : 	 Pierre		##
##		Analyse Syntaxique : Florent	##
##		Évaluateur :		 Pierre		##
##		Partie Grapgique :	 Alexandre	##
##										##
##########################################

Introduction :
	Le projet s'inscrit dans le cadre de 
l'harmonisation scientifique de début du 
cycle ingénieur pour les profils info.

Projet :
	Le grapheur d'expression mathématiques
 prends en entrée une chaine de 
caractère de la part d'un utilisateur. 
Dans le cas où cette chaîne de caractère
 s'avère être une expression mathématique
correcte, l'application affiche la 
fonction mathématique correspondante.

Utilisation :
	L'utilisateur exécute le fichier .jar
ayant le nom de l'application.
Une fenêtre affichant un cadrillage ainsi 
qu'une zone de texte (située en bas de 
la fenêtre) s'ouvre. Il ne reste plus 
qu'a écrire dans cette zone de texte une 
expression mathématique et appuyer sur 
"Entrée". La fonction correspondante est
 ensuite affiché sur le cadrillage.
L'utilisateur peut ensuite déplacer en 
bougant la souris le long de la fonction
 tracée un curseur indiquant les 
coordonnées en haut à gauche de l'écran.
L'utilisateur peut également à l'aide de
 la molette de la souris zoomer et 
dézoomer, respectivement molette vers 
l'avant et molette vers l'arrière.
L'utilisateur peut se déplacer au sein 
des axes en maintenant le clic gauche 
de la souris enfoncée tout en la 
déplacant de façon opposée au côté 
souhaiter (swipe).
Pour dessiner une nouvelle courbe, 
il suffit d'effacer l'expression dans 
la zone de texte, la remplacer par une
 nouvelle et appuyer sur "Entrée"

Test :
	Des cas de tests unitaires sont 
retrouvables dans le package
/test/graphtest/* .