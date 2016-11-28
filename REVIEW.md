Film.java:

-Tweede constructor lijkt overbodig en parset niet de JSON verder en slaat het ook niet op als variabele in de class
-Variabelen kunnen private met een public method die ze returnt, zo kunnen ze niet aangepast worden in een andere class
-Class slim bedacht zodanig dat je het kunt sturen als Serializable

Http parser en async tasks:

-Async tasks straightforward in hun werking, goede comments
-Exceptions bij http parser kan korter met een exception bij de header, maar het is alsnog prima hoe ze zijn opgesteld

MovieViewActivity:

-onCreate kan veel korter door code in aparte functies te plaatsen
-Gedefinieerde functies duidelijk in hun werking en hun benaming

SearchActivity:

-onCreate nice en kort
-functie setData(Film foundData) lijkt ongebruikt, ook vanuit de XML

WatchListActvity:

-Adapters en listeners beter buiten de onCreate en in apart functie aanroepen in onCreate

Overall:

-Gebruik en doel van functies duidelijk, maar individuele lines ietwat onduidelijk zonder comments
-Slim gebruik van aparte class bij het sturen via SharedPreferences
-onCreates bij MovieViewActivity en WatchListActvity kunnen korter zoals bij SearchActivity
-Vrijwel geen header comments, weinig comments bij functies met name bij de activities
-Naast setData geen overbodige code

